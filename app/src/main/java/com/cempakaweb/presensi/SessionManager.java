package com.cempakaweb.presensi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by dwiagus on 10/10/17.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    public static final String _APP_NAME = "PresensiWajah";
    public static final String _SUDAH_LOGIN = "SudahLogin";
    public static final String _NAMA ="NamaPegawai";
    public static final String _NIK = "NikPegawai";

    public SessionManager(Context context){
        pref = context.getSharedPreferences(_APP_NAME,Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveString(String skey , String svalue)
    {
        editor.putString(skey,svalue);
        editor.commit();
    }

    public void saveInt(String skey, int svalue)
    {
        editor.putInt(skey,svalue);
        editor.commit();
    }

    public void saveBoelan(String skey , boolean svalue)
    {
        editor.putBoolean(skey,svalue);
        editor.commit();
    }

    public String getAppName()
    {
        return pref.getString(_APP_NAME,"");
    }

    public String getNama()
    {
        return pref.getString(_NAMA,"");
    }

    public String getNik()
    {
        return pref.getString(_NIK,"");
    }

    public boolean getSudahLogin()
    {
        return pref.getBoolean(_SUDAH_LOGIN,false);
    }

    public void createSession(String Nama , String NIK)
    {
        editor.putBoolean(_SUDAH_LOGIN, true);
        editor.putString(_NAMA,Nama);
        editor.putString(_NIK,NIK);
        editor.commit();
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context,MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

}
