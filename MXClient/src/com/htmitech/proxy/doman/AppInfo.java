package com.htmitech.proxy.doman;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * 应用信息
 */
public class AppInfo implements Serializable {
    /**
     * 应用唯一标识
     */
    private long app_id;
    private long corp_id;
    /**
     * 应用编码
     */
    private String app_code;
    /**
     * APP名称
     */
    private String app_name;
    /**
     * 应用简称
     */
    private String app_shortname;
    /**
     * APP类型
     */
    private int app_type;
    /**
     * 应用中心图标地址
     */
    private String app_logo = "";
    /**
     * 应用描述
     */
    private String app_desc;

    private int comp_id;
    /**
     * 平台构件主键
     */
    private String comp_code;

    private int plugin_id;
    /**
     * 插件主键
     */
    private String plugin_code = "";
    /**
     * app_id 父类
     */
    private long parent_app_id;

    private long app_usergroup_id;

    private int status_flag;
    /**
     * 当前版本
     */
    private long current_version;

    /**
     * 不可用状态下的图片id
     */
    private String picture_disabled;
    /**
     * 选中状态下的图片id
     */
    private String picture_selected;
    /**
     * 正常状态下的图片id
     */
    private String picture_normal = "";

    /**
     * 最大版本号
     */
    private long max_version_number;
    /**
     * 新 and 未安装  1 新  2 未安装 3的时候 可以直接进入
     *
     * @return
     */

    private int apk_flag = 3;
    /**
     * 门户导航Id
     */
    private long tab_item_id;
    /**
     * 是否点击暂不升级
     */
    private boolean isUpdate = false;

    /**
     * 分类存的图标
     * @return
     */


    /**
     * 新增
     */
    public String user_name;
    public String login_name;
    public String group_corp_id;
    public String user_id;
    private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private long parent_appgroup_app_id;
    private int appcenter_diplay_order;
    private int appcenter_include_atfirst;
    private TextView angleNumber = null; //角标的TextView
    private String display_title;

    public String getDisplay_title() {
        if(TextUtils.isEmpty(display_title)){
            display_title = app_shortname;
        }

        if(TextUtils.isEmpty(display_title)){
            display_title = app_name;
        }
        return display_title;
    }

    public void setDisplay_title(String display_title) {
        this.display_title = display_title;
    }

    public int getAppcenter_include_atfirst() {
        return appcenter_include_atfirst;
    }

    public void setAppcenter_include_atfirst(int appcenter_include_atfirst) {
        this.appcenter_include_atfirst = appcenter_include_atfirst;
    }

    public TextView getAngleNumber() {
        return angleNumber;
    }

    public void setAngleNumber(TextView angleNumber) {
        this.angleNumber = angleNumber;
    }

    public int getAppcenter_diplay_order() {
        return appcenter_diplay_order;
    }

    public void setAppcenter_diplay_order(int appcenter_diplay_order) {
        this.appcenter_diplay_order = appcenter_diplay_order;
    }

    public long getParent_appgroup_app_id() {
        return parent_appgroup_app_id;
    }

    public void setParent_appgroup_app_id(long parent_appgroup_app_id) {
        this.parent_appgroup_app_id = parent_appgroup_app_id;
    }

    public int getAppcenter_remove() {
        return appcenter_remove;
    }

    public ArrayList<AppInfo> getClassifyAppInfo() {
        if(ClassifyAppInfo == null){
            ClassifyAppInfo = new ArrayList<>();
        }
        return ClassifyAppInfo;
    }

    public void setClassifyAppInfo(ArrayList<AppInfo> classifyAppInfo) {
        ClassifyAppInfo = classifyAppInfo;
    }

    public View view;

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setAppcenter_remove(int appcenter_remove) {
        this.appcenter_remove = appcenter_remove;
    }

    /**
     * 新增应用分类
     */
    private ArrayList<AppInfo> ClassifyAppInfo;
    /**
     * 应用中心中是否支持移除
     *
     * @return
     */

    public int appcenter_remove;

    /**
     * 应用中心中子项是否展示表示
     *
     * @return
     */

    public int type_flag;

    public int getType_flag() {
        return type_flag;
    }

    public void setType_flag(int type_flag) {
        this.type_flag = type_flag;
    }

    public ArrayList<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(ArrayList<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public long getTab_item_id() {
        return tab_item_id;
    }

    public void setTab_item_id(long tab_item_id) {
        this.tab_item_id = tab_item_id;
    }

    private AppVersion mAppVersion;

    public AppVersion getmAppVersion() {
        if(mAppVersion == null){
            mAppVersion = new AppVersion();
        }
        return mAppVersion;
    }

    public void setmAppVersion(AppVersion mAppVersion) {
        this.mAppVersion = mAppVersion;
    }

    public int getApk_flag() {
        return apk_flag;
    }

    public void setApk_flag(int apk_flag) {
        this.apk_flag = apk_flag;
    }

    public long getMax_version_number() {
        return max_version_number;
    }

    public void setMax_version_number(long max_version_number) {
        this.max_version_number = max_version_number;
    }

    private Map<String, AppVersion> appVersionMap;

    public Map<String, AppVersion> getAppVersionMap() {
        return appVersionMap;
    }

    public void setAppVersionMap(Map<String, AppVersion> appVersionMap) {
        this.appVersionMap = appVersionMap;
    }

    private ArrayList<AppVersion> mAppVersionList;

    public ArrayList<AppVersion> getmAppVersionList() {
        return mAppVersionList;
    }

    public void setmAppVersionList(ArrayList<AppVersion> mAppVersionList) {
        this.mAppVersionList = mAppVersionList;
    }

    public long getApp_id() {
        return app_id;
    }

    public int getStatus_flag() {
        return status_flag;
    }

    public int getApp_type() {
        return app_type;
    }

    public int getComp_id() {
        return comp_id;
    }

    public int getPlugin_id() {
        return plugin_id;
    }

    public long getApp_usergroup_id() {
        return app_usergroup_id;
    }

    public long getCorp_id() {
        return corp_id;
    }

    public long getCurrent_version() {
        return current_version;
    }

    public long getParent_app_id() {
        return parent_app_id;
    }

    public String getApp_code() {
        return app_code;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public String getApp_name() {
        return app_name;
    }

    public String getApp_shortname() {
        return getDisplay_title();
    }


    public String getApp_shortnames() {
        String shortName = "";
        if(TextUtils.isEmpty(app_shortname)){
            shortName = app_name;
        }else{
            shortName = app_shortname;
        }

        return shortName;
    }
    public String getComp_code() {
        if(TextUtils.isEmpty(comp_code)){
            comp_code = "";
        }
        return comp_code;
    }

    public String getPicture_disabled() {
        return picture_disabled;
    }

    public String getPicture_normal() {
        if (TextUtils.isEmpty(picture_normal)) {
            picture_normal = app_logo;
        }
        return picture_normal;
    }

    public String getPicture_selected() {
        return picture_selected;
    }

    public String getPlugin_code() {
        if(TextUtils.isEmpty(plugin_code)){
            plugin_code = "";
        }
        return plugin_code;
    }

    public void setStatus_flag(int status_flag) {
        this.status_flag = status_flag;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public void setApp_code(String app_code) {
        this.app_code = app_code;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public void setApp_shortname(String app_shortname) {
        this.app_shortname = app_shortname;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public void setApp_usergroup_id(long app_usergroup_id) {
        this.app_usergroup_id = app_usergroup_id;
    }

    public void setComp_code(String comp_code) {
        this.comp_code = comp_code;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
    }

    public void setCorp_id(long corp_id) {
        this.corp_id = corp_id;
    }

    public void setCurrent_version(long current_version) {
        this.current_version = current_version;
    }

    public void setParent_app_id(long parent_app_id) {
        this.parent_app_id = parent_app_id;
    }

    public void setPicture_disabled(String picture_disabled) {
        this.picture_disabled = picture_disabled;
    }

    public void setPicture_normal(String picture_normal) {
        this.picture_normal = picture_normal;
    }

    public void setPicture_selected(String picture_selected) {
        this.picture_selected = picture_selected;
    }

    public void setPlugin_code(String plugin_code) {
        this.plugin_code = plugin_code;
    }

    public void setPlugin_id(int plugin_id) {
        this.plugin_id = plugin_id;
    }


    public boolean equals(Object obj) {
        if (obj instanceof AppInfo) {
            AppInfo u = (AppInfo) obj;
            return this.app_id == u.getApp_id();
        }
        return super.equals(obj);
    }
}
