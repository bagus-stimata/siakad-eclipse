/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "mahasiswa")
public class Mahasiswa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nim", nullable = false, length = 12)
    private String nim;
    @Column(name = "nama", length = 45)
    private String nama;
    @Column(name = "alamat", length = 50)
    private String alamat;
    @Column(name = "sks")
    private Integer sks;
    @Column(name = "gender")
    private Short gender;
    @Column(name = "tgl_lhr")
    @Temporal(TemporalType.DATE)
    private Date tglLhr;
    @Column(name = "kode", length = 255)
    private String kode;
    @JoinColumn(name = "jurusan", referencedColumnName = "kd_jurusan")
    @ManyToOne
    private Jurusan jurusan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mahasiswa")
    private Set<Krs> krsSet;

    public Mahasiswa() {
    }

    public Mahasiswa(String nim) {
        this.nim = nim;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getSks() {
        return sks;
    }

    public void setSks(Integer sks) {
        this.sks = sks;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Date getTglLhr() {
        return tglLhr;
    }

    public void setTglLhr(Date tglLhr) {
        this.tglLhr = tglLhr;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Jurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }

    public Set<Krs> getKrsSet() {
        return krsSet;
    }

    public void setKrsSet(Set<Krs> krsSet) {
        this.krsSet = krsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nim != null ? nim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mahasiswa)) {
            return false;
        }
        Mahasiswa other = (Mahasiswa) object;
        if ((this.nim == null && other.nim != null) || (this.nim != null && !this.nim.equals(other.nim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.Mahasiswa[ nim=" + nim + " ]";
    }
    
}
