package com.minxing.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.LeftMenuLayout;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.BaseModel;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.common.AppPreferenceHelper;
import com.htmitech.emportal.common.CommonSettings;
import com.htmitech.emportal.common.PreferenceKeys;
import com.htmitech.emportal.common.lib.residemenu.ResideMenu;
import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.emportal.entity.AddOrDeleteUser;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.UploadPicRequestBean;
import com.htmitech.emportal.receive.RegistOrUnRegisterReceive;
import com.htmitech.emportal.request.RequestPeopleModel;
import com.htmitech.emportal.service.UpdateServiceHanle;
import com.htmitech.emportal.ui.contacts.ContactOcuActivity;
import com.htmitech.emportal.ui.homepage.HomeSet;
import com.htmitech.emportal.ui.homepage.PortalMessageService;
import com.htmitech.emportal.ui.login.data.logindata.UserOptionListEntity;
import com.htmitech.emportal.ui.login.model.task.LoginModel;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.listener.CallbackMX;

import activity.ScheduleDetailActivity;
import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.myEnum.BottomButtonSlyteEnum;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.myEnum.TimeEnum;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.ButtomColorFactory;
import com.htmitech.proxy.LeftColorFactory;
import com.htmitech.proxy.MenuTabItemFactory;
import com.htmitech.proxy.ProxyDealLeftButtom;
import com.htmitech.proxy.activity.ZWLoginActivity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.GetPictureResult;
import com.htmitech.proxy.doman.OtherParams;
import com.htmitech.proxy.doman.SysDefault;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackButtom;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.interfaces.ConcerCountShow;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.HomePageStyleEnum;

import com.htmitech.proxy.services.UpgradeFromDatabaseServices;
import com.htmitech.proxy.util.CacheActivity;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.LogMesgUpUtil;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.unit.BackgroundDetector;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.minxing.client.activity.GesturePasswordActivity;
import com.minxing.client.tab.MenuTabHost;
import com.minxing.client.tab.MenuTabItem;
import com.minxing.client.upgrade.AppUpgradeInfo;
import com.minxing.client.upgrade.EmpmAppUpgradeInfo;
import com.minxing.client.util.BadgeUtil;
import com.minxing.client.util.NotificationUtil;
import com.minxing.client.util.Utils;
import com.umeng.update.UmengUpdateAgent;

import org.apache.http.entity.StringEntity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.feng.skin.manager.base.BaseTabActivity;
import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.content.MXConstant;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.listener.ChatFinishListener;
import com.htmitech.htworkflowformpluginnew.util.BadgeAllUnit;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

@SuppressWarnings("deprecation")
public class ClientTabActivity extends BaseTabActivity implements CallbackMX, IBaseCallback, CallBackButtom, ObserverCallBackType {
    public static MenuTabHost mTabHost = null;
    //public static MenuTabHost mTabHostCp = null;
    public DrawerLayout drawer_layout;
    public static boolean backFlag = false;
    public static int beforeBack = 0;
    public static boolean HomeSetback = false;

    private Intent mChatIntent = null; // 敏信
    private Intent mCircleIntent = null; // 圈子
    private Intent mToDoIntent = null; // 待办
    // private Intent mToReadIntent = null; // 待阅
    private Intent mContactsIntent = null; // 通讯录

    private MenuTabItem chatTabItem = null;
    // private MenuTabItem toreadTabItem = null;
    public static MenuTabItem todoTabItem = null;
    private MenuTabItem circleTabItem = null;
    private MenuTabItem contactsTabItem = null;

    //首页
    private MenuTabItem homeTabItem = null;
    private Intent mHomeIntent = null;

    private BroadcastReceiver receiver = null;

    private LeftMenuLayout mResideMenu;

    private View chatHandler;
    private View contactsHandler;
    private View appCenterHandler;
    private View circleHandler;

    public static final String SHOW_CHAT_LIST_FLAG = "show_chat_list";
    public static final String AUTO_ENTER_CHAT_ID = "auto_chat_id";
    private long mInterval = 0; // 兩次返回鍵的间隔时间
    private final long mDiffTime = 2000; // 两次返回键小于2秒 则退出
    private boolean isAutoEnterChat = false;
    private int autoEnterChatID = -1;
    boolean showHandle = true;
    private String currentPage = null;

    private ArrayList<MenuTabItem> styleList = null;


    private String form_operating_button = "1";//配置表单操作按钮

    private boolean isUpdate = false;
    private EmpmAppUpgradeInfo upgradeInfos = null;

    private AppliationCenterDao mAppliationCenterDao;
    private INetWorkManager netWorkManager;
    private PopChooseTimeHelper mPopBirthHelper;
    private PopChooseTimeHelper mPopBirthHelperGender;
    private double numberSize;
    private boolean ischeckupgrade;
    private final static String PORTAL_DEFAULT = "portal_default";
    public boolean isChangeProtal = false;
    public int number = -1;
    public BaseModel baseModel;
    public IBaseCallback iBaseCallbackSavePeopleMessage;

    private RequestPeopleModel mRequestPeopleModel;
    private UploadPicRequestBean uploadPicRequestBean;
    private String getPictureUrl;
    private File filePic;
    private static final String HTTPTYPEPICTUTR = "getDicPicture";
    private static final String UPDATEUSERMESSAGE = "orguserUpdate";//个人信息修改
    private static final String ADDCONTACT = "orgusercontactcontrollerSave";//添加常用联系人
    private static final String DELETECONTACT = "orgusercontactcontrollerDelete";//删除常用联系人
    private String updateUserMessageUrl;
    private String addContactURL = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.SYNCDATA_ADD_USER_JAVA;
    private String deleteContactURL = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.SYNCDATA_DELETE_USER_JAVA;
    private AddOrDeleteUser mAddContactUser;
    private AddOrDeleteUser mDeleteContactUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EventBus.getDefault().register(this);
        ButtomColorFactory.getInstance(this).setButtomListColor();
        LeftColorFactory.getInstance(this).setLeftBarColor();
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        CacheActivity.finishActivity();
        ischeckupgrade = getIntent().getBooleanExtra("ischeckupgrade", false);
        if(ischeckupgrade){//防止从loading页面进入该参数为false导致升级信息不准确
            SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
            SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
            securityEditor.putBoolean("ischeckupgrade", ischeckupgrade);
            securityEditor.apply();
        }
        getIntent().removeExtra("ischeckupgrade");
        showHandle = ResourceUtil.getConfBoolean(this,
                R.string.client_show_handle);
        if (getIntent().getAction() != null
                && !"".equals(getIntent().getAction())
                && getIntent().getAction().equals("finish")) {

            return;
        }
        LogMesgUpUtil.uploadLoadMsg();
        if(PreferenceUtils.getIsEMIUser(ClientTabActivity.this) != 0){
            boolean isHaveCommunity = false;
            try {
                /**
                 * 切换社区
                 */
                ArrayList<Integer> netWorkIds = ConcreteLogin.getInstance().getNetworkIds(this);
                if(netWorkIds == null){
                    isHaveCommunity = true;
                }else{
                    for (int i = 0; i < netWorkIds.size(); i++) {
                        if ((netWorkIds.get(i) + "").equals(BookInit.getInstance().getMx_appid())) {
                            isHaveCommunity = true;
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isHaveCommunity) {
                try{
                    ConcreteLogin.getInstance().switchNetwork(this, Integer.parseInt(BookInit.getInstance().getMx_appid()));
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else {
//                if (TextUtils.isEmpty(BookInit.getInstance().getMx_appid())) {
//
//                    Toast.makeText(ClientTabActivity.this, getResources().getString(R.string.login_error_msg_community), Toast.LENGTH_LONG).show();
//                } else if (TextUtils.isEmpty(BookInit.getInstance().getPortalId())) {
//                    Toast.makeText(ClientTabActivity.this, getResources().getString(R.string.login_error_msg_portal), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(ClientTabActivity.this, getResources().getString(R.string.login_error_msg_not_community), Toast.LENGTH_LONG).show();
//                }
//                Intent mIntent = new Intent(ClientTabActivity.this, LoginActivity.class);
//                startActivity(mIntent);
//                this.finish();
//                return;

                ConcreteLogin.getInstance().setType(1);
            }

            if (TextUtils.isEmpty(BookInit.getInstance().getPortalId())) {
                Toast.makeText(ClientTabActivity.this, getResources().getString(R.string.login_error_msg_portal), Toast.LENGTH_LONG).show();
                Intent mIntents = new Intent(ClientTabActivity.this, LoginActivity.class);
                startActivity(mIntents);
                this.finish();
                return;
            }
        }


        form_operating_button = ResourceUtil.getConfString(ClientTabActivity.this, "client_form_operating_button");
        if (form_operating_button.equals("1")) {

            BookInit.getInstance().setmBottomButtonEnum(BottomButtonSlyteEnum.DRAG);
        } else {
            BookInit.getInstance().setmBottomButtonEnum(BottomButtonSlyteEnum.BOTTOM);
        }
        mAppliationCenterDao = new AppliationCenterDao(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取应用中心中的应用来进行
                ArrayList<AppInfo> appInfos = mAppliationCenterDao.getPortalAppInfoAll();
//                ArrayList<AppInfo> appInfosLeft = mAppliationCenterDao.getLeftAppInfo();
//                appInfos.addAll(appInfosLeft);
                String portal_id = BookInit.getInstance().getPortalId() + "";
                String userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
                CommonSettings.DEFAULT_DOCUMENT_CACHE_FOLDER = CommonSettings.DEFAULT_CACHE_FOLDER
                        + File.separator + userId + File.separator + portal_id;
                CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER = CommonSettings.DEFAULT_CACHE_FOLDER
                        + File.separator + userId + File.separator + portal_id;
                CommonSettings.DEFAULT_ATTACHMENT_CACHE_FOLDER = CommonSettings.DEFAULT_CACHE_FOLDER
                        + File.separator + userId + File.separator + portal_id;
                File file2 = new File(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER);

                //将所有的应用中心都建立文件夹
                for (AppInfo mAppInfo : appInfos) {
                    String filePath = CommonSettings.DEFAULT_CACHE_FOLDER
                            + File.separator + userId + File.separator + portal_id + File.separator + mAppInfo.getApp_id();
                    File filePathFile = new File(filePath);

                    if (!filePathFile.exists()) {
                        filePathFile.mkdirs();
                    }
                }
            }
        }).start();


//
//        if (!file2.exists()) {
//            boolean floag = file2.mkdirs();
//
//        }
        BookInit.getInstance().setCrrentUserId(OAConText.getInstance(HtmitechApplication.instance()).UserID);
        BookInit.getInstance().setmCallbackMX(this); // 初始化
        BookInit.getInstance().setSdCardPath(CommonSettings.DEFAULT_DOCFILE_CACHE_FOLDER);
        ArrayList<String> fileUrl = mAppliationCenterDao.getParamGroupCorpValue("file_url_prefix");
        if (fileUrl != null && fileUrl.size() > 0) {
            BookInit.getInstance().setPhoneUrl(fileUrl.get(0));
        }


        BookInit.getInstance().setCurrentUserName(OAConText.getInstance(HtmitechApplication.instance()).UserName);

        String cellPhoneLength = ResourceUtil.getConfString(this, "client_encrypt_cellphone");
        BookInit.getInstance().setCellPhoneLength(cellPhoneLength.split(","));

        if(PreferenceUtils.getIsEMIUser(ClientTabActivity.this) != 0){
            if (ConcreteLogin.getInstance().isCurrentUserNull(this)) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return;
            }
        }

        // 初始化界面
        initView();
        // initNotification();

//         initSlidingMenu();
        mResideMenu = (LeftMenuLayout) findViewById(R.id.left_menu_scroll_client);
        closeDrawer();
//        mResideMenu.attachToActivity(this);
//        mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        initResideMenu();
        //
//        if (!isChangeProtal && ischeckupgrade) {
//            //升级服务
//            Intent mIntent = new Intent(ClientTabActivity.this, UpgradeFromDatabaseServices.class);
//            startService(mIntent);
//        }
        ischeckupgrade = false;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent
                        .getAction()
                        .equals(MXConstant.BroadcastAction.MXKIT_REVOKE_DISPATCH_UNSEEN)
                        || intent.getAction().equals(
                        AppConstants.MXCLIENT_REFRESH_NEW_VERSION)) {
                    updateAll();
                    if (PreferenceUtils
                            .checkUpgradeMark(ClientTabActivity.this)) {
                        // mResideMenu.UpgradeNewVersionMark(true);

                        EmpmAppUpgradeInfo upgradeInfos = (EmpmAppUpgradeInfo) intent
                                .getSerializableExtra(AppConstants.MXCLIENT_UPGRADE_INFO);
                        AppUpgradeInfo upgradeInfo = new AppUpgradeInfo();
                        if (upgradeInfo != null
                                && upgradeInfo.getVersion() != null
                                && !"".equals(upgradeInfo.getVersion())) {
                            String upgrade_url = ResourceUtil.getConfString(ClientTabActivity.this, "apk_url");
                            upgradeInfo.setSmart_url(upgrade_url);
                            upgradeInfo.setUpgrade_url(upgrade_url);
                            upgradeInfo.setSmartUpgrade(false);
//                            upgradeInfo.setSmart_size(upgradeInfos.result.siz);
                            Utils.showUpgradeDialog(ClientTabActivity.this,
                                    upgradeInfo);
                        }
                    } else {
                        // mResideMenu.UpgradeNewVersionMark(false);
                    }
                }
            }
        };
        if(PreferenceUtils.getIsEMIUser(ClientTabActivity.this) != 0){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MXConstant.BroadcastAction.MXKIT_REVOKE_DISPATCH_UNSEEN);
            filter.addAction(AppConstants.MXCLIENT_REFRESH_NEW_VERSION);
            registerReceiver(receiver, filter);
            ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
            mConcreteLogin.setMXShareLink(this, new htmitech.com.componentlibrary.listener.MXShareLinkListener() {
                @Override
                public boolean onLinkClicked(String appUrl, String title) {
                    //arg1.getAppUrl().indexOf("native") == -1
                    //if (arg1.getAppUrl().substring(0, 3) == "aa" && arg1.getAppUrl().substring(0, 3) == "bb") {
                    if (TextUtils.isEmpty(appUrl)) {
                        return false;
                    }
                    if (appUrl.substring(0, 2).equals("aa") || appUrl.substring(0, 2).equals("bb")) {
                        String[] split = appUrl.split("\\|");
                        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                        AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                        try {
                            HashMap map = new HashMap<String, String>();
                            map.put("app_id", split[0].substring(2));
                            map.put("UserID", split[1]);
                            map.put("app_version_id", split[2]);
                            map.put("DocId", split[3]);
                            map.put("Kind", split[4]);
                            try{
                                map.put("flowId", split[5]);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            map.put("DocTitle", title);
                            map.put("come_share", "1");
                            HTActivityUnit.switchTo(ClientTabActivity.this, WorkFlowFormDetalActivity.class, map);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (appUrl.substring(0, 2).equals("ee")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(appUrl.substring(2, appUrl.length()));
                        intent.setData(content_url);
                        startActivity(intent);
                        return true;
                    } else if (appUrl.substring(0, 2).equals("TF")) {
                        String[] split = appUrl.split("\\|");
                        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                        AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                        try {
                            HashMap map = new HashMap<String, String>();
                            map.put("isShare", true);
                            map.put("app_id", split[0].substring(2));
                            map.put("userID", split[1]);
                            map.put("app_version_id", split[2]);
                            map.put("conditions", split[3]);
                            map.put("order", split[4]);
                            map.put("userName", split[5]);
                            ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
                        } catch (NotApplicationException e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (appUrl.substring(0, 2).equals("BT")) {
                        try {
                            String[] split = appUrl.split("\\|");
                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                            HashMap map = new HashMap<String, String>();
                            map.put("isShare", true);
                            map.put("app_id", split[0].substring(2));
                            map.put("userID", split[1]);
                            map.put("app_version_id", split[2]);
//                                map.put("com_report_pie_mobileconfig_define", split[3]);
                            map.put("com_report_pie_mobileconfig_include_security", split[4]);
                            map.put("com_report_pie_mobileconfig_condition", split[5]);
                            map.put("userName", split[6]);
                            ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (appUrl.substring(0, 2).equals("CF")) {
                        String[] split = appUrl.split("\\|");
                        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                        AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                        try {
                            HashMap map = new HashMap<String, String>();
                            map.put("id", split[0].substring(2));
                            map.put("form_id", split[1]);
                            map.put("data_id", split[2]);
                            map.put("app_id", split[3]);
                            map.put("user_id", split[4]);
                            map.put("subsystemid", split[5]);
                            map.put("doc_title", split[6]);
                            map.put("come_share", "1");
//                                HTActivityUnit.switchTo(ClientTabActivity.this, GeneralFormDetalActivity.class, map);
//                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (appUrl.substring(0, 2).equals("RC")) {
                        String[] split = appUrl.split("\\|");
                        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                        AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                        try {
                            HashMap map = new HashMap<String, String>();
                            map.put("UserID", split[0].substring(2));
                            map.put("app_id", split[1]);
                            map.put("sch_id", split[2]);
                            map.put("corpId", split[3]);
                            map.put("groupCorpId", split[3]);
                            map.put("flag", "2");
                            HTActivityUnit.switchTo(ClientTabActivity.this, ScheduleDetailActivity.class, map);
//                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else if (appUrl.substring(0, 2).equals("ZX")) {
                        try {
                            String[] split = appUrl.split("\\|");
                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
                            HashMap map = new HashMap<String, String>();
                            map.put("isShare", true);
                            map.put("app_id", split[0].substring(2));
                            map.put("userID", split[1]);
                            map.put("app_version_id", split[2]);
                            map.put("com_report_barline_mobileconfig_condition", split[3]);
                            map.put("order", split[4]);
                            map.put("userName", split[5]);
                            ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else {
                        return false;
                    }

                }
            });
        }

//        MXDataPlugin.getInstance().setMXShareLinkListener(
//                new MXShareLinkListener() {
//
//                    @Override
//                    public boolean onLinkClicked(Context arg0, ShareLink arg1) {
//                        //arg1.getAppUrl().indexOf("native") == -1
//                        //if (arg1.getAppUrl().substring(0, 3) == "aa" && arg1.getAppUrl().substring(0, 3) == "bb") {
//                        if (TextUtils.isEmpty(arg1.getAppUrl())) {
//                            return false;
//                        }
//                        if (arg1.getAppUrl().substring(0, 2).equals("aa") || arg1.getAppUrl().substring(0, 2).equals("bb")) {
//                            String appUrl = arg1.getAppUrl();
//                            String[] split = appUrl.split("\\|");
//                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                            try {
//                                HashMap map = new HashMap<String, String>();
//                                map.put("app_id", split[0].substring(2));
//                                map.put("UserID", split[1]);
//                                map.put("app_version_id", split[2]);
//                                map.put("DocId", split[3]);
//                                map.put("Kind", split[4]);
//                                try{
//
//                                }catch (Exception e){
//
//                                }
//
//                                map.put("DocTitle", arg1.getTitle());
//                                map.put("come_share", "1");
////                                HTActivityUnit.switchTo(ClientTabActivity.this, DetailActivity.class, map);
//                                HTActivityUnit.switchTo(ClientTabActivity.this, WorkFlowFormDetalActivity.class, map);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
////                            Intent intent = new Intent();
////                            intent.setClass(ClientTabActivity.this,
////                                    DetailActivity.class);
////                            intent.putExtra("api_url", arg1.getAppUrl().toString());
////                            intent.putExtra("doc_title", arg1.getTitle());
////                            intent.putExtra("come_share","1");
////                            startActivity(intent);
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("ee")) {
//                            Intent intent = new Intent();
//                            intent.setAction("android.intent.action.VIEW");
//                            Uri content_url = Uri.parse(arg1.getAppUrl().substring(2, arg1.getAppUrl().length()));
//                            intent.setData(content_url);
//                            startActivity(intent);
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("TF")) {
//                            String appUrl = arg1.getAppUrl();
//                            String[] split = appUrl.split("\\|");
//                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                            try {
//                                HashMap map = new HashMap<String, String>();
//                                map.put("isShare", true);
//                                map.put("app_id", split[0].substring(2));
//                                map.put("userID", split[1]);
//                                map.put("app_version_id", split[2]);
//                                map.put("conditions", split[3]);
//                                map.put("order", split[4]);
//                                map.put("userName", split[5]);
//                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//                            } catch (NotApplicationException e) {
//                                e.printStackTrace();
//                            }
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("BT")) {
//                            try {
//                                String appUrl = arg1.getAppUrl();
//                                String[] split = appUrl.split("\\|");
//                                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                                AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                                HashMap map = new HashMap<String, String>();
//                                map.put("isShare", true);
//                                map.put("app_id", split[0].substring(2));
//                                map.put("userID", split[1]);
//                                map.put("app_version_id", split[2]);
////                                map.put("com_report_pie_mobileconfig_define", split[3]);
//                                map.put("com_report_pie_mobileconfig_include_security", split[4]);
//                                map.put("com_report_pie_mobileconfig_condition", split[5]);
//                                map.put("userName", split[6]);
//                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("CF")) {
//                            String appUrl = arg1.getAppUrl();
//                            String[] split = appUrl.split("\\|");
//                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                            try {
//                                HashMap map = new HashMap<String, String>();
//                                map.put("id", split[0].substring(2));
//                                map.put("form_id", split[1]);
//                                map.put("data_id", split[2]);
//                                map.put("app_id", split[3]);
//                                map.put("user_id", split[4]);
//                                map.put("subsystemid", split[5]);
//                                map.put("doc_title", split[6]);
//                                map.put("come_share", "1");
////                                HTActivityUnit.switchTo(ClientTabActivity.this, GeneralFormDetalActivity.class, map);
////                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("RC")) {
//                            String appUrl = arg1.getAppUrl();
//                            String[] split = appUrl.split("\\|");
//                            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                            AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                            try {
//                                HashMap map = new HashMap<String, String>();
//                                map.put("UserID", split[0].substring(2));
//                                map.put("app_id", split[1]);
//                                map.put("sch_id", split[2]);
//                                map.put("corpId", split[3]);
//                                map.put("groupCorpId", split[3]);
//                                map.put("flag", "2");
//                                HTActivityUnit.switchTo(ClientTabActivity.this, ScheduleDetailActivity.class, map);
////                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return true;
//                        } else if (arg1.getAppUrl().substring(0, 2).equals("ZX")) {
//                            try {
//                                String appUrl = arg1.getAppUrl();
//                                String[] split = appUrl.split("\\|");
//                                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(ClientTabActivity.this);
//                                AppInfo appInfo = mAppliationCenterDao.getAppInfo(split[0].substring(2));
//                                HashMap map = new HashMap<String, String>();
//                                map.put("isShare", true);
//                                map.put("app_id", split[0].substring(2));
//                                map.put("userID", split[1]);
//                                map.put("app_version_id", split[2]);
//                                map.put("com_report_barline_mobileconfig_condition", split[3]);
//                                map.put("order", split[4]);
//                                map.put("userName", split[5]);
//                                ClentAppUnit.getInstance(ClientTabActivity.this).setActivity(appInfo, map);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return true;
//                        } else {
//                            return false;
//                        }
//
//                    }
//                });

        /**
         * 启动广告页 （***在onCreate最后一行调用，否则容易出现背景view没有 广告页显示不出来问题***）
         */
        showAdv();
        /**
         * 登录成功后 判断是否是第一次启动应用，如果是第一次启动应用则需要调用第一次启动应用日志
         * 如果不是第一次启动跳过
         */
        logFristStart();
//        Toast.makeText(this, "欢迎您，" + PreferenceUtils.getOAUserName(this), Toast.LENGTH_SHORT).show();


        HtmitechApplication.instance().setLocation();
        /**
         * 如果没有发现第一次进入的时候
         */
        if (Constant.minutes < 0) {
            HtmitechApplication.instance().setLocation();
        }
    }

    private void logFristStart() {
        if (PreferenceUtils.isFristStartApp(this)) {
            PreferenceUtils.saveFristStartApp(this);
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            netWorkManager.logFunactionOnce(this, this, "frist_start", LogManagerEnum.APP_FIRST_START.functionCode);
        }
    }

    @Override
    protected void onPause() {
        try {
            //取消注册按下home键的广播
            RegistOrUnRegisterReceive.unRegisterHomeKeyEventReceive(this);
            if (mTabHost != null)
                beforeBack = mTabHost.getCurrentTab();
            com.htmitech.unit.BackgroundDetector.getInstance().onAppPause(ClientTabActivity.this);
            com.htmitech.unit.BackgroundDetector.getInstance().setDetectorStop(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateBadgeCount();


        super.onPause();
    }


    private void initView() {
        setContentView(R.layout.main_tab);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout_client);
        setupIntent();
        refreshAlertIcon();
//        //登陆成功 开始同步联系人
//        Intent i = new Intent(this, BookService.class);
//        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
//        startService(i);

    }

    //item 启动的页面
    private void setupIntent() {
        initTabhost();
    }


    public LeftMenuLayout getResideMenu() {
        return mResideMenu;
    }


    /* 初始化右侧按钮列表菜单 */
    private void initResideMenu() {

        mResideMenu.updateCurrentUserAvatar(BookInit.getInstance().currentUserName);

        /**
         * 获取左侧的所有
         */
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
        ArrayList<AppInfo> appinfoList = mAppliationCenterDao.getLeftAppInfo();


//        mResideMenu.setNumber(appinfoList.size());
        for (AppInfo mAppInfo : appinfoList) {
            ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mAppInfo);
            if (mApplicationAllEnum == null) {
                mApplicationAllEnum = ApplicationAllEnum.CJ;
                mApplicationAllEnum.compCode = mAppInfo.getComp_code();
            }

            mApplicationAllEnum.appId = mAppInfo.getApp_id() + "";
//            mApplicationAllEnum.compCode = mAppInfo.getComp_code();
            mApplicationAllEnum.name = mAppInfo.getApp_name();
            mApplicationAllEnum.url = mAppInfo.getPicture_normal();
            mApplicationAllEnum.url_disabled = mAppInfo.getPicture_normal();//2017-10-10 18:33:51 更改成normal
//            mApplicationAllEnum.url_disabled = mAppInfo.getPicture_disabled();
            mApplicationAllEnum.tab_item_id = mAppInfo.getTab_item_id();
            mApplicationAllEnum.appType = mAppInfo.getApp_type();
            mApplicationAllEnum.mAppInfo = mAppInfo;

            if (mApplicationAllEnum == ApplicationAllEnum.QCHC) {
                mApplicationAllEnum.name = mAppInfo.getApp_name();
            }
            /**
             * 如果是头像的话  将进行单独设置
             */
//            if (mApplicationAllEnum == ApplicationAllEnum.GRSZ) {
//                mResideMenu.setSlidUser(View.VISIBLE);
//            } else {
//
//            }
            ProxyDealLeftButtom mProxyDealLeftButtom = new ProxyDealLeftButtom(this, new CallBackLeftJC() {

                @Override
                public void onClickLeft(ApplicationAllEnum leftEnum) {
                    upgrade();
                }
            }, mApplicationAllEnum);
            ResideMenuItem mResideMenuItems = mProxyDealLeftButtom.leftShowIntent();
            if (mResideMenuItems != null)
                mResideMenu.addMenuItem(mResideMenuItems,
                        ResideMenu.DIRECTION_LEFT);
        }
//        com.alibaba.fastjson.JSONObject mJson = new com.alibaba.fastjson.JSONObject();
//        mJson.put("userId",PreferenceUtils.getEMPUserID(ClientTabActivity.this));
//        mJson.put("versionName", ResourceUtil.getVerName(ClientTabActivity.this));
//        mJson.put("versionNo", ResourceUtil.getVerCode(this) + "");
//        mJson.put("type","1");
//        mJson.put("envType",new AppliationCenterDao(ClientTabActivity.this).getAppClientEnvtype(ResourceUtil.getVerCode(this)) + "");
//        AnsynHttpRequest.requestByPostWithToken(this, mJson.toJSONString(), ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_VERSION_UPDATE, CHTTP.POSTWITHTOKEN, this, "upgrade", LogManagerEnum.APP_CENTER_DELETE.functionCode);

    }

    public void upgrade() {
        if (!Utils.checkConnectState(this)) {
            Utils.toast(this, R.string.upgrade_network_disable, Toast.LENGTH_SHORT);
            return;
        }

//        if (isUpdate) {
//            Utils.toast(this, R.string.upgrade_no_new_version, Toast.LENGTH_SHORT);
//            return;
//        }
//        AppUpgradeInfo upgradeInfo = new AppUpgradeInfo();
//        if (upgradeInfos != null && upgradeInfos.result != null) {
//            upgradeInfo.setUpgrade_url(upgradeInfos.result.downloadUrl);
//            upgradeInfo.setDescription(upgradeInfos.result.updateDesc);
//            upgradeInfo.setUpdateTime(upgradeInfos.result.createTime);
//            upgradeInfo.setVersion(upgradeInfos.result.versionName);
//            upgradeInfo.setVersion_code(upgradeInfos.result.versionNo);
//            upgradeInfo.setMandatoryUpgrade(upgradeInfos.result.mustupdated == 2 ? true : false);
//        }
        com.alibaba.fastjson.JSONObject mJson = new com.alibaba.fastjson.JSONObject();
        mJson.put("userId", PreferenceUtils.getEMPUserID(ClientTabActivity.this));
        mJson.put("versionName", ResourceUtil.getVerName(ClientTabActivity.this));
        mJson.put("versionNo", ResourceUtil.getVerCode(this) + "");
        mJson.put("type", "1");
        mJson.put("envType", new AppliationCenterDao(ClientTabActivity.this).getAppClientEnvtype(ResourceUtil.getVerCode(this)) + "");
        AnsynHttpRequest.requestByPostWithToken(this, mJson.toJSONString(), ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_VERSION_UPDATE, CHTTP.POSTWITHTOKEN, this, "upgrade", LogManagerEnum.APP_CENTER_DELETE.getFunctionCode());

//        Utils.showUpgradeDialog(this, upgradeInfo);
    }

    private void initTabhost() {
        this.mTabHost = (MenuTabHost) getTabHost();
        try {
            this.mTabHost.setCurrentTab(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.mTabHost.clearAllTabs();
        //获取首页
        currentPage = HomePageStyleEnum.getHomePageStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style).code;
        styleList = new ArrayList<MenuTabItem>();
        /**
         * 风格设置
         */
        ProxyDealLeftButtom mProxyDealLeftButtom = null;

        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
        ArrayList<AppInfo> mAppInfoList = mAppliationCenterDao.getButtomInfo();
        for (AppInfo mAppInfo : mAppInfoList) {

            ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mAppInfo);

            if (mApplicationAllEnum == null) {
                mApplicationAllEnum = ApplicationAllEnum.CJ;
                mApplicationAllEnum.compCode = mAppInfo.getComp_code();
            }
            mApplicationAllEnum.appId = mAppInfo.getApp_id() + "";
//            mApplicationAllEnum.compCode = mAppInfo.getComp_code();
            mApplicationAllEnum.name = mAppInfo.getApp_name();
            mApplicationAllEnum.url = mAppInfo.getPicture_normal();
            mApplicationAllEnum.url_disabled = mAppInfo.getPicture_normal();//2017-10-10 18:33:51 更改成normal
//          mApplicationAllEnum.url_disabled = mAppInfo.getPicture_disabled();
            mApplicationAllEnum.url_selected = mAppInfo.getPicture_selected();
            mApplicationAllEnum.tab_item_id = mAppInfo.getTab_item_id();
            mApplicationAllEnum.appType = mAppInfo.getApp_type();
            mApplicationAllEnum.mAppInfo = mAppInfo;

            if (mAppInfo.getApp_code().equals("app_platformhome")) {
                ArrayList<MenuTabItem> pageItem = null;
                if (currentPage.equalsIgnoreCase(HomeSet.METRO_PAGE)) { //磁铁风格
                    mProxyDealLeftButtom = new ProxyDealLeftButtom(this, mApplicationAllEnum, HomePageStyleEnum.CTFG, this);
                    pageItem = mProxyDealLeftButtom.getStyleMenuList();

                    HomeSetback = true;
                } else if (currentPage.equalsIgnoreCase(HomeSet.GRID_PAGE) || currentPage.equalsIgnoreCase(HomeSet.GROUP_FZ_PAGE)) {//网格风格
                    mProxyDealLeftButtom = new ProxyDealLeftButtom(this, mApplicationAllEnum, HomePageStyleEnum.WGFG, this);
                    pageItem = mProxyDealLeftButtom.getStyleMenuList();
                    RefreshTotal.addReshActivity();
                    HomeSetback = true;
                } else if (currentPage.equalsIgnoreCase(HomeSet.GROUP_PAGE)) {//分组导航风格
                    mProxyDealLeftButtom = new ProxyDealLeftButtom(this, mApplicationAllEnum, HomePageStyleEnum.FZDHFG, this);
                    pageItem = mProxyDealLeftButtom.getStyleMenuList();
                    HomeSetback = true;
                }

                if (pageItem != null) {
                    styleList.addAll(pageItem);
                }

            }


            if (HomeSetback) {

                for (MenuTabItem mMenuTabItem : styleList) {
                    if (mMenuTabItem == null) {
                        continue;
                    }
                    ApplicationAllEnum currentApplicationAllEnum = mMenuTabItem.getButtomEnum();
                    if (mApplicationAllEnum != null && currentApplicationAllEnum == mApplicationAllEnum) {
                        //去除重复的主页
                        HomeSetback = false;
                        mMenuTabItem.setmButtomEnum(mApplicationAllEnum);
                    }

                }
                if (!HomeSetback) {
                    continue;
                }
            }
            ProxyDealLeftButtom mProxyDealLeftButtoms = new ProxyDealLeftButtom(this, mApplicationAllEnum, this);
            //对移动端不支持的进行过滤
            if (mProxyDealLeftButtoms.ButtomShowIntent() != null) {
                styleList.add(mProxyDealLeftButtoms.ButtomShowIntent());
            }

            if (mAppInfo.getApp_code().equals("com_workflow_oa")) {
                //同步常用意见
                LoginModel loginModel = new LoginModel(ClientTabActivity.this);
                loginModel.getDataFromServerByType(LoginModel.TYPE_GetUserOptions,
                        OAConText.getInstance(HtmitechApplication.getInstance()).UserID + "&app_id=" + mAppInfo.getApp_id());
            }

        }

        int index = -1;
        /**
         * 获取代办下表
         */
        for (int i = 0; i < styleList.size(); i++) {
            if (styleList.get(i) != null && styleList.get(i).getButtomEnum() == ApplicationAllEnum.DB) {
                index = i;
                break;
            }

        }

        ConcerCountShow.getInstances().setMenuItemInfo(styleList);//将底部导航栏中的元素引用保留
        int mNumber = 0;
        for (MenuTabItem mMenuTabItem : styleList) {
            if (mMenuTabItem != null && mMenuTabItem.getButtomEnum() != null) {
                switch (mMenuTabItem.getButtomEnum()) {
                    case GT:
                        chatTabItem = mMenuTabItem;
                        mChatIntent = chatTabItem.getIntent();
                        break;
                    case GZQ:
                        circleTabItem = mMenuTabItem;
                        break;
                    case ZY:
                        number = mNumber;
                        mMenuTabItem.getIntent().putExtra("CurrentTabIndex", index);
                        break;
                }
                this.mTabHost.addMenuItem(mMenuTabItem);
            }
            mNumber++;
        }
        if (this.mTabHost.getMenuTabItem().size() <= 0) {
            this.mTabHost.getMenuTabItem().clear();

            String msg = String.format(getResources().getString(R.string.login_error_msg_not_application_cfg), BookInit.getInstance().getPortalName());
            Toast.makeText(ClientTabActivity.this, msg, Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(ClientTabActivity.this, LoginActivity.class);
            startActivity(mIntent);
            finish();
        }
        initChatListener();
        updateAll();

        if (number != -1 && mTabHost != null) {
            this.mTabHost.setCurrentTab(number);
        }
    }

    @Override
    public void resideMenu() {
//        ClientTabActivity.this.getResideMenu().openMenu(
//                ResideMenu.DIRECTION_LEFT);
//        ClientTabActivity.this.getResideMenu().
        drawer_layout.openDrawer(getResideMenu());
    }

    @Override
    public void moveTaskToBack() {
        moveTaskToBack(true);
    }


    private void initChatListener() {
        ConcreteLogin.getInstance().initChatListener(this, new htmitech.com.componentlibrary.listener.ShareListener() {
            @Override
            public void onSuccess() {
                showTabByTag(MenuTabHost.TAB_TAG_CHAT);
            }
        }, new ChatFinishListener() {
            @Override
            public void onBackToChatRoot(Context context) {
                Intent intent = new Intent(context, ClientTabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_right_out);
                showTabByTag(MenuTabHost.TAB_TAG_CHAT);
            }
        });
    }


    private void updateAll() {
        int unreadMessage = ConcreteLogin.getInstance().badgeMXCount(this);
        if (unreadMessage == -1) {
            return;
        }
        if (unreadMessage > 0) {
            String unReadCount = unreadMessage <= 99 ? String
                    .valueOf(unreadMessage) : "...";
            if (chatTabItem != null)
                chatTabItem.showNumberMarker(unReadCount);
        } else {
            if (chatTabItem != null)
                chatTabItem.hideNumberMarker();
        }

        if (ConcreteLogin.getInstance().checkNetworkCircleUnread(this)) {
            if (circleTabItem != null)
                circleTabItem.showMarker();
            if (mTabHost != null && mTabHost.getCurrentTabTag() != null && mTabHost.getCurrentTabTag().equals(MenuTabHost.TAB_TAG_CIRCLES)) {
                boolean isAPPSwitchToBackground = BackgroundDetector
                        .getInstance().isApplicationSentToBackground(this);
                boolean isScreenLocked = BackgroundDetector.getInstance()
                        .isScreenLocked(this);
                if (!isAPPSwitchToBackground && !isScreenLocked) {
                    ConcreteLogin.getInstance().setCircleAutoRefresh(this);
                }
            } else {
                ConcreteLogin.getInstance().setCircleAutoRefresh(this);
            }
        } else {
            if (circleTabItem != null)
                circleTabItem.hideMarker();
        }
        refreshAlertIcon();
        updateBadgeCount();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (HtmitechApplication.isHomeBack) {
            if (netWorkManager == null) {
                netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
            }

            netWorkManager.logFunactionOnce(this, this, "login_home_back", LogManagerEnum.APP_RESUME.functionCode);
            HtmitechApplication.isHomeBack = false;
        }
        //注册监听按home键的广播
        RegistOrUnRegisterReceive.registerHomeKeyEventReceive(this);
        if (getIntent().getAction() != null
                && !"".equals(getIntent().getAction())
                && getIntent().getAction().equals("finish")) {
//            DBCipherManager.getInstance(this).db.close();
            // 打开登录页面
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("finish");
            intent.putExtra(
                    AppConstants.SYSTEM_START_TYPE_APP2APP,
                    getIntent().getBooleanExtra(
                            AppConstants.SYSTEM_START_TYPE_APP2APP, false));
            intent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, getIntent()
                    .getIntExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1));
            startActivity(intent);
            finish();
            return;
        }
        if(PreferenceUtils.getIsEMIUser(ClientTabActivity.this) != 0){
            if (ConcreteLogin.getInstance().isCurrentUserNull(this)) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return;
            }
        }

        int groupID = getIntent().getIntExtra(
                MXConstant.IntentKey.SHOW_CURRENT_GROUP_WORK_CIRCLE, -1);
        if (groupID != -1) {
            switchToCircle(groupID);
            return;
        }

        if (getIntent().getBooleanExtra(SHOW_CHAT_LIST_FLAG, false)) {
            getIntent().removeExtra(SHOW_CHAT_LIST_FLAG);
            int chatID = getIntent().getIntExtra(AUTO_ENTER_CHAT_ID, -999);
            if (chatID == -999) {
                return;
            }
            NotificationUtil.clearAllNotification(this);
            getIntent().removeExtra(AUTO_ENTER_CHAT_ID);
            isAutoEnterChat = true;
            autoEnterChatID = chatID;
            if (BackgroundDetector.getInstance().isGesturePwdViewEnabled()
                    || BackgroundDetector.getInstance().isPasswordCheckActive()) {
                return;
            }
        }
        autoEnterChat();
        if (HomeSetback) {
            initTabhost();//刷新底部菜单
            HomeSetback = false;
        }
        if (mResideMenu != null) {
            mResideMenu.updateCurrentUserAvatar(BookInit.getInstance().getCurrentUserName());
        }
        Intent intentMessageFlag = new Intent(this, PortalMessageService.class);
        startService(intentMessageFlag);
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), PreferenceUtils.PREFERENCE_SYSTEM, Context.MODE_PRIVATE);
        ischeckupgrade = securitySharedPreference.getBoolean("ischeckupgrade", false);
        if (!isChangeProtal && ischeckupgrade) {
            //升级服务
            Intent mIntent = new Intent(ClientTabActivity.this, UpgradeFromDatabaseServices.class);
            startService(mIntent);
        }
    }

    private void autoEnterChat() {
        if (!isAutoEnterChat || autoEnterChatID == -1) {
            isAutoEnterChat = false;
            autoEnterChatID = -1;
            return;
        }
        if (MenuTabHost.TAB_TAG_CHAT.equals(mTabHost.getCurrentTabTag())) {
            Intent autoEnterIntent = new Intent(
                    MXConstant.BroadcastAction.MXKIT_AUTO_ENTER_CHAT);
            autoEnterIntent
                    .putExtra(MXConstant.IntentKey.SHOW_CURRENT_CHAT_ID,
                            autoEnterChatID);
            sendBroadcast(autoEnterIntent);
        } else {
            mChatIntent.putExtra(MXConstant.IntentKey.SHOW_CURRENT_CHAT_ID,
                    autoEnterChatID);
            mTabHost.setCurrentTabByTag(MenuTabHost.TAB_TAG_CHAT);
        }
        isAutoEnterChat = false;
        autoEnterChatID = -1;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        recreate();
    }

    @Override
    protected void onStart() {

        if (BackgroundDetector.getInstance().isGesturePwdViewEnabled()) {
            Intent intent = new Intent(this, GesturePasswordActivity.class);
            intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY,
                    GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);
            BackgroundDetector.getInstance().setPasswordCheckActive(true);
        }else if(com.htmitech.unit.BackgroundDetector.getInstance().isGestureZWPwdViewEnabled()){
            setIntent(this,3);
            com.htmitech.unit.BackgroundDetector.getInstance().setPasswordCheckActive(true);
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        try {
            this.unregisterReceiver(receiver);
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BadgeAllUnit.get().clearBadgeUnit();
        ComponentInit.getInstance().getmILogUpdateCallListener().closeMseeage();
        super.onDestroy();
    }

    protected void updateNetworkTitle(String name) {
        if (chatHandler != null) {
            ((TextView) chatHandler.findViewById(R.id.current_network))
                    .setText(name);
        }
        if (contactsHandler != null) {
            ((TextView) contactsHandler.findViewById(R.id.current_network))
                    .setText(name);
        }
        if (appCenterHandler != null) {
            ((TextView) appCenterHandler.findViewById(R.id.current_network))
                    .setText(name);
        }
        if (circleHandler != null) {
            ((TextView) circleHandler.findViewById(R.id.current_network))
                    .setText(name);
        }
    }

    private void switchToCircle(int groupID) {
        ConcreteLogin.getInstance().switchCircleGroup(groupID);
        getIntent().removeExtra(
                MXConstant.IntentKey.SHOW_CURRENT_GROUP_WORK_CIRCLE);
        mTabHost.setCurrentTabByTag(MenuTabHost.TAB_TAG_CIRCLES);
    }

    private void refreshAlertIcon() {
        ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
        mConcreteLogin.refreshAlertIcon(this, PreferenceUtils.checkUpgradeMark(this), R.id.work_circle_icon, chatHandler, contactsHandler, appCenterHandler, circleHandler);
    }

//    private void refreshHandlerLayout(View handlerView,
//                                      MXCurrentUser currentUser) {
//        if (handlerView != null) {
//            TextView unreadNum = (TextView) handlerView
//                    .findViewById(R.id.sms_num);
//            ImageView unreadIcon = (ImageView) handlerView
//                    .findViewById(R.id.work_circle_icon);
//
//            List<MXNetwork> networks = currentUser.getNetworks();
//            boolean isCircleUnread = false;
//            for (MXNetwork network : networks) {
//                if (currentUser.getNetworkID() != network.getId()) {
//                    if (!isCircleUnread) {
//                        isCircleUnread = MXAPI.getInstance(this)
//                                .checkNetworkCircleUnread(network.getId());
//                    }
//                }
//            }
//
//            int unReadChatMessage = MXAPI.getInstance(this)
//                    .queryNonCurrentNetworkChatUnread(
//                            currentUser.getNetworkID());
//
//            if (unReadChatMessage > 0 || isCircleUnread) {
//                if (unReadChatMessage > 0) {
//                    unreadNum.setVisibility(View.GONE);
//                    unreadIcon.setVisibility(View.GONE);
//                    String unReadCount = unReadChatMessage <= 99 ? String
//                            .valueOf(unReadChatMessage) : "...";
//                    unreadNum.setText(unReadCount);
//                } else {
//                    unreadNum.setVisibility(View.GONE);
//                    unreadIcon.setVisibility(View.GONE);
//                    unreadNum.setText("");
//                }
//            } else {
//                unreadNum.setVisibility(View.GONE);
//                unreadIcon.setVisibility(View.GONE);
//                unreadNum.setText("");
//            }
//
//            if (unReadChatMessage == 0 && !isCircleUnread) {
//                if (PreferenceUtils.checkUpgradeMark(this)) {
//                    unreadIcon.setVisibility(View.GONE);
//                } else {
//                    unreadIcon.setVisibility(View.GONE);
//                }
//            }
//        }
//    }

    private void showTabByTag(String tag) {
        if (mTabHost != null) {
            mTabHost.setCurrentTabByTag(tag);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                // long spent = System.currentTimeMillis() - mInterval;
                // if (spent < mDiffTime) {
                // finish();
                // } else {
                // ToastInfo toast = ToastInfo.getInstance(HtmitechApplication
                // .instance());
                // toast.setView(
                // LayoutInflater.from(HtmitechApplication.instance()),
                // R.drawable.prompt_warn, R.string.app_exit_tip);
                // toast.show(Toast.LENGTH_SHORT);
                // mInterval = System.currentTimeMillis();
                // }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //网络操作-----获得当前用户的常用意见

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        // TODO Auto-generated method stub
        if (requestTypeId == LoginModel.TYPE_GetUserOptions) {

            if (result != null && result instanceof UserOptionListEntity) {
                UserOptionListEntity UserOptionListResult = (UserOptionListEntity) result;
                StringBuilder sb = new StringBuilder();
                if (UserOptionListResult.getResult() != null && UserOptionListResult.getResult().getItems() != null) {
                    for (int i = 0; i < UserOptionListResult.getResult().getItems().size(); i++) {
                        if (sb.length() > 0)
                            sb.append("|");
                        sb.append(UserOptionListResult.getResult().getItems().get(i).getValue());
                    }
                    AppPreferenceHelper.getInstance(this).putString(PreferenceKeys.KEY_OPINION_SAVE, sb.toString());
                }
            }
        }
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg,
                       Object result) {
        // TODO Auto-generated method stub
        //登陆失败逻辑
        Utils.toast(ClientTabActivity.this, "获取常用意见失败," + statusCode + ":" + errorMsg, Toast.LENGTH_SHORT);

    }

    @Override
    public void callbackSendMessage(String userID) {
        ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
        mConcreteLogin.chatMX(this, userID);
//        if (userID.equalsIgnoreCase("admin")) {
//            Toast.makeText(this, "不能发起对admin的聊天",
//                    Toast.LENGTH_SHORT).show();
//        } else if (userID.equalsIgnoreCase(MXAPI.getInstance(this)
//                .currentUser().getLoginName())) {
//            Toast.makeText(this, "不能发起对自己的聊天", Toast.LENGTH_SHORT)
//                    .show();
//        } else {
//
//            Log.d("FlowFragment", "发起聊天，对：" + userID);
//            String[] loginNames = new String[]{userID};
//            // TODO Auto-generated method stub
//            MXAPI.getInstance(this).chat(loginNames,
//                    new MXApiCallback() {
//                        @Override
//                        public void onLoading() {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onFail(MXError arg0) {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onSuccess() {
//                            // TODO Auto-generated method stub
//
//                        }
//                    });
//        }
    }

    @Override
    public void callbackGroups() {

    }

    @Override
    public void callbackFunction() {

    }

    @Override
    public void callbackSpecialFocus() {

    }

    @Override
    public void callbackSpecialFocusButton() {

    }

    @Override
    public void callAddBook() {

    }

    @Override
    public void callBackMain() {

    }

    @Override
    public void callUserMeesageMain() {
//        ClientTabActivity.this.getResideMenu().openMenu(
//                ResideMenu.DIRECTION_LEFT);
        drawer_layout.openDrawer(getResideMenu());
    }

    @Override
    public void callFunction() {
        try {
            if (Constant.FUNCATION_CODE) {
                Intent mIntent = new Intent(this, ContactOcuActivity.class);
                startActivity(mIntent);
            }
        } catch (Exception e) {

        }
    }

    public T_UserRelationship mT_UserRelationship;
    public String app_id;

    @Override
    public void callAddCUser(final T_UserRelationship mT_UserRelationship, String app_id) {
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        this.mT_UserRelationship = mT_UserRelationship;
        this.app_id = app_id;
        netWorkManager.logFunactionStart(ClientTabActivity.this, this, LogManagerEnum.ADD_USER_RELATIONSHIP);

    }

    @Override
    public void callRemoveCUser(final T_UserRelationship mT_UserRelationship, String app_id) {
        this.mT_UserRelationship = mT_UserRelationship;
        this.app_id = app_id;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        netWorkManager.logFunactionStart(ClientTabActivity.this, ClientTabActivity.this, LogManagerEnum.REMOVE_USER_RELATIONSHIP);
    }

    public void closeDrawer() {
        try {
            drawer_layout.closeDrawer(getResideMenu());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePortalSizeStyle(String using_home_style, String using_color_style, String using_font_style) {
        String currentUserId = BookInit.getInstance().getCrrentUserId();
        String currentPortalId = BookInit.getInstance().getPortalId();

        OtherParams mOtherParams = new OtherParams();
        mOtherParams.setUsing_color_style(using_color_style);
        mOtherParams.setUsing_home_style(using_home_style);
        mOtherParams.setUsing_font_style(using_font_style);

        SysDefault mSysDefault = new SysDefault();
        mSysDefault.setPortal_id(currentPortalId);
        mSysDefault.setUser_id(currentUserId);
        mSysDefault.setOtherParams(mOtherParams);
        String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.USET_PORTAL_DEFINE;
        AnsynHttpRequest.requestByPostWithToken(this, mSysDefault, url, CHTTP.POSTWITHTOKEN, this, PORTAL_DEFAULT, LogManagerEnum.APP_CENTER_ADD.functionCode);
    }

    @Override
    public void callSavePeopleMessage(OrgUser mSYS_User, final int type, String bittoBase64) {
        mSYS_User.setEfs2(BookInit.getInstance().getPortalId() + "");
        if (netWorkManager == null) {
            netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        }
//        final UpdateMessage mUpdateMessage = new UpdateMessage();
//        mUpdateMessage.UserInfo = mSYS_User;
//        mUpdateMessage.context = OAConText.getInstance(HtmitechApplication.instance());
        uploadPicRequestBean = new UploadPicRequestBean();
        uploadPicRequestBean.setEmiType(mSYS_User.getEmi_type());
        uploadPicRequestBean.setUserId(mSYS_User.getUser_id() + "");
        uploadPicRequestBean.setBirthday(mSYS_User.getBirthday());
        uploadPicRequestBean.setDisplay_order(mSYS_User.getDisplay_order());
//        uploadPicRequestBean.setEfd1(mSYS_User.getEfd1().replace(".0",""));
//        uploadPicRequestBean.setEfd2(mSYS_User.getEfd2().replace(".0",""));
        uploadPicRequestBean.setEfi1(mSYS_User.getEfi1());
        uploadPicRequestBean.setEfi2(mSYS_User.getEfi2());
        uploadPicRequestBean.setEfi3(mSYS_User.getEfi3());
        uploadPicRequestBean.setEfi4(mSYS_User.getEfi4());
        uploadPicRequestBean.setEfi5(mSYS_User.getEfi5());
        uploadPicRequestBean.setEfn1(mSYS_User.getEfn1());
        uploadPicRequestBean.setEfn2(mSYS_User.getEfn2());
        uploadPicRequestBean.setEfn3(mSYS_User.getEfn3());
        uploadPicRequestBean.setEfs2(mSYS_User.getEfs2());
        uploadPicRequestBean.setEfs3(mSYS_User.getEfs3());
        uploadPicRequestBean.setEfs4(mSYS_User.getEfs4());
        uploadPicRequestBean.setEfs5(mSYS_User.getEfs5());
        uploadPicRequestBean.setEfs6(mSYS_User.getEfs6());
        uploadPicRequestBean.setEfs7(mSYS_User.getEfs7());
        uploadPicRequestBean.setEfs8(mSYS_User.getEfs8());
        uploadPicRequestBean.setEfs9(mSYS_User.getEfs9());
        uploadPicRequestBean.setEfs10(mSYS_User.getEfs10());
        uploadPicRequestBean.setEmail(mSYS_User.getEmail());
        uploadPicRequestBean.setFax(mSYS_User.getFax());
        uploadPicRequestBean.setGender(mSYS_User.getGender());
        uploadPicRequestBean.setGroupCorpId(mSYS_User.getGroup_corp_id());
        uploadPicRequestBean.setHomePhone(mSYS_User.getHome_phone());
        uploadPicRequestBean.setLoginName(mSYS_User.getLogin_name());
        uploadPicRequestBean.setLoginPassword(mSYS_User.getLogin_password());
        uploadPicRequestBean.setMobilePhone(mSYS_User.getMobile_phone());
        uploadPicRequestBean.setOfficeAddress(mSYS_User.getOffice_address());
        uploadPicRequestBean.setOfficePhone(mSYS_User.getOffice_phone());
        uploadPicRequestBean.setPostalCode(mSYS_User.getPostal_code());
        uploadPicRequestBean.setRemark(mSYS_User.getRemark());
        uploadPicRequestBean.setStatusFlag(1);
        uploadPicRequestBean.setThirdUserId(OAConText.getInstance(this).OA_UserId);
        uploadPicRequestBean.setTitle(mSYS_User.getTitle());
        uploadPicRequestBean.setUserCode(mSYS_User.getUser_code());
        uploadPicRequestBean.setUserName(mSYS_User.getUser_name());
        uploadPicRequestBean.setUserNickname(mSYS_User.getUser_nickname());
        uploadPicRequestBean.setUserPyname(mSYS_User.getUser_pyname());
        uploadPicRequestBean.setEmiType(1);
        readyStartSavePeopleMessage(type, mRequestPeopleModel, uploadPicRequestBean, bittoBase64);
    }

    public void readyStartSavePeopleMessage(int type, RequestPeopleModel mRequestPeopleModel, UploadPicRequestBean mUpdateMessage, String bittoBase64) {
        //修改头像的修改个人信息用的是一套appID 和appVersionID
        String appID = ApplicationAllEnum.GRSZ.mAppInfo.getApp_id() + "";
        String appVersionID = "0";
        if (1 == type) {//修改个人信息
            LogManagerEnum.MYINFO_UPDATE_INFO.appVersionId = appVersionID;
            LogManagerEnum.MYINFO_UPDATE_INFO.app_id = appID;
            startLog(type, LogManagerEnum.MYINFO_UPDATE_INFO, mRequestPeopleModel, mUpdateMessage, bittoBase64);
        } else if (2 == type) {//修改头像
            LogManagerEnum.MYINFO_UPDATE_PIC.appVersionId = appVersionID;
            LogManagerEnum.MYINFO_UPDATE_PIC.app_id = appID;
            startLog(type, LogManagerEnum.MYINFO_UPDATE_PIC, mRequestPeopleModel, mUpdateMessage, bittoBase64);
        }
    }

    public void startLog(final int type, LogManagerEnum mLogManagerEnum, final RequestPeopleModel mRequestPeopleModel, final UploadPicRequestBean mUpdateMessage, final String bittoBase64) {
        netWorkManager.logFunactionStart(this, new ObserverCallBackType() {

            @Override
            public void success(String requestValue, int typeLog, String requestName) {
                if (type == 2)
                    uploadPic(bittoBase64);
                else {
                    updateUserMessageUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATA_USER_MESSAGE_JAVA;
                    AnsynHttpRequest.requestByPostWithToken(ClientTabActivity.this, mUpdateMessage, updateUserMessageUrl, CHTTP.POSTWITHTOKEN, ClientTabActivity.this, UPDATEUSERMESSAGE, LogManagerEnum.MYINFO_UPDATE_INFO.functionCode);
//                    mRequestPeopleModel.getDataFromServerByType(type, mUpdateMessage);
                }
            }

            @Override
            public void fail(String exceptionMessage, int typeLog, String requestName) {
                if (type == 2)
                    uploadPic(bittoBase64);
                else {
                    updateUserMessageUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATA_USER_MESSAGE_JAVA;
                    AnsynHttpRequest.requestByPostWithToken(ClientTabActivity.this, mUpdateMessage, updateUserMessageUrl, CHTTP.POSTWITHTOKEN, ClientTabActivity.this, UPDATEUSERMESSAGE, LogManagerEnum.MYINFO_UPDATE_INFO.functionCode);
//                    mRequestPeopleModel.getDataFromServerByType(type, mUpdateMessage);
                }
            }

            @Override
            public void notNetwork() {

            }

            @Override
            public void callbackMainUI(String successMessage) {

            }
        }, mLogManagerEnum);
    }

    /*
    * 修改个人头像前需要先上传头像
    * */
    private void uploadPic(String bittoBase64) {
        getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FilecontrollerPicture;
        filePic = new File(bittoBase64);
        AnsynHttpRequest.requestByPostWithToken(this, filePic, getPictureUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPEPICTUTR, LogManagerEnum.MYINFO_UPDATE_PIC.getFunctionCode());
    }

    /**
     * 同步完成
     *
     * @param flag
     */
    @Override
    public void sysUserSuccess(boolean flag) {
        mResideMenu.updateCurrentUserAvatar("");
    }

    @Override
    public void settingGender(Context context, final EditText editText) {
        if (mPopBirthHelperGender == null) {
            mPopBirthHelperGender = new PopChooseTimeHelper(context);
            mPopBirthHelperGender.setTimeEnums(TimeEnum.TIME);
        } else {
            if (mPopBirthHelperGender.isShowing()) {
                mPopBirthHelperGender.dismiss();
            }

        }
        mPopBirthHelperGender.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {

            @Override
            public void onClickOk(String birthday) {
                // TODO Auto-generated method stub
                mPopBirthHelperGender.dismiss();
                if (!birthday.equals("")) {
                    editText.setText(birthday);
                }

            }
        });

        mPopBirthHelperGender.show(editText);
    }


    @Override
    public void settingData(Context context, final EditText editText) {
        if (mPopBirthHelper == null) {
            mPopBirthHelper = new PopChooseTimeHelper(context);
            mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
        } else {
            if (mPopBirthHelper.isShowing()) {
                mPopBirthHelper.dismiss();
            }

        }
        mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {

            @Override
            public void onClickOk(String birthday) {
                // TODO Auto-generated method stub
                mPopBirthHelper.dismiss();
                if (!birthday.equals("")) {
                    String bString = birthday.split("-")[0] + "/" + birthday.split("-")[1] + "/" + birthday.split("-")[2];
                    editText.setText(bString);
                }

            }
        });

        mPopBirthHelper.show(editText);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        if (event.msg.equals("ynchronizeData")) {
            ButtomColorFactory.getInstance(this).setButtomListColor();
            LeftColorFactory.getInstance(this).setLeftBarColor();
        }
        if (event.msg.equals("TextViewSize") || event.msg.equals("ynchronizeData") || event.msg.equals("TXZX")) {
            isUpdate = false;
            isChangeProtal = true;
            recreate();
        }
        if (event.msg.equals("portalSwitch")) {
            HtmitechApplication.isAdvShow = true;
            isUpdate = false;
            isChangeProtal = true;
            recreate();
        }
        if (event.msg.equals("angle")) { //更新底部角标
            if (this.mTabHost != null && this.mTabHost.getMenuTabItem() != null) {
                for (MenuTabItem mMenuTabItem : this.mTabHost.getMenuTabItem()) {
                    MenuTabItemFactory.getInstance().setAllAngleNumber(mMenuTabItem);
                }
            }

        } else if (event.msg.equals("ZYJB")) {
            for (MenuTabItem mMenuTabItem : this.mTabHost.getMenuTabItem()) {
                switch (mMenuTabItem.getButtomEnum()) {
                    case ZY:
                        if (event.number > 0) {
//                            mMenuTabItem.showNumberMarker(event.number > 99 ? 99 + "+" : event.number + "");
                            mMenuTabItem.showNumberMarker();
                        } else {
                            mMenuTabItem.hideMarker();
                        }
                        break;
                    case YYZX:
                        if (event.number > 0) {
//                            mMenuTabItem.showNumberMarker(event.number > 99 ? 99 + "+" : event.number + "");
                            mMenuTabItem.showNumberMarker();
                        } else {
                            mMenuTabItem.hideMarker();
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /**
         * 状态
         */
        closeDrawer();
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("login_home_back")) {
            Log.d("ClientTabActivity", "唤醒成功");
        } else if (requestName.equals("upgrade")) {
            if (requestValue != null && !"".equals(requestValue.trim()))
                upgradeInfos = com.alibaba.fastjson.JSONObject.parseObject(requestValue, EmpmAppUpgradeInfo.class);
            if (upgradeInfos != null && upgradeInfos.result != null) {
                isUpdate = false;
                UpdateServiceHanle mUpdateServiceHanle = new UpdateServiceHanle(ClientTabActivity.this);
                mUpdateServiceHanle.showDialog(upgradeInfos.result);
            } else {
                isUpdate = true;
                Utils.toast(this, R.string.upgrade_no_new_version, Toast.LENGTH_SHORT);
            }
        } else if (requestName.equals(HTTPTYPEPICTUTR)) {    //个人头像上传
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getPictureUrl, filePic, this, requestName, LogManagerEnum.MYINFO_UPDATE_PIC.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
//                GetPictureResult mGetPictureResult = mGson.fromJson(requestValue.toString(), GetPictureResult.class);
                Toast.makeText(ClientTabActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                GetPictureResult mGetPictureResult = com.alibaba.fastjson.JSONObject.parseObject(requestValue.toString(), GetPictureResult.class);
                uploadPicRequestBean.setHeadPictureId(mGetPictureResult.getResult().pictureId + "");
//                mRequestPeopleModel.getDataFromServerByType(2, uploadPicRequestBean);
                updateUserMessageUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.UPDATA_USER_MESSAGE_JAVA;
                AnsynHttpRequest.requestByPostWithToken(ClientTabActivity.this, uploadPicRequestBean, updateUserMessageUrl, CHTTP.POSTWITHTOKEN, this, UPDATEUSERMESSAGE, LogManagerEnum.MYINFO_UPDATE_INFO.functionCode);
            }
        } else if (requestName.equals(UPDATEUSERMESSAGE)) {    //个人信息修改
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, updateUserMessageUrl, uploadPicRequestBean, this, requestName, LogManagerEnum.MYINFO_UPDATE_PIC.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                com.alibaba.fastjson.JSONObject resultObject = com.alibaba.fastjson.JSONObject.parseObject(requestValue);
                if (null != resultObject) {
                    if (null != resultObject.get("code") && resultObject.get("code").equals(200)) {
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
//                mContactsIntent.putExtra("addressFragmentType", Constant.LOING_INIT);
                        mIntent.putExtra("value", 5);
                        //发送广播
                        ClientTabActivity.this.sendBroadcast(mIntent);
                    } else {
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 6);
                        //发送广播
                        ClientTabActivity.this.sendBroadcast(mIntent);
                    }
                }

            }


        } else if (requestName.equals(PORTAL_DEFAULT)) {

        } else if (requestName.equals(LogManagerEnum.ADD_USER_RELATIONSHIP.functionCode)) {
            if (mT_UserRelationship != null) {
                if (mT_UserRelationship != null) {
                    mAddContactUser = new AddOrDeleteUser();
                    mAddContactUser.appId = app_id;
                    mAddContactUser.contactId = mT_UserRelationship.getmSYS_User().getUserId();
                    mAddContactUser.contactName = mT_UserRelationship.getmSYS_User().getFullName();
                    mAddContactUser.userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
                    AnsynHttpRequest.requestByPostWithToken(ClientTabActivity.this, mAddContactUser, addContactURL, CHTTP.POSTWITHTOKEN, this, ADDCONTACT, LogManagerEnum.ADD_USER_RELATIONSHIP.functionCode);
                }
            }

        } else if (ADDCONTACT.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, addContactURL, mAddContactUser, this, requestName, LogManagerEnum.ADD_USER_RELATIONSHIP.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                netWorkManager.logFunactionFinsh(ClientTabActivity.this, ClientTabActivity.this, "addRelationUserSuccess",
                        LogManagerEnum.ADD_USER_RELATIONSHIP.functionCode, requestValue, INetWorkManager.State.SUCCESS);
                com.alibaba.fastjson.JSONObject resultObject = com.alibaba.fastjson.JSONObject.parseObject(requestValue);
                if (null != resultObject) {
                    if (null != resultObject.get("code") && resultObject.get("code").equals(200)) {
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 2);
                        mIntent.putExtra("userId", mT_UserRelationship.getUserId());
                        //发送广播
                        ClientTabActivity.this.sendBroadcast(mIntent);
                    } else {
                        if (null != resultObject)
                            Toast.makeText(ClientTabActivity.this, "添加联系人失败！" + resultObject.get("debugMsg"), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        } else if (requestName.equals(LogManagerEnum.REMOVE_USER_RELATIONSHIP.functionCode)) {
            if (mT_UserRelationship != null) {
                mDeleteContactUser = new AddOrDeleteUser();
                mDeleteContactUser.appId = app_id;
                mDeleteContactUser.contactId = mT_UserRelationship.getmSYS_User().getUserId();
                mDeleteContactUser.contactName = mT_UserRelationship.getmSYS_User().getFullName();
                mDeleteContactUser.userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
                AnsynHttpRequest.requestByPostWithToken(ClientTabActivity.this, mDeleteContactUser, deleteContactURL, CHTTP.POSTWITHTOKEN, this, DELETECONTACT, LogManagerEnum.REMOVE_USER_RELATIONSHIP.functionCode);
            }
        } else if (DELETECONTACT.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, deleteContactURL, mDeleteContactUser, this, requestName, LogManagerEnum.REMOVE_USER_RELATIONSHIP.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                netWorkManager.logFunactionFinsh(ClientTabActivity.this, ClientTabActivity.this, "removeRelationUserSuccess",
                        LogManagerEnum.REMOVE_USER_RELATIONSHIP.functionCode, requestValue, INetWorkManager.State.SUCCESS);
                com.alibaba.fastjson.JSONObject resultObject = com.alibaba.fastjson.JSONObject.parseObject(requestValue);
                if (null != resultObject) {
                    if (null != resultObject.get("code") && resultObject.get("code").equals(200)) {
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 3);
                        mIntent.putExtra("CUserId", mT_UserRelationship.getCUserId());
                        //发送广播
                        ClientTabActivity.this.sendBroadcast(mIntent);
                    } else {
                        Toast.makeText(ClientTabActivity.this, "删除联系人失败！" + resultObject.get("debugMsg"), Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
//                    mContactsIntent.putExtra("addressFragmentType", Constant.LOING_INIT);
                        mIntent.putExtra("value", 4);
                        //发送广播
                        ClientTabActivity.this.sendBroadcast(mIntent);
                    }
                }
            }
        } else if ("frist_start".equals(requestName)) {

        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Log.e("右侧边栏升级", exceptionMessage + "");
        if (requestName.equals("upgrade")) {
            Utils.toast(this, "未获取到服务器版本信息", Toast.LENGTH_SHORT);
        } else if ("frist_start".equals(requestName)) {

        } else if (HTTPTYPEPICTUTR.equals(requestName)) {
//            Utils.toast(this, "头像上传失败", Toast.LENGTH_SHORT);
        } else if (requestName.equals(UPDATEUSERMESSAGE)) {
            Intent mIntent = new Intent(Constant.ACTION_NAME);
//                mContactsIntent.putExtra("addressFragmentType", Constant.LOING_INIT);
            mIntent.putExtra("value", 6);
            //发送广播
            ClientTabActivity.this.sendBroadcast(mIntent);
        } else if (ADDCONTACT.equals(requestName)) {
            Toast.makeText(ClientTabActivity.this, "添加联系人失败！" + exceptionMessage, Toast.LENGTH_SHORT).show();
        } else if (deleteContactURL.equals(requestName)) {
            netWorkManager.logFunactionFinsh(ClientTabActivity.this, ClientTabActivity.this, "removeRelationUserSuccess",
                    LogManagerEnum.REMOVE_USER_RELATIONSHIP.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            Toast.makeText(ClientTabActivity.this, "删除联系人失败！" + exceptionMessage, Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(Constant.ACTION_NAME);
//                    mContactsIntent.putExtra("addressFragmentType", Constant.LOING_INIT);
            mIntent.putExtra("value", 4);
            //发送广播
            ClientTabActivity.this.sendBroadcast(mIntent);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    /**
     * 桌面角标的更新
     */
    private void updateBadgeCount() {
        final int unreadMessage = ConcreteLogin.getInstance().badgeMXCount(this);
        if (unreadMessage == -1) {
            return;
        }
//        final int unreadMessage = MXAPI.getInstance(this).queryNetworkChatUnread(
//                currentUser.getNetworkID());


        String path = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_COUNTNEED_Count_JAVA;
//        String path = "http://114.112.89.94:8081/testv7/api/GetMobileData/SetAttentionYesOrNo";
        AsyncHttpClient client = new AsyncHttpClient();
        OAConText instance = OAConText.getInstance(ClientTabActivity.this);
        String refreshToken = PreferenceUtils.getRefreshToken();
        String accessToken = PreferenceUtils.getAccessToken();
        client.addHeader("accessToken", accessToken);
        client.addHeader("refreshToken", refreshToken);
        client.addHeader("Content-Type", "application/json");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", instance.UserID);
            jsonObject.put("portalId", BookInit.getInstance().getPortalId());
            jsonObject.put("groupCorpId", BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id() + "");
            StringEntity stringEntity = new StringEntity(
                    jsonObject.toString(), "utf-8");
            RequestHandle post = client.post(ClientTabActivity.this, path,
                    stringEntity, null, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              JSONObject response) {
                            try {
//                                Log.d("ClientTabActivity", "onSuccess: " + response.getInt("result"));
                                if (response != null) {
                                    JSONObject countJSON = response.getJSONObject("result");
                                    if (countJSON != null) {
                                        int countStr = countJSON.getInt("count");
                                        int countTemp = 0;
                                        countTemp = countStr + unreadMessage;
                                        if (countTemp > 0) {
                                            BadgeUtil.setBadgeCount(ClientTabActivity.this, countTemp);
                                        } else {
                                            BadgeUtil.resetBadgeCount(ClientTabActivity.this);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            super.onSuccess(statusCode, headers, response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable,
                                    errorResponse);
                            throwable.printStackTrace();
                            Log.d("ClientTabActivity", "onSuccess: " + errorResponse);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override

    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

//        if(hasFocus){
//            if(number != -1 && mTabHost != null){
//                this.mTabHost.setCurrentTab(number);
//            }
//        }
    }

    public void showAdv() {

        if (HtmitechApplication.isAdvShow) {
            HtmitechApplication.isAdvShow = false;
            /**
             * 启动广告页
             */
            AppInfo mAppInfo = mAppliationCenterDao.getAppInfoByAppCode(ApplicationAllEnum.ADV_PAGE.compCode);
            if (mAppInfo != null) {
                mAppInfo.setView(drawer_layout);
                ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(this);
                mProxyDealApplication.applicationCenterProxy(mAppInfo);
            }

        }
    }

    @Override
    public void setIntent(Context context, int type) {
        switch (type) {
            case 1:
                Intent intent = new Intent(context, GesturePasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY, GesturePasswordActivity.PWD_SCREEN_MODE_FORCE);
                context.startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                Log.e("setIntent", "setIntentsetIntentsetIntentsetIntent-");
                intent = new Intent(context, ZWLoginActivity.class);
                intent.putExtra(GesturePasswordActivity.PWD_SCREEN_MODE_KEY,
                        GesturePasswordActivity.PWD_SCREEN_MODE_BACKGROUND);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public boolean isGesturePwdEnable() {
//        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (ConcreteLogin.getInstance().isCurrentUserNull(this)) {
            return false;
        }
        return PreferenceUtils.isGesturePwdEnable(this,  PreferenceUtils.getLogin_name(this));
    }

    @Override
    public boolean isInitGesturePwd() {
//        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (ConcreteLogin.getInstance().isCurrentUserNull(this)) {
            return false;
        }
        return PreferenceUtils.isInitGesturePwd(this,  PreferenceUtils.getLogin_name(this));
    }

    @Override
    public boolean isZWGensturePswd() {
//        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (ConcreteLogin.getInstance().isCurrentUserNull(this)) {
            return false;
        }
        return PreferenceUtils.isInitZWGesturePwd(this,  PreferenceUtils.getLogin_name(this));
    }

}
