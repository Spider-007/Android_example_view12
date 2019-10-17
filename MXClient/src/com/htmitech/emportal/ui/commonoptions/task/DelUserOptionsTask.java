package com.htmitech.emportal.ui.commonoptions.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.commonoptions.data.DelUserOptionsEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DelUserOptionsTask extends BaseTaskBody{
	private final String TAG = DelUserOptionsTask.class.getName();
	public static int TASK_TYPE = 5;
	private DelUserOptionsEntity mDelUserOptionsEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public DelUserOptionsTask(int taskType) {
		super.taskType = taskType;
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
				ServerUrlConstant.SERVER_BASE_URL(),
				ServerUrlConstant.USERINFO_DELUSEROPTIONS_METHOD);
		if (paramObject != null) {
			requestEntity.setRequestObject(paramObject);
		} 

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

	
	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mDelUserOptionsEntity = new DelUserOptionsEntity();
			mDelUserOptionsEntity.parseJson(result);
			return mDelUserOptionsEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mDelUserOptionsEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mDelUserOptionsEntity != null ? mDelUserOptionsEntity.getMessage()
				.getStatusCode() : BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	@Override
	protected int getStatus() {
		return mDelUserOptionsEntity != null ? mDelUserOptionsEntity
				.getStatus() : BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mDelUserOptionsEntity != null
				&& mDelUserOptionsEntity.getMessage() != null ? mDelUserOptionsEntity
				.getMessage().getStatusMessage() : "";
	}


}
