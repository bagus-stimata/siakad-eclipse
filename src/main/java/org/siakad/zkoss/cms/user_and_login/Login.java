/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.cms.user_and_login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsUsersDaoInter;
import org.config.spring.hibernate.model.CmsUsers;
import org.springframework.context.ApplicationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author yhawin
 */
public class Login {
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    CmsUsersDaoInter usersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
   // CmsUsersModulesInter usersModulesDao = (CmsUsersModulesInter) appContext.getBean("CmsUsersModulesDao");
    
    private String userid;
    private String pass;
    private boolean checkTetapSignIn;
    private boolean checkIngatSaya;
    private CmsUsers user;
    
    @Init
    public void init() {
        //Ambil Cookie Jika Ada dilokasi yang telah ditentukan dan LOAD duluan
        Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
        if (cookies != null){
            for (int i=0;i<cookies.length;i++){
                Cookie c = cookies[i];
                if (c.getName().equalsIgnoreCase("CookieUserId")){
                    setUserid(c.getValue());
                } 
                if (c.getName().equalsIgnoreCase("CookiePass")){
                    setPass(c.getValue());
                } 
               
                if (c.getName().equalsIgnoreCase("CookieIngatSaya")) {
                    if (c.getValue().equalsIgnoreCase("Y")) {
                        setCheckIngatSaya(true);
                    } else {
                        setCheckIngatSaya(false);
                    }
                }
                if (c.getName().equalsIgnoreCase("CookieTetapSignIn")) {
                    if (c.getValue().equalsIgnoreCase("Y")) {
                        setCheckTetapSignIn(true);
                    } else {
                        setCheckTetapSignIn(false);
                    }
                }
                
            }
        }
        //Tidak Lanjut dari hasil check cookie
        //1. Jika tidak ingat saya maka hapus cookie username dan password
            if (isCheckIngatSaya()==false) {
                resetCookieUseridPass();
            }
        
        //2. Jika biarkan saya tetap sign-in maka:
            //A. Mencoba untuk redirect login manual dengan username dan password dari cookie
            //B. Jika gagal Maka 
                //Maka tetap di posisi semula (menunggu user mengubah username dan password lalu sign-in)
            
            //Jika tidak posisi tetap signIn atau user id kosong atau null maka tetap diposisi halaman login
            if (isCheckTetapSignIn()==true && getUserid() != null) {
                if (! getUserid().trim().equals("")) {
                    user=usersDao.findById(getUserid());
                    if (user != null) {
                        if (user.getPassword().equals(getPass())) {                        
                            //Username ada dan Password Benar
                            HttpSession session = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getSession();
                            session.setAttribute("login", "Y");
                            session.setMaxInactiveInterval(60*60*24*1);

                            Execution exec = Executions.getCurrent();
                            HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
                            try {
                                response.sendRedirect(response.encodeRedirectURL("index.htm")); 

                            } catch (IOException ex) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //Session Status Login True
                        }

                    }
                }
            } 
            
    }
    public void resetCookieUseridPass(){
        //Untuk User
        Cookie cookieUser =  new Cookie("CookieUserId", "");
        cookieUser.setMaxAge(0); //Maximal age 2 hari
        Cookie cookiePass = new Cookie("CookiePass", "");
        cookiePass.setMaxAge(0); //Maximal age 2 hari

        String cp=Executions.getCurrent().getContextPath();
        if (cp.length()==0) cp="/";       

        cookieUser.setPath(cp);
        ((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(cookieUser);       

        cookiePass.setPath(cp);       
        ((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(cookiePass);
    }
    
    public void simpanCookieUseridPass(){
        //Untuk User
        Cookie cookieUser =  new Cookie("CookieUserId", getUserid());
        cookieUser.setMaxAge(60*60*24*3); //Maximal age 2 hari
        Cookie cookiePass = new Cookie("CookiePass", getPass());
        cookiePass.setMaxAge(60*60*24*3); //Maximal age 2 hari

        String cp=Executions.getCurrent().getContextPath();
        if (cp.length()==0) cp="/";       

        cookieUser.setPath(cp);
        ((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(cookieUser);       

        cookiePass.setPath(cp);       
        ((HttpServletResponse)Executions.getCurrent().getNativeResponse()).addCookie(cookiePass);
    }

    public void simpanCookieIngetSaya(){
        String strIngatSaya="";
        if (isCheckIngatSaya()==true) {
            strIngatSaya = "Y";
        } else { 
            strIngatSaya="N"; 
        }
        Cookie cookieIngatSaya = new Cookie("CookieIngatSaya", strIngatSaya);
        cookieIngatSaya.setMaxAge(60*60*24*30); //Maximal age 30 hari
        
        //Lokasi Untuk menaruh cookie
        String cp=Executions.getCurrent().getContextPath();
        if (cp.length()==0) cp="/";       
        cookieIngatSaya.setPath(cp);
        ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookieIngatSaya);

    }
    
    public void simpanCookieTetapSignIn(){
        //Sebagai penunjuk tetap sign in atau tidak ini yang akan diubah-ubah
        String strTetapSignIn="";
        if (isCheckTetapSignIn()==true) {
            strTetapSignIn = "Y";
        } else { 
            strTetapSignIn="N"; 
        }
        Cookie cookieTetapSignIn = new Cookie("CookieTetapSignIn", strTetapSignIn);
        cookieTetapSignIn.setMaxAge(60*60*24*30); //Maximal age 30 hari

        //Lokasi Untuk menaruh cookie
        String cp=Executions.getCurrent().getContextPath();
        if (cp.length()==0) cp="/";       

        cookieTetapSignIn.setPath(cp);
        ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookieTetapSignIn);
    }
    
    @Command("checkIngatSaya")
    public void checkIngatSaya(){
       simpanCookieIngetSaya();
       //Maka akan disimpan apapun yang ada di username dan password
       if (checkIngatSaya==true) {
            simpanCookieUseridPass();
       } else {
           //Maka username dan password di reset
           resetCookieUseridPass();
       }
    }

    @Command("checkTetapSignIn")
    public void checkTetapSignIn(){
        simpanCookieTetapSignIn();
    }
    
    @AfterCompose
    public void afterCompose(){
    }

    //LOGIN
    @Command("signIn")
    public void signIn(){
           if (getUserid() != null) {
               if (! getUserid().trim().equals("")) {
                    user=usersDao.findById(getUserid());
                    if (user != null && user.getPassword() !=null) { //Makanya tidak boleh ada password yang null --> Atur di pembuatan user
                        if (user.getPassword().equals(getPass())) {                        
                            //Username ada dan Password Benar
                            HttpSession session = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getSession();
                            session.setAttribute("login", "Y");
                            session.setMaxInactiveInterval(60*60*24*1);
                            //Antisipasi
                            simpanCookieIngetSaya();
                            simpanCookieTetapSignIn();
                            simpanCookieUseridPass();
                            
                            Executions.sendRedirect(Executions.encodeURL("index.htm"));
                            //Session Status Login True
                        } else {
                             Messagebox.show("user id dan password tidak cocok!");
                        }

                    } else {
                             Messagebox.show("user id dan password tidak cocok!");                    
                    }
                } else {
                   Messagebox.show("user id tidak boleh kosong!");
                }
            } 

    }
    
    
    
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isCheckTetapSignIn() {
        return checkTetapSignIn;
    }

    public void setCheckTetapSignIn(boolean checkTetapSignIn) {
        this.checkTetapSignIn = checkTetapSignIn;
    }

    public boolean isCheckIngatSaya() {
        return checkIngatSaya;
    }

    public void setCheckIngatSaya(boolean checkIngatSaya) {
        this.checkIngatSaya = checkIngatSaya;
    }

    public CmsUsers getUser() {
        return user;
    }

    public void setUser(CmsUsers user) {
        this.user = user;
    }
    
    
    
}
