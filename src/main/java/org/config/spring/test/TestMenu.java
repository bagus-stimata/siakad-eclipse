/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsUserMenuDaoInter;
import org.config.spring.hibernate.model.CmsUserMenu;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestMenu {
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        CmsUserMenuDaoInter cmsUserMenuDao = (CmsUserMenuDaoInter) appContext.getBean("CmsUserMenuDaoBean");
        
        
    }
}
