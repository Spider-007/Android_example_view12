package com.htmitech.ztcustom.zt.enums;

import java.util.ArrayList;

/**
 * 选择维度的类型
 * @author Tony
 *
 */
public enum DicttypeEnum {

	SBLX("SBLX", "设备类型"),SSLX("SSLX", "伤损类型"), SSCD("SSCD", "伤损程度"), XJS("XJS", "新旧伤"), CZZT("CZZT", "处置状态");
//	, CLCS("CLCS", "处理措施")
	//, FXFS("FXFS","发现方式")
	private String code, tableName;

	private DicttypeEnum(String code, String tableName) {
		this.code = code;
		this.tableName = tableName;
	}
	/**
	 * 获取Name
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (DicttypeEnum c : DicttypeEnum.values()) {
			if (c.code.equals(code)) {
				return c.tableName;
			}
		}
		return null;
	}
	/**
	 * 获取Code
	 * @param name
	 * @return
	 */

	public static String getCode(String name) {
		for (DicttypeEnum c : DicttypeEnum.values()) {
			if (c.tableName.equals(name)) {
				return c.code;
			}
		}
		return name;
	}
	public static ArrayList<String> getCodes(){
		ArrayList<String> arrList = new ArrayList<String>();
		for(DicttypeEnum c : DicttypeEnum.values()){
			arrList.add(c.toString());
		}
		
		return arrList;
	}
	public static ArrayList<String> getNames(){
		ArrayList<String> arrList = new ArrayList<String>();
		for(DicttypeEnum c : DicttypeEnum.values()){
			arrList.add(c.tableName);
		}
		return arrList;
	}
}
