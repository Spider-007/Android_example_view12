package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.htnativestartformplugin.activity.StartDetailActivity;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * 发起流程  包含通用表单
 */
public class OAStartPlugin implements ApplicationObserver {

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
        AppInfo appInfo = mAppliationCenterDao.getAppInfo(mAppInfo.getParent_app_id() + "");
        parameters.put("parentAppId",mAppInfo.getParent_app_id()+"");
        parameters.put("parentAppVersionID",mAppInfo.getmAppVersion().getApp_version_id() + ""+"");
        HTActivityUnit.switchTo((Activity) context, StartDetailActivity.class, parameters);
    }

}
