package com.example.gearshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.HoaDonChiTiet;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    // Define custom query methods if needed
    // For example, find by order ID or product ID
    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);

}
