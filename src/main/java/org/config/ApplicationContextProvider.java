/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.config;

import java.io.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Saiful Bahri
 * blog    : http://bahrie27.wordpress.com/
 * twitter : @bahrie127
 */
public class ApplicationContextProvider {

    private ApplicationContext applicationContext;
    private ApplicationContext applicationContextHsql;

    private static ApplicationContextProvider provider;

    private ApplicationContextProvider() throws ExceptionInInitializerError {
        try {
            this.applicationContext = new ClassPathXmlApplicationContext("org" + File.separator + "config" 
                    + File.separator + "spring" + File.separator + "ApplicationContext.xml");
        } catch (Throwable ex) {
            System.err.print("error " + ex);
            //throw new ExceptionInInitializerError(ex);
        }
    }

    public synchronized static ApplicationContextProvider getInstance() throws ExceptionInInitializerError {
        ApplicationContextProvider tempProvider = null;
        if (provider == null) {
            provider = new ApplicationContextProvider();
            tempProvider = provider;
        }else if(provider.getApplicationContext()==null){
            provider=new ApplicationContextProvider();
            tempProvider=provider;
        }else{
            tempProvider=provider;
        }

        return tempProvider;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContextHsql() {
        return applicationContextHsql;
    }

    public void setApplicationContextHsql(ApplicationContext applicationContextHsql) {
        this.applicationContextHsql = applicationContextHsql;
    }
    
    
}
