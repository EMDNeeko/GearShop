package com.example.gearshop.controller;

import com.example.gearshop.model.HoaDon;
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
        // Lấy hóa đơn từ session hoặc model
        HoaDon hoaDon = (HoaDon) session.getAttribute("hoaDon");
        if (hoaDon == null) {
            model.addAttribute("error", "Không tìm thấy hóa đơn.");
            return "redirect:/order";
        }

        // Tính tổng tiền từ hóa đơn
        double totalPrice = hoaDon.getTongGia().doubleValue();
        int qrAmount = (int)(totalPrice / 1000); // Giá trị QR dựa trên tổng tiền

        // Lưu qrAmount vào session
        model.addAttribute("qrAmount", qrAmount);

        // Truyền hóa đơn vào giao diện
        model.addAttribute("hoaDon", hoaDon);

        return "clientTemplate/thanhtoan";
    }
}