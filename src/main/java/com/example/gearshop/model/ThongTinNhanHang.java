package com.example.gearshop.model;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "thongTinNhanHang")
@Entity
public class ThongTinNhanHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "khachHangID")
    private KhachHang khachHang;

    private String tenNguoiNhan;

    private String sdt;

    private String diachi;

    // Getters và Setters (nếu không dùng Lombok)
}