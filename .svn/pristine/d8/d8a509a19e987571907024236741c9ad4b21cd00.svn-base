package com.htmitech.htworkflowformpluginnew.entity;

import com.alibaba.fastjson.JSON;

public class WordTextInfo {
	private String Result;
	private com.htmitech.emportal.entity.netcommon.Message Message;
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

	public com.htmitech.emportal.entity.netcommon.Message getMessage() {
		return Message;
	}

	public void setMessage(com.htmitech.emportal.entity.netcommon.Message message) {
		Message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
