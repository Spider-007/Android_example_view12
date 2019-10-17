package com.htmitech.emportal.ui.daiban.MineMode;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.ui.daiban.model.task.GetFaQiListTask;

/**
 * Created by yanxin on 2016-9-28.
 */
public class GetFaQiListModel extends BaseModel {
    private GetFaQiListTask mTask;

    /** 初次获取列表，或刷新数据 */
    public static final int TYPE_GET_ZERO_LIST = 0;
    /***listview列表下拉获取更多***/
    public static final int TYPE_GET_MORE_LISTDATA = 3;

    public GetFaQiListModel(IBaseCallback callback) {
        super(callback);
    }



    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        // TODO Auto-generated method stub
        BaseTaskBody task = null;
        switch (taskType) {
            case TYPE_GET_ZERO_LIST:
                mTask = new GetFaQiListTask();
                mTask.taskType = TYPE_GET_ZERO_LIST;
                mTask.buildRequestParam(paramObject);
                task = mTask;
                break;
            case TYPE_GET_MORE_LISTDATA:
                mTask = new GetFaQiListTask();
                mTask.taskType = TYPE_GET_MORE_LISTDATA;
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
