
package com.htmitech.emportal.ui.login.data.logindata;

import java.util.List;

import org.json.JSONObject;

//获取用户常用意见的数据集
public class UserOptionsResult {

	private List<UserOption> items;
	
	public List<UserOption> getItems() {
		return items;
	}



	public void setItems(List<UserOption> items) {
		this.items = items;
	}



	public void parseJson(JSONObject json) throws Exception {
//		mUserID = json.optString("UserID");
//		mUserName = json.optString("UserName");
//		mOA_UserId = json.optString("OA_UserId");
//		mOA_UserName = json.optString("OA_UserName");
//		mOA_UnitId = json.optString("OA_UnitId");
//		mThirdDepartmentId = json.optString("ThirdDepartmentId");
//		mThirdDepartmentName = json.optString("ThirdDepartmentName");
//		mAttribute1 = json.optString("attribute1");
    }
	
	
	
	public class UserOption{
		private String id;
		
		private String value;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public void parseJson(JSONObject json) throws Exception {
			id = json.optString("id");
			value = json.optString("value");
	    }
	}
    
    
   

}
