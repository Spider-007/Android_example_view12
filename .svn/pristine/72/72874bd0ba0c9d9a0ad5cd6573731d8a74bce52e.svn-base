package com.htmitech.proxy.plugin;

import android.content.Context;

import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.minxing.kit.api.MXAPI;

import java.util.Map;


/**
 * Created by heyang on 2016-11-21.
 */
public class FunctionPlugin implements ApplicationObserver {

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
//        Intent mIntent = new Intent(context, ContactOcuActivity.class);
//        context.startActivity(mIntent);
//        String functionId="d17b6ea882d74a73326a285b7e527f2d";//公众号ID  后续添加
        try{
            String functionId=mAppInfo.getmAppVersion().getFile_location();//表示功能号Id
            functionId = functionId.substring(functionId.lastIndexOf("/") + 1);
            MXAPI.getInstance(context).startOcuChat(functionId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
