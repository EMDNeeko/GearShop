package com.example.gearshop.repository;

import com.example.gearshop.model.SanPhamCPU;
import com.example.gearshop.model.SanPhamVGA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamVGARepository extends JpaRepository<SanPhamVGA, Integer> {

    @Query("SELECT v FROM SanPhamVGA v WHERE"
            + "(:thuongHieu IS NULL OR v.sanPham.thuongHieu.tenThuongHieu = :thuongHieu) "
            + "AND (:kieuBoNho IS NULL OR v.kieuBoNho = :kieuBoNho)"
            + "AND (:dungLuongBoNho IS NULL OR v.dungLuongBoNho = :dungLuongBoNho) "
            + "AND (:chipGPU IS NULL OR v.chipGPU = :chipGPU) " +
            "AND (:giaMin IS NULL OR v.sanPham.gia >= :giaMin) " +
            "AND (:giaMax IS NULL OR v.sanPham.gia <= :giaMax) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'giaTangDan' THEN v.sanPham.gia END ASC, " +
            "CASE WHEN :sort = 'giaGiamDan' THEN v.sanPham.gia END DESC")
    List<SanPhamVGA> filterVGAs(@Param("thuongHieu") String thuongHieu, @Param("kieuBoNho") String kieuBoNho,
            @Param("dungLuongBoNho") String dungLuongBoNho, @Param("chipGPU") String chipGPU,
            @Param("giaMin") Long giaMin, @Param("giaMax") Long giaMax, @Param("sort") String sort);

    @Query("SELECT DISTINCT v.kieuBoNho FROM SanPhamVGA v")
    List<String> findAllKieuBoNho();

    @Query("SELECT DISTINCT v.dungLuongBoNho FROM SanPhamVGA v")
    List<String> findAllDungLuongBoNho();

    @Query("SELECT DISTINCT v.chipGPU FROM SanPhamVGA v")
    List<String> findAllChipGPU();

    @Query("SELECT DISTINCT sp.thuongHieu.tenThuongHieu FROM SanPhamVGA v JOIN v.sanPham sp")
    List<String> findAllThuongHieu();
}
