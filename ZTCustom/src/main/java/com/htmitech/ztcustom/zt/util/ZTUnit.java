package com.htmitech.ztcustom.zt.util;


import com.htmitech.ztcustom.zt.domain.Dictitemlist;

import java.util.ArrayList;

public class ZTUnit {
	// public static ArrayList<ParameterList>
	// updateParameterList(ArrayList<ParameterList> mArray,String
	// name,ArrayList<Dicttype> value) {
	// for (ParameterList mParameterList : mArray) {
	// if (mParameterList.getName().equals(name)) {
	// mParameterList.getValues().addAll(value);
	// }
	// }
	// return mArray;
	// }
	// 获取sumitem_list的List String s = "";

	public static ArrayList<String> getSumitemValue(ArrayList<String> arrs) {
		ArrayList<String> arrList = new ArrayList<String>();
		int i = 0;
		StringBuffer sb = new StringBuffer();
		for (String c : arrs) {
			if (i == 0) {
				sb.append("[\"");
				sb.append(c);
				sb.append("\"");
			} else if (i == arrs.size() - 1) {
				sb.append(",\"");
				sb.append(c);
				sb.append("\"]");
			} else {
				sb.append(",\"");
				sb.append(c);
				sb.append("\"");
			}
			i++;
		}
		if(arrs.size() == 1){
			sb.append("]");
		}
		arrList.add(sb.toString());
		return arrList;
	}

	public static String getDictitemlistName(ArrayList<Dictitemlist> list,
			String code) {
		if(list == null){
			return "";
		}
		for (Dictitemlist mDictitemlist : list) {
			if (mDictitemlist.code.equals(code)) {
				return mDictitemlist.name;
			}
		}
		return "";
	}
}
