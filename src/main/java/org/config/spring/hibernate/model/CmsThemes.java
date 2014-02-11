/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author yhawin
 */
@Entity
@Table(name = "cms_themes")
@NamedQueries({
    @NamedQuery(name = "CmsThemes.findAll", query = "SELECT c FROM CmsThemes c"),
    @NamedQuery(name = "CmsThemes.findByIdThemes", query = "SELECT c FROM CmsThemes c WHERE c.idThemes = :idThemes"),
    @NamedQuery(name = "CmsThemes.findByTitle", query = "SELECT c FROM CmsThemes c WHERE c.title = :title"),
    @NamedQuery(name = "CmsThemes.findByDescription", query = "SELECT c FROM CmsThemes c WHERE c.description = :description")})
public class CmsThemes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_themes", nullable = false)
    private Integer idThemes;
    @Column(name = "title", length = 25)
    private String title;
    @Column(name = "description", length = 45)
    private String description;

    public CmsThemes() {
    }

    public CmsThemes(Integer idThemes) {
        this.idThemes = idThemes;
    }

    public Integer getIdThemes() {
        return idThemes;
    }

    public void setIdThemes(Integer idThemes) {
        this.idThemes = idThemes;
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
        hash += (idThemes != null ? idThemes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmsThemes)) {
            return false;
        }
        CmsThemes other = (CmsThemes) object;
        if ((this.idThemes == null && other.idThemes != null) || (this.idThemes != null && !this.idThemes.equals(other.idThemes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.CmsThemes[ idThemes=" + idThemes + " ]";
    }
    
}
