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
@Table(name = "m_matkul_kur")
public class MMatkulKur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_kurikulum", nullable = false, length = 10)
    private String idKurikulum;
    @Column(name = "deskripsi", length = 45)
    private String deskripsi;
    @Column(name = "tahun_kurikulum")
    private Integer tahunKurikulum;
    @Lob
    @Column(name = "ket", length = 65535)
    private String ket;
    
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="matkulKur")
    @Fetch(FetchMode.JOIN)
    private Set<MMatkul> matkulSet;
    
    public MMatkulKur() {
    }

    public String getIdKurikulum() {
        return idKurikulum;
    }

    public void setIdKurikulum(String idKurikulum) {
        this.idKurikulum = idKurikulum;
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


    public Integer getTahunKurikulum() {
        return tahunKurikulum;
    }

    public void setTahunKurikulum(Integer tahunKurikulum) {
        this.tahunKurikulum = tahunKurikulum;
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
        final MMatkulKur other = (MMatkulKur) obj;
        if ((this.idKurikulum == null) ? (other.idKurikulum != null) : !this.idKurikulum.equals(other.idKurikulum)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.idKurikulum != null ? this.idKurikulum.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MMatkulKur{" + "idKurikulum=" + idKurikulum + '}';
    }

    
}
