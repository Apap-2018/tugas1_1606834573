package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	Optional<PegawaiModel> findById(long id);
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggal_lahir, String tahunMasuk);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
}