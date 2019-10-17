package com.htmitech.emportal.ui.document.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.document.data.DocumentNodeListEntity;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class DocumentNodeListTask extends BaseTaskBody {

	private final String TAG = DocumentNodeListTask.class.getName();
	private DocumentNodeListEntity mEntitys;
	private Object paramObject = null;

	public DocumentNodeListTask() {

	}

	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected int getRequestType() {
		if (this.taskType == DocumentModel.TYPE_GET_DocumentTopLevelNodeList) {
			return GET_HTTP_REQUEST_TYPE;
		} else
			return POST_HTTP_REQUEST_TYPE;
		
		
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {

		if (this.taskType == DocumentModel.TYPE_GET_DocumentTopLevelNodeList) {
		/*	HttpRequestEntity requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.OCUINFO_GETOCULIST_METHOD + "?userid=" + paramObject.toString());*/
			HttpRequestEntity requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.DOCUMENT_GETTOPLEVELNODE_METHOD);
			requestEntity.addQueryStringParameter("userid", paramObject.toString());
			return requestEntity;
		} else {
			HttpRequestEntity requestEntity = new HttpRequestEntity(
					ServerUrlConstant.SERVER_BASE_URL(),
					ServerUrlConstant.DOCUMENT_GETSUBNODE_METHOD);
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
			mEntitys = new DocumentNodeListEntity();
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
