package com.bwie.d.boni.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.d.boni.R;

import java.util.List;

/**
 * Created by d on 2017/12/28.
 */

public class DrawListAdapter extends BaseAdapter{
    Context context;
    List<String> data;
    List<Integer> image;
    public DrawListAdapter(Context context, List<String> data, List<Integer> image) {
        this.context = context;
        this.data = data;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int i) {
        return image.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context,R.layout.layout_draw,null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.draw_text);
            viewHolder.mVmageView = (ImageView) view.findViewById(R.id.draw_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(data.get(i));
        viewHolder.mVmageView.setImageResource(image.get(i));
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;


    }
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }


    class ViewHolder {
        TextView mTextView;
        ImageView mVmageView;
    }
}
