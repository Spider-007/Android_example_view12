package com.htmitech.htworkflowformpluginnew.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.entity.AttachmentInfo;
import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.InfoTab;
import htmitech.com.componentlibrary.entity.TrackInfo;

public class DocInfoDes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String docID;
    private String formId;
    private String dataId;
    public List<InfoTab> TabItems;
    private List<ActionInfo> listActionInfo;
    private List<AttachmentInfo> listAttInfo;
    private String DocAttachmentID;
    private String docAttachmentType;
    private String FlowId;
    private String FlowName;
    private String CurrentNodeID;
    private String CurrentNodeName;
    private String CurrentUserId;
    private String CurrentUsername;
    private String CurrentTrackId;
    //新增 2017年6月9日11:08:47
    private String systemCode;
    private List<TrackInfo> listTrackInfo;

    //规则里面接口不给返回 EditFields 这个字段了，但是为了方便提报表单时候传值，这里面就留下了。
    private List<EditField> EditFields;

    public List<EditField> getEditFields() {
        return EditFields;
    }

    public void setEditFields(List<EditField> editFields) {

        //java 新接口要求不在使用“;”去分割 而是使用"|";
        for (int i = 0; i < editFields.size(); i++) {
            if (!TextUtils.isEmpty(editFields.get(i).getValue())) {
                editFields.get(i).getValue().replace(";", "|");
            }
        }

        if (EditFields == null || EditFields.size() == 0) {
            EditFields = new Vector<EditField>();
            for (int j = 0; j < editFields.size(); j++) {
                EditFields.add(editFields.get(j));
            }
        } else {
            //先从现有的EditFields中查找，是否存在。存在则更新。不在则添加
            boolean hasFind = false;
            for (int j = 0; j < editFields.size(); j++) {
                hasFind = false; //开始查找

                for (int i = 0; i < EditFields.size(); i++) {
                    if (EditFields.get(i).getKey().equalsIgnoreCase(editFields.get(j).getKey()) && null != EditFields.get(i).getFormKey() && null != editFields.get(j).getFormKey() &&
                            EditFields.get(i).getFormKey().equalsIgnoreCase(editFields.get(j).getFormKey())) {
                        hasFind = true;
                        EditFields.get(i).setValue(editFields.get(j).getValue());
                    }
                }
                if (!hasFind) //没有找到
                    EditFields.add(editFields.get(j));
            }

        }

    }

    public String getDocAttachmentType() {
        return docAttachmentType;
    }

    public void setDocAttachmentType(String docAttachmentType) {
        this.docAttachmentType = docAttachmentType;
    }

    public void cleanFields() {
        EditFields = null;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public List<InfoTab> getTabItems() {
        return TabItems;
    }

    public void setTabItems(List<InfoTab> tabItems) {
        TabItems = tabItems;
    }

    public List<ActionInfo> getListActionInfo() {
        return listActionInfo;
    }

    public void setListActionInfo(List<ActionInfo> listActionInfo) {
        this.listActionInfo = listActionInfo;
    }

    public List<AttachmentInfo> getListAttInfo() {
        return listAttInfo;
    }

    public void setListAttInfo(List<AttachmentInfo> listAttInfo) {
        this.listAttInfo = listAttInfo;
    }

    public String getDocAttachmentID() {
        return DocAttachmentID;
    }

    public void setDocAttachmentID(String docAttachmentID) {
        DocAttachmentID = docAttachmentID;
    }

    public String getFlowId() {
        return FlowId;
    }

    public void setFlowId(String flowId) {
        FlowId = flowId;
    }

    public String getFlowName() {
        return FlowName;
    }

    public void setFlowName(String flowName) {
        FlowName = flowName;
    }

    public String getCurrentNodeID() {
        return CurrentNodeID;
    }

    public void setCurrentNodeID(String currentNodeID) {
        CurrentNodeID = currentNodeID;
    }

    public String getCurrentNodeName() {
        return CurrentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        CurrentNodeName = currentNodeName;
    }

    public String getCurrentUserId() {
        return CurrentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        CurrentUserId = currentUserId;
    }

    public String getCurrentUsername() {
        return CurrentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        CurrentUsername = currentUsername;
    }

    public String getCurrentTrackId() {
        return CurrentTrackId;
    }

    public void setCurrentTrackId(String currentTrackId) {
        CurrentTrackId = currentTrackId;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public List<TrackInfo> getListTrackInfo() {
        return listTrackInfo;
    }

    public void setListTrackInfo(List<TrackInfo> listTrackInfo) {
        this.listTrackInfo = listTrackInfo;
    }
}
