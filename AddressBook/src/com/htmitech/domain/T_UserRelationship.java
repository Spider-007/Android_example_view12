package com.htmitech.domain;

import com.easemob.chat.EMContact;
import com.htmitech.dao.SYS_UserDAO;

import java.io.Serializable;

/**
 * *************************************************
 * 用户的常用联系人
 * *************************************************
 * @author Tony
 *
 */
public class T_UserRelationship extends EMContact implements Serializable{
	private String UserId;
	private String CUserId;
	private String header;
	public Short statusFlag;
	private SYS_User mSYS_User ;
	public SYS_User getmSYS_User() {
		return mSYS_User;
	}
	public void setmSYS_User(SYS_User mSYS_User) {
		this.mSYS_User = mSYS_User;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getCUserId() {
		return CUserId;
	}
	public void setCUserId(String cUserId) {
		CUserId = cUserId;
	}

	public Short getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Short statusFlag) {
		this.statusFlag = statusFlag;
	}

	// 重写
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null || !(o instanceof T_UserRelationship)) {

			return false;
		}
		T_UserRelationship mT_UserRelationship = (T_UserRelationship) o;

		return mT_UserRelationship.CUserId.equals(CUserId);
	}
}
