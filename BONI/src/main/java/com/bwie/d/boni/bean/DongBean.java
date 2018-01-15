package com.bwie.d.boni.bean;

/**
 * Created by d on 2018/1/10.
 */

public class DongBean {

    private boolean type;

    public DongBean(boolean type) {
        this.type = type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DongBean{" +
                "type=" + type +
                '}';
    }
}
