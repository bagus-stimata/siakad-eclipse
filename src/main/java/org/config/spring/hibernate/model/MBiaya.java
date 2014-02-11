/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name="m_biaya")
public class MBiaya implements Serializable  {
    @Id
    @Basic(optional=false)
    private Long id_biaya;
    @Column(name="deskripsi", length=25)
    private String deskripsi;
    @Column(name="nilai_biaya")
    private Double nilaiBiaya;
    @Column(name="ket1", length=200)
    private String ket1;
    @Column(name="ket2", length=200)
    private String ket2;

    public Long getId_biaya() {
        return id_biaya;
    }

    public void setId_biaya(Long id_biaya) {
        this.id_biaya = id_biaya;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKet1() {
        return ket1;
    }

    public void setKet1(String ket1) {
        this.ket1 = ket1;
    }

    public String getKet2() {
        return ket2;
    }

    public void setKet2(String ket2) {
        this.ket2 = ket2;
    }

    public Double getNilaiBiaya() {
        return nilaiBiaya;
    }

    public void setNilaiBiaya(Double nilaiBiaya) {
        this.nilaiBiaya = nilaiBiaya;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MBiaya other = (MBiaya) obj;
        if (this.id_biaya != other.id_biaya && (this.id_biaya == null || !this.id_biaya.equals(other.id_biaya))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id_biaya != null ? this.id_biaya.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MBiaya{" + "id_biaya=" + id_biaya + '}';
    }
    
    
    
}
