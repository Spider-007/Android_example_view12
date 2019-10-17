package com.htmitech.proxy.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.htcommonformplugin.entity.Conidtionfiled;
import com.htmitech.htcommonformplugin.entity.Searchcondition;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;

import java.util.ArrayList;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by htrf-pc on 2017/4/18.
 */
public class AngleUntil {

    public static void setAngleNumberGeneralForm(final BinnerBitmapMessage mBinnerBitmapMessage, final Context context, final BaseAdapter baseAdapter){
        setAngleNumberGeneralForms(mBinnerBitmapMessage, context, baseAdapter,false);
    }

    /**
     * flag == 是否需要展示已办信息
     *
     * @param mBinnerBitmapMessage
     * @param context
     * @param baseAdapter
     */
    public static void setAngleNumberGeneralForm(final BinnerBitmapMessage mBinnerBitmapMessage, Context context, final BaseAdapter baseAdapter,boolean flag){
        if(mBinnerBitmapMessage.appInfo == null){
            Searchcondition mSearchcondition = new Searchcondition();
            mSearchcondition.app_id = mBinnerBitmapMessage.appid;
            mSearchcondition.user_id = PreferenceUtils.getEMPUserID(context == null ? HtmitechApplication.getInstance() : context);
            mSearchcondition.todoflag = mBinnerBitmapMessage.todoFlag;
            setrequest(mBinnerBitmapMessage,context,mSearchcondition,baseAdapter);
        }else{
            setAngleNumberGeneralForms(mBinnerBitmapMessage,context,baseAdapter,flag);
        }

    }



    private static void setAngleNumberGeneralForms(final BinnerBitmapMessage mBinnerBitmapMessage, final Context context, final BaseAdapter baseAdapter,boolean flag) {

        Searchcondition mSearchcondition = new Searchcondition();
        mSearchcondition.app_id = mBinnerBitmapMessage.appInfo.getApp_id() + "";
        mSearchcondition.user_id = PreferenceUtils.getEMPUserID(context);

        if (mBinnerBitmapMessage.appInfo.getmAppVersion() == null) {
            return;
        }
        ArrayList<AppVersionConfig> appVersionConfigs = mBinnerBitmapMessage.appInfo.getmAppVersion().getAppVersionConfigArrayList();
        if (appVersionConfigs == null) {
            return;
        }

        String com_commonform_plugin_selector_paramter_starttime = "";//查询的开始时间
        String com_commonform_plugin_selector_paramter_endtime = "";//查询的结束时间
        String com_commonform_plugin_selector_paramter_modulename = "";//支持模糊搜索，如“公文呈批件”，“公文收文”模块，输入“公文”来检索出所有流程名称中含“公文”的流程待办或已办项目
        String com_commonform_plugin_selector_paramter_todoflag = "";//代办已办标记0，代表待办；1，代表已办。空代表不限定
        String com_commonform_plugin_selector_paramter_myfavflag = "";//我关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_otherfavflag = "";//其他人关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_mystartflag = "";//我关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_otherconditions = "";
        String com_commonform_plugin_selector_paramter_flag = "";
        String com_workflow_plugin_selector_paramter_timeout_value = "";
        int days = 0;
        for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
            String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
            if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_starttime")) {
                com_commonform_plugin_selector_paramter_starttime = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_starttime")) {
                com_commonform_plugin_selector_paramter_endtime = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_days")) {
                days = configValue.equals("") ? 0 : Integer.parseInt(configValue);
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_modulename")) {
                com_commonform_plugin_selector_paramter_modulename = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_todoflag")) {
                com_commonform_plugin_selector_paramter_todoflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_myfavflag")) {
                com_commonform_plugin_selector_paramter_myfavflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_otherfavflag")) {
                com_commonform_plugin_selector_paramter_otherfavflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_mystartflag")) {
                com_commonform_plugin_selector_paramter_mystartflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_otherconditions")) {
                com_commonform_plugin_selector_paramter_otherconditions = configValue;
            }else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_flag")) {
                com_commonform_plugin_selector_paramter_flag = configValue;
            }else if(mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_timeout_value")){
                com_workflow_plugin_selector_paramter_timeout_value = configValue;//插件的
            }
        }
        if(!flag){
            if (com_commonform_plugin_selector_paramter_todoflag.equals("1")) {//如果是已办的话那么就不获得角标
                return;
            }
        }

        mSearchcondition.starttime = com_commonform_plugin_selector_paramter_starttime;
        mSearchcondition.endtime = com_commonform_plugin_selector_paramter_endtime;
        mSearchcondition.days = days;
        mSearchcondition.mystartflag = com_commonform_plugin_selector_paramter_mystartflag;
        mSearchcondition.todoflag = com_commonform_plugin_selector_paramter_todoflag;
        mSearchcondition.modulename = com_commonform_plugin_selector_paramter_modulename;
        mSearchcondition.otherfavflag = com_commonform_plugin_selector_paramter_otherfavflag;
        mSearchcondition.myfavflag = com_commonform_plugin_selector_paramter_myfavflag;
        mSearchcondition.flag = com_commonform_plugin_selector_paramter_flag;
        if(!TextUtils.isEmpty(com_workflow_plugin_selector_paramter_timeout_value)){
            mSearchcondition.TimeoutValue = com_workflow_plugin_selector_paramter_timeout_value;
        }
        List<Conidtionfiled> conidtionfileds = null;
        if (!com_commonform_plugin_selector_paramter_otherconditions.equals(""))
            conidtionfileds = JSON.parseArray(com_commonform_plugin_selector_paramter_otherconditions, Conidtionfiled.class);
        mSearchcondition.otherconditions = conidtionfileds;

        setrequest(mBinnerBitmapMessage,context,mSearchcondition,baseAdapter);
    }

    private static void setrequest(final BinnerBitmapMessage mBinnerBitmapMessage,final Context context,Searchcondition mSearchcondition,final BaseAdapter baseAdapter){
        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_GENERAL_FORM_COUNT;
        AnsynHttpRequest.requestByPost(context, mSearchcondition, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                Log.d("AnsynHttpRequest", successMessage);
                JSONObject mJSONObject = JSON.parseObject(successMessage);
                String Result = mJSONObject.getString("Result");

                if (Result != null && !Result.equals("") && !Result.equals("0")) {
                    try {
                        int resultInteger = Integer.parseInt(Result);
                        mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                        mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                        mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                        mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                        if (resultInteger > 99) {
                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
                            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                        }
                        if(baseAdapter != null)
                            baseAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
            }
        });
    }

    public static Searchcondition getSearchcondition(Context context,AppInfo appInfo){
        Searchcondition mSearchcondition = new Searchcondition();
        mSearchcondition.app_id = appInfo.getApp_id() + "";
        mSearchcondition.user_id = PreferenceUtils.getEMPUserID(context);

        if (appInfo.getmAppVersion() == null) {
            return null;
        }
        ArrayList<AppVersionConfig> appVersionConfigs = appInfo.getmAppVersion().getAppVersionConfigArrayList();
        if (appVersionConfigs == null) {
            return null;
        }

        String com_commonform_plugin_selector_paramter_starttime = "";//查询的开始时间
        String com_commonform_plugin_selector_paramter_endtime = "";//查询的结束时间
        String com_commonform_plugin_selector_paramter_modulename = "";//支持模糊搜索，如“公文呈批件”，“公文收文”模块，输入“公文”来检索出所有流程名称中含“公文”的流程待办或已办项目
        String com_commonform_plugin_selector_paramter_todoflag = "";//代办已办标记0，代表待办；1，代表已办。空代表不限定
        String com_commonform_plugin_selector_paramter_myfavflag = "";//我关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_otherfavflag = "";//其他人关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_mystartflag = "";//我关注的标记：0，不限；1只查询我关注的；
        String com_commonform_plugin_selector_paramter_otherconditions = "";
        String com_commonform_plugin_selector_paramter_flag = "";
        int days = 0;
        for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
            String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
            if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_starttime")) {
                com_commonform_plugin_selector_paramter_starttime = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_starttime")) {
                com_commonform_plugin_selector_paramter_endtime = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_days")) {
                days = configValue.equals("") ? 0 : Integer.parseInt(configValue);
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_modulename")) {
                com_commonform_plugin_selector_paramter_modulename = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_todoflag")) {
                com_commonform_plugin_selector_paramter_todoflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_myfavflag")) {
                com_commonform_plugin_selector_paramter_myfavflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_otherfavflag")) {
                com_commonform_plugin_selector_paramter_otherfavflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_mystartflag")) {
                com_commonform_plugin_selector_paramter_mystartflag = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_otherconditions")) {
                com_commonform_plugin_selector_paramter_otherconditions = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_commonform_plugin_selector_paramter_flag")) {
                com_commonform_plugin_selector_paramter_flag = configValue;
            }
        }
        mSearchcondition.starttime = com_commonform_plugin_selector_paramter_starttime;
        mSearchcondition.endtime = com_commonform_plugin_selector_paramter_endtime;
        mSearchcondition.days = days;
        mSearchcondition.mystartflag = com_commonform_plugin_selector_paramter_mystartflag;
        mSearchcondition.todoflag = com_commonform_plugin_selector_paramter_todoflag;
        mSearchcondition.modulename = com_commonform_plugin_selector_paramter_modulename;
        mSearchcondition.otherfavflag = com_commonform_plugin_selector_paramter_otherfavflag;
        mSearchcondition.myfavflag = com_commonform_plugin_selector_paramter_myfavflag;
        mSearchcondition.flag = com_commonform_plugin_selector_paramter_flag;
        List<Conidtionfiled> conidtionfileds = null;
        if (!com_commonform_plugin_selector_paramter_otherconditions.equals(""))
            conidtionfileds = JSON.parseArray(com_commonform_plugin_selector_paramter_otherconditions, Conidtionfiled.class);
        mSearchcondition.otherconditions = conidtionfileds;
        return mSearchcondition;
    }
}
