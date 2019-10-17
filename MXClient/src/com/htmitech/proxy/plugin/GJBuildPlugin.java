package com.htmitech.proxy.plugin;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.pop.AlertDialog;
import com.htmitech.pop.LoginAlertDialog;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.ActivateInfo;
import com.htmitech.proxy.doman.Activateparameter;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.IsActivateStateInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 进入构件 比如 工作流构件，通讯录构件  等
 */
public class GJBuildPlugin implements ApplicationObserver, ObserverCallBackType {
    private static final String TAG = "GJBuildPlugin";
    private String jsonToString;
    private Gson mGson = new Gson();
    private String getIsActivateStateUrl;
    private final String HTTPTYPE = "isActivity";
    private ActivateInfo mActivateInfo;
    private ApplicationAllEnum mApplicationAllEnum;
    private Map<String, Object> parameters;
    private String app_id;
    private Context context;
    //新增 子帐号激活dialog 2017-06-13 11:30:40
    private LoginAlertDialog loginAlertDialog;
    private String app_name;
    private String getActivateUrl;
    private Activateparameter mActivateparameter;
    private static final String MHTTPTYPE = "activate";
    private static final String MHTTPTYPE_LOG = "activate_log";
    private static final String MHTTPTYPE_LOG_FINISH = "activate_log_finish";

    LoadingDialog mLoadingDialog;
    private INetWorkManager netWorkManager;

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        this.context = context;
        this.parameters = parameters;
        this.parameters.put("app_version_id", mAppInfo.getmAppVersion().getApp_version_id() + "");
        mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mAppInfo);
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        if (mApplicationAllEnum == null) {
            Log.d("GJBuildPlugin", mAppInfo.getComp_code() + " 在ApplicationAllEnum中不存在");
            new AlertDialog(context).builder().setTitle("应用打开失败").setMsg(mAppInfo.getComp_code() + "配置有误！").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }
        app_id = mAppInfo.getApp_id() + "";
        app_name = mAppInfo.getApp_name();
        mApplicationAllEnum.appId = mAppInfo.getApp_id() + "";
        mApplicationAllEnum.compCode = mAppInfo.getComp_code();
        mApplicationAllEnum.name = mAppInfo.getApp_name();
        mApplicationAllEnum.url = mAppInfo.getPicture_normal();
        mApplicationAllEnum.url_disabled = mAppInfo.getPicture_normal();//2017-10-10 18:33:51 更改成normal
//        mApplicationAllEnum.url_disabled = mAppInfo.getPicture_disabled();
        mApplicationAllEnum.tab_item_id = mAppInfo.getTab_item_id();
//        mApplicationAllEnum.homeStyle = mAppInfo.getHomeStyle();
        mApplicationAllEnum.appType = mAppInfo.getApp_type();
        mApplicationAllEnum.mAppInfo = mAppInfo;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);

        //判断该构件的目标系统是否被激活
        getIsActivateStateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerIsActivateState;
        IsActivateStateInfo mIsActivateStateInfo = new IsActivateStateInfo();
        //    <--------------------Administrator -> 2019-8-13:18:33:设置新接口的userID使用新的接口--------------------->
        mIsActivateStateInfo.setUser_id(OAConText.getInstance(context).UserID);
        //    <--------------------Administrator -> 2019-8-13:18:33:设置新接口的appID使用新的接口--------------------->
        mIsActivateStateInfo.setApp_id(mAppInfo.getApp_id() + "");
        jsonToString = mGson.toJson(mIsActivateStateInfo);
        showDialog();
        AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getIsActivateStateUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.GGENERAL.getFunctionCode());


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
                        Log.d(TAG, "success: 对应目标系统账号已停用");
                        new AlertDialog(context).builder().setTitle("应用打开失败").setMsg("对应目标系统账号已停用！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case 1:
                        Log.d(TAG, "success: 对应目标系统账号已激活");
                        try {
                            if (mApplicationAllEnum == ApplicationAllEnum.E_MAIL) {
                                Class cls = Class.forName("com.htmitech.proxy.plugin.EmailPlugin"); //指定路径，服务器配置的路径.
                                ApplicationObserver mApplicationObserver = (ApplicationObserver) cls.newInstance();

                                mApplicationObserver.excetStart(context, mApplicationAllEnum.mAppInfo, parameters);
                            } else {
                                ClentAppUnit.getInstance(context).setActivitys(mApplicationAllEnum, parameters);
                            }

                        } catch (NotApplicationException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
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
                                    getActivateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerActivate;
                                    mActivateparameter = new Activateparameter();
                                    mActivateparameter.setUser_id(OAConText.getInstance(context).UserID);
                                    mActivateparameter.setCis_id("");
                                    mActivateparameter.setApp_id(app_id);
                                    mActivateparameter.setLogin_name(user_name);
                                    mActivateparameter.setPassword(DesUtil.encode(DesUtil.KEY, pass_word));
                                    jsonToString = mGson.toJson(mActivateparameter);
                                    try {
                                        netWorkManager.logFunactionStart(context, GJBuildPlugin.this, MHTTPTYPE_LOG, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, GJBuildPlugin.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.functionCode);

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
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, getActivateUrl, jsonToString, this, requestName, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
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
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.SUCCESS);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("激活成功！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case 1:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("用户被禁用，不能激活，请联系管理员！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                        break;
                    case 9:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("调用接口返回信息失败！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -10:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("字段校验失败！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -9:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的应用信息！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -8:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的目标系统，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -7:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("没有对应的目标系统接口，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                    case -1:
                        netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                        new AlertDialog(context).builder().setTitle("目标系统").setMsg("对应目标系统账号未激活！").setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                        break;
                }

            }


        } else if (requestName.equals(MHTTPTYPE_LOG)) {
            AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, GJBuildPlugin.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
        }
        dismissDialog();

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        dismissDialog();
        if (requestName.equals(MHTTPTYPE_LOG)) {
            AnsynHttpRequest.requestByPostWithToken(context, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, GJBuildPlugin.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
        } else if (requestName.equals(MHTTPTYPE)) {
            netWorkManager.logFunactionFinsh(context, GJBuildPlugin.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);

        }

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
