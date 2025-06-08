package com.example.gearshop.controller;

import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.model.KhachHang;
import com.example.gearshop.model.ThongTinNhanHang;
import com.example.gearshop.service.ThongTinNhanHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GioHangController {

    @Autowired
    private ThongTinNhanHangService thongTinNhanHangService;

    @PostMapping("/save-selected-items")
    public ResponseEntity<Void> saveSelectedItems(@RequestBody List<Map<String, Object>> selectedItems, HttpSession session) {
        session.setAttribute("selectedItems", selectedItems);
        return ResponseEntity.ok().build(); // Trả về phản hồi HTTP 200 OK
    }

    @GetMapping("/cart")
    public String showCartPage() {
        // Trả về giao diện giohang.html
        return "clientTemplate/giohang";
    }

    @GetMapping("/order")
    public String showOrderPage(HttpSession session, Model model) {
        // Lấy thông tin người dùng từ session
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null) {
            model.addAttribute("error", "Bạn cần đăng nhập để đặt hàng.");
            return "redirect:/dangnhap";
        }
        model.addAttribute("nguoiDung", nguoiDung);

        // Lấy thông tin khách hàng từ session
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang == null) {
            model.addAttribute("error", "Không tìm thấy thông tin khách hàng.");
            return "redirect:/cart";
        }
        model.addAttribute("khachHang", khachHang);

        // Lấy danh sách thông tin nhận hàng ứng với khachHangID
        List<ThongTinNhanHang> thongTinNhanHangList = thongTinNhanHangService.getThongTinNhanHangByKhachHangID(khachHang.getId());
        model.addAttribute("receivers", thongTinNhanHangList);

        // Lấy các sản phẩm đã chọn từ session
        List<Map<String, Object>> selectedItems = (List<Map<String, Object>>) session.getAttribute("selectedItems");
        if (selectedItems == null || selectedItems.isEmpty()) {
            model.addAttribute("error", "Bạn chưa chọn sản phẩm nào.");
            return "redirect:/cart";
        }

        // Xử lý chuyển đổi giá trị price và tính tổng tiền
        double totalPrice = 0;
        for (Map<String, Object> item : selectedItems) {
            int quantity = Integer.parseInt(item.get("quantity").toString());
            String priceString = item.get("price").toString();
            double price = Double.parseDouble(priceString.replace(",", "").replace("₫", "").trim()); // Chuyển đổi giá trị để tính toán
            item.put("priceNumeric", price); // Thêm giá trị đã chuyển đổi vào map
            totalPrice += quantity * price;
        }

        model.addAttribute("cart", selectedItems);
        model.addAttribute("totalPrice", totalPrice);

        // Trả về giao diện xemhoadon.html
        return "clientTemplate/xemhoadon";
    }

    @PostMapping("/save-order-info")
    public String saveOrderInfo(HttpSession session, @RequestParam String tenNguoiDung, @RequestParam String email,
                                @RequestParam String sdt, @RequestParam String diachi, Model model) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null) {
            model.addAttribute("error", "Bạn cần đăng nhập để lưu thông tin.");
            return "redirect:/dangnhap";
        }

        // Cập nhật thông tin người dùng
        nguoiDung.setTenNguoiDung(tenNguoiDung);
        nguoiDung.setEmail(email);
        nguoiDung.setSdt(sdt);
        nguoiDung.setDiaChi(diachi);

        session.setAttribute("nguoiDung", nguoiDung); // Lưu lại thông tin vào session

        model.addAttribute("nguoiDung", nguoiDung);
        return "redirect:/order"; // Quay lại trang xem hóa đơn
    }
}