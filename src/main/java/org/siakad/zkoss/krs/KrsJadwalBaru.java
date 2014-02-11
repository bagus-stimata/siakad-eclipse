/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.krs;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author yhawin
 */

public class KrsJadwalBaru extends AbstractValidator {
    
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    
    KrsJadwalDaoInter tKrsJadwalDao = (KrsJadwalDaoInter) appContext.getBean("KrsJadwalDaoBean");
    ProdiDaoInter prodiDao = (ProdiDaoInter) appContext.getBean("ProdiDaoBean");
    PegawaiDaoInter pegawaiDao = (PegawaiDaoInter) appContext.getBean("PegawaiDaoBean");
    MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
    KuliahJamDaoInter kuliahJamDao = (KuliahJamDaoInter) appContext.getBean("KuliahJamDaoBean");
    KuliahRuangDaoInter kuliahRuangDao = (KuliahRuangDaoInter) appContext.getBean("KuliahRuangDaoBean");
    
    
    private List<TKrsJadwal> listKrsJadwal= new ArrayList<TKrsJadwal>();    
    private TKrsJadwal tKrsJadwal = new TKrsJadwal();
    private TKrsJadwal tempKrsJadwal=new TKrsJadwal(); //temp digunakan untuk: Tambah Baru
    private TKrsJadwal cariKrsJadwal = new TKrsJadwal();
    //ComboBox dll
    private List<MProdi> listProdi = new ArrayList<MProdi>();
    private List<MPegawai> listPegawai = new ArrayList<MPegawai>();
    private List<MMatkul> listMatkul = new ArrayList<MMatkul>();
    private List<MKuliahJam> listKuliahJam = new ArrayList<MKuliahJam>();
    private List<MKuliahRuang> listKuliahRuang = new ArrayList<MKuliahRuang>();
 
  
    public String[] getListHari() {
        String [] hari = {"","Minggu", "Senin", "Selasa", "Rabo", "Kamis", "Jumat", "Sabtu"};
        return hari;
    }
    public Boolean[] getListGenapGasal() {
        Boolean [] genapGasal = {false, true};
        return genapGasal;
    }
    public List<Integer> getListTahunAjaran(){
        List<Integer> lst = new ArrayList<Integer>();
        Calendar cal = Calendar.getInstance();
        int tahunSekarang = cal.get(Calendar.YEAR);
        for (int i= tahunSekarang; i>tahunSekarang-10; i--) {
            lst.add(i);
        }
        return lst;
    }

    
    private MMatkul cariMatkulCmb1 = new MMatkul();
    private MPegawai cariPegawaiCmb2 = new MPegawai();
    

    //Entity or Variable Util atau pendukung
    private Boolean addNew=false;
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
        listKrsJadwal = tKrsJadwalDao.getAll();        
        //listProdi = prodiDao.getAll();
        listMatkul = matkulDao.getAll();
        listPegawai = pegawaiDao.getAll();
        listKuliahJam = kuliahJamDao.getAll();
        listKuliahRuang = kuliahRuangDao.getAll();
        
        //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
        if (listKrsJadwal.size() > 0)
            tKrsJadwal = listKrsJadwal.get(0);
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
   
    @NotifyChange({"tKrsJadwal", "addNew", "selectedTab", "resetInputIcon"})
    @Command("baru")
    public void baru(){
        setTempKrsJadwal(gettKrsJadwal());
        tKrsJadwal = new TKrsJadwal();
        setResetInputIcon(true);      
        selectedTab=0;        
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
                            /*
                            //OK is clicked
                            //1. Cari apakah kode dipakai di tabel Program Studi (Karena dia adalah foreint key kurikulum)
                            if (tKrsJadwal.getProdiSet().size()>0) {       
                                  Messagebox.show("Kode Kelompok tersebut sudah dipakai oleh tabel PROGRAM STUDI", "Warning", 
                                          Messagebox.OK, Messagebox.EXCLAMATION);
                            } else {
                                //Hapus didatabase
                                tKrsJadwalDao.delete(tKrsJadwal);
                                //Hapus di tabel 
                                listKrsJadwal.remove(tKrsJadwal); //Notify Change gak berhasil disini
                                //Kosongkan isi teksbox
                                tKrsJadwal = new TKrsJadwal();
                                //Karena pake notifyChange Annotiotion Biasa tidak bisa maka pake cara ini BindUtils.postNotifyChange
                                BindUtils.postNotifyChange(null, null, KrsJadwal.this, "listKrsJadwal");        
                                BindUtils.postNotifyChange(null, null, KrsJadwal.this, "tKrsJadwal");        
                                
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
    
    @NotifyChange({"listKrsJadwal", "addNew", "resetInputIcon"})
    @Command("simpan")
    public void simpan(){
        //Messagebox.show(tKrsJadwal.getKrsJadwalKel().getDeskripsi());
        //simpan di database
        tKrsJadwalDao.saveOrUpdate(tKrsJadwal);
        
        //Jika tombol tambah maka list(tabel) ditambah isinya
        if (addNew==true)
            listKrsJadwal.add(tKrsJadwal);
        
       setResetInputIcon(false);
       setAddNew(false);
        Clients.showNotification("Simpan Berhasil", "info", null, "top_right", 3000);
    }
    
    @NotifyChange({"tKrsJadwal", "addNew","resetInputIcon"})    
    @Command("batal")
    public void batal(){
        //Jika addNew==true maka isi lagi dari yang disimpan di temporer supaya ada isinya lagi
        setResetInputIcon(false);
        if (addNew==true) settKrsJadwal(getTempKrsJadwal()); //
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
    }
    
    @NotifyChange({"tKrsJadwal", "selectedTab", "addNew"})    
    @Command("selesai")
    public void selesai(){
        if (addNew==true) settKrsJadwal(getTempKrsJadwal()); //

        setResetInputIcon(false);
        setAddNew(false); //apapun itu buat supaya tambah baru selalu kondisi false
        selectedTab = 1;
    }
    //Fungsi-fungsi tombol dari list NAVIGASI EDIT pada LIST
    //Untuk Tambah kita pake aja tombol tambah namun dipaksa agar selectedIndes tab=0
    @NotifyChange({"tKrsJadwal","selectedTab"})
    @Command("ubahList")
    public void ubahList(@BindingParam("parameter1") TKrsJadwal m){
        
        tKrsJadwal = new TKrsJadwal();
        tKrsJadwal =  m;
        selectedTab=0;
    }

    @NotifyChange("listKrsJadwal")
    @Command("cari")
    public void cari(){
 /*       
        //Jaga jaga supaya tidak terjadi null
        if (cariKrsJadwal.getNip()==null) cariKrsJadwal.setNip("");
        if (cariKrsJadwal.getNidn()==null) cariKrsJadwal.setNidn("");
        if (cariKrsJadwal.getNamaKrsJadwal()==null) cariKrsJadwal.setNamaKrsJadwal("");
        
        if (cariKrsJadwal.getNip().equals("") && cariKrsJadwal.getNidn().equals("") && cariKrsJadwal.getNamaKrsJadwal().equals("")) {
            listKrsJadwal = tKrsJadwalDao.getAll();
        } else {
            
            cariKrsJadwal.setNip("%"+ cariKrsJadwal.getNip() + "%");
            cariKrsJadwal.setNidn("%"+ cariKrsJadwal.getNidn() + "%");
            cariKrsJadwal.setNamaKrsJadwal("%"+ cariKrsJadwal.getNamaKrsJadwal() + "%");
            listKrsJadwal = tKrsJadwalDao.findByManualCriteria(cariKrsJadwal);
        }
        * 
        */
        
    }
    @NotifyChange("listKrsJadwal")
    @Command("reload")
    public void reload(){
        listKrsJadwal = tKrsJadwalDao.getAll();
    }
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (Integer)beanProps.get("idJadwal").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaKelas").getValue());   
       
        validateTahunAjaran(vc, (Integer)beanProps.get("tahunAjaran").getValue());
        validateSmtGenapGasal(vc, (Boolean)beanProps.get("smtGenapGasal").getValue());
        
        validateCombo1(vc, (MMatkul)beanProps.get("matkul").getValue());        
        validateCombo2(vc, (MPegawai)beanProps.get("pegawai").getValue());        
        validateCombo3(vc, (MKuliahRuang)beanProps.get("kuliahRuang").getValue());        
        
        validateCombo4(vc, (MKuliahJam)beanProps.get("jamMulai").getValue());        
        validateCombo4(vc, (MKuliahJam)beanProps.get("jamSelesai").getValue());     
        validateKapasitas(vc, (Integer)beanProps.get("kapasitas").getValue());   
        
        validateHari(vc, (Integer)beanProps.get("hari").getValue());
        validateJamMulai(vc, (MKuliahJam)beanProps.get("jamMulai").getValue());
        validateJamSelesai(vc, (MKuliahJam)beanProps.get("jamSelesai").getValue());
       
        
    }
   
    @NotifyChange("listMatkul")
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
        listMatkul = matkulDao.findByManualCriteria(cariMatkulCmb1);
    }
    
    @NotifyChange("listPegawai")
    @Command("cariCmb2")
    public void cariCmb2(){
        
        //Jaga jaga supaya tidak terjadi null
        if (cariPegawaiCmb2.getIdPegawai()==null) cariPegawaiCmb2.setIdPegawai("");
        if (cariPegawaiCmb2.getNamaPegawai()==null) cariPegawaiCmb2.setNamaPegawai("");

        cariPegawaiCmb2.setNip("%");
        cariPegawaiCmb2.setNidn("%");
        MProdi k = new MProdi();
        k.setIdProdi("%");

        cariPegawaiCmb2.setProdi(k);
        listPegawai = pegawaiDao.findByManualCriteria(cariPegawaiCmb2);
        
    }
    
    //@NotifyChange("tKrsJadwal")
    @Command("pilihRuang")
    public void pilihRuang(){
      tKrsJadwal.setKapasitas(tKrsJadwal.getKuliahRuang().getKapasitas());
      
    }

    private void validateKode(ValidationContext ctx, Integer  kode, boolean tambahBaru) {
                
        if (kode==null) kode=0; //Jaga jaga supaya tidak ada error karena null
        if(kode==0) {
            this.addInvalidMessage(ctx, "err_kode", "Kode tidak boleh kosong");
        } else if (tambahBaru){ //Hanya berlalu pada saat tambah aja
            //1. Cek Kode
            if (tKrsJadwalDao.findById(kode) != null) {
                TKrsJadwal temp = new TKrsJadwal();
                temp = tKrsJadwalDao.findById(kode);
                this.addInvalidMessage(ctx, "err_kode", "KODE TERSEBUT SUDAH TERPAKAI oleh : " + 
                        temp.getNamaKelas());
                
            }            
        }
    }
    
    private void validateDeskripsi(ValidationContext ctx, String descripsi) {
        if (descripsi==null) descripsi="";//Jaga-jaga supaya tidak ada erro karena null
        if(descripsi.trim().equals("")) {
            this.addInvalidMessage(ctx, "err_deskripsi", "Deskripsi tidak boleh kosong");
        } 
    }
    private void validateCombo1(ValidationContext ctx, MMatkul f) {
        if(f==null) {
            this.addInvalidMessage(ctx, "err_matkul", "MATA KULIAH tidak boleh Kosong");
        } 
    }
    private void validateCombo2(ValidationContext ctx, MPegawai f) {
        if(f==null) {
            this.addInvalidMessage(ctx, "err_dosen", "DOSEN tidak boleh Kosong");
        } 
    }
    private void validateCombo3(ValidationContext ctx, MKuliahRuang f) {
        if(f==null) {
            this.addInvalidMessage(ctx, "err_ruang", "RUANG KELAS tidak boleh Kosong");
        } 
    }
    private void validateCombo4(ValidationContext ctx, MKuliahJam f) {
        if(f==null) {
            this.addInvalidMessage(ctx, "err_jam", "JAM MULAI dan JAM SELESAI tidak boleh Kosong");
        } 
    }
    private void validateKapasitas(ValidationContext ctx, Integer kapasitas) {
        if (kapasitas==null) {
            kapasitas =0;
        }
        if(kapasitas==0) {
            this.addInvalidMessage(ctx, "err_kapasitas", "KAPASITAS TIDAK BOLEH KOSONG");
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

    public TKrsJadwal getCariKrsJadwal() {
        return cariKrsJadwal;
    }

    public void setCariKrsJadwal(TKrsJadwal cariKrsJadwal) {
        this.cariKrsJadwal = cariKrsJadwal;
    }

    public MMatkul getCariMatkulCmb1() {
        return cariMatkulCmb1;
    }

    public void setCariMatkulCmb1(MMatkul cariMatkulCmb1) {
        this.cariMatkulCmb1 = cariMatkulCmb1;
    }

    public MPegawai getCariPegawaiCmb2() {
        return cariPegawaiCmb2;
    }

    public void setCariPegawaiCmb2(MPegawai cariPegawaiCmb2) {
        this.cariPegawaiCmb2 = cariPegawaiCmb2;
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

    public TKrsJadwal gettKrsJadwal() {
        return tKrsJadwal;
    }

    public void settKrsJadwal(TKrsJadwal tKrsJadwal) {
        this.tKrsJadwal = tKrsJadwal;
    }


    public KuliahJamDaoInter getKuliahJamDao() {
        return kuliahJamDao;
    }

    public void setKuliahJamDao(KuliahJamDaoInter kuliahJamDao) {
        this.kuliahJamDao = kuliahJamDao;
    }

    public KuliahRuangDaoInter getKuliahRuangDao() {
        return kuliahRuangDao;
    }

    public void setKuliahRuangDao(KuliahRuangDaoInter kuliahRuangDao) {
        this.kuliahRuangDao = kuliahRuangDao;
    }

    public List<TKrsJadwal> getListKrsJadwal() {
        return listKrsJadwal;
    }

    public void setListKrsJadwal(List<TKrsJadwal> listKrsJadwal) {
        this.listKrsJadwal = listKrsJadwal;
    }

    public List<MKuliahJam> getListKuliahJam() {
        return listKuliahJam;
    }

    public void setListKuliahJam(List<MKuliahJam> listKuliahJam) {
        this.listKuliahJam = listKuliahJam;
    }

    public List<MKuliahRuang> getListKuliahRuang() {
        return listKuliahRuang;
    }

    public void setListKuliahRuang(List<MKuliahRuang> listKuliahRuang) {
        this.listKuliahRuang = listKuliahRuang;
    }

    public List<MMatkul> getListMatkul() {
        return listMatkul;
    }

    public void setListMatkul(List<MMatkul> listMatkul) {
        this.listMatkul = listMatkul;
    }

    public List<MPegawai> getListPegawai() {
        return listPegawai;
    }

    public void setListPegawai(List<MPegawai> listPegawai) {
        this.listPegawai = listPegawai;
    }

    public List<MProdi> getListProdi() {
        return listProdi;
    }

    public void setListProdi(List<MProdi> listProdi) {
        this.listProdi = listProdi;
    }

    public MatkulDaoInter getMatkulDao() {
        return matkulDao;
    }

    public void setMatkulDao(MatkulDaoInter matkulDao) {
        this.matkulDao = matkulDao;
    }

    public PegawaiDaoInter getPegawaiDao() {
        return pegawaiDao;
    }

    public void setPegawaiDao(PegawaiDaoInter pegawaiDao) {
        this.pegawaiDao = pegawaiDao;
    }

    public ProdiDaoInter getProdiDao() {
        return prodiDao;
    }

    public void setProdiDao(ProdiDaoInter prodiDao) {
        this.prodiDao = prodiDao;
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

    public KrsJadwalDaoInter gettKrsJadwalDao() {
        return tKrsJadwalDao;
    }

    public void settKrsJadwalDao(KrsJadwalDaoInter tKrsJadwalDao) {
        this.tKrsJadwalDao = tKrsJadwalDao;
    }

    public TKrsJadwal getTempKrsJadwal() {
        return tempKrsJadwal;
    }

    public void setTempKrsJadwal(TKrsJadwal tempKrsJadwal) {
        this.tempKrsJadwal = tempKrsJadwal;
    }

    private void validateTahunAjaran(ValidationContext vc, Integer integer) {
        if (integer==null) {
            this.addInvalidMessage(vc, "err_tahun_ajaran", "Tahun Ajaran Tidak Boleh Kosong");
        }
    }

    private void validateSmtGenapGasal(ValidationContext vc, Boolean aBoolean) {
        if (aBoolean==null) {
            this.addInvalidMessage(vc, "err_smt_genap_gasal", "Semester genap gasal tidak boleh Kosong");
        }
    }

    private void validateHari(ValidationContext vc, Integer integer) {
        if (integer==null) {
            this.addInvalidMessage(vc, "err_hari", "Hari tidak boleh Kosong");
        } else if (integer.equals(0)) {
            this.addInvalidMessage(vc, "err_hari", "Anda belum memilih hari");        
        }
    }

    private void validateJamMulai(ValidationContext vc, MKuliahJam mKuliahJam) {
        if (mKuliahJam==null) {
            this.addInvalidMessage(vc, "err_jam_mulai", "Jam Mulai tidak boleh Kosong");
        }
    }

    private void validateJamSelesai(ValidationContext vc, MKuliahJam mKuliahJam) {
        if (mKuliahJam==null) {
            this.addInvalidMessage(vc, "err_jam_selesai", "Jam Selesai tidak boleh Kosong");
        }
    }


    
    
}
