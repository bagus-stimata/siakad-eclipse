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
@Table(name = "m_pegawai_gol")
public class MPegawaiGol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_golongan", nullable = false, length = 10)
    private String idGolongan;
    @Column(name = "nama_golongan", length = 45)
    private String namaGolongan;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="pegawaiGol")
    @Fetch(FetchMode.JOIN)
    private Set<MPegawai> pegawaiSet;

    public String getIdGolongan() {
        return idGolongan;
    }

    public void setIdGolongan(String idGolongan) {
        this.idGolongan = idGolongan;
    }

    public String getNamaGolongan() {
        return namaGolongan;
    }

    public void setNamaGolongan(String namaGolongan) {
        this.namaGolongan = namaGolongan;
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
        final MPegawaiGol other = (MPegawaiGol) obj;
        if ((this.idGolongan == null) ? (other.idGolongan != null) : !this.idGolongan.equals(other.idGolongan)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.idGolongan != null ? this.idGolongan.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MPegawaiGol{" + "idGolongan=" + idGolongan + '}';
    }


    
}
