package com.example.gearshop.controller;

import com.example.gearshop.model.HoaDon;
import com.example.gearshop.model.ThongTinNhanHang;
import com.example.gearshop.model.KhachHang;
import com.example.gearshop.service.HoaDonService;
import com.example.gearshop.service.ThongTinNhanHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HoaDonController {

    @Autowired
    private ThongTinNhanHangService thongTinNhanHangService;

    @Autowired
    private HoaDonService hoaDonService;

    @PostMapping("/save-order")
    public String saveOrder(HttpSession session, @RequestParam String tenNguoiNhan, @RequestParam String sdt, @RequestParam String email,
                            @RequestParam String diachi, Model model) {
        // Lấy thông tin khách hàng từ session
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang == null) {
            model.addAttribute("error", "Không tìm thấy thông tin khách hàng.");
            return "redirect:/order";
        }

        // Lưu thông tin nhận hàng
        ThongTinNhanHang thongTinNhanHang = thongTinNhanHangService.createThongTinNhanHang(khachHang.getId(), tenNguoiNhan, email, sdt, diachi);

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("selectedItems");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống.");
            return "redirect:/order";
        }

        // Tính tổng giá trị hóa đơn
        double tongGia = 0;
        for (Map<String, Object> item : cart) {
            int quantity = Integer.parseInt(item.get("quantity").toString());
            double price = Double.parseDouble(item.get("price").toString().replace(",", "").replace("₫", "").trim());
            tongGia += quantity * price;
        }

        // Lưu hóa đơn
        HoaDon hoaDon = hoaDonService.createHoaDon("HD", thongTinNhanHang.getId(), tongGia);

        // Lưu chi tiết hóa đơn
        for (Map<String, Object> item : cart) {
            int quantity = Integer.parseInt(item.get("quantity").toString());
            double price = Double.parseDouble(item.get("price").toString().replace(",", "").replace("₫", "").trim());
            int sanPhamID = Integer.parseInt(item.get("sanPhamID").toString());
            hoaDonService.createHoaDonChiTiet("HDCT", hoaDon.getId(), sanPhamID, quantity, quantity * price);
        }

        // Chuyển hướng đến giao diện thanh toán
        model.addAttribute("hoaDon", hoaDon);
        return "clientTemplate/thanhtoan";
    }
}
