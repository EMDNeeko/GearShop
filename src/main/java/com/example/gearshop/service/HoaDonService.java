package com.example.gearshop.service;

import com.example.gearshop.model.HoaDon;
import com.example.gearshop.model.KhachHang;
import com.example.gearshop.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    public HoaDon createHoaDon(String maHoaDon, KhachHang khachHang, double tongGia) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(maHoaDon);
        hoaDon.setKhachHang(khachHang); // Sử dụng đối tượng KhachHang thay vì ID
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTongGia(BigDecimal.valueOf(tongGia)); // Chuyển đổi từ double sang BigDecimal
        hoaDon.setTrangThaiDonHang("Chờ thanh toán");
        return hoaDonRepository.save(hoaDon);
    }
}