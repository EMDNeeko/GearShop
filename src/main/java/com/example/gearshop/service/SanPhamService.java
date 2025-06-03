package com.example.gearshop.service;

import com.example.gearshop.model.LoaiSanPham;
import com.example.gearshop.model.SanPham;
import com.example.gearshop.model.SanPhamCPU;
import com.example.gearshop.model.SanPhamCase;
import com.example.gearshop.model.SanPhamCooler;
import com.example.gearshop.model.SanPhamMainBoard;
import com.example.gearshop.model.SanPhamManHinh;
import com.example.gearshop.model.SanPhamOCung;
import com.example.gearshop.model.SanPhamPSU;
import com.example.gearshop.model.SanPhamRAM;
import com.example.gearshop.model.SanPhamVGA;
import com.example.gearshop.model.ThuongHieu;
import com.example.gearshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamCPURepository cpuRepo;
    @Autowired
    private SanPhamRAMRepository ramRepo;
    @Autowired
    private SanPhamMainBoardRepository mainBoardRepo;
    @Autowired
    private SanPhamVGARepository vgaRepo;
    @Autowired
    private SanPhamOCungRepository oCungRepo;
    @Autowired
    private SanPhamPSURepository psuRepo;
    @Autowired
    private SanPhamCoolerRepository coolerRepo;
    @Autowired
    private SanPhamCaseRepository caseRepo;
    @Autowired
    private SanPhamManHinhRepository manHinhRepo;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;
    private SanPhamVGARepository sanPhamVGARepository;

    SanPhamService(SanPhamVGARepository sanPhamVGARepository) {
        this.sanPhamVGARepository = sanPhamVGARepository;
    }

    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public List<SanPham> getByLoai(String tenLoai) {
        return sanPhamRepository.findByLoaiSanPham_TenLoaiSanPham(tenLoai);
    }

    public SanPham getSanPhamById(Integer id) {
        return sanPhamRepository.findById(id).orElse(null);
    }

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public List<LoaiSanPham> getAllLoaiSanPham() {
        return loaiSanPhamRepository.findAll();
    }

    public Object layChiTietTheoLoai(SanPham sp) {
        String tenLoai = sp.getLoaiSanPham().getTenLoaiSanPham();
        System.out.println("Loại sản phẩm: " + tenLoai);
        switch (tenLoai) {
            case "CPU":
                return (SanPhamCPU) cpuRepo.findBySanPhamID(sp.getId());
            case "RAM":
                return (SanPhamRAM) ramRepo.findBySanPhamID(sp.getId());
            case "Mainboard":
                return (SanPhamMainBoard) mainBoardRepo.findBySanPhamID(sp.getId());
            case "VGA":
                return (SanPhamVGA) vgaRepo.findBySanPhamID(sp.getId());
            case "Ổ Cứng":
                return (SanPhamOCung) oCungRepo.findBySanPhamID(sp.getId());
            case "PSU":
                return (SanPhamPSU) psuRepo.findBySanPhamID(sp.getId());
            case "Cooler":
                return (SanPhamCooler) coolerRepo.findBySanPhamID(sp.getId());
            case "Case":
                return (SanPhamCase) caseRepo.findBySanPhamID(sp.getId());
            case "Màn hình":
                return (SanPhamManHinh) manHinhRepo.findBySanPhamID(sp.getId());
            default:
                return null;
        }
    }

    public void capNhatSanPhamVaChiTiet(SanPham sanPham, Object sanPhamMoi) {

        switch (sanPham.getLoaiSanPham().getTenLoaiSanPham().toLowerCase()) {
            case "cpu":
                if (sanPhamMoi instanceof SanPhamCPU cpuMoi) {
                    SanPham sanPhamTuForm = cpuMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamCPU cpu = cpuRepo.findBySanPham(sanPham);
                    if (cpu != null) {
                        cpu.setLoaiCPU(cpuMoi.getLoaiCPU());
                        cpu.setSoNhansoLuong(cpuMoi.getSoNhansoLuong());
                        cpu.setMota(cpuMoi.getMota());
                        cpuRepo.save(cpu);
                    } else {
                        cpuMoi.setSanPham(sanPham);
                        cpuRepo.save(cpuMoi);
                    }
                }
                break;

            case "mainboard":
                if (sanPhamMoi instanceof SanPhamMainBoard mbMoi) {
                    SanPham sanPhamTuForm = mbMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamMainBoard mb = mainBoardRepo.findBySanPham(sanPham);
                    if (mb != null) {
                        mb.setModelMain(mbMoi.getModelMain());
                        mb.setChipset(mbMoi.getChipset());
                        mb.setSocketMain(mbMoi.getSocketMain());
                        mb.setKichThuoc(mbMoi.getKichThuoc());
                        mb.setSoKheRAM(mbMoi.getSoKheRAM());
                        mb.setMota(mbMoi.getMota());
                        mainBoardRepo.save(mb);
                    } else {
                        mbMoi.setSanPham(sanPham);
                        mainBoardRepo.save(mbMoi);
                    }
                }
                break;

            case "ram":
                if (sanPhamMoi instanceof SanPhamRAM ramMoi) {
                    SanPham sanPhamTuForm = ramMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamRAM ram = ramRepo.findBySanPham(sanPham);
                    if (ram != null) {
                        ram.setChuanRAM(ramMoi.getChuanRAM());
                        ram.setDungLuong(ramMoi.getDungLuong());
                        ram.setMota(ramMoi.getMota());
                        ramRepo.save(ram);
                    } else {
                        ramMoi.setSanPham(sanPham);
                        ramRepo.save(ramMoi);
                    }
                }
                break;

            case "vga":
                if (sanPhamMoi instanceof SanPhamVGA vgaMoi) {
                    SanPham sanPhamTuForm = vgaMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamVGA vga = vgaRepo.findBySanPham(sanPham);
                    if (vga != null) {
                        vga.setKieuBoNho(vgaMoi.getKieuBoNho());
                        vga.setDungLuongBoNho(vgaMoi.getDungLuongBoNho());
                        vga.setChipGPU(vgaMoi.getChipGPU());
                        vga.setMota(vgaMoi.getMota());
                        vgaRepo.save(vga);
                    } else {
                        vgaMoi.setSanPham(sanPham);
                        vgaRepo.save(vgaMoi);
                    }
                }
                break;

            case "ocung":
                if (sanPhamMoi instanceof SanPhamOCung ocungMoi) {
                    SanPham sanPhamTuForm = ocungMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamOCung ocung = oCungRepo.findBySanPham(sanPham);
                    if (ocung != null) {
                        ocung.setLoaiOCung(ocungMoi.getLoaiOCung());
                        ocung.setDungLuong(ocungMoi.getDungLuong());
                        ocung.setMota(ocungMoi.getMota());
                        oCungRepo.save(ocung);
                    } else {
                        ocungMoi.setSanPham(sanPham);
                        oCungRepo.save(ocungMoi);
                    }
                }
                break;

            case "psu":
                if (sanPhamMoi instanceof SanPhamPSU psuMoi) {
                    SanPham sanPhamTuForm = psuMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamPSU psu = psuRepo.findBySanPham(sanPham);
                    if (psu != null) {
                        psu.setDienApVao(psuMoi.getDienApVao());
                        psu.setCongSuat(psuMoi.getCongSuat());
                        psu.setMota(psuMoi.getMota());
                        psuRepo.save(psu);
                    } else {
                        psuMoi.setSanPham(sanPham);
                        psuRepo.save(psuMoi);
                    }
                }
                break;

            case "cooler":
                if (sanPhamMoi instanceof SanPhamCooler coolerMoi) {
                    SanPham sanPhamTuForm = coolerMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamCooler cooler = coolerRepo.findBySanPham(sanPham);
                    if (cooler != null) {
                        cooler.setLoaiTan(coolerMoi.getLoaiTan());
                        cooler.setCoLED(coolerMoi.getCoLED());
                        cooler.setMota(coolerMoi.getMota());
                        coolerRepo.save(cooler);
                    } else {
                        coolerMoi.setSanPham(sanPham);
                        coolerRepo.save(coolerMoi);
                    }
                }
                break;

            case "case":
                if (sanPhamMoi instanceof SanPhamCase caseMoi) {
                    SanPham sanPhamTuForm = caseMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamCase spCase = caseRepo.findBySanPham(sanPham);
                    if (spCase != null) {
                        spCase.setHoTroMain(caseMoi.getHoTroMain());
                        spCase.setMauCase(caseMoi.getMauCase());
                        spCase.setMota(caseMoi.getMota());
                        caseRepo.save(spCase);
                    } else {
                        caseMoi.setSanPham(sanPham);
                        caseRepo.save(caseMoi);
                    }
                }
                break;

            case "manhinh":
                if (sanPhamMoi instanceof SanPhamManHinh mhMoi) {
                    SanPham sanPhamTuForm = mhMoi.getSanPham();
                    sanPham.setTenSanPham(sanPhamTuForm.getTenSanPham());
                    sanPham.setGia(sanPhamTuForm.getGia());
                    sanPham.setLoaiSanPham(sanPhamTuForm.getLoaiSanPham());
                    sanPham.setThuongHieu(sanPhamTuForm.getThuongHieu());
                    sanPham.setNguoiThem(sanPhamTuForm.getNguoiThem());
                    sanPham.setNgayThem(LocalDateTime.now());
                    sanPhamRepository.save(sanPham);
                    SanPhamManHinh mh = manHinhRepo.findBySanPham(sanPham);
                    if (mh != null) {
                        mh.setKichThuoc(mhMoi.getKichThuoc());
                        mh.setBeMat(mhMoi.getBeMat());
                        mh.setTanSoQuet(mhMoi.getTanSoQuet());
                        mh.setTamNen(mhMoi.getTamNen());
                        mh.setDoPhanGiai(mhMoi.getDoPhanGiai());
                        mh.setMota(mhMoi.getMota());
                        manHinhRepo.save(mh);
                    } else {
                        mhMoi.setSanPham(sanPham);
                        manHinhRepo.save(mhMoi);
                    }
                }
                break;

            default:
                throw new IllegalArgumentException(
                        "Loại sản phẩm không hợp lệ: " + sanPham.getLoaiSanPham().getTenLoaiSanPham().toLowerCase());
        }
    };

    public Optional<SanPham> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
