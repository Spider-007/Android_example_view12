package com.htmitech.htworkflowformpluginnew.entity;


import java.io.Serializable;
import java.util.List;

import htmitech.com.componentlibrary.entity.AuthorInfo;

public class RouteInfo  implements Serializable{
	public String routeID;
	public String routeName="";
	public List<AuthorInfo> routeAuthors;
	public boolean isAllowSelectUser = true;
	public boolean isMultiSelectUser;
	public boolean isFreeSelectUser;

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public List<AuthorInfo> getRouteAuthors() {
		return routeAuthors;
	}

	public void setRouteAuthors(List<AuthorInfo> routeAuthors) {
		this.routeAuthors = routeAuthors;
	}

	public boolean isAllowSelectUser() {
		return isAllowSelectUser;
	}

	public void setIsAllowSelectUser(boolean isAllowSelectUser) {
		this.isAllowSelectUser = isAllowSelectUser;
	}

	public boolean isMultiSelectUser() {
		return isMultiSelectUser;
	}

	public void setIsMultiSelectUser(boolean isMultiSelectUser) {
		this.isMultiSelectUser = isMultiSelectUser;
	}

	public boolean isFreeSelectUser() {
		return isFreeSelectUser;
	}

	public void setIsFreeSelectUser(boolean isFreeSelectUser) {
		this.isFreeSelectUser = isFreeSelectUser;
	}
}
