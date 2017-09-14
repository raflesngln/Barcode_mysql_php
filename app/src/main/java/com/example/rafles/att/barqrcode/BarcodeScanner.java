package com.example.rafles.att.barqrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rafles.att.MainActivity;
import com.example.rafles.att.R;
import com.example.rafles.att.crudmysql.RequestHandler;
import com.example.rafles.att.crudmysql.konfigurasi;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rafles on 12/19/15.
 */
public class BarcodeScanner extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    private Button scanButton;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_scanner);

        initControls();
    }

    private void initControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(BarcodeScanner.this, mCamera, previewCb,
                autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanButton = (Button) findViewById(R.id.ScanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //startActivity(new Intent(BarcodeScanner.this, MainActivity.class));
            //BarcodeScanner.this.finish();
            //releaseCamera();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    //Log.i("Barcode and QRCode" + sym.getData());
                    String scanResult = sym.getData().trim();
                    //cekdata(scanResult);
                      cekIDExist(scanResult);
                        barcodeScanned = true;
                        break;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

public void cekdata(final String nomor){
        String nomorDb="12345";
        if(nomor.equals(nomorDb)){
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Toast.makeText(BarcodeScanner.this, "NOMOR DITEMUKAN" + nomor, Toast.LENGTH_LONG).show();
            showAlertDialog(nomor);
    } else{
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            Toast.makeText(BarcodeScanner.this, "NOMOR INI TIDAK KETEMU DI DATABASE " + nomor, Toast.LENGTH_LONG).show();
            //refresh to this class to start barcode again
            startActivity(getIntent());
    }

}
    private void showAlertDialog(final String nomor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure save ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Toast.makeText(BarcodeScanner.this, "Data di update! " + nomor, Toast.LENGTH_LONG).show();
                startActivity(getIntent());
                dialog.dismiss();
            }


        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BarcodeScanner.this, "Dibatalkan! " + nomor, Toast.LENGTH_LONG).show();
                startActivity(getIntent());
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
}

/*=====================FUNGSI UNTUK MENGECEK DATA KE DATABASE===================================  */
//Dibawah ini untuk Cek data ada atau tidak dengan nomor tertentu
private void cekIDExist(final String nomor){
    class cekIdEmployee extends AsyncTask<Void,Void,String> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(BarcodeScanner.this,"Mencari data...","Waiit...",false,false);
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
                    notif="TIDAK KETEMU !!";
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(600);
                    startActivity(getIntent());
                } else{
                    notif="KETEMU !!";
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                    confirmUpdate(nomor);
                }
                //Toast.makeText(BarcodeScanner.this,message + " Status "+ notif+"nomor"+nomor,Toast.LENGTH_LONG).show();
                Toast.makeText(BarcodeScanner.this, message + " dengan Nomor BARCODE/QRCODE  "+nomor, 1000).show();

            } catch (JSONException e){
                e.printStackTrace();
                System.out.println("error Not defined");
            }

        }
        @Override
        protected String doInBackground(Void... v) {
            HashMap<String,String> params = new HashMap<>();
            String id=nomor;
            params.put(konfigurasi.KEY_EMP_ID,id);
            RequestHandler rh = new RequestHandler();
            String res = rh.sendPostRequest(konfigurasi.URL_CEK_EMP, params);
            return res;
        }
    }
    cekIdEmployee ae = new cekIdEmployee();
    ae.execute();
}

//Show dialog jika barcode cocok dengan id di database
    private void confirmUpdate(final String nomor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure Update this ID ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Toast.makeText(BarcodeScanner.this, "Data di update! ", Toast.LENGTH_LONG).show();
                callUpdateExist(nomor);
                startActivity(getIntent());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BarcodeScanner.this, "Dibatalkan! " , Toast.LENGTH_LONG).show();
                startActivity(getIntent());
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
                loading = ProgressDialog.show(BarcodeScanner.this,"Megupdate data...","Wait...",false,false);
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
                    Toast.makeText(BarcodeScanner.this,message + " Status "+ notif,Toast.LENGTH_LONG).show();

                } catch (JSONException e){
                    e.printStackTrace();
                    System.out.println("error Not defined");
                }

            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                String id=nomor;
                String name="Mawar";
                String alamat="Jakarta Barat Cengkareng";
                String jabatan="HRD";

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
/* =========================== END OF DATA CEK DATABASE ==============================================
*/

}
