package com.example.rafles.att.crud_mysql;

/**
 * Created by Rafles on 04/09/2017.
 */


public class konfigurasi {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD="http://192.168.10.33/android_php/tambah_data.php";
    public static final String URL_GET_ALL = "http://192.168.10.33/android_php/get_data.php";
    public static final String URL_GET_EMP = "http://192.168.10.33/android_php/detail_data.php?id=";
    public static final String URL_UPDATE_EMP = "http://192.168.10.33/android_php/edit_data.php";
    public static final String URL_DELETE_EMP = "http://192.168.10.33/android_php/delete_data.php?id=";
    //public static final String URL_DELETE_EMP = "http://192.168.43.78/Android/pegawai/hapusPgw.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_DATA_ID = "id";
    public static final String KEY_DATA_USERNAME = "username";
    public static final String KEY_DATA_FULLNAME = "fullname"; //fullname itu variabel untuk fullname
    public static final String KEY_DATA_PASSWORD = "password"; //password itu variabel untuk password

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_FULNAME = "fullname";
    public static final String TAG_PASSWORD= "password";

    //ID DATA
    //itu singkatan dari Employee
    public static final String DATA_ID = "dtID";
}