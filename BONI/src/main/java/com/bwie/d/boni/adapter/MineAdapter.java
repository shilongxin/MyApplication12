package com.bwie.d.boni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.d.boni.R;
import com.bwie.d.boni.util.Model;

import java.util.List;

/**
 * Created by d on 2018/1/7.
 */

public class MineAdapter extends BaseAdapter {
    List<Model> list;
    Context context;

    public MineAdapter(List<Model> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list2, null, false);
            holder = new ViewHolder();
            holder.text_song = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_autor = (TextView) convertView.findViewById(R.id.text_autor);
//            holder.simple= (SimpleDraweeView) convertView.findViewById(R.id.simple);
            holder.image = (ImageView) convertView.findViewById(R.id.image_more);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text_song.setText(list.get(position).getText_song());
        holder.text_autor.setText(list.get(position).getText_singer());


        return convertView;
    }

    public class ViewHolder {
        TextView text_song;
        TextView text_autor;
        ImageView image;

    }

}
