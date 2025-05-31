package com.example.gearshop.controller;

import com.example.gearshop.model.SanPhamCPU;
import com.example.gearshop.service.SanPhamCPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sanphamcpu")
public class SanPhamCPUController {

    @Autowired
    private SanPhamCPUService cpuService;

    @GetMapping("/{id}")
    public String getCPUById(@PathVariable Integer id, Model model) {
        // Lấy chi tiết sản phẩm CPU theo ID
        SanPhamCPU cpu = cpuService.findById(id);

        // Kiểm tra nếu sản phẩm không tồn tại hoặc không phải CPU
        if (cpu == null || cpu.getSanPham().getLoaiSanPham().getId() != 2) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu không tìm thấy sản phẩm
        }

        // Tách chuỗi mô tả thành danh sách
        List<String> motaList = cpu.getMota() != null
                ? Arrays.asList(cpu.getMota().split("\\|"))
                : null;

        // Thêm dữ liệu vào model
        model.addAttribute("sanPham", cpu.getSanPham());
        model.addAttribute("sanPhamCPU", cpu);
        model.addAttribute("motaList", motaList);
        model.addAttribute("loaiSanPham", "CPU");

        // Trả về giao diện chung
        return "clientTemplate/chitietsanpham";
    }
}
