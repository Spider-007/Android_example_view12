package com.minxing.client.http;

public enum Interface {
    UPGRADE("/data-crab/appclientversion/hasnewappversion", Object.class),									//系统升级检测
    PING("/api/v1/ping?a", Object.class);
    
	private String mInterface;
	private String formatFace;
	
	private Class<?> mClazz; 
	
	Interface(String aType,Class<?> clazz) { 
		mInterface = aType;
		formatFace = aType;
		mClazz = clazz;
	}
	public String getFormatFace(){
		return formatFace;
	}

	public String getInsType() {
		return mInterface;
	}
	
	public Class<?> getClazz(){
		return mClazz;
	}
	
	public Interface insertParam(Object... param) {
		if (param != null) {
			formatFace = String.format(mInterface, param);
		} else {
			formatFace = mInterface;
		}
		return this;
	}
}
