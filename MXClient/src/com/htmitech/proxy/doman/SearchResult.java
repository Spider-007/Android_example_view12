package com.htmitech.proxy.doman;

import java.util.ArrayList;

import htmitech.com.componentlibrary.entity.Dics;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SearchResult {
    public int code;
    public String debugMsg;
    public String message;
    public ArrayList<Dics> result;

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

    public ArrayList<Dics> getResult() {
        if(result == null){
            result = new ArrayList<Dics>();
        }
        return result;
    }

    public void setResult(ArrayList<Dics> result) {
        this.result = result;
    }
}
