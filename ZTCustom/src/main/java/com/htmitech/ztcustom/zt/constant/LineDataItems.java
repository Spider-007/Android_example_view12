package com.htmitech.ztcustom.zt.constant;

import java.util.List;

public class LineDataItems {
	private String ID;

	private int ChartColor;

	private int TextColor;

	private String Des;

	private String DataValue;

	private List<LineValueItems> ValueItems;

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return this.ID;
	}

	public void setChartColor(int ChartColor) {
		this.ChartColor = ChartColor;
	}

	public int getChartColor() {
		return this.ChartColor;
	}

	public void setTextColor(int TextColor) {
		this.TextColor = TextColor;
	}

	public int getTextColor() {
		return this.TextColor;
	}

	public void setDes(String Des) {
		this.Des = Des;
	}

	public String getDes() {
		return this.Des;
	}

	public void setDataValue(String DataValue) {
		this.DataValue = DataValue;
	}

	public String getDataValue() {
		return this.DataValue;
	}

	public void setValueItems(List<LineValueItems> ValueItems) {
		this.ValueItems = ValueItems;
	}

	public List<LineValueItems> getValueItems() {
		return this.ValueItems;
	}
}
