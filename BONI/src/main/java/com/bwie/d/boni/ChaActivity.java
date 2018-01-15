package com.bwie.d.boni;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.d.boni.adapter.ChaAdapter;
import com.bwie.d.boni.bean.ChaBean;
import com.bwie.d.boni.okhttp.AbstractUiCallBack;
import com.bwie.d.boni.okhttp.OkhttpUtils;

import java.util.List;

public class ChaActivity extends AppCompatActivity {

    private EditText cha_ed;
    private ImageView cha_fan;
    private ImageView cha_qr;
    private RecyclerView cha_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha);

        cha_ed = (EditText) findViewById(R.id.cha_ed);
        cha_fan = (ImageView) findViewById(R.id.cha_fan);
        cha_qr = (ImageView) findViewById(R.id.cha_qr);
        cha_recycle = (RecyclerView) findViewById(R.id.cha_recycle);

        cha_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cha_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = cha_ed.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(ChaActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(trim)){
                    OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.search.catalogSug&query="+trim, new AbstractUiCallBack<ChaBean>() {

                        private ChaAdapter chaAdapter;
                        private LinearLayoutManager manager;

                        @Override
                        public void success(ChaBean chaBean) {
                            manager = new LinearLayoutManager(ChaActivity.this,LinearLayoutManager.VERTICAL,false);
                            List<ChaBean.SongBean> song = chaBean.getSong();
                            chaAdapter = new ChaAdapter(ChaActivity.this,song);
                            cha_recycle.setLayoutManager(manager);
                            cha_recycle.setAdapter(chaAdapter);
                        }

                        @Override
                        public void failure(Exception e) {

                        }
                    });
                }
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
