package com.htmitech.emportal.ui.login.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.login.data.logindata.LoginEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class LoginTask extends BaseTaskBody {

	private final String TAG = LoginTask.class.getName();
	public static int TASK_TYPE = 3;
	private LoginEntity mLoginEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	public LoginTask(String functionCode) {
		super(functionCode);
	}

	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected int getRequestType() {
		return POST_HTTP_REQUEST_TYPE;
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {
		HttpRequestEntity requestEntity = new HttpRequestEntity(
				ServerUrlConstant.SERVER_EMPAPI_URL(),
				ServerUrlConstant.USERINFO_LOGIN_METHOD);
		if (paramObject != null) {
			requestEntity.setRequestObject(paramObject);
		}
		else if (paramHashMap != null) {
			requestEntity.setJSONParameter(paramHashMap);
		}
		
		paramHashMap.clear();
		return requestEntity;
	}

	public void buildRequestParam(HashMap<String, String> temp) {
		if (paramHashMap == null) {
			paramHashMap = new HashMap<String, String>();
		} else {
			paramHashMap.clear();
		}
		paramHashMap.putAll(temp);
	}
	
	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mLoginEntity = new LoginEntity();
//			mLoginEntity.parseJson(result);
			return mLoginEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mLoginEntity != null){
//			if (mLoginEntity.getResult() != null)
				return true;
		}
		return false;
		
//		return mLoginEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
//		return mLoginEntity != null && mLoginEntity.getMessage() != null ?
//				mLoginEntity.getMessage().getStatusCode()
//				: BaseNetRequestHandler.CODE_FAIL_STATUS;
		return 1;
	}
	
	@Override
	protected int getStatus() {
//		return mLoginEntity != null ?
//				mLoginEntity.getStatus()
//				: BaseNetRequestHandler.STATUSERROR;
		return 1;
	}
	
	@Override
	protected String getErrorMsg() {
//		return mLoginEntity != null
//				&& mLoginEntity.getMessage() != null ?
//				 mLoginEntity.getMessage().getStatusMessage()
//				 : "";
		return "";
	}
	
	

	

}
