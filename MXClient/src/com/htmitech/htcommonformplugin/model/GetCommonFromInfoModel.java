package com.htmitech.htcommonformplugin.model;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.ui.detail.model.task.DoActionTask;
import com.htmitech.emportal.ui.detail.model.task.DownFileIsFinish_AttachmentTask;
import com.htmitech.emportal.ui.detail.model.task.DownFileIsFinish_DocFileTask;
import com.htmitech.emportal.ui.detail.model.task.FlowTask;
import com.htmitech.emportal.ui.detail.model.task.GetDetailTask;
import com.htmitech.emportal.ui.detail.model.task.H5StartDetailTask;
import com.htmitech.emportal.ui.detail.model.task.StartDetailTask;
import com.htmitech.emportal.ui.detail.model.task.WorkTextTask;
import com.htmitech.htcommonformplugin.task.GetCommonFormDetailTask;
import com.htmitech.htcommonformplugin.task.GetGeneralFormDoActionTask;
import com.htmitech.htcommonformplugin.task.GetGeneralFormDownAttachmentFile_IsFinishTask;
import com.htmitech.htcommonformplugin.task.SaveExtFieldsTask;
import com.htmitech.proxy.myenum.LogManagerEnum;

public class GetCommonFromInfoModel extends BaseModel {
	public static final int TYPE_GET_DETAILTASK = 10001;
	public static final int TYPE_GET_FLOW = 1;
	public static final int TYPE_GET_WORD_TEXTTASK = 2;
	public static final int TYPE_GET_DOWNLOAD_DOCISFINISH = 3;
	public static final int TYPE_GET_DOWNLOAD_ATTISFINISH = 4;
	public static final int TYPE_DOACTION_TASK = 5;
	public static final int TYPE_START_DETAILTASK = 6; //发起流程
	public static final int TYPE_START_DETAILTASK_H5 = 7;
	public static final int TYPE_SAVEEXTFIELDS = 8;


	private GetCommonFormDetailTask mGetCommonFormDetailTask;
	private FlowTask mFlowTask;
	private WorkTextTask mWordTextTask;
	private DownFileIsFinish_DocFileTask mDownDocFileTask;
	private GetGeneralFormDownAttachmentFile_IsFinishTask mGetGeneralFormDownAttachmentFile_IsFinishTask;
	private GetGeneralFormDoActionTask mGetGeneralFormDoActionTask;
	private StartDetailTask mStartDetailTask;
	private H5StartDetailTask mH5StartDetailTask;
	private SaveExtFieldsTask mSaveExtFieldsTask;


	public GetCommonFromInfoModel(IBaseCallback callback) {
		super(callback);
	}


	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		switch (taskType) {
			case TYPE_GET_DETAILTASK:
				mGetCommonFormDetailTask = new GetCommonFormDetailTask(taskType);
				mGetCommonFormDetailTask.setFunctionCode(LogManagerEnum.GET_COMMONFORM_DETAIL.functionCode);
				mGetCommonFormDetailTask.buildRequestParam(paramObject);//paramObject ->  DocInfoParameters
				return mGetCommonFormDetailTask;
			case TYPE_GET_FLOW:
				mFlowTask = new FlowTask(taskType);
				mFlowTask.setFunctionCode(LogManagerEnum.APP_DOC_FLOW.functionCode);
				mFlowTask.buildRequestParam(paramObject); //paramObject ->  DocInfoParameters
				return mFlowTask;
			case TYPE_GET_WORD_TEXTTASK:
				mWordTextTask = new WorkTextTask(taskType);
				mWordTextTask.buildRequestParam(paramObject); //paramObject ->  DocInfoParameters
				return mWordTextTask;
			case TYPE_GET_DOWNLOAD_DOCISFINISH:
				mDownDocFileTask = new DownFileIsFinish_DocFileTask(taskType);
				mDownDocFileTask.buildRequestParam(paramObject);//paramObject ->  DocInfoParameters
				return mDownDocFileTask;
			case TYPE_GET_DOWNLOAD_ATTISFINISH:
				mGetGeneralFormDownAttachmentFile_IsFinishTask = new GetGeneralFormDownAttachmentFile_IsFinishTask(taskType);
				mGetGeneralFormDownAttachmentFile_IsFinishTask.buildRequestParam(paramObject); //paramObject ->  AttachmentInfoParameters
				return mGetGeneralFormDownAttachmentFile_IsFinishTask;
			case TYPE_SAVEEXTFIELDS:
				mSaveExtFieldsTask = new SaveExtFieldsTask(taskType);
				mSaveExtFieldsTask.setFunctionCode(LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode);
				mSaveExtFieldsTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
				return mSaveExtFieldsTask;
			case TYPE_DOACTION_TASK:
				mGetGeneralFormDoActionTask = new GetGeneralFormDoActionTask(taskType);
				mGetGeneralFormDoActionTask.setFunctionCode(LogManagerEnum.GET_COMMONFORM_DO_ACTION.functionCode);
				mGetGeneralFormDoActionTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
				return mGetGeneralFormDoActionTask;

			case TYPE_START_DETAILTASK_H5:
				mH5StartDetailTask = new H5StartDetailTask(taskType);
				mH5StartDetailTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
				return mH5StartDetailTask;
			case TYPE_START_DETAILTASK:
				mStartDetailTask = new StartDetailTask(taskType);
				mStartDetailTask.buildRequestParam(paramObject); //paramObject -> DoActionParameter
				return mStartDetailTask;
		}
		return super.createTask(taskType, paramObject);
	}

}
