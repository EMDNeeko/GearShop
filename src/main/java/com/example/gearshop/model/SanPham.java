package com.example.gearshop.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sanpham")
@Entity
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String maSanPham;
    private String tenSanPham;
    private String hinhAnh;
    private LocalDateTime ngayThem;
    private BigDecimal gia;
    private Integer tonKho;

    @ManyToOne
    @JoinColumn(name = "khachHangID")
    private NhanVien nguoiThem;

    @ManyToOne
    @JoinColumn(name = "loaiSPID")
    private LoaiSanPham loaiSP;

    @ManyToOne
    @JoinColumn(name = "thuongHieuID")
    private ThuongHieu thuongHieu;

}
