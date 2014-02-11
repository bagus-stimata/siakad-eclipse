/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.master;

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
public class KuliahJam extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    KuliahJamDaoInter kuliahJamDao = (KuliahJamDaoInter) appContext.getBean("KuliahJamDaoBean");
            
    //Entity or Variable utama
    private List<MKuliahJam> listKuliahJam;
    private MKuliahJam kuliahJam;
    private MKuliahJam tempKuliahJam; //temp digunakan untuk: Tambah Baru
    //ComboBox dll

    //Entity or Variable Util atau pendukung
    private boolean addNew;
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
    @Init
    public void init(){
        //Inisialisasi isi utama
        listKuliahJam = kuliahJamDao.getAll();
        
        listKuliahJam = kuliahJamDao.getAll();
        
        kuliahJam = new MKuliahJam();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listKuliahJam.size() > 0)
            kuliahJam = listKuliahJam.get(0);
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
   
    @NotifyChange({"kuliahJam", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempKuliahJam(getKuliahJam());
        kuliahJam = new MKuliahJam();
        setResetInputIcon(true);
        setAddNew(true);
    }
    
    //@NotifyChange({"listMatkul", "matkul"}) //Notify Change ini tidak berlaku jika pake MessageBox sehingga NotifyChange harus pake BindUtils.NotifyChange
    @Command("hapus") 
    public void hapus(){
        
        Messagebox.show("Anda Yakin Hapus?", "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                            /*
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                            if (kuliahJam.getProdiSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel PROGRAM STUDI", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase
                                kuliahJamDao.delete(kuliahJam);
                                //Hapus di tabel 
                                listKuliahJam.remove(kuliahJam); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                kuliahJam = new MKuliahJam();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, KuliahJam.this, "listKuliahJam");        
                                BindUtils.postNotifyChange(null, null, KuliahJam.this, "kuliahJam");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
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
    
    @NotifyChange({"listKuliahJam", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        kuliahJamDao.saveOrUpdate(kuliahJam);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listKuliahJam.add(kuliahJam);
        
       setResetInputIcon(false);
       
       setAddNew(false);
       Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"kuliahJam", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setKuliahJam(getTempKuliahJam()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (Integer)beanProps.get("idJam").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("deskripsi").getValue());        
        //validateCombo1(vc, (MKuliahJam)beanProps.get("kuliahJam").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, Integer kode, boolean tambahBaru) {
                
        if (kode==null) kode=0; //Jaga jaga supaya tidak ada error karena null
        if(kode==null) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (kuliahJamDao.findById(kode) != null) {
                MKuliahJam temp = new MKuliahJam();
                temp = kuliahJamDao.findById(kode);
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
    /*
    private void validateCombo1(ValidationContext ctx, MKuliahJam f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode KuliahJam tidak boleh Kosong");
        } 
    }
    * 
    */
    

    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
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

    public String getImgOkUrl() {
        return imgOkUrl;
    }

    public void setImgOkUrl(String imgOkUrl) {
        this.imgOkUrl = imgOkUrl;
    }


    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }

    public List<MKuliahJam> getListKuliahJam() {
        return listKuliahJam;
    }

    public void setListKuliahJam(List<MKuliahJam> listKuliahJam) {
        this.listKuliahJam = listKuliahJam;
    }


    public MKuliahJam getKuliahJam() {
        return kuliahJam;
    }

    public void setKuliahJam(MKuliahJam kuliahJam) {
        this.kuliahJam = kuliahJam;
    }

    public MKuliahJam getTempKuliahJam() {
        return tempKuliahJam;
    }

    public void setTempKuliahJam(MKuliahJam tempKuliahJam) {
        this.tempKuliahJam = tempKuliahJam;
    }


    
}
