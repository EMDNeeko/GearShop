package com.example.gearshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "khachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String maKhachHang;
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "nguoiDungID", referencedColumnName = "id")
    private NguoiDung nguoiDung;
}
