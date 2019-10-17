package com.htmitech.htworkflowformpluginnew.entity;



public class DownFilesIsFinishResultInfo {
    private DownFileIsFinishResult result;
    private int code;
    private String debugMsg;
    private String message;

    public DownFileIsFinishResult getResult() {
        if(result == null){
            result = new DownFileIsFinishResult();
        }
        return result;
    }

    public void setResult(DownFileIsFinishResult result) {
        this.result = result;
    }

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
}
