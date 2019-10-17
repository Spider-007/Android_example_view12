package com.htmitech.emportal.ui.detail.model.task;

import java.util.HashMap;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.DownFilesIsFinishResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DownFileIsFinish_AttachmentTask extends BaseTaskBody {
	private final String TAG = DownFileIsFinish_AttachmentTask.class.getName();
	private DownFilesIsFinishResultInfo mDownFilesIsFinishResultInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public DownFileIsFinish_AttachmentTask(int taskType) {
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
				ServerUrlConstant.OA_DOWNFILE_ISFinish_ATTFile_METHOD);
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
			mDownFilesIsFinishResultInfo = new DownFilesIsFinishResultInfo();
			mDownFilesIsFinishResultInfo.parseJson(result);
			return mDownFilesIsFinishResultInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mDownFilesIsFinishResultInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mDownFilesIsFinishResultInfo != null
				&& mDownFilesIsFinishResultInfo.getMessage() != null ? mDownFilesIsFinishResultInfo
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mDownFilesIsFinishResultInfo != null ? mDownFilesIsFinishResultInfo.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mDownFilesIsFinishResultInfo != null
				&& mDownFilesIsFinishResultInfo.getMessage().getStatusMessage() != null ? mDownFilesIsFinishResultInfo
				.getMessage().getStatusMessage() : "";
	}

}
