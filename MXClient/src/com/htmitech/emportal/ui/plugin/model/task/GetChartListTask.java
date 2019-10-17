package com.htmitech.emportal.ui.plugin.model.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.plugin.zt.entity.TableChartResultInfo1;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class GetChartListTask extends BaseTaskBody {

	//暂时写在这个文件里，等接口定死再调整
	

		
	private String GET_CHART_LIST_URL = PreferenceUtils.getOaLoginUrl() + "/" + PreferenceUtils.getApiUrl() + "/api/MobileReport/GetListDefiniensHasDataByIDAndParameters";
	
	public String addUrl = "";
	
	private final String TAG = GetChartListTask.class.getName();
	public static int TASK_TYPE = 3;
	private TableChartResultInfo1 mChartList;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;
	
	//taskType 在 会掉函数里有返回值
	public GetChartListTask(int taskType) {
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
				GET_CHART_LIST_URL, addUrl);		
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
			mChartList = new TableChartResultInfo1();
			mChartList.parseJson(result);
			return mChartList;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (mChartList != null){
			if (mChartList.getResult() != null)
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
		return mChartList != null && mChartList.getMessage() != null ?
				mChartList.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return mChartList != null ?
				mChartList.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return mChartList != null
				&& mChartList.getMessage() != null ? 
						mChartList.getMessage().getStatusMessage()
				 : "";
	}

}
