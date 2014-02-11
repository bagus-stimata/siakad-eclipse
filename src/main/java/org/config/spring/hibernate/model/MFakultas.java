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
@Table(name = "m_fakultas")
public class MFakultas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_fakultas", nullable = false, length = 10)
    private String idFakultas;
    @Column(name = "nama_fakultas", length = 45)
    private String namaFakultas;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="fakultas")
    @Fetch(FetchMode.JOIN)
    private Set<MJurusan> jurusanSet;

    public String getIdFakultas() {
        return idFakultas;
    }

    public void setIdFakultas(String idFakultas) {
        this.idFakultas = idFakultas;
    }

    public Set<MJurusan> getJurusanSet() {
        return jurusanSet;
    }

    public void setJurusanSet(Set<MJurusan> jurusanSet) {
        this.jurusanSet = jurusanSet;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }


    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFakultas != null ? idFakultas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MFakultas)) {
            return false;
        }
        MFakultas other = (MFakultas) object;
        if ((this.idFakultas == null && other.idFakultas != null) || (this.idFakultas != null && !this.idFakultas.equals(other.idFakultas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.MFakultas[ idFakultas=" + idFakultas + " ]";
    }
    
}
