/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.krs;

import java.sql.Time;
import java.util.*;
import org.config.ApplicationContextProvider;
import org.config.spring.hibernate.dao.CmsUsersDaoInter;
import org.config.spring.hibernate.dao.KrsDetailDaoInter;
import org.config.spring.hibernate.dao.KrsHeaderDaoInter;
import org.config.spring.hibernate.dao.KrsJadwalDaoInter;
import org.config.spring.hibernate.dao.MatkulDaoInter;
import org.config.spring.hibernate.dao.MhsDaoInter;
import org.config.spring.hibernate.dao.SysvarDaoInter;
import org.config.spring.hibernate.model.*;
import org.siakad.zkoss.utils.SiakadCookie;
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

public class KrsUbah extends AbstractValidator {
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
    
    private MMhs mhs = new MMhs();
    
    private int jumlahSks = 0;
    private int maxSks = 10; //Ini minimal untuk menjaga jika ada parameter sistem yang TERHAPUS
    private double ipSemesterLalu = 0.0;
    
    private int tahunAkademik=0;
    private int semesterGenapGanjil=1; //Ganjil
    private Boolean genapGasalBol=true; //False:Genap:0 >> True:Ganjil:1
    private int semester=0;
    //Otentikasi
    private boolean granted=false;
    private boolean krsBuka =false; 
    //ComboBox dll
    
    private boolean showPilihJadwal=false;
    private boolean showLihatJadwal=false;
    //Entity or Variable Util atau pendukung
    private boolean addNew;
    private int selectedTab=1;
    
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
      
    @Init
    public void init(){
        //0. Jika bukan mahasiswa, atau krsTutup(ambil dari parameter sistem) maka listMatkulKosong
        /* Jika tidak Mempunyai otorisasi maka semua grid akan ditutup
         */
       Sysvar sysvar = new Sysvar();
       //1. Cek apakah mahasiswa atau bukan       
       SiakadCookie sc = new SiakadCookie();
       CmsUsers pengguna = new CmsUsers();
       if (sc.getCookieUserId() != null) {
           pengguna = cmsUsersDao.findById(sc.getCookieUserId());
           
           if (pengguna.getMhs() != null || pengguna.getUserType() != 1) {
                mhs = pengguna.getMhs();
                granted = true;
           } else {
               Messagebox.show("Anda Bukan Mahasiswa! \n Modul ini hanya bisa diakses oleh mahasiswa saja");
           }           
       }
       //2. Cek bebas BAU dan Tidak diblokir
       boolean krsBuka1 =false, krsBuka2=false, krsBuka3=false, krsBuka4=false;
       if (granted==true && mhs !=null) {
          if (mhs.getBebasBau() !=null) 
              if (mhs.getBebasBau()==true ) krsBuka1=true;
          if (mhs.getKrsOpened() !=null) 
              if (mhs.getKrsOpened()==true) krsBuka2=true;
          if (mhs.getStatusAktif() !=null)
              if (mhs.getStatusAktif()==true) krsBuka3=true;
       }
       //CEK BOLEH MEMBUKA MATA KULIAH ATAU TIDAK
       //3. CEK KRS-AN DIBUKA ATAU DITUTUP (Sudah habis masa KRSAN)       
       Date actualDate = new Date();
       long longActDate = actualDate.getTime();
       //Cek Krs Buka Semester Pertama
       sysvar = sysvarDao.findById("AKKRS00");
       if (sysvar!=null){
            if ((actualDate.getTime() > sysvar.getNilaiDate1().getTime()) && (actualDate.getTime()<sysvar.getNilaiDate2().getTime())){
                krsBuka4=true;
                //Messagebox.show("Krs Sudah Terbuka");
            }else {
                //krsBuka4 =false; //Gakperlu pake karena jadi satu sama bawah
                //Messagebox.show(actualDate.toString() + ">> " + sysvar.getNilaiDate1().toString() + ">> " + sysvar.getNilaiDate2().toString());
            }
       } else {
           Messagebox.show("Parameter sistem AKKRS00 --> Krs Terbuka belum ada");
       }
       //Cek Krs Buka Semester Kedua
       sysvar = sysvarDao.findById("AKKRS11");
       if (sysvar!=null){
            if ((actualDate.getTime() > sysvar.getNilaiDate1().getTime()) && (actualDate.getTime()<sysvar.getNilaiDate2().getTime())){
                krsBuka4=true;
                //Messagebox.show("Krs Sudah Terbuka");
            }else {
                //krsBuka=false;
                //Messagebox.show(actualDate.toString() + ">> " + sysvar.getNilaiDate1().toString() + ">> " + sysvar.getNilaiDate2().toString());
            }
       } else {
           Messagebox.show("Parameter sistem AKKRS11 --> Krs Terbuka belum ada");
       }
       
      //AKUMULASI KRS BUKA
       if (krsBuka1==true && krsBuka2==true && krsBuka3==true && krsBuka4==true) {
           krsBuka=true;
       }
       
       
       
       
        //1. SaveOrUpdate otomatis krs headers untu field-fied --> 
        //Mhs(ambil dari login), maxSks(ambil dari parameter sistem dan Mhs->ipSmtLalu), tahunAkademik(ambil dari parameter sistem)
       if (granted==true) { //Supaya tidak susah sudah malakukan ini semua jika ternyata user tidak diijinkan (Tidak punya grant)
            sysvar = sysvarDao.findById("AK001");
            String strTahunAkademik ="";
            String strTahunAkademik2Digit="";
            if (sysvar !=null) {
                tahunAkademik = sysvar.getNilaiInt1();
                strTahunAkademik = String.valueOf(tahunAkademik);
                strTahunAkademik2Digit = strTahunAkademik.substring(strTahunAkademik.length()-2, strTahunAkademik.length());
            } else {
                Messagebox.show("Parameter Sistem AK001 -> Tahun Akademik Sekarang belum ada");
                krsBuka=false;            
            }
            sysvar = sysvarDao.findById("AK002");
            if (sysvar !=null) {
                //Genap:False:0 >> Ganjil:True:1
                genapGasalBol = sysvar.getNilaiBol1();
                if (sysvar.getNilaiBol1()==true) {
                    semesterGenapGanjil = 1;
                } else {
                    semesterGenapGanjil =0;
                }
            } else {            
                Messagebox.show("Parameter Sistem AK002 -> Semester Genap=False=0-> Ganjil=True=1");
                krsBuka=false;            
            }
            //#### KRS HEADER ####
            //YANG DISIMPAN DALAM KRSHEADER
            //1. idKrs, 2. tahunAkademik, 3. Semester, 4. idMhs, 5. ipSemester Lalu, 6. genapGasal, 
            //Inisialisasi isi utama

            TKrsHeader hd = new TKrsHeader();
            //1. buat IdKrs
            String kodeGenapGanjil = "00";
            if (semesterGenapGanjil== 1) kodeGenapGanjil="01";
            hd.setIdKrs(strTahunAkademik2Digit + kodeGenapGanjil + mhs.getIdMhs());
            
            //2. Konfirmasi idKrs tersebut --> Jika sudah pernah ada load sebagai data awal --> Jika belum ada gunakan yang akan dibentuk
            TKrsHeader hdCari = new TKrsHeader();
            hdCari = krsHeaderDao.findById(hd.getIdKrs());            
            if (hdCari != null)
                hd =hdCari; 
            //2. Set tahun Akademik
            hd.setThnAkademik(tahunAkademik);
            //3. Semester dan 4.IdMahasiswa, 5. ipSemester Lalu
            if (mhs !=null) {
                hd.setSemester(mhs.getSemester());
                hd.setIpSemesterLalu(mhs.getIpSemesterLalu());
                hd.setMhs(mhs);
               
            }
            
            //Perhituangan Max SKS disini
            Double range1 = null, range2 = null, range3 = null, range4 = null;
            int maxSksRange1 = 0, maxSksRange2 = 0, maxSksRange3 = 0, maxSksRange4 = 0;
            sysvar = sysvarDao.findById("AKKRS01");
            if (sysvar==null) {
                Messagebox.show("Parameter Sistem AKKRS01 -> Range 1 KRS Belum Ada");
                krsBuka=false;
            } else {
                range1 = sysvar.getNilaiDouble1(); maxSksRange1 = sysvar.getNilaiDouble2().intValue();
            }
            sysvar = sysvarDao.findById("AKKRS02");
            if (sysvar==null) {
                Messagebox.show("Parameter Sistem AKKRS01 -> Range 2 KRS Belum Ada");
                krsBuka=false;
            } else { 
                range2 = sysvar.getNilaiDouble1(); maxSksRange2 = sysvar.getNilaiDouble2().intValue();
            }
            sysvar = sysvarDao.findById("AKKRS03");
            if (sysvar==null) {
                Messagebox.show("Parameter Sistem AKKRS01 -> Range 3 KRS Belum Ada");
                krsBuka=false;
            } else {
                range3 = sysvar.getNilaiDouble1(); maxSksRange3 = sysvar.getNilaiDouble2().intValue();
            }
            sysvar = sysvarDao.findById("AKKRS04");
            if (sysvar==null) {
                Messagebox.show("Parameter Sistem AKKRS01 -> Range 4 KRS Belum Ada");
                krsBuka=false;
            } else {
                range4 = sysvar.getNilaiDouble1(); maxSksRange4 = sysvar.getNilaiDouble2().intValue();
            }
            
            if (mhs !=null) {
                if (mhs.getIpSemesterLalu() != null){
                    if (mhs.getIpSemesterLalu() < range1) {
                        setMaxSks(maxSksRange1);
                    } else if (mhs.getIpSemesterLalu() < range2) {
                        setMaxSks(maxSksRange2);
                    } else if (mhs.getIpSemesterLalu() < range3) {
                        setMaxSks(maxSksRange3);
                    } else if (mhs.getIpSemesterLalu() < range4) {
                        setMaxSks(maxSksRange4);
                    } else {
                        setMaxSks(24);
                    }
                }
            }           
            //6. MaxSks
            hd.setMaxSks(maxSks);
            //7. Genap Gasal
            hd.setSmtGenapGasal(genapGasalBol);
     //DAO UPDATE dan Timpa
            krsHeaderDao.saveOrUpdate(hd);
            krsHeaders = krsHeaderDao.findById(hd.getIdKrs());
            listKrsDetails = new ArrayList<TKrsDetail>(krsHeaders.getKrsDetailSet());

            if (krsHeaders !=null)
                listKrsDetails = new ArrayList<TKrsDetail>(krsHeaders.getKrsDetailSet());

            //Matkul adalah Matkul Semester Yang berjalan dan Jadwal Adalah Tahun yang bersangkutan
                listMatkul = matkulDao.getAll();
             reload();

            //Jika ada isinya maka isi dengan yang pertama jika tidak ada biarin kosong
            if (listMatkul.size() > 0)
                matkul = listMatkul.get(0);
            //Mula-mula tombol tambah adalah false
            setAddNew(false);
        
       }
        
    }
    
    
    
    @AfterCompose
    public void afterCompose(){
    }
    
    //Saring Data yang ditampilkan di List Mata Kuliah
    @NotifyChange({"jumlahSks"})
    public List<MMatkul> getListMatkul() {
        int jmlSks = 0;
        List<MMatkul> lst = new ArrayList<MMatkul>();        
        for (TKrsDetail dt: listKrsDetails) {
            lst.add(dt.getMatkul());  
            jmlSks = jmlSks + dt.getMatkul().getSks();
        }
        //Parameter jumlah sks
        jumlahSks = jmlSks;   
        
        listMatkul.removeAll(lst);
        return listMatkul;
    }

    public void setListMatkul(List<MMatkul> listMatkul) {
        this.listMatkul = listMatkul;
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
                                BindUtils.postNotifyChange(null, null, KrsUbah.this, "listMatkul");        
                                BindUtils.postNotifyChange(null, null, KrsUbah.this, "matkul");        
                                
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
    
    @NotifyChange({"listKrsDetails", "listMatkul"})
    @Command("pilihMatkul")
    public void pilihMatkul(@BindingParam("parameter1") MMatkul m){
        
        //Cek apakah melampaui krsMax jika iya maka STOP
        if (jumlahSks + m.getSks() > maxSks) {
            Messagebox.show("Jumlah SKS yang dipilih adalah : " + (jumlahSks+m.getSks()) + "\n" + "Tidak dapat memilih lagi karena melampaui Sks Maximum yaitu : " + maxSks);
        } else {
            //1. Cek Prasarat Matkul Pertama            
            //2. Cek Prasarat Matkul Kedua
            //3. Cek Jumalah Sks
            boolean saratOk1 = false;
            boolean saratOk2 = false;
            boolean saratOk3 = false; //Sarat Jumlah SKS
            
            String saratMatkul1 = "";
            if (m.getPrasarat() !=null)
                if (m.getPrasarat().getIdMatkul() !=null)
                    saratMatkul1 = m.getPrasarat().getIdMatkul();
            
            String saratMatkul2="";
            if (m.getPrasarat2()!=null)
                if (m.getPrasarat2().getIdMatkul() !=null)
                    saratMatkul2 = m.getPrasarat2().getIdMatkul();
 
            int saratJumlahSks = 0;
            if (m.getPrasaratJumlah() !=null)
                saratJumlahSks = m.getPrasaratJumlah();
            int hitungJumlahSks = 0;
            
            MMhs cekMhs = new MMhs();
            //cekMhs = mhsDao.findById("123");
            cekMhs = mhsDao.findById(mhs.getIdMhs());
            if (saratMatkul1.equals("")) saratOk1=true;if (saratMatkul2.equals("")) saratOk2=true; if (saratJumlahSks==0) saratOk3=true;  
            if (cekMhs !=null) {
                if (saratOk1 != true || saratOk2 != true || saratOk3 !=true) {
                        List<TKrsHeader> headers = new ArrayList<TKrsHeader>(cekMhs.getKrsHeaderSet());
                        for (TKrsHeader item: headers) {
                            List<TKrsDetail> details = new ArrayList<TKrsDetail>(item.getKrsDetailSet());
                            
                            for (TKrsDetail item2: details) {
                                if (item2.getLulus() ==null) {
                                    item2.setLulus(false);
                                }
                                if (item2.getLulus()==true) {
                                    hitungJumlahSks = hitungJumlahSks + item2.getMatkul().getSks();

                                    if ( saratMatkul1.trim().equals("")){
                                        saratOk1=true;
                                    }  else if (item2.getMatkul().getIdMatkul().equals(saratMatkul1)) {
                                            saratOk1 = true;
                                    } 

                                    if (saratMatkul2.trim().equals(""))   { 
                                        saratOk2 =true;
                                    } else if (item2.getMatkul().getIdMatkul().equals(saratMatkul2)) {
                                            saratOk1 =true;
                                    }                                                      
                                }                                                
                            }
                            
                        }                                     
                        //Prasarat Jumlah   LEBIH BESAR ATAU SAMA DENGAN
                        if (hitungJumlahSks >= saratJumlahSks) {
                            saratOk3=true;
                        }
                }
                
            }
            
            //Messagebox.show(String.valueOf(saratOk1) + "\t" + String.valueOf(saratOk2) + "\t" +String.valueOf(saratOk3) + "\t") ;
            if (saratOk1==true && saratOk2==true && saratOk3==true) {
                //Messagebox.show("Sarat Terpenuhi");
                TKrsDetail f = new TKrsDetail();
                f.setMatkul(m);
                f.setKrsHeader(krsHeaders);

                listKrsDetails.add(f);
                listMatkul.remove(m);

                TKrsDetailPK krsDetailPK = new TKrsDetailPK();
                krsDetailPK.setIdKrs(krsHeaders.getIdKrs());
                krsDetailPK.setIdMatkul(m.getIdMatkul());
                f.settKrsDetailPK(krsDetailPK);
                //Set TIMESTAMP terakhir update
                Calendar cal = Calendar.getInstance();
                krsHeaders.setLasUpdate(cal.getTime());
                krsHeaderDao.saveOrUpdate(krsHeaders);
                
                //Update Dao
                krsDetailDao.saveOrUpdate(f);
            } else {
                String pesan1 = "Anda belum lulus matkul Kode: " + saratMatkul1 ; if (saratOk1==true) pesan1 ="";
                String pesan2 = " \nAnda belum lulus matkul Kode: " + saratMatkul2 ; if (saratOk2==true) pesan2 ="";
                String pesan3 = "\n Jumlah SKS yang harus terpenuhi : " + saratJumlahSks ; if (saratOk3==true) pesan3 ="";
                String pesan4 = "\n Jumlah SKS yang baru anda tempuh adalah : " + hitungJumlahSks + " SKS" + 
                        " Jadi kurang " + (saratJumlahSks-hitungJumlahSks) + " SKS lagi" ; if (saratOk3==true) pesan4 ="";
                Messagebox.show(pesan1 + pesan2 + pesan3 + pesan4);
            }
        }        
    }
    
    @NotifyChange({"listKrsDetails", "listMatkul"})
    @Command("hapusMatkul")
    public void hapusMatkul(@BindingParam("parameter1") TKrsDetail kd){

        listKrsDetails.remove(kd);
        
        MMatkul mk = new MMatkul();
        mk = kd.getMatkul();
        listMatkul.add(mk);

        //Update Quota Jadwal Jika ada --> dikurang aja 1
        TKrsJadwal krsJadwalLama = new TKrsJadwal();
        krsJadwalLama = kd.getKrsJadwal(); //Pasti ada nilainya soalnya
        if (kd.getKrsJadwal() != null) { //Jika tidak null atau tidak kosong maka dikurangi aja (Null berarti belum ada jadwal)
            if (! kd.getKrsJadwal().getTerisi().equals(0)) {
                krsJadwalLama.setTerisi(kd.getKrsJadwal().getTerisi() - 1);
                //Set TIMESTAMP terakhir update
                Calendar cal = Calendar.getInstance();
                krsHeaders.setLasUpdate(cal.getTime());
                krsHeaderDao.saveOrUpdate(krsHeaders);
                
                krsJadwalDao.saveOrUpdate(krsJadwalLama);
            }
        }
        //Update Dao
        krsDetailDao.delete(kd);
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
            cariMatkul.setIdMatkul("%"+ cariMatkul.getIdMatkul() + "%");
            cariMatkul.setNamaMatkul("%"+ cariMatkul.getNamaMatkul() + "%");
            cariMatkul.setStatusAktif(true);
            
            MMatkulKur kur = new MMatkulKur();
            
            cariMatkul.setMatkulKur(kur);
            listMatkul = matkulDao.findByManualCriteria(cariMatkul);
    }

    @NotifyChange("listMatkul")
    @Command("reload")
    public void reload(){
        //if (cariMatkul.getIdMatkul()==null) cariMatkul.setIdMatkul("");
        //if (cariMatkul.getNamaMatkul()==null) cariMatkul.setNamaMatkul("");
        //if (cariMatkul.getSks()==null) cariMatkul.setSks(0);
        //if (cariMatkul.getSemester()==null) cariMatkul.setSemester(0);       
            
            MMatkul localMatkul = new MMatkul();
        
            localMatkul.setIdMatkul("%");
            localMatkul.setNamaMatkul("%");
            localMatkul.setStatusAktif(true);
            
            MMatkulKur kur = new MMatkulKur();
            
            localMatkul.setMatkulKur(kur);
            listMatkul = matkulDao.findByManualCriteria(localMatkul);       
        
    }
    
    
    @NotifyChange({"showLihatJadwal", "listKrsJadwal", "lihatJadwalMatkul"})
    @Command({"lihatJadwal"})
    public void lihatJadwal(@BindingParam("parameter1") MMatkul m){
        //1. Buka Window Jadwal berdasarkan idMatkul, tahunAjaran, semesterGenapGanjil
        lihatJadwalMatkul = m;
        
        TKrsJadwal kj = new TKrsJadwal();
        kj.setIdJadwal(0);
        kj.setMatkul(m); 
        kj.setTahunAjaran(getTahunAkademik());
        
//MASIH PERLU PEMIKIRAN
        //kj.setHari(1);
        
        kj.setSmtGenapGasal(genapGasalBol);
        
        listKrsJadwal = krsJadwalDao.findByIdJadIdMatIdPegThnHariGenapGasal(kj);

        showLihatJadwal = true;
    }
    
    @NotifyChange({"showPilihJadwal", "listKrsJadwal", "lihatJadwalMatkul", "krsDetails"})
    @Command({"pilihJadwal"})
    public void pilihJadwal(@BindingParam("parameter1") TKrsDetail m){
        //1. Buka Windows Pilih Krs berdasar idMatkul, tahunAjaran, semesterGenapGajil --> kodeJadwal --> KrsDetail
        //2. Tambah Quota Jadwal
        krsDetails = m; //KRS detail yang sedang dipilih
       
        TKrsJadwal kj = new TKrsJadwal();
        kj.setIdJadwal(0);      
        kj.setMatkul(m.getMatkul()); lihatJadwalMatkul = m.getMatkul();
        kj.setTahunAjaran(getTahunAkademik());
        //kj.setHari(1);
        kj.setSmtGenapGasal(genapGasalBol);
        
        listKrsJadwal = krsJadwalDao.findByIdJadIdMatIdPegThnHariGenapGasal(kj);
        
        showPilihJadwal = true;
    }
    //Nyambung sama atasnya(command pilihJadwal yaitu Matakuliah yang dipilih
    @NotifyChange({"showPilihJadwal", "listKrsJadwal", "lihatJadwalMatkul", "krsDetails"})
    @Command({"pilihJadwalOk"})
    public void pilihJadwalOk(@BindingParam("parameter1") TKrsJadwal m){
        
            //if (krsDetails.getKrsJadwal()==null) Messagebox.show("Null Bos");
            if (m !=null && ! m.equals(krsDetails.getKrsJadwal()) ) { //Jika null atau jika Krs Sama maka tidak perlu melakukan apa-apa
                //Update Kuota
                //1. Kurangi Kuota Jadwal Lama --> 
                TKrsJadwal krsJadwalLama = new TKrsJadwal();
                if (krsDetails.getKrsJadwal() !=null) {
                    krsJadwalLama = krsJadwalDao.findById(krsDetails.getKrsJadwal().getIdJadwal()); //Pakai Krs detail untuk dapat Jadwal
                    if (krsJadwalLama!=null) { //Jika Ada yang lama dihapus jika tidak ada gak perlu
                        if (krsJadwalLama.getTerisi()!=null) { // Jika field terisi belum pernah disentuh atau null
                            if (! krsJadwalLama.getTerisi().equals(0)){ // Jika sudah mencapai null maka tidak usah dikurangi
                                krsJadwalLama.setTerisi(krsJadwalLama.getTerisi() -1); //Harus selalu bisa mengurangi
                            }
                        } else {
                            krsJadwalLama.setTerisi(0);
                        }
                        krsJadwalDao.saveOrUpdate(krsJadwalLama); //Update Dao
                        //Messagebox.show(String.valueOf(krsJadwalLama.getTerisi()));
                    }
                } 

                //krsJadwalDao.saveOrUpdate(m);
                //2. Tambah Kuota Jadwal Baru
                if (m.getTerisi() == null) m.setTerisi(0);
                    m.setTerisi(m.getTerisi() + 1);
                krsJadwalDao.saveOrUpdate(m);

                //Jika Kuota sudah penuh maka tidak bisa dipilih   #################     
                //Update Kelas
                krsDetails.setKrsJadwal(m);
                krsDetailDao.saveOrUpdate(krsDetails);
                
            }
             
           
            showPilihJadwal = false;            
    
    }
            
            
    @NotifyChange({"showLihatJadwal", "showPilihJadwal"})
    @Command({"closeJadwal"})
    public void closeJadwal(){
        showLihatJadwal =false;
        showPilihJadwal = false;
    }
    
    @NotifyChange({"krsHeaders"})
    @Command("postKomen")
    public void postKomen(){
        krsHeaderDao.saveOrUpdate(krsHeaders);
    }
    
    
    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        validateKode(vc, (String)beanProps.get("idMatkul").getValue(), (Boolean)vc.getValidatorArg("tambahBaru"));        
        validateDeskripsi(vc, (String)beanProps.get("namaMatkul").getValue());        
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
    
    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(boolean addNew) {
        this.addNew = addNew;
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

    public List<TKrsDetail> getListKrsDetails() {
        return listKrsDetails;
    }

    public void setListKrsDetails(List<TKrsDetail> listKrsDetails) {
        this.listKrsDetails = listKrsDetails;
    }

    public List<TKrsHeader> getListTKrsHeaders() {
        return listTKrsHeaders;
    }

    public void setListTKrsHeaders(List<TKrsHeader> listTKrsHeaders) {
        this.listTKrsHeaders = listTKrsHeaders;
    }

    public TKrsDetail getKrsDetails() {
        return krsDetails;
    }

    public void setKrsDetails(TKrsDetail krsDetails) {
        this.krsDetails = krsDetails;
    }

    public TKrsHeader getKrsHeaders() {
        return krsHeaders;
    }

    public void setKrsHeaders(TKrsHeader krsHeaders) {
        this.krsHeaders = krsHeaders;
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


    @DependsOn({"listKrsDetails", "listMatkul"})
    public int getJumlahSks() {
        return jumlahSks;
    }

    public void setJumlahSks(int jumlahSks) {
        this.jumlahSks = jumlahSks;
    }

    public int getMaxSks() {
        return maxSks;
    }

    public void setMaxSks(int maxSks) {
        this.maxSks = maxSks;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public MMhs getMhs() {
        return mhs;
    }

    public void setMhs(MMhs mhs) {
        this.mhs = mhs;
    }

    public int getTahunAkademik() {
        return tahunAkademik;
    }

    public void setTahunAkademik(int tahunAkademik) {
        this.tahunAkademik = tahunAkademik;
    }

    public int getSemesterGenapGanjil() {
        return semesterGenapGanjil;
    }

    public void setSemesterGenapGanjil(int semesterGenapGanjil) {
        this.semesterGenapGanjil = semesterGenapGanjil;
    }

    public boolean isKrsBuka() {
        return krsBuka;
    }

    public void setKrsBuka(boolean krsBuka) {
        this.krsBuka = krsBuka;
    }

    public boolean isShowLihatJadwal() {
        return showLihatJadwal;
    }

    public void setShowLihatJadwal(boolean showLihatJadwal) {
        this.showLihatJadwal = showLihatJadwal;
    }

    public boolean isShowPilihJadwal() {
        return showPilihJadwal;
    }

    public void setShowPilihJadwal(boolean showPilihJadwal) {
        this.showPilihJadwal = showPilihJadwal;
    }

    public List<TKrsJadwal> getListKrsJadwal() {
        return listKrsJadwal;
    }

    public void setListKrsJadwal(List<TKrsJadwal> listKrsJadwal) {
        this.listKrsJadwal = listKrsJadwal;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
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

    public KrsDetailDaoInter getKrsDetailDao() {
        return krsDetailDao;
    }

    public void setKrsDetailDao(KrsDetailDaoInter krsDetailDao) {
        this.krsDetailDao = krsDetailDao;
    }

    public KrsHeaderDaoInter getKrsHeaderDao() {
        return krsHeaderDao;
    }

    public void setKrsHeaderDao(KrsHeaderDaoInter krsHeaderDao) {
        this.krsHeaderDao = krsHeaderDao;
    }

    public KrsJadwalDaoInter getKrsJadwalDao() {
        return krsJadwalDao;
    }

    public void setKrsJadwalDao(KrsJadwalDaoInter krsJadwalDao) {
        this.krsJadwalDao = krsJadwalDao;
    }

    public MatkulDaoInter getMatkulDao() {
        return matkulDao;
    }

    public void setMatkulDao(MatkulDaoInter matkulDao) {
        this.matkulDao = matkulDao;
    }

    public SysvarDaoInter getSysvarDao() {
        return sysvarDao;
    }

    public void setSysvarDao(SysvarDaoInter sysvarDao) {
        this.sysvarDao = sysvarDao;
    }

    public MMatkul getLihatJadwalMatkul() {
        return lihatJadwalMatkul;
    }

    public void setLihatJadwalMatkul(MMatkul lihatJadwalMatkul) {
        this.lihatJadwalMatkul = lihatJadwalMatkul;
    }



    
}
