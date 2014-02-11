/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author yhawin
 */
@Embeddable
public class CmsUserSubmenuPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_menu", nullable = true)
    private int idMenu;
    
    @Basic(optional = false)
    @Column(name = "id_user", nullable = false, length = 25)
    private String idUser;

    @Basic(optional = false)
    @Column(name = "id_submenu", nullable = true)
    private int idSubmenu;

    public CmsUserSubmenuPK() {
    }

    public CmsUserSubmenuPK(int idMenu, int idSubmenu) {
        this.idMenu = idMenu;
        this.idSubmenu = idSubmenu;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdSubmenu() {
        return idSubmenu;
    }

    public void setIdSubmenu(int idSubmenu) {
        this.idSubmenu = idSubmenu;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMenu;
        hash += (int) idSubmenu;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserSubmenuPK)) {
            return false;
        }
        CmsUserSubmenuPK other = (CmsUserSubmenuPK) object;
        if (this.idMenu != other.idMenu) {
            return false;
        }
        if (this.idSubmenu != other.idSubmenu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsUserSubmenuPK[ idMenu=" + idMenu + ", idSubmenu=" + idSubmenu + " ]";
    }
    
}
