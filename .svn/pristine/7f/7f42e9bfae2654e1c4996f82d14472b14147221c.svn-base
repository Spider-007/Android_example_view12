package htmitech.com.componentlibrary.unit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import htmitech.com.componentlibrary.entity.RequestEntity;


public class PreferenceUtils {
    public static final String PREFERENCE_SYSTEM_2 = "system_preference2";
    private static final String PREFERENCE_TOKEN = "system_preference_token";

    private static final String DEFAULT_SETTING = "default_setting"; //包含当前门户 字体大小 默认风格
    private static final String TEXTSIZE = "text_size"; //当前字体大小
    private static final String CURRENT_PORTAL = "current_portal";//当前门户
    public static Context mContext = null;

    public static final String AllPortal_NUM = "all_portal_num";
    public static final String PDFSIGNSAVE = "pdf_sign_save";//金格pdf签批是否保存

    private static final String PREFERENCE_ITEM_IS_FTT = "isAPPFTT";
    private static final String PREFERENCE_ITEM_LOGIN_NAME = "login_name";
    private static final String PREFERENCE_ITEM_GESTURE_PWD = "gesture_pwd";
    private static final String PREFERENCE_ITEM_GESTURE_PWD_ENABLE = "gesture_pwd_enable";
    private static final String PREFERENCE_ITEM_SYSTEM_NOTIFICATION = "preference_notification";
    private static final String PREFERENCE_ITEM_MAIL_NOTIFICATION = "preference_mail_notification";
    private static final String PREFERENCE_ITEM_NOTIFICATION_SOUND = "preference_notification_sound";
    private static final String PREFERENCE_ITEM_NOTIFICATION_SHAKE = "preference_notification_shake";
    private static final String PREFERENCE_ITEM_DISTURB = "preference_disturb";
    private static final String PREFERENCE_ITEM_UPGRADE_MARK = "client_upgrade";
    private static final String PREFERENCE_ITEM_AUTOLOGIN = "isAutoLogin";
    private static final String PREFERENCE_ITEM_GESTURE_ZW_PWD = "gesture_ZW_pwd";

    //需要缓存的用户信息
    public static final String PREFERENCE_ITEM_EMP_USERID = "UserID";
    private static final String PREFERENCE_ITEM_EMP_USERNAME = "UserName";
    public static final String PREFERENCE_ITEM_OA_USERID = "OA_UserId";
    private static final String PREFERENCE_ITEM_OA_USERNAME = "OA_UserName";
    private static final String PREFERENCE_ITEM_OA_USERUNITID = "OA_UnitId";
    private static final String PREFERENCE_THIRD_DEPARTMENT_Id = "ThirdDepartmentId";
    private static final String PREFERENCE_THIRD_DEPARTMENT_NAME = "ThirdDepartmentName";
    private static final String PREFERENCE_ATTRIBUTE1 = "Attribute1";
    private static final String PREFERENCE_ITEM_OA_NETWORk = "YNSCZ";
    //存住上一次获取的用户信息的时间
    public static final String PREFERENCE_LAST_TIME = "lastTime";
    private static final String PREFERENCE_APP_ID = "APPID";
    //各种url
    private static final String PREFERENCE_OALOG_URL = "OaLoginUrl";
    private static final String PREFERENCE_OADEBUG_URL = "OaDebugUrl";
    private static final String PREFERENCE_PUSH_URL = "PushUrl";
    private static final String PREFERENCE_IM_URL = "ImUrl";
    private static final String PREFERENCE_API_URL = "ApiDir";
    private static final String PREFERENCE_DZ_URL = "DZUrl";

    private static final String PREFERENCE_URL_CONFIG = "UrlConfig";
    //保存linces验证信息
    private static final String PREFERENCE_SERVER_IP = "ServerIp";
    //5个界面的首次登陆
    private static final String PREFERENCE_FIRST_LOGIN_FORM = "firstLoginForm";
    private static final String PREFERENCE_FIRST_LOGIN_DAIYIBAN = "firstLoginDaiYiban";
    private static final String PREFERENCE_FIRST_LOGIN_CALL = "firstLoginCall";
    private static final String PREFERENCE_FIRST_LOGIN_CIRCLE = "firstLoginCircle";
    private static final String PREFERENCE_FIRST_LOGIN_CENTER = "firstLoginCenter";

    private static final String PREFERENCE_CURRENT_HOMEPAGE = "homepage";
    private static final String PREFERENCE_ISSETHOMEPAGE = "sethomepage";  //是否手动设置过首页风格
    //两个token
    private static final String PREFERENCE_ACCESS_TOKEN = "accessToken";
    private static final String PREFERENCE_REFRESH_TOKEN = "refreshToken";

    /***
     * 新增参数
     */
    private static final String PREFERENCE_IsEMIUser = "IsEMIUser";
    private static final String PREFERENCE_group_corp_id = "group_corp_id";
    public static final String PREFERENCE_login_name = "login_name";
    //app是否第一次启动
    private static final String PREFERENCE_ITEM_IS_APP_FRIATSTART = "isAPPFRISTSTART";

    private static final String JWIFI_UPDATE = "j_wifi_update";
    private static final String PREFERENCE_BIG_FILE = "saveBigFile";

    //中铁定制
    public static final String PREFERENCE_SYSTEM = "system_preference";
    private static final String PREFERENCE_USERNAME = "UserName";
    private static final String PREFERENCE_USERID = "UserID";
    private static final String PREFERENCE_SCODE = "SCode";
    private static final String PREFERENCE_CVERSION = "CVersion";
    private static final String PREFERENCE_ISDEV = "IsDev";


    /**
     * 判断五个界面的首次登陆
     *
     * @param context
     * @param
     */
    public static void setLoginForm(Context context, Boolean isFirstLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_FIRST_LOGIN_FORM, isFirstLogin).apply();
    }

    public static boolean isLoginForm(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_FIRST_LOGIN_FORM, Boolean.TRUE);
    }

    public static void setLoginDaiYiBan(Context context, Boolean isFirstLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_FIRST_LOGIN_DAIYIBAN, isFirstLogin).apply();
    }

    public static boolean isLoginDaiYiBan(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_FIRST_LOGIN_DAIYIBAN, Boolean.TRUE);
    }

    public static void setLoginCall(Context context, Boolean isFirstLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_FIRST_LOGIN_CALL, isFirstLogin).apply();
    }

    public static boolean isLoginCall(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_FIRST_LOGIN_CALL, Boolean.TRUE);
    }

    public static void setLoginCircle(Context context, Boolean isFirstLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_FIRST_LOGIN_CIRCLE, isFirstLogin).apply();
    }

    public static boolean isLoginCircle(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_FIRST_LOGIN_CIRCLE, Boolean.TRUE);
    }

    public static void setLoginCenter(Context context, Boolean isFirstLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_FIRST_LOGIN_CENTER, isFirstLogin).apply();
    }

    public static boolean isLoginCenter(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_FIRST_LOGIN_CENTER, Boolean.TRUE);
    }


    /***
     * 上一次获取的时间
     *
     * @param lastTime
     */
    public static void saveLastTime(String lastTime) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_LAST_TIME, lastTime).apply();
    }

    public static String getLastTime() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        Log.e("Tag", sp.getString(PREFERENCE_LAST_TIME, "1999-01-01 00:00:00") + "");
        return sp.getString(PREFERENCE_LAST_TIME, "1999-01-01 00:00:00");
    }

    /**
     * 2015-7-11 用于配置登陆不同服务器 不同接口
     * 利用 AutoCompleteTextView 拿到的值
     **********************************************************************************************/
    public static boolean isUrlConfig() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_URL_CONFIG, Boolean.TRUE);
    }

    public static boolean setUrlConfig() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.edit().putBoolean(PREFERENCE_URL_CONFIG, false).commit();
    }


    //保存EMMLogUrl
    public static void saveOaLoginUrl(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_OALOG_URL, url).apply();
    }

    //保存EMMDebugUrl
    public static void saveDebugUrl(String url) {
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_OADEBUG_URL, url).apply();
    }

    //保存PushUrl
    public static void savePushUrl(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_PUSH_URL, url).apply();
    }

    //保存ImUrl
    public static void saveImUrl(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_IM_URL, url).apply();
    }

    //保存定制url
    public static void saveDZUrl(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_DZ_URL, url).apply();
    }

    //保存Api Dir
    public static void saveApiUrl(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_API_URL, url).apply();
    }

    //保存Api Dir
    public static void saveSoftWareCode(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_APP_ID, url).apply();
    }

    //保存Api Dir
    public static void saveServerIp(String url) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_SERVER_IP, url).apply();
    }

    //获取EMMLogUrl
    public static String getOaLoginUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_http_cloudapi_host");
        return sp.getString(PREFERENCE_OALOG_URL, defaultValue);
    }
//		return sp.getString(PREFERENCE_OALOG_URL, "http://172.19.80.50:8081");  //大唐
//		return sp.getString(PREFERENCE_OALOG_URL, defaultValue);
//		return sp.getString(PREFERENCE_OALOG_URL, "http://192.168.88.122:8081");
//		return sp.getString(PREFERENCE_OALOG_URL, "http://219.140.196.75:8084");


    //获取EMMDebugUrl
    public static String getDebugUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_http_empapi_host");
        return sp.getString(PREFERENCE_OADEBUG_URL, defaultValue);
    }

    //获取PushUrl
    public static String getPushUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_push_host");
        return sp.getString(PREFERENCE_PUSH_URL, defaultValue);

    }

    //获取ImUrl
    public static String getImUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_http_host");
        return sp.getString(PREFERENCE_IM_URL, defaultValue);
    }

    //获取Api Dir
    public static String getApiUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_http_cloudapidir");
        return sp.getString(PREFERENCE_API_URL, defaultValue);

    }

    //获取定制IP
    public static String getDzUrl() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_dz_ip");
        return sp.getString(PREFERENCE_DZ_URL, defaultValue);
    }

    public static String getSoftWareCode() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "client_conf_http_emmappid");
        return sp.getString(PREFERENCE_APP_ID, defaultValue);
    }

    public static String getServerIp() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        String defaultValue = ResourceUtil.getConfString(mContext, "serverip");
        String defaultValueT = sp.getString(PREFERENCE_SERVER_IP, defaultValue);
//		if(!defaultValueT.substring(defaultValue.length()-1).equals("/")){
//			defaultValueT = defaultValueT+"/";
//		}
        return defaultValueT;
    }

    /************************************************************************************************/


	/* 用户信息缓存部分 begin */
    public static void clearAllPreference(Context context) {
//        context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE).edit().clear().commit();
//        context.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE).edit().clear().commit();
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        SecuritySharedPreference sp2 = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp2.edit().clear().apply();
    }

    /**
     * 登录成功后保存用户信息
     */
    public static void saveUserInfo(Context context, String mEMP_UserName, String mEMP_UserID, String mOA_UserID, String mOA_UserName
            , String mThirdDepartmentId, String mThirdDepartmentName, int isEMIUser, String group_corp_id, String login_name) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);

        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putString(PREFERENCE_ITEM_EMP_USERID, mEMP_UserID);
        securityEditor.putString(PREFERENCE_ITEM_OA_USERID, mOA_UserID);
        securityEditor.putString(PREFERENCE_ITEM_EMP_USERNAME, mEMP_UserName);
        securityEditor.putString(PREFERENCE_ITEM_OA_USERNAME, mOA_UserName);
        securityEditor.putString(PREFERENCE_THIRD_DEPARTMENT_Id, mThirdDepartmentId);
        securityEditor.putString(PREFERENCE_THIRD_DEPARTMENT_NAME, mThirdDepartmentName);
        securityEditor.putString(PREFERENCE_THIRD_DEPARTMENT_NAME, mThirdDepartmentName);
        securityEditor.putInt(PREFERENCE_IsEMIUser, isEMIUser);
        securityEditor.putString(PREFERENCE_group_corp_id, group_corp_id);
        securityEditor.putString(PREFERENCE_login_name, login_name);
        securityEditor.apply();
//        sp.edit().putString(PREFERENCE_ITEM_EMP_USERID, mEMP_UserID).commit();
//        sp.edit().putString(PREFERENCE_ITEM_OA_USERID, mOA_UserID).commit();
//        sp.edit().putString(PREFERENCE_ITEM_EMP_USERNAME, mEMP_UserName).commit();
//        sp.edit().putString(PREFERENCE_ITEM_OA_USERNAME, mOA_UserName).commit();
//        sp.edit().putString(PREFERENCE_THIRD_DEPARTMENT_Id, mThirdDepartmentId).commit();
//        sp.edit().putString(PREFERENCE_THIRD_DEPARTMENT_NAME, mThirdDepartmentName).commit();

        /**记录登录参数2017 05 23**/
//        sp.edit().putInt(PREFERENCE_IsEMIUser, isEMIUser).commit();
//        sp.edit().putString(PREFERENCE_group_corp_id, group_corp_id).commit();
//        sp.edit().putString(PREFERENCE_login_name, login_name).commit();

    }

    /**
     * 清空用户信息
     */
    public static void resetUserInfo(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);

        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.remove(PREFERENCE_ITEM_EMP_USERID).apply();
        securityEditor.remove(PREFERENCE_ITEM_EMP_USERNAME).apply();
        securityEditor.remove(PREFERENCE_ITEM_OA_USERID).apply();
        securityEditor.remove(PREFERENCE_ITEM_OA_USERNAME).apply();
        securityEditor.remove(PREFERENCE_ITEM_OA_USERUNITID).apply();
        securityEditor.remove(PREFERENCE_THIRD_DEPARTMENT_Id).apply();
        securityEditor.remove(PREFERENCE_THIRD_DEPARTMENT_NAME).apply();
        securityEditor.remove(PREFERENCE_ATTRIBUTE1).apply();
        /**记录登录参数2017 05 23**/
        securityEditor.remove(PREFERENCE_IsEMIUser).apply();
        securityEditor.remove(PREFERENCE_group_corp_id).apply();
        securityEditor.remove(PREFERENCE_login_name).apply();

    }

    /**
     * 获得平台用户的用户ID
     */
    public static String getEMPUserID(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);

        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
//        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();

        return sp.getString(PREFERENCE_ITEM_EMP_USERID, "0");
    }

    /**
     * 获得平台用户的用户 获取EMP   UserName
     */
    public static String getEMPUserName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_EMP_USERNAME, null);
    }

    /**
     * 获得OA系统中用户ID
     */
    public static String getOAUserID(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_OA_USERID, null);
    }

    /**
     * 获得OA系统中用户姓名
     */
    public static String getOAUserName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_OA_USERNAME, null);
    }

    /**
     * 获得部门id
     */
    public static String getThirdDepartmentId(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_THIRD_DEPARTMENT_Id, null);
    }


    /**
     * 获得UnitId
     */
    public static String getOAUnitId(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_OA_USERUNITID, null);
    }

    /**
     * 获得是否有签名图片的标示
     */
    public static String getAttribute1(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ATTRIBUTE1, null);
    }


    /**
     * 获得部门名称
     */
    public static String getThirdDepartmentName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_THIRD_DEPARTMENT_NAME, null);
    }

    /**
     * 是否开通敏行
     */
    public static int getIsEMIUser(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getInt(PREFERENCE_IsEMIUser, 0);
    }

    /**
     * 获得敏行的登录名
     */
    public static String getLogin_name(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_login_name, null);
    }

    /**
     * 获得组
     */
    public static String getGroup_corp_id(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_group_corp_id, null);
    }

	/* 用户信息缓存部分  end */


    public static void setIsAutoLogin(Context context, Boolean isAutoLogin) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_AUTOLOGIN, isAutoLogin).apply();
    }

    public static boolean isAutoLogin(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_AUTOLOGIN, Boolean.TRUE);
    }

    public static void saveAPPFTTStatus(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_IS_FTT, Boolean.FALSE).apply();
    }

    public static boolean isAPPFTT(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_IS_FTT, Boolean.TRUE);
    }

    public static void saveLoginName(Context context, String name) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_ITEM_LOGIN_NAME, name).apply();
    }

    public static void resetLoginName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().remove(PREFERENCE_ITEM_LOGIN_NAME).apply();
    }

    public static String getLoginName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_LOGIN_NAME, null);
    }

    public static String getNetworkName(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ITEM_OA_NETWORk, null);
    }

    public static void saveGesturePwd(Context context, String loginName, String gesturePwd) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_ITEM_GESTURE_PWD + loginName, gesturePwd).apply();
    }

    public static void saveZWGesturePwd(Context context, String loginName, String gesturePwd) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_ITEM_GESTURE_ZW_PWD + loginName, gesturePwd).apply();
    }


    public static boolean isInitZWGesturePwd(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return !"".equals(sp.getString(PREFERENCE_ITEM_GESTURE_ZW_PWD + loginName, ""));
    }

    public static boolean isInitGesturePwd(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return !"".equals(sp.getString(PREFERENCE_ITEM_GESTURE_PWD + loginName, ""));
    }

    public static boolean checkGesturePwd(Context context, String loginName, String gesturePwd) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        if (gesturePwd != null && !"".equals(gesturePwd)) {
            return gesturePwd.equals(sp.getString(PREFERENCE_ITEM_GESTURE_PWD + loginName, ""));
        }
        return false;
    }

    public static void resetGesturePwd(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().remove(PREFERENCE_ITEM_GESTURE_PWD + loginName).apply();
    }

    public static void enableGesturePwd(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_GESTURE_PWD_ENABLE + loginName, true).apply();
    }

    public static void disableGesturePwd(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_GESTURE_PWD_ENABLE + loginName, false).apply();
    }

    public static boolean isGesturePwdEnable(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_GESTURE_PWD_ENABLE + loginName, false);
    }

    public static void enableMessageNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_SYSTEM_NOTIFICATION + loginName, true).apply();
    }

    public static void disableMessageNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_SYSTEM_NOTIFICATION + loginName, false).apply();
    }

    public static boolean isMessageNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_SYSTEM_NOTIFICATION + loginName, true);
    }

    public static void enableNotificationSound(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_NOTIFICATION_SOUND + loginName, true).apply();
    }

    public static void disableNotificationSound(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_NOTIFICATION_SOUND + loginName, false).apply();
    }

    public static boolean isNotificationSound(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_NOTIFICATION_SOUND + loginName, true);
    }

    public static void enableNotificationShake(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_NOTIFICATION_SHAKE + loginName, true).apply();
    }

    public static void disableNotificationShake(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_NOTIFICATION_SHAKE + loginName, false).apply();
    }

    public static boolean isNotificationShake(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_NOTIFICATION_SHAKE + loginName, true);
    }

    public static void enableMailNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_MAIL_NOTIFICATION + loginName, true).apply();
    }

    public static void disableMailNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_MAIL_NOTIFICATION + loginName, false).apply();
    }

    public static boolean isMailNotification(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_MAIL_NOTIFICATION + loginName, true);
    }

    public static void saveCurrentDisturbType(Context context, String loginName, int disturb) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putInt(PREFERENCE_ITEM_DISTURB + loginName, disturb).apply();
    }

    public static int getCurrentDisturbType(Context context, String loginName) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getInt(PREFERENCE_ITEM_DISTURB + loginName, 0);
    }

    public static void saveUpgradeMark(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_UPGRADE_MARK, true).apply();
    }

    public static void clearUpgradeMark(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().remove(PREFERENCE_ITEM_UPGRADE_MARK).apply();
    }

    public static boolean checkUpgradeMark(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_UPGRADE_MARK, false);
    }

    //缓存accessToken
    public static void saveAccessToken(String accessTokenString) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_ACCESS_TOKEN, accessTokenString).apply();
    }

    //缓存refreshToken
    public static void saveRefreshToken(String refreshTokenString) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_REFRESH_TOKEN, refreshTokenString).apply();
    }

    //获取accessToken
    public static String getAccessToken() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_ACCESS_TOKEN, "");
    }

    //获取refreshToken
    public static String getRefreshToken() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_REFRESH_TOKEN, "");
    }

    //清除Token信息
    public static void clearToken() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    //保存字体比例系数
    public static void saveTextSize(float textSize) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        sp.edit().putFloat(TEXTSIZE, textSize).apply();

    }

    //取出字体比例系数
    public static float getTextSize() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        return sp.getFloat(TEXTSIZE, -1);
    }

    //获取当前门户
    public static String getCurrentPortal() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        return sp.getString(CURRENT_PORTAL, "-1");
    }

    //保存当前门户
    public static void saveCurrentPortal(String currentPortal) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        sp.edit().putString(CURRENT_PORTAL, currentPortal).apply();


    }

    /***
     * 判断当前首页
     */
    public static void saveCurrentPage(String page) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putString(PREFERENCE_CURRENT_HOMEPAGE, page).apply();


    }

    public static String getCurrentPage() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.getString(PREFERENCE_CURRENT_HOMEPAGE, "");
//		return sp.getString(PREFERENCE_CURRENT_HOMEPAGE, "metropage");
    }

    public static void setIsSetHomePage(boolean isSet) {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ISSETHOMEPAGE, isSet).apply();
    }

    public static void clearUser(Context context) {
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static boolean getIsSetHomePage() {
//        SharedPreferences sp = mContext.getSharedPreferences(PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ISSETHOMEPAGE, false);
    }

    public static boolean isFristStartApp(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.getBoolean(PREFERENCE_ITEM_IS_APP_FRIATSTART, true);
    }

    public static void saveFristStartApp(Context context) {
//        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putBoolean(PREFERENCE_ITEM_IS_APP_FRIATSTART, Boolean.FALSE).apply();
    }

    public static void saveUploadBigFileNet(Context context, int code) {
        //1 总是上传 2 仅以此允许 3 仅wifi上传  默认是仅wifi上传
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        sp.edit().putInt(PREFERENCE_BIG_FILE, code).apply();
    }

    public static int getUploadBigFileNet(Context context) {
        SecuritySharedPreference sp = new SecuritySharedPreference(context, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
        return sp.getInt(PREFERENCE_BIG_FILE, 3);//1 总是上传 2 仅一次允许 3 仅wifi上传  默认是仅wifi上传
    }

    public static RequestEntity getRequestEntity(Context context) {
        RequestEntity mRequestEntity = new RequestEntity();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        mRequestEntity.UserName = sp.getString(PREFERENCE_USERNAME, "");
        mRequestEntity.UserId = sp.getString(PREFERENCE_USERID, "");
        mRequestEntity.SCode = sp.getString(PREFERENCE_SCODE, "");
        mRequestEntity.CVersion = sp.getString(PREFERENCE_CVERSION, "");
        mRequestEntity.IsDev = sp.getString(PREFERENCE_ISDEV, "");
        return mRequestEntity;
    }


}
