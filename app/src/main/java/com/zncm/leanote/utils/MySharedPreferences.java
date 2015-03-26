package com.zncm.leanote.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.zncm.leanote.MyApplication;

import java.util.Map;

public class MySharedPreferences {

    public static SharedPreferences getPreferences(String fileString) {
        return MyApplication.getInstance().getSharedPreferences(fileString, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(SharedPreferences perference,
                                     String keyString, Boolean defaultBoolean) {
        return perference.getBoolean(keyString, defaultBoolean.booleanValue());
    }

    public static void putBoolean(SharedPreferences perPreference,
                                  String keyString, Boolean booleanValue) {
        perPreference.edit().putBoolean(keyString, booleanValue).commit();
    }


    public static float getFloat(SharedPreferences perference,
                                 String keyString, Float defaultFloat) {
        return perference.getFloat(keyString, defaultFloat.floatValue());
    }


    public static void putFloat(SharedPreferences perference, String keyString,
                                Float floatValue) {
        perference.edit().putFloat(keyString, floatValue.floatValue()).commit();
    }


    public static int getInt(SharedPreferences perference, String keyString,
                             Integer defaultInteger) {
        return perference.getInt(keyString, defaultInteger.intValue());
    }


    public static void putInt(SharedPreferences perference, String keyString,
                              Integer integerVaule) {
        perference.edit().putInt(keyString, integerVaule.intValue()).commit();
    }


    public static Long getLong(SharedPreferences perference, String keyString,
                               Long defaultLong) {
        return Long.valueOf(perference.getLong(keyString,
                defaultLong.longValue()));
    }


    public static void putLong(SharedPreferences perference, String keyString,
                               Long longVaule) {
        perference.edit().putLong(keyString, longVaule.longValue()).commit();
    }


    public static String getString(SharedPreferences perference,
                                   String keyString, String defaultString) {
        return perference.getString(keyString, defaultString);
    }


    public static void putString(SharedPreferences perference,
                                 String keyString, String stringVaule) {
        perference.edit().putString(keyString, stringVaule).commit();
    }


    public static Map<String, ?> getAllKeyValues(SharedPreferences perference) {
        return perference.getAll();
    }


    public static void clearAllKeyValues(SharedPreferences perference) {
        perference.edit().clear().commit();
    }


    public static boolean containKey(SharedPreferences perPreference,
                                     String keyString) {
        return perPreference.contains(keyString);
    }


    public static void removeKey(SharedPreferences perference, String keyString) {
        perference.edit().remove(keyString).commit();
    }
}
