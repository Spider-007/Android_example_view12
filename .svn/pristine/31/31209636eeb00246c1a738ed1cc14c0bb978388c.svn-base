package com.minxing.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.FloatLabeledEditText;
import com.htmitech.MyView.VerifyCode;
import com.htmitech.app.widget.CustomEditText;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.doman.ChangeUserPasswordRequest;
import com.htmitech.proxy.doman.ChangeUserPasswordResult;
import com.htmitech.proxy.interfaces.INetWorkManager;


import com.htmitech.proxy.util.DesUtil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.kit.MXKit;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


public class ChangeUserPasswordActivity extends BaseFragmentActivity implements View.OnClickListener, ObserverCallBackType {

    private ImageButton backButton = null;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private CustomEditText verificationCodeEditView = null;
    private LinearLayout llVerificationCode;
    private VerifyCode verifyCode;
    private TextView tvOldNotific;
    private TextView tvNewNotific;
    private TextView tvVerCode;
    private Button btSure;
    private String oldPassword = "";
    private String newPassword = "";
    private INetWorkManager netWorkManager;
    private String CHANGEPASSWORD = "change_password";
    public String CHANGEPASSWORDPATH = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.CHANGE_USER_PASSWORD;
    private ChangeUserPasswordRequest changeUserPasswordRequest;
    String LoginOutUrl = "";//退出登入接口url
    private int chageNumnber = 0;
    private FloatLabeledEditText floatLabeledEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        floatLabeledEditText = (FloatLabeledEditText) findViewById(R.id.fle_verification_edittext);
        floatLabeledEditText.setHintGone();
        backButton = (ImageButton) findViewById(R.id.title_left_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) findViewById(R.id.title_name)).setText("修改密码");
        initView();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
    }

    private void initView() {
        etOldPassword = (EditText) findViewById(R.id.et_change_user_password_oldpass);
        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newPassword != null && !newPassword.equals("") && newPassword.length() >= 6) {
                    String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}";
                    boolean flag = newPassword.matches(regex);
                    if (!s.toString().equals("")) {
                        if (flag) {
                            btSure.setEnabled(true);
                            btSure.setFocusable(true);
                            btSure.setBackgroundResource(R.drawable.change_user_password_button_confim_shape);
                            if (tvNewNotific.getVisibility() == View.VISIBLE) {
                                tvNewNotific.setVisibility(View.GONE);
                            }
                            if (tvOldNotific.getVisibility() == View.VISIBLE) {
                                tvOldNotific.setVisibility(View.GONE);
                            }
                        } else {
                            tvNewNotific.setVisibility(View.VISIBLE);
                            tvNewNotific.setText("密码过于简单，需采用字母和数字组合的形式");
                            btSure.setEnabled(false);
                            btSure.setFocusable(false);
                            btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                        }
                    } else {
                        if (!flag) {
                            tvNewNotific.setVisibility(View.VISIBLE);
                            tvNewNotific.setText("密码过于简单，需采用字母和数字组合的形式");
                        } else {
                            if (tvNewNotific.getVisibility() == View.VISIBLE) {
                                tvNewNotific.setVisibility(View.GONE);
                            }
                        }
                        tvOldNotific.setVisibility(View.VISIBLE);
                        tvOldNotific.setText("还未输入旧密码");
                        btSure.setEnabled(false);
                        btSure.setFocusable(false);
                        btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                    }
                } else {
                    tvNewNotific.setVisibility(View.VISIBLE);
                    tvNewNotific.setText("密码长度不能小于6位");
                    if (s.toString().equals("")) {
                        tvOldNotific.setVisibility(View.VISIBLE);
                        tvOldNotific.setText("还未输入旧密码");
                    }
                    btSure.setEnabled(false);
                    btSure.setFocusable(false);
                    btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                oldPassword = s.toString();
            }
        });
        etNewPassword = (EditText) findViewById(R.id.et_change_user_password_newpass);
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (oldPassword != null && !oldPassword.equals("")) {
                    if (!s.toString().equals("") && s.toString().length() >= 6) {
                        String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}";
                        boolean flag = s.toString().matches(regex);
                        if (flag) {
                            if (oldPassword.equals(s.toString())) {
                                tvNewNotific.setVisibility(View.VISIBLE);
                                tvNewNotific.setText("新旧密码不能相同");
                                btSure.setEnabled(false);
                                btSure.setFocusable(false);
                                btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                            } else {
                                if (tvNewNotific.getVisibility() == View.VISIBLE) {
                                    tvNewNotific.setVisibility(View.GONE);
                                }
                                if (tvOldNotific.getVisibility() == View.VISIBLE) {
                                    tvOldNotific.setVisibility(View.GONE);
                                }
                                btSure.setEnabled(true);
                                btSure.setFocusable(true);
                                btSure.setBackgroundResource(R.drawable.change_user_password_button_confim_shape);
                            }
                        } else {
                            tvNewNotific.setVisibility(View.VISIBLE);
                            tvNewNotific.setText("密码过于简单，需采用字母和数字组合的形式");
                            btSure.setEnabled(false);
                            btSure.setFocusable(false);
                            btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                        }
                    } else {
                        tvNewNotific.setVisibility(View.VISIBLE);
                        tvNewNotific.setText("密码长度不能小于6位");
                        btSure.setEnabled(false);
                        btSure.setFocusable(false);
                        btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                    }
                } else {
                    tvOldNotific.setVisibility(View.VISIBLE);
                    tvOldNotific.setText("还未输入旧密码");
                    btSure.setEnabled(false);
                    btSure.setFocusable(false);
                    btSure.setBackgroundResource(R.drawable.change_user_password_button_shape);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword = s.toString();
            }
        });
        tvOldNotific = (TextView) findViewById(R.id.tv_change_user_password_oldpass_notifice);
        tvNewNotific = (TextView) findViewById(R.id.tv_change_user_password_newpass_notifice);
        btSure = (Button) findViewById(R.id.bt_change_user_password_ensure);
        btSure.setOnClickListener(this);
        verificationCodeEditView = (CustomEditText) findViewById(R.id.verification_code);
        llVerificationCode = (LinearLayout) findViewById(R.id.ll_verification_code);
        verifyCode = (VerifyCode) findViewById(R.id.vc_pictrue);
        tvVerCode = (TextView) findViewById(R.id.tv_change_user_password_yzm);
        verificationCodeEditView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence value, int start, int before, int count) {
                if (value.toString().trim().length() == 4) {
                    if (!verifyCode.isEqualsIgnoreCase(value.toString().trim())) {
                        Toast.makeText(ChangeUserPasswordActivity.this, "验证码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
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

        verificationCodeEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_change_user_password_ensure:
                ensureChangePassword();
                break;
        }
    }

    private void ensureChangePassword() {
        if (newPassword.length() < 6) {
            tvNewNotific.setVisibility(View.VISIBLE);
            tvNewNotific.setText("密码长度不能小于6位");
            return;
        }
        if (newPassword.equals(oldPassword)) {
            tvNewNotific.setVisibility(View.VISIBLE);
            tvNewNotific.setText("新旧密码不能相同");
            return;
        }
        if (llVerificationCode.getVisibility() == View.VISIBLE) {
            if (!verifyCode.isEqualsIgnoreCase(verificationCodeEditView.getText().toString().trim())) {
                Toast.makeText(this, "验证码输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        setDialogValue("修改密码中，请稍后");
        setCanceledOnTouchOutside(false);
        showDialog();
        changeUserPasswordRequest = new ChangeUserPasswordRequest();
        changeUserPasswordRequest.newPasswd = DesUtil.encode(DesUtil.KEY, newPassword);
        changeUserPasswordRequest.oldPasswd = DesUtil.encode(DesUtil.KEY, oldPassword);
        netWorkManager.logFunactionStart(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, LogManagerEnum.CHANGEUSERPASSWORD);
    }


    @Override
    public void success(String requestValue, int type, String requestName) {
        if (LogManagerEnum.CHANGEUSERPASSWORD.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, changeUserPasswordRequest, CHANGEPASSWORDPATH, CHTTP.POSTWITHTOKEN, this, CHANGEPASSWORD, LogManagerEnum.CHANGEUSERPASSWORD.functionCode);
        } else if (requestName.equals(CHANGEPASSWORD)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, CHANGEPASSWORDPATH, changeUserPasswordRequest, this, requestName, LogManagerEnum.CHANGEUSERPASSWORD.functionCode);
            dismissDialog();
            if (!TextUtils.isEmpty(requestValue)) {
                ChangeUserPasswordResult result = FastJsonUtils.getPerson(requestValue, ChangeUserPasswordResult.class);
                if (null != result) {
                    if (result.code == 200) {
                        netWorkManager.logFunactionFinsh(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, "changeUserPasswordFinish", LogManagerEnum.CHANGEUSERPASSWORD.functionCode, result.message, INetWorkManager.State.SUCCESS);
                        Toast.makeText(ChangeUserPasswordActivity.this, "修改密码成功,将退出到登录界面", Toast.LENGTH_SHORT).show();
                        setDialogValue("退出登录中，请稍后");
                        setCanceledOnTouchOutside(false);
                        showDialog();
                        MXKit.getInstance().logout(this, new MXKit.MXKitLogoutListener() {
                            @Override
                            public void onLogout() {
                                //EmpApi退出
                                EmpApiLoginOut();
                            }
                        });
                    } else {
                        setVerificationCodeVisiable();
                        Toast.makeText(ChangeUserPasswordActivity.this, result.message, Toast.LENGTH_SHORT).show();
                        netWorkManager.logFunactionFinsh(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, "changeUserPasswordFinish", LogManagerEnum.CHANGEUSERPASSWORD.functionCode, result.message, INetWorkManager.State.FAIL);
                    }
                } else {
                    setVerificationCodeVisiable();
                    netWorkManager.logFunactionFinsh(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, "changeUserPasswordFinish", LogManagerEnum.CHANGEUSERPASSWORD.functionCode, "解析后实体为空", INetWorkManager.State.FAIL);
                }
            } else {
                setVerificationCodeVisiable();
                netWorkManager.logFunactionFinsh(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, "changeUserPasswordFinish", LogManagerEnum.CHANGEUSERPASSWORD.functionCode, "解析后实体为空", INetWorkManager.State.FAIL);
            }
        } else if (requestName.equals("LoginOut")) {//调用此方法防止token失效，返回的是token没有失效所生成的结果
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, LoginOutUrl, null, this, requestName, "");
            dismissDialog();
            if (requestValue != null && !requestValue.equals("")) {
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                if (entity != null && entity.code == 200) {//EmpApi退出登入成功
                    //清除Token缓存
                    PreferenceUtils.clearToken();
                    Intent finishIntent = new Intent(this, ClientTabActivity.class);
                    finishIntent.setAction("finish");
                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    this.startActivity(finishIntent);
                } else {
                    Toast.makeText(this, "退出失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "退出失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.CHANGEUSERPASSWORD.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, changeUserPasswordRequest, CHANGEPASSWORDPATH, CHTTP.POSTWITHTOKEN, this, CHANGEPASSWORD, LogManagerEnum.CHANGEUSERPASSWORD.functionCode);
        } else if (requestName.equals(CHANGEPASSWORD)) {
            dismissDialog();
            setVerificationCodeVisiable();
            Toast.makeText(this, "请您稍后重试", Toast.LENGTH_SHORT).show();
            netWorkManager.logFunactionFinsh(ChangeUserPasswordActivity.this, ChangeUserPasswordActivity.this, "changeUserPasswordFinish", LogManagerEnum.CHANGEUSERPASSWORD.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
        } else if (requestName.equals("LoginOut")) {
            dismissDialog();
            if (exceptionMessage.contains("超时") || exceptionMessage.contains("TIMEOUT")) {
                Toast.makeText(this, "退出登录请求超时", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    /**
     * 退出登入接口
     **/
    public void EmpApiLoginOut() {
        LoginOutUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.USER_LOGINOUT_EMPAPI;
        AnsynHttpRequest.requestByPostWithToken(this, null, LoginOutUrl, CHTTP.POSTWITHTOKEN, this, "LoginOut", "0");
    }

    private void setVerificationCodeVisiable() {
        chageNumnber++;
        if (chageNumnber >= 3) {
            tvVerCode.setVisibility(View.VISIBLE);
            llVerificationCode.setVisibility(View.VISIBLE);
        }
    }
}
