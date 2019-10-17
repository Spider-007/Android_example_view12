package com.minxing.client.receiver.pushmodel;

import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.BaseTaskBody;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.minxing.client.receiver.pushtask.receiptpushtask;

/**
 * Created by yanxin on 2017-3-9.
 */
public class receiptpushmodel extends BaseModel {
    public static final int TYPE_POST_RECEIPT = 0;



    private receiptpushtask mreceiptpushtask;



    public receiptpushmodel(IBaseCallback callback) {
        super(callback);
    }


    @Override
    protected BaseTaskBody createTask(int taskType, Object paramObject) {
        switch (taskType) {
            case TYPE_POST_RECEIPT:
                mreceiptpushtask = new receiptpushtask(taskType);
                mreceiptpushtask.setFunctionCode(LogManagerEnum.APP_DOC_INFO.functionCode);
                mreceiptpushtask.buildRequestParam(paramObject);//paramObject ->  DocInfoParameters
                return mreceiptpushtask;

        }
        return super.createTask(taskType, paramObject);
    }
}
