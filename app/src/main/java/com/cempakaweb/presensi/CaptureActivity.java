package com.cempakaweb.presensi;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CaptureActivity extends AppCompatActivity {
    SessionManager session;
    public static String FILE_UPLOAD_URL = "http://api.presensi.uny.ac.id/android/upload";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public  static final int RequestPermissionCode  = 1 ;
    Button Presensi,Datang,Pulang;
    ImageView imageView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        session = new SessionManager(getApplicationContext());
        String Nama = session.getNama();
        //Toast.makeText(getApplicationContext(),"oke"+Nama, Toast.LENGTH_LONG).show();
        Presensi = (Button)findViewById(R.id.btnPresensi);
        Datang = (Button)findViewById(R.id.btnDatang);
        Pulang = (Button)findViewById(R.id.btnPulang);
        imageView = (ImageView)findViewById(R.id.imgPreview);
        AccessCameraPermission();
        Presensi.setVisibility(1);
        Datang.setVisibility(View.GONE);
        Pulang.setVisibility(View.GONE);
        Presensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Presensi.setVisibility(View.GONE);
                Datang.setVisibility(View.VISIBLE);
                Pulang.setVisibility(View.VISIBLE);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,7);
            }
        });
        Datang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Pulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    public void AccessCameraPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(CaptureActivity.this, Manifest.permission.CAMERA)){
            Toast.makeText(CaptureActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        }else{
            ActivityCompat.requestPermissions(CaptureActivity.this,new String[]{Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }



}
