package com.htmitech.emportal.entity;

import java.io.Serializable;

public class GetDoclistNew_Entity  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Doc[] docs;
	private int totalRecordCount;
	
	public Doc[] getDocs() {
		return docs;
	}

	public void setDocs(Doc[] docs) {
		this.docs = docs;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}



}
