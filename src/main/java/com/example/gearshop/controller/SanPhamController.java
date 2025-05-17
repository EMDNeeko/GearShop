package com.example.gearshop.controller;

import com.example.gearshop.model.SanPham;
import com.example.gearshop.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/sanpham")
    public String danhSachSanPham(Model model) {
        List<SanPham> list = sanPhamService.getAllSanPham();
        model.addAttribute("sanphams", list);
        return "sanpham"; // Trả về tên view Thymeleaf: sanpham.html
    }
}
