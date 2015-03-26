package com.zncm.leanote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.malinskiy.materialicons.Iconify;
import com.zncm.leanote.R;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.ft.NotebooksFt;
import com.zncm.leanote.utils.MySp;
import com.zncm.leanote.utils.XUtil;


public class NoteBookAc extends BaseAc {
    NotebooksFt notebooksFt;
    Notebooks curBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        notebooksFt = new NotebooksFt();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, notebooksFt)
                .commit();
    }

    public void initTitle() {
        if (XUtil.notEmptyOrNull(MySp.getDefaultNoteBook())) {
            curBook = new Gson().fromJson(MySp.getDefaultNoteBook(), Notebooks.class);
        }
        getSupportActionBar().setTitle("笔记本管理 " + curBook.Title + "(默认)");
    }

    @Override
    protected int setCV() {
        return R.layout.common_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("add").setIcon(XUtil.initIconWhite(Iconify.IconValue.md_add)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        if (item == null || item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals("add")) {
            notebooksFt.initAddEtDlg();
        }

        return super.onOptionsItemSelected(item);
    }


}
