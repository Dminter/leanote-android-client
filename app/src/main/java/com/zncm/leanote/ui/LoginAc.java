package com.zncm.leanote.ui;

import android.os.Bundle;

import com.zncm.leanote.R;
import com.zncm.leanote.ft.LoginFt;


public class LoginAc extends BaseAc {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFt())
                .commit();
    }

    @Override
    protected int setCV() {
        return R.layout.common_ac_nobar;
    }


}
