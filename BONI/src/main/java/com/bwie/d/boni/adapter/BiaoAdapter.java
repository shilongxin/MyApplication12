package com.bwie.d.boni.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.d.boni.R;
import com.bwie.d.boni.bean.OneBean;
import com.bwie.d.boni.okhttp.AbstractUiCallBack;
import com.bwie.d.boni.okhttp.OkhttpUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by d on 2017/12/29.
 */

public class BiaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final static int TYPE_1 = 0;
    public final static int TYPE_2 = 1;
    FragmentActivity activity;
    public BiaoAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(TYPE_1==viewType){
            view = View.inflate(activity, R.layout.layout_song1, null);
            return new IViewHolder1(view);
        }
        else if(TYPE_2==viewType){
            view = View.inflate(activity, R.layout.layout_song2,null);
            return new IViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof IViewHolder1){
            if(position==0){
                ((IViewHolder1) holder).textView.setText("主打榜单");
            }
            else if(position==3){
                ((IViewHolder1) holder).textView.setText("分类榜单");
            }
        }
        else if(holder instanceof IViewHolder2) {
            if(position==1||position==2){
                OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type="+position+"&size=10&offset=0", new AbstractUiCallBack<OneBean>() {
                    @Override
                    public void success(OneBean oneBean) {
                        ((IViewHolder2) holder).simpleDraweeView.setImageURI(oneBean.getBillboard().getPic_s192());
                        ((IViewHolder2) holder).textView1.setText("1."+oneBean.getSong_list().get(0).getTitle()+oneBean.getSong_list().get(0).getArtist_name());
                        ((IViewHolder2) holder).textView2.setText("2."+oneBean.getSong_list().get(1).getTitle()+oneBean.getSong_list().get(1).getArtist_name());
                        ((IViewHolder2) holder).textView3.setText("3."+oneBean.getSong_list().get(2).getTitle()+oneBean.getSong_list().get(2).getArtist_name());
                    }

                    @Override
                    public void failure(Exception e) {

                    }
                });
            }

            else if(position==4||position==5){
                int i = position + 7;
                OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type="+i+"&size=10&offset=0", new AbstractUiCallBack<OneBean>() {
                    @Override
                    public void success(OneBean oneBean) {
                        ((IViewHolder2) holder).simpleDraweeView.setImageURI(oneBean.getBillboard().getPic_s192());
                        ((IViewHolder2) holder).textView1.setText("1."+oneBean.getSong_list().get(0).getTitle()+oneBean.getSong_list().get(0).getArtist_name());
                        ((IViewHolder2) holder).textView2.setText("2."+oneBean.getSong_list().get(1).getTitle()+oneBean.getSong_list().get(1).getArtist_name());
                        ((IViewHolder2) holder).textView3.setText("3."+oneBean.getSong_list().get(2).getTitle()+oneBean.getSong_list().get(2).getArtist_name());
                    }

                    @Override
                    public void failure(Exception e) {

                    }
                });
            }
            else if(position==6){
                int i = position + 10;
                OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type="+i+"&size=10&offset=0", new AbstractUiCallBack<OneBean>() {
                    @Override
                    public void success(OneBean oneBean) {
                        ((IViewHolder2) holder).simpleDraweeView.setImageURI(oneBean.getBillboard().getPic_s192());
                        ((IViewHolder2) holder).textView1.setText("1."+oneBean.getSong_list().get(0).getTitle()+oneBean.getSong_list().get(0).getArtist_name());
                        ((IViewHolder2) holder).textView2.setText("2."+oneBean.getSong_list().get(1).getTitle()+oneBean.getSong_list().get(1).getArtist_name());
                        ((IViewHolder2) holder).textView3.setText("3."+oneBean.getSong_list().get(2).getTitle()+oneBean.getSong_list().get(2).getArtist_name());
                    }

                    @Override
                    public void failure(Exception e) {

                    }
                });
            }

            else if(position==7||position==8||position==9||position==10||position==11){
                int i = position + 14;
                OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type="+i+"&size=10&offset=0", new AbstractUiCallBack<OneBean>() {
                    @Override
                    public void success(OneBean oneBean) {
                        ((IViewHolder2) holder).simpleDraweeView.setImageURI(oneBean.getBillboard().getPic_s192());
                        ((IViewHolder2) holder).textView1.setText("1."+oneBean.getSong_list().get(0).getTitle()+oneBean.getSong_list().get(0).getArtist_name());
                        ((IViewHolder2) holder).textView2.setText("2."+oneBean.getSong_list().get(1).getTitle()+oneBean.getSong_list().get(1).getArtist_name());
                        ((IViewHolder2) holder).textView3.setText("3."+oneBean.getSong_list().get(2).getTitle()+oneBean.getSong_list().get(2).getArtist_name());
                    }

                    @Override
                    public void failure(Exception e) {

                    }
                });
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.click(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_1;
        }
        else if(position==3){
            return TYPE_1;
        }
        return TYPE_2;
    }

    static class IViewHolder1 extends RecyclerView.ViewHolder{

        private final TextView textView;

        public IViewHolder1(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.song_text);

        }
    }

    static class IViewHolder2 extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simpleDraweeView;
        private final TextView textView1;
        private final TextView textView2;
        private final TextView textView3;

        public IViewHolder2(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.song_sim);
            textView1 = (TextView) itemView.findViewById(R.id.song_type1);
            textView2 = (TextView) itemView.findViewById(R.id.song_type2);
            textView3 = (TextView) itemView.findViewById(R.id.song_type3);
        }
    }

    onClickListener listener;
    public void setClickListener(onClickListener listener){
        this.listener = listener;
    }

    public interface onClickListener{
        public void click(View view, int position);
    }
}
