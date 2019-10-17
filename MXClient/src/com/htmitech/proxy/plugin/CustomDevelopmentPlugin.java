package com.htmitech.proxy.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.pop.AlertDialog;
import com.htmitech.pop.LoginAlertDialog;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.ActivateInfo;
import com.htmitech.proxy.doman.Activateparameter;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.IsActivateStateInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.bean.GetCisAccountByAppResultRoot;
import com.htmitech.ztcustom.zt.chinarailway.InitDamage;

import java.util.Map;

import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.entity.RequestEntity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 用于定制开发界面
 */
public class CustomDevelopmentPlugin implements ApplicationObserver,ObserverCallBackType {
    private Gson mGson = new Gson();
    private String jsonToString;
    LoadingDialog mLoadingDialog;
    private final String HTTPTYPE = "isActivity";
    private final String GETCISACCOUNT = "getcisaccount";
    private INetWorkManager netWorkManager;
    public Context context;
    private ActivateInfo mActivateInfo;
    private String getIsActivateStateUrl;
    private AppInfo mAppInfo;
    private Map<String, Object> parameters;
    private LoginAlertDialog loginAlertDialog;
    private String app_id;
    private String app_name;
    private Activateparameter mActivateparameter;
    private static final String MHTTPTYPE_LOG_FINISH = "activate_log_finish";
    private static final String MHTTPTYPE_LOG = "activate_log";
    private static final String MHTTPTYPE = "activate";
    private static final String getCisAccountByApp = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GETCISACCOUNTBYAPP;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 700:
                    HTActivityUnit.switchTo((Activity) context, mAppInfo.getmAppVersion().getPackage_name() + "." + mAppInfo.getmAppVersion().getPackage_main(), parameters);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        try {
            this.context = context;
            this.mAppInfo = mAppInfo;
            this.parameters = parameters;
            mLoadingDialog = new LoadingDialog(context);
            mLoadingDialog.setCancelable(true);
            mLoadingDialog.setCanceledOnTouchOutside(true);
            app_id = mAppInfo.getApp_id() + "";
            app_name = mAppInfo.getApp_name();
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            String function_code = (String)parameters.get("function_code");
            netWorkManager.logFunactionOnce(context,CustomDevelopmentPlugin.this,"log_function_once", function_code);
            getIsActivateStateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerIsActivateState;
            IsActivateStateInfo mIsActivateStateInfo = new IsActivateStateInfo();
            //    <--------------------Administrator -> 2019-8-13:18:33:设置新接口的userID使用新的接口--------------------->
            mIsActivateStateInfo.setUser_id(OAConText.getInstance(context).UserID);
            //    <--------------------Administrator -> 2019-8-13:18:33:设置新接口的appID使用新的接口--------------------->
            mIsActivateStateInfo.setApp_id(mAppInfo.getApp_id() + "");
            jsonToString = mGson.toJson(mIsActivateStateInfo);
            showDialog();
            AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getIsActivateStateUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.GGENERAL.getFunctionCode());
//            HTActivityUnit.switchTo((Activity) context, mAppInfo.getmAppVersion().getPackage_name() + "." + mAppInfo.getmAppVersion().getPackage_main(), parameters);
        }catch (NotApplicationException e){
            Log.d("TAG","找不到对应的界面");
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(HTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, getIsActivateStateUrl, jsonToString, this, requestName, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {

                mActivateInfo = mGson.fromJson(requestValue.toString(), ActivateInfo.class);
                if (mActivateInfo.code == 400) {
                    if (mActivateInfo != null && mActivateInfo.getMessage() != null) {
                        Toast.makeText(context, "服务器异常" + mActivateInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismissDialog();
                    return;
                }
                int result = mActivateInfo.getResult();
                switch (result) {
                    case 0:
                        /*
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("对应目标系统账号已停用！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();*/
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("user_id",OAConText.getInstance(context).UserID);
                            jsonObject.put("app_id",app_id);
                            //    <--------------------Administrator -> 2019-8-15:14:01:->调用getCisAccountByApp接口，获得当前用户访问当前应用的子账号信息；--------------------->
                            AnsynHttpRequest.requestByPostWithToken(context, jsonObject.toJSONString(), getCisAccountByApp, CHTTP.POSTWITHTOKEN, this, GETCISACCOUNT, LogManagerEnum.GGENERAL.getFunctionCode());
                        }catch (Exception e) {
                            e.printStackTrace();
                            Log.e("YJH", "success:-> 判断用户子账号是否激活:"+ e.getMessage() + "-----" + e );
                        }
                        break;
                    case 1:
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("user_id",OAConText.getInstance(context).UserID);
                            jsonObject.put("app_id",app_id);
                            AnsynHttpRequest.requestByPostWithToken(context, jsonObject.toJSONString(), getCisAccountByApp, CHTTP.POSTWITHTOKEN, this, GETCISACCOUNT, LogManagerEnum.GGENERAL.getFunctionCode());
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case -10:
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("字段校验失败！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case -9:
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("没有对应的应用信息！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case -8:
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("没有对应的目标系统，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case -7:
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("没有对应的目标系统接口，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case -1:
                        loginAlertDialog = new LoginAlertDialog(context).builder().setTitle(app_name).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                        loginAlertDialog.setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (loginAlertDialog.getParams() != null) {
                                    Map<String, Object> params = loginAlertDialog.getParams();
                                    String user_name = (String) params.get("userName");
                                    String pass_word = (String) params.get("passWord");
                                    getIsActivateStateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerActivate;
                                    mActivateparameter = new Activateparameter();
                                    mActivateparameter.setUser_id(OAConText.getInstance(context).UserID);
                                    mActivateparameter.setCis_id("");
                                    mActivateparameter.setApp_id(app_id);
                                    mActivateparameter.setLogin_name(user_name);
                                    mActivateparameter.setPassword(DesUtil.encode(DesUtil.KEY, pass_word));
                                    jsonToString = mGson.toJson(mActivateparameter);
                                    try {
                                        netWorkManager.logFunactionStart(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getIsActivateStateUrl, CHTTP.POSTWITHTOKEN, GJBuildPlugin.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.functionCode);

                                } else {
                                    return;
                                }
                            }
                        });
                        loginAlertDialog.show();
//                            Log.d(TAG, "success: 对应目标系统账号未激活");
//                            Map<String, Object> params = new HashMap<String, Object>();
//                            params.put("titleName", "");
//                            params.put("Cis_id","");
//                            params.put("app_id", app_id);
//                            ZTActivityUnit.switchTo((Activity) context, ChilDaccountYZActivity.class, params);
                        break;
                }


            }
        } else if (requestName.equals(MHTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, getIsActivateStateUrl, jsonToString, this, requestName, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                if (requestValue != null)
                    mActivateInfo = mGson.fromJson(requestValue.toString(), ActivateInfo.class);
                int result = mActivateInfo.getResult();
                if (loginAlertDialog != null && loginAlertDialog.isShow()) {
                    loginAlertDialog.dialog_dismiss();
                }
                switch (result) {
                    case 0:
//                        Toast.makeText(ChilDaccountYZActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.SUCCESS);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("激活成功！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case 1:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("用户被禁用，不能激活，请联系管理员！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case 9:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("调用接口返回信息失败！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -10:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("字段校验失败！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -9:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的应用信息！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -8:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的目标系统，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -7:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的目标系统接口，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -1:
                        netWorkManager.logFunactionFinsh(context, CustomDevelopmentPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("对应目标系统账号未激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                }

            }


        } else if (requestName.equals(MHTTPTYPE_LOG)) {
            AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getIsActivateStateUrl, CHTTP.POSTWITHTOKEN, CustomDevelopmentPlugin.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
        }else if(GETCISACCOUNT.equals(requestName)){ //    <--------------------Administrator -> 2019-8-15:14:05:获取子账号 使用cis的新的接口--------------------->
            GetCisAccountByAppResultRoot getCisAccountByAppResultRoot = JSONObject.parseObject(requestValue, GetCisAccountByAppResultRoot.class);
            if(null != getCisAccountByAppResultRoot && null != getCisAccountByAppResultRoot.result){
                //    <--------------------Administrator -> 2019-8-13:21:09: 缓存 --------------------->
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisId = getCisAccountByAppResultRoot.result.cisId;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisCode = getCisAccountByAppResultRoot.result.cisCode;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisName = getCisAccountByAppResultRoot.result.cisName;
                ZTCustomInit.get().getmCache().getCisAccountDetail().applyType = getCisAccountByAppResultRoot.result.applyType;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId = getCisAccountByAppResultRoot.result.cisAccountId;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountName = getCisAccountByAppResultRoot.result.cisAccountName;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId = getCisAccountByAppResultRoot.result.cisDeptId;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptName = getCisAccountByAppResultRoot.result.cisDeptName;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptCode = getCisAccountByAppResultRoot.result.cisDeptCode;
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType = getCisAccountByAppResultRoot.result.cisDeptType;
                //    <--------------------Administrator -> 2019-8-15:17:29: 子账号添加显示--------------------->
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountLogin = getCisAccountByAppResultRoot.result.cisAccountLogin;
                ZTCustomInit.get().getmCache().currentUserId = getCisAccountByAppResultRoot.result.cisAccountId;
                Log.e("YJH", "success-> cisAccountLogin:"+getCisAccountByAppResultRoot.result.cisAccountLogin);

//                ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountLogin = getCisAccountByAppResultRoot.result.cisAccountLogin;
//                ZTCustomInit.get().getmCache().getmListDetails().AccountId = getCisAccountByAppResultRoot.result.cisAccountId;
//
//                ZTCustomInit.get().getmCache().getmListDetails().OrgId = getCisAccountByAppResultRoot.result.cisDeptId;
//                ZTCustomInit.get().getmCache().getmListDetails().cisDeptType = getCisAccountByAppResultRoot.result.cisDeptType;

                RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(context);
                mRequestEntity.UserId=PreferenceUtils.getLogin_name(context);
                mRequestEntity.CVersion=getCisAccountByAppResultRoot.result.cisAccountId;
                mRequestEntity.UserName=OAConText.getInstance(context).UserName;
                mRequestEntity.IsDev="0";
                mRequestEntity.SCode="htmitech.com.s20160324184850985";
                InitDamage mInitDamage = new InitDamage(); //初始化所有信息
                mInitDamage.init(context, handler, mRequestEntity);

            }
//            HTActivityUnit.switchTo((Activity) context, mAppInfo.getmAppVersion().getPackage_name() + "." + mAppInfo.getmAppVersion().getPackage_main(), parameters);

        }
        dismissDialog();
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }


    public void showDialog() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();

        }
    }

    public void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {

            mLoadingDialog.dismiss();
        }
    }
}