package com.htmitech.emportal.ui.plugin.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.plugin.zt.entity.GetPieChartParameterResultInfo;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class GetPieDataParameterTask extends BaseTaskBody {

	//暂时写在这个文件里，等接口定死再调整
	private String GET_PIE_DATE_PARAMETER_URL =  PreferenceUtils.getOaLoginUrl() + "/" + PreferenceUtils.getApiUrl() + "/api/MobileReport/GetDataParametersByReportID?reportGuid=";
	public String addUrl = "";
	
	private final String TAG = GetPieDataParameterTask.class.getName();
	public static int TASK_TYPE = 3;
	private GetPieChartParameterResultInfo mPieParameterEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	
	//taskType 在 会掉函数里有返回值
	public GetPieDataParameterTask(int taskType) {
		super.taskType = taskType;
	}
	
	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {
		HttpRequestEntity requestEntity = new HttpRequestEntity(
				GET_PIE_DATE_PARAMETER_URL, addUrl);		
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
			mPieParameterEntity = new GetPieChartParameterResultInfo();
			mPieParameterEntity.parseJson(result);
			return mPieParameterEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mPieParameterEntity != null){
			if (mPieParameterEntity.getResult() != null)
				return true;
		}
		return false;
	}

	@Override
	protected String getToken() {
		return "";
	}

	@Override
	protected int getStatusCode() {
		return mPieParameterEntity != null && mPieParameterEntity.getMessage() != null ?
				mPieParameterEntity.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return mPieParameterEntity != null ?
				mPieParameterEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return mPieParameterEntity != null
				&& mPieParameterEntity.getMessage() != null ? 
						mPieParameterEntity.getMessage().getStatusMessage()
				 : "";
	}

}
