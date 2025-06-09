package com.example.gearshop.controller;

import com.example.gearshop.model.HoaDon;
import com.example.gearshop.model.KhachHang;
import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.repository.KhachHangRepository;
import com.example.gearshop.repository.ThongTinNhanHangRepository;
import com.example.gearshop.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
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

    @PostMapping("/update-payment-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(@RequestParam int orderId, @RequestParam double amount) {
        try {
            HoaDon hoaDon = hoaDonService.findById(orderId);
            if (hoaDon == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Không tìm thấy hóa đơn với ID: " + orderId
                ));
            }

            hoaDon.setTrangThaiDonHang("Đã thanh toán");

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Trạng thái thanh toán đã được cập nhật"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Lỗi cập nhật: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/update-payment-status-extra-missing")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updatePaymentStatusExtraMissing(
            @RequestParam int orderId,
            @RequestParam String type,
            @RequestParam double amount) {
        try {
            HoaDon hoaDon = hoaDonService.findById(orderId);
            if (hoaDon == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Không tìm thấy hóa đơn với ID: " + orderId
                ));
            }

            String statusPay;
            if ("extra".equals(type)) {
                statusPay = "Thanh toán thừa";
            } else if ("missing".equals(type)) {
                statusPay = "Thanh toán thiếu";
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "Loại thanh toán không hợp lệ"
                ));
            }

            hoaDon.setTrangThaiDonHang(statusPay);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Trạng thái thanh toán đã được cập nhật"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Lỗi cập nhật: " + e.getMessage()
            ));
        }
    }
}