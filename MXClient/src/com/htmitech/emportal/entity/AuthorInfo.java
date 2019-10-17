package com.htmitech.emportal.entity;


import java.io.Serializable;

public class AuthorInfo implements Serializable{
	

	private String UserID;

	private String UserName;
	
	private String thridThirdUserId;

	public String getThridThirdUserId() {
		return thridThirdUserId;
	}

	public void setThridThirdUserId(String thridThirdUserId) {
		this.thridThirdUserId = thridThirdUserId;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}


 
}
