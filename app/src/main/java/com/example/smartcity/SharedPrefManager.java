package com.example.smartcity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartcity.Activity.Login;
import com.example.smartcity.Activity.Profil;
import com.google.android.gms.common.annotation.KeepName;

/**
 *
 * Singleton Class To Manage Android SharedPreferences
 *
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String dataName = "my_data";
    private static final String keyId = "user_Id";
    private static final String keyPseudo = "user_Pseudo";
    private static final String keyFirstName = "user_FirstName";
    private static final String keyLastName = "user_LastName";
    private static final String keyEmail = "user_Email";
    private static final String keyAge = "user_Age";
    private static final String keyCity = "user_City";
    private static final String keyMDP = "user_MDP";

    private static final String keyNetworkId = "network_id";
    private static final String keyNetworkAdmin = "network_admin";
    private static final String keyNetworkName = "network_name";
    private static final String keyNetworkType = "network_type";
    private static final String keyKeywordNetwork = "network_keyword";
    private static final String keyStatusNetwork = "network_network_status";

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

    public boolean set_DataUser (int id, String pseudo, String firstname, String lastname, String email, int age, String city) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(keyId,id);
        editor.putString(keyPseudo,pseudo);
        editor.putString(keyFirstName,firstname);
        editor.putString(keyLastName,lastname);
        editor.putString(keyEmail,email);
        editor.putInt(keyAge,age);
        editor.putString(keyCity,city);
        editor.apply();

        return true;
    }


    public boolean isLoggin(){
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


    public int getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyId, 0);
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


    /**
     * RESEAU SOCIAL
     */


    public boolean set_network(int id, String admin, String name, String type, int status){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(keyNetworkId, id);
        editor.putString(keyNetworkAdmin, admin);
        editor.putString(keyNetworkName, name);
        editor.putString(keyNetworkType, type);
        editor.putInt(keyStatusNetwork,status);

        editor.apply();

        return true;
    }


    public int getNetworkId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyNetworkId, 0);
    }

    public String getNetworkAdmin(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyNetworkAdmin, null);
    }

    public String getNetworkName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyNetworkName, null);
    }

    public String getNetworkType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyNetworkType, null);
    }

    public String getKeywordNetwork(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyKeywordNetwork, null);
    }

    public int getStatusNetwork(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(dataName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(keyStatusNetwork, 0);
    }

}
