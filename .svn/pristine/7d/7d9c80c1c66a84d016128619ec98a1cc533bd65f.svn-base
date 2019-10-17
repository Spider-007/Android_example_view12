package com.minxing.client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.commonx.util.UIUtil;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.service.BookService;
import com.htmitech.emportal.ui.login.data.logindata.LoginEntity;
import com.htmitech.emportal.ui.login.data.logindata.LoginInfo;
import com.htmitech.emportal.ui.login.data.logindata.PNList;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.RequestEntity;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.activity.ZWLoginActivity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.CacheActivity;
import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.CHTTP;
import com.htmitech.ztcustom.zt.chinarailway.InitDamage;
import com.minxing.client.activity.GesturePasswordActivity;
import com.minxing.client.activity.SystemNewsActivity;
import com.minxing.client.service.UpgradeService;
import com.minxing.client.service.ViewCallBack;
import com.minxing.client.util.BadgeUtil;
import com.minxing.client.util.ConfigStyleUtil;
import com.minxing.client.util.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.listener.ICallLoginMXListener;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.Network;

public class LoadingActivity extends RootActivity implements CallBackSuccess, ObserverCallBackType {

    public static final String LAUNCH_TYPE_SSO_LOGIN = "sso_login";
    public static final String SSO_LOGIN_USERNAME_KEY = "sso_username";
    public static final String SSO_LOGIN_PASSWORD_KEY = "sso_password";

    private int resultCode = RESULT_CANCELED;
    private LoadingDialog mProgressDialog = null;
    private AppliationCenterDao appCenterDao;
    private INetWorkManager netWorkManager;
    public String versionCode = "";
    private String ssoUsername = "";
    private String ssoPassword = "";
    private LoadingDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setHandleStatusColor(false);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.system_loading);
        HtmitechApplication.isAdvShow = true;
        CacheActivity.addActivity(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //2.如果没有授权，那么申请授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 321);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //2.如果没有授权，那么申请授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 321);
        }
        if (!Network.checkNetWork(LoadingActivity.this)) {
            Toast.makeText(LoadingActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
//            SharedPreferences sp = getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
            SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
            sp.edit().putString("password", "").apply();
            Intent intent = null;
            if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
            } else {
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            this.finish();
            return;
        }
        if (DBCipherManager.isDBexists(this)) {
            PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
        }
        try {
            appCenterDao = new AppliationCenterDao(this);
            appCenterDao.switchPortalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BookInit.getInstance().setmApcUserdefinePortal(null);
        ComponentInit.getInstance().setSuccess(this);
        mProgressDialog = new LoadingDialog(LoadingActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        BookInit.getInstance().setSdCardPath(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER);
        ssoUsername = getIntent().getStringExtra(SSO_LOGIN_USERNAME_KEY);
        ssoPassword = getIntent().getStringExtra(SSO_LOGIN_PASSWORD_KEY);
        progressDialog = new LoadingDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        PackageManager pm = getApplicationContext().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(LoadingActivity.this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = pi.versionName;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);

        netWorkManager.logFunactionStart(this, LoadingActivity.this, "login", LogManagerEnum.APP_MOBILE_LOGIN.functionCode);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MobclickAgent.updateOnlineConfig(LoadingActivity.this);
                boolean sdcardAvailable = Utils.sdcardAvailable();
                if (!sdcardAvailable) {
                    Utils.toast(LoadingActivity.this, R.string.init_error_no_sdcard, Toast.LENGTH_SHORT);
                    return;
                }

            }
        }, 1000);
    }

    @Override
    protected void onStart() {
        isStart = false;
        super.onStart();
    }

    private void loginMXKit() {
        boolean isSSOLogin = getIntent().getBooleanExtra(LAUNCH_TYPE_SSO_LOGIN, false);
        ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();

        if (isSSOLogin) {
            BadgeUtil.resetBadgeCount(LoadingActivity.this);
            mConcreteLogin.setICallLoginMXListener(new ICallLoginMXListener() {
                @Override
                public void onMXSuccess() {
                    new Handler(LoadingActivity.this.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(LoadingActivity.this);
                            mRequestEntity.UserId=PreferenceUtils.getLogin_name(LoadingActivity.this);
                            mRequestEntity.CVersion="11.0";
                            mRequestEntity.UserName=PreferenceUtils.getOAUserName(LoadingActivity.this);
                            mRequestEntity.IsDev="0";
                            mRequestEntity.SCode="htmitech.com.s20160324184850985";
                            InitDamage mInitDamage = new InitDamage();
                            mInitDamage.init(LoadingActivity.this, mHandler, mRequestEntity);
                            loginMXKit();
                            Log.e("nihaoa","123");
                        }
                    });
                    // 登陆成功后逻辑
                    resultCode = RESULT_OK;
                    setResult(resultCode);
                    netWorkManager.logFunactionFinsh(LoadingActivity.this, LoadingActivity.this, "loginfinshSuccess", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, loginResult.message, INetWorkManager.State.SUCCESS);
                    finish();
                }

                @Override
                public void onMXFail(String message) {
                    // 登陆失败逻辑
                    if (!TextUtils.isEmpty(message)) {
                        Utils.toast(LoadingActivity.this, message, Toast.LENGTH_SHORT);
                    }
                    Intent intent = null;
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    netWorkManager.logFunactionFinsh(LoadingActivity.this, LoadingActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, loginResult.message, INetWorkManager.State.FAIL);
                    finish();
                }
            });
//            }
        } else {
            try {

                mConcreteLogin.setICallLoginMXListener(new ICallLoginMXListener() {
                    @Override
                    public void onMXSuccess() {
                        Intent i = new Intent(LoadingActivity.this, BookService.class);
                        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
                        i.putExtra("from", "login");
                        startService(i);
                        String clientId = ResourceUtil.getConfString(LoadingActivity.this, "client_app_client_id");
                        new UpgradeService().checkUpgrade(LoadingActivity.this, new AppliationCenterDao(LoadingActivity.this).getAppClientEnvtype(ResourceUtil.getVerCode(LoadingActivity.this)) + "", ResourceUtil.getVerCode(LoadingActivity.this), true, new ViewCallBack(
                                LoadingActivity.this) {
                        });
                        netWorkManager.logFunactionFinsh(LoadingActivity.this, LoadingActivity.this, "loginfinshSuccess", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, loginResult.message, INetWorkManager.State.SUCCESS);

                    }

                    @Override
                    public void onMXFail(String message) {
                        Intent intent = null;
                        if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                            intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
                        } else {
                            intent = new Intent(LoadingActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                        BadgeUtil.resetBadgeCount(LoadingActivity.this);
                        netWorkManager.logFunactionFinsh(LoadingActivity.this, LoadingActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, loginResult.message, INetWorkManager.State.FAIL);
                        finish();

                    }
                });
            } catch (Exception e) {
                Intent intent = null;
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                BadgeUtil.resetBadgeCount(LoadingActivity.this);
                finish();
            }

        }
        mConcreteLogin.loading(this, ssoUsername, ssoPassword, isSSOLogin);
    }

    private void launchApp() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
//        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (!TextUtils.isEmpty(PreferenceUtils.getLoginName(LoadingActivity.this))) {
            Intent intent = null;
            if (PreferenceUtils.isGesturePwdEnable(this, PreferenceUtils.getLoginName(LoadingActivity.this))
                    && PreferenceUtils.isInitGesturePwd(this, PreferenceUtils.getLoginName(LoadingActivity.this))) {
                intent = new Intent(this, GesturePasswordActivity.class);
                intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_FORCE);
            } else if (PreferenceUtils.isInitZWGesturePwd(this, PreferenceUtils.getLoginName(LoadingActivity.this))) {
                intent = new Intent(this, ZWLoginActivity.class);
                intent.putExtra("login", true);
                intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY,
                        GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            } else {
                RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(LoadingActivity.this);
                mRequestEntity.UserId=PreferenceUtils.getLogin_name(LoadingActivity.this);
                mRequestEntity.CVersion="11.0";
                mRequestEntity.UserName=PreferenceUtils.getOAUserName(LoadingActivity.this);
                mRequestEntity.IsDev="0";
                mRequestEntity.SCode="htmitech.com.s20160324184850985";
                InitDamage mInitDamage = new InitDamage();
                mInitDamage.init(LoadingActivity.this, mHandler, mRequestEntity);

            }


        }
    }

    @Override
    public void sysUserSuccess(boolean flag) {
        if (flag) {
            try {
                if(TextUtils.isEmpty(BookInit.getInstance().getPortalId())){
                    return;
                }
                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
                EmpPortal mEmpPortal = mAppliationCenterDao.getPortalId();
                BookInit.getInstance().setApc_style(mEmpPortal.apc_style);
                HomePageStyleEnum.saveHomePages(mEmpPortal.home_style);
                float textSize = 1;
                textSize = textSize + (mEmpPortal.font_style * 0.1f);
                Constant.TEXTVIEWSIXE = textSize;

                ConfigStyleUtil.changeTextSize(LoadingActivity.this, new ConfigStyleUtil.FinishPortalSwitch() {
                    @Override
                    public void finishPortalSwitchActivity() {

                    }
                });
                launchApp();
            } catch (Exception e) {
                Log.d("LoadingActivity", "社区不存在");
            }
        } else {
            UIUtil.showToast(LoadingActivity.this, "文件下载失败");
            Intent intent = null;
            if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
            } else {
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            BadgeUtil.resetBadgeCount(LoadingActivity.this);
            finish();
            progressDialog.dismiss();
        }

    }

    @Override
    public void setProgressbar(final String value) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(value)) {
                    if (value.equals("finish")) {
                        LoadingActivity.this.finish();
                    }
                }
                mProgressDialog.setValue(value);
            }
        });
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if ("login".equals(requestName)) {
            //修改，首先登陆EMM，登入EMPAPI，再登陆敏行。
            loginEmpm();
        }
    }

    /*
    * 新登录接口
    * */
    LoginEntity loginResult = new LoginEntity();

    public void loginEmpm() {
        LoginInfo login = new LoginInfo();
        login.loginName = PreferenceUtils.getLoginName(LoadingActivity.this);
        BookInit.getInstance().setCurrentUserName(login.loginName);
//        SharedPreferences sp = getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
//        SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sp.getString("password", ""))) {
            login.loginPassword = DesUtil.encode(DesUtil.KEY, sp.getString("password", ""));
        }
        login.groupCorpId = OAConText.getInstance(LoadingActivity.this).group_corp_id;
        BookInit.getInstance().setGroup_corp_id(login.groupCorpId);
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
                ServerUrlConstant.USERINFO_LOGIN_METHOD;
        AnsynHttpRequest.requestByPostWithHeader(this, login, url, CHTTP.POSTWITHHEADER, new ObserverCallBack() {
            @Override
            public void success(String requestValue) {
                if (requestValue != null) {
                    loginResult = JSONObject.parseObject(requestValue, LoginEntity.class);
                    if (null != loginResult) {
                        if (null != loginResult.getResult()) {
                            PreferenceUtils.saveAccessToken(loginResult.result.accessToken);
                            PreferenceUtils.saveRefreshToken(loginResult.result.refreshToken);
                            ArrayList<PNList> pnLists = loginResult.getResult().getPnList();
                            PNList mPNListTemp = null;
                            try {
                                /**
                                 * 切换社区
                                 */
                                ArrayList<Integer> netWorksIds = new ArrayList<Integer>();
                                for (PNList mPNList : pnLists) {
                                    for (int i = 0; i < netWorksIds.size(); i++) {
                                        if ((netWorksIds.get(i) + "").equals(mPNList.getMxAppid())) {
                                            mPNListTemp = mPNList;
                                            break;
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (mPNListTemp == null) {
                                if (loginResult.getResult().getPnList() != null && loginResult.getResult().getPnList().size() > 0) {
                                    mPNListTemp = loginResult.getResult().getPnList().get(0);
                                }
                            }

                            if (loginResult.getResult().getPnList() != null && loginResult.getResult().getPnList().size() > 0) {
                                BookInit.getInstance().setCrrentUserId(loginResult.getResult().getUserId());
                                BookInit.getInstance().setPortalId(mPNListTemp.getPortalId());
                                BookInit.getInstance().setPortalName(mPNListTemp.getPortalName());
                                BookInit.getInstance().setEmi_network_id(mPNListTemp.getMxNetworkId());
                                BookInit.getInstance().setMx_appid(mPNListTemp.getMxAppid());
                                BookInit.getInstance().setNetwork_name(mPNListTemp.getNetworkName());
                                BookInit.getInstance().setNetwork_code(mPNListTemp.getNetworkCode());
                            }
                            //登入EMPAPI
                            try {
                                LoginMX();//登入敏行
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Intent intent = null;
                            if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                                intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
                            } else {
                                intent = new Intent(LoadingActivity.this, LoginActivity.class);
                            }
                            startActivity(intent);
                            BadgeUtil.resetBadgeCount(LoadingActivity.this);
                            finish();
                            progressDialog.dismiss();
                        }
                    } else {
                        Intent intent = null;
                        if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                            intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
                        } else {
                            intent = new Intent(LoadingActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                        BadgeUtil.resetBadgeCount(LoadingActivity.this);
                        finish();
                        progressDialog.dismiss();
                    }
                } else {
                    Intent intent = null;
                    if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                        intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
                    } else {
                        intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    }
                    startActivity(intent);
                    BadgeUtil.resetBadgeCount(LoadingActivity.this);
                    finish();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void fail(String exceptionMessage) {
                PreferenceUtils.setIsAutoLogin(LoadingActivity.this, Boolean.FALSE);
//                Toast.makeText(LoadingActivity.this, exceptionMessage, Toast.LENGTH_SHORT).show();
                PreferenceUtils.resetUserInfo(LoadingActivity.this);
                netWorkManager.logFunactionFinsh(LoadingActivity.this, LoadingActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
                progressDialog.dismiss();

                Intent intent = null;
                if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                    intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
                } else {
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                BadgeUtil.resetBadgeCount(LoadingActivity.this);
                finish();
            }

            @Override
            public void notNetwork() {
                progressDialog.dismiss();
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }
        }, LogManagerEnum.APP_MOBILE_LOGIN.functionCode);
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if ("login".equals(requestName)) {//只有登陆失败 才会退出  日志失败不进行退出
            Intent intent = null;
            if (PreferenceUtils.isAPPFTT(LoadingActivity.this)) {
                intent = new Intent(LoadingActivity.this, SystemNewsActivity.class);
            } else {
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            BadgeUtil.resetBadgeCount(LoadingActivity.this);
            finish();
        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
    public Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = (Integer) msg.obj;
            Intent intent = new Intent(LoadingActivity.this, ClientTabActivity.class);
            startActivity(intent);

            finish();
        }


    };
    public void LoginMX() {
        ConcreteLogin concreteLogin = ConcreteLogin.getInstance();
        concreteLogin.setICallLoginMXListener(new ICallLoginMXListener() {
            @Override
            public void onMXSuccess() {
                new Handler(LoadingActivity.this.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        loginMXKit();
                    }
                });
            }

            @Override
            public void onMXFail(String message) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        mProgressDialog.show();
                    }
                });
            }
        });
        concreteLogin.prepareResource(LoadingActivity.this, PreferenceUtils.isAPPFTT(getApplicationContext()));
//        MXKit.getInstance().prepareResource(LoadingActivity.this, PreferenceUtils.isAPPFTT(getApplicationContext()),
//                new MXKit.MXKitPrepareResourceListener() {
//
//                    @Override
//                    public void onComplete() {
//                        new Handler(LoadingActivity.this.getMainLooper()).post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                loginMXKit();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onProcessing(final String message) {
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//
//                                mProgressDialog.show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFail() {
//                        new Handler(LoadingActivity.this.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                loginMXKit();
//                            }
//                        });
//                        // 拷贝资源失败
//                    }
//                });
    }

    //提示用户去应用设置界面手动开启权限
    AlertDialog dialog;

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    private void showDialogTipUserGoToAppSettting() {
        Log.e("LOADING", "dialog");
        dialog = new AlertDialog(this).builder()
                .setTitle("存储权限不可用")
                .setMsg("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }

                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setCancelable(false);
        dialog.show();
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e("LOADING", grantResults.length+"");
                Log.e("LOADING", permissions.length+"");
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    Log.e("LOADING", b + "");
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
