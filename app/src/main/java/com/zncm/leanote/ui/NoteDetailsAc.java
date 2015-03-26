package com.zncm.leanote.ui;

import android.os.Bundle;

import com.zncm.leanote.R;
import com.zncm.leanote.ft.NoteDetailsFt;


public class NoteDetailsAc extends BaseAc {
    NoteDetailsFt detailsFt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsFt = new NoteDetailsFt();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailsFt)
                .commit();
    }


    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected int setCV() {
        return R.layout.common_ac;
    }


}
