package com.pxy.miniweather.util;

import android.content.Context;
import android.content.SharedPreferences;

//封装的SharedPreferences工具类，可以直接使用
public class SharedPreferencesUtil {
    private static final String PREFS_NAME = "my_prefs";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void saveInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void saveLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void saveFloat(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void saveString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
