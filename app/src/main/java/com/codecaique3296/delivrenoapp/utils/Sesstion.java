package com.codecaique3296.delivrenoapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.codecaique3296.delivrenoapp.models.object.Tayar;
import com.codecaique3296.delivrenoapp.view.LoginActivity;


public class Sesstion {
    private static final String SHARED_PREF_NAME = "tayarSession";
    private static final String TAYAR_ID = "tayar_id";
    private static final String TAYAR_FIRSTNAME = "tayar_firstname";
    private static final String TAYAR_SECONDNAME = "tayar_secondname";
    private static final String TAYAR_PHONE1 = "tayar_phone1";
    private static final String TAYAR_PHONE2 = "tayar_phone2";
    private static final String TAYAR_EMAIL = "tayar_email";
    private static final String TAYAR_IMAGE = "tayar_image";
    private static Sesstion mInstance;
    private static Context mCtx;
    private Sesstion(Context context) {
        mCtx = context;
    }
    public static synchronized Sesstion getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Sesstion(context);
        }
        return mInstance;
    }
    public void userLogin(Tayar tayar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAYAR_ID, tayar.getId());
        editor.putString(TAYAR_FIRSTNAME, tayar.getFirstname());
        editor.putString(TAYAR_SECONDNAME, tayar.getSecondname());
        editor.putString(TAYAR_PHONE1, tayar.getPhone1());
        editor.putString(TAYAR_PHONE2, tayar.getPhone2());
        editor.putString(TAYAR_EMAIL, tayar.getEmail());
        editor.putString(TAYAR_IMAGE, tayar.getImage());
        editor.apply();
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAYAR_ID, null) != null;
    }
    public Tayar getTayar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Tayar( sharedPreferences.getString(TAYAR_ID, null) ,
                sharedPreferences.getString(TAYAR_FIRSTNAME, null) ,
                sharedPreferences.getString(TAYAR_SECONDNAME, null),
                sharedPreferences.getString(TAYAR_PHONE1, null),
                sharedPreferences.getString(TAYAR_PHONE2, null) ,
                sharedPreferences.getString(TAYAR_EMAIL, null),
                sharedPreferences.getString(TAYAR_IMAGE, null));
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void editProfileUser(String profileImage) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAYAR_IMAGE, profileImage);
        editor.apply();
    }

    public void editPhoneUser(String userPhone) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAYAR_PHONE2, userPhone);
        editor.apply();
    }
}
