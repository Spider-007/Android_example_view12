package com.htmitech.emportal.ui.applicationcenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.doman.RequestResort;
import com.htmitech.proxy.interfaces.CallBackRemove;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.HTActivityUnit;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.FastJsonUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * tony
 * <p/>
 * 应用分类第二种 列表分类
 */
public class ApplicationCenterListActivity extends BaseFragmentActivity implements View.OnClickListener, ICallBackAppCenterLong, CallBackRemove, ObserverCallBackType, UserMessageScrollView.OnScrollListener {
    private static final String TAG = "ApplicationCenterActivi";
    public static int StruesHeight = 0;
    public RelativeLayout rl_strues;
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
    private int scroolHight = 0;
    private LinearLayout ll_emptoy;
    private boolean isHome = false;
    private ApplicationCenterListChildLayout mApplicationCenterListChildLayout;
    private ArrayList<BinnerBitmapMessage> binnerBitmapMessages;
    private ImageView iv_back_home;
    private static long lastClickTime = System.currentTimeMillis();
    private UserMessageScrollView scroll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_center_list);
        initView();
        initData();
    }

    public void initView() {

        rl_strues = (RelativeLayout) findViewById(R.id.rl_strues);
        mApplicationCenterListChildLayout = (ApplicationCenterListChildLayout) findViewById(R.id.ll_app_center_list_child);
        complete = (TextView) findViewById(R.id.complete);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_emptoy = (LinearLayout) findViewById(R.id.layout_search_no);
        iv_back_home = (ImageView) findViewById(R.id.iv_back_home);
        scroll_view = (UserMessageScrollView) findViewById(R.id.scroll_view);
    }


    public void initData() {
        mProxyDealApplication = new ProxyDealApplicationPlugin(ApplicationCenterListActivity.this);
        mAppliationCenterDao = new AppliationCenterDao(this);
        mEmpPortal = mAppliationCenterDao.getPortalId();
        complete.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        mAppliationCenterDao.replace(mEmpPortal.portal_id);
        isHome = getIntent().getBooleanExtra("isHome", false);
        mApplicationCenterListChildLayout.setCallBackRemove(this);
        if (isHome) {
            iv_back_home.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.GONE);
        }

        deleteUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.REMOVE + "/" + mEmpPortal.portal_id + "/" + OAConText.getInstance(this).UserID;
        tuodongUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.RESORTAPP;
        ViewTreeObserver vto = rl_strues.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                // TODO Auto-generated method stub
                StruesHeight = rl_strues.getMeasuredHeight() - scroolHight;
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                appInfoList = getList();
                ApplicationCenterListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mApplicationCenterListChildLayout.setUserMessageScrollView(scroll_view);

                        mApplicationCenterListChildLayout.initData(appInfoList);

                        mApplicationCenterListChildLayout.setICallBackAppCenterLong(ApplicationCenterListActivity.this);
                    }
                });

            }
        }).start();
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
        if (mProxyDealApplication != null) {
            for (AppInfo AppInfos : appInfos) {
                mProxyDealApplication.interceptAPK(AppInfos);
            }
        }
        if (appInfos == null || appInfos.size() == 0) {
            ll_emptoy.setVisibility(View.VISIBLE);
        } else {
            ll_emptoy.setVisibility(View.GONE);
        }
        return appInfos;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.channelConstant && mApplicationCenterListChildLayout != null && scroll_view != null) {
            appInfoList = getList();
            mApplicationCenterListChildLayout.initData(appInfoList);
            Constant.channelConstant = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:
//                gradview.refresh();
                complete.setVisibility(View.GONE);
                iv_menu.setVisibility(View.VISIBLE);
                mApplicationCenterListChildLayout.isDelete(false);
                ArrayList<RequestResort> mRequestResortList = getResort();
                //拖动更换顺序
                AnsynHttpRequest.requestByPostWithToken(this, mRequestResortList, tuodongUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE_TD, LogManagerEnum.APP_CENTER_RESORT.functionCode);
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

//    /**
//     * 是否快速点击两次
//     *
//     * @return
//     */
//    public static boolean isFastDoubleClick() {
//        long time = System.currentTimeMillis();
//        if (time - lastClickTime < 500) {
//            return true;
//        }
//        lastClickTime = time;
//        return false;
//    }

//    public ArrayList<RequestResort> getResort() {
//        requestResortArrayList = new ArrayList<RequestResort>();
//
//        for (int i = 0; i < appInfoList.size(); i++) {
//            RequestResort mRequestResort = new RequestResort();
//            mRequestResort.setAppId(appInfoList.get(i).getApp_id());
//            mRequestResort.setDisplayOrder(i + 1);
//            mRequestResort.setPortalId(Long.parseLong(mEmpPortal.portal_id));
//            mRequestResort.setUserId(Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID));
//            mRequestResort.setAppInfo(appInfoList.get(i));
//            requestResortArrayList.add(mRequestResort);
//        }
//        return requestResortArrayList;
//    }

    //    @Override
//    public void onClickItem() {
//        iv_menu.setVisibility(View.GONE);
//        complete.setVisibility(View.VISIBLE);
//    }
    public ArrayList<RequestResort> getResort() {
        requestResortArrayList = new ArrayList<RequestResort>();
        for (String key : mApplicationCenterListChildLayout.getCenterListChildMap().keySet()) {
            ArrayList<AppInfo> appInfos = mApplicationCenterListChildLayout.getCenterListChildMap().get(key);
            for (int i = 0; i < appInfos.size(); i++) {
                RequestResort mRequestResort = new RequestResort();
                mRequestResort.setAppId(appInfos.get(i).getApp_id());
                mRequestResort.setDisplayOrder(i + 1);
                mRequestResort.setPortalId(Long.parseLong(mEmpPortal.portal_id));
                mRequestResort.setUserId(Long.parseLong(OAConText.getInstance(HtmitechApplication.instance()).UserID));
                mRequestResort.setAppInfo(appInfos.get(i));
                requestResortArrayList.add(mRequestResort);
            }
        }

        return requestResortArrayList;
    }

    @Override
    public void callBackRemoveApp(AppInfo mAppInfo, int ponstion, int classifyIndex) {
        this.mAppInfo = mAppInfo;
        this.ponstion = ponstion;

        showDialog();
        setDialogValue("删除中...");
        nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("appids", mAppInfo.getApp_id() + ""));
        Log.d(TAG, "callBackRemoveApp: " + mAppInfo.getApp_name());
        AnsynHttpRequest.requestByPostWithToken(this, nameValuePairList, deleteUrl, CHTTP.POSTWITHTOKEN, this, HTTPTYPE, LogManagerEnum.APP_CENTER_DELETE.functionCode);
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
                    Constant.channelConstant = true;
                    mAppliationCenterDao.deleteAppInfos(mAppInfo.getApp_id() + "");
                    adapter.deletInfo(mAppInfo, ponstion);
//                    Toast.makeText(this, mAppInfo.getApp_name() + "移除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, entity == null ? "删除出错" : entity.message, Toast.LENGTH_SHORT).show();
                }
            } else if (requestName.equals(HTTPTYPE_TD)) {
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                dismissDialog();
                if (entity != null && entity.code == 200) {//拖动成功
                    mAppliationCenterDao.sortAppList(requestResortArrayList);
                    Constant.channelConstant = true;
//                    Toast.makeText(this, requestResortArrayList.getApp_name() + entity.message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, entity == null ? "" : entity.message, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestName.equals("application")) {
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {

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
    }


    @Override
    public void callCenterLong(boolean isLong) {
        iv_menu.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
    }


}
