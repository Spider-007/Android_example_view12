package com.htmitech.emportal.ui.document.data;

import java.util.ArrayList;

public class DocumentNodeListEntity {
	public int code;
	public String message;
	public String debugMsg;
	public ArrayList<DocumentNodeEntity> result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMsg() {
		return debugMsg;
	}

	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}

	public ArrayList<DocumentNodeEntity> getResult() {
		return result;
	}

	public void setResult(ArrayList<DocumentNodeEntity> result) {
		this.result = result;
	}
}
