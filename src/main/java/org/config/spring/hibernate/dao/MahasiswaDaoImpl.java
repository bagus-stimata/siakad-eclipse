/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.sql.SQLException;
import java.util.List;
import org.config.spring.hibernate.model.Mahasiswa;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author bagus
 */
public class MahasiswaDaoImpl extends HibernateDaoSupport implements MahasiswaDao{
    @Autowired
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<Mahasiswa> getAll() {
       return getHibernateTemplate().executeFind(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                Query queryOb = sn.createQuery("FROM Mahasiswa");
                return queryOb.list();
            }
        });
  
    }
    
    @Override
    public List<Mahasiswa> getAll(Mahasiswa mhs) {
        String nim = mhs.getNim()==null? "":mhs.getNim().trim();
        String nama = mhs.getNama()==null? "":mhs.getNama().trim();
        
       Session sn= getSessionFactory().openSession();
       Criteria crit = sn.createCriteria(Mahasiswa.class)
               .add(Restrictions.and(
               Restrictions.like("nim", nim), Restrictions.like("nama", nama)));
       return crit.list();
    }
    
    @Override
    public void addObject(Mahasiswa object) {
       getHibernateTemplate().saveOrUpdate(object);
       
    }

    @Override
    public void updateObject(Mahasiswa object) {
        getHibernateTemplate().saveOrUpdate(object);
    }

    @Override
    public void deleteObject(String ID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteObject(Mahasiswa object) {
        getHibernateTemplate().delete(object);
    }

}
