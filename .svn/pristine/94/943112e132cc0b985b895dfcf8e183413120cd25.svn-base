package com.htmitech.htworkflowformpluginnew.util;

import android.content.Intent;
import android.text.TextUtils;

import com.htmitech.proxy.doman.AppInfo;

import java.util.ArrayList;
import java.util.HashMap;

import htmitech.com.componentlibrary.entity.BadgeResult;

/**
 * 获取所有角标的工具类
 */

public class BadgeAllUnit {

    private HashMap<String, BadgeResult> hashMap = new HashMap<>();

    private static BadgeAllUnit instance;

    private BadgeAllUnit() {

    }

    public static BadgeAllUnit get() {
        if (instance == null) {
            instance = new BadgeAllUnit();
        }
        return instance;
    }

    public int getAllNumber(){
        int count = 0;
        if(hashMap.size() == 0){
            return 0;
        }
        for(String key : hashMap.keySet()){
            String number = hashMap.get(key).getAppBadge();
            if(!TextUtils.isEmpty(number)){
                count = Integer.parseInt(number);
            }
        }
        return count;
    }

    /**
     * 获取关于对应key的 实体 此处 的key表示的是 appId
     *
     * @param key
     * @return
     */
    public BadgeResult getBadgeBean(String key) {
        return hashMap.get(key);
    }

    /**
     * @param key
     * @return
     */
    public String getBadge(String key) {
        if (hashMap.get(key) == null) {
            return "";
        }
        return hashMap.get(key).getAppBadge();
    }

    /**
     * @param
     * @return
     */
    public String getBadge(AppInfo appInfo) {

        if (appInfo.getApp_type() == 7) {

            return classifyBadge(appInfo.getClassifyAppInfo());

        } else {

            if (hashMap.get(appInfo.getApp_id() + "") == null) {

                return "";

            }

            return hashMap.get(appInfo.getApp_id() + "").getAppBadge();

        }
    }

    /**
     * 获取分组的所有分类角标
     *
     * @param appIds
     * @return
     */
    public String classifyBadge(ArrayList<AppInfo> appIds) {
        int number = 0;
        for (AppInfo appId : appIds) {
            String badge = getBadge(appId.getApp_id() + "");
            if (!TextUtils.isEmpty(badge)) {
                number += Integer.parseInt(badge);
            }
        }
        return number + "";
    }

    /**
     * 添加对应的key的值
     *
     * @param badgeResults
     */
    public void addALlBadgeBean(ArrayList<BadgeResult> badgeResults) {
        hashMap.clear();

        for (BadgeResult mBadgeResult : badgeResults) {
            hashMap.put(mBadgeResult.getAppId(), mBadgeResult);
        }
    }

    /**
     * 释放对应的缓存信息
     */
    public void clearBadgeUnit() {
        hashMap.clear();
    }

    public int getBadgeSize() {
        return hashMap.size();
    }

}
