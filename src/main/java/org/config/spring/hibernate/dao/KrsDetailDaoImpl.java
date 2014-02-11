/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.TKrsDetail;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author yhawin
 */
public class KrsDetailDaoImpl extends GenericDaoImpl<TKrsDetail, Serializable> implements KrsDetailDaoInter{

    @Override
    public List<TKrsDetail> findByIdMatIdMhsLulus(TKrsDetail m) throws HibernateException {
        DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
        
        
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(crit);
    }
    
    public static void main(String [] args) {
        ApplicationContext appContext  = ApplicationContextProvider.getInstance().getApplicationContext();
        KrsDetailDaoInter krsDetailDao = (KrsDetailDaoInter) appContext.getBean("KrsDetailDaoBean");
        List<TKrsDetail> list = krsDetailDao.getAll();
        for (TKrsDetail item: list) {
            System.out.println(item.getKrsHeader().getIdKrs() + "\t" + item.getKrsHeader().getMhs() +"\t" + "\t" );
        }
        
    }
}
