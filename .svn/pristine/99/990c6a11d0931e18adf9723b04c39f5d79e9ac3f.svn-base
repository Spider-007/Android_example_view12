package com.htmitech.myEnum;

/**
 *  对应三个版本 tony
 */
public enum StylesEnum {
    GENERAL(1),  //企业通用版本

    RED(1),  //党政红色版


    BLUE(1); //企业蓝色版
    public int color;
    StylesEnum(int color){
        this.color = color;
    }

    public static int getColor(int colorStyle){
        if(colorStyle == 1){
            return StylesEnum.GENERAL.color;
        }else if(colorStyle == 2){
            return StylesEnum.BLUE.color;
        }else if(colorStyle == 3){
            return StylesEnum.RED.color;
        }else{
            return StylesEnum.BLUE.color;
        }
    }

}
