package com.htmitech.htworkflowformpluginnew.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowCountCallBack;

import htmitech.com.componentlibrary.entity.BadgeResult;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.utils.FastJsonUtils;
import com.minxing.client.tab.MenuTabItem;

import java.util.ArrayList;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import org.greenrobot.eventbus.EventBus;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import utils.DeviceUtils;

/**
 * 代办已办角标数量封装
 *
 * @anthor joe
 * @date 2017-07-19 15:39:17
 */
public class WorkFlowCountHttpUtil implements IWorkFlowCountCallBack, ObserverCallBackType {

    public static String daiban_yiban_url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT_JAVA;
    public static String daiban_yiban_all_url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT_ALL_JAVA;
    private INetWorkManager netWorkManager;
    private int TYPE = 0;
    public AppInfo mAppInfo;
    private TextView textNumber;
    private Context context;
    private HomeGridSysle.BinnerBitmapMessage mBinnerBitmapMessage;
    private MenuTabItem homeTabItem;
    private BinnerBitmapMessage binnerBitmapMessage;
    private DocSearchParameters docSearchParameters;
    private BaseAdapter baseAdapter;
    private static String HTTPDATBANYIBAN = "daibanyibancount";
    private static String HTTPDATBANYIBAN_ALL = "daibanyibancount_all";
    private String TodoFlag = "";
    private DocSearchParameters docSearchParameter;
    private int number = 0;
    public Map<String, Object> numMap;

    public ArrayList<BinnerBitmapMessage> magnetBinnerBitmapMessage;

    public boolean isTypeNatvie = false;


    public WorkFlowCountHttpUtil() {
        netWorkManager = new NetWorkManager();
    }

    @Override
    public void ShowNumber(AppInfo mAppInfo, TextView textNumber, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter) {

        this.TYPE = 0;
        this.mAppInfo = mAppInfo;
        this.textNumber = textNumber;
        this.context = context;
        this.docSearchParameters = docSearchParameters;
        this.baseAdapter = baseAdapter;
//        if (refresh(docSearchParameters)) {
////            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
//            AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//        }
        if (BadgeAllUnit.get().getBadgeSize() == 0) { //如果 == 0 则进行请求接口 否则都不进行请求

            ArrayList<BadgeResult> badgeResults = new ArrayList<>();
            BadgeResult mBadgeResult = new BadgeResult();
            mBadgeResult.setAppId("1");//临时的app_id
            badgeResults.add(mBadgeResult);
            BadgeAllUnit.get().addALlBadgeBean(badgeResults); //防止请求出现延迟 出现多个请求现象，临时添加一条数据

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("userId", docSearchParameters.userId);
            jsonObject.put("appVersionType", "1");
            AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
            return;
        }
    }

    @Override
    public void ShowNumber(BinnerBitmapMessage binnerBitmapMessage, Context context, DocSearchParameters docSearchParameters) {
        this.TYPE = 1;
        this.binnerBitmapMessage = binnerBitmapMessage;
        this.context = context;
        this.docSearchParameters = docSearchParameters;
        this.mAppInfo = binnerBitmapMessage.appInfo;
        /**
         * 对获取角标接口进行改造
         */
        if (BadgeAllUnit.get().getBadgeSize() == 0 && !isTypeNatvie) { //如果 == 0 则进行请求接口 否则都不进行请求
//            ArrayList<BadgeResult> badgeResults = new ArrayList<>();
//            BadgeResult mBadgeResult = new BadgeResult();
//            mBadgeResult.setAppId("1");//临时的app_id
//            badgeResults.add(mBadgeResult);
//            BadgeAllUnit.get().addALlBadgeBean(badgeResults); //防止请求出现延迟 出现多个请求现象，临时添加一条数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("userId", docSearchParameters.userId);
            jsonObject.put("appVersionType", "1");
            AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
            return;
        }

        if (isTypeNatvie) {
            if (binnerBitmapMessage.isCJ) {
                refresh(docSearchParameters);
//            AnsynHttpRequest.requestByPostWithToken(context, docSearchParameters, daiban_yiban_url, CHTTP.POSTWITHTOKEN, WorkFlowCountHttpUtil.this, HTTPDATBANYIBAN, LogManagerEnum.GETDOCCOUNT.functionCode);

                AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
            } else {
                if (refresh(docSearchParameters)) {
                    AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//                netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
                }

            }
        }


    }

    @Override
    public void ShowNumber(HomeGridSysle.BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter) {

        this.TYPE = 2;
        this.mBinnerBitmapMessage = mBinnerBitmapMessage;
        this.context = context;
        this.mAppInfo = mBinnerBitmapMessage.mAppInfo;
        this.docSearchParameters = docSearchParameters;
        this.baseAdapter = baseAdapter;

        /**
         * 对获取角标接口进行改造
         */
        if (BadgeAllUnit.get().getBadgeSize() == 0) { //如果 == 0 则进行请求接口 否则都不进行请求
//            ArrayList<BadgeResult> badgeResults = new ArrayList<>();
//            BadgeResult mBadgeResult = new BadgeResult();
//            mBadgeResult.setAppId("1");//临时的app_id
//            badgeResults.add(mBadgeResult);
//            BadgeAllUnit.get().addALlBadgeBean(badgeResults); //防止请求出现延迟 出现多个请求现象，临时添加一条数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("userId", docSearchParameters.userId);
            jsonObject.put("appVersionType", "1");
            AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
            return;
        }

//        if (refresh(docSearchParameters)){
//            AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//        }
//            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);

    }

    @Override
    public void ShowNumber(MenuTabItem homeTabItem, Context context, DocSearchParameters docSearchParameters) {

        this.TYPE = 3;
        this.context = context;
        this.homeTabItem = homeTabItem;
        /**
         * 对获取角标接口进行改造
         */
        if (BadgeAllUnit.get().getBadgeSize() == 0) { //如果 == 0 则进行请求接口 否则都不进行请求
//            ArrayList<BadgeResult> badgeResults = new ArrayList<>();
//            BadgeResult mBadgeResult = new BadgeResult();
//            mBadgeResult.setAppId("1");//临时的app_id
//            badgeResults.add(mBadgeResult);
//            BadgeAllUnit.get().addALlBadgeBean(badgeResults); //防止请求出现延迟 出现多个请求现象，临时添加一条数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("userId", docSearchParameters.userId);
            jsonObject.put("appVersionType", "1");
            AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
            return;
        }
//        if (refresh(docSearchParameters)){
//            AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//        }
//            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
    }

//    @Override
//    public void ShowNumber(BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter) {
//
//        this.TYPE = 4;
//        this.binnerBitmapMessage = mBinnerBitmapMessage;
//        this.context = context;
//        this.docSearchParameters = docSearchParameters;
//        this.baseAdapter = baseAdapter;
//        this.mAppInfo = mBinnerBitmapMessage.appInfo;
//
//        if (refresh(docSearchParameters))
//            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
//    }

    @Override
    public void ShowNumber(BinnerBitmapMessage mBinnerBitmapMessage, Context context, DocSearchParameters docSearchParameters, BaseAdapter baseAdapter) {

        this.TYPE = 5;
        this.binnerBitmapMessage = mBinnerBitmapMessage;
        this.context = context;
        this.docSearchParameter = docSearchParameters;
        this.baseAdapter = baseAdapter;
        this.mAppInfo = mBinnerBitmapMessage.appInfo;
        /**
         * 对获取角标接口进行改造
         */
        if (BadgeAllUnit.get().getBadgeSize() == 0) { //如果 == 0 则进行请求接口 否则都不进行请求
//            ArrayList<BadgeResult> badgeResults = new ArrayList<>();
//            BadgeResult mBadgeResult = new BadgeResult();
//            mBadgeResult.setAppId("1");//临时的app_id
//            badgeResults.add(mBadgeResult);
//            BadgeAllUnit.get().addALlBadgeBean(badgeResults); //防止请求出现延迟 出现多个请求现象，临时添加一条数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("userId", docSearchParameters.userId);
            jsonObject.put("appVersionType", "1");
            AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
            return;
        }
//        String prefix = "com_workflow";
//        String TodoFlag = "";
//        String com_commonform_plugin_selector_paramter_modulename = "";
//        if (mAppInfo != null) {
//            ArrayList<AppVersionConfig> appVersionConfigs = mAppInfo.getmAppVersion().getAppVersionConfigArrayList();
//            for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
//                String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
//                if (mAppVersionConfig.getConfig_code().equals(prefix + "_plugin_selector_paramter_ModelName")) {
//                    com_commonform_plugin_selector_paramter_modulename = configValue;
//                } else if (mAppVersionConfig.getConfig_code().equals(prefix + "_plugin_selector_paramter_TodoFlag")) {
//                    TodoFlag = configValue;
//                }
//            }
//        }
//        if (mAppInfo.getApp_type() == 1) {
//            TodoFlag = "0";
//        }
//        this.docSearchParameter.modelName = com_commonform_plugin_selector_paramter_modulename;
//        this.docSearchParameter.todoFlag = TodoFlag;
//        if (TodoFlag.equals("0")) {
////            netWorkManager.logFunactionStart(context, this, LogManagerEnum.GETDOCCOUNT);
//            AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN, "");
//        }
    }

    public boolean refresh(DocSearchParameters docSearchParameters) {

//        if(mAppInfo == null){
//            return true;
//        }

        if (mAppInfo == null) {
            return true;
        }
        int appType = mAppInfo.getApp_type();
        String prefix = "com_workflow";
        TodoFlag = "";
        String Importance = "";
        String com_workflow_mobileconfig_todoflag = "";
        String com_workflowplugin_selector_paramter__todoflag = "";
        String com_commonform_plugin_selector_paramter_modulename = "";
        String com_workflow_plugin_selector_paramter_timeout_value = "";
        String com_workflow_mobileconfig_importance_workas_toreadflag = "";
        String com_workflow_mobileconfig_others = "";
        String com_workflow_mobileconfig_tabbutton_style = "";
        String startTime = "";
        String endTime = "";
        String title = "";
        if (mAppInfo != null && mAppInfo.getmAppVersion() != null) {
            ArrayList<AppVersionConfig> appVersionConfigs = mAppInfo.getmAppVersion().getAppVersionConfigArrayList();
            for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
                String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
                if (mAppVersionConfig.getConfig_code().equalsIgnoreCase(prefix + "_plugin_selector_paramter_ModelName")) {
                    com_commonform_plugin_selector_paramter_modulename = configValue;
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase(prefix + "_plugin_selector_paramter_TodoFlag")) {
                    TodoFlag = configValue;
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase(prefix + "_plugin_selector_paramter_importance_workas_toreadflag")) {
                    Importance = configValue;
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase("com_workflow_plugin_selector_paramter_timeout_value")) {
                    com_workflow_plugin_selector_paramter_timeout_value = configValue;//插件的
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase("com_workflow_mobileconfig_todoflag")) {
                    com_workflow_mobileconfig_todoflag = configValue;
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase("com_workflow_mobileconfig_importance_workas_toreadflag")) {
                    com_workflow_mobileconfig_importance_workas_toreadflag = configValue;
                } else if (mAppVersionConfig.getConfig_code().equalsIgnoreCase("com_workflow_plugin_selector_paramter_todoflag")) {
                    com_workflowplugin_selector_paramter__todoflag = configValue;
                } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_startTime")) {
                    startTime = configValue;
                } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_endTime")) {
                    endTime = configValue;
                } else if ("com_workflow_mobileconfig_tabbutton_style".equals(mAppVersionConfig.getConfig_code())) {
                    com_workflow_mobileconfig_tabbutton_style = configValue;
                } else if ("com_workflow_plugin_selector_paramter_Title".equals(mAppVersionConfig.getConfig_code())) {
                    title = configValue;
                } else if ("com_workflow_mobileconfig_others".equals(mAppVersionConfig.getConfig_code())) {
                    com_workflow_mobileconfig_others = configValue;
                } else if ("com_workflow_plugin_selector_paramter_others".equals(mAppVersionConfig.getConfig_code())) {
                    com_workflow_mobileconfig_others = configValue;
                }
            }
            if (mAppInfo.getApp_type() == 1) {
                TodoFlag = "0";
            }
        }

        if (!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && (com_workflow_mobileconfig_tabbutton_style.equals("4") || com_workflow_mobileconfig_tabbutton_style.equals("6"))) {
            return false;
        }
        if (binnerBitmapMessage != null && binnerBitmapMessage.todoFlag != null && TodoFlag.equals("")) {
            TodoFlag = binnerBitmapMessage.todoFlag;
        }
        docSearchParameters.modelName = com_commonform_plugin_selector_paramter_modulename;
        docSearchParameters.todoFlag = TodoFlag;
        if (appType == 2) {
            com_workflow_mobileconfig_todoflag = com_workflowplugin_selector_paramter__todoflag;
        } else if (appType == 1) {
            Importance = com_workflow_mobileconfig_importance_workas_toreadflag;
        }
        if (TextUtils.isEmpty(com_workflow_mobileconfig_todoflag) && !TextUtils.isEmpty(Importance)) {
            docSearchParameters.importance = Importance;
        }

        if (!TextUtils.isEmpty(com_workflow_plugin_selector_paramter_timeout_value)) {
            docSearchParameters.timeoutValue = com_workflow_plugin_selector_paramter_timeout_value;
        }

        if (!TextUtils.isEmpty(endTime)) {
            docSearchParameters.endTime = endTime;
        }

        if (!TextUtils.isEmpty(startTime)) {
            docSearchParameters.startTime = startTime;
        }

        if (!TextUtils.isEmpty(title)) {
            docSearchParameters.title = title;
        }

        if (appType == 1) {
            if (!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && (com_workflow_mobileconfig_tabbutton_style.equals("2") || com_workflow_mobileconfig_tabbutton_style.equals("5"))) {
                docSearchParameters.importance = "0";
            } else if (!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && (com_workflow_mobileconfig_tabbutton_style.equals("1") || com_workflow_mobileconfig_tabbutton_style.equals("3"))) {
                docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
            }
        }

        if (!TextUtils.isEmpty(com_workflow_mobileconfig_others)) {
            docSearchParameters.setOthers(com_workflow_mobileconfig_others);
        }

//        return TodoFlag.equals("0") ? true : false;
        if (!TextUtils.isEmpty(TodoFlag)) {
            return TodoFlag.equals("0") ? true : false;
        } else {
            return Importance.equals("0") ? true : false;
        }
    }


    @Override
    public void success(String requestValue, int type, String requestName) {

        if (requestName == LogManagerEnum.GETDOCCOUNT.functionCode) {
            AnsynHttpRequest.requestByPostWithToken(context, docSearchParameters, daiban_yiban_url, CHTTP.POSTWITHTOKEN, WorkFlowCountHttpUtil.this, HTTPDATBANYIBAN, LogManagerEnum.GETDOCCOUNT.functionCode);
        } else if (requestName == HTTPDATBANYIBAN) {
            JSONObject mJSONObject = null;
            try {
                mJSONObject = JSON.parseObject(requestValue);
            } catch (Exception e) {
                return;
            }

            String Result = mJSONObject.getString("result");
            if (Result != null && !Result.equals("") && !Result.equals("0")) {
//                netWorkManager.logFunactionFinsh(context, this, "loginfinshSuccess", LogManagerEnum.GETDOCCOUNT.functionCode, "success", INetWorkManager.State.SUCCESS);
            } else {
//                netWorkManager.logFunactionFinsh(context, this, "loginfinshFail", LogManagerEnum.GETDOCCOUNT.functionCode, "fail", INetWorkManager.State.FAIL);
                return;
            }
            switch (TYPE) {
                case 0:
                    if (Result != null && !Result.equals("") && !Result.equals("0")) {
                        try {
                            int resultInteger = Integer.parseInt(Result);
                            textNumber.setVisibility(View.VISIBLE);
                            textNumber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                            if (resultInteger > 99) {
                                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                textNumber.setLayoutParams(layoutParms);
                            }
                            if (TodoFlag.equals("0")) {
                                number += resultInteger;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    if (Result != null && !Result.equals("")) {
                        try {
                            int resultInteger = Integer.parseInt(Result);
                            if (TodoFlag.equals("0")) {
                                number += resultInteger;
                            }
                            if (resultInteger > 0) {
                                if (binnerBitmapMessage != null && binnerBitmapMessage.numberText != null) {
                                    binnerBitmapMessage.numberText.setVisibility(View.VISIBLE);
                                    binnerBitmapMessage.numberText.setText(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                                }
                                if (binnerBitmapMessage != null && binnerBitmapMessage.angle_nulber != null) {
                                    binnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                                    binnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                                }
                                if (binnerBitmapMessage != null && binnerBitmapMessage.getmBaseAdapter() != null) {
                                    binnerBitmapMessage.setNumber(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                                    binnerBitmapMessage.numberFlag = View.VISIBLE;
                                    binnerBitmapMessage.getmBaseAdapter().notifyDataSetChanged();
                                }
                            } else {
                                if (binnerBitmapMessage != null && binnerBitmapMessage.getmBaseAdapter() != null) {
                                    binnerBitmapMessage.numberFlag = View.GONE;
                                    binnerBitmapMessage.getmBaseAdapter().notifyDataSetChanged();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (binnerBitmapMessage != null && binnerBitmapMessage.numberText != null) {
                            binnerBitmapMessage.numberText.setVisibility(View.GONE);
                            binnerBitmapMessage.numberText.setText("");
                        } else if (binnerBitmapMessage != null && binnerBitmapMessage.angle_nulber != null) {
                            binnerBitmapMessage.angle_nulber.setVisibility(View.GONE);
                            binnerBitmapMessage.angle_nulber.setText("");
                        }

                    }
                    break;
                case 2:
                    if (Result != null && !Result.equals("") && !Result.equals("0")) {
                        try {
                            int resultInteger = Integer.parseInt(Result);
                            number += resultInteger;
                            mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                            mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                            mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                            mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                            if (resultInteger > 99) {
                                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                            }
                            if (baseAdapter != null)
                                baseAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    if (Result != null && !Result.equals("")) {
                        int resultInteger = Integer.parseInt(Result);
                        number += resultInteger;
                        if (resultInteger > 0) {
                            homeTabItem.showNumberMarker(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                        } else {
                            homeTabItem.hideNumberMarker();
                        }

                    } else {
                    }
                    break;
                case 4:
                    if (Result != null && !Result.equals("") && !Result.equals("0")) {
                        try {
                            int resultInteger = Integer.parseInt(Result);
                            number += resultInteger;
                            if (mBinnerBitmapMessage == null && binnerBitmapMessage != null) {
                                binnerBitmapMessage.numberFlag = View.VISIBLE;
                                binnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                                binnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                                binnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                                if (resultInteger > 99) {
                                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                    binnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                                }
                            } else {
                                mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                                mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                                mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                                mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                                if (resultInteger > 99) {
                                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                    mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                                }
                            }

                            if (baseAdapter != null)
                                baseAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 5:
                    if (Result != null && !Result.equals("") && !Result.equals("0")) {
                        try {
                            int resultInteger = Integer.parseInt(Result);
                            number += resultInteger;
                            mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                            mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                            mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                            mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                            if (resultInteger > 99) {
                                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                                mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                            }
                            if (baseAdapter != null)
                                baseAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
            if (!TodoFlag.equals("1")) {
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "ZYJB";
                mClassEvent.number = number;
                EventBus.getDefault().post(mClassEvent);
            }
            Log.d("tony", "TODOFLAG = " + TodoFlag + "   number ------->" + number);
        } else if (requestName.equals(HTTPDATBANYIBAN_ALL)) {
            JSONObject mJSONObject = null;
            try {
                mJSONObject = JSON.parseObject(requestValue);
            } catch (Exception e) {
                return;
            }
            if (mJSONObject != null && mJSONObject.getInteger("code") != null && mJSONObject.getInteger("code") == 200) {

                BadgeAllUnit.get().clearBadgeUnit();
                BadgeAllUnit.get().addALlBadgeBean((ArrayList<BadgeResult>) FastJsonUtils.getPersonList(mJSONObject.getString("result"), BadgeResult.class));
                if (binnerBitmapMessage != null && binnerBitmapMessage.getmBaseAdapter() != null) {
                    binnerBitmapMessage.getmBaseAdapter().notifyDataSetChanged();
                }


                if (baseAdapter != null) {
                    baseAdapter.notifyDataSetChanged();
                }
                int counts = BadgeAllUnit.get().getAllNumber();
                if (magnetBinnerBitmapMessage != null) {
                    for (BinnerBitmapMessage mBinnerBitmapMessage : magnetBinnerBitmapMessage) {
                        if (mBinnerBitmapMessage != null && mBinnerBitmapMessage.angle_nulber != null) {
                            String number = BadgeAllUnit.get().getBadge(mBinnerBitmapMessage.appInfo);
                            if (!TextUtils.isEmpty(number) && !TextUtils.equals("0",number)) {
                                int count = Integer.parseInt(number);
                                mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                                mBinnerBitmapMessage.angle_nulber.setText(count > 99 ? 99 + "+" : count + "");
                                if(count > 99){
                                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 20), DeviceUtils.dip2px(context, 15));
                                    layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                    mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                                }
                            } else {
                                mBinnerBitmapMessage.angle_nulber.setVisibility(View.GONE);
                            }

                        }
                    }
                }

                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "ZYJB";
                mClassEvent.number = counts;
                EventBus.getDefault().post(mClassEvent);

            }

        }


    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName == LogManagerEnum.GETDOCCOUNT.functionCode) {
            AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url, CHTTP.POST_LOG, WorkFlowCountHttpUtil.this, HTTPDATBANYIBAN, LogManagerEnum.GETDOCCOUNT.functionCode);
        } else {
//            netWorkManager.logFunactionFinsh(context, this, "loginfinshFail", LogManagerEnum.GETDOCCOUNT.functionCode, "fail", INetWorkManager.State.FAIL);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }


}
