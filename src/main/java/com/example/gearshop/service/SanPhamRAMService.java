package com.example.gearshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gearshop.model.SanPhamRAM;
import com.example.gearshop.repository.SanPhamRAMRepository;

@Service
public class SanPhamRAMService {
    @Autowired
    private SanPhamRAMRepository repository;

    public List<SanPhamRAM> locSanPham(String thuongHieu, String chuanRAM, String dungLuong,
            Long giaMin, Long giaMax, String sort) {
        List<SanPhamRAM> sanPhamRAMs = repository.findAll();

        return sanPhamRAMs.stream()
                .filter(ram -> thuongHieu == null || thuongHieu.isEmpty()
                        || ram.getSanPham().getThuongHieu().getTenThuongHieu().equals(thuongHieu))
                .filter(ram -> chuanRAM == null || ram.getChuanRAM().equals(chuanRAM) || chuanRAM.isEmpty())
                .filter(ram -> dungLuong == null || ram.getDungLuong().equals(dungLuong) || dungLuong.isEmpty())
                .filter(ram -> giaMin == null
                        || ram.getSanPham().getGia().compareTo(java.math.BigDecimal.valueOf(giaMin)) >= 0)
                .filter(ram -> giaMax == null
                        || ram.getSanPham().getGia().compareTo(java.math.BigDecimal.valueOf(giaMax)) <= 0)
                .sorted((ram1, ram2) -> {
                    if ("giaTangDan".equals(sort)) {
                        return ram1.getSanPham().getGia().compareTo(ram2.getSanPham().getGia());
                    } else if ("giaGiamDan".equals(sort)) {
                        return ram2.getSanPham().getGia().compareTo(ram1.getSanPham().getGia());
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }

    public List<String> getTatCaChuanRAM() {
        return repository.findDistinctChuanRAM();
    }

    public List<String> getTatCaDungLuong() {
        return repository.findDistinctDungLuong();
    }

    public List<String> getTatCaThuongHieu() {
        return repository.findAllThuongHieu();
    }
}
