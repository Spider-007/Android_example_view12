package com.htmitech.emportal.entity;


import android.text.TextUtils;

import java.util.Arrays;

public class DoActionResult  {
	boolean IsExcuted;
	boolean IsMultiSelectRoute;
	boolean IsMultiSelectUser;
	
	boolean IsFreeSelectUser; 
    String ResultCode;
    String ResultInfo;
    RouteInfo[] ListRouteInfo;
    AuthorInfo[] ListAuthorInfo;
    
    RouteInfo HasSelectedRoute;  //add by gulbel 2015-08-20
    
    public RouteInfo getHasSelectedRoute() {
		return HasSelectedRoute;
	}
	public void setHasSelectedRoute(RouteInfo hasSelectedRoute) {
		HasSelectedRoute = hasSelectedRoute;
	}
	public boolean isIsFreeSelectUser() {
		return IsFreeSelectUser;
	}
	public void setIsFreeSelectUser(boolean isFreeSelectUser) {
		IsFreeSelectUser = isFreeSelectUser;
	}
    
	public boolean isIsExcuted() {
		return IsExcuted;
	}
	public void setIsExcuted(boolean isExcuted) {
		IsExcuted = isExcuted;
	}
	public boolean isIsMultiSelectRoute() {
		return IsMultiSelectRoute;
	}
	public void setIsMultiSelectRoute(boolean isMultiSelectRoute) {
		IsMultiSelectRoute = isMultiSelectRoute;
	}
	public boolean isIsMultiSelectUser() {
		return IsMultiSelectUser;
	}
	public void setIsMultiSelectUser(boolean isMultiSelectUser) {
		IsMultiSelectUser = isMultiSelectUser;
	}
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public String getResultInfo() {
		if(TextUtils.isEmpty(ResultInfo)){
			ResultInfo = "";
		}
		return ResultInfo;
	}
	public void setResultInfo(String resultInfo) {
		ResultInfo = resultInfo;
	}
	public RouteInfo[] getListRouteInfo() {
		return ListRouteInfo;
	}
	public void setListRouteInfo(RouteInfo[] listRouteInfo) {
		ListRouteInfo = listRouteInfo;
	}
	public AuthorInfo[] getListAuthorInfo() {
		return ListAuthorInfo;
	}
	public void setListAuthorInfo(AuthorInfo[] listAuthorInfo) {
		ListAuthorInfo = listAuthorInfo;
	}

	@Override
	public String toString() {
		return "DoActionResult{" +
				"IsExcuted=" + IsExcuted +
				", IsMultiSelectRoute=" + IsMultiSelectRoute +
				", IsMultiSelectUser=" + IsMultiSelectUser +
				", IsFreeSelectUser=" + IsFreeSelectUser +
				", ResultCode='" + ResultCode + '\'' +
				", ResultInfo='" + ResultInfo + '\'' +
				", ListRouteInfo=" + Arrays.toString(ListRouteInfo) +
				", ListAuthorInfo=" + Arrays.toString(ListAuthorInfo) +
				", HasSelectedRoute=" + HasSelectedRoute +
				'}';
	}
}
