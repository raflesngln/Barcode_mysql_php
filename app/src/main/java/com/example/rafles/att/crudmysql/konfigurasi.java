package com.example.rafles.att.crudmysql;

/**
 * Created by Rafles on 01/19/2017.
 * kodingindonesia
 */

public class konfigurasi {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    //public static String URL="http://192.168.10.33/android_php/crud2/";
    public static String URL="http://192.168.43.78/android_php/crud2/";

    public static final String URL_ADD=URL+"tambahPgw.php";
    public static final String URL_GET_ALL =URL+"tampilSemuaPgw.php";
    public static final String URL_GET_EMP =URL+"tampilPgw.php?id=";
    public static final String URL_UPDATE_EMP =URL+"updatePgw.php";
    public static final String URL_DELETE_EMP =URL+"hapusPgw.php?id=";
    public static final String URL_CEK_EMP =URL+"cekPgw.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "desg"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_GAJIH = "salary"; //salary itu variabel untuk gajih

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "desg";
    public static final String TAG_GAJIH = "salary";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
