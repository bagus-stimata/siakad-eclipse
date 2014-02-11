/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.MMatkul;
import org.config.spring.hibernate.model.MPegawai;
import org.config.spring.hibernate.model.TKrsJadwal;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class KrsJadwalDaoImpl extends GenericDaoImpl<TKrsJadwal, Serializable> implements KrsJadwalDaoInter{

    @Override
    public List<TKrsJadwal> findByManualCriteria(TKrsJadwal m) throws HibernateException {
                    DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                    
                    if (m.getTahunAjaran() ==0 && m.getSmtGenapGasal()==null && m.getHari()==0 && (m.getJamMulai().getIdJam()==0 || m.getJamSelesai().getIdJam()==0)) {
                        crit.add(Restrictions.and(Restrictions.like("tahunAjaran", m.getMatkul().getIdMatkul()),
                                Restrictions.like("hari", m.getPegawai().getIdPegawai())));
                        
                    } else if (m.getSmtGenapGasal()==null && m.getHari()==0 && (m.getJamMulai().getIdJam()==0 || m.getJamSelesai().getIdJam()==0)){
                        crit.add(Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("smtGenapGasal", m.getSmtGenapGasal()),
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()),
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), 
                                Restrictions.between("jamMulai", m.getJamMulai(),m.getJamSelesai())))))));                   
                    
                    } else if (m.getHari()==0 && (m.getJamMulai().getIdJam()==0 || m.getJamSelesai().getIdJam()==0)) {
                        crit.add(Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("smtGenapGasal", m.getSmtGenapGasal()),
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()),
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), 
                                Restrictions.between("jamMulai", m.getJamMulai(),m.getJamSelesai())))))));
                    
                    
                    } else if ((m.getJamMulai().getIdJam()==0 || m.getJamSelesai().getIdJam()==0)){
                        crit.add(Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("smtGenapGasal", m.getSmtGenapGasal()),
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()),
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), 
                                Restrictions.between("jamMulai", m.getJamMulai(),m.getJamSelesai())))))));
                    
                    
                    } else {
                        crit.add(Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("smtGenapGasal", m.getSmtGenapGasal()),
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()),
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), 
                                Restrictions.between("jamMulai", m.getJamMulai(),m.getJamSelesai())))))));
                    
                    
                    }
                    

                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
                    
                    
    }

    @Override
    public List<TKrsJadwal> findByIdJadIdMatIdPegThnHariGenapGasal(TKrsJadwal m) throws HibernateException {
            DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
            if (m.getIdJadwal()==null) m.setIdJadwal(0); //Kriteria IF
            if (m.getMatkul()==null) {
                MMatkul m1 = new MMatkul();
                m1.setIdMatkul("%");
                m.setMatkul(m1);
            }              
            if (m.getPegawai()==null) {
                MPegawai p1 = new MPegawai();
                p1.setIdPegawai("%");
                m.setPegawai(p1);            
            }//Gak tau bisa apa tidak
            
            if (m.getTahunAjaran()==null) m.setTahunAjaran(0); //Kriteria IF
            if (m.getHari()==null) m.setHari(0); //Kriteria IF
            
            if (m.getSmtGenapGasal()==null) { }
            
            if (m.getSmtGenapGasal()==null) {
                    if (m.getIdJadwal().equals(0) && m.getTahunAjaran().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.ge("hari", m.getHari()))))));
                    }  else if (m.getTahunAjaran().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.ge("hari", m.getHari()))))));
                    }  else if (m.getIdJadwal().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.ge("hari", m.getHari()))))));
                    }  else if (m.getIdJadwal().equals(0) && m.getTahunAjaran().equals(0) ) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.eq("hari", m.getHari()))))));
                    }  else if (m.getIdJadwal().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.eq("hari", m.getHari()))))));
                    }  else if (m.getTahunAjaran().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.eq("hari", m.getHari()))))));
                    }  else if (m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.ge("hari", m.getHari()))))));
                    }
            } else {
                     if (m.getIdJadwal().equals(0) && m.getTahunAjaran().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getTahunAjaran().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getIdJadwal().equals(0) && m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getIdJadwal().equals(0) && m.getTahunAjaran().equals(0) ) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.eq("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getIdJadwal().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.ge("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.eq("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getTahunAjaran().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.ge("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.eq("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }  else if (m.getHari().equals(0)) {
                        crit.add(Restrictions.and(Restrictions.eq("idJadwal", m.getIdJadwal()), 
                                Restrictions.and(Restrictions.like("matkul.idMatkul", m.getMatkul().getIdMatkul()), 
                                Restrictions.and(Restrictions.like("pegawai.idPegawai", m.getPegawai().getIdPegawai()), 
                                Restrictions.and(Restrictions.eq("tahunAjaran", m.getTahunAjaran()),
                                Restrictions.and(Restrictions.ge("hari", m.getHari()), Restrictions.eq("smtGenapGasal",m.getSmtGenapGasal())))))));
                    }
           
            }

            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return getHibernateTemplate().findByCriteria(crit);
        
    }
    
    
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        KrsJadwalDaoInter krsJadwalDao =  (KrsJadwalDaoInter) appContext.getBean("KrsJadwalDaoBean");
                
        List<TKrsJadwal> list = krsJadwalDao.getAll();
        for (TKrsJadwal item: list) {
        
        }
    
    }

}
