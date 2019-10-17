package com.minxing.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.htmitech.proxy.activity.ZWLoginActivity;
import com.minxing.client.AppConstants;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.LoadingActivity;
import com.minxing.client.LoginActivity;
import com.htmitech.emportal.R;
import com.minxing.client.RootActivity;
import com.minxing.client.util.CacheManager;
import com.minxing.client.util.Utils;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.listener.ICallLoginMXListener;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class LaunchApp2AppActivity extends RootActivity {

	private static final String LAUNCH_APP2APP_ACTION = "com.minxing.client.app2app.launcher";
	private static final String LAUNCH_APP2APP_TYPE_KEY = "type";
	private static final String LAUNCH_APP2APP_DATA_KEY = "data";
	private static final String LAUNCH_APP2APP_SSO_USERNAME_KEY = "sso_username";
	private static final String LAUNCH_APP2APP_SSO_PASSWORD_KEY = "sso_password";

	private static final String LAUNCH_APP2APP_TYPE_CHAT = "startChat";
	private static final String LAUNCH_APP2APP_TYPE_TAB_SHEET = "showTabSheet";

	private static final int REQUEST_SSO_LOGIN_CODE = 10001;
	private int mCurrentApp2APPType = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!Utils.checkConnectState(this)) {
			Utils.toast(this, getString(R.string.error_no_network), Toast.LENGTH_SHORT);
			finish();
			return;
		}

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getStringExtra(LAUNCH_APP2APP_TYPE_KEY);

		if (action == null || "".equals(action) || !LAUNCH_APP2APP_ACTION.equals(action) || type == null || "".equals(type)) {
			finish();
			return;
		}

		if (LAUNCH_APP2APP_TYPE_CHAT.equals(type)) {
			handleStartChat(intent);
		} else if (LAUNCH_APP2APP_TYPE_TAB_SHEET.equals(type)) {
			handleShowTabSheet(intent);
		} else {
			finish();
			return;
		}
	}

	private void handleStartChat(Intent intent) {
		String data = intent.getStringExtra(LAUNCH_APP2APP_DATA_KEY);
		if (data != null && !"".equals(data)) {
			String[] loginNames = data.split(",");
			CacheManager.getInstance().setHoldedShareContent(loginNames);
			app2appStatusDispatch(intent, AppConstants.SYSTEM_APP2APP_TYPE_START_CHAT);
		} else {
			Utils.toast(this, R.string.app2app_start_chat_error, Toast.LENGTH_SHORT);
			finish();
		}
	}

	private void handleShowTabSheet(Intent intent) {
		String data = intent.getStringExtra(LAUNCH_APP2APP_DATA_KEY);
		if (data != null && !"".equals(data)) {
			CacheManager.getInstance().setHoldedShareContent(data);
			app2appStatusDispatch(intent, AppConstants.SYSTEM_APP2APP_TYPE_TAB_SHEET);
		} else {
			Utils.toast(this, R.string.app2app_switch_tab_error, Toast.LENGTH_SHORT);
			finish();
		}
	}

	private void app2appStatusDispatch(Intent intent, int app2appType){
		String ssoUsername = intent.getStringExtra(LAUNCH_APP2APP_SSO_USERNAME_KEY);
		String ssoPassword = intent.getStringExtra(LAUNCH_APP2APP_SSO_PASSWORD_KEY);
		if(ssoUsername != null && !"".equals(ssoUsername) && ssoPassword != null && !"".equals(ssoPassword)){
			if(!TextUtils.isEmpty(PreferenceUtils.getLogin_name(this)) && ssoUsername.equals(PreferenceUtils.getLogin_name(this))){
				startApp2App(app2appType);
			} else {
				mCurrentApp2APPType = app2appType;
				Intent i = new Intent(this, LoadingActivity.class);
				i.putExtra(LoadingActivity.LAUNCH_TYPE_SSO_LOGIN, true);
				i.putExtra(LoadingActivity.SSO_LOGIN_USERNAME_KEY, ssoUsername);
				i.putExtra(LoadingActivity.SSO_LOGIN_PASSWORD_KEY, ssoPassword);
				startActivityForResult(i, REQUEST_SSO_LOGIN_CODE);
			}
		} else {
			startApp2App(app2appType);
		}
	}

	private void startApp2App(int app2appType) {
		Intent intent = null;
		if (!TextUtils.isEmpty(PreferenceUtils.getLogin_name(this))) {
			if (PreferenceUtils.isGesturePwdEnable(this, PreferenceUtils.getLogin_name(this))) {
				intent = new Intent(this, GesturePasswordActivity.class);
				intent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, true);
				intent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, app2appType);
				startActivity(intent);
				finish();
			} else if(com.htmitech.unit.BackgroundDetector.getInstance().isGestureZWPwdViewEnabled()){
				intent = new Intent(this, ZWLoginActivity.class);
				intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY,
						GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
				intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				startActivity(intent);
				finish();
			}else {
				switch (app2appType) {
					case AppConstants.SYSTEM_APP2APP_TYPE_START_CHAT:
						String[] loginNames = (String[]) CacheManager.getInstance().getHoldedShareContent();
						ConcreteLogin mCpmcreteLogin = ConcreteLogin.getInstance();
						mCpmcreteLogin.setICallLoginMXListener(new ICallLoginMXListener() {
							@Override
							public void onMXSuccess() {
								finish();

							}

							@Override
							public void onMXFail(String message) {
								finish();
							}
						});
						mCpmcreteLogin.chat(this,loginNames);
//						MXAPI.getInstance(this).chat(loginNames, new MXApiCallback() {
//
//							@Override
//							public void onLoading() {
//							}
//
//							@Override
//							public void onFail(MXError error) {
//								Utils.toast(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
//								finish();
//							}
//
//							@Override
//							public void onSuccess() {
//								finish();
//							}
//						});
						break;

					case AppConstants.SYSTEM_APP2APP_TYPE_TAB_SHEET:
						String tabSheet = (String) CacheManager.getInstance().getHoldedShareContent();
						intent = new Intent(this, ClientTabActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE_TAB_SHEET_VALUE, tabSheet);
						startActivity(intent);
						finish();
						break;
				}
			}
		} else {
			intent = new Intent(this, LoginActivity.class);
			intent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, true);
			intent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, app2appType);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
			case REQUEST_SSO_LOGIN_CODE:
				startApp2App(mCurrentApp2APPType);
				break;
		}
	}
}