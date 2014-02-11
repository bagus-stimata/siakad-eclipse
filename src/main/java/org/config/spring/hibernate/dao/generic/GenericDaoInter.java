/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao.generic;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author bagus
 */
public interface GenericDaoInter<T, ID extends Serializable> {
        public List<T> getAll() throws HibernateException;
	public T findById(final ID id) throws HibernateException;
	public void save(final T domain) throws HibernateException;
	public void saveOrUpdate(final T domain) throws HibernateException;        
	//public void saveOrUpdateAll(final List<T> domains) throws HibernateException;        
        public void save(final List<T> domains) throws HibernateException;
	public void delete(final T domain) throws HibernateException;
        public void deleteAll(final List<T> domains) throws HibernateException;
	public Long countAll() throws HibernateException;
        public int countByExample(final T exampleInstance) throws HibernateException;
	public List<T> findAll() throws HibernateException;
	public List<T> findByExample(T exampleInstance, String... excludeProperty) throws HibernateException;
        public List<T> findByExample(T exampleInstance) throws HibernateException;
        
        //Customise
        public List<T> findByIdAndDeskripsi(String id, String des) throws HibernateException;

        
}
