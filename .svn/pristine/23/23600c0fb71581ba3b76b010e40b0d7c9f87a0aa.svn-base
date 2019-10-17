package com.htmitech.emportal.ui.appcenter.data;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.entity.netcommon.Message;
import com.minxing.kit.api.bean.MXAppInfo;

public class OcuListEntity {

	private List<MXAppInfo> currentOcus;
	private String Result;
	private Message Message;
	private int Status;

	public void parseJson(String json) throws Exception {
		
		//{"Result":"未将对象引用设置到对象的实例。","Message":{"StatusCode":1000,"StatusMessage":"系统维护中，请联系管理员","ElementName":null},"Status":1}
		
		OcuListEntity entity = JSON.parseObject(json, OcuListEntity.class);
		this.Result = entity.Result;
		this.Message = entity.Message;
		this.Status = entity.Status;
	}



	public String getResult() {
		return Result;
	}



	public void setResult(String result) {
		Result = result;
		
		if (result != null){
			//序列化从网络上读取的Json字符串
			setCurrentOcus(null);
		}
		else
			setCurrentOcus(null);
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



	public List<MXAppInfo> getCurrentOcus() {
		return currentOcus;
	}



	public void setCurrentOcus(List<MXAppInfo> currentOcus) {
		this.currentOcus = currentOcus;
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
