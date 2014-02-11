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
@Table(name = "test_model")
@NamedQueries({
    @NamedQuery(name = "TestModel.findAll", query = "SELECT t FROM TestModel t"),
    @NamedQuery(name = "TestModel.findByKdJurusan", query = "SELECT t FROM TestModel t WHERE t.kdJurusan = :kdJurusan"),
    @NamedQuery(name = "TestModel.findByDeskripsi", query = "SELECT t FROM TestModel t WHERE t.deskripsi = :deskripsi")})
public class TestModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_jurusan", nullable = false, length = 255)
    private String kdJurusan;
    @Column(name = "deskripsi", length = 255)
    private String deskripsi;

    public TestModel() {
    }

    public TestModel(String kdJurusan) {
        this.kdJurusan = kdJurusan;
    }

    public String getKdJurusan() {
        return kdJurusan;
    }

    public void setKdJurusan(String kdJurusan) {
        this.kdJurusan = kdJurusan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdJurusan != null ? kdJurusan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestModel)) {
            return false;
        }
        TestModel other = (TestModel) object;
        if ((this.kdJurusan == null && other.kdJurusan != null) || (this.kdJurusan != null && !this.kdJurusan.equals(other.kdJurusan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.config.spring.hibernate.model.TestModel[ kdJurusan=" + kdJurusan + " ]";
    }
    
}
