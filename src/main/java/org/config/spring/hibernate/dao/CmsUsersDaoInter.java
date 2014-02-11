/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.CmsUsers;
import org.hibernate.HibernateException;

/**
 *
 * @author yhawin
 */
public interface CmsUsersDaoInter extends GenericDaoInter<CmsUsers, Serializable> {
    List<CmsUsers> findByIdFullName(String id, String fullName) throws HibernateException;
    
}
