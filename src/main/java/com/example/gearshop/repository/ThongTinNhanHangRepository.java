package com.example.gearshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.ThongTinNhanHang;

public interface ThongTinNhanHangRepository extends JpaRepository<ThongTinNhanHang, Integer> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
    Optional<ThongTinNhanHang> findByKhachHangId(Integer khachHangId);
}
