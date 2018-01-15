package com.bwie.d.boni;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.d.boni.adapter.LieAdapter;
import com.bwie.d.boni.bean.OneBean;
import com.bwie.d.boni.okhttp.AbstractUiCallBack;
import com.bwie.d.boni.okhttp.OkhttpUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class LieBiaoActivity extends AppCompatActivity {


    private SimpleDraweeView simpleDraweeView;
    private SimpleDraweeView msimple;
    private RecyclerView recyclerView;
    private TextView tv;
    int type = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lie_biao);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.lie_sim);
        msimple = (SimpleDraweeView) findViewById(R.id.lie_msim);
        recyclerView = (RecyclerView) findViewById(R.id.lie_recycle);
        tv= (TextView) findViewById(R.id.tv);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 1);
        if(position==1||position==2){
            type = position;
        }
        else if(position==4||position==5)

        {
            type = position+7;;
        }
        else if(position==6){
            type = position+10;
        }
        else if(position==7||position==8||position==9||position==10||position==11){
            type = position+14;
        }

        OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=" + type + "&size=10&offset=0", new AbstractUiCallBack<OneBean>() {

            private LieAdapter lieAdapter;
            private LinearLayoutManager manager;
            private List<OneBean.SongListBean> song_list;

            @Override
            public void success(OneBean oneBean) {
                String pic_s192 = oneBean.getBillboard().getPic_s640();
                String pic_s444 = oneBean.getBillboard().getPic_s444();
                simpleDraweeView.setImageURI(pic_s192);
                tv.setText(oneBean.getSong_list().get(0).getAlbum_title());
                msimple.setImageURI(pic_s444);
                song_list = oneBean.getSong_list();
                manager = new LinearLayoutManager(LieBiaoActivity.this,LinearLayoutManager.VERTICAL,false);
                lieAdapter = new LieAdapter(LieBiaoActivity.this,oneBean,song_list);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(lieAdapter);
            }
            @Override
            public void failure(Exception e) {
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
