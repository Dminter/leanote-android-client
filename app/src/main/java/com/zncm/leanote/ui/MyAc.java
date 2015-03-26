package com.zncm.leanote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.ft.NoteFt;
import com.zncm.leanote.ft.NotebooksFt;
import com.zncm.leanote.utils.MySp;
import com.zncm.leanote.utils.XUtil;


public class MyAc extends ActionBarActivity {
    NotebooksFt notebooksFt;
    View notebooksView;
    public Notebooks curBook = null;
    NoteFt noteFt;
    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        notebooksView = findViewById(R.id.notebooksView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        notebooksFt = new NotebooksFt();
        getSupportFragmentManager().beginTransaction().replace(R.id.notebooksView, notebooksFt).commit();
        noteFt = new NoteFt();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, noteFt).commit();
        if (XUtil.notEmptyOrNull(MySp.getDefaultNoteBook())) {
            curBook = new Gson().fromJson(MySp.getDefaultNoteBook(), Notebooks.class);
        }
        if (curBook != null) {
            MyApplication.getInstance().curBook = curBook;
        }
    }


    public void bookChange() {
        closeFt();
        getSupportActionBar().setTitle(curBook.Title);
        noteFt.onRefresh();
    }


    public void closeFt() {
        mDrawerLayout.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            MySp.setUserInfo(null);
            MySp.setFullNoteBook(null);
            MySp.setDefaultNoteBook(null);
            finish();
            return true;
        }
        if (id == R.id.action_notebook) {
            Intent intent = new Intent(this, NoteBookAc.class);
            intent.putExtra(Key.KEY_PARAM_BOOLEAN, true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
