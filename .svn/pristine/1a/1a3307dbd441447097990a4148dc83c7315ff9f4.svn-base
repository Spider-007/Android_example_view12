package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class DownFilesIsFinishResultInfo {
	private DownFileIsFinishResult Result;
	private Message Message;
	private int Status;
	
	public DownFileIsFinishResult getResult() {
		return Result;
	}

	public DownFilesIsFinishResultInfo() {

	}

	public void setResult(DownFileIsFinishResult result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		DownFilesIsFinishResultInfo entity = JSON.parseObject(json, DownFilesIsFinishResultInfo.class);
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
