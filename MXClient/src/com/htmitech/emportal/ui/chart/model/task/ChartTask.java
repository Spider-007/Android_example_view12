package com.htmitech.emportal.ui.chart.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.entity.ReportResultInfo;
import com.htmitech.emportal.net.HttpRequestEntity;


import java.util.HashMap;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class ChartTask extends BaseTaskBody {

	//暂时写在这个文件里，等接口定死再调整
	private static String CHART_PATH = PreferenceUtils.getOaLoginUrl() + "/" + PreferenceUtils.getApiUrl() + "/api/MobileReport/GetDataParametersByReportID?reportGuid=";
	public String addUrl = "";
	
	private final String TAG = ChartTask.class.getName();
	public static int TASK_TYPE = 3;
	private ReportResultInfo mChartEntity;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	
	//taskType 在 会掉函数里有返回值
	public ChartTask(int taskType) {
		super.taskType = taskType;
	}
	
	@Override
	protected String getPreferenceKey() {
		return "";
	}

	/*@Override
	protected int getRequestType() {
		return POST_HTTP_REQUEST_TYPE;
	}*/

	@Override
	protected HttpRequestEntity buildRequestEntity() {
		HttpRequestEntity requestEntity = new HttpRequestEntity(
				CHART_PATH, addUrl);		
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
			mChartEntity = new ReportResultInfo();
			mChartEntity.parseJson(result);
			return mChartEntity;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mChartEntity != null){
			if (mChartEntity.getResult() != null)
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
		return mChartEntity != null && mChartEntity.getMessage() != null ?
				mChartEntity.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return mChartEntity != null ?
				mChartEntity.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return mChartEntity != null
				&& mChartEntity.getMessage() != null ? 
						mChartEntity.getMessage().getStatusMessage()
				 : "";
	}

}
