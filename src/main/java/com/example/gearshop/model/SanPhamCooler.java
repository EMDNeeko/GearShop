package com.example.gearshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sanphamcooler")
public class SanPhamCooler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String maCooler;

    @ManyToOne
    @JoinColumn(name = "sanPhamID", nullable = false)
    private SanPham sanPham;

    private String loaiTan;
    private Boolean coLED;

    @Column(length = 500)
    private String mota;

    // getters và setters
    public Integer getId() {
        return ID;
    }

    public String getMaCooler() {
        return maCooler;
    }

    public void setMaCooler(String maCooler) {
        this.maCooler = maCooler;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public String getLoaiTan() {
        return loaiTan;
    }

    public void setLoaiTan(String loaiTan) {
        this.loaiTan = loaiTan;
    }

    public Boolean getCoLED() {
        return coLED;
    }

    public void setCoLED(Boolean coLED) {
        this.coLED = coLED;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
