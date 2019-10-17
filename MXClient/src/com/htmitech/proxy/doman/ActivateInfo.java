package com.htmitech.proxy.doman;

import java.io.Serializable;

/**
 * Created by Think on 2017/5/4.
 */

public class ActivateInfo implements Serializable {

    public int code;
    public String debugMsg;
    public String message;
    public int result = -1;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
