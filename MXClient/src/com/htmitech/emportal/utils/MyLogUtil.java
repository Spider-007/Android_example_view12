package com.htmitech.emportal.utils;

import android.util.Log;

import com.htmitech.emportal.common.CommonSettings;

/**
 * log打印类,通过{@code #LOGGABLE}控制是否打印.通过{@code MyLogUtil#mFILEABLE} 控制是否要将loginfo输出到文件中.
 * 
 */
public final class MyLogUtil {
    /**
     * 默认的日志Tag标签.
     */
    static final String DEFAULT_TAG = "htmitech";

    /**
     * 此常量用于控制是否打日志到Logcat中 release版本中本变量应置为false.
     */
    public static final boolean LOGGABLE = CommonSettings.DEBUG;

    /**
     * 是否要将日志输出到文件中.
     * 
     * @return true if output the log info into files,otherwise return false.
     */

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
