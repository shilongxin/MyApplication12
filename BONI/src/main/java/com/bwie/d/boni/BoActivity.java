package com.bwie.d.boni;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bwie.d.boni.bean.DongBean;
import com.bwie.d.boni.bean.Lrc;
import com.bwie.d.boni.bean.Musicbean;
import com.bwie.d.boni.bean.Timebean;
import com.bwie.d.boni.fragment.Fragment_one;
import com.bwie.d.boni.fragment.Fragment_two;
import com.bwie.d.boni.jiekou.MyAdaptercallback;
import com.bwie.d.boni.okhttp.OkhttpUtils;
import com.bwie.d.boni.service.MyService;
import com.bwie.d.boni.util.Model;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoActivity extends AppCompatActivity {

    @BindView(R.id.finsh)
    ImageView finsh;
    @BindView(R.id.bo_text1)
    TextView boText1;
    @BindView(R.id.bo_text2)
    TextView boText2;
    @BindView(R.id.bo_viewpager)
    ViewPager viewpager;
    @BindView(R.id.text_start)
    TextView textStart;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.text_stop)
    TextView textStop;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.activity_bo)
    LinearLayout activityBo;
    @BindView(R.id.radio_playm)
    CheckBox radioPlaym;
    private Intent service;

    private ServiceConnection conn;
    private MyService.MyBinder binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 3);
        if(flag==2){
            String zname = intent.getStringExtra("zname");
            String musicname = intent.getStringExtra("musicname");
            boText1.setText(zname);
            boText2.setText(musicname);
        }
        else if (flag == 0) {
            String songname = intent.getStringExtra("songname");
            String songautor = intent.getStringExtra("songautor");
            boText1.setText(songname);
            boText2.setText(songautor);
            String songid = intent.getStringExtra("songid");
            Log.i("-------", songid);
            radioPlaym.setChecked(true);
            EventBus.getDefault().postSticky(new Lrc(songid));
            OkhttpUtils.getInstance().getSong(songid, new MyAdaptercallback() {

                @Override
                public void success(String json) {
                    Gson gson = new Gson();
                    final Musicbean musicbean = gson.fromJson(json, Musicbean.class);
                    final String path = musicbean.getBitrate().getShow_link();

                    service = new Intent(BoActivity.this, MyService.class);
                    //连接服务的对象
                    conn = new ServiceConnection() {

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            // activity与服务断开连接时调用的方法

                        }

                        //activity与服务连接上调用的方法
                        //IBinder service中间人对象....MyService中的onBind方法返回的对象
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            // 链接后返回的中间人对象
                            binder = (MyService.MyBinder) service;
                            binder.playInBinder(path);
                            EventBus.getDefault().postSticky(new DongBean(true));

				/*//调用到了service里面的方法
                binder.jieQianBinder();*/
                        }
                    };

                    //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
                    startService(service);

                    BoActivity.this.bindService(service, conn, BIND_AUTO_CREATE);
                    Log.i("---link---", musicbean.getBitrate().getShow_link());


                }
            });

        } else if (flag == 1) {
            Model model = (Model) intent.getSerializableExtra("model");
            final String path = model.getPath();
            boText1.setText(model.getText_song());
            boText2.setText(model.getText_singer());
            radioPlaym.setChecked(true);
            service = new Intent(BoActivity.this, MyService.class);
            //连接服务的对象
            conn = new ServiceConnection() {

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    // activity与服务断开连接时调用的方法

                }

                //activity与服务连接上调用的方法
                //IBinder service中间人对象....MyService中的onBind方法返回的对象
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    // 链接后返回的中间人对象
                    binder = (MyService.MyBinder) service;
                    binder.playInBinder(path);

				/*//调用到了service里面的方法
                binder.jieQianBinder();*/
                }
            };

            //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
            startService(service);

            BoActivity.this.bindService(service, conn, BIND_AUTO_CREATE);

        }


        final List<Fragment> list = new ArrayList<>();
        list.add(new Fragment_one());
        list.add(new Fragment_two());

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //===========================================================

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(seekBar.getProgress());
                Message message = new Message();
                message.obj = seekBar.getProgress();
                message.what = 1;
                MyService.handler.sendMessage(message);

            }
        });


    }


    @OnClick({R.id.finsh, R.id.image_shang, R.id.radio_playm, R.id.image_xia})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                finish();
                break;
            case R.id.image_shang:
                break;
            case R.id.radio_playm:
                binder.stopInBinder();
                break;
            case R.id.image_xia:
                break;
        }
    }


    @Subscribe(sticky = false)
    public void GetEvent(final Timebean timebean) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textStart.setText(dateFormat.format(new Date(timebean.currentPosition)));
                textStop.setText(dateFormat.format(new Date(timebean.duration)));
                seekbar.setProgress(timebean.currentPosition * 100 / timebean.duration);

            }
        });


    }

    @Override
    protected void onDestroy() {
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


}
