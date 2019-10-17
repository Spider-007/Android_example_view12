package com.htmitech.emportal.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.NotificationCompat;

import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppclientVersion;
import com.htmitech.proxy.util.DialogActivity;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.minxing.client.util.Utils;

/**
 * =======================================
 * 通用在线更新入口
 *
 * @author Administrator Tony
 * =======================================
 *
 */
public class UpdateServiceHanle {

	public Context mContext;

	public int versionCode;

	public String versionName; // 可以只根据VersionCode
	public AppliationCenterDao mAppliationCenterDao;
	public UpdateServiceHanle(Context context) {
		this.mContext = context;
		mAppliationCenterDao = new AppliationCenterDao(mContext);
		getCurrentLocalVersion();
	}

	/**
	 * 获取本地版本信息
	 */
	public void getCurrentLocalVersion() {
		try {
			PackageInfo info = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0);
			this.versionCode = info.versionCode;
			this.versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  获取服务器版本号
	 */
	public void getServerVersionInfomation(String url,Context mContext){
		AnsynHttpRequest.requestByPost(mContext, null, url, CHTTP.POST, new ObserverCallBack() {
			@Override
			public void success(String requestValue) {

			}

			@Override
			public void fail(String exceptionMessage) {

			}

			@Override
			public void notNetwork() {

			}

			@Override
			public void callbackMainUI(String successMessage) {

			}
		});
	}

	/**
	 *  获取本地版本号
	 */
	public void getServerVersionInfomation() {
		AppclientVersion mAppclientVersion = mAppliationCenterDao.getAppClientVersion(versionCode);
		ContrastVersion(mAppclientVersion);
//		HttpAsyncTaskManager http = new HttpAsyncTaskManager(mContext);
//		http.request(url, new StringTaskHandler() {
//			@Override
//			public void onNetError() {
//				// TODO Auto-generated method stub
//				BaseToast.showToastShort(mContext, "网络异常！");
//			}
//
//			@Override
//			public void onSuccess(String result) {
//				// TODO Auto-generated method stub
//				Gson mGson = new Gson();
//				UpdateInfo mUpdateInfo = mGson.fromJson(result,
//						UpdateInfo.class);
//				ContrastVersion(mUpdateInfo);
//			}
//
//			@Override
//			public void onFail() {
//				// TODO Auto-generated method stub
//				BaseToast.showToastShort(mContext, "失败！");
//			}
//		});


	}

	/**
	 * 获取服务器版本号
	 *
	 * @param mUpdateInfo
	 */
	public void ContrastVersion(AppclientVersion mUpdateInfo) {
		if (mUpdateInfo != null && mUpdateInfo.getVersion_no() > versionCode) {
			showDialog(mUpdateInfo);
		}
	}

	/**
	 * 获取本地版本号
	 *
	 * @param mUpdateInfo
	 */
	String path="";
	public void showDialog(final AppclientVersion mUpdateInfo) {
		if (Utils.isBackground(mContext)){
			Intent intent = new Intent(mContext, DialogActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("result",mUpdateInfo);
			PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("版本升级")
					.setTicker(mUpdateInfo.getVersion_name())
					.setContentText(mUpdateInfo.getUpdate_desc());
//                .setNumber(++numMessages)
//                .setContentIntent(contentIntent);
			if (contentIntent != null)
				notification.setContentIntent(contentIntent);
			notification.setAutoCancel(true);
			NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
			notificationManager.notify(0, notification.build());
		}else{
			Intent intent = new Intent(mContext, DialogActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("result",mUpdateInfo);
			mContext.startActivity(intent);
		}

//		if(mUpdateInfo.getMustupdated() == 2){
//			new com.htmitech.pop.AlertDialog(mContext).builder().setTitle(mUpdateInfo.getVersion_name()+"版本").setMsg(mUpdateInfo.getUpdate_desc()).setPositiveButton("现在升级", new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(mUpdateInfo != null && mUpdateInfo.resetClient == 1){
//						if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//							 path = Environment.getExternalStorageDirectory().getPath()+ File.separator+"htmitech";
//						}
//						if(!"".equals(path))
//							CacheDeleteUtils.clearFiles(path);
//					}
//					Intent updateIntent = new Intent(mContext,
//							UpdateService.class);
//					updateIntent.putExtra("url", mUpdateInfo.getDownload_url());
//					updateIntent.putExtra("app_name",
//							mContext.getPackageName());
//					mContext.startService(updateIntent);
//				}
//			}).setType().setCancelable(mUpdateInfo.mustupdated == 2 ?false:true).show();
//		}else{
//			new com.htmitech.pop.AlertDialog(mContext).builder().setTitle(mUpdateInfo.getVersion_name()+"版本").setMsg(mUpdateInfo.getUpdate_desc()).setPositiveButton("现在升级", new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if(mUpdateInfo != null && mUpdateInfo.resetClient == 1){
//						if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//							path = Environment.getExternalStorageDirectory().getPath()+ File.separator+"htmitech";
//						}
//						if(!"".equals(path))
//							CacheDeleteUtils.clearFiles(path);
//					}
//					Intent updateIntent = new Intent(mContext,
//							UpdateService.class);
//					updateIntent.putExtra("url", mUpdateInfo.getDownload_url());
//					updateIntent.putExtra("app_name",
//							mContext.getPackageName());
//					mContext.startService(updateIntent);
//				}
//			}).setNegativeButton("暂不升级", null).setType().setCancelable(mUpdateInfo.mustupdated == 2 ?false:true).show();
//		}

	}
}
