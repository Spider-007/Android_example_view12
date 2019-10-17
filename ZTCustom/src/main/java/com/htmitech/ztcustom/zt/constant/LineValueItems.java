package com.htmitech.ztcustom.zt.constant;

public class LineValueItems {
	private int Value;

	private String DataValue;

	private boolean IsValid;

	public void setValue(int Value) {
		this.Value = Value;
	}

	public int getValue() {
		return this.Value;
	}

	public void setDataValue(String DataValue) {
		this.DataValue = DataValue;
	}

	public String getDataValue() {
		return this.DataValue;
	}

	public void setIsValid(boolean IsValid) {
		this.IsValid = IsValid;
	}

	public boolean getIsValid() {
		return this.IsValid;
	}
}
