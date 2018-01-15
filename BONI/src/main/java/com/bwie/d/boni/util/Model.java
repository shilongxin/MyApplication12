package com.bwie.d.boni.util;

import java.io.Serializable;

/**
 * Created by d on 2018/1/7.
 */

public class Model implements Serializable {
    String text_song;
    public String getText_song() {
        return text_song;
    }
    public void setText_song(String text_song) {
        this.text_song = text_song;
    }
    public String getText_singer() {
        return text_singer;
    }
    public void setText_singer(String text_singer) {
        this.text_singer = text_singer;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    String text_singer;
    String path;

}
