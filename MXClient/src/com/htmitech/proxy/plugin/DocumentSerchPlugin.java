package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.emportal.ui.document.DocumentSerchMainActivity;
import com.htmitech.htnativestartformplugin.activity.StartDetailActivity;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * Created by heyang on 2017-9-15.
 */
public class DocumentSerchPlugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
            HTActivityUnit.switchTo((Activity) context, DocumentSerchMainActivity.class, parameters);
    }
}
