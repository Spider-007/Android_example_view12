package com.htmitech.emportal.ui.plugin.zt.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

public class GetPieChartParameterResultInfo {
	GetPieChartParameterResult Result[];
	Message Message;
	int Status;
	
	public void parseJson(String json) throws Exception {
		GetPieChartParameterResultInfo entity = JSON.parseObject(json, GetPieChartParameterResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}
	
	public GetPieChartParameterResult[] getResult() {
		return Result;
	}

	public void setResult(GetPieChartParameterResult[] result) {
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
