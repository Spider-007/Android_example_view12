package com.htmitech.htcommonformplugin.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.daiban.model.task.GetDocListTask;
import com.htmitech.htcommonformplugin.entity.GetCommonFormListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class GetCommonFormListTask extends BaseTaskBody {

	private final String TAG = GetDocListTask.class.getName();
	public static int TASK_TYPE = 3;
	private GetCommonFormListEntity mEntitys;
	private Object paramObject = null;
	public GetCommonFormListTask() {

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
				ServerUrlConstant.GET_COMMONFORM_LIST);
		if (paramObject != null) {
			requestEntity.setRequestObject(paramObject);
		}
		return requestEntity;
	}

	
	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mEntitys = new GetCommonFormListEntity();
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
		return mEntitys != null && mEntitys.getMessage() != null ?
				mEntitys.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected String getErrorMsg() {
		return mEntitys != null
				&& mEntitys.getMessage() != null ? 
				 mEntitys.getMessage().getStatusMessage()
				 : "";
	}
	
	@Override
	protected int getStatus() {
		return mEntitys != null ? mEntitys.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}

	

}
