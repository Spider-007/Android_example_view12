package com.htmitech.emportal.ui.document.model.task;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.proxy.myenum.LogManagerEnum;

public class DocumentModel extends BaseModel {
	private final String TAG = DocumentModel.class.getName();
	private DocumentListTask mGetDocumentListTask;
	private DocumentNodeListTask mGetDocNodeListTask;

	/** 获取文件列表 */
	public static final int TYPE_GET_DocumentList = 80;
	
	/** listView下拉获取更多文件列表 */
	public static final int TYPE_GET_MOREDocumentList = 81;
	
	/*** 获取文件目录（分类、节点）列表  顶层***/
	public static final int TYPE_GET_DocumentTopLevelNodeList = 82;
	
	/*** 获取文件目录（分类、节点）列表  非顶层***/
	public static final int TYPE_GET_DocumentSubNodeList = 83;
	
	/***listview列表下拉获取更多***/
	public static final int TYPE_GET_MORESubNode_LISTDATA = 84;
   
	public DocumentModel(IBaseCallback callback) {
		super(callback);
	}

	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		// TODO Auto-generated method stub
		BaseTaskBody task = null;
		switch (taskType) {
		case TYPE_GET_DocumentList:
		case TYPE_GET_MORESubNode_LISTDATA:
			case TYPE_GET_MOREDocumentList :
			mGetDocumentListTask = new DocumentListTask();
			mGetDocumentListTask.taskType = taskType;
				mGetDocumentListTask.setFunctionCode(LogManagerEnum.DOCUMENT_LIST.getFunctionCode());
			mGetDocumentListTask.buildRequestParamforGet(paramObject);
			task = mGetDocumentListTask;
			break;
		case TYPE_GET_DocumentTopLevelNodeList:
			mGetDocNodeListTask = new DocumentNodeListTask();
			mGetDocNodeListTask.setFunctionCode(LogManagerEnum.DOCUMENT_MAIN.getFunctionCode());
			mGetDocNodeListTask.taskType = taskType;
			mGetDocNodeListTask.buildRequestParam(paramObject);
			task = mGetDocNodeListTask;
			break;
		case TYPE_GET_DocumentSubNodeList:
			mGetDocNodeListTask = new DocumentNodeListTask();
			mGetDocNodeListTask.taskType = taskType;
			mGetDocNodeListTask.setFunctionCode(LogManagerEnum.DOCUMENT_SUB.getFunctionCode());
			mGetDocNodeListTask.buildRequestParam(paramObject);
			task = mGetDocNodeListTask;
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
