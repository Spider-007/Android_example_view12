package com.htmitech.proxy.interfaces;

import com.htmitech.proxy.doman.AppInfo;

/**
 * tony
 *
 * 点击删除的时候进行回调
 */
public interface CallBackRemove {
    /**
     *
     * @param mAppInfo
     * @param ponstion
     */
    public void callBackRemoveApp(AppInfo mAppInfo,int ponstion,int classifyIndex);
}
