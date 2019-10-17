package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class WordTextInfo {
	private String Result;
	private Message Message;
	private int Status;
	
	public String getResult() {
		return Result;
	}

	public WordTextInfo() {

	}

	public void setResult(String result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		WordTextInfo entity = JSON.parseObject(json, WordTextInfo.class);
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
