package com.htmitech.commonx.base.bitmap.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;

import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;

import java.lang.reflect.Method;

/**
 * 默认图片读取回调
 * @param <T>
 */
public class DefaultBitmapLoadCallBack<T extends View> extends BitmapLoadCallBack<T> {
    
    @Override
    public void onLoadCompleted(T container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
        this.setBitmap(container, bitmap);
        Animation animation = config.getAnimation();
        if (animation != null) {
            animationDisplay(container, animation);
        }
    }

    @Override
    public void onLoadFailed(T container, String uri, Drawable drawable) {
        this.setDrawable(container, drawable);
    }

    private void animationDisplay(T container, Animation animation) {
        try {
            Method cloneMethod = Animation.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
            container.startAnimation((Animation) cloneMethod.invoke(animation));
        } catch (Throwable e) {
            container.startAnimation(animation);
        }
    }
}
