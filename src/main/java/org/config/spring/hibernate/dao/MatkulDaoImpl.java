/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoImpl;
import org.config.spring.hibernate.model.MMatkul;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author yhawin
 */
public class MatkulDaoImpl extends GenericDaoImpl<MMatkul, Serializable> implements  MatkulDaoInter{

    @Override
    public List<MMatkul> findByManualCriteria(MMatkul m) throws HibernateException {
                    //Mencegah jika terjadi null dsb
                    if (m.getIdMatkul() ==null) {
                        m.setIdMatkul("%");
                    } else if (m.getIdMatkul().equals("")) {
                        m.setIdMatkul("%");
                    }
                    if (m.getNamaMatkul() ==null) {
                        m.setNamaMatkul("%");
                    } else if (m.getNamaMatkul().equals("")) {
                        m.setNamaMatkul("%");
                    }
                    
                    if (m.getSks()==null) {
                        m.setSks(0);
                    }
                    if (m.getSemester()==null) {
                        m.setSemester(0);
                    }
                    
                    if (m.getMatkulKur().getIdKurikulum()==null) {
                        m.getMatkulKur().setIdKurikulum("%");
                    } else if (m.getMatkulKur().getIdKurikulum().equals("")) {
                        m.getMatkulKur().setIdKurikulum("%");                        
                    }
                    
                    DetachedCriteria crit = DetachedCriteria.forClass(getDomainClass());
                    
                    if (m.getSemester()==0 && m.getSks()==0 && m.getStatusAktif() !=null ) {
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.ge("semester", m.getSemester()),
                                Restrictions.and(Restrictions.ge("sks", m.getSks()),
                                Restrictions.and(Restrictions.eq("statusAktif", m.getStatusAktif()), 
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum())))) )));
                        
                    } else if (m.getSemester()==0 && m.getStatusAktif() != null){
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.ge("semester", m.getSemester()),
                                Restrictions.and(Restrictions.eq("sks", m.getSks()),
                                Restrictions.and(Restrictions.eq("statusAktif", m.getStatusAktif()), 
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum())))) )));
                    } else if (m.getSks()==0 && m.getStatusAktif() !=null) {
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.eq("semester", m.getSemester()),
                                Restrictions.and(Restrictions.ge("sks", m.getSks()),
                                Restrictions.and(Restrictions.eq("statusAktif", m.getStatusAktif()), 
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum())))) )));
                    } else if (m.getSemester()==0){                  
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.ge("semester", m.getSemester()),
                                Restrictions.and(Restrictions.eq("sks", m.getSks()),
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum()  ))) )));
                    } else if (m.getSks()==0){                  
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.eq("semester", m.getSemester()),
                                Restrictions.and(Restrictions.ge("sks", m.getSks()),
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum()  ))) )));
                    } else if (m.getSemester()==0 && m.getSks()==0 ){                    
                        crit.add(Restrictions.and(Restrictions.like("idMatkul", m.getIdMatkul()),
                                Restrictions.and(Restrictions.like("namaMatkul", m.getNamaMatkul()),
                                Restrictions.and(Restrictions.ge("semester", m.getSemester()),
                                Restrictions.and(Restrictions.ge("sks", m.getSks()),
                                Restrictions.like("matkulKur.idKurikulum", m.getMatkulKur().getIdKurikulum()  ))) )));
                    }

                    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                    return getHibernateTemplate().findByCriteria(crit);
                    
    }
    
}
