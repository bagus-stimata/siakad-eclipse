/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.KrsDetailDaoInter;
import org.config.spring.hibernate.model.TKrsDetail;
import org.config.spring.hibernate.model.TKrsJadwal;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestJadwal {
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        
        
        KrsDetailDaoInter krsDetailsDao = (KrsDetailDaoInter) appContext.getBean("KrsDetailDaoBean"); 
    
        TKrsJadwal krsJadwal = new TKrsJadwal();
        
        List<TKrsDetail> list = krsDetailsDao.getAll() ;
        for (TKrsDetail item: list) {
            System.out.println(item.gettKrsDetailPK().getIdKrs() + "\t" + item.gettKrsDetailPK().getIdMatkul());
        
        }
        
        
    }
}
