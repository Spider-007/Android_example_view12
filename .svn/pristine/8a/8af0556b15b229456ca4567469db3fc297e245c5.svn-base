package com.htmitech.proxy.myenum;

import android.content.Context;
import android.widget.Toast;

import com.htmitech.proxy.activity.DeviceSafeMainActivity;
import com.htmitech.proxy.doman.DeviceSafeConfigResult;
import com.htmitech.proxy.interfaces.IDeviceState;

import com.htmitech.thread.Network;

/**
 * Created by yanxin on 2017-8-1.
 * 用户设备状态
 */
public enum DeviceStateEnum implements IDeviceState {
    BIND(1, 1, "绑定") {
        @Override
        public ISCheckEnum deviceOperate(Context context) {
            if (notNetWork(context)) {
                return ISCheckEnum.NOT_NET_WORK;
            }
            return checkState(context);

        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }

        @Override
        public boolean notNetWork(Context context) {
            return this.isNotWork(context);
        }
    },         //绑定
    UNBIND(0, 2, "未绑定") {
        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return ISCheckEnum.NOT_NET_WORK;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }

        @Override
        public boolean notNetWork(Context context) {
            return this.isNotWork(context);
        }
    },      //解绑 未绑定
    PULLBLACK(-1, 3, "拉黑") {
        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return ISCheckEnum.NOT_NET_WORK;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }

        @Override
        public boolean notNetWork(Context context) {
            return this.isNotWork(context);
        }
    }, //拉黑
    LOSS(-2, 4, "挂失") {
        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return ISCheckEnum.NOT_NET_WORK;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }

        @Override
        public boolean notNetWork(Context context) {
            return this.isNotWork(context);
        }
    },     //挂失
    LOOK(100, 100, "查看") {
        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return ISCheckEnum.NOT_NET_WORK;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }

        @Override
        public boolean notNetWork(Context context) {
            return this.isNotWork(context);
        }
    },//查看
    UNPULLBLACK(0, 5, "解拉黑") {
        @Override
        public boolean notNetWork(Context context) {
            return false;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return null;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }
    },//解拉黑
    UNLOSS(0, 6, "解挂失") {
        @Override
        public boolean notNetWork(Context context) {
            return false;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context) {
            return null;
        }

        @Override
        public ISCheckEnum deviceOperate(Context context, int bindNum, DeviceSafeConfigResult mDeviceSafeConfigResult) {
            return null;
        }
    };//解挂失

    public int bindState = -10;
    public int appplyType = -10;
    public String typeName;

    DeviceStateEnum(int bindState, int appplyType, String typeName) {
        this.bindState = bindState;
        this.appplyType = appplyType;
        this.typeName = typeName;
    }

    public static int getApplyType(int bindState) {
        for (DeviceStateEnum a : DeviceStateEnum.values()) {
            if (a.bindState == bindState) {
                return a.appplyType;
            }
        }
        return 0;
    }

    public static String getTypeName(int appplyType) {
        for (DeviceStateEnum a : DeviceStateEnum.values()) {
            if (a.appplyType == appplyType) {
                return a.typeName;
            }
        }
        return "";
    }

    public static String getBindName(int bindState) {
        for (DeviceStateEnum a : DeviceStateEnum.values()) {
            if (a.bindState == bindState) {
                return a.typeName;
            }
        }
        return "";
    }

    public boolean isNotWork(Context context) {
        if (!Network.checkNetWork(context)) {
            Toast.makeText(context, "网络异常，请检查！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public ISCheckEnum checkState(Context context) {
        int isNeedCheck = ((DeviceSafeMainActivity) context).deviceSafeConfig.appVerifyPullBlack;
        int isNeedAudit = ((DeviceSafeMainActivity) context).deviceSafeConfig.auditPullBlack;
        if (isNeedCheck == 1) {
            //绑定前需要校验
//            toVerify(mDeviceUserListResultItem,isNeedAudit,DeviceStateEnum.UNBIND.bindState);

            ISCheckEnum.IS_NEET_CHECK.setIeedAuditValue(isNeedAudit);
            ISCheckEnum.IS_NEET_CHECK.setIneedCheckValue(isNeedCheck);
            return ISCheckEnum.IS_NEET_CHECK;
        } else if (isNeedAudit == 1) {
            //需要审批
//            auditFromServer(mDeviceUserListResultItem,state);
            ISCheckEnum.IS_NEET_AUDIT.setIeedAuditValue(isNeedAudit);
            ISCheckEnum.IS_NEET_AUDIT.setIneedCheckValue(isNeedCheck);
            return ISCheckEnum.IS_NEET_AUDIT;
        } else {
//            updateDeviceState(mDeviceUserListResultItem, state);
            ISCheckEnum.OTHER.setIeedAuditValue(isNeedAudit);
            ISCheckEnum.OTHER.setIneedCheckValue(isNeedCheck);
            return ISCheckEnum.OTHER;
        }
    }


    public static int getBindState(int appplyType) {
        for (DeviceStateEnum a : DeviceStateEnum.values()) {
            if (a.appplyType == appplyType) {
                return a.bindState;
            }
        }
        return 0;
    }

}
