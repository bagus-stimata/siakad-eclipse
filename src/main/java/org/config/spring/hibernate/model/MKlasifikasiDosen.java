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
@Table(name = "m_klasifikasi_dosen")
@NamedQueries({
    @NamedQuery(name = "MKlasifikasiDosen.findAll", query = "SELECT m FROM MKlasifikasiDosen m"),
    @NamedQuery(name = "MKlasifikasiDosen.findByIdKlasifikasi", query = "SELECT m FROM MKlasifikasiDosen m WHERE m.idKlasifikasi = :idKlasifikasi"),
    @NamedQuery(name = "MKlasifikasiDosen.findByNamaKlasifikasi", query = "SELECT m FROM MKlasifikasiDosen m WHERE m.namaKlasifikasi = :namaKlasifikasi")})
public class MKlasifikasiDosen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_klasifikasi", nullable = false, length = 10)
    private String idKlasifikasi;
    @Column(name = "nama_klasifikasi", length = 45)
    private String namaKlasifikasi;
    @Lob
    @Column(name = "ket", length = 65535)
    private String ket;

    public MKlasifikasiDosen() {
    }

    public MKlasifikasiDosen(String idKlasifikasi) {
        this.idKlasifikasi = idKlasifikasi;
    }

    public String getIdKlasifikasi() {
        return idKlasifikasi;
    }

    public void setIdKlasifikasi(String idKlasifikasi) {
        this.idKlasifikasi = idKlasifikasi;
    }

    public String getNamaKlasifikasi() {
        return namaKlasifikasi;
    }

    public void setNamaKlasifikasi(String namaKlasifikasi) {
        this.namaKlasifikasi = namaKlasifikasi;
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
        hash += (idKlasifikasi != null ? idKlasifikasi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MKlasifikasiDosen)) {
            return false;
        }
        MKlasifikasiDosen other = (MKlasifikasiDosen) object;
        if ((this.idKlasifikasi == null && other.idKlasifikasi != null) || (this.idKlasifikasi != null && !this.idKlasifikasi.equals(other.idKlasifikasi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.MKlasifikasiDosen[ idKlasifikasi=" + idKlasifikasi + " ]";
    }
    
}
