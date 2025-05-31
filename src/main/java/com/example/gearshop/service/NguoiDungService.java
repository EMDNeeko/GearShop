package com.example.gearshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.repository.NguoiDungRepository;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public void capNhatThongTin(String tenDangNhap, String sdtMoi, String diaChiMoi) {
        NguoiDung nd = nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        if (nd != null) {
            nd.setSdt(sdtMoi);
            nd.setDiaChi(diaChiMoi);
            nguoiDungRepository.save(nd);
        }
    }

    public String doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi, String xacNhan) {
        NguoiDung nd = nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        if (nd == null) {
            return "Không tìm thấy người dùng.";
        }

        if (!matKhauCu.equals(nd.getMatKhau())) {
            return "Mật khẩu cũ không đúng.";
        }

        if (!matKhauMoi.equals(xacNhan)) {
            return "Mật khẩu mới và xác nhận không khớp.";
        }

        nd.setMatKhau(matKhauMoi);
        nguoiDungRepository.save(nd);
        return "Đổi mật khẩu thành công.";
    }

}
