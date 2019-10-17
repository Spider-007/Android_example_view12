package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class DocResultInfo {
	private DocInfoDes Result;
	private Message Message;
	private int Status;

	public DocInfoDes getResult() {
		return Result;
	}

	public DocResultInfo() {

	}

	public void setResult(DocInfoDes result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		DocResultInfo entity = JSON.parseObject(json, DocResultInfo.class);
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
