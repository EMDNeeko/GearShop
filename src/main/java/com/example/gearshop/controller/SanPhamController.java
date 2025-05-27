package com.example.gearshop.controller;

import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.SanPhamMainBoard;
import com.example.gearshop.repository.NguoiDungRepository;
import com.example.gearshop.repository.SanPhamMainBoardRepository;
import com.example.gearshop.service.MainboardService;
import com.example.gearshop.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private SanPhamMainBoardRepository sanPhamMainBoardRepo;
    @Autowired
    private MainboardService mainboardService;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @ModelAttribute("nguoiDung")
    public NguoiDung addNguoiDungToModel(Principal principal) {
        if (principal != null) {
            return nguoiDungRepository.findByTenDangNhap(principal.getName());
        }
        return null;
    }

    @GetMapping("/sanpham")
    public String danhSachSanPham(Model model) {
        List<SanPham> list = sanPhamService.getAllSanPham();
        model.addAttribute("sanphams", list);
        return "sanpham"; // Trả về tên view Thymeleaf: sanpham.html
    }

    @GetMapping("/sanphammainboard")
    public String listMainboards(
            @RequestParam(required = false) String[] thuongHieu,
            @RequestParam(required = false) String[] modelMain,
            @RequestParam(required = false) String[] chipset,
            @RequestParam(required = false) String[] socketMain,
            @RequestParam(required = false) String[] kichThuoc,
            @RequestParam(required = false) String[] soKheRAM,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String sort,
            Model model) {
        List<SanPhamMainBoard> mainboards = mainboardService.findFiltered(
                thuongHieu, modelMain, chipset, socketMain, kichThuoc, soKheRAM, minPrice, maxPrice, sort);
        System.out.println("Tìm thấy " + mainboards.size() + " mainboard");

        model.addAttribute("mainboards", mainboards);
        model.addAttribute("thuongHieuSet",
                extractSet(mainboards, mb -> mb.getSanPham().getThuongHieu().getTenThuongHieu()));
        model.addAttribute("modelMainSet", extractSet(mainboards, SanPhamMainBoard::getModelMain));
        model.addAttribute("chipsetSet", extractSet(mainboards, SanPhamMainBoard::getChipset));
        model.addAttribute("socketMainSet", extractSet(mainboards, SanPhamMainBoard::getSocketMain));
        model.addAttribute("kichThuocSet", extractSet(mainboards, SanPhamMainBoard::getKichThuoc));
        model.addAttribute("soKheRAMSet",
                extractSet(mainboards, mb -> mb.getSoKheRAM() == null ? null : mb.getSoKheRAM().toString()));

        model.addAttribute("selectedThuongHieu", thuongHieu != null ? thuongHieu : new String[0]);
        model.addAttribute("selectedModelMain", modelMain != null ? modelMain : new String[0]);
        model.addAttribute("selectedChipset", chipset != null ? chipset : new String[0]);
        model.addAttribute("selectedSocketMain", socketMain != null ? socketMain : new String[0]);
        model.addAttribute("selectedKichThuoc", kichThuoc != null ? kichThuoc : new String[0]);
        model.addAttribute("selectedSoKheRAM", soKheRAM != null ? soKheRAM : new String[0]);
        model.addAttribute("minPrice", minPrice != null ? minPrice : 0);
        model.addAttribute("maxPrice", maxPrice != null ? maxPrice : 1000000000);
        model.addAttribute("sort", sort != null ? sort : "default");

        return "clientTemplate/sanphammainboard";
    }

    private Set<String> extractSet(List<SanPhamMainBoard> list, Function<SanPhamMainBoard, String> getter) {
        return list.stream().map(getter).filter(Objects::nonNull).collect(Collectors.toCollection(TreeSet::new));
    }
}
