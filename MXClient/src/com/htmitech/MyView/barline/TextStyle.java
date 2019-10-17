package com.htmitech.MyView.barline;

/**
 * Created by htrf-pc on 2016/12/30.
 */
public class TextStyle {
    private String color = "";
    private String fontWeight = "";
    private int fontSize = 0;
    private String align = "";
    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }



    public TextStyle(){

    }
    public TextStyle(String color, String fontWeight, int fontSize){
        this.color = color;
        this.fontWeight = fontWeight;
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public int getFontSize() {
        if(fontSize == 0){
            fontSize = 18;
        }
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
