package com.example.projectwiki;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class SharePrefManager {
    private static SharePrefManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAMA = "password";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_UID = "useruid";
    private static final String KEY_TYPE_USER = "usertypeuser";


    private SharePrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharePrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(String uid, String email, String password, String type_user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("session login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_UID, uid);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAMA, password);
        editor.putString(KEY_TYPE_USER, type_user);

        editor.apply();
        return true;
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EMAIL, null) !=null){
            return true;
        }
        return false;
    }

    public  boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


}