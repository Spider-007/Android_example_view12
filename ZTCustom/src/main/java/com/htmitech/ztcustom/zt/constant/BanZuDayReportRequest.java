package com.htmitech.ztcustom.zt.constant;

import java.io.Serializable;
import java.util.List;

public class BanZuDayReportRequest implements Serializable {
	private String userid;
	private String workday;
	private String gq_orgid;
	private List<String> wzlx;
	private String search;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWorkday() {
		return workday;
	}

	public void setWorkday(String workday) {
		this.workday = workday;
	}

	public String getGq_orgid() {
		return gq_orgid;
	}

	public void setGq_orgid(String gq_orgid) {
		this.gq_orgid = gq_orgid;
	}

	public List<String> getWzlx() {
		return wzlx;
	}

	public void setWzlx(List<String> wzlx) {
		this.wzlx = wzlx;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
