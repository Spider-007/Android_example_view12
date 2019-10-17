package com.htmitech.emportal.ui.daiban.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class GetDocListTask extends BaseTaskBody {

	private final String TAG = GetDocListTask.class.getName();
	public static int TASK_TYPE = 3;
	private GetDocListEntity mEntitys;
	private Object paramObject = null;
	public GetDocListTask() {

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
				ServerUrlConstant.OA_GETDOCLIST_METHOD);
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
			mEntitys = new GetDocListEntity();
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
