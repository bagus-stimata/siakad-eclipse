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
@Table(name = "cms_menu")
public class CmsMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_menu", nullable = false)
    private Integer idMenu;
    @Column(name = "id_menu_type", length = 15)
    private Integer idMenuType;
    
    @Column(name = "title", length = 45)
    private String title;
    
    @Column(name = "content_url", length = 200)
    private String contentUrl;
    @Column(name = "image_url", length = 200)
    private String imageUrl;
    @Column(name = "title_icon_url", length = 200)
    private String titleIconUrl;
    @Column(name = "admin_menu")
    private Boolean adminMenu;
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "order_index")
    private Integer orderIndex;
    
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "otorize", length = 15)
    private String otorize;

    //SORTING >>> Milik WAJIB FETCH EIGER LHO
    @OneToMany(cascade= CascadeType.ALL, mappedBy="cmsMenu")
    @Fetch(FetchMode.JOIN)
    private Set<CmsSubmenu> cmsSubmenuSet;
    
 /*   TIDAK BOLEH ADA YANG KOSONG
    @ManyToOne(optional=false)
    @JoinColumn(name="id_menu_type", referencedColumnName="id_menu_type", insertable=false, updatable=false)
    private CmsMenuType cmsMenuType;
    * 
    */
    
    public CmsMenu() {
    }

    public CmsMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getIdMenuType() {
        return idMenuType;
    }

    public void setIdMenuType(Integer idMenuType) {
        this.idMenuType = idMenuType;
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

    public Boolean getAdminMenu() {
        return adminMenu;
    }

    public void setAdminMenu(Boolean adminMenu) {
        this.adminMenu = adminMenu;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getOtorize() {
        return otorize;
    }

    public void setOtorize(String otorize) {
        this.otorize = otorize;
    }

    public Set<CmsSubmenu> getCmsSubmenuSet() {
        return cmsSubmenuSet;
    }

    public void setCmsSubmenuSet(Set<CmsSubmenu> cmsSubmenuSet) {
        this.cmsSubmenuSet = cmsSubmenuSet;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsMenu)) {
            return false;
        }
        CmsMenu other = (CmsMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsMenu[ idMenu=" + idMenu + " ]";
    }
    
}
