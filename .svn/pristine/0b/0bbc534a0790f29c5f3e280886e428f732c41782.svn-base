package com.minxing.client.receiver.PushReceiverBean;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.Message;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-3-9.
 */
public class CallBackReceiptBean implements Serializable {
    public String Result;
    public Message Message;
    public int Status;

    public void parseJson(String json) throws Exception {
        CallBackReceiptBean entity = JSON.parseObject(json, CallBackReceiptBean.class);
        this.Result = entity.Result;
        this.Message = entity.Message;
        this.Status = entity.Status;
    }
}
