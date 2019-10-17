package com.htmitech.htexceptionmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.widget.LoadingView;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.emportal.ui.widget.NewTopTabIndicator;
import com.htmitech.htexceptionmanage.adapter.ManageExceptionForCollectionAdapter;
import com.htmitech.htexceptionmanage.entity.ChangeStateEntity;
import com.htmitech.htexceptionmanage.entity.ExceptionChangeStateparameter;
import com.htmitech.htexceptionmanage.entity.ExceptionDetailEntity;
import com.htmitech.htexceptionmanage.entity.ExceptionDetailparameter;
import com.htmitech.htexceptionmanage.entity.ExceptionList;
import com.htmitech.htworkflowformpluginnew.entity.DocResultInfo;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.myenum.LogManagerEnum;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.thread.Network;

import htmitech.com.componentlibrary.entity.EditFieldList;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/***
 * 消息提醒 详情
 *
 * @author joe
 * @date 2017-9-25 16:07:47
 */
public class ManageExceptionDetalActivity extends cn.feng.skin.manager.base.BaseFragmentActivity implements
        ObserverCallBackType, View.OnClickListener {

    private static final String TAG = ManageExceptionDetalActivity.class.getSimpleName();
    private MainViewPager mViewPager_mycollection;
    private ManageExceptionForCollectionAdapter mAdapter;
    private NewTopTabIndicator mMyTopTabIndicator;
    public DocResultInfo mDocResultInfo;
    private LoadingView mLoadingView = null;

    private DetailActivityLayout mDetailActivityLayout;
    private EmptyLayout mEmptyLayout;
    public String app_id;


    private String manegeExceptionDetailUrl;
    private String alertTitle;
    private String alertId;
    private static final String EXCEPTIONDETAIL = "exceptiondetail";
    private Gson mGson = new Gson();
    private ExceptionDetailEntity exceptionDetailEntity;
    private String mSourceContentInfo;
    private ExceptionDetailparameter exceptionDetailparameter;
    private ExceptionList exceptionList;


    private String manageExceptionChangeStateUrl;
    private static final String EXCEPTIONCHANGESTATE = "exceptionchangestate";
    private ExceptionChangeStateparameter exceptionChangeStateparameter;
    private ChangeStateEntity changeStateEntity;
    private int isWaterSecurity;

    public ExceptionList getExceptionList() {
        return exceptionList;
    }

    public int getIsWaterSecurity() {
        return isWaterSecurity;
    }

    public String getSourceContentInfo() {
        return mSourceContentInfo;
    }

    protected int getLayoutById() {
        return R.layout.activity_exception_detail;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_exception_detail, null);
        setContentView(mDetailActivityLayout);
        mEmptyLayout = (EmptyLayout) mDetailActivityLayout.findViewById(R.id.emptyLayout);
        mEmptyLayout.setErrorButtonClickListener(this);
        initView();
        initData();

    }

    /**
     * 初始化UI
     */
    //该方法在 onCreate 的时候调用
    protected void initView() {
        EditFieldList mustFieldList = EditFieldList.getInstance();
        mustFieldList.Clear();
        View titleBar = findViewById(R.id.layout_detail_titlebar);    //构件titlebar
        titleBar.findViewById(R.id.imgview_titlebar_back).setOnClickListener(
                this);
        mLoadingView = (LoadingView) findViewById(R.id.loadingview_detail); //构建 加载视图

        Intent intent = getIntent();
        alertTitle = intent.getStringExtra("alertTitle");
        alertId = intent.getStringExtra("alertId");
        isWaterSecurity = intent.getIntExtra("com_alert_mobileconfig_include_security", 0);

        ((TextView) titleBar.findViewById(R.id.textview_titlebar_title))
                .setText(alertTitle);

    }

    private void initData() {

        showLoadingView();
        manegeExceptionDetailUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.MANAGE_EXCEPTION_DETAIL;
        exceptionDetailparameter = new ExceptionDetailparameter();
        exceptionDetailparameter.setAlertId(alertId);
        AnsynHttpRequest.requestByPostWithToken(this, exceptionDetailparameter, manegeExceptionDetailUrl, CHTTP.POSTWITHTOKEN, this, EXCEPTIONDETAIL, LogManagerEnum.GGENERAL.getFunctionCode());
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (EXCEPTIONDETAIL.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, manegeExceptionDetailUrl, exceptionDetailparameter, this, requestName, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                try {
                    mEmptyLayout.hide();
                    exceptionDetailEntity = mGson.fromJson(requestValue.toString(), ExceptionDetailEntity.class);
                    exceptionList = exceptionDetailEntity.getResult();
                    mSourceContentInfo = exceptionDetailEntity.getResult().getSourceContentObject();
                    if (mSourceContentInfo != null) {
                        mViewPager_mycollection = (MainViewPager) findViewById(R.id.viewPager_qarank);
                        FragmentManager fm = getSupportFragmentManager();
                        mViewPager_mycollection.setOffscreenPageLimit(2);
                        mMyTopTabIndicator = (NewTopTabIndicator) this
                                .findViewById(R.id.topTabIndicator_detail);
                        ArrayList<ManageExceptionForCollectionAdapter.ChildType> list = new ArrayList<ManageExceptionForCollectionAdapter.ChildType>();
                        List<String> listStr = new ArrayList<String>();
                        listStr.add("提醒信息");
                        list.add(ManageExceptionForCollectionAdapter.ChildType.TAB_FORM);
                        listStr.add("提醒来源");
                        list.add(ManageExceptionForCollectionAdapter.ChildType.TAB_TEXT);
                        String[] arrayTopTabIndicator = new String[listStr.size()];
                        listStr.toArray(arrayTopTabIndicator);
                        mMyTopTabIndicator.setCommonData2(mViewPager_mycollection,
                                arrayTopTabIndicator, R.color.color_title,
                                R.color.color_ff888888);
                        mAdapter = new ManageExceptionForCollectionAdapter(HtmitechApplication
                                .instance().getApplicationContext(), fm, list);
                        mViewPager_mycollection.setAdapter(mAdapter);
                        mViewPager_mycollection.setNoScroll(true);

                        exceptionChangeStateparameter = new ExceptionChangeStateparameter();
                        exceptionChangeStateparameter.setAlertIdList(alertId);
                        manageExceptionChangeStateUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.MANAGE_EXCEPTION_CHANGESTATE;
                        exceptionChangeStateparameter.setUserId(OAConText.getInstance(HtmitechApplication.getInstance()).UserID);
                        AnsynHttpRequest.requestByPostWithToken(this, exceptionChangeStateparameter, manageExceptionChangeStateUrl, CHTTP.POSTWITHTOKEN, this, EXCEPTIONCHANGESTATE, LogManagerEnum.GGENERAL.getFunctionCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ManageExceptionDetalActivity.this.finish();
                        }
                    });
                    mEmptyLayout.showError();
                }
                hideLoadingView();
            }
        } else if (EXCEPTIONDETAIL.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, manageExceptionChangeStateUrl, exceptionChangeStateparameter, this, requestName, LogManagerEnum.GGENERAL.getFunctionCode());
            if (requestValue != null && !requestValue.equals("")) {
                changeStateEntity = mGson.fromJson(requestValue.toString(), ChangeStateEntity.class);
                Log.d(TAG, "EXCEPTIONDETAIL: " + changeStateEntity.getMessage());
            }
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (!Network.checkNetWork(HtmitechApplication.instance().getApplicationContext())) {
            mEmptyLayout.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
            mEmptyLayout.showNowifi();
        } else {
            mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManageExceptionDetalActivity.this.finish();
                }
            });
            mEmptyLayout.showError();
        }
    }

    @Override
    public void notNetwork() {
        mEmptyLayout.setNoWifiButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        mEmptyLayout.showNowifi();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgview_titlebar_back:
                exit();
                break;
            case R.id.buttonError: //重试按钮
                exit();
                break;
        }
    }

    /**
     * 退出
     **/
    public void exit() {
        ClassEvent mClassEvent = new ClassEvent();
        mClassEvent.msg = "angle";
        EventBus.getDefault().post(mClassEvent);
        finish();
        finish();
    }

    private void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.stopLoading();
    }

    private void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startLoading();
    }

}
