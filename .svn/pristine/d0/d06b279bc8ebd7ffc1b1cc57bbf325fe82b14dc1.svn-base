package com.htmitech.ztcustom.zt.domain.longin;

import org.json.JSONObject;

/**
 * 登录完成之后 请求权限 子业务系统等实体
 * @author htrf-pc
 *
 */
public class RequestEntity {
	public String SCode;
	public String CVersion;
	public String UserId;
	public String IsDev;
	public String UserName;
	
	public void parseJson(JSONObject json) throws Exception {
		SCode = json.optString("SCode");
		CVersion = json.optString("CVersion");
		UserId = json.optString("UserId");
		IsDev = json.optString("IsDev");
		UserName = json.optString("UserName");
    }
	
}
