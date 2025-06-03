package com.example.gearshop.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.model.NhanVien;
import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.SanPhamCPU;
import com.example.gearshop.model.SanPhamCase;
import com.example.gearshop.model.SanPhamCooler;
import com.example.gearshop.model.SanPhamMainBoard;
import com.example.gearshop.model.SanPhamManHinh;
import com.example.gearshop.model.SanPhamOCung;
import com.example.gearshop.model.SanPhamPSU;
import com.example.gearshop.model.SanPhamRAM;
import com.example.gearshop.model.SanPhamVGA;
import com.example.gearshop.repository.SanPhamCPURepository;
import com.example.gearshop.repository.SanPhamCaseRepository;
import com.example.gearshop.repository.SanPhamCoolerRepository;
import com.example.gearshop.repository.SanPhamMainBoardRepository;
import com.example.gearshop.repository.SanPhamManHinhRepository;
import com.example.gearshop.repository.SanPhamOCungRepository;
import com.example.gearshop.repository.SanPhamPSURepository;
import com.example.gearshop.repository.SanPhamRAMRepository;
import com.example.gearshop.repository.SanPhamVGARepository;
import com.example.gearshop.service.LoaiSanPhamService;
import com.example.gearshop.service.MainboardService;
import com.example.gearshop.service.NguoiDungService;
import com.example.gearshop.service.NhanVienService;
import com.example.gearshop.service.SanPhamService;
import com.example.gearshop.service.ThuongHieuService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminQuanLySanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private SanPhamMainBoardRepository sanPhamMainBoardRepository;
    @Autowired
    private SanPhamCPURepository sanPhamCPURepository;
    @Autowired
    private SanPhamRAMRepository sanPhamRAMRepository;
    @Autowired
    private SanPhamVGARepository sanPhamVGARepository;
    @Autowired
    private SanPhamOCungRepository sanPhamOCungRepository;
    @Autowired
    private SanPhamPSURepository sanPhamPSURepository;
    @Autowired
    private SanPhamCoolerRepository sanPhamCoolerRepository;
    @Autowired
    private SanPhamManHinhRepository sanPhamManHinhRepository;
    @Autowired
    private SanPhamCaseRepository sanPhamCaseRepository;

    @GetMapping("/quanlysanpham")
    public String quanLySanPham(@RequestParam(value = "loai", required = false) String loai,
            @RequestParam(value = "sort", required = false) String sort,
            Model model) {
        List<SanPham> danhSachSanPham;
        if (loai == null || loai.isEmpty()) {
            danhSachSanPham = sanPhamService.getAllSanPham();
        } else {
            danhSachSanPham = sanPhamService.getByLoai(loai);
        }

        // Sắp xếp danh sách
        if (sort != null) {
            switch (sort) {
                case "maSanPham_asc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getMaSanPham));
                case "maSanPham_desc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getMaSanPham).reversed());

                case "tenSanPham_asc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getTenSanPham));
                case "tenSanPham_desc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getTenSanPham).reversed());

                case "thuongHieu_asc" ->
                    danhSachSanPham.sort(Comparator.comparing((SanPham sp) -> sp.getThuongHieu().getTenThuongHieu()));
                case "thuongHieu_desc" -> danhSachSanPham
                        .sort(Comparator.comparing((SanPham sp) -> sp.getThuongHieu().getTenThuongHieu()).reversed());

                case "ngayThem_asc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getNgayThem));
                case "ngayThem_desc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getNgayThem).reversed());

                case "gia_asc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getGia));
                case "gia_desc" -> danhSachSanPham.sort(Comparator.comparing(SanPham::getGia).reversed());
            }
        }
        List<String> loaiSanPhams = Arrays.asList("MainBoard", "CPU", "RAM", "VGA", "Ổ cứng", "PSU", "Tản", "Case",
                "Màn hình");

        model.addAttribute("dsSanPham", danhSachSanPham);
        model.addAttribute("sort", sort);
        model.addAttribute("dsLoaiSanPham", loaiSanPhams); // Gửi danh sách loại sản phẩm
        model.addAttribute("loaiDangChon", loai);
        return "adminTemplate/quanlysanpham";
    }

    @GetMapping("/quanlysanpham/chitiet/{id}")
    public String xemChiTietSanPham(@PathVariable Integer id, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        if (sanPham == null)
            return "redirect:/admin/quanlysanpham";

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("thuongHieuList", sanPhamService.getAllThuongHieu());
        model.addAttribute("loaiSanPhamList", sanPhamService.getAllLoaiSanPham());
        model.addAttribute("chiTietSanPham", sanPhamService.layChiTietTheoLoai(sanPham));

        return "adminTemplate/chitietsanpham";
    }

    @PostMapping("/quanlysanpham/capnhat/mainboard")
    public String capNhatSanPhamMainBoard(@ModelAttribute SanPhamMainBoard mainboard, HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";

        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);

        // Lấy SanPham từ mainboard, rồi cập nhật các thông tin chung
        SanPham sanPham = mainboard.getSanPham();
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);

        // Gọi service để cập nhật cả SanPham và chi tiết
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, mainboard);

        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/cpu")
    public String capNhatSanPhamCPU(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamCPU cpu,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, cpu);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/ram")
    public String capNhatSanPhamRAM(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamRAM ram,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, ram);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/vga")
    public String capNhatSanPhamVGA(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamVGA vga,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, vga);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/ocung")
    public String capNhatSanPhamOCung(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamOCung ocung,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, ocung);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/psu")
    public String capNhatSanPhamPSU(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamPSU psu,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, psu);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/cooler")
    public String capNhatSanPhamCooler(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamCooler cooler,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, cooler);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/case")
    public String capNhatSanPhamCase(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamCase caseSp,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, caseSp);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }

    @PostMapping("/quanlysanpham/capnhat/manhinh")
    public String capNhatSanPhamManHinh(@ModelAttribute SanPham sanPham, @ModelAttribute SanPhamManHinh manHinh,
            HttpSession session) {
        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        if (nguoiDung == null)
            return "redirect:/dangnhap";
        NhanVien nhanVien = nhanVienService.getNhanVienByNguoiDung(nguoiDung);
        sanPham.setNgayThem(LocalDateTime.now());
        sanPham.setNguoiThem(nhanVien);
        sanPhamService.capNhatSanPhamVaChiTiet(sanPham, manHinh);
        return "redirect:/admin/quanlysanpham/" + sanPham.getId();
    }
}
