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
@Table(name = "m_jurusan")
public class MJurusan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_jurusan", nullable = false, length = 10)
    private String idJurusan;
    @Column(name = "nama_jurusan", length = 45)
    private String namaJurusan;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="jurusan")
    @Fetch(FetchMode.JOIN)
    private Set<MProdi> prodiSet;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_fakultas", referencedColumnName="id_fakultas", insertable=true, updatable=true)
    private MFakultas fakultas;

    public String getIdJurusan() {
        return idJurusan;
    }

    public void setIdJurusan(String idJurusan) {
        this.idJurusan = idJurusan;
    }

    public String getNamaJurusan() {
        return namaJurusan;
    }

    public void setNamaJurusan(String namaJurusan) {
        this.namaJurusan = namaJurusan;
    }

    public Set<MProdi> getProdiSet() {
        return prodiSet;
    }

    public void setProdiSet(Set<MProdi> prodiSet) {
        this.prodiSet = prodiSet;
    }
    
    
    
    
    @Override
    public String toString() {
        return "MJurusan{" + "idJurusan=" + idJurusan + '}';
    }

    public MFakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(MFakultas fakultas) {
        this.fakultas = fakultas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MJurusan other = (MJurusan) obj;
        if ((this.idJurusan == null) ? (other.idJurusan != null) : !this.idJurusan.equals(other.idJurusan)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.idJurusan != null ? this.idJurusan.hashCode() : 0);
        return hash;
    }

    
}
