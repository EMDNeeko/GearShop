package com.example.gearshop.repository;

import com.example.gearshop.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    Optional<HoaDonChiTiet> findTopByOrderByIdDesc();

    List<HoaDonChiTiet> findByHoaDonID(Integer hoaDonID);

    Optional findById(Integer id);
}
