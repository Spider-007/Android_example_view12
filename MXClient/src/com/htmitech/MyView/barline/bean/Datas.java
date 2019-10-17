package com.htmitech.MyView.barline.bean;

public class Datas {
    public String key;
    public String value;

    public Datas(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }
}