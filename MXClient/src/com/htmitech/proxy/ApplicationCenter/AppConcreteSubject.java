package com.htmitech.proxy.ApplicationCenter;

import android.content.Context;
import android.text.TextUtils;

import com.htmitech.app.Constant;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationSubject;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationEnum;
import com.htmitech.proxy.util.LogMesgUpUtil;

import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.api.ComponentInit;

/**
 * 分发
 */
public class AppConcreteSubject implements ApplicationSubject {

    private Map<ApplicationEnum,ApplicationObserver> observerHashMap = new HashMap<ApplicationEnum,ApplicationObserver>();

    private Context context;

    public AppConcreteSubject(Context context){
        this.context = context;
    }

    /**
     * 添加插件
     * @param appId
     * @param mApplicationSubject
     * @throws NotApplicationException
     */
    @Override
    public void addApplicationObserver(String appId,ApplicationObserver mApplicationSubject) throws NotApplicationException{
        observerHashMap.put(getApplicationEnum(appId), mApplicationSubject);
    }

    /**
     * 删除插件
     * @param mApplicationSubject
     */
    @Override
    public void deleteApplicationObserver(ApplicationObserver mApplicationSubject) {
        observerHashMap.remove(mApplicationSubject);
    }

    /**
     * 进行分发插件
     * @param mAppInfo
     * @throws NotApplicationException
     */
    @Override
    public void notifyApplicationObserver(AppInfo mAppInfo) throws NotApplicationException {
        ApplicationEnum mApplicationEnum = getApplicationEnum(mAppInfo.getApp_type()+"");
        ApplicationObserver mApplicationObserver = observerHashMap.get(mApplicationEnum);
        Map<String,Object> parameters = new HashMap<String,Object>();
        if(mAppInfo.getmAppVersion() != null) {
            parameters.put("url", mAppInfo.getmAppVersion().getFile_location());//web 路径
            parameters.put("app_version_id",mAppInfo.getmAppVersion().getApp_version_id() + "");
            for (AppVersionConfig mAppVersionConfig : mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                parameters.put(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());//是否支持功能号
//            parameters.put("isStart",mAppVersionConfig.getConfig_value()); //是否支持我发起
            }
        }
        parameters.put("addressFragmentType", Constant.HOME_INIT);
        parameters.put("app_id",mAppInfo.getApp_id()+ ""); //传入工作流构建需要的appId
        parameters.put("Type",true);
        parameters.put("appCode",mAppInfo.getApp_code());
        if(null != mAppInfo && !TextUtils.isEmpty(mAppInfo.getDisplay_title())){
            mAppInfo.setApp_name(mAppInfo.getDisplay_title());
        }else if(null != mAppInfo && !TextUtils.isEmpty(mAppInfo.getApp_shortnames())){
            mAppInfo.setApp_name(mAppInfo.getApp_shortnames());
        }
        parameters.put("appName",mAppInfo.getApp_name());
        parameters.put("appShortName",mAppInfo.getApp_shortname());
        mApplicationObserver.excetStart(context, mAppInfo, parameters);

        ComponentInit.getInstance().getmILogUpdateCallListener().logMessage(LogMesgUpUtil.getLogMsg(mAppInfo));

    }

    /**
     * 寻找对应的appType是否在规定类型中
     * @param appId
     * @return
     * @throws NotApplicationException
     */
    private ApplicationEnum getApplicationEnum(String appId) throws NotApplicationException{
        if(ApplicationEnum.getApplicationEnum(appId) == null){
            throw new NotApplicationException("枚举中没有发现对应的ApplicationEnum");
        }
        return ApplicationEnum.getApplicationEnum(appId);
    }
}
