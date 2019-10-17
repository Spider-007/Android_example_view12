package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class H5DocResultInfo {
	private H5DocInfoDes Result;
	private com.htmitech.emportal.entity.netcommon.Message Message;
	private int Status;

	private String json;

	public H5DocInfoDes getResult() {
		return Result;
	}

	public H5DocResultInfo() {

	}

	public void setResult(H5DocInfoDes result) {
		Result = result;
	}

	public String  getJsonResult() {
		return json;
	}

	public void parseJson(String json) throws Exception {
		this.json = json;

		H5DocResultInfo entity = JSON.parseObject(json, H5DocResultInfo.class);
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
