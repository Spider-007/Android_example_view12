package com.htmitech.htcommonformplugin.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 待办已办列表返回结构 实体
 * @date 2017/04/17
 * @author joe
 */
public class GetDoActionEntity {

	private DoAction_result  Result;
	private Message Message;
	private int Status;

	public void parseJson(String json) throws Exception {
		
		//反序列化成Doc结构了
		GetDoActionEntity entity = JSON.parseObject(json, GetDoActionEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}


	public DoAction_result getResult() {
		return Result;
	}



	public void setResult(DoAction_result result) {
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
