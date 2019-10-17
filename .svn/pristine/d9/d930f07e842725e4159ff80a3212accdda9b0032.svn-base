package com.htmitech.emportal.ui.commonoptions.data;


import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class AddUserOptionsEntity {
	
	private AddUserOptionsResult  Result;
	private Message Message;
	private int Status;

	public AddUserOptionsEntity() {

	}

	public void parseJson(String json) throws Exception {
		AddUserOptionsEntity entity = JSON.parseObject(json,
				AddUserOptionsEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;

	}

	

	public AddUserOptionsResult getResult() {
		return Result;
	}

	public void setResult(AddUserOptionsResult result) {
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
