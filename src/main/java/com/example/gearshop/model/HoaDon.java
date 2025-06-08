package com.example.gearshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String maHoaDon;

    // Lưu ID của thông tin nhận hàng thay vì ánh xạ đối tượng
    private int thongTinNhanHangID;

    private LocalDateTime ngayTao;

    private BigDecimal tongGia;

    private String trangThaiDonHang;

    // Getters và Setters
}
