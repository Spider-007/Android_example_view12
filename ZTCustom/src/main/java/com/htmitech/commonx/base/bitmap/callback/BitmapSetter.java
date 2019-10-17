package com.htmitech.commonx.base.bitmap.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Setter
 * @param <T>
 */
public interface BitmapSetter<T extends View> {
    void setBitmap(T container, Bitmap bitmap);

    void setDrawable(T container, Drawable drawable);

    Drawable getDrawable(T container);
}
