package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	List<JabatanModel> getJabatan();
	Optional<JabatanModel> getJabatanById(long id);
}
