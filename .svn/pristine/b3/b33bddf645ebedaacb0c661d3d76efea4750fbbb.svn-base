package com.htmitech.proxy;

import android.content.Context;

import com.htmitech.emportal.R;
import com.htmitech.myEnum.StylesEnum;
import com.htmitech.proxy.myenum.ApplicationAllEnum;


import java.util.HashMap;
import java.util.Map;

/**
 * 底部导航栏的颜色配置 tongy
 */
public class ButtomColorFactory {

    public Map<ApplicationAllEnum,LeftBar> buttomMap;

    public Context context;

    private static StylesEnum mColorEnum;

    private static ButtomColorFactory mButtomColorFactory;
    private ButtomColorFactory(Context context){
        this.context = context;
        setButtomListColor();
    }
    public static ButtomColorFactory getInstance(Context context){
        if(mButtomColorFactory == null){
            return mButtomColorFactory = new ButtomColorFactory(context);
        }
        return mButtomColorFactory;
    }

    public void setButtomListColor(){
        int colorValue = context.getResources().getColor(R.color.ht_hred_title);
        int colorBulue = context.getResources().getColor(R.color.ht_hred_title_duibi);
        int colorRed  = context.getResources().getColor(R.color.ht_red_dangzheng);
        int colorWite = context.getResources().getColor(R.color.white);
        StylesEnum.BLUE.color = colorBulue;
        StylesEnum.GENERAL.color = colorWite;
        StylesEnum.RED.color = colorRed;
        if(colorValue == colorBulue){
            mColorEnum = StylesEnum.BLUE ;
        } else if(colorValue == colorRed){
            mColorEnum = StylesEnum.RED ;
        }else if(colorValue == colorWite){
            mColorEnum = StylesEnum.GENERAL ;
        }
        buttomMap = new HashMap<ApplicationAllEnum,LeftBar>();
        buttomMap.put(ApplicationAllEnum.DB,new LeftBar(mColorEnum, R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
        buttomMap.put(ApplicationAllEnum.TXL,new LeftBar(mColorEnum,R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
        buttomMap.put(ApplicationAllEnum.GT,new LeftBar(mColorEnum,R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
        buttomMap.put(ApplicationAllEnum.GZQ,new LeftBar(mColorEnum,R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
        buttomMap.put(ApplicationAllEnum.ZY,new LeftBar(mColorEnum,R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
        //给插件一个默认图片
        buttomMap.put(ApplicationAllEnum.CJ,new LeftBar(mColorEnum,R.drawable.pictures_no,R.drawable.pictures_no,R.drawable.pictures_no));
//        buttomMap.put(ButtomEnum.WG,new LeftBar(mColorEnum,R.drawable.tab_new_home_selector_red,R.drawable.tab_new_home_selector,R.drawable.tab_new_home_selector));
//        buttomMap.put(ButtomEnum.FZDH,new LeftBar(mColorEnum,R.drawable.tab_new_home_selector_red,R.drawable.tab_new_home_selector,R.drawable.tab_new_home_selector));

    }
    public Map<ApplicationAllEnum,LeftBar> getButtomColor(){
        return buttomMap;
    }
}
