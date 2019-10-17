package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class ReportResultInfo {
	private ReportResult Result[];
	private Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
		ReportResultInfo entity = JSON.parseObject(json, ReportResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public ReportResult[] getResult() {
		return Result;
	}

	public void setResult(ReportResult[] result) {
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
