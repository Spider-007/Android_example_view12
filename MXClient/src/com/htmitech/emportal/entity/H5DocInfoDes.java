package com.htmitech.emportal.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class H5DocInfoDes implements Serializable {
	private static final long serialVersionUID = 1L;
	private String DocID;
	private String FormID;
	private String DataId;
	private String TableName;
	public List<H5EditField> FormInfo;

	private InfoRegion[] RegionItems;
	public List<InfoTab> TabItems;
	private List<EditField> EditFields;
	private List<ActionInfo> listActionInfo;
	private List<AttachmentInfo> listAttInfo;
	private String DocAttachmentID;
	private String FlowId;
	private String FlowName;
	private String CurrentAuthorId;
	private String CurrentAuthor;
	private String CurrentNodeID;
	private String CurrentNodeName;
	private String CurrentUserId;
	private String CurrentUsername;
	private String CurrentTrackId;
	private String Kind;

	public String getDocID() {
		return DocID;
	}

	public void setDocID(String docID) {
		DocID = docID;
	}

	public String getFormID() {
		return FormID;
	}

	public void setFormID(String formID) {
		FormID = formID;
	}

	public String getDataId() {
		return DataId;
	}

	public void setDataId(String dataId) {
		DataId = dataId;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public List<H5EditField> getFormInfo() {
		return FormInfo;
	}

	public void setFormInfo(List<H5EditField> formInfo) {
		FormInfo = formInfo;
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

	public String getCurrentAuthorId() {
		return CurrentAuthorId;
	}

	public void setCurrentAuthorId(String currentAuthorId) {
		CurrentAuthorId = currentAuthorId;
	}

	public String getCurrentAuthor() {
		return CurrentAuthor;
	}

	public void setCurrentAuthor(String currentAuthor) {
		CurrentAuthor = currentAuthor;
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

	public String getKind() {
		return Kind;
	}

	public void setKind(String kind) {
		Kind = kind;
	}
	public InfoRegion[] getRegionItems() {
		return RegionItems;
	}

	public void setRegionItems(InfoRegion[] regionItems) {
		RegionItems = regionItems;
	}

	public List<EditField> getEditFields() {
		return EditFields;
	}

	public void setEditFields(List<EditField> editFields) {


		if (EditFields == null || EditFields.size() == 0){
			EditFields = new Vector<EditField>();
			for(int j=0; j<editFields.size(); j++){
				EditFields.add(editFields.get(j));
			}
		}

		else{
			//先从现有的EditFields中查找，是否存在。存在则更新。不在则添加
			boolean hasFind = false;
			for(int j=0; j<editFields.size(); j++){
				hasFind = false; //开始查找

				for (int i=0; i<EditFields.size(); i++ ){
					if (EditFields.get(i).getKey().equalsIgnoreCase(editFields.get(j).getKey())  && null != EditFields.get(i).getFormKey() && null != editFields.get(j).getFormKey() &&
							EditFields.get(i).getFormKey().equalsIgnoreCase(editFields.get(j).getFormKey()) ){
						hasFind = true;
						EditFields.get(i).setValue( editFields.get(j).getValue());
					}
				}
				if (!hasFind) //没有找到
					EditFields.add(editFields.get(j));
			}



		}

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


}
