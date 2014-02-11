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

public class Pegawai extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    PegawaiDaoInter pegawaiDao = (PegawaiDaoInter) appContext.getBean("PegawaiDaoBean");
    ProdiDaoInter prodiDao = (ProdiDaoInter) appContext.getBean("ProdiDaoBean"); 
    PegawaiGolDaoInter pegawaiGolDao = (PegawaiGolDaoInter) appContext.getBean("PegawaiGolDaoBean"); 
    PegawaiJabDaoInter pegawaiJabDao = (PegawaiJabDaoInter) appContext.getBean("PegawaiJabDaoBean"); 
    //Entity or Variable utama
    private List<MPegawai> listPegawai;
    private MPegawai pegawai;
    private MPegawai tempPegawai; //temp digunakan untuk: Tambah Baru
    private MPegawai cariPegawai;
    
    //ComboBox dll
    private List<MPegawaiGol> listPegawaiGol;
    private List<MPegawaiJab> listPegawaiJab;
    private List<MProdi> listProdi;
    
    
    //Entity or Variable Util atau pendukung
    private boolean addNew;
    private int selectedTab=1;
    
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
        listPegawai = pegawaiDao.getAll();
        
        listProdi = prodiDao.getAll();
        listPegawaiGol = pegawaiGolDao.getAll();
        listPegawaiJab = pegawaiJabDao.getAll();
        
        //Menghindari null value
        pegawai = new MPegawai();
        tempPegawai = new MPegawai();
        cariPegawai = new MPegawai();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listPegawai.size() > 0)
            pegawai = listPegawai.get(0);
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
   
    @NotifyChange({"pegawai", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempPegawai(getPegawai());
        pegawai = new MPegawai();
        setResetInputIcon(true);      
        selectedTab=0;        
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(@BindingParam("parameter1") final MPegawai m){
        
        Messagebox.show("Anda Yakin Hapus?", "Konfirmasi", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                            //if (pegawai.getProdiSet().size()>0) {       
                            //      Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel PROGRAM STUDI", "Warning", 
                            //              Messagebox.OK, Messagebox.EXCLAMATION);
                            //} else {
                                if (m !=null)
                                    setPegawai(m);
                                //Hapus didatabase
                                //pegawaiDao.delete(pegawai);
                                //Hapus di tabel 
                                listPegawai.remove(pegawai); //Notify Change gak berhasil disini jadi harus pake BindUtils
                                //Kosongkan isi teksbox
                                pegawai = new MPegawai();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, Pegawai.this, "listPegawai");        
                                BindUtils.postNotifyChange(null, null, Pegawai.this, "pegawai");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            //}
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listPegawai", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(pegawai.getPegawaiKel().getDeskripsi());
        //simpan di database
        pegawaiDao.saveOrUpdate(pegawai);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listPegawai.add(pegawai);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"pegawai", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setPegawai(getTempPegawai()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    @NotifyChange({"pegawai", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (isAddNew()==true) setPegawai(getTempPegawai()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }
    //Fungsi-fungsi tombol dari list NAVIGASI EDIT pada LIST
    //Untuk Tambah kita pake aja tombol tambah namun dipaksa agar selectedIndes tab=0
    @NotifyChange({"pegawai","selectedTab"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") MPegawai m){
        
        pegawai = new MPegawai();
        pegawai =  m;
        selectedTab=0;
    }

    @NotifyChange("listPegawai")
    @Command("cari")
    public void cari(){
        //Jaga jaga supaya tidak terjadi null
        if (cariPegawai.getNip()==null) cariPegawai.setNip("");
        if (cariPegawai.getNidn()==null) cariPegawai.setNidn("");
        if (cariPegawai.getNamaPegawai()==null) cariPegawai.setNamaPegawai("");
        
        if (cariPegawai.getNip().equals("") && cariPegawai.getNidn().equals("") && cariPegawai.getNamaPegawai().equals("")) {
            listPegawai = pegawaiDao.getAll();
        } else {
            
            cariPegawai.setIdPegawai("%");
            cariPegawai.setNip("%"+ cariPegawai.getNip() + "%");
            cariPegawai.setNidn("%"+ cariPegawai.getNidn() + "%");
            cariPegawai.setNamaPegawai("%"+ cariPegawai.getNamaPegawai() + "%");
            listPegawai = pegawaiDao.findByManualCriteria(cariPegawai);
        }
    }
    @NotifyChange("listPegawai")
    @Command("reload")
    public void reload(){
        listPegawai = pegawaiDao.getAll();
    }
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idPegawai").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaPegawai").getValue());        
        //validateCombo1(vc, (MPegawai)beanProps.get("pegawai").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (pegawaiDao.findById(kode) != null) {
                MPegawai temp = new MPegawai();
                temp = pegawaiDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaPegawai());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, MPegawai f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode Pegawai tidak boleh Kosong");
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

    public List<MPegawai> getListPegawai() {
        return listPegawai;
    }

    public void setListPegawai(List<MPegawai> listPegawai) {
        this.listPegawai = listPegawai;
    }

    public List<MPegawaiGol> getListPegawaiGol() {
        return listPegawaiGol;
    }

    public void setListPegawaiGol(List<MPegawaiGol> listPegawaiGol) {
        this.listPegawaiGol = listPegawaiGol;
    }

    public List<MPegawaiJab> getListPegawaiJab() {
        return listPegawaiJab;
    }

    public void setListPegawaiJab(List<MPegawaiJab> listPegawaiJab) {
        this.listPegawaiJab = listPegawaiJab;
    }

    public MPegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(MPegawai pegawai) {
        this.pegawai = pegawai;
    }

    public PegawaiDaoInter getPegawaiDao() {
        return pegawaiDao;
    }

    public void setPegawaiDao(PegawaiDaoInter pegawaiDao) {
        this.pegawaiDao = pegawaiDao;
    }

    public PegawaiGolDaoInter getPegawaiGolDao() {
        return pegawaiGolDao;
    }

    public void setPegawaiGolDao(PegawaiGolDaoInter pegawaiGolDao) {
        this.pegawaiGolDao = pegawaiGolDao;
    }

    public PegawaiJabDaoInter getPegawaiJabDao() {
        return pegawaiJabDao;
    }

    public void setPegawaiJabDao(PegawaiJabDaoInter pegawaiJabDao) {
        this.pegawaiJabDao = pegawaiJabDao;
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

    public MPegawai getTempPegawai() {
        return tempPegawai;
    }

    public void setTempPegawai(MPegawai tempPegawai) {
        this.tempPegawai = tempPegawai;
    }

    public List<MProdi> getListProdi() {
        return listProdi;
    }

    public void setListProdi(List<MProdi> listProdi) {
        this.listProdi = listProdi;
    }

    public String getImgFemaleUrl() {
        return imgFemaleUrl;
    }

    public void setImgFemaleUrl(String imgFemaleUrl) {
        this.imgFemaleUrl = imgFemaleUrl;
    }

    public String getImgMaleUrl() {
        return imgMaleUrl;
    }

    public void setImgMaleUrl(String imgMaleUrl) {
        this.imgMaleUrl = imgMaleUrl;
    }

    public String getImgFemale2Url() {
        return imgFemale2Url;
    }

    public void setImgFemale2Url(String imgFemale2Url) {
        this.imgFemale2Url = imgFemale2Url;
    }

    public String getImgMale2Url() {
        return imgMale2Url;
    }

    public void setImgMale2Url(String imgMale2Url) {
        this.imgMale2Url = imgMale2Url;
    }

    public MPegawai getCariPegawai() {
        return cariPegawai;
    }

    public void setCariPegawai(MPegawai cariPegawai) {
        this.cariPegawai = cariPegawai;
    }


    

    
}
