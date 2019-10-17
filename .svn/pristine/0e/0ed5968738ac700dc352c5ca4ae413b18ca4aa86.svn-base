package com.htmitech.emportal.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.HttpHandler;
import com.htmitech.commonx.base.http.ResponseInfo;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.http.client.HttpRequest;
import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.utils.MyLogUtil;

public class BaseNetRequestHandler {

	private volatile boolean mTaskHasDone = true;

	private String mToken = "";

	private NetWorkRequestCallback mNetWorkRequestCallback;

	private BaseModel mModel;

	private BaseTaskBody mTaskBody;

	AppPreferenceHelper mPreferenceHelper = AppPreferenceHelper
			.getInstance(HtmitechApplication.instance());


	/** "StatusCode": 200,
	 "StatusMessage": "操作成功",*/
	public static final int CODE_STATUSSUCCESS = 200;

	/** 已经正常执行完成服务器端的方法*/
	public static final int STATUSSUCCESS = 1;


	/** 执行完成服务器端的方法但是返回结果是失败*/
	public static final int STATUSERROR = 0;


	// Success
	public static final int CODE_SUCCESS = 0;

	/**
	 * 未定义的错误信息，或者服务器未返回任何statuscode 即为 Fail for other reasons Failed
	 */
	public static final int CODE_FAIL_STATUS = 1;
	/** NonLogin */
	public static final int CODE_FAIL_NO_LOGIN = 2;
	/** InvalidParams */
	public static final int CODE_FAIL_INVALID_PARAMS = 3;
	/** PermissionDenied */
	public static final int CODE_FAIL_PERMISSION_DENIED = 4;
	/** Spam */
	public static final int CODE_FAIL_SPAM = 5;
	/** Deleted */
	public static final int CODE_FAIL_DELETED = 6;

	public BaseNetRequestHandler(BaseModel model) {
		mNetWorkRequestCallback = new NetWorkRequestCallback();
		mModel = model;
	}

	/**
	 * 根据task 先从缓存中拿缓存，然后请求网络 根据后台返回的token 判断是否需要替换缓存，刷新ui。
	 *
	 * @param task
	 */
	public <T> HttpHandler<T> getData(BaseTaskBody task) {
		mTaskHasDone = false;
		if (task != null) {
			mTaskBody = task;
		}
		if (!TextUtils.isEmpty(mTaskBody.getPreferenceKey())) {
			mToken = mPreferenceHelper.getString(mTaskBody.getPreferenceKey(),
					"");
		}
		// 是否已经具有旧数据
		if (!mModel.hasResult()) {
			new ReadCacheFromTask().execute();
		}

		// if (!mModel.hasResult()) {
		// Object result = mTaskBody.readCacheFromDisk();
		// if (result != null) {
		// if (mModel != null)
		// mModel.notifySuccess(CODE_SUCCESS, result);
		// } else {
		// mPreferenceHelper.putString(mTaskBody.getPreferenceKey(), "");
		// }
		// }
		return getDataFromNet();
	}

	private class ReadCacheFromTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			Object result = mTaskBody.readCacheFromDisk();
			if (!mTaskHasDone) {// 还没联网结束，可以使用本地缓存
				if (result != null) {
					if (mModel != null)
						mModel.notifySuccess(CODE_SUCCESS, result);
				} else {
					mPreferenceHelper.putString(mTaskBody.getPreferenceKey(),
							"");
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	@SuppressWarnings("unchecked")
	private <T> HttpHandler<T> getDataFromNet() {
		if (!TextUtils.isEmpty(mTaskBody.getPreferenceKey())) {
			mPreferenceHelper.putString(mTaskBody.getPreferenceKey(), "");
		}
		HttpRequestEntity entity = mTaskBody.buildRequestEntity();
		HttpHandler<T> httpHandler = null;
		if (entity != null) {
			switch (mTaskBody.getRequestType()) {
				case BaseTaskBody.GET_HTTP_REQUEST_TYPE:
					// 不缓存请求,否则，更新的数据不能立刻拿到
					mTaskBody.httpUtils.configHttpCacheSize(0);

					httpHandler = (HttpHandler<T>) mTaskBody.httpUtils.send(HttpRequest.HttpMethod.GET,
							entity.getUrl(), entity.getRequestParams(),
							mNetWorkRequestCallback);
					break;
				case BaseTaskBody.POST_HTTP_REQUEST_TYPE:
					// 不缓存请求,否则，更新的数据不能立刻拿到
					mTaskBody.httpUtils.configHttpCacheSize(0);

					httpHandler = (HttpHandler<T>) mTaskBody.httpUtils.send(HttpRequest.HttpMethod.POST,
							entity.getUrl(), entity.getRequestParams(),
							mNetWorkRequestCallback);


					break;
				default:
					break;
			}
		}
		return httpHandler;
	}

	/****
	 * 联网成功 onSuccess ， code == CODE_SUCCESS 请求ok的 ，其他的code 都是error 联网失败 onFail
	 */

	private final class NetWorkRequestCallback extends RequestCallBack<String> {

		@Override
		public void onSuccess(ResponseInfo<String> responseInfo) {
			// TODO Auto-generated method stub
			try {
				if (responseInfo != null && responseInfo.result != null
						&& responseInfo.result.length() > 0) {
					// 记录是否已经成功读取缓存。
					String result = responseInfo.result;
					boolean hasReadCache = mTaskBody.hasDataEntity();
					Object entity = mTaskBody.parseJson(result);
					String errorMsg = mTaskBody.getErrorMsg();
					if (errorMsg == null || entity == null) {
						errorMsg = "数据读取错误";
						MyLogUtil.e("BaseNetRequestHandler", result);
					}

					// 通过返回的 statuscode 判断是否对于server 方法调用是否成功。
					if (mTaskBody.getStatusCode() != CODE_STATUSSUCCESS || mTaskBody.getStatus() != STATUSSUCCESS
							|| entity == null  ) {
						if (mModel != null)
							mModel.notifyFail(mTaskBody.taskType,
									mTaskBody.getStatusCode(), errorMsg, result);
					} else {
						int code = mTaskBody.getStatusCode();
						// 如果token 不同或者读取缓存失败，存储token, 重写本地缓存。
						if (TextUtils.isEmpty(mToken)
								|| mToken != mTaskBody.getToken()
								|| !hasReadCache) {
							mTaskBody.writeCache2Disk(entity);
							mModel.notifySuccess(mTaskBody.taskType,
									entity);
							mPreferenceHelper.putString(
									mTaskBody.getPreferenceKey(),
									mTaskBody.getToken());
						}
					}
				} else {
					mModel.notifyFail(mTaskBody.taskType, CODE_FAIL_STATUS,
							"数据返回异常",null);
				}
			} catch (Exception e) {
				MyLogUtil.e("BaseNetRequestHandler  error");
			}
			// 请求成功之后，修改标识
			mTaskHasDone = true;
			if (mModel != null)
				mModel.next(mTaskBody);
		}

		@Override
		public void onFailure(HttpException error, String msg) {
			// TODO Auto-generated method stub
			String errorMsg = "网络连接失败";
			if (CommonSettings.DEBUG) {
				errorMsg = TextUtils.isEmpty(msg) ? errorMsg : "网络连接失败:" + msg;
			}
			mModel.notifyFail(mTaskBody.taskType, mTaskBody.getStatusCode(),
					errorMsg + "", null);
			mTaskHasDone = true;
			if (mModel != null)
				mModel.next(mTaskBody);
		}
	}

	public boolean isIdle() {
		return mTaskHasDone;
	}

	public void setIsIdle(){
		mTaskHasDone = true;
	}
}
