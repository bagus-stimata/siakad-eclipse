/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.MMatkul;
import org.config.spring.hibernate.model.MMhs;
import org.hibernate.HibernateException;

/**
 *
 * @author yhawin
 */
public interface MhsDaoInter extends GenericDaoInter<MMhs, Serializable> {
      public List<MMhs> findByManualCriteria(MMhs m) throws HibernateException;
     
    
}
