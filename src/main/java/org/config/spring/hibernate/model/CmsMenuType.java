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
@Table(name = "cms_menu_type")
public class CmsMenuType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_menu_type", nullable = false)
    private Integer idMenuType;
    @Column(name = "title", length = 25)
    private String title;
    @Column(name = "description", length = 100)
    private String description;
/*
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER, mappedBy="cmsMenuType")
    private Set<CmsMenu> cmsMenuSet;
* 
*/
            
    public CmsMenuType() {
    }

    public CmsMenuType(Integer idMenuType) {
        this.idMenuType = idMenuType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenuType != null ? idMenuType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsMenuType)) {
            return false;
        }
        CmsMenuType other = (CmsMenuType) object;
        if ((this.idMenuType == null && other.idMenuType != null) || (this.idMenuType != null && !this.idMenuType.equals(other.idMenuType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsMenuType[ idMenuType=" + idMenuType + " ]";
    }
    
}
