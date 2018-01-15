package com.bwie.d.boni.bean;

/**
 * Created by d on 2018/1/7.
 */

public class EventBean {

    public String zimage;
    public String zname;
    public String musicname;

    public EventBean(String zimage, String zname, String musicname) {
        this.zimage = zimage;
        this.zname = zname;
        this.musicname = musicname;
    }

    public String getZimage() {
        return zimage;
    }

    public void setZimage(String zimage) {
        this.zimage = zimage;
    }

    public String getZname() {
        return zname;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    @Override
    public String toString() {
        return "EventBean{" +
                "zimage='" + zimage + '\'' +
                ", zname='" + zname + '\'' +
                ", musicname='" + musicname + '\'' +
                '}';
    }
}
