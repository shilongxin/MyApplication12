package com.bwie.d.boni.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.d.boni.R;
import com.bwie.d.boni.bean.Lrc;
import com.bwie.d.boni.bean.Lrcbean;
import com.bwie.d.boni.jiekou.MyAdaptercallback;
import com.bwie.d.boni.okhttp.OkhttpUtils;
import com.bwie.d.boni.service.MyService;
import com.bwie.d.boni.util.LrcView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by d on 2018/1/7.
 */

public class Fragment_two extends Fragment{
    private static LrcView lrcView;
    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_two, null, false);
        lrcView = (LrcView) view.findViewById(R.id.lrc_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventBus.getDefault().register(this);


    }

    @Subscribe(sticky = true)
    public void getLrc(Lrc lrc){
        OkhttpUtils.getInstance().getLrc(lrc.getSongid(), new MyAdaptercallback() {
            @Override
            public void success(String json) {
                Gson gson = new Gson();
                Lrcbean lrcbean = gson.fromJson(json, Lrcbean.class);
                String lrcContent = lrcbean.getLrcContent();
                lrcView.setLrc(lrcContent);
                lrcView.setPlayer(MyService.mediaPlayer);
                lrcView.init();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
