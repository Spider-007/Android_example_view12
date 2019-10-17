package com.htmitech.htexceptionmanage.entity;

import java.io.Serializable;

/**
 * 已读、未读数量
 * @author joe
 * @date 2017-07-05 12:04:57
 */
public class AlertCountEntity implements Serializable{

    private int code;
    private String message;
    private String debugMsg;
    private AlertCountResult result;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }
    public String getDebugMsg() {
        return debugMsg;
    }

    public AlertCountResult getResult() {
        return result;
    }

    public void setResult(AlertCountResult result) {
        this.result = result;
    }
}
