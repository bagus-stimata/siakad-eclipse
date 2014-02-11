/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.MMhs;
import org.config.spring.hibernate.model.MPegawai;
import org.config.spring.hibernate.model.TKrsDetail;
import org.config.spring.hibernate.model.TKrsHeader;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yhawin
 */
public class PegawaiDaoImpl extends GenericDaoImpl<MPegawai, Serializable> implements PegawaiDaoInter{

    @Override
    public List<MPegawai> findByManualCriteria(MPegawai m) throws HibernateException {
        
                    DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                    
                    crit.add(Restrictions.and(Restrictions.like("idPegawai", m.getIdPegawai()),
                            Restrictions.and(Restrictions.like("nip", m.getNip()),
                            Restrictions.and(Restrictions.like("nidn", m.getNidn()), 
                            Restrictions.like("namaPegawai", m.getNamaPegawai())) ) ));
                    
                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
    }

    
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)    
    @Override
    public List<MPegawai> findByPegThnGGApproveSudahTolak(final String idpegawai, final Integer tahunajaran, final Boolean genapganjil, 
                                final Integer belumsudahapprove, final Integer tolaksetuju, final String idmhs, final String namamhs) {
        
        DetachedCriteria crit = DetachedCriteria.forClass(MPegawai.class);
        crit.createCriteria("anakWaliSet").add(Restrictions.like("idMhs", "001"));
        //crit.add(Restrictions.like("anakWaliSet.idMhs", "001"));
        
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<MPegawai> list = getHibernateTemplate().findByCriteria(crit);
        
        /*
        Session sn = getSessionFactory().openSession();
        Criteria crit = sn.createCriteria(getDomainClass());
        * 
        */
        
        //crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return list;       
        
    }
    
    @Override
    public List<MPegawai> findByPegThnGGApproveSudahTolak2(final String idpegawai, final Integer tahunajaran, final Boolean genapganjil, 
                                final Integer belumsudahapprove, final Integer tolaksetuju, final String idmhs, final String namamhs) {
            Session sn = getSessionFactory().openSession();
            Query q = sn.createQuery("SELECT p FROM MPegawai p JOIN p.MMhs a JOIN a.krsHeaderSet b WHERE (p.idPegawai = :parIdPegawai) "
                    + "AND (a.idMhs = :parIdMhs) AND (b.thnAkademik = :parThnAkademik) ")
                    .setParameter("parIdPegawai", idpegawai)
                    .setParameter("parIdMhs", idmhs)
                    .setParameter("parThnAkademik", tahunajaran);                    
            return  q.list();                       
    }
    
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        PegawaiDaoInter pegawaiDao = (PegawaiDaoInter) appContext.getBean("PegawaiDaoBean");
        
        String idpegawai = "DSN02";
        Integer tahunAjaran = 2014;
        Boolean genapganjil = false;
        Integer belumsudahapprove = 0;  //0.Belum
        Integer tolaksetuju=0;          //0.tolak
        String idmhs="123";
        String namamhs="%";
        
        List<MPegawai> lst = pegawaiDao.findByPegThnGGApproveSudahTolak(idpegawai, 
                tahunAjaran, genapganjil, belumsudahapprove, tolaksetuju, idmhs, namamhs);
        
        for (MPegawai wali: lst){
                System.out.println(wali.getIdPegawai() + "\t" + wali.getNamaPegawai() + "\t" + wali.getAnakWaliSet().size());

                List<MMhs> listMhs = new ArrayList<MMhs>(wali.getAnakWaliSet());
                for (MMhs itemMhs: listMhs) {
                        System.out.println("\t" + itemMhs.getIdMhs() + "\t" + itemMhs.getNamaMhs());
                        
                        List<TKrsHeader> listKrsHeader=new ArrayList<TKrsHeader>(itemMhs.getKrsHeaderSet());
                        for (TKrsHeader itemKrsHeader: listKrsHeader) {
                                System.out.println("\t\t" + itemKrsHeader.getIdKrs() + "\t" + itemKrsHeader.getThnAkademik() + "\t" 
                                        + (itemKrsHeader.getSmtGenapGasal()==false?"Smt Genap":"Smt Ganjil") );

                                List<TKrsDetail> listKrsDetail = new ArrayList<TKrsDetail>(itemKrsHeader.getKrsDetailSet());
                                for (TKrsDetail itemKrsDetail: listKrsDetail) {
                                    System.out.println("\t\t\t" + itemKrsDetail.getMatkul().getIdMatkul() + "\t" + itemKrsDetail.getMatkul().getNamaMatkul() +
                                            "\t" + itemKrsDetail.getDisetujuiWali() );
                                }
                        }
                         
                        
                }
        }
    }
        

    
    
}
