package com.example.gearshop.repository;

import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.LoaiSanPham;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    List<SanPham> findTop10ByOrderByDaBanDesc(); // top sản phẩm bán chạy

    List<SanPham> findTop10ByLoaiSanPhamOrderByDaBanDesc(LoaiSanPham loaiSanPham); // theo loại

    List<SanPham> findByTenSanPhamContainingIgnoreCase(String keyword);

    List<SanPham> findByTenSanPhamContainingIgnoreCaseOrderByGiaAsc(String keyword);

    List<SanPham> findByTenSanPhamContainingIgnoreCaseOrderByGiaDesc(String keyword);

    List<SanPham> findByLoaiSanPham_TenLoaiSanPham(String tenLoaiSanPham);

    Optional<SanPham> findById(Integer id);

    @Query("SELECT MAX(sp.maSanPham) FROM SanPham sp")
    String findMaxMaSanPham();

    Boolean existsByThuongHieu_Id(Integer id);
}