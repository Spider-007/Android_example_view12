package com.htmitech.emportal.ui.commonoptions.data;

import java.io.Serializable;

import android.content.Context;

public class Optionsdel implements Serializable {
	private static final long serialVersionUID = 11L;
	private static Optionsdel option;

	public String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Optionsdel() {
	}

	public static Optionsdel getInstance(Context androidcontext, String id) {
		if (null == option) {
			option = new Optionsdel();
		}

		option.id = id;

		return option;
	}

}
