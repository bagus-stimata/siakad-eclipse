/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author yhawin
 */
public class SiakadCookie {
    
    public String getCookieUserId(){
        String idUser=null;
        Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
        if (cookies != null){
            for (int i=0;i<cookies.length;i++){
                Cookie c = cookies[i];
                if (c.getName().equalsIgnoreCase("CookieUserId")){
                    idUser = c.getValue();
                } 
                
            }
        }
        return idUser;
    }
    
}
