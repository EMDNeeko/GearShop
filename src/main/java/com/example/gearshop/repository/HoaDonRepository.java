package com.example.gearshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.HoaDon;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    // Define custom query methods if needed
    // For example, find by customer ID or status
    List<HoaDon> findByKhachHangId(Long khachHangId);
}
