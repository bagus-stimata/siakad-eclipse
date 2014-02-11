/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.krs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.*;
import org.config.spring.hibernate.model.*;
import org.springframework.context.ApplicationContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author yhawin
 */
public class KrsWaliAprove {
    //Inisialisasi Dao
    ApplicationContext appContext = ApplicationContextProvider.getInstance().getApplicationContext();
    CmsUsersDaoInter cmsUsersDao = (CmsUsersDaoInter) appContext.getBean("CmsUsersDaoBean");
    MhsDaoInter mhsDao = (MhsDaoInter) appContext.getBean("MhsDaoBean");
    SysvarDaoInter sysvarDao = (SysvarDaoInter) appContext.getBean("SysvarDaoBean");
    
    MatkulDaoInter matkulDao = (MatkulDaoInter) appContext.getBean("MatkulDaoBean");
    KrsHeaderDaoInter krsHeaderDao = (KrsHeaderDaoInter) appContext.getBean("KrsHeaderDaoBean");
    KrsDetailDaoInter krsDetailDao = (KrsDetailDaoInter) appContext.getBean("KrsDetailDaoBean");
    KrsJadwalDaoInter krsJadwalDao = (KrsJadwalDaoInter) appContext.getBean("KrsJadwalDaoBean");
    
    private List<MMatkul> listMatkul = new ArrayList<MMatkul>();
    private List<TKrsHeader> listTKrsHeaders = new ArrayList<TKrsHeader>();
    private TKrsHeader krsHeaders = new TKrsHeader();
    
    private List<TKrsDetail> listKrsDetails = new ArrayList<TKrsDetail>();
    private TKrsDetail krsDetails = new TKrsDetail();
    
    private MMatkul matkul = new MMatkul();
    private MMatkul tempMatkul = new MMatkul(); //temp digunakan untuk: Tambah Baru
    private MMatkul cariMatkul= new MMatkul();
    private MMatkul lihatJadwalMatkul = new MMatkul(); 
    private List<TKrsJadwal> listKrsJadwal = new ArrayList<TKrsJadwal>();
    
    private List<MMhs> listMhs = new ArrayList<MMhs>();
    private MMhs cariMhs = new MMhs();
    private MProdi cariProdi = new MProdi();
    
    private MMhs mhs = new MMhs();
    
    private int jumlahSks = 0; //default 0
    private int maxSks = 10; //Ini minimal untuk menjaga jika ada parameter sistem yang TERHAPUS
    private double ipSemesterLalu = 0.0;
    
    private int tahunAkademik=0; //default 0
    private int semesterGenapGanjil=1; //dafault 0 >> False:Genap:0 >> True:Ganjil:1
    private Boolean genapGasalBol=true; //False:Genap:0 >> True:Ganjil:1
    private int semester=0; //default 0
    
    private int belumSudahApprove=0;
    private int tolakSetuju=0;
    //Otentikasi
    private boolean granted=true; //default TAK BALIK true --> Jika ada satu saja yang tidak terpenuhi TIDAK BISA DIBUKA
    private boolean krsBuka =true; //default krsBuka
    //ComboBox dll
    
    private boolean showPilihJadwal=false; //defalut false
    //private boolean showLihatJadwal=false;
    //Entity or Variable Util atau pendukung
    //private boolean addNew; 
    //private int selectedTab=1; 
    
    private String imgErrorUrl = "/widgets/images/navigation/12x12/Error.png";
    private String imgOkUrl = "/widgets/images/navigation/12x12/OK.png";
    private String imgFavouritesUrl = "/widgets/images/navigation/12x12/Favourites.png";
    private String imgAktifUrl = "/widgets/images/navigation/12x12/Apply.png";
    private String imgNonAktifUrl = "/widgets/images/navigation/12x12/Problem.png";
    private String imgTungguPersetujuanUrl="/widgets/images/navigation/14x14/Question.png";
    private String imgDitolakUrl = "/widgets/images/navigation/14x14/Red-star.png";
    private String imgDisetujuiUrl="/widgets/images/navigation/14x14/OK.png";
    private boolean resetInputIcon=false;
    //Urutan Construktor : 1. Konstruktor Standart Java, 2.Init, 3.AfterCompose

    public List<Integer> getListTahunAjaran(){
        
        List<Integer> lst = new ArrayList<Integer>();
        Calendar cal = Calendar.getInstance();
        int tahunIni = cal.get(Calendar.YEAR);
        for (int i=tahunIni; i>tahunIni-8 ; i--) {
            lst.add(i);
        }
        return lst;       
    }
    
    public String [] getListGenapGasal(){
        String [] lst= {"Genap", "Gasal"};
        return lst;
    }   
    public String [] getListBelumSudah(){
        String [] lst = {"Belum selesai", "Sudah", "Tampil Semua"};
        return lst;
    }
    public String [] getListTolakSetuju(){
        String [] lst = {"Tolak", "Setuju", "Tampil Semua"};
        return lst;
    }
     
    
    
    @Init
    public void init(){
        Sysvar sysvar = new Sysvar();
    //A. ATURAN
    //B. INISIALISASI
        //AKADEMIK
        //1. Tahun berjalan mengambil dari parameter sistem
       sysvar = sysvarDao.findById("AK001");
       if (sysvar ==null) {
           Messagebox.show("Parameter sistem AK001 --> TAHUN AKADEMIK BERJALAN belum ada");
           granted=false;
       } else {
           tahunAkademik = sysvar.getNilaiInt1();
       }
       sysvar = sysvarDao.findById("AK002");
       //2. Genap gasal berasal dari sistem
       if (sysvar ==null){
           Messagebox.show("Parameter sitem AK002 --> SEMESTER GENAP GASAL belum ada");
           granted=false;
       } else {
           if (sysvar.getNilaiBol1()==false) { //False:Genap:0
               semesterGenapGanjil =0;
               genapGasalBol = false;
           } else {
               semesterGenapGanjil=1;
              genapGasalBol =true;
           }
       }
       //3. BELUM APPROVE
        belumSudahApprove =2; //0.Belum, 1.Sudah, 2.Semua
       //4. TOLAK SETUJU
        tolakSetuju =2;//0.Belum, 1.Sudah, 2.Semua
        //MAHASISWA
        //a. Mahasiswa yang diload adalah Mahasiswa yang di WALI-i oleh dia dan yang SESUAI DENGAN PARAMETER atasnya
        //listMhs = mhsDao.findByManualCriteria(mhs);
        //KRS
        //a. MULA MULA KOSONG SAMPAI ADA MAHASISWA YANG DISELEKSI 
    //C. NILAI AWAL
    }
    @AfterCompose
    public void afterCompse(){
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public MMatkul getCariMatkul() {
        return cariMatkul;
    }

    public void setCariMatkul(MMatkul cariMatkul) {
        this.cariMatkul = cariMatkul;
    }

    public CmsUsersDaoInter getCmsUsersDao() {
        return cmsUsersDao;
    }

    public void setCmsUsersDao(CmsUsersDaoInter cmsUsersDao) {
        this.cmsUsersDao = cmsUsersDao;
    }

    public Boolean getGenapGasalBol() {
        return genapGasalBol;
    }

    public void setGenapGasalBol(Boolean genapGasalBol) {
        this.genapGasalBol = genapGasalBol;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public String getImgAktifUrl() {
        return imgAktifUrl;
    }

    public void setImgAktifUrl(String imgAktifUrl) {
        this.imgAktifUrl = imgAktifUrl;
    }

    public String getImgDisetujuiUrl() {
        return imgDisetujuiUrl;
    }

    public void setImgDisetujuiUrl(String imgDisetujuiUrl) {
        this.imgDisetujuiUrl = imgDisetujuiUrl;
    }

    public String getImgDitolakUrl() {
        return imgDitolakUrl;
    }

    public void setImgDitolakUrl(String imgDitolakUrl) {
        this.imgDitolakUrl = imgDitolakUrl;
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

    public String getImgTungguPersetujuanUrl() {
        return imgTungguPersetujuanUrl;
    }

    public void setImgTungguPersetujuanUrl(String imgTungguPersetujuanUrl) {
        this.imgTungguPersetujuanUrl = imgTungguPersetujuanUrl;
    }

    public double getIpSemesterLalu() {
        return ipSemesterLalu;
    }

    public void setIpSemesterLalu(double ipSemesterLalu) {
        this.ipSemesterLalu = ipSemesterLalu;
    }

    public int getJumlahSks() {
        return jumlahSks;
    }

    public void setJumlahSks(int jumlahSks) {
        this.jumlahSks = jumlahSks;
    }

    public boolean isKrsBuka() {
        return krsBuka;
    }

    public void setKrsBuka(boolean krsBuka) {
        this.krsBuka = krsBuka;
    }

    public KrsDetailDaoInter getKrsDetailDao() {
        return krsDetailDao;
    }

    public void setKrsDetailDao(KrsDetailDaoInter krsDetailDao) {
        this.krsDetailDao = krsDetailDao;
    }

    public TKrsDetail getKrsDetails() {
        return krsDetails;
    }

    public void setKrsDetails(TKrsDetail krsDetails) {
        this.krsDetails = krsDetails;
    }

    public KrsHeaderDaoInter getKrsHeaderDao() {
        return krsHeaderDao;
    }

    public void setKrsHeaderDao(KrsHeaderDaoInter krsHeaderDao) {
        this.krsHeaderDao = krsHeaderDao;
    }

    public TKrsHeader getKrsHeaders() {
        return krsHeaders;
    }

    public void setKrsHeaders(TKrsHeader krsHeaders) {
        this.krsHeaders = krsHeaders;
    }

    public KrsJadwalDaoInter getKrsJadwalDao() {
        return krsJadwalDao;
    }

    public void setKrsJadwalDao(KrsJadwalDaoInter krsJadwalDao) {
        this.krsJadwalDao = krsJadwalDao;
    }

    public MMatkul getLihatJadwalMatkul() {
        return lihatJadwalMatkul;
    }

    public void setLihatJadwalMatkul(MMatkul lihatJadwalMatkul) {
        this.lihatJadwalMatkul = lihatJadwalMatkul;
    }

    public List<TKrsDetail> getListKrsDetails() {
        return listKrsDetails;
    }

    public void setListKrsDetails(List<TKrsDetail> listKrsDetails) {
        this.listKrsDetails = listKrsDetails;
    }

    public List<TKrsJadwal> getListKrsJadwal() {
        return listKrsJadwal;
    }

    public void setListKrsJadwal(List<TKrsJadwal> listKrsJadwal) {
        this.listKrsJadwal = listKrsJadwal;
    }

    public List<MMatkul> getListMatkul() {
        return listMatkul;
    }

    public void setListMatkul(List<MMatkul> listMatkul) {
        this.listMatkul = listMatkul;
    }

    public List<TKrsHeader> getListTKrsHeaders() {
        return listTKrsHeaders;
    }

    public void setListTKrsHeaders(List<TKrsHeader> listTKrsHeaders) {
        this.listTKrsHeaders = listTKrsHeaders;
    }

    public MMatkul getMatkul() {
        return matkul;
    }

    public void setMatkul(MMatkul matkul) {
        this.matkul = matkul;
    }

    public MatkulDaoInter getMatkulDao() {
        return matkulDao;
    }

    public void setMatkulDao(MatkulDaoInter matkulDao) {
        this.matkulDao = matkulDao;
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

    public boolean isResetInputIcon() {
        return resetInputIcon;
    }

    public void setResetInputIcon(boolean resetInputIcon) {
        this.resetInputIcon = resetInputIcon;
    }
    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSemesterGenapGanjil() {
        return semesterGenapGanjil;
    }

    public void setSemesterGenapGanjil(int semesterGenapGanjil) {
        this.semesterGenapGanjil = semesterGenapGanjil;
    }
    public boolean isShowPilihJadwal() {
        return showPilihJadwal;
    }

    public void setShowPilihJadwal(boolean showPilihJadwal) {
        this.showPilihJadwal = showPilihJadwal;
    }

    public SysvarDaoInter getSysvarDao() {
        return sysvarDao;
    }

    public void setSysvarDao(SysvarDaoInter sysvarDao) {
        this.sysvarDao = sysvarDao;
    }

    public int getTahunAkademik() {
        return tahunAkademik;
    }

    public void setTahunAkademik(int tahunAkademik) {
        this.tahunAkademik = tahunAkademik;
    }

    public MMatkul getTempMatkul() {
        return tempMatkul;
    }

    public void setTempMatkul(MMatkul tempMatkul) {
        this.tempMatkul = tempMatkul;
    }

    public int getMaxSks() {
        return maxSks;
    }

    public void setMaxSks(int maxSks) {
        this.maxSks = maxSks;
    }

    public List<MMhs> getListMhs() {
        return listMhs;
    }

    public void setListMhs(List<MMhs> listMhs) {
        this.listMhs = listMhs;
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

    public int getBelumSudahApprove() {
        return belumSudahApprove;
    }

    public void setBelumSudahApprove(int belumSudahApprove) {
        this.belumSudahApprove = belumSudahApprove;
    }


    public int getTolakSetuju() {
        return tolakSetuju;
    }

    public void setTolakSetuju(int tolakSetuju) {
        this.tolakSetuju = tolakSetuju;
    }
    
    
}
