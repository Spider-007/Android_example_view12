package com.htmitech.emportal.ui.daiban.data.getdoclist;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.Doc;
import com.htmitech.emportal.entity.netcommon.Message;

public class GetDocListEntity {

	private Doc[]  Result;
	private Message Message;
	private int Status;

	public void parseJson(String json) throws Exception {
		
		//反序列化成Doc结构了
		GetDocListEntity entity = JSON.parseObject(json, GetDocListEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}


	public Doc[] getResult() {
		return Result;
	}



	public void setResult(Doc[] result) {
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

//	@Override
//	public int hashCode() {
//		return new HashCodeBuilder().append(data).append(status).toHashCode();
//	}
//
//	@Override
//	public boolean equals(Object other) {
//		if (other == this) {
//			return true;
//		}
//		if ((other instanceof LoginEntity) == false) {
//			return false;
//		}
//		LoginEntity rhs = ((LoginEntity) other);
//		return new EqualsBuilder().append(data, rhs.data)
//				.append(status, rhs.status).isEquals();
//	}
}
