package com.htmitech.emportal.base;

import java.util.HashMap;

import com.htmitech.commonx.base.HttpUtils;
import com.htmitech.emportal.net.HttpRequestEntity;

/**
 * 对应URL
 * 
 * @author thinkleesion
 */
public class BaseTaskBody {

	public static final int GET_HTTP_REQUEST_TYPE = 0;

	public static final int POST_HTTP_REQUEST_TYPE = 1;

	public int TASK_REQUEST_TYPE = GET_HTTP_REQUEST_TYPE;

	protected HashMap<String, String> urlparams = new HashMap<String, String>();

	public HttpUtils httpUtils ;

	public int taskType;

	protected Object paramObject = null;
	public BaseTaskBody(){
		httpUtils = new HttpUtils();
	}

	public BaseTaskBody(String functionCode){
		httpUtils = new HttpUtils(functionCode,0);
	}
	/**
	 * 重载实现对应的缓存token 的sp 文件中的key值。
	 * 
	 * @return
	 */
	protected String getPreferenceKey() {
		return "";
	}

	/**
	 * Http 请求类型 get 还是 post
	 * 
	 * @return
	 */
	protected int getRequestType() {
		return TASK_REQUEST_TYPE;
	}

	/**
	 * 创建 http request entity
	 * 
	 * @return
	 */
	protected HttpRequestEntity buildRequestEntity() {
		return null;
	}

	/**
	 * 返回 缓存对象。如果不需要缓存直接返回null。
	 * 
	 * @return
	 */
	protected Object readCacheFromDisk() {
		return null;
	}

	/**
	 * 
	 * @param object
	 */
	protected void writeCache2Disk(Object object) {

	}

	protected synchronized Object parseJson(String result) {
		return null;
	}

	protected synchronized boolean hasDataEntity() {
		return false;
	}

	protected String getToken() {
		return "";
	}

	protected int getStatusCode() {
		return 0;
	}

	protected int getStatus() {
		return 0;
	}

	protected String getErrorMsg() {
		return "";
	}

	public void buildRequestParam(HashMap<String, String> temp) {
		if (urlparams == null) {
			urlparams = new HashMap<String, String>();
		} else {
			urlparams.clear();
		}
		if (temp != null)
			urlparams.putAll(temp);
	}

	public void buildRequestParam(Object paramObjectinput) {
		this.paramObject = paramObjectinput;
	}
	

	public HashMap<String, String> getURLParams() {
		return urlparams;
	}

	public boolean isSameType(BaseTaskBody task) {
		return task != null ? TASK_REQUEST_TYPE == task.TASK_REQUEST_TYPE
				&& urlparams.equals(task.getURLParams()) : false;
	}

	public void setFunctionCode(String functionCode){
		httpUtils.logfunctionCode = functionCode;
	}
}
