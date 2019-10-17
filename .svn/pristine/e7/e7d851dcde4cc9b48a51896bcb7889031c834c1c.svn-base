
package com.htmitech.emportal.ui.login.data.logindata;

import org.json.JSONObject;

public class Message {
	
    private Integer mStatusCode;
    private String mStatusMessage;
    private String mElementName;

    public void parseJson(JSONObject json) throws Exception {
    	mStatusCode = json.optInt("StatusCode");
    	mStatusMessage = json.optString("StatusMessage");
    	mElementName = json.optString("ElementName");
    }

	public Integer getStatusCode() {
		return mStatusCode;
	}

	public void setStatusCode(Integer statusCode) {
		mStatusCode = statusCode;
	}

	public String getStatusMessage() {
		return mStatusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		mStatusMessage = statusMessage;
	}

	public String getElementName() {
		return mElementName;
	}

	public void setElementName(String elementName) {
		mElementName = elementName;
	}
    
    

}
