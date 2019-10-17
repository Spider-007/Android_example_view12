package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-8-3.
 */
public class DeviceAuditRequest implements Serializable {
    public String groupCorpId;
    public String userId;
    public String deviceId;
    public String deviceSn;
    public int applySource;
    public int verifyMethod;
    public int applyType;
    public String pictureId;
    public int needAudit;
}
