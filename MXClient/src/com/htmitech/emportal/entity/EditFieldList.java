package com.htmitech.emportal.entity;

import java.util.ArrayList;

public class EditFieldList {
	
    private ArrayList<EditField> list = null;
	public ArrayList<EditField> getList() {
		return list;
	}

	public void setList(ArrayList<EditField> list) {
		this.list = list;
	}


	private static EditFieldList mustFieldList = null;
	public static EditFieldList getInstance(){
		if(null == mustFieldList){
			mustFieldList = new EditFieldList();
			mustFieldList.setList(new ArrayList<EditField>());
    	}
    	return mustFieldList ;
	}

	public void Clear(){
		list.clear();
	}

	
    
}
