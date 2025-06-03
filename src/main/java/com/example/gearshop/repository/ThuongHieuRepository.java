package com.example.gearshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.ThuongHieu;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {
    // Define any custom query methods if needed
    // For example, you can add methods to find brands by name or other criteria
    // List<ThuongHieu> findByTenThuongHieuContainingIgnoreCase(String
    // tenThuongHieu);
    List<ThuongHieu> findAll();

}
