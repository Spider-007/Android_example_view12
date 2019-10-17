package entity;

import android.content.Context;
import android.telephony.TelephonyManager;



import java.util.ArrayList;

import htmitech.com.componentlibrary.api.ComponentInit;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public class Logfunction {
    public String deviceInfo;
    public String functionCode;
    public long consumeMillis;
    public String portalId;
    public int resultStatus;
    public String appId;
    public String appVersionId;
    public String resultInfo;
    public long functionLogId;
    public long userId;
    public LogfunactionAppInfo appInfo;
    public ArrayList<KeyvVlue> keyvalue = new ArrayList<KeyvVlue>();
    public Logfunction(){
        deviceInfo =  ((TelephonyManager) ComponentInit.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
}
