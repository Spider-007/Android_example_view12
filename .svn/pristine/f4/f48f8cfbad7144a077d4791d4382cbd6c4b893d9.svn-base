package com.htmitech.emportal.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class TableChartResultInfo {
	private TableChartResult Result;
	private Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
//		JSON.parseObject(text, features)
		TableChartResultInfo entity = JSON.parseObject(json, TableChartResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public TableChartResult getResult() {
		return Result;
	}

	public void setResult(TableChartResult result) {
		Result = result;
	}

	public Message getMessage() {
		return Message;
	}

	public void setMessage(Message message) {
		Message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
}
