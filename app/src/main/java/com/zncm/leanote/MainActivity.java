package com.zncm.leanote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.data.User;
import com.zncm.leanote.ui.LoginAc;
import com.zncm.leanote.ui.MyAc;
import com.zncm.leanote.utils.MySp;
import com.zncm.leanote.utils.XUtil;

import java.util.List;

import im.fir.sdk.FIR;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = null;
        if (XUtil.notEmptyOrNull(MySp.getUserInfo())) {
            user = new Gson().fromJson(MySp.getUserInfo(), User.class);
        }
        if (user != null) {
            MyApplication.getInstance().user = user;
            Intent intent = new Intent(this, MyAc.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginAc.class);
            startActivity(intent);
            finish();
        }
    }
}
