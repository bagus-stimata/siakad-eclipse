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
@Table(name = "t_krs_detail")
public class TKrsDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TKrsDetailPK tKrsDetailPK;
    @Column(name = "kelas", length = 10)
    private String kelas;
    @Column(name = "disetujuiKajur")
    private Boolean disetujuiKajur;
    @Column(name="disetujuiWali")
    private Boolean disetujuiWali;
    @Column(name="disetujuiBau")
    private Boolean disetujuiBau;
    
    @Column(name="lulus")
    private Boolean lulus;

    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_matkul", referencedColumnName="id_matkul", updatable=false, insertable=false)
    private MMatkul matkul;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_krs", referencedColumnName="id_krs", updatable=false, insertable=false)
    private TKrsHeader krsHeader;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_jadwal", referencedColumnName="id_jadwal", updatable=true, insertable=true)
    private TKrsJadwal krsJadwal;
    
    
    public Boolean getDisetujuiBau() {
        return disetujuiBau;
    }

    public void setDisetujuiBau(Boolean disetujuiBau) {
        this.disetujuiBau = disetujuiBau;
    }

    public Boolean getDisetujuiKajur() {
        return disetujuiKajur;
    }

    public void setDisetujuiKajur(Boolean disetujuiKajur) {
        this.disetujuiKajur = disetujuiKajur;
    }

    public Boolean getDisetujuiWali() {
        return disetujuiWali;
    }

    public void setDisetujuiWali(Boolean disetujuiWali) {
        this.disetujuiWali = disetujuiWali;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public TKrsHeader getKrsHeader() {
        return krsHeader;
    }

    public void setKrsHeader(TKrsHeader krsHeader) {
        this.krsHeader = krsHeader;
    }

    public MMatkul getMatkul() {
        return matkul;
    }

    public void setMatkul(MMatkul matkul) {
        this.matkul = matkul;
    }

    public TKrsDetailPK gettKrsDetailPK() {
        return tKrsDetailPK;
    }

    public void settKrsDetailPK(TKrsDetailPK tKrsDetailPK) {
        this.tKrsDetailPK = tKrsDetailPK;
    }

    public TKrsJadwal getKrsJadwal() {
        return krsJadwal;
    }

    public void setKrsJadwal(TKrsJadwal krsJadwal) {
        this.krsJadwal = krsJadwal;
    }

    public Boolean getLulus() {
        return lulus;
    }

    public void setLulus(Boolean lulus) {
        this.lulus = lulus;
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TKrsDetail other = (TKrsDetail) obj;
        if (this.tKrsDetailPK != other.tKrsDetailPK && (this.tKrsDetailPK == null || !this.tKrsDetailPK.equals(other.tKrsDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.tKrsDetailPK != null ? this.tKrsDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TKrsDetail{" + "tKrsDetailPK=" + tKrsDetailPK + '}';
    }



    
}
