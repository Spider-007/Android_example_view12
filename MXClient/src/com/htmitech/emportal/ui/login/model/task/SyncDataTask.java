package com.htmitech.emportal.ui.login.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.SyncDataResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class SyncDataTask extends BaseTaskBody {

	private final String TAG = SyncDataTask.class.getName();
	public static int TASK_TYPE = 3;
	private SyncDataResultInfo mSyncDataResultInfo;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	public SyncDataTask() {

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
//		http://htrf.dscloud.me:8030/testv210/api/GetMobileData/GetSyncData
//		HttpRequestEntity requestEntity = new HttpRequestEntity(
//				ServerUrlConstant.SERVER_BASE_URL(),
//				ServerUrlConstant.GETSYNCDATA_METHOD);
		HttpRequestEntity requestEntity = new HttpRequestEntity(
				ServerUrlConstant.SERVER_BASE_URL(),ServerUrlConstant.GETSYNCDATA_METHOD
			);   //	"http://htrf.dscloud.me:8030/CloudAPI_JZ13710/api/","GetMobileData/GetSyncData"
		if (paramObject != null) {
			requestEntity.setRequestObject(paramObject);
		}
		else if (paramHashMap != null) {
			requestEntity.setJSONParameter(paramHashMap);
		}
		
		paramHashMap.clear();
		return requestEntity;
	}

	public void buildRequestParam(HashMap<String, String> temp) {
		if (paramHashMap == null) {
			paramHashMap = new HashMap<String, String>();
		} else {
			paramHashMap.clear();
		}
		paramHashMap.putAll(temp);
	}
	
	public void buildRequestParam(Object paramObjectinput) {
		paramObject = paramObjectinput;
	}

	@Override
	protected synchronized Object parseJson(String result) {
		try {
			mSyncDataResultInfo = new SyncDataResultInfo();
			mSyncDataResultInfo.parseJson(result);
			return mSyncDataResultInfo;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mSyncDataResultInfo != null){
			if (mSyncDataResultInfo.getResult() != null)
				return true;
		}
		return false;
		
//		return mSyncDataResultInfo != null ? true : false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mSyncDataResultInfo != null && mSyncDataResultInfo.getMessage() != null ?
				mSyncDataResultInfo.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return mSyncDataResultInfo != null ?
				mSyncDataResultInfo.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return mSyncDataResultInfo != null
				&& mSyncDataResultInfo.getMessage() != null ? 
				 mSyncDataResultInfo.getMessage().getStatusMessage()
				 : "";
	}
	
	

	

}
