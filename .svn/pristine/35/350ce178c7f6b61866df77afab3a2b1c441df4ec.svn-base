package com.minxing.client.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;

import com.minxing.client.activity.GesturePasswordActivity;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class BackgroundDetector {
	public static BackgroundDetector instance;
	private Timer schedulePopGesturePwdViewTimer;
	private boolean activated = true;
	private boolean gesturePwdViewEnabled = false;
	private boolean detectorStop = false;
	private boolean passwordCheckActive = false;

	public boolean isPasswordCheckActive() {
		return passwordCheckActive;
	}

	public void setPasswordCheckActive(boolean passwordCheckActive) {
		this.passwordCheckActive = passwordCheckActive;
	}

	public void setDetectorStop(boolean detectorStop) {
		this.detectorStop = detectorStop;
	}

	public boolean isGesturePwdViewEnabled() {
		if (activated || detectorStop) {
			return false;
		}
		return gesturePwdViewEnabled;
	}

	public static BackgroundDetector getInstance() {
		if (instance == null) {
			instance = new BackgroundDetector();
		}
		return instance;
	}

	public void onAppStart(Context context) {
		activated = true;
		gesturePwdViewEnabled = false;
		if (schedulePopGesturePwdViewTimer != null) {
			schedulePopGesturePwdViewTimer.cancel();
			schedulePopGesturePwdViewTimer = null;
		}
	}

	public void onAppPause(final Context context) {
		Handler handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				if (isScreenLocked(context) || isApplicationSentToBackground(context)) {
					onBackgeround(context);
				}
				return false;
			}
		});
		handler.sendMessage(new Message());
	}

	private void onBackgeround(Context context) {
		MXCurrentUser currentUser = MXAPI.getInstance(context).currentUser();
		
		if (detectorStop || currentUser == null) {
			return;
		}
		if (PreferenceUtils.isGesturePwdEnable(context, currentUser.getLoginName())
				&& PreferenceUtils.isInitGesturePwd(context, currentUser.getLoginName())) {
			activated = false;
			schedulePopGesturePwdView(context);
		}
	}

	private void schedulePopGesturePwdView(final Context context) {
		if (schedulePopGesturePwdViewTimer != null) {
			schedulePopGesturePwdViewTimer.cancel();
			schedulePopGesturePwdViewTimer = null;
		}
		schedulePopGesturePwdViewTimer = new Timer();
		schedulePopGesturePwdViewTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (activated || detectorStop) {
					return;
				}
				Intent intent = new Intent(context, GesturePasswordActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

				if (!isApplicationSentToBackground(context)) {
					intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_FORCE);
					context.startActivity(intent);
				}
				gesturePwdViewEnabled = true;
			}
		}, 0);
//		}, 3 * 1000);
	}

	@SuppressWarnings("deprecation")
	public boolean isApplicationSentToBackground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean isScreenLocked(Context context) {
		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		return !powerManager.isScreenOn();
	}
}
