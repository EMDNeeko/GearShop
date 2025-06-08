package com.example.gearshop.service;

import com.example.gearshop.repository.VoucherRepository;
import com.example.gearshop.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher getVoucherByMaVoucher(String maVoucher) {
        return voucherRepository.findByMaVoucher(maVoucher)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy voucher với mã: " + maVoucher));
    }
}
