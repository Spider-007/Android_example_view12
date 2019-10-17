package com.htmitech.emportal.entity;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.emportal.HtmitechApplication;

import java.util.ArrayList;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public class Logfunction {
    public String deviceInfo;
    public String functionCode;
    public String groupCorpId;
    public long consumeMillis;
    public String portalId;
    public int resultStatus;
    public String appId;
    public String appVersionId;
    public String resultInfo;
    public long functionLogId;
    public long userId;
    public JSONObject appInfo;
    public String keyvalue ;
    public Logfunction(){
        deviceInfo =  ((TelephonyManager) HtmitechApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
