package com.htmitech.emportal.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.R;

public class CommonUtils {

	/**
	 * 返回图片
	 * 
	 * @return
	 */
	// public static int getErrorImage() {
	// int mEmptyImageView = 0;
	// int num = new Random().nextInt(2) + 1;
	// switch (num) {
	// case 1:
	// mEmptyImageView = R.drawable.empty_view_image_1;
	// break;
	// case 2:
	// mEmptyImageView = R.drawable.empty_view_image_2;
	// break;
	// default:
	// mEmptyImageView = R.drawable.empty_view_image_1;
	// break;
	// }
	// LogUtil.v("wb", "随机数" + num + "\t 资源名称:" + mEmptyImageView);
	// return mEmptyImageView;
	// }

	public static int getErrorTipStr() {
		int mEmptyImageView = 0;
		int num = new Random().nextInt(2) + 1;
		switch (num) {
		case 1:
			mEmptyImageView = R.string.empty_view_reason1;
			break;
		case 2:
			mEmptyImageView = R.string.empty_view_reason2;
			break;
		default:
			mEmptyImageView = R.string.empty_view_reason1;
			break;
		}
		LogUtil.v("wb", "随机数" + num + "\t 资源名称:" + mEmptyImageView);
		return mEmptyImageView;
	}

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";

	public static String formatPretty(Long timeMillis) {
		if (timeMillis == null)
			return "";
		Date date = new Date(timeMillis.longValue() * 1000);
		return format(date);
	}

	public static String format(Date date) {
		long delta = new Date(System.currentTimeMillis()).getTime()
				- date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2
						.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

	private static boolean isLastDate(Date date) {
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -1);
		Date date1 = cal1.getTime();
		if (isSameDate(date, date1)) {
			return true;
		}
		return false;
	}

	private static boolean isSameYear(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		return isSameYear;
	}

	/**
	 * 防止注入javascript漏洞
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isUrlValid(String url) {
		Pattern patt = Pattern
				.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
		Matcher matcher = patt.matcher(url);
		boolean isMatch = matcher.matches();

		if (isMatch == false || url.contains("javascript")) { // ||
																// !url.contains(ServerUrlConstant.SERVER_BASE_URL_H5()))
																// {
			return false;
		}

		return true;
	}

	public static final String CHANNEL_NAME = "BaiduMobAd_CHANNEL";

	/*
	 * 获取AndroidManifest里设置的渠道号
	 */
	public static String getMetaDataValue(Context c) {
		String value = "";
		// 防止潜在内存泄露
		if (c == null) {
			return value;
		}

		Context context = c.getApplicationContext();
		PackageManager packageManager = context.getPackageManager();
		ApplicationInfo applicationInfo;
		try {
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (applicationInfo != null && applicationInfo.metaData != null) {
				value = applicationInfo.metaData.getString(CHANNEL_NAME);
			}
		} catch (NameNotFoundException e) {
			throw new RuntimeException(
					"Could not read the name in the manifest file.", e);
		}
		if (TextUtils.isEmpty(value)) {
			throw new RuntimeException("The name '" + CHANNEL_NAME
					+ "' is not defined in the manifest file's meta data.");
		}
		return value;
	}

}
