package com.htmitech.htnativestartformplugin.entity;

import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.entity.AttachmentInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import htmitech.com.componentlibrary.entity.EditField;
import htmitech.com.componentlibrary.entity.InfoRegion;
import htmitech.com.componentlibrary.entity.InfoTab;

public class DocInfoDes implements Serializable {
	private static final long serialVersionUID = 1L;
	private String docID;
	private String docType;

	private String formId;
	private String dataId;
	private String TableName;
	private String FormPart;
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

	//新增 2017年6月9日11:08:47
	private String DocviewUrl;


	private String Kind;



	public String getDocviewUrl() {
		return DocviewUrl;
	}

	public void setDocviewUrl(String docviewUrl) {
		DocviewUrl = docviewUrl;
	}

	public String getKind() {
		return Kind;
	}

	public void setKind(String kind) {
		Kind = kind;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public InfoRegion[] getRegionItems() {
		return RegionItems;
	}

	public void setRegionItems(InfoRegion[] regionItems) {
		RegionItems = regionItems;
	}

	public static long getSerialversionuid() {
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

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		this.TableName = tableName;
	}

	public String getFormPart() {
		return FormPart;
	}

	public void setFormPart(String formPart) {
		this.FormPart = formPart;
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
			
			
			/*for (int i=0; i<countCount; i++ ){
				
				for(int j=0; j<editFields.size(); j++){
					if (EditFields.get(i).getKey().equalsIgnoreCase(editFields.get(j).getKey())  &&
							EditFields.get(i).getFormKey().equalsIgnoreCase(editFields.get(j).getFormKey()) )
						EditFields.get(i).setValue( editFields.get(j).getValue());
					else
						EditFields.add(editFields.get(j));
				}
			}*/
				
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

	public void cleanFields(){
		EditFields = null;
	}

}
