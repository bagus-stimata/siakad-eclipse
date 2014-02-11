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
public class Jurusan extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
   FakultasDaoInter fakultasDao = (FakultasDaoInter) appContext.getBean("FakultasDaoBean");
   JurusanDaoInter jurusanDao = (JurusanDaoInter) appContext.getBean("JurusanDaoBean");
    
    //Entity or Variable utama
    private List<MJurusan> listJurusan = new ArrayList<MJurusan>();
    private MJurusan jurusan = new MJurusan();
    private MJurusan tempJurusan = new MJurusan(); //temp digunakan untuk: Tambah Baru
    //ComboBox dll
    private List<MFakultas> listFakultas = new ArrayList<MFakultas>();

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
        listFakultas = fakultasDao.getAll();        
        listJurusan = jurusanDao.getAll();
        
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listJurusan.size() > 0)
            jurusan = listJurusan.get(0);
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
   
    @NotifyChange({"jurusan", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempJurusan(getJurusan());
        jurusan = new MJurusan();
        setResetInputIcon(true);
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(){
        
        Messagebox.show("Anda Yakin Hapus?", "Question", Messagebox.OK | Messagebox.CANCEL,
                Messagebox.QUESTION,
                new org.zkoss.zk.ui.event.EventListener(){
                    @Override
                    public void onEvent(Event e){
                        if(Messagebox.ON_OK.equals(e.getName())){
                            /* TUNGGU SAMPAI KETAHUAN APA AJA YANG LINK KEBAWAH DENGAN MATKUL*/
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Kurikulum (Karena dia adalah foreint key kurikulum)
                            if (jurusan.getProdiSet().size() >0 ) {       
                                  Messagebox.show("Kode Kelompok tersebut masih dipakai oleh tabel PRODI", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase

                                //jurusanDao.delete(jurusan);
                                //Hapus di tabel 
                                listJurusan.remove(jurusan); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                jurusan = new MJurusan();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, Jurusan.this, "listJurusan");        
                                BindUtils.postNotifyChange(null, null, Jurusan.this, "jurusan");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            }
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listJurusan", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        jurusanDao.saveOrUpdate(jurusan);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listJurusan.add(jurusan);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"jurusan", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setJurusan(getTempJurusan()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idJurusan").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaJurusan").getValue());        
        validateCombo1(vc, (MFakultas)beanProps.get("fakultas").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (jurusanDao.findById(kode) != null) {
                MJurusan temp = new MJurusan();
                temp = jurusanDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaJurusan());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, MFakultas f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode Jurusan tidak boleh Kosong");
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

    public FakultasDaoInter getFakultasDao() {
        return fakultasDao;
    }

    public void setFakultasDao(FakultasDaoInter fakultasDao) {
        this.fakultasDao = fakultasDao;
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

    public MJurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(MJurusan jurusan) {
        this.jurusan = jurusan;
    }

    public JurusanDaoInter getJurusanDao() {
        return jurusanDao;
    }

    public void setJurusanDao(JurusanDaoInter jurusanDao) {
        this.jurusanDao = jurusanDao;
    }

    public List<MFakultas> getListFakultas() {
        return listFakultas;
    }

    public void setListFakultas(List<MFakultas> listFakultas) {
        this.listFakultas = listFakultas;
    }

    public List<MJurusan> getListJurusan() {
        return listJurusan;
    }

    public void setListJurusan(List<MJurusan> listJurusan) {
        this.listJurusan = listJurusan;
    }

    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }

    public MJurusan getTempJurusan() {
        return tempJurusan;
    }

    public void setTempJurusan(MJurusan tempJurusan) {
        this.tempJurusan = tempJurusan;
    }
    



    
}
