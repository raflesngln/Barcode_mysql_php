package com.example.rafles.att.table_reject;

public class ConfigReject {
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static String URL="http://149.129.214.176/barcode/";

    public static final String URL_ADD=URL+"tambahReject.php";
    public static final String URL_GET_ALL =URL+"tampilReject.php";
    public static final String URL_GET_EMP =URL+"detailReject.php?id=";
    public static final String URL_UPDATE_EMP =URL+"updateReject.php";
    public static final String URL_DELETE_EMP =URL+"hapusReject.php?id=";
    public static final String URL_CEK_EMP =URL+"cekReject.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "Noid";
    public static final String KEY_EMP_CONNOTE = "connote";
    public static final String KEY_EMP_CN35 = "CN35";
    public static final String KEY_EMP_CN38 = "CN38";
    public static final String KEY_EMP_DATECREATE = "datecreate";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "Noid";
    public static final String TAG_CONNOTE = "connote";
    public static final String TAG_CN35 = "CN35";
    public static final String TAG_CN38 = "CN38";
    public static final String TAG_DATECREATE = "datecreate";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
