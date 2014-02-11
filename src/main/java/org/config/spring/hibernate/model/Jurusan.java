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
@Table(name = "jurusan")
public class Jurusan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_jurusan", nullable = false, length = 5)
    private String kdJurusan;
    @Column(name = "deskripsi", length = 45)
    private String deskripsi;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "jurusan")
    @Fetch(FetchMode.JOIN)
    private Set<Mahasiswa> mahasiswaSet;

    public Jurusan() {
    }

    public Jurusan(String kdJurusan) {
        this.kdJurusan = kdJurusan;
    }

    public String getKdJurusan() {
        return kdJurusan;
    }

    public void setKdJurusan(String kdJurusan) {
        this.kdJurusan = kdJurusan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Set<Mahasiswa> getMahasiswaSet() {
        return mahasiswaSet;
    }

    public void setMahasiswaSet(Set<Mahasiswa> mahasiswaSet) {
        this.mahasiswaSet = mahasiswaSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdJurusan != null ? kdJurusan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jurusan)) {
            return false;
        }
        Jurusan other = (Jurusan) object;
        if ((this.kdJurusan == null && other.kdJurusan != null) || (this.kdJurusan != null && !this.kdJurusan.equals(other.kdJurusan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.Jurusan[ kdJurusan=" + kdJurusan + " ]";
    }
    
}
