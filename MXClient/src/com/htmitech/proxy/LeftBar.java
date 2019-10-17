package com.htmitech.proxy;

import com.htmitech.myEnum.StylesEnum;

/**
 * Created by htrf-pc on 2016/11/15.
 */
public class LeftBar {

    StylesEnum mColorEnum;
    public int red;
    public int blue;
    public int general; //企业通用版

    public LeftBar(StylesEnum mColorEnum, int red, int blue, int general) {
        this.mColorEnum = mColorEnum;
        this.red = red;
        this.blue = blue;
        this.general = general;
    }

    public int getColor() {
        if(mColorEnum != null){
            switch (mColorEnum) {
                case RED:
                    return red;
                case BLUE:
                    return blue;
                case GENERAL:
                    return general;
                default:
                    return red;
            }
        }else{
            return red;
        }

    }
}
