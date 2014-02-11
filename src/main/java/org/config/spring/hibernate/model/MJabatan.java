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
@Table(name = "m_jabatan")
public class MJabatan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_jabatan", nullable = false, length = 10)
    private String idJabatan;
    @Column(name = "nama_jabatan", length = 45)
    private String namaJabatan;
    @Lob
    @Column(name = "ket", length = 65535)
    private String ket;

    public MJabatan() {
    }

    public MJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }

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

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJabatan != null ? idJabatan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MJabatan)) {
            return false;
        }
        MJabatan other = (MJabatan) object;
        if ((this.idJabatan == null && other.idJabatan != null) || (this.idJabatan != null && !this.idJabatan.equals(other.idJabatan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.MJabatan[ idJabatan=" + idJabatan + " ]";
    }
    
}
