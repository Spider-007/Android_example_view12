package com.htmitech.proxy.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.entity.KeyvVlue;
import com.htmitech.emportal.entity.LogfunactionAppInfo;
import com.htmitech.emportal.entity.Logfunction;
import com.htmitech.emportal.entity.OAConText;

import cn.feng.skin.manager.util.PreferencesUtils;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.ApplicationAllEnum;


import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import org.json.JSONObject;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Tony 2017/1/10.
 */
public class NetWorkManager implements INetWorkManager {
    private String app_id;

    @Override
    public void logFunactionStart(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode) {
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.LOG_FUNCTION_EMPM_START;
//        LogFunactionStartInfo mLogFunactionStartInfo = new LogFunactionStartInfo();
//        mLogFunactionStartInfo.context = OAConText.getInstance(HtmitechApplication
//                .instance());
        Logfunction logfunction = new Logfunction();
        if (funactionCode.contains("!")) {
            funactionCode = funactionCode.split("!")[0];
        }
        logfunction.userId = Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID);
        logfunction.portalId = BookInit.getInstance().getPortalId();
        logfunction.functionCode = funactionCode;
//        mLogFunactionStartInfo.logfunction = logfunction;

        if (requestName.contains("function")) {//如果包含function 表示代办中的
            logfunction.appId = ApplicationAllEnum.DB.appId;
            if (ApplicationAllEnum.DB.mAppInfo == null) {
                logfunction.appVersionId = "-1";
            } else {
                logfunction.appVersionId = ApplicationAllEnum.DB.mAppInfo.getCurrent_version() + "";
            }
        } else if (requestName.contains("commonform")) {//如果包含commonform 表示通用表单中的
            logfunction.appId = ApplicationAllEnum.TYBD.appId;
            if (ApplicationAllEnum.TYBD.mAppInfo == null) {
                logfunction.appVersionId = "-1";
            } else {
                logfunction.appVersionId = ApplicationAllEnum.TYBD.mAppInfo.getCurrent_version() + "";
            }

        } else if (requestName.contains("application")) { //如果是应用中心的
            logfunction.appId = ApplicationAllEnum.YYZX.appId;
            logfunction.appVersionId = ApplicationAllEnum.YYZX.mAppInfo.getCurrent_version() + "";
        } else if (requestName.contains("login")) { //登录
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                logfunction.appVersionId = pi.versionCode + "";
            } catch (PackageManager.NameNotFoundException e) {
                logfunction.appVersionId = "";
                e.printStackTrace();
            }
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
            try {
                AppInfo appInfo = mAppliationCenterDao.accordCodeCheckAppId(ApplicationAllEnum.DL.compCode);
                logfunction.appId = appInfo.getApp_id() + "";
            } catch (Exception e) {
                logfunction.appId = "";
            }
            LogManagerEnum.getLogManagerEnum(LogManagerEnum.APP_PAUSE_TOBACK.getFunctionCode()).currentTimeMillis = System.currentTimeMillis();
        } else if (requestName.contains("lead_log")) {
            logfunction.appId = app_id;
            logfunction.appVersionId = "";
        } else if (requestName.contains("nativeStart")) {
            logfunction.appId = app_id;
            logfunction.appVersionId = "";
        }
        LogfunactionAppInfo mLogfunactionAppInfo = new LogfunactionAppInfo();
        mLogfunactionAppInfo.app_id = logfunction.appId;
        mLogfunactionAppInfo.app_version_id = logfunction.appVersionId;
        mLogfunactionAppInfo.portal_id = logfunction.portalId;
//        try {
//            String pkName = context.getPackageName();
//            logfunction.appInfo = "Android_"+context.getPackageManager().getPackageInfo(pkName, 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        KeyvVlue mKeyvVlue = new KeyvVlue();
        mKeyvVlue.app_id =  null;
        mKeyvVlue.app_version_id = null;
        mKeyvVlue.portal_id = null;
        mKeyvVlue.key = "login_name";
        mKeyvVlue.value = OAConText.getInstance(context).login_name;
        logfunction.keyvalue = com.alibaba.fastjson.JSONObject.toJSONString(mKeyvVlue);
        LogManagerEnum.getLogManagerEnum(funactionCode).currentTimeMillis = System.currentTimeMillis();
        AnsynHttpRequest.requestByPost(context, logfunction, url, CHTTP.POST_LOG, mObserverCallBackType, requestName, funactionCode);
    }

    @Override
    public void logFunactionStart(Context context, ObserverCallBackType mObserverCallBackType, LogManagerEnum mLogManagerEnum) {
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.LOG_FUNCTION_EMPM_START;
//        LogFunactionStartInfo mLogFunactionStartInfo = new LogFunactionStartInfo();
//        mLogFunactionStartInfo.context = OAConText.getInstance(HtmitechApplication
//                .instance());
        Logfunction logfunction = new Logfunction();
        LogfunactionAppInfo mLogfunactionAppInfo = new LogfunactionAppInfo();

        String funactionCode = "";
        if (mLogManagerEnum.getFunctionCode().contains("!")) {
            funactionCode = mLogManagerEnum.getFunctionCode().split("!")[0];
        } else {
            funactionCode = mLogManagerEnum.getFunctionCode();
        }
        logfunction.functionCode = funactionCode;
        logfunction.portalId = BookInit.getInstance().getPortalId();
//        mLogFunactionStartInfo.logfunction = logfunction;
        logfunction.userId = Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID);

        switch (mLogManagerEnum) {
            case APP_MOBILE_LOGIN:

                PackageManager pm = context.getPackageManager();
                try {
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                    logfunction.appVersionId = pi.versionCode + "";
                } catch (PackageManager.NameNotFoundException e) {
                    logfunction.appVersionId = "";
                    e.printStackTrace();
                }
                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
                try {
                    AppInfo appInfo = mAppliationCenterDao.accordCodeCheckAppId(ApplicationAllEnum.DL.compCode);
                    logfunction.appId = appInfo.getApp_id() + "";
                } catch (Exception e) {
                    logfunction.appId = "";
                }
                break;
            default:

                logfunction.appId = mLogManagerEnum.app_id;
                logfunction.appVersionId = mLogManagerEnum.appVersionId;
                break;
        }
        mLogfunactionAppInfo.app_id = logfunction.appId;
        mLogfunactionAppInfo.app_version_id = logfunction.appVersionId;
        mLogfunactionAppInfo.portal_id = logfunction.portalId;
//        try {
//            String pkName = context.getPackageName();
//            logfunction.appInfo = "Android_"+context.getPackageManager().getPackageInfo(pkName, 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        KeyvVlue mKeyvVlue = new KeyvVlue();
        mKeyvVlue.app_id =  mLogfunactionAppInfo.app_id;
        mKeyvVlue.app_version_id = logfunction.appVersionId;
        mKeyvVlue.portal_id = logfunction.portalId;
        logfunction.keyvalue = com.alibaba.fastjson.JSONObject.toJSONString(mKeyvVlue);


        LogManagerEnum.getLogManagerEnum(mLogManagerEnum.getFunctionCode()).currentTimeMillis = System.currentTimeMillis();
        AnsynHttpRequest.requestByPost(context, logfunction, url, CHTTP.POST_LOG, mObserverCallBackType, mLogManagerEnum.getFunctionCode(), mLogManagerEnum.getFunctionCode());

    }


    @Override
    public void logFunactionFinsh(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode, String resultInfo, State resultStatus) {
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.LOG_FUNCTION_EMPM_FINISH;
//        LogFunactionStartInfo mLogFunactionStartInfo = new LogFunactionStartInfo();
//        mLogFunactionStartInfo.context = OAConText.getInstance(HtmitechApplication
//                .instance());

        Logfunction logfunction = new Logfunction();
        if(TextUtils.isEmpty(LogManagerEnum.getLogManagerEnum(funactionCode).functionId)){
            LogManagerEnum.getLogManagerEnum(funactionCode).functionId = "0";
        }
        logfunction.functionLogId = Long.parseLong(LogManagerEnum.getLogManagerEnum(funactionCode).functionId);
        logfunction.resultInfo = resultInfo;
//        mLogFunactionStartInfo.logfunction = logfunction;
        if (funactionCode.contains("!")) {
            funactionCode = funactionCode.split("!")[0];
        }
        logfunction.functionCode = funactionCode;
        long upMillis = LogManagerEnum.getLogManagerEnum(funactionCode).currentTimeMillis;
        long currentMillis = System.currentTimeMillis();
        logfunction.consumeMillis = currentMillis - upMillis;
        logfunction.resultStatus = resultStatus.getStateValue();

        LogManagerEnum.getLogManagerEnum(funactionCode).currentTimeMillis = 0l;
//        LogManagerEnum.getLogManagerEnum(funactionCode).functionId = "0";
        AnsynHttpRequest.requestByPost(context, logfunction, url, CHTTP.POST_LOG, mObserverCallBackType, requestName, funactionCode);
    }

    @Override
    public void logFunactionOnce(Context context, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode) {
        if (!TextUtils.isEmpty(funactionCode) && funactionCode.contains("!")) {
            funactionCode = funactionCode.split("!")[0];
        }
        String url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.LOG_FUNCTION_EMPM_ONCE_JAVA;
//        LogFunactionStartInfo mLogFunctionStartInfo = new LogFunactionStartInfo();
//        mLogFunctionStartInfo.context = OAConText.getInstance(HtmitechApplication.instance());
        Logfunction logfunction = new Logfunction();
//        logfunction.functionCode = funactionCode;
        logfunction.functionCode = funactionCode;
        logfunction.userId = Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put("portalId", BookInit.getInstance().getPortalId());
        logfunction.appInfo = jsonObject;
        logfunction.groupCorpId = OAConText.getInstance(context).group_corp_id;
        LogManagerEnum mLogManagerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
        /**
         * 新增添加时间戳
         */
        if (mLogManagerEnum != null && mLogManagerEnum.getFunctionCode().equals(LogManagerEnum.APP_PAUSE_TOBACK.getFunctionCode())) {
            long currentMillis = System.currentTimeMillis();
            long upMillis = LogManagerEnum.getLogManagerEnum(LogManagerEnum.APP_PAUSE_TOBACK.getFunctionCode()).currentTimeMillis;
            logfunction.consumeMillis = currentMillis - upMillis;
            LogManagerEnum.getLogManagerEnum(LogManagerEnum.APP_PAUSE_TOBACK.getFunctionCode()).currentTimeMillis = 0l;
            ComponentInit.getInstance().getmILogUpdateCallListener().closeMseeage();
        } else if (mLogManagerEnum != null && mLogManagerEnum.functionCode.equals(LogManagerEnum.APP_RESUME.getFunctionCode())) {
            LogMesgUpUtil.uploadLoadMsg();
            LogManagerEnum.getLogManagerEnum(LogManagerEnum.APP_PAUSE_TOBACK.getFunctionCode()).currentTimeMillis = System.currentTimeMillis();
        }
//        mLogFunctionStartInfo.logfunction = logfunction;
        AnsynHttpRequest.requestByPost(context, logfunction, url, CHTTP.POST_LOG, mObserverCallBackType, requestName, funactionCode);
    }

    @Override
    public void setAppId(String appId) {
        this.app_id = appId;
    }

}
