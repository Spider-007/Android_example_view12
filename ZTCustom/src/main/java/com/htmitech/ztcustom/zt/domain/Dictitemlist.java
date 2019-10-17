package com.htmitech.ztcustom.zt.domain;

import java.io.Serializable;

public class Dictitemlist implements Serializable{
	public String code;
	public String name;
	public String getCode() {
		return code;
	}
	public void setCode(String item_code) {
		this.code = item_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String item_name) {
		this.name = item_name;
	}

	@Override
	public String toString() {
		return "Dictitemlist{" +
				"code='" + code + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
