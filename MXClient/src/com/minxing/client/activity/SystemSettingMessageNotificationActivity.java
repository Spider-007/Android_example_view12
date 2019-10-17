package com.minxing.client.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.minxing.client.RootActivity;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;

public class SystemSettingMessageNotificationActivity extends RootActivity {

	private RelativeLayout new_message_notification_off = null;
	private RelativeLayout new_message_notification_on = null;

	private TextView message_notification_off = null;
	private TextView message_notification_on = null;

	private TextView notification_sound_check = null;
	private TextView notification_shake_check = null;

	private LinearLayout new_message_mail_notification;
	private TextView mail_notification;

	private LinearLayout message_disturb = null;

	private ImageButton backButton = null;
	private Vibrator vib = null;

	private String loginName = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_setting_message_notification);
		loginName = PreferenceUtils.getLogin_name(this);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		((TextView) findViewById(R.id.title_name)).setText(R.string.setting_message_notification);
		backButton = (ImageButton) findViewById(R.id.title_left_button);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishWithAnimation();
			}
		});

		message_disturb = (LinearLayout) findViewById(R.id.message_disturb);
		message_disturb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SystemSettingMessageNotificationActivity.this, SystemSettingDisturbActivity.class);
				startActivity(intent);
			}
		});

		new_message_notification_off = (RelativeLayout) findViewById(R.id.new_message_notification_off);
		new_message_notification_on = (RelativeLayout) findViewById(R.id.new_message_notification_on);

		message_notification_off = (TextView) findViewById(R.id.message_notification_off);
		message_notification_off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PreferenceUtils.enableMessageNotification(SystemSettingMessageNotificationActivity.this, loginName);
				new_message_notification_on.setVisibility(View.VISIBLE);
				new_message_notification_off.setVisibility(View.GONE);
			}
		});
		message_notification_on = (TextView) findViewById(R.id.message_notification_on);
		message_notification_on.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PreferenceUtils.disableMessageNotification(SystemSettingMessageNotificationActivity.this, loginName);
				new_message_notification_on.setVisibility(View.GONE);
				new_message_notification_off.setVisibility(View.VISIBLE);
			}
		});
		
		if (PreferenceUtils.isMessageNotification(this, loginName)) {
			new_message_notification_on.setVisibility(View.VISIBLE);
			new_message_notification_off.setVisibility(View.GONE);
		} else {
			new_message_notification_on.setVisibility(View.GONE);
			new_message_notification_off.setVisibility(View.VISIBLE);
		}
		
		new_message_mail_notification = (LinearLayout)findViewById(R.id.new_message_mail_notification);
		if(ResourceUtil.getConfBoolean(this, "client_show_mail")){
			new_message_mail_notification.setVisibility(View.VISIBLE);
		} else {
			new_message_mail_notification.setVisibility(View.GONE);
		}
		
		mail_notification = (TextView) findViewById(R.id.mail_notification);
		mail_notification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PreferenceUtils.isMailNotification(SystemSettingMessageNotificationActivity.this, loginName)) {
					PreferenceUtils.disableMailNotification(SystemSettingMessageNotificationActivity.this, loginName);
					mail_notification.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_normal, 0);
				} else {
					PreferenceUtils.enableMailNotification(SystemSettingMessageNotificationActivity.this, loginName);
					mail_notification.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_selected, 0);
				}
			}
		});

		notification_sound_check = (TextView) findViewById(R.id.notification_sound_check);
		if (PreferenceUtils.isNotificationSound(this, loginName)) {
			notification_sound_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_selected, 0);
		} else {
			notification_sound_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_normal, 0);
		}

		notification_sound_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PreferenceUtils.isNotificationSound(SystemSettingMessageNotificationActivity.this, loginName)) {
					PreferenceUtils.disableNotificationSound(SystemSettingMessageNotificationActivity.this, loginName);
					notification_sound_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_normal, 0);
				} else {
					PreferenceUtils.enableNotificationSound(SystemSettingMessageNotificationActivity.this, loginName);
					notification_sound_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_selected, 0);
				}
			}
		});

		notification_shake_check = (TextView) findViewById(R.id.notification_shake_check);
		if (PreferenceUtils.isNotificationShake(this, loginName)) {
			notification_shake_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_selected, 0);
		} else {
			notification_shake_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_normal, 0);
		}

		notification_shake_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PreferenceUtils.isNotificationShake(SystemSettingMessageNotificationActivity.this, loginName)) {
					PreferenceUtils.disableNotificationShake(SystemSettingMessageNotificationActivity.this, loginName);
					notification_shake_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_normal, 0);
				} else {
					vib.vibrate(new long[] { 300, 300, 300, 300 }, -1);
					PreferenceUtils.enableNotificationShake(SystemSettingMessageNotificationActivity.this, loginName);
					notification_shake_check.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mx_icon_checkbox_selected, 0);
				}
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
}