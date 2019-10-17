package com.htmitech.htworkflowformpluginnew.entity;

/**
 * Created by heyang on 2018-1-26.
 */
public class AttentionParameters {
    public String userId;
    public String docId;
    public String doctype;
    public String systemCode;
    public String appId;
    public String attentionFlag;
    public String allowPush;
    public String docTitle;
    public String sendFrom;
    public String sendDate;
    public String iconid;
    public String flowId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(String attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    public String getAllowPush() {
        return allowPush;
    }

    public void setAllowPush(String allowPush) {
        this.allowPush = allowPush;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }
}
