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
@Table(name = "m_mhs")
public class MMhs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_mhs", nullable = false, length = 10)
    private String idMhs;
    @Column(name = "nama_mhs", length = 45)
    private String namaMhs;
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
    @Column(name = "status_nikah")
    private Boolean statusNikah;
    @Column(name = "status_aktif")
    private Boolean statusAktif;
    
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
    
    @Column(name="sudah_lulus")
    private Boolean sudahLulus;

    @Column(name="angkatan")
    private Integer angkatan;
    @Column(name="semester")
    private Integer semester;
    
    @Column(name="ipk")
    private Double ipk;
    @Column(name="ip_semester_lalu")
    private Double ipSemesterLalu;
    
    @Column(name="biaya_spp")
    private Double biayaSpp;
    @Column(name="biaya_terminal")
    private Double biayaTerminal;

        //field-field yang mempunyai interkoneksi dengan parameter sistem
    @Column(name="krs_opened")
    private Boolean krsOpened;
    @Column(name="bebas_bau")
    private Boolean bebasBau;
        
    @ManyToOne(optional=true)
    @JoinColumn(name="id_prodi", referencedColumnName="id_prodi", insertable=true, updatable=true)
    private MProdi prodi;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="id_pegawai", referencedColumnName="id_pegawai",  insertable=true, updatable=true)
    private MPegawai waliMurid;
    
    //TABEL-TABEL TRANSAKSI
    @OneToMany(cascade= CascadeType.ALL, mappedBy="mhs")
    @Fetch(FetchMode.JOIN)
    private Set<TKrsHeader> krsHeaderSet;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="mhs")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUsers> cmsUsersSet;
    
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(Integer angkatan) {
        this.angkatan = angkatan;
    }

    public Boolean getBebasBau() {
        return bebasBau;
    }

    public void setBebasBau(Boolean bebasBau) {
        this.bebasBau = bebasBau;
    }

    public Double getBiayaSpp() {
        return biayaSpp;
    }

    public void setBiayaSpp(Double biayaSpp) {
        this.biayaSpp = biayaSpp;
    }

    public Double getBiayaTerminal() {
        return biayaTerminal;
    }

    public void setBiayaTerminal(Double biayaTerminal) {
        this.biayaTerminal = biayaTerminal;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getIdMhs() {
        return idMhs;
    }

    public void setIdMhs(String idMhs) {
        this.idMhs = idMhs;
    }

    public Double getIpSemesterLalu() {
        return ipSemesterLalu;
    }

    public void setIpSemesterLalu(Double ipSemesterLalu) {
        this.ipSemesterLalu = ipSemesterLalu;
    }

    public Double getIpk() {
        return ipk;
    }

    public void setIpk(Double ipk) {
        this.ipk = ipk;
    }

    public String getKlasifikasi() {
        return klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        this.klasifikasi = klasifikasi;
    }

    public Set<TKrsHeader> getKrsHeaderSet() {
        return krsHeaderSet;
    }

    public void setKrsHeaderSet(Set<TKrsHeader> krsHeaderSet) {
        this.krsHeaderSet = krsHeaderSet;
    }

    public Boolean getKrsOpened() {
        return krsOpened;
    }

    public void setKrsOpened(Boolean krsOpened) {
        this.krsOpened = krsOpened;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNamaMhs() {
        return namaMhs;
    }

    public void setNamaMhs(String namaMhs) {
        this.namaMhs = namaMhs;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public MProdi getProdi() {
        return prodi;
    }

    public void setProdi(MProdi prodi) {
        this.prodi = prodi;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public Boolean getStatusNikah() {
        return statusNikah;
    }

    public void setStatusNikah(Boolean statusNikah) {
        this.statusNikah = statusNikah;
    }

    public Boolean getSudahLulus() {
        return sudahLulus;
    }

    public void setSudahLulus(Boolean sudahLulus) {
        this.sudahLulus = sudahLulus;
    }

    public Date getTglKeluar() {
        return tglKeluar;
    }

    public void setTglKeluar(Date tglKeluar) {
        this.tglKeluar = tglKeluar;
    }

    public Date getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(Date tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public MPegawai getWaliMurid() {
        return waliMurid;
    }

    public void setWaliMurid(MPegawai waliMurid) {
        this.waliMurid = waliMurid;
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
        final MMhs other = (MMhs) obj;
        if ((this.idMhs == null) ? (other.idMhs != null) : !this.idMhs.equals(other.idMhs)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.idMhs != null ? this.idMhs.hashCode() : 0);
        return hash;
    }

 

    
}
