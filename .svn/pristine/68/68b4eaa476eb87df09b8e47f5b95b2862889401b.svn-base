package com.htmitech.proxy.myenum;

import android.app.Activity;

import com.htmitech.addressbook.BookActivity;
import com.htmitech.addressbook.PeopleMessageActivity;
import com.htmitech.emportal.ui.announcement.AnnouncementListActivity;
import com.htmitech.emportal.ui.applicationcenter.ApplicationCenterActivity;
import com.htmitech.emportal.ui.detail.OpinionInputActivity;
import com.htmitech.emportal.ui.document.DocumentMainActivity;
import com.htmitech.emportal.ui.homepage.HomeSet;
import com.htmitech.htcommonformplugin.activity.GeneralFormChildActivity;
import com.htmitech.htcommonformplugin.activity.InitFormFragmentActivity;
import com.htmitech.htexceptionmanage.activity.InitManageExceptionActivity;
import com.htmitech.htworkflowformpluginnew.activity.InitWorkFlowFormActivity;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.proxy.activity.BarLineReportActivity;
import com.htmitech.proxy.activity.ChilDaccountActivity;
import com.htmitech.proxy.activity.ClearCacheActivity;
import com.htmitech.proxy.activity.ConnectionActivity;
import com.htmitech.proxy.activity.FeedBackActivity;
import com.htmitech.proxy.activity.PicReportActivity;
import com.htmitech.proxy.activity.PlatformLead;
import com.htmitech.proxy.activity.PortalSwitchActivity;
import com.htmitech.proxy.activity.ScanForDownLoadActivity;

import com.htmitech.proxy.activity.SettingActivity;
import com.htmitech.proxy.activity.StatisticalReportActivity;
import com.htmitech.proxy.activity.TextViewSizeActivity;
import com.htmitech.proxy.doman.AppReturnClass;
import com.htmitech.proxy.interfaces.CallBackLeftJC;

import com.htmitech_updown.updownloadmanagement.UpDownLoadActivity;
import com.minxing.client.activity.SystemSettingGesturePasswordActivity;
import com.minxing.client.activity.SystemSettingMessageNotificationActivity;
import com.minxing.kit.internal.circle.CircleTopicsListActivity;

import activity.ScheduleDetailActivity;
import activity.ScheduleMainActivity;

/**
 * 基础应用 都从这里面来进行获取与跳转
 */
public enum BaseApplicationEnum {

    PTZN(ApplicationAllEnum.PTZN, PlatformLead.class),

    PTFX(ApplicationAllEnum.PTFX, ScanForDownLoadActivity.class),//平台分享暂时放入平台指南

    MMSZ(ApplicationAllEnum.MMSZ, SystemSettingGesturePasswordActivity.class),

    XXTX(ApplicationAllEnum.XXTX, SystemSettingMessageNotificationActivity.class),

    YYZX(ApplicationAllEnum.YYZX, ApplicationCenterActivity.class),//左侧边栏应用中心
//
//    YYZX_CLASSIFY(ApplicationAllEnum.YYZX, ApplicationCenterListActivity.class),//左侧边栏应用中心分类

    ZYYYZX(ApplicationAllEnum.ZYYYZX, ApplicationCenterActivity.class),//右上角应用中心
//
//    ZYYYZX_CLASSIFY(ApplicationAllEnum.ZYYYZX, ApplicationCenterListActivity.class),//右上角应用中心分类

    CYYJ(ApplicationAllEnum.CYYJ, OpinionInputActivity.class),//常用意见

    TXL(ApplicationAllEnum.TXL, BookActivity.class),//通讯录

    //    DB(ApplicationAllEnum.DB, DaiBanFragmentActivity.class),//代办
    DB(ApplicationAllEnum.DB, InitWorkFlowFormActivity.class),//新增 代办

    DBXQ(ApplicationAllEnum.DBXQ, WorkFlowFormDetalActivity.class),//

    LXKF(ApplicationAllEnum.LXKF, ConnectionActivity.class),//联系客服暂缺

    MHQH(ApplicationAllEnum.MHQH, PortalSwitchActivity.class),//门户切换

    ZKL(ApplicationAllEnum.ZLK, DocumentMainActivity.class),//资料库

    QCHC(ApplicationAllEnum.QCHC, ClearCacheActivity.class),//清除缓存
    SETTING(ApplicationAllEnum.SETTING, SettingActivity.class),
//    SETTING(ApplicationAllEnum.SETTING, ArchiverModuleActivity.class),// 这儿是文件管理的
//    SETTING(ApplicationAllEnum.SETTING, GeneralFileViewActivity.class),//通用的文件FIle 可在线打开PDF word txt 网络图片等

    TZGG(ApplicationAllEnum.TZGG, AnnouncementListActivity.class),//新增 通知公告页面跳转

    FGSZ(ApplicationAllEnum.FGSZ, HomeSet.class),

    GRSZ(ApplicationAllEnum.GRSZ, PeopleMessageActivity.class), //新增个人设置
//    FGSZ(ApplicationAllEnum.FGSZ, AppCenterActivity.class),

    WDSC(ApplicationAllEnum.WDSC, null),  //ClientTabActivity.class 表示的是调用敏形的需要进行回调参数

    CJRW(ApplicationAllEnum.CJRW, null),

    ZZHD(ApplicationAllEnum.ZZHD, null),

    FQTP(ApplicationAllEnum.FQTP, null),

    GZFX(ApplicationAllEnum.GZFX, null),

    SYS(ApplicationAllEnum.SYS, null),

    FBXX(ApplicationAllEnum.FBXX, null),

    CJ(ApplicationAllEnum.CJ, null),//新增

    REPORT(ApplicationAllEnum.REPORT, StatisticalReportActivity.class),//新增

    REPORT_LINE(ApplicationAllEnum.REPORT_LINE, BarLineReportActivity.class),//新增柱线图构件

    REPORT_PIE(ApplicationAllEnum.REPORT_PIE, PicReportActivity.class),//饼图

    TYBD(ApplicationAllEnum.TYBD, InitFormFragmentActivity.class),//新增 通用表单构件

    TYBDCJ(ApplicationAllEnum.TYBDCJ, GeneralFormChildActivity.class),//新增通用表单插件

//    TYBDXQ(ApplicationAllEnum.TYBDXQ, GeneralFormDetalActivity.class),//通用表单详情

    ZTDX(ApplicationAllEnum.ZTDX, TextViewSizeActivity.class),//新增字体大小


    ZZHGL(ApplicationAllEnum.ZZHGL, ChilDaccountActivity.class),//新增子帐号管理;


    GZQ(ApplicationAllEnum.GZQ, null),//新增工作圈;

    ADV_PAGE(ApplicationAllEnum.ADV_PAGE, null),//新增广告;

    WTFK(ApplicationAllEnum.WTFK, FeedBackActivity.class),//新增问题反馈

    TXZX(ApplicationAllEnum.TXZX, InitManageExceptionActivity.class),//新增提醒中心


    CJQL(ApplicationAllEnum.CJQL, null),//新增创建群聊


    HT(ApplicationAllEnum.HT, CircleTopicsListActivity.class),//新增话题

    SCHEDULE(ApplicationAllEnum.SCHEDULE, ScheduleMainActivity.class),//日程管理

    SCHEDULEDETAIL(ApplicationAllEnum.SCHEDULEDETAIL, ScheduleDetailActivity.class),//日程详情

    FILEUPLOADMANAGER(ApplicationAllEnum.FILEUPLOADMANAGER, UpDownLoadActivity.class);//文件上传管理


    private ApplicationAllEnum code;
    private Class<? extends Activity> tableClass;

    private BaseApplicationEnum(ApplicationAllEnum code, Class<? extends Activity> tableName) {
        this.code = code;
        this.tableClass = tableName;
    }

    /**
     * 获取获取相对应的activity
     *
     * @return
     */
    public static AppReturnClass getActivity(ApplicationAllEnum mApplicationAllEnum, CallBackLeftJC mCallBackLeftJC) {
        AppReturnClass mAppReturnClass = new AppReturnClass();
        for (BaseApplicationEnum c : BaseApplicationEnum.values()) {
            if (c.code == mApplicationAllEnum) {
                if (c.tableClass == null) {
                    mCallBackLeftJC.onClickLeft(c.code);
                    mAppReturnClass.value = "MXOnClick";
                    return mAppReturnClass;
                } else {
                    mAppReturnClass.value = c.tableClass.toString();
                    mAppReturnClass.mClass = c.tableClass;
                    return mAppReturnClass;
                }

            }
        }
        mAppReturnClass.value = "MXOnClick";
        return mAppReturnClass;
    }


}
