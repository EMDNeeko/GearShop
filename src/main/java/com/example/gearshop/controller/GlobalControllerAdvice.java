package com.example.gearshop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.gearshop.model.NguoiDung;
import com.example.gearshop.repository.NguoiDungRepository;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @ModelAttribute("nguoiDung")
    public NguoiDung thongTinNguoiDungDangNhap(HttpSession session) {
        return (NguoiDung) session.getAttribute("nguoiDung");
    }
}
