package com.htmitech.emportal.ui.plugin.zt.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.ui.plugin.zt.netcommon.Message;


public class BuildResultInfo {
	BuildResult Result;
	com.htmitech.emportal.ui.plugin.zt.netcommon.Message Message;
	int Status;
	
	public void parseJson(String json) throws Exception {
		BuildResultInfo entity = JSON.parseObject(json, BuildResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}
	
	public BuildResult getResult() {
		return Result;
	}
	public void setResult(BuildResult result) {
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
