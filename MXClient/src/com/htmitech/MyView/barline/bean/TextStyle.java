package com.htmitech.MyView.barline.bean;

public class TextStyle {
    private String align = "";
    private String color = "";
    private int fontSize = 0;
    private String fontWeight = "";

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String str) {
        this.align = str;
    }

    public TextStyle(String str, String str2, int i) {
        this.color = str;
        this.fontWeight = str2;
        this.fontSize = i;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public String getFontWeight() {
        return this.fontWeight;
    }

    public void setFontWeight(String str) {
        this.fontWeight = str;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int i) {
        this.fontSize = i;
    }
}