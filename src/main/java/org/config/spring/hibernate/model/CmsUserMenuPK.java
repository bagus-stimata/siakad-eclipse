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
public class CmsUserMenuPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_menu", nullable = false)
    private int idMenu;
    @Basic(optional = false)
    @Column(name = "id_user", nullable = false, length = 25)
    private String idUser;

    public CmsUserMenuPK() {
    }

    public CmsUserMenuPK(int idMenu, String idUser) {
        this.idMenu = idMenu;
        this.idUser = idUser;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
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
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsUserMenuPK)) {
            return false;
        }
        CmsUserMenuPK other = (CmsUserMenuPK) object;
        if (this.idMenu != other.idMenu) {
            return false;
        }
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsUserMenuPK[ idMenu=" + idMenu + ", idUser=" + idUser + " ]";
    }
    
}
