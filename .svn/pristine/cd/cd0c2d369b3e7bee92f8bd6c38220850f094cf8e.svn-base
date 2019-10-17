package com.htmitech.emportal.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.detail.DetailActivity;
import com.htmitech.pop.AlertDialog;
import com.minxing.client.ClientTabActivity;

import htmitech.com.componentlibrary.content.MXConstant;

public class HTPushReceiver extends BroadcastReceiver {


	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(MXConstant.BroadcastAction.MXKIT_OUTSIDE_PUSH_MESSAGE)) {

			final String pushData = intent.getStringExtra(MXConstant.IntentKey.MXKIT_OUTSIDE_PUSH_MESSAGE_KEY);
			if (pushData != null && !"".equals(pushData)) {
				Log.d("MXKitPushReceiver", pushData);
				JSONObject data = null;
				//判断是否为JSON数据格式
				try {
					data = JSONObject.parseObject(pushData);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// JSON数据格式处理
				if(data != null){
					String type = data.getString("msgtype"); //消息类型 todo 为 待办推送
					if (type != null && type.equalsIgnoreCase("todo")){
						String docId = data.getString("workid");  //待办流程实例id
						String docTitle = data.getString("title");  //待办流程实例标题
						String docKind = data.getString("kind");  //待办流程appid
						String docType = data.getString("doctype");
						Log.d("MXKitPushReceiver", "docTitle:" + docTitle);

						//启动nodifiaction
						//获取到系统服务中的通知服务NOTIFICATION_SERVICE
						NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						Intent notificationIntent = new Intent(context,
								DetailActivity.class); // 点击该通知后要跳转的Activity
						notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						notificationIntent.putExtra("DocId", docId);
						notificationIntent.putExtra("DocTitle", docTitle);
						notificationIntent.putExtra("Kind", docKind);
						notificationIntent.putExtra("DocType", docType);
						PendingIntent pendingIntent = PendingIntent
								.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						Notification notification = new Notification.Builder(context)
								.setContentTitle("待办提醒")
								.setContentText(docTitle)
								.setContentIntent(pendingIntent)
								.setSmallIcon(R.drawable.ic_launcher)
								.build();
						notification.flags = Notification.FLAG_AUTO_CANCEL;
						//FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
						//FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
						//FLAG_ONGOING_EVENT 通知放置在正在运行
						//FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
						//notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
						//notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
						notification.flags |= Notification.DEFAULT_LIGHTS;
						notification.flags |= Notification.DEFAULT_SOUND;
						notification.flags |= Notification.DEFAULT_VIBRATE;
						//DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
						//DEFAULT_LIGHTS  使用默认闪光提示
						//DEFAULT_SOUND  使用默认提示声音
						//DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
						notification.defaults = Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;
						//叠加效果常量
						//notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
						notification.ledARGB = Color.BLUE;
						notification.ledOnMS =5000; //闪光时间，毫秒



						// PendingIntent为一个特殊的Intent,通过getBroadcast或者getActivity或者getService得到.

//						notification.setLatestEventInfo(context, "待办提醒",
//								docTitle, pendingIntent);
						RemoteViews contentView = new RemoteViews(HtmitechApplication.instance().getPackageName(),
								R.layout.notification_message);
						contentView.setImageViewResource(R.id.notificationImage, R.drawable.ic_launcher);
						contentView.setTextViewText(R.id.notificationTitle, "待办提醒");
						contentView.setTextViewText(R.id.notificationPercent, "新消息");
						notification.contentView = contentView;
						if (ClientTabActivity.todoTabItem != null)
							ClientTabActivity.todoTabItem.showMarker();

						// 启动通知事件
						nManager.notify(0, notification);
					}else if(type != null && type.equalsIgnoreCase("todo")){
						showNormalDialog(context);
					}
				}

			}
		}

	}

	private void showNormalDialog(final Context context){

		new AlertDialog(TestActivityManager.getInstance().getCurrentActivity()).builder().setTitle("同步数据").setMsg("同步数据").setPositiveButton("同步数据", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				YnchronizeData ynchronizeData=new YnchronizeData(context);
				ynchronizeData.startServiceToYnchronizeData();
			}
		}).setNegativeButton("暂不同步", new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		}).show();
	}



}
