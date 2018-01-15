package com.bwie.d.boni.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.d.boni.R;
import com.bwie.d.boni.bean.DongBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by d on 2018/1/7.
 */

public class Fragment_one extends Fragment {
    private ImageView disc,needle,playingPlay;
    private ObjectAnimator discAnimation,needleAnimation;
    private boolean isPlaying = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_one, container, false);
        disc = (ImageView) view.findViewById(R.id.disc);
        needle = (ImageView) view.findViewById(R.id.needle);
        playingPlay = (ImageView) view.findViewById(R.id.playing_play);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAnimations();
        playingPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    playing();
                }else {
                    if (needleAnimation != null) {
                        Toast.makeText(getActivity(),"shang",Toast.LENGTH_SHORT).show();
                        needleAnimation.reverse();
                        needleAnimation.end();
                    }
                    if (discAnimation != null && discAnimation.isRunning()) {
                        Toast.makeText(getActivity(),"xia",Toast.LENGTH_SHORT).show();
                        discAnimation.cancel();
                        float valueAvatar = (float) discAnimation.getAnimatedValue();
                        discAnimation.setFloatValues(valueAvatar, 360f + valueAvatar);
                    }
                    playingPlay.setImageResource(R.drawable.ic_status_bar_play_dark);
                    isPlaying = true;
                }
            }
        });
    }

    private void setAnimations() {
        discAnimation = ObjectAnimator.ofFloat(disc, "rotation", 0, 360);
        discAnimation.setDuration(20000);
        discAnimation.setInterpolator(new LinearInterpolator());
        discAnimation.setRepeatCount(ValueAnimator.INFINITE);

        needleAnimation = ObjectAnimator.ofFloat(needle, "rotation", 0, 25);
        needle.setPivotX(0);
        needle.setPivotY(0);
        needleAnimation.setDuration(800);
        needleAnimation.setInterpolator(new LinearInterpolator());
    }



    private void playing(){
        needleAnimation.start();
        discAnimation.start();
        playingPlay.setImageResource(R.drawable.ic_status_bar_pause_dark_pressed);
        isPlaying = false;
    }
    @Subscribe(sticky = true)
    public void Dong(DongBean dongBean){
        needleAnimation.start();
        discAnimation.start();
        isPlaying = false;
    }
}
