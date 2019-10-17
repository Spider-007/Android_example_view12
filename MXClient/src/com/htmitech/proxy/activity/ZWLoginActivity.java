package com.htmitech.proxy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.proxy.util.CacheActivity;
import com.htmitech.zhiwen.IBackMainOnClick;
import com.htmitech.zhiwen.ZWUtils;
import com.minxing.client.AppConstants;
import com.minxing.client.ClientTabActivity;

import java.text.ParseException;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class ZWLoginActivity extends BaseFragmentActivity implements OnClickListener,IBackMainOnClick {
	private ImageView iv_default;
	private TextView default_tv;
	private TextView tv_name;
	private TextView tv_wakeup;
	private ImageView iv_wakeup;
	private TextView tv_other_login;
	public static final String LOGIN_ACTIVITY = "login";
	private ZWUtils mZWUtil;
	private Intent intent;
	private boolean isLogin = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zw_login);
		CacheActivity.addActivity(this);
		initView();
		initData();
	}

	public void initView(){
		iv_default = (ImageView) findViewById(R.id.iv_default);
		default_tv = (TextView) findViewById(R.id.default_tv);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_wakeup = (TextView) findViewById(R.id.tv_wakeup);
		iv_wakeup = (ImageView) findViewById(R.id.iv_wakeup);
		tv_other_login = (TextView) findViewById(R.id.tv_other_login);
	}

	public void initData(){
		intent = getIntent();
		isLogin = intent.getBooleanExtra(LOGIN_ACTIVITY,false);
		try {
			BookInit.getInstance().getPhotoBitMap(this, iv_default, default_tv);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tv_wakeup.setOnClickListener(this);
		iv_wakeup.setOnClickListener(this);
		tv_other_login.setOnClickListener(this);
		tv_name.setText(PreferenceUtils.getOAUserName(this));
//		mZWUtil = new ZWUtils(isLogin,this);
//		mZWUtil.initFingure(ZWLoginActivity.this);
	}
	protected void finishWithAnimation() {
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mZWUtil == null){
			mZWUtil = new ZWUtils(ZWLoginActivity.this,isLogin,this);
		}

		mZWUtil.initFingure();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.iv_wakeup:
			case R.id.tv_wakeup:
				mZWUtil.initFingure();
				break;
			case R.id.tv_other_login:
				Intent finishIntent = new Intent(this, ClientTabActivity.class);
				finishIntent.setAction("finish");
				finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finishIntent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP,
						getIntent().getBooleanExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false));
				finishIntent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, getIntent().getIntExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1));
				startActivity(finishIntent);
				finish();
				break;
		}
	}
	public void onBack(){
		if(isLogin){
			intent = new Intent(this, ClientTabActivity.class);
			startActivity(intent);
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onMainOnClick() {
		onBack();
	}
	//屏蔽返回键
	@Override
	public void onBackPressed() {
		return;
	}
	@Override
	public void onLoginOnClick() {
		Intent finishIntent = new Intent(this, ClientTabActivity.class);
		finishIntent.setAction("finish");
		finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finishIntent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP,
				getIntent().getBooleanExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false));
		finishIntent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, getIntent().getIntExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1));
		startActivity(finishIntent);
		finish();
	}
}
