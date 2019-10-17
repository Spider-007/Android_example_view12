package com.htmitech.emportal.ui.commonoptions.data;

import java.io.Serializable;

import android.content.Context;


public class Options implements Serializable {
	

	private static final long serialVersionUID = 11L;
	private static Options option;

	public String id;
	public String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Options() {
	}

	public static Options getInstance(Context androidcontext, String id,String value) {
		if (null == option) {
			option = new Options();
		}

		option.id = id;
		option.value = value;

		return option;
	}

}
