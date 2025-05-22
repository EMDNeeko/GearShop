package com.example.gearshop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.gearshop.repository.SanPhamRepository;
import com.example.gearshop.repository.LoaiSanPhamRepository;
import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.LoaiSanPham;
import com.example.gearshop.model.ThuongHieu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    private SanPhamRepository sanPhamRepo;

    @Autowired
    private LoaiSanPhamRepository loaiSPRepo;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("sanPhamBanChay", sanPhamRepo.findTop10ByOrderByDaBanDesc());

        List<String> tenLoaiList = List.of("Mainboard", "CPU", "RAM", "VGA", "Ổ cứng", "Nguồn", "tản nhiệt", "case",
                "màn hình");

        Map<String, List<SanPham>> sanPhamTheoLoai = new LinkedHashMap<>();
        for (String tenLoai : tenLoaiList) {
            LoaiSanPham loai = loaiSPRepo.findByTenLoaiSanPham(tenLoai);
            if (loai != null) {
                sanPhamTheoLoai.put(tenLoai, sanPhamRepo.findTop10ByLoaiSanPhamOrderByDaBanDesc(loai));
            }
        }
        model.addAttribute("sanPhamTheoLoai", sanPhamTheoLoai);

        return "clientTemplate/trangchu"; // home.html trong templates
    }
}
