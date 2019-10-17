package com.htmitech.proxy.doman;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yanxin on 2017-8-1.
 * 设备配置相应的Result
 */
public class DeviceSafeConfigResult implements Serializable {
    public String logFunction;
    public String logException;
    public String logBehavior;
    public String beLogList;
    public String exLogList;
    public String pageSize;
    public String pageNum;
    public String orderBy;
    public ArrayList<Object> page;
    public String createTime;
    public String updateTime;
    public String configId;
    public int configType;
    public String groupCorpId;
    public String corpId;
    public String userId;
    public Integer defineLevel;
    public int loginVerifyDevice;
    public int autoBindingDevice;
    public int auditAddBinging;
    public int auditRemoveBinding;
    public int auditPullBlack;
    public int auditLossReport;
    public String skipAuditList;
    public int userBindingDeviceLimit;
    public int appVerifyAddBinding;
    public int appVerifyRemoveBinding;
    public int appVerifyPullBlack;
    public int appVerifyLossReport;
    public String createBy;
    public String updateBy;
    public Integer  vipFlag;
}
