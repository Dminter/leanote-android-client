package com.zncm.leanote;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.activeandroid.ActiveAndroid;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.data.User;

import java.util.List;

import im.fir.sdk.FIR;


public class MyApplication extends Application {
    public Context ctx;
    public static MyApplication instance;
    public User user;
    public Notebooks curBook;
    public List<Notebooks> fullNotebookses;

    @Override
    public void onCreate() {
        FIR.init(this);
        super.onCreate();
        this.ctx = this.getApplicationContext();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }


}
