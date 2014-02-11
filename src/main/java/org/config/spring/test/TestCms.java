/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.ArrayList;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsUsersDaoInter;
import org.config.spring.hibernate.model.*;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestCms {

    public static void main(String[] args) {

        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        CmsUsersDaoInter usersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");

        List<CmsUsers> lst = usersDao.findAll();
        List<CmsUserMenu> list = new ArrayList<CmsUserMenu>(lst.get(0).getCmsUserMenuSet());
        for (CmsUserMenu item: list) {
            System.out.println(item.getCmsMenu().getIdMenu() + "\t" + item.getCmsMenu().getTitle() + "\t" + item.getCmsMenu().getOrderIndex());
        }
        
        System.out.println("============================");
        List<CmsUserMenu> listCmsUserMenu = new ArrayList<CmsUserMenu>(list);
       /*
        for (int i=0; i<list2.size()-1;i++) {
            for (int j=0; j<list2.size()-1;j++) {
                if (list2.get(j).getCmsMenu().getOrderIndex() > list2.get(j+1).getCmsMenu().getOrderIndex() ) {
                    CmsUserMenu temp = new CmsUserMenu();
                    temp = list2.get(j);
                    list2.set(j, list2.get(j+1));
                    list2.set(j+1, temp);
                    
                }
            }
        }
        * 
        */
        
        int k;
        for (int i=0; i<listCmsUserMenu.size()-1;i++){
            k=i;
            for (int j=i+1; j<listCmsUserMenu.size(); j++) {
                //ORDER ASC
                if (listCmsUserMenu.get(j).getCmsMenu().getOrderIndex()==null) {
                    k=j; //NULL LANGSUNG DIPINDAH 
                    System.out.println("hehehehe");
                } 
                
                else {
                    if (listCmsUserMenu.get(k).getCmsMenu().getOrderIndex() > listCmsUserMenu.get(j).getCmsMenu().getOrderIndex()) {
                        k = j;
                    }
                }
                 
                
                
            }
            CmsUserMenu temp = new CmsUserMenu();
            temp = listCmsUserMenu.get(i);
            listCmsUserMenu.set(i, listCmsUserMenu.get(k));
            listCmsUserMenu.set(k, temp);
        }
        for (CmsUserMenu item: listCmsUserMenu) {
            System.out.println(item.getCmsMenu().getIdMenu() + "\t" + item.getCmsMenu().getTitle() + "\t" + item.getCmsMenu().getOrderIndex());
        }
    }
    
    
    
}
