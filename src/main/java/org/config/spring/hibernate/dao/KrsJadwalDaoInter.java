/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.TKrsJadwal;
import org.hibernate.HibernateException;

/**
 *
 * @author yhawin
 */
public interface KrsJadwalDaoInter extends GenericDaoInter<TKrsJadwal, Serializable> {
       public List<TKrsJadwal> findByManualCriteria(TKrsJadwal m) throws HibernateException;
       public List<TKrsJadwal> findByIdJadIdMatIdPegThnHariGenapGasal(TKrsJadwal m) throws HibernateException;
}
