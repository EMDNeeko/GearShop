package com.example.gearshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.NhanVien;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Optional<NhanVien> findByNguoiDung_Id(int nguoiDungId);
}
