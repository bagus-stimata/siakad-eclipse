/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config.spring.test;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author yhawin
 */
public class TestBiasa {
    
    public static void main(String [] args) {
        Calendar cal = Calendar.getInstance();
        Date waktu = cal.getTime();
        System.out.println(waktu);
        
        Date waktu2 = new Date();
        System.out.println(waktu2);        
    }
    
}
