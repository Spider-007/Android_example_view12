package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.emportal.ui.detail.H5StartDetailActivity;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * H5发起
 */
public class H5StartPlugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        HTActivityUnit.switchTo((Activity) context, H5StartDetailActivity.class, parameters);
//        ZTActivityUnit.switchTo((Activity) context, WebPluginActivity.class, parameters);
    }
}
