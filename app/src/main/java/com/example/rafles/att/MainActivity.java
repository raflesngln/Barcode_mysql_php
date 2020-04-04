package com.example.rafles.att;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rafles.att.barqrcode.BarcodeScanner;
import com.example.rafles.att.crud_mysql.Mysql_crud;
import com.example.rafles.att.crud_sqlite.Sql_lite_view;
import com.example.rafles.att.crudmysql.CrudMysql;
import com.example.rafles.att.mahasiswa.ViewActivity;
import com.example.rafles.att.table_reject.RejectShipmentActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCamera();
                //startActivity(new Intent(MainActivity.this, BarcodeScanner.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Menu pengaturan main", 1000).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            // Handle the camera action
            Intent in=new Intent(this,NavSettings.class);
            startActivity(in);
            return true;
        }else if (id == R.id.nav_about) {
            Intent in=new Intent(this,About.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_profile) {
            Toast.makeText(MainActivity.this, "Profile Masih dalam pengembangan", 1000).show();
        } else if (id == R.id.nav_crud_sql_lite) {
            Intent in=new Intent(this,Sql_lite_view.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_crud_phpmysql) {
            Intent in=new Intent(this,Mysql_crud.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_crudMysql) {
            Intent in=new Intent(this,CrudMysql.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_barcode) {
            getCamera();
        }else if (id == R.id.nav_reject) {
            Intent in=new Intent(this,RejectShipmentActivity.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_mhs) {
            Intent in=new Intent(this,ViewActivity.class);
            startActivity(in);
            return true;
        } else if (id == R.id.nav_exit) {
            showDialog();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title dialog
        alertDialogBuilder.setTitle("Are You Sure exit ? ");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Click OKE For for exit !")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("OKE",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("NO",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        dialog.cancel();
                    }
                });
        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();
        // menampilkan alert dialog
        alertDialog.show();
    }

    //CEK FOR REQUEST CAMERA
    private void getCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            startActivity(new Intent(MainActivity.this, BarcodeScanner.class));
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                getCamera();
            } else {
                // Permission request was denied.
                Toast.makeText(MainActivity.this, "Dibatalkan permition", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }


}
