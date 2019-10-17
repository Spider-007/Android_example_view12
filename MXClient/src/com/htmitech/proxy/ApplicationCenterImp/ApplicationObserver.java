package com.htmitech.proxy.ApplicationCenterImp;

import android.content.Context;

import com.htmitech.proxy.doman.AppInfo;

import java.util.Map;

/**
 * tony
 */
public interface ApplicationObserver {
    /**
     *
     * @param context
     * @param mAppInfo  方便寻找类名以及weburl 功能号ID
     * @param parameters   接受参数
     */
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters);

}
