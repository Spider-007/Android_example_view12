package com.htmitech.myEnum;

/**
 * Created by htrf-pc on 2016/7/4.
 */
public enum BottomButtonSlyteEnum {
    DRAG(1), //可拖动
    BOTTOM(0); // 底部

    int code;
    BottomButtonSlyteEnum(int code){
        this.code = code;
    }

    /**
     * 获取是可拖动的 还是底部的
     * 默认是底部
     * @param code
     * @return
     */
    public static BottomButtonSlyteEnum getBottomButtonSlyteEnum(int code){
        for(BottomButtonSlyteEnum mBottomButtonSlyteEnum : values()){
            if(code == mBottomButtonSlyteEnum.code){
                return mBottomButtonSlyteEnum;
            }
        }
        return BottomButtonSlyteEnum.BOTTOM;
    }
}
