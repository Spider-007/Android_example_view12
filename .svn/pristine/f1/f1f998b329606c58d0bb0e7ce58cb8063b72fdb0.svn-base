package com.htmitech.htexceptionmanage.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.htmitech.htexceptionmanage.entity.AlertCountEntity;
import com.htmitech.htexceptionmanage.entity.AlertCountInfo;
import com.htmitech.htexceptionmanage.entity.ManageExceptionparameter;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.myenum.LogManagerEnum;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 消息提醒角标
 * @author  joe
 * @data 2017-9-28 18:06:55
 */
public class ExceptionAngleUtils implements ObserverCallBackType {

    private Context context;
    private ManageExceptionparameter manageExceptionparameter;
    private IexceptionAlertItem iexceptionAlertItem;
    private String alertCountUrl;
    private static  final  String ALERTCOUNT = "alertcount";
    private AlertCountEntity alertCountEntity;
    private Gson mGson = new Gson();
    private AlertCountInfo alertCountInfo;

    public ExceptionAngleUtils(Context context, ManageExceptionparameter manageExceptionparameter, IexceptionAlertItem iexceptionAlertItem) {
        this.context = context;
        this.manageExceptionparameter = manageExceptionparameter;
        this.iexceptionAlertItem = iexceptionAlertItem;
        initData();
    }

    private void initData() {
        alertCountUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.MANAGE_EXCEPTION_ALERTCOUNT;
        AnsynHttpRequest.requestByPostWithToken(context, manageExceptionparameter, alertCountUrl, CHTTP.POSTWITHTOKEN, this, ALERTCOUNT, LogManagerEnum.GGENERAL.getFunctionCode());
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if(ALERTCOUNT.equals(requestName)){
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context,requestValue, type, alertCountUrl, manageExceptionparameter, this, requestName, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                try {
                    alertCountEntity = mGson.fromJson(requestValue.toString(), AlertCountEntity.class);
                    alertCountInfo = alertCountEntity.getResult().getAlertCount();
                    if(alertCountEntity!=null&&alertCountInfo!=null&&alertCountInfo.getNoDealCount()!=null&&Integer.parseInt(alertCountInfo.getNoDealCount())>0){
                        iexceptionAlertItem.AlertItemClick(alertCountInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public interface IexceptionAlertItem{
        void AlertItemClick(AlertCountInfo alertCountInfo);
    }
}
