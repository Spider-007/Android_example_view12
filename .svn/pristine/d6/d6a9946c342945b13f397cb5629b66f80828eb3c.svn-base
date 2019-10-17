package com.htmitech.thread;

import android.content.Context;

import com.htmitech.downmanage.DownAsyncTaskManager;
import com.htmitech.downmanage.DownTaskHandler;

import htmitech.com.componentlibrary.listener.ObserverCallBack;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

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
	public static void requestByPost(Context context, Object entity, String url,
			int requestType, ObserverCallBack mObserverCallBack){
		if (!Network.checkNetWork(context)) {
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack));
		}

	}



	/**
	 * 此方法是带有log日志接口的统一调用
	 */
	public static void requestByPost(Context context, Object entity, String url, int requestType, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode ){
		if (!Network.checkNetWork(context)) {
			mObserverCallBackType.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBackType,requestName,funactionCode));
		}

	}

	/**
	 *
	 * @param context
	 * @param entity
	 * @param url
	 * @param requestType
	 * @param mObserverCallBack
	 */



	public static void requestByPostWithHeader(Context context, Object entity, String url,
											   int requestType, ObserverCallBack mObserverCallBack, String funactionCode){
		if (!Network.checkNetWork(context)) {
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack,funactionCode));
		}

	}

	/**
	 *
	 * @param context
	 * @param entity
	 * @param url
	 * @param requestType
	 * @param mObserverCallBack
	 */

	public static void requestByPostWithToken(Context context, Object entity, String url,
											   int requestType, ObserverCallBackType mObserverCallBack,String RequestName,String funactionCode){
		if (!Network.checkNetWork(context)) {
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack,RequestName,funactionCode));
		}

	}


	public static void requestByGet(Context context,Object entity, String url,
									 int requestType, ObserverCallBack mObserverCallBack){
		if (!Network.checkNetWork(context)) {
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack));
		}

	}

	public static void fileParsing(Context context,String zipName,ObserverCallBack mObserverCallBack){
		ThreadPoolUtils.execute(new FileThread(context, zipName, mObserverCallBack));
	}

	//上传
	public static void requestByPostWithTokenUpLoad(Context context, Object entity, String url,
											  int requestType, ObserverCallBackType mObserverCallBack,String RequestName,String funactionCode){
		if (!Network.checkNetWork(context)) {
			mObserverCallBack.notNetwork();
		}else{
			ThreadPoolUtils.execute(new RequestThread(context, entity, url, requestType, mObserverCallBack,RequestName,funactionCode));
		}

	}

	/**
	 * 下载管理
	 * @param context
	 * @param url
	 * @param filePath
	 * @param handler
	 */
	public static void downLoadTask(Context context,int postion,String url, String filePath, DownTaskHandler handler){
		if (!Network.checkNetWork(context)) {
			handler.notNetwork();
		}else{
			ThreadPoolUtils.execute(new DownAsyncTaskManager(url,postion, filePath, handler));
		}
	}

}
