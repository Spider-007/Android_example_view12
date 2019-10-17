package entity;

import java.io.Serializable;

/**
 * Created by yanxin on 2018-3-30.
 * 日程参与人确认返回接口根数据
 */
public class SchusersCertainResponseRoot implements Serializable {
    private int code;
    private String message;
    private String debugMsg;
    private SchusersCertainResponse result;

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

    public SchusersCertainResponse getResult() {
        return result;
    }

    public void setResult(SchusersCertainResponse result) {
        this.result = result;
    }
}
