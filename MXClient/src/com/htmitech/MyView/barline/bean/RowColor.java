package com.htmitech.MyView.barline.bean;

public class RowColor {
    private String evenRowColor;
    private float evenRowColorAlpha = 0.0f;
    private String headRowColor;
    private String oddRowColor;
    private float oddRowColorAlpha = 0.0f;
    private String sumRowColor;

    public float getOddRowColorAlpha() {
        return this.oddRowColorAlpha;
    }

    public void setOddRowColorAlpha(float f) {
        this.oddRowColorAlpha = f;
    }

    public float getEvenRowColorAlpha() {
        return this.evenRowColorAlpha;
    }

    public void setEvenRowColorAlpha(float f) {
        this.evenRowColorAlpha = f;
    }

    public String getHeadRowColor() {
        return this.headRowColor;
    }

    public void setHeadRowColor(String str) {
        this.headRowColor = str;
    }

    public String getSumRowColor() {
        return this.sumRowColor;
    }

    public void setSumRowColor(String str) {
        this.sumRowColor = str;
    }

    public String getOddRowColor() {
        return this.oddRowColor;
    }

    public void setOddRowColor(String str) {
        this.oddRowColor = str;
    }

    public String getEvenRowColor() {
        return this.evenRowColor;
    }

    public void setEvenRowColor(String str) {
        this.evenRowColor = str;
    }
}