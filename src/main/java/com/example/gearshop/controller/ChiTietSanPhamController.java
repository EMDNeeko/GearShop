package com.example.gearshop.controller;

import com.example.gearshop.model.*;
import com.example.gearshop.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/chitietsanpham")
public class ChiTietSanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/{id}")
    public String chiTietSanPham(@PathVariable("id") Integer id, Model model) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu dựa trên ID
        SanPham sanPham = sanPhamService.getSanPhamById(id);

        // Kiểm tra nếu sản phẩm không tồn tại
        if (sanPham == null) {
            return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu không tìm thấy sản phẩm
        }

        // Thêm dữ liệu sản phẩm chung vào model
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("loaiSanPham", sanPham.getLoaiSanPham().getTenLoaiSanPham());
        System.out.println("Loai San Pham: " + sanPham.getLoaiSanPham().getTenLoaiSanPham());

        // Lấy thông tin chi tiết theo loại sản phẩm
        Object chiTietSanPham = sanPhamService.layChiTietTheoLoai(sanPham);
        model.addAttribute("chiTietSanPham", chiTietSanPham);

        // Ép kiểu đối tượng chi tiết sản phẩm theo loại
        if (chiTietSanPham instanceof SanPhamMainBoard) {
            SanPhamMainBoard mainBoard = (SanPhamMainBoard) chiTietSanPham;
            List<String> motaList = mainBoard.getMota() != null
                    ? Arrays.asList(mainBoard.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamCPU) {
            SanPhamCPU cpu = (SanPhamCPU) chiTietSanPham;
            List<String> motaList = cpu.getMota() != null
                    ? Arrays.asList(cpu.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamRAM) {
            SanPhamRAM ram = (SanPhamRAM) chiTietSanPham;
            List<String> motaList = ram.getMota() != null
                    ? Arrays.asList(ram.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamVGA) {
            SanPhamVGA vga = (SanPhamVGA) chiTietSanPham;
            List<String> motaList = vga.getMota() != null
                    ? Arrays.asList(vga.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamOCung) {
            SanPhamOCung oCung = (SanPhamOCung) chiTietSanPham;
            List<String> motaList = oCung.getMota() != null
                    ? Arrays.asList(oCung.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamPSU) {
            SanPhamPSU psu = (SanPhamPSU) chiTietSanPham;
            List<String> motaList = psu.getMota() != null
                    ? Arrays.asList(psu.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamCooler) {
            SanPhamCooler cooler = (SanPhamCooler) chiTietSanPham;
            List<String> motaList = cooler.getMota() != null
                    ? Arrays.asList(cooler.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamCase) {
            SanPhamCase spCase = (SanPhamCase) chiTietSanPham;
            List<String> motaList = spCase.getMota() != null
                    ? Arrays.asList(spCase.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        } else if (chiTietSanPham instanceof SanPhamManHinh) {
            SanPhamManHinh manHinh = (SanPhamManHinh) chiTietSanPham;
            List<String> motaList = manHinh.getMota() != null
                    ? Arrays.asList(manHinh.getMota().split("\\|"))
                    : null;
            model.addAttribute("motaList", motaList);
        }

        // Trả về template chi tiết sản phẩm
        return "clientTemplate/chitietsanpham";
    }
}
