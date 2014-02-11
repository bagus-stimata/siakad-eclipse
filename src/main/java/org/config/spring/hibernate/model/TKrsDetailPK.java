/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author yhawin
 */
@Embeddable
public class TKrsDetailPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_krs", nullable = false, length = 18)
    private String idKrs;
    @Basic(optional = false)
    @Column(name = "id_matkul", nullable = false, length = 10)
    private String idMatkul;

    public TKrsDetailPK() {
    }

    public TKrsDetailPK(String idKrs, String idMatkul) {
        this.idKrs = idKrs;
        this.idMatkul = idMatkul;
    }

    public String getIdKrs() {
        return idKrs;
    }

    public void setIdKrs(String idKrs) {
        this.idKrs = idKrs;
    }

    public String getIdMatkul() {
        return idMatkul;
    }

    public void setIdMatkul(String idMatkul) {
        this.idMatkul = idMatkul;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKrs != null ? idKrs.hashCode() : 0);
        hash += (idMatkul != null ? idMatkul.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TKrsDetailPK)) {
            return false;
        }
        TKrsDetailPK other = (TKrsDetailPK) object;
        if ((this.idKrs == null && other.idKrs != null) || (this.idKrs != null && !this.idKrs.equals(other.idKrs))) {
            return false;
        }
        if ((this.idMatkul == null && other.idMatkul != null) || (this.idMatkul != null && !this.idMatkul.equals(other.idMatkul))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.TKrsDetailPK[ idKrs=" + idKrs + ", idMatkul=" + idMatkul + " ]";
    }
    
}
