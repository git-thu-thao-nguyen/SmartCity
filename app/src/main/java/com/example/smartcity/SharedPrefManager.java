package com.example.smartcity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * Singleton Class To Manage Android SharedPreferences
 *
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String dataName = "my_data";
    private static final String keyPseudo = "user_Pseudo";
    private static final String keyFirstName = "user_FirstName";
    private static final String keyLastName = "user_LastName";
    private static final String keyEmail = "user_Email";
    private static final String keyAge = "user_Age";
    private static final String keyCity = "user_City";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /**
     * UTILISATEUR
     */

    public boolean dataUser (String pseudo, String firstname, String lastname, String email, int age, String city) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(keyPseudo,pseudo);
        editor.putString(keyFirstName,firstname);
        editor.putString(keyLastName,lastname);
        editor.putString(keyEmail,email);
        editor.putInt(keyAge,age);
        editor.putString(keyCity,city);
        editor.apply();

        return true;
    }


    public boolean login(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return (sharedPreferences.getString(keyPseudo, null) != null);
    }


    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUserPseudo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyPseudo, null);
    }

    public String getUserFirstName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyFirstName, null);
    }

    public String getUserLastName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyLastName, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyEmail, null);
    }

    public int getUserAge(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyAge, 0);
    }

    public String getUserCity(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyCity, null);
    }

}
