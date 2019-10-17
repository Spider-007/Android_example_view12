package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.emportal.ui.detail.OpinionInputActivity;
import com.htmitech.emportal.ui.detail.StartDetailActivity;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * Created by htrf-pc on 2017/2/22.
 */
public class OptionSelectPlugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        parameters.put("parent_app_id",mAppInfo.getParent_app_id());
        parameters.put("extra_datas", "ClientTabActivity");
        HTActivityUnit.switchTo((Activity) context, OpinionInputActivity.class, parameters);
    }
}
