package com.zncm.leanote.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.malinskiy.materialicons.Iconify;
import com.zncm.leanote.R;
import com.zncm.leanote.ft.NoteAddFt;
import com.zncm.leanote.utils.XUtil;


public class NoteAddAc extends BaseAc {
    NoteAddFt addFt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFt = new NoteAddFt();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, addFt)
                .commit();
    }

    @Override
    protected int setCV() {
        return R.layout.common_ac_nobar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        addFt.addNote();
    }














}
