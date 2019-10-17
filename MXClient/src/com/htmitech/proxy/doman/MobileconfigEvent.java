package com.htmitech.proxy.doman;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 事件定义
 */
public class MobileconfigEvent implements Serializable {
    public String jump_appid;
    public ArrayList<JumpParameter> jump_parameter;


    public String getJump_appid() {
        return jump_appid;
    }

    public void setJump_appid(String jump_appid) {
        this.jump_appid = jump_appid;
    }

    public ArrayList<JumpParameter> getJump_parameter() {
        if(jump_parameter == null){
            jump_parameter = new ArrayList<JumpParameter>();
        }
        return jump_parameter;
    }

    public void setJump_parameter(ArrayList<JumpParameter> jump_parameter) {
        this.jump_parameter = jump_parameter;
    }
}
