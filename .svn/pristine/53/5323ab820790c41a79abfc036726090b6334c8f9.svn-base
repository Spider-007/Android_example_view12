package com.htmitech.proxy.plugin;

import android.app.Activity;
import android.content.Context;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.activity.H5UniversalActivity;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotFindException;
import com.htmitech.proxy.util.ClassUtil;
import com.htmitech.proxy.util.ZTActivityUnit;

import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * H5 构件   添加一个敏形社区H5插件
 */
public class H5Plugin implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        //如果是h5发起构件 那么将进入发起
        if(mAppInfo.getComp_code().equals("com_workflow")){
            try {
                customH5(context,mAppInfo,parameters);
            } catch (NotFindException e) {
                e.printStackTrace();
            }
            //TODO ???? 拿到热度榜的comp_code
        }else if(mAppInfo.getApp_code().equals("SNS_redubang")){
            String h5destination =  CommonSettings.DEFAULT_FOLDER + "/appstore/plugin/apps/HotView/100020/www/index.html";
            parameters.put("localFile",h5destination);
//            MXUIEngine.getInstance().getAppCenterManager().refreshAppByID(context,"100020");
            h5Universal(context,mAppInfo,parameters);
        }else{
            mAppInfo.user_name = BookInit.getInstance().getCurrentUserName();
            mAppInfo.login_name = BookInit.getInstance().getCurrentUser().getLogin_name();
            mAppInfo.group_corp_id = PreferenceUtils.getGroup_corp_id(context);
            mAppInfo.user_id = PreferenceUtils.getEMPUserID(context);
            ClassUtil.appInfo = mAppInfo;
            h5Universal(context,mAppInfo,parameters);
        }
    }

    private void customH5(Context context,AppInfo mAppInfo,Map<String,Object> parameters) throws NotFindException {

        Class cls = null;
        try {
//            cls = Class.forName("com.htmitech.proxy.plugin.ThirdPartyPlugin"); //指定路径，服务器配置的路径
            cls = Class.forName(mAppInfo.getmAppVersion().getPackage_name()+"."+mAppInfo.getmAppVersion().getPackage_main()); //指定路径，服务器配置的路径
            ApplicationObserver mApplicationObserver = (ApplicationObserver)cls.newInstance();
            mApplicationObserver.excetStart(context, mAppInfo, parameters);
        } catch (Exception e) {
            throw new NotFindException("配置指定路径下的文件找不到！！！");
        }
    }

    /**
     * h5通用的入口
     * @param context
     * @param mAppInfo
     * @param parameters
     */
    public void h5Universal(Context context,AppInfo mAppInfo,Map<String,Object> parameters){

        ZTActivityUnit.switchTo((Activity) context, H5UniversalActivity.class, parameters);
    }
}
