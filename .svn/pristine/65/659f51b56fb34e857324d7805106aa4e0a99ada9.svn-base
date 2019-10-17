package com.htmitech.emportal.ui.appcenter.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.appcenter.data.OcuListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class AppCenterSaveOcusTask extends BaseTaskBody {

	private final String TAG = AppCenterSaveOcusTask.class.getName();
	private OcuListEntity mEntitys;
	private Object paramObject = null;

	public AppCenterSaveOcusTask(int taskType) {
		super.taskType = taskType;
	}

	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected int getRequestType() {

		if (this.taskType == AppCenterModel.TYPE_GET_CURRENTOCU_LIST) {
			return GET_HTTP_REQUEST_TYPE;
		} else
			return POST_HTTP_REQUEST_TYPE;
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {

		if (this.taskType == AppCenterModel.TYPE_GET_CURRENTOCU_LIST) {
			HttpRequestEntity requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.OCUINFO_GETOCULIST_METHOD + "?userid=" + paramObject.toString());
			return requestEntity;
		} else {
			HttpRequestEntity requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.OCUINFO_SAVEOCULIST_METHOD);
			if (paramObject != null) {
				requestEntity.setRequestObject(paramObject);
			}
			return requestEntity;
		}

	}

	public void buildRequestParamforGet(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mEntitys = new OcuListEntity();
			mEntitys.parseJson(result);
			return mEntitys;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		return mEntitys != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mEntitys != null && mEntitys.getMessage() != null ? mEntitys
				.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}

	@Override
	protected String getErrorMsg() {
		return mEntitys != null && mEntitys.getMessage() != null ? mEntitys
				.getMessage().getStatusMessage() : "";
	}

	@Override
	protected int getStatus() {
		return mEntitys != null ? mEntitys.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

}
