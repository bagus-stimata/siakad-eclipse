/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.TKrsDetail;
import org.hibernate.HibernateException;

/**
 *
 * @author yhawin
 */
public interface KrsDetailDaoInter extends GenericDaoInter<TKrsDetail, Serializable> {
    List<TKrsDetail> findByIdMatIdMhsLulus(TKrsDetail m) throws HibernateException;
    
}
