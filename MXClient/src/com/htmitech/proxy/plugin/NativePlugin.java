package com.htmitech.proxy.plugin;

import android.content.Context;

import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotFindException;

import java.util.Map;

/**
 * 加载自定义类并实现 ApplicationObserver 这个接口的 配置参数待定
 *
 * 提供寻找外部 ApplicationObserver
 *
 */
public class NativePlugin  implements ApplicationObserver {

    public static final String TAG = NativePlugin.class.getName();

    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters){
        try {
            this.customNative(context,mAppInfo,parameters);
        } catch (NotFindException e) {
            e.printStackTrace();
        }
    }


    private void customNative(Context context,AppInfo mAppInfo,Map<String,Object> parameters) throws NotFindException{

        Class cls = null;
        try {
//            cls = Class.forName("com.htmitech.proxy.plugin.ThirdPartyPlugin"); //指定路径，服务器配置的路径
            cls = Class.forName(mAppInfo.getmAppVersion().getPackage_name()+"."+mAppInfo.getmAppVersion().getPackage_main()); //指定路径，服务器配置的路径
            ApplicationObserver mApplicationObserver = (ApplicationObserver)cls.newInstance();
            parentValue(context, mAppInfo, parameters);
            mApplicationObserver.excetStart(context, mAppInfo, parameters);
        } catch (Exception e) {
            throw new NotFindException(TAG + "配置指定路径下的文件找不到！！！");
        }


    }

    //过滤所有原声插件 将原声插件的父节点找到并传递下去
    public void parentValue(Context context,AppInfo mAppInfo,Map<String,Object> parameters){
        AppliationCenterDao m = new AppliationCenterDao(context);
        AppInfo parentAppInfo = m.getAppInfo(mAppInfo.getParent_app_id() + "");
        if(parentAppInfo != null && parentAppInfo.getmAppVersion() != null) {
            for (AppVersionConfig mAppVersionConfig : parentAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                if(!parameters.containsKey(mAppVersionConfig.getConfig_code())){
                    parameters.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
                }
            }
        }
    }

}
