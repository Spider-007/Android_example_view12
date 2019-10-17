package com.htmitech.emportal.ui.commonoptions.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.commonoptions.data.AddUserOptionsEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class AddUserOptionsTask extends BaseTaskBody {

	private final String TAG = AddUserOptionsTask.class.getName();
	public static int TASK_TYPE = 6;
	private AddUserOptionsEntity mAddUserOptionsEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();

	public AddUserOptionsTask(int taskType) {
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
				ServerUrlConstant.USERINFO_ADDUSEROPTIONS_METHOD);
	
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
			mAddUserOptionsEntity = new AddUserOptionsEntity();
			mAddUserOptionsEntity.parseJson(result);
			return mAddUserOptionsEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mAddUserOptionsEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mAddUserOptionsEntity != null ? mAddUserOptionsEntity.getMessage()
				.getStatusCode() : BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	@Override
	protected int getStatus() {
		return mAddUserOptionsEntity != null ? mAddUserOptionsEntity
				.getStatus() : BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mAddUserOptionsEntity != null
				&& mAddUserOptionsEntity.getMessage() != null ? mAddUserOptionsEntity
				.getMessage().getStatusMessage() : "";
	}


}
