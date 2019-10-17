package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

//暂时不用……
public class AppInfo {
	private String appID;
	private String avatarUrl;
	private int id;
	private String name;
	
	public void parseJson(String json) throws Exception {
		AppInfo entity = JSON.parseObject(json, AppInfo.class);
		this.appID = entity.appID;
		this.avatarUrl = entity.avatarUrl;
		this.id = entity.id;
		this.name = entity.name;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
