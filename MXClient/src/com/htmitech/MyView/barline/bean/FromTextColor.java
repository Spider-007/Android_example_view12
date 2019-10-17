package com.htmitech.MyView.barline.bean;

public class FromTextColor {
    public String affect = "";
    public int postion;
    public TextStyle textStyle;
    public int width;

    public FromTextColor(String str, int i, TextStyle textStyle, int i2) {
        this.affect = str;
        this.postion = i;
        this.textStyle = new TextStyle(textStyle.getColor(), textStyle.getFontWeight(), textStyle.getFontSize());
        this.width = i2;
    }

    public TextStyle getTextStyle() {
        return this.textStyle;
    }

    public void setTextStyle(TextStyle textStyle) {
        this.textStyle = new TextStyle(textStyle.getColor(), textStyle.getFontWeight(), textStyle.getFontSize());
    }
}