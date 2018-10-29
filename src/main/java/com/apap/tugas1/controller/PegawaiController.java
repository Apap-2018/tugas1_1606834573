package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		model.addAttribute("home", true);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping("/pegawai")
	private String viewPegawai(@RequestParam(value = "nip", required = true) String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		
		double gaji = 0;
		double persentunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		List<String> jabatan = new ArrayList<>();
		
		for(JabatanPegawaiModel jabatanPegawai : pegawai.getJabatanPegawai()) {
			double temp = 0;
			if(jabatanPegawai.getPegawai().getId() == pegawai.getId()) {
				temp = jabatanPegawai.getJabatan().getGajiPokok();
				jabatan.add(jabatanPegawai.getJabatan().getNama());
				if(gaji < temp) {
					gaji = temp;
				}
			}
		}
		
		gaji = ((gaji * persentunjangan /100) + gaji);
		model.addAttribute("gaji", "Rp"+ String.format("%.0f", gaji));
		model.addAttribute("home", true);
		model.addAttribute("jabatan", jabatan);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String tambahPegawai(Model model, @ModelAttribute PegawaiModel pegawai) {
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("tambahPegawai", true);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/view-by-age", method = RequestMethod.GET)
	private String viewPegawaiByAge(@RequestParam(value="idInstansi") long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(idInstansi);
		List<PegawaiModel> listPegawai = instansi.getListPegawai();
		
		Collections.sort(listPegawai);
		Collections.reverse(listPegawai);
		
		PegawaiModel termuda = listPegawai.get(0);
		PegawaiModel tertua = listPegawai.get(listPegawai.size()-1);
		
		double gajiTermuda = 0;
		double gajiTertua = 0;
		
		List<String> jabatanTermuda = new ArrayList<>();
		List<String> jabatanTertua = new ArrayList<>();
		
		for (JabatanPegawaiModel jabatanPegawai : termuda.getJabatanPegawai()) {
			double temp = 0;	
			temp = jabatanPegawai.getJabatan().getGajiPokok();
			jabatanTermuda.add(jabatanPegawai.getJabatan().getNama());
			if(gajiTermuda < temp) {
				gajiTermuda = temp;
			}
		}
		
		for (JabatanPegawaiModel jabatanPegawai : tertua.getJabatanPegawai()) {
			double temp = 0;	
			temp = jabatanPegawai.getJabatan().getGajiPokok();
			jabatanTertua.add(jabatanPegawai.getJabatan().getNama());
			if(gajiTertua < temp) {
				gajiTertua = temp;
			}
		
		}
		
		double persenTunjangan = termuda.getInstansi().getProvinsi().getPresentaseTunjangan();
		
		model.addAttribute("termuda", termuda);
		model.addAttribute("tertua", tertua);
		model.addAttribute("gajiTermuda", "Rp"+ String.format("%.0f", gajiTermuda * persenTunjangan/100 + gajiTermuda));
		model.addAttribute("gajiTertua", "Rp"+ String.format("%.0f", gajiTertua * persenTunjangan/100 + gajiTertua));
		model.addAttribute("home", true);
		model.addAttribute("jabatanTermuda", jabatanTermuda);
		model.addAttribute("jabatanTertua", jabatanTertua);
		
		return "view-pegawai-by-age";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Model model) {
		List<JabatanModel> jabatan = jabatanService.getJabatan();
		List<InstansiModel> instansi = instansiService.getInstansi();
		List<ProvinsiModel> provinsi = provinsiService.getProvinsi();
		model.addAttribute("listJabatan", jabatan);
		model.addAttribute("listInstansi", instansi);
		model.addAttribute("listProvinsi", provinsi);
		model.addAttribute("pageTitle", "Cari Pegawai");
		
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.POST)
	private String findPegawaiCari(@ModelAttribute PegawaiModel pegawai1, @RequestParam("instansi")long idInstansi, @RequestParam("provinsi")long idProvinsi, @RequestParam("jabatan")long idJabatan, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(idInstansi);
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		List<PegawaiModel> listPegawai = pegawaiService.getFilter(idInstansi, idJabatan);
		
		model.addAttribute("nama", instansi.getNama());
		model.addAttribute("namaJabatan", jabatan.getNama());
	    model.addAttribute("listPegawai", listPegawai);
	    
	    List<ProvinsiModel> listProvinsi = provinsiService.getProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		List<InstansiModel> listInstansi = instansiService.getInstansi();
		
	    model.addAttribute("listJabatan", listJabatan);
	    model.addAttribute("listInstansi", listInstansi);
	    model.addAttribute("listProvinsi", listProvinsi);
	    
		return "cari-pegawai";
	}
	
}
