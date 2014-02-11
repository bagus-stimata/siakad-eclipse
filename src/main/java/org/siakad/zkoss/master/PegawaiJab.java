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
public class PegawaiJab extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    PegawaiJabDaoInter pegawaiJabDao = (PegawaiJabDaoInter) appContext.getBean("PegawaiJabDaoBean");
            
    //Entity or Variable utama
    private List<MPegawaiJab> listPegawaiJab;
    private MPegawaiJab pegawaiJab;
    private MPegawaiJab tempPegawaiJab; //temp digunakan untuk: Tambah Baru
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
        listPegawaiJab = pegawaiJabDao.getAll();
        
        listPegawaiJab = pegawaiJabDao.getAll();
        
        pegawaiJab = new MPegawaiJab();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listPegawaiJab.size() > 0)
            pegawaiJab = listPegawaiJab.get(0);
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
   
    @NotifyChange({"pegawaiJab", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempPegawaiJab(getPegawaiJab());
        pegawaiJab = new MPegawaiJab();
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
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                            if (pegawaiJab.getPegawaiSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel Pegawai", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase
                                pegawaiJabDao.delete(pegawaiJab);
                                //Hapus di tabel 
                                listPegawaiJab.remove(pegawaiJab); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                pegawaiJab = new MPegawaiJab();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, PegawaiJab.this, "listPegawaiJab");        
                                BindUtils.postNotifyChange(null, null, PegawaiJab.this, "pegawaiJab");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            }
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listPegawaiJab", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        pegawaiJabDao.saveOrUpdate(pegawaiJab);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listPegawaiJab.add(pegawaiJab);
        
       setResetInputIcon(false);
       setAddNew(false);
       Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"pegawaiJab", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setPegawaiJab(getTempPegawaiJab()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idJabatan").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaJabatan").getValue());        
        //validateCombo1(vc, (MPegawaiJab)beanProps.get("pegawaiJab").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (pegawaiJabDao.findById(kode) != null) {
                MPegawaiJab temp = new MPegawaiJab();
                temp = pegawaiJabDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaJabatan());
                
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
    private void validateCombo1(ValidationContext ctx, MPegawaiJab f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode PegawaiJab tidak boleh Kosong");
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

    public List<MPegawaiJab> getListPegawaiJab() {
        return listPegawaiJab;
    }

    public void setListPegawaiJab(List<MPegawaiJab> listPegawaiJab) {
        this.listPegawaiJab = listPegawaiJab;
    }


    public MPegawaiJab getPegawaiJab() {
        return pegawaiJab;
    }

    public void setPegawaiJab(MPegawaiJab pegawaiJab) {
        this.pegawaiJab = pegawaiJab;
    }

    public MPegawaiJab getTempPegawaiJab() {
        return tempPegawaiJab;
    }

    public void setTempPegawaiJab(MPegawaiJab tempPegawaiJab) {
        this.tempPegawaiJab = tempPegawaiJab;
    }


    
}
