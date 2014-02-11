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
@Table(name = "cms_submenu")
public class CmsSubmenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_submenu", nullable = false)
    private Integer idSubmenu;
    @Column(name = "title", length = 25)
    private String title;
    @Column(name = "content_url", length = 200)
    private String contentUrl;
    @Column(name = "image_url", length = 200)
    private String imageUrl;
    @Column(name = "title_icon_url", length = 200)
    private String titleIconUrl;
    @Column(name = "admin_menu")
    private Short adminMenu;
    @Column(name = "active")
    private Short active;    
    
    @Column(name = "order_index")
    private Integer orderIndex;
    
    @Column(name = "visible")
    private Short visible;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="cmsSubmenu")
    @Fetch(FetchMode.JOIN)
    private Set<CmsUserSubmenu> cmsUserSubmenuSet;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="id_menu", referencedColumnName="id_menu", insertable=false, updatable=false)
    private CmsMenu cmsMenu;
    
    public CmsSubmenu() {
    }

    public CmsSubmenu(Integer idSubmenu) {
        this.idSubmenu = idSubmenu;
    }

    public Integer getIdSubmenu() {
        return idSubmenu;
    }

    public void setIdSubmenu(Integer idSubmenu) {
        this.idSubmenu = idSubmenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitleIconUrl() {
        return titleIconUrl;
    }

    public void setTitleIconUrl(String titleIconUrl) {
        this.titleIconUrl = titleIconUrl;
    }

    public Short getAdminMenu() {
        return adminMenu;
    }

    public void setAdminMenu(Short adminMenu) {
        this.adminMenu = adminMenu;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public Short getVisible() {
        return visible;
    }

    public void setVisible(Short visible) {
        this.visible = visible;
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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubmenu != null ? idSubmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsSubmenu)) {
            return false;
        }
        CmsSubmenu other = (CmsSubmenu) object;
        if ((this.idSubmenu == null && other.idSubmenu != null) || (this.idSubmenu != null && !this.idSubmenu.equals(other.idSubmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsSubmenu[ idSubmenu=" + idSubmenu + " ]";
    }
    
}
