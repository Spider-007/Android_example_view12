package com.htmitech.emportal.ui.detail.model.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.FlowProcessInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class FlowTask extends BaseTaskBody {
	private final String TAG = FlowTask.class.getName();
	private FlowProcessInfo mFlowProcessInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public FlowTask(int taskType) {
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
				ServerUrlConstant.OA_GETDOC_FLOW_METHOD);
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
			mFlowProcessInfo = new FlowProcessInfo();
			mFlowProcessInfo.parseJson(result);
			return mFlowProcessInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mFlowProcessInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mFlowProcessInfo != null
				&& mFlowProcessInfo.getMessage() != null ? mFlowProcessInfo
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mFlowProcessInfo != null ? mFlowProcessInfo.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mFlowProcessInfo != null
				&& mFlowProcessInfo.getMessage().getStatusMessage() != null ? mFlowProcessInfo
				.getMessage().getStatusMessage() : "";
	}

}
