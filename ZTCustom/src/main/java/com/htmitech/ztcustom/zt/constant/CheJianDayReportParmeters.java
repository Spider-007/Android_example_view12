package com.htmitech.ztcustom.zt.constant;

import java.util.List;

/**
 * 
 * @author heyang
 *	车间日报请求参数javabean
 */
public class CheJianDayReportParmeters {

	private String userid;
	private String workday;
	private String cjid;
	private String sblx;
	private String search;
	private List<String> wzlx;
	private List<String> sblxmx;

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

	public String getCjid() {
		return cjid;
	}

	public void setCjid(String cjid) {
		this.cjid = cjid;
	}

	public String getSblx() {
		return sblx;
	}

	public void setSblx(String sblx) {
		this.sblx = sblx;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<String> getWzlx() {
		return wzlx;
	}

	public void setWzlx(List<String> wzlx) {
		this.wzlx = wzlx;
	}

	public List<String> getSblxmx() {
		return sblxmx;
	}

	public void setSblxmx(List<String> sblxmx) {
		this.sblxmx = sblxmx;
	}

}
