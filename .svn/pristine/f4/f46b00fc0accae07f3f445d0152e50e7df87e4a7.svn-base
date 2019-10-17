package com.htmitech.emportal.request;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.proxy.myenum.LogManagerEnum;

import java.util.HashMap;

public class AddOrDeleteModel extends BaseModel {
    private final String TAG = AddOrDeleteModel.class.getName();
    private AddUserTask mUserTask;
    private DeleteUserTask mDeleteUserTask;
    public static final int TYPE_ADD_USER = 1;
    public static final int TYPE_DELETE_USER = 2;
    public static final int TYPE_GetUserOptions = 1; //获得当前用户的常用意见

    public AddOrDeleteModel(IBaseCallback callback) {
        super(callback);
    }

    protected BaseTaskBody createTask(int type,
                                      HashMap<String, String> paramHashMap) {
        BaseTaskBody task = null;
        switch (type) {
            case TYPE_ADD_USER:
                mUserTask = new AddUserTask();
                mUserTask.setFunctionCode(LogManagerEnum.ADD_USER_RELATIONSHIP.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER;
                mUserTask.buildRequestParam(paramHashMap);
                task = mUserTask;
                break;
            case TYPE_DELETE_USER:
                mDeleteUserTask = new DeleteUserTask();
                mDeleteUserTask.taskType = TYPE_DELETE_USER;
                mDeleteUserTask.buildRequestParam(paramHashMap);
                task = mDeleteUserTask;
                break;
        }
        return task;
    }


    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        // TODO Auto-generated method stub

        BaseTaskBody task = null;
        switch (taskType) {
            case TYPE_ADD_USER:
                mUserTask = new AddUserTask();
                mUserTask.setFunctionCode(LogManagerEnum.ADD_USER_RELATIONSHIP.getFunctionCode());
                mUserTask.taskType = TYPE_ADD_USER;
                mUserTask.buildRequestParam(paramObject);
                task = mUserTask;
                break;
            case TYPE_DELETE_USER:
                mDeleteUserTask = new DeleteUserTask();
                mDeleteUserTask.setFunctionCode(LogManagerEnum.REMOVE_USER_RELATIONSHIP.getFunctionCode());
                mDeleteUserTask.taskType = TYPE_DELETE_USER;
                mDeleteUserTask.buildRequestParam(paramObject);
                task = mDeleteUserTask;
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
