package com.htmitech.emportal.ui.appcenter.model.task;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;

public class AppCenterModel extends BaseModel {
	private final String TAG = AppCenterModel.class.getName();
	private AppCenterTask mGetTask;
	private AppCenterSaveOcusTask mSaveTask;

	/** 获取当前用户定义的快捷键列表 */
	public static final int TYPE_GET_CURRENTOCU_LIST = 10;
	/*** 保存当前用户已经选择的快捷链接列表  ***/
	public static final int TYPE_SAVE_CURRENTOCU_LIST = 11;
   
	public AppCenterModel(IBaseCallback callback) {
		super(callback);
	}

	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		// TODO Auto-generated method stub
		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_GET_CURRENTOCU_LIST:
			mGetTask = new AppCenterTask(taskType);
			mGetTask.buildRequestParamforGet(paramObject);
			task = mGetTask;
			break;
		case TYPE_SAVE_CURRENTOCU_LIST:
			mSaveTask = new AppCenterSaveOcusTask(taskType);
			mSaveTask.buildRequestParam(paramObject);
			task = mSaveTask;
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
