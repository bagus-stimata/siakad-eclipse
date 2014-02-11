/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.FakultasDaoInter;
import org.config.spring.hibernate.dao.JurusanDaoInter;
import org.config.spring.hibernate.model.MFakultas;
import org.config.spring.hibernate.model.MJurusan;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestFakultas {
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        FakultasDaoInter fakultasDao = (FakultasDaoInter) appContext.getBean("FakultasDaoBean");
        List<MFakultas> list = fakultasDao.getAll();
        
        JurusanDaoInter jurusanDao = (JurusanDaoInter) appContext.getBean("JurusanDaoBean");
        List<MJurusan> lst = jurusanDao.getAll();
    }
}
