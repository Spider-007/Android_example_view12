package com.htmitech.ztcustom.zt.thread;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.htmitech.ztcustom.zt.dialog.AlertDialog;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.unit.Network;


/**
 * 网络请求封装
 * @author htrf-pc
 *
 */
public class AnsynHttpRequest {
	
	/**
	 * 
	 * @param context
	 * @param entity
	 * @param url
	 * @param requestType
	 * @param mObserverCallBack
	 */
	public static void request(final Context context, Object entity, String url,
			int requestType, ObserverCallBack mObserverCallBack) {
		if (!Network.isNetworkConnected(context)) {
			new AlertDialog(context).builder().setMsg("无网络!是否打开网络?")
			.setTitle("请设置").setCancelable(false)
			.setNegativeButton("", new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			}).setPositiveButton("", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=null;
	                //判断手机系统的版本  即API大于10 就是3.0或以上版本 
	                if(android.os.Build.VERSION.SDK_INT>10){
	                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
	                }else{
	                    intent = new Intent();
	                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
	                    intent.setComponent(component);
	                    intent.setAction("android.intent.action.VIEW");
	                }
	                context.startActivity(intent);
				}
			}).show();
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack));
		}
		
	}
	
}
