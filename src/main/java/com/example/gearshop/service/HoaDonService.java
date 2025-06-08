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
        String maHoaDon = generateMaHoaDon(maHoaDonPrefix); // Tạo mã hóa đơn tự động

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHoaDon(maHoaDon);
        hoaDon.setThongTinNhanHangID(thongTinNhanHangID);
        hoaDon.setNgayTao(java.time.LocalDateTime.now());
        hoaDon.setTongGia(BigDecimal.valueOf(tongGia));
        hoaDon.setTrangThaiDonHang("Chưa thanh toán");

        return hoaDonRepository.save(hoaDon); // Lưu vào cơ sở dữ liệu
    }

    public HoaDonChiTiet createHoaDonChiTiet(String maHoaDonChiTietPrefix, int hoaDonID, int sanPhamID, int soLuongSP, double thanhTien) {
        System.out.println("Dang luu chi tiet hoa don...");
        System.out.println("Ma hoa don chi tiet: " + maHoaDonChiTietPrefix);
        System.out.println("Hoa don ID: " + hoaDonID);
        System.out.println("San pham ID: " + sanPhamID);
        System.out.println("So luong: " + soLuongSP);
        System.out.println("Thanh tien: " + thanhTien);

        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        hoaDonChiTiet.setMaHoaDonChiTiet(maHoaDonChiTietPrefix);
        hoaDonChiTiet.setHoaDonID(hoaDonID);
        hoaDonChiTiet.setSanPhamID(sanPhamID);
        hoaDonChiTiet.setSoLuongSP(soLuongSP);
        hoaDonChiTiet.setThanhTien(BigDecimal.valueOf(thanhTien));

        return hoaDonChiTietRepository.save(hoaDonChiTiet); // Lưu vào cơ sở dữ liệu
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

    public HoaDon findById(int id) {
        return hoaDonRepository.findById(id).orElse(null);
    }
}