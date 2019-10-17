package com.htmitech.proxy.doman;

/**
 * Created by htrf-pc on 2017/6/13.
 */
public class CenterTitle {
    public boolean show;
    public String text;
    public String subText;
    public int fontSize;
    public int subFontSize;
    public String textColor;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getSubFontSize() {
        return subFontSize;
    }

    public void setSubFontSize(int subFontSize) {
        this.subFontSize = subFontSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
