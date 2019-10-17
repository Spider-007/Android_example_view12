package com.htmitech.emportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapGlobalConfig;
import com.htmitech.emportal.base.CrashHandler;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.push.TestActivityManager;
import com.htmitech.proxy.dao.ParamDao;
import com.htmitech.unit.BackgroundDetector;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.activity.GesturePasswordActivity;
import com.minxing.client.notification.NotificationHolder;
import com.minxing.client.util.ImageUtil;
import com.minxing.client.util.NotificationUtil;
import com.minxing.client.util.Utils;
import com.minxing.kit.MXKit;
import com.minxing.kit.api.bean.ChatMessage;
import com.minxing.kit.api.bean.MXError;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

import cn.feng.skin.manager.loader.SkinManager;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.content.ComponentConstant;
import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.listener.ICallMXInit;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;

/**
 * code is far away from bug with the animal protecting
 */

public class HtmitechApplication extends BaseApplication implements
        IBaseCallback {
    public static Activity contactActivity = null;

    private static HtmitechApplication mApplication;

    public static HtmitechApplication instance() {
        return mApplication;
    }

    //获取到主线程的上下文
    private static BaseApplication mContext = null;
    //获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    //获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    //获取到主线程
    private static Thread mMainThead = null;
    //获取到主线程的id
    private static int mMainTheadId;

    public static boolean isHomeBack = false;

    public static boolean isAdvShow = true;

    /**
     * 构造方法.
     */
    public HtmitechApplication() {
        mApplication = this;
    }

    @Override
    public void init() {
        if (CommonSettings.DEBUG) {
            Toast.makeText(getApplicationContext(), "这是Debug版本！",
                    Toast.LENGTH_SHORT).show();
        }

        /** 清除通知 */
        NotificationUtil.clearAllNotification(getApplicationContext());


        BitmapUtils.init(this, BitmapGlobalConfig.getInstance(
                getApplicationContext(),
                CommonSettings.DEFAULT_CACHE_IMAGE_FOLDER));
    }


    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), ResourceUtil.getConfString(getApplicationContext(), "client_conf_buglycode"), false);
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
        this.mContext = this;
        DBCipherManager.getInstance(this);
        DbUtil.setContext(this);
        UploadManager.init(this);
        CrashHandler.getInstance().init(this);//注册本地日志信息
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        ComponentInit.getInstance().setContext(this);
        QbSdk.initX5Environment(this,null);
        //android.os.Process.myUid()获取到用户id
        //android.os.Process.myPid();//获取到进程id
        //android.os.Process.myTid()获取到调用线程的id
        this.mMainTheadId = android.os.Process.myTid();//主線程id
//		MXKit.getInstance().setMasterClearListener(new MXKitMasterClearListener() {
//
//            @Override
//            public void onKitMasterClear() {
//                Utils.toast(getApplicationContext(), "擦除数据，强制退出", Toast.LENGTH_SHORT);
//
//                PreferenceUtils.clearAllPreference(getApplicationContext());
//
//                Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
//                finishIntent.setAction("master_clear");
//                finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(finishIntent);
//            }
//        });

        //ResourceUtil.getConfString(getApplicationContext(),  "client_conf_http_host")
        //ResourceUtil.getConfString(getApplicationContext(), "client_conf_push_host")
        /***
         * PreferenceUtils.getImUrl()
         * PreferenceUtils.getPushUrl()
         */

        if (PreferenceUtils.mContext == null) {
            PreferenceUtils.mContext = getApplicationContext();
        }
        ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
        mConcreteLogin.setICallMXInit(new ICallMXInit() {

            @Override
            public void onKitMasterClear() {
                Utils.toast(getApplicationContext(), R.string.master_clear_alert, Toast.LENGTH_SHORT);

                PreferenceUtils.clearAllPreference(getApplicationContext());

                Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
                finishIntent.setAction("master_clear");
                finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(finishIntent);
            }

            @Override
            public void onChatNotify(Context context, ChatMessage message, boolean flag) {
                NotificationUtil.handleMessageNotification(context, message, false);
            }

            @Override
            public void dismissNotification(Context context, int chatID) {
                if (NotificationHolder.getInstance().checkChatMessage(chatID)) {
                    NotificationUtil.clearAllNotification(context);
                }
            }

            @Override
            public void onMessageRevoked(Context context, ChatMessage revokedMessage) {
                if (NotificationHolder.getInstance().checkChatMessage(revokedMessage.getChatID())) {
                    NotificationUtil.handleMessageNotification(context, revokedMessage, true);
                }
            }

            @Override
            public void onActivityStart(Activity activity) {
                if (com.htmitech.unit.BackgroundDetector.getInstance().isGesturePwdViewEnabled()) {
                    Intent intent = new Intent(activity, GesturePasswordActivity.class);
                    intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    activity.startActivity(intent);
                    com.htmitech.unit.BackgroundDetector.getInstance().setPasswordCheckActive(true);
                }else if(com.htmitech.unit.BackgroundDetector.getInstance().isGestureZWPwdViewEnabled()){
                    BookInit.getInstance().getmCallbackMX().setIntent(activity, 3);

                    com.htmitech.unit.BackgroundDetector.getInstance().setPasswordCheckActive(true);
                }
                com.htmitech.unit.BackgroundDetector.getInstance().setDetectorStop(false);
                com.htmitech.unit.BackgroundDetector.getInstance().onAppStart(activity);
            }

            @Override
            public void onActivityPause(Activity activity) {
                BackgroundDetector.getInstance().onAppPause(activity);
            }

            @Override
            public void onStartActivityForResult(Activity activity) {
                BackgroundDetector.getInstance().setDetectorStop(true);
                if (activity != null) {
                    if (activity.getParent() != null) {
                        activity.getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    } else {
                        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    }
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                TestActivityManager.getInstance().setCurrentActivity(activity);
            }
        });

        mConcreteLogin.mXInit(this,
                ResourceUtil.getConfString(getApplicationContext(), "client_conf_sdcard_root"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_ocu"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_company"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_multi_chat"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_vip"),
                ResourceUtil.getConfString(getApplicationContext(), "client_encrypt_cellphone"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_app_center_force_refresh"),
                ResourceUtil.getConfString(getApplicationContext(), "client_app_client_id"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "client_water_mark_enable"),
                ResourceUtil.getConfBoolean(getApplicationContext(), "file_download_forbidden"),
                PreferenceUtils.getImUrl(), PreferenceUtils.getPushUrl());
        ImageUtil.initImageEngine(getApplicationContext());
//        MXKitConfiguration config = new MXKitConfiguration.Builder(getApplicationContext())
//                // http server地址// 推送server地址
//                .hostOptions(PreferenceUtils.getImUrl(),// http server地址 修改
//                        PreferenceUtils.getPushUrl())// 推送server地址
//                // SD卡目录
//                //
//                .sdCardCacheFolder(ResourceUtil.getConfString(getApplicationContext(), "client_conf_sdcard_root"))
//                // 设置是否在通讯录中显示公众号操作
//                .contactOcu(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_ocu"))
//                // 设置是否在通讯录中显示公司通讯录操作。
//                .contactCompany(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_company"))
//                // 设置是否在通讯录中显示群聊操作
//                .contactMultiChat(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_multi_chat"))
//                // 设置是否在通讯录中显示特别关注操作
//                .contactVip(ResourceUtil.getConfBoolean(getApplicationContext(), "client_show_contact_vip"))
//                // 设置手机号码隐藏规则
//                .encryptCellphone(ResourceUtil.getConfString(getApplicationContext(), "client_encrypt_cellphone"))
//                .appForceRefresh(ResourceUtil.getConfBoolean(getApplicationContext(), "client_app_center_force_refresh"))
//                .appClientId(ResourceUtil.getConfString(getApplicationContext(), "client_app_client_id"))
//                .waterMarkEnable(ResourceUtil.getConfBoolean(getApplicationContext(), "client_water_mark_enable"))
//                .fileDownloadForbidden(ResourceUtil.getConfBoolean(getApplicationContext(), "file_download_forbidden")).build();
//        MXKit.getInstance().init(getApplicationContext(), config);
//
//        ImageUtil.initImageEngine(getApplicationContext());
        MXKit.getInstance().setConflictListener(new MXKit.MXKitUserConflictListener() {

            @Override
            public void onUserConflict(MXError error) {
                // 用户被踢出后处理逻辑
                if (Utils.isApplicationForeground(getApplicationContext())) {
                    Utils.toast(getApplicationContext(), String.valueOf(error.getMessage()), Toast.LENGTH_SHORT);
                    Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
                    finishIntent.setAction("finish");
                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(finishIntent);
                }
            }
        });
//
//        MXKit.getInstance().setMasterClearListener(new MXKit.MXKitMasterClearListener() {
//
//            @Override
//            public void onKitMasterClear() {
//                Utils.toast(getApplicationContext(), R.string.master_clear_alert, Toast.LENGTH_SHORT);
//
//                PreferenceUtils.clearAllPreference(getApplicationContext());
//
//                Intent finishIntent = new Intent(getApplicationContext(), ClientTabActivity.class);
//                finishIntent.setAction("master_clear");
//                finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(finishIntent);
//            }
//        });
//
//        MXKit.getInstance().setupNotification(new MXChatNotificationListener() {
//            @Override
//            public void onChatNotify(Context context, ChatMessage message, boolean flag) {
//                NotificationUtil.handleMessageNotification(context, message, false);
//            }
//
//            @Override
//            public void dismissNotification(Context context, int chatID) {
//                if (NotificationHolder.getInstance().checkChatMessage(chatID)) {
//                    NotificationUtil.clearAllNotification(context);
//                }
//            }
//
//            @Override
//            public void onMessageRevoked(Context context, ChatMessage revokedMessage) {
//                if (NotificationHolder.getInstance().checkChatMessage(revokedMessage.getChatID())) {
//                    NotificationUtil.handleMessageNotification(context, revokedMessage, true);
//                }
//            }
//        });
//
//        // 用以返回主界面
//        MXKit.getInstance().setMXUIListener(new MXUIListener() {
//
//            @Override
//            public void switchToMainScreen(Context context) {
//                Intent intent = new Intent(context, ClientTabActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void reLaunchApp(Context context) {
//                Intent intent = new Intent(context, ClientTabActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
//            }
//        });
//
//        MXKit.getInstance().setViewSwitchListener(new MXUIEngine.ViewSwitchListener() {
//
//            @Override
//            public void switchToCircle(Context context, int groupID) {
//                Intent intent = new Intent(context, ClientTabActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra(MXConstants.IntentKey.SHOW_CURRENT_GROUP_WORK_CIRCLE, groupID);
//                context.startActivity(intent);
//            }
//        });
//        MXKit.getInstance().setLifecycleCallbacks(new MXKitLifecycleCallbacks() {
//
//            @Override
//            public void onActivityStop(Activity activity) {
//            }
//
//            @Override
//            public void onActivityStart(Activity activity) {
//                if (BackgroundDetector.getInstance().isGesturePwdViewEnabled()) {
//                    Intent intent = new Intent(activity, GesturePasswordActivity.class);
//                    intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                    activity.startActivity(intent);
//                    BackgroundDetector.getInstance().setPasswordCheckActive(true);
//                }
//                BackgroundDetector.getInstance().setDetectorStop(false);
//                BackgroundDetector.getInstance().onAppStart(activity);
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//            }
//
//            @Override
//            public void onActivityResume(Activity activity) {
//                // 友盟统计回调
////				MobclickAgent.onResume(activity);
//            }
//
//            @Override
//            public void onActivityPause(Activity activity) {
//                BackgroundDetector.getInstance().onAppPause(activity);
//                // 友盟统计回调
////				MobclickAgent.onPause(activity);
//            }
//
//            @Override
//            public void onActivityDestroy(Activity activity) {
//            }
//
//            @Override
//            public void onActivityCreate(Activity activity, Bundle savedInstanceState) {
////				handleStatusBarColor(activity);
//            }
//
//            @Override
//            public void onStartActivityForResult(Activity activity) {
//                BackgroundDetector.getInstance().setDetectorStop(true);
//                if (activity != null) {
//                    if (activity.getParent() != null) {
//                        activity.getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                    } else {
//                        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                    }
//                }
//            }
//
//            @Override
//            public void onStartActivity(Activity activity) {
//                if (activity != null) {
//                    if (activity.getParent() != null) {
//                        activity.getParent().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                    } else {
//                        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//                    }
//                }
//            }
//
//            @Override
//            public void onActivityFinish(Activity activity) {
//                if (activity != null) {
//                    activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//                }
//            }
//        });
//        MXMail.getInstance().init(this);
//
//        MXKit.getInstance().initForeBackgroundDetector(this);
//
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//                TestActivityManager.getInstance().setCurrentActivity(activity);
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });


        setLocation();

    }

    public void setLocation() {
        ParamDao mParamDao = new ParamDao(this);
        try {
            HashMap<String, String> stringStringHashMap = mParamDao.getParamHashMap();
            if (!TextUtils.isEmpty(stringStringHashMap.get(Constant.POSITION_MINUTES)))
                Constant.minutes = Integer.parseInt(stringStringHashMap.get(Constant.POSITION_MINUTES));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (Constant.minutes > 0) {
            AMapLocationClient locationClient = new AMapLocationClient(getApplicationContext());
            //设置定位参数
            locationClient.setLocationOption(getDefaultOption());
            // 设置定位监听
            locationClient.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation loc) {

                    if (null != loc) {
                        if (loc.getErrorCode() == 0) {
                            Constant.mScheduleLocation.setPositionDesc(loc.getAddress());
                            Constant.mScheduleLocation.setLatitude(loc.getLatitude());
                            Constant.mScheduleLocation.setLongitude(loc.getLongitude());

                            ComponentConstant.scheduleLocationStr = JSON.toJSON(Constant.mScheduleLocation).toString();
                        } else {
                            Log.e("location", loc.getErrorInfo());
                        }
                    } else {
                        Log.e("location", "定位失败");
                    }
                }
            });
            locationClient.startLocation();
        }
    }

    /*
     * 设置定位参数
     * */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(Constant.minutes * 60 * 1000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        return mOption;
    }

    public static BaseApplication getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(int arg0, Object arg1) {
    }


    public void exit() {
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
