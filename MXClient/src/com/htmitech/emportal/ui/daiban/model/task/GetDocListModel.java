package com.htmitech.emportal.ui.daiban.model.task;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.htcommonformplugin.task.GetGeneralFromListTask;

public class GetDocListModel extends BaseModel {
    private final String TAG = GetDocListModel.class.getName();
    private BaseTaskBody mTask;

    /**
     * 初次获取列表，或刷新数据
     */
    public static final int TYPE_GET_ZERO_LIST = 0;
    /***
     * listview列表下拉获取更多
     ***/
    public static final int TYPE_GET_MORE_LISTDATA = 3;

    public static final int TYPE_GET_GENERAL_FORM_LIST = 4; //通用表单类型

    public static final int TYPE_GET_GENERAL_FORM_LISTDATA = 5; //通用表单类型

    public GetDocListModel(IBaseCallback callback) {
        super(callback);
    }

    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        // TODO Auto-generated method stub
        BaseTaskBody task = null;
        switch (taskType) {
            case TYPE_GET_ZERO_LIST:
                mTask = new GetDocListTask();
                mTask.taskType = TYPE_GET_ZERO_LIST;
                mTask.buildRequestParam(paramObject);
                task = mTask;
                break;
            case TYPE_GET_MORE_LISTDATA:
                mTask = new GetDocListTask();
                mTask.taskType = TYPE_GET_MORE_LISTDATA;
                mTask.buildRequestParam(paramObject);
                task = mTask;
                break;
            case TYPE_GET_GENERAL_FORM_LIST://通用表单
                mTask = new GetGeneralFromListTask();
                mTask.taskType = TYPE_GET_GENERAL_FORM_LIST;
                mTask.buildRequestParam(paramObject);
                task = mTask;
                break;
            case TYPE_GET_GENERAL_FORM_LISTDATA://通用表单
                mTask = new GetGeneralFromListTask();
                mTask.taskType = TYPE_GET_GENERAL_FORM_LISTDATA;
                mTask.buildRequestParam(paramObject);
                task = mTask;
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
