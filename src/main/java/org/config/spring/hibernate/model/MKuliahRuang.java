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
@Table(name = "m_kuliah_ruang")
public class MKuliahRuang implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ruang", nullable = false, length = 10)
    private String idRuang;
    @Column(name = "deskripsi", length = 45)
    private String deskripsi;

    @Column(name="jenis_ruang", length=15)
    private String jenisRuang;
    @Column(name="kapasitas")
    private int kapasitas;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="kuliahRuang")
    private Set<TKrsJadwal> tKrsJadwalSet;
    
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public void setIdRuang(String idRuang) {
        this.idRuang = idRuang;
    }

    public String getJenisRuang() {
        return jenisRuang;
    }

    public void setJenisRuang(String jenisRuang) {
        this.jenisRuang = jenisRuang;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public Set<TKrsJadwal> gettKrsJadwalSet() {
        return tKrsJadwalSet;
    }

    public void settKrsJadwalSet(Set<TKrsJadwal> tKrsJadwalSet) {
        this.tKrsJadwalSet = tKrsJadwalSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MKuliahRuang other = (MKuliahRuang) obj;
        if ((this.idRuang == null) ? (other.idRuang != null) : !this.idRuang.equals(other.idRuang)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idRuang != null ? this.idRuang.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MKuliahRuang{" + "idRuang=" + idRuang + '}';
    }

    
    
}
