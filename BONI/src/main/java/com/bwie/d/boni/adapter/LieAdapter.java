package com.bwie.d.boni.adapter;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.d.boni.BoActivity;
import com.bwie.d.boni.R;
import com.bwie.d.boni.bean.BoBean;
import com.bwie.d.boni.bean.EventBean;
import com.bwie.d.boni.bean.OneBean;
import com.bwie.d.boni.okhttp.AbstractUiCallBack;
import com.bwie.d.boni.okhttp.OkhttpUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by d on 2017/12/29.
 */

public class LieAdapter extends RecyclerView.Adapter<LieAdapter.IViewHolder> implements View.OnClickListener{
    Context context;
    OneBean oneBean;
    List<OneBean.SongListBean> song_list;
    int i ;
    private Dialog mCameraDialog;

    public LieAdapter(Context context, OneBean oneBean, List<OneBean.SongListBean> song_list) {
        this.context = context;
        this.song_list = song_list;
        this.oneBean = oneBean;
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_lie, null);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, final int position) {
        holder.simpleDraweeView.setImageURI(song_list.get(position).getAlbum_500_500());
        holder.textView1.setText(song_list.get(position).getAlbum_title());
        holder.textView2.setText(song_list.get(position).getArtist_name());
        i = position;
        holder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().postSticky(new EventBean(oneBean.getBillboard().getPic_s192(),song_list.get(position).getArtist_name(),song_list.get(position).getAlbum_title()));
                Intent intent = new Intent(context, BoActivity.class);
                intent.putExtra("flag",0);
                intent.putExtra("songname",song_list.get(position).getTitle());
                intent.putExtra("songautor",song_list.get(position).getAuthor());
                intent.putExtra("songid",song_list.get(position).getSong_id());

                context.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDialog();
            }
        });
    }

    private void setDialog() {
        mCameraDialog = new Dialog(context, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        root.findViewById(R.id.btn_choose_img).setOnClickListener(this);
        root.findViewById(R.id.btn_open_camera).setOnClickListener(this);
        root.findViewById(R.id.btn_cancel).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lie_image:
                //弹出对话框
                setDialog();
                break;
            case R.id.btn_choose_img:
                //选择照片按钮
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "213");
                shareIntent.setType("text/plain");
                //设置分享列表
                context.startActivity(Intent.createChooser(shareIntent, "分享到"));
                mCameraDialog.dismiss();
                break;
            case R.id.btn_open_camera:

                OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=" + song_list.get(i).getSong_id(), new AbstractUiCallBack<BoBean>() {
                    @Override
                    public void success(BoBean boBean) {
                        String file_link = boBean.getBitrate().getFile_link();
                        String title = boBean.getSonginfo().getTitle();
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(file_link));
//指定下载路径和下载文件名
                        request.setDestinationInExternalPublicDir("/download/", title);
//获取下载管理器
                        DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//将下载任务加入下载队列，否则不会进行下载
                        downloadManager.enqueue(request);
                        Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(Exception e) {

                    }
                });
                mCameraDialog.dismiss();
                break;



            case R.id.btn_cancel:
                //取消按钮
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                mCameraDialog.dismiss();
                break;

        }
    }

    @Override
    public int getItemCount() {
        return song_list.size();
    }

    static class IViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView simpleDraweeView;
        private final TextView textView1;
        private final TextView textView2;
        private final ImageView imageView;

        public IViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.lie_image);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sim_adapter);
            textView1 = (TextView) itemView.findViewById(R.id.lie_name1);
            textView2 = (TextView) itemView.findViewById(R.id.lie_name2);
        }
    }
}
