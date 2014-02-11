/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name="sysvar")
public class Sysvar implements Serializable {
    @Id
    @Basic(optional=false)
    @Column(name="id_sysvar", length=10, nullable=false)
    private String idSysvar;
    @Column(name="group_sysvar", length=10)
    private String groupSysvar;
    @Column(name="deskripsi", length=100)
    private String deskripsi;
    @Column(name="notes", length=200)
    private String notes;    
    @Column(name="tipe_data", length=5)
    private String tipeData;

    @Column(name="nilai_string1", length=200)
    private String nilaiString1;
    @Column(name="nilai_string2", length=200)    
    
    private String nilaiString2;
    @Column(name="nilai_bol1") 
    private Boolean nilaiBol1;
    @Column(name="nilai_bol2") 
    private Boolean nilaiBol2;

    @Column(name="nilai_int1") 
    private Integer nilaiInt1;
    @Column(name="nilai_int2") 
    private Integer nilaiInt2;
    @Column(name="nilai_double1") 
    private Double nilaiDouble1;
    @Column(name="nilai_double2") 
    private Double nilaiDouble2;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="nilai_date1") 
    private Date nilaiDate1;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="nilai_date2") 
    private Date nilaiDate2;
    @Column(name="nilai_time1") 
    private Time nilaiTime1;
    @Column(name="nilai_time2") 
    private Time nilaiTime2;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGroupSysvar() {
        return groupSysvar;
    }

    public void setGroupSysvar(String groupSysvar) {
        this.groupSysvar = groupSysvar;
    }

    public String getIdSysvar() {
        return idSysvar;
    }

    public void setIdSysvar(String idSysvar) {
        this.idSysvar = idSysvar;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTipeData() {
        return tipeData;
    }

    public void setTipeData(String tipeData) {
        this.tipeData = tipeData;
    }

    public Boolean getNilaiBol1() {
        return nilaiBol1;
    }

    public void setNilaiBol1(Boolean nilaiBol1) {
        this.nilaiBol1 = nilaiBol1;
    }

    public Boolean getNilaiBol2() {
        return nilaiBol2;
    }

    public void setNilaiBol2(Boolean nilaiBol2) {
        this.nilaiBol2 = nilaiBol2;
    }

    public String getNilaiString1() {
        return nilaiString1;
    }

    public void setNilaiString1(String nilaiString1) {
        this.nilaiString1 = nilaiString1;
    }

    public String getNilaiString2() {
        return nilaiString2;
    }

    public void setNilaiString2(String nilaiString2) {
        this.nilaiString2 = nilaiString2;
    }

    public Double getNilaiDouble1() {
        return nilaiDouble1;
    }

    public void setNilaiDouble1(Double nilaiDouble1) {
        this.nilaiDouble1 = nilaiDouble1;
    }

    public Double getNilaiDouble2() {
        return nilaiDouble2;
    }

    public void setNilaiDouble2(Double nilaiDouble2) {
        this.nilaiDouble2 = nilaiDouble2;
    }

    public Integer getNilaiInt1() {
        return nilaiInt1;
    }

    public void setNilaiInt1(Integer nilaiInt1) {
        this.nilaiInt1 = nilaiInt1;
    }

    public Integer getNilaiInt2() {
        return nilaiInt2;
    }

    public void setNilaiInt2(Integer nilaiInt2) {
        this.nilaiInt2 = nilaiInt2;
    }

    public Date getNilaiDate1() {
        return nilaiDate1;
    }

    public void setNilaiDate1(Date nilaiDate1) {
        this.nilaiDate1 = nilaiDate1;
    }

    public Date getNilaiDate2() {
        return nilaiDate2;
    }

    public void setNilaiDate2(Date nilaiDate2) {
        this.nilaiDate2 = nilaiDate2;
    }

    public Time getNilaiTime1() {
        return nilaiTime1;
    }

    public void setNilaiTime1(Time nilaiTime1) {
        this.nilaiTime1 = nilaiTime1;
    }

    public Time getNilaiTime2() {
        return nilaiTime2;
    }

    public void setNilaiTime2(Time nilaiTime2) {
        this.nilaiTime2 = nilaiTime2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sysvar other = (Sysvar) obj;
        if ((this.idSysvar == null) ? (other.idSysvar != null) : !this.idSysvar.equals(other.idSysvar)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.idSysvar != null ? this.idSysvar.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Sysvar{" + "idSysvar=" + idSysvar + '}';
    }

    
    
    
}
