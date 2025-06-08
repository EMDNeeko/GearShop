package com.example.gearshop.controller;

import com.example.gearshop.model.KhachHang;
import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.repository.KhachHangRepository;
import com.example.gearshop.repository.ThongTinNhanHangRepository;
import com.example.gearshop.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ThanhToanController {

    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private ThongTinNhanHangRepository thongTinNhanHangRepository;

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        // Lấy thông tin người dùng từ session
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        model.addAttribute("nguoiDung", nguoiDung);

        // Lấy thông tin khách hàng từ session
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang == null) {
            model.addAttribute("error", "Bạn cần đăng nhập với vai trò khách hàng để thanh toán.");
            return "redirect:/dangnhap";
        }
        model.addAttribute("khachHang", khachHang);

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        model.addAttribute("cart", cart);

        // Tính tổng tiền từ giỏ hàng
        double totalPrice = 0;
        if (cart != null) {
            for (Map<String, Object> item : cart) {
                int quantity = (int) item.get("quantity");
                double price = (double) item.get("price");
                totalPrice += quantity * price;
            }
        }
        model.addAttribute("totalPrice", totalPrice);

        // Tạo mã hóa đơn mới với giá trị QR động là 5000
        double qrAmount = 5000; // Giá trị cố định cho mã QR
        String maHoaDon = "HD" + String.valueOf(System.currentTimeMillis()).substring(6);
        // hoaDonService.createHoaDon(maHoaDon, khachHang, totalPrice);

        // Truyền mã hóa đơn và giá trị QR vào giao diện
        model.addAttribute("qrAmount", qrAmount);
        model.addAttribute("orderId", maHoaDon);

        return "clientTemplate/thanhtoan";
    }
}