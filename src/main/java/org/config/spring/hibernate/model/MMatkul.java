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
@Table(name = "m_matkul")
public class MMatkul implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_matkul", nullable = false, length = 10)
    private String idMatkul;
    @Column(name = "nama_matkul", length = 45)
    private String namaMatkul;
    
    @Column(name = "status_aktif")
    private Boolean statusAktif;

    @Column(name = "sks")
    private Integer sks;
    @Column(name = "semester")
    private Integer semester;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "matkul")    
    @Fetch(FetchMode.JOIN)
    private Set<TKrsJadwal> tkrsJadwalSet;
    
    
    
    
    /** CIRCULAR REFERENCE Perhatikan kejadian paling aneh dibawah**/
    //@Column(name = "prasarat", length = 10)
    //private String prasarat;
    @OneToMany(cascade= CascadeType.ALL, mappedBy = "prasarat")    
    @Fetch(FetchMode.JOIN)
    private Set<MMatkul> matkulSet;
    @Column(name="prasaratJumlah")
    private Integer prasaratJumlah;

   
    @JoinColumn(name = "prasarat", referencedColumnName = "id_matkul")
    @ManyToOne
    private MMatkul prasarat;
    
    @JoinColumn(name = "prasarat2", referencedColumnName = "id_matkul")
    @ManyToOne
    private MMatkul prasarat2;
    
    @Column(name = "ket", length = 100)
    private String ket;
   
    @ManyToOne(optional=true)//Jika foreingn key apa tetep masih ditampilkan
    @JoinColumn(name="id_kelompok", referencedColumnName="id_kelompok",  insertable=true, updatable=true)
    private MMatkulKel matkulKel; //ini yang dimappingkan di MMatkulKel mappedBy
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_kurikulum", referencedColumnName="id_kurikulum", insertable=true, updatable=true, 
            columnDefinition="varchar(10) default '' ")
    private MMatkulKur matkulKur;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="matkul")
    @Fetch(FetchMode.JOIN)
    private Set<TKrsDetail> krsDetailSet;
    
    public MMatkul() {
    }

    public MMatkul(String idMatkul) {
        this.idMatkul = idMatkul;
    }

    public String getIdMatkul() {
        return idMatkul;
    }

    public void setIdMatkul(String idMatkul) {
        this.idMatkul = idMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public Integer getSks() {
        return sks;
    }

    public void setSks(Integer sks) {
        this.sks = sks;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Set<MMatkul> getMatkulSet() {
        return matkulSet;
    }

    public void setMatkulSet(Set<MMatkul> matkulSet) {
        this.matkulSet = matkulSet;
    }

    public MMatkul getPrasarat() {
        return prasarat;
    }

    public void setPrasarat(MMatkul prasarat) {
        this.prasarat = prasarat;
    }


    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public MMatkulKel getMatkulKel() {
        return matkulKel;
    }

    public void setMatkulKel(MMatkulKel matkulKel) {
        this.matkulKel = matkulKel;
    }

    public MMatkulKur getMatkulKur() {
        return matkulKur;
    }

    public void setMatkulKur(MMatkulKur matkulKur) {
        this.matkulKur = matkulKur;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public Set<TKrsJadwal> getTkrsJadwalSet() {
        return tkrsJadwalSet;
    }

    public void setTkrsJadwalSet(Set<TKrsJadwal> tkrsJadwalSet) {
        this.tkrsJadwalSet = tkrsJadwalSet;
    }

    public Set<TKrsDetail> getKrsDetailSet() {
        return krsDetailSet;
    }

    public void setKrsDetailSet(Set<TKrsDetail> krsDetailSet) {
        this.krsDetailSet = krsDetailSet;
    }

    public MMatkul getPrasarat2() {
        return prasarat2;
    }

    public void setPrasarat2(MMatkul prasarat2) {
        this.prasarat2 = prasarat2;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MMatkul other = (MMatkul) obj;
        if ((this.idMatkul == null) ? (other.idMatkul != null) : !this.idMatkul.equals(other.idMatkul)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.idMatkul != null ? this.idMatkul.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MMatkul{" + "idMatkul=" + idMatkul + '}';
    }

    public Integer getPrasaratJumlah() {
        return prasaratJumlah;
    }

    public void setPrasaratJumlah(Integer prasaratJumlah) {
        this.prasaratJumlah = prasaratJumlah;
    }



     
}
