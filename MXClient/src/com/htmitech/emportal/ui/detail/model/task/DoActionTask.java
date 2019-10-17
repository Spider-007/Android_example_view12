package com.htmitech.emportal.ui.detail.model.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.DoActionResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DoActionTask extends BaseTaskBody {
	private final String TAG = DoActionTask.class.getName();
	private DoActionResultInfo mDoActionResultInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public DoActionTask(int taskType) {
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
				ServerUrlConstant.SERVER_JAVA_URL(),
				ServerUrlConstant.OA_DOAction_METHOD_JAVA);
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
			mDoActionResultInfo = new DoActionResultInfo();
			mDoActionResultInfo.parseJson(result);
			return mDoActionResultInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mDoActionResultInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mDoActionResultInfo != null
				&& mDoActionResultInfo.getMessage() != null ?mDoActionResultInfo.getCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mDoActionResultInfo != null ? 1
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mDoActionResultInfo != null
				&& mDoActionResultInfo.getMessage() != null ? mDoActionResultInfo
				.getMessage() : "";
	}

}
