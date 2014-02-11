/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.master;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author yhawin
 */

public class Mhs extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    MhsDaoInter mhsDao = (MhsDaoInter) appContext.getBean("MhsDaoBean");
    ProdiDaoInter prodiDao = (ProdiDaoInter) appContext.getBean("ProdiDaoBean"); 
    //Entity or Variable utama
    private List<MMhs> listMhs = new ArrayList<MMhs>();
    private MMhs mhs = new MMhs();
    private MMhs tempMhs = new MMhs(); //temp digunakan untuk: Tambah Baru
    
    private MMhs cariMhs = new MMhs();
    private MProdi cariProdi = new MProdi();
    
    //ComboBox dll
    private List<MProdi> listProdi = new ArrayList<MProdi>();
    
    
    //Entity or Variable Util atau pendukung
    private Boolean addNew;
    private Integer selectedTab=1;
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private String imgAktifUrl = "/widgets/images/navigation/12x12/Apply.png";
    private String imgNonAktifUrl = "/widgets/images/navigation/12x12/Problem.png";
    private String imgMaleUrl = "/widgets/images/navigation/12x12/Male-symbol.png";
    private String imgMale2Url = "/widgets/images/navigation/14x14/Male.png";
    private String imgFemaleUrl = "/widgets/images/navigation/12x12/Female-symbol.png";
    private String imgFemale2Url = "/widgets/images/navigation/14x14/Female.png";

    private Boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
    @Init
    public void init(){
        //Inisialisasi isi utama
            listMhs = mhsDao.getAll();
        
            listProdi = prodiDao.getAll();
        
        
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listMhs.size() > 0)
            mhs = listMhs.get(0);
        
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
   
    @NotifyChange({"mhs", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempMhs(getMhs());
        mhs = new MMhs();
        setResetInputIcon(true);      
        selectedTab=0;        
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(@BindingParam("parameter1") final MMhs m){
        
        Messagebox.show("Anda Yakin Hapus?", "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                            /*
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                            if (mhs.getProdiSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel PROGRAM STUDI", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                            */
                                if (m != null)
                                    setMhs(m);
                                //Hapus didatabase
                               // mhsDao.delete(mhs);
                                //Hapus di tabel 
                                listMhs.remove(mhs); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                mhs = new MMhs();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, Mhs.this, "listMhs");        
                                BindUtils.postNotifyChange(null, null, Mhs.this, "mhs");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            /*
                            }
                            * 
                            */
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listMhs", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(mhs.getMhsKel().getDeskripsi());
        //simpan di database
        mhsDao.saveOrUpdate(mhs);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (getAddNew()==true)
            listMhs.add(mhs);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"mhs", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (getAddNew()==true) setMhs(getTempMhs()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    @NotifyChange({"mhs", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (getAddNew()==true) setMhs(getTempMhs()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }
    //Fungsi-fungsi tombol dari list NAVIGASI EDIT pada LIST
    //Untuk Tambah kita pake aja tombol tambah namun dipaksa agar selectedIndes tab=0
    @NotifyChange({"mhs","selectedTab"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") MMhs m){
        
        mhs = new MMhs();
        mhs =  m;
        selectedTab=0;
    }

    @NotifyChange("listMhs")
    @Command("cari")
    public void cari(){
        
        //Jaga jaga supaya tidak terjadi null
        if (cariMhs.getIdMhs()==null) cariMhs.setIdMhs("");
        if (cariMhs.getNamaMhs()==null) cariMhs.setNamaMhs("");
        if (cariMhs.getAngkatan()==null) cariMhs.setAngkatan(0);
         //Ingat Cuma ID yang kita isi lhoo
        if (cariProdi.getIdProdi()==null) cariProdi.setIdProdi("");
        
        if (cariMhs.getIdMhs().equals("") && cariMhs.getNamaMhs().equals("") && 
                cariMhs.getAngkatan().equals(0) && cariProdi.getIdProdi().equals("")) {
       
            listMhs = mhsDao.getAll();    
       
        } else {
            
            cariMhs.setIdMhs("%"+ cariMhs.getIdMhs().trim() + "%");
            cariMhs.setNamaMhs("%"+ cariMhs.getNamaMhs().trim() + "%");
            
            MProdi f = new MProdi();
            f.setIdProdi("%" + getCariProdi().getIdProdi() + "%");
            
            cariMhs.setProdi(f);
            listMhs = mhsDao.findByManualCriteria(cariMhs);
            
        }
        
    }
    
    @NotifyChange("listMhs")
    @Command("reload")
    public void reload(){
        listMhs = mhsDao.getAll();
    }
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idMhs").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaMhs").getValue());        
        validateCombo1(vc, (MProdi)beanProps.get("prodi").getValue());        
        validateAngkatan(vc, (Integer)beanProps.get("angkatan").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (mhsDao.findById(kode) != null) {
                MMhs temp = new MMhs();
                temp = mhsDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaMhs());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, MProdi f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "KODE PROGRAM STRUDI tidak boleh Kosong");
        } 
    }
    private void validateAngkatan(ValidationContext vc, Integer angkatan) {
        if (angkatan==null) angkatan=0;//Jaga-jaga supaya tidak ada erro karena null
        if(angkatan < 1) {
            this.addInvalidMessage(vc, "err_angkatan", "Angkatan harus diisi");
        } 
    }

    public Boolean getAddNew() {
        return addNew;
    }

    public void setAddNew(Boolean addNew) {
        this.addNew = addNew;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public MMhs getCariMhs() {
        return cariMhs;
    }

    public void setCariMhs(MMhs cariMhs) {
        this.cariMhs = cariMhs;
    }

    public MProdi getCariProdi() {
        return cariProdi;
    }

    public void setCariProdi(MProdi cariProdi) {
        this.cariProdi = cariProdi;
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

    public List<MMhs> getListMhs() {
        return listMhs;
    }

    public void setListMhs(List<MMhs> listMhs) {
        this.listMhs = listMhs;
    }

    public List<MProdi> getListProdi() {
        return listProdi;
    }

    public void setListProdi(List<MProdi> listProdi) {
        this.listProdi = listProdi;
    }

    public MMhs getMhs() {
        return mhs;
    }

    public void setMhs(MMhs mhs) {
        this.mhs = mhs;
    }

    public MhsDaoInter getMhsDao() {
        return mhsDao;
    }

    public void setMhsDao(MhsDaoInter mhsDao) {
        this.mhsDao = mhsDao;
    }

    public ProdiDaoInter getProdiDao() {
        return prodiDao;
    }

    public void setProdiDao(ProdiDaoInter prodiDao) {
        this.prodiDao = prodiDao;
    }

    public Boolean getResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(Boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }

    public Integer getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(Integer selectedTab) {
        this.selectedTab = selectedTab;
    }

    public MMhs getTempMhs() {
        return tempMhs;
    }

    public void setTempMhs(MMhs tempMhs) {
        this.tempMhs = tempMhs;
    }



    

    
}
