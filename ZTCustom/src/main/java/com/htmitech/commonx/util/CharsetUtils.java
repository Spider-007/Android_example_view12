package com.htmitech.commonx.util;

import org.apache.http.protocol.HTTP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CharsetUtils {

    private CharsetUtils() {
    }

    public static String toCharset(final String str, final String charset, int judgeCharsetLength) {
        try {
            String oldCharset = getEncoding(str, judgeCharsetLength);
            return new String(str.getBytes(oldCharset), charset);
        } catch (Throwable ex) {
            LogUtil.w(ex);
            return str;
        }
    }

    public static String getEncoding(final String str, int judgeCharsetLength) {
        String encode = CharsetUtils.DEFAULT_ENCODING_CHARSET;
        for (String charset : SUPPORT_CHARSET) {
            if (isCharset(str, charset, judgeCharsetLength)) {
                encode = charset;
                break;
            }
        }
        return encode;
    }

    public static boolean isCharset(final String str, final String charset, int judgeCharsetLength) {
        try {
            String temp = str.length() > judgeCharsetLength ? str.substring(0, judgeCharsetLength) : str;
            return temp.equals(new String(temp.getBytes(charset), charset));
        } catch (Throwable e) {
            return false;
        }
    }

    public static final String DEFAULT_ENCODING_CHARSET = HTTP.DEFAULT_CONTENT_CHARSET;

    public static final List<String> SUPPORT_CHARSET = new ArrayList<String>();

    static {
        SUPPORT_CHARSET.add("ISO-8859-1");

        SUPPORT_CHARSET.add("GB2312");
        SUPPORT_CHARSET.add("GBK");
        SUPPORT_CHARSET.add("GB18030");

        SUPPORT_CHARSET.add("US-ASCII");
        SUPPORT_CHARSET.add("ASCII");

        SUPPORT_CHARSET.add("ISO-2022-KR");

        SUPPORT_CHARSET.add("ISO-8859-2");

        SUPPORT_CHARSET.add("ISO-2022-JP");
        SUPPORT_CHARSET.add("ISO-2022-JP-2");

        SUPPORT_CHARSET.add("UTF-8");
    }
    
    public static Timestamp str2Time(String src) {
        if (src == null) {
            return null;
        }

        try {
            return Timestamp.valueOf(src);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Float str2Float(String src) {
        if (src == null) {
            return null;
        }

        try {
            return Float.parseFloat(src);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float str2Float(String src, float def) {
        if (src == null) {
            return def;
        }

        try {
            return Float.parseFloat(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static Integer str2Int(String src) {
        if (src == null) {
            return null;
        }

        try {
            return Integer.parseInt(src);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer str2Int(String src, int def) {
        if (src == null) {
            return def;
        }

        try {
            return Integer.parseInt(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static Long str2Long(String src) {
        if (src == null) {
            return null;
        }

        try {
            return Long.parseLong(src);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long str2Long(String src, long def) {
        if (src == null) {
            return def;
        }

        try {
            return Long.parseLong(src);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static BigDecimal str2BigDecimal(String src) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = new BigDecimal(src);
        } catch (NumberFormatException e) {
            result = BigDecimal.ZERO;
        }
        return result;
    }
}
