package com.htmitech.proxy.myenum;

import com.htmitech.proxy.doman.AppInfo;

import java.util.Map;

/**
 * Tony  基础应用以及工作流构建的描述
 * <p>
 * 基础应用拿的是appCode
 * 构建都是拿的是compCode
 */
public enum ApplicationAllEnum {
    DB("com_workflow", "待办"),

    DBXQ("com_workflow", "待办详情"),

    TXL("com_addressbook", "通讯录"),

    GT("app_messages", "消息"), //沟通

    GZQ("app_worksocial", "工作组"),//工作组

    CJ("app_cj", "插件", ""),

    XXTX("app_notification_setting", "消息提醒"),//消息提醒

    CYYJ("com_workflow_plugin_opinions", "常用意见"),//常用意见

    YYZX("app_center", "应用中心"),//应用中心

    FGSZ("app_homestyle_setting", "风格设置"),//风格设置

    WDSC("app_myfavorite", "我的收藏"),//我的收藏

    BBXX("app_versioninfo", "版本信息"),//版本信息

    MMSZ("app_password_setting", "密码设置"),//密码设置

    PTZN("app_platformhelp", "平台指南"),//平台指南

    LXKF("app_manualservice", "联系客服"),//联系客服

    MHQH("app_convertportal", "门户切换"),//门户切换

    PTFX("app_platformshare", "平台分享"),//平台分享


    ZYYYZX("app_right_center", "应用中心"),//右上角应用中心

    SYS("app_platformscan", "扫一扫"),//扫一扫

    GZFX("app_worksocial_share", "工作分享"),//工作分享

    FBXX("app_worksocial_sendnotice", "发布消息"),//发布消息

    CJRW("app_worksocial_createtask", "创建任务"),//创建任务

    ZZHD("app_worksocial_startactivity", "组织活动"),//组织活动

    FQTP("app_worksocial_startvoting", "发起投票"),//发起投票

    GRSZ("app_person_setting", "个人设置"),//新增个人设置

    ZTDX("app_fontsize_setting", "字体大小"),//新增字体大小设置


    SETTING("app_setting", "设置"),//新增字体大小设置

    ZY("app_platformhome", "平台首页"),

    ZLK("com_filescenter", "资料库"),//新增 资料库

    QCHC("app_clearcache", "清楚缓存"),//清除缓存 新增

    REPORT("com_report_list", "统计"),//新增统计

    REPORT_LINE("com_report_barline", "柱线图构件"),//新增柱线图构件

    REPORT_PIE("com_report_pie", "饼图"),//新增饼图

    TZGG("com_notice", "通知公告"),//新加

    TYBD("com_commonform", "通用表单"),//通用表单

    TYBDXQ("com_commonform", "通用表单详情"),//通用表单详情

    TYBDCJ("com_commonform_plugin_selector", "通用表单插件"),//新增通用表单插件 添加plugin_code


    DL("app_login", "登录"),

    ZZHGL("app_cis_account_management", "子帐号管理"),//新增子帐号管理

    E_MAIL("com_email", "邮件"),//新增邮箱构建

    ADV_PAGE("app_advertise", "广告页"),//新增广告页
    //TODO
    WTFK("app_issuesreport", "问题反馈"), //新增问题反馈

    CJQL("app_startforum", "发起讨论组"), //新增创建群聊 发起讨论组

    HT("app_topic", "话题"), //新增话题

    JWIFI("wifi", "仅WIFI上传"), //WIFI上传

    WIFI_DOWN("wifi_down", "仅WIFI下载"), //WIFI上传

    TXZX("com_alert", "提醒中心"), //新增提醒中心

    SCHEDULE("com_schedule", "日程管理"),//日程管理

    SCHEDULEDETAIL("com_schedule", "日程详情"),//日程详情

    FILEUPLOADMANAGER("app_filetransfer_manager", "文件上传管理");//文件上传管理


    public String compCode;
    public String name;
    public String url; //(左侧，右侧)图标
    public String url_selected = "";//点击状态下
    public String url_disabled; //正常状态下(修改成之前代码)
    public String appId;
    public int homeStyle; //样式
    public long tab_item_id;
    public String startValue;
    public int appType; //app类型
    public Map<String, Object> map;
    public AppInfo mAppInfo;

    ApplicationAllEnum() {
    }

    ApplicationAllEnum(String compCode, String name) {
        this.compCode = compCode;
        this.name = name;
    }

    ApplicationAllEnum(String compCode, String name, String url) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
    }

    ApplicationAllEnum(String compCode, String name, String url, String appId) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
        this.appId = appId;
    }

    ApplicationAllEnum(String compCode, String name, String url, String appId, int homeStyle) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.homeStyle = homeStyle;
    }

    ApplicationAllEnum(String compCode, String name, String url, String appId, int homeStyle, long tab_item_id) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.homeStyle = homeStyle;
        this.tab_item_id = tab_item_id;
    }

    ApplicationAllEnum(String compCode, String name, String url, String url_selected, String appId, int homeStyle, long tab_item_id) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.homeStyle = homeStyle;
        this.tab_item_id = tab_item_id;
        this.url_selected = url_selected;
    }

    ApplicationAllEnum(String compCode, String name, String url, String url_selected, String appId, int homeStyle, long tab_item_id, String startValue, int appType, AppInfo mAppInfo) {
        this.compCode = compCode;
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.homeStyle = homeStyle;
        this.tab_item_id = tab_item_id;
        this.url_selected = url_selected;
        this.startValue = startValue;
        this.appType = appType;
        this.mAppInfo = mAppInfo;
    }

    /**
     * 根据appID获取对应的Enum
     *
     * @param appInfo
     * @return
     */
    public static ApplicationAllEnum getAppIdToEnum(AppInfo appInfo) {
        String compCode = "";
        switch (appInfo.getApp_type()) {
            case 1:
                compCode = appInfo.getComp_code();
                break;
            case 2:
                compCode = appInfo.getPlugin_code();
                break;
            default:
                compCode = appInfo.getApp_code();
                break;
        }
        for (ApplicationAllEnum a : ApplicationAllEnum.values()) {
            if (a.compCode.equals(compCode)) {
                a.mAppInfo = appInfo;
                a.mAppInfo = appInfo;
                return a;
            }
        }
        return CJ;
    }

    /**
     * 根据appId获取对应的name
     */

    public static String getName(String appCode) {
        for (ApplicationAllEnum a : ApplicationAllEnum.values()) {
            if (a.compCode.equals(appCode)) {
                return a.name;
            }
        }
        return null;
    }

    /**
     * 根据AppID 获取对应的Application
     *
     * @return
     */
    public static ApplicationAllEnum getApplication(String appId) {
        for (ApplicationAllEnum a : ApplicationAllEnum.values()) {
            if (a.appId.equals(appId)) {
                return a;
            }
        }
        return null;
    }
}
