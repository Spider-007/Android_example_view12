package htmitech.com.componentlibrary.listener;

/**
 * Created by heyang on 2016-12-12.
 */

public interface ObserverCallBackType {
	
    public void success(String requestValue, int type, String requestName);

    public void fail(String exceptionMessage, int type, String requestName);

    public void notNetwork();

    public void callbackMainUI(String successMessage);
}


