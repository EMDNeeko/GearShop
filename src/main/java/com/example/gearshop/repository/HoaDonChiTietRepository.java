package com.example.gearshop.repository;

<<<<<<< Updated upstream
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.HoaDonChiTiet;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    // Define custom query methods if needed
    // For example, find by order ID or product ID
    List<HoaDonChiTiet> findByHoaDonId(Integer hoaDonId);

=======
import com.example.gearshop.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    Optional<HoaDonChiTiet> findTopByOrderByIdDesc();
>>>>>>> Stashed changes
}
