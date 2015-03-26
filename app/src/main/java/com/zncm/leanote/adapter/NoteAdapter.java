package com.zncm.leanote.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zncm.leanote.R;
import com.zncm.leanote.data.Base;

import java.util.List;

public abstract class NoteAdapter extends BaseAdapter {

    private List<? extends Base> items;
    private Activity ctx;

    public NoteAdapter(Activity ctx) {
        this.ctx = ctx;
    }

    public void setItems(List<? extends Base> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (items != null) {
            return items.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (items != null) {
            return position;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(
                    R.layout.cell_note, null);
            holder = new MyViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        setData(position, holder);

        return convertView;
    }

    public abstract void setData(int position, MyViewHolder holder);

    public class MyViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
    }
}