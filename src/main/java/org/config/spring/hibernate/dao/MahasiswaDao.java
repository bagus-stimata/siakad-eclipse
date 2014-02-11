/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.util.List;
import org.config.spring.hibernate.model.Mahasiswa;


/**
 *
 * @author bagus
 */
public interface MahasiswaDao {
    public List<Mahasiswa> getAll();
    public List<Mahasiswa> getAll(Mahasiswa mhs);
    public void addObject(Mahasiswa object);
    public void updateObject(Mahasiswa object);
    public void deleteObject(String ID);
    public void deleteObject(Mahasiswa object);    
}
