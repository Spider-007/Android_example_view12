package com.htmitech.emportal.ui.plugin.model.task;

import android.content.Context;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;

public class ZtChartModel extends BaseModel {
	private final String TAG = ZtChartModel.class.getName();
	private ProjectNameTask mProjectNameTask = null;
	private BuildTask mBuildTask = null;
	private GetDataParameterTask mGetDataParameterTask = null;
	private GetChartListTask mGetChartListTask = null;
	private GetPieDataParameterTask mGetPieDataParameterTask = null;
	String addUrl = "";
	private Context mContext = null;

	//这个TYPE用于在回调中做返回区分
	public static final int TYPE_GETPROJECTNAME = 1;   //获得自动匹配数据
	public static final int TYPE_GETBUILDNAME = 2;
	
	public static final int TYPE_GET_CHARTPARAMETER = 3; //请求第一次数据的结构
	public static final int TYPE_GET_CHARTLIST = 4; //填充请求的数据结构去 请求报表列表
	public static final int TYPE_GET_PIECHART_PARAMETER = 5; //请求饼图的数据结构

	public ZtChartModel(IBaseCallback callback) {
		super(callback);
	}
	
	public ZtChartModel(IBaseCallback callback, Context mContext) {
		super(callback);
		this.mContext = mContext;
	}

	

	//为给传参数
	public void getDataFromServerByType(int type, Object paramObject, String addUrl) {
		this.addUrl = addUrl;
		super.getDataFromServerByType(type, paramObject);
	}
	
	@Override
	public void getDataFromServerByType(int type, Object paramObject) {
		super.getDataFromServerByType(type, paramObject);
	}

	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		// TODO Auto-generated method stub

		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_GETPROJECTNAME:
			mProjectNameTask = new ProjectNameTask(taskType, mContext);
			mProjectNameTask.buildRequestParam(paramObject);
			task = mProjectNameTask;
			break;
		case TYPE_GETBUILDNAME:
			mBuildTask = new BuildTask(taskType, mContext);
			mBuildTask.buildRequestParam(paramObject);
			task = mBuildTask;
			break;
		case TYPE_GET_CHARTPARAMETER:							
			mGetDataParameterTask = new GetDataParameterTask(taskType);
			mGetDataParameterTask.buildRequestParam(paramObject);
			mGetDataParameterTask.addUrl = addUrl;
			task = mGetDataParameterTask;
			break;
		case TYPE_GET_CHARTLIST:
			mGetChartListTask = new GetChartListTask(taskType);
			mGetChartListTask.buildRequestParam(paramObject);
			task = mGetChartListTask;
			break;
		case TYPE_GET_PIECHART_PARAMETER:
			mGetPieDataParameterTask = new GetPieDataParameterTask(taskType);
			mGetPieDataParameterTask.buildRequestParam(paramObject);
			mGetPieDataParameterTask.addUrl = addUrl;
			task = mGetPieDataParameterTask;
			break;
		}
		return task;
	}

	@Override
	public void notifySuccess(int statuscode, Object result) {
		// TODO Auto-generated method stub
		super.notifySuccess(statuscode, result);
	}

	@Override
	public void notifyFail(int requestTypeId, int statuscode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub
		super.notifyFail(requestTypeId, statuscode, errorMsg, result);
	}

	@Override
	public boolean hasResult() {
		// TODO Auto-generated method stub
		return super.hasResult();
	}
	
}
