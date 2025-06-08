package com.example.gearshop.repository;

<<<<<<< Updated upstream
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.ThongTinNhanHang;

public interface ThongTinNhanHangRepository extends JpaRepository<ThongTinNhanHang, Integer> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
    Optional<ThongTinNhanHang> findByKhachHangId(Integer khachHangId);
=======
import com.example.gearshop.model.ThongTinNhanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongTinNhanHangRepository extends JpaRepository<ThongTinNhanHang, Integer> {
    List<ThongTinNhanHang> findByKhachHangID(int khachHangID);
>>>>>>> Stashed changes
}
