package com.htmitech.emportal.ui.plugin.zt.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class ProjectNameResultInfo {
	ProjectNameResult Result;
	Message Message;
	int Status;
	
	public void parseJson(String json) throws Exception {
		ProjectNameResultInfo entity = JSON.parseObject(json, ProjectNameResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}
	
	public ProjectNameResult getResult() {
		return Result;
	}
	public void setResult(ProjectNameResult result) {
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
