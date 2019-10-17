package com.htmitech.emportal.ui.login.model.task;

import java.util.HashMap;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.proxy.myenum.LogManagerEnum;

public class LoginModel extends BaseModel {
	private final String TAG = LoginModel.class.getName();
	private LoginTask mLoginTask;
	private GetUserOptionsTask mUserOptionsTask;
	private SyncDataTask mSyncDataTask;
	public static final int TYPE_LOGIN = 0;
	public static final int TYPE_GetUserOptions = 1; //获得当前用户的常用意见
	public static final int TYPE_GetSyncData = 2;
	public LoginModel(IBaseCallback callback) {
		super(callback);
	}

	protected BaseTaskBody createTask(int type,
			HashMap<String, String> paramHashMap) {
		BaseTaskBody task = null;
		switch (type) {
		case TYPE_LOGIN:
			mLoginTask = new LoginTask(LogManagerEnum.APP_MOBILE_LOGIN.getFunctionCode());
//			mLoginTask.httpUtils.logfunctionCode = LogManagerEnum.APP_MOBILE_LOGIN.functionCode;
			mLoginTask.taskType = TYPE_LOGIN;
			mLoginTask.buildRequestParam(paramHashMap);
			task = mLoginTask;
			break;
		case TYPE_GetUserOptions:
			mUserOptionsTask = new GetUserOptionsTask();
			mUserOptionsTask.taskType = TYPE_GetUserOptions;
			mUserOptionsTask.buildRequestParam(paramHashMap);
			task = mUserOptionsTask;
			break;
		case TYPE_GetSyncData:
			mSyncDataTask = new SyncDataTask();
			mSyncDataTask.taskType = TYPE_GetSyncData;
			mSyncDataTask.buildRequestParam(paramHashMap);
			task = mSyncDataTask;
			break;
		}
		return task;
	}
	
	

	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		// TODO Auto-generated method stub

		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_LOGIN:
			mLoginTask = new LoginTask(LogManagerEnum.APP_MOBILE_LOGIN.getFunctionCode());
			mLoginTask.buildRequestParam(paramObject);
			task = mLoginTask;
			break;
		case TYPE_GetUserOptions:
			mUserOptionsTask = new GetUserOptionsTask();
			mUserOptionsTask.taskType = TYPE_GetUserOptions;
			mUserOptionsTask.buildRequestParam(paramObject);
			task = mUserOptionsTask;
			break;
		case TYPE_GetSyncData:
			mSyncDataTask = new SyncDataTask();
			mSyncDataTask.taskType = TYPE_GetSyncData;
			mSyncDataTask.buildRequestParam(paramObject);
			task = mSyncDataTask;
			break;
		}
		return task;
		
		
	}

	@Override
	public void notifySuccess(int statuscode, Object result) {
		// TODO Auto-generated method stub
		super.notifySuccess(statuscode, result);
	}

	@Override
	public void notifyFail(int requestTypeId, int statuscode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub
		super.notifyFail(requestTypeId, statuscode, errorMsg, result);
	}
	
}
