package com.htmitech.emportal.entity;

import java.io.Serializable;


public class GetDocInfo_NEWCONTEXT_Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	String ResultCode ;
	String  CommentFieldName ;
	ActionInfo[] listActionInfo;
	String fileId ;
	AttachmentInfo[] listAttInfo ;
	DocInfoDes docinfo;
	
	public String getResultCode() {
		return ResultCode;
	}
	public String getCommentFieldName() {
		return CommentFieldName;
	}
	public ActionInfo[] getListActionInfo() {
		return listActionInfo;
	}
	public String getFileId() {
		return fileId;
	}
	public AttachmentInfo[] getListAttInfo() {
		return listAttInfo;
	}
	public DocInfoDes getDocinfo() {
		return docinfo;
	}
	public void setDocinfo(DocInfoDes docinfo) {
		this.docinfo = docinfo;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public void setCommentFieldName(String commentFieldName) {
		CommentFieldName = commentFieldName;
	}
	public void setListActionInfo(ActionInfo[] listActionInfo) {
		this.listActionInfo = listActionInfo;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public void setListAttInfo(AttachmentInfo[] listAttInfo) {
		this.listAttInfo = listAttInfo;
	}

}
