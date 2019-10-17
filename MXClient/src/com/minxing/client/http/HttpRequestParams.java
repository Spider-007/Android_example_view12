package com.minxing.client.http;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("deprecation")
public class HttpRequestParams {
	private HttpMethod requestType;			//访问主机的方法GET/POST/DELETE
	private Interface wbinterface;			//访问主机的接口
	private TreeMap<String, String> headers;	//访问主机携带的header数据
	private List<NameValuePair> params;			//访问接口携带的参数
	public String jsonString;

	public HttpMethod getRequestType() {
		return requestType;
	}

	public void setRequestType(HttpMethod requestType) {
		this.requestType = requestType;
	}

	public Interface getWbinterface() {
		return wbinterface;
	}

	public void setWbinterface(Interface wbinterface) {
		this.wbinterface = wbinterface;
	}

	public TreeMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(TreeMap<String, String> headers) {
		this.headers = headers;
	}

	public List<NameValuePair> getParams() {
		return params;
	}

	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	public void setJsonParams(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonParams() {
		return jsonString;
	}
}
