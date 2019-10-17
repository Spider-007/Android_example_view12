package com.htmitech.emportal.ui.chart.model.task;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;

import java.util.HashMap;

public class ChartListModel extends BaseModel {
	private final String TAG = ChartListModel.class.getName();
	private ChartListTask mChartListTask = null;
	String addUrl = "";

	//这个TYPE用于在回调中做返回区分
	public static final int TYPE_GETCHARTlist = 10;   //获得报表列表
	public static final int TYPE_GETCHARTParameters = 11; //获得报表参数
	public static final int TYPE_GETEVENTParamters = 12; //获取事件请求参数
	//获得具体报表数据
	public static final int TYPE_CHART_BAR = 0;  //获得柱装图详情
	public static final int TYPE_CHART_LINE = 1; //获得线装图详情
	public static final int TYPE_CHART_PIE = 2;  //获得饼状图详情
	public static final int TYPE_CHART_LIST = 3; //获得列表报表详情

	public ChartListModel(IBaseCallback callback) {
		super(callback);
	}

	//为给传参数
	public void getDataFromServerByType(int type, Object paramObject, String addUrl) {
		this.addUrl = addUrl;
		getDataFromServerByType(type, paramObject);
	}

	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		// TODO Auto-generated method stub

		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_GETCHARTlist:
			mChartListTask = new ChartListTask(taskType);
			mChartListTask.buildRequestParam(paramObject);
			task = mChartListTask;
			break;
		case TYPE_GETCHARTParameters:
			ChartTask mChartParametersTask = new ChartTask(taskType);
			mChartParametersTask.addUrl = addUrl;
			mChartParametersTask.buildRequestParam(paramObject);
			task = mChartParametersTask;
			break;
		case TYPE_CHART_LIST:
			TableChartTask mTableChartTask = new TableChartTask(taskType);
			mTableChartTask.buildRequestParam(paramObject);
			task = mTableChartTask;
			break;
		case TYPE_CHART_BAR:
		case TYPE_CHART_LINE:
			BarChartTask mOneChartTask = new BarChartTask(taskType);
			mOneChartTask.buildRequestParam(paramObject);
			task = mOneChartTask;
			break;
		case TYPE_CHART_PIE:
			PieChartTask mPieChartTask = new PieChartTask(taskType);
			mPieChartTask.buildRequestParam(paramObject);
			task = mPieChartTask;
			break;
		case TYPE_GETEVENTParamters:
			EventDataTask mEventDaskTask = new EventDataTask(taskType);
			mEventDaskTask.buildRequestParam(paramObject);
			task = mEventDaskTask;
			break;
		}
		return task;
	}
	
	protected BaseTaskBody createTask(int taskType,
			HashMap<String, String> paramHashMap) {
		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_GETCHARTlist:
			mChartListTask = new ChartListTask(taskType);
			mChartListTask.taskType = TYPE_GETCHARTlist;
			mChartListTask.buildRequestParam(paramHashMap);
			task = mChartListTask;
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
	
}
