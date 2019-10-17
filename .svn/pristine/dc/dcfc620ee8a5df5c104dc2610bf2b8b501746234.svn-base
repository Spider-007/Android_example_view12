package proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by htrf-pc on 2017/1/10.
 */
public class LogManagerProxy implements InvocationHandler {

    private static HashMap<Class<?>, LogManagerProxy> invoHandlers = new HashMap<Class<?>, LogManagerProxy>();
    private Object target; // 代理目标
    private Object proxy; // 代理对象

    private LogManagerProxy() {
    }

    public synchronized static <T> T getProxyInstance(Class<T> clazz) {
        LogManagerProxy invoHandler = invoHandlers.get(clazz);

        if (null == invoHandler) {
            invoHandler = new LogManagerProxy();
            try {
                T tar = clazz.newInstance();
                invoHandler.setTarget(tar);
                invoHandler.setProxy(Proxy.newProxyInstance(tar.getClass().getClassLoader(),
                        tar.getClass().getInterfaces(), invoHandler));
            } catch (Exception e) {
                e.printStackTrace();
            }
            invoHandlers.put(clazz, invoHandler);

        }

        return (T) invoHandler.getProxy();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        String logFuncationCode = (String) args[args.length - 1];
//        LogManagerEnum mLogManagerEnum = LogManagerEnum.getLogManagerEnum(logFuncationCode);
//        if(mLogManagerEnum != null){
//            String funcationId = mLogManagerEnum.getFunctionId();
//            if(funcationId == null || funcationId.equals("")){
//                NetWorkManager mNetWorkManager = (NetWorkManager) target;
//                mNetWorkManager.logFunactionFinsh();
//            }
//        }
//
        try {
            return method.invoke(target, args); // 执行业务处理
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
