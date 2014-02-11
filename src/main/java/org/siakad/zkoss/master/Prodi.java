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
public class Prodi extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    ProdiDaoInter prodiDao = (ProdiDaoInter) appContext.getBean("ProdiDaoBean");
    JurusanDaoInter jurusanDao = (JurusanDaoInter) appContext.getBean("JurusanDaoBean");
    
    //Entity or Variable utama
    private List<MProdi> listProdi = new ArrayList<MProdi>();
    private MProdi prodi = new MProdi();
    private MProdi tempProdi = new MProdi(); //temp digunakan untuk: Tambah Baru
    //ComboBox dll
    private List<MFakultas> listFakultas = new ArrayList<MFakultas>();
    private List<MJurusan> listJurusan = new ArrayList<MJurusan>();

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
        listProdi = prodiDao.getAll();        
        listJurusan = jurusanDao.getAll();
        
        prodi = new MProdi();
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listProdi.size() > 0)
            prodi = listProdi.get(0);
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
   
    @NotifyChange({"prodi", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempProdi(getProdi());
        prodi = new MProdi();
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
                            /* TUNGGU SAMPAI KETAHUAN APA AJA YANG LINK KEBAWAH DENGAN MATKUL*/
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Kurikulum (Karena dia adalah foreint key kurikulum)
                            if (prodi.getPegawaiSet().size() >0 || prodi.getMhsSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut masih dipakai oleh tabel PEGAWAI atau MAHASISWA", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase

                                //prodiDao.delete(prodi);
                                //Hapus di tabel 
                                listProdi.remove(prodi); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                prodi = new MProdi();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, Prodi.this, "listProdi");        
                                BindUtils.postNotifyChange(null, null, Prodi.this, "prodi");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            }
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    @NotifyChange({"listProdi", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        prodiDao.saveOrUpdate(prodi);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listProdi.add(prodi);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"prodi", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setProdi(getTempProdi()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idProdi").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaProdi").getValue());        
        validateCombo1(vc, (MJurusan)beanProps.get("jurusan").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (prodiDao.findById(kode) != null) {
                MProdi temp = new MProdi();
                temp = prodiDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaProdi());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, MJurusan f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo1", "Kode Fakultas tidak boleh Kosong");
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

    public List<MFakultas> getListFakultas() {
        return listFakultas;
    }

    public void setListFakultas(List<MFakultas> listFakultas) {
        this.listFakultas = listFakultas;
    }

    public List<MProdi> getListProdi() {
        return listProdi;
    }

    public void setListProdi(List<MProdi> listProdi) {
        this.listProdi = listProdi;
    }

    public MProdi getProdi() {
        return prodi;
    }

    public void setProdi(MProdi prodi) {
        this.prodi = prodi;
    }

    public MProdi getTempProdi() {
        return tempProdi;
    }

    public void setTempProdi(MProdi tempProdi) {
        this.tempProdi = tempProdi;
    }

    public List<MJurusan> getListJurusan() {
        return listJurusan;
    }

    public void setListJurusan(List<MJurusan> listJurusan) {
        this.listJurusan = listJurusan;
    }


    
}
