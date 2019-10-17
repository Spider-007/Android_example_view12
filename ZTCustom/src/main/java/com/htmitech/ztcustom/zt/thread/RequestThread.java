package com.htmitech.ztcustom.zt.thread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.MyHttp;


public class RequestThread implements Runnable {
    public Context context;
    public Object entity;
    public String url;
    public int requestType;
    public ObserverCallBack mObserverCallBack;

    public RequestThread(Context context, Object entity, String url,
                         int requestType, ObserverCallBack mObserverCallBack) {
        this.context = context;
        this.entity = entity;
        this.url = url;
        this.requestType = requestType;
        this.mObserverCallBack = mObserverCallBack;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        switch (requestType) {
            case CHTTP.POST:
                Message msg = new Message();
                try {
                    String request = MyHttp.requestByHttpPost(entity, url);
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
            case CHTTP.GET:
                msg = new Message();
                try {
                    String request = MyHttp.requestByHttpGet(url);
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
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String o = (String) msg.obj;
            if (o.contains("timeout") || o.contains("操作超时")) {
                Toast.makeText(context, "网络请求超时，请重新访问！", Toast.LENGTH_LONG).show();
            }
            switch (msg.what) {
                case 1:
                    mObserverCallBack.success(o);
                    break;
                case 2:
                    mObserverCallBack.fail(o);
                    break;
                case 3:
                    break;
            }
        }
    };

}
