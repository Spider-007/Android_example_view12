package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class ReportListResultInfo {

	private ReportListResult[] Result;
	private Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
		ReportListResultInfo entity = JSON.parseObject(json, ReportListResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public ReportListResult[] getResult() {
		return Result;
	}

	public void setResult(ReportListResult[] reportListResult) {
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
