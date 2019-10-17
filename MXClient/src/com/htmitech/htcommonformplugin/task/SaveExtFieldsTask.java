package com.htmitech.htcommonformplugin.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.htcommonformplugin.entity.GetSaveExtFieldsEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class SaveExtFieldsTask extends BaseTaskBody {
	private final String TAG = SaveExtFieldsTask.class.getName();
	private GetSaveExtFieldsEntity mGetSaveExtFieldsEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public SaveExtFieldsTask(int taskType) {
		super.taskType = taskType;
	}
	public void setFunctionCode(String functionCode){
		httpUtils.logfunctionCode = functionCode;
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
				ServerUrlConstant.SET_COMMONFORM_SaveExtFields);
		if (paramObject != null) {
			requestEntity.setRequestObject(paramObject);
		}
		return requestEntity;
	}

	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
		
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
			mGetSaveExtFieldsEntity = new GetSaveExtFieldsEntity();
			mGetSaveExtFieldsEntity.parseJson(result);
			return mGetSaveExtFieldsEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mGetSaveExtFieldsEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mGetSaveExtFieldsEntity != null
				&& mGetSaveExtFieldsEntity.getMessage() != null ? mGetSaveExtFieldsEntity
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mGetSaveExtFieldsEntity != null ? mGetSaveExtFieldsEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mGetSaveExtFieldsEntity != null
				&& mGetSaveExtFieldsEntity.getMessage().getStatusMessage() != null ? mGetSaveExtFieldsEntity
				.getMessage().getStatusMessage() : "";
	}

}
