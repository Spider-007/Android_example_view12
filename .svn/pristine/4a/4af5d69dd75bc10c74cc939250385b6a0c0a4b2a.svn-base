package com.htmitech.ztcustom.zt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FastJsonUtils {

	/**
	 * 将Json数据解析至JavaBean
	 * 
	 * @param data
	 *            Json数据
	 * @param clazz
	 *            类的字节码
	 * @return JavaBean
	 */
	public static <T> T getPerson(String jsonstring, Class<T> cls) {

		T t = null;
		try {
			t = JSON.parseObject(jsonstring, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <T> T getPerson(JSONObject jsonObject, Class<T> cls) {

		T t = null;
		try {
			t = JSON.parseObject(jsonObject.toString(), cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static <t> List<t> getPersonList(String jsonstring, Class<t> cls) {

		List<t> list = new ArrayList<t>();
		try {
			list = JSON.parseArray(jsonstring, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	public static <t> List<t> getPersonList(JSONArray jsonArray, Class<t> cls) {

		List<t> list = new ArrayList<t>();
		try {
			list = JSON.parseArray(jsonArray.toString(), cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

//	public static <T> ListModel<T> getListModel(JSONObject object, Class<T> cls)
//			throws JSONException {
//
//		ListModel<T> model = new ListModel<T>();
//		/** 此处加try catch 是因为报NullException */
//		try {
//			model.setNewInvestment(getPersonList(object.getJSONArray("rows"),
//					cls));
//			model.setPage(getPerson(object.getJSONObject("page"), FXDPage.class));
//			model.setTotal(object.getInt("total"));
//
//		} catch (Exception e) {
//		}
//		return model;
//	}

	public static <T> List<Map<String, Object>> getPersonListMap1(
			String jsonstring) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonstring,
					new TypeReference<List<Map<String, Object>>>() {
					}.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static String inputStream2String(InputStream in_st)
			throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(in_st));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}
