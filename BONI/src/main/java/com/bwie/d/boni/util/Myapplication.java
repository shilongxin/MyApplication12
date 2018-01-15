package com.bwie.d.boni.util;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by d on 2018/1/8.
 */

public class Myapplication extends Application {
    public static boolean Isplay = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
