package com.htmitech.emportal.ui.daiban.model.task;

import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.daiban.MineMode.MineFaQiResultBean;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by yanxin on 2016-9-28.
 */
public class GetFaQiListTask extends BaseTaskBody {
    public static int TASK_TYPE = 3;
    private MineFaQiResultBean mEntitys;
    private Object paramObject = null;
    public GetFaQiListTask() {

    }

    @Override
    protected String getPreferenceKey() {
        return "";
    }

    @Override
    protected int getRequestType() {
        return POST_HTTP_REQUEST_TYPE;
    }

    @Override
    protected HttpRequestEntity buildRequestEntity() {
        HttpRequestEntity requestEntity = new HttpRequestEntity(
                ServerUrlConstant.SERVER_BASE_URL(),
                ServerUrlConstant.GET_MY_SEND_FLOWLIST);
        if (paramObject != null) {
            requestEntity.setRequestObject(paramObject);
        }
        return requestEntity;
    }


    public void buildRequestParam(Object paramObjectinput) {
        paramObject = paramObjectinput;
    }

    @Override
    protected synchronized Object parseJson(String result) {
        try {
            mEntitys = new MineFaQiResultBean();
            mEntitys.parseJson(result);
            return mEntitys;
        } catch (Exception e) {
//            LogUtil.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected synchronized boolean hasDataEntity() {
        return mEntitys != null ? true : false;
    }

    @Override
    protected String getToken() {
        return "";
    }

    @Override
    protected int getStatusCode() {
        return mEntitys != null && mEntitys.Message != null ?
                mEntitys.Message.StatusCode
                : BaseNetRequestHandler.CODE_FAIL_STATUS;
    }

    @Override
    protected String getErrorMsg() {
        return mEntitys != null
                && mEntitys.Message != null ?
                mEntitys.Message.StatusMessage
                : "";
    }

    @Override
    protected int getStatus() {
        return mEntitys != null ? mEntitys.Status
                : BaseNetRequestHandler.STATUSERROR;
    }

}
