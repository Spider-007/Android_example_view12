package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class PieChartResultInfo {
	private PieChartResult Result;
	private Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
		PieChartResultInfo entity = JSON.parseObject(json, PieChartResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public PieChartResult getResult() {
		return Result;
	}

	public void setResult(PieChartResult result) {
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
