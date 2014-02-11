/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.administrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.*;
import org.config.spring.hibernate.model.*;

import org.springframework.context.ApplicationContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author yhawin
 */

public class UserManager extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    CmsUsersDaoInter cmsUsersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
    
    CmsSubMenuDaoInter cmsSubmenuDao = (CmsSubMenuDaoInter) appContext.getBean("CmsSubmenuDaoBean");

    CmsUserMenuDaoInter cmsUserMenuDao = (CmsUserMenuDaoInter) appContext.getBean("CmsUserMenuDaoBean");
    CmsUserSubmenuDaoInter cmsUserSubmenuDao = (CmsUserSubmenuDaoInter) appContext.getBean("CmsUserSubmenuDaoBean");
    
    MhsDaoInter mhsDao =  (MhsDaoInter) appContext.getBean("MhsDaoBean");
    PegawaiDaoInter pegawaiDao = (PegawaiDaoInter) appContext.getBean("PegawaiDaoBean");
    
    //Entity or Variable utama
    private List<CmsUsers> listCmsUsers = new ArrayList<CmsUsers>();  
    private List<CmsUsers> listCmsUsersPilih = new ArrayList<CmsUsers>();
    
    private List<MMhs> listMhs = new ArrayList<MMhs>();
    private List<MPegawai> listPegawai = new ArrayList<MPegawai>();
    
    private List<CmsSubmenu> listCmsSubmenu = new ArrayList<CmsSubmenu>();
    private List<CmsUserSubmenu> listCmsUserSubmenu1 = new ArrayList<CmsUserSubmenu>();
    private List<CmsUserSubmenu> listCmsUserSubmenu11 = new ArrayList<CmsUserSubmenu>();
    private List<CmsUserSubmenu> listCmsUserSubmenu2 = new ArrayList<CmsUserSubmenu>();
    private List<CmsUserSubmenu> listCmsUserSubmenu22 = new ArrayList<CmsUserSubmenu>();
    
    private CmsUsers cmsUsers;
    private CmsUsers cmsUsersPilih;
    private CmsUsers tempCmsUsers; //temp digunakan untuk: Tambah Baru
    private CmsUsers cariCmsUsers = new CmsUsers();
    private CmsUsers cariCmsUsersPilih = new CmsUsers();
    
    private CmsMenu cariCmsMenu;
    private CmsSubmenu cariCmsSubmenu;
    
    //ComboBox dll   
    
    //Entity or Variable Util atau pendukung
    private boolean addNew;
    private int selectedTab=1;
    private Boolean showWindowUsers=false;
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private String imgAktifUrl = "/widgets/images/navigation/12x12/Apply.png";
    private String imgNonAktifUrl = "/widgets/images/navigation/12x12/Problem.png";
    private String imgMaleUrl = "/widgets/images/navigation/12x12/Male-symbol.png";
    private String imgMale2Url = "/widgets/images/navigation/14x14/Male.png";
    private String imgFemaleUrl = "/widgets/images/navigation/12x12/Female-symbol.png";
    private String imgFemale2Url = "/widgets/images/navigation/14x14/Female.png";
    private String imgBlockedUrl = "/widgets/images/navigation/14x14/No.png";

    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
    
    public List<String> getListTipeUser(){
        List<String> lst = new ArrayList<String>();
        lst.add("Others");
        lst.add("Mahasiswa");
        lst.add("Pegawai");
        lst.add("Administrator");
        return lst;
    }
    
    @Init
    public void init(){
        //Inisialisasi isi utama
        listCmsUsers = cmsUsersDao.getAll();
        listCmsUsersPilih = new ArrayList<CmsUsers>(listCmsUsers);
        
        listMhs = mhsDao.getAll();
        listPegawai  = pegawaiDao.getAll();
        
        //Menghindari null value
        cmsUsers = new CmsUsers();
        tempCmsUsers = new CmsUsers();
        cariCmsUsers = new CmsUsers();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listCmsUsers.size() > 0)
            cmsUsers = listCmsUsers.get(0);
        //Mula-mula tombol tambah adalah false
        setAddNew(false);
        
        //Penggunaaanya bersama 
        listCmsSubmenu = cmsSubmenuDao.getAll();
        //Inisialisasi User otentikasi
        reloadListCmsUserSubmenu2();
        reloadListCmsUserSubmenu1();
        
    }
    
    @AfterCompose
    public void afterCompose(){
    }
    public void reloadListCmsUserSubmenu1(){
        listCmsUserSubmenu1 = new ArrayList<CmsUserSubmenu>();
        for (CmsSubmenu itm: listCmsSubmenu) {
            CmsUserSubmenu f = new CmsUserSubmenu();

            CmsUserSubmenuPK fPk = new CmsUserSubmenuPK();
            fPk.setIdMenu(itm.getCmsMenu().getIdMenu());
            fPk.setIdSubmenu(itm.getIdSubmenu());
            fPk.setIdUser(cmsUsers.getIdUser());            
            f.setCmsUserSubmenuPK(fPk);            
            
            f.setCmsSubmenu(itm);
            
           listCmsUserSubmenu1.add(f);
        }
        
        listCmsUserSubmenu1.removeAll(listCmsUserSubmenu2);
        BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenuSelected1");
    }
    
    public void reloadListCmsUserSubmenu2(){
        //Temp target
        listCmsUserSubmenu2 = new ArrayList<CmsUserSubmenu>(cmsUsers.getCmsUserSubmenuSet());
        BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenuSelected2");
    }
    @Command({"tambahGrant"})
    public void tambahGrant(){
        if (listCmsUserSubmenu11.size() >0) {
            listCmsUserSubmenu1.removeAll(listCmsUserSubmenu11);
            listCmsUserSubmenu2.addAll(listCmsUserSubmenu11);
            
            listCmsUserSubmenu11 = new ArrayList<CmsUserSubmenu>(); //Harus dikosongi lagi
            
            BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu1");
            BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu2");
        } else {
            Messagebox.show("Belum ada yang diseleksi");
        }
        
    }
    @Command({"kurangiGrant"})
    public void kurangiGrant(){
        if (listCmsUserSubmenu22.size() >0) {
            listCmsUserSubmenu1.addAll(listCmsUserSubmenu22);
            listCmsUserSubmenu2.removeAll(listCmsUserSubmenu22);
            listCmsUserSubmenu22 = new ArrayList<CmsUserSubmenu>(); //Harus dikosongi lagi

            BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu1");
            BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu2");
        } else {
            Messagebox.show("Belum ada yang diseleksi");
        }
    }
    
    
    
    
    @NotifyChange({"cmsUsers","selectedTab","listCmsUserSubmenu1", "listCmsUserSubmenu2"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") CmsUsers m){
        
        cmsUsers = new CmsUsers();
        cmsUsers =  m;
        reloadListCmsUserSubmenu2();//Karena user berubah maka reload dengan user aktif
        reloadListCmsUserSubmenu1();//Karena user berubah maka reload dengan user aktif
        
        selectedTab=0;
   }
    @NotifyChange({"showWindowUsers"})
    @Command({"copyOtorisasi"})
    public void copyOtorisasi(){
        showWindowUsers = true;
        cmsUsersPilih = new CmsUsers();
    }
    
    @NotifyChange({"showWindowUsers", "listCmsUserSubmenu1", "listCmsUserSubmenu2"})
    @Command({"okPilih"})
    public void okPilih(){
    
        if (cmsUsersPilih.getIdUser() ==null) {
            Messagebox.show("Anda Belum memilih user yang akan dicopy otorisasinya");
        } else {
                cmsUserMenuDao.deleteAll(new ArrayList<CmsUserMenu>(cmsUsers.getCmsUserMenuSet()));
                //cmsUserSubmenuDao.deleteAll(new ArrayList<CmsUserSubmenu>(cmsUsers.getCmsUserSubmenuSet()));
                List<CmsUserSubmenu> listSumber = new ArrayList<CmsUserSubmenu>(cmsUsersPilih.getCmsUserSubmenuSet());
                for (CmsUserSubmenu itm: listSumber) {
                    CmsUserMenu  m = new CmsUserMenu();

                    CmsUserMenuPK mpk = new CmsUserMenuPK();
                    mpk.setIdMenu(itm.getCmsSubmenu().getCmsMenu().getIdMenu());
                    mpk.setIdUser(cmsUsers.getIdUser());
                    m.setCmsUserMenuPK(mpk);

                    m.setCmsUsers(cmsUsers);
                    m.setCmsMenu(itm.getCmsSubmenu().getCmsMenu());
                    cmsUserMenuDao.saveOrUpdate(m);

                }

                for (CmsUserSubmenu itm: listSumber) {
                    CmsUserSubmenu sm = new CmsUserSubmenu();

                    CmsUserSubmenuPK smpk = new CmsUserSubmenuPK();
                    smpk.setIdMenu(itm.getCmsSubmenu().getCmsMenu().getIdMenu());
                    smpk.setIdUser(cmsUsers.getIdUser());
                    smpk.setIdSubmenu(itm.getCmsUserSubmenuPK().getIdSubmenu());

                    sm.setCmsUserSubmenuPK(smpk);

                    cmsUserSubmenuDao.saveOrUpdate(sm);
                } 
                showWindowUsers=false;

                reloadListCmsUserSubmenu2();
                //BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu2");

                reloadListCmsUserSubmenu1();        
                //BindUtils.postNotifyChange(null, null, UserManager.this, "listCmsUserSubmenu1");
       }
                
                
    }
    @NotifyChange({"showWindowUsers"})
    @Command({"okBatal"})
    public void okBatal(){
        showWindowUsers=false;
    }
    
    @NotifyChange({"addNew","resetInputIcon"})
    @Command("selectList")
    public void select(){
        setResetInputIcon(false);
        setAddNew(false);
    }
   
    @NotifyChange({"cmsUsers", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempCmsUsers(cmsUsers);
        cmsUsers = new CmsUsers();
        setResetInputIcon(true);      
        selectedTab=0;        
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(@BindingParam("parameter1") final CmsUsers m){
        
    }
    
    @NotifyChange({"listCmsUsers", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(cmsUsers.getCmsUsersKel().getDeskripsi());
        //simpan di database
        cmsUsersDao.saveOrUpdate(cmsUsers);
        
        cmsUserMenuDao.deleteAll(new ArrayList<CmsUserMenu>(cmsUsers.getCmsUserMenuSet()));
        //cmsUserSubmenuDao.deleteAll(new ArrayList<CmsUserSubmenu>(cmsUsers.getCmsUserSubmenuSet()));
        
        for (CmsUserSubmenu itm: listCmsUserSubmenu2) {
            CmsUserMenu  m = new CmsUserMenu();
            
            CmsUserMenuPK mpk = new CmsUserMenuPK();
            mpk.setIdMenu(itm.getCmsSubmenu().getCmsMenu().getIdMenu());
            mpk.setIdUser(cmsUsers.getIdUser());
            m.setCmsUserMenuPK(mpk);
            
            m.setCmsUsers(cmsUsers);
            m.setCmsMenu(itm.getCmsSubmenu().getCmsMenu());
            cmsUserMenuDao.saveOrUpdate(m);
            
        }
        
        for (CmsUserSubmenu itm: listCmsUserSubmenu2) {
            CmsUserSubmenu sm = new CmsUserSubmenu();
            
            CmsUserSubmenuPK smpk = new CmsUserSubmenuPK();
            smpk.setIdMenu(itm.getCmsSubmenu().getCmsMenu().getIdMenu());
            smpk.setIdUser(cmsUsers.getIdUser());
            smpk.setIdSubmenu(itm.getCmsUserSubmenuPK().getIdSubmenu());
            
            sm.setCmsUserSubmenuPK(smpk);
            
            cmsUserSubmenuDao.saveOrUpdate(sm);
        } 
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listCmsUsers.add(cmsUsers);
        
       setResetInputIcon(false);
       setAddNew(false);
       Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"cmsUsers", "addNew","resetInputIcon", "listCmsUserSubmenu1", "listCmsUserSubmenu2"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setCmsUsers(getTempCmsUsers()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        
        reloadListCmsUserSubmenu1();
        reloadListCmsUserSubmenu2();
 
        
    }
    
    @NotifyChange({"cmsUsers", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (isAddNew()==true) setCmsUsers(getTempCmsUsers()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }

    @NotifyChange("listCmsUsers")
    @Command("cariCmsUsers")
    public void cariCmsUsers(){
        if (cariCmsUsers.getIdUser() == null) cariCmsUsers.setIdUser("");
        if (cariCmsUsers.getFullName() == null ) cariCmsUsers.setFullName("");
 
        listCmsUsers = cmsUsersDao.findByIdFullName("%" + cariCmsUsers.getIdUser() + "%", "%" + cariCmsUsers.getFullName() + "%");        
    }
    
    @NotifyChange({"listCmsUsersPilih"})
    @Command({"cariPilihUser"})
    public void cariPilihUser(){
        if (cariCmsUsersPilih.getIdUser()== null) cariCmsUsersPilih.setIdUser("");
        if (cariCmsUsersPilih.getFullName()==null) cariCmsUsersPilih.setFullName("");
        
        //Messagebox.show(cariCmsUsersPilih.getIdUser() + "\t" + cariCmsUsersPilih.getFullName() );
        listCmsUsersPilih = cmsUsersDao.findByIdFullName("%" + cariCmsUsersPilih.getIdUser() + "%", "%" + cariCmsUsersPilih.getFullName() + "%");
        
    }
    
    @NotifyChange("listCmsUsers")
    @Command("reload")
    public void reload(){
        listCmsUsers = cmsUsersDao.getAll();
    }
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idUser").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validatePassword(vc, (String)beanProps.get("password").getValue());        
        //validateRetype(vc, (String)beanProps.get("password").getValue());        
        validateFullName(vc, (String)beanProps.get("fullName").getValue());      
        validateEmail(vc, (String)beanProps.get("email").getValue());      
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (cmsUsersDao.findById(kode) != null) {
                CmsUsers temp = new CmsUsers();
                temp = cmsUsersDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getIdUser());
                
            }            
        }
    }
    
    private void validatePassword(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateFullName(ValidationContext ctx, String str) {
        if (str==null) str="";//Jaga-jaga supaya tidak ada erro karena null
        if(str.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_full_name", "Full name tidak boleh kosong");
        } 
    }
    private void validateEmail(ValidationContext ctx, String str) {
        if (str==null) str="";//Jaga-jaga supaya tidak ada erro karena null
        if(str.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_email", "Email tidak boleh kosong");
        } 
    }
    
    private void validateCombo1(ValidationContext ctx, CmsUsers f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode CmsUsers tidak boleh Kosong");
        } 
    }

    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public CmsUsers getCariCmsUsers() {
        return cariCmsUsers;
    }

    public void setCariCmsUsers(CmsUsers cariCmsUsers) {
        this.cariCmsUsers = cariCmsUsers;
    }

    public CmsUsers getCmsUsers() {
        return cmsUsers;
    }

    public void setCmsUsers(CmsUsers cmsUsers) {
        this.cmsUsers = cmsUsers;
    }

    public CmsUsersDaoInter getCmsUsersDao() {
        return cmsUsersDao;
    }

    public void setCmsUsersDao(CmsUsersDaoInter cmsUsersDao) {
        this.cmsUsersDao = cmsUsersDao;
    }

    public String getImgAktifUrl() {
        return imgAktifUrl;
    }

    public void setImgAktifUrl(String imgAktifUrl) {
        this.imgAktifUrl = imgAktifUrl;
    }

    public String getImgErrorUrl() {
        return imgErrorUrl;
    }

    public void setImgErrorUrl(String imgErrorUrl) {
        this.imgErrorUrl = imgErrorUrl;
    }

    public String getImgFavouritesUrl() {
        return imgFavouritesUrl;
    }

    public void setImgFavouritesUrl(String imgFavouritesUrl) {
        this.imgFavouritesUrl = imgFavouritesUrl;
    }

    public String getImgFemale2Url() {
        return imgFemale2Url;
    }

    public void setImgFemale2Url(String imgFemale2Url) {
        this.imgFemale2Url = imgFemale2Url;
    }

    public String getImgFemaleUrl() {
        return imgFemaleUrl;
    }

    public void setImgFemaleUrl(String imgFemaleUrl) {
        this.imgFemaleUrl = imgFemaleUrl;
    }

    public String getImgMale2Url() {
        return imgMale2Url;
    }

    public void setImgMale2Url(String imgMale2Url) {
        this.imgMale2Url = imgMale2Url;
    }

    public String getImgMaleUrl() {
        return imgMaleUrl;
    }

    public void setImgMaleUrl(String imgMaleUrl) {
        this.imgMaleUrl = imgMaleUrl;
    }

    public String getImgNonAktifUrl() {
        return imgNonAktifUrl;
    }

    public void setImgNonAktifUrl(String imgNonAktifUrl) {
        this.imgNonAktifUrl = imgNonAktifUrl;
    }

    public String getImgOkUrl() {
        return imgOkUrl;
    }

    public void setImgOkUrl(String imgOkUrl) {
        this.imgOkUrl = imgOkUrl;
    }

    public List<CmsUsers> getListCmsUsers() {
        return listCmsUsers;
    }

    public void setListCmsUsers(List<CmsUsers> listCmsUsers) {
        this.listCmsUsers = listCmsUsers;
    }

    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmsUsers getTempCmsUsers() {
        return tempCmsUsers;
    }

    public void setTempCmsUsers(CmsUsers tempCmsUsers) {
        this.tempCmsUsers = tempCmsUsers;
    }

    public CmsMenu getCariCmsMenu() {
        return cariCmsMenu;
    }

    public void setCariCmsMenu(CmsMenu cariCmsMenu) {
        this.cariCmsMenu = cariCmsMenu;
    }

    public CmsSubmenu getCariCmsSubmenu() {
        return cariCmsSubmenu;
    }

    public void setCariCmsSubmenu(CmsSubmenu cariCmsSubmenu) {
        this.cariCmsSubmenu = cariCmsSubmenu;
    }

    public String getImgBlockedUrl() {
        return imgBlockedUrl;
    }

    public void setImgBlockedUrl(String imgBlockedUrl) {
        this.imgBlockedUrl = imgBlockedUrl;
    }

    public List<MMhs> getListMhs() {
        return listMhs;
    }

    public void setListMhs(List<MMhs> listMhs) {
        this.listMhs = listMhs;
    }

    public List<MPegawai> getListPegawai() {
        return listPegawai;
    }

    public void setListPegawai(List<MPegawai> listPegawai) {
        this.listPegawai = listPegawai;
    }

    public MhsDaoInter getMhsDao() {
        return mhsDao;
    }

    public void setMhsDao(MhsDaoInter mhsDao) {
        this.mhsDao = mhsDao;
    }

    public PegawaiDaoInter getPegawaiDao() {
        return pegawaiDao;
    }

    public void setPegawaiDao(PegawaiDaoInter pegawaiDao) {
        this.pegawaiDao = pegawaiDao;
    }

    public List<CmsUserSubmenu> getListCmsUserSubmenu1() {
        return listCmsUserSubmenu1;
    }

    public void setListCmsUserSubmenu1(List<CmsUserSubmenu> listCmsUserSubmenu1) {
        this.listCmsUserSubmenu1 = listCmsUserSubmenu1;
    }

    public List<CmsUserSubmenu> getListCmsUserSubmenu11() {
        return listCmsUserSubmenu11;
    }

    public void setListCmsUserSubmenu11(List<CmsUserSubmenu> listCmsUserSubmenu11) {
        this.listCmsUserSubmenu11 = listCmsUserSubmenu11;
    }

    public List<CmsUserSubmenu> getListCmsUserSubmenu2() {
        return listCmsUserSubmenu2;
    }

    public void setListCmsUserSubmenu2(List<CmsUserSubmenu> listCmsUserSubmenu2) {
        this.listCmsUserSubmenu2 = listCmsUserSubmenu2;
    }

    public List<CmsUserSubmenu> getListCmsUserSubmenu22() {
        return listCmsUserSubmenu22;
    }

    public void setListCmsUserSubmenu22(List<CmsUserSubmenu> listCmsUserSubmenu22) {
        this.listCmsUserSubmenu22 = listCmsUserSubmenu22;
    }

    public Boolean getShowWindowUsers() {
        return showWindowUsers;
    }

    public void setShowWindowUsers(Boolean showWindowUsers) {
        this.showWindowUsers = showWindowUsers;
    }

    public List<CmsUsers> getListCmsUsersPilih() {
        return listCmsUsersPilih;
    }

    public void setListCmsUsersPilih(List<CmsUsers> listCmsUsersPilih) {
        this.listCmsUsersPilih = listCmsUsersPilih;
    }

    public CmsUsers getCmsUsersPilih() {
        return cmsUsersPilih;
    }

    public void setCmsUsersPilih(CmsUsers cmsUsersPilih) {
        this.cmsUsersPilih = cmsUsersPilih;
    }

    public CmsUsers getCariCmsUsersPilih() {
        return cariCmsUsersPilih;
    }

    public void setCariCmsUsersPilih(CmsUsers cariCmsUsersPilih) {
        this.cariCmsUsersPilih = cariCmsUsersPilih;
    }




    
}
