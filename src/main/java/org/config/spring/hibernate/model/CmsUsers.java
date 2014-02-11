/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
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
@Table(name = "cms_users")
public class CmsUsers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_user", nullable = false, length = 25)
    private String idUser;
    @Column(name = "password", length = 200)
    private String password;
    @Column(name = "full_name", length = 45)
    private String fullName;
    @Column(name="gender")
    private Boolean gender;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "phone", length = 25)
    private String phone;
    @Column(name = "ref_code", length = 45)
    private String refCode;
    @Column(name = "themes_index")
    private Integer themesIndex;
    @Column(name = "user_type")
    private Integer userType;
    @Column(name = "block")
    private Boolean block;
    @Column(name = "register_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;
    @Column(name = "last_visit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitDate;
    @Column(name = "activation")
    private Boolean activation;
    @Column(name = "params", length = 100)
    private String params;
    
    //SORTING SEMETARA
    @OneToMany(cascade= CascadeType.ALL,  mappedBy="cmsUsers")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUserMenu> cmsUserMenuSet;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="cmsUsers")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUserSubmenu> cmsUserSubmenuSet;

    @ManyToOne(optional=true)
    @JoinColumn(name="id_pegawai", referencedColumnName="id_pegawai", updatable=true, insertable=true)
    private MPegawai pegawai;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="id_mhs", referencedColumnName="id_mhs", updatable=true, insertable=true)
    private MMhs mhs;
    
    public CmsUsers() {
    }

    public CmsUsers(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Integer getThemesIndex() {
        return themesIndex;
    }

    public void setThemesIndex(Integer themesIndex) {
        this.themesIndex = themesIndex;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }


    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Set<CmsUserMenu> getCmsUserMenuSet() {
        return cmsUserMenuSet;
    }

    public void setCmsUserMenuSet(Set<CmsUserMenu> cmsUserMenuSet) {
        this.cmsUserMenuSet = cmsUserMenuSet;
    }


    public MPegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(MPegawai pegawai) {
        this.pegawai = pegawai;
    }

    public MMhs getMhs() {
        return mhs;
    }

    public void setMhs(MMhs mhs) {
        this.mhs = mhs;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Set<CmsUserSubmenu> getCmsUserSubmenuSet() {
        return cmsUserSubmenuSet;
    }

    public void setCmsUserSubmenuSet(Set<CmsUserSubmenu> cmsUserSubmenuSet) {
        this.cmsUserSubmenuSet = cmsUserSubmenuSet;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CmsUsers other = (CmsUsers) obj;
        if ((this.idUser == null) ? (other.idUser != null) : !this.idUser.equals(other.idUser)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.idUser != null ? this.idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CmsUsers{" + "idUser=" + idUser + '}';
    }

 


    
    
}
