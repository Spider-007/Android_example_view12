package com.htmitech.htnativestartformplugin.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by yanxin on 2018-3-31.
 */
public class StartResultInfo implements Serializable {
    public int code;
    public Object debugMsg;
    public String message;
    public DocInfoDes result;
    public void parseJson(String json) throws Exception {
        StartResultInfo entity = JSON.parseObject(json, StartResultInfo.class);
        this.result = entity.result;
        this.message = entity.message;
        this.code = entity.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(Object debugMsg) {
        this.debugMsg = debugMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DocInfoDes getResult() {
        return result;
    }

    public void setResult(DocInfoDes result) {
        this.result = result;
    }
}
