package com.htmitech.proxy.imageload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.emportal.R;

/**
 * tony
 */
public class GlideImageLoader implements IGlide {
    private Context context;

    private static GlideImageLoader INSTACES ;

    static {
        INSTACES = new GlideImageLoader();
    }

    public static GlideImageLoader get(){
        return INSTACES;
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if (options.getBitmapTransformations() == null) {
            Glide.with(context).load(TextUtils.isEmpty(options.getUrl()) ? options.getResource() : options.getUrl()).placeholder(options.getResourceId()).error(options.getErrorResourceId()).diskCacheStrategy(options.getmDiskCacheStrategy()).into(options.getViewContainer());
        } else {
            Glide.with(context).load(TextUtils.isEmpty(options.getUrl()) ? options.getResource() : options.getUrl()).placeholder(options.getResourceId()).error(options.getErrorResourceId()).diskCacheStrategy(options.getmDiskCacheStrategy()).transform(options.getBitmapTransformations()).into(options.getViewContainer());
        }
    }

    @Override
    public void cleanMemory(Context context) {

    }

    @Override
    public  GlideImageLoader with(Context context) {
        this.context = context;
        return this;
    }
}
