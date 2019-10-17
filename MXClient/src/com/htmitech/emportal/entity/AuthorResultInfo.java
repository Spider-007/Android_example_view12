package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class AuthorResultInfo {
	private AuthorResult Result;
	private Message Message;
	private int Status;
	
	public AuthorResult getResult() {
		return Result;
	}

	public AuthorResultInfo() {

	}

	public void setResult(AuthorResult result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		AuthorResultInfo entity = JSON.parseObject(json, AuthorResultInfo.class);
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
