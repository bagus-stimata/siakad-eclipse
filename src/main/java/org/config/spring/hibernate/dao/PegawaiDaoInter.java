/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.MPegawai;
import org.config.spring.hibernate.model.TKrsHeader;
import org.hibernate.HibernateException;

/**
 *
 * @author yhawin
 */
public interface PegawaiDaoInter extends GenericDaoInter<MPegawai, Serializable> {
      public List<MPegawai> findByManualCriteria(MPegawai object) throws HibernateException;
      public List<MPegawai> findByPegThnGGApproveSudahTolak(String idpegawai, Integer tahunajaran, Boolean genapganjil, Integer belumsudahapprove, Integer tolaksetuju, String idmhs, String namamhs );
      public List<MPegawai> findByPegThnGGApproveSudahTolak2(String idpegawai, Integer tahunajaran, Boolean genapganjil, Integer belumsudahapprove, Integer tolaksetuju, String idmhs, String namamhs );
      
}
