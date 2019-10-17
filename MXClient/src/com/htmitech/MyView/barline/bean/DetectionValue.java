package com.htmitech.MyView.barline.bean;

public class DetectionValue {
    public String code;
    public String color;
    public String name;
    public double value;

    public DetectionValue(String str, double d, String str2, String str3) {
        this.color = str;
        this.value = d;
        this.name = str2;
        this.code = str3;
    }
}