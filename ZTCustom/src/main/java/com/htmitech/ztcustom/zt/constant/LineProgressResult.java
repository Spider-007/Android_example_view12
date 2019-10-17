package com.htmitech.ztcustom.zt.constant;

import java.util.List;

public class LineProgressResult {
	private LineYobj Yobj;
	private  List<LineDataItems> DataItems;
	public LineYobj getYobj() {
		return Yobj;
	}
	public void setYobj(LineYobj yobj) {
		Yobj = yobj;
	}
	public List<LineDataItems> getDataItems() {
		return DataItems;
	}
	public void setDataItems(List<LineDataItems> dataItems) {
		DataItems = dataItems;
	}
	
}
