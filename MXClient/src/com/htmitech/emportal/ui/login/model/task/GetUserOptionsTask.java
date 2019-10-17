package com.htmitech.emportal.ui.login.model.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.login.data.logindata.UserOptionListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class GetUserOptionsTask extends BaseTaskBody {

	private final String TAG = GetUserOptionsTask.class.getName();
	public static int TASK_TYPE = 3;
	private UserOptionListEntity mEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	public GetUserOptionsTask() {

	}

	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected int getRequestType() {
		return GET_HTTP_REQUEST_TYPE;
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {
		HttpRequestEntity requestEntity = null;
		if(paramObject == null){
			requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.USERINFO_GETUSEROPTIONS_METHOD + "?user_id=" );
		}else{
			requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.USERINFO_GETUSEROPTIONS_METHOD + "?user_id=" + paramObject.toString());
		}
		return requestEntity;
	}

	public void buildRequestParamforGet(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mEntity = new UserOptionListEntity();
			mEntity.parseJson(result);
			return mEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mEntity != null){
			if (mEntity.getResult() != null)
				return true;
		}
		return false;
		
//		return mEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mEntity != null && mEntity.getMessage() != null ?
				mEntity.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return mEntity != null ?
				mEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return mEntity != null
				&& mEntity.getMessage() != null ? 
				 mEntity.getMessage().getStatusMessage()
				 : "";
	}
	
	

	

}
