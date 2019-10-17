package com.htmitech.emportal.request;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;

import java.util.HashMap;

public class RequestPeopleModel extends BaseModel {
    private final String TAG = RequestPeopleModel.class.getName();
    private PeopleMessageRequestTask mUserTask;
    public static final int TYPE_ADD_USER = 1;
    public static final int TYPE_ADD_USER_IMAGE = 2;

    public RequestPeopleModel(IBaseCallback callback) {
        super(callback);
    }

    protected BaseTaskBody createTask(int type,
                                      HashMap<String, String> paramHashMap) {
        BaseTaskBody task = null;
        switch (type) {
            case TYPE_ADD_USER:
                mUserTask = new PeopleMessageRequestTask();
                mUserTask.setFunctionCode(LogManagerEnum.MYINFO_UPDATE_INFO.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER;
                mUserTask.buildRequestParam(paramHashMap);
                task = mUserTask;
                break;
            case TYPE_ADD_USER_IMAGE:
                mUserTask = new PeopleMessageRequestTask();
                mUserTask.setFunctionCode(LogManagerEnum.MYINFO_UPDATE_PIC.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER_IMAGE;
                mUserTask.buildRequestParam(paramHashMap);
        }
        return task;
    }


    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        // TODO Auto-generated method stub

        BaseTaskBody task = null;
        switch (taskType) {
            case TYPE_ADD_USER:
                mUserTask = new PeopleMessageRequestTask();
                mUserTask.setFunctionCode(LogManagerEnum.MYINFO_UPDATE_INFO.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER;
                mUserTask.buildRequestParam(paramObject);
                task = mUserTask;
                break;
            case TYPE_ADD_USER_IMAGE:
                mUserTask = new PeopleMessageRequestTask();
                mUserTask.setFunctionCode(LogManagerEnum.MYINFO_UPDATE_PIC.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER_IMAGE;
                mUserTask.buildRequestParam(paramObject);
                task = mUserTask;
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
