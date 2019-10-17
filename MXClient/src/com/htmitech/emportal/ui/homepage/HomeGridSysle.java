package com.htmitech.emportal.ui.homepage;

/**
 * Created by htrf-pc on 2016/6/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.htmitech.MyView.ApplicationCenterListChildLayout;
import com.htmitech.MyView.LoopViewPager;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.entity.Banner;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.PageResult;
import com.htmitech.emportal.entity.PageResultInfo;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.appcenter.adapter.AdvAdapter;
import com.htmitech.emportal.ui.appcenter.adapter.HomePageGridAdapter;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.homepage.widget.PortalSwitchPopwindow;
import com.htmitech.htcommonformplugin.entity.Searchcondition;
import com.htmitech.htexceptionmanage.entity.AlertCountInfo;
import com.htmitech.htexceptionmanage.entity.ManageExceptionparameter;
import com.htmitech.htexceptionmanage.utils.ExceptionAngleUtils;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.AppVersionConfig;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.doman.PageResultDoman;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.ConcerCountShow;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.HomePageStyleEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.proxy.util.AngleUntil;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.tab.MenuTabItem;
import com.minxing.kit.api.bean.MXAppInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * 网格风格
 *
 * @author Tony
 */
public class HomeGridSysle extends MyBaseFragmentActivity implements
        OnItemClickListener, OnClickListener ,CallBackSuccess {
    private LoopViewPager mCustomViewpager;
    private ArrayList<BinnerBitmapMessage> arrBitmap;
    private ArrayList<BinnerBitmapMessage> magnetBinnerBitmapMessage;
    private ImageView[] imageViews = null;
    private ImageView imageView;
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    private HtmitechApplication app;
    private GridView fragmentHomeGv;
    private TextView binnername;
    private ImageView functionButton;
    private HomePageGridAdapter mHomePageGridAdapter;
    // 下拉图
    private ToRightPopMenum functionPopMenu;
    private String DOWNPATH;
    private PageResultInfo pageResInfo;
    private PageResult pageRes;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private int CurrentTabIndex = -1;
    private ArrayList<AppInfo> buttomAppinfoList;
    private AppliationCenterDao mAppliationCenterDao;
    private static long lastClickTime = System.currentTimeMillis();
    private PortalSwitchPopwindow mPortalSwitchPopwindow;
    private ImageView portalMessageFlag;
    private List<AppInfo> appInfoList;
    public UserMessageScrollView scroll_view;
    private boolean isClassify;
    private RelativeLayout parentLayout;
    private LinearLayout ll_child_top;
    private FrameLayout framelayout;
    private ApplicationCenterListChildLayout mApplicationCenterListChildLayout;
    private LinearLayout layout_search_no;
    private RelativeLayout relativeLayoutTitle;
    private ProgressBar progressBar;
    @Override
    public void sysUserSuccess(boolean flag) {
        initCitie();
    }

    @Override
    public void setProgressbar(String value) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public class BinnerBitmapMessage {
        public Bitmap mBitmap;
        public String appid;
        public String Caption;
        public int FavDisOrder;
        public TextView angle_nulber = new TextView(HomeGridSysle.this);
        public int numberFlag = View.GONE;
        public String number = "";
        public String avatarUrl;
        public AppInfo mAppInfo;
        public String compCode;
        /**
         * 栏目是否选中
         */
        public String AppDescObject;
        public Integer selected;
        public String BackGroundImageURL;

        public BinnerBitmapMessage(Bitmap mBitmap, String appid, String Caption,
                                   String FavDisOrder, AppInfo mAppInfo, String compCode, String avatarUrl, String BackGroundImageURL) {
            this.mBitmap = mBitmap;
            this.appid = appid;
            this.Caption = Caption;
            this.FavDisOrder = Integer.parseInt(FavDisOrder);
            this.mAppInfo = mAppInfo;
            this.compCode = compCode;
            this.avatarUrl = avatarUrl;
            this.BackGroundImageURL = BackGroundImageURL;
        }

        public BinnerBitmapMessage(Bitmap mBitmap, String appid, String Caption,
                                   String FavDisOrder, AppInfo mAppInfo, String compCode, String avatarUrl) {
            this.mBitmap = mBitmap;
            this.appid = appid;
            this.Caption = Caption;
            this.FavDisOrder = Integer.parseInt(FavDisOrder);
            this.mAppInfo = mAppInfo;
            this.compCode = compCode;
            this.avatarUrl = avatarUrl;
        }

        public boolean equals(Object obj) {
            if (obj instanceof BinnerBitmapMessage) {
                BinnerBitmapMessage u = (BinnerBitmapMessage) obj;
                return this.mAppInfo.getApp_id() == u.mAppInfo.getApp_id();
            }
            return super.equals(obj);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        if (null != mAppliationCenterDao && null != mAppliationCenterDao.getPortalAll()
                && mAppliationCenterDao.getPortalAll().size() > 1 && event.msg.equals("redflag")) {
            boolean aBoolean = false;
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(HomeGridSysle.this);
            ArrayList<EmpPortal> portalAll = mAppliationCenterDao.getPortalAll();
//            SharedPreferences sp = HomeGridSysle.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            SecuritySharedPreference sp = new SecuritySharedPreference(HomeGridSysle.this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            for (int i = 0; i < portalAll.size(); i++) {
                aBoolean = sp.getBoolean(portalAll.get(i).getPortal_id(), false);
                if (aBoolean && null != portalMessageFlag) {
                    portalMessageFlag.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    protected int getLayoutById() {
        return R.layout.activity_home_gridsysle;
    }

    public void initView() {
        EventBus.getDefault().register(this);
        ComponentInit.getInstance().setSuccess(this);
        CurrentTabIndex = getIntent().getIntExtra("CurrentTabIndex", -1);
        app = (HtmitechApplication) getApplication();
        mAppliationCenterDao = new AppliationCenterDao(this);
        findViewById(R.id.btn_home_person).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ClientTabActivity) getParent()).callUserMeesageMain();
                    }
                });
        functionButton = (ImageView) findViewById(R.id.imageview_home_more);
        scroll_view = (UserMessageScrollView) findViewById(R.id.scroll_view);
        parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        ll_child_top = (LinearLayout) findViewById(R.id.ll_child_top);
        progressBar = (ProgressBar) findViewById(R.id.progress_);
        layout_search_no = (LinearLayout) findViewById(R.id.layout_search_no);
        mApplicationCenterListChildLayout = (ApplicationCenterListChildLayout) findViewById(R.id.ll_app_center_list_child);
        TextView tvTitle = (TextView) findViewById(R.id.text_home_title);
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        relativeLayoutTitle = (RelativeLayout) findViewById(R.id.layout_home_titlebar);
        EmpPortal portalId = mAppliationCenterDao.getPortalId();
        tvTitle.setText(portalId != null ? portalId.getPortal_name() : "首页");
        mPortalSwitchPopwindow = new PortalSwitchPopwindow(getParent());
        portalMessageFlag = (ImageView) findViewById(R.id.protal_message_flag);
        ImageView portalArrowFlag = (ImageView) findViewById(R.id.portal_flag);
        parentLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(isClassify){
                            mApplicationCenterListChildLayout.onScroll(scroll_view, ll_child_top, scroll_view.getScrollY());
                        }

                    }
                });
        functionPopMenu = new ToRightPopMenum(
                this);
        if (null != mAppliationCenterDao && mAppliationCenterDao.getPortalAll().size() > 1) {
            portalArrowFlag.setVisibility(View.VISIBLE);
            portalArrowFlag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });
            tvTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });

        }
        functionPopMenu.setView(ApplicationAllEnum.ZY.tab_item_id, functionButton);
        functionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(v);
                }
            }
        });

//        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
//        if (currentUser != null) {
//            /*
//             * ((TextView) this.findViewById(R.id.text_home_title))
//			 * .setText(currentUser.getNetworkName() +
//			 * this.getString(R.string.app_name));
//			 */
////            ((TextView) this.findViewById(R.id.text_home_title))
////                    .setText(currentUser.getNetworkName()
////                            + this.getString(R.string.app_name));
//        }
        mCustomViewpager = (LoopViewPager) findViewById(R.id.vp);
        fragmentHomeGv = (GridView) findViewById(R.id.fragment_home_gv);

        fragmentHomeGv.setOnItemClickListener(this);
        binnername = (TextView) findViewById(R.id.binnername);
        initUrl();

        buttomAppinfoList = mAppliationCenterDao.getButtomInfo();
        if(null == buttomAppinfoList || buttomAppinfoList.size() <= 0){
            try {
                if(null != ConcerCountShow.getInstances().getMenuTabItem()){
                    ArrayList<MenuTabItem> menuTabItem = ConcerCountShow.getInstances().getMenuTabItem();
                    for(MenuTabItem data : menuTabItem){
                        switch (data.getButtomEnum()) {
                            case ZY:
                                data.hideMarker();
                                data.hideNumberMarker();
                                break;
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        fragmentHomeGv.setNumColumns(BookInit.getInstance().getApc_style());
    }

    private String upName;

    /**
     * GRIDVIEW对应的ITEM点击监听接口
     */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view,
                            final int position, long id) {
        // 如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isFastDoubleClick()) {
            return;
        }
        switch (parent.getId()) {
            case R.id.fragment_home_gv:
                final BinnerBitmapMessage channel = ((HomePageGridAdapter) parent
                        .getAdapter()).getItem(position);// 获取点击的频道内容
                int index = -1;
                for (int i = 0; i < buttomAppinfoList.size(); i++) {
                    AppInfo appInfo = buttomAppinfoList.get(i);
                    if (appInfo.getApp_id() == channel.mAppInfo.getApp_id() || appInfo.getParent_app_id() == channel.mAppInfo.getApp_id()) {
                        index = i;
                        break;
                    }
                }

                try {
                    if (channel.mAppInfo.getApk_flag() != 3) {
                        RefreshTotal.addReshActivity();
                    }
//                    if(index != -1){
//                        ClientTabActivity.mTabHost.setCurrentTab(index);
//                        magnetBinnerBitmapMessage.get(position).mAppInfo.setApk_flag(3);
//                    }else{
                    channel.mAppInfo.setView(fragmentHomeGv);
                    int success = mProxyDealApplication.applicationCenterProxy(channel.mAppInfo);

                    switch (success) {
                        case 1: //强制升级以及下载
                            new AlertDialog(HomeGridSysle.this).builder().setTitle("下载").setMsg("应用名称：" + channel.mAppInfo.getApp_name() + "\n" + "大小：" + channel.mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(HomeGridSysle.this, view, channel.mAppInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).show();
                            break;
                        case 2://可暂时不升级
                            new AlertDialog(HomeGridSysle.this).builder().setTitle("升级").setMsg("应用名称：" + channel.mAppInfo.getApp_name() + "\n" + "大小：" + channel.mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(HomeGridSysle.this, view, channel.mAppInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).setNegativeButton("暂不升级", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //暂不升级可直接进入
                                    try {
                                        channel.mAppInfo.setIsUpdate(true);
                                        mProxyDealApplication.applicationCenterProxy(channel.mAppInfo);
                                    } catch (NotApplicationException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).show();
                            break;
                    }
//                    }


                } catch (NotApplicationException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    /**
     * 是否快速点击两次
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    initViewPager();
                    break;
                case 1:
                    progressBar.setVisibility(View.GONE);
                    if (appInfoList == null || appInfoList.size() == 0) {
                        layout_search_no.setVisibility(View.VISIBLE);
                    } else {
                        layout_search_no.setVisibility(View.GONE);
                    }
                    if (isClassify) {
                        fragmentHomeGv.setVisibility(View.GONE);
                        mApplicationCenterListChildLayout.setVisibility(View.VISIBLE);
                        mApplicationCenterListChildLayout.isOnLong(false);//表示不允许拖动
                        mApplicationCenterListChildLayout.setUserMessageScrollView(scroll_view);
                        mApplicationCenterListChildLayout.initData(appInfoList);
                        if(mApplicationCenterListChildLayout.getCenterListChildMap().size() == 0){
                            layout_search_no.setVisibility(View.VISIBLE);
                        }
                    }else{
                        fragmentHomeGv.setVisibility(View.VISIBLE);
                        mApplicationCenterListChildLayout.setVisibility(View.GONE);
                        mHomePageGridAdapter = new HomePageGridAdapter(
                                HomeGridSysle.this, magnetBinnerBitmapMessage, app);
                        fragmentHomeGv.setAdapter(mHomePageGridAdapter);
                        setAngleNumber();
                    }


                    break;
            }
            super.handleMessage(msg);
        }

    };

    public synchronized void setAngleNumber() {
        try {
            if(magnetBinnerBitmapMessage != null && magnetBinnerBitmapMessage.size() > 0)
                for(int i = 0; i < magnetBinnerBitmapMessage.size();i++){
                if("com_workflow".equals(magnetBinnerBitmapMessage.get(i).compCode)){
                    setAngleNumber(magnetBinnerBitmapMessage.get(0));
                }

                if(null != magnetBinnerBitmapMessage.get(i).mAppInfo && magnetBinnerBitmapMessage.get(i).mAppInfo.getApp_type() == 7){
                    if(null != magnetBinnerBitmapMessage.get(i) && null != magnetBinnerBitmapMessage.get(i).mAppInfo && null != magnetBinnerBitmapMessage.get(i).mAppInfo.getClassifyAppInfo() && magnetBinnerBitmapMessage.get(i).mAppInfo.getClassifyAppInfo().size() > 0){
                        for(int j = 0; j < magnetBinnerBitmapMessage.get(i).mAppInfo.getClassifyAppInfo().size();j++){
                            if("com_workflow".equals(magnetBinnerBitmapMessage.get(i).mAppInfo.getClassifyAppInfo().get(j).getComp_code())){
                                setAngleNumber(magnetBinnerBitmapMessage.get(0));
                            }
                        }
                    }

                }
                }

//            for (BinnerBitmapMessage mBinnerBitmapMessage : magnetBinnerBitmapMessage) {
//                if (mBinnerBitmapMessage.compCode.contains("com_workflow") && !mBinnerBitmapMessage.mAppInfo.getPlugin_code().equals("com_workflow_plugin_opinions")) {
//
//                } else if (mBinnerBitmapMessage.compCode.contains("com_commonform")) {
//                    setAngleNumberGeneralForm(mBinnerBitmapMessage);
//                } else if (mBinnerBitmapMessage.compCode.contains("com_alert")) {
//                    setAngleNumberAlert(mBinnerBitmapMessage);
//                }
//
//            }
        } catch (Exception e) {

        }
    }

    /*
       *   磁贴变更处理
       * */
//    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
//    public void TilesChangeNotification(ClassEvent event) {
//        if(!event.msg.equals("ZYJB"))
//            initUrl();
//    }


    public void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
//        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;


        DocSearchParameters docSearchParameters = new DocSearchParameters();
//        docSearchParameters.context = OAConText.getInstance(HomeGridSysle.this);
//        docSearchParameters.app_id = mBinnerBitmapMessage.mAppInfo.getApp_id() + "";
//        docSearchParameters.Title = "";
//        if (mBinnerBitmapMessage.appid.equals("OA_todo_showalltodo")) {
//            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
//        } else if (mBinnerBitmapMessage.appid.equals("OA_todo_showyiban")) {
//            docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
//        } else {
//            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
//            docSearchParameters.ModelName = "";
//        }
//
//        docSearchParameters.RecordStartIndex = 0;
//        docSearchParameters.RecordEndIndex = 14;

        docSearchParameters.userId = OAConText.getInstance(HomeGridSysle.this).UserID;
        docSearchParameters.appId = mBinnerBitmapMessage.mAppInfo.getApp_id() + "";
        docSearchParameters.title = "";
        if (mBinnerBitmapMessage.appid.equals("OA_todo_showalltodo")) {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        } else if (mBinnerBitmapMessage.appid.equals("OA_todo_showyiban")) {
            docSearchParameters.todoFlag = "1"; // 0，待办；1，已办
        } else {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.modelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.Caption + "";
        }


        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.ShowNumber(mBinnerBitmapMessage, HomeGridSysle.this, docSearchParameters, mHomePageGridAdapter);

//        AnsynHttpRequest.requestByPost(this, docSearchParameters, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {
//
//            @Override
//            public void success(String successMessage) {
//                // TODO Auto-generated method stub
//                Log.d("AnsynHttpRequest", successMessage);
//                JSONObject mJSONObject = JSON.parseObject(successMessage);
//                String Result = mJSONObject.getString("Result");
//
//                if (Result != null && !Result.equals("") && !Result.equals("0")) {
//                    try {
//                        int resultInteger = Integer.parseInt(Result);
//                        mBinnerBitmapMessage.numberFlag = View.VISIBLE;
//                        mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
//                        mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
//                        mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
//                        if (resultInteger > 99) {
//                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(HomeGridSysle.this, 30), DeviceUtils.dip2px(HomeGridSysle.this, 15));
//                            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
//                        }
//                        mHomePageGridAdapter.notifyDataSetChanged();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void notNetwork() {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void callbackMainUI(String successMessage) {
//
//            }
//
//            @Override
//            public void fail(String exceptionMessage) {
//                // TODO Auto-generated method stub
//            }
//        });


    }

    private void setAngleNumberGeneralForm(final BinnerBitmapMessage mBinnerBitmapMessage) {
        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_GENERAL_FORM_COUNT;

        Searchcondition mSearchcondition = AngleUntil.getSearchcondition(this, mBinnerBitmapMessage.mAppInfo);
        if (mSearchcondition != null && mSearchcondition.todoflag.equals("1")) {//如果是已办的话那么就不获得角标
            return;
        }
        AnsynHttpRequest.requestByPost(this, mSearchcondition, daiban_yiban_url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                Log.d("AnsynHttpRequest", successMessage);
                JSONObject mJSONObject = JSON.parseObject(successMessage);
                String Result = mJSONObject.getString("Result");

                if (Result != null && !Result.equals("") && !Result.equals("0")) {
                    try {
                        int resultInteger = Integer.parseInt(Result);
                        mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                        mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                        mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                        mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                        if (resultInteger > 99) {
                            RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(HomeGridSysle.this, 30), DeviceUtils.dip2px(HomeGridSysle.this, 15));
                            mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                        }
                        mHomePageGridAdapter.notifyDataSetChanged();
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

    private void setAngleNumberAlert(final BinnerBitmapMessage mBinnerBitmapMessage) {
        ManageExceptionparameter manageExceptionparameter = new ManageExceptionparameter();
        manageExceptionparameter.setUserId(OAConText.getInstance(HtmitechApplication.instance()).UserID);

        if (mBinnerBitmapMessage.Caption.equals("全部")) {
            manageExceptionparameter.setFilterDays("0");
            manageExceptionparameter.setSourceType("");
            manageExceptionparameter.setKeyWord("");

        } else {
            if (mBinnerBitmapMessage.mAppInfo != null && mBinnerBitmapMessage.mAppInfo.getmAppVersion() != null && mBinnerBitmapMessage.mAppInfo.getmAppVersion().getAppVersionConfigArrayList() != null) {
                for (AppVersionConfig appVersionConfig : mBinnerBitmapMessage.mAppInfo.getmAppVersion().getAppVersionConfigArrayList()) {
                    if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_days")) {
                        manageExceptionparameter.setFilterDays(appVersionConfig.getConfig_value() == null ? "" : appVersionConfig.getConfig_value());
                    }
                    if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_sourcetype")) {
                        manageExceptionparameter.setSourceType(appVersionConfig.getConfig_value() == null ? "" : appVersionConfig.getConfig_value());
                    }
                    if (appVersionConfig.getConfig_code().equals("com_alert_plugin_selector_paramter_title_keyword")) {
                        manageExceptionparameter.setKeyWord(appVersionConfig.getConfig_value() == null ? "" : appVersionConfig.getConfig_value());
                    }
                }
            }
        }
        ExceptionAngleUtils exceptionAngleUtils = new ExceptionAngleUtils(this, manageExceptionparameter, new ExceptionAngleUtils.IexceptionAlertItem() {
            @Override
            public void AlertItemClick(AlertCountInfo alertCountInfo) {
                if (alertCountInfo != null) {
                    try {
                        try {
                            int resultInteger = Integer.parseInt(alertCountInfo.getNoDealCount());
                            mBinnerBitmapMessage.numberFlag = View.VISIBLE;
                            mBinnerBitmapMessage.angle_nulber.setVisibility(View.VISIBLE);
                            mBinnerBitmapMessage.angle_nulber.setText(resultInteger > 99 ? "99+" : resultInteger + "");
                            mBinnerBitmapMessage.number = resultInteger > 99 ? "99+" : resultInteger + "";
                            if (resultInteger > 99) {
                                RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(HomeGridSysle.this, 30), DeviceUtils.dip2px(HomeGridSysle.this, 15));
                                mBinnerBitmapMessage.angle_nulber.setLayoutParams(layoutParms);
                            }
                            mHomePageGridAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void initUrl() {
        String portal_id = BookInit.getInstance().getPortalId();
        DOWNPATH = ServerUrlConstant.SERVER_EMPAPI_URL() + "tilecontroller/init/" + portal_id;
//        requestByPostWithToken(this, null, url, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.APP_CENTER_ADD.functionCode)
        AnsynHttpRequest.requestByPostWithToken(this, null, DOWNPATH, CHTTP.POSTWITHTOKEN, new ObserverCallBackType() {


            @Override
            public void success(String requestValue, int type, String requestName) {
                try {
                    PageResultDoman PageResultDoman = JSON.parseObject(requestValue, PageResultDoman.class);
//                    String Shortoucs = new String(Base64.decode(pageResInfo
//                            .getResult().getBytes("UTF-8")));
//                    pageRes = JSON.parseObject(Shortoucs, PageResult.class);
                    pageRes = PageResultDoman.result;
                    initData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String exceptionMessage, int type, String requestName) {

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
            }

            @Override
            public void callbackMainUI(String successMessage) {

            }


        }, "", "");

        getViewGroupNumber();


    }

    public void initData() {
        arrBitmap = new ArrayList<BinnerBitmapMessage>();

        // 初始化广告栏
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (pageRes != null) {
                    for (Banner mListNodes : pageRes.getBannerItems()) {
                        JSONObject mJSONObject = JSON.parseObject(mListNodes.getAppDescObject());
                        String app_id = "";
                        String avatarUrl = "";
                        if (mJSONObject != null) {
                            app_id = mJSONObject.get("appId").toString();
                            avatarUrl = mJSONObject.get("avatarUrl").toString();
                        }

                        BinnerBitmapMessage mBinnerBitmapMessage = new BinnerBitmapMessage(null,
                                app_id, mListNodes.getText(),
                                "0", null, "", avatarUrl, mListNodes.getBackGroundImageURL());
//                    String enToStr = new String(android.util.Base64.decode(mListNodes.getAppDescObject().getBytes(), android.util.Base64.DEFAULT));

                        arrBitmap.add(mBinnerBitmapMessage);
                    }
                }

                Message msg = handler.obtainMessage();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }).start();

    }

    public void initCitie() {
        // 初始化下面GridView

        new Thread(new Runnable() {
            @Override
            public void run() {
                appInfoList = getList();
                ArrayList<AppInfo> appcenterList = mAppliationCenterDao.getAppcenterList();
                try {
                    ArrayList<AppInfo> appInfosResult = (ArrayList<AppInfo>) appInfoList;
                    if(null != appInfoList && appcenterList != null ){
                        for(int i = 0; i < appcenterList.size(); i++){
                            long app_id = appcenterList.get(i).getApp_id();
                            for(int j = 0; j < appInfoList.size(); j++){
                                if(app_id == appInfoList.get(j).getApp_id()){
                                    break;
                                }else{
                                    if(j == appInfoList.size()-1){
                                        appInfosResult.add(appcenterList.get(i));
                                    }
                                }
                            }
                        }
                    }
                    for(int i = 0; i < appInfoList.size();i++){
                        if(appInfoList.get(i).getApp_type() == 7){
                            if(null != appInfoList && null != appInfoList.get(i)  && null != appInfoList.get(i).getClassifyAppInfo() && appInfoList.get(i).getClassifyAppInfo().size() > 0){
                                for(int j = 0; j < appInfoList.get(i).getClassifyAppInfo().size();j++){
                                    AppInfo appInfo = appInfoList.get(i).getClassifyAppInfo().get(j);
                                    appInfosResult.remove(appInfo);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isClassify = false;
                String currentPage = HomePageStyleEnum.getHomePageStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style).code;
                if(!TextUtils.isEmpty(currentPage) && currentPage.equalsIgnoreCase(HomeSet.GROUP_FZ_PAGE)){
                    isClassify = true;
                }
                for (AppInfo appInfo : appInfoList) {
                    if (appInfo.getApp_type() == 7) {
//                        if (appInfo.getAppcenter_include_atfirst() == 1) {
//
//                            break;
//                        }
                        isClassify = true; //强制使用
                    }
                }

                if(BookInit.getInstance().getmApcUserdefinePortal().getUsing_home_style() == 2){
                    isClassify = false;
                }
                if (!isClassify) {
                    magnetBinnerBitmapMessage = new ArrayList<BinnerBitmapMessage>();
                    for (AppInfo mAppInfo : appInfoList) {
//                    magnetBinnerBitmapMessage.add(0, new BinnerBitmapMessage(BitmapLoader
//                            .loadBitmap(mAppInfo.getPicture_normal()), mAppInfo.getApp_code(), mAppInfo.getApp_name(), "0",mAppInfo));
                        BinnerBitmapMessage mBinnerBitmapMessage = new BinnerBitmapMessage(null, mAppInfo.getApp_code(), mAppInfo.getApp_name(), "0", mAppInfo, mAppInfo.getComp_code(), mAppInfo.getPicture_normal());
                        if (!magnetBinnerBitmapMessage.contains(mBinnerBitmapMessage)) {
                            magnetBinnerBitmapMessage.add(mBinnerBitmapMessage);
                        }

                    }
                }

//                sort();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();


    }

    @Override
    protected void onResume() {
        Intent intentMessageFlag = new Intent(this, PortalMessageService.class);
        startService(intentMessageFlag);
        if (RefreshTotal.isZY() && fragmentHomeGv != null) {
            initCitie();
            RefreshTotal.refershZY(false);
        }
//        if(mApplicationCenterListChildLayout != null){
//            mApplicationCenterListChildLayout.initData(appInfoList);
//        }
        super.onResume();
    }

    private List<AppInfo> getList() {
        // TODO Auto-generated method stub
        mProxyDealApplication = new ProxyDealApplicationPlugin(this);
        if (mAppliationCenterDao == null) {
            mAppliationCenterDao = new AppliationCenterDao(this);
        }
        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getApplicationCenterInfo();
        if (mProxyDealApplication != null) {
            for (AppInfo AppInfos : appInfos) {
                mProxyDealApplication.interceptAPK(AppInfos);
            }
        }
        return appInfos;
    }

    // 根据FavDisOrder 进行排序
    public void sort() {
        Comparator comp = new Comparator() {
            public int compare(Object o1, Object o2) {
                BinnerBitmapMessage p1 = (BinnerBitmapMessage) o1;
                BinnerBitmapMessage p2 = (BinnerBitmapMessage) o2;
                if (p1.FavDisOrder < p2.FavDisOrder)
                    return -1;
                else if (p1.FavDisOrder == p2.FavDisOrder)
                    return 0;
                else if (p1.FavDisOrder > p2.FavDisOrder)
                    return 1;
                return 0;
            }
        };
        Collections.sort(magnetBinnerBitmapMessage, comp);
    }


    private void initViewPager() {
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        group.removeAllViews();
        ProgressBar viewProgress = (ProgressBar) findViewById(R.id.viewProgress);
        viewProgress.setVisibility(View.GONE);
        List<BinnerBitmapImageView> advPics = new ArrayList<BinnerBitmapImageView>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        if (arrBitmap.size() == 0) {
            framelayout.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < ((arrBitmap.size() <= 2) ? arrBitmap.size() + 2 : arrBitmap.size()); i++) {
            int count = 0;
            if (i >= arrBitmap.size()) {
                count = i % ((arrBitmap.size() == 0) ? 1 : arrBitmap.size());
            } else {
                count = i;
            }
            ImageView img1 = new ImageView(this);
            img1.setLayoutParams(mParams);
            img1.setScaleType(ScaleType.CENTER_CROP);
//            img1.setImageBitmap(arrBitmap.get(count).mBitmap);

            Glide.with(HtmitechApplication.getInstance()).load(arrBitmap.get(count).BackGroundImageURL).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).into(img1);


//            img1.setTag(arrBitmap.get(count).appid);// 通过tag来判断点击哪个
            advPics.add(new BinnerBitmapImageView(img1, arrBitmap.get(count).appid,
                    arrBitmap.get(count).FavDisOrder + "", "0", arrBitmap.get(count).avatarUrl, arrBitmap.get(count).BackGroundImageURL));
        }
        // 对imageviews进行填充
        imageViews = new ImageView[arrBitmap.size()];
        // 小图标
        for (int i = 0; i < arrBitmap.size(); i++) {
            TextView text = new TextView(this);
            text.setWidth(20);
            imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(20, 20));
            imageView.setPadding(5, 5, 5, 5);
            imageView.setTag("banner" + i);
//            imageView.setOnClickListener(this);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.white_dot);
            } else {

                imageViews[i].setBackgroundResource(R.drawable.gray_dot);
            }
            group.addView(text);
            group.addView(imageViews[i]);
        }
        binnername.setText(arrBitmap.get(0).Caption);
        mCustomViewpager.setAdapter(new AdvAdapter(this, advPics, app, mProxyDealApplication, mAppliationCenterDao, buttomAppinfoList));
        mCustomViewpager.setOnPageChangeListener(new GuidePageChangeListener());
        mCustomViewpager.setCurrentItem(0);
        mCustomViewpager.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }
        }).start();
    }

    private final class GuidePageChangeListener implements OnPageChangeListener {
        private int currentPosition;

        public void onPageScrollStateChanged(int state) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            int ponstion;
            if (arrBitmap.size() == 0) {
                ponstion = 1;
            } else {
                ponstion = arg0 % arrBitmap.size();
            }

            what.getAndSet(ponstion);
            if (arrBitmap.size() != 0) {
                binnername.setText(arrBitmap.get(ponstion).Caption);
                for (int i = 0; i < imageViews.length; i++) {
                    imageViews[ponstion].setBackgroundResource(R.drawable.white_dot);
                    if (ponstion != i) {
                        imageViews[i].setBackgroundResource(R.drawable.gray_dot);
                    }
                }
            }
        }
    }

    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCustomViewpager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }
    };

    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-(imageViews.length));
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
//		Utils.toast(DaiBanFragmentActivity.this, "返回了0:" + arg0 + ",1:" + arg1, Toast.LENGTH_SHORT);
        if (arg1 == ActivityResultConstant.SAVEOCUS_RESULT_OK)
            getViewGroupNumber();
    }

    List<MXAppInfo> currentOcuList = null;

    public void getViewGroupNumber() {

        initCitie();
//        AppCenterModel getocuListModel = new AppCenterModel(new IBaseCallback() {
//            @Override
//            public void onSuccess(int requestTypeId, Object result) {
//                currentOcuList = null;
//                if (result != null) {
//                    ArrayList<HashMap<MXAppInfo, Boolean>> resultList = null;
//                    OcuListEntity entity = (OcuListEntity) result;
//                    if (entity.getResult() != null && entity.getResult().trim() != "" && entity.getResult().length() > 0) {
//                        String Shortoucs = "";
//                        try {
//                            Shortoucs = new String(Base64.decode(entity.getResult().getBytes("UTF-8")));
//                        } catch (UnsupportedEncodingException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        /** 当前用户已经选择的快捷方式列表 */
//                        currentOcuList = JSON.parseArray(Shortoucs, MXAppInfo.class);
////					Utils.toast(HomeGridSysle.this, "取得了" + currentOcuList.size() + "条快捷方式" + "Shortoucs:::::" + Shortoucs, Toast.LENGTH_SHORT);
//                    }
//                    MXAPI.getInstance(HomeGridSysle.this).getAppCenterInfos(new AppInfoCallback() {
//
//                        @Override
//                        public void onSuccess() {
//                            // TODO Auto-generated method stub
//
//                        }
//
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
//                        public void onResult(List<MXAppInfo> allOcuList) {
//                            // TODO Auto-generated method stub
//                            ArrayList<MXAppInfo> tempInfo;
//                            if (currentOcuList != null) {
//                                tempInfo = new ArrayList<MXAppInfo>(currentOcuList);
//                            } else {
//                                tempInfo = new ArrayList<MXAppInfo>();
//                            }
//
//
//                            if (currentOcuList != null && currentOcuList.size() != 0) {
//                                if (allOcuList != null && allOcuList.size() > 0) {
//                                    for (int j = 0; j < currentOcuList.size(); j++) {
//                                        boolean bIN = false;
//                                        for (int a = 0; a < allOcuList.size(); a++) {
//                                            String appId = currentOcuList.get(j).getAppID();
//                                            if (!TextUtils.isEmpty(appId)) {
//                                                if (currentOcuList.get(j).getAppID().equalsIgnoreCase(allOcuList.get(a).getAppID())) {
//                                                    bIN = true;
//                                                    currentOcuList.get(j).setName(allOcuList.get(a).getName());
//                                                    currentOcuList.get(j).setAppID(allOcuList.get(a).getAppID());
//                                                    currentOcuList.get(j).setAvatarUrl(allOcuList.get(a).getAvatarUrl());
//                                                }
//                                            }
//                                        }
//                                        if (!bIN)
//                                            tempInfo.remove(currentOcuList.get(j));
//                                    }
//                                } else
//                                    tempInfo.clear();
//                            }
//
//
//
//                        }
//                    });
//
//
//                }
//
//            }
//
//            @Override
//            public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
//
//            }
//        });
//        getocuListModel.getDataFromServerByType(AppCenterModel.TYPE_GET_CURRENTOCU_LIST,
//                OAConText.getInstance(HtmitechApplication.getInstance()).UserID);
    }

    @Override
    public void onClick(View v) {
        String curOnClick = (String) v.getTag();
        //Log.i("TAG", curOnClick);
//        if (curOnClick.equals("banner1")) {
//            BookInit.getInstance().activityWebView(HomeGridSysle.this, "http://www.creei.cn/newsshow.jsp?newsId=21998");
//        } else if (curOnClick.equals("banner0")) {
//            BookInit.getInstance().activityWebView(HomeGridSysle.this, "http://c.m.163.com/news/a/C1G03R4P00238087.html?spss=newsapp&spsw=1");
//        }
    }

}
