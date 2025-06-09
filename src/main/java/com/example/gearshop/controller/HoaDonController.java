package com.example.gearshop.controller;

import com.example.gearshop.model.HoaDon;
import com.example.gearshop.model.ThongTinNhanHang;
import com.example.gearshop.model.KhachHang;
import com.example.gearshop.service.HoaDonService;
import com.example.gearshop.service.ThongTinNhanHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HoaDonController {

    @Autowired
    private ThongTinNhanHangService thongTinNhanHangService;

    @Autowired
    private HoaDonService hoaDonService;

    @PostMapping("/save-order")
    public String saveOrder(HttpSession session, @RequestParam int thongTinNhanHangID, Model model) {
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        if (khachHang == null) {
            model.addAttribute("error", "Không tìm thấy thông tin khách hàng.");
            return "redirect:/order";
        }

        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("selectedItems");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống.");
            return "redirect:/order";
        }

        System.out.println("Du lieu gio hang:");
        cart.forEach(item -> System.out.println(item));

        double tongGia = 0;
        for (Map<String, Object> item : cart) {
            Object quantityObj = item.get("quantity");
            Object priceObj = item.get("price");

            if (quantityObj == null || priceObj == null) {
                model.addAttribute("error", "Dữ liệu giỏ hàng không hợp lệ.");
                return "redirect:/order";
            }

            int quantity = Integer.parseInt(quantityObj.toString());
            double price = Double.parseDouble(priceObj.toString().replace(",", "").replace("₫", "").trim());
            tongGia += quantity * price;
        }

        // Lưu hóa đơn trước
        HoaDon hoaDon = hoaDonService.createHoaDon("HD", thongTinNhanHangID, tongGia);
        System.out.println("Da tao hoa don voi ID: " + hoaDon.getId());

        // Lưu chi tiết hóa đơn
        for (Map<String, Object> item : cart) {
            Object quantityObj = item.get("quantity");
            Object priceObj = item.get("priceNumeric");
            Object sanPhamIDObj = item.get("sanPhamID");

            if (quantityObj == null || priceObj == null || sanPhamIDObj == null) {
                model.addAttribute("error", "Dữ liệu giỏ hàng không hợp lệ.");
                return "redirect:/order";
            }

            int quantity = Integer.parseInt(quantityObj.toString());
            double price = Double.parseDouble(priceObj.toString().replace(",", "").replace("₫", "").trim());
            int sanPhamID = Integer.parseInt(sanPhamIDObj.toString());

            hoaDonService.createHoaDonChiTiet("HDCT", hoaDon.getId(), sanPhamID, quantity, quantity * price);
        }
        System.out.println("Da luu chi tiet hoa don voi ID: " + hoaDon.getId());
        model.addAttribute("hoaDon", hoaDon);
        return "clientTemplate/thanhtoan";
    }
}
