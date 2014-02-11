/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.CmsUsers;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yhawin
 */
public class CmsUsersDaoImpl extends GenericDaoImpl<CmsUsers, Serializable> implements CmsUsersDaoInter{

    @Override
    public List<CmsUsers> findByIdFullName(String id, String fullName) throws HibernateException {
        
        DetachedCriteria crit = DetachedCriteria.forClass(CmsUsers.class);
        crit.add(Restrictions.and(Restrictions.like("idUser", id), Restrictions.like("fullName", fullName)));
        
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return getHibernateTemplate().findByCriteria(crit);
    }
    
}
