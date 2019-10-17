package com.htmitech.proxy.interfaces;

import android.content.Context;

import com.htmitech.proxy.doman.DeviceSafeConfigResult;

/**
 * Created by yanxin on 2017-8-8.
 */
public interface IDeviceState {
    public boolean notNetWork(Context context);

    public ISCheckEnum deviceOperate(Context context);

    public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult);

    public enum ISCheckEnum{

        IS_NEET_CHECK(),//检查
        IS_NEET_AUDIT(),//审批
        OTHER(),//其他
        NOT_NET_WORK();//返回无网络
        int ineedCheckValue ,ieedAuditValue;
        ISCheckEnum(){

        }
        ISCheckEnum(int value,int ieedAuditValue){
            this.ineedCheckValue = value;
            this.ieedAuditValue = ieedAuditValue;
        }

        public void setIeedAuditValue(int ieedAuditValue) {
            this.ieedAuditValue = ieedAuditValue;
        }

        public void setIneedCheckValue(int ineedCheckValue) {
            this.ineedCheckValue = ineedCheckValue;
        }

        public int getIeedAuditValue() {
            return ieedAuditValue;
        }

        public int getIneedCheckValue() {
            return ineedCheckValue;
        }
    }
}
