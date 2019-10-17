package com.htmitech.ztcustom.zt.constant;

import java.util.ArrayList;

public class ParameterList {
	private String ID;
	private String DataSetID;
	private String Name;
	private String Des;
	private int Count;
	private int Type;
	private ArrayList<String> Values;
	public ArrayList<String> getValues() {
		return Values;
	}
	public void setValues(ArrayList<String> values) {
		Values = values;
	}
	private String IsCustomer;
	private String DataSourceName;
	private String DataSetName;
	private String FieldName;
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
	public String getIsCustomer() {
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
