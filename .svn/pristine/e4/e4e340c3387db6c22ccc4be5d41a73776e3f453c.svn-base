package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class BarLineChartResultInfo {

	private BarLineChartResult Result;
	private Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
		BarLineChartResultInfo entity = JSON.parseObject(json, BarLineChartResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public BarLineChartResult getResult() {
		return Result;
	}

	public void setResult(BarLineChartResult reportListResult) {
		this.Result = reportListResult;
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
