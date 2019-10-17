package com.htmitech.htworkflowformpluginnew.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.htworkflowformpluginnew.entity.OpintionDelectOrEditResult;
import com.htmitech.htworkflowformpluginnew.entity.OptionsParametersEdit;
import com.htmitech.htworkflowformpluginnew.entity.UserOption;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.client.util.FastJsonUtils;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class OptionEditActivity extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback, ObserverCallBackType {

    private TextView btnOptionSave;
    private EditText etOptions;
    private String id;
    private String value;
    private boolean isEdit = false;
    private String oldOptions;
    String app_id;
    public String EDITOPTIONPATH = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USERINFO_EditUSEROPTIONS_METHOD_JAVA;
    public static final String EDIT_OPTION = "editOption";
    private INetWorkManager netWorkManager;
    private OptionsParametersEdit mOptionsParameters;

    protected int getLayoutById() {
        return R.layout.activity_optionnew;
    }

    protected void initView() {

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.textview_titlebar_title)).setText("编辑常用意见");
        btnOptionSave = (TextView) findViewById(R.id.btn_option_save);
        etOptions = (EditText) findViewById(R.id.et_Options);
        btnOptionSave.setOnClickListener(this);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        app_id = getIntent().getStringExtra("app_id");
        String[] mItemArray = org.apache.commons.lang3.StringUtils.split(data, "|");
        id = mItemArray[0];
        value = mItemArray[1];
        etOptions.setText(value);
        oldOptions = value;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option_save:
                if (etOptions.getText().toString().trim().equals("")) {
                    Toast.makeText(OptionEditActivity.this, "您输入的内容为空", Toast.LENGTH_SHORT).show();
                } else if (etOptions.getText().toString().equals(oldOptions)) {
                    Toast.makeText(OptionEditActivity.this, "您的意见尚未修改，请编辑您的意见", Toast.LENGTH_SHORT).show();
                } else {
                    // 修改意见调用
                    mOptionsParameters = new OptionsParametersEdit();
                    mOptionsParameters.appId = app_id;
                    UserOption userOption = new UserOption();
                    userOption.opinionId = id;
                    mOptionsParameters.userId = OAConText.getInstance(HtmitechApplication.getApplication()).UserID;
                    userOption.opinionText = etOptions.getText().toString();
                    mOptionsParameters.option = userOption;
//                    AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(OptionEditActivity.this);
//                    addUserOptionsModel.getDataFromServerByType(AddUserOptionsModel.TYPE_GET_EDIT_OPTIONS, mOptionsParameters);
                    netWorkManager.logFunactionStart(OptionEditActivity.this, OptionEditActivity.this, LogManagerEnum.EDITUSEROPINTION);
                    showDialog();
                }
                break;
            case R.id.imgview_titlebar_back:
                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
//        if (requestTypeId == AddUserOptionsModel.TYPE_GET_EDIT_OPTIONS) {
//            if (result != null && result instanceof EditUserOptionsEntity) {
//                EditUserOptionsEntity mEditUserOptionsEntity = (EditUserOptionsEntity) result;
//                if (mEditUserOptionsEntity.getResult() != null) {
//                    isEdit = mEditUserOptionsEntity.getResult();
//                    progressDialog.dismiss();
//                    if (isEdit) {
//                        OptionEditActivity.this.finish();
//                    }
//
//                }
//
//            }
//        }

    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
//        // TODO Auto-generated method stub
//        Toast.makeText(OptionEditActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (LogManagerEnum.EDITUSEROPINTION.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, EDITOPTIONPATH, CHTTP.POSTWITHTOKEN, this, EDIT_OPTION, LogManagerEnum.EDITUSEROPINTION.functionCode);
        } else if (requestName.equals(EDIT_OPTION)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, EDITOPTIONPATH, mOptionsParameters, this, requestName, LogManagerEnum.EDITUSEROPINTION.functionCode);
            dismissDialog();
            if (!TextUtils.isEmpty(requestValue)) {
                OpintionDelectOrEditResult mDelUserOptionsEntity = FastJsonUtils.getPerson(requestValue, OpintionDelectOrEditResult.class);
                if (null != mDelUserOptionsEntity) {
                    if (mDelUserOptionsEntity.code == 200) {
                        isEdit = mDelUserOptionsEntity.Result;
                        if (isEdit) {
                            netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, mDelUserOptionsEntity.message, INetWorkManager.State.SUCCESS);
                            OptionEditActivity.this.finish();
                        } else {
                            netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, mDelUserOptionsEntity.message, INetWorkManager.State.FAIL);
                        }
                    } else {
                        netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, mDelUserOptionsEntity.message, INetWorkManager.State.FAIL);
                    }
                } else {
                    netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, "解析后实体为空", INetWorkManager.State.FAIL);
                }
            } else {
                netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, "返回参数为空", INetWorkManager.State.FAIL);
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.EDITUSEROPINTION.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, EDITOPTIONPATH, CHTTP.POSTWITHTOKEN, this, EDIT_OPTION, LogManagerEnum.EDITUSEROPINTION.functionCode);
        } else if (requestName.equals(EDIT_OPTION)) {
            Toast.makeText(OptionEditActivity.this, "请您稍后重试", Toast.LENGTH_SHORT).show();
            dismissDialog();
            netWorkManager.logFunactionFinsh(OptionEditActivity.this, OptionEditActivity.this, "editOpintionfunctionFinish", LogManagerEnum.EDITUSEROPINTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
