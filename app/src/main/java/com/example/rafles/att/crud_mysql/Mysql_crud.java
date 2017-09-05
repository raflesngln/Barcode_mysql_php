package com.example.rafles.att.crud_mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafles.att.R;

import java.util.HashMap;

public class Mysql_crud extends AppCompatActivity implements View.OnClickListener{
    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editFullname;
    private EditText editUsername;
    private EditText editPassword;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_crud);

        //Inisialisasi dari View
        editFullname = (EditText) findViewById(R.id.editFullname);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }


    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void AddData(){

        final String fullnm = editFullname.getText().toString().trim();
        final String usr = editUsername.getText().toString().trim();
        final String pass = editPassword.getText().toString().trim();

        class AddData extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Mysql_crud.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Mysql_crud.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();

                params.put(konfigurasi.KEY_DATA_USERNAME,fullnm);
                params.put(konfigurasi.KEY_DATA_FULLNAME,usr);
                params.put(konfigurasi.KEY_DATA_PASSWORD,pass);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddData add_data = new AddData();
        add_data.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            AddData();
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilData.class));

        }
    }
}