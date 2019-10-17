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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.entity.Banner;
import com.htmitech.emportal.entity.PageResult;
import com.htmitech.emportal.entity.PageResultInfo;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.appcenter.adapter.AdvAdapter;
import com.htmitech.emportal.ui.appcenter.adapter.HomePageGridAdapter;
import com.htmitech.emportal.ui.homepage.widget.PortalSwitchPopwindow;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.PageResultDoman;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXAppInfo;
import com.minxing.kit.api.bean.MXCurrentUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 网格风格
 *
 * @author Tony
 */
public class HomeGridListSysle extends MyBaseFragmentActivity implements OnClickListener {
    private LoopViewPager mCustomViewpager;
    private ArrayList<BinnerBitmapMessage> arrBitmap;
    private ArrayList<BinnerBitmapMessage> magnetBinnerBitmapMessage;
    private ImageView[] imageViews = null;
    private ImageView imageView;
    private boolean isContinue = true;
    private AtomicInteger what = new AtomicInteger(0);
    private HtmitechApplication app;
//    private GridView fragmentHomeGv;
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
    private ApplicationCenterListChildLayout mApplicationCenterListChildLayout;
    private List<AppInfo> appInfoList;
    public UserMessageScrollView scroll_view;
    public class BinnerBitmapMessage {
        public Bitmap mBitmap;
        public String appid;
        public String Caption;
        public int FavDisOrder;
        public TextView angle_nulber = new TextView(HomeGridListSysle.this);
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
    public void onStringEvent(ClassEvent event){
        if (null != mAppliationCenterDao&&null!=mAppliationCenterDao.getPortalAll()
                &&mAppliationCenterDao.getPortalAll().size()>1&&event.msg.equals("redflag")){
//            SharedPreferences sp = HomeGridListSysle.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            SecuritySharedPreference sp = new SecuritySharedPreference(HomeGridListSysle.this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            boolean aBoolean = sp.getBoolean(BookInit.getInstance().getPortalId(), false);
            if(null != portalMessageFlag && aBoolean){
                portalMessageFlag.setVisibility(View.VISIBLE);
            }
        }
    }
    protected int getLayoutById() {
        return R.layout.activity_home_gridsyslelist;
    }

    public void initView() {
        EventBus.getDefault().register(this);
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

        scroll_view = (UserMessageScrollView) findViewById(R.id.scroll_view);
        mApplicationCenterListChildLayout = (ApplicationCenterListChildLayout) findViewById(R.id.ll_app_center_list_child);
        functionButton = (ImageView) findViewById(R.id.imageview_home_more);
        TextView tvTitle = (TextView) findViewById(R.id.text_home_title);
        mPortalSwitchPopwindow = new PortalSwitchPopwindow(HomeGridListSysle.this);
        portalMessageFlag = (ImageView)findViewById(R.id.protal_message_flag);
        ImageView portalArrowFlag = (ImageView)findViewById(R.id.portal_flag);

        functionPopMenu = new ToRightPopMenum(
                this);
        if(null != mAppliationCenterDao && mAppliationCenterDao.getPortalAll().size()>1){
            portalArrowFlag.setVisibility(View.VISIBLE);
            portalArrowFlag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mPortalSwitchPopwindow.isShowing()){
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(v);
                    }
                }
            });
            tvTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mPortalSwitchPopwindow.isShowing()){
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(v);
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

        MXCurrentUser currentUser = MXAPI.getInstance(this).currentUser();
        if (currentUser != null) {
            /*
             * ((TextView) this.findViewById(R.id.text_home_title))
			 * .setText(currentUser.getNetworkName() +
			 * this.getString(R.string.app_name));
			 */
//            ((TextView) this.findViewById(R.id.text_home_title))
//                    .setText(currentUser.getNetworkName()
//                            + this.getString(R.string.app_name));
        }
        mCustomViewpager = (LoopViewPager) findViewById(R.id.vp);
//        fragmentHomeGv = (GridView) findViewById(R.id.fragment_home_gv);

        binnername = (TextView) findViewById(R.id.binnername);
        initUrl();

        buttomAppinfoList = mAppliationCenterDao.getButtomInfo();

//        fragmentHomeGv.setNumColumns(BookInit.getInstance().getApc_style());
    }

    private String upName;


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
//                    mHomePageGridAdapter = new HomePageGridAdapter(
//                            HomeGridListSysle.this, magnetBinnerBitmapMessage, app);
//                    fragmentHomeGv.setAdapter(mHomePageGridAdapter);
                    mApplicationCenterListChildLayout.isOnLong(false);//表示不允许拖动
                    mApplicationCenterListChildLayout.setUserMessageScrollView(scroll_view);
                    mApplicationCenterListChildLayout.initData(appInfoList);

                    break;
            }
            super.handleMessage(msg);
        }

    };


    /*
       *   磁贴变更处理
       * */
//    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
//    public void TilesChangeNotification(ClassEvent event) {
//        if(!event.msg.equals("ZYJB"))
//            initUrl();
//    }




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
//                magnetBinnerBitmapMessage = new ArrayList<BinnerBitmapMessage>();
//                for (AppInfo mAppInfo : appInfoList) {
////                    magnetBinnerBitmapMessage.add(0, new BinnerBitmapMessage(BitmapLoader
////                            .loadBitmap(mAppInfo.getPicture_normal()), mAppInfo.getApp_code(), mAppInfo.getApp_name(), "0",mAppInfo));
//                    BinnerBitmapMessage mBinnerBitmapMessage = new BinnerBitmapMessage(null, mAppInfo.getApp_code(), mAppInfo.getApp_name(), "0", mAppInfo, mAppInfo.getComp_code(), mAppInfo.getPicture_normal());
//                    if(!magnetBinnerBitmapMessage.contains(mBinnerBitmapMessage)){
//                        magnetBinnerBitmapMessage.add(mBinnerBitmapMessage);
//                    }
//
//                }
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
        if (RefreshTotal.isZY() && mApplicationCenterListChildLayout != null) {
            initCitie();
            RefreshTotal.refershZY(false);
        }

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
