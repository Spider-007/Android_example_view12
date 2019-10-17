package com.htmitech.commonx.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 文件编码相关的一些工具方法
 * 
 * @author yuankai
 * @version 1.0
 * @date 2011-1-19
 */
public class MiscUtil {
	/**
	 * 获取文件扩展名
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 扩展名
	 */
	public static String getExtention(String filePath) {
		if (filePath == null || filePath.length() == 0)
			return null;

		String ext = null;
		int index = filePath.lastIndexOf('.');
		if (index > 0 && index < filePath.length() - 1) {
			ext = filePath.substring(index + 1);
		}
		return ext;
	}

	public static String getExtension(String fileName) {
		String myExtension = "";

		if (!TextUtils.isEmpty(fileName)) {
			final int index = fileName.lastIndexOf('.');
			myExtension = (index != -1) ? fileName.substring(index + 1)
					.toLowerCase().intern() : "";
		}

		return myExtension;
	}

	private static boolean isHexDigit(char ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f')
				|| (ch >= 'A' && ch <= 'F');
	}

	public static String decodeHtmlReference(String name) {
		int index = 0;
		while (true) {
			index = name.indexOf('%', index);
			if (index == -1 || index >= name.length() - 2) {
				break;
			}
			if (isHexDigit(name.charAt(index + 1))
					&& isHexDigit(name.charAt(index + 2))) {
				char c = 0;
				try {
					c = (char) Integer.decode(
							"0x" + name.substring(index + 1, index + 3))
							.intValue();
				} catch (NumberFormatException e) {
				}
				name = name.substring(0, index) + c + name.substring(index + 3);
			}
			index = index + 1;
		}
		return name;
	}

	public static <T> boolean equals(T o0, T o1) {
		return (o0 == null) ? (o1 == null) : o0.equals(o1);
	}

	public static <T> boolean listsEquals(List<T> list1, List<T> list2) {
		if (list1 == null) {
			return list2 == null || list2.isEmpty();
		}
		if (list1.size() != list2.size()) {
			return false;
		}
		return list1.containsAll(list2);
	}

	public static <KeyT, ValueT> boolean mapsEquals(Map<KeyT, ValueT> map1,
			Map<KeyT, ValueT> map2) {
		if (map1 == null) {
			return map2 == null || map2.isEmpty();
		}
		if (map1.size() != map2.size()
				|| !map1.keySet().containsAll(map2.keySet())) {
			return false;
		}
		for (KeyT key : map1.keySet()) {
			final ValueT value1 = map1.get(key);
			final ValueT value2 = map2.get(key);
			if (!equals(value1, value2)) {
				return false;
			}
		}
		return true;
	}

	public static boolean matchesIgnoreCase(String text, String lowerCasePattern) {
		return (text.length() >= lowerCasePattern.length())
				&& (text.toLowerCase().indexOf(lowerCasePattern) >= 0);
	}

	public static String getTitle(String fileName) {
		String title = "";

		if (!TextUtils.isEmpty(fileName)) {
			// fileName.lastIndexOf("\\");
			final int index = fileName.lastIndexOf('.');
			title = (index != -1) ? fileName.substring(0, index).toLowerCase()
					.intern() : "";
		}

		return title;
	}

	public static String getFolderNameByPath(String path) {
		if (TextUtils.isEmpty(path)) {
			return "";
		}
		String folderName = "";

		int idx = path.lastIndexOf("/");

		if (idx != -1) {
			folderName = path.substring(idx + 1);
		}

		return folderName;
	}

	/**
	 * 通过path获取title，不含.extension
	 * 
	 * @param path
	 * @return
	 */
	public static String getTitleByPath(String path) {
		if (TextUtils.isEmpty(path)) {
			return "";
		}
		String fileName = "";

		int idx = path.lastIndexOf("/");

		if (idx != -1) {
			fileName = path.substring(idx + 1);
		}

		return MiscUtil.getTitle(fileName);
	}

	public static boolean isLetter(char ch) {
		return (('a' <= ch) && (ch <= 'z')) || (('A' <= ch) && (ch <= 'Z'))
				||
				// ' is "letter" (in French, for example)
				(ch == '\'')
				||
				// ^ is "letter" (in Esperanto)
				(ch == '^')
				||
				// latin1
				((0xC0 <= ch) && (ch <= 0xFF) && (ch != 0xD7) && (ch != 0xF7))
				||
				// extended latin1
				((0x100 <= ch) && (ch <= 0x178)) ||
				// cyrillic
				((0x410 <= ch) && (ch <= 0x44F)) ||
				// cyrillic YO & yo
				(ch == 0x401) || (ch == 0x451);
	}

	public static float round(float value, int scale, int roudingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roudingMode);
		float dest = bd.floatValue();
		bd = null;

		return dest;
	}

	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	public static String urlEncode(String url, String charset) {
		try {
			return URLEncoder.encode(url, charset);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	public static String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	public static String urlDecode(String url, String charset) {
		try {
			return URLDecoder.decode(url, charset);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}
}
