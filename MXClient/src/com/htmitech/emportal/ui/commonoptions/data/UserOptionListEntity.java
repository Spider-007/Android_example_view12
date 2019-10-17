package com.htmitech.emportal.ui.commonoptions.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;
import com.htmitech.emportal.ui.login.data.logindata.UserOptionsResult;

public class UserOptionListEntity {

	private UserOptionsResult Result;
	private Message Message;
	private int Status;

	public void parseJson(String json) throws Exception {

		// {"Result":"未将对象引用设置到对象的实例。","Message":{"StatusCode":1000,"StatusMessage":"系统维护中，请联系管理员","ElementName":null},"Status":1}

		UserOptionListEntity entity = JSON.parseObject(json,
				UserOptionListEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}

	public UserOptionsResult getResult() {
		return Result;
	}

	public void setResult(UserOptionsResult result) {
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

	public void setStatus(Integer status) {
		Status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
