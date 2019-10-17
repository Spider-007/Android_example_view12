package com.htmitech.ztcustom.zt.util;

import com.htmitech.ztcustom.zt.domain.ParameterList;

import java.util.ArrayList;

public class DamageUtil {
	public static String damageUtil(StringBuffer share, boolean isShare,
									ArrayList<ParameterList> array, ArrayList<String> sumitemList,
									String startTime, String code, String orgid, String dateend,
									String userid, String appUrl) {
		String userId = "";
		if (!isShare) {
			share.setLength(0);
			for (ParameterList mParameterList : array) {
				mParameterList.getValues().clear();
				if (mParameterList.getName().equals("sumitem_list")) {
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sumitemList));
					if(sumitemList.size() <= 5){
						for (int i = 0; i < sumitemList.size(); i++) {
							if (sumitemList.size() == 1) {
								share.append("" + sumitemList.get(i));
							} else if (i < sumitemList.size() - 1) {
								share.append(sumitemList.get(i) + ",");
							} else {
								share.append(sumitemList.get(i));
							}
						}
					}
					share.append("|");
				} else if (mParameterList.getName().equals("datebegin")) {
					mParameterList.getValues().add(startTime);
					share.append("" + startTime + "|");
				} else if (mParameterList.getName().equals("sumclass")) {
					mParameterList.getValues().add(code);
					share.append("" + code + "|");
				} else if (mParameterList.getName().equals("orgid")) {
					mParameterList.getValues().add(orgid);
					share.append("" + orgid + "|");
				} else if (mParameterList.getName().equals("dateend")) {
					mParameterList.getValues().add(dateend);
					share.append("" + dateend + "|");
				} else if (mParameterList.getName().equals("userid")) {
					mParameterList.getValues().add(userid);
					share.append("" + userid + "|");
				}
			}
		} else {
			String[] appUrlList = appUrl.split("\\|");
			int index = 0;
			for (ParameterList mParameterList : array) {
				mParameterList.getValues().clear();
				if (mParameterList.getName().equals("sumitem_list")) {
					sumitemList.clear();
					String[] ss = appUrlList[index].split(",");
					for (int i = 0; i < ss.length; i++) {
						sumitemList.add(ss[i]);
					}
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sumitemList));
				} else if (mParameterList.getName().equals("datebegin")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("sumclass")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("orgid")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("dateend")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("userid")) {
					mParameterList.getValues().add(appUrlList[index]);
					userId = appUrlList[index];
				}
				index++;
			}
		}
		return userId;
	}
}
