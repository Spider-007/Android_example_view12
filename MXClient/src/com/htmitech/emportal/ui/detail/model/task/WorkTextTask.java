package com.htmitech.emportal.ui.detail.model.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.WordTextInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class WorkTextTask extends BaseTaskBody {
	private final String TAG = WorkTextTask.class.getName();
	private WordTextInfo mWordTextInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public WorkTextTask(int taskType) {
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
				ServerUrlConstant.OA_GETDOC_TEXT_METHOD);
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
			mWordTextInfo = new WordTextInfo();
			mWordTextInfo.parseJson(result);
			return mWordTextInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mWordTextInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mWordTextInfo != null
				&& mWordTextInfo.getMessage() != null ? mWordTextInfo
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mWordTextInfo != null ? mWordTextInfo.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mWordTextInfo != null
				&& mWordTextInfo.getMessage().getStatusMessage() != null ? mWordTextInfo
				.getMessage().getStatusMessage() : "";
	}

}
