package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.addressbook.InitWebView;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.activity.WebPluginActivity;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * Created by heyang on 2016-11-24.
 */
public class WebPlugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        HTActivityUnit.switchTo((Activity) context, InitWebView.class, parameters);
    }
}
