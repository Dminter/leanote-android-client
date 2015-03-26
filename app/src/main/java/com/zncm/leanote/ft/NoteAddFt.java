package com.zncm.leanote.ft;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.malinskiy.materialicons.Iconify;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Msg;
import com.zncm.leanote.data.Note;
import com.zncm.leanote.data.NoteEx;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.services.NoteService;
import com.zncm.leanote.services.ServiceFactory;
import com.zncm.leanote.ui.MyAc;
import com.zncm.leanote.ui.NoteAddAc;
import com.zncm.leanote.utils.XUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NoteAddFt extends BaseFt {
    Notebooks curBook = null;
    private EditText etTitle, etContent;
    String title;
    String content;
    String tags;
    String mAbstract;
    boolean isMarkdown = false;
    boolean isEdit = false;
    private Spinner spTab;

    NoteEx noteEx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ctx = (NoteAddAc) getActivity();


        view = inflater.inflate(R.layout.note_add_ft, null);


        Serializable dataParam = ctx.getIntent().getSerializableExtra(Key.KEY_PARAM_DATA);
        curBook = (Notebooks) dataParam;
        isEdit = ctx.getIntent().getBooleanExtra(Key.KEY_PARAM_BOOLEAN, false);

        Serializable dataNoteEx = ctx.getIntent().getSerializableExtra(Key.KEY_PARAM_DATA2);
        noteEx = (NoteEx) dataNoteEx;

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etContent = (EditText) view.findViewById(R.id.etContent);
        spTab = (Spinner) view.findViewById(R.id.spTab);
        List<String> tags = new ArrayList<>();
        final List<Notebooks> tmp = MyApplication.getInstance().fullNotebookses;
        if (XUtil.listNotNull(tmp)) {
            for (Notebooks data : tmp) {
                tags.add(data.Title);
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(ctx, android.R.layout.simple_list_item_1, tags);
        spTab.setAdapter(adapter);
        spTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curBook = tmp.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (isEdit) {
            spTab.setEnabled(false);
            etTitle.setText(noteEx.Title);
            etContent.setText(noteEx.Content);
            etContent.setSelection(noteEx.Content.length());
        } else {
            int pos = -1;
            if (XUtil.listNotNull(tmp)) {
                for (int i = 0; i < tmp.size(); i++) {
                    if (curBook.NotebookId.equals(tmp.get(i).NotebookId)) {
                        pos = i;
                        break;
                    }
                }
            }
            spTab.setSelection(pos);

        }

        return view;
    }


    public void addNote() {
        try {
            title = etTitle.getText().toString().trim();
            content = etContent.getText().toString().trim();

            if (isEdit) {
                ServiceFactory.getService(NoteService.class).updateNote(MyApplication.getInstance().user.Token, noteEx.NoteId, noteEx.Usn, noteEx.NotebookId, title, null, content, null, false, false, new Callback<Msg>() {
                    @Override
                    public void success(Msg msg, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            } else {
                ServiceFactory.getService(NoteService.class).addNote(MyApplication.getInstance().user.Token, curBook.NotebookId, title, tags, content, mAbstract, isMarkdown, new Callback<Note>() {
                    @Override
                    public void success(Note note, Response response) {
                        XUtil.debug("==>" + note);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        XUtil.debug("error:" + error);
                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("save").setIcon(XUtil.initIconWhite(Iconify.IconValue.md_save)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item == null || item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals("save")) {
            addNote();
        }

        return super.onOptionsItemSelected(item);
    }


}
