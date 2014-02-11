/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "matkul")
@NamedQueries({
    @NamedQuery(name = "Matkul.findAll", query = "SELECT m FROM Matkul m"),
    @NamedQuery(name = "Matkul.findByKodeMatkul", query = "SELECT m FROM Matkul m WHERE m.kodeMatkul = :kodeMatkul"),
    @NamedQuery(name = "Matkul.findByDescription", query = "SELECT m FROM Matkul m WHERE m.description = :description"),
    @NamedQuery(name = "Matkul.findBySks", query = "SELECT m FROM Matkul m WHERE m.sks = :sks")})
public class Matkul implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_matkul", nullable = false, length = 12)
    private String kodeMatkul;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "sks")
    private Integer sks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matkul1")
    private Set<Krs> krsSet;

    public Matkul() {
    }

    public Matkul(String kodeMatkul) {
        this.kodeMatkul = kodeMatkul;
    }

    public String getKodeMatkul() {
        return kodeMatkul;
    }

    public void setKodeMatkul(String kodeMatkul) {
        this.kodeMatkul = kodeMatkul;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSks() {
        return sks;
    }

    public void setSks(Integer sks) {
        this.sks = sks;
    }

    public Set<Krs> getKrsSet() {
        return krsSet;
    }

    public void setKrsSet(Set<Krs> krsSet) {
        this.krsSet = krsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeMatkul != null ? kodeMatkul.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matkul)) {
            return false;
        }
        Matkul other = (Matkul) object;
        if ((this.kodeMatkul == null && other.kodeMatkul != null) || (this.kodeMatkul != null && !this.kodeMatkul.equals(other.kodeMatkul))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.Matkul[ kodeMatkul=" + kodeMatkul + " ]";
    }
    
}
