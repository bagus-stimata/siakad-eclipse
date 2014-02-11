/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "cms_user_submenu")
public class CmsUserSubmenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CmsUserSubmenuPK cmsUserSubmenuPK;
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "params", length = 100)
    private String params;
    @Column(name = "notes", length = 100)
    private String notes;
    
    @ManyToOne(optional=false)
    @JoinColumns({@JoinColumn(name="id_user", referencedColumnName="id_user", insertable=false, updatable=false), 
        @JoinColumn(name="id_menu", referencedColumnName="id_menu", insertable=false, updatable=false)})
    private CmsUserMenu cmsUserMenu;
 
    @ManyToOne(optional=false)
    @JoinColumn(name="id_submenu", referencedColumnName="id_submenu", insertable=false, updatable=false)
    private CmsSubmenu cmsSubmenu;

    @ManyToOne(optional=false)
    @JoinColumn(name="id_user",referencedColumnName="id_user", updatable=false, insertable=false)
    private CmsUsers cmsUsers;
    
    public CmsUserSubmenu() {
    }

    public CmsUserSubmenu(CmsUserSubmenuPK cmsUserSubmenuPK) {
        this.cmsUserSubmenuPK = cmsUserSubmenuPK;
    }

    public CmsUserSubmenu(int idMenu, int idSubmenu) {
        this.cmsUserSubmenuPK = new CmsUserSubmenuPK(idMenu, idSubmenu);
    }

    public CmsUserSubmenuPK getCmsUserSubmenuPK() {
        return cmsUserSubmenuPK;
    }

    public void setCmsUserSubmenuPK(CmsUserSubmenuPK cmsUserSubmenuPK) {
        this.cmsUserSubmenuPK = cmsUserSubmenuPK;
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

    public CmsUserMenu getCmsUserMenu() {
        return cmsUserMenu;
    }

    public void setCmsUserMenu(CmsUserMenu cmsUserMenu) {
        this.cmsUserMenu = cmsUserMenu;
    }

    public CmsSubmenu getCmsSubmenu() {
        return cmsSubmenu;
    }

    public void setCmsSubmenu(CmsSubmenu cmsSubmenu) {
        this.cmsSubmenu = cmsSubmenu;
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
        hash += (cmsUserSubmenuPK != null ? cmsUserSubmenuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserSubmenu)) {
            return false;
        }
        CmsUserSubmenu other = (CmsUserSubmenu) object;
        if ((this.cmsUserSubmenuPK == null && other.cmsUserSubmenuPK != null) || (this.cmsUserSubmenuPK != null && !this.cmsUserSubmenuPK.equals(other.cmsUserSubmenuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsUserSubmenu[ cmsUserSubmenuPK=" + cmsUserSubmenuPK + " ]";
    }
    
}
