package com.zncm.leanote.utils;

import android.content.SharedPreferences;

public class MySp extends MySharedPreferences {
    private static final String STATE_PREFERENCE = "state_preference";
    private static SharedPreferences sharedPreferences;

    enum Key {
        app_version_code,
        user_info,
        full_note_book,
        default_note_book,
        default_note_book_id,
    }


    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = getPreferences(STATE_PREFERENCE);
        return sharedPreferences;
    }


    public static void setAppVersionCode(Integer app_version_code) {
        putInt(getSharedPreferences(), Key.app_version_code.toString(), app_version_code);
    }

    public static Integer getAppVersionCode() {
        return getInt(getSharedPreferences(), Key.app_version_code.toString(), 0);
    }

    public static void setUserInfo(String user_info) {
        putString(getSharedPreferences(), Key.user_info.toString(), user_info);
    }

    public static String getUserInfo() {
        return getString(getSharedPreferences(), Key.user_info.toString(), "");
    }

    public static void setDefaultNoteBook(String default_note_book) {
        putString(getSharedPreferences(), Key.default_note_book.toString(), default_note_book);
    }

    public static String getDefaultNoteBook() {
        return getString(getSharedPreferences(), Key.default_note_book.toString(), "");
    }

    public static void setFullNoteBook(String full_note_book) {
        putString(getSharedPreferences(), Key.full_note_book.toString(), full_note_book);
    }

    public static String getFullNoteBook() {
        return getString(getSharedPreferences(), Key.full_note_book.toString(), "");
    }


}
