package com.example.gearshop.repository;

import com.example.gearshop.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    Optional<HoaDonChiTiet> findTopByOrderByIdDesc();
}
