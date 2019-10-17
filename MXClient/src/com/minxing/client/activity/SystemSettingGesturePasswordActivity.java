package com.minxing.client.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.activity.DeviceSafeMainActivity;
import com.htmitech.proxy.doman.DeviceSafeConfigRequest;
import com.htmitech.proxy.doman.DeviceSafeConfigResponse;
import com.htmitech.proxy.util.ZTActivityUnit;
import com.htmitech.unit.BackgroundDetector;
import com.htmitech.zhiwen.IBackMainOnClick;
import com.htmitech.zhiwen.ZWUtils;
import com.minxing.client.RootActivity;

import java.util.HashMap;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class SystemSettingGesturePasswordActivity extends RootActivity implements ObserverCallBackType, IBackMainOnClick {

    private LinearLayout gesture_password_setting_off = null;
    private RelativeLayout gesture_password_setting_on = null;

    private TextView gesture_password_off = null;
    private TextView gesture_password_on = null;
    private LinearLayout change_gesture_password = null;
    private LinearLayout change_user_password = null;

    private ImageButton backButton = null;
    private String loginName = null;

    private static final int REQUEST_SETTING_GESTURE_PASSWORD = 9901;
    private static final int REQUEST_CANCEL_GESTURE_PASSWORD = 9902;
    private LinearLayout ll_device_safe;
    private LinearLayout gesture_password_zw;
    private ImageView iv_zw;
    private String pathUrl;
    private DeviceSafeConfigRequest mDeviceSafeConfigRequest;
    private LoadingDialog mProgressDialog;
    private String app_id;
    private com.htmitech.pop.AlertDialog mAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_setting_gesture_password);
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        mAlertDialog =  new com.htmitech.pop.AlertDialog(this);
        loginName = PreferenceUtils.getLogin_name(this);
        ((TextView) findViewById(R.id.title_name)).setText(R.string.setting_gesture_password);
        backButton = (ImageButton) findViewById(R.id.title_left_button);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnimation();
            }
        });

        gesture_password_setting_off = (LinearLayout) findViewById(R.id.gesture_password_setting_off);
        gesture_password_setting_on = (RelativeLayout) findViewById(R.id.gesture_password_setting_on);
        ll_device_safe = (LinearLayout) findViewById(R.id.ll_device_safe);
        gesture_password_zw = (LinearLayout) findViewById(R.id.gesture_password_zw);
        gesture_password_off = (TextView) findViewById(R.id.gesture_password_off);
        gesture_password_on = (TextView) findViewById(R.id.gesture_password_on);
        iv_zw = (ImageView) findViewById(R.id.iv_zw);
        change_gesture_password = (LinearLayout) findViewById(R.id.change_gesture_password);
        change_user_password = (LinearLayout) findViewById(R.id.ll_device_change_password);

        if (PreferenceUtils.isInitGesturePwd(this, loginName) && PreferenceUtils.isGesturePwdEnable(this, loginName)) {
            gesture_password_setting_off.setVisibility(View.GONE);
            gesture_password_setting_on.setVisibility(View.VISIBLE);
        } else {
            gesture_password_setting_off.setVisibility(View.VISIBLE);
            gesture_password_setting_on.setVisibility(View.GONE);
        }

        if (!PreferenceUtils.isInitZWGesturePwd(SystemSettingGesturePasswordActivity.this, loginName)) {
            iv_zw.setImageResource(R.drawable.icon_checkbox_normal);
        } else {
            iv_zw.setImageResource(R.drawable.icon_checkbox_selected);
        }
        gesture_password_zw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    ZWUtils mZWUtil = new ZWUtils(SystemSettingGesturePasswordActivity.this, false, SystemSettingGesturePasswordActivity.this, true);
                    mZWUtil.initFingure();
                }else{
                    if(null != mAlertDialog){
                        mAlertDialog.builder().setImage().setCancelable(false).setTitle("没有指纹传感器").setMsg("您的手机无指纹验证功能，无法开启！").setPositiveButton("我知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                    }

                }
            }

        });
        ll_device_safe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Network.checkNetWork(SystemSettingGesturePasswordActivity.this)) {
                    Toast.makeText(SystemSettingGesturePasswordActivity.this, "网络未连接，请检查！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressDialog = new LoadingDialog(SystemSettingGesturePasswordActivity.this);
                mProgressDialog.show();
                mDeviceSafeConfigRequest = new DeviceSafeConfigRequest();
                mDeviceSafeConfigRequest.userId = OAConText.getInstance(SystemSettingGesturePasswordActivity.this).UserID;
                mDeviceSafeConfigRequest.groupCorpId = OAConText.getInstance(SystemSettingGesturePasswordActivity.this).group_corp_id;
                pathUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_DCSAFE_CONFIG;
                AnsynHttpRequest.requestByPostWithToken(SystemSettingGesturePasswordActivity.this, mDeviceSafeConfigRequest, pathUrl, CHTTP.POSTWITHTOKEN
                        , SystemSettingGesturePasswordActivity.this, "safeConfig", "0");
            }
        });
        gesture_password_on.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SystemSettingGesturePasswordActivity.this, GesturePasswordActivity.class);
                i.putExtra("is_cancel_password", true);
                startActivityForResult(i, REQUEST_CANCEL_GESTURE_PASSWORD);
            }
        });

        change_gesture_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SystemSettingGesturePasswordActivity.this, GesturePasswordActivity.class);
                i.putExtra("is_reset_password", true);
                startActivity(i);
            }
        });

        gesture_password_off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceUtils.isInitGesturePwd(SystemSettingGesturePasswordActivity.this, loginName)) {
                    PreferenceUtils.resetGesturePwd(SystemSettingGesturePasswordActivity.this, loginName);
                }
                Intent i = new Intent(SystemSettingGesturePasswordActivity.this, GesturePasswordActivity.class);
                startActivityForResult(i, REQUEST_SETTING_GESTURE_PASSWORD);
            }
        });

        //修改用户密码
        change_user_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangeUserPassword = new Intent(SystemSettingGesturePasswordActivity.this, ChangeUserPasswordActivity.class);
                startActivity(intentChangeUserPassword);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithAnimation();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_SETTING_GESTURE_PASSWORD:
                PreferenceUtils.enableGesturePwd(this, loginName);
                gesture_password_setting_off.setVisibility(View.GONE);
                gesture_password_setting_on.setVisibility(View.VISIBLE);
                break;

            case REQUEST_CANCEL_GESTURE_PASSWORD:
                PreferenceUtils.disableGesturePwd(this, loginName);
                gesture_password_setting_off.setVisibility(View.VISIBLE);
                gesture_password_setting_on.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("safeConfig")) {
            DeviceSafeConfigResponse mDeviceSafeConfigResponse = null;
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, pathUrl, mDeviceSafeConfigRequest, this, requestName, "0");
            try {
                if (requestValue != null && !"".equals(requestValue)) {
                    Gson gson = new Gson();
                    mDeviceSafeConfigResponse = gson.fromJson(requestValue, DeviceSafeConfigResponse.class);
                    if (null != mDeviceSafeConfigResponse && null != mDeviceSafeConfigResponse.result) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("result", mDeviceSafeConfigResponse.result);
                        params.put("app_id", app_id);
                        ZTActivityUnit.switchTo(SystemSettingGesturePasswordActivity.this, DeviceSafeMainActivity.class, params);
                    } else if (mDeviceSafeConfigResponse.code != 900) {
                        Toast.makeText(SystemSettingGesturePasswordActivity.this, "设备配置信息未返回，继续使用将会有异常！", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DeviceConfig", e.getMessage());
            } finally {
//				Intent i = new Intent(SystemSettingGesturePasswordActivity.this, DeviceSafeMainActivity.class);
//				i.putExtra("result",mDeviceSafeConfigResponse.result);
//				startActivity(i);
                if (null != mProgressDialog) {
                    mProgressDialog.dismiss();
                }
            }

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

    @Override
    public void onMainOnClick() {
        if (!PreferenceUtils.isInitZWGesturePwd(SystemSettingGesturePasswordActivity.this, loginName)) {
            iv_zw.setImageResource(R.drawable.icon_checkbox_selected);
            PreferenceUtils.saveZWGesturePwd(SystemSettingGesturePasswordActivity.this, loginName, "zw_password");
            Toast.makeText(SystemSettingGesturePasswordActivity.this, "已经开启指纹验证！", Toast.LENGTH_SHORT).show();
        } else {
            iv_zw.setImageResource(R.drawable.icon_checkbox_normal);
            PreferenceUtils.saveZWGesturePwd(SystemSettingGesturePasswordActivity.this, loginName, "");
            Toast.makeText(SystemSettingGesturePasswordActivity.this, "指纹验证已关闭！", Toast.LENGTH_SHORT).show();
            BackgroundDetector.getInstance().isGestureZWPwdViewEnabled();
        }
    }

    @Override
    public void onLoginOnClick() {

    }
}