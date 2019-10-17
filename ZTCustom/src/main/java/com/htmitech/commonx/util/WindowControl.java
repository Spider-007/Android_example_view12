package com.htmitech.commonx.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Android窗口控制类,外部通过静态方法进行调用
 * 
 * @author yuankai
 * @version 1.0
 * 
 * @author Jeremy
 * @date 2013-08-07
 */
public class WindowControl {

	/**
	 * 设置是否显示标题
	 * 
	 * @param hasTitle 是否有标题
	 */
	public static void setHasTitle(Activity activity, boolean hasTitle) {
		if (hasTitle) {
			activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		} else {
			activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		int width = 0;
		if (null != context) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			if (null != wm) {
				width = wm.getDefaultDisplay().getWidth();
			}
		}
		return width;
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		int height = 0;
		if (null != context) {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			if (null != wm) {
				height = wm.getDefaultDisplay().getHeight();
			}
		}
		return height;
	}

	/**
	 * 获取是否全屏
	 * 
	 * @return 是否全屏
	 */
	public static boolean getIsFullScreen(Activity activity) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
		return (attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
	}

	/**
	 * 设置是否全屏
	 * 
	 * @param activity
	 *            上下文
	 * @param isFullScreen
	 *            是否全屏
	 */
	public static void setFullScreen(Activity activity, boolean isFullScreen) {
		if (isFullScreen) {
			// go full screen
			WindowManager.LayoutParams attrs = activity.getWindow()
					.getAttributes();
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			activity.getWindow().setAttributes(attrs);
			// activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			// go non-full screen
			WindowManager.LayoutParams attrs = activity.getWindow()
					.getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attrs);
			// activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	/**
	 * 伸展通知栏
	 * 
	 * @throws Exception
	 *             invoke方法报的异常
	 */
	public static void expendNotification(Activity activity) throws Exception {
		Object service = activity.getSystemService("statusbar");
		if (service != null) {
			Method expand = service.getClass().getMethod("expand");
			expand.invoke(service);
		}
	}

	/**
	 * 设置当前屏幕是否自动锁屏
	 * 
	 * @param activity
	 *            上下文
	 * @param isLock
	 *            是否锁屏
	 */
	public static void setScreenAutoLock(final Activity activity,
			final boolean isLock) {
		Window window = activity.getWindow();
		if (isLock) {
			window.setFlags(0, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	/**
	 * 获取是否自动锁屏
	 * 
	 * @param activity
	 * @return boolean
	 */
	public static boolean getScreenAutoLock(Activity activity) {
		WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
		return (attrs.flags & WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0;
	}

	/**
	 * 设置当前屏的方向设置
	 * 
	 * @param activity
	 *            上下文
	 * @param orietation
	 *            方向值
	 */
	public static void setRequestedOrientation(Activity activity, int orietation) {
		activity.setRequestedOrientation(orietation);
	}

	public static int getRequestedOrientation(final Activity activity) {
		return activity.getRequestedOrientation();
	}

	/**
	 * 获取当前手机屏幕方向
	 * 
	 * @param activity
	 *            活动
	 * @return ActivityInfo的常量值
	 */
	public static int getScreenOrientation(Activity activity) {
		return activity.getResources().getConfiguration().orientation;
	}

	/**
	 * 设置当前屏的亮度
	 * 
	 * @param activity
	 * @param brightness
	 *            亮度值，0-1小数
	 */
	public static void setScreenBrightness(Activity activity, float brightness) {
		Window window = activity.getWindow();
		WindowManager.LayoutParams attrs = window.getAttributes();
		attrs.screenBrightness = brightness;
		window.setAttributes(attrs);
	}

	/**
	 * 判断是否开启了亮度自动调节。
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isAutoBrightness(final Activity activity) {
		boolean automicBrightness = false;
		ContentResolver cr = activity.getContentResolver();
		if (cr == null) {
			return false;
		}
		try {
			int mod = System.getInt(cr, System.SCREEN_BRIGHTNESS_MODE);
			if (mod == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
				automicBrightness = true;
			}
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}

		return automicBrightness;
	}

	/**
	 * 获取当前屏的粒度
	 * 
	 * @param activity
	 * @return
	 */
	public static float getDensity(final Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.density;
	}

	public static float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	public static void setLayoutFlag(final Activity activity, final int flag) {
		activity.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		activity.getWindow().addFlags(flag);
	}

}
