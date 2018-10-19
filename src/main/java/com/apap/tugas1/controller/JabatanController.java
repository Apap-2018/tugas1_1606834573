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
	
}
