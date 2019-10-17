package com.minxing.client.receiver.PushReceiverBean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yanxin on 2017-3-8.
 */
public class ReceiverBean implements Serializable{
    public String push_id;
    public String push_code = "";
    public String push_time;
    public String comp_code;
    public int is_need_alert;
    public String push_title;
    public JSONObject  mPush;
    public String push_info;
    public List<PushInfoBean> pushInfoList;
    public int is_need_receipt;
    public List<PushInfoBean> getPushInfoList() {
        pushInfoList = new ArrayList<PushInfoBean>();
        PushInfoBean mPushInfo = null ;
        if(mPush != null){
            Set<String> keySet = mPush.keySet();
            for(String key : keySet){
                mPushInfo = new PushInfoBean(key,mPush.get(key).toString());
                pushInfoList.add(mPushInfo);
            }
        }

        return pushInfoList;
    }

    public void setPushInfoList(List<PushInfoBean> pushInfoList) {
        this.pushInfoList = pushInfoList;
    }


}
