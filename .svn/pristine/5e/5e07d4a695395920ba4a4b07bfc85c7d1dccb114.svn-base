package com.htmitech.emportal.ui.plugin.model.task;

import android.content.Context;

import com.htmitech.commonx.base.HttpUtils;
import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.plugin.zt.entity.ProjectNameResultInfo;


import java.util.HashMap;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class ProjectNameTask extends BaseTaskBody {

	//暂时写在这个文件里，等接口定死再调整
	private String PROJECT_PATH = PreferenceUtils.getOaLoginUrl() + "/" + PreferenceUtils.getApiUrl() + "/api/reportmanager/ZgtwProjectNameQry?username=";
	
	private final String TAG = ProjectNameTask.class.getName();
	public static int TASK_TYPE = 3;
	private ProjectNameResultInfo projectTypeResult;
	private HashMap<String, String> paramHashMap = new HashMap<String, String>();
	private Object paramObject = null;

	
	//taskType 在 会掉函数里有返回值
	public ProjectNameTask(int taskType) {
		super.taskType = taskType;
	}
	
	//taskType 在 会掉函数里有返回值
	public ProjectNameTask(int taskType, Context mContext) {
		super.taskType = taskType;
		PROJECT_PATH =  PROJECT_PATH + PreferenceUtils.getLoginName(mContext) + "&projectName=";;
		//设置超时时间 
		int iTimeout = 1000 * 5; // 15s
		super.httpUtils = new HttpUtils(iTimeout);
	}
	
	
	@Override
	protected String getPreferenceKey() {
		return "";
	}

	@Override
	protected int getRequestType() {
		return GET_HTTP_REQUEST_TYPE;
	}

	@Override
	protected HttpRequestEntity buildRequestEntity() {
		HttpRequestEntity requestEntity = new HttpRequestEntity(
				PROJECT_PATH, "");
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
			projectTypeResult = new ProjectNameResultInfo();
			projectTypeResult.parseJson(result);
			return projectTypeResult;
		} catch (Exception e) {
			LogUtil.e(TAG, e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected synchronized boolean hasDataEntity() {
		if (projectTypeResult != null){
			if (projectTypeResult.getResult() != null)
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
		return projectTypeResult != null && projectTypeResult.getMessage() != null ?
				projectTypeResult.getMessage().getStatusCode()
				: BaseNetRequestHandler.CODE_FAIL_STATUS;
	}
	
	@Override
	protected int getStatus() {
		return projectTypeResult != null ?
				projectTypeResult.getStatus()
				: BaseNetRequestHandler.STATUSERROR;
	}
	
	@Override
	protected String getErrorMsg() {
		return projectTypeResult != null
				&& projectTypeResult.getMessage() != null ? 
						projectTypeResult.getMessage().getStatusMessage()
				 : "";
	}

}
