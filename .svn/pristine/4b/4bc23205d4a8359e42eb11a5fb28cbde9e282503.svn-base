package com.htmitech.emportal.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;

import com.htmitech.emportal.R;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.util.SysConvertUtil;


/**
 * Created by heyang on 2017-12-28.
 */
public class DrawableUtils {

    public static LayerDrawable getLayerDrawable(Context context, int color) {
        //创建Drawable
        GradientDrawable gradientDrawable = getGraDra(context, color, 0);
//        GradientDrawable grayDrawable =getGraDra(context, color, 0);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gradientDrawable});
        //设置padding
        layerDrawable.setLayerInset(0, DensityUtil.dip2px(context, -1), DensityUtil.dip2px(context, -1), 0, DensityUtil.dip2px(context, -1));
        //设置drawable为背景
//        ViewCompat.setBackground(acTvInfo,layerDrawable);
        return layerDrawable;
    }

    public static GradientDrawable getGraDra(Context context, int color, float dp) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(SysConvertUtil.formattingH(color));
        drawable.setStroke(DensityUtil.dip2px(context, 1), Color.parseColor("#B4B4B4"));
        drawable.setCornerRadius(DensityUtil.dip2px(context, dp));
        return drawable;
    }
}
