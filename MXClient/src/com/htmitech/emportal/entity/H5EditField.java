package com.htmitech.emportal.entity;

import java.io.Serializable;
import java.util.List;

public class H5EditField implements Serializable {

	private static final long serialVersionUID = 111111L;

	private String Key;

	private Boolean isReadOnly;

	private String Sign;

	private String isHidden;

	private String Input;

	private String length;

	private Boolean MustInput;

	public String tempValue ;

	public List<SelectionsInfo> Selections ;

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public Boolean getReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		isReadOnly = readOnly;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	public String getInput() {
		return Input;
	}

	public void setInput(String input) {
		Input = input;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Boolean getMustInput() {
		return MustInput;
	}

	public void setMustInput(Boolean mustInput) {
		MustInput = mustInput;
	}

	public String getTempValue() {
		return tempValue;
	}

	public void setTempValue(String tempValue) {
		this.tempValue = tempValue;
	}

	public List<SelectionsInfo> getSelections() {
		return Selections;
	}

	public void setSelections(List<SelectionsInfo> selections) {
		Selections = selections;
	}
}
