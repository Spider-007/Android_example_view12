package com.htmitech.htcommonformplugin.entity;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 通知图片长传成功返回
 * @date 2017/06/08
 * @author joe
 */
public class GetSaveExtFieldsEntity {

	private SaveExtFields_result  Result;
	private Message Message;
	private int Status;

	public void parseJson(String json) throws Exception {
		
		//反序列化成Doc结构了
		GetSaveExtFieldsEntity entity = JSON.parseObject(json, GetSaveExtFieldsEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}


	public SaveExtFields_result getResult() {
		return Result;
	}

	public void setResult(SaveExtFields_result result) {
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
