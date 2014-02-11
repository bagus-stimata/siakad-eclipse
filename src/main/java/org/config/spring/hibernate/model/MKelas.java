/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "m_kelas")
public class MKelas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_kelas", nullable = false, length = 10)
    private String idKelas;
    @Column(name = "dosen", length = 10)
    private String dosen;
    @Column(name = "nama_kelas", length = 25)
    private String namaKelas;
    @Column(name = "kapasistas")
    private Integer kapasistas;
    @Column(name = "terisi")
    private Integer terisi;
    @Column(name = "tgl_disahkan")
    @Temporal(TemporalType.DATE)
    private Date tglDisahkan;
    @Column(name = "jam_mulai")
    private Integer jamMulai;
    @Column(name = "jam_sampai")
    private Integer jamSampai;
    @Column(name = "ruang", length = 10)
    private String ruang;

    public MKelas() {
    }

    public MKelas(String idKelas) {
        this.idKelas = idKelas;
    }

    public String getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(String idKelas) {
        this.idKelas = idKelas;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public Integer getKapasistas() {
        return kapasistas;
    }

    public void setKapasistas(Integer kapasistas) {
        this.kapasistas = kapasistas;
    }

    public Integer getTerisi() {
        return terisi;
    }

    public void setTerisi(Integer terisi) {
        this.terisi = terisi;
    }

    public Date getTglDisahkan() {
        return tglDisahkan;
    }

    public void setTglDisahkan(Date tglDisahkan) {
        this.tglDisahkan = tglDisahkan;
    }

    public Integer getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(Integer jamMulai) {
        this.jamMulai = jamMulai;
    }

    public Integer getJamSampai() {
        return jamSampai;
    }

    public void setJamSampai(Integer jamSampai) {
        this.jamSampai = jamSampai;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MKelas other = (MKelas) obj;
        if ((this.idKelas == null) ? (other.idKelas != null) : !this.idKelas.equals(other.idKelas)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.idKelas != null ? this.idKelas.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MKelas{" + "idKelas=" + idKelas + '}';
    }

  


    
}
