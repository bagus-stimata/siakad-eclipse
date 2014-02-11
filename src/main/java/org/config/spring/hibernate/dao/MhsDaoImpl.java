/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.MMhs;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author yhawin
 */
public class MhsDaoImpl extends GenericDaoImpl<MMhs, Serializable> implements MhsDaoInter{

    @Override
    public List<MMhs> findByManualCriteria(MMhs m) throws HibernateException {
                    DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                    if (m.getAngkatan()==0) {
                        crit.add(Restrictions.and(Restrictions.like("idMhs", m.getIdMhs()),
                                Restrictions.and(Restrictions.like("namaMhs", m.getNamaMhs()),
                                Restrictions.like("prodi.idProdi", m.getProdi().getIdProdi() )) ));
                    } else {
                        crit.add(Restrictions.and(Restrictions.like("idMhs", m.getIdMhs()),
                                Restrictions.and(Restrictions.like("namaMhs", m.getNamaMhs()),
                                Restrictions.and(Restrictions.ge("prodi.idProdi", m.getProdi().getIdProdi()),
                                Restrictions.eq("angkatan", m.getAngkatan() ))) ));
                    }

                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);

    }

    
}
