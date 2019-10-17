package com.htmitech.emportal.entity;


//Parameters
public class ReportResult {
	@Override
	public String toString() {
		return "ID=" + ID + ", DataSetID=" + DataSetID
				+ ", Name=" + Name + ", Des=" + Des + ", Count=" + Count
				+ ", Type=" + Type + ", Values=" + Values + ", IsCustomer="
				+ IsCustomer + ", DataSourceName=" + DataSourceName
				+ ", DataSetName=" + DataSetName + ", FieldName=" + FieldName
				+ "";
	}
	String ID;
	String DataSetID;
	String Name;
	String Des;
	int Count;
	int Type;
	String Values[];
	String IsCustomer;
	String DataSourceName;
	String DataSetName;
	String FieldName;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDataSetID() {
		return DataSetID;
	}
	public void setDataSetID(String dataSetID) {
		DataSetID = dataSetID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDes() {
		return Des;
	}
	public void setDes(String des) {
		Des = des;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public String[] getValues() {
		return Values;
	}
	public void setValues(String[] values) {
		Values = values;
	}
	public String isIsCustomer() {
		return IsCustomer;
	}
	public void setIsCustomer(String isCustomer) {
		IsCustomer = isCustomer;
	}
	public String getDataSourceName() {
		return DataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		DataSourceName = dataSourceName;
	}
	public String getDataSetName() {
		return DataSetName;
	}
	public void setDataSetName(String dataSetName) {
		DataSetName = dataSetName;
	}
	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
}
