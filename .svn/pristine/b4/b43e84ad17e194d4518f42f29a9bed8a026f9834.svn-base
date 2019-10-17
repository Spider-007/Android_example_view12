package com.htmitech.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.htmitech.entity.LogFunctionResult;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.utils.FastJsonUtils;

import java.lang.ref.WeakReference;

import htmitech.com.componentlibrary.listener.ObserverCallBack;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


public class RequestThread implements Runnable {
    public Object entity;
    public String url;
    public int requestType;
    public ObserverCallBack mObserverCallBack;
    public ObserverCallBackType mObserverCallBackType;
    public String requestName;
    public String funactionCode = "";
    private int typs;
    public WeakReference<Context> weakReference;
    public RequestThread(Context context, Object entity, String url,
                         int requestType, ObserverCallBack mObserverCallBack) {
        weakReference = new WeakReference<Context>(context);
        this.entity = entity;
        this.url = url;
        this.requestType = requestType;
        this.mObserverCallBack = mObserverCallBack;
    }

    public RequestThread(Context context, Object entity, String url,
                         int requestType, ObserverCallBack mObserverCallBack, String funactionCode) {
        weakReference = new WeakReference<Context>(context);
        this.entity = entity;
        this.url = url;
        this.requestType = requestType;
        this.mObserverCallBack = mObserverCallBack;
        this.funactionCode = funactionCode;
    }

    public RequestThread(Context context, Object entity, String url,
                         int requestType, ObserverCallBackType mObserverCallBackType, String requestName) {
        weakReference = new WeakReference<Context>(context);
        this.entity = entity;
        this.url = url;
        this.requestType = requestType;
        this.mObserverCallBackType = mObserverCallBackType;
        this.requestName = requestName;
    }

    public RequestThread(Context context, Object entity, String url,
                         int requestType, ObserverCallBackType mObserverCallBackType, String requestName, String funactionCode) {
        weakReference = new WeakReference<Context>(context);
        this.entity = entity;
        this.url = url;
        this.requestType = requestType;
        this.mObserverCallBackType = mObserverCallBackType;
        this.requestName = requestName;
        this.funactionCode = funactionCode;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        switch (requestType) {
            case CHTTP.POST:
                Message msg = new Message();
                try {
                    String request = MyHttp.requestByHttpPost(weakReference.get(),entity, url, funactionCode);
                    msg.what = 1;
                    msg.obj = request;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    msg.what = 2;
                    msg.obj = e.getMessage();
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
                break;
            case CHTTP.POST_LOG:
                msg = new Message();
                typs = interceptLog() ? 1 : 0;

                try {
                    String request = MyHttp.requestByHttpPost(weakReference.get(),entity, url, funactionCode);
                    msg.what = 3;
                    msg.obj = request;
                    int type;
                    if (url.equals(ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REFRESH_TOKEN)) {
                        type = 1;//刷新Token返回的类型type
                    } else {
                        type = 2;//其他网络请求返回的type
                    }
                    msg.arg1 = type;

                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    msg.what = 4;
                    msg.obj = e.getMessage();
                    int type;
                    if (url.equals(ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REFRESH_TOKEN)) {
                        type = 1;//刷新Token返回的类型type
                    } else {
                        type = 2;//其他网络请求返回的type
                    }
                    msg.arg1 = type;
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }

                break;
            case CHTTP.GET:
                Message msgs = new Message();
                try {
                    String request = MyHttp.requestGet(url);
                    msgs.what = 1;
                    msgs.obj = request;
                    mHandler.sendMessage(msgs);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    msgs.what = 2;
                    msgs.obj = e.getMessage();
                    mHandler.sendMessage(msgs);
                    e.printStackTrace();
                }

                break;
            case CHTTP.POSTWITHHEADER:
                Message msgh = new Message();
                try {
                    String request = MyHttp.requestByHttpPostWithHeader(weakReference.get(),entity, url, funactionCode);
                    msgh.what = 1;
                    msgh.obj = request;
                    mHandler.sendMessage(msgh);
                } catch (Exception e) {
                    msgh.what = 2;
                    msgh.obj = e.getMessage();
                    mHandler.sendMessage(msgh);
                    e.printStackTrace();
                }
                break;
            case CHTTP.POSTWITHTOKEN:
                Message msgt = new Message();
                try {
                    String request = MyHttp.requestByHttpPostWithToken(weakReference.get(),entity, url, funactionCode);
                    msgt.what = 3;
                    msgt.obj = request;
                    int type;
                    if (url.equals(ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REFRESH_TOKEN)) {
                        type = 1;//刷新Token返回的类型type
                    } else {
                        type = 2;//其他网络请求返回的type
                    }
                    msgt.arg1 = type;
                    mHandler.sendMessage(msgt);
                } catch (Exception e) {
                    msgt.what = 4;
                    msgt.obj = e.getMessage();
                    int type;
                    if (url.equals(ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REFRESH_TOKEN)) {
                        type = 1;//刷新Token返回的类型type
                    } else {
                        type = 2;//其他网络请求返回的type
                    }
                    msgt.arg1 = type;
                    mHandler.sendMessage(msgt);
                    e.printStackTrace();
                }
                break;

            case CHTTP.POSTWITHTOKEN_UPLOAD:
                msgh = new Message();
                try {
                    String request = MyHttp.requestByHttpPostWithTokenUpLoad(weakReference.get(),entity, url, funactionCode);
                    msgh.what = 3;
                    msgh.obj = request;
                    mHandler.sendMessage(msgh);
                } catch (Exception e) {
                    msgh.what = 4;
                    msgh.obj = e.getMessage();
                    mHandler.sendMessage(msgh);
                    e.printStackTrace();
                }
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String o = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    mObserverCallBack.success(o);
                    break;
                case 2:
                    mObserverCallBack.fail(o);
                    break;
                case 3:
                    LogManagerEnum mLogManagerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
                    /**
                     * 这里是排除不是Log请求的一切
                     */
                    //--------------解决第二次点击登录时依然带着第一次的functionlogid，未清除上次信息------------------------
                    if (mLogManagerEnum != null && typs != 0) {
                        mLogManagerEnum.functionId = "0";
                    }
                    //-------------------------------------------------------------------------------------------------------
                    if (mLogManagerEnum != null && mLogManagerEnum.functionId != null && mLogManagerEnum.functionId.equals("0")) {
                        LogFunctionResult logFunctionResult = FastJsonUtils.getPerson(o, LogFunctionResult.class);
                        String Result = "0";
                        if (logFunctionResult != null && logFunctionResult.result != null) {
                            if (logFunctionResult.code == 200)
                                Result = logFunctionResult.result.functionLogId +"";
                        }
                        if (Result == null) {
                            Result = "0";
                        }
                        mLogManagerEnum.functionId = Result;
                    }
                    mObserverCallBackType.success(o, msg.arg1, requestName);
                    break;
                case 4:
                    mObserverCallBackType.fail(o, msg.arg1, requestName);
                    break;
            }
        }

        ;
    };

    /**
     * 拦截  如果是funactionStart请求 或者是funactionFinsh请求的话 就直接放过
     *
     * @return
     */
    public boolean interceptLog() {
        if (url.contains("LogFunctionStart")) {
            return true;
        }
        return false;
    }

}
