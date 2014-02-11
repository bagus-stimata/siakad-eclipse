/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.setting;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author yhawin
 */

public class ParameterSistem extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    SysvarDaoInter sysvarDao = (SysvarDaoInter) appContext.getBean("SysvarDaoBean");

    //Entity or Variable utama
    private List<Sysvar> listSysvar;
    private Sysvar sysvar;
    private Sysvar tempSysvar; //temp digunakan untuk: Tambah Baru
    private Sysvar cariSysvar;
    
    //ComboBox dll   
    
    //Entity or Variable Util atau pendukung
    private boolean addNew;
    private int selectedTab=1;
    
    public String [] getComboTipeData(){
        String [] tipeData = {"STR1", "STR2", "BOL1", "BOL2", "INT1", "INT2", "DBL1", "DBL2", "DATE1", "DATE2"};
        return tipeData;
    }
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private String imgAktifUrl = "/widgets/images/navigation/12x12/Apply.png";
    private String imgNonAktifUrl = "/widgets/images/navigation/12x12/Problem.png";
    private String imgMaleUrl = "/widgets/images/navigation/12x12/Male-symbol.png";
    private String imgMale2Url = "/widgets/images/navigation/14x14/Male.png";
    private String imgFemaleUrl = "/widgets/images/navigation/12x12/Female-symbol.png";
    private String imgFemale2Url = "/widgets/images/navigation/14x14/Female.png";

    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
    @Init
    public void init(){
        //Inisialisasi isi utama
        listSysvar = sysvarDao.getAll();
        
        //Menghindari null value
        sysvar = new Sysvar();
        tempSysvar = new Sysvar();
        cariSysvar = new Sysvar();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listSysvar.size() > 0)
            sysvar = listSysvar.get(0);
        //Mula-mula tombol tambah adalah false
        setAddNew(false);
    }
    
    @AfterCompose
    public void afterCompose(){
    }
    @NotifyChange({"addNew","resetInputIcon"})
    @Command("selectList")
    public void select(){
        setResetInputIcon(false);
        setAddNew(false);
    }
   
    @NotifyChange({"sysvar", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempSysvar(getSysvar());
        sysvar = new Sysvar();
        setResetInputIcon(true);      
        selectedTab=0;        
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(@BindingParam("parameter1") final Sysvar m){
        
        Messagebox.show("Anda Yakin Hapus? \n Perhatian Penghapusan Parameter Dapat Mempengaruhi Seluruh Sistem", "Konfirmasi", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                                if (m != null)
                                    setSysvar(m);
                                //Hapus didatabase
                                //sysvarDao.delete(sysvar);
                                //Hapus di tabel 
                                listSysvar.remove(sysvar); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                sysvar = new Sysvar();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, ParameterSistem.this, "listSysvar");        
                                BindUtils.postNotifyChange(null, null, ParameterSistem.this, "sysvar");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listSysvar", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(sysvar.getDeskripsi());
        //simpan di database
        sysvarDao.saveOrUpdate(sysvar);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listSysvar.add(sysvar);
        
        setResetInputIcon(false);
        setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"sysvar", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setSysvar(getTempSysvar()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    @NotifyChange({"sysvar", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (isAddNew()==true) setSysvar(getTempSysvar()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }
    //Fungsi-fungsi tombol dari list NAVIGASI EDIT pada LIST
    //Untuk Tambah kita pake aja tombol tambah namun dipaksa agar selectedIndes tab=0
    @NotifyChange({"sysvar","selectedTab"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") Sysvar m){
        
        sysvar = new Sysvar();
        sysvar =  m;
        selectedTab=0;
    }

    @NotifyChange("listSysvar")
    @Command("cari")
    public void cari(){
        //Jaga jaga supaya tidak terjadi null
        if (cariSysvar.getIdSysvar()==null) cariSysvar.setIdSysvar("");
        if (cariSysvar.getGroupSysvar()==null) cariSysvar.setGroupSysvar("");
        if (cariSysvar.getDeskripsi()==null) cariSysvar.setDeskripsi("");
            
        cariSysvar.setIdSysvar("%"+ cariSysvar.getIdSysvar() + "%");
        cariSysvar.setGroupSysvar("%"+ cariSysvar.getGroupSysvar() + "%");
        cariSysvar.setDeskripsi("%"+ cariSysvar.getDeskripsi()+ "%");
        
        listSysvar = sysvarDao.findByManualCriteria(cariSysvar);
            
    }
    @NotifyChange("listSysvar")
    @Command("reload")
    public void reload(){
        listSysvar = sysvarDao.getAll();
    }
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idSysvar").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("deskripsi").getValue());        
        validateGroup(vc, (String)beanProps.get("groupSysvar").getValue());        
        validateTipeData(vc, (String)beanProps.get("tipeData").getValue());        
        
        //validateCombo1(vc, (Sysvar)beanProps.get("sysvar").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (sysvarDao.findById(kode) != null) {
                Sysvar temp = new Sysvar();
                temp = sysvarDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getDeskripsi());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateGroup(ValidationContext ctx, String gr) {
        if (gr==null) gr="";//Jaga-jaga supaya tidak ada erro karena null
        if(gr.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_group", "Group tidak boleh kosong");
        } 
    }
    private void validateTipeData(ValidationContext ctx, String gr) {
        if (gr==null) gr="";//Jaga-jaga supaya tidak ada erro karena null
        if(gr.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_tipeData", "TIPE DATA tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, Sysvar f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode Sysvar tidak boleh Kosong");
        } 
    }
    

    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
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

    public List<Sysvar> getListSysvar() {
        return listSysvar;
    }

    public void setListSysvar(List<Sysvar> listSysvar) {
        this.listSysvar = listSysvar;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public Sysvar getCariSysvar() {
        return cariSysvar;
    }

    public void setCariSysvar(Sysvar cariSysvar) {
        this.cariSysvar = cariSysvar;
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

    public Sysvar getSysvar() {
        return sysvar;
    }

    public void setSysvar(Sysvar sysvar) {
        this.sysvar = sysvar;
    }

    public SysvarDaoInter getSysvarDao() {
        return sysvarDao;
    }

    public void setSysvarDao(SysvarDaoInter sysvarDao) {
        this.sysvarDao = sysvarDao;
    }

    public Sysvar getTempSysvar() {
        return tempSysvar;
    }

    public void setTempSysvar(Sysvar tempSysvar) {
        this.tempSysvar = tempSysvar;
    }


    

    
}
