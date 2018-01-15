package com.bwie.d.boni.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by d on 2017/12/29.
 */

public class MyAppcliation extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
