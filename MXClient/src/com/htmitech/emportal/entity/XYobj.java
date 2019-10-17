package com.htmitech.emportal.entity;

public class XYobj {
	String ID;
	int Numbers;
	int MaxValue;
	int MinValue;
	String TextArray[];
	int Style;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getNumbers() {
		return Numbers;
	}
	public void setNumbers(int numbers) {
		Numbers = numbers;
	}
	public int getMaxValue() {
		return MaxValue;
	}
	public void setMaxValue(int maxValue) {
		MaxValue = maxValue;
	}
	public int getMinValue() {
		return MinValue;
	}
	public void setMinValue(int minValue) {
		MinValue = minValue;
	}
	public String[] getTextArray() {
		return TextArray;
	}
	public void setTextArray(String[] textArray) {
		TextArray = textArray;
	}
	public int getStyle() {
		return Style;
	}
	public void setStyle(int style) {
		Style = style;
	}
}
