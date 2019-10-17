package com.htmitech.emportal.request;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.SyncDataResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class PeopleMessageRequestTask extends BaseTaskBody {

	private final String TAG = PeopleMessageRequestTask.class.getName();
	public static int TASK_TYPE = 3;
	private SyncDataResultInfo mEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	public PeopleMessageRequestTask() {

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
		String nurl = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.UPDATA_USER_MESSAGE_JAVA;
		HttpRequestEntity requestEntity = new HttpRequestEntity(nurl, "");
	
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
			mEntity = new SyncDataResultInfo();
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
