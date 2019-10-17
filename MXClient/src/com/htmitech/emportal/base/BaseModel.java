package com.htmitech.emportal.base;

import java.util.ArrayList;
import java.util.HashMap;

import com.htmitech.commonx.base.http.HttpHandler;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;

import android.os.Handler;
import android.os.Looper;

public class BaseModel {
	private BaseNetRequestHandler mRequestHandler;
	private Handler mMainHandler = new Handler(Looper.getMainLooper());
	private ArrayList<BaseTaskBody> mTaskList = new ArrayList<BaseTaskBody>();
	private ArrayList<HttpHandler<Object>> mHandlerList = new ArrayList<HttpHandler<Object>>();

	// 页面是否返回过数据。
	private boolean hasResult = false;

	private boolean isCanceled = false;

	// 注入回调
	private IBaseCallback mInjectCallback = new IBaseCallback() {

		@Override
		public void onSuccess(int statusCode, Object result) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result)  {
			// TODO Auto-generated method stub

		}

	};

	public BaseModel(IBaseCallback callback) {
		if (callback != null) {
			mInjectCallback = callback;
		}
		mRequestHandler = new BaseNetRequestHandler(this);
	}

	/**
	 * 统一网络访问路径。 TODO ： 应该将参数封装在model。
	 *
	 * @param type
	 * @param paramHashMap
	 * @return 是否成功发起请求。
	 */
	public void getDataFromServerByType(int type,
										HashMap<String, String> paramHashMap) {
		isCanceled = false;
		addTask(createTask(type, paramHashMap));
		if (mRequestHandler.isIdle() && mTaskList.size() > 0) {
			addHttpHandler(mRequestHandler.getData(mTaskList.get(0)));
		}

	}



	public void getDataFromServerByType(int type, Object paramObject) {
		isCanceled = false;
		addTask(createTask(type, paramObject));
		if (mRequestHandler.isIdle() && mTaskList.size() > 0) {
			addHttpHandler(mRequestHandler.getData(mTaskList.get(0)));
		}
	}

	public void abort(){
		mRequestHandler.setIsIdle();
		isCanceled = true;
		for (int i = 0; i < mHandlerList.size(); i++) {
			mHandlerList.get(i).cancel();
		}
		mHandlerList.clear();
		mTaskList.clear();
	}

	public void notifySuccess(final int requestTypeId, final Object result) {
		// 如果页面没有包含缓存.则依照缓存类型改变条件
		if (result != null && isSameTypeResult(result))
			hasResult = true;

		synchronized (mInjectCallback) {
			if (mInjectCallback != null) {
				mMainHandler.post(new Runnable() {
					@Override
					public void run() {
						if (!isCanceled)
							mInjectCallback.onSuccess(requestTypeId, result);
					}
				});
			}
		}
	}

	public void notifyFail(final int requestTypeId, final int statuscode, final String errorMsg,
						   final Object result) {
		synchronized (mInjectCallback) {
			if (mInjectCallback != null) {
				mMainHandler.post(new Runnable() {
					@Override
					public void run() {
						if (!isCanceled)
							mInjectCallback.onFail(requestTypeId, statuscode, errorMsg,
									result);
					}
				});
			}
		}
	}

	public synchronized void next(BaseTaskBody pretask) {
		if (mTaskList.size() > 0) {
			mTaskList.remove(pretask);
			if (mTaskList.size() > 0) {
				mRequestHandler.getData(mTaskList.get(0));
			}
		}
	}

	private boolean addTask(BaseTaskBody baseTaskBody) {
		if (baseTaskBody != null) {
			boolean hasAdd = false;
			for (int i = 0; i < mTaskList.size(); i++) {
				hasAdd = mTaskList.get(i).isSameType(baseTaskBody);
			}
			if (!hasAdd) {
				mTaskList.add(baseTaskBody);
				return true;
			}
		}
		return false;
	}

	private boolean addHttpHandler(HttpHandler<Object> httpHandler) {
		if (httpHandler != null && !httpHandler.isCancelled()) {
			boolean hasAdd = false;
			for (int i = 0; i < mHandlerList.size(); i++) {
				hasAdd = mHandlerList.get(i).equals(httpHandler);
			}
			if (!hasAdd) {
				mHandlerList.add(httpHandler);
				return true;
			}
		}
		return false;
	}

	protected BaseTaskBody createTask(int taskType,
									  HashMap<String, String> paramHashMap) {
		return null;
	}

	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		return null;
	}

	/**
	 * 子类重载方法，用于判断是否已经成功请求过 class 类型的数据。（即页面中是否已经存在缓存），<br>
	 * 如果不重载改方法，则认为成功请求过页面就含有必须的缓存。
	 *
	 * @return
	 */
	protected boolean isSameTypeResult(Object result) {
		return true;
	}

	public void setCallBack(IBaseCallback callback) {
		if (callback != null) {
			synchronized (mInjectCallback) {
				mInjectCallback = callback;
			}
		}
	}

	public boolean hasResult() {
		return hasResult;
	}

	public int getType() {
		return 0;
	}

}
