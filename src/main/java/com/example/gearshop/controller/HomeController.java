package com.example.gearshop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.gearshop.repository.SanPhamRepository;

import jakarta.servlet.http.HttpSession;

import com.example.gearshop.repository.KhachHangRepository;
import com.example.gearshop.repository.LoaiSanPhamRepository;
import com.example.gearshop.repository.NguoiDungRepository;
import com.example.gearshop.repository.NhanVienRepository;
import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.LoaiSanPham;
import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.model.ThuongHieu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private SanPhamRepository sanPhamRepo;

    @Autowired
    private LoaiSanPhamRepository loaiSPRepo;

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private NhanVienRepository nhanVienRepo;

    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung != null) {
            model.addAttribute("nguoiDung", nguoiDung);
        }
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

    @GetMapping("/dangnhap")
    public String showLoginPage() {
        return "clientTemplate/dangnhap"; // login.html trong templates
    }

    @PostMapping("/dangnhap")
    public String login(@RequestParam String tenDangNhap,
            @RequestParam String matKhau,
            HttpSession session,
            Model model) {
        Optional<NguoiDung> optionalNguoiDung = nguoiDungRepo.findByTenDangNhapAndMatKhau(tenDangNhap, matKhau);

        if (optionalNguoiDung.isPresent()) {
            NguoiDung nguoiDung = optionalNguoiDung.get();
            session.setAttribute("nguoiDung", nguoiDung);

            boolean isKhachHang = khachHangRepo.findByNguoiDung_Id(nguoiDung.getId()).isPresent();
            boolean isNhanVien = nhanVienRepo.findByNguoiDung_Id(nguoiDung.getId()).isPresent();

            if (isKhachHang) {

                return "redirect:/";
            } else if (isNhanVien) {

                return "redirect:/trangchuadmin";
            } else {
                model.addAttribute("error", "Tài khoản không thuộc vai trò hợp lệ.");
                return "clientTemplate/dangnhap";
            }
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            return "clientTemplate/dangnhap";
        }
    }

    // Tim kiem san pham
    @GetMapping("/timkiem")
    public String timKiemSanPham(@RequestParam("q") String keyword,
            @RequestParam(value = "sort", required = false) String sort,
            Model model) {

        List<SanPham> ketQua;

        if ("giaTangDan".equals(sort)) {
            ketQua = sanPhamRepo.findByTenSanPhamContainingIgnoreCaseOrderByGiaAsc(keyword);
        } else if ("giaGiamDan".equals(sort)) {
            ketQua = sanPhamRepo.findByTenSanPhamContainingIgnoreCaseOrderByGiaDesc(keyword);
        } else {
            ketQua = sanPhamRepo.findByTenSanPhamContainingIgnoreCase(keyword);
        }

        model.addAttribute("ketQua", ketQua);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        return "clientTemplate/timkiem";
    }
}
