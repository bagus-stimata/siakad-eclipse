/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.master;

import java.util.List;
import java.util.Map;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.MatkulKurDaoInter;
import org.config.spring.hibernate.model.MMatkulKur;

import org.springframework.context.ApplicationContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author yhawin
 */
public class MatkulKurikulum extends AbstractValidator{
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    MatkulKurDaoInter matkulKurDao = (MatkulKurDaoInter) appContext.getBean("MatkulKurDaoBean");
    //Entity or Variable utama
    private List<MMatkulKur> listMatkulKurikulum;
    private MMatkulKur matkulKur;
    private MMatkulKur tempMatkulKur; //temp digunakan untuk: Tambah Baru
    
    //Entity or Variable Pendukung
    private boolean addNew;
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
    public MatkulKurikulum() {
    }
    
    @Init
    public void init(){
        //Inisialisasi isi utama
        listMatkulKurikulum = matkulKurDao.getAll();
        matkulKur = new MMatkulKur();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listMatkulKurikulum.size() > 0)
            matkulKur = listMatkulKurikulum.get(0);
        //Mula-mula tombol tambah adalah false
        setAddNew(false);
    }
    
    @AfterCompose
    public void afterCompose(){
    }
    
    
    @NotifyChange({"matkulKur", "addNew", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempMatkulKur(getMatkulKur());
        matkulKur = new MMatkulKur();
        setResetInputIcon(true);
        setAddNew(true);
    }
    
    //@NotifyChange({"listMatkulKurikulum", "matkulKur"}) //Notify Change ini tidak berlaku jika pake MessageBox sehingga NotifyChange harus pake BindUtils.NotifyChange
    @Command("hapus") 
    public void hapus(){

        Messagebox.show("Anda Yakin Hapus?", "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                        //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Kurikulum (Karena dia adalah foreint key kurikulum)
                            if (matkulKur.getMatkulSet().size()>0) {       
                                  Messagebox.show("Kode Kurikulum tersebut masih dipakai oleh tabel Mata Kuliah", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase
                                matkulKurDao.delete(matkulKur);
                                //Hapus di tabel 
                                listMatkulKurikulum.remove(matkulKur); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                matkulKur = new MMatkulKur();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, MatkulKurikulum.this, "listMatkulKurikulum");        
                                BindUtils.postNotifyChange(null, null, MatkulKurikulum.this, "matkulKur");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            }
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
        
        
    }
 
    @NotifyChange({"listMatkulKurikulum", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //simpan di database
        matkulKurDao.saveOrUpdate(matkulKur);
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listMatkulKurikulum.add(matkulKur);
        setResetInputIcon(false); 
        setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"matkulKur", "addNew", "resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        if (isAddNew()==true) setMatkulKur(getTempMatkulKur()); //
        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
   
    public List<MMatkulKur> getListMatkulKurikulum() {
        return listMatkulKurikulum;
    }

    public void setListMatkulKurikulum(List<MMatkulKur> listMatkulKurikulum) {
        this.listMatkulKurikulum = listMatkulKurikulum;
    }

    public MMatkulKur getMatkulKur() {
        return matkulKur;
    }

    public void setMatkulKur(MMatkulKur matkulKur) {
        this.matkulKur = matkulKur;
    }

    public MMatkulKur getTempMatkulKur() {
        return tempMatkulKur;
    }

    public void setTempMatkulKur(MMatkulKur tempMatkulKur) {
        this.tempMatkulKur = tempMatkulKur;
    }

    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idKurikulum").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("deskripsi").getValue());        
    }
    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (matkulKurDao.findById(kode) != null) {
                MMatkulKur temp = new MMatkulKur();
                temp = matkulKurDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI pada: " + temp.getDeskripsi());
            }
            
        }
    }
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        }
    }
    
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

    public String getImgOkUrl() {
        return imgOkUrl;
    }

    public void setImgOkUrl(String imgOkUrl) {
        this.imgOkUrl = imgOkUrl;
    }

    public String getImgFavouritesUrl() {
        return imgFavouritesUrl;
    }

    public void setImgFavouritesUrl(String imgFavouritesUrl) {
        this.imgFavouritesUrl = imgFavouritesUrl;
    }

    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }
    
    
}
