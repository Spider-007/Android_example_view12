
package com.htmitech.emportal.ui.plugin.zt.netcommon;

import com.alibaba.fastjson.JSON;
import com.htmitech.emportal.ui.plugin.zt.entity.Doc;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private ArrayList<Doc> array;

	
	public void parseJson(JSONArray jsonarry) throws Exception {
		//
		List list = new ArrayList();
		list = JSON.parseArray(jsonarry.toString(), Doc.class);
//		for (int i = 0; i < jsonarry.length(); ++i) {  
//            JSONObject o = (JSONObject) jsonarry.get(i);  
//            System.out.println("name:" + o.getString("name") + "," + "age:"  
//                    + o.getInt("age"));  
//        }  
    }
	
	
	
    
    
   

}
