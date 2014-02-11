/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author bagus
 */
public class GenericDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDaoInter<T, ID> {
    private final Class<T> persistentClass;
    private Class domainClass;
    
    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
            this.domainClass = (Class) ((ParameterizedType) getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    public void setSuperSessionFactory(SessionFactory sessionFactory) throws HibernateException {
        super.setSessionFactory(sessionFactory);
    }

    public Class getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(Class domainClass) {
        this.domainClass = domainClass;
    }
      
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public List<T> getAll() throws HibernateException {       
            return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
                public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                    Criteria crit = sn.createCriteria(domainClass);
                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return  crit.list();
                }
            });
    }

    //@SuppressWarnings("unchecked")
    //@Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void save(T domain) throws HibernateException {
        getHibernateTemplate().save(domain);
    }
    
    //@SuppressWarnings("unchecked")
    //@Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void saveOrUpdate(T domain) throws HibernateException {
        getHibernateTemplate().saveOrUpdate(domain);
        
    }
    /*
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void saveOrUpdateAll(List<T> domains) throws HibernateException {
        getHibernateTemplate().saveOrUpdateAll(domains);
    }
    * 
    */

   
    //@SuppressWarnings("unchecked")
    //@Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void save(List<T> domains) throws HibernateException {
            for (T domain : domains) {
                    getHibernateTemplate().saveOrUpdate(domain);
            }
    }
    //@SuppressWarnings("unchecked")
    //@Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void delete(T domain) throws HibernateException{
        getHibernateTemplate().delete(domain);
    }
    //@SuppressWarnings("unchecked")
    //@Transactional(propagation = Propagation.SUPPORTS, readOnly=false)
    @Override
    public void deleteAll(List<T> domains) throws HibernateException {
        getHibernateTemplate().deleteAll(domains);
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public Long countAll() throws HibernateException{
            
            List list = getHibernateTemplate().find(
                            "select count(*) from " + domainClass.getName() + " x");
            Long count = (Long) list.get(0);
            return count;
             
            
            /*
            DetachedCriteria crit = DetachedCriteria.forClass(domainClass);
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            long count = getHibernateTemplate().findByCriteria(crit).size();
            return count;
            * 
            */
            
    }
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public int countByExample(T exampleInstance) throws HibernateException{
            Criteria crit = getSession().createCriteria(domainClass);
            Example example = Example.create(exampleInstance);
            crit.add(example);
            return crit.list().size();
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public List<T> findAll() throws HibernateException {
        /*
            return getHibernateTemplate().executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                //Menggunakan teknik yang agak laen
                    //Criteria crit = sn.createCriteria(domainClass);
                    //crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    //return  crit.list();
                    
                    DetachedCriteria crit = DetachedCriteria.forClass(domainClass);
                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
                    
                }
            });
            * 
            */
                    //Kalau pake get hibernate template berarti bisa multi tasking dao >> Sama dengan statement diatas
                    DetachedCriteria crit = DetachedCriteria.forClass(domainClass);
                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public List<T> findByExample(T exampleInstance, String... excludeProperty) throws HibernateException {
            Criteria crit = getSession().createCriteria(domainClass);
            Example example = Example.create(exampleInstance);
            for (String exclude : excludeProperty) {
                    example.excludeProperty(exclude);
            }
            crit.add(example);
            return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public List<T> findByExample(T exampleInstance) throws HibernateException {
            Criteria crit = getSession().createCriteria(domainClass);
            Example example = Example.create(exampleInstance);
            crit.add(example);
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly=true)
    @Override
    public T findById(ID id) throws HibernateException {
        //Kalau pake get hibernate template berarti bisa multi tasking dao
        final T result = (T) getHibernateTemplate().get(domainClass, id);  
        return result;
    }

    @Override
    public List<T> findByIdAndDeskripsi(String id, String des) throws HibernateException {
                    
                    DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                    String id_table = "id";
                    String des_table = "deskripsi";
                    String id_field = "id";
                    String des_field = "deskripsi";
                    
                        
                    for (Field field: getDomainClass().getDeclaredFields()){
                        Column col;
                        if ((col = field.getAnnotation(Column.class)) != null){
                            if (col.name().contains("id_")) {
                                id_table = col.name().trim();
                                id_field = field.getName().trim();
                                //System.out.println(col.name() + "\t" +  col.table());
                            } else if (col.name().contains("kode_")) {
                                id_field = col.name().trim();
                                id_field = field.getName().trim();
                                //System.out.println(col.name());                            
                            }
                            
                            if (col.name().contains("deskripsi")){
                                des_field =  col.name();
                                des_field = field.getName().trim();
                                //System.out.println(col.name());
                            } else if (col.name().contains("nama_")){
                                des_field =  col.name();
                                des_field = field.getName().trim();
                                //System.out.println(col.name());
                            } else if (col.name().contains("descript")){
                                des_field =  col.name();
                                des_field = field.getName().trim();
                                //System.out.println(col.name());
                            } 
                            
                        }
                    }

                    crit.add(Restrictions.and(Restrictions.like(id_field, id), Restrictions.like(des_field, des)));
                    
                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
       
    }

 
    
}
