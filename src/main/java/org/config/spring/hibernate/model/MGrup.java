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
@Table(name = "m_grup")
@NamedQueries({
    @NamedQuery(name = "MGrup.findAll", query = "SELECT m FROM MGrup m"),
    @NamedQuery(name = "MGrup.findByIdGrup", query = "SELECT m FROM MGrup m WHERE m.idGrup = :idGrup"),
    @NamedQuery(name = "MGrup.findByNamaGrup", query = "SELECT m FROM MGrup m WHERE m.namaGrup = :namaGrup")})
public class MGrup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_grup", nullable = false, length = 10)
    private String idGrup;
    @Column(name = "nama_grup", length = 45)
    private String namaGrup;
    @Lob
    @Column(name = "ket", length = 65535)
    private String ket;

    public MGrup() {
    }

    public MGrup(String idGrup) {
        this.idGrup = idGrup;
    }

    public String getIdGrup() {
        return idGrup;
    }

    public void setIdGrup(String idGrup) {
        this.idGrup = idGrup;
    }

    public String getNamaGrup() {
        return namaGrup;
    }

    public void setNamaGrup(String namaGrup) {
        this.namaGrup = namaGrup;
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
        hash += (idGrup != null ? idGrup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MGrup)) {
            return false;
        }
        MGrup other = (MGrup) object;
        if ((this.idGrup == null && other.idGrup != null) || (this.idGrup != null && !this.idGrup.equals(other.idGrup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.MGrup[ idGrup=" + idGrup + " ]";
    }
    
}
