package com.zncm.leanote.ft;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cocosw.bottomsheet.BottomSheet;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.adapter.NoteAdapter;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Msg;
import com.zncm.leanote.data.Note;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.services.NoteService;
import com.zncm.leanote.services.ServiceFactory;
import com.zncm.leanote.ui.MyAc;
import com.zncm.leanote.ui.NoteAddAc;
import com.zncm.leanote.ui.NoteDetailsAc;
import com.zncm.leanote.utils.XUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NoteFt extends BaseListFt {
    private NoteAdapter mAdapter;
    private MyAc ctx;
    private List<Note> datas = null;
    private boolean onLoading = false;
    Notebooks curBook = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ctx = (MyAc) getActivity();
        emptyText.setText("日记...");
        addButton.setVisibility(View.VISIBLE);


        datas = new ArrayList<Note>();
        mAdapter = new NoteAdapter(ctx) {
            @Override
            public void setData(final int position, MyViewHolder holder) {
                final Note data = datas.get(position);

                if (XUtil.notEmptyOrNull(data.Title)) {
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(data.Title);
                } else {
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if (XUtil.notEmptyOrNull(data.Desc)) {
                    holder.tvContent.setVisibility(View.VISIBLE);
                    holder.tvContent.setText(Html.fromHtml(data.Desc));
                } else {
                    holder.tvContent.setVisibility(View.GONE);
                }


            }


        };
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @SuppressWarnings({"rawtypes", "unchecked"})
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                int curPosition = position - listView.getHeaderViewsCount();
                                                Note data = datas.get(curPosition);
                                                if (data == null) {
                                                    return;
                                                }
                                                Intent intent = new Intent(ctx, NoteDetailsAc.class);
                                                intent.putExtra(Key.KEY_PARAM_DATA, data);
                                                startActivity(intent);
                                            }
                                        }

        );


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                bottomShow(position);
                return true;
            }
        });

        getData(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, NoteAddAc.class);
                intent.putExtra(Key.KEY_PARAM_DATA, ctx.curBook);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bottomShow(int position) {
        final Note data = datas.get(position);
        new BottomSheet.Builder(ctx).title(data.Title).sheet(R.menu.bottom_note).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.edit:
                        XUtil.tShort(Key.FIR_UNREALIZE);
                        break;
                    case R.id.delete:
                        delNote(data);
                        break;
                    case R.id.share:
                        XUtil.tShort(Key.FIR_UNREALIZE);
                        break;
                }
            }
        }).show();
    }

    private void delNote(final Note data) {
        ServiceFactory.getService(NoteService.class).deleteNote(MyApplication.getInstance().user.Token, data.NoteId, data.Usn, new Callback<Msg>() {
            @Override
            public void success(Msg msg, Response response) {
                XUtil.tShort("删除笔记:" + msg.ret(msg));
                if (msg.Ok) {
                    datas.remove(data);
                    mAdapter.setItems(datas);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void getData(boolean bFirst) {
        try {
            curBook = ctx.curBook;
            if (curBook == null) {
                curBook = new Notebooks();
                curBook.NotebookId = "550001b99cb5f036c9000001";
            }

            //MyApplication.getInstance().user.Token
            //55015ea938f41165dd000110 550001b99cb5f036c9000001 550001b99cb5f036c9000001
            datas = new ArrayList<Note>();
            ServiceFactory.getService(NoteService.class).getNotes(MyApplication.getInstance().user.Token, curBook.NotebookId, new Callback<List<Note>>() {
                @Override
                public void success(List<Note> notes, Response response) {

//                MyApplication.getInstance().user = user;
//                MySp.setUserInfo(user.toString());

                    XUtil.debug("==>" + notes);
                    if (XUtil.listNotNull(notes)) {
                        datas.addAll(notes);
                        for (Note tmp : notes) {
                            XUtil.debug("tmp" + tmp.Title);
                        }
                    }

                    mAdapter.setItems(datas);
                    loadingRet();

                }

                @Override
                public void failure(RetrofitError error) {


                    loadingRet();

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadingRet() {
        swipeLayout.setRefreshing(false);
        onLoading = false;
        listView.setCanLoadMore(false);
        listView.onLoadMoreComplete();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }


    @Override
    public void onRefresh() {
        if (onLoading) {
            return;
        }
        refresh();
    }

    private void refresh() {
        onLoading = true;
        swipeLayout.setRefreshing(true);
        getData(true);
    }


    @Override
    public void onLoadMore() {

        getData(false);

    }


}
