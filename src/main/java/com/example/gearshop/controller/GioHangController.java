package com.example.gearshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GioHangController {

    @GetMapping("/cart")
    public String showCartPage() {
        // Trả về giao diện giohang.html
        return "clientTemplate/giohang";
    }
}