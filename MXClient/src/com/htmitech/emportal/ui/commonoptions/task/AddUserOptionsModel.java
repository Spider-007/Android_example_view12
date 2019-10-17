package com.htmitech.emportal.ui.commonoptions.task;


import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;

public class AddUserOptionsModel extends BaseModel  {

	private AddUserOptionsTask mAddUserOptionsTask;
	private DelUserOptionsTask mDelUserOptionsTask;
	private EditUserOptionsTask mEditUserOptionsTask;
	/** 新增常用意见 */
	public static final int TYPE_GET_NEW_OPTIONS = 111;
	/***更新常用意见***/
	public static final int TYPE_GET_EDIT_OPTIONS = 112;
	/**删除常用意见***/
	public static final int TYPE_GET_DEL_OPTIONS = 113;

	public AddUserOptionsModel(IBaseCallback callback) {
		super(callback);
	}

	
	@Override
	protected BaseTaskBody createTask(int taskType, Object paramObject) {
		switch (taskType) {
		case TYPE_GET_NEW_OPTIONS:
			mAddUserOptionsTask = new AddUserOptionsTask(taskType);
			mAddUserOptionsTask.buildRequestParam(paramObject);//paramObject ->  OptionsParameterAdd
			return mAddUserOptionsTask;
		case TYPE_GET_EDIT_OPTIONS:
			mEditUserOptionsTask = new EditUserOptionsTask(taskType);
			mEditUserOptionsTask.buildRequestParam(paramObject); //paramObject ->  OptionsParameterEdit
			return mEditUserOptionsTask;
		case TYPE_GET_DEL_OPTIONS:
			mDelUserOptionsTask = new DelUserOptionsTask(taskType);
			mDelUserOptionsTask.buildRequestParam(paramObject); //paramObject ->  OptionsParameter
			return mDelUserOptionsTask;
		
		}
		return super.createTask(taskType, paramObject);
	}

}
