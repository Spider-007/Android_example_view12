package com.htmitech.ztcustom.zt.interfaces;

/**
 * 网络请求数据接口
 * @author Tony
 *
 */
public interface ObserverCallBack {
	public void success(String successMessage);
	public void fail(String exceptionMessage);
	public void notNetwork();
}
