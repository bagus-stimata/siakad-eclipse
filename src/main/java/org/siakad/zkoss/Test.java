/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss;

import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsMenuDaoInter;
import org.config.spring.hibernate.dao.CmsUserMenuDaoInter;
import org.config.spring.hibernate.dao.MahasiswaDao;
import org.config.spring.hibernate.model.Mahasiswa;
import org.springframework.context.ApplicationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

/**
 *
 * @author bagus
 */
public class Test {
	
	ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    MahasiswaDao mhsDao = (MahasiswaDao) appContext.getBean("MahasiswaDaoBean");
	    
    List<Mahasiswa> listMhs = new ArrayList<Mahasiswa>();
            
    @Init
    public void init(){
        Mahasiswa mhs1 = new Mahasiswa();
        mhs1.setNama("Bagus winarno");
        mhs1.setAlamat("Jln. Kauman Gang IV Malang");
        listMhs.add(mhs1);
    }
    @AfterCompose
    public void afterCompose(){
    }

    public List<Mahasiswa> getListMhs() {
        return listMhs;
    }

    public void setListMhs(List<Mahasiswa> listMhs) {
        this.listMhs = listMhs;
    }
    
    
}
