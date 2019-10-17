package com.htmitech.proxy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.addressbook.BookActivity;
import com.htmitech.addressbook.UserDetailsChildActivity;
import com.htmitech.app.Constant;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.service.BookService;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;
import com.htmitech.emportal.ui.homepage.HomePage;
import com.htmitech.emportal.ui.homepage.PortalHomeFragmentActivity;
import com.htmitech.emportal.utils.BitmapLoader;
import com.htmitech.htcommonformplugin.entity.Searchcondition;
import com.htmitech.htworkflowformpluginnew.activity.InitWorkFlowFormActivity;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.proxy.doman.AppReturnClass;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.interfaces.CallBackButtom;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.BaseApplicationEnum;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.proxy.util.AngleUntil;
import com.minxing.client.tab.MenuTabHost;
import com.minxing.client.tab.MenuTabItem;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.CirclePopCenter;
import com.minxing.kit.api.bean.MXCircleGroup;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.ui.chat.ChatManager;
import com.minxing.kit.ui.chat.MXChatActivity;
import com.minxing.kit.ui.circle.CircleManager;
import com.minxing.kit.ui.circle.MXCircleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 获取底部参数 没有自定义参数
 */
public class MenuTabItemFactory {
    public Context context;

    public Map<ApplicationAllEnum, LeftBar> buttomMap;

    private CallBackButtom mCallBackButtom;

    private static MenuTabItemFactory mMenuTabItemFactory;

    private Map<HomePageStyleEnum, ArrayList<MenuTabItem>> styleMap;

    private ArrayList<ApplicationAllEnum> cacheList;
    private String appId;
    private int resultInteger;

    private MenuTabItemFactory() {
        styleMap = new HashMap<HomePageStyleEnum, ArrayList<MenuTabItem>>();
        cacheList = new ArrayList<ApplicationAllEnum>();
    }

    public static MenuTabItemFactory getInstance() {
        if (mMenuTabItemFactory == null) {
            mMenuTabItemFactory = new MenuTabItemFactory();
        }
        return mMenuTabItemFactory;
    }

    /**
     * 根据枚举 获取单个配置底部菜单
     *
     * @param contexts
     * @param buttomEnum
     * @param mCallBackButtoms
     * @return
     */
    public MenuTabItem getMenuTabItem(Context contexts, ApplicationAllEnum buttomEnum, CallBackButtom mCallBackButtoms) {
        context = contexts;
        mCallBackButtom = mCallBackButtoms;
        return getMenuTabItem(buttomEnum);
    }

    /**
     * 获取风格设置中的菜单列表
     *
     * @return
     */
    public ArrayList<MenuTabItem> getStyleEnum(Context contexts, HomePageStyleEnum mHomePageStyleEnum, CallBackButtom mCallBackButtoms) {
        context = contexts;
        mCallBackButtom = mCallBackButtoms;

        return requestStyleMenumTabItem(mHomePageStyleEnum);
    }

    /**
     * 插件配置  暂时实现 没有详细数据进行支配实现
     *
     * @param buttomEnum
     * @return
     */

//    public MenuTabItem getMenuTabItem(Context contexts,ButtomEnum buttomEnum,CallBackButtom mCallBackButtoms){
//        context = contexts;
//        mCallBackButtom = mCallBackButtoms;
//        return getMenuTabItem(buttomEnum);
//    }
    private MenuTabItem getMenuTabItem(ApplicationAllEnum buttomEnum) {
        return requestMenuTabItem(buttomEnum);
    }

    public ArrayList<MenuTabItem> requestStyleMenumTabItem(HomePageStyleEnum mHomePageStyleEnum) {
        /**
         * 磁铁风格
         */
        ArrayList<MenuTabItem> ctMenuList = new ArrayList<MenuTabItem>();
        ctMenuList.add(requestMenuTabItem(ApplicationAllEnum.ZY, mHomePageStyleEnum));
        styleMap.put(HomePageStyleEnum.CTFG, ctMenuList);
        /**
         * 网格风格
         */
        ArrayList<MenuTabItem> wgMenuList = new ArrayList<MenuTabItem>();
        wgMenuList.add(requestMenuTabItem(ApplicationAllEnum.ZY, mHomePageStyleEnum));
        styleMap.put(HomePageStyleEnum.WGFG, wgMenuList);
        /**
         * 分组导航风格
         */
        ArrayList<MenuTabItem> fzdhMenuList = new ArrayList<MenuTabItem>();
        fzdhMenuList.add(requestMenuTabItem(ApplicationAllEnum.ZY, mHomePageStyleEnum));
        styleMap.put(HomePageStyleEnum.FZDHFG, fzdhMenuList);
//        /**
//         * 代办列表风格
//         */
//        ArrayList<MenuTabItem> dblbMenuList = new ArrayList<MenuTabItem>();
//        dblbMenuList.add(requestMenuTabItem(ApplicationAllEnum.DB));
//        styleMap.put(HomePageStyleEnum.DBLBFG, dblbMenuList);
        return styleMap.get(mHomePageStyleEnum);
    }

    /**
     * 获取单个
     *
     * @param buttomEnum
     * @return
     */
    private MenuTabItem requestMenuTabItem(ApplicationAllEnum buttomEnum) {
        if (buttomEnum == null) {
            return null;
        }


        buttomMap = ButtomColorFactory.getInstance(context).getButtomColor();

        if (!cacheList.contains(buttomEnum)) {
            cacheList.add(buttomEnum);
        }


        MenuTabItem mMenuTabItem = null;
        switch (buttomEnum) {
            case CJ:
                mMenuTabItem = CJResideMenumItem(buttomEnum);
                break;
            case ZY:
                HomePageStyleEnum mHomePageStyleEnum = HomePageStyleEnum.getHomePageStyle(buttomEnum.homeStyle);
                switch (mHomePageStyleEnum) {
                    case CTFG:
                        mMenuTabItem = getCTMenuTabItem(buttomEnum);
                        break;
                    case WGFG:
                        mMenuTabItem = getWGMenuTabItem(buttomEnum);
                        break;
                    case FZDHFG:
                        mMenuTabItem = getFZDHMenuTabItem(buttomEnum);
                        break;
                    case DBLBFG:
                        mMenuTabItem = getDBMenuTabItem(buttomEnum);
                        break;
                }
                break;
            case TXL:
                mMenuTabItem = getTXLMenuTabItem(buttomEnum);
                break;
            case GT:
                ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
                if(mConcreteLogin.getType() == 0){
                    mMenuTabItem = getGTMenuTabItem(buttomEnum);
                }else{
                    return null;
                }

                break;
            case GZQ:
                mConcreteLogin = ConcreteLogin.getInstance();
                if(mConcreteLogin.getType() == 0){
                    mMenuTabItem = getGZQMenuTabItem(buttomEnum);
                }else {
                    return null;
                }
                break;
            case DB:
                mMenuTabItem = getDBMenuTabItem(buttomEnum);
                break;
            default: //新增
                mMenuTabItem = CJResideMenumItem(buttomEnum);
                break;
        }
        return mMenuTabItem;
    }

    private MenuTabItem requestMenuTabItem(ApplicationAllEnum buttomEnum, HomePageStyleEnum homePageStyleEnum) {
        if (!cacheList.contains(buttomEnum)) {
            cacheList.add(buttomEnum);
        }
        buttomMap = ButtomColorFactory.getInstance(context).getButtomColor();
        MenuTabItem mMenuTabItem = null;
        switch (buttomEnum) {
            case ZY:
                switch (homePageStyleEnum) {
                    case CTFG:
                        mMenuTabItem = getCTMenuTabItem(buttomEnum);
                        break;
                    case WGFG:
                        mMenuTabItem = getWGMenuTabItem(buttomEnum);
                        break;
                    case FZDHFG:
                        mMenuTabItem = getFZDHMenuTabItem(buttomEnum);
                        break;
                    case DBLBFG:
                        mMenuTabItem = getDBMenuTabItem(buttomEnum);
                        break;
                }
                break;
            case TXL:
                mMenuTabItem = getTXLMenuTabItem(buttomEnum);
                break;
            case GT:
                ConcreteLogin mConcreteLogin = ConcreteLogin.getInstance();
                if(mConcreteLogin.getType() == 0){
                    mMenuTabItem = getGTMenuTabItem(buttomEnum);
                }else{
                    return null;
                }

                break;
            case GZQ:
                mConcreteLogin = ConcreteLogin.getInstance();
                if(mConcreteLogin.getType() == 0){
                    mMenuTabItem = getGZQMenuTabItem(buttomEnum);
                }else {
                    return null;
                }
                break;
            case DB:
                mMenuTabItem = getDBMenuTabItem(buttomEnum);
                break;
        }
        return mMenuTabItem;
    }

    /**
     * 磁铁
     */
    private MenuTabItem getCTMenuTabItem(final ApplicationAllEnum buttomEnum) {
        Intent mHomeIntent = null;
        mHomeIntent = new Intent(context, HomePage.class);

        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_HOME,
                mHomeIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?context.getString(R.string.tab_home):buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);

        homeTabItem.setOnClickListener(new MenuTabItem.OnClickListener() {
            @Override
            public void onClick() {
                if(Constant.HOME_PAGE_REFRESH){
                    synchronized (BookService.class){
                        Intent i = new Intent(context, BookService.class);
                        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
                        context.startService(i);
                    }
                }

            }
        });
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);

                    homeTabItem.setDrawable(selected, unSelected);
                }
            }).start();
        }
        return homeTabItem;
    }

    /**
     * 网格
     */
    private MenuTabItem getWGMenuTabItem(final ApplicationAllEnum buttomEnum) {
        Intent mHomeIntent = null;
        mHomeIntent = new Intent(context, HomeGridSysle.class);
        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_HOME,
                mHomeIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?context.getString(R.string.tab_home):buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);
        homeTabItem.setOnClickListener(new MenuTabItem.OnClickListener() {
            @Override
            public void onClick() {
                if(Constant.HOME_PAGE_REFRESH){
                    synchronized (BookService.class){
                        Intent i = new Intent(context, BookService.class);
                        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
                        context.startService(i);
                    }
                }

            }
        });
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);


                    homeTabItem.setDrawable(selected, unSelected);
                }
            }).start();
        }
        return homeTabItem;
    }

    /**
     * 分组导航
     *
     * @param buttomEnum
     * @return
     */
    private MenuTabItem getFZDHMenuTabItem(final ApplicationAllEnum buttomEnum) {
        Intent mHomeIntent = null;
        mHomeIntent = new Intent(context, PortalHomeFragmentActivity.class);
        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_HOME,
                mHomeIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?context.getString(R.string.tab_home):buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);

        homeTabItem.setOnClickListener(new MenuTabItem.OnClickListener() {
            @Override
            public void onClick() {
                if(Constant.HOME_PAGE_REFRESH){
                    synchronized (BookService.class){
                        Intent i = new Intent(context, BookService.class);
                        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
                        context.startService(i);
                    }
                }

            }
        });
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);

                    homeTabItem.setDrawable(selected, unSelected);
                }
            }).start();
        }
        return homeTabItem;
    }

    private void setAngleNumberGeneralForm(final MenuTabItem homeTabItem) {
        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_GENERAL_FORM_COUNT;

        Searchcondition mSearchcondition = AngleUntil.getSearchcondition(context, homeTabItem.getButtomEnum().mAppInfo);
        if (mSearchcondition != null && mSearchcondition.todoflag.equals("1")) {//如果是已办的话那么就不获得角标
            return;
        }
        AnsynHttpRequest.requestByPost(context, mSearchcondition, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                Log.d("AnsynHttpRequest", successMessage);
                JSONObject mJSONObject = JSON.parseObject(successMessage);
                String Result = mJSONObject.getString("Result");

                if (Result != null && !Result.equals("") && !Result.equals("0")) {
                    try {
                        int resultInteger = Integer.parseInt(Result);
                        if (resultInteger > 0) {
                            homeTabItem.showNumberMarker(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                        } else {
                            homeTabItem.hideNumberMarker();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void setAllAngleNumber(final MenuTabItem homeTabItem) {
        if (homeTabItem.getButtomEnum() != null) {
            if (homeTabItem.getButtomEnum() == ApplicationAllEnum.DB) {
                setAngleNumber(homeTabItem);
            } else {
                setAngleNumberGeneralForm(homeTabItem);
            }
        }
    }

    /*
    * 获取待办底部角标数量
    * */
    public int setAngleNumber(final MenuTabItem homeTabItem) {

        String daiban_yiban_url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT_JAVA;
        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.mAppInfo = homeTabItem.mAppInfo;
        DocSearchParameters docSearchParameters = new DocSearchParameters();
        docSearchParameters.userId = OAConText.getInstance(context).UserID;
//        docSearchParameters.context = OAConText.getInstance(context);
        docSearchParameters.appId = appId;
        docSearchParameters.title = "";
        docSearchParameters.modelName = "";
        mWorkFlowCountHttpUtil.refresh(docSearchParameters);
        String com_workflow_mobileconfig_tabbutton_style = "";
        if(homeTabItem.mAppInfo != null){
            for (AppVersionConfig mAppVersionConfig : homeTabItem.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                if("com_workflow_mobileconfig_tabbutton_style".equals(mAppVersionConfig.getConfig_code())){
                    com_workflow_mobileconfig_tabbutton_style = mAppVersionConfig.getConfig_value();
                }
            }
        }

        if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("4")){
            docSearchParameters.todoFlag = "1"; // 0，待办；1，已办
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("6")){
            docSearchParameters.importance = "1"; // 0，待办；1，已办
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("1")){
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("2")){
            docSearchParameters.importance = "0"; // 0，待办；1，已办
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("5")){
            docSearchParameters.importance = "0"; // 0，待办；1，已办
        }else if(!TextUtils.isEmpty(com_workflow_mobileconfig_tabbutton_style) && com_workflow_mobileconfig_tabbutton_style.equals("3")){
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        }
//        if(TextUtils.isEmpty(todoFlag)){
//            docSearchParameters.importance = "0";
//        }else{
//            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
//        }


//        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
//        mWorkFlowCountHttpUtil.ShowNumber(homeTabItem,context,docSearchParameters);
        AnsynHttpRequest.requestByPost(context, docSearchParameters, daiban_yiban_url
                , CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        Log.d("AnsynHttpRequest", successMessage);
                        try{
                            JSONObject mJSONObject = JSON.parseObject(successMessage);
                            String Result = mJSONObject.getString("result");
//                && !Result.equals("0")

                            if (Result != null && !Result.equals("")) {
                                resultInteger = Integer.parseInt(Result);
                                if (resultInteger > 0) {
                                    homeTabItem.showNumberMarker(resultInteger > 99 ? 99 + "+" : resultInteger + "");
                                } else {
                                    homeTabItem.hideNumberMarker();
                                }

                            } else {
                                resultInteger = -1;
                            }
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void callbackMainUI(String successMessage) {
                        Log.d("AnsynHttpRequest", "callbackMainUI");
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        resultInteger = -1;
                    }
                });

        return resultInteger;
    }

    /**
     * 获取所有代办
     */
    private MenuTabItem getDBMenuTabItem(final ApplicationAllEnum buttomEnum) {
        appId = buttomEnum.appId;

//        Intent mToDoIntent = new Intent(context, DaiBanFragmentActivity.class);
        Intent mToDoIntent = new Intent(context, InitWorkFlowFormActivity.class);
        if (buttomEnum.mAppInfo.getmAppVersion() != null && buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null)
            for (AppVersionConfig mAppVersionConfig : buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                mToDoIntent.putExtra(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value()); //是否支持我发起
                //            mToDoIntent.putExtra("app_id", buttomEnum.mApplicationCenterInfo.getApp_id()); //传入工作流构建需要的appId
            }
        if (buttomEnum.mAppInfo.getmAppVersion() != null)
            mToDoIntent.putExtra("app_version_id", buttomEnum.mAppInfo.getmAppVersion().getApp_version_id() +"");
        mToDoIntent.putExtra("app_id", buttomEnum.appId);
        mToDoIntent.putExtra("appShortName", buttomEnum.mAppInfo.getApp_shortname());
        mToDoIntent.putExtra("appName", buttomEnum.mAppInfo.getApp_name());
        mToDoIntent.putExtra("isHome", true);
        mToDoIntent.putExtra("Type", false);

        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_TODO + buttomEnum.appId,
                mToDoIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?buttomEnum.mAppInfo.getApp_shortname():buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);
        setAngleNumber(homeTabItem);

        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);
                    homeTabItem.setDrawable(selected, unSelected);

                }
            }).start();
        }
        return homeTabItem;
    }


    /**
     * 获取通讯录
     */
    private MenuTabItem getTXLMenuTabItem(final ApplicationAllEnum buttomEnum) {
        boolean isFlag = false;
        boolean isContant = false;
        boolean isMxpp = false;
        if (buttomEnum.mAppInfo.getmAppVersion() != null && buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
            for (AppVersionConfig mAppVersionConfig : buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                if (mAppVersionConfig.getConfig_value() != null && (mAppVersionConfig.getConfig_code().equals("com_addressbook_mobileconfig_contact")) && mAppVersionConfig.getConfig_value().equals("0")) {
                    isContant = true;
                }

                if (mAppVersionConfig.getConfig_value() != null && (mAppVersionConfig.getConfig_code().equals("com_addressbook_mobileconfig_mxpp")) && mAppVersionConfig.getConfig_value().equals("0")) {
                    isMxpp = true;
                }
            }
        }
        Intent mContactsIntent = null;
        if (isContant && isMxpp) {
            mContactsIntent = new Intent(context, UserDetailsChildActivity.class);
            mContactsIntent.putExtra("isContact", 0);
        } else {
            mContactsIntent = new Intent(context, BookActivity.class);
        }

        mContactsIntent.putExtra("appName", buttomEnum.mAppInfo.getApp_name());
        mContactsIntent.putExtra("app_id", buttomEnum.appId);
        mContactsIntent.putExtra("addressFragmentType", Constant.LOING_INIT);
        mContactsIntent.putExtra("isHome", true);
        if (buttomEnum.mAppInfo.getmAppVersion() != null && buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
            for (AppVersionConfig mAppVersionConfig : buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                mContactsIntent.putExtra(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value());
            }
        }
        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_CONTACTS,
                mContactsIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?buttomEnum.mAppInfo.getApp_shortname():buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);
        ;
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            Resources res = context.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.pictures_no);

            Drawable drawable = new BitmapDrawable(bmp);

            homeTabItem.setDrawable(drawable, drawable);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);

                    homeTabItem.setDrawable(selected, unSelected);
                }
            }).start();
        }

        return homeTabItem;
    }


    /**
     * 获取沟通
     */
    private MenuTabItem getGTMenuTabItem(final ApplicationAllEnum buttomEnum) {
        Intent mChatIntent = new Intent(context, MXChatActivity.class);
        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_CHAT,
                mChatIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?buttomEnum.mAppInfo.getApp_shortname():buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);

        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            Resources res = context.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.pictures_no);

            Drawable drawable = new BitmapDrawable(bmp);

            homeTabItem.setDrawable(drawable, drawable);
            new Thread(new Runnable() {

                @Override
                public void run() {

                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected.equals("") ? buttomEnum.url_disabled : buttomEnum.url_selected);


                    homeTabItem.setDrawable(selected, unSelected);
                }
            }).start();
        }
        initChatHeaderView(buttomEnum);
        return homeTabItem;
    }

    /**
     * 获取工作圈
     */
    private MenuTabItem getGZQMenuTabItem(final ApplicationAllEnum buttomEnum) {
        Intent mCircleIntent = new Intent(context, MXCircleActivity.class);
        final MenuTabItem homeTabItem = new MenuTabItem(context, MenuTabHost.TAB_TAG_CIRCLES,
                mCircleIntent, context.getResources().getDrawable(buttomMap.get(buttomEnum).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?buttomEnum.mAppInfo.getApp_shortname():buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            homeTabItem.setDefaultDrawable();
        } else {
            Resources res = context.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.pictures_no);

            Drawable drawable = new BitmapDrawable(bmp);

            homeTabItem.setDrawable(drawable, drawable);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected);

                    homeTabItem.setDrawable(selected, unSelected);

                }
            }).start();
        }
        initCircleHeaderView(homeTabItem, buttomEnum);
        homeTabItem.setOnReClickListener(new MenuTabItem.OnReClickListener() {

            @Override
            public void onReClick(MenuTabItem menuTabItem) {
                MXAPI.getInstance(context).forceRefreshCircle();
            }
        });

        homeTabItem.setBeforeTabChangeListener(new MenuTabItem.BeforeTabChangeListener() {

            @Override
            public void beforeTabChange(MenuTabItem menuTabItem) {
                MXCurrentUser currentUser = MXAPI.getInstance(
                        context).currentUser();
                if (currentUser != null) {
                    if (MXAPI.getInstance(context)
                            .checkNetworkCircleUnread(
                                    currentUser.getNetworkID())) {
                        MXAPI.getInstance(context)
                                .setCircleAutoRefresh();
                    }
                }
            }
        });

        return homeTabItem;
    }

    /**
     * 插件
     */
    private MenuTabItem CJResideMenumItem(final ApplicationAllEnum buttomEnum) {
        //暂时忽略插件
        //新增 定制开发
//        mAppInfo.getmAppVersion().getPackage_name()+"."+mAppInfo.getmAppVersion().getPackage_main()

        Intent mToDoIntent = null;
        if (buttomEnum.mAppInfo.getApp_type() == 8) {
            mToDoIntent = new Intent();
            mToDoIntent.setClassName(context, buttomEnum.mAppInfo.getmAppVersion().getPackage_name() + "." + buttomEnum.mAppInfo.getmAppVersion().getPackage_main());
        } else {
            AppReturnClass mAppReturnClass = BaseApplicationEnum.getActivity(buttomEnum, new CallBackLeftJC() {

                @Override
                public void onClickLeft(ApplicationAllEnum mLeftEnum) {

                }
            });
            if (mAppReturnClass == null || mAppReturnClass.mClass == null) {
                return null;
            }
            mToDoIntent = new Intent(context, mAppReturnClass.mClass);
        }
        mToDoIntent.putExtra("isHome", true);
        mToDoIntent.putExtra("appName", buttomEnum.mAppInfo.getApp_name());
        mToDoIntent.putExtra("appShortName", buttomEnum.mAppInfo.getApp_shortname());
        mToDoIntent.putExtra("tab_item_id", buttomEnum.mAppInfo.getTab_item_id());
        if (buttomEnum.mAppInfo.getmAppVersion() != null && buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null)
            for (AppVersionConfig mAppVersionConfig : buttomEnum.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                mToDoIntent.putExtra(mAppVersionConfig.getConfig_code(), mAppVersionConfig.getConfig_value()); //是否支持我发起
                //            mToDoIntent.putExtra("app_id", buttomEnum.mApplicationCenterInfo.getApp_id()); //传入工作流构建需要的appId
            }
        if (buttomEnum.mAppInfo.getmAppVersion() != null)
            mToDoIntent.putExtra("app_version_id", buttomEnum.mAppInfo.getmAppVersion().getApp_version_id() +"");
        mToDoIntent.putExtra("app_id", buttomEnum.appId);
        //忽略进来的类型（有可能是基础数据类型，有可能是构建） 获取默认图片都用插件的默认图片
        final MenuTabItem cjResideMenumItem = new MenuTabItem(context, buttomEnum.mAppInfo.getApp_code(),
                mToDoIntent, context.getResources().getDrawable(buttomMap.get(ApplicationAllEnum.CJ).getColor()),
                buttomEnum.mAppInfo.getDisplay_title()==null?buttomEnum.mAppInfo.getApp_shortname():buttomEnum.mAppInfo.getDisplay_title(),buttomEnum.mAppInfo, buttomEnum);
        setAngleNumberGeneralForm(cjResideMenumItem);
        if (buttomEnum.url_disabled == null || buttomEnum.url_disabled.equals("")) {
            cjResideMenumItem.setDefaultDrawable();
        } else {
            Resources res = context.getResources();

            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.pictures_no);

            Drawable drawable = new BitmapDrawable(bmp);

            cjResideMenumItem.setDrawable(drawable, drawable);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    Drawable unSelected = BitmapLoader.loadDrawable(buttomEnum.url_disabled);
                    Drawable selected = BitmapLoader.loadDrawable(buttomEnum.url_selected);

                    cjResideMenumItem.setDrawable(selected, unSelected);

                }
            }).start();
        }


        return cjResideMenumItem;
    }

    /**
     * 初始化圈子header view
     */
    private void initCircleHeaderView(MenuTabItem homeTabItem, ApplicationAllEnum buttomEnum) {
        final CircleManager circleManager = MXUIEngine.getInstance()
                .getCircleManager();
        RelativeLayout circleHeader = (RelativeLayout) LayoutInflater
                .from(context).inflate(R.layout.circle_header_view, null);
        LinearLayout circleHandler = (LinearLayout) circleHeader
                .findViewById(R.id.system_handle);
        final ImageButton addButton = (ImageButton) circleHeader
                .findViewById(R.id.title_right_new_function);

        LinearLayout middleTitleBar = (LinearLayout) circleHeader
                .findViewById(R.id.middle_title_bar);
        final TextView title = (TextView) circleHeader.findViewById(R.id.title);
        final ToRightPopMenum circleTopRightPopMenu = new ToRightPopMenum(
                context);
        circleTopRightPopMenu.setView(buttomEnum.tab_item_id, addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler(context.getMainLooper())
                        .post(new Runnable() {
                            public void run() {
                                if (!circleTopRightPopMenu.isShowing()) {
                                    circleTopRightPopMenu
                                            .showAsDropDown(addButton);
                                }
                            }
                        });
            }
        });
        circleHandler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                    context.getResideMenu().openMenu(
//                            ResideMenu.DIRECTION_LEFT);
                mCallBackButtom.resideMenu();

            }
        });

        middleTitleBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 获取组管理界面
                CirclePopCenter circlePopCenter = circleManager
                        .getCirclePopCenter();
                if (!circlePopCenter.isShowing()) {
                    circlePopCenter.showAsDropDown(v);
                }
            }
        });

        circleManager.setOnGroupChangeListener(new CircleManager.OnGroupChangeListener() {

            @Override
            public void onGroupChange(MXCircleGroup group) {
                // 监听组切换事件来更新标题
                title.setText(group.getName());
            }
        });
        circleManager.setHeaderView(circleHeader);
        circleManager
                .setBackListener(new CircleManager.HomeScreenBackListener() {

                    @Override
                    public boolean onBack(Context context) {
                        mCallBackButtom.moveTaskToBack();
                        return true;
                    }
                });
    }


    /**
     * 初始化聊天header view
     */
    private void initChatHeaderView(ApplicationAllEnum buttomEnum) {
        ChatManager chatManager = MXUIEngine.getInstance().getChatManager();
        RelativeLayout chatHeader = (RelativeLayout) LayoutInflater.from(context)
                .inflate(R.layout.chat_header_view, null);
        View chatHandler = chatHeader.findViewById(R.id.system_handle);
        ImageButton bacKBtn = (ImageButton) chatHeader
                .findViewById(R.id.title_left_back_button);

        chatHeader.findViewById(R.id.middle_title).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ChatManager chatManager = MXUIEngine.getInstance()
                                .getChatManager();
                        chatManager.scrollChatToTop();
                    }
                });

        chatHandler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallBackButtom.resideMenu();
            }
        });

        final ImageButton addButton = (ImageButton) chatHeader
                .findViewById(R.id.title_right_new_function);
        final ToRightPopMenum functionPopMenu = new ToRightPopMenum(
                context);
        functionPopMenu.setView(buttomEnum.tab_item_id, addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    public void run() {
                        if (!functionPopMenu.isShowing()) {
                            functionPopMenu.showAsDropDown(addButton);
                        }
                    }
                });
            }
        });
        chatManager.setHeaderView(chatHeader);
        chatManager.setBackListener(new ChatManager.HomeScreenBackListener() {

            @Override
            public boolean onBack(Context context) {
                mCallBackButtom.moveTaskToBack();
                return true;
            }
        });
    }

}
