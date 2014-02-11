/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "krs")
public class Krs implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrsPK krsPK;
    @Column(name = "tgl_entri")
    @Temporal(TemporalType.DATE)
    private Date tglEntri;
    @JoinColumn(name = "nim", referencedColumnName = "nim", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Mahasiswa mahasiswa;
    @JoinColumn(name = "matkul", referencedColumnName = "kode_matkul", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Matkul matkul1;

    public Krs() {
    }

    public Krs(KrsPK krsPK) {
        this.krsPK = krsPK;
    }

    public Krs(String matkul, String nim) {
        this.krsPK = new KrsPK(matkul, nim);
    }

    public KrsPK getKrsPK() {
        return krsPK;
    }

    public void setKrsPK(KrsPK krsPK) {
        this.krsPK = krsPK;
    }

    public Date getTglEntri() {
        return tglEntri;
    }

    public void setTglEntri(Date tglEntri) {
        this.tglEntri = tglEntri;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public Matkul getMatkul1() {
        return matkul1;
    }

    public void setMatkul1(Matkul matkul1) {
        this.matkul1 = matkul1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krsPK != null ? krsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Krs)) {
            return false;
        }
        Krs other = (Krs) object;
        if ((this.krsPK == null && other.krsPK != null) || (this.krsPK != null && !this.krsPK.equals(other.krsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.Krs[ krsPK=" + krsPK + " ]";
    }
    
}
