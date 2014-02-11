/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "m_pegawai")
public class MPegawai implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pegawai", nullable = false, length = 10)
    private String idPegawai;
    @Column(name = "nip", length = 25)
    private String nip;
    @Column(name = "nidn", length = 25)
    private String nidn;
    @Column(name = "nupn", length = 25)
    private String nupn;
    @Column(name = "nama_pegawai", length = 45)
    private String namaPegawai;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "birth_place", length = 20)
    private String birthPlace;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "address1", length = 65)
    private String address1;
    @Column(name = "city1", length = 25)
    private String city1;
    @Column(name = "state1", length = 25)
    private String state1;
    @Column(name = "country1", length = 25)
    private String country1;
    @Column(name = "address2", length = 65)
    private String address2;
    @Column(name = "city2", length = 25)
    private String city2;
    @Column(name = "state2", length = 25)
    private String state2;
    @Column(name = "country2", length = 25)
    private String country2;
    @Column(name = "phone", length = 45)
    private String phone;
    @Column(name = "mobile", length = 45)
    private String mobile;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "npwp", length = 45)
    private String npwp;
    @Column(name = "religion", length = 10)
    private String religion;
    @Column(name = "fakultas", length = 10)
    private String fakultas;
    @Column(name = "jurusan", length = 10)
    private String jurusan;
    @Column(name = "jabatan", length = 10)
    private String jabatan;
    @Column(name = "status_nikah")
    private Boolean statusNikah;
    @Column(name = "status_aktif")
    private Boolean statusAktif;
    
    @Column(name = "golongan", length = 10)
    private String golongan;
    
    @Column(name = "tgl_masuk")
    @Temporal(TemporalType.DATE)
    private Date tglMasuk;
    @Column(name = "tgl_keluar")
    @Temporal(TemporalType.DATE)
    private Date tglKeluar;
    @Column(name = "photo1_url", length = 200)
    private String photo1Url;
    @Column(name = "photo2_url", length = 200)
    private String photo2Url;
    @Column(name = "photo3_url", length = 200)
    private String photo3Url;
    @Column(name = "photo4_url", length = 200)
    private String photo4Url;
    @Column(name = "klasifikasi", length = 10)
    private String klasifikasi;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_golongan", referencedColumnName="id_golongan", insertable=true, updatable=true)
    private MPegawaiGol pegawaiGol;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_jabatan", referencedColumnName="id_jabatan", insertable=true, updatable=true)
    private MPegawaiJab pegawaiJab;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_prodi", referencedColumnName="id_prodi", insertable=true, updatable=true)
    private MProdi prodi;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="pegawai")
    @Fetch(FetchMode.JOIN)
    private Set<TKrsJadwal> krsJadwalSet;
    
    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER, mappedBy="pegawai")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUsers> cmsUsersSet;
    
    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER, mappedBy="waliMurid")
    @Fetch(FetchMode.JOIN)
    private Set<MMhs> anakWaliSet;
    
    public MPegawai() {
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getNupn() {
        return nupn;
    }

    public void setNupn(String nupn) {
        this.nupn = nupn;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public Boolean getStatusNikah() {
        return statusNikah;
    }

    public void setStatusNikah(Boolean statusNikah) {
        this.statusNikah = statusNikah;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public Date getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(Date tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public Date getTglKeluar() {
        return tglKeluar;
    }

    public void setTglKeluar(Date tglKeluar) {
        this.tglKeluar = tglKeluar;
    }

    public String getPhoto1Url() {
        return photo1Url;
    }

    public void setPhoto1Url(String photo1Url) {
        this.photo1Url = photo1Url;
    }

    public String getPhoto2Url() {
        return photo2Url;
    }

    public void setPhoto2Url(String photo2Url) {
        this.photo2Url = photo2Url;
    }

    public String getPhoto3Url() {
        return photo3Url;
    }

    public void setPhoto3Url(String photo3Url) {
        this.photo3Url = photo3Url;
    }

    public String getPhoto4Url() {
        return photo4Url;
    }

    public void setPhoto4Url(String photo4Url) {
        this.photo4Url = photo4Url;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        this.klasifikasi = klasifikasi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MPegawaiGol getPegawaiGol() {
        return pegawaiGol;
    }

    public void setPegawaiGol(MPegawaiGol pegawaiGol) {
        this.pegawaiGol = pegawaiGol;
    }

    public MPegawaiJab getPegawaiJab() {
        return pegawaiJab;
    }

    public void setPegawaiJab(MPegawaiJab pegawaiJab) {
        this.pegawaiJab = pegawaiJab;
    }

    public MProdi getProdi() {
        return prodi;
    }

    public void setProdi(MProdi prodi) {
        this.prodi = prodi;
    }

    public Set<TKrsJadwal> getKrsJadwalSet() {
        return krsJadwalSet;
    }

    public void setKrsJadwalSet(Set<TKrsJadwal> krsJadwalSet) {
        this.krsJadwalSet = krsJadwalSet;
    }

    public Set<MMhs> getAnakWaliSet() {
        return anakWaliSet;
    }

    public void setAnakWaliSet(Set<MMhs> anakWaliSet) {
        this.anakWaliSet = anakWaliSet;
    }

    public Set<CmsUsers> getCmsUsersSet() {
        return cmsUsersSet;
    }

    public void setCmsUsersSet(Set<CmsUsers> cmsUsersSet) {
        this.cmsUsersSet = cmsUsersSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MPegawai other = (MPegawai) obj;
        if ((this.idPegawai == null) ? (other.idPegawai != null) : !this.idPegawai.equals(other.idPegawai)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.idPegawai != null ? this.idPegawai.hashCode() : 0);
        return hash;
    }

  
 
   
    
}
