package com.htmitech.htexceptionmanage.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 异常详情
 *
 * @author joe
 * @date 2017-9-26 09:39:29
 */
public class ExceptionList implements Serializable {

    private String alertId;
    private String sourceType;
    private String sourceTypeName;
    private String sourceId;
    private String createTime;
    private String alertTitle;
    private String alertContent;
    private String alertUserList;
    private String sourceUserName;

    private String sourceContentObject;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public String getAlertUserList() {
        return alertUserList;
    }

    public void setAlertUserList(String alertUserList) {
        this.alertUserList = alertUserList;
    }

    public String getSourceContentObject() {
        return sourceContentObject;
    }

    public void setSourceContentObject(String sourceContentObject) {
        this.sourceContentObject = sourceContentObject;
    }

    public String getSourceUserName() {
        return sourceUserName;
    }

    public void setSourceUserName(String sourceUserName) {
        this.sourceUserName = sourceUserName;
    }
}

