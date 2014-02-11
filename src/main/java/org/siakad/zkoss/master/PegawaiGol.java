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
public class PegawaiGol extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    PegawaiGolDaoInter pegawaiGolDao = (PegawaiGolDaoInter) appContext.getBean("PegawaiGolDaoBean");
            
    //Entity or Variable utama
    private List<MPegawaiGol> listPegawaiGol;
    private MPegawaiGol pegawaiGol;
    private MPegawaiGol tempPegawaiGol; //temp digunakan untuk: Tambah Baru
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
        listPegawaiGol = pegawaiGolDao.getAll();
        
        listPegawaiGol = pegawaiGolDao.getAll();
        
        pegawaiGol = new MPegawaiGol();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listPegawaiGol.size() > 0)
            pegawaiGol = listPegawaiGol.get(0);
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
   
    @NotifyChange({"pegawaiGol", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempPegawaiGol(getPegawaiGol());
        pegawaiGol = new MPegawaiGol();
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
                            if (pegawaiGol.getPegawaiSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel Pegawai", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase
                                pegawaiGolDao.delete(pegawaiGol);
                                //Hapus di tabel 
                                listPegawaiGol.remove(pegawaiGol); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                pegawaiGol = new MPegawaiGol();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, PegawaiGol.this, "listPegawaiGol");        
                                BindUtils.postNotifyChange(null, null, PegawaiGol.this, "pegawaiGol");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            }
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listPegawaiGol", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        pegawaiGolDao.saveOrUpdate(pegawaiGol);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listPegawaiGol.add(pegawaiGol);
        
       setResetInputIcon(false);
       setAddNew(false);
       Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"pegawaiGol", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setPegawaiGol(getTempPegawaiGol()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idGolongan").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaGolongan").getValue());        
        //validateCombo1(vc, (MPegawaiGol)beanProps.get("pegawaiGol").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (pegawaiGolDao.findById(kode) != null) {
                MPegawaiGol temp = new MPegawaiGol();
                temp = pegawaiGolDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaGolongan());
                
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
    private void validateCombo1(ValidationContext ctx, MPegawaiGol f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode PegawaiGol tidak boleh Kosong");
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

    public List<MPegawaiGol> getListPegawaiGol() {
        return listPegawaiGol;
    }

    public void setListPegawaiGol(List<MPegawaiGol> listPegawaiGol) {
        this.listPegawaiGol = listPegawaiGol;
    }


    public MPegawaiGol getPegawaiGol() {
        return pegawaiGol;
    }

    public void setPegawaiGol(MPegawaiGol pegawaiGol) {
        this.pegawaiGol = pegawaiGol;
    }

    public MPegawaiGol getTempPegawaiGol() {
        return tempPegawaiGol;
    }

    public void setTempPegawaiGol(MPegawaiGol tempPegawaiGol) {
        this.tempPegawaiGol = tempPegawaiGol;
    }


    
}
