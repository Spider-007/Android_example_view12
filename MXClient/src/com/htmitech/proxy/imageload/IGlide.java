package com.htmitech.proxy.imageload;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Tony
 */
public interface IGlide {
    void showImage(@NonNull ImageLoaderOptions options);

    void cleanMemory(Context context);

    IGlide with(Context context);


}
