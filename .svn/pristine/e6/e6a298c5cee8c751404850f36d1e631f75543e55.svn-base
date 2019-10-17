package com.htmitech.emportal.receive;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;

/**
 * Created by sunxl on 2017-1-18.
 */
public class HomeKeyEventReceive extends BroadcastReceiver implements ObserverCallBackType {

    private INetWorkManager netWorkManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            if ("homekey".equals(intent.getStringExtra("reason"))) {
                Log.d("HomeKeyEventReceive", "执行了");
                HtmitechApplication.isHomeBack=true;
                if(AlertDialog.dialogList != null){
                    for (int i = 0; i < AlertDialog.dialogList.size(); i++){
                        Dialog dialog = AlertDialog.dialogList.get(i);
                        if(dialog != null && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                }
                //启动 和完成和唤醒
                netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
                netWorkManager.logFunactionOnce(context, this, "login_home", LogManagerEnum.APP_PAUSE_TOBACK.functionCode);
            }
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        Log.d("HOMEKEYDOWN","退到后台成功");
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Log.d("HOMEKEYDOWN","退到后台失败");
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
