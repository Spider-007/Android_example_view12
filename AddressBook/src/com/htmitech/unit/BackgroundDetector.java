package com.htmitech.unit;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;

import com.htmitech.api.BookInit;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import htmitech.com.componentlibrary.content.ConcreteLogin;

public class BackgroundDetector {
	public static BackgroundDetector instance;
	private Timer schedulePopGesturePwdViewTimer;
	private boolean activated = true;
	private boolean gesturePwdViewEnabled = false;
	private boolean gestureZWPwdViewEnabled = false;
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
	public boolean isGestureZWPwdViewEnabled() {
		if (activated || detectorStop) {
			return false;
		}
		return gestureZWPwdViewEnabled;
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
		gestureZWPwdViewEnabled = false;
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

		if (detectorStop || ConcreteLogin.getInstance().isCurrentUserNull(context)) {
			return;
		}
		try{
			if (BookInit.getInstance().getmCallbackMX().isGesturePwdEnable()
					&& BookInit.getInstance().getmCallbackMX().isInitGesturePwd()) {
				activated = false;
				schedulePopGesturePwdView(context,1);
			}else if(BookInit.getInstance().getmCallbackMX().isZWGensturePswd()){
				activated = false;
				schedulePopGesturePwdView(context,3);
			}
		}catch (Exception e){

		}

	}

	private void schedulePopGesturePwdView(final Context context,final int type) {
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

				if (!isApplicationSentToBackground(context)) {
					BookInit.getInstance().getmCallbackMX().setIntent(context,type);
				}
				if(type == 1){
					gesturePwdViewEnabled = true;
				}else if(type == 3){
					gestureZWPwdViewEnabled = true;
				}


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
