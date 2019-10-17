package com.htmitech.emportal.ui.detail.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.H5DocResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class H5StartDetailTask extends BaseTaskBody {
	private final String TAG = H5StartDetailTask.class.getName();
	private H5DocResultInfo mDocResultInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public H5StartDetailTask(int taskType) {
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
				ServerUrlConstant.OA_STARTFLOW_METHOD_H5);
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
			mDocResultInfo = new H5DocResultInfo();
			mDocResultInfo.parseJson(result);
			return mDocResultInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mDocResultInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mDocResultInfo != null ? mDocResultInfo.getMessage()
				.getStatusCode() : BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mDocResultInfo != null ? mDocResultInfo.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mDocResultInfo != null
				&& mDocResultInfo.getMessage().getStatusMessage() != null ? mDocResultInfo
				.getMessage().getStatusMessage() : "";
	}

}