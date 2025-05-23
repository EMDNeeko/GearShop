package com.example.gearshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gearshop.model.KhachHang;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {

    Optional<KhachHang> findByNguoiDung_Id(int nguoiDungId);
}
