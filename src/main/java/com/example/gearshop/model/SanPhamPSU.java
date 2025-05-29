package com.example.gearshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanphampsu")
@Getter
@Setter
@NoArgsConstructor

public class SanPhamPSU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String maPSU;

    @ManyToOne
    @JoinColumn(name = "sanPhamID", nullable = false)
    private SanPham sanPham;

    private Integer dienApVao;
    private Integer congSuat;

    @Column(length = 500)
    private String mota;
}
