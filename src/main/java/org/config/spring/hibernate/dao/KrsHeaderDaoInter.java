/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.config.spring.hibernate.dao.generic.GenericDaoInter;
import org.config.spring.hibernate.model.TKrsHeader;

/**
 *
 * @author yhawin
 */
public interface KrsHeaderDaoInter extends GenericDaoInter<TKrsHeader, Serializable> {
    public List<TKrsHeader> findByPegThnGGApproveSudahTolak(String idpegawai, Integer tahunajaran, Boolean genapganjil, Integer belumsudahapprove, Integer tolaksetuju, String idmhs, String namamhs );
    
}
