/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.Sysvar;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yhawin
 */
public class SysvarDaoImpl extends GenericDaoImpl<Sysvar, Serializable> implements SysvarDaoInter{

    @Override
    public List<Sysvar> findByManualCriteria(Sysvar m) throws HibernateException {
                DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                crit.add(Restrictions.and(Restrictions.like("idSysvar", m.getIdSysvar()),
                        Restrictions.and(Restrictions.like("groupSysvar", m.getGroupSysvar()),
                        Restrictions.like("deskripsi", m.getDeskripsi() ))));


                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                return getHibernateTemplate().findByCriteria(crit);
    }
    
}
