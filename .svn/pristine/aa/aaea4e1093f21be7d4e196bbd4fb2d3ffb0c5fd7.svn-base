package com.htmitech.commonx.base.bitmap.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;

/**
 * 图片读取或下载时的接口回调
 * 
 * @param <T>
 */
public abstract class BitmapLoadCallBack<T extends View> {

    /**
     * 图片准备读取时的回调
     *
     * @param container 当前图片对象
     * @param uri   下载地址
     * @param config    BitmapDisplayConfig配置对象
     */
    public void onPreLoad(T container, String uri, BitmapDisplayConfig config) {
    }

    /**
     * 图片开始读取时的回调
     *
     * @param container 当前图片对象
     * @param uri   下载地址
     * @param config    BitmapDisplayConfig配置对象
     */
    public void onLoadStarted(T container, String uri, BitmapDisplayConfig config) {
    }

    /**
     * 图片正在读取时的回调
     *
     * @param container 当前图片对象
     * @param uri   下载地址
     * @param config    BitmapDisplayConfig配置对象
     * @param total 数据总大小
     * @param current   当前已下载大小
     */
    public void onLoading(T container, String uri, BitmapDisplayConfig config, long total, long current) {
    }

    /**
     * 图片读取完毕的回调
     *
     * @param container 当前图片对象
     * @param uri   下载地址
     * @param config    BitmapDisplayConfig配置对象
     * @param from  图片读取来源：MEMORY_CACHE, DISK_CACHE, URI
     */
    public abstract void onLoadCompleted(T container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from);

    /**
     * 图片读取失败的回调
     *
     * @param container 当前图片对象
     * @param uri   下载地址
     * @param drawable  失败后展示的图片
     */
    public abstract void onLoadFailed(T container, String uri, Drawable drawable);

    private BitmapSetter<T> bitmapSetter;

    /**
     * 设置图片对象Setter
     * 
     * @param bitmapSetter
     */
    public void setBitmapSetter(BitmapSetter<T> bitmapSetter) {
        this.bitmapSetter = bitmapSetter;
    }

    public void setBitmap(T container, Bitmap bitmap) {
        if (bitmapSetter != null) {
            bitmapSetter.setBitmap(container, bitmap);
        } else if (container instanceof ImageView) {
            ((ImageView) container).setImageBitmap(bitmap);
        } else {
            container.setBackgroundDrawable(new BitmapDrawable(container.getResources(), bitmap));
        }
    }

    public void setDrawable(T container, Drawable drawable) {
        if (bitmapSetter != null) {
            bitmapSetter.setDrawable(container, drawable);
        } else if (container instanceof ImageView) {
            ((ImageView) container).setImageDrawable(drawable);
        } else {
            container.setBackgroundDrawable(drawable);
        }
    }

    public Drawable getDrawable(T container) {
        if (bitmapSetter != null) {
            return bitmapSetter.getDrawable(container);
        } else if (container instanceof ImageView) {
            return ((ImageView) container).getDrawable();
        } else {
            return container.getBackground();
        }
    }
}
