package com.htmitech.emportal.ui.homepage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.MyView.LoopViewPager;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.BitmapGlobalConfig;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.Banner;
import com.htmitech.emportal.entity.Metro;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.PageResult;
import com.htmitech.emportal.entity.PageResultInfo;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.homepage.widget.PortalSwitchPopwindow;
import com.htmitech.emportal.utils.Base64;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.BadgeAllUnit;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.doman.PageResultDoman;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.ConcerCountShow;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.proxy.util.AngleUntil;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.unit.DensityUtil;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.tab.MenuTabItem;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXCurrentUser;
import com.minxing.kit.ui.contacts.ContactManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpStatus;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 磁铁风格
 */
public class HomePage extends BaseFragmentActivity implements OnClickListener,
        OnPageChangeListener, ObserverCallBackType, CallBackSuccess {

    private LoopViewPager vp;
    private ViewPageAdapter vpAdapter;
    private List<View> views;
    private static Map<String, RelativeLayout.LayoutParams> itemLayoutParamsList = null;
    private static boolean firstComimg = true;
    int heightPos = 0;
    private AtomicInteger what = new AtomicInteger(0);
    PageResultInfo pageResInfo = null;
    PageResult pageRes = null;
    private int bannerCount = 0;

    private ImageView[] dots;
    private LinearLayout linearDot = null;

    private View contactsHandler;

    // 下拉图
    private ToRightPopMenum functionPopMenu;

    private ImageView functionButton;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public static final int SUCCESS = 0;
    public static final String DEFAULT_CACHE_IMAGE_FOLDER = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "image";

    //public static String DOWNPATH = "http://114.112.89.94:8081/cloudapiv6/api/getmobiledata/GetUserMainDefine?userid=liubn";
    public String DOWNPATH = null;
    int before, after;

    private int currentIndex;

    public int TIME = 3500;

    Handler handler = new Handler();

    BitmapDisplayConfig bitConfig;
    LinearLayout sonLinearLayout = null;
    private int CurrentTabIndex = -1;
    private AppliationCenterDao mAppliationCenterDao;
    private ArrayList<AppInfo> buttomAppinfoList;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private LinearLayout bgLinearLayout;
    private ArrayList<BinnerBitmapMessage> magnetBinnerBitmapMessage;
    private INetWorkManager netWorkManager;
    private final static String HOME_PAGE = "home_page_all";
    private String app_id = "";
    private String app_version_id = "";
    private ImageView portalMessageFlag;
    private PortalSwitchPopwindow mPortalSwitchPopwindow;
    private FrameLayout parentFrameLayout;
    private ProgressBar viewProgress;
    private EmptyLayout empty_parent;
    private RelativeLayout relativeLayoutTitle;
    private ProgressBar progress_;
    private int disPlayStyle = 1;//1 全部展示  2-表示无权限的不显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page2);
        EventBus.getDefault().register(this);
        ComponentInit.getInstance().setSuccess(this);
        CurrentTabIndex = getIntent().getIntExtra("CurrentTabIndex", -1);
        app_id = getIntent().getStringExtra("app_id");
        app_version_id = getIntent().getStringExtra("app_version_id");
        mAppliationCenterDao = new AppliationCenterDao(this);
        DOWNPATH = ServerUrlConstant.SERVER_EMPAPI_URL() + "tilecontroller/init/" + BookInit.getInstance().getPortalId();
        portalMessageFlag = (ImageView) findViewById(R.id.protal_message_flag_metro);
        ImageView portalArrowFlag = (ImageView) findViewById(R.id.portal_flag_metro);
        TextView tvTitle = (TextView) findViewById(R.id.text_home_title);
        viewProgress = (ProgressBar) findViewById(R.id.viewProgress);
        empty_parent = (EmptyLayout) findViewById(R.id.empty_parent);
        progress_ = (ProgressBar) findViewById(R.id.progress_);
        relativeLayoutTitle = (RelativeLayout) findViewById(R.id.layout_home_titlebar);
        EmpPortal portalId = mAppliationCenterDao.getPortalId();
        disPlayStyle = portalId.getTitle_display_style();
        tvTitle.setText(portalId != null ? portalId.getPortal_name() : "首页");
        mPortalSwitchPopwindow = new PortalSwitchPopwindow(getParent());
        if (null != mAppliationCenterDao && mAppliationCenterDao.getPortalAll().size() > 1) {
            portalArrowFlag.setVisibility(View.VISIBLE);
            tvTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });
            portalArrowFlag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });
        }
        // 修改titlebar 文字
        findViewById(R.id.btn_home_person).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ClientTabActivity) getParent()).callUserMeesageMain();
                    }
                });
        functionButton = (ImageView) findViewById(R.id.imageview_home_more);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(ApplicationAllEnum.ZY.tab_item_id, functionButton);
        functionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(v);
                }
            }
        });

        // 设置title
        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (currentUser != null) {
//            ((TextView) this.findViewById(R.id.text_home_title))
//                    .setText(currentUser.getNetworkName()
//                            + this.getString(R.string.app_name));
        }

        // 初始配置图片框架
        BitmapUtils.init(this, BitmapGlobalConfig.getInstance(
                getApplicationContext(), DEFAULT_CACHE_IMAGE_FOLDER));

        bgLinearLayout = (LinearLayout) findViewById(R.id.metro_ll);
        parentFrameLayout = (FrameLayout) findViewById(R.id.parent_frame_layout);
        buttomAppinfoList = mAppliationCenterDao.getButtomInfo();
        mProxyDealApplication = new ProxyDealApplicationPlugin(this);
        //BitmapUtils.instance().clearDiskCache();
        // 从服务端获取数据
//        new InitThread().start();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        LogManagerEnum.HOME_PAGE_API.appVersionId = app_version_id;
        LogManagerEnum.HOME_PAGE_API.app_id = app_id;
        netWorkManager.logFunactionStart(this, this, LogManagerEnum.HOME_PAGE_API);

    }

    @Override
    protected void onResume() {
        Intent intentMessageFlag = new Intent(this, PortalMessageService.class);
        startService(intentMessageFlag);

        try {
            if (magnetBinnerBitmapMessage != null) {
                if (BadgeAllUnit.get().getBadgeSize() > 0) {
                    if (magnetBinnerBitmapMessage != null) {
                        for (BinnerBitmapMessage message : magnetBinnerBitmapMessage) {
                            if (TextUtils.isEmpty(BadgeAllUnit.get().getBadge(message.getAppInfo().getApp_id() + "")) || BadgeAllUnit.get().getBadge(message.getAppInfo().getApp_id() + "").equals("0")) {

                                message.angle_nulber.setVisibility(View.GONE);

                            } else {

                                message.angle_nulber.setVisibility(View.VISIBLE);
                                if (Integer.parseInt(BadgeAllUnit.get().getBadge(message.getAppInfo().getApp_id() + "")) > 99) {
                                    RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(HomePage.this, 20), DeviceUtils.dip2px(HomePage.this, 15));
                                    layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                    message.angle_nulber.setLayoutParams(layoutParms);
                                    message.angle_nulber.setText("99+");
                                } else {
                                    message.angle_nulber.setText(BadgeAllUnit.get().getBadge(message.getAppInfo()) + "");
                                }


                            }
                        }
                    }

                } else {
                    setAngleNumber();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        if (pageRes != null) {
            initBanner(pageRes.getBannerItems());
            initMetro(pageRes.getMetroItems(), pageRes.getBackGroundImageURL());
        } else {
            viewProgress.setVisibility(View.GONE);
            empty_parent.showEmpty();
        }
        progress_.setVisibility(View.GONE);

    }

    /*
   *   磁贴变更处理
   * */
//    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
//    public void TilesChangeNotification(ClassEvent event) {
////        new InitThread().start();
//        if(!event.msg.equals("ZYJB"))
//            netWorkManager.logFunactionStart(this, this, LogManagerEnum.HOME_PAGE_API);
//
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        if (null != mAppliationCenterDao && null != mAppliationCenterDao.getPortalAll()
                && event.msg.equals("redflag")) {
            boolean aBoolean = false;
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(HomePage.this);
            ArrayList<EmpPortal> portalAll = mAppliationCenterDao.getPortalAll();
//            SharedPreferences sp = HomePage.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            SecuritySharedPreference sp = new SecuritySharedPreference(HomePage.this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            for (int i = 0; i < portalAll.size(); i++) {
                aBoolean = sp.getBoolean(portalAll.get(i).getPortal_id(), false);
                if (aBoolean && null != portalMessageFlag) {
                    portalMessageFlag.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    /**
     * 初始banner
     *
     * @param bannerItems
     */
    private void initBanner(Banner[] bannerItems) {
        if (bannerItems == null) {
            parentFrameLayout.setVisibility(View.GONE);
            return;
        }

        linearDot = (LinearLayout) findViewById(R.id.dot_ll);
        linearDot.removeAllViews();
        views = new ArrayList<View>();
        bannerCount = bannerItems.length;
        dots = new ImageView[bannerItems.length];
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (bannerCount == 0) {
            parentFrameLayout.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < ((bannerCount <= 2) ? bannerCount + 2 : bannerCount); i++) {
            int count = 0;
            if (i >= bannerCount) {
                count = i % ((bannerCount == 0) ? 1 : bannerCount);
            } else {
                count = i;
            }
            ImageView image = new ImageView(this);
            image.setLayoutParams(mParams);
            image.setAdjustViewBounds(true);
//            String enToStr = new String(android.util.Base64.decode(bannerItems[count].getAppDescObject().getBytes(), android.util.Base64.DEFAULT));
            JSONObject mJSONObject = JSON.parseObject(bannerItems[count].getAppDescObject());
            String avatarUrl = "";
            String app_id = "";
            if (mJSONObject != null) {
                app_id = mJSONObject.get("appId").toString();
                avatarUrl = mJSONObject.get("avatarUrl").toString();
            }

//            BitmapUtils.instance().display(image,
//                    bannerItems[count].getBackGroundImageURL());


            Glide.with(HtmitechApplication.getInstance()).load(bannerItems[count].getBackGroundImageURL()).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).
                    diskCacheStrategy(DiskCacheStrategy.RESULT).into(image);

            image.setScaleType(ScaleType.CENTER_CROP);
            image.setOnClickListener(new CTOnClickListener(app_id, avatarUrl));
            views.add(image);


        }
        if (bannerItems.length > 1) {
            for (int i = 0; i < bannerItems.length; i++) {
                // 根据图片数据动态添加圆点
                dots[i] = getDotItem();
                linearDot.addView(dots[i]);

            }
            // 设置初始起始原点
            currentIndex = 0;
            dots[currentIndex].setEnabled(false);
        }
        // 设置viewpageadapter
        vp = (LoopViewPager) findViewById(R.id.viewpager);
        vpAdapter = new ViewPageAdapter(views, HomePage.this);
        vp.setAdapter(vpAdapter);

        // 设置监听
        vp.setOnPageChangeListener(this);

        // 启动循环
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, TIME);

    }

    /**
     * 初始磁贴
     *
     * @param metroItems
     * @param bgUrl
     */
    @SuppressLint("NewApi")
    private void initMetro(Metro[][] metroItems, String bgUrl) {
        if(null == metroItems || (null != metroItems &&metroItems.length > 0)){
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
        viewProgress.setVisibility(View.GONE);
        if (metroItems == null)
            return;
        if (itemLayoutParamsList == null) {
            itemLayoutParamsList = new HashMap<String, RelativeLayout.LayoutParams>();
        } else {
            itemLayoutParamsList.clear();
        }
        magnetBinnerBitmapMessage = new ArrayList<BinnerBitmapMessage>();
        // 构造一块 layout 填入 BackGroundImageURL 指定的图片
        bgLinearLayout.removeAllViews();
        LinearLayout sonLinearLayout = null;

        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new BounceInterpolator());

        Animation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);

        // 设置子元素比例
        bgLinearLayout.setWeightSum(metroItems.length);//metroItems.length==2

        // 设置背景图片
        if (bgUrl != null && !"".equals(bgUrl)) {
            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
            BitmapUtils.instance().display(parentLayout, bgUrl);
        }
        // 设置布局参数
        LinearLayout.LayoutParams sonLayoutParams = new LinearLayout.LayoutParams(
                0, LayoutParams.WRAP_CONTENT, 1);

        for (int x = 0; x < metroItems.length; x++) {
            sonLinearLayout = new LinearLayout(this);
            if (x == 0) {
                sonLinearLayout.setPadding(DensityUtil.dip2px(this, 10), 0, DensityUtil.dip2px(this, 5), 0);
            } else if (x == 1) {
                sonLinearLayout.setPadding(DensityUtil.dip2px(this, 5), 0, DensityUtil.dip2px(this, 10), 0);
            } else if (x == 2) {
                sonLinearLayout.setPadding(DensityUtil.dip2px(this, 5), 0, DensityUtil.dip2px(this, 10), 0);
            } else if (x == 3) {
                sonLinearLayout.setPadding(DensityUtil.dip2px(this, 5), 0, DensityUtil.dip2px(this, 10), 0);
            } else if (x == 4) {
                sonLinearLayout.setPadding(DensityUtil.dip2px(this, 5), 0, DensityUtil.dip2px(this, 10), 0);
            }
            sonLinearLayout.setLayoutParams(sonLayoutParams);
            sonLinearLayout.setOrientation(LinearLayout.VERTICAL);

            sonLinearLayout.setLayoutAnimation(controller);

            for (int y = 0; y < metroItems[x].length; y++) {//metroItems[x].length==5

                BitmapUtils.instance().clearCache(metroItems[x][y].getBackGroundImageURL());

                View view = LayoutInflater.from(this).inflate(R.layout.metro_item_count, null);
                ImageView itemImg = (ImageView) view.findViewById(R.id.imageView);
                TextView itemNum = (TextView) view.findViewById(R.id.tv_num);


                String path = metroItems[x][y].getBackGroundImageURL();

//                BitmapUtils.instance().display(itemImg,
//                        path,
//                        new BitmapLoadCallBack<ImageView>() {
//                            @SuppressLint("NewApi")
//                            @Override
//                            public void onLoadCompleted(
//                                    ImageView container, String uri,
//                                    Bitmap bitmap,
//                                    BitmapDisplayConfig config,
//                                    BitmapLoadFrom from) {
//                                int height = itemImg.getWidth()
//                                        * bitmap.getHeight()
//                                        / bitmap.getWidth(); // 无敌算法
//                                RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(
//                                        LayoutParams.MATCH_PARENT, height);
//                                itemLayoutParams.setMargins(0, 16, 0, 0);
//                                container.setLayoutParams(itemLayoutParams);
//                                container.setImageBitmap(bitmap);
//                                itemLayoutParamsList.put(uri,
//                                        itemLayoutParams); // 存入链表
//                            }
//
//                            @Override
//                            public void onLoadFailed(ImageView container,
//                                                     String uri, Drawable drawable) {
//                                // TODO Auto-generated method stub
//                            }
//                        });
//                if(path.equals("") || path == null){
//                    continue;
//                }
                Glide.with(HtmitechApplication.instance()).
                        load(path).
                        placeholder(R.drawable.pictures_no).
                        error(R.drawable.pictures_no).
                        diskCacheStrategy(DiskCacheStrategy.RESULT).
                        into(itemImg);
                // 添加到列表 并获取appid
                JSONObject mJSONObject = JSON.parseObject(metroItems[x][y]
                        .getAppDescObject());
                String appId = "";
                if (mJSONObject != null) {
                    appId = mJSONObject.get("appId").toString();
                }
                AppInfo appInfos = mAppliationCenterDao.getAppInfo(appId);

                if (appInfos == null || TextUtils.isEmpty(BadgeAllUnit.get().getBadge(appInfos.getApp_id() + "")) || BadgeAllUnit.get().getBadge(appInfos.getApp_id() + "").equals("0")) {

                    itemNum.setVisibility(View.GONE);

                } else {

                    itemNum.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(BadgeAllUnit.get().getBadge(appInfos.getApp_id() + "")) > 99) {
                        RelativeLayout.LayoutParams layoutParms = new RelativeLayout.LayoutParams(DeviceUtils.dip2px(this, 20), DeviceUtils.dip2px(this, 15));
                        layoutParms.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        itemNum.setLayoutParams(layoutParms);
                        itemNum.setText("99+");
                    } else {
                        if (!TextUtils.isEmpty(BadgeAllUnit.get().getBadge(appInfos)) && !BadgeAllUnit.get().getBadge(appInfos).equals("0")) {
                            itemNum.setText(BadgeAllUnit.get().getBadge(appInfos) + "");
                            itemNum.setVisibility(View.VISIBLE);
                        }else{
                            itemNum.setVisibility(View.GONE);
                        }
                    }


                }
                if (disPlayStyle == 1 || disPlayStyle == 0) {
                    if (appInfos != null)
                        magnetBinnerBitmapMessage.add(new BinnerBitmapMessage(null, appId, appInfos.getApp_name(), "", appInfos.getApp_id(), appInfos, appInfos.getComp_code(), itemNum));
//                itemImg.setTag(appId);
                    itemImg.setOnClickListener(new CTOnClickListener(appId, view));
                    sonLinearLayout.addView(view);

                } else if (disPlayStyle == 2) {
                    if (appInfos != null) {
                        magnetBinnerBitmapMessage.add(new BinnerBitmapMessage(null, appId, appInfos.getApp_name(), "", appInfos.getApp_id(), appInfos, appInfos.getComp_code(), itemNum));
//                itemImg.setTag(appId);
                        itemImg.setOnClickListener(new CTOnClickListener(appId, view));
                        sonLinearLayout.addView(view);
                    }
                }


            }
            bgLinearLayout.addView(sonLinearLayout);
        }
        firstComimg = false;
        setAngleNumber();
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(LogManagerEnum.HOME_PAGE_API.getFunctionCode())) {
            AnsynHttpRequest.requestByPostWithToken(this, null, DOWNPATH, CHTTP.POSTWITHTOKEN, this, HOME_PAGE, "");
        } else if (requestName.equals(HOME_PAGE)) {
            empty_parent.hide();
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, DOWNPATH, null, this, requestName, "");
            if (requestValue != null && !requestValue.equals("")) {
                PageResultDoman mPageResultDoman = JSON.parseObject(requestValue, PageResultDoman.class);

                // 解析json数据
                pageRes = mPageResultDoman.result;

                // 通知主线程
                mHandler.obtainMessage(SUCCESS).sendToTarget();
            }
        }


    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        viewProgress.setVisibility(View.GONE);
        progress_.setVisibility(View.GONE);
        empty_parent.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgress.setVisibility(View.VISIBLE);
                LogManagerEnum.HOME_PAGE_API.appVersionId = app_version_id;
                LogManagerEnum.HOME_PAGE_API.app_id = app_id;
                netWorkManager.logFunactionStart(HomePage.this, HomePage.this, LogManagerEnum.HOME_PAGE_API);
            }
        });
        empty_parent.showEmpty();
        empty_parent.setShowEmptyButton(true);
    }

    @Override
    public void notNetwork() {
        viewProgress.setVisibility(View.GONE);
        progress_.setVisibility(View.GONE);
        empty_parent.setNoWifiButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgress.setVisibility(View.VISIBLE);
                LogManagerEnum.HOME_PAGE_API.appVersionId = app_version_id;
                LogManagerEnum.HOME_PAGE_API.app_id = app_id;
                netWorkManager.logFunactionStart(HomePage.this, HomePage.this, LogManagerEnum.HOME_PAGE_API);
            }
        });
        empty_parent.showNowifi();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void sysUserSuccess(boolean flag) {
        AnsynHttpRequest.requestByPostWithToken(this, null, DOWNPATH, CHTTP.POSTWITHTOKEN, this, HOME_PAGE, "");
    }

    @Override
    public void setProgressbar(String value) {
        HomePage.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_.setVisibility(View.VISIBLE);
            }
        });

    }

    public class CTOnClickListener implements OnClickListener {
        public String app_id;
        public String url = "";
        public View view;
		public boolean isFlag;
        public CTOnClickListener(String app_id) {
            this.app_id = app_id;
        }

        public CTOnClickListener(String app_id, View view) {
            this.app_id = app_id;
            this.view = view;
			isFlag = true;
        }

        public CTOnClickListener(String app_id, String url) {
            this.app_id = app_id;
            this.url = url;
			isFlag = false;
        }

        @Override
        public void onClick(View v) {
            setViews(app_id, url, view,isFlag);
        }
    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == 200) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 把base64 转成json 解析json成MXAppInfo
     *
     * @param baseString
     */
    private String addAppDescList(String baseString) {
        String appId = "";
        try {
            String appString = new String(Base64.decode(baseString
                    .getBytes("UTF-8")));
            appId = JSON.parseObject(appString).getLong("id") + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appId;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    // 初始化视图
                    initView();
                    break;
            }
        }
    };

    class InitThread extends Thread {

        private String readStream(InputStream is) {
            InputStreamReader isr;
            String result = "";
            try {
                String line = "";
                isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(DOWNPATH);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpStatus.SC_OK) {

                    String jsonString = readStream(conn.getInputStream());
                    PageResultInfo pageResInfo = new PageResultInfo();

                    // 转码Base64
                    pageResInfo.parseJson(jsonString);
                    String Shortoucs = new String(Base64.decode(pageResInfo
                            .getResult().getBytes("UTF-8")));
                    PageResultDoman mPageResultDoman = JSON.parseObject(jsonString, PageResultDoman.class);
                    // 解析json数据
                    pageRes = mPageResultDoman.result;
//                    pageRes.parseJson(Shortoucs);

                    // 通知主线程
                    mHandler.obtainMessage(SUCCESS).sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }
    }

    // 图片切换伺服器
    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, TIME);

            what.incrementAndGet();
            if (what.get() > bannerCount - 1) {
                what.getAndAdd(-(bannerCount));
            }

            setCurView(what.get());
        }
    };

    // 生成一个原点图标
    private ImageView getDotItem() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.rightMargin = 6;

        ImageView img = new ImageView(this);
        img.setLayoutParams(params);
        img.setBackgroundResource(R.drawable.dot);
        img.setEnabled(true);
        // img.setOnClick
        return img;
    }

    // 设置当前视图
    private void setCurView(int position) {
        if (position < 0 || position >= bannerCount
                || position == vp.getCurrentItem())
            return;

        vp.setCurrentItem(position);
    }

    /**
     * 设置当前原点
     */
    private void setCurDot(int position) {
        int number;
        if (dots.length == 0) {
            number = 1/*/*/;
        } else {
            number = position % dots.length;
        }
        what.getAndSet(number);
        if (number < 0 || number > bannerCount - 1
                || number == currentIndex)
            return;
        dots[currentIndex].setEnabled(true);
        dots[number].setEnabled(false);
        currentIndex = number;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        setCurDot(arg0);
    }

    @Override
    protected void onPause() {
        // BitmapUtils.instance().clearCache();
        super.onPause();
    }

    /**
     * 初始化通讯录header view
     */
    private void initContactsHeaderView() {
        final ContactManager contactManager = MXUIEngine.getInstance()
                .getContactManager();
        RelativeLayout contactsHeader = (RelativeLayout) LayoutInflater.from(
                this).inflate(R.layout.contacts_header_view, null);
        contactsHandler = contactsHeader.findViewById(R.id.system_handle);
        ImageButton bacKBtn = (ImageButton) contactsHeader
                .findViewById(R.id.title_left_back_button);
        contactsHandler.setVisibility(View.GONE);
        bacKBtn.setVisibility(View.VISIBLE);
        bacKBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HtmitechApplication.contactActivity.finish();
            }
        });

        ImageButton newContaceButton = (ImageButton) contactsHeader
                .findViewById(R.id.title_right_new_function);
        newContaceButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contactManager.addContacts((Activity) (v.getContext()));
            }
        });
        contactManager.setHeaderView(contactsHeader);
        contactManager
                .setBackListener(new ContactManager.HomeScreenBackListener() {
                    @Override
                    public boolean onBack(Context context) {
                        Activity activity = (Activity) context;
                        activity.finish();
                        return true;
                    }
                });
    }

    public void setViews(String appId, String url, final View view,boolean isFlag) {

        if (!TextUtils.isEmpty(url)) {
            BookInit.getInstance().activityWebView(HomePage.this, url);
        } else if (!TextUtils.isEmpty(appId)) {
            final AppInfo appInfos = mAppliationCenterDao.getAppInfo(appId);
            int index = -1;
            try {
                for (int i = 0; i < buttomAppinfoList.size(); i++) {
                    AppInfo appInfo = buttomAppinfoList.get(i);
                    if (appInfo.getApp_id() == Long.parseLong(appId) || appInfo.getParent_app_id() == Long.parseLong(appId)) {
                        index = i;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                int success = mProxyDealApplication.applicationCenterProxy(appInfos);

                switch (success) {
                    case 1: //强制升级以及下载
                        new AlertDialog(HomePage.this).builder().setTitle("下载").setMsg("应用名称：" + appInfos.getApp_name() + "\n" + "大小：" + appInfos.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ApplicationDown mApplicationDown = new ApplicationDown(HomePage.this, view, appInfos, 0);
                                mApplicationDown.initView();
                            }
                        }).show();
                        break;
                    case 2://可暂时不升级
                        new AlertDialog(HomePage.this).builder().setTitle("升级").setMsg("应用名称：" + appInfos.getApp_name() + "\n" + "大小：" + appInfos.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ApplicationDown mApplicationDown = new ApplicationDown(HomePage.this, view, appInfos, 0);
                                mApplicationDown.initView();
                            }
                        }).setNegativeButton("暂不升级", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //暂不升级可直接进入
                                try {
                                    appInfos.setIsUpdate(true);
                                    mProxyDealApplication.applicationCenterProxy(appInfos);
                                } catch (NotApplicationException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                        break;
                }

            } catch (NotApplicationException e) {
                e.printStackTrace();
            }
        } else {
			if(isFlag){
				
				new AlertDialog(this).builder().setTitle("应用打开失败").setMsg("应用未配置，请联系管理员!").setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
				
			}
            
        }
    }

    @Override
    public void onClick(View v) {

    }

    public synchronized void setAngleNumber() {
        if (magnetBinnerBitmapMessage != null && magnetBinnerBitmapMessage.size() > 0) {
            setAngleNumber(magnetBinnerBitmapMessage.get(0));
        }

//        for (BinnerBitmapMessage mBinnerBitmapMessage : magnetBinnerBitmapMessage) {
//            if (mBinnerBitmapMessage.compCode.contains("com_workflow")) {
//                setAngleNumber(mBinnerBitmapMessage);
//            } else if (mBinnerBitmapMessage.compCode.contains("com_commonform")) {
//                AngleUntil.setAngleNumberGeneralForm(mBinnerBitmapMessage, this, null);
//            }
//
//        }
    }

    public void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
//        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;


        DocSearchParameters docSearchParameters = new DocSearchParameters();
//        docSearchParameters.context = OAConText.getInstance(HomePage.this);
//        docSearchParameters.app_id = mBinnerBitmapMessage.appInfo.getApp_id() + "";
//        docSearchParameters.Title = "";
//        if (mBinnerBitmapMessage.appid.equals("OA_todo_showalltodo")) {
//            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
//        } else if (mBinnerBitmapMessage.appid.equals("OA_todo_showyiban")) {
//            docSearchParameters.TodoFlag = "1"; // 0，待办；1，已办
//        } else {
//            docSearchParameters.TodoFlag = "0"; // 0，待办；1，已办
//            docSearchParameters.ModelName = "";
//        }

        docSearchParameters.userId = OAConText.getInstance(this).UserID;
        docSearchParameters.appId = mBinnerBitmapMessage.appInfo.getApp_id() + "";
        docSearchParameters.title = "";

        if (mBinnerBitmapMessage.appid.equals("OA_todo_showalltodo")) {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        } else if (mBinnerBitmapMessage.appid.equals("OA_todo_showyiban")) {
            docSearchParameters.todoFlag = "1"; // 0，待办；1，已办
        } else {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.modelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.Caption + "";
        }

//        docSearchParameters.todoFlag = todoFlag; // 0，待办；1，已办
//        docSearchParameters.modelName = mBinnerBitmapMessage.Caption.equals("全部") ? "" : mBinnerBitmapMessage.Caption + "";


        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mBinnerBitmapMessage.isCJ = false;
        mWorkFlowCountHttpUtil.magnetBinnerBitmapMessage = magnetBinnerBitmapMessage;
        mWorkFlowCountHttpUtil.ShowNumber(mBinnerBitmapMessage, HomePage.this, docSearchParameters);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
