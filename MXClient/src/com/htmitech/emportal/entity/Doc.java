package com.htmitech.emportal.entity;

import java.io.Serializable;



public class Doc implements Serializable{
	
	 private static final long serialVersionUID = 1L;

	  private String docTitle;
      private String docID;
      private String sendFrom;
      private String sendDate;
      private String docType;
      private String todoFlag;
      private String kind;
      private String iconId;


	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getTodoFlag() {
		return todoFlag;
	}

	public void setTodoFlag(String todoFlag) {
		this.todoFlag = todoFlag;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public void setIconId(String iconUrl) {
		this.iconId = iconUrl;
	}
	
	public String getIconId() {
		return iconId;
	}


	@Override
	public String toString() {
		return docID+docTitle;
	}
	
	
}
