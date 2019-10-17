package com.htmitech.htworkflowformpluginnew.entity;

import android.text.TextUtils;

import com.htmitech.emportal.ui.daiban.minewidget.ui.SlideView;

import java.io.Serializable;

/**
 * 1.1.2待办已办列表
 * 已重新构造完
 */

public class Doc implements Serializable {

    private static final long serialVersionUID = 1L;

    private String docTitle;
    private String docId;
    private String sendFrom;
    private String sendDate;
    private String flowName;
    private String flowId;
    private String todoFlag;
    private String systemCode;
    private String iconUrl;
    //新增 我的关注里面独有的字段
    public String AttentionFlag;
    public String AllowPush;
    public SlideView slideView;
    public boolean isCheck;
    public double efn1;

    public double getEfn1() {
        return efn1;
    }

    public void setEfn1(double efn1) {
        this.efn1 = efn1;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocId() {
        if(TextUtils.isEmpty(docId)){
            docId = "";
        }
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getTodoFlag() {
        return todoFlag;
    }

    public void setTodoFlag(String todoFlag) {
        this.todoFlag = todoFlag;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAttentionFlag() {
        return AttentionFlag;
    }

    public void setAttentionFlag(String attentionFlag) {
        AttentionFlag = attentionFlag;
    }

    public String getAllowPush() {
        return AllowPush;
    }

    public void setAllowPush(String allowPush) {
        AllowPush = allowPush;
    }

    public SlideView getSlideView() {
        return slideView;
    }

    public void setSlideView(SlideView slideView) {
        this.slideView = slideView;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Doc){
            Doc doc = (Doc)o;
            return doc.getDocId().equals(getDocId());
        }

        return false;
    }
}
