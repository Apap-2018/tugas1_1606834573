package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional

public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public List<JabatanModel> getJabatan() {
		return jabatanDb.findAll();
	}
	
	@Override
	public Optional<JabatanModel> getJabatanById(long id) {
		return jabatanDb.findById(id);
	}
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public void deleteJabatanById(long id) {
		jabatanDb.deleteById(id);
	}

	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel updateJabatan = jabatanDb.findById(jabatan.getId()).get();
		updateJabatan.setNama(jabatan.getNama());
		updateJabatan.setDeskripsi(jabatan.getDeskripsi());
		updateJabatan.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(updateJabatan);
	}
	
}
