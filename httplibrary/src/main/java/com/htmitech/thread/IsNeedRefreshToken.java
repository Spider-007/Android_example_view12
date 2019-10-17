package com.htmitech.thread;

import android.text.TextUtils;

/**
 * Created by heyang on 2017-2-15.
 */
public class IsNeedRefreshToken {
    public int code;
    public String debugMsg;
    public String message;

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
        if(TextUtils.isEmpty(message)){
            message = "";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
