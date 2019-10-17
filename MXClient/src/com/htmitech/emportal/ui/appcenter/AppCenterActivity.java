package com.htmitech.emportal.ui.appcenter;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.minxing.client.RootActivity;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.ui.appcenter.AppCenterManager;
import com.minxing.kit.ui.appcenter.AppCenterManager.OnEditModeListener;
import com.minxing.kit.ui.appcenter.MXAppCenterView;

public class AppCenterActivity extends RootActivity {
	private MXAppCenterView appView = null;
	private ImageButton leftBackButton = null;
	private ImageButton rightAddButton = null;
	private Button rightTextButton = null;

	private AppCenterManager appCenterManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appcenter);

		appCenterManager = MXUIEngine.getInstance().getAppCenterManager();
		
		((TextView)findViewById(R.id.title_name)).setText("应用中心");

		leftBackButton = (ImageButton) findViewById(R.id.title_left_button);
		leftBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});

		rightAddButton = (ImageButton) findViewById(R.id.title_right_image_button);
		rightAddButton.setVisibility(View.VISIBLE);
		rightAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				appCenterManager.addApp(AppCenterActivity.this);
				overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			}
		});

		rightTextButton = (Button) findViewById(R.id.title_right_button);
		rightTextButton.setText("完成");

		appView = (MXAppCenterView) findViewById(R.id.app_center_view);
		appView.enableAppChangeMonitor();

		appCenterManager.setEditModeListener(new OnEditModeListener() {

			@Override
			public void onStartEditMode() {
				rightAddButton.setVisibility(View.GONE);
				rightTextButton.setVisibility(View.VISIBLE);
				rightTextButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						appCenterManager.endEdit();
					}
				});
			}

			@Override
			public void onEndEditMode() {
				rightAddButton.setVisibility(View.VISIBLE);
				rightTextButton.setVisibility(View.GONE);
			}
		});
		
		//启动帮助页面
//		if (PreferenceUtils.isLoginCenter(this)) {
//			PreferenceUtils.setLoginCenter(this, false);
//			Intent i = new Intent(this, HelpActivity.class);
//			i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.CENTER_HELPPAGE);
//			startActivity(i);
//		}
		
	}

	
	
	@Override
	protected void onResume() {
		if (appCenterManager != null) {
			appCenterManager.setAppView(appView);
		}
//		appView.load(false);
		appView.loadData(false);
		super.onResume();
	}

	@Override
	protected void onPause() {
		appView.endEdit();
		if (appCenterManager != null) {
			appCenterManager.clearAppView();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		appView.disableAppChangeMonitor();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishWithAnimation();
		}
		return false;
	}
	
}
