package com.minxing.client.receiver.pushtask;

import com.htmitech.commonx.util.LogUtil;
import com.htmitech.emportal.base.BaseNetRequestHandler;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.net.HttpRequestEntity;
import com.htmitech.emportal.ui.detail.model.task.DoActionTask;
import com.minxing.client.receiver.PushReceiverBean.CallBackReceiptBean;

import java.util.HashMap;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by yanxin on 2017-3-9.
 */
public class receiptpushtask extends BaseTaskBody {
    private final String TAG = DoActionTask.class.getName();
    private CallBackReceiptBean mCallBackReceiptBean;
    private HashMap<String, String> paramHashMap = new HashMap<String, String>();
    public static int TASK_TYPE = 0;

    public receiptpushtask(int taskType) {
        super.taskType = taskType;
    }
    public void setFunctionCode(String functionCode){
        httpUtils.logfunctionCode = functionCode;
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
                ServerUrlConstant.RECEIPT_MESSAGE);
        if (paramObject != null) {
            requestEntity.setRequestObject(paramObject);
        }
        return requestEntity;
    }

    public void buildRequestParam(Object paramObjectinput) {
        paramObject = paramObjectinput;

    }

    public void buildRequestParam(HashMap<String, String> temp) {
        if (paramHashMap == null) {
            paramHashMap = new HashMap<String, String>();
        } else {
            paramHashMap.clear();
        }
        paramHashMap.putAll(temp);
    }

    @Override
    protected synchronized Object parseJson(String result) {
        try {
            mCallBackReceiptBean = new CallBackReceiptBean();
            mCallBackReceiptBean.parseJson(result);
            return mCallBackReceiptBean;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected synchronized boolean hasDataEntity() {
        return mCallBackReceiptBean != null ? true : false;
    }

    @Override
    protected String getToken() {
        return "";
    }

    @Override
    protected int getStatusCode() {
        return mCallBackReceiptBean != null
                && mCallBackReceiptBean.Message != null ? mCallBackReceiptBean.
                Message.getStatusCode()
                : BaseNetRequestHandler.CODE_FAIL_STATUS;
    }

    protected int getStatus() {
        return mCallBackReceiptBean != null ? mCallBackReceiptBean.Status
                : BaseNetRequestHandler.STATUSERROR;
    }

    @Override
    protected String getErrorMsg() {
        return mCallBackReceiptBean != null
                && mCallBackReceiptBean.Message.getStatusMessage() != null ? mCallBackReceiptBean
                .Message.getStatusMessage() : "";
    }

}
