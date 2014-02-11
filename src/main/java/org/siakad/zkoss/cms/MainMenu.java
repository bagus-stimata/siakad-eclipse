/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.cms;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsUsersDaoInter;
import org.config.spring.hibernate.model.CmsUserMenu;
import org.config.spring.hibernate.model.CmsUserSubmenu;
import org.config.spring.hibernate.model.CmsUsers;
import org.springframework.context.ApplicationContext;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author bagus
 */
public class MainMenu {
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    CmsUsersDaoInter usersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
    
    private String cmsMenuActive;
    private String cmsContentPageActive;

    //untuk userotentikasi dan cookie
    private String userid;
    private String pass;
    private boolean checkTetapSignIn;
    private boolean checkIngatSaya;
    String sudahLogin;
    
    CmsUsers cmsUsers;
    
    private CmsUsers user;
    List<CmsUserMenu> listCmsUserMenu;
    List<CmsUserSubmenu> listCmsUserSubmenu;
        
    public MainMenu() {
    }

    @Init
    public void init(){       
        boolean paksaLoginLagi =true;
        //Ambil Cookie Jika Ada dilokasi yang telah ditentukan dan LOAD duluan
        //Jika tidak ada cookie atau pasangan cookie username dan password salah maka dipaksa ke menu login
                
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
        

        //Syarat untuk bisa mengakses halaman ini tanpa harus login adalah
        //1. Session login = "Y" atau -->Cookie TetapSignIn=true (dilakukan secara otomatis)--> Inget Saya dilakukan oleh login.htm
        //2. Cookie username dan password sudah diverifikasi dan benar
       
        
        //Jika tidak dipaksa ke halaman login (Sementara seperti ini)
                //Cek Username dan password cookie dari database
                //1. Jika benar maka tampilkan data pengguna
                //2. Jika salah maka paksa untuk menuju ke menu login
            
            HttpSession sess = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getSession();
            sudahLogin =  (String) sess.getAttribute("login");
            if (sudahLogin !=null) {
                if (sudahLogin.equals("Y")) { //Jika sudah login masih harus diperiksa lagi username dan password
                        if (getUserid() != null) {
                            if (! getUserid().trim().equals("")) {
                                user=usersDao.findById(getUserid());
                                if (user != null) {
                                    if (user.getPassword().equals(getPass())) {                        
                                        //Username ada dan Password Benar --> Tampilkan identitas pengguna dan menu pengguna
                                        //INI YANG VITAL
                                        paksaLoginLagi=false; //Supaya tidak diredirect ke page login.htm
                                    } 

                                } 
                            } 
                        }
                
                
                }
            }
           
        
            if (paksaLoginLagi==true) {
                    //Username ada tapi password SALAH
                    //HttpServletResponse resp = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
                    //((HttpServletResponse)Executions.getCurrent().getNativeResponse()).sendRedirect("login.htm");
                Executions.sendRedirect(Executions.encodeURL("http://localhost:8084/siakad-zkoss/login.htm"));
                     

            }
            
            
        
            //Init Constructor
             cmsUsers = usersDao.findById(userid);
             listCmsUserMenu = new ArrayList<CmsUserMenu>(cmsUsers.getCmsUserMenuSet());
             if (listCmsUserMenu.size() >0) {
                listCmsUserSubmenu = new ArrayList<CmsUserSubmenu>(listCmsUserMenu.get(0).getCmsUserSubmenuSet());
                setCmsMenuActive(listCmsUserMenu.get(0).getCmsMenu().getTitle());
             } else { //Jika tidak punya hak akses
             }
        

    }
    
    @AfterCompose
    public void afterCompose(){
    }

    public CmsUsers getCmsUsers() {
        return cmsUsers;
    }

    public void setCmsUsers(CmsUsers cmsUsers) {
        this.cmsUsers = cmsUsers;
    }

    public List<CmsUserMenu> getListCmsUserMenu() {
        //PENGURUTAN MANUAL      
        if (listCmsUserMenu != null) {
            int k;
            for (int i=0; i<listCmsUserMenu.size()-1;i++){
                k=i;                
                for (int j=i+1; j<listCmsUserMenu.size(); j++) {
                    //ORDER ASC                    
                    if (listCmsUserMenu.get(k).getCmsMenu().getOrderIndex()==null || 
                            listCmsUserMenu.get(j).getCmsMenu().getOrderIndex()==null) {
                        //k=j; //NULL LANGSUNG DIPINDAH DAN UNTUK MENCEGAH ERROR KARENA NULL
                    } else {                        
                        if (listCmsUserMenu.get(k).getCmsMenu().getOrderIndex() > listCmsUserMenu.get(j).getCmsMenu().getOrderIndex()) {
                            k = j;
                        }
                    }
                     
                    
                }                
                CmsUserMenu temp = new CmsUserMenu();
                temp = listCmsUserMenu.get(i);
                listCmsUserMenu.set(i, listCmsUserMenu.get(k));
                listCmsUserMenu.set(k, temp);
               
            }
        }
        
        return listCmsUserMenu;
    }

    public void setListCmsUserMenu(List<CmsUserMenu> listCmsUserMenu) {
        this.listCmsUserMenu = listCmsUserMenu;
    }

    public List<CmsUserSubmenu> getListCmsUserSubmenu() {
        //PENGURUTAN MANUAL       
        if (listCmsUserSubmenu !=null) {
            int k;
            for (int i=0; i<listCmsUserSubmenu.size()-1;i++){
                k=i;
                for (int j=i+1; j<listCmsUserSubmenu.size(); j++) {
                    //ORDER ASC
                    if ( listCmsUserSubmenu.get(k) .getCmsSubmenu().getOrderIndex()==null || 
                            listCmsUserSubmenu.get(j).getCmsSubmenu().getOrderIndex()==null) {
                        //k=j; //NULL LANGSUNG DIPINDAH DAN UNTUK MENCEGAH ERROR KARENA NULL
                    } else {
                        if (listCmsUserSubmenu.get(k).getCmsSubmenu().getOrderIndex() > listCmsUserSubmenu.get(j).getCmsSubmenu().getOrderIndex()) {
                            k = j;
                        }
                    }
                }
                CmsUserSubmenu temp = new CmsUserSubmenu();
                temp = listCmsUserSubmenu.get(i);
                listCmsUserSubmenu.set(i, listCmsUserSubmenu.get(k));
                listCmsUserSubmenu.set(k, temp);
            }
        }
        
        return listCmsUserSubmenu;
    }

    public void setListCmsUserSubmenu(List<CmsUserSubmenu> listCmsUserSubmenu) {
        this.listCmsUserSubmenu = listCmsUserSubmenu;
    }
       
 
    @NotifyChange({"listCmsUserSubmenu","cmsMenuActive", "cmsContentPageActive"})
    @Command("mainMenuClick")
    public void mainMenuClick(@BindingParam("param1") String param){
       
        int intParam=Integer.parseInt(param);
        int i=0;
        for (i=0; i<listCmsUserMenu.size();i++){
            if (listCmsUserMenu.get(i).getCmsUserMenuPK().getIdMenu() ==intParam) break;
        }        
            listCmsUserSubmenu = new ArrayList<CmsUserSubmenu>(listCmsUserMenu.get(i).getCmsUserSubmenuSet());
            //setCmsMenuActive(listCmsMenu.get(i).getTitle());        
            setCmsMenuActive(listCmsUserMenu.get(i).getCmsMenu().getTitle());
            if (listCmsUserMenu.get(i).getCmsMenu().getContentUrl() !=null){
                if (listCmsUserMenu.get(i).getCmsMenu().getContentUrl().trim().length()>0)
                    setCmsContentPageActive(listCmsUserMenu.get(i).getCmsMenu().getContentUrl());
            }
        
    }
    
    @NotifyChange({"cmsContentPageActive"})
    @Command("subMenuClick")
    public void subMenuClick(@BindingParam("param1") String param){
       
        int intParam=Integer.parseInt(param);
        int i=0;
        for (i=0; i<listCmsUserSubmenu.size();i++){
            if (listCmsUserSubmenu.get(i).getCmsUserSubmenuPK().getIdSubmenu() ==intParam) break;
        }        
        setCmsContentPageActive(listCmsUserSubmenu.get(i).getCmsSubmenu().getContentUrl());
        
    }
    
    @Command("signOut")
     public void signOut(){
        //Ciptakan Session Bahwa sedang logout
        HttpSession session = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getSession();
        session.setAttribute("login", "N");
        session.setMaxInactiveInterval(60*60*24*1);

        //Sebagai penunjuk tetap sign in atau tidak ini yang akan diubah-ubah :: Membuat cookie TetapSignIn="NO" sebelum diredirect keluar
        Cookie cookieTetapSignIn = new Cookie("CookieTetapSignIn", "N");
        //cookiePass.setMaxAge(60*60*24*30); //Maximal age 30 hari
        //Lokasi Untuk menaruh cookie
        String cp=Executions.getCurrent().getContextPath();
        if (cp.length()==0) cp="/";       

        cookieTetapSignIn.setPath(cp);
        ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookieTetapSignIn);
        
        //Session Login jadi false
        //Keluar menuju login
        Executions.sendRedirect(Executions.encodeURL("login.htm"));
            
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public CmsUsers getUser() {
        return user;
    }

    public void setUser(CmsUsers user) {
        this.user = user;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isCheckIngatSaya() {
        return checkIngatSaya;
    }

    public void setCheckIngatSaya(boolean checkIngatSaya) {
        this.checkIngatSaya = checkIngatSaya;
    }

    public boolean isCheckTetapSignIn() {
        return checkTetapSignIn;
    }

    public void setCheckTetapSignIn(boolean checkTetapSignIn) {
        this.checkTetapSignIn = checkTetapSignIn;
    }

    public String getCmsMenuActive() {
        return cmsMenuActive;
    }

    public void setCmsMenuActive(String cmsMenuActive) {
        this.cmsMenuActive = cmsMenuActive;
    }

    public String getCmsContentPageActive() {
        return cmsContentPageActive;
    }

    public void setCmsContentPageActive(String cmsContentPageActive) {
        this.cmsContentPageActive = cmsContentPageActive;
    }
    
    
}
