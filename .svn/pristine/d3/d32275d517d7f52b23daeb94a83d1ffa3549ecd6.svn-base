package com.htmitech.htcommonformplugin.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class DownAttachmentFile_isFinishEntity {
	private Download_result Result;
	private Message Message;
	private int Status;

	public Download_result getResult() {
		return Result;
	}

	public DownAttachmentFile_isFinishEntity() {

	}

	public void setResult(Download_result result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		DownAttachmentFile_isFinishEntity entity = JSON.parseObject(json, DownAttachmentFile_isFinishEntity.class);
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
