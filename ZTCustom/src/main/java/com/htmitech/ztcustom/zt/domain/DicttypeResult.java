package com.htmitech.ztcustom.zt.domain;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * 	IB04_GetDictTypeObject-得到单个字典类型的数据列表
 */
public class DicttypeResult implements Serializable{
	public boolean success;
	public String msg;
	public String class_code;
	public String class_name;
	public ArrayList<Dictitemlist> dictitemlist = new ArrayList<Dictitemlist>();
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getClass_code() {
		return class_code;
	}
	public void setClass_code(String class_code) {
		this.class_code = class_code;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public ArrayList<Dictitemlist> getDictitemlist() {
		return dictitemlist;
	}
	public void setDictitemlist(ArrayList<Dictitemlist> dictitemlist) {
		this.dictitemlist = dictitemlist;
	}

	@Override
	public String toString() {
		return "DicttypeResult{" +
				"success=" + success +
				", msg='" + msg + '\'' +
				", class_code='" + class_code + '\'' +
				", class_name='" + class_name + '\'' +
				", dictitemlist=" + dictitemlist +
				'}';
	}
}
