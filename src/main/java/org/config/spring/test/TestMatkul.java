/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.ArrayList;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.MatkulDaoInter;
import org.config.spring.hibernate.dao.MatkulKelDaoInter;
import org.config.spring.hibernate.dao.MatkulKurDaoInter;
import org.config.spring.hibernate.model.MMatkul;
import org.config.spring.hibernate.model.MMatkulKel;
import org.config.spring.hibernate.model.MMatkulKur;
import org.config.spring.hibernate.model.TKrsJadwal;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class TestMatkul {
    public static void main(String [] args){
    
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
        MatkulKurDaoInter matkulKurDao = (MatkulKurDaoInter) appContext.getBean("MatkulKurDaoBean");
      
        List<MMatkul> lst = matkulDao.getAll();
        System.out.println(lst.size());
       
        MMatkul matkul = new MMatkul();
        int x = 0;
        for (MMatkul itm: lst){
            System.out.println(itm.getIdMatkul() + "\t" + itm.getNamaMatkul() + 
                    "\t" + itm.getMatkulKur().getIdKurikulum());
            List<TKrsJadwal> lst2 = new ArrayList<TKrsJadwal>(itm.getTkrsJadwalSet());
            if (lst2.size() > 0 ){
                for (TKrsJadwal itm2: lst2){
                    System.out.println("\t" + itm2.getIdJadwal() + "\t" + itm2.getNamaKelas() + "\t" + itm2.getTahunAjaran());
                    //lst.get(x).getTkrsJadwalSet().remove(itm2);
                    
                }        
            }
            x=x+1;
        }
        
        System.out.println("==================================");
        MMatkul ex = new MMatkul();
        ex.setIdMatkul("MK%");
        TKrsJadwal jd = new TKrsJadwal();
        jd.setSmtGenapGasal(Boolean.TRUE);
        MMatkulKur kur = new MMatkulKur();
        kur.setIdKurikulum("MKKB");
        
        ex.setMatkulKur(kur);
        
        
        List<MMatkul> list = matkulDao.findByExample(ex);

        for (MMatkul itm: list){
            System.out.println(itm.getIdMatkul() + "\t" + itm.getNamaMatkul() + 
                    "\t" + itm.getMatkulKur().getIdKurikulum() + "\t");
            List<TKrsJadwal> list2 = new ArrayList<TKrsJadwal>(itm.getTkrsJadwalSet());
            if (list2.size() > 0 ){
                for (TKrsJadwal itm2: list2){
                    System.out.println("\t" + itm2.getIdJadwal() + "\t" + itm2.getNamaKelas() + "\t" + itm2.getTahunAjaran() + "\t" + itm2.getSmtGenapGasal());
                    
                }        
            }
        }
        
        
        Double dbl = 3.2;
        System.out.println(dbl);
        Integer t = dbl.intValue();
        System.out.println(t);
        
        
    }
    
}
