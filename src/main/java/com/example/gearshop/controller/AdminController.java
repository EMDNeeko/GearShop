package com.example.gearshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/trangchu")
    public String adminHome() {
        return "adminTemplate/trangchuadmin";
    }

    @GetMapping("/thongke")
    public String thongKe() {
        return "adminTemplate/thongke"; // nếu bạn cũng đặt các file khác ở adminTemplate
    }

    @GetMapping("/nguoidung")
    public String quanLyNguoiDung() {
        return "adminTemplate/nguoidung";
    }

    @GetMapping("/hoantien")
    public String hoanTien() {
        return "adminTemplate/hoantien";
    }

    @GetMapping("/hoadon")
    public String hoaDon() {
        return "adminTemplate/hoadon";
    }

    @GetMapping("/themsanpham")
    public String themSanPham() {
        return "adminTemplate/themsanpham";
    }

    @GetMapping("/quanlysanpham")
    public String quanLySanPham() {
        return "adminTemplate/quanlysanpham";
    }

    @GetMapping("/khuyenmai")
    public String khuyenMai() {
        return "adminTemplate/khuyenmai";
    }

    @GetMapping("/thanhtoan")
    public String thanhToan() {
        return "adminTemplate/thanhtoan";
    }

}
