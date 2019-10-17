package com.htmitech.proxy.plugin;

import android.content.Context;
import android.content.Intent;

import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.api.bean.MXAppInfo;
import com.minxing.kit.mail.MXMail;
import com.minxing.kit.ui.appcenter.AppCenterManager;

import java.util.Map;

/**
 * 邮箱插件
 */
public class EmailPlugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
//        MXUIEngine.getInstance().getAppCenterManager().launchAppById(context, parameters.get("com_email_mobileconfig_mxappid").toString(), null, false, false, new AppCenterManager.OnAPPLaunchListener() {
//            @Override
//            public boolean handleUpgrade() {
//                return false;
//            }
//
//            @Override
//            public boolean handleInstall() {
//                return false;
//            }
//
//            @Override
//            public void handleIntent(Intent intent) {
//
//            }
//
//            @Override
//
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onStartFail() {
//
//            }
//        });

        MXAppInfo mxAppInfo = new MXAppInfo();
        mxAppInfo.setAppID(parameters.get("com_email_mobileconfig_mxappid").toString());
        if(parameters.get("com_email_mobileconfig_mxAvatarUrl") != null){
            mxAppInfo.setAvatarUrl(parameters.get("com_email_mobileconfig_mxAvatarUrl").toString());
        }
        MXMail.getInstance().loadApp(context,mxAppInfo);
//        MXMail.getInstance().launchMXMail(context);
    }
}
