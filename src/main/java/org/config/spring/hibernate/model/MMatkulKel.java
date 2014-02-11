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
@Table(name = "m_matkul_kel")
public class MMatkulKel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_kelompok", nullable = false, length = 10)
    private String idKelompok;
    @Column(name = "deskripsi", length = 45)
    private String deskripsi;
    @Lob
    @Column(name = "ket", length = 65535)
    private String ket;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="matkulKel")
    @Fetch(FetchMode.JOIN)
    private Set<MMatkul> matkulSet;
    
    public MMatkulKel() {
    }

    public String getIdKelompok() {
        return idKelompok;
    }

    public void setIdKelompok(String idKelompok) {
        this.idKelompok = idKelompok;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Set<MMatkul> getMatkulSet() {
        return matkulSet;
    }

    public void setMatkulSet(Set<MMatkul> matkulSet) {
        this.matkulSet = matkulSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MMatkulKel other = (MMatkulKel) obj;
        if ((this.idKelompok == null) ? (other.idKelompok != null) : !this.idKelompok.equals(other.idKelompok)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.idKelompok != null ? this.idKelompok.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MMatkulKel{" + "idKelompok=" + idKelompok + '}';
    }

    
}
