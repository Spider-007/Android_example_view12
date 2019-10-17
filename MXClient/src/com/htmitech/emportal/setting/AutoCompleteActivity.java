package com.htmitech.emportal.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableRow;

import com.htmitech.emportal.R;
import com.htmitech.emportal.utils.CacheDeleteUtils;
import com.minxing.client.ClientTabActivity;

import java.io.File;
import java.util.ArrayList;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

public class AutoCompleteActivity extends Activity implements OnClickListener {

	private Button mOaLoginButton;
	private Button mOaDebugButton;
	private Button mPushButton;
	private Button mImButton;
	private Button mApiButton;
	private Button dzButton;
	private Button mSoftwareCodeButton;
	private Button mCancelButton;
	private Button mOkButton;

	private AutoCompleteTextView mOaLoginText;
	private AutoCompleteTextView mOaDebugText;
	private AutoCompleteTextView mPushText;
	private AutoCompleteTextView mImText;
	private AutoCompleteTextView mApiText;
	private AutoCompleteTextView mSoftwareCodeText;
	private AutoCompleteTextView dzAutoCompleteTextView;


	ArrayAdapter<String> loginAdapter;
	ArrayAdapter<String> debugAdapter;
	ArrayAdapter<String> pushAdapter;
	ArrayAdapter<String> imAdapter;
	ArrayAdapter<String> apiAdapter;
	ArrayAdapter<String> softwareCodeAdapter;
	ArrayAdapter<String> serveripAdapter;
	ArrayAdapter<String> dzipAdapter;


	ArrayList<String> loginList;
	ArrayList<String> debugList;
	ArrayList<String> pushList;
	ArrayList<String> imList;
	ArrayList<String> apiList;
	ArrayList<String> softwareCodeList;
	ArrayList<String> ServerIpList;
	ArrayList<String> dzIpList;


	AutoCompleteJSONSerializer AutoCompleteBuild;
	private AutoCompleteTextView mServerIpText;
	private Button mServerIpButton;
	private TableRow tb_dz_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE  | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_table);
		AutoCompleteBuild = new AutoCompleteJSONSerializer(this);
		viewInit();
	}

	private void viewInit() {

		mOaLoginText = (AutoCompleteTextView) findViewById(R.id.oa_login_AutoCompleteTextView);
		mOaDebugText = (AutoCompleteTextView) findViewById(R.id.oa_debug_autoCompleteTextView);
		mPushText = (AutoCompleteTextView) findViewById(R.id.push_autoCompleteTextView);
		mImText = (AutoCompleteTextView) findViewById(R.id.im_autoCompleteTextView);
		mApiText = (AutoCompleteTextView) findViewById(R.id.api_autoCompleteTextView);
		mSoftwareCodeText = (AutoCompleteTextView) findViewById(R.id.softwarecode_autoCompleteTextView);
		mServerIpText = (AutoCompleteTextView) findViewById(R.id.serverip_autoCompleteTextView);
		mOaLoginButton = (Button) findViewById(R.id.oa_login_button);
		mOaDebugButton = (Button) findViewById(R.id.oa_debug_button);
		mPushButton = (Button) findViewById(R.id.push_button);
		mImButton = (Button) findViewById(R.id.im_button);
		mApiButton = (Button) findViewById(R.id.api_button);
		mSoftwareCodeButton = (Button) findViewById(R.id.softwarecode_button);
		mServerIpButton = (Button) findViewById(R.id.serverip_button);
		//cancel and ok button
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mOkButton = (Button) findViewById(R.id.ok_button);
		dzAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.dz_autoCompleteTextView);
		dzButton = (Button) findViewById(R.id.dz_button);
		tb_dz_login = (TableRow) findViewById(R.id.tb_dz_login);

		apiList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_API);
		if (apiList == null || !(apiList.size() > 0)) {
			apiList = new ArrayList<String>();
			apiList.add(PreferenceUtils.getApiUrl());
		}
		apiAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, apiList);


		debugList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_DEBUG);
		if (debugList == null || !(debugList.size() > 0)) {
			debugList = new ArrayList<String>();
			debugList.add(PreferenceUtils.getDebugUrl());
		}
		debugAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, debugList);


		imList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_IM);
		if (imList == null || !(imList.size() > 0)) {
			imList = new ArrayList<String>();
			imList.add(PreferenceUtils.getImUrl());
		}
		imAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, imList);


		loginList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_LOGIN);
		if (loginList == null || !(loginList.size() > 0)) {
			loginList = new ArrayList<String>();
			loginList.add(PreferenceUtils.getOaLoginUrl());
		}
		loginAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, loginList);


		pushList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_PUSH);
		if (pushList == null || !(pushList.size() > 0)) {
			pushList = new ArrayList<String>();
			pushList.add(PreferenceUtils.getPushUrl());
		}
		pushAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, pushList);


		softwareCodeList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_CODE);
		if (softwareCodeList == null || !(softwareCodeList.size() > 0)) {
			softwareCodeList = new ArrayList<String>();
			softwareCodeList.add(PreferenceUtils.getSoftWareCode());
		}
		softwareCodeAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, softwareCodeList);

		ServerIpList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_CODE);;
		if (ServerIpList == null || !(ServerIpList.size() > 0)) {
			ServerIpList = new ArrayList<String>();
			ServerIpList.add(PreferenceUtils.getServerIp());
		}
		serveripAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, ServerIpList);


		dzIpList = AutoCompleteBuild.getAnyOne(AutoCompleteJSONSerializer.JSON_DZ);

		if (dzIpList == null || !(dzIpList.size() > 0)) {
			dzIpList = new ArrayList<String>();
			dzIpList.add(PreferenceUtils.getDzUrl());
		}
		dzipAdapter = new ArrayAdapter<String>(this, R.layout.activity_main2, dzIpList);



		if (TextUtils.isEmpty(PreferenceUtils.getDzUrl())) {
			tb_dz_login.setVisibility(View.GONE);
		}else{
			tb_dz_login.setVisibility(View.VISIBLE);
		}
		mOaLoginText.setText(PreferenceUtils.getOaLoginUrl());
		mOaDebugText.setText(PreferenceUtils.getDebugUrl());
		mPushText.setText(PreferenceUtils.getPushUrl());
		mImText.setText(PreferenceUtils.getImUrl());
		mApiText.setText(PreferenceUtils.getApiUrl());
		mSoftwareCodeText.setText(PreferenceUtils.getSoftWareCode());
		mServerIpText.setText(PreferenceUtils.getServerIp());
		dzAutoCompleteTextView.setText(PreferenceUtils.getDzUrl());

		mOaLoginText.setAdapter(loginAdapter);
		mOaDebugText.setAdapter(debugAdapter);
		mPushText.setAdapter(pushAdapter);
		mImText.setAdapter(imAdapter);
		mApiText.setAdapter(apiAdapter);
		mSoftwareCodeText.setAdapter(softwareCodeAdapter);
		mServerIpText.setAdapter(serveripAdapter);
		dzAutoCompleteTextView.setAdapter(dzipAdapter);

		mOaLoginButton.setOnClickListener(this);
		mOaDebugButton.setOnClickListener(this);
		mPushButton.setOnClickListener(this);
		mImButton.setOnClickListener(this);
		mApiButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);
		mOkButton.setOnClickListener(this);
		mServerIpButton.setOnClickListener(this);
		dzButton.setOnClickListener(this);
	}

	private void saveAll() {
		String logTmp = null;
		String debugTmp = null;
		String pushTmp = null;
		String imTmp = null;
		String apiTmp = null;
		String softWareCode = null;
		String dzUrl = null;
		String serverIp = null;
		logTmp = mOaLoginText.getText().toString();
		if (logTmp != null && !"".equals(logTmp)) {
			if (!loginList.contains(logTmp)) {
				loginList.add(logTmp);
			}
			PreferenceUtils.saveOaLoginUrl(logTmp);

		}

		debugTmp = mOaDebugText.getText().toString();
		if (debugTmp != null && !"".equals(debugTmp)) {
			if (!debugList.contains(debugTmp)) {
				debugList.add(debugTmp);
			}
			PreferenceUtils.saveDebugUrl(debugTmp);

		}

		pushTmp = mPushText.getText().toString();
		if (pushTmp != null && !"".equals(pushTmp)) {
			if (!pushList.contains(pushTmp)) {
				pushList.add(pushTmp);
			}
			PreferenceUtils.savePushUrl(pushTmp);

		}

		imTmp = mImText.getText().toString();
		if (imTmp != null && !"".equals(imTmp)) {
			if (!imList.contains(imTmp)) {
				imList.add(imTmp);
			}
			PreferenceUtils.saveImUrl(imTmp);

		}

		apiTmp = mApiText.getText().toString();
		if (apiTmp != null && !"".equals(apiTmp)) {
			if (!apiList.contains(apiTmp)) {
				apiList.add(apiTmp);
			}
			PreferenceUtils.saveApiUrl(apiTmp);
		}

		softWareCode = mSoftwareCodeText.getText().toString();
		if (softWareCode != null && !"".equals(softWareCode)) {
			if (!softwareCodeList.contains(softWareCode)) {
				softwareCodeList.add(softWareCode);
			}
			PreferenceUtils.saveSoftWareCode(softWareCode);
		}
		dzUrl = dzAutoCompleteTextView.getText().toString();
		if (dzUrl != null && !"".equals(dzUrl)) {
			if (!dzIpList.contains(dzUrl)) {
				dzIpList.add(dzUrl);
			}
			PreferenceUtils.saveDZUrl(dzUrl);
		}

		serverIp = mOaDebugText.getText().toString()+ File.separator;
//		if(!serverIp.startsWith("http://")){
//			serverIp = "http://"+serverIp;
//		}
		if (serverIp != null && !"".equals(serverIp)) {
			if (!ServerIpList.contains(serverIp)) {
				ServerIpList.add(serverIp);
			}
			PreferenceUtils.saveServerIp(serverIp);
		}
		try {
			AutoCompleteBuild.saveAllToFile(loginList, debugList, pushList, imList, apiList,softwareCodeList,ServerIpList,dzIpList);
		} catch (Exception e) {
			e.printStackTrace();
		}


		if(PreferenceUtils.mContext == null) {
			PreferenceUtils.mContext = getApplicationContext();
		}
		ConcreteLogin.getInstance().mXRefreshInit(this,
				ResourceUtil.getConfString(getApplicationContext(), "client_conf_sdcard_root"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_ocu"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_company"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_multi_chat"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_vip"),
				ResourceUtil.getConfString(getApplicationContext(), "client_encrypt_cellphone"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_app_center_force_refresh"),
				ResourceUtil.getConfString(getApplicationContext(), "client_app_client_id"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "client_water_mark_enable"),
				ResourceUtil.getConfBoolean(getApplicationContext(), "file_download_forbidden"),
				PreferenceUtils.getImUrl(), PreferenceUtils.getPushUrl());


		/*敏行初始化*/
//		MXKitConfiguration config = new MXKitConfiguration.Builder(getApplicationContext())
//				// http server地址// 推送server地址
//				.hostOptions(PreferenceUtils.getImUrl(),// http server地址 修改
//						PreferenceUtils.getPushUrl())// 推送server地址
//						// SD卡目录
//				.sdCardCacheFolder(ResourceUtil.getConfString(getApplicationContext(), "client_conf_sdcard_root"))
//						// 设置是否在通讯录中显示公众号操作
//				.contactOcu(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_ocu"))
//						// 设置是否在通讯录中显示公司通讯录操作。
//				.contactCompany(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_company"))
//						// 设置是否在通讯录中显示群聊操作
//				.contactMultiChat(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_multi_chat"))
//						// 设置是否在通讯录中显示特别关注操作
//				.contactVip(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_vip"))
//						// 设置手机号码隐藏规则
//				.encryptCellphone(ResourceUtil.getConfString(getApplicationContext(), "client_encrypt_cellphone"))
//				.appForceRefresh(ResourceUtil.getConfBoolean(getApplicationContext(), "client_app_center_force_refresh"))
//				.appClientId(ResourceUtil.getConfString(getApplicationContext(), "client_app_client_id"))
//				.waterMarkEnable(ResourceUtil.getConfBoolean(getApplicationContext(), "client_water_mark_enable"))
//				.fileDownloadForbidden(ResourceUtil.getConfBoolean(getApplicationContext(), "file_download_forbidden")).build();
//		MXKit.getInstance().init(getApplicationContext(), config);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.oa_login_button:
				mOaLoginText.showDropDown();
				break;
			case R.id.oa_debug_button:
				mOaDebugText.showDropDown();
				break;
			case R.id.push_button:
				mPushText.showDropDown();
				break;
			case R.id.im_button:
				mImText.showDropDown();
				break;
			case R.id.api_button:
				mApiText.showDropDown();
				break;
			case R.id.serverip_button:
				mServerIpText.showDropDown();
				break;
			case R.id.dz_button:
				dzAutoCompleteTextView.showDropDown();
				break;
			case R.id.ok_button:
				saveAll();

				clean();
				this.finish();
				break;
			case R.id.cancel_button:
				this.finish();
				break;
			default:
				break;
		}

	}

	public void clean() {
		SecuritySharedPreference sp = new SecuritySharedPreference(this, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
		sp.edit().putString(PreferenceUtils.PREFERENCE_LAST_TIME, "1999-01-01 00:00:00").commit();
//                CacheDeleteUtils.cleanApplicationData(mContext, DBManager.databaseSdcardPath);     //会导致请求无法处理请稍后重试        ActivityManager mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        mActivityManager.clearApplicationUserData();
		String path = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getPath() + File.separator + "htmitech";
		}
		final String tmpPath = path;
		if (!"".equals(path)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					CacheDeleteUtils.clearFiles(tmpPath);
				}
			}).start();
		}
		PreferenceUtils.clearUser(this);
//                if (isreceipt == 1)
//                    ReceiptMessage(pushid);
		Intent finishIntent = new Intent(this, ClientTabActivity.class);

		finishIntent.setAction("finish");
		finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(finishIntent);
	}

}
