package com.htmitech.emportal.ui.document.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.document.data.DocumentListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DocumentListTask extends BaseTaskBody {

	private final String TAG = DocumentListTask.class.getName();
	private DocumentListEntity mEntitys;
	private Object paramObject = null;

	public DocumentListTask() {

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
				ServerUrlConstant.DOCUMENT_GETDOCUMENTLIST_METHOD);
		requestEntity.setRequestObject(paramObject);
		return requestEntity;

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
			mEntitys = new DocumentListEntity();
//			mEntitys.parseJson(result);
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
//		return mEntitys != null && mEntitys.getMessage() != null ? mEntitys
//				.getMessage().getStatusCode()
//				: BaseNetRequestHandler.CODE_FAIL_STATUS;
		return 1;
	}

	@Override
	protected String getErrorMsg() {
//		return mEntitys != null && mEntitys.getMessage() != null ? mEntitys
//				.getMessage().getStatusMessage() : "";
		return "";
	}

	@Override
	protected int getStatus() {
//		return mEntitys != null ? mEntitys.getStatus()
//				: BaseNetRequestHandler.STATUSERROR;
		return 1;
	}

}
