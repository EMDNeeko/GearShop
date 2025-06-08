package com.example.gearshop.service;

import com.example.gearshop.model.HoaDon;
import com.example.gearshop.model.HoaDonChiTiet;
import com.example.gearshop.repository.HoaDonRepository;
import com.example.gearshop.repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    public HoaDon createHoaDon(String maHoaDonPrefix, int thongTinNhanHangID, double tongGia) {
        // Tự động tăng mã hóa đơn
        String maHoaDon = generateMaHoaDon(maHoaDonPrefix);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(maHoaDon);
        hoaDon.setThongTinNhanHangID(thongTinNhanHangID); // Lưu ID thay vì đối tượng
        hoaDon.setNgayTao(java.time.LocalDateTime.now());
        hoaDon.setTongGia(BigDecimal.valueOf(tongGia));
        hoaDon.setTrangThaiDonHang("Chưa thanh toán");

        return hoaDonRepository.save(hoaDon);
    }

    public HoaDonChiTiet createHoaDonChiTiet(String maHoaDonChiTietPrefix, int hoaDonID, int sanPhamID, int soLuongSP, double thanhTien) {
        // Tự động tăng mã hóa đơn chi tiết
        String maHoaDonChiTiet = generateMaHoaDonChiTiet(maHoaDonChiTietPrefix);

        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        hoaDonChiTiet.setMaHoaDonChiTiet(maHoaDonChiTiet);
        hoaDonChiTiet.setHoaDonID(hoaDonID);
        hoaDonChiTiet.setSanPhamID(sanPhamID);
        hoaDonChiTiet.setSoLuongSP(soLuongSP);
        hoaDonChiTiet.setThanhTien(BigDecimal.valueOf(thanhTien));

        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    private String generateMaHoaDon(String prefix) {
        Optional<HoaDon> lastHoaDon = hoaDonRepository.findTopByOrderByIdDesc();
        if (lastHoaDon.isPresent()) {
            String lastMaHoaDon = lastHoaDon.get().getMaHoaDon();
            int lastNumber = Integer.parseInt(lastMaHoaDon.replace(prefix, ""));
            return prefix + String.format("%03d", lastNumber + 1);
        }
        return prefix + "001";
    }

    private String generateMaHoaDonChiTiet(String prefix) {
        Optional<HoaDonChiTiet> lastHoaDonChiTiet = hoaDonChiTietRepository.findTopByOrderByIdDesc();
        if (lastHoaDonChiTiet.isPresent()) {
            String lastMaHoaDonChiTiet = lastHoaDonChiTiet.get().getMaHoaDonChiTiet();
            int lastNumber = Integer.parseInt(lastMaHoaDonChiTiet.replace(prefix, ""));
            return prefix + String.format("%03d", lastNumber + 1);
        }
        return prefix + "001";
    }
}