package com.example.rafles.att.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafles.att.R;
//import com.example.rafles.att.barcode.Barcode;

public class QrBarcodeee extends AppCompatActivity {
    Button btn_qr, btn_bar;
    TextView txtalert;
    String contents, format;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_barcode);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_qr = (Button) findViewById(R.id.btnQrcode);
        btn_bar = (Button) findViewById(R.id.btnBarcode);
        txtalert = (TextView) findViewById(R.id.txtalert);

        btn_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtalert.setText("Barcode Masih dalam pengembangan");
                Toast.makeText(QrBarcodeee.this, "Barcode Masih dalam pengembangan", 1000).show();
            }
        });
        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtalert.setText("QR code Masih dalam pengembangan");
                Toast.makeText(QrBarcodeee.this, "QrBarcodeee Masih dalam pengembangan", 1000).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
