package com.htmitech.proxy.plugin;

import android.content.Context;

import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.util.ClassLoaderUtil;
import java.net.URL;
import java.util.Map;


/**
 * 基于热更新部署
 */
public class SDCardPlugin implements ApplicationObserver {

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters){
        try {
            ClassLoaderUtil c1 = new ClassLoaderUtil( new URL[]{ new URL( "sdcard/fanxin/")} , SDCardPlugin.class.getClassLoader());
            Class clazz = c1.load("ThirdPartyPlugin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
