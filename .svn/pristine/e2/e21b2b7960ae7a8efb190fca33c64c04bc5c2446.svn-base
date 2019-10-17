package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class EventDataResultInfo {
	EventDataResult Result;
	Message Message;
	int Status;
	
	public void parseJson(String json) throws Exception {
		EventDataResultInfo entity = JSON.parseObject(json, EventDataResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public EventDataResult getResult() {
		return Result;
	}

	public void setResult(EventDataResult result) {
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
