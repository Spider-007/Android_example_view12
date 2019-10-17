package com.htmitech.proxy.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by htrf-pc on 2017/7/25.
 */
public class GlideUtil {

    public static void loadGild(Context context, String url, int resourceId, int error, ImageView view, BitmapTransformation... transformations) {
//        JITSecurityManager mJITSecurityManager = HtmitechApplication.instance().getjITSecurityManager();
//        url = mJITSecurityManager.createNewUrl(url);
        Glide.with(context).load(url).placeholder(resourceId).error(error).transform(transformations).into(view);
    }

    public static void loadGild(Context context, String url, int resourceId, int error, ImageView view) {
//        JITSecurityManager mJITSecurityManager = HtmitechApplication.instance().getjITSecurityManager();
//        url = mJITSecurityManager.createNewUrl(url);
        Glide.with(context).load(url).placeholder(resourceId).error(error).into(view);
    }

    public static void loadGild(Context context, String url, int resourceId, int error, DiskCacheStrategy strategy, ImageView view) {
//        JITSecurityManager mJITSecurityManager = HtmitechApplication.instance().getjITSecurityManager();
//        url = mJITSecurityManager.createNewUrl(url);
        Glide.with(context).load(url).placeholder(resourceId).error(error).diskCacheStrategy(strategy).into(view);
    }

    public static void loadGildGif(Context context, String url, int resourceId, int error, ImageView view) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resourceId).error(error).into(view);
    }
}
