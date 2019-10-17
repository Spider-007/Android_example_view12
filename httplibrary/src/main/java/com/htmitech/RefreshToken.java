package com.htmitech;

import android.content.Context;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * Created by heyang on 2016-12-9.
 * <p/>
 * 刷新Token 更新缓存里面的token值
 */
public class RefreshToken {

    public static void RefreshAccessToken(Context context, ObserverCallBackType mObserverCallBackType, String requestName) {
//        String refreshToken = PreferenceUtils.getRefreshToken();
//        String accessToken = PreferenceUtils.getAccessToken();
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REFRESH_TOKEN;
        AnsynHttpRequest.requestByPostWithToken(context.getApplicationContext(), null, url, CHTTP.POSTWITHTOKEN, mObserverCallBackType, requestName, "");
    }

}
