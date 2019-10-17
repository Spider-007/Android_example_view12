package com.minxing.client;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.VerifyCode;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.CustomEditText;
import com.htmitech.commonx.util.UIUtil;
import com.htmitech.domain.ApcUserdefinePortal;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.service.BookService;
import com.htmitech.emportal.service.UpdateServiceHanle;
import com.htmitech.emportal.setting.AutoCompleteActivity;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginEntity;
import com.htmitech.emportal.ui.login.data.logindata.LoginEntity;
import com.htmitech.emportal.ui.login.data.logindata.LoginInfo;
import com.htmitech.emportal.ui.login.data.logindata.PNList;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.entity.RequestEntity;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.dao.ParamDao;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.AntiHijackingUtil;
import com.htmitech.proxy.util.CacheActivity;
import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.proxy.util.RootUtil;
import com.htmitech.thread.CHTTP;
import com.htmitech.ztcustom.zt.chinarailway.InitDamage;
import com.htmitech.ztcustom.zt.chinarailway.RegisteActivity;
import com.minxing.client.upgrade.EmpmAppUpgradeInfo;
import com.minxing.client.util.CacheManager;
import com.minxing.client.util.ConfigStyleUtil;
import com.minxing.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.listener.ICallLoginMXListener;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.Network;

public class LoginActivity extends RootActivity implements CallBackSuccess, ObserverCallBackType {

    private static final boolean DEBUG = true;
    private CustomEditText usernameEditView = null;
    private CustomEditText passwordEditView = null;
    private CustomEditText verificationCodeEditView = null;
    private LoadingDialog progressDialog = null;
    private Button loginBtn = null;
    private Bitmap orgBgBitmap = null;
    private Bitmap backgroundBitmap = null;
    private View contentView = null;

    private Button mLoginSetBtn = null;
    private CheckBox autoLoginCheckBox = null;
    private Button loginSettingBtn = null;
    private EmpApiLoginEntity entity = new EmpApiLoginEntity();
    private String usernameString = "";
    private String passwordString = "";
    private INetWorkManager netWorkManager;

    private String versionCode;
    private ImageButton title_left_button;
    private AppliationCenterDao appCenterDao;
    public boolean isNeedCheck = true;
    private VerifyCode verifyCode;
    private LinearLayout llVerificationCode;
    private ParamDao mParamDao;
    private List<String> mPermissionList = new ArrayList<String>();
    private String[] permissions =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };
    private Button registeButton;

    //    private TextView title_name;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        contentView = LayoutInflater.from(this).inflate(R.layout.system_login, null);
        appCenterDao = new AppliationCenterDao(this);
        try {
            appCenterDao.switchPortalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }


        CacheActivity.addActivity(this);
        setContentView(contentView);
        if (getIntent().getAction() != null
                && !"".equals(getIntent().getAction())
                && getIntent().getAction().equals("finish")) {
            ComponentInit.getInstance().getSuccess().setProgressbar("finish");
            ComponentInit.getInstance().setSuccess(null);
        } else {
            ComponentInit.getInstance().setSuccess(this);
        }
//        if (RootUtil.isRoot()) {
//            new com.htmitech.pop.AlertDialog(this).builder().setTitle("提示").setMsg("您的手机已经root不能进行登录！").setPositiveButton("确定", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    System.exit(0);
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                }
//            }).show();
//        }
        BookInit.getInstance().setmApcUserdefinePortal(null);
        BookInit.getInstance().setSdCardPath(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER);
        usernameEditView = (CustomEditText) findViewById(R.id.username);
        passwordEditView = (CustomEditText) findViewById(R.id.password);
        autoLoginCheckBox = (CheckBox) findViewById(R.id.checkSavePass);

        verificationCodeEditView = (CustomEditText) findViewById(R.id.verification_code);
        llVerificationCode = (LinearLayout) findViewById(R.id.ll_verification_code);
        verifyCode = (VerifyCode) findViewById(R.id.vc_pictrue);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registeButton = (Button) findViewById(R.id.registe_btn);
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentInit.getInstance().setSuccess(LoginActivity.this);
                login();
            }
        });
        registeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisteActivity.class));
            }
        });
        String preLoginName = PreferenceUtils.getLoginName(this);
        boolean isFocus = false;
        if (preLoginName != null && !"".equals(preLoginName)) {
            usernameEditView.setText(preLoginName);
            passwordEditView.requestFocus();
            isFocus = true;
        }
        usernameEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {//获得焦点
//
//                } else {//失去焦点
//                    usernameEditView.doCancel(true);
//                }

                usernameEditView.doCancel(!hasFocus);
            }
        });

        usernameEditView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 0) {
                    loginBtn.setEnabled(false);
                } else {
                    if (passwordEditView.getText().toString().trim().length() > 0) {
                        loginBtn.setEnabled(true);
                        usernameString = usernameEditView.getText().toString();
                        passwordString = passwordEditView.getText().toString();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (isFocus) {
            usernameEditView.doCancel(true);
        }

        passwordEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {//获得焦点
//
//                } else {//失去焦点
//                    passwordEditView.doCancel(true);
//                }

                passwordEditView.doCancel(!hasFocus);
            }
        });


        verificationCodeEditView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 4) {
                    if (!verifyCode.isEqualsIgnoreCase(value.toString().trim())) {
                        Toast.makeText(LoginActivity.this, "验证码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        verificationCodeEditView.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    return true;
                }
                return false;
            }
        });

        passwordEditView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 0) {
                    loginBtn.setEnabled(false);
                } else {
                    if (usernameEditView.getText().toString().trim().length() > 0) {
                        loginBtn.setEnabled(true);
                        usernameString = usernameEditView.getText().toString();
                        passwordString = passwordEditView.getText().toString();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditView.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

        /* 登录设置 */
        loginSettingBtn = (Button) findViewById(R.id.login_settingBtn);
        loginSettingBtn.setVisibility(View.VISIBLE);
        loginSettingBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 跳转至登陆设置页面
//                Toast.makeText(LoginActivity.this, ServerUrlConstant.SERVER_BASE_URL(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, AutoCompleteActivity.class);
                startActivity(i);
            }
        });
//        DBCipherManager.getInstance(LoginActivity.this).openDB();

        checkPermission();
//        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

//            // 检查该权限是否已经获取
//            int i = this.checkSelfPermission(permissions[0]);
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//            if (i != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                showDialogTipUserRequestPermission();
//            }
            try {
//                int REQUEST_CODE_CONTACT = 101;
////                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                //验证是否许可权限
//                for (String str : permissions) {
//                    if (ContextCompat.checkSelfPermission(this, str) != PackageManager.PERMISSION_GRANTED) {
//                        //申请权限
////                this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
////                    startRequestPermission();
//                        showDialogTipUserRequestPermission();
//                    }
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void upgradeBackUp() {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("type", "1");
        jsonParam.put("versionNo", ResourceUtil.getVerCode(this) + "");
        jsonParam.put("envType", "2");
        AnsynHttpRequest.requestByPostWithToken(this, jsonParam.toJSONString(), ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.VERSION_UPDATE, CHTTP.POSTWITHTOKEN,
                new ObserverCallBackType() {
                    @Override
                    public void success(String requestValue, int type, String requestName) {
                        if (requestValue != null && !"".equals(requestValue.trim()))
                            upgradeInfos = com.alibaba.fastjson.JSONObject.parseObject(requestValue, EmpmAppUpgradeInfo.class);
                        if (upgradeInfos != null && upgradeInfos.result != null) {
                            UpdateServiceHanle mUpdateServiceHanle = new UpdateServiceHanle(LoginActivity.this);
                            mUpdateServiceHanle.showDialog(upgradeInfos.result);
                            isNeedCheck = false;
                        }
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
                }, "upgradebackup", LogManagerEnum.APP_CENTER_DELETE.functionCode);

    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        upgradeBackUp();
        if (orgBgBitmap != null) {
            orgBgBitmap.recycle();
            orgBgBitmap = null;
        }

        if (backgroundBitmap != null) {
            backgroundBitmap.recycle();
            backgroundBitmap = null;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean safe = AntiHijackingUtil.checkActivity(LoginActivity.this);
                if (!safe) {//对防止恶意劫持代码进行判断
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new com.htmitech.pop.AlertDialog(LoginActivity.this).builder().setType().setCancelable(false).setTitle("提示").setMsg("当前界面已经不是我们的应用有潜在的危险!").setPositiveButton("退出", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.exit(0);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                }).setNegativeButton("忽略", new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        }).start();
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (orgBgBitmap != null) {
            orgBgBitmap.recycle();
        }

        if (backgroundBitmap != null) {
            backgroundBitmap.recycle();
        }
        super.onStop();
    }

    private void checkPermission() {
        mPermissionList.clear();
        /**
         * 判断哪些权限未授予
         * 以便必要的时候重新申请
         */
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);
            }
        }
        /**
         * 判断存储委授予权限的集合是否为空
         */
        if (!mPermissionList.isEmpty()) {
            showDialogTipUserRequestPermission();
        } else {//未授予的权限为空，表示都授予了

        }
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new com.htmitech.pop.AlertDialog(this).builder()
                .setTitle("存储权限不可用")
                .setMsg("当前应用需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用")
                .setPositiveButton("立即开启", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startRequestPermission();
                    }


                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    @TargetApi(Build.VERSION_CODES.M)
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    //提示用户去应用设置界面手动开启权限
    com.htmitech.pop.AlertDialog dialog;

    private void showDialogTipUserGoToAppSettting() {
        dialog = new com.htmitech.pop.AlertDialog(this).builder()
                .setTitle("存储权限不可用")
                .setMsg("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }

                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setCancelable(false);
        dialog.show();
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    private String buildConfigInfo() {
        return ConcreteLogin.getInstance().buildConfigInfo(this);
    }

    private void login() {
        if (!Network.checkNetWork(LoginActivity.this)) {
            Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (DEBUG) {
            if (usernameString != null && !"".equals(usernameString.trim()) && "getconfig".equals(usernameString.trim())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("app config");
                String message = buildConfigInfo();
                if (message != null && !"".equals(message)) {
                    builder.setMessage(message);
                }

                builder.setPositiveButton(R.string.ok, new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }

        if (usernameString.trim().length() == 0 || passwordString.trim().length() == 0) {
            return;
        }
        if (llVerificationCode.getVisibility() == View.VISIBLE) {
            if (!verifyCode.isEqualsIgnoreCase(verificationCodeEditView.getText().toString().trim())) {
                Toast.makeText(LoginActivity.this, "验证码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        progressDialog = new LoadingDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        PackageManager pm = getApplicationContext().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(LoginActivity.this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = pi.versionName;

        //启动 和完成和唤醒
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putString(PreferenceUtils.PREFERENCE_login_name, usernameString);
        securityEditor.apply();
        netWorkManager.logFunactionStart(this, LoginActivity.this, "login", LogManagerEnum.APP_MOBILE_LOGIN.functionCode);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    public EmpmAppUpgradeInfo upgradeInfos = null;

    @SuppressWarnings("unchecked")
    private void launchApp(String usernameString) {
        PreferenceUtils.saveLoginName(this, usernameString);

        boolean isApp2App = getIntent().getBooleanExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false);
        int app2appType = getIntent().getIntExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1);
        if (isApp2App && app2appType != -1) {
            switch (app2appType) {
                case AppConstants.SYSTEM_APP2APP_TYPE_SHARE_TEXT:
                    ConcreteLogin.getInstance().shareTextToChat(this, (String) CacheManager.getInstance().getHoldedShareContent());
//                    MXAPI.getInstance(this).shareTextToChat(this, (String) CacheManager.getInstance().getHoldedShareContent());
                    break;

                case AppConstants.SYSTEM_APP2APP_TYPE_SHARE_SINGLE_IMAGE:
                    ConcreteLogin.getInstance().shareImageToChat(this, (Uri) CacheManager.getInstance().getHoldedShareContent());
//                    MXAPI.getInstance(this).shareImageToChat(this, (Uri) CacheManager.getInstance().getHoldedShareContent());
                    break;

                case AppConstants.SYSTEM_APP2APP_TYPE_SHARE_MULTI_IMAGE:
                    ConcreteLogin.getInstance().shareImagesToChat(this, (List<Uri>) CacheManager.getInstance().getHoldedShareContent());
//                    MXAPI.getInstance(this).shareImagesToChat(this, (List<Uri>) CacheManager.getInstance().getHoldedShareContent());
                    break;
                case AppConstants.SYSTEM_APP2APP_TYPE_MAILTO:
                    ConcreteLogin.getInstance().shareToMail(this, (Uri) CacheManager.getInstance().getHoldedShareContent());
//                    MXAPI.getInstance(this).shareToMail(this, (Uri) CacheManager.getInstance().getHoldedShareContent());
                    break;
            }

        } else {
            Intent intent = new Intent(this, ClientTabActivity.class);
            intent.putExtra("ischeckupgrade", isNeedCheck);
            startActivity(intent);
        }
        finish();
    }

    public void onSuccess() {

        //登陆成功后逻辑
//        SharedPreferences sp = this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
        sp.edit().clear().commit();    // 清除保存门户消息数量的缓存
        //2,敏行登陆成功后，登录OA系统
        // TODO Auto-generated method stub
        //1,设置是否自动登录
        PreferenceUtils.setIsAutoLogin(LoginActivity.this, autoLoginCheckBox.isChecked());


        final String usernameString = usernameEditView.getText().toString();
        launchApp(usernameString);
    }


    @Override
    public void sysUserSuccess(boolean flag) {

        //------------update by heyang 2017-11-17 14:45:43 start---------
        // 添加else里面的内容 通过广播返回的回掉知道登录之后的文件下载是否成功，如果失败则把登录的菊花图消失
        if (flag) {
            EmpPortal mEmpPortal = appCenterDao.getPortalId();
            HomePageStyleEnum.saveHomePages(mEmpPortal.home_style);
            BookInit.getInstance().setApc_style(mEmpPortal.apc_style);
            ApcUserdefinePortal apcUserdefinePortal = new ApcUserdefinePortal();
            apcUserdefinePortal.setUsing_home_style(mEmpPortal.home_style);
            apcUserdefinePortal.setUsing_color_style(mEmpPortal.color_style);
            BookInit.getInstance().setmApcUserdefinePortal(apcUserdefinePortal);
            float textSize = 1;
            textSize = textSize + (mEmpPortal.font_style * 0.1f);
            Constant.TEXTVIEWSIXE = textSize;
            ConfigStyleUtil.changeTextSize(LoginActivity.this, new ConfigStyleUtil.FinishPortalSwitch() {
                @Override
                public void finishPortalSwitchActivity() {
                    onSuccess();
                }
            });
        } else {
            //这个地方CloudApi EMPM EMI已经登录成功，不确定是否需要把退出登录
            UIUtil.showToast(LoginActivity.this, "文件下载失败");
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        //------------update by heyang 2017-11-17 14:45:43 start--------------------
    }

    @Override
    public void setProgressbar(final String value) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && value != null)
                    progressDialog.setValue(value);
            }
        });
    }
    public Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = (Integer) msg.obj;
        }


    };
    //登入敏行
    private void LoginMX(int isEMIUSer,final ArrayList<PNList> pnLists) {
        ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();

        mConcreteLogin.setType(isEMIUSer==0?1:0);
        mConcreteLogin.setICallLoginMXListener(new ICallLoginMXListener() {
            @Override
            public void onMXSuccess() {
                RequestEntity mRequestEntity = PreferenceUtils.getRequestEntity(LoginActivity.this);
                mRequestEntity.UserId=PreferenceUtils.getLogin_name(LoginActivity.this);
                mRequestEntity.CVersion="11.0";
                mRequestEntity.UserName=PreferenceUtils.getOAUserName(LoginActivity.this);
                mRequestEntity.IsDev="0";
                mRequestEntity.SCode="htmitech.com.s20160324184850985";
                String s = JSONObject.toJSONString(mRequestEntity);
                Log.e("Json续传",s);
                InitDamage mInitDamage = new InitDamage();
                mInitDamage.init(LoginActivity.this, new Handler(), mRequestEntity);
                if (pnLists != null && pnLists.size() > 0) {
                    PNList mPNListTemp = null;
                    try {
                        /**
                         * 切换社区
                         */
                        ArrayList<Integer> netWorksIds = ConcreteLogin.getInstance().getNetworkIds(LoginActivity.this);;
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
                        mPNListTemp = pnLists.get(0);
                    }

                    BookInit.getInstance().setPortalId(mPNListTemp.getPortalId());
                    BookInit.getInstance().setPortalName(mPNListTemp.getPortalName());
                    BookInit.getInstance().setEmi_network_id(mPNListTemp.getMxNetworkId());
                    BookInit.getInstance().setMx_appid(mPNListTemp.getMxAppid());
                    BookInit.getInstance().setNetwork_name(mPNListTemp.getNetworkName());
                    BookInit.getInstance().setNetwork_code(mPNListTemp.getNetworkCode());
                }
                netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, "登录成功", INetWorkManager.State.SUCCESS);
                LogManagerEnum.APP_MOBILE_LOGIN.functionId = "";
                Intent i = new Intent(LoginActivity.this, BookService.class);
                i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
                i.putExtra("from", "login");
                startService(i);
            }

            @Override
            public void onMXFail(String message) {
                llVerificationCode.setVisibility(View.VISIBLE);
                //登陆失败逻辑
                Utils.toast(LoginActivity.this, TextUtils.isEmpty(message) ? "登录出错！" : message, Toast.LENGTH_SHORT);
                netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, message, INetWorkManager.State.FAIL);
                progressDialog.dismiss();
            }
        });
        mConcreteLogin.login(this, usernameString, passwordString);
//        //开始登陆敏行
//        MXKit.getInstance().loginMXKit(this, usernameString, passwordString, new MXKitLoginListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFail(MXError error) {
//
//            }
//
//            @Override
//            public void onSMSVerify() {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    @Override
    public void success(String requestValue, int type, String requestName) {

        if ("login".equals(requestName)) {
            SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
            SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
            securityEditor.putString(PreferenceUtils.PREFERENCE_login_name, "");
            securityEditor.apply();
//            LogManagerEnum.APP_MOBILE_LOGIN.functionId = JSONObject.parseObject(JSONObject.parseObject(requestValue).get("result").toString()).get("functionLogId").toString();
//            Log.e("QQQ",JSONObject.parseObject(JSONObject.parseObject(requestValue).get("result").toString()).get("functionLogId").toString());
            loginEmpm();
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if ("login".equals(requestName)) {
            SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
            SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
            securityEditor.putString(PreferenceUtils.PREFERENCE_login_name, "");
            securityEditor.apply();
            //修改，首先登陆EMM，登入EMPAPI，再登陆敏行。
            loginEmpm();
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public void loginEmpm() {
        LoginInfo login = new LoginInfo();
        login.loginName = usernameString;
        BookInit.getInstance().setCurrentUserName(usernameString);
        login.loginPassword = DesUtil.encode(DesUtil.KEY, passwordString);
        login.groupCorpId = OAConText.getInstance(LoginActivity.this).group_corp_id;
        BookInit.getInstance().setGroup_corp_id(login.groupCorpId);
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() +
                ServerUrlConstant.USERINFO_LOGIN_METHOD;
        AnsynHttpRequest.requestByPostWithHeader(this, login, url, CHTTP.POSTWITHHEADER, new ObserverCallBack() {
            @Override
            public void success(String requestValue) {
                LoginEntity loginResult = null;
                if (PreferenceUtils.getEMPUserID(LoginActivity.this) != null && PreferenceUtils.getLoginName(LoginActivity.this) != null && !PreferenceUtils.getLoginName(LoginActivity.this).equals(usernameEditView.getText().toString())) {
                    try {
                        appCenterDao.deleteUserProtal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BookInit.getInstance().setBoradCast(false);
                    PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
                    BookInit.getInstance().setOrgUser(null);
                    BookInit.getInstance().bitmapClean();
                } else {
                    if (DBCipherManager.isDBexists(LoginActivity.this)) {
                        PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
                    }
                }
//                SharedPreferences sp = getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
//                sp.edit().putString("password", passwordString).commit();
                SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
                SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
                securityEditor.putString("password", passwordString);
                securityEditor.apply();
                if (requestValue != null) {
                    String oaUserId = "";
                    String oaDeptName = "";
                    String oaDeptId = "";
                    loginResult = JSONObject.parseObject(requestValue, LoginEntity.class);
                    if (loginResult == null) {
                        Toast.makeText(LoginActivity.this, "登录出错，返回值为空！", Toast.LENGTH_SHORT).show();
                        netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode,
                                loginResult != null ? loginResult.getMessage() : "登录出错，返回值为空！", INetWorkManager.State.FAIL);
                        progressDialog.dismiss();
                    } else {
                        switch (loginResult.getCode()) {
                            case 200:
                                PreferenceUtils.saveAccessToken(loginResult.result.accessToken);
                                PreferenceUtils.saveRefreshToken(loginResult.result.refreshToken);


                                for (int i = 0; i < loginResult.getResult().getCisAccountList().size(); i++) {
                                    if (null != loginResult && null != loginResult.getResult() && null != loginResult.getResult().getCisAccountList()) {
                                        if (loginResult.getResult().getCisAccountList().get(i).applyType.equals("1")) {
                                            oaUserId = loginResult.getResult().getCisAccountList().get(i).cisAccountId;
                                            oaDeptName = loginResult.getResult().getCisAccountList().get(i).cisDeptName;
                                            oaDeptId = loginResult.getResult().getCisAccountList().get(i).cisDeptId;
                                        }
                                    }

                                }


                                //2,保存用户的基本信息
                                PreferenceUtils.saveUserInfo(LoginActivity.this, loginResult.getResult().getUserName(),
                                        loginResult.getResult().getUserId(),
                                        oaUserId,
                                        loginResult.getResult().getUserName(),    // OA 的username
                                        oaDeptId,
                                        oaDeptName,
                                        loginResult.getResult().getIsEMIUser(),
                                        loginResult.getResult().getGroupCorpId(),
                                        loginResult.getResult().getLoginName()
                                );
                                //登入EMPAPI
                                try {

                                    BookInit.getInstance().setCrrentUserId(loginResult.getResult().getUserId());
                                    LoginMX(loginResult.getResult().isEMIUser,loginResult.getResult().getPnList());//登入敏行
                                } catch (Exception e) {

                                }
                                break;
                            default:
                                llVerificationCode.setVisibility(View.VISIBLE);
                                if(null != loginResult && !"null".equalsIgnoreCase(loginResult.getMessage()) && !TextUtils.isEmpty(loginResult.getMessage())){
                                    Toast.makeText(LoginActivity.this, loginResult.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode,
                                        loginResult != null ? loginResult.getMessage() : "", INetWorkManager.State.FAIL);
                                progressDialog.dismiss();
                                break;
                        }
                    }


                } else {
                    llVerificationCode.setVisibility(View.VISIBLE);
                    netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode,
                            loginResult != null ? loginResult.getMessage() : "", INetWorkManager.State.FAIL);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void fail(String exceptionMessage) {
                llVerificationCode.setVisibility(View.VISIBLE);
                PreferenceUtils.setIsAutoLogin(LoginActivity.this, Boolean.FALSE);
                Toast.makeText(LoginActivity.this, TextUtils.isEmpty(exceptionMessage) ? "登录出错！" : exceptionMessage, Toast.LENGTH_SHORT).show();
                PreferenceUtils.resetUserInfo(LoginActivity.this);
                netWorkManager.logFunactionFinsh(LoginActivity.this, LoginActivity.this, "loginfinshFail", LogManagerEnum.APP_MOBILE_LOGIN.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
                progressDialog.dismiss();
            }

            @Override
            public void notNetwork() {
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }
        }, LogManagerEnum.APP_MOBILE_LOGIN.functionCode);
    }


    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
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
