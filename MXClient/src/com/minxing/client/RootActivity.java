package com.minxing.client;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.unit.BackgroundDetector;
import com.minxing.client.activity.GesturePasswordActivity;
import com.umeng.analytics.MobclickAgent;

import cn.feng.skin.manager.base.BaseActivity;
import htmitech.com.componentlibrary.api.ComponentInit;

public class RootActivity extends BaseActivity {
	private ComponentName componentName = getComponentName();
	private long startActivityTime = 0;
	private final long DELAYTIME = 1000; // 1 SECOND
	private boolean mHandleStatusColor = true;
	public boolean isStart = true;
	@Override
	public void startActivity(Intent intent) {
		if (componentName == null || intent.getComponent() == null || SystemClock.uptimeMillis() - startActivityTime > DELAYTIME
				|| !intent.getComponent().equals(componentName)) {
			super.startActivity(intent);

			if (getParent() != null) {
				getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			} else {
				overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			}

			startActivityTime = SystemClock.uptimeMillis();
			componentName = intent.getComponent();
		}
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		if (componentName == null || intent.getComponent() == null || SystemClock.uptimeMillis() - startActivityTime > DELAYTIME
				|| !intent.getComponent().equals(componentName)) {
			BackgroundDetector.getInstance().setDetectorStop(true);
			super.startActivityForResult(intent, requestCode);

			if (getParent() != null) {
				getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			} else {
				overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			}

			startActivityTime = SystemClock.uptimeMillis();
			componentName = intent.getComponent();
		}
	}

	protected void finishWithAnimation() {

		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}

	@Override
	protected void onPause() {
		super.onPause();
		com.htmitech.unit.BackgroundDetector.getInstance().onAppPause(this);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStart() {
		if(isStart){
			if (com.htmitech.unit.BackgroundDetector.getInstance().isGesturePwdViewEnabled()) {
				Intent intent = new Intent(this, GesturePasswordActivity.class);
				intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
				intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
				startActivity(intent);
				com.htmitech.unit.BackgroundDetector.getInstance().setPasswordCheckActive(true);
			}else if(com.htmitech.unit.BackgroundDetector.getInstance().isGestureZWPwdViewEnabled()){
				BookInit.getInstance().getmCallbackMX().setIntent(this,3);

				com.htmitech.unit.BackgroundDetector.getInstance().setPasswordCheckActive(true);
			}
			com.htmitech.unit.BackgroundDetector.getInstance().setDetectorStop(false);
			com.htmitech.unit.BackgroundDetector.getInstance().onAppStart(this);
		}
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mHandleStatusColor) {
//			handleStatusBarColor();
		}
	}

	@TargetApi(21/* Build.VERSION_CODES.LOLLIPOP */)
//	private void handleStatusBarColor() {
//		if (Build.VERSION.SDK_INT >= 21/* Build.VERSION_CODES.LOLLIPOP */) {
//			Window window = getWindow();
//			// clear FLAG_TRANSLUCENT_STATUS flag:
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//			// finally change the color
//			window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));
//		}
//	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {

			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();

			if (isShouldHideInput(v, ev)) {
				hideSoftInput(v.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	/**
	 * 多种隐藏软件盘方法的其中一种
	 *
	 * @param token
	 */
	private void hideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	protected void setHandleStatusColor(boolean isEnable) {
		mHandleStatusColor = isEnable;
	}

}
