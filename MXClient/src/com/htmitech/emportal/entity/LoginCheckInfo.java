package com.htmitech.emportal.entity;


public class LoginCheckInfo   {
	

	private String UserName;

    private String PassWord;

    private String SimIMSI;

    private String PhoneIMEI;

    private String PhoneNumber;

    private boolean BindDevice;

    private boolean isNeedCheckCode;
    
    private String CheckCode;
    
    
    private static LoginCheckInfo instance ;
    private LoginCheckInfo(){
    	
    }
    
    public static LoginCheckInfo getInstance(){
    	if(null == instance){
    		instance = new LoginCheckInfo();
    	}
    	return instance ;
    }

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getSimIMSI() {
		return SimIMSI;
	}

	public void setSimIMSI(String simIMSI) {
		SimIMSI = simIMSI;
	}

	public String getPhoneIMEI() {
		return PhoneIMEI;
	}

	public void setPhoneIMEI(String phoneIMEI) {
		PhoneIMEI = phoneIMEI;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public boolean isBindDevice() {
		return BindDevice;
	}

	public void setBindDevice(boolean bindDevice) {
		BindDevice = bindDevice;
	}

	public boolean isNeedCheckCode() {
		return isNeedCheckCode;
	}

	public void setNeedCheckCode(boolean isNeedCheckCode) {
		this.isNeedCheckCode = isNeedCheckCode;
	}

	public String getCheckCode() {
		return CheckCode;
	}

	public void setCheckCode(String checkCode) {
		CheckCode = checkCode;
	}


	public static void setInstance(LoginCheckInfo instance) {
		LoginCheckInfo.instance = instance;
	}

}
