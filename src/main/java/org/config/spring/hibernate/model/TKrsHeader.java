/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "t_krs_header")
public class TKrsHeader implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_krs", nullable = false, length = 18)
    private String idKrs;
    @Column(name="komen_kajur", length=200)
    private String komenKajur;
    @Column(name="komen_balas_kajur", length=200)
    private String komenBalasKajur;
    @Column(name="komen_wali", length=200)
    private String komenWali;
    @Column(name="komen_balas_wali", length=200)
    private String komenBalasWali;
    
    @Column(name = "thn_akademik")
    private Integer thnAkademik;    
    @Column(name="smt_genap_gasal")
    private Boolean smtGenapGasal;
    @Column(name="semester")
    private Integer semester;
    @Column(name="ip_semester_lalu")
    private Double ipSemesterLalu;   
    @Column(name="max_sks")
    private int maxSks;

    @Column(name="last_update")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lasUpdate;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_mhs", referencedColumnName="id_mhs", insertable=true, updatable=true)
    private MMhs mhs;
      
    @OneToMany(cascade= CascadeType.ALL, mappedBy="krsHeader")
    @Fetch(FetchMode.JOIN)
    private Set<TKrsDetail> krsDetailSet;

    public String getIdKrs() {
        return idKrs;
    }

    public void setIdKrs(String idKrs) {
        this.idKrs = idKrs;
    }

    public String getKomenBalasKajur() {
        return komenBalasKajur;
    }

    public void setKomenBalasKajur(String komenBalasKajur) {
        this.komenBalasKajur = komenBalasKajur;
    }

    public String getKomenBalasWali() {
        return komenBalasWali;
    }

    public void setKomenBalasWali(String komenBalasWali) {
        this.komenBalasWali = komenBalasWali;
    }

    public String getKomenKajur() {
        return komenKajur;
    }

    public void setKomenKajur(String komenKajur) {
        this.komenKajur = komenKajur;
    }

    public String getKomenWali() {
        return komenWali;
    }

    public void setKomenWali(String komenWali) {
        this.komenWali = komenWali;
    }

    public Set<TKrsDetail> getKrsDetailSet() {
        return krsDetailSet;
    }

    public void setKrsDetailSet(Set<TKrsDetail> krsDetailSet) {
        this.krsDetailSet = krsDetailSet;
    }

    public int getMaxSks() {
        return maxSks;
    }

    public void setMaxSks(int maxSks) {
        this.maxSks = maxSks;
    }

    public MMhs getMhs() {
        return mhs;
    }

    public void setMhs(MMhs mhs) {
        this.mhs = mhs;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Boolean getSmtGenapGasal() {
        return smtGenapGasal;
    }

    public void setSmtGenapGasal(Boolean smtGenapGasal) {
        this.smtGenapGasal = smtGenapGasal;
    }

    public Integer getThnAkademik() {
        return thnAkademik;
    }

    public void setThnAkademik(Integer thnAkademik) {
        this.thnAkademik = thnAkademik;
    }

    public Double getIpSemesterLalu() {
        return ipSemesterLalu;
    }

    public void setIpSemesterLalu(Double ipSemesterLalu) {
        this.ipSemesterLalu = ipSemesterLalu;
    }

    public Date getLasUpdate() {
        return lasUpdate;
    }

    public void setLasUpdate(Date lasUpdate) {
        this.lasUpdate = lasUpdate;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TKrsHeader other = (TKrsHeader) obj;
        if ((this.idKrs == null) ? (other.idKrs != null) : !this.idKrs.equals(other.idKrs)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.idKrs != null ? this.idKrs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TKrsHeader{" + "idKrs=" + idKrs + '}';
    }

    
}
