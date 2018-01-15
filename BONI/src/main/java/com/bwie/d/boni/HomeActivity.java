package com.bwie.d.boni;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.d.boni.adapter.DrawListAdapter;
import com.bwie.d.boni.bean.EventBean;
import com.bwie.d.boni.fragment.Fragment1;
import com.bwie.d.boni.fragment.Fragment2;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private List<String> data;
    private DrawerLayout drawerLayout;
    List<Integer> image = new ArrayList<>();
    private DrawListAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment fragment;
    private List<String> list;
    private ImageView draw_image;
    private LinearLayout cehua;
    private ImageView home_cha;
    private LinearLayout linear;
    private TextView musicname;
    private TextView zname;
    private SimpleDraweeView zimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.list_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_draw);
        tabLayout = (TabLayout) findViewById(R.id.shou_tab);
        viewPager = (ViewPager) findViewById(R.id.shou_pager);
        draw_image = (ImageView) findViewById(R.id.draw_image);
        cehua =    (LinearLayout) findViewById(R.id.cehua);
        home_cha = (ImageView) findViewById(R.id.home_cha);
        linear = (LinearLayout) findViewById(R.id.home_linear);
        zname = (TextView) findViewById(R.id.zname);
        musicname = (TextView) findViewById(R.id.musicname);
        zimage = (SimpleDraweeView) findViewById(R.id.zimage);

        EventBus.getDefault().register(this);
        data = new ArrayList<>();
        data.add("功能设置");
        data.add("夜间模式");
        data.add("定时停止播放");
        data.add("退出应用");
        data.add("关于波尼音乐");
        image.add(R.mipmap.neirongguanzhu);
        image.add(R.mipmap.shequ);
        image.add(R.mipmap.liulanjilu);
        image.add(R.mipmap.faxian2);
        image.add(R.mipmap.wode2);

        adapter = new DrawListAdapter(HomeActivity.this,data,image);
        listView.setAdapter(adapter);
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (listView==1){
//
//                }
            }
        });
        list = new ArrayList<>();
        list.add("我的音乐");
        list.add("在线音乐");

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {

                if(list.get(position).equals("我的音乐")){
                    fragment = new Fragment1();
                }
                else{
                    fragment = new Fragment2();
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        tabLayout.setupWithViewPager(viewPager);


        drawerLayout.closeDrawer(cehua);
        draw_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(cehua);
            }
        });

        home_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ChaActivity.class);
                startActivity(intent);
            }
        });

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,BoActivity.class);
                intent.putExtra("flag",2);
                String zname = HomeActivity.this.zname.getText().toString().trim();
                String musicname = HomeActivity.this.musicname.getText().toString().trim();
                intent.putExtra("zname",zname);
                intent.putExtra("musicname",musicname);
                startActivity(intent);
            }
        });


    }

    @Subscribe(sticky = true)
    public void onEventMain(EventBean eventBean){
        zimage.setImageURI(eventBean.getZimage());
        zname.setText(eventBean.getZname());
        musicname.setText(eventBean.getMusicname());
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
