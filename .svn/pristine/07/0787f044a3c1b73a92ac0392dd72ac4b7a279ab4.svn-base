package com.htmitech.emportal.ui.applicationcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.ApplicationCenterListChildLayout;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.homepage.BinnerBitmapMessage;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.doman.RequestResort;
import com.htmitech.proxy.doman.RequestResortJson;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackRemove;
import com.htmitech.proxy.interfaces.ConcerCountShow;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.video.utils.DisplayUtils;
import com.minxing.client.tab.MenuTabItem;
import com.minxing.client.util.FastJsonUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * tony
 */
public class ApplicationCenterActivity extends BaseFragmentActivity implements View.OnClickListener, CallBackApplicationCenter, ICallBackAppCenterLong, CallBackRemove, ObserverCallBackType, UserMessageScrollView.OnScrollListener {
    private static final String TAG = "ApplicationCenterActivi";
    public static int StruesHeight = 0;
    public RelativeLayout rl_strues;
    public DragGrid gradview;
    public TextView complete;
    public ImageView iv_menu;
    public DragAdapter adapter;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private AppInfo mAppInfo;
    private String deleteUrl;//删除url
    private String tuodongUrl;//拖动url
    private AppliationCenterDao mAppliationCenterDao;
    private List<NameValuePair> nameValuePairList;
    private int ponstion;
    private ImageView iv_back;
    private static final String HTTPTYPE = "delete";
    private static final String HTTPTYPE_TD = "tuodong";
    private List<AppInfo> appInfoList;
    private EmpPortal mEmpPortal;
    private ArrayList<RequestResort> requestResortArrayList;
    public static int Width, Height;
    private UserMessageScrollView scroll_view;
    private int scroolHight = 0;
    private LinearLayout ll_emptoy;
    private boolean isHome = false;
    private ApplicationCenterListChildLayout mApplicationCenterListChildLayout;
    private ArrayList<BinnerBitmapMessage> binnerBitmapMessages;
    private ImageView iv_back_home;
    private static long lastClickTime = System.currentTimeMillis();
    private boolean isClassify;
    private int removeIndex;
    private LinearLayout ll_child_top;
    private ProgressBar progress_;
    private RelativeLayout parentLayout;
    private TextView tv_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_center);
        initView();
        initData();
    }

    public void initView() {

        rl_strues = (RelativeLayout) findViewById(R.id.rl_strues);
        parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);
        gradview = (DragGrid) findViewById(R.id.gradview);
        tv_names = (TextView) findViewById(R.id.tv_names);
        complete = (TextView) findViewById(R.id.complete);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        scroll_view = (UserMessageScrollView) findViewById(R.id.scroll_view);
        ll_emptoy = (LinearLayout) findViewById(R.id.layout_search_no);
        iv_back_home = (ImageView) findViewById(R.id.iv_back_home);
        ll_child_top = (LinearLayout) findViewById(R.id.ll_child_top);
        progress_ = (ProgressBar) findViewById(R.id.progress_);
        mApplicationCenterListChildLayout = (ApplicationCenterListChildLayout) findViewById(R.id.ll_app_center_list_child);
    }

    public void setEmplyView(List<AppInfo> appInfos) {
        if (appInfos == null || appInfos.size() == 0) {
            ll_emptoy.setVisibility(View.VISIBLE);
        } else {
            ll_emptoy.setVisibility(View.GONE);
        }
    }


    public void initData() {
        isHome = getIntent().getBooleanExtra("isHome", false);
        String shortName = getIntent().getStringExtra("appShortName");

        tv_names.setText(shortName);
        mAppliationCenterDao = new AppliationCenterDao(this);
        mEmpPortal = mAppliationCenterDao.getPortalId();
        mAppliationCenterDao.replace(mEmpPortal.portal_id);
//        ll_emptoy.setVisibility(View.VISIBLE);
        if (isHome) {
            iv_back_home.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.GONE);
        }
        ViewTreeObserver vto = rl_strues.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                // TODO Auto-generated method stub
                StruesHeight = rl_strues.getMeasuredHeight() - scroolHight;
                return true;
            }
        });
        scroll_view.setOnScrollListener(this);
        mProxyDealApplication = new ProxyDealApplicationPlugin(ApplicationCenterActivity.this);
        iv_back.setOnClickListener(this);
        iv_back_home.setOnClickListener(this);
        mApplicationCenterListChildLayout.setCallBackRemove(this);
        deleteUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REMOVE + "/" + mEmpPortal.portal_id + "/" + OAConText.getInstance(this).UserID;
        tuodongUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.RESORTAPP;
        parentLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mApplicationCenterListChildLayout.onScroll(scroll_view, ll_child_top, scroll_view.getScrollY());
                    }
                });
        adapter = new DragAdapter(this, null);
        adapter.setCallBackRemove(this);
        gradview.setCallBackApplicationCenter(this);
        gradview.setAdapter(adapter);
        gradview.setNumColumns(BookInit.getInstance().getApc_style());

        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
                // TODO Auto-generated method stub
                if (isFastDoubleClick()) {
                    return;
                }
                mAppInfo = (AppInfo) parent.getItemAtPosition(position);
                mAppInfo.setView(gradview);
                if (mAppInfo.getApk_flag() != 3) {
                    RefreshTotal.addReshActivity();
                }
                try {
                    int success = mProxyDealApplication.applicationCenterProxy(mAppInfo);

                    switch (success) {
                        case 1: //强制升级以及下载

                            new AlertDialog(ApplicationCenterActivity.this).builder().setTitle("下载").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(ApplicationCenterActivity.this, view, mAppInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).show();
                            break;
                        case 2://可暂时不升级
                            new AlertDialog(ApplicationCenterActivity.this).builder().setTitle("升级").setMsg("应用名称：" + mAppInfo.getApp_name() + "\n" + "大小：" + mAppInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(ApplicationCenterActivity.this, view, mAppInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).setNegativeButton("暂不升级", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //暂不升级可直接进入
                                    try {
                                        mAppInfo.setIsUpdate(true);
                                        mProxyDealApplication.applicationCenterProxy(mAppInfo);
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
            }
        });
        iv_menu.setOnClickListener(this);
        complete.setOnClickListener(this);
        showDialog();
        initClassify();

    }

    /**
     * 获取手机的屏幕密度DPI
     */
    private void initDensityDpi() {
        // TODO Auto-generated method stub
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Width = DisplayUtils.getScreenWidthPixels(this);
        Height = DisplayUtils.getScreenHeightPixels(this);
    }

    /**
     * 获取初始化数据
     *
     * @return
     * @list
     */
    private List<AppInfo> getList() {
        // TODO Auto-generated method stub

        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getApplicationCenterInfo();
        ArrayList<AppInfo> appcenterList = mAppliationCenterDao.getAppcenterList();
        ArrayList<AppInfo> appInfosResult = appInfos;
        try {
            if(null != appInfos && appcenterList != null ){
                for(int i = 0; i < appcenterList.size(); i++){
                    long app_id = appcenterList.get(i).getApp_id();
                    for(int j = 0; j < appInfos.size(); j++){
//                        if(appInfos.get(j).getApp_type() == 7){
//                            for(int k = 0; k < appInfos.get(j).getClassifyAppInfo().size();k++){
//                                if(app_id == appInfos.get(j).getClassifyAppInfo().get(k).getApp_id()){
//                                    break;
//                                }
//                            }
//                        }
                      if(app_id == appInfos.get(j).getApp_id()){
                          break;
                      }else{
                          if(j == appInfos.size()-1){
                              appInfosResult.add(appcenterList.get(i));
                          }
                      }
                    }
                }

                for(int i = 0; i < appInfos.size();i++){
                    if(appInfos.get(i).getApp_type() == 7){
                        if(null != appInfos && null != appInfos.get(i)  && null != appInfos.get(i).getClassifyAppInfo() && appInfos.get(i).getClassifyAppInfo().size() > 0){
                            for(int j = 0; j < appInfos.get(i).getClassifyAppInfo().size();j++){
                                AppInfo appInfo = appInfos.get(i).getClassifyAppInfo().get(j);
                                appInfosResult.remove(appInfo);
                            }
                        }
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mProxyDealApplication != null) {
            for (AppInfo AppInfos : appInfosResult) {
                mProxyDealApplication.interceptAPK(AppInfos);
            }
        }
        return appInfosResult;
    }

    public void initClassify() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appInfoList = getList();
                isClassify = false;
                for (AppInfo appInfo : appInfoList) {
                    if (appInfo.getApp_type() == 7) {
                        if (appInfo.getAppcenter_include_atfirst() == 1) {
                            isClassify = true;
                            break;
                        }
                    }
                }

                ApplicationCenterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (appInfoList == null || appInfoList.size() == 0) {
                            ll_emptoy.setVisibility(View.VISIBLE);
                            try {
                                if(null != ConcerCountShow.getInstances().getMenuTabItem()){
                                    ArrayList<MenuTabItem> menuTabItem = ConcerCountShow.getInstances().getMenuTabItem();
                                    for(MenuTabItem data : menuTabItem){
                                        switch (data.getButtomEnum()) {
                                            case YYZX:
                                                data.hideMarker();
                                                data.hideNumberMarker();
                                                break;
                                        }
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ll_emptoy.setVisibility(View.GONE);
                        }
                        if (isClassify) {
//                            Map<String,Object> map = new HashMap<String, Object>();
//                            map.put("isHome",isHome);
//                            HTActivityUnit.switchTo(ApplicationCenterActivity.this,ApplicationCenterListActivity.class,map);
//                            ApplicationCenterActivity.this.finish();
                            gradview.setVisibility(View.GONE);
                            mApplicationCenterListChildLayout.setVisibility(View.VISIBLE);
                            mApplicationCenterListChildLayout.setUserMessageScrollView(scroll_view);

                            mApplicationCenterListChildLayout.initData(appInfoList);
                            if (mApplicationCenterListChildLayout.getCenterListChildMap().size() == 0) {
                                ll_emptoy.setVisibility(View.VISIBLE);
                            }
                            mApplicationCenterListChildLayout.setICallBackAppCenterLong(ApplicationCenterActivity.this);
                        } else {

                            gradview.setVisibility(View.VISIBLE);
                            mApplicationCenterListChildLayout.setVisibility(View.GONE);
                            adapter.setData(appInfoList);

                        }
                        dismissDialog();
                    }
                });

            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gradview != null && RefreshTotal.isYYZX()) {
//            appInfoList = getList();
//            adapter.setData(appInfoList);
            if(isHome){
                RefreshTotal.refershYYZX(false);
            }
            initClassify();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:
                progress_.setVisibility(View.VISIBLE);
                gradview.refresh();
                mApplicationCenterListChildLayout.isDelete(false);
                complete.setVisibility(View.GONE);
                iv_menu.setVisibility(View.VISIBLE);
                getResort();
                ArrayList<RequestResortJson> mRequestResortJsonList = copyResortJson();
                //拖动更换顺序
                if(null != mRequestResortJsonList && mRequestResortJsonList.size() > 0){
                    AnsynHttpRequest.requestByPostWithToken(this, mRequestResortJsonList, tuodongUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE_TD, LogManagerEnum.APP_CENTER_RESORT.getFunctionCode());
                }else{
                    adapter.setData(appInfoList);
                    progress_.setVisibility(View.GONE);
                }

                break;
            case R.id.iv_menu:
//                MXAPI.getInstance(this).startOcuChat("098e37f8284e9799f12db850a0d7c384");
                HTActivityUnit.switchTo(this, ApplicationCenterAddActivity.class, null);
//                this.finish();  //2017年3月22日09:36:38
                break;
            case R.id.iv_back:
            case R.id.iv_back_home:
                if (isHome) {
                    BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                } else {
                    this.finish();
                }

                break;
        }
    }

    public ArrayList<RequestResortJson> copyResortJson() {
        ArrayList<RequestResortJson> requestResortJsonArrayList = new ArrayList<RequestResortJson>();
        for (RequestResort requestResort : requestResortArrayList) {
            RequestResortJson mRequestResortJson = new RequestResortJson();

            mRequestResortJson.setAppId(requestResort.getAppId());
            mRequestResortJson.setDisplayOrder(requestResort.getDisplayOrder());
            mRequestResortJson.setPortalId(requestResort.getPortalId());
            mRequestResortJson.setUserId(requestResort.getUserId());
            requestResortJsonArrayList.add(mRequestResortJson);
        }
        return requestResortJsonArrayList;
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

    public ArrayList<RequestResort> getResort() {
        requestResortArrayList = new ArrayList<RequestResort>();
        if (isClassify) {

            for (String key : mApplicationCenterListChildLayout.getCenterListChildMap().keySet()) {
                for (int i = 0; i < mApplicationCenterListChildLayout.getCenterListChildMap().get(key).size(); i++) {
                    RequestResort mRequestResort = new RequestResort();
                    AppInfo appInfo = mApplicationCenterListChildLayout.getCenterListChildMap().get(key).get(i);
                    mRequestResort.setAppId(appInfo.getApp_id());
                    mRequestResort.setDisplayOrder(i + 1);
                    mRequestResort.setPortalId(Long.parseLong(mEmpPortal.portal_id));
                    mRequestResort.setUserId(Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID));
                    mRequestResort.setAppInfo(appInfo);
                    requestResortArrayList.add(mRequestResort);
                }
            }
        } else {
            for (int i = 0; i < appInfoList.size(); i++) {
                // 如果存在分类的话
//                if(appInfoList.get(i).getApp_type() == 7){
//                    for(int n = 0; n < appInfoList.get(i).getClassifyAppInfo().size() ; n++){
//                        RequestResort mRequestResort = new RequestResort();
//                        mRequestResort.setAppId(appInfoList.get(i).getClassifyAppInfo().get(n).getApp_id());
//                        mRequestResort.setDisplayOrder(n + 1);
//                        mRequestResort.setPortalId(Long.parseLong(mEmpPortal.portal_id));
//                        mRequestResort.setUserId(Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID));
//                        mRequestResort.setAppInfo(appInfoList.get(i).getClassifyAppInfo().get(n));
//                        requestResortArrayList.add(mRequestResort);
//                    }
//                }else{
//
//                }
                RequestResort mRequestResort = new RequestResort();
                mRequestResort.setAppId(appInfoList.get(i).getApp_id());
                mRequestResort.setDisplayOrder(i + 1);
                mRequestResort.setPortalId(Long.parseLong(mEmpPortal.portal_id));
                mRequestResort.setUserId(Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID));
                mRequestResort.setAppInfo(appInfoList.get(i));
                requestResortArrayList.add(mRequestResort);
            }


        }
        return requestResortArrayList;
    }

    @Override
    public void onClickItem() {
        iv_menu.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
    }


    @Override
    public void callCenterLong(boolean isLong) {
        iv_menu.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
    }

    @Override
    public void callBackRemoveApp(AppInfo mAppInfo, int ponstion, int classifyIndex) {
        this.mAppInfo = mAppInfo;
        this.ponstion = ponstion;
        this.removeIndex = classifyIndex;
        showDialog();
        setDialogValue("删除中...");
        nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("appids", mAppInfo.getApp_id() + ""));
        Log.d(TAG, "callBackRemoveApp: " + mAppInfo.getApp_name());
        AnsynHttpRequest.requestByPostWithToken(this, nameValuePairList, deleteUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.APP_CENTER_DELETE.getFunctionCode());
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        String functionCode = "";
        if (requestName.equals(HTTPTYPE)) {
            functionCode = LogManagerEnum.APP_CENTER_DELETE.functionCode;
        } else if (requestName.equals(HTTPTYPE_TD)) {
            functionCode = LogManagerEnum.APP_CENTER_RESORT.functionCode;
        }
        requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, deleteUrl, nameValuePairList, this, requestName, functionCode);
        if (requestValue != null && !requestValue.equals("")) {
            if (requestName.equals(HTTPTYPE)) {
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                dismissDialog();
                if (entity != null && entity.code == 200) {
//                mApplicationCenterAddAdapter.setData(entity.result);
                    RefreshTotal.addReshActivity();
                    mAppliationCenterDao.deleteAppInfos(mAppInfo.getApp_id() + "");
                    adapter.deletInfo(mAppInfo, ponstion);
                    mApplicationCenterListChildLayout.delete(mAppInfo, ponstion, removeIndex);
//                    Toast.makeText(this, mAppInfo.getApp_name() + "移除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, entity == null ? "删除出错" : entity.message, Toast.LENGTH_SHORT).show();
                }
            } else if (requestName.equals(HTTPTYPE_TD)) {

                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
//                Toast.makeText(this,  entity.message, Toast.LENGTH_SHORT).show();
                dismissDialog();
                if (entity != null && entity.code == 200) {//拖动成功
                    mAppliationCenterDao.sortAppList(requestResortArrayList);
                    RefreshTotal.addReshActivity();
//                    Toast.makeText(this, requestResortArrayList.getApp_name() + entity.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, entity == null ? "" : entity.message, Toast.LENGTH_SHORT).show();
                }
                progress_.setVisibility(View.GONE);
            }
        } else if (requestName.equals("application")) {
        } else if (!TextUtils.isEmpty(requestValue)) {
            progress_.setVisibility(View.GONE);
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Toast.makeText(this, exceptionMessage, Toast.LENGTH_SHORT).show();
        progress_.setVisibility(View.GONE);
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        StruesHeight -= t;
        scroolHight = t;
//        if(mApplicationCenterListChildLayout != null){
//            mApplicationCenterListChildLayout.onScroll(ll_child_top,t);
//        }
    }
}
