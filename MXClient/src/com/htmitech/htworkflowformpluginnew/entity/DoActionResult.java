package com.htmitech.htworkflowformpluginnew.entity;


public class DoActionResult {
	public boolean isExecuted;
	public boolean isMultiSelectRoute;
    public String resultCode;
    public String resultInfo;
	public RouteInfo[] listRouteInfo;

	public boolean isExcuted() {
		return isExecuted;
	}

	public void setIsExcuted(boolean isExcuted) {
		this.isExecuted = isExcuted;
	}

	public boolean isMultiSelectRoute() {
		return isMultiSelectRoute;
	}

	public void setIsMultiSelectRoute(boolean isMultiSelectRoute) {
		this.isMultiSelectRoute = isMultiSelectRoute;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public RouteInfo[] getListRouteInfo() {
		return listRouteInfo;
	}

	public void setListRouteInfo(RouteInfo[] listRouteInfo) {
		this.listRouteInfo = listRouteInfo;
	}
}
