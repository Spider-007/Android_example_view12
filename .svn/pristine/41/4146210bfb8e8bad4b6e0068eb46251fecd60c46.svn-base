package com.htmitech.proxy;

import android.content.Context;

import com.htmitech.emportal.R;
import com.htmitech.myEnum.StylesEnum;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取侧边栏图片 纯基础应用数据
 */
public class LeftColorFactory {

    private static Context context;

    private static ApplicationAllEnum mLeftEnums;

    private static  LeftColorFactory mLeftColorFactory;

    private static StylesEnum mColorEnum;

    private Map<ApplicationAllEnum,LeftBar> leftMap ;

    private LeftColorFactory(Context context){
        this.context = context;
        setLeftBarColor();
    }



    public static LeftColorFactory getInstance(Context context){
        if(mLeftColorFactory == null){
            mLeftColorFactory = new LeftColorFactory(context);
        }
        return mLeftColorFactory;
    }

    public void setLeftBarColor(){
//        leftList = new ArrayList<LeftBar>();
        leftMap = new HashMap<ApplicationAllEnum,LeftBar>();
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
        leftMap.put(ApplicationAllEnum.XXTX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.MMSZ, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.YYZX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.BBXX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.FGSZ, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
//        leftMap.put(ApplicationAllEnum.ZLK, new LeftBar(mColorEnum, R.drawable.leftbar_data_red, R.drawable.leftbar_data_blue, R.drawable.leftbar_data_blue));
        leftMap.put(ApplicationAllEnum.CYYJ, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.WDSC, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.PTZN, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));

        leftMap.put(ApplicationAllEnum.ZTDX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));//临时增加字体大小设置

        leftMap.put(ApplicationAllEnum.ZYYYZX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.SYS, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.GZFX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.FQTP, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.CJRW, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.ZZHD, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.FBXX, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.CJQL, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));
        leftMap.put(ApplicationAllEnum.HT, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));

        leftMap.put(ApplicationAllEnum.MHQH, new LeftBar(mColorEnum, R.drawable.pictures_no, R.drawable.pictures_no, R.drawable.pictures_no));

    }


    public Map<ApplicationAllEnum,LeftBar> getLeftColorMap(){
        return leftMap;
    }



}
