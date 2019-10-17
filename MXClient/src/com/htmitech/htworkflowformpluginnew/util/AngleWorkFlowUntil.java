package com.htmitech.htworkflowformpluginnew.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;

import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2017/4/18.
 */
public class AngleWorkFlowUntil {

    public static void setAngleNumberWorkFlowForm(final BinnerBitmapMessage mBinnerBitmapMessage, final Context context, final BaseAdapter baseAdapter) {
        setAngleNumberWorkFlowForms(mBinnerBitmapMessage, context, baseAdapter, false);
    }

    /**
     * flag == 是否需要展示已办信息
     *
     * @param mBinnerBitmapMessage
     * @param context
     * @param baseAdapter
     */
    public static void setAngleNumberWorkFlowForm(final BinnerBitmapMessage mBinnerBitmapMessage, Context context, final BaseAdapter baseAdapter, boolean flag) {
        if (mBinnerBitmapMessage.appInfo == null) {
            DocSearchParameters mDocSearchParameters = new DocSearchParameters();
            mDocSearchParameters.appId = mBinnerBitmapMessage.appid;
            mDocSearchParameters.userId = OAConText.getInstance(context).UserID;
            mDocSearchParameters.todoFlag = mBinnerBitmapMessage.todoFlag;
            setrequest(mBinnerBitmapMessage, context, mDocSearchParameters, baseAdapter);
        } else {
            setAngleNumberWorkFlowForms(mBinnerBitmapMessage, context, baseAdapter, flag);
        }

    }


    private static void setAngleNumberWorkFlowForms(final BinnerBitmapMessage mBinnerBitmapMessage, final Context context, final BaseAdapter baseAdapter, boolean flag) {

        DocSearchParameters mDocSearchParameters = new DocSearchParameters();
        mDocSearchParameters.appId = mBinnerBitmapMessage.appInfo.getApp_id() + "";
        mDocSearchParameters.userId = OAConText.getInstance(context).UserID;
        if (mBinnerBitmapMessage.appInfo.getmAppVersion() == null) {
            return;
        }
        ArrayList<AppVersionConfig> appVersionConfigs = mBinnerBitmapMessage.appInfo.getmAppVersion().getAppVersionConfigArrayList();
        if (appVersionConfigs == null) {
            return;
        }


        String com_workflow_plugin_selector_paramter_IsMyFav = "";//是否要查询我关注的
        String com_workflow_plugin_selector_paramter_IsMyStart = "";//是否要查询我发起的
        String com_workflow_plugin_selector_paramter_ModelName = "";//模块名称
        String com_workflow_plugin_selector_paramter_Title = "";//检索标题
        String com_workflow_plugin_selector_paramter_TodoFlag = "";//待办已办标记
        for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
            String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
            if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_IsMyFav")) {
                com_workflow_plugin_selector_paramter_IsMyFav = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_IsMyStart")) {
                com_workflow_plugin_selector_paramter_IsMyStart = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_ModelName")) {
                com_workflow_plugin_selector_paramter_ModelName = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_Title")) {
                com_workflow_plugin_selector_paramter_Title = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_TodoFlag")) {
                com_workflow_plugin_selector_paramter_TodoFlag = configValue;
            }
        }
        mDocSearchParameters.title = com_workflow_plugin_selector_paramter_Title;
        mDocSearchParameters.modelName = com_workflow_plugin_selector_paramter_ModelName;
        mDocSearchParameters.todoFlag = com_workflow_plugin_selector_paramter_TodoFlag;
        if (!flag) {
            if (com_workflow_plugin_selector_paramter_TodoFlag.equals("1")) {//如果是已办的话那么就不获得角标
                return;
            }
        }

        setrequest(mBinnerBitmapMessage, context, mDocSearchParameters, baseAdapter);
    }

    private static void setrequest(final BinnerBitmapMessage mBinnerBitmapMessage, final Context context, DocSearchParameters mDocSearchParameters, final BaseAdapter baseAdapter) {
//        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;
        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.ShowNumber(mBinnerBitmapMessage, context, mDocSearchParameters, baseAdapter);
//        AnsynHttpRequest.requestByPost(context, mDocSearchParameters, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {
//
//            @Override
//            public void success(String successMessage) {
//                // TODO Auto-generated method stub
//                Log.d("AnsynHttpRequest", successMessage);
//                JSONObject mJSONObject = JSON.parseObject(successMessage);
//                String Result = mJSONObject.getString("Result");
//
//                if (Result != null && !Result.equals("") && !Result.equals("0")) {
//                    try {
//                        int resultInteger = Integer.parseInt(Result);
//                        mBinnerBitmapMessage.numberFlag = View.VISIBLE;
//                        mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
//                        mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
//                        mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
//                        if (resultInteger > 99) {
//                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(context, 30), DeviceUtils.dip2px(context, 15));
//                            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
//                        }
//                        if(baseAdapter != null)
//                            baseAdapter.notifyDataSetChanged();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void notNetwork() {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void callbackMainUI(String successMessage) {
//
//            }
//
//            @Override
//            public void fail(String exceptionMessage) {
//                // TODO Auto-generated method stub
//            }
//        });
    }

    public static DocSearchParameters getSearchcondition(Context context, AppInfo appInfo) {
        DocSearchParameters mDocSearchParameters = new DocSearchParameters();
        if(appInfo == null){
            return null;
        }
        mDocSearchParameters.appId = appInfo.getApp_id() + "";

        if(context != null){
            mDocSearchParameters.userId = OAConText.getInstance(context).UserID;
        }
        if (appInfo.getmAppVersion() == null) {
            return null;
        }
        ArrayList<AppVersionConfig> appVersionConfigs = appInfo.getmAppVersion().getAppVersionConfigArrayList();
        if (appVersionConfigs == null) {
            return null;
        }

        String com_workflow_plugin_selector_paramter_IsMyFav = "";//是否要查询我关注的
        String com_workflow_plugin_selector_paramter_IsMyStart = "";//是否要查询我发起的
        String com_workflow_plugin_selector_paramter_ModelName = "";//模块名称
        String com_workflow_plugin_selector_paramter_Title = "";//检索标题
        String com_workflow_plugin_selector_paramter_TodoFlag = "";//待办已办标记
        String com_workflow_plugin_selector_paramter_importance_workas_toreadflag = "";
        String startTime = "";
        String endTime = "";
        String com_workflow_plugin_selector_paramter_others = "";
        for (AppVersionConfig mAppVersionConfig : appVersionConfigs) {
            String configValue = mAppVersionConfig.getConfig_value() == null ? "" : mAppVersionConfig.getConfig_value();
            if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_IsMyFav")) {
                com_workflow_plugin_selector_paramter_IsMyFav = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_IsMyStart")) {
                com_workflow_plugin_selector_paramter_IsMyStart = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_ModelName")) {
                com_workflow_plugin_selector_paramter_ModelName = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_Title")) {
                com_workflow_plugin_selector_paramter_Title = configValue;
            } else if (mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_TodoFlag")) {
                com_workflow_plugin_selector_paramter_TodoFlag = configValue;
            }else if(mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_importance_workas_toreadflag")){
                com_workflow_plugin_selector_paramter_importance_workas_toreadflag = configValue;
            }else if(mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_startTime")){
                startTime = configValue;
            }else if(mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_endTime")){
                endTime = configValue;
            }else if(mAppVersionConfig.getConfig_code().equals("com_workflow_plugin_selector_paramter_others")){
                com_workflow_plugin_selector_paramter_others = configValue;//插件的
            }else if("com_workflow_plugin_selector_paramter_others".equals(mAppVersionConfig.getConfig_code())){
                com_workflow_plugin_selector_paramter_others = configValue;
            }
        }
        if(TextUtils.isEmpty(com_workflow_plugin_selector_paramter_TodoFlag) && !TextUtils.isEmpty(com_workflow_plugin_selector_paramter_importance_workas_toreadflag)){
            mDocSearchParameters.importance = com_workflow_plugin_selector_paramter_importance_workas_toreadflag;
        }
        mDocSearchParameters.startTime = startTime;
        mDocSearchParameters.startTime = endTime;
        mDocSearchParameters.setOthers(com_workflow_plugin_selector_paramter_others);
        mDocSearchParameters.title = com_workflow_plugin_selector_paramter_Title;
        mDocSearchParameters.modelName = com_workflow_plugin_selector_paramter_ModelName;
        mDocSearchParameters.todoFlag = TextUtils.isEmpty(com_workflow_plugin_selector_paramter_TodoFlag) ? "0" : com_workflow_plugin_selector_paramter_TodoFlag;
        return mDocSearchParameters;
    }
}
