package utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.util.regex.Pattern;

/**
 * 设备相关信息类.
 * 
 * 
 */
public class DeviceUtils {
	public static final int CPU_TYPE_UNDEFIND = 0; // 未指定的指令集
	public static final int CPU_TYPE_ARM = 1; // ARM 指令集
	public static final int CPU_TYPE_X86 = 2; // x86 指令集
	public static final int CPU_TYPE_MIPS = 3; // mips 指令集
	private static int sCPUType = CPU_TYPE_UNDEFIND; // 默认未指定cpu架构

	private DeviceUtils() {

	}

	/**
	 * 获取设备机型.
	 * 
	 * @return 设备机型字符串
	 */
	public static String getDeviceType() {
		return Build.MODEL;
	}

	/**
	 * 获取系统版本.
	 * 
	 * @return 当前版本
	 */
	public static String getSystemVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取应用版本名称.
	 * 
	 * @param context
	 *            上下文
	 * @return 应用版本名
	 */
	public static String getAppVersionName(Context context) {
		PackageInfo pi = getPackageInfo(context);
		if (null != pi) {
			return pi.versionName;
		}
		return null;
	}

	/**
	 * 获取版本号。
	 * 
	 * @param context
	 *            上下文
	 * @return 应用版本号
	 */
	public static int getAppVersionCode(Context context) {
		PackageInfo pi = getPackageInfo(context);
		if (null != pi) {
			return pi.versionCode;
		}
		return -1;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;
		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * 获取设备号，imei号。
	 * 
	 * @param context
	 *            上下文
	 * @return 设备ID
	 */
	public static String getDeviceId(Context context) {
		String imeiNum = "";

		if (null != context) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (null != tm) {
				imeiNum = tm.getDeviceId();
			}
		}

		return imeiNum;
	}

	/**
	 * 获取wifi地址。
	 * 
	 * @param context
	 *            上下文
	 * @return mac地址
	 */
	public static String getWifiMacAddress(Context context) {
		String wifiMac = "";
		// 加入try catch 解决线上崩溃
		try {
			if (null != context) {
				WifiManager wm = (WifiManager) context
						.getSystemService(Context.WIFI_SERVICE);
				if (null != wm && null != wm.getConnectionInfo()) {
					wifiMac = wm.getConnectionInfo().getMacAddress();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return wifiMac;
	}

	/**
	 * 获取显示矩阵。
	 * 
	 * @param context
	 *            上下文
	 * @return 返回矩阵
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		try {
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dm;
	}

	/**
	 * 获取屏幕宽度。
	 * 
	 * @param context
	 *            上下文
	 * @return 宽度
	 */
	public static int getScreenWidthPx(Context context) {
		return getDisplayMetrics(context).widthPixels;
	}

	/**
	 * 获取屏幕高度。
	 * 
	 * @param context
	 *            上下文
	 * @return 屏幕高度
	 */
	public static int getScreenHeightPx(Context context) {
		return getDisplayMetrics(context).heightPixels;
	}

	/**
	 * dip转px。
	 * 
	 * @param context
	 *            上下文
	 * @param pxValue
	 *            dip
	 * @return px
	 */
	public static int dip2px(Context context, float pxValue) {
		return Math.round(pxValue
				* context.getResources().getDisplayMetrics().density);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取当前手机的类型.
	 * 
	 * @return DeviceUtils.CPU_TYPE_ARM or DeviceUtils.CPU_TYPE_X86 or
	 *         DeviceUtils.CPU_TYPE_MIPS or
	 */
	public static int getCpuType() {
		// 已经检查过cpu类型，直接返回
		if (sCPUType != CPU_TYPE_UNDEFIND) {
			return sCPUType;
		}

		int result = CPU_TYPE_UNDEFIND;

		String cpuType = Build.CPU_ABI;

		if (cpuType.indexOf("arm") >= 0) {
			result = CPU_TYPE_ARM;
		} else if (cpuType.indexOf("x86") >= 0) {
			result = CPU_TYPE_X86;
		} else if (cpuType.indexOf("mips") >= 0) {
			result = CPU_TYPE_MIPS;
		}

		// 缓存cpu类型
		sCPUType = result;

		return result;
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 *            当前手机号
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	public static long getAvailableSpace(File dir) {
		try {
			final StatFs stats = new StatFs(dir.getPath());
			return (long) stats.getBlockSize()
					* (long) stats.getAvailableBlocks();
		} catch (Throwable e) {
			return -1;
		}

	}

	public static int getScreenScal(Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (scale);
	}

}
