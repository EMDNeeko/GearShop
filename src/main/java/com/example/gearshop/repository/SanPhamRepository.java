package com.example.gearshop.repository;

import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.LoaiSanPham;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    List<SanPham> findTop10ByOrderByDaBanDesc(); // top sản phẩm bán chạy

    List<SanPham> findTop10ByLoaiSanPhamOrderByDaBanDesc(LoaiSanPham loaiSanPham); // theo loại
}