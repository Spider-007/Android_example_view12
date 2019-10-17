package com.htmitech.htworkflowformpluginnew.entity;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 1.1.2获取待办已办列表
 * 改造完成
 */

public class GetDocListEntity {

    private Doc[] result;
    public int code;
    public String message;
    public String debugMsg;

    public void parseJson(String json) throws Exception {
        //反序列化成Doc结构了
        GetDocListEntity entity = JSON.parseObject(json, GetDocListEntity.class);
        this.result = entity.result;
        this.message = entity.message;
        this.code = entity.code;
    }

    public Doc[] getResult() {
        return result;
    }

    public void setResult(Doc[] result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
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
