/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.MatkulDaoInter;
import org.config.spring.hibernate.dao.MatkulKelDaoInter;
import org.config.spring.hibernate.dao.MatkulKurDaoInter;
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

public class Matkul extends AbstractValidator {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
    MatkulKurDaoInter matkulKurDao = (MatkulKurDaoInter) appContext.getBean("MatkulKurDaoBean"); 
    MatkulKelDaoInter matkulKelDao = (MatkulKelDaoInter) appContext.getBean("MatkulKelDaoBean"); 
            
    //Entity or Variable utama
    private List<MMatkul> listMatkul;
    private MMatkul matkul;
    private MMatkul tempMatkul; //temp digunakan untuk: Tambah Baru
    private MMatkul cariMatkul;
    
    private MMatkulKur cariMatkulKur;
    
    //ComboBox dll
    private List<MMatkulKur> listMatkulKur;
    private List<MMatkulKel> listMatkulKel;
    private MMatkulKur selectedKur= new MMatkulKur();
    private MMatkulKel selectedKel= new MMatkulKel();
    
    private List<MMatkul> listMatkulCmb;
    private MMatkul cariMatkulCmb1;
    
    //Entity or Variable Util atau pendukung
    private boolean addNew;
    private int selectedTab=1;
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private String imgAktifUrl = "/widgets/images/navigation/12x12/Apply.png";
    private String imgNonAktifUrl = "/widgets/images/navigation/12x12/Problem.png";
    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose
      
    @Init
    public void init(){
        //Inisialisasi isi utama
        listMatkul = matkulDao.getAll();
        
        listMatkulCmb = new ArrayList<MMatkul>(listMatkul);       
        listMatkulKur = matkulKurDao.getAll();
        listMatkulKel = matkulKelDao.getAll();
        
        matkul = new MMatkul();
        cariMatkul = new MMatkul();
        tempMatkul = new MMatkul();
        cariMatkulKur = new  MMatkulKur();
        cariMatkulCmb1 = new MMatkul();
        
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listMatkul.size() > 0)
            matkul = listMatkul.get(0);
        //Mula-mula tombol tambah adalah false
        setAddNew(false);
    }
    
    @AfterCompose
    public void afterCompose(){
    }
    
    @NotifyChange({"selectedKur"})
    @Command("selectKur")
    public void selectKur(@BindingParam("selectedKur")  MMatkulKur sk){
        selectedKur = new MMatkulKur();
        selectedKur = sk;
    }
    @NotifyChange({"selectedKel"})
    @Command("selectKel")
    public void selectKel(@BindingParam("selectedKel")  MMatkulKel sk){
        selectedKel = new MMatkulKel();
        selectedKel = sk;
    }
    @NotifyChange({"addNew","resetInputIcon"})
    @Command("selectList")
    public void select(){
        
        setResetInputIcon(false);
        setAddNew(false);
    }
    
    @NotifyChange({"matkul", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        selectedTab=0;
        setTempMatkul(getMatkul());
        matkul = new MMatkul();
        setResetInputIcon(true);
        setAddNew(true);
    }
    
    @Command("hapus") 
    public void hapus(@BindingParam("parameter1") final MMatkul m){
        
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
                                    setMatkul(m);
                                //Hapus didatabase
                                //pegawaiDao.delete(pegawai);
                                //Hapus di tabel 
                                listMatkul.remove(matkul); //Notify Change gak berhasil disini jadi harus pake BindUtils
                                //Kosongkan isi teksbox
                                matkul = new MMatkul();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, Matkul.this, "listMatkul");        
                                BindUtils.postNotifyChange(null, null, Matkul.this, "matkul");        
                                
                                Clients.showNotification("Berhasil dihapus", "info", null, "top_right", 3000);
                            //}
                            
                        }else if(Messagebox.ON_CANCEL.equals(e.getName())){
                        //Cancel is clicked
                        }
                    }
                 }
        );
        
    }
    
    
    @NotifyChange({"listMatkul", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(matkul.getMatkulKel().getDeskripsi());
        //simpan di database
        matkulDao.saveOrUpdate(matkul);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (isAddNew()==true)
            listMatkul.add(matkul);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"matkul", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (isAddNew()==true) setMatkul(getTempMatkul()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }

    @NotifyChange({"matkul", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (isAddNew()==true) setMatkul(getTempMatkul()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }

    //Fungsi-fungsi tombol dari list NAVIGASI EDIT pada LIST
    //Untuk Tambah kita pake aja tombol tambah namun dipaksa agar selectedIndes tab=0
    @NotifyChange({"matkul","selectedTab"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") MMatkul m){
        
        matkul = new MMatkul();
        matkul =  m;
        selectedTab=0;
    }
    
    @NotifyChange("listMatkul")
    @Command("cari")
    public void cari(){
        
        //Jaga jaga supaya tidak terjadi null
        if (cariMatkul.getIdMatkul()==null) cariMatkul.setIdMatkul("");
        if (cariMatkul.getNamaMatkul()==null) cariMatkul.setNamaMatkul("");
        if (cariMatkul.getSks()==null) cariMatkul.setSks(0);
        if (cariMatkul.getSemester()==null) cariMatkul.setSemester(0);       
        //Ingat Cuma ID yang kita isi lhoo
        if (cariMatkulKur.getIdKurikulum()==null) cariMatkulKur.setIdKurikulum("");
        
        if (cariMatkul.getIdMatkul().equals("") && cariMatkul.getNamaMatkul().equals("") && 
                cariMatkul.getSks().equals(0) && cariMatkul.getSemester().equals(0) && 
                cariMatkulKur.getIdKurikulum().equals("")) {
            
            listMatkul = matkulDao.getAll();    
        } else {
            
            cariMatkul.setIdMatkul("%"+ cariMatkul.getIdMatkul() + "%");
            cariMatkul.setNamaMatkul("%"+ cariMatkul.getNamaMatkul() + "%");
            
            MMatkulKur kur = new MMatkulKur();
            kur.setIdKurikulum("%" + getCariMatkulKur().getIdKurikulum() +"%");
            
            cariMatkul.setMatkulKur(kur);
            listMatkul = matkulDao.findByManualCriteria(cariMatkul);
        }
    }
    
    @NotifyChange("listMatkulCmb")
    @Command("cariCmb1")
    public void cariCmb1(){
        
        //Jaga jaga supaya tidak terjadi null
        if (cariMatkulCmb1.getIdMatkul()==null) cariMatkulCmb1.setIdMatkul("");
        if (cariMatkulCmb1.getNamaMatkul()==null) cariMatkulCmb1.setNamaMatkul("");
        if (cariMatkulCmb1.getSks()==null) cariMatkulCmb1.setSks(0);
        if (cariMatkulCmb1.getSemester()==null) cariMatkulCmb1.setSemester(0);       
        
        cariMatkulCmb1.setIdMatkul("%"+ cariMatkulCmb1.getIdMatkul() + "%");
        cariMatkulCmb1.setNamaMatkul("%"+ cariMatkulCmb1.getNamaMatkul() + "%");

        MMatkulKur kur = new MMatkulKur();
        kur.setIdKurikulum("%");

        cariMatkulCmb1.setMatkulKur(kur);
        listMatkulCmb = matkulDao.findByManualCriteria(cariMatkulCmb1);
    }
    
    @Command("coba")
    public void coba(){
         Messagebox.show(cariMatkulKur.getIdKurikulum());
        
    }
    
    @NotifyChange("listMatkul")
    @Command("reload")
    public void reload(){
        listMatkul = matkulDao.getAll();
    }
    @NotifyChange("listMatkulCmb")
    @Command("reloadCmb1")
    public void reloadCmb1(){
        listMatkulCmb = matkulDao.getAll();
    }
    

    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idMatkul").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaMatkul").getValue());    
        validateComboKurikulum(vc, (MMatkulKur)beanProps.get("matkulKur").getValue());        
        
    }

    private void validateKode(ValidationContext ctx, String kode, boolean tambahBaru) {
                
        if (kode==null) kode=""; //Jaga jaga supaya tidak ada error karena null
        if(kode.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (matkulDao.findById(kode) != null) {
                MMatkul temp = new MMatkul();
                temp = matkulDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + temp.getNamaMatkul());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateComboKurikulum(ValidationContext ctx, MMatkulKur f) {
        //Kalau kosong pasti null bukan ""
        if(f==null) {
            this.addInvalidMessage(ctx, "err_combo_kurikulum", "KURIKULUM TIDAK BOLEH KOSONG");
        } 
    }
    
    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
    }

    public List<MMatkul> getListMatkulCmb() {
        return listMatkulCmb;
    }

    public void setListMatkulCmb(List<MMatkul> listMatkulCmb) {
        this.listMatkulCmb = listMatkulCmb;
    }

    public List<MMatkul> getListMatkul() {
        return listMatkul;
    }

    public void setListMatkul(List<MMatkul> listMatkul) {
        this.listMatkul = listMatkul;
    }

    public MMatkul getMatkul() {
        return matkul;
    }

    public void setMatkul(MMatkul matkul) {
        this.matkul = matkul;
    }

    public MMatkul getTempMatkul() {
        return tempMatkul;
    }

    public void setTempMatkul(MMatkul tempMatkul) {
        this.tempMatkul = tempMatkul;
    }

    public List<MMatkulKur> getListMatkulKur() {
        return listMatkulKur;
    }

    public void setListMatkulKur(List<MMatkulKur> listMatkulKur) {
        this.listMatkulKur = listMatkulKur;
    }

    public MMatkulKur getSelectedKur() {
        return selectedKur;
    }

    public void setSelectedKur(MMatkulKur selectedKur) {
        this.selectedKur = selectedKur;
    }

    public List<MMatkulKel> getListMatkulKel() {
        return listMatkulKel;
    }

    public void setListMatkulKel(List<MMatkulKel> listMatkulKel) {
        this.listMatkulKel = listMatkulKel;
    }

    public MMatkulKel getSelectedKel() {
        return selectedKel;
    }

    public void setSelectedKel(MMatkulKel selectedKel) {
        this.selectedKel = selectedKel;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
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

    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public String getImgFavouritesUrl() {
        return imgFavouritesUrl;
    }

    public void setImgFavouritesUrl(String imgFavouritesUrl) {
        this.imgFavouritesUrl = imgFavouritesUrl;
    }


    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }

    public String getImgAktifUrl() {
        return imgAktifUrl;
    }

    public void setImgAktifUrl(String imgAktifUrl) {
        this.imgAktifUrl = imgAktifUrl;
    }

    public String getImgNonAktifUrl() {
        return imgNonAktifUrl;
    }

    public void setImgNonAktifUrl(String imgNonAktifUrl) {
        this.imgNonAktifUrl = imgNonAktifUrl;
    }

    public MMatkul getCariMatkul() {
        return cariMatkul;
    }

    public void setCariMatkul(MMatkul cariMatkul) {
        this.cariMatkul = cariMatkul;
    }

    public MMatkulKur getCariMatkulKur() {
        return cariMatkulKur;
    }

    public void setCariMatkulKur(MMatkulKur cariMatkulKur) {
        this.cariMatkulKur = cariMatkulKur;
    }

    public MMatkul getCariMatkulCmb1() {
        return cariMatkulCmb1;
    }

    public void setCariMatkulCmb1(MMatkul cariMatkulCmb1) {
        this.cariMatkulCmb1 = cariMatkulCmb1;
    }


    

    
}
