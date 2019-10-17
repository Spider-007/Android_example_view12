package com.htmitech.emportal.entity;


public class TextSave {
	
	private String str;
//	private ReceiverOpinionView ro;
	private static TextSave ts = null;
	
	private TextSave(){
	}

	public static TextSave getInstance(){
		if(null == ts){
			ts = new TextSave();
    	}
    	return ts ;
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

//	public ReceiverOpinionView getRo() {
//		return ro;
//	}
//
//	public void setRo(ReceiverOpinionView ro) {
//		this.ro = ro;
//	}
}
