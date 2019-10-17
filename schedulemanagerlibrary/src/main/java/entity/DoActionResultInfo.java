package entity;

import com.alibaba.fastjson.JSON;

public class DoActionResultInfo {
	private DoActionResult Result;
	private String Message;
	private int Status;
	private int code;

	public DoActionResult getResult() {
		return Result;
	}

	public DoActionResultInfo() {

	}

	public void setResult(DoActionResult result) {
		Result = result;
	}

	public void parseJson(String json) throws Exception {
		DoActionResultInfo entity = JSON.parseObject(json, DoActionResultInfo.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
		this.code = entity.code;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
