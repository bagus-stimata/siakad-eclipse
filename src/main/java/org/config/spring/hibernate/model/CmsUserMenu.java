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
@Table(name = "cms_user_menu")
public class CmsUserMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CmsUserMenuPK cmsUserMenuPK;
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "params", length = 100)
    private String params;
    @Column(name = "notes", length = 100)
    private String notes;

    //SORTING SEMENTARA SAMBUNGAN DARI YANG USERS
    @OrderBy("cmsUserSubmenuPK.idSubmenu")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsUserMenu")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUserSubmenu> cmsUserSubmenuSet;
    
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CmsMenu cmsMenu;

    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CmsUsers cmsUsers;

    public CmsUserMenu() {
    }

    public CmsUserMenu(CmsUserMenuPK cmsUserMenuPK) {
        this.cmsUserMenuPK = cmsUserMenuPK;
    }

    public CmsUserMenu(int idMenu, String idUser) {
        this.cmsUserMenuPK = new CmsUserMenuPK(idMenu, idUser);
    }

    public CmsUserMenuPK getCmsUserMenuPK() {
        return cmsUserMenuPK;
    }

    public void setCmsUserMenuPK(CmsUserMenuPK cmsUserMenuPK) {
        this.cmsUserMenuPK = cmsUserMenuPK;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<CmsUserSubmenu> getCmsUserSubmenuSet() {
        return cmsUserSubmenuSet;
    }

    public void setCmsUserSubmenuSet(Set<CmsUserSubmenu> cmsUserSubmenuSet) {
        this.cmsUserSubmenuSet = cmsUserSubmenuSet;
    }

    public CmsMenu getCmsMenu() {
        return cmsMenu;
    }

    public void setCmsMenu(CmsMenu cmsMenu) {
        this.cmsMenu = cmsMenu;
    }

    public CmsUsers getCmsUsers() {
        return cmsUsers;
    }

    public void setCmsUsers(CmsUsers cmsUsers) {
        this.cmsUsers = cmsUsers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmsUserMenuPK != null ? cmsUserMenuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserMenu)) {
            return false;
        }
        CmsUserMenu other = (CmsUserMenu) object;
        if ((this.cmsUserMenuPK == null && other.cmsUserMenuPK != null) || (this.cmsUserMenuPK != null && !this.cmsUserMenuPK.equals(other.cmsUserMenuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsUserMenu[ cmsUserMenuPK=" + cmsUserMenuPK + " ]";
    }
    
}
