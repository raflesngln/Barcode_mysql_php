package com.example.rafles.att.crudmysql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafles.att.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CrudMysql extends AppCompatActivity implements View.OnClickListener{

    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextAlamat;
    private EditText editTextJabatan;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_mysql);

        //Inisialisasi dari View
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        editTextJabatan = (EditText) findViewById(R.id.editTextJabatan);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(){
        final String id = editTextId.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String alamat = editTextAlamat.getText().toString().trim();
        final String jab = editTextJabatan.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CrudMysql.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s)   {
                super.onPostExecute(s);
                loading.dismiss();
                String str = s;
                try {
                    String notif;
                    JSONObject obj  = new JSONObject(str);
                    //int success = obj.getInt("success");
                    String success = obj.getString("success");
                    String message = obj.getString("message");
                    if(success=="0"){
                        notif="GAGAL";
                    } else{
                        notif="BERHASIL";
                    }
                    Toast.makeText(CrudMysql.this,message + " Status "+ notif,Toast.LENGTH_LONG).show();

                } catch (JSONException e){
                    e.printStackTrace();
                    System.out.println("error Not defined");
                }

            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_ID,id);
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_ALAMAT,alamat);
                params.put(konfigurasi.KEY_EMP_JABATAN,jab);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addEmployee();
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaPgw.class));
        }
    }

    //Dibawah ini untuk Cek data ada atau tidak dengan nomor tertentu
    private void cekIDExist(final String nomor){
        //final String id = editTextId.getText().toString().trim();
        class cekIdEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CrudMysql.this,"Mencari data...","Waiit...",false,false);
            }
            @Override
            protected void onPostExecute(String s)   {
                super.onPostExecute(s);
                loading.dismiss();
                String str = s;
                try {
                    String notif;
                    JSONObject obj  = new JSONObject(str);
                    //int success = obj.getInt("success");
                    String success = obj.getString("success");
                    String message = obj.getString("message");
                    if(success=="0"){
                        notif="TIDAK KETEMU !!";
                    } else{
                        notif="KETEMU !!";
                        confirmUpdate(nomor);
                    }
                    Toast.makeText(CrudMysql.this,message + " Status "+ notif,Toast.LENGTH_LONG).show();

                } catch (JSONException e){
                    e.printStackTrace();
                    System.out.println("error Not defined");
                }

            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                String id=nomor;
                String name=nomor;
                params.put(konfigurasi.KEY_EMP_ID,id);
                params.put(konfigurasi.KEY_EMP_NAMA,name);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_CEK_EMP, params);
                return res;
            }
        }
        cekIdEmployee ae = new cekIdEmployee();
        ae.execute();
    }


    private void confirmUpdate(final String nomor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure Update this ID ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Toast.makeText(CrudMysql.this, "Data di update! ", Toast.LENGTH_LONG).show();
                callUpdateExist(nomor);
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CrudMysql.this, "Dibatalkan! " , Toast.LENGTH_LONG).show();
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Update data jika idnya ketemu atau barcode ketemu
    private void callUpdateExist(final String nomor){
        //final String id = editTextId.getText().toString().trim();
        class updateIdEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CrudMysql.this,"Megupdate data...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s)   {
                super.onPostExecute(s);
                loading.dismiss();
                String str = s;
                try {
                    String notif;
                    JSONObject obj  = new JSONObject(str);
                    String success = obj.getString("success");
                    String message = obj.getString("message");
                    if(success=="0"){
                        notif="Data NOt Update !!";
                    } else{
                        notif="Data Updated !!";
                    }
                    Toast.makeText(CrudMysql.this,message + " Status "+ notif,Toast.LENGTH_LONG).show();

                } catch (JSONException e){
                    e.printStackTrace();
                    System.out.println("error Not defined");
                }

            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                String id=nomor;
                String name="Raflesia nainggolan";
                String alamat="Lorem ipsum dolor sit amet";
                String jabatan="90000";

                params.put(konfigurasi.KEY_EMP_ID,id);
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_ALAMAT,alamat);
                params.put(konfigurasi.KEY_EMP_JABATAN,jabatan);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP, params);
                return res;
            }
        }
        updateIdEmployee ae = new updateIdEmployee();
        ae.execute();
    }





}
