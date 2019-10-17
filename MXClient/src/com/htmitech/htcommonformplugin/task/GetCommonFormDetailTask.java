package com.htmitech.htcommonformplugin.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.htcommonformplugin.entity.GetDetailEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class GetCommonFormDetailTask extends BaseTaskBody {
	private final String TAG = GetCommonFormDetailTask.class.getName();
	private GetDetailEntity mGetDetailEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public GetCommonFormDetailTask(int taskType) {
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
				ServerUrlConstant.GET_COMMONFORM_DETAL);
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
			mGetDetailEntity = new GetDetailEntity();
			mGetDetailEntity.parseJson(result);
			return mGetDetailEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mGetDetailEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mGetDetailEntity != null ? mGetDetailEntity.getMessage()
				.getStatusCode() : BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mGetDetailEntity != null ? mGetDetailEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mGetDetailEntity != null
				&& mGetDetailEntity.getMessage().getStatusMessage() != null ? mGetDetailEntity
				.getMessage().getStatusMessage() : "";
	}

}
