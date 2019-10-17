package com.htmitech.emportal.entity;


public class UserInfo {
	

	private String userID;

	private String userName;

	private String simIMSI;

	private String phoneIMEI;

	private String phoneNumber;

	private String companyName;

	private String oA_UserName;
	private String oA_DeptName;

	private String oA_UserId;

	private String oA_Account;

	private String pwd_string;

	private boolean bindDevice;

	private boolean phone_office_Authy;

	private boolean phone_Chart_Authy;

	private boolean isNeedCheckCode;

	private String checkCode;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSimIMSI() {
		return simIMSI;
	}

	public void setSimIMSI(String simIMSI) {
		this.simIMSI = simIMSI;
	}

	public String getPhoneIMEI() {
		return phoneIMEI;
	}

	public void setPhoneIMEI(String phoneIMEI) {
		this.phoneIMEI = phoneIMEI;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getoA_UserName() {
		return oA_UserName;
	}

	public void setoA_UserName(String oAUserName) {
		oA_UserName = oAUserName;
	}

	public String getoA_DeptName() {
		return oA_DeptName;
	}

	public void setoA_DeptName(String oADeptName) {
		oA_DeptName = oADeptName;
	}

	public String getoA_UserId() {
		return oA_UserId;
	}

	public void setoA_UserId(String oAUserId) {
		oA_UserId = oAUserId;
	}

	public String getoA_Account() {
		return oA_Account;
	}

	public void setoA_Account(String oAAccount) {
		oA_Account = oAAccount;
	}

	public String getPwd_string() {
		return pwd_string;
	}

	public void setPwd_string(String pwdString) {
		pwd_string = pwdString;
	}

	public boolean isBindDevice() {
		return bindDevice;
	}

	public void setBindDevice(boolean bindDevice) {
		this.bindDevice = bindDevice;
	}

	public boolean isPhone_office_Authy() {
		return phone_office_Authy;
	}

	public void setPhone_office_Authy(boolean phoneOfficeAuthy) {
		phone_office_Authy = phoneOfficeAuthy;
	}

	public boolean isPhone_Chart_Authy() {
		return phone_Chart_Authy;
	}

	public void setPhone_Chart_Authy(boolean phoneChartAuthy) {
		phone_Chart_Authy = phoneChartAuthy;
	}

	public boolean isNeedCheckCode() {
		return isNeedCheckCode;
	}

	public void setNeedCheckCode(boolean isNeedCheckCode) {
		this.isNeedCheckCode = isNeedCheckCode;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}


}
