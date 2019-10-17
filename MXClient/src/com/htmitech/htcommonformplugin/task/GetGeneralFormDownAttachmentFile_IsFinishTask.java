package com.htmitech.htcommonformplugin.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.htcommonformplugin.entity.DownAttachmentFile_isFinishEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class GetGeneralFormDownAttachmentFile_IsFinishTask extends BaseTaskBody {
	private final String TAG = GetGeneralFormDownAttachmentFile_IsFinishTask.class.getName();
	private DownAttachmentFile_isFinishEntity mDownAttachmentFile_isFinishEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	public static int TASK_TYPE = 0;

	public GetGeneralFormDownAttachmentFile_IsFinishTask(int taskType) {
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
				ServerUrlConstant.GET_COMMONFORM_DownAttachmentFile_IsFinish);
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
			mDownAttachmentFile_isFinishEntity = new DownAttachmentFile_isFinishEntity();
			mDownAttachmentFile_isFinishEntity.parseJson(result);
			return mDownAttachmentFile_isFinishEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mDownAttachmentFile_isFinishEntity != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mDownAttachmentFile_isFinishEntity != null
				&& mDownAttachmentFile_isFinishEntity.getMessage() != null ? mDownAttachmentFile_isFinishEntity
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	protected int getStatus() {
		return mDownAttachmentFile_isFinishEntity != null ? mDownAttachmentFile_isFinishEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	@Override
	protected String getErrorMsg() {
		return mDownAttachmentFile_isFinishEntity != null
				&& mDownAttachmentFile_isFinishEntity.getMessage().getStatusMessage() != null ? mDownAttachmentFile_isFinishEntity
				.getMessage().getStatusMessage() : "";
	}

}
