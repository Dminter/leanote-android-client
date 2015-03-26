package com.zncm.leanote.ft;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;
import com.zncm.leanote.adapter.NoteBookAdapter;
import com.zncm.leanote.data.Key;
import com.zncm.leanote.data.Msg;
import com.zncm.leanote.data.Note;
import com.zncm.leanote.data.Notebooks;
import com.zncm.leanote.services.NoteService;
import com.zncm.leanote.services.NotebooksService;
import com.zncm.leanote.services.ServiceFactory;
import com.zncm.leanote.ui.MyAc;
import com.zncm.leanote.ui.NoteBookAc;
import com.zncm.leanote.utils.MySp;
import com.zncm.leanote.utils.XUtil;


import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NotebooksFt extends BaseListFt {
    private NoteBookAdapter mAdapter;
    private Activity ctx;
    private List<Notebooks> datas = null;
    private boolean onLoading = false;
    private boolean canEditable = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ctx = (Activity) getActivity();

        canEditable = ctx.getIntent().getBooleanExtra(Key.KEY_PARAM_BOOLEAN, false);


        emptyText.setText("日记本...");
        addButton.setVisibility(View.GONE);
        datas = new ArrayList<Notebooks>();
        mAdapter = new NoteBookAdapter(ctx) {
            @Override
            public void setData(final int position, MyViewHolder holder) {
                final Notebooks data = datas.get(position);


                if (XUtil.notEmptyOrNull(data.Title)) {
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(data.Title + "(" + data.NumberNotes + ")");
                } else {
                    holder.tvTitle.setVisibility(View.GONE);
                }

            }


        };
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @SuppressWarnings({"rawtypes", "unchecked"})
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                int curPosition = position - listView.getHeaderViewsCount();
                                                Notebooks data = datas.get(curPosition);
                                                if (data == null) {
                                                    return;
                                                }

                                                if (ctx instanceof MyAc) {
                                                    MyAc tmp = (MyAc) ctx;
                                                    tmp.curBook = data;
                                                    tmp.bookChange();
                                                }


                                            }
                                        }

        );


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                if (canEditable) {
                    bottomShow(position);
                }


                return true;
            }
        });

        getData(false);


        return view;
    }


    private void bottomShow(final int position) {
        final Notebooks data = datas.get(position);
        new BottomSheet.Builder(ctx).title(data.Title).sheet(R.menu.bottom_notebook).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.edit:
                        initEtDlg(position, data);
                        break;
                    case R.id.delete:
                        int numbernotes = data.NumberNotes;
                        new MaterialDialog.Builder(ctx)
                                .title("删除笔记本!")
                                .content("笔记本下含有" + numbernotes + "篇笔记,确认删除?")
                                .theme(Theme.LIGHT)
                                .positiveText("删除")
                                .negativeText("取消")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        delNoteBook(data);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        super.onNegative(dialog);
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        break;
                    case R.id.set_default:
                        MySp.setDefaultNoteBook(data.toString());
                        if (ctx instanceof NoteBookAc) {
                            NoteBookAc tmp = (NoteBookAc) ctx;
                            tmp.initTitle();
                        }
                        break;

                }
            }
        }).show();
    }


    void initEtDlg(final int pos, final Notebooks data) {

        LinearLayout view = new LinearLayout(ctx);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final EditText editText = new EditText(ctx);
        XUtil.autoKeyBoardShow(editText);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        editText.setLines(5);
        editText.setHint("输入...");
        editText.setTextColor(getResources().getColor(R.color.black));
        editText.setText(data.Title);
        editText.setSelection(data.Title.length());
        editText.setBackgroundDrawable(new BitmapDrawable());
        view.addView(editText);
        MaterialDialog md = new MaterialDialog.Builder(ctx)
                .customView(view)
                .positiveText("修改")
                .positiveColor(getResources().getColor(R.color.positive_color))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.negative_color))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        final String content = editText.getText().toString().trim();
                        if (!XUtil.notEmptyOrNull(content)) {
                            XUtil.tShort("输入内容~");
                            XUtil.dismissShowDialog(dialog, false);
                            return;
                        }


                        ServiceFactory.getService(NotebooksService.class).updateNotebook(MyApplication.getInstance().user.Token, data.NotebookId, content, null, data.Seq, data.Usn, new Callback<Msg>() {
                            @Override
                            public void success(Msg msg, Response response) {
                                XUtil.tShort("修改笔记本:" + msg.ret(msg));


//                                if (msg.Ok) {
                                data.Title = content;
                                datas.set(pos, data);
                                mAdapter.setItems(datas);
//                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false)
                .build();
        md.setCancelable(false);
        md.show();

    }

    public void initAddEtDlg() {

        LinearLayout view = new LinearLayout(ctx);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final EditText editText = new EditText(ctx);
        XUtil.autoKeyBoardShow(editText);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        editText.setLines(5);
        editText.setHint("笔记本名称");
        editText.setTextColor(getResources().getColor(R.color.black));
        editText.setBackgroundDrawable(new BitmapDrawable());
        view.addView(editText);
        MaterialDialog md = new MaterialDialog.Builder(ctx)
                .customView(view)
                .positiveText("添加")
                .positiveColor(getResources().getColor(R.color.positive_color))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.negative_color))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(final MaterialDialog dialog) {
                        super.onPositive(dialog);
                        String content = editText.getText().toString().trim();
                        if (!XUtil.notEmptyOrNull(content)) {
                            XUtil.tShort("输入笔记本名称~");
                            XUtil.dismissShowDialog(dialog, false);
                            return;
                        }


                        ServiceFactory.getService(NotebooksService.class).addNotebook(MyApplication.getInstance().user.Token, content, null, 0, new Callback<Msg>() {
                            @Override
                            public void success(Msg msg, Response response) {
                                XUtil.tShort("添加笔记本:" + msg.ret(msg));

                                refresh();
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .autoDismiss(false)
                .build();
        md.setCancelable(false);
        md.show();

    }


    private void delNoteBook(final Notebooks data) {
        ServiceFactory.getService(NotebooksService.class).deleteNotebook(MyApplication.getInstance().user.Token, data.NotebookId, data.Usn, new Callback<Msg>() {
            @Override
            public void success(Msg msg, Response response) {
                XUtil.tShort("删除笔记本:" + msg.ret(msg));

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


    private void getData(boolean bSync) {
        try {
            //MyApplication.getInstance().user.Token
            //55015ea938f41165dd000110

            datas = new ArrayList<>();

            if (!bSync) {
                if (XUtil.notEmptyOrNull(MySp.getFullNoteBook())) {
                    datas = new Gson().fromJson(MySp.getFullNoteBook(), new TypeToken<List<Notebooks>>() {
                    }.getType());
                }
                if (XUtil.listNotNull(datas)) {
                    MyApplication.getInstance().fullNotebookses = datas;
                    initView();
                    return;
                }
            }

            ServiceFactory.getService(NotebooksService.class).getNotebooks(MyApplication.getInstance().user.Token, new Callback<List<Notebooks>>() {
                @Override
                public void success(List<Notebooks> notebooks, Response response) {
                    if (XUtil.listNotNull(notebooks)) {
                        datas.addAll(notebooks);
                        MyApplication.getInstance().fullNotebookses = datas;
                        MySp.setFullNoteBook(notebooks.toString());
                        if (!XUtil.notEmptyOrNull(MySp.getDefaultNoteBook())) {
                            MySp.setDefaultNoteBook(datas.get(datas.size() - 1).toString());
                        }
                    }
                    initView();
                }

                @Override
                public void failure(RetrofitError error) {
                    XUtil.debug("error:" + error);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mAdapter.setItems(datas);
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
