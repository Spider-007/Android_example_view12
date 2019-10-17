package com.htmitech.ztcustom.zt.interfaces;

import java.util.List;

public interface DayReportCallBackListener {
	public void getData(List<String> list_xb, List<String> list_lx, String jhh, String rb_text);
	public void cancle();
}
