package com.htmitech.commonx.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * 字符串工具类。
 * 
 */
public class StringUtil {
	private StringUtil() {

	}

	/***取部分字符串**/
	public static String splitString(String  aimString , char ch){
		if(aimString.indexOf(ch) <= aimString.length() -1){
			StringBuffer sbuffer = new  StringBuffer();
			sbuffer.append( aimString.substring(0, aimString.indexOf(ch)));
			if(aimString.substring(aimString.indexOf(ch)+1).equals("00:00:00")){
			}else{
				sbuffer.append("  "+aimString.substring(aimString.indexOf(ch)+1));
			}
			return sbuffer.toString();
		}
		return "截取数据错误！！！";
	}
	
	/**
	 * 检查字符参数是否为空。
	 * 
	 * @param args
	 *            字符串参数列表
	 * @return boolean
	 */
	public static boolean isStringParamEmpty(String... args) {
		if (null == args) {
			return true;
		}

		int length = args.length;

		for (int i = 0; i < length; i++) {
			if (TextUtils.isEmpty(args[i])) {
				return true;
			}
		}

		return false;
	}
	
	/**
     * 字符串翻转.
     * 
     * @param origin 原始字符串
     * @return 翻转后的字符串
     */
    public static String strrev(String origin) {
        char[] array = origin.toCharArray();
        for (int start = 0, end = array.length - 1; start <= end; start++, end--) {
            charSwitch(array, start, end);
        }
        return String.valueOf(array);
    }
    /**
     * 数组内两字符交换.
     * 
     * @param array 输入的数组
     * @param first 需要交换的位
     * @param second 被交换的位
     */
    public static void charSwitch(char[] array, int first, int second) {
        char c = array[first];
        array[first] = array[second];
        array[second] = c;
    }
    
    protected static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messageDigest = null;
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println(FileMD5.class.getName() + "初始化失败，MessageDigest不支持MD5Util.");
            e.printStackTrace();
        }
    }
    
    /**
     * 对字符串做MD5操作。
     * 
     * @param plainText 明文
     * @return hash后的密文
     */
    public static String md5(String plainText) {
        try {
            byte[] bytes = plainText.getBytes();
            messageDigest.update(bytes);
            return bufferToHex(messageDigest.digest());
        } catch (Exception e) {
            LogUtil.e("Md5", e);
        }
        return "";
    }
    
    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }
    private static final int HIGHT_F = 0xf0;
    private static final int LOW_F = 0xf;
    private static final int HALF_BYTE = 4;
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & HIGHT_F) >> HALF_BYTE];
        char c1 = hexDigits[bt & LOW_F];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    
    public static String replaceSpace(String s) {
    	if (null == s) {
			return "";
		}
    	return s.replaceAll("\\s*", "");
    }
    
    /**
	 * 倒序字符串
	 * 
	 * @param src
	 *            源
	 * @return 倒序之后的字符串
	 */
	public static String revertStr(String src) {
		if (src == null || src.length() == 0)
			return src;

		String des = "";
		for (int index = src.length() - 1; index >= 0; index--) {
			des += src.charAt(index);
		}
		return des;
	}
	
	/**
	 * 将一个数组中的所有字符串用seperator连接起来
	 * 
	 * @param srcList
	 *            源列表
	 * @param seperator
	 *            字符串分隔
	 * @return 连接之后的字符串
	 */
	public static String joinStr(List<String> srcList, String seperator) {
		if (srcList == null || seperator == null || srcList.size() == 0) {
			return null;
		} else if (srcList.size() == 1) {
			return srcList.get(0);
		}

		StringBuilder des = null;
		for (String src : srcList) {
			if (des == null) {
				des = new StringBuilder();
			} else {
				des.append(seperator);
			}

			des.append(src);
		}
		return des.toString();
	}
	
    private static final int STRING_BUFFER_LENGTH = 100;
	
    public static long sizeOfString(final String str, String charset) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int len = str.length();
        if (len < STRING_BUFFER_LENGTH) {
            return str.getBytes(charset).length;
        }
        long size = 0;
        for (int i = 0; i < len; i += STRING_BUFFER_LENGTH) {
            int end = i + STRING_BUFFER_LENGTH;
            end = end < len ? end : len;
            String temp = getSubString(str, i, end);
            size += temp.getBytes(charset).length;
        }
        return size;
    }
    
    public static String utf8ToUnicode(String original) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(original, i)));
        }
        String unicode = sb.toString();
        return unicode;
    }
    
    // get the sub string for large string
    public static String getSubString(final String str, int start, int end) {
        return new String(str.substring(start, end));
    }
}
