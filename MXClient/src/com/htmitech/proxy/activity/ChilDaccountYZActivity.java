package com.htmitech.proxy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.receive.RegistOrUnRegisterReceive;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.doman.ActivateInfo;
import com.htmitech.proxy.doman.Activateparameter;
import com.htmitech.proxy.doman.EmpCisInfo;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 子账号激活登录页面
 *
 * @author joe
 * @Date 2017-06-05
 */
public class ChilDaccountYZActivity extends cn.feng.skin.manager.base.BaseActivity implements OnClickListener, ObserverCallBackType {
    private static final String MHTTPTYPE = "activate";
    private static final String MHTTPTYPE_LOG = "activate_log";
    private static final String MHTTPTYPE_LOG_FINISH = "activate_log_finish";
    private INetWorkManager netWorkManager;

    private TextView tv_fn5_title_name;
    private String titleName;
    private ImageView ibn_fn5_back;
    private Button login_btn;
    private EditText username, password;
    private EmpCisInfo mEmpCisInfo;
    private Activateparameter mActivateparameter;
    private Gson mGson = new Gson();
    private String jsonToString;
    private String getActivateUrl;
    private ActivateInfo mActivateInfo;
    private Long Cis_id;
    private String app_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_childaccount_wjh);
        Intent intent = getIntent();
        titleName = intent.getStringExtra("titleName");
        Cis_id = intent.getLongExtra("Cis_id", 0);
        app_id = intent.getStringExtra("app_id");
        initView();
        initData();
    }

    public void initView() {
        tv_fn5_title_name = (TextView) findViewById(R.id.tv_fn5_title_name);
        ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
        login_btn = (Button) findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    public void initData() {

        if (titleName != null && !titleName.trim().equals(""))
            tv_fn5_title_name.setText("" + titleName);
        login_btn.setOnClickListener(this);
        ibn_fn5_back.setOnClickListener(this);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 0) {
                    login_btn.setEnabled(false);
                } else {
                    if (username.getText().toString().trim().length() > 0) {
                        login_btn.setEnabled(true);
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

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 0) {
                    login_btn.setEnabled(false);
                } else {
                    if (password.getText().toString().trim().length() > 0) {
                        login_btn.setEnabled(true);
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

        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.ibn_fn5_back:
                finish();
                break;
            case R.id.login_btn:
                getActivateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerActivate;
                mActivateparameter = new Activateparameter();
                mActivateparameter.setUser_id(OAConText.getInstance(ChilDaccountYZActivity.this).UserID);
                mActivateparameter.setCis_id(Cis_id + "");
                mActivateparameter.setApp_id(app_id);
                mActivateparameter.setLogin_name(username.getText().toString());
                mActivateparameter.setPassword(DesUtil.encode(DesUtil.KEY, password.getText().toString()));
                jsonToString = mGson.toJson(mActivateparameter);
                try {
                    netWorkManager.logFunactionStart(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                AnsynHttpRequest.requestByPostWithToken(this, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.functionCode);
                break;
        }
    }


    @Override
    public void success(String requestValue, int type, String requestName) {

        if (requestName.equals(MHTTPTYPE)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getActivateUrl, jsonToString, this, requestName, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                if (requestName.equals(MHTTPTYPE)) {
                    if (requestValue != null)
                        mActivateInfo = mGson.fromJson(requestValue.toString(), ActivateInfo.class);
                    int result = mActivateInfo.getResult();
                    switch (result) {
                        case 0:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.SUCCESS);
//                        Toast.makeText(ChilDaccountYZActivity.this, "激活成功", Toast.LENGTH_SHORT).show();
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("激活成功！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case 1:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("用户被禁用，不能激活，请联系管理员！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case 9:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("调用接口返回信息失败！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case -10:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("字段校验失败！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case -9:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("没有对应的应用信息！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case -8:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("没有对应的目标系统，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case -7:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("没有对应的目标系统接口，无法激活！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                        case -1:
                            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
                            new AlertDialog(ChilDaccountYZActivity.this).builder().setTitle("目标系统").setMsg("对应目标系统账号未激活！").setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                            break;
                    }
                    finish();

                }

            }

        } else if (requestName.equals(MHTTPTYPE_LOG)) {
            AnsynHttpRequest.requestByPostWithToken(ChilDaccountYZActivity.this, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, ChilDaccountYZActivity.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
        } else if (requestName.equals("login_home_back")) {
            Log.d("DetailActivity", "唤醒成功");
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals(MHTTPTYPE)) {
            netWorkManager.logFunactionFinsh(ChilDaccountYZActivity.this, ChilDaccountYZActivity.this, MHTTPTYPE_LOG_FINISH, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode(), mActivateInfo.getMessage(), INetWorkManager.State.FAIL);
        } else if (requestName.equals(MHTTPTYPE_LOG)) {
            AnsynHttpRequest.requestByPostWithToken(ChilDaccountYZActivity.this, jsonToString, getActivateUrl, CHTTP.POSTWITHTOKEN, ChilDaccountYZActivity.this, MHTTPTYPE, LogManagerEnum.OBJECTIVESYS_ACTIVATE.getFunctionCode());
        } else if (requestName.equals("login_home_back")) {
            Log.d("DetailActivtiy", "唤醒失败");
        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (HtmitechApplication.isHomeBack) {
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            netWorkManager.logFunactionOnce(this, this, "login_home_back", LogManagerEnum.APP_RESUME.getFunctionCode());
            HtmitechApplication.isHomeBack = false;
        }
        //注册监听按home键的广播
        RegistOrUnRegisterReceive.registerHomeKeyEventReceive(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        RegistOrUnRegisterReceive.unRegisterHomeKeyEventReceive(this);
    }
}
