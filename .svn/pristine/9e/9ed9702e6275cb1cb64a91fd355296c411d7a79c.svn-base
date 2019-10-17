package com.htmitech.commonx.util;

import android.util.Log;


public class LogUtil {
    /**
     * 默认的文库日志Tag标签.
     */
    static final String DEFAULT_TAG = "common-x";

    /**
     * 此常量用于控制是否打日志到Logcat中 release版本中本变量应置为false.
     */
    public static final boolean LOGGABLE = true;

    private LogUtil() {
    }

    /**
     * 打印debug级别的log.
     * 
     * @param tag tag标签
     * @param str 内容
     */
    public static void d(String tag, String str) {
        if (LOGGABLE) {
            Log.d(tag, str);
        }
    }

    /**
     * 打印debug级别的log.
     * 
     * @param str 内容
     */
    public static void d(String str) {
        if (LOGGABLE) {
            Log.d(DEFAULT_TAG, str);
        }
    }

    /**
     * 打印warning级别的log.
     * 
     * @param tag tag标签
     * @param str 内容
     */
    public static void w(String tag, String str) {
        if (LOGGABLE) {
            Log.w(tag, str);
        }
    }

    /**
     * 打印warning级别的log.
     * 
     * @param str 内容
     */
    public static void w(String str) {
        if (LOGGABLE) {
            Log.w(DEFAULT_TAG, str);
        }
    }

    public static void w(Throwable e) {
        if (LOGGABLE) {
            Log.v(DEFAULT_TAG, e != null ? e.getMessage() : "");
        }
    }

    /**
     * 打印error级别的log.
     * 
     * @param tag tag标签
     * @param msg 内容
     * @param e 错误对象.
     */
    public static void e(String tag, String msg, Throwable e) {
        if (LOGGABLE) {
            Log.e(tag, msg, e);
        }
    }

    /**
     * 打印error级别的log.
     * 
     * @param tag tag标签
     * @param msg 内容
     * @param e 错误对象.
     */
    public static void e(String tag, String msg) {
        if (LOGGABLE) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, Throwable e) {
        if (LOGGABLE) {
            Log.e(tag, e != null ? e.getMessage() : "", e);
        }
    }

    /**
     * 打印error级别的log.
     * 
     * @param str 内容
     */
    public static void e(String str) {
        if (LOGGABLE) {
            Log.e(DEFAULT_TAG, str);
        }
    }

    /**
     * 打印info级别的log.
     * 
     * @param tag tag标签
     * @param str 内容
     */
    public static void i(String tag, String str) {
        if (LOGGABLE) {
            Log.i(tag, str);
        }
    }

    /**
     * 打印info级别的log.
     * 
     * @param str 内容
     */
    public static void i(String str) {
        if (LOGGABLE) {
            Log.i(DEFAULT_TAG, str);
        }
    }

    /**
     * 打印verbose级别的log.
     * 
     * @param tag tag标签
     * @param str 内容
     */
    public static void v(String tag, String str) {
        if (LOGGABLE) {
            Log.v(tag, str);
        }
    }

    /**
     * 打印verbose级别的log.
     * 
     * @param str 内容
     */
    public static void v(String str) {
        if (LOGGABLE) {
            Log.v(DEFAULT_TAG, str);
        }
    }

}
