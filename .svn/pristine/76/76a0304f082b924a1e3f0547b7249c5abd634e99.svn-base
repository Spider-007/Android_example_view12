package htmitech.com.componentlibrary.content;

import htmitech.com.componentlibrary.listener.ICallLogin;
import htmitech.com.componentlibrary.listener.ICallLoginMXListener;
import htmitech.com.componentlibrary.listener.ICallMXInit;

/**
 * Created by Administrator on 2018/4/26.
 */

public class LoginFactory {

    /**
     * 此处的type 是为是否为emi  如果是1 则不是emi  如果是 0 则是emi 默认是有emi的
     *
     * @param type
     */
    public ICallLogin createLogin(int type, ICallLoginMXListener mxListener, ICallMXInit iCallMXInit) {
        ICallLogin mICallLogin = null;

        if (type == 1) {

            mICallLogin = new NotEMILogin();

        } else {

            mICallLogin = new EMILogin();

        }

        mICallLogin.setICallLoginMXListener(mxListener);
        mICallLogin.setICallMXInit(iCallMXInit);

        return mICallLogin;

    }/**
     * 此处的type 是为是否为emi  如果是1 则不是emi  如果是 0 则是emi 默认是有emi的
     *
     * @param type
     */
    public ICallLogin createLogin(int type, ICallLoginMXListener mxListener) {
        ICallLogin mICallLogin = null;

        if (type == 1) {

            mICallLogin = new NotEMILogin();

        } else {

            mICallLogin = new EMILogin();

        }

        mICallLogin.setICallLoginMXListener(mxListener);

        return mICallLogin;

    }
}
