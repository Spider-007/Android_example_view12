package com.htmitech.domain;

import android.text.format.Time;

/**
 * *************************************************
 * 人员部门关系
 * *************************************************
 * @author Tony
 *
 */
public class SYS_OrgUser {
	public Integer ID;
	public String UserId;
	public String DepartmentCode;
	public String CreatedBy;
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public String CreatedDate;
	public Integer DisOrder;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getDepartmentCode() {
		return DepartmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		DepartmentCode = departmentCode;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public Integer getDisOrder() {
		return DisOrder;
	}
	public void setDisOrder(Integer disOrder) {
		DisOrder = disOrder;
	}
}
