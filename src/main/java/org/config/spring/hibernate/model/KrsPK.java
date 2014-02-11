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
public class KrsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "matkul", nullable = false, length = 12)
    private String matkul;
    @Basic(optional = false)
    @Column(name = "nim", nullable = false, length = 12)
    private String nim;

    public KrsPK() {
    }

    public KrsPK(String matkul, String nim) {
        this.matkul = matkul;
        this.nim = nim;
    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matkul != null ? matkul.hashCode() : 0);
        hash += (nim != null ? nim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrsPK)) {
            return false;
        }
        KrsPK other = (KrsPK) object;
        if ((this.matkul == null && other.matkul != null) || (this.matkul != null && !this.matkul.equals(other.matkul))) {
            return false;
        }
        if ((this.nim == null && other.nim != null) || (this.nim != null && !this.nim.equals(other.nim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.KrsPK[ matkul=" + matkul + ", nim=" + nim + " ]";
    }
    
}
