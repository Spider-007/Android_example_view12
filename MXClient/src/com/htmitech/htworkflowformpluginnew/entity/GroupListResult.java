package com.htmitech.htworkflowformpluginnew.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/17.
 */

public class GroupListResult implements Serializable{
    public String flowId;
    public String flowName;
    public String flowShortName;
    public int count;

    public GroupListResult(){

    }
    public GroupListResult(String flowName, String flowShortName){
        this.flowShortName = flowShortName;
        this.flowName = flowName;
    }

    public String getFlowId() {
        if(TextUtils.isEmpty(flowId)){
            flowId = "";
        }
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        if(TextUtils.isEmpty(flowName)){
            flowName = "";
        }
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowShortName() {
        if(TextUtils.isEmpty(flowShortName)){
            flowShortName = "";
        }
        return flowShortName;
    }

    public void setFlowShortName(String flowShortName) {
        this.flowShortName = flowShortName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
