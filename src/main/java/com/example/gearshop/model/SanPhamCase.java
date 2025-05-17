package com.example.gearshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sanphamcase")
public class SanPhamCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String maCase;

    @ManyToOne
    @JoinColumn(name = "sanPhamID", nullable = false)
    private SanPham sanPham;

    private String hoTroMain;
    private String mauCase;

    @Column(length = 500)
    private String mota;

    // getters và setters
    public String getMaCase() {
        return maCase;
    }

    public void setMaCase(String maCase) {
        this.maCase = maCase;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getHoTroMain() {
        return hoTroMain;
    }

    public void setHoTroMain(String hoTroMain) {
        this.hoTroMain = hoTroMain;
    }

    public String getMauCase() {
        return mauCase;
    }

    public void setMauCase(String mauCase) {
        this.mauCase = mauCase;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
