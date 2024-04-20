package com.moutamid.exercises.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Config {
    public static String preference_name = "Exercise App";
    static SharedPreferences sharedpreferences;
    static SharedPreferences.Editor editor;

    public static void storeValue(String key, int value, Context context) {
        sharedpreferences = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void storeStringValue(String key, String value, Context context) {
        sharedpreferences = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static int getValue(String key, Context context) {
        sharedpreferences = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        return sharedpreferences.getInt(key, 1);
    }
    public static String getStringValue(String key, Context context) {
        sharedpreferences = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "Guest");
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
