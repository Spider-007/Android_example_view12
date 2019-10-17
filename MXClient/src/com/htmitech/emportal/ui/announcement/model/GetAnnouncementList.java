package com.htmitech.emportal.ui.announcement.model;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.ui.announcement.task.GetAnnouncementListTask;

/**
 * Created by yanxin on 2017-3-30.
 * 请求通知公告列表网络Model
 */
public class GetAnnouncementList extends BaseModel {
    private final String TAG = GetAnnouncementList.class.getName();
    private GetAnnouncementListTask  mTask;

    /** 获取列表*/
    public static final int TYPE_GET_ZERO_LIST = 0;
    /** 获取更多列表*/
    public static final int TYPE_GET_More_LIST = 1;

    public GetAnnouncementList(IBaseCallback callback) {
        super(callback);
    }

    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        // TODO Auto-generated method stub
        BaseTaskBody task = null;
        switch (taskType) {
            case TYPE_GET_ZERO_LIST:
                mTask = new GetAnnouncementListTask() ;
                mTask.taskType = TYPE_GET_ZERO_LIST;
                mTask.buildRequestParam(paramObject);
                task = mTask;
                break;
            case TYPE_GET_More_LIST:
                mTask = new GetAnnouncementListTask() ;
                mTask.taskType = TYPE_GET_More_LIST;
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
