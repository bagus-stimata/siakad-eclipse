/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.*;
import org.config.spring.hibernate.model.*;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestFaktor {
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
        MatkulKurDaoInter matkulKurDao = (MatkulKurDaoInter) appContext.getBean("MatkulKurDaoBean");
        CmsUsersDaoInter cmsUsersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
        
        MhsDaoInter mhsDao = (MhsDaoInter) appContext.getBean("MhsDaoBean");
        ProdiDaoInter prodiDao = (ProdiDaoInter) appContext.getBean("ProdiDaoBean");

        
        
        MMhs m = new MMhs();
        m.setIdMhs("%");
        m.setNamaMhs("%");
        
        MProdi prodi = new MProdi();
        prodi.setIdProdi("%");
                
        m.setProdi(prodi);
        m.setAngkatan(0);
        
        List<MMhs> lst = mhsDao.getAll();
        MMhs ms = new MMhs();
        for (MMhs itm : lst){
            System.out.println(itm.getIdMhs() + "\t" + itm.getNamaMhs() + "\t" + itm.getAngkatan());
            ms = new MMhs();
            ms = itm;
        }
         
        
        
        System.out.println("==================================");
        List<CmsUsers> list = cmsUsersDao.getAll();
    
    }
    
}
