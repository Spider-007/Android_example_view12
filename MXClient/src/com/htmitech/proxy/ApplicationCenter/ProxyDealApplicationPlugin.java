package com.htmitech.proxy.ApplicationCenter;

import android.content.Context;

import com.htmitech.proxy.ApplicationCenterImp.ApplicationCenterProxyImp;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;

/**
 * 代理的入口
 */
public class ProxyDealApplicationPlugin implements ApplicationCenterProxyImp {



    private PrivateDealApplication privateDealApplication;

    private Context context;

    public ProxyDealApplicationPlugin(Context context){
        this.context = context;
        privateDealApplication = new PrivateDealApplication(context);
    }


    /**
     * 应用中心点击入口
     * @param mAppInfo
     * @return
     * @throws NotApplicationException
     */

    @Override
    public int applicationCenterProxy(AppInfo mAppInfo) throws NotApplicationException {

        return privateDealApplication.applicationCenterProxy(mAppInfo);
    }

    /**
     * 拦截apk已经卸载和本地已经删除的应用图片的展示
     * @param mAppInfo
     */
    public boolean interceptAPK(AppInfo mAppInfo){
        return privateDealApplication.interceptAPK(mAppInfo);
    }

}
