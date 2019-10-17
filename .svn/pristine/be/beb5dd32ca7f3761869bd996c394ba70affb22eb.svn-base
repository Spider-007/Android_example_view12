package com.htmitech.emportal.entity;

import com.alibaba.fastjson.JSON;

public class PageResult {
	Banner[] BannerItems;
	int ColumnCount;
	String BackGroundImageURL;
	Metro[][] MetroItems;
	
	
	public void parseJson(String json) throws Exception {
		PageResult entity = JSON.parseObject(json, PageResult.class);
		this.BannerItems = entity.BannerItems;
		this.ColumnCount = entity.ColumnCount;
		this.BackGroundImageURL = entity.BackGroundImageURL;
		this.MetroItems = entity.MetroItems;
	}
	
	public Banner[] getBannerItems() {
		return BannerItems;
	}
	
	public void setBannerItems(Banner[] bannerItems) {
		BannerItems = bannerItems;
	}
	
	public int getColumnCount() {
		return ColumnCount;
	}
	
	public void setColumnCount(int columnCount) {
		ColumnCount = columnCount;
	}
	
	public String getBackGroundImageURL() {
		return BackGroundImageURL;
	}
	
	public void setBackGroundImageURL(String backGroundImageURL) {
		BackGroundImageURL = backGroundImageURL;
	}
	
	public Metro[][] getMetroItems() {
		return MetroItems;
	}
	
	public void setMetroItems(Metro[][] metroItems) {
		MetroItems = metroItems;
	}

}
