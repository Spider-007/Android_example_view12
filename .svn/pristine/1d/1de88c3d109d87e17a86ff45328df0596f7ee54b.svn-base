package com.htmitech.emportal.ui.commonoptions.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.commonoptions.data.EditUserOptionsEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class EditUserOptionsTask extends BaseTaskBody {

	private final String TAG = EditUserOptionsTask.class.getName();
	public static int TASK_TYPE = 8;
	private EditUserOptionsEntity mEditUserOptionsEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public EditUserOptionsTask(int taskType) {
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
				ServerUrlConstant.USERINFO_EditUSEROPTIONS_METHOD);
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
			mEditUserOptionsEntity = new EditUserOptionsEntity();
			mEditUserOptionsEntity.parseJson(result);
			return mEditUserOptionsEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mEditUserOptionsEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mEditUserOptionsEntity != null ? mEditUserOptionsEntity.getMessage()
				.getStatusCode() : BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	@Override
	protected int getStatus() {
		return mEditUserOptionsEntity != null ? mEditUserOptionsEntity
				.getStatus() : BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mEditUserOptionsEntity != null
				&& mEditUserOptionsEntity.getMessage() != null ? mEditUserOptionsEntity
				.getMessage().getStatusMessage() : "";
	}



}
