/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.TKrsHeader;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class KrsHeaderDaoImpl extends GenericDaoImpl<TKrsHeader, Serializable> implements KrsHeaderDaoInter{

    @Override
    public List<TKrsHeader> findByPegThnGGApproveSudahTolak(String idpegawai, Integer tahunajaran, Boolean genapganjil, 
                                Integer belumsudahapprove, Integer tolaksetuju, String idmhs, String namamhs) {
        DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
        
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(crit);
    }
    
    public static void main(String [] args){
        ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
        KrsHeaderDaoInter krsHeaderDao = (KrsHeaderDaoInter) appContext.getBean("KrsHeaderBean");
        String idpegawai = "DSN02";
        Integer tahunAjaran = 2013;
        Boolean genapganjil = false;
        Integer belumsudahapprove = 0; //0.Belum
        Integer tolaksetuju=0;//0.tolak
        String idmhs="%";
        String namamhs="%";
        
        List<TKrsHeader> lst = krsHeaderDao.findByPegThnGGApproveSudahTolak(idpegawai, 
                tahunAjaran, genapganjil, belumsudahapprove, tolaksetuju, idmhs, namamhs);
        for (TKrsHeader itm: lst) {
            
        }
        
    }
}
