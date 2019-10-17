package com.htmitech.ztcustom.zt.constant;

/**
 * 
 * @author heyang
 *
 * 车间日报解析javabean
 */
public class CheJianDayReportResultBean {
	private String cjid;
	private String jhh;
	private String gq_shortname;
	private String bz_shortname;
	private String sblx_name;
	private String line_typename;
	private String line_name;
	private String xb_name;
	private String start_milage;
	private String end_milage;
	private String ts_bs;
	private double ts_jhs;
	private double ts_aps;
	private double ts_wcs;

	public String getCjid() {
		return cjid;
	}

	public void setCjid(String cjid) {
		this.cjid = cjid;
	}

	public String getJhh() {
		return jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	public String getGq_shortname() {
		return gq_shortname;
	}

	public void setGq_shortname(String gq_shortname) {
		this.gq_shortname = gq_shortname;
	}

	public String getBz_shortname() {
		return bz_shortname;
	}

	public void setBz_shortname(String bz_shortname) {
		this.bz_shortname = bz_shortname;
	}

	public String getSblx_name() {
		return sblx_name;
	}

	public void setSblx_name(String sblx_name) {
		this.sblx_name = sblx_name;
	}

	public String getLine_typename() {
		return line_typename;
	}

	public void setLine_typename(String line_typename) {
		this.line_typename = line_typename;
	}

	public String getLine_name() {
		return line_name;
	}

	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}

	public String getXb_name() {
		return xb_name;
	}

	public void setXb_name(String xb_name) {
		this.xb_name = xb_name;
	}

	public String getStart_milage() {
		return start_milage;
	}

	public void setStart_milage(String start_milage) {
		this.start_milage = start_milage;
	}

	public String getEnd_milage() {
		return end_milage;
	}

	public void setEnd_milage(String end_milage) {
		this.end_milage = end_milage;
	}

	public String getTs_bs() {
		return ts_bs;
	}

	public void setTs_bs(String ts_bs) {
		this.ts_bs = ts_bs;
	}

	public double getTs_jhs() {
		return ts_jhs;
	}

	public void setTs_jhs(double ts_jhs) {
		this.ts_jhs = ts_jhs;
	}

	public double getTs_aps() {
		return ts_aps;
	}

	public void setTs_aps(double ts_aps) {
		this.ts_aps = ts_aps;
	}

	public double getTs_wcs() {
		return ts_wcs;
	}

	public void setTs_wcs(double ts_wcs) {
		this.ts_wcs = ts_wcs;
	}

	@Override
	public String toString() {
		return "CheJianDayReportResultBean [cjid=" + cjid + ", jhh=" + jhh
				+ ", gq_shortname=" + gq_shortname + ", bz_shortname="
				+ bz_shortname + ", sblx_name=" + sblx_name
				+ ", line_typename=" + line_typename + ", line_name="
				+ line_name + ", xb_name=" + xb_name + ", start_milage="
				+ start_milage + ", end_milage=" + end_milage + ", ts_bs="
				+ ts_bs + ", ts_jhs=" + ts_jhs + ", ts_aps=" + ts_aps
				+ ", ts_wcs=" + ts_wcs + "]";
	}
	

}
