package com.htmitech.emportal.entity;


import java.io.Serializable;

public class PieChartResult implements Serializable{
	String ReportName;
	String ID;
	Item[] Items;
	int BorderColor;
	int BackColor;
	boolean ShowBorder;
	boolean ShowLegend;
	EventDefiniens ClickEventDefiniens;
	EventDefiniens LongPressEventDefiniens;
	String Remark;
	String CreatedUserID;
	String ModifiedUserID;
	String CreatedTime;
	String ModifiedTime;
	String CreatedUserName;
	String ModifiedUserName;
	String CashTime;
	boolean DefiniensHasParameters;
	boolean HasParameters;
	
	public String getReportName() {
		return ReportName;
	}
	public void setReportName(String reportName) {
		ReportName = reportName;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Item[] getItems() {
		return Items;
	}
	public void setItems(Item[] items) {
		Items = items;
	}
	public int getBorderColor() {
		return BorderColor;
	}
	public void setBorderColor(int borderColor) {
		BorderColor = borderColor;
	}
	public int getBackColor() {
		return BackColor;
	}
	public void setBackColor(int backColor) {
		BackColor = backColor;
	}
	public boolean isShowBorder() {
		return ShowBorder;
	}
	public void setShowBorder(boolean showBorder) {
		ShowBorder = showBorder;
	}
	public boolean isShowLegend() {
		return ShowLegend;
	}
	public void setShowLegend(boolean showLegend) {
		ShowLegend = showLegend;
	}
	public EventDefiniens getClickEventDefiniens() {
		return ClickEventDefiniens;
	}
	public void setClickEventDefiniens(EventDefiniens clickEventDefiniens) {
		ClickEventDefiniens = clickEventDefiniens;
	}
	public EventDefiniens getLongPressEventDefiniens() {
		return LongPressEventDefiniens;
	}
	public void setLongPressEventDefiniens(EventDefiniens longPressEventDefiniens) {
		LongPressEventDefiniens = longPressEventDefiniens;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getCreatedUserID() {
		return CreatedUserID;
	}
	public void setCreatedUserID(String createdUserID) {
		CreatedUserID = createdUserID;
	}
	public String getModifiedUserID() {
		return ModifiedUserID;
	}
	public void setModifiedUserID(String modifiedUserID) {
		ModifiedUserID = modifiedUserID;
	}
	public String getCreatedTime() {
		return CreatedTime;
	}
	public void setCreatedTime(String createdTime) {
		CreatedTime = createdTime;
	}
	public String getModifiedTime() {
		return ModifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		ModifiedTime = modifiedTime;
	}
	public String getCreatedUserName() {
		return CreatedUserName;
	}
	public void setCreatedUserName(String createdUserName) {
		CreatedUserName = createdUserName;
	}
	public String getModifiedUserName() {
		return ModifiedUserName;
	}
	public void setModifiedUserName(String modifiedUserName) {
		ModifiedUserName = modifiedUserName;
	}
	public String getCashTime() {
		return CashTime;
	}
	public void setCashTime(String cashTime) {
		CashTime = cashTime;
	}
	public boolean isDefiniensHasParameters() {
		return DefiniensHasParameters;
	}
	public void setDefiniensHasParameters(boolean definiensHasParameters) {
		DefiniensHasParameters = definiensHasParameters;
	}
	public boolean isHasParameters() {
		return HasParameters;
	}
	public void setHasParameters(boolean hasParameters) {
		HasParameters = hasParameters;
	}
}
