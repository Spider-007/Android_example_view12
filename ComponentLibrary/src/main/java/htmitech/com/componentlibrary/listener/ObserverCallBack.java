package htmitech.com.componentlibrary.listener;

/**
 * 网络请求数据接口
 * @author Tony
 *
 */
public interface ObserverCallBack {
	public void success(String requestValue);
	public void fail(String exceptionMessage);
	public void notNetwork();
	public void callbackMainUI(String successMessage);
}
