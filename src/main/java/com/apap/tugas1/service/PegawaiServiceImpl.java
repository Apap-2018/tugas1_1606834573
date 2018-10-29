package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
		
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}
	
	@Override
	public List<PegawaiModel> getFilter(long idInstansi, long idJabatan) {	
		List<PegawaiModel> list = new ArrayList<PegawaiModel>();
		InstansiModel instansi = instansiDb.findById(idInstansi);
		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansi(instansi);
		
		for(PegawaiModel pegawai : listPegawai) {
			for(JabatanModel jabatanA : pegawai.getListJabatan()) {
				if(jabatanA.getId() == idJabatan) {
					list.add(pegawai);
				}
			}
		}
		
		return list;
	}

}
