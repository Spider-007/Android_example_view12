package com.minxing.client.receiver;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.service.BookService;
import com.htmitech.emportal.service.UpdateServiceHanle;
import com.htmitech.emportal.utils.CacheDeleteUtils;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.schedulebean.ScheduleDetailBean;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.ScheduleUtils;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.receiver.PushReceiverBean.ReceiverBean;
import com.minxing.client.receiver.PushReceiverBean.RequestReceiptBean;
import com.minxing.client.receiver.pushmodel.receiptpushmodel;
import com.minxing.client.upgrade.AppUpgradeInfo;
import com.minxing.client.upgrade.EmpmAppUpgradeInfo;
import com.minxing.client.upgrade.SmartUpgradeHelper;
import com.minxing.client.util.ConfigStyleUtil;
import com.minxing.client.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.listener.MXKitLogoutListener;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

/**
 * Created by yanxin on 2017-3-8.
 * 消息推送相关功能处理
 */
public class PushReceiverFunctionService implements IBaseCallback, ObserverCallBackType, CallBackSuccess {
    public Context mContext;
    private NotificationManager notificationManager;
    public ReceiverBean mReceiverBean;
    public static ArrayList<com.htmitech.pop.AlertDialog> dialogList = new ArrayList();  //缓存dialog
    public int notifycation_flag = 0;

    public PushReceiverFunctionService(Context context, ReceiverBean mReceiverBean) {
        this.mContext = context;
        this.mReceiverBean = mReceiverBean;
        notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
    }

    //-------------------------------------------数据同步-------------------------------------------
    public void SynchData(final String pushid, final int isreceipt) {
        ActivityManager mActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        ComponentInit.getInstance().setSuccess(this);
        if (runningActivity.equals(com.minxing.client.LoginActivity.class.getName())) {
            return;
        }
        if (runningActivity.equals(com.htmitech.addressbook.PeopleMessageEditActivity.class.getName())) {
            Intent intend = new Intent(mContext, BookService.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isback", false);
            bundle.putBoolean("isSys", true);
            intend.putExtras(bundle);
            mContext.startService(intend);
            return;
        }
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
        if (Utils.isBackground(mContext)) {
            Intent intend = new Intent(mContext, BookService.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isback", false);
            bundle.putBoolean("isSys", true);
            intend.putExtras(bundle);
            mContext.startService(intend);
            return;
        }
        if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
            showNotification("", "", mReceiverBean.push_title, 0, 0, null);
        }
        if (dialogList != null && dialogList.size() > 0) {
            for (int i = 0; i < dialogList.size(); i++) {
                if (dialogList.get(i).isShow())
                    return;
            }
        }
        if (runningActivity.equals(com.minxing.client.ClientTabActivity.class.getName())) {
            for (int i = 0; i < dialogList.size(); i++) {
                com.htmitech.pop.AlertDialog dialog = (com.htmitech.pop.AlertDialog) dialogList.get(i);
                dialog.setDismiss();
            }
            com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(mContext);
            mDialog.builder().setTitle("数据有更新").setMsg("更新数据将重启应用").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        AppliationCenterDao appCenterDao = new AppliationCenterDao(mContext);
                        appCenterDao.switchPortalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intend = new Intent(mContext, BookService.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isback", true);
                    intend.putExtras(bundle);
                    mContext.startService(intend);
                }
            }).setType().setCancelable(false).show();
            dialogList.add(mDialog);
            return;
        }
        com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(mContext);
        mDialog.builder().setTitle("数据有更新").setMsg("是否更新数据")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < dialogList.size(); i++) {
                            com.htmitech.pop.AlertDialog dialog = (com.htmitech.pop.AlertDialog) dialogList.get(i);
                            dialog.setDismiss();
                        }
                        try {
                            AppliationCenterDao appCenterDao = new AppliationCenterDao(mContext);
                            appCenterDao.switchPortalAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intend = new Intent(mContext, BookService.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isback", false);
                        bundle.putBoolean("isSys", true);
                        intend.putExtras(bundle);
                        mContext.startService(intend);
                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < dialogList.size(); i++) {
                            com.htmitech.pop.AlertDialog dialog = (com.htmitech.pop.AlertDialog) dialogList.get(i);
                            dialog.setDismiss();
                        }
                        try {
                            AppliationCenterDao appCenterDao = new AppliationCenterDao(mContext);
                            appCenterDao.switchPortalAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intend = new Intent(mContext, BookService.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isback", true);
                        intend.putExtras(bundle);
                        mContext.startService(intend);
                    }
                }).setType().show();
        dialogList.add(mDialog);
    }

    //---------------------------------------------磁贴变更处理-------------------------------------------------
    public void ChangeTiles(String pushid, int isreceipt) {
        ClassEvent mClassEvent = new ClassEvent();
        mClassEvent.msg = "TilesChange";
        EventBus.getDefault().post(mClassEvent);
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
        if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
            showNotification("", "", mReceiverBean.push_title, 0, 0, null);
        }
    }

    //---------------------------------------------挂失清除-------------------------------------------------
    public void DeviceLossPush(String pushid, int isreceipt) {
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
        String deviceId = ((TelephonyManager) HtmitechApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (null != mReceiverBean && null != mReceiverBean.mPush) {
            if (mReceiverBean.mPush.getString("device_sn").equals(deviceId)) {
                ClearCacheData(pushid, isreceipt);
                Toast.makeText(mContext, mReceiverBean.mPush.getString("push_title") == null
                        ? "您的设备由于挂失将要停用，请您知悉" : mReceiverBean.mPush.getString("push_title"), Toast.LENGTH_SHORT).show();
            }
        }
        if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
            showNotification("", "", mReceiverBean.push_title, 0, 0, null);
        }
    }

    //-----------------------------------------提示有新版本更新-----------------------------------------------------
    public EmpmAppUpgradeInfo upgradeInfos = null;

    public void NeedUpdateAppVersion(String pushid, int isreceipt) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
        if (mReceiverBean != null && !mReceiverBean.equals("")) {
            if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
            }
//            if(runningActivity.equals("com.minxing.client.ClientTabActivity"))
//                return;            //如果当前是ClientTabActivity界面则不接收
            com.alibaba.fastjson.JSONObject mJson = new com.alibaba.fastjson.JSONObject();
            mJson.put("userId", PreferenceUtils.getEMPUserID(mContext));
            mJson.put("versionName", ResourceUtil.getVerName(mContext));
            mJson.put("versionNo", ResourceUtil.getVerCode(mContext) + "");
            mJson.put("type", "1");
            mJson.put("envType", new AppliationCenterDao(mContext).getAppClientEnvtype(ResourceUtil.getVerCode(mContext)) + "");
            AnsynHttpRequest.requestByPostWithToken(mContext, mJson.toJSONString(), ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_VERSION_UPDATE, CHTTP.POSTWITHTOKEN, this, "upgrade", LogManagerEnum.APP_CENTER_DELETE.getFunctionCode());

        }

    }

    //----------------------------------------用户停用系统消息--------------------------------------
    public void SystemUserStop(String pushid, int isreceipt) {
        ClearCacheData(pushid, isreceipt);
    }

    //---------------------------------------设备停用系统消息--------------------------------------
    public void UserDeceiveStop(String pushid, int isreceipt) {
        ClearCacheData(pushid, isreceipt);
    }

    //-----------------------------------------清除缓存数据----------------------------------------
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void ClearCacheData(final String pushid, final int isreceipt) {
        ConcreteLogin.getInstance().logout(mContext, new MXKitLogoutListener() {
            @Override
            public void onLogout() {
                String path = "";
//                SharedPreferences sp = mContext.getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
                SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
                sp.edit().putString(PreferenceUtils.PREFERENCE_LAST_TIME, "1999-01-01 00:00:00").commit();
//                CacheDeleteUtils.cleanApplicationData(mContext, DBManager.databaseSdcardPath);     //会导致请求无法处理请稍后重试        ActivityManager mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        mActivityManager.clearApplicationUserData();
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    path = Environment.getExternalStorageDirectory().getPath() + File.separator + "htmitech";
                }
                final String tmpPath = path;
                if (!"".equals(path)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CacheDeleteUtils.clearFiles(tmpPath);
                        }
                    }).start();
                }
                PreferenceUtils.clearAllPreference(mContext);
//                if (isreceipt == 1)
//                    ReceiptMessage(pushid);
                Intent finishIntent = new Intent(mContext, ClientTabActivity.class);

                finishIntent.setAction("finish");
                finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(finishIntent);
            }
        });
//        MXKit.getInstance().logout(mContext, new MXKit.MXKitLogoutListener() {
//            @Override
//            public void onLogout() {
//                String path = "";
////                SharedPreferences sp = mContext.getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
//                SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
//                sp.edit().putString(PreferenceUtils.PREFERENCE_LAST_TIME, "1999-01-01 00:00:00").commit();
////                CacheDeleteUtils.cleanApplicationData(mContext, DBManager.databaseSdcardPath);     //会导致请求无法处理请稍后重试        ActivityManager mActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
////        mActivityManager.clearApplicationUserData();
//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    path = Environment.getExternalStorageDirectory().getPath() + File.separator + "htmitech";
//                }
//                final String tmpPath = path;
//                if (!"".equals(path)) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            CacheDeleteUtils.clearFiles(tmpPath);
//                        }
//                    }).start();
//                }
//                PreferenceUtils.clearAllPreference(mContext);
//                if (isreceipt == 1)
//                    ReceiptMessage(pushid);
//                Intent finishIntent = new Intent(mContext, ClientTabActivity.class);
//
//                finishIntent.setAction("finish");
//                finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(finishIntent);
//            }
//        });

    }

    //---------------------------------------待办消息提醒------------------------------------------
    public void TodoBusinessAlert(String pushid, int isreceipt) {
        if (mReceiverBean != null && !mReceiverBean.equals("")) {
            if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
            }
        }
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
    }

    //-------------------------------------------催办消息提醒---------------------------------------
    public void HurryTodoBusinessAlert(String pushid, int isreceipt) {
        if (mReceiverBean != null && !mReceiverBean.equals("")) {
            if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
            }
        }
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
    }

    //---------------------------------------待办日程消息提醒---------------------------------------
    public void ToConfirmScheduleAlert(String pushid, int isreceipt) {
//        ScheduleDetailBean scheduleDetailResultBean = JSONObject.parseObject(mReceiverBean.push_info, ScheduleDetailBean.class);
//        if(null != scheduleDetailResultBean){
//            if(scheduleDetailResultBean.participation.contains(OAConText.getInstance(mContext).UserID)){
//                SimpleDateFormat simpleDateFormat;
//                if(scheduleDetailResultBean.schBeginTime.length() == 18)
//                 simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                else
//                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                long time = 0;
//                try {
//                    time = simpleDateFormat.parse(scheduleDetailResultBean.schBeginTime).getTime();
//                    ScheduleUtils.setAlarmTimeFromNotifycation(mContext, time - ((Integer.parseInt(scheduleDetailResultBean.firstRemind)-1) * 5 * 60 * 1000), scheduleDetailResultBean.appId,
//                            scheduleDetailResultBean.schId,
//                            scheduleDetailResultBean.schTitle, scheduleDetailResultBean.firstRemind,mReceiverBean.push_title);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }


        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;
        if (mReceiverBean != null && !mReceiverBean.equals("")) {
//            if (mReceiverBean.is_need_alert == 1) {//is_need_alert为1的时候需要提示框notifycation
            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfo(mReceiverBean.mPush.getString("appId"));
            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            ApplicationAllEnum.SCHEDULEDETAIL.mAppInfo = appInfo;
            if (ApplicationAllEnum.SCHEDULE.mAppInfo == null) {
                ApplicationAllEnum.SCHEDULE.appId = appInfo.getApp_id() + "";
                ApplicationAllEnum.SCHEDULE.mAppInfo = appInfo;
            }
            parameters.put("user_id", OAConText.getInstance(mContext).UserID);
            parameters.put("app_id", mReceiverBean.mPush.getString("appId"));
            parameters.put("sch_id", mReceiverBean.mPush.getString("schId"));
//            parameters.put("form_id", mReceiverBean.mPush.getString("form_id"));
            parameters.put("title", mReceiverBean.mPush.getString("title"));
            parameters.put("flag", "1");
//            parameters.put("iconurl", mReceiverBean.mPush.getString("iconurl"));
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.SCHEDULEDETAIL, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
//                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
            showNotification(mReceiverBean.push_title, "日程确认提醒", mReceiverBean.mPush.getString("title"), 0, 200, contentIntent);
//            }
        }
//        if (isreceipt == 1)
//            ReceiptMessage(pushid);
    }

    //---------------------------------------日程成立提醒-------------------------------------------
    public void ScheduleSetupAlert(String pushid, int isreceipt) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;

        if (mReceiverBean != null && !mReceiverBean.equals("")) {
            // //判断参与人中是否包含自己，包含的话需要创建本地提醒
            ScheduleDetailBean scheduleDetailResultBean = JSONObject.parseObject(mReceiverBean.push_info, ScheduleDetailBean.class);
            if (null != scheduleDetailResultBean) {
                if (scheduleDetailResultBean.participation.contains(OAConText.getInstance(mContext).UserID)) {
                    SimpleDateFormat simpleDateFormat;
                    if (scheduleDetailResultBean.schBeginTime.length() == 18)
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    else
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long time = 0;
                    try {
                        time = simpleDateFormat.parse(scheduleDetailResultBean.schBeginTime).getTime();
                        ScheduleUtils.setAlarmTimeFromNotifycation(mContext, time - ((Integer.parseInt(scheduleDetailResultBean.firstRemind) - 1) * 5 * 60 * 1000), scheduleDetailResultBean.appId,
                                scheduleDetailResultBean.schId,
                                scheduleDetailResultBean.schTitle, scheduleDetailResultBean.firstRemind, mReceiverBean.push_title);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfo(mReceiverBean.mPush.getString("appId"));
                Map<String, Object> parameters = new HashMap<String, Object>();
                com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
                ApplicationAllEnum.SCHEDULEDETAIL.mAppInfo = appInfo;
                if (ApplicationAllEnum.SCHEDULE.mAppInfo == null) {
                    ApplicationAllEnum.SCHEDULE.appId = appInfo.getApp_id() + "";
                    ApplicationAllEnum.SCHEDULE.mAppInfo = appInfo;
                }
                parameters.put("user_id", OAConText.getInstance(mContext).UserID);
                parameters.put("app_id", mReceiverBean.mPush.getString("appId"));
                parameters.put("sch_id", mReceiverBean.mPush.getString("schId"));
//            parameters.put("form_id", mReceiverBean.mPush.getString("form_id"));
                parameters.put("title", mReceiverBean.mPush.getString("title"));
                parameters.put("flag", "1");
//            parameters.put("iconurl", mReceiverBean.mPush.getString("iconurl"));
                try {
                    Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.SCHEDULEDETAIL, parameters);
                    contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                } catch (NotApplicationException e) {
                    Log.e(this.getClass().getName(), e.getMessage());
                }
//                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
                showNotification(mReceiverBean.push_title, "日程成立提醒", mReceiverBean.mPush.getString("title"), 0, 201, contentIntent);


            }
        }

    }

    //---------------------------------------日程修改提醒-------------------------------------------
    public void ScheduleUpdateAlert(String pushid, int isreceipt) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;
        //判断参与人中是否包含自己，包含的话需要创建本地提醒
        if (mReceiverBean != null && !mReceiverBean.equals("")) {
            ScheduleDetailBean scheduleDetailResultBean = JSONObject.parseObject(mReceiverBean.push_info, ScheduleDetailBean.class);
            if (null != scheduleDetailResultBean) {
                if (scheduleDetailResultBean.participation.contains(OAConText.getInstance(mContext).UserID)) {
                    SimpleDateFormat simpleDateFormat;
                    if (scheduleDetailResultBean.schBeginTime.length() == 18)
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    else
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    long time = 0;
                    try {
                        time = simpleDateFormat.parse(scheduleDetailResultBean.schBeginTime).getTime();
                        ScheduleUtils.setAlarmTimeFromNotifycation(mContext, time - ((Integer.parseInt(scheduleDetailResultBean.firstRemind) - 1) * 5 * 60 * 1000), scheduleDetailResultBean.appId,
                                scheduleDetailResultBean.schId,
                                scheduleDetailResultBean.schTitle, scheduleDetailResultBean.firstRemind, mReceiverBean.push_title);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }

            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfo(mReceiverBean.mPush.getString("appId"));
            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            ApplicationAllEnum.SCHEDULEDETAIL.mAppInfo = appInfo;
            if (ApplicationAllEnum.SCHEDULE.mAppInfo == null) {
                ApplicationAllEnum.SCHEDULE.appId = appInfo.getApp_id() + "";
                ApplicationAllEnum.SCHEDULE.mAppInfo = appInfo;
            }
            parameters.put("user_id", OAConText.getInstance(mContext).UserID);
            parameters.put("app_id", mReceiverBean.mPush.getString("appId"));
            parameters.put("sch_id", mReceiverBean.mPush.getString("schId"));
//            parameters.put("form_id", mReceiverBean.mPush.getString("form_id"));
            parameters.put("title", mReceiverBean.mPush.getString("title"));
            parameters.put("flag", "1");
//            parameters.put("iconurl", mReceiverBean.mPush.getString("iconurl"));
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.SCHEDULEDETAIL, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
//                showNotification("", "", mReceiverBean.push_title, 0, 0, null);
            showNotification(mReceiverBean.push_title, "日程修改提醒", mReceiverBean.mPush.getString("title"), 0, 202, contentIntent);
        }

    }

    //---------------------------------------工作流推送---------------------------------------------
    public void OAflowFormPushInfo(ReceiverBean mReceiverBean) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;
        if (mReceiverBean != null && mReceiverBean.mPush.getString("app_code") != null) {
            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfoByAppCode(mReceiverBean.mPush.getString("app_code"));
            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            ApplicationAllEnum.DBXQ.mAppInfo = appInfo;
            if (ApplicationAllEnum.DB.mAppInfo == null && appInfo != null) {
                ApplicationAllEnum.DB.appId = appInfo.getApp_id() + "";
                ApplicationAllEnum.DB.mAppInfo = appInfo;
            }
            parameters.put("DocId", mReceiverBean.mPush.getString("DocId"));
            if(TextUtils.isEmpty(mReceiverBean.mPush.getString("DocId"))){
                parameters.put("DocId", mReceiverBean.mPush.getString("workid"));
            }
            parameters.put("DocType", mReceiverBean.mPush.getString("DocType"));
            parameters.put("DocTitle", mReceiverBean.mPush.getString("title"));
            parameters.put("Kind", mReceiverBean.mPush.getString("Kind"));
            parameters.put("app_id", mReceiverBean.mPush.getString("app_id"));
            parameters.put("flowId", mReceiverBean.mPush.getString("workFlowId"));
            parameters.put("IconId", mReceiverBean.mPush.getString("IconId"));
            parameters.put("TodoFlag", "0");
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.DBXQ, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
        }
        showNotification(mReceiverBean.push_title, "您有待办事项", mReceiverBean.mPush.getString("title"), 0, 104, contentIntent);

    }

    //-----------------------------------------工作流列表-----------------------------------
    public void completePushInfoWorkFlowList(ReceiverBean mReceiverBean) {
        PendingIntent contentIntent = null;
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        if (mReceiverBean != null && mReceiverBean.mPush.getString("app_code") != null) {//此处为app_code 为必传
            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfoByAppCode(mReceiverBean.mPush.getString("app_code"));

            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            ApplicationAllEnum.DB.appId = appInfo.getApp_id() + "";
            ApplicationAllEnum.DB.mAppInfo = appInfo;
            parameters.put("Type", true);
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.DB, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }

        }

        showNotification(mReceiverBean.push_title, "您有待办事项", mReceiverBean.mPush.getString("title"), 0, 104, contentIntent);
    }


    //----------------------------------------通用表单推送------------------------------------------
    public void commFormPushInfo(ReceiverBean mReceiverBean) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;
        if (mReceiverBean != null && mReceiverBean.mPush.getString("app_id") != null) {
            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfo(mReceiverBean.mPush.getString("app_id"));
            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            ApplicationAllEnum.TYBDXQ.mAppInfo = appInfo;
            if (ApplicationAllEnum.TYBD.mAppInfo == null) {
                ApplicationAllEnum.TYBD.appId = appInfo.getApp_id() + "";
                ApplicationAllEnum.TYBD.mAppInfo = appInfo;
            }
            parameters.put("user_id", OAConText.getInstance(mContext).UserID);
            parameters.put("app_id", mReceiverBean.mPush.getString("app_id"));
            parameters.put("data_id", mReceiverBean.mPush.getString("data_id"));
            parameters.put("form_id", mReceiverBean.mPush.getString("form_id"));
            parameters.put("doc_title", mReceiverBean.mPush.getString("title"));
            parameters.put("iconurl", mReceiverBean.mPush.getString("iconurl"));
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.TYBDXQ, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
        }
        showNotification(mReceiverBean.push_title, "您有待办事项", mReceiverBean.mPush.getString("title"), 0, 105, contentIntent);
    }

    //----------------------------------------提醒中心推送------------------------------------------
    public void comAlertPushInfo(ReceiverBean mReceiverBean) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        PendingIntent contentIntent = null;
        if (mReceiverBean != null) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(mContext);
            parameters.put("user_id", OAConText.getInstance(mContext).UserID);
            parameters.put("title", mReceiverBean.mPush.getString("title"));
            parameters.put("source_type", mReceiverBean.mPush.getString("source_type"));
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.TXZX, parameters);
                contentIntent = PendingIntent.getActivity(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }
        }
        showNotification(mReceiverBean.push_title, "您有待办事项", mReceiverBean.mPush.getString("title"), 0, 106, contentIntent);
    }

    //----------------------------------------展示推送框提示----------------------------------------
    public void showNotification(String tickerText, String contentTitle,
                                 String contentText, int iconId, int notiId, PendingIntent contentIntent) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(contentTitle)
                .setTicker(tickerText)
                .setContentText(contentText);
//                .setNumber(++numMessages)
//                .setContentIntent(contentIntent);
        if (contentIntent != null)
            notification.setContentIntent(contentIntent);
        notification.setAutoCancel(true);
        notificationManager.notify(notiId, notification.build());
    }

    //------------------------------------------强制升级app版本-------------------------------------
    public void ForceUpgradeAppVersion(AppUpgradeInfo upgradeInfo) {
        //如果为强制升级
        BookInit.getInstance().setBoradCast(false);
        PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
        BookInit.getInstance().setOrgUser(null);
        BookInit.getInstance().bitmapClean();

        SmartUpgradeHelper updates = new SmartUpgradeHelper(mContext, upgradeInfo);
        updates.startUpdate(mContext);
    }


    //------------------------------------发送回执信息反馈-----------------------------------------
    public void ReceiptMessage(String pushid) {
//        SharedPreferences sp = mContext.getSharedPreferences(PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        SecuritySharedPreference sp = new SecuritySharedPreference(mContext, PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        String oaUserId = sp.getString(PreferenceUtils.PREFERENCE_ITEM_EMP_USERID, "");
        receiptpushmodel mreceiptpushmodel = new receiptpushmodel(PushReceiverFunctionService.this);
        RequestReceiptBean mRequestReceiptBean = new RequestReceiptBean();
        mRequestReceiptBean.push_id = pushid;
        mRequestReceiptBean.user_id = oaUserId;
        mreceiptpushmodel.getDataFromServerByType(receiptpushmodel.TYPE_POST_RECEIPT, mRequestReceiptBean);

    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {

    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("upgrade")) {
            if (requestValue != null && !"".equals(requestValue.trim()))
                upgradeInfos = com.alibaba.fastjson.JSONObject.parseObject(requestValue, EmpmAppUpgradeInfo.class);
            if (upgradeInfos != null && upgradeInfos.result != null) {
                UpdateServiceHanle mUpdateServiceHanle = new UpdateServiceHanle(mContext);
                mUpdateServiceHanle.showDialog(upgradeInfos.result);
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void sysUserSuccess(boolean flag) {
        Intent intent = new Intent(mContext, ClientTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        ConfigStyleUtil.changeTextSize(mContext, new ConfigStyleUtil.FinishPortalSwitch() {
            @Override
            public void finishPortalSwitchActivity() {

            }
        });

    }

    @Override
    public void setProgressbar(String value) {

    }
}
