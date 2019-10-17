package com.htmitech.emportal.common;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

/**
 * @version 1.0
 * @date 2011-2-15
 */
public class AppPreferenceHelper {

    public static final String HTMITECH_PREFERENCES = "htmitech";

    /**
     * key constants
     */
    public class CommonPreferenceKeys {
        // 设置里的Push开关
        public static final String KEY_DEBUG_SWITCH = "debug_switch";
        public static final String KEY_PUSH_SWITCH = "push_switch";
        public static final String KEY_PUSH_MESSAGE = "push_message";
        public static final String KEY_PUSH_ID = "push_id";
        public static final String KEY_PUSH_LAST_TOKEN = "push_last_token";
        public static final String KEY_PUSH_BDUSS = "push_bduss";
        public static final String KEY_PUSH_UID = "push_uid";
        public static final String KEY_PUSH_REQUEST_CODE = "push_request_code";

        public static final String KEY_AUTODOWNLOAD_WIFI = "autodownload_wifi"; // wifi网络下自动离线，默认开启
        public static final String KEY_AUTODOWNLOAD_MOBILE = "autodownload_mobile"; // 3G/2G网络下自动离线，默认关闭
        public static final String KEY_AUTODOWNLOAD_NOTICE = "autodownload_notice"; // 记录通知日期，如果当日已经通知，就不再通知
        public static final String KEY_WIFI_FIRST_TRIGGER = "wifi_first_trigger"; // 第一次触发wifi环境自动离线
        public static final String KEY_MOBILE_FIRST_TRIGGER = "mobile_first_trigger"; // 第一次触发2G/3G网络且非自动离线时
        /****** 创建快捷方式，首次有效 ****/
        public static final String KEY_CREATE_SHORTCUT_FIRST = "shortcut_first";

        public static final String KEY_FULLMARKESSAY_FIRST = "fullmarkessay_first";
    }
    private Context context;
    private static AppPreferenceHelper mInstance;
    private SharedPreferences mPreferences;
    private Editor mEditor;
    
    /**
     * 单例获取方法
     * @param context 上下文
     * @return 单例对象
     */
    public synchronized static AppPreferenceHelper getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = new AppPreferenceHelper(context);
        }
        return mInstance;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    private AppPreferenceHelper(final Context context) {
//        mPreferences = context.getSharedPreferences(HTMITECH_PREFERENCES, Activity.MODE_PRIVATE);
        this.context = context;
        SecuritySharedPreference sp = new SecuritySharedPreference(context, HTMITECH_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = sp.edit();
    }

    public SharedPreferences getPreferences(Context context, String prefName) {
        return context.getSharedPreferences(prefName, Activity.MODE_PRIVATE);
    }

    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    public boolean getBoolean(String key, boolean defValue) {
        SecuritySharedPreference sp = new SecuritySharedPreference(context, HTMITECH_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        SecuritySharedPreference sp = new SecuritySharedPreference(context, HTMITECH_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        SecuritySharedPreference sp = new SecuritySharedPreference(context, HTMITECH_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        SecuritySharedPreference sp = new SecuritySharedPreference(context, HTMITECH_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean putBoolean(String key, boolean b) {
        mEditor.putBoolean(key, b);
        return mEditor.commit();
    }

    public boolean putInt(String key, int i) {
        mEditor.putInt(key, i);
        return mEditor.commit();
    }

    public boolean putFloat(String key, float f) {
        mEditor.putFloat(key, f);
        return mEditor.commit();
    }

    public boolean putLong(String key, long l) {
        mEditor.putLong(key, l);
        return mEditor.commit();
    }

    public boolean putString(String key, String s) {
        mEditor.putString(key, s);
        return mEditor.commit();
    }

    /**
     * 移除一个键
     * 
     * @param key
     * @return
     */
    public boolean removeKey(String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }
}
