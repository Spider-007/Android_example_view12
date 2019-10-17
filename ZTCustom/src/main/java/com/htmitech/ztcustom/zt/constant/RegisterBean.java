package com.htmitech.ztcustom.zt.constant;

/**
 * Created by htmitech on 2018/8/16.
 */

public class RegisterBean {


    /**
     * success : true
     * msg : 用户注册申请成功
     */

    private boolean success;
    private String msg;
    public String message;
    public int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
