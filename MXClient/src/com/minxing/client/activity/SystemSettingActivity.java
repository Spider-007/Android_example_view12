package com.minxing.client.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.RootActivity;
import com.minxing.client.service.UpgradeService;
import com.minxing.client.service.ViewCallBack;
import com.minxing.client.upgrade.AppUpgradeInfo;
import com.minxing.client.util.Utils;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.ResourceUtil;

public class SystemSettingActivity extends RootActivity {

	private TextView newflag = null;
	private TextView upgrade_desc = null;
	private String upgrade_url = null;

	private LinearLayout message_notification = null;
	private LinearLayout gesture_password = null;
	private LinearLayout change_password = null;
	private LinearLayout upgrade = null;
	private LinearLayout exit_btn_layout = null;
	private LinearLayout mobil_contacts = null;
	private LinearLayout function_introdution = null;

	private ImageButton backButton = null;
	private AppUpgradeInfo upgradeInfo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_setting);

		((TextView) findViewById(R.id.title_name)).setText(R.string.title_setting);

		backButton = (ImageButton) findViewById(R.id.title_left_button);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});
		exit_btn_layout = (LinearLayout) findViewById(R.id.exit_btn_layout);
		exit_btn_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Utils.showSystemDialog(SystemSettingActivity.this, getResources().getString(R.string.system_tip), SystemSettingActivity.this
						.getResources().getString(R.string.warning_logout), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ConcreteLogin.getInstance().logout(SystemSettingActivity.this, new htmitech.com.componentlibrary.listener.MXKitLogoutListener() {
							@Override
							public void onLogout() {
								Intent finishIntent = new Intent(SystemSettingActivity.this, ClientTabActivity.class);
								finishIntent.setAction("finish");
								finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(finishIntent);
							}
						});
//						MXKit.getInstance().logout(SystemSettingActivity.this, new MXKitLogoutListener() {
//							@Override
//							public void onLogout() {
//								Intent finishIntent = new Intent(SystemSettingActivity.this, ClientTabActivity.class);
//								finishIntent.setAction("finish");
//								finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//								startActivity(finishIntent);
//							}
//						});
					}
				}, null, true);
			}
		});

		upgrade_desc = (TextView) this.findViewById(R.id.upgrade_desc);
		upgrade_desc.setText(String.format(getString(R.string.version_upgrade), ResourceUtil.getVerName(this)));
		newflag = (TextView) this.findViewById(R.id.newflag);

		gesture_password = (LinearLayout) findViewById(R.id.gesture_password);
		gesture_password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SystemSettingActivity.this, SystemSettingGesturePasswordActivity.class);
				startActivity(intent);
			}
		});

		change_password = (LinearLayout) findViewById(R.id.change_password);
		if (ResourceUtil.getConfBoolean(this, "client_sms_password_login_enable")
				|| !ResourceUtil.getConfBoolean(this, "client_enable_reset_password", true)) {
			change_password.setVisibility(View.GONE);
		} else {
			change_password.setVisibility(View.VISIBLE);
			change_password.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConcreteLogin.getInstance().changePassword(SystemSettingActivity.this);
				}
			});
		}

		message_notification = (LinearLayout) findViewById(R.id.message_notification);
		message_notification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SystemSettingActivity.this, SystemSettingMessageNotificationActivity.class);
				startActivity(intent);
			}
		});

		upgrade = (LinearLayout) findViewById(R.id.upgrade);
		upgrade.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				upgrade();
			}
		});

		if (Utils.checkConnectState(this)) {
			String clientId = ResourceUtil.getConfString(SystemSettingActivity.this, "client_app_client_id");
			new UpgradeService().checkUpgrade(SystemSettingActivity.this,new AppliationCenterDao(SystemSettingActivity.this).getAppClientEnvtype(ResourceUtil.getVerCode(this))+"", ResourceUtil.getVerCode(this), false, new ViewCallBack(this) {
				@Override
				public void success(Object obj) {
					upgradeInfo = (AppUpgradeInfo) obj;
					if (!upgradeInfo.isNeedUpgrade()) {
						return;
					}
					upgrade_url = upgradeInfo.getUpgrade_url();
					if (upgrade_url == null || "".equals(upgrade_url) || !upgrade_url.startsWith("http")) {
						return;
					}
					newflag.setTag(upgrade_url);
					newflag.setVisibility(View.VISIBLE);
				}
			});
		}

		mobil_contacts = (LinearLayout) findViewById(R.id.mobil_contacts);
		mobil_contacts.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConcreteLogin.getInstance().contactsConfig(SystemSettingActivity.this);
			}
		});

//		LinearLayout mx_ui_custom = (LinearLayout) findViewById(R.id.mx_ui_custom);
//		mx_ui_custom.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(SystemSettingActivity.this, UICustomActivity.class);
//				startActivity(intent);
//			}
//		});
//
//		// 链接地址为 baseUrl+/introduces/index.html?v=5.0.0 最后拼当前版本号。
//		function_introdution = (LinearLayout) findViewById(R.id.function_introdution);
//
//		if (ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_introduction")) {
//			function_introdution.setVisibility(View.VISIBLE);
//			function_introdution.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					String url = ResourceUtil.getConfString(SystemSettingActivity.this, "client_conf_http_host") + "/introduces/index.html?v="
//							+ ResourceUtil.getVerCode(SystemSettingActivity.this);
//					Intent intent = new Intent(SystemSettingActivity.this, SystemFunctionIntrodutionActivity.class);
//					intent.putExtra("introdution_url", url);
//					startActivity(intent);
//				}
//			});
//		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishWithAnimation();
		}
		return false;
	}

	public void upgrade() {
		if (!Utils.checkConnectState(this)) {
			Utils.toast(this, R.string.upgrade_network_disable, Toast.LENGTH_SHORT);
			return;
		}

		if (newflag.getVisibility() == View.GONE) {
			Utils.toast(this, R.string.upgrade_no_new_version, Toast.LENGTH_SHORT);
			return;
		}
		Utils.showUpgradeDialog(this, upgradeInfo);
	}
}