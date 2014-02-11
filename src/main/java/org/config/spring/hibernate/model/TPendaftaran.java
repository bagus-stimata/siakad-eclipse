/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "t_pendaftaran")
public class TPendaftaran implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_reg", nullable = false, length = 20)
    private String noReg;
    @Basic(optional = false)
    @Column(name = "nama_lengkap", nullable = false, length = 45)
    private String namaLengkap;
    @Basic(optional = false)
    @Column(name = "jns_kartu_identitas", nullable = false, length = 5)
    private String jnsKartuIdentitas;
    @Basic(optional = false)
    @Column(name = "no_kartu_identitas", nullable = false, length = 25)
    private String noKartuIdentitas;
    @Basic(optional = false)
    @Column(name = "tempat_lhr", nullable = false, length = 45)
    private String tempatLhr;
    @Basic(optional = false)
    @Column(name = "tanggal_lhr", nullable = false, length = 45)
    private String tanggalLhr;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 45)
    private String email;
    @Basic(optional = false)
    @Column(name = "gender", nullable = false)
    private boolean gender;
    @Basic(optional = false)
    @Column(name = "status_nikah", nullable = false, length = 45)
    private String statusNikah;
    @Basic(optional = false)
    @Column(name = "religion", nullable = false, length = 45)
    private String religion;
    @Basic(optional = false)
    @Column(name = "kewarganegaraan", nullable = false, length = 45)
    private String kewarganegaraan;
    @Basic(optional = false)
    @Column(name = "negara_asal", nullable = false, length = 45)
    private String negaraAsal;
    @Basic(optional = false)
    @Column(name = "address1", nullable = false, length = 45)
    private String address1;
    @Basic(optional = false)
    @Column(name = "city1", nullable = false, length = 45)
    private String city1;
    @Basic(optional = false)
    @Column(name = "state1", nullable = false, length = 45)
    private String state1;
    @Basic(optional = false)
    @Column(name = "address2", nullable = false, length = 45)
    private String address2;
    @Basic(optional = false)
    @Column(name = "city2", nullable = false, length = 45)
    private String city2;
    @Basic(optional = false)
    @Column(name = "state2", nullable = false, length = 45)
    private String state2;
    @Basic(optional = false)
    @Column(name = "RT", nullable = false, length = 45)
    private String rt;
    @Basic(optional = false)
    @Column(name = "RW", nullable = false, length = 45)
    private String rw;
    @Basic(optional = false)
    @Column(name = "kode_pos", nullable = false, length = 45)
    private String kodePos;
    @Basic(optional = false)
    @Column(name = "kelurahan", nullable = false, length = 45)
    private String kelurahan;
    @Basic(optional = false)
    @Column(name = "kecamatan", nullable = false, length = 45)
    private String kecamatan;
    @Basic(optional = false)
    @Column(name = "propinsi", nullable = false, length = 45)
    private String propinsi;
    @Basic(optional = false)
    @Column(name = "kota", nullable = false, length = 45)
    private String kota;
    @Basic(optional = false)
    @Column(name = "telepon", nullable = false, length = 45)
    private String telepon;
    @Basic(optional = false)
    @Column(name = "hp", nullable = false, length = 45)
    private String hp;

    public TPendaftaran() {
    }

    public TPendaftaran(String noReg) {
        this.noReg = noReg;
    }

    public TPendaftaran(String noReg, String namaLengkap, String jnsKartuIdentitas, String noKartuIdentitas, String tempatLhr, String tanggalLhr, String email, boolean gender, String statusNikah, String religion, String kewarganegaraan, String negaraAsal, String address1, String city1, String state1, String address2, String city2, String state2, String rt, String rw, String kodePos, String kelurahan, String kecamatan, String propinsi, String kota, String telepon, String hp) {
        this.noReg = noReg;
        this.namaLengkap = namaLengkap;
        this.jnsKartuIdentitas = jnsKartuIdentitas;
        this.noKartuIdentitas = noKartuIdentitas;
        this.tempatLhr = tempatLhr;
        this.tanggalLhr = tanggalLhr;
        this.email = email;
        this.gender = gender;
        this.statusNikah = statusNikah;
        this.religion = religion;
        this.kewarganegaraan = kewarganegaraan;
        this.negaraAsal = negaraAsal;
        this.address1 = address1;
        this.city1 = city1;
        this.state1 = state1;
        this.address2 = address2;
        this.city2 = city2;
        this.state2 = state2;
        this.rt = rt;
        this.rw = rw;
        this.kodePos = kodePos;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.propinsi = propinsi;
        this.kota = kota;
        this.telepon = telepon;
        this.hp = hp;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJnsKartuIdentitas() {
        return jnsKartuIdentitas;
    }

    public void setJnsKartuIdentitas(String jnsKartuIdentitas) {
        this.jnsKartuIdentitas = jnsKartuIdentitas;
    }

    public String getNoKartuIdentitas() {
        return noKartuIdentitas;
    }

    public void setNoKartuIdentitas(String noKartuIdentitas) {
        this.noKartuIdentitas = noKartuIdentitas;
    }

    public String getTempatLhr() {
        return tempatLhr;
    }

    public void setTempatLhr(String tempatLhr) {
        this.tempatLhr = tempatLhr;
    }

    public String getTanggalLhr() {
        return tanggalLhr;
    }

    public void setTanggalLhr(String tanggalLhr) {
        this.tanggalLhr = tanggalLhr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getStatusNikah() {
        return statusNikah;
    }

    public void setStatusNikah(String statusNikah) {
        this.statusNikah = statusNikah;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getNegaraAsal() {
        return negaraAsal;
    }

    public void setNegaraAsal(String negaraAsal) {
        this.negaraAsal = negaraAsal;
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

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(String propinsi) {
        this.propinsi = propinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noReg != null ? noReg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPendaftaran)) {
            return false;
        }
        TPendaftaran other = (TPendaftaran) object;
        if ((this.noReg == null && other.noReg != null) || (this.noReg != null && !this.noReg.equals(other.noReg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.TPendaftaran[ noReg=" + noReg + " ]";
    }
    
}
