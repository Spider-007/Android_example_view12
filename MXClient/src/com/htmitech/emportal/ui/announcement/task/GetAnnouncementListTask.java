package com.htmitech.emportal.ui.announcement.task;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.announcement.entity.ResponseAnnouncementList;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by yanxin on 2017-3-30.
 * 请求通知公告列表网络Task
 */
public class GetAnnouncementListTask extends BaseTaskBody {
    private final String TAG = GetAnnouncementListTask.class.getName();
    public static int TASK_TYPE = 3;
    private ResponseAnnouncementList mEntitys;
    private Object paramObject = null;
    public GetAnnouncementListTask() {

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
                ServerUrlConstant.GET_ANNOUNCEMENT_LIST);
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
            mEntitys = new ResponseAnnouncementList();
            mEntitys.parseJson(result);
            return mEntitys;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
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
                mEntitys.Message.getStatusCode()
                : BaseNetRequestHandler.CODE_FAIL_STATUS;
    }

    @Override
    protected String getErrorMsg() {
        return mEntitys != null
                && mEntitys.Message != null ?
                mEntitys.Message.getStatusMessage()
                : "";
    }

    @Override
    protected int getStatus() {
        return mEntitys != null ? mEntitys.Status
                : BaseNetRequestHandler.STATUSERROR;
    }
}
