package com.htmitech.proxy.myenum;

import com.htmitech.proxy.plugin.ApplicationClassify;
import com.htmitech.proxy.plugin.ClentAppPlugin;
import com.htmitech.proxy.plugin.CustomDevelopmentPlugin;
import com.htmitech.proxy.plugin.FunctionPlugin;
import com.htmitech.proxy.plugin.GJBuildPlugin;
import com.htmitech.proxy.plugin.H5Plugin;
import com.htmitech.proxy.plugin.NativePlugin;
import com.htmitech.proxy.plugin.ThirdPartyPlugin;

import com.htmitech.proxy.plugin.WebPlugin;


/**
 * 配置插件
 */
public enum ApplicationEnum {

    //1表示工作流构建 0 表示基础构建
    JCGJ("0", ClentAppPlugin.class),

    GJ("1", GJBuildPlugin.class),//构建

    NATIVE("2", NativePlugin.class),//原声插件

    THIRDPARTY("4", ThirdPartyPlugin.class),//第三方插件

    H5("3", H5Plugin.class), //h5插件

    FUNCTION("6",FunctionPlugin.class),//功能号

    WEB("5", WebPlugin.class),//WEB插件

    YYFL("7", ApplicationClassify.class), //应用分类

    DZKF("8", CustomDevelopmentPlugin.class);//定制开发

    public String appId;

    public Class c;

    ApplicationEnum(String appId,Class<?> c){
        this.appId = appId;
        this.c = c;
    }
    public static  ApplicationEnum getApplicationEnum(String appId){
        for(ApplicationEnum a : ApplicationEnum.values()){
            if(a.appId.equals(appId)){
                return a;
            }
        }
        return null;
    }
    public static Class getAppIdApplicationObserver(String appId){
        for(ApplicationEnum a : ApplicationEnum.values()){
            if(a.appId.equals(appId)){
                return a.c;
            }
        }
        return null;
    }

}
