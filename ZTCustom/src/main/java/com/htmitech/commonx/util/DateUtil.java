package com.htmitech.commonx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 关于日期方面的方法。
 * @author thinkleesion
 *
 */
public class DateUtil {
	
	public enum DATE_PATTERN {
		/**
		 * yyyy-MM-dd
		 */
		pattern1("yyyy-MM-dd"),
		
		/**
		 * yyyy年MM月dd日
		 */
		pattern2("yyyy年MM月dd日");
		
		private String pattern;
		
		private DATE_PATTERN(String s){
			pattern = s;
		}
		
		public String getPattern() {
			return pattern;
		}
	}
	
	/**
	 * 将时间戳转化成日期*后端传过来的时间戳少3位。
	 * @param l 精确到毫秒，共13位。
	 * @return
	 */
	public static String Date2String (Long l, DATE_PATTERN p) {
		SimpleDateFormat instance = (SimpleDateFormat)SimpleDateFormat.getDateInstance();
		Date date = new Date(l);
		instance.applyPattern(p.getPattern());
		return instance.format(date);
	}
	
	/**
	 * 将时间戳转化成日期*后端传过来的时间戳少3位。
	 * @param s 精确到毫秒，共13位。
	 * @return
	 */
	public static String Date2String (String s, DATE_PATTERN p) {
		try {
			Long l = Long.valueOf(s);
			return Date2String(l, p);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}
}
