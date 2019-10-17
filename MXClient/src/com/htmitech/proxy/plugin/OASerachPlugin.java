package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.htmitech.emportal.ui.plugin.oamodel.OAModelFragmentActivity;
import com.htmitech.htcommonformplugin.activity.GeneralFormChildActivity;
import com.htmitech.htexceptionmanage.activity.ManageExceptionChildActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormChildActivity;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.util.HTActivityUnit;

import java.util.Map;

/**
 * 筛选插件  包含通用表单
 */

public class OASerachPlugin implements ApplicationObserver {

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {

        if (mAppInfo.getPlugin_code().equals("com_workflow_plugin_selector")) {
            //如果是工作流构建的原声插件的话，那么将工作流构建的参数也要写入进来
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
            AppInfo appInfo = mAppliationCenterDao.getAppInfo(mAppInfo.getParent_app_id() + "");
            if (appInfo != null) {

                if (appInfo.getmAppVersion() != null && appInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                    for (AppVersionConfig mAppVersionConfig : appInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                        if(parameters.get(mAppVersionConfig.getConfig_code())  == null || TextUtils.isEmpty(parameters.get(mAppVersionConfig.getConfig_code()).toString())){
                            parameters.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                        }

                    }
                }
                parameters.put("app_id", appInfo.getApp_id() + "");//替换原来的app_id
                ApplicationAllEnum.DB.mAppInfo = appInfo;
                ApplicationAllEnum.DB.appId = appInfo.getApp_id() + "";
            }
//            HTActivityUnit.switchTo((Activity) context, OAModelFragmentActivity.class, parameters);
            HTActivityUnit.switchTo((Activity) context, WorkFlowFormChildActivity.class, parameters);
        } else if (mAppInfo.getPlugin_code().equals("com_commonform_plugin_selector")) {
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
            AppInfo appInfo = mAppliationCenterDao.getAppInfo(mAppInfo.getParent_app_id() + "");
            if (appInfo != null) {
                if (appInfo.getmAppVersion() != null && appInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                    for (AppVersionConfig mAppVersionConfig : appInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                        if(parameters.get(mAppVersionConfig.getConfig_code())  == null || TextUtils.isEmpty(parameters.get(mAppVersionConfig.getConfig_code()).toString())){
                            parameters.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                        }
                    }
                }
                parameters.put("appName", mAppInfo.getApp_name());
            }
            HTActivityUnit.switchTo((Activity) context, GeneralFormChildActivity.class, parameters);
        }else if (mAppInfo.getPlugin_code().equals("com_alert_plugin_selector")) {
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
            AppInfo appInfo = mAppliationCenterDao.getAppInfo(mAppInfo.getParent_app_id() + "");
            if (appInfo != null) {
                if (appInfo.getmAppVersion() != null && appInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                    for (AppVersionConfig mAppVersionConfig : appInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                        if(parameters.get(mAppVersionConfig.getConfig_code())  == null || TextUtils.isEmpty(parameters.get(mAppVersionConfig.getConfig_code()).toString())){
                            parameters.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                        }
                    }
                }
                parameters.put("appName", mAppInfo.getApp_name());
            }
            HTActivityUnit.switchTo((Activity) context, ManageExceptionChildActivity.class, parameters);
        }  else {
//            HTActivityUnit.switchTo((Activity) context, OAModelFragmentActivity.class, parameters);
            HTActivityUnit.switchTo((Activity) context, WorkFlowFormChildActivity.class, parameters);
        }
    }

}
