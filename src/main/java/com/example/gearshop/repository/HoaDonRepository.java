package com.example.gearshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gearshop.model.HoaDon;

import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    Optional<HoaDon> findTopByOrderByIdDesc();

    List<HoaDon> findByThongTinNhanHang_KhachHangID(Integer khachHangID);
}
