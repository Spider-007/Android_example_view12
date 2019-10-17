package com.htmitech.proxy.ApplicationCenterImp;

import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.exception.NotApplicationException;

/**
 * 通知到各个并实现具体业务
 */
public interface ApplicationSubject {


    /**
     * 新增一个
     * @param
     */
    public void addApplicationObserver(String appType,ApplicationObserver mApplicationObserver) throws NotApplicationException;

    /**
     * 删除一个
     * @param mApplicationObserver
     */
    public void deleteApplicationObserver(ApplicationObserver mApplicationObserver);

    /**
     * 通知 实现各个应用入口的
     * @param appInfo
     */
    public void notifyApplicationObserver(AppInfo appInfo) throws NotApplicationException;
}
