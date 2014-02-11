/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.MatkulDaoInter;
import org.config.spring.hibernate.dao.MhsDaoInter;
import org.config.spring.hibernate.model.*;
import org.springframework.context.ApplicationContext;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author yhawin
 */
public class CekPrasarat {

    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        MhsDaoInter mhsDao = (MhsDaoInter) appContext.getBean("MhsDaoBean");
        MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
        MMatkul m = new MMatkul();
        m = matkulDao.findById("002");
            //Cek Prasarat
            boolean saratOk1 = false;
            boolean saratOk2 = false;
            boolean saratOk3 = false; //Sarat Jumlah SKS
            
            String saratMatkul1 = "";
            if (m.getPrasarat() !=null)
                if (m.getPrasarat().getIdMatkul() !=null)
                    saratMatkul1 = m.getPrasarat().getIdMatkul();
            
            String saratMatkul2="";
            if (m.getPrasarat2()!=null)
                if (m.getPrasarat2().getIdMatkul() !=null)
                    saratMatkul2 = m.getPrasarat2().getIdMatkul();
            int saratJumlahSks = 0;
            if (m.getPrasaratJumlah() !=null)
                saratJumlahSks = m.getPrasaratJumlah();
            int hitungJumlahSks = 0;
            
            
            
            MMhs cekMhs = new MMhs();
            cekMhs = mhsDao.findById("123");
            System.out.println(cekMhs.getIdMhs()  + "\t" + cekMhs.getNamaMhs());
            
            if (cekMhs !=null) {
                List<TKrsHeader> headers = new ArrayList<TKrsHeader>(cekMhs.getKrsHeaderSet());
                for (TKrsHeader item: headers) {
                    List<TKrsDetail> details = new ArrayList<TKrsDetail>(item.getKrsDetailSet());
                    for (TKrsDetail item2: details) {
                        if (item2.getLulus() ==null) {
                            item2.setLulus(false);
                        }
                        if (item2.getLulus()==true) {
                            hitungJumlahSks = hitungJumlahSks + item2.getMatkul().getSks();
                            
                            if ( saratMatkul1.trim().equals("")){
                                saratOk1=true;
                            }  else if (item2.getMatkul().getIdMatkul().equals(saratMatkul1)) {
                                    saratOk1 = true;
                            } 

                            if (saratMatkul2.trim().equals(""))   { 
                                saratOk2 =true;
                            } else if (item2.getMatkul().getIdMatkul().equals(saratMatkul2)) {
                                    saratOk1 =true;
                            }
                            
                            
                        }
                                                
                    }                        
                    
                }
                                     
                //Prasarat Jumlah   LEBIH BESAR ATAU SAMA DENGAN
                if (hitungJumlahSks >= saratJumlahSks) {
                    saratOk3=true;
                }
                
            }
            
            
            if (saratOk1==true) {
                JOptionPane.showMessageDialog(null,"Sarat Terpenuhi Satu");
            } else {
                JOptionPane.showMessageDialog(null, "Satu Anda belum lulus matkul Kode: " );
            }
            
            if (saratOk2==true) {
                JOptionPane.showMessageDialog(null,"Sarat Terpenuhi DUA");
            } else {
                JOptionPane.showMessageDialog(null, "DUA Anda belum lulus matkul Kode: " );
            }

            if (saratOk3==true) {
                JOptionPane.showMessageDialog(null,"Sarat Terpenuhi TIGA");
            } else {
                JOptionPane.showMessageDialog(null, "TIGA Anda belum lulus matkul Kode: " );
            }
            
            
             
            
            
                
    
    
    }
}
