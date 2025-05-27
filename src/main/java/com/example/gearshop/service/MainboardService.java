package com.example.gearshop.service;

import java.util.List;

import com.example.gearshop.model.SanPhamMainBoard;

public interface MainboardService {
    List<SanPhamMainBoard> findFiltered(String[] thuongHieu, String[] modelMain,
            String[] chipset, String[] socketMain,
            String[] kichThuoc, String[] soKheRAM,
            Integer minPrice, Integer maxPrice, String sort);
}
