package com.htmitech.emportal.ui.plugin.zt.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.ui.plugin.zt.netcommon.Message;


public class TableChartResultInfo1 {
	private TableChartResult1 Result;
	private com.htmitech.emportal.ui.plugin.zt.netcommon.Message Message;
	private int Status;
	
	public void parseJson(String json) throws Exception {
//		JSON.parseObject(text, features)
		TableChartResultInfo1 entity = JSON.parseObject(json, TableChartResultInfo1.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public TableChartResult1 getResult() {
		return Result;
	}

	public void setResult(TableChartResult1 result) {
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
