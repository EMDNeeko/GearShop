package com.example.gearshop.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String maHoaDonChiTiet;
    private int soLuongSP;
    private BigDecimal thanhTien;

    @ManyToOne
    @JoinColumn(name = "hoaDonID")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "sanPhamID")
    private SanPham sanPham;
}
