package com.htmitech.emportal.ui.homepage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.homepage.widget.PortalSwitchPopwindow;
import com.htmitech.emportal.ui.homepage.widget.XListView;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.htworkflowformpluginnew.entity.DocSearchParameters;
import com.htmitech.htworkflowformpluginnew.util.WorkFlowCountHttpUtil;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.CallBackSuccess;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.proxy.util.AngleUntil;
import com.minxing.client.ClientTabActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.RefreshTotal;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;

/***
 * 04
 * 移动工作门户  fragment
 *
 * @author Jeff
 */
public class PortalHomeFragmentActivity extends MyBaseFragmentActivity implements
        IBottomItemSelectCallBack, View.OnClickListener ,CallBackSuccess {

    private ToRightPopMenum functionPopMenu;
    private ImageView iv_daiban_more, iv_daiban_person;
    private TextView tv_portal_title;
    private XListView lv_portal;
    private ListViewAdapter mListViewAdapter;
    private List<BinnerBitmapMessage> currentOcuList = new ArrayList<BinnerBitmapMessage>();
    private int CurrentTabIndex = -1;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    private List<AppInfo> appInfoList;
    private LinearLayout layout_search_no;
    private RelativeLayout relativeLayoutTitle;
    private ProgressBar progressBar;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    if(currentOcuList.size() == 0){
                        layout_search_no.setVisibility(View.VISIBLE);
                    }else{
                        layout_search_no.setVisibility(View.GONE);
                    }
                    mListViewAdapter =  new ListViewAdapter(currentOcuList,PortalHomeFragmentActivity.this);

                    lv_portal.setAdapter(mListViewAdapter);

                    for (BinnerBitmapMessage mBinnerBitmapMessage : currentOcuList) {
                        if (mBinnerBitmapMessage.compCode.contains("com_workflow")) {
                            setAngleNumber(mBinnerBitmapMessage);
                        } else if (mBinnerBitmapMessage.compCode.contains("com_commonform")) {
                            AngleUntil.setAngleNumberGeneralForm(mBinnerBitmapMessage, PortalHomeFragmentActivity.this, mListViewAdapter);
                        }

                    }
                    progressBar.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private ImageView portalMessageFlag;
    private PortalSwitchPopwindow mPortalSwitchPopwindow;
    private AppliationCenterDao mAppliationCenterDao;

    @Override
    protected int getLayoutById() {
        return R.layout.activity_portalhome;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        ComponentInit.getInstance().setSuccess(this);
        mProxyDealApplication = new ProxyDealApplicationPlugin(PortalHomeFragmentActivity.this);
        appInfoList = getList();
        iv_daiban_person = (ImageView) findViewById(R.id.iv_daiban_person);
        iv_daiban_person.setOnClickListener(this);
        iv_daiban_more = (ImageView) findViewById(R.id.iv_daiban_more);
        layout_search_no = (LinearLayout)findViewById(R.id.layout_search_no);
        iv_daiban_more.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_);
        tv_portal_title = (TextView) findViewById(R.id.tv_portal_title);
        portalMessageFlag = (ImageView) findViewById(R.id.protal_message_flag_pro);
        ImageView portalArrowFlag = (ImageView) findViewById(R.id.portal_flag_pro);
        //用来作为显示popwindow的基线，在这个relativelayouttitle下面显示popwindow
        relativeLayoutTitle = (RelativeLayout) findViewById(R.id.layout_daiban_titlebar);
//        tv_portal_title.setText(getString(R.string.app_name));
        lv_portal = (XListView) findViewById(R.id.lv_portal);
        mAppliationCenterDao = new AppliationCenterDao(this);
        EmpPortal portalId = mAppliationCenterDao.getPortalId();
        tv_portal_title.setText(portalId != null ? portalId.getPortal_name() : "首页");
        functionPopMenu = new ToRightPopMenum(this);
        functionPopMenu.setView(ApplicationAllEnum.ZY.tab_item_id, iv_daiban_more);
//        mAppliationCenterDao = new AppliationCenterDao(this);
        mPortalSwitchPopwindow = new PortalSwitchPopwindow(getParent());
        if (null != mAppliationCenterDao && mAppliationCenterDao.getPortalAll().size() > 1) {
            portalArrowFlag.setVisibility(View.VISIBLE);
            tv_portal_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });
            portalArrowFlag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mPortalSwitchPopwindow.isShowing()) {
                        mPortalSwitchPopwindow.setView();
                        mPortalSwitchPopwindow.showAsDropDown(relativeLayoutTitle);
                    }
                }
            });
        }
        //启动帮助页面
        if (PreferenceUtils.isLoginDaiYiBan(this)) {
            PreferenceUtils.setLoginDaiYiBan(this, false);
            Intent i = new Intent(this, HelpActivity.class);
            i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
            startActivity(i);
        }
        setClickListener();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        if (event.msg.equals("redflag")) {
            boolean aBoolean = false;
            AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(PortalHomeFragmentActivity.this);
            ArrayList<EmpPortal> portalAll = mAppliationCenterDao.getPortalAll();
//            SharedPreferences sp = PortalHomeFragmentActivity.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
            SecuritySharedPreference sp = new SecuritySharedPreference(PortalHomeFragmentActivity.this, PreferenceUtils.PREFERENCE_SYSTEM_2, Context.MODE_PRIVATE);
            for (int i = 0; i < portalAll.size(); i++) {
                aBoolean = sp.getBoolean(portalAll.get(i).getPortal_id(), false);
                if (aBoolean && null != portalMessageFlag) {
                    portalMessageFlag.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (appInfoList != null) {
                    if (currentOcuList != null) {
                        currentOcuList.clear();
                    }
                    for (AppInfo appInfo : appInfoList) {
                        BinnerBitmapMessage mBinnerBitmapMessage = new BinnerBitmapMessage(
                                null,
                                appInfo.getApp_code(), appInfo.getApp_shortname(), "", appInfo.getTab_item_id(), appInfo, appInfo.getComp_code());
                        if (!currentOcuList.contains(mBinnerBitmapMessage))
                            currentOcuList.add(mBinnerBitmapMessage);
                    }
                }
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
        if (RefreshTotal.isZY() && lv_portal != null) {
            appInfoList = getList();
            initData();
            RefreshTotal.refershZY(false);
        }

        super.onResume();
    }

    /**
     * 获取初始化数据
     *
     * @return
     * @list
     */
    private List<AppInfo> getList() {
        // TODO Auto-generated method stub
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
        ArrayList<AppInfo> appInfos = mAppliationCenterDao.getApplicationCenterInfo();
        if (mProxyDealApplication != null) {
            for (AppInfo AppInfos : appInfos) {
                mProxyDealApplication.interceptAPK(AppInfos);
            }
        }
        return appInfos;
    }

    private void setClickListener() {
        lv_portal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                final BinnerBitmapMessage currentOcuInfo = ((ListViewAdapter) lv_portal.getAdapter()).getCurrentOcuList().get(position);
                try {
                    if (currentOcuInfo.appInfo.getApk_flag() != 3) {
                        RefreshTotal.addReshActivity();
                    }
                    currentOcuInfo.appInfo.setView(lv_portal);
                    int success = mProxyDealApplication.applicationCenterProxy(currentOcuInfo.appInfo);

                    switch (success) {
                        case 1: //强制升级以及下载
                            new AlertDialog(PortalHomeFragmentActivity.this).builder().setTitle("下载").setMsg("应用名称：" +currentOcuInfo.appInfo.getApp_name() + "\n" + "大小：" + currentOcuInfo.appInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(PortalHomeFragmentActivity.this, view, currentOcuInfo.appInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).show();
                            break;
                        case 2://可暂时不升级
                            new AlertDialog(PortalHomeFragmentActivity.this).builder().setTitle("升级").setMsg("应用名称：" + currentOcuInfo.appInfo.getApp_name() + "\n" + "大小：" + currentOcuInfo.appInfo.getmAppVersion().getApp_filesize()).setPositiveButton("下载", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApplicationDown mApplicationDown = new ApplicationDown(PortalHomeFragmentActivity.this, view, currentOcuInfo.appInfo, position);
                                    mApplicationDown.initView();
                                }
                            }).setNegativeButton("暂不升级", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //暂不升级可直接进入
                                    try {
                                        currentOcuInfo.appInfo.setIsUpdate(true);
                                        mProxyDealApplication.applicationCenterProxy(currentOcuInfo.appInfo);
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
    }

    public void setAngleNumber(final BinnerBitmapMessage mBinnerBitmapMessage) {
//        String daiban_yiban_url = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.USER_DAIBAN_YIBAN_COUNT;


        DocSearchParameters docSearchParameters = new DocSearchParameters();
//        docSearchParameters.context = OAConText.getInstance(this);
        docSearchParameters.appId = mBinnerBitmapMessage.appInfo.getApp_id() + "";
        docSearchParameters.title = "";
        if (mBinnerBitmapMessage.appid.equals("OA_todo_showalltodo")) {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
        } else if (mBinnerBitmapMessage.appid.equals("OA_todo_showyiban")) {
            docSearchParameters.todoFlag = "1"; // 0，待办；1，已办
        } else {
            docSearchParameters.todoFlag = "0"; // 0，待办；1，已办
            docSearchParameters.modelName = "";
        }

        docSearchParameters.recordStartIndex = 0;
        docSearchParameters.recordEndIndex = 14;
        WorkFlowCountHttpUtil mWorkFlowCountHttpUtil = new WorkFlowCountHttpUtil();
        mWorkFlowCountHttpUtil.ShowNumber(mBinnerBitmapMessage, PortalHomeFragmentActivity.this, docSearchParameters, mListViewAdapter);

    }

    @Override
    public void onFragmentTabClick(int position) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.iv_daiban_person:
                ((ClientTabActivity) getParent()).callUserMeesageMain();
                break;
            case R.id.iv_daiban_more:
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(arg0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void sysUserSuccess(boolean flag) {
        if(flag){
            appInfoList = getList();
            initData();
        }
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
}
