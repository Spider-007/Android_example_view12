package com.htmitech.emportal.ui.document.data;

import java.io.Serializable;

/**
 * Created by yanxin on 2017-8-31.
 */
public class DocumentSubListEntity implements Serializable {
    public int code;
    public String message;
    public String debugMsg;
    public FsDocnodeListResult result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }

    public FsDocnodeListResult getResult() {
        return result;
    }

    public void setResult(FsDocnodeListResult result) {
        this.result = result;
    }
}
