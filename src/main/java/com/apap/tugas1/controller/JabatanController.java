package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("idJabatan") long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/view-all", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getJabatan();
		model.addAttribute("listJabatan", listJabatan);
		return "view-all-jabatan";
	}
	
	@RequestMapping(value="/jabatan/add-jabatan", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/add-jabatan", method = RequestMethod.POST)
	private String submitNewJabatan (@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("message","Data berhasil ditambahkan!");
		return "add-result";
	}
	
	@RequestMapping(value="/jabatan/update-jabatan",  method = RequestMethod.GET )
	private String updateJabatan(@RequestParam(value = "idJabatan", required = true) long idJabatan, Model model) {
		model.addAttribute("jabatan", jabatanService.getJabatanById(idJabatan).get());
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/update-jabatan",  method = RequestMethod.POST )
	private String submitUpdateJabatan(@ModelAttribute JabatanModel jabatan, Model model){
		jabatanService.addJabatan(jabatan);
		model.addAttribute("message", "Data berhasil diubah!");
		return "add-result";
	}
	
	@RequestMapping(value = "/jabatan/delete-jabatan", method = RequestMethod.POST)
	private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.deleteJabatanById(jabatan.getId());
		model.addAttribute("message", "Data berhasil dihapus!");
		return "add-result";
	}
}
