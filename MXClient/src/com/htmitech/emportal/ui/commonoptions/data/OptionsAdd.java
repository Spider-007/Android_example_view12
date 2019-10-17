package com.htmitech.emportal.ui.commonoptions.data;

import java.io.Serializable;

import android.content.Context;

public class OptionsAdd implements Serializable {
	private static final long serialVersionUID = 11L;
	private static OptionsAdd option;

	public String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public OptionsAdd() {
	}

	public static OptionsAdd getInstance(Context androidcontext, String value) {
		if (null == option) {
			option = new OptionsAdd();
		}
		option.value = value;

		return option;
	}

}
