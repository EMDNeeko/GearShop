package com.example.gearshop.controller;

import com.example.gearshop.model.NguoiDung;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ThanhToanController {

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        // Lấy thông tin người dùng từ session
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        model.addAttribute("nguoiDung", nguoiDung);

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        model.addAttribute("cart", cart);

        // Tính tổng tiền
        double totalPrice = 0;
        if (cart != null) {
            for (Map<String, Object> item : cart) {
                int quantity = (int) item.get("quantity");
                double price = (double) item.get("price");
                totalPrice += quantity * price;
            }
        }
        model.addAttribute("totalPrice", totalPrice);

        return "clientTemplate/thanhtoan";
    }
}
