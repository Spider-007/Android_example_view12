package com.htmitech.proxy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.app.widget.DrawableCenterTextView;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.ui.detail.CallBackLayout;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.listener.BackHandledInterface;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.pop.LoadingPopupWindow;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.Conditions;
import com.htmitech.proxy.doman.JumpParameter;
import com.htmitech.proxy.doman.MobileconfigEvent;
import com.htmitech.proxy.doman.Order;
import com.htmitech.proxy.doman.ReportSoso;
import com.htmitech.proxy.doman.ReportSosoResult;
import com.htmitech.proxy.doman.ReportValue;
import com.htmitech.proxy.doman.SosoResult;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.fragment.StatiscalReportFragment;
import com.htmitech.proxy.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.managerApp.ClentAppUnit;

import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;
import com.mx.google.gson.internal.LinkedHashTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

/**
 * 报表的通用构建
 */
public class StatisticalReportActivity extends BaseFragmentActivity implements BackHandledInterface, ObserverCallBackType,
        CallBackDamageTypeGridListener, CallBackLayout, View.OnTouchListener, View.OnClickListener {
    private FromLayout mFromLayout;

    private String com_report_list_mobileconfig_condition;//报表支持的查询条件定义

    private String com_report_list_mobileconfig_define; //表格的整体定义

    private final static String REQUEST_SOSO = "SOSO";//搜索条件
    private final static String REQUEST_VALUE = "BODY";//搜索值

    private final static String REQUEST_SOSO_URL = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.REPORT_SOSO;

    private final static String REQUEST_VALUE_URL = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.REPORT_VALUE;

    private String userID;

    private String appID;

    private String app_version_id;

    private String interface_id = "-1";

    private String key_word = "";

    private String field_name = "";

    private String input_type = "";
    private String default_name = "";
    ;

    private HTMRDataTable mHTMRDataTable;

    private JSONArray conditionJSONArray;

    private String field_value = "";

    private SosoResult mSosoResult = null;

    private ArrayList<SosoResult> sosoResults = null;

    private String label_name = "";

    private StatiscalReportFragment mDamageTypeFragment;

    private Animation upAnimation, downAnimation;

    private LinearLayout ll_content_soso;

    private TextView tv_shaixuan;

    private TextView tv_buttom;

    //    private EmptyLayout mEmptyLayout;
    private DetailActivityLayout mDetailActivityLayout;
    private FunctionPopupWindow mFunctionPopupWindow;
    private AddFloatingActionButton menuMultipleActions;
    private ArrayList<ActionInfo> mDataList;
    private ShareLink shareLink;
    private ArrayList<Conditions> conditionses;
    private Order mOrder;
    private LoadingPopupWindow mLoadingPopupWindow;
    private EmptyLayout layout_down;
    private String conditions = "-1";
    private boolean isShare = false;
    private String userName;
    private ArrayList<Conditions> conditionses1;
    private String com_report_list_mobileconfig_actionbutton_style = "";
    private LinearLayout ll_bottom_style;
    private DrawableCenterTextView tvShare;
    private DrawableCenterTextView tv_select;
    private String com_report_list_mobileconfig_include_share = "";
    private TextView iv_no_add;
    private String com_report_list_mobileconfig_include_security = ""; //是否支持水印
    private String com_report_list_mobileconfig_font_controller = ""; //列表中的字体大小，是否受平台整体字体大小控制

    private String com_report_list_mobileconfig_columns;//列定义
    private TextView tv_center_soso_title;
    private String com_report_list_mobileconfig_event;//	事件定义
    private MobileconfigEvent mobileconfigEvent;

    public JSONArray data;
    private String appName;
    private TextView report_title;
    private TextView title_name;
    private ImageButton title_left_button;
    private Map<String, String> map;
    private INetWorkManager netWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistical_report);

        initView();

        initData();
    }

    private void initData() {
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        try {
            showDialog();
            map = new LinkedHashTreeMap<String, String>();
            Intent intent = getIntent();
            com_report_list_mobileconfig_define = intent.getStringExtra("com_report_list_mobileconfig_define");
            com_report_list_mobileconfig_condition = intent.getStringExtra("com_report_list_mobileconfig_condition");
            com_report_list_mobileconfig_actionbutton_style = intent.getStringExtra("com_report_list_mobileconfig_actionbutton_style");
            com_report_list_mobileconfig_include_share = intent.getStringExtra("com_report_list_mobileconfig_include_share");
            com_report_list_mobileconfig_include_security = intent.getStringExtra("com_report_list_mobileconfig_include_security");
            com_report_list_mobileconfig_font_controller = intent.getStringExtra("com_report_list_mobileconfig_font_controller");
            com_report_list_mobileconfig_event = intent.getStringExtra("com_report_list_mobileconfig_event");
            com_report_list_mobileconfig_columns = intent.getStringExtra("com_report_list_mobileconfig_columns");
            if (!TextUtils.isEmpty(com_report_list_mobileconfig_event)) {
                mobileconfigEvent = JSON.parseObject(com_report_list_mobileconfig_event, MobileconfigEvent.class);
            }
            if (!TextUtils.isEmpty(com_report_list_mobileconfig_actionbutton_style) && com_report_list_mobileconfig_actionbutton_style.equals("0")) {
                ll_bottom_style.setVisibility(View.VISIBLE);
                menuMultipleActions.setVisibility(View.GONE);
            } else {
                ll_bottom_style.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(com_report_list_mobileconfig_include_share) && com_report_list_mobileconfig_include_share.equals("0"))
                tvShare.setVisibility(View.GONE);
            else
                tvShare.setVisibility(View.VISIBLE);
            userName = intent.getStringExtra("userName");
            isShare = intent.getBooleanExtra("isShare", false);
            if (isShare) {
                ll_bottom_style.setVisibility(View.GONE);
                menuMultipleActions.setVisibility(View.GONE);
            }
            conditions = intent.getStringExtra("conditions");
            interface_id = intent.getStringExtra("interface_id");
            app_version_id = intent.getStringExtra("app_version_id");
            appID = intent.getStringExtra("app_id");
            appName = intent.getStringExtra("appName");
            report_title.setText(appName + "");
            title_name.setText(appName + "");
            conditionJSONArray = JSON.parseArray(com_report_list_mobileconfig_condition);
            JSONObject jsonObject = JSON.parseObject(com_report_list_mobileconfig_define);
            String title = jsonObject.getJSONObject("title").getString("text");
            String[] titles = title.split("\\|");
            for (String s : titles) {
                map.put(s, s);
            }
            if (intent.getStringExtra("userID") != null && !"".equals(intent.getStringExtra("userID")))
                userID = intent.getStringExtra("userID");
            else
                userID = PreferenceUtils.getEMPUserID(this);
            sosoResults = new ArrayList<SosoResult>();
            mDataList = new ArrayList<ActionInfo>();
            ActionInfo mActionInfoShare = new ActionInfo();
            mActionInfoShare.setActionID("Share");
            mActionInfoShare.setActionName("分享");
            if (!TextUtils.isEmpty(com_report_list_mobileconfig_include_share) && com_report_list_mobileconfig_include_share.equals("1"))
                mDataList.add(mActionInfoShare);
            ActionInfo mActionInfoSelect = new ActionInfo();
            mActionInfoSelect.setActionID("Select");
            mActionInfoSelect.setActionName("筛选");
            mDataList.add(mActionInfoSelect);
            mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDataList.size());
            mFunctionPopupWindow.initArcMenu(mDataList);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels - 50;

            if (conditionJSONArray != null) {
                JSONObject conditionJSONObject = (JSONObject) conditionJSONArray.get(0);
                field_value = conditionJSONObject.getString("default_value");
                if (interface_id == null || interface_id.equals("-1")) {
                    interface_id = conditionJSONObject.getString("interface_id");
                }
                label_name = conditionJSONObject.getString("label_name");
                key_word = conditionJSONObject.getString("key");
                field_name = conditionJSONObject.getString("field_name");
                input_type = conditionJSONObject.getString("input_type");
                conditionses = new ArrayList<Conditions>();


                if (!isShare) {
                    for (int i = 0; i < conditionJSONArray.size(); i++) {
                        conditionJSONObject = (JSONObject) conditionJSONArray.get(i);
                        String hidden_flag = conditionJSONObject.getString("hidden_flag");

                        field_value = conditionJSONObject.getString("default_value");
                        interface_id = conditionJSONObject.getString("interface_id");
                        label_name = conditionJSONObject.getString("label_name");
                        key_word = conditionJSONObject.getString("key");
                        field_name = conditionJSONObject.getString("field_name");
                        input_type = conditionJSONObject.getString("input_type");
                        default_name = conditionJSONObject.getString("default_name");
                        Conditions mConditions = new Conditions();
                        mConditions.setField_name(field_name);
                        mConditions.setField_value(field_value);
                        conditionses.add(mConditions);
                        if (!TextUtils.isEmpty(map.get(field_name))) {
                            map.put(field_name, default_name);
                        }
                        if (hidden_flag.equals("1")) {
                            continue;
                        }


                        AnsynHttpRequest.requestByPost(this, reportSoso(), REQUEST_SOSO_URL, CHTTP.POST_LOG, this, REQUEST_SOSO + "," + input_type + "," + label_name, "");
//                    try {
//                        Thread.sleep(500);
//                    }catch (Exception e){
//
//                    }
//            }
                    }
                }
            }


            if (isShare) {
                conditionses = (ArrayList<Conditions>) JSON.parseArray(conditions, Conditions.class);
            }
            LogManagerEnum.VIDEO_LIST.appVersionId = app_version_id;
            LogManagerEnum.VIDEO_LIST.app_id = appID;
            showDialog();
            netWorkManager.logFunactionStart(this, this, LogManagerEnum.BB_LIST);
            String name = "";
            for (String key : map.keySet()) {
                name += map.get(key);
            }
            tv_center_soso_title.setText(name);
            mDamageTypeFragment = new StatiscalReportFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("input_type",
                    input_type);
            mDamageTypeFragment.setArguments(mBundle);
            transaction.add(R.id.content_soso, mDamageTypeFragment);
            transaction.commit();

            ll_content_soso.getBackground().setAlpha(150);

            upAnimation = AnimationUtils.loadAnimation(this,
                    R.anim.zt_score_business_query_exit);

            downAnimation = AnimationUtils.loadAnimation(this,
                    R.anim.zt_score_business_query_enter);


            downAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                    ll_content_soso.getBackground().setAlpha(150);
                }
            });
//        if(isShare){
//            menuMultipleActions.setVisibility(View.GONE);
//        }
            tv_buttom.setOnClickListener(this);

            mFromLayout.setOnItemClickListener(new IOnItemClickListener() {
                @Override
                public void onItemClick(ArrayList<Table> arrayList) {
                    if (mobileconfigEvent == null) {
                        return;
                    }


                    if (mobileconfigEvent.getJump_parameter().size() != 0) {
                        JSONObject jsonObject = getJSONObject(arrayList);
                        for (JumpParameter jumpParameter : mobileconfigEvent.getJump_parameter()) {
                            Set<String> keySet = jsonObject.keySet();
                            for (String key : keySet) {
                                if (jumpParameter.getField_value_from().equals(key)) {
                                    if (jumpParameter.getField_value_type().equals("data")) {
                                        jumpParameter.setDefault_value(jsonObject.get(key).toString());
                                    } else if (jumpParameter.getField_value_type().equals("condition")) {
                                        for (Conditions mCon : conditionses1) {
                                            if (mCon.getField_name().equals("" + key)) {
                                                jumpParameter.setDefault_value(mCon.getField_value());
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }
                    AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(StatisticalReportActivity.this);
                    AppInfo appInfo = mAppliationCenterDao.getAppInfo(mobileconfigEvent.getJump_appid());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("mobileconfigEvent", mobileconfigEvent);

//                    ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(StatisticalReportActivity.this);
                    try {
                        ClentAppUnit.getInstance(StatisticalReportActivity.this).setActivity(appInfo, map);
//                        mProxyDealApplication.applicationCenterProxy(appInfo);
                    } catch (NotApplicationException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            dismissDialog();
            mFromLayout.setVisibility(View.GONE);
//            iv_no_add.setVisibility(View.VISIBLE);
            layout_down.setEmptyButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    netWorkManager.logFunactionStart(StatisticalReportActivity.this, StatisticalReportActivity.this, LogManagerEnum.BB_LIST);
                }
            });
            layout_down.setShowEmptyButton(true);
            layout_down.showEmpty();
        }

    }

    public JSONObject getJSONObject(ArrayList<Table> arrayList) {
        for (Table mTable : arrayList) {
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                Set<String> keySet = jsonObject.keySet();
                for (String key : keySet) {
                    if (mTable.key.equals(key) && mTable.value.equals(jsonObject.get(key).toString())) {
                        return jsonObject;
                    }
                }
            }
        }

        return null;
    }

    public String menuViewTag;

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            menuViewTag = v.getTag().toString();
            if (menuViewTag.equals("Share")) {
                ShareListener();
            } else if (menuViewTag.equals("Select")) {
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(StatisticalReportActivity.this,
                        R.anim.zt_score_business_query_enter);


                downAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                        ll_content_soso.getBackground().setAlpha(150);
                    }
                });
                ll_content_soso.setVisibility(View.VISIBLE);
                ll_content_soso.setAnimation(downAnimation);
                mFunctionPopupWindow.dismiss();
                menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
            }
        }

    }

    //分享
    //分享
    public int curItem = 0;
    public String apiUrl = null;

    private void ShareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享报表");
//        shareLink.setDesc(docTitle);
//        shareLink.setThumbnail(iconId);
        shareLink.setUrl(REQUEST_VALUE_URL);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                StatisticalReportActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            private String jsonString;

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                jsonString = JSONArray.toJSONString(conditionses1);
                jsonString = JSON.toJSONString(conditionses1);
                if (curItem == 0) {
                    apiUrl = "TF" + appID + "|" + userID + "|" + app_version_id + "|" + jsonString
                            + "|" + "" + "|" + PreferenceUtils.getEMPUserName(StatisticalReportActivity.this);
                    shareLink.setAppUrl(apiUrl);
                    MXAPI.getInstance(StatisticalReportActivity.this).shareToChat(StatisticalReportActivity.this, shareLink);
                } else {
                    apiUrl = "TF" + appID + "|" + userID + "|" + app_version_id + "|" + jsonString
                            + "|" + JSONObject.toJSONString(mOrder) + "|" + PreferenceUtils.getEMPUserName(StatisticalReportActivity.this);
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享报表");
                    MXAPI.getInstance(StatisticalReportActivity.this).shareToCircle(StatisticalReportActivity.this, shareLink);
                }
                curItem = 0;
            }
        });
        builder.setSingleChoiceItems(pos, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        curItem = which;
                    }
                });
        builder.show();
    }

    /**
     * 初始化定义 从数据库中读取
     */
    public void initHTMRDataTable(String titleJson, String bodyJson) {
        if (TextUtils.isEmpty(titleJson) || TextUtils.isEmpty(bodyJson)) {
            return;
        }
        try {
            JSONObject jsonObjectTile = JSON.parseObject(titleJson);
            JSONObject jsonObjectBody = JSON.parseObject(bodyJson);
            JSONObject resultJSONObject = (JSONObject) jsonObjectBody.get("Result");
            JSONObject jsonObject = new JSONObject();
            jsonObjectTile.put("columns", JSON.parseArray(com_report_list_mobileconfig_columns));
            jsonObject.put("define", jsonObjectTile);
            data = resultJSONObject.getJSONArray("data");
            jsonObject.put("data", data);
            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);
//        String jsonStr = readRawFile();
//        mHTMRDataTable = JSON.parseObject(jsonStr, HTMRDataTable.class);
            if (mHTMRDataTable.getDefine().getWatermark() != null) {
                if (isShare) {
                    mHTMRDataTable.getDefine().getWatermark().setFrozenColumnText(PreferenceUtils.getEMPUserName(this));
                    mHTMRDataTable.getDefine().getWatermark().setText(userName);
                } else {
                    mHTMRDataTable.getDefine().getWatermark().setText(PreferenceUtils.getEMPUserName(this));
                }
            }

        } catch (Exception e) {

        }

    }

    public ReportSoso reportSoso() {
        ReportSoso mReportSoso = new ReportSoso();
        mReportSoso.setApp_id(appID);
        mReportSoso.setUser_id(userID);
//        if(mList!=null&&mList.get(0)!=null)
//            mReportSoso.setInterface_id(mList.get(0).field_name);
//        else
        mReportSoso.setInterface_id(interface_id);
        mReportSoso.setKey_word(key_word);
        return mReportSoso;
    }

    public ReportValue reportValue(ArrayList<Conditions> conditionses) {
        ReportValue mReportValue = new ReportValue();
        mReportValue.setUser_id(userID);
        mReportValue.setApp_id(appID);
        mReportValue.setApp_version_id(app_version_id);
        mReportValue.setConditions(conditionses);
//        mOrder = new Order();
//        mOrder.setField_name(field_name);
//        mOrder.setOrder_type("");
        mReportValue.setOrder(mOrder);

        return mReportValue;

    }


    private void initView() {
        mDetailActivityLayout = (DetailActivityLayout) findViewById(R.id.dal_layout);
        mDetailActivityLayout.setValue(this);
        mFromLayout = (FromLayout) findViewById(R.id.fromlayout);
        menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
        menuMultipleActions.setOnClickListener(this);
        menuMultipleActions.setOnTouchListener(this);
        menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
        ll_content_soso = (LinearLayout) findViewById(R.id.ll_content_soso);
        layout_down = (EmptyLayout) findViewById(R.id.layout_down);
        tv_shaixuan = (TextView) findViewById(R.id.tv_shaixuan);
        tv_buttom = (TextView) findViewById(R.id.tv_buttom);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        iv_no_add = (TextView) findViewById(R.id.iv_no_add);
        report_title = (TextView) findViewById(R.id.report_title);
        title_name = (TextView) findViewById(R.id.title_name);
        tv_center_soso_title = (TextView) findViewById(R.id.tv_center_soso_title);
        ll_bottom_style = (LinearLayout) findViewById(R.id.ll_bottom_style);
        tvShare = (DrawableCenterTextView) findViewById(R.id.tv_share);
        tv_select = (DrawableCenterTextView) findViewById(R.id.tv_select);
        tvShare.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        title_left_button.setOnClickListener(this);
//        mEmptyLayout = (EmptyLayout) findViewById(R.id.layout_search_no);
    }

    public void onClickBack(View v) {
        this.finish();
    }

    @Override
    public void success(final String requestValue, int type, String requestName) {
        if (LogManagerEnum.BB_LIST.getFunctionCode().equals(requestName)) {

            AnsynHttpRequest.requestByPost(this, reportValue(conditionses), REQUEST_VALUE_URL, CHTTP.POST_LOG, this, REQUEST_VALUE + "", "");
        } else if (requestName.contains(REQUEST_SOSO)) {
            try {
                mSosoResult = JSON.parseObject(requestValue, SosoResult.class);
                mSosoResult.setTitle(requestName.split(",")[2]);
                mSosoResult.setInput_type(requestName.split(",")[1]);
                sosoResults.add(mSosoResult);
                mDamageTypeFragment.refresh(sosoResults);
            } catch (Exception e) {
                layout_down.setEmptyButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netWorkManager.logFunactionStart(StatisticalReportActivity.this, StatisticalReportActivity.this, LogManagerEnum.BB_LIST);
                    }
                });
                layout_down.setShowEmptyButton(true);
                layout_down.showEmpty();
            }

        } else if (requestName.equals(REQUEST_VALUE)) {
            netWorkManager.logFunactionFinsh(StatisticalReportActivity.this, StatisticalReportActivity.this, "FunctionFinish", LogManagerEnum.BB_LIST.getFunctionCode(), "成功", INetWorkManager.State.SUCCESS);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    initHTMRDataTable(com_report_list_mobileconfig_define, requestValue);

                    StatisticalReportActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (mHTMRDataTable != null) {


                                    if (!TextUtils.isEmpty(com_report_list_mobileconfig_include_security) && com_report_list_mobileconfig_include_security.equals("0")) {
                                        mHTMRDataTable.getDefine().getWatermark().setShow(false);
                                    } else if (!TextUtils.isEmpty(com_report_list_mobileconfig_include_security) && com_report_list_mobileconfig_include_security.equals("1")) {
                                        mHTMRDataTable.getDefine().getWatermark().setShow(true);
                                    }
                                    mHTMRDataTable.getDefine().getTitle().setText(tv_center_soso_title.getText().toString());

                                    mHTMRDataTable.getDefine().getWatermark().setFrozenShow(isShare);
                                    layout_down.hide();
                                    iv_no_add.setVisibility(View.GONE);
                                    mFromLayout.initFromLayout(mHTMRDataTable);
                                    if (!TextUtils.isEmpty(com_report_list_mobileconfig_font_controller) && com_report_list_mobileconfig_font_controller.equals("0")) {
                                        mFromLayout.setSP(false);
                                    } else {
                                        mFromLayout.setSP(true);
                                    }

                                } else {
                                    mFromLayout.setVisibility(View.GONE);
//                                    iv_no_add.setVisibility(View.VISIBLE);
                                    layout_down.setEmptyButtonClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            netWorkManager.logFunactionStart(StatisticalReportActivity.this, StatisticalReportActivity.this, LogManagerEnum.BB_LIST);
                                        }
                                    });
                                    layout_down.setShowEmptyButton(true);
                                    layout_down.showEmpty();
                                }
                            } catch (Exception e) {
                                mFromLayout.setVisibility(View.GONE);
//                                iv_no_add.setVisibility(View.VISIBLE);
                                layout_down.setEmptyButtonClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        netWorkManager.logFunactionStart(StatisticalReportActivity.this, StatisticalReportActivity.this, LogManagerEnum.BB_LIST);
                                    }
                                });
                                layout_down.setShowEmptyButton(true);
                                layout_down.showEmpty();
                                e.printStackTrace();

                            }

                            dismissDialog();
                        }
                    });
                }
            }).start();


        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.BB_LIST.getFunctionCode().equals(requestName)) {
            AnsynHttpRequest.requestByPost(this, reportValue(conditionses), REQUEST_VALUE_URL, CHTTP.POST_LOG, this, REQUEST_VALUE + "", "");
        } else {
            netWorkManager.logFunactionFinsh(this, this, "FunctionFinish", LogManagerEnum.BB_LIST.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);

            dismissDialog();
        }

//        if (!Utils.isNetworkAvailable()) {
//            mEmptyLayout.showNowifi();
//        } else {
//            mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//            mEmptyLayout.showError();
//        }
    }

    @Override
    public void notNetwork() {
        dismissDialog();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    @Override
    public void callBack(ArrayList<ReportSosoResult> list) {
        showDialog();
        conditionses1 = new ArrayList<Conditions>();
        for (ReportSosoResult mReportSosoResult : list) {


            int index = 0;
            int hidden = 0;
            for (int i = 0; i < conditionJSONArray.size(); i++) {
                if (hidden <= mReportSosoResult.getPostion()) {
                    JSONObject conditionJSONObject = (JSONObject) conditionJSONArray.get(i);
                    String hidden_flag = conditionJSONObject.getString("hidden_flag");
                    if (hidden_flag.equals("1")) {
                        index++;
                    } else {
                        hidden++;
                    }
                }

            }
            JSONObject conditionJSONObject = (JSONObject) conditionJSONArray.get(mReportSosoResult.getPostion() + index);
            String field_name = conditionJSONObject.getString("field_name");
            Conditions mConditions = new Conditions();
            mConditions.setField_name(field_name);
            mConditions.setField_value(mReportSosoResult.getValue());
            conditionses1.add(mConditions);
            if (!TextUtils.isEmpty(map.get(field_name))) {
                map.put(field_name, mReportSosoResult.getName());
            }
        }
        String name = "";
        for (String key : map.keySet()) {
            name += map.get(key);
        }

        tv_center_soso_title.setText(name);
        if (conditionses1.size() == 0) {
            Conditions mConditions = new Conditions();
            mConditions.setField_name(field_name);
            mConditions.setField_value(field_value);
            conditionses1.add(mConditions);
        }

        for (Conditions mConditions : conditionses) {
            boolean isEquals = false;
            for (Conditions mConditions1 : conditionses1) {
                if (mConditions.field_name.equals(mConditions1.getField_name())) {
                    isEquals = true;
                }
            }
            if (isEquals) {
                continue;
            }
            conditionses1.add(mConditions);
        }


        AnsynHttpRequest.requestByPost(this, reportValue(conditionses1), REQUEST_VALUE_URL, CHTTP.POST_LOG, this, REQUEST_VALUE + "", "");
    }

    public void onClickShaixuan(View view) {
        ll_content_soso.clearAnimation();
        downAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_enter);


        downAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                ll_content_soso.getBackground().setAlpha(150);
            }
        });
        ll_content_soso.setVisibility(View.VISIBLE);
        ll_content_soso.setAnimation(downAnimation);
    }

    @Override
    public void complete() {
        upAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zt_score_business_query_exit);

        ll_content_soso.setBackgroundColor(getResources().getColor(R.color.transparent));
        ll_content_soso.setVisibility(View.GONE);
        ll_content_soso.setAnimation(upAnimation);


    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }


    @Override
    public void callBackLayout() {
        if (action_move)
            menuMultipleActions.layout(left, top, right, bottom);
    }

    int popupHeight;
    int popupWidth;
    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;
    private boolean isTuoZhuai = false;
    private float x, y;
    private float ex, ey;
    private int left, top, right, bottom;
    private boolean action_move = false;
    private long startTime = 0;
    private long endTime = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        ex = 0;
        ey = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                startTime = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                isTuoZhuai = false;
                break;
            /**
             * layout(l,t,r,b) l Left position, relative to parent t Top position,
             * relative to parent r Right position, relative to parent b Bottom
             * position, relative to parent
             * */
            case MotionEvent.ACTION_MOVE:

                action_move = true;
                isTuoZhuai = true;
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                left = v.getLeft() + dx;
                top = v.getTop() + dy;
                right = v.getRight() + dx;
                bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > screenWidth) {
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if (mFunctionPopupWindow != null) {
                    if (top - (mFunctionPopupWindow.isShowing() ? popupHeight : 0) < 0) {
                        top = mFunctionPopupWindow.isShowing() ? popupHeight : 0;
                        bottom = top + v.getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - v.getHeight();
                    }

                    v.layout(left, top, right, bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    mFunctionPopupWindow.update(location[0] - popupWidth, location[1]
                            - popupHeight, -1, -1);
                }
                break;
            case MotionEvent.ACTION_UP:

                endTime = System.currentTimeMillis();
                ex = event.getX() - x;
                ey = event.getY() - y;
                //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                //以前是根据点击挪动距离来进行判断，有些手机敏感性强导致无法弹出等问题
                if ((endTime - startTime) > 0.1 * 2000L) {
                    isTuoZhuai = true;
                } else {
                    isTuoZhuai = false;
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.function:
                if (!isTuoZhuai) {
                    if (mFunctionPopupWindow == null) {
                        return;
                    }
                    if (!mFunctionPopupWindow.isShowing()) {
//					menuMultipleActions.startAnimation(animation);
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_on));
                        mFunctionPopupWindow = new FunctionPopupWindow(this,
                                new MenuOnClickListener(), mDataList.size());
                        mFunctionPopupWindow.initArcMenu(mDataList);
                        popupWidth = mFunctionPopupWindow.mMenuView.getMeasuredWidth();
                        popupWidth = DeviceUtils.dip2px(this, 55) + popupWidth;

                        popupHeight = mFunctionPopupWindow.getHeight();
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        mFunctionPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                (location[0] - popupWidth), location[1]
                                        - popupHeight);
                        mFunctionPopupWindow.update();
                    } else {
                        menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
                        mFunctionPopupWindow.dismiss();
                    }
                }
                break;

            case R.id.tv_buttom:

                complete();

                break;
            case R.id.tv_share:
                ShareListener();
                break;
            case R.id.title_left_button:
                onClickBack(title_left_button);
                break;
            case R.id.tv_select:
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(StatisticalReportActivity.this,
                        R.anim.zt_score_business_query_enter);


                downAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        ll_content_soso.setBackgroundColor(getResources().getColor(R.color.bantouming));
                        ll_content_soso.getBackground().setAlpha(150);
                    }
                });
                ll_content_soso.setVisibility(View.VISIBLE);
                ll_content_soso.setAnimation(downAnimation);
                break;
        }

    }
}
