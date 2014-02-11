/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.siakad.zkoss.master.KuliahJam;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "t_krs_jadwal")
public class TKrsJadwal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.AUTO)  
    @Column(name = "id_jadwal", nullable = false)
    private Integer idJadwal;
    @Column(name="nama_kelas", length=25)
    private String namaKelas;
    @Column(name="kapasitas")
    private Integer kapasitas;
    @Column(name="terisi")
    private Integer terisi;
    
    @Column(name="tahun_ajaran")
    private Integer tahunAjaran;
    @Column(name="smt_genap_gasal")
    private Boolean smtGenapGasal;
    
    @Column(name="hari")
    private Integer hari;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_jam_mulai", referencedColumnName="id_jam", insertable=true, updatable=true)
    private MKuliahJam jamMulai;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_jam_selesai", referencedColumnName="id_jam", insertable=true, updatable=true)
    private MKuliahJam jamSelesai;    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_ruang", referencedColumnName="id_ruang", insertable=true, updatable=true)
    private MKuliahRuang kuliahRuang;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_matkul", referencedColumnName="id_matkul", insertable=true, updatable=true)
    private MMatkul matkul;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_pegawai", referencedColumnName="id_pegawai", insertable=true, updatable=true)
    private MPegawai pegawai;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="krsJadwal")
    @Fetch(FetchMode.JOIN)
    private Set<TKrsDetail> krsDetailsSet;
    
    public Integer getHari() {
        return hari;
    }

    public void setHari(Integer hari) {
        this.hari = hari;
    }

    public Integer getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(Integer idJadwal) {
        this.idJadwal = idJadwal;
    }

    public MKuliahJam getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(MKuliahJam jamMulai) {
        this.jamMulai = jamMulai;
    }

    public MKuliahJam getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(MKuliahJam jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public Integer getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(Integer kapasitas) {
        this.kapasitas = kapasitas;
    }

    public MKuliahRuang getKuliahRuang() {
        return kuliahRuang;
    }

    public void setKuliahRuang(MKuliahRuang kuliahRuang) {
        this.kuliahRuang = kuliahRuang;
    }

    public MMatkul getMatkul() {
        return matkul;
    }

    public void setMatkul(MMatkul matkul) {
        this.matkul = matkul;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public MPegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(MPegawai pegawai) {
        this.pegawai = pegawai;
    }

    public Boolean getSmtGenapGasal() {
        return smtGenapGasal;
    }

    public void setSmtGenapGasal(Boolean smtGenapGasal) {
        this.smtGenapGasal = smtGenapGasal;
    }

    public Integer getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(Integer tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public Integer getTerisi() {
        return terisi;
    }

    public void setTerisi(Integer terisi) {
        this.terisi = terisi;
    }

    public Set<TKrsDetail> getKrsDetailsSet() {
        return krsDetailsSet;
    }

    public void setKrsDetailsSet(Set<TKrsDetail> krsDetailsSet) {
        this.krsDetailsSet = krsDetailsSet;
    }


    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TKrsJadwal other = (TKrsJadwal) obj;
        if (this.idJadwal != other.idJadwal && (this.idJadwal == null || !this.idJadwal.equals(other.idJadwal))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.idJadwal != null ? this.idJadwal.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TKrsJadwal{" + "idJadwal=" + idJadwal + '}';
    }

    
}
