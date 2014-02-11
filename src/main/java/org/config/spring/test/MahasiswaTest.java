/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.ArrayList;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.*;
import org.config.spring.hibernate.model.*;
import org.springframework.context.ApplicationContext;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author bagus
 */
public class MahasiswaTest {
   
    
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        MhsDaoInter mhsDao = (MhsDaoInter) appContext.getBean("MhsDaoBean");
        CmsUsersDaoInter cmsUsersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
        
        
        KrsDetailDaoInter krsDetailDao = (KrsDetailDaoInter) appContext.getBean("KrsDetailDaoBean");
        List<TKrsDetail> listKrsDetails = krsDetailDao.getAll();
        
        MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
        List<MMatkul> listMatkul = matkulDao.getAll();
        List<MMatkul> listMatkulMir = new ArrayList<MMatkul>(listMatkul);
        List<CmsUsers> list = new ArrayList<CmsUsers>();
        list = cmsUsersDao.getAll();
        for (CmsUsers item: list) {
            System.out.println(item.getIdUser() + "\t" + item.getFullName());
            
        }
        
        
        List<MMhs> lst = new ArrayList<MMhs>();        
        lst = mhsDao.getAll();
        for (MMhs itm: lst) {
            System.out.println(itm.getIdMhs() + "\t" + itm.getNamaMhs());
        }
        
        
    
        
    
    }    
}
