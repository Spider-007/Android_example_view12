package com.htmitech.emportal.ui.daiban.data.shortoucs;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

public class Status {

    
    private String msg;
    
    private Integer code;

    public void parseJson(JSONObject jsonObject) throws Exception {
        code = jsonObject.optInt("code");
        msg = jsonObject.optString("msg");
    }

    /**
     * 
     * @return The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(msg).append(code).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Status) == false) {
            return false;
        }
        Status rhs = ((Status) other);
        return new EqualsBuilder().append(msg, rhs.msg).append(code, rhs.code).isEquals();
    }

}
