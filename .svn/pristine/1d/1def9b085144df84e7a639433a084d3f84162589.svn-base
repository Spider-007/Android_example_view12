package com.htmitech.emportal.ui.commonoptions.data;


import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class EditUserOptionsEntity {
	private Boolean Result;
	private Message Message;
	private int Status;

	public EditUserOptionsEntity() {

	}

	public void parseJson(String json) throws Exception {
		EditUserOptionsEntity entity = JSON.parseObject(json,
				EditUserOptionsEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;

	}

	public Boolean getResult() {
		return Result;
	}

	public void setResult(Boolean result) {
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
