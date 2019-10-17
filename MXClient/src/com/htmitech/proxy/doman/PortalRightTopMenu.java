package com.htmitech.proxy.doman;

import com.htmitech.proxy.myenum.ApplicationAllEnum;

/**
 * 右上角
 */
public class PortalRightTopMenu {
    private long righttop_item_id;
    private long portal_id;
    private long app_id;
    private String picture_normal;
    private String picture_selected;
    private String picture_disabled;
    private String app_loge;
    private String appCode;
    private AppInfo mAppInfo;
    private String Comp_code;

    public String getComp_code() {
        return Comp_code;
    }

    public void setComp_code(String comp_code) {
        Comp_code = comp_code;
    }

    public AppInfo getmAppInfo() {
        return mAppInfo;
    }

    public void setmAppInfo(AppInfo mAppInfo) {
        this.mAppInfo = mAppInfo;
    }

    public String getApp_loge() {
        return app_loge;
    }

    public void setApp_loge(String app_loge) {
        this.app_loge = app_loge;
    }

    private String display_title;
    private long display_order;

    private ApplicationAllEnum mApplicationAllEnum;

    public ApplicationAllEnum getmApplicationAllEnum() {
        return mApplicationAllEnum;
    }

    public void setmApplicationAllEnum(ApplicationAllEnum mApplicationAllEnum) {
        this.mApplicationAllEnum = mApplicationAllEnum;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public long getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(long display_order) {
        this.display_order = display_order;
    }


    public long getPortal_id() {
        return portal_id;
    }

    public long getRighttop_item_id() {
        return righttop_item_id;
    }

    public String getPicture_normal() {
        return picture_normal;
    }

    public void setPicture_normal(String picture_normal) {
        this.picture_normal = picture_normal;
    }

    public String getPicture_selected() {
        return picture_selected;
    }

    public void setPicture_selected(String picture_selected) {
        this.picture_selected = picture_selected;
    }

    public String getPicture_disabled() {
        return picture_disabled;
    }

    public void setPicture_disabled(String picture_disabled) {
        this.picture_disabled = picture_disabled;
    }

    public String getDisplay_title() {
        return display_title;
    }

    public void setDisplay_title(String display_title) {
        this.display_title = display_title;
    }


    public void setPortal_id(long portal_id) {
        this.portal_id = portal_id;
    }

    public void setRighttop_item_id(long righttop_item_id) {
        this.righttop_item_id = righttop_item_id;
    }
}
