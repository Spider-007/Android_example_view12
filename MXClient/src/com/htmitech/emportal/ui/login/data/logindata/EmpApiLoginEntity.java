package com.htmitech.emportal.ui.login.data.logindata;

/**
 * Created by heyang on 2016-12-8.
 */
public class EmpApiLoginEntity {
    public int code;
    public String debugMsg;
    public String message;
    public EmpApiLoginResult result;

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

    public EmpApiLoginResult getResult() {
        return result;
    }

    public void setResult(EmpApiLoginResult result) {
        this.result = result;
    }
}

