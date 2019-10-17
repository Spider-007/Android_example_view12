package com.htmitech.htworkflowformpluginnew.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * todo
 * 定制docgroup返回接口
 *
 */

public class DZDocGroupListResult implements Serializable{
    public int code;
    public String debugMsg;
    public String message;
    public ArrayList<GroupListResult> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<GroupListResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<GroupListResult> result) {
        this.result = result;
    }
}
