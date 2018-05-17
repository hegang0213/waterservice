package com.bdwater.waterservice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.DocumentsContract;

import com.bdwater.waterservice.MainActivity;
import com.google.gson.Gson;

/**
 * Created by hegang on 2018/5/17.
 */

public class StorageUtil {
    private final static String TAG = "StorageUtil";
    private final static String ROOT = "root";
    public final static String USER = "user";
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(ROOT, Context.MODE_PRIVATE);
    }
    public static void putString(Context context, String tag, String value) {
        SharedPreferences sp = getSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(tag, value);
        editor.commit();
    }
    public static String getString(Context context, String tag) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(tag, null);
    }
    public static void put(Context context, String tag, Object object) {
        String value = new Gson().toJson(object);
        putString(context, tag, value);
    }
    public static <T> T get(Context context, String tag, Class<T> classOfT) {
        String value = getString(context, tag);
        if(value == null) return null;
        return new Gson().fromJson(value, classOfT);
    }

}
