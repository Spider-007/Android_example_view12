package com.htmitech.htworkflowformpluginnew.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.htworkflowformpluginnew.entity.AddUserOptionsEntity;
import com.htmitech.htworkflowformpluginnew.entity.OptionsAdd;
import com.htmitech.htworkflowformpluginnew.entity.OptionsParametersAdd;
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

public class OptionNewActivity extends SlidingBackAcitivity implements
        OnClickListener, IBaseCallback, ObserverCallBackType {

    private TextView btnOptionSave;
    private EditText etOptions;
    private boolean isSuccess = false;
    String app_id;
    private String NewUserOptionPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USERINFO_ADDUSEROPTIONS_METHOD_JAVA;
    private static final String NEW_USEROPTION = "newUserOption";
    private INetWorkManager netWorkManager;
    private OptionsParametersAdd mOptionsParameters;

    protected int getLayoutById() {
        return R.layout.activity_optionnew;
    }

    protected void initView() {

        findViewById(R.id.imgview_titlebar_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.textview_titlebar_title)).setText("新增常用意见");
        btnOptionSave = (TextView) findViewById(R.id.btn_option_save);
        try {
            if(null != BookInit.getInstance().getmApcUserdefinePortal() && BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 3){
                btnOptionSave.setBackgroundResource(R.drawable.option_save_shape_red);
            }else{
                btnOptionSave.setBackgroundResource(R.drawable.option_save_shape);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        etOptions = (EditText) findViewById(R.id.et_Options);
        btnOptionSave.setOnClickListener(this);
        app_id = getIntent().getStringExtra("app_id");
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option_save:
                if (etOptions.getText().toString().trim().equals("")) {
                    Toast.makeText(OptionNewActivity.this, "您输入的内容为空", Toast.LENGTH_SHORT).show();
                } else {
                    // 新增意见调用
                    mOptionsParameters = new OptionsParametersAdd();
                    mOptionsParameters.appId = app_id;
                    OptionsAdd optionsAdd = new OptionsAdd();
                    optionsAdd.opinionText = etOptions.getText().toString();
                    mOptionsParameters.option = optionsAdd;
                    mOptionsParameters.userId = OAConText.getInstance(HtmitechApplication.getApplication()).UserID;
                    netWorkManager.logFunactionStart(OptionNewActivity.this, OptionNewActivity.this, LogManagerEnum.ADDUSEROPINTION);
//                    AddUserOptionsModel addUserOptionsModel = new AddUserOptionsModel(OptionNewActivity.this);
//                    addUserOptionsModel.getDataFromServerByType(AddUserOptionsModel.TYPE_GET_NEW_OPTIONS, mOptionsParameters);
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
//        if (requestTypeId == AddUserOptionsModel.TYPE_GET_NEW_OPTIONS) {
//            if (result != null && result instanceof AddUserOptionsEntity) {
//                AddUserOptionsEntity mAddUserOptionsEntity = (AddUserOptionsEntity) result;
//                progressDialog.dismiss();
//                if (mAddUserOptionsEntity.getResult() != null && mAddUserOptionsEntity.getResult().getValue() != null) {
//                    isSuccess = true;
//                    OptionNewActivity.this.finish();
//                }
//            }
//        }

    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
//        // TODO Auto-generated method stub
//        Toast.makeText(OptionNewActivity.this, errorMsg + "请您稍后重试", Toast.LENGTH_SHORT).show();
//        progressDialog.dismiss();

    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (LogManagerEnum.ADDUSEROPINTION.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, NewUserOptionPath, CHTTP.POSTWITHTOKEN, this, NEW_USEROPTION, LogManagerEnum.ADDUSEROPINTION.functionCode);
        } else if (requestName.equals(NEW_USEROPTION)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, NewUserOptionPath, mOptionsParameters, this, requestName, LogManagerEnum.ADDUSEROPINTION.functionCode);
            dismissDialog();
            if (!TextUtils.isEmpty(requestValue)) {
                AddUserOptionsEntity addUserOptionsEntity = FastJsonUtils.getPerson(requestValue, AddUserOptionsEntity.class);
                if (null != addUserOptionsEntity) {
                    if (addUserOptionsEntity.code == 200) {
                        if (addUserOptionsEntity.result != null && addUserOptionsEntity.result.opinionText != null && addUserOptionsEntity.result.opinionId != null) {
                            netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, addUserOptionsEntity.message, INetWorkManager.State.SUCCESS);
                            isSuccess = true;
                            OptionNewActivity.this.finish();
                        } else {
                            netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, addUserOptionsEntity.message, INetWorkManager.State.FAIL);
                        }
                    } else {
                        netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, addUserOptionsEntity.message, INetWorkManager.State.FAIL);
                    }
                } else {
                    netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, "解析后实体为空", INetWorkManager.State.FAIL);
                }
            } else {
                netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, "服务器返回内容为空", INetWorkManager.State.FAIL);
            }
            finish();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.ADDUSEROPINTION.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, mOptionsParameters, NewUserOptionPath, CHTTP.POSTWITHTOKEN, this, NEW_USEROPTION, LogManagerEnum.ADDUSEROPINTION.functionCode);
        } else if (requestName.equals(NEW_USEROPTION)) {
            dismissDialog();
            Toast.makeText(OptionNewActivity.this, "请您稍后重试", Toast.LENGTH_SHORT).show();
            netWorkManager.logFunactionFinsh(OptionNewActivity.this, OptionNewActivity.this, "newOpintionfunctionFinish", LogManagerEnum.ADDUSEROPINTION.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
