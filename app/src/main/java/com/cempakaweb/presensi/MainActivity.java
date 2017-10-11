package com.cempakaweb.presensi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;
import com.cempakaweb.presensi.api.LoginAPI;
import com.cempakaweb.presensi.model.LoginValue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://api.presensi.uny.ac.id/android/";
    private ProgressDialog progress;
    SessionManager session;

    @BindView(R.id.txtUsername) EditText usValue;
    @BindView(R.id.txtPassword) EditText pwValue;

    @OnClick(R.id.btnLogin) void daftar(){

        //Membuat Progress Dialog
        progress = ProgressDialog.show(MainActivity.this, null, null, true);
        progress.setContentView(R.layout.progress_bar_splash);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress.show();



        //Mengambil Data Dari EditText
        String username = usValue.getText().toString();
        String password = pwValue.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<LoginValue> call = api.daftar(username,password);
        call.enqueue(new Callback<LoginValue>() {
            @Override
            public void onResponse(Call<LoginValue> call, Response<LoginValue> response) {
                Boolean status = response.body().getStatus();
                String username = response.body().getUsername();
                progress.dismiss();
                if(status){
                    Alerter.create(MainActivity.this)
                            .setTitle("Login Berhasil")
                            .setBackgroundColor(R.color.alerterInfo)
                            .setText("Login Dengan Username : " + username)
                            .show();
                    session = new SessionManager(getApplicationContext());
                    session.createSession(username,username);
                    Intent i = new Intent(getApplicationContext(), CaptureActivity.class);
                    startActivity(i);
                    finish();
                    //session.saveString(SessionManager._NAMA,username);
                    //session.saveBoelan(SessionManager._SUDAH_LOGIN,true);
                    //startActivity(new Intent(MainActivity.this,CaptureActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    //finish();
                }else{
                    Alerter.create(MainActivity.this)
                            .setTitle("Login Gagal")
                            .setText("Login Gagal")
                            .setBackgroundColor(R.color.alerterError)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<LoginValue> call, Throwable t) {
                progress.dismiss();
                Alerter.create(MainActivity.this)
                        .setTitle("Error")
                        .setText("Error, Cek Jaringan Anda")
                        .setBackgroundColor(R.color.alerterError)
                        .show();
            }
        });
    }

//    @OnClick(R.id.lupaPassword) void reset(){
//        startActivity(new Intent(MainActivity.this,MainReset.class));
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
        if(session.getSudahLogin()){
            startActivity(new Intent(getApplicationContext(), CaptureActivity.class));
        }else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
        }
    }
}
