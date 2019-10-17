package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class FlowProcessInfo {
	private StepDesList Result;
	private Message Message;
	private int Status;
	
	public StepDesList getResult() {
		return Result;
	}

	public FlowProcessInfo() {

	}

	public void setResult(StepDesList result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		FlowProcessInfo entity = JSON.parseObject(json, FlowProcessInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
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