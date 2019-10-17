package com.htmitech.proxy.plugin;

import android.content.Context;

import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import java.util.Map;

/**
 * 基础应用的跳转  后续添加
 */
public class ClentAppPlugin  implements ApplicationObserver {
    @Override
    public void excetStart(Context context, AppInfo mAppInfo, Map<String, Object> parameters) {
        try {
//            ClentAppUnit.getInstance(context).setActivity(ApplicationAllEnum.getAppIdToEnum(mAppInfo));
            ClentAppUnit.getInstance(context).setContext(context).setActivitys(ApplicationAllEnum.getAppIdToEnum(mAppInfo), parameters);

        } catch (NotApplicationException e) {
            e.printStackTrace();
        }
    }
}
