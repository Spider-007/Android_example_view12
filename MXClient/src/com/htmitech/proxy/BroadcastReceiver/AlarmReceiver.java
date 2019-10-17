package com.htmitech.proxy.BroadcastReceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.htmitech.emportal.R;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.ApplicationAllEnum;


import com.minxing.client.util.Utils;

import java.util.HashMap;
import java.util.Map;

import activity.ScheduleDetailActivity;
import scheduleEnum.FristReminEnum;

/**
 * Created by yanxin on 2017-9-26.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private String appId;
    private String detailConfig;
    private String schId;
    private String dropdownOptionManual;
    private String includeSecurity;

    private String fristremin;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if ("com.htmitech.proxy.BroadcastReceiver.AlarmReceiver".equals(intent.getAction())) {
            appId = intent.getStringExtra("app_id");
            detailConfig = intent.getStringExtra("com_schedule_mobileconfig_detailview_config");
            schId = intent.getStringExtra("sch_id");
            dropdownOptionManual = intent.getStringExtra("com_schedule_mobileconfig_dropdown_option_manual");
            includeSecurity = intent.getStringExtra("com_schedule_mobileconfig_include_security");
            String title = intent.getStringExtra("title");
            fristremin = intent.getStringExtra("fristremin");
            String reminValut = FristReminEnum.getReminValut(fristremin);
            boolean applicationForeground = Utils.isApplicationForeground(context);
            Intent intent1 = new Intent(context, ScheduleDetailActivity.class);
            intent1.putExtra("app_id", appId);
            intent1.putExtra("com_schedule_mobileconfig_detailview_config", detailConfig);
            intent1.putExtra("sch_id", schId);
            intent1.putExtra("flag", "1");
            intent1.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
            intent1.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
            showAlarmWay(intent1,context,title,"日程提醒");
//            if(!applicationForeground){
//                Intent intent1 = new Intent(context, ScheduleDetailActivity.class);
//                intent1.putExtra("app_id", appId);
//                intent1.putExtra("com_schedule_mobileconfig_detailview_config", detailConfig);
//                intent1.putExtra("sch_id", schId);
//                intent1.putExtra("flag", "1");
//                intent1.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
//                intent1.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
//                intent1.putExtra("title", title);
//                intent1.putExtra("fristremin", fristremin);
//                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationManager  notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//                NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("日程提醒")
////                        .setTicker("123")
//                        .setContentText(title)
////                .setNumber(++numMessages)
//                .setContentIntent(contentIntent);
////            if (contentIntent != null)
////                notification.setContentIntent(contentIntent);
//                notification.setAutoCancel(true);
//                notificationManager.notify(1, notification.build());
//            }else{
//                final com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(context);
//                mDialog.builder().setTitle(title).setMsg(reminValut)
//                        .setNegativeButton("知道了", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mDialog.setDismiss();
//                            }
//                        })
//                        .setPositiveButton("打开日程", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent1 = new Intent(context, ScheduleDetailActivity.class);
//                                intent1.putExtra("app_id", appId);
//                                intent1.putExtra("com_schedule_mobileconfig_detailview_config", detailConfig);
//                                intent1.putExtra("sch_id", schId);
//                                intent1.putExtra("flag", "1");
//                                intent1.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
//                                intent1.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
//                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent1);
//                            }
//                        }).setType().show();
//            }

            return;
        }else if("setupScheduleAlarm".equals(intent.getAction())){
            String appId = intent.getStringExtra("app_id");
            String schId = intent.getStringExtra("sch_id");
            String title = intent.getStringExtra("title");

            String push_title = intent.getStringExtra("push_title");
            String reminValut = FristReminEnum.getReminValut(fristremin);
            fristremin = intent.getStringExtra("fristremin");
            com.htmitech.proxy.managerApp.ClentAppUnit mClentAppUnit = com.htmitech.proxy.managerApp.ClentAppUnit.getInstance(context);
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(context);
            com.htmitech.proxy.doman.AppInfo appInfo = mAppliationCenterDao.getAppInfo(appId);
            ApplicationAllEnum.SCHEDULEDETAIL.mAppInfo = appInfo;
            if (ApplicationAllEnum.SCHEDULE.mAppInfo == null) {
                ApplicationAllEnum.SCHEDULE.appId = appInfo.getApp_id() + "";
                ApplicationAllEnum.SCHEDULE.mAppInfo = appInfo;
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("app_id",appId);
            parameters.put("app_id",schId);
            parameters.put("title",title);
            parameters.put("flag", "1");
            try {
                Intent mIntent = mClentAppUnit.getActivity(ApplicationAllEnum.SCHEDULEDETAIL, parameters);
                showAlarmWay(mIntent,context,title,push_title);
//                PendingIntent contentIntent =  PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (NotApplicationException e) {
                Log.e(this.getClass().getName(), e.getMessage());
            }

        }
    }

    public void showAlarmWay(final Intent mIntent,final Context context,String title,String pushTitle){
        boolean applicationForeground = Utils.isApplicationForeground(context);
        if(!applicationForeground){
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager  notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(pushTitle)
//                        .setTicker("123")
                    .setContentText(title)
//                .setNumber(++numMessages)
                    .setContentIntent(contentIntent);
//            if (contentIntent != null)
//                notification.setContentIntent(contentIntent);
            notification.setAutoCancel(true);
            notificationManager.notify(1, notification.build());
        }else{
            final com.htmitech.pop.AlertDialog mDialog = new com.htmitech.pop.AlertDialog(context);
            mDialog.builder().setTitle(pushTitle).setMsg(title)
                    .setNegativeButton("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.setDismiss();
                        }
                    })
                    .setPositiveButton("打开日程", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(mIntent);
                        }
                    }).setType().show();
        }

    }
}
