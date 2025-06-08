package com.example.gearshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< Updated upstream
import com.example.gearshop.model.HoaDon;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    // Define custom query methods if needed
    // For example, find by customer ID or status
    List<HoaDon> findByThongTinNhanHangId(Long id);
=======
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    Optional<HoaDon> findTopByOrderByIdDesc();
>>>>>>> Stashed changes
}
