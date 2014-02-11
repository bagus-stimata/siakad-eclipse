/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "m_kuliah_jam")
public class MKuliahJam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_jam", nullable = false)
    private int idJam;
    @Column(name = "deskripsi", length = 45)
    private String deskripsi;
    @Column(name="jam_mulai")
    private Time jamMulai;
    @Column(name="jam_selesai")
    private Time jamSelesai;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="jamMulai")
    private Set<TKrsJadwal> tKrsJadwalMulaiSet;
    @OneToMany(cascade= CascadeType.ALL, mappedBy="jamSelesai")
    private Set<TKrsJadwal> tKrsJadwalSelesaiSet;
    
    public MKuliahJam() {
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getIdJam() {
        return idJam;
    }

    public void setIdJam(int idJam) {
        this.idJam = idJam;
    }


    public Time getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(Time jamMulai) {
        this.jamMulai = jamMulai;
    }

    public Time getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(Time jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public Set<TKrsJadwal> gettKrsJadwalMulaiSet() {
        return tKrsJadwalMulaiSet;
    }

    public void settKrsJadwalMulaiSet(Set<TKrsJadwal> tKrsJadwalMulaiSet) {
        this.tKrsJadwalMulaiSet = tKrsJadwalMulaiSet;
    }

    public Set<TKrsJadwal> gettKrsJadwalSelesaiSet() {
        return tKrsJadwalSelesaiSet;
    }

    public void settKrsJadwalSelesaiSet(Set<TKrsJadwal> tKrsJadwalSelesaiSet) {
        this.tKrsJadwalSelesaiSet = tKrsJadwalSelesaiSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MKuliahJam other = (MKuliahJam) obj;
        if (this.idJam != other.idJam) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.idJam;
        return hash;
    }

    @Override
    public String toString() {
        return "MKuliahJam{" + "idJam=" + idJam + '}';
    }


    
    
}
