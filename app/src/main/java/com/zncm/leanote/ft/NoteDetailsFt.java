package com.zncm.leanote.ft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.malinskiy.materialicons.Iconify;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Note;
import com.zncm.leanote.data.NoteDetails;
import com.zncm.leanote.data.NoteEx;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.services.NoteService;
import com.zncm.leanote.services.ServiceFactory;
import com.zncm.leanote.ui.NoteAddAc;
import com.zncm.leanote.ui.NoteDetailsAc;
import com.zncm.leanote.utils.XUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NoteDetailsFt extends BaseFt {
    Note note = null;
    NoteDetails details = null;
    private TextView tvTag, tvContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ctx = (Activity) getActivity();
        view = inflater.inflate(R.layout.note_details_ft, null);
        Serializable dataParam = ctx.getIntent().getSerializableExtra(Key.KEY_PARAM_DATA);
        note = (Note) dataParam;


        if (XUtil.notEmptyOrNull(note.Title)) {
            ctx.setTitle(note.Title);
        } else {
            ctx.setTitle("未命名");
        }


        tvTag = (TextView) view.findViewById(R.id.tvTag);
        tvContent = (TextView) view.findViewById(R.id.tvContent);

//        tvTag.setText(note.T);


        ServiceFactory.getService(NoteService.class).getNoteContent(MyApplication.getInstance().user.Token, note.NoteId, new Callback<NoteDetails>() {
            @Override
            public void success(NoteDetails details, Response response) {
                XUtil.debug("details:" + details.toString());
                if (details != null) {
                    NoteDetailsFt.this.details = details;
                    if (XUtil.notEmptyOrNull(details.Content)) {
                        tvContent.setVisibility(View.VISIBLE);
                        tvContent.setText(Html.fromHtml(details.Content));
                    } else {
                        tvContent.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("edit").setIcon(XUtil.initIconWhite(Iconify.IconValue.md_edit)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item == null || item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals("edit")) {
            NoteEx noteEx = new NoteEx(details.Content, details.Abstract, note);
            Intent intent = new Intent(ctx, NoteAddAc.class);
            intent.putExtra(Key.KEY_PARAM_DATA2, noteEx);
            intent.putExtra(Key.KEY_PARAM_BOOLEAN, true);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
