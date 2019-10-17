package com.htmitech.emportal.entity;

import java.io.Serializable;

public class EventDataResult implements Serializable {
	String SummaryString;
	Object DetailDataSet;
	String ReportID;
	ReportResult[] ReportParamers;
	int ReportType;
	public String getSummaryString() {
		return SummaryString;
	}
	public void setSummaryString(String summaryString) {
		SummaryString = summaryString;
	}
/*	public JSONObject getDetailDataSet() {
		return DetailDataSet;
	}
	public void setDetailDataSet(JSONObject detailDataSet) {
		DetailDataSet = detailDataSet;
	}*/
	public String getReportID() {
		return ReportID;
	}
	public void setReportID(String reportID) {
		ReportID = reportID;
	}
	public ReportResult[] getReportParamers() {
		return ReportParamers;
	}
	public void setReportParamers(ReportResult[] reportParamers) {
		ReportParamers = reportParamers;
	}
	public int getReportType() {
		return ReportType;
	}
	public void setReportType(int reportType) {
		ReportType = reportType;
	}
}
