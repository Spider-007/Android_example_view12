package com.htmitech.proxy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.doman.DeviceApplyInfo;
import com.htmitech.proxy.doman.DeviceAuditRequest;
import com.htmitech.proxy.doman.DeviceSafeConfigResult;
import com.htmitech.proxy.doman.DeviceUserListResultItem;
import com.htmitech.proxy.doman.DeviceVertifyInfo;
import com.htmitech.proxy.myenum.DeviceStateEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.ZTActivityUnit;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 设备绑定登录验证界面
 * @author joe
 * @date 2017-8-3 11:01:47
 */

public class DeviceVerifyPasswordActivity extends BaseFragmentActivity implements View.OnClickListener ,ObserverCallBackType {

    private static final String TAG = "DeviceVerifyPasswordAct";

    private DeviceUserListResultItem deviceUserListResultItem;
    private DeviceSafeConfigResult deviceSafeConfig;
    private int bindNum;

    private TextView titleName;
    private ImageButton titleLeftButton;
    private TextView deviceDescription;
    private EditText devicePassword;
    private Button deviceEnsure;

    private static final String HTTPVERIFY = "getverify";
    private static final String HTTPAUDIT = "getaudit";
    private static final String HTTPOPERT = "getopert";
    public String opertUrl = "";
    private  String verifyUrl ="";
    private  String auditUrl ="";
    private Gson mGson = new Gson();
    DeviceAuditRequest mDeviceAuditRequest;
    private String app_id = "";
    private DeviceUserListResultItem mDeviceUserListRequest;
    private String typeName;
    private String deviceName;
    private EmptyLayout mEmptyLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_verify_password);
        intView();
        initData();

    }

    private void intView() {
        titleName = (TextView) findViewById(R.id.title_name);
        titleLeftButton = (ImageButton) findViewById(R.id.title_left_button);
        deviceDescription = (TextView) findViewById(R.id.tv_device_description);
        devicePassword = (EditText) findViewById(R.id.et_device_password);
        deviceEnsure = (Button) findViewById(R.id.btn_device_ensure);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);
        titleLeftButton.setOnClickListener(this);
        deviceEnsure.setOnClickListener(this);
        devicePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s!=null&&s.length()>0){
                   deviceEnsure.setEnabled(true);
               }else {
                   deviceEnsure.setEnabled(false);
               }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        deviceSafeConfig = (DeviceSafeConfigResult)intent.getSerializableExtra("deviceSafeConfig");
        bindNum = intent.getIntExtra("bindNum",-1);
        deviceUserListResultItem = (DeviceUserListResultItem) intent.getSerializableExtra("deviceUserListResultItem");
        typeName =(DeviceStateEnum.getTypeName(Integer.parseInt(deviceUserListResultItem.applyType)).equals("未绑定")?"解绑":DeviceStateEnum.getTypeName(Integer.parseInt(deviceUserListResultItem.applyType)));
        deviceName = (deviceUserListResultItem.deviceName==null||deviceUserListResultItem.deviceName.trim().equals(""))?"":"【"+deviceUserListResultItem.deviceName+"】";
        titleName.setText("设备"+typeName );
        deviceDescription.setText("     您正在申请为当前的账号"+typeName+
                "设备"+deviceName+"，该操作需要验证您的身份。");
        if(deviceUserListResultItem.needAudit.equals("1")){
            deviceEnsure.setText("验证并申请"+typeName);
        }else {
            deviceEnsure.setText("验证并确认"+typeName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(devicePassword.getText()!=null&&!devicePassword.getText().toString().trim().equals("")){
            devicePassword.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_name:
                break;
            case R.id.title_left_button:
                DeviceVerifyPasswordActivity.this.finish();
                break;
            case R.id.btn_device_ensure:
                if(devicePassword.getText().toString().trim().equals("")){
                    Toast.makeText(DeviceVerifyPasswordActivity.this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String salt = "ht1234";
                String passWord = MD5Util.getMD5String(devicePassword.getText().toString().trim() + salt);
                verifyUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GetDeviceVertifyPassword+
                        "/"+ OAConText.getInstance(HtmitechApplication.instance()).login_name+"/"+passWord;
                AnsynHttpRequest.requestByPostWithToken(this, null, verifyUrl, CHTTP.POSTWITHTOKEN, this, HTTPVERIFY, LogManagerEnum.GGENERAL.getFunctionCode());
                break;
        }

    }

    public void auditFromServer(DeviceUserListResultItem mDeviceUserListResultItem){

        mDeviceAuditRequest = new DeviceAuditRequest();
        mDeviceAuditRequest.deviceSn = mDeviceUserListResultItem.deviceSn;
        mDeviceAuditRequest.deviceId = mDeviceUserListResultItem.deviceId;
        mDeviceAuditRequest.applySource = 1;
        mDeviceAuditRequest.verifyMethod = 1;
        mDeviceAuditRequest.needAudit = 1;
        mDeviceAuditRequest.applyType = Integer.parseInt(mDeviceUserListResultItem.applyType);
        mDeviceAuditRequest.groupCorpId = OAConText.getInstance(this).group_corp_id;
        mDeviceAuditRequest.userId = OAConText.getInstance(this).UserID;
        auditUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GetDeviceSaveAudit;
        AnsynHttpRequest.requestByPostWithToken(this, mDeviceAuditRequest, auditUrl, CHTTP.POSTWITHTOKEN
                , this, HTTPAUDIT,  LogManagerEnum.GGENERAL.getFunctionCode());
    }
    private  void toAudit(DeviceUserListResultItem mDeviceUserListResultItem){
        Map<String, Object> params = new HashMap<String, Object>();
        mDeviceUserListResultItem.needAudit = "1";//需要审批
        params.put("deviceUserListResultItem",mDeviceUserListResultItem);
        params.put("app_id",app_id);
        ZTActivityUnit.switchTo(this, DeviceAuditPhotoActivity.class, params);
        this.finish();
    }

    public void updateDeviceState(DeviceUserListResultItem mDeviceUserListResultItem) {
        opertUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATE_BIND_STATUS;
        mDeviceUserListRequest = new DeviceUserListResultItem();
        mDeviceUserListRequest.userId = OAConText.getInstance(this).UserID;
        mDeviceUserListRequest.groupCorpId = OAConText.getInstance(this).group_corp_id;
        mDeviceUserListRequest.deviceId = mDeviceUserListResultItem.deviceId;
        mDeviceUserListRequest.loginName = OAConText.getInstance(this).login_name;
        mDeviceUserListRequest.deviceSn = mDeviceUserListResultItem.deviceSn;
        mDeviceUserListRequest.applySource = "1";
        mDeviceUserListRequest.applyType = mDeviceUserListResultItem.applyType;
        mDeviceUserListRequest.verifyMethod = "1";
        mDeviceUserListRequest.needAudit = "0";
        mDeviceUserListRequest.statusFlag = mDeviceUserListResultItem.statusFlag;
        mDeviceUserListRequest.bindStatus = mDeviceUserListResultItem.bindStatus;
        AnsynHttpRequest.requestByPostWithToken(this, mDeviceUserListRequest, opertUrl, CHTTP.POSTWITHTOKEN
                , this, HTTPOPERT, LogManagerEnum.GGENERAL.getFunctionCode());
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if(requestName.equals(HTTPVERIFY)){
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, verifyUrl, null, this, HTTPVERIFY, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                DeviceVertifyInfo mDeviceVertifyInfo = mGson.fromJson(requestValue.toString(), DeviceVertifyInfo.class);
                if(mDeviceVertifyInfo.getCode()==200){
                    if(deviceUserListResultItem.needAudit.equals("1")){
                        if(deviceUserListResultItem.applyType=="1"&&(bindNum == 0 && deviceSafeConfig.skipAuditList.contains("skip_first_binding"))){
                            updateDeviceState(deviceUserListResultItem);
                        }else {
                            auditFromServer(deviceUserListResultItem);
                        }
                    }else {
                        Toast.makeText(DeviceVerifyPasswordActivity.this, mDeviceVertifyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        updateDeviceState(deviceUserListResultItem);

                    }
                }else {
                    Toast.makeText(DeviceVerifyPasswordActivity.this, mDeviceVertifyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "success1: " + requestValue);
            } else {
                Log.e(TAG, "success2: " + requestValue);
                return;
            }
        }else if (requestName.equals(HTTPAUDIT)){
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, auditUrl, mDeviceUserListRequest, this, HTTPOPERT, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                    DeviceApplyInfo mDeviceApplyInfo = mGson.fromJson(requestValue.toString(), DeviceApplyInfo.class);
                if(mDeviceApplyInfo.getCode()==200){
                    deviceUserListResultItem.applyId = mDeviceApplyInfo.result;
                    toAudit(deviceUserListResultItem);
                }else {
                    Toast.makeText(DeviceVerifyPasswordActivity.this, mDeviceApplyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Log.e(TAG, "success1: " + requestValue);
            } else {
                Log.e(TAG, "success2: " + requestValue);
                return;
            }
        }else if(requestName.equals(HTTPOPERT)){
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, opertUrl, mDeviceAuditRequest, this, HTTPAUDIT, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                DeviceApplyInfo mDeviceApplyInfo = mGson.fromJson(requestValue.toString(), DeviceApplyInfo.class);
                if(mDeviceApplyInfo.getCode()==200){
                    finish();
                }else {
                    Toast.makeText(DeviceVerifyPasswordActivity.this, mDeviceApplyInfo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.e(TAG, "success1: " + requestValue);
            } else {
                Log.e(TAG, "success2: " + requestValue);
                return;
            }

        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if(requestName.equals(HTTPVERIFY)){
            Log.e(TAG, "fail: "+ exceptionMessage);
            Toast.makeText(DeviceVerifyPasswordActivity.this,"验证失败"+exceptionMessage, Toast.LENGTH_SHORT).show();
        }else if(requestName.equals(HTTPAUDIT)){
            Toast.makeText(DeviceVerifyPasswordActivity.this,"审核申请失败"+exceptionMessage, Toast.LENGTH_SHORT).show();
        }else if(requestName.equals(HTTPOPERT)){
            Toast.makeText(DeviceVerifyPasswordActivity.this,"绑定操作失败"+exceptionMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
