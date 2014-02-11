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

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "m_prodi")
public class MProdi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_prodi", nullable = false, length = 10)
    private String idProdi;

    @Basic(optional = true)
    @Column(name = "nama_prodi", length = 45)
    private String namaProdi;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_jurusan", referencedColumnName="id_jurusan", insertable=true, updatable=true)
    private MJurusan jurusan; 

    @OneToMany(cascade= CascadeType.ALL, mappedBy="prodi")
    @Fetch(FetchMode.JOIN)
    private Set<MPegawai> pegawaiSet; 

    @OneToMany(cascade= CascadeType.ALL, mappedBy="prodi")
    @Fetch(FetchMode.JOIN)
    private Set<MMhs> mhsSet;

    public String getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(String idProdi) {
        this.idProdi = idProdi;
    }

    public MJurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(MJurusan jurusan) {
        this.jurusan = jurusan;
    }

    public Set<MMhs> getMhsSet() {
        return mhsSet;
    }

    public void setMhsSet(Set<MMhs> mhsSet) {
        this.mhsSet = mhsSet;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public void setNamaProdi(String namaProdi) {
        this.namaProdi = namaProdi;
    }

    public Set<MPegawai> getPegawaiSet() {
        return pegawaiSet;
    }

    public void setPegawaiSet(Set<MPegawai> pegawaiSet) {
        this.pegawaiSet = pegawaiSet;
    }

    
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MProdi other = (MProdi) obj;
        if ((this.idProdi == null) ? (other.idProdi != null) : !this.idProdi.equals(other.idProdi)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.idProdi != null ? this.idProdi.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MProdi{" + "idProdi=" + idProdi + '}';
    }

    
    
}
