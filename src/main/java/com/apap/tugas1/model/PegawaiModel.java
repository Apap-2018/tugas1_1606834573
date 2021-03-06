package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * PegawaiModel
 */
@Entity
@Table(name = "pegawai")

public class PegawaiModel implements Serializable, Comparable<PegawaiModel> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    @NotNull
    @Size(max = 255)
    @Column(name = "nip", nullable = false, unique=true)
    private String nip;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "tempat_lahir", nullable = false)
    private String tempatLahir;
    
    @NotNull
    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggalLahir;
    
    @NotNull
    @Size(max = 255)
    @Column(name = "tahun_masuk", nullable = false)
    private String tahunMasuk;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_instansi", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private InstansiModel instansi;
    
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<JabatanPegawaiModel> jabatanPegawai;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "jabatanPegawaiNew", joinColumns = { @JoinColumn(name="id_pegawai", referencedColumnName="id")}, inverseJoinColumns = { @JoinColumn(name="id_jabatan", referencedColumnName="id") }) 
	 private List<JabatanModel> listJabatan;
	
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempat_lahir) {
		this.tempatLahir = tempat_lahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggal_lahir) {
		this.tanggalLahir = tanggal_lahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahun_masuk) {
		this.tahunMasuk = tahun_masuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	public List<JabatanPegawaiModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<JabatanPegawaiModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}
	
	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}

	public int getAge() {
		int birthdayYear = tanggalLahir.toLocalDate().getYear();
		int nowYear = LocalDate.now().getYear();
		return nowYear -birthdayYear;
	}
	
	public double getGaji() {
		double tunjangan = (instansi.getProvinsi().getPresentaseTunjangan())/100;
		double gajiTerbesar = 0;
		
		for(JabatanPegawaiModel jabatan : jabatanPegawai) {
			double gajiPokok = jabatan.getJabatan().getGajiPokok();
			if(gajiPokok > gajiTerbesar) gajiTerbesar = gajiPokok;
		}

		double gaji = gajiTerbesar + (tunjangan * gajiTerbesar);
		return gaji;
	}
	
	@Override
	public int compareTo(PegawaiModel o) {
		return this.tanggalLahir.compareTo(o.getTanggalLahir());
	}
}
