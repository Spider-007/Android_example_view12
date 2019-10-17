package com.htmitech.ztcustom.zt.domain;

import java.util.ArrayList;

/**
 * 报表参数返回列表
 * @author htrf-pc
 *
 */
public class ReportParameters {
	public ArrayList<ParameterList>	Result;
	public Messages Message;
	public String Status;
	public ArrayList<ParameterList> getResult() {
		return Result;
	}
	public void setResult(ArrayList<ParameterList> result) {
		Result = result;
	}
	public Messages getMessage() {
		return Message;
	}
	public void setMessage(Messages message) {
		Message = message;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "ReportParameters{" +
				"Result=" + Result +
				", Message=" + Message +
				", Status='" + Status + '\'' +
				'}';
	}
}
