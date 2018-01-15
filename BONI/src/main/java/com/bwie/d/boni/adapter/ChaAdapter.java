package com.bwie.d.boni.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.d.boni.R;
import com.bwie.d.boni.bean.ChaBean;

import java.util.List;

/**
 * Created by d on 2017/12/29.
 */

public class ChaAdapter extends RecyclerView.Adapter<ChaAdapter.IViewHolder>{
    Context context;
    List<ChaBean.SongBean> song;
    public ChaAdapter(Context context, List<ChaBean.SongBean> song) {
        this.context = context;
        this.song = song;
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_cha, null);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, final int position) {
        holder.cha_name1.setText(song.get(position).getArtistname());
        holder.cha_name2.setText(song.get(position).getSongname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"别点了，没歌",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return song.size();
    }

    static class IViewHolder extends RecyclerView.ViewHolder{

        private final TextView cha_name1;
        private final TextView cha_name2;

        public IViewHolder(View itemView) {
            super(itemView);

            cha_name1 = (TextView) itemView.findViewById(R.id.cha_name1);
            cha_name2 = (TextView) itemView.findViewById(R.id.cha_name2);
        }
    }
}
