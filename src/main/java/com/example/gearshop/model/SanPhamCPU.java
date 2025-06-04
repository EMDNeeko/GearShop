package com.example.gearshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sanphamcpu")
public class SanPhamCPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String maCPU;

    @ManyToOne
    @JoinColumn(name = "sanPhamID", nullable = false)
    private SanPham sanPham;

    private String loaiCPU;
    private String soNhansoLuong;

    @Column(length = 500)
    private String mota;

    public Integer getId() {
        return ID;
    }

    public String getMaCPU() {
        return maCPU;
    }

    public void setMaCPU(String maCPU) {
        this.maCPU = maCPU;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getLoaiCPU() {
        return loaiCPU;
    }

    public void setLoaiCPU(String loaiCPU) {
        this.loaiCPU = loaiCPU;
    }

    public String getSoNhansoLuong() {
        return soNhansoLuong;
    }

    public void setSoNhansoLuong(String soNhansoLuong) {
        this.soNhansoLuong = soNhansoLuong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    // getters và setters
}
