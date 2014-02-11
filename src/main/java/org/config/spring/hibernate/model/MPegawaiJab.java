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
@Table(name = "m_pegawai_jab")
public class MPegawaiJab implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_jabatan", nullable = false, length = 10)
    private String idJabatan;
    @Column(name = "nama_jabatan", length = 45)
    private String namaJabatan;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="pegawaiJab")
    @Fetch(FetchMode.JOIN)
    private Set<MPegawai> pegawaiSet;

    public String getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }

    public String getNamaJabatan() {
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
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
        final MPegawaiJab other = (MPegawaiJab) obj;
        if ((this.idJabatan == null) ? (other.idJabatan != null) : !this.idJabatan.equals(other.idJabatan)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.idJabatan != null ? this.idJabatan.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MPegawaiJab{" + "idJabatan=" + idJabatan + '}';
    }

    
    
}
