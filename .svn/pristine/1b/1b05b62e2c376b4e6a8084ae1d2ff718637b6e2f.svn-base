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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.barline.ChartView;
import com.htmitech.MyView.barline.OnBarLineClickListener;
import com.htmitech.MyView.barline.adapter.LegendGridViewAdapter;
import com.htmitech.MyView.barline.bean.NameColor;
import com.htmitech.MyView.barline.bean.Root;
import com.htmitech.MyView.barline.bean.YData;
import com.htmitech.MyView.barline.requestbean.BarLineOrder;
import com.htmitech.MyView.barline.requestbean.BarRequsetnBean;
import com.htmitech.api.BookInit;
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
import com.htmitech.proxy.doman.Conditions;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.JumpParameter;
import com.htmitech.proxy.doman.MobileconfigEvent;
import com.htmitech.proxy.doman.ReportSoso;
import com.htmitech.proxy.doman.ReportSosoResult;
import com.htmitech.proxy.doman.SosoResult;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.fragment.StatiscalReportFragment;
import com.htmitech.proxy.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;
import com.mx.google.gson.internal.LinkedHashTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by htrf-pc on 2017/6/13.
 */
public class BarLineReportActivity extends BaseFragmentActivity implements ObserverCallBackType, BackHandledInterface, View.OnTouchListener, CallBackDamageTypeGridListener, CallBackLayout, View.OnClickListener, OnBarLineClickListener {

    public ImageButton title_left_button;
    public TextView title_name;
    public TextView tv_child_title;
    public String userName;
    public LinearLayout ll_top_title;
    public LinearLayout ll_pic_view_layout;
    public String app_id;
    public String user_id;
    public String app_version_id;
    private ArrayList<Conditions> conditionses;
    private ArrayList<Conditions> conditionses1;
    private ArrayList<Conditions> conditionsesToShare;
    private final static String BAR_LINE_REPORT_VALUE = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.BARLINE_REPORT_VALUE;
    private final static String BAR_LINE_SOSO_VALUE = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.BAR_LINE_REPORT_SOSO;
    private final static String BAR_LINE_REQUEST_VALUE = "BODY";//搜索值
    private final static String BAR_LINE_REQUEST_SOSO = "SOSO";//搜索值

    private String com_report_bar_line_mobileconfig_define;
    private String com_report_bar_line_mobileconfig_include_share;
    private String com_report_bar_line_mobileconfig_actionbutton_style;
    private String com_report_bar_line_mobileconfig_include_security;
    private String com_report_barline_mobileconfig_font_controller = "";
    private String com_report_bar_line_mobileconfig_condition;
    private String com_report_barline_mobileconfig_event = ""; //事件定义
    private ArrayList mDataList;
    private ShareLink shareLink;
    private FunctionPopupWindow mFunctionPopupWindow;
    private LinearLayout ll_content_soso;
    private LinearLayout ll_bottom_style;
    private AddFloatingActionButton menuMultipleActions;
    private Animation upAnimation, downAnimation;
    private DrawableCenterTextView tvShare;
    private DrawableCenterTextView tv_select;
    private DrawableCenterTextView tv_detail;
    private DetailActivityLayout mDetailActivityLayout;
    private StatiscalReportFragment mDamageTypeFragment;
    private SosoResult mSosoResult = null;
    private ArrayList<SosoResult> sosoResults = null;
    private String field_name;
    private ChartView chartview;
    private RelativeLayout addView;
    private LinearLayout ll__top_lengend;
    private LinearLayout ll_bottom_lengend;
    private GridView gridView;
    private boolean isShare = false; //是否是分享过来的
    private String interface_id = "-1";
    private JSONArray conditionJSONArray;
    //    private String conditions = "-1";
    private String key_word;
    private String input_type;
    private String default_name;
    private String field_value = "";
    private String label_name;
    public String menuViewTag;
    public AppliationCenterDao mAppliationCenterDao;
    public AppInfo appInfo;
    private INetWorkManager netWorkManager;
    private MobileconfigEvent mobileconfigEvent;
    private MobileconfigEvent barLineMobileconfigEvent;
    private String appName;
    private Map<String, String> map;
    private ArrayList<String> labName;
    private TextView tv_buttom;
    private ArrayList<YData> yDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_line);
        initView();
        initData();
    }

    public void initView() {
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_name = (TextView) findViewById(R.id.title_name);
        tv_child_title = (TextView) findViewById(R.id.tv_child_title);
        ll_top_title = (LinearLayout) findViewById(R.id.ll_top_title);
        ll_pic_view_layout = (LinearLayout) findViewById(R.id.ll_pic_view_layout);
        ll_content_soso = (LinearLayout) findViewById(R.id.ll_content_soso);
        ll_bottom_style = (LinearLayout) findViewById(R.id.ll_bottom_style);
        menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
        tvShare = (DrawableCenterTextView) findViewById(R.id.tv_share);
        tv_select = (DrawableCenterTextView) findViewById(R.id.tv_select);
        tv_detail = (DrawableCenterTextView) findViewById(R.id.tv_detail);
        menuMultipleActions.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        menuMultipleActions.setOnTouchListener(this);
        menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
        mDetailActivityLayout = (DetailActivityLayout) findViewById(R.id.dal_layout);
        addView = (RelativeLayout) findViewById(R.id.rl_view_layout);
        ll__top_lengend = (LinearLayout) findViewById(R.id.ll_top_legend);
        ll_bottom_lengend = (LinearLayout) findViewById(R.id.ll_bottom_legend);
        gridView = (GridView) findViewById(R.id.gradview_bar_line_legend);
        tv_buttom = (TextView) findViewById(R.id.tv_buttom);
    }

    public void initData() {
        yDataList = new ArrayList<YData>();
        map = new LinkedHashTreeMap<String, String>();
        labName = new ArrayList<String>();
        mAppliationCenterDao = new AppliationCenterDao(this);
        appInfo = mAppliationCenterDao.getAppInfo("25102430182081055");
//        chartview = new ChartView(this);
//        chartview.setOnBarLineClickListener(this);
        mDetailActivityLayout.setValue(this);
        tv_buttom.setOnClickListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        app_id = intent.getStringExtra("app_id");
        appName = intent.getStringExtra("appName");
        mobileconfigEvent = (MobileconfigEvent) intent.getSerializableExtra("mobileconfigEvent");
        app_version_id = intent.getStringExtra("app_version_id");
//        conditions = intent.getStringExtra("conditions");
        interface_id = intent.getStringExtra("interface_id");
        com_report_bar_line_mobileconfig_define = intent.getStringExtra("com_report_barline_mobileconfig_define");
        com_report_bar_line_mobileconfig_include_share = intent.getStringExtra("com_report_barline_mobileconfig_include_share");
        com_report_bar_line_mobileconfig_actionbutton_style = intent.getStringExtra("com_report_barline_mobileconfig_actionbutton_style");
        com_report_bar_line_mobileconfig_include_security = intent.getStringExtra("com_report_barline_mobileconfig_include_security");
        com_report_bar_line_mobileconfig_condition = intent.getStringExtra("com_report_barline_mobileconfig_condition");
        com_report_barline_mobileconfig_font_controller = intent.getStringExtra("com_report_barline_mobileconfig_font_controller");
        com_report_barline_mobileconfig_event = intent.getStringExtra("com_report_barline_mobileconfig_event");

        if (!TextUtils.isEmpty(com_report_barline_mobileconfig_event)) {
            barLineMobileconfigEvent = JSON.parseObject(com_report_barline_mobileconfig_event, MobileconfigEvent.class);
        }
        isShare = intent.getBooleanExtra("isShare", false);
        title_name.setText(appName);
        conditionJSONArray = JSON.parseArray(com_report_bar_line_mobileconfig_condition);
        if (!TextUtils.isEmpty(com_report_bar_line_mobileconfig_actionbutton_style) && com_report_bar_line_mobileconfig_actionbutton_style.equals("1")) {
            ll_bottom_style.setVisibility(View.GONE);
            menuMultipleActions.setVisibility(View.VISIBLE);
        } else {
            ll_bottom_style.setVisibility(View.VISIBLE);
            menuMultipleActions.setVisibility(View.GONE);
        }

        if (isShare) {
            ll_bottom_style.setVisibility(View.GONE);
            menuMultipleActions.setVisibility(View.GONE);
            if (intent.getStringExtra("userID") != null && !"".equals(intent.getStringExtra("userID"))) {
                user_id = intent.getStringExtra("userID");
            } else {
                user_id = PreferenceUtils.getEMPUserID(this);
            }
        } else {
            user_id = BookInit.getInstance().getCrrentUserId();
        }

        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarLineReportActivity.this.finish();
            }
        });
//        if (!TextUtils.isEmpty(com_report_bar_line_mobileconfig_include_security) && com_report_bar_line_mobileconfig_include_security.equals("1")) {
//            chartview.setWaterMark(true);
//        } else {
//            chartview.setWaterMark(false);
//        }
        JSONObject conditionJSONObject = (JSONObject) conditionJSONArray.get(0);
        field_value = conditionJSONObject.getString("field_value");
        if (interface_id == null || interface_id.equals("-1")) {
            interface_id = conditionJSONObject.getString("interface_id");
        }
        label_name = conditionJSONObject.getString("label_name");
        key_word = conditionJSONObject.getString("key");
        field_name = conditionJSONObject.getString("field_name");
        input_type = conditionJSONObject.getString("input_type");

        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        showDialog();
//        try {
//            netWorkManager.logFunactionStart(this, this, "functionStartGetData", LogManagerEnum.BAR_LINE_GETDATA.functionCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        conditionses = new ArrayList<Conditions>();
        JSONObject jsonObject = JSON.parseObject(com_report_bar_line_mobileconfig_define);
        try {
            String title = jsonObject.getJSONObject("datasource_define").getString("title");
            String[] titles = title.split("\\|");
            for (String s : titles) {
                map.put(s, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (mobileconfigEvent != null) {
                    for (JumpParameter mJumpParameter : mobileconfigEvent.getJump_parameter()) {
                        if (field_name.equals(mJumpParameter.field_value_from)) {
                            field_value = mJumpParameter.getDefault_value();
                            default_name = field_value;
                        }
                    }
                }
                if (!TextUtils.isEmpty(map.get(field_name))) {
                    map.put(field_name, default_name);
                }
                mConditions.setField_name(field_name);
                mConditions.setField_value(field_value);
                conditionses.add(mConditions);
                if (hidden_flag.equals("1")) {
                    continue;
                }
                labName.add(label_name);
                AnsynHttpRequest.requestByPost(this, reportSoso(), BAR_LINE_SOSO_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_SOSO, LogManagerEnum.BAR_LINE_GETDICTS.getFunctionCode());
//                try {
//                    netWorkManager.logFunactionStart(this, this, "functionStartetdictsGByFieldId", LogManagerEnum.BAR_LINE_GETDICTS.functionCode);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }


        String name = "";
        for (String key : map.keySet()) {
            name += map.get(key);
        }
        tv_child_title.setText(name);

        if (isShare) {
            conditionses = (ArrayList<Conditions>) JSON.parseArray(com_report_bar_line_mobileconfig_condition, Conditions.class);
        }
        saveToShareCondition(conditionses);
        AnsynHttpRequest.requestByPost(this, reportValue(conditionses), BAR_LINE_REPORT_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_VALUE, LogManagerEnum.BAR_LINE_GETDATA.getFunctionCode());

        mDataList = new ArrayList<ActionInfo>();
        ActionInfo mActionInfoShare = new ActionInfo();
        mActionInfoShare.setActionID("Share");
        mActionInfoShare.setActionName("分享");
        if (!TextUtils.isEmpty(com_report_bar_line_mobileconfig_include_share) && com_report_bar_line_mobileconfig_include_share.equals("1")) {
            tvShare.setVisibility(View.VISIBLE);
            tvShare.setOnClickListener(this);
            mDataList.add(mActionInfoShare);
        } else {
            tvShare.setVisibility(View.GONE);
        }
        ActionInfo mActionInfoSelect = new ActionInfo();
        mActionInfoSelect.setActionID("Select");
        mActionInfoSelect.setActionName("筛选");
        mDataList.add(mActionInfoSelect);

        ActionInfo mActionInfoDetail = new ActionInfo();
        mActionInfoDetail.setActionID("Detail");
        mActionInfoDetail.setActionName("详细数据");
        mDataList.add(mActionInfoDetail);

        mFunctionPopupWindow = new FunctionPopupWindow(this, new MenuOnClickListener(), mDataList.size());
        mFunctionPopupWindow.initArcMenu(mDataList);
        sosoResults = new ArrayList<SosoResult>();
        mDamageTypeFragment = new StatiscalReportFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("input_type", "");
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


    }
//    public JSONObject getJSONObject(ArrayList<Table> arrayList){
//        for (Table mTable : arrayList) {
//            for (int i = 0; i < data.size(); i++) {
//                JSONObject jsonObject = data.getJSONObject(i);
//                Set<String> keySet = jsonObject.keySet();
//                for (String key : keySet) {
//                    if (mTable.key.equals(key) && mTable.value.equals(jsonObject.get(key).toString())) {
//                        return jsonObject;
//                    }
//                }
//            }
//        }
//
//        return null;
//    }

    @Override
    public void onBarLineClickListener(float value) {//点击柱状图，和折线点 的点击事件
        try {
            if (mobileconfigEvent == null) {
                return;
            }


//            if (barLineMobileconfigEvent.getJump_parameter().size() != 0) {
//                for (JumpParameter jumpParameter : barLineMobileconfigEvent.getJump_parameter()) {
////                    Set<String> keySet = jsonObject.keySet();
////                    for(String key : keySet){
////                        if (jumpParameter.getField_value_from().equals(key)) {
////                            if(jumpParameter.getField_value_type().equals("data")){
////                                jumpParameter.setDefault_value(jsonObject.get(key).toString());
////                            }else
////                        }
////                    }
//                    if(jumpParameter.getField_value_type().equals("condition")){
//                        for(Conditions mCon : conditionses1){
//                            if(mCon.getField_name().equals(""+jumpParameter.getField_value_from())){
//                                jumpParameter.setDefault_value(mCon.getField_value());
//                            }
//                        }
//                    }
//                }
//            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mobileconfigEvent", mobileconfigEvent);


//            ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(this);
//            mProxyDealApplication.applicationCenterProxy(appInfo);
            ClentAppUnit.getInstance(BarLineReportActivity.this).setActivity(appInfo, map);
        } catch (NotApplicationException e) {
            e.printStackTrace();
        }

    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            menuViewTag = v.getTag().toString();
            if (menuViewTag.equals("Share")) {
                shareListener();
            } else if (menuViewTag.equals("Select")) {
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(BarLineReportActivity.this,
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
                menuMultipleActions.setImageDrawable(getResources().getDrawable(R.drawable.btn_operation_homelabel_off));
                mFunctionPopupWindow.dismiss();
            } else if (menuViewTag.equals("Detail")) {
                startDetailActivity(yDataList);
            }
        }

    }


    public BarRequsetnBean reportValue(ArrayList<Conditions> conditionses) {
        BarRequsetnBean bean = new BarRequsetnBean();
        bean.app_id = app_id;
        bean.app_version_id = app_version_id;
        bean.user_id = user_id;
        bean.conditions = conditionses;
        BarLineOrder order = new BarLineOrder();
        order.field_name = "";
        order.order_type = 0;
        bean.order = order;
        return bean;
    }

    public ReportSoso reportSoso() {
        ReportSoso mReportSoso = new ReportSoso();
        mReportSoso.setApp_id(app_id);
        mReportSoso.setUser_id(user_id);
        mReportSoso.setInterface_id(interface_id);
        mReportSoso.setKey_word(key_word);
        return mReportSoso;
    }


    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

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

        tv_child_title.setText(name);
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
        saveToShareCondition(conditionses1);
        AnsynHttpRequest.requestByPost(this, reportValue(conditionses1), BAR_LINE_REPORT_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_VALUE, LogManagerEnum.BAR_LINE_GETDATA.getFunctionCode());
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
    public void callBackLayout() {
        if (action_move)
            menuMultipleActions.layout(left, top, right, bottom);
    }

    private int postion = 0;
    private JSONArray data;

    @Override
    public void success(String requestValue, int type, String requestName) {

        if (BAR_LINE_REQUEST_VALUE.equals(requestName)) {
            Root root = JSON.parseObject(requestValue, Root.class);
//            data = JSON.parseObject(requestValue).parseArray("listdata");
//            title_name.setText(root.Result.dataSource.title);
//            if (root.Result.dataSource.unit != null) {
//                tv_child_title.setText("(单位：" + root.Result.dataSource.unit + ")");
//            }
            if (root.Result.title.show) {
                ll_top_title.setVisibility(View.VISIBLE);
            } else {
                ll_top_title.setVisibility(View.GONE);
            }
            if (root.Result.getLegend().show) {
                List<String> name = new ArrayList<String>();
                for (int i = 0; i < root.Result.dataSource.yData.size(); i++) {
                    name.add(root.Result.dataSource.yData.get(i).belong.name);
                }
                List<NameColor> nameColors = new ArrayList<NameColor>();
                for (int j = 0; j < name.size(); j++) {
                    boolean flag = false;
                    for (int bar = 0; bar < root.Result.barStyle.size(); bar++) {
                        if (root.Result.barStyle.get(bar).name.equals(name.get(j))) {
                            NameColor nameColor = new NameColor();
                            nameColor.name = name.get(j);
                            nameColor.color = root.Result.barStyle.get(bar).color;
                            nameColors.add(nameColor);
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        continue;
                    for (int line = 0; line < root.Result.lineStyle.size(); line++) {
                        if (root.Result.lineStyle.get(line).name.equals(name.get(j))) {
                            NameColor nameColor = new NameColor();
                            nameColor.name = name.get(j);
                            nameColor.color = root.Result.lineStyle.get(line).color;
                            nameColors.add(nameColor);
                            break;
                        }
                    }
                }
                ll_bottom_lengend.setVisibility(View.VISIBLE);
                LegendGridViewAdapter adapter = new LegendGridViewAdapter(BarLineReportActivity.this, nameColors);
                gridView.setAdapter(adapter);
            } else {
                ll__top_lengend.setVisibility(View.GONE);
                ll_bottom_lengend.setVisibility(View.GONE);
            }
//            netWorkManager.logFunactionFinsh(this, this, "fouctionSuccess", LogManagerEnum.BAR_LINE_GETDATA.functionCode, root.Message.StatusMessage, INetWorkManager.State.SUCCESS);
            //**************************
            chartview = new ChartView(this);
            chartview.setOnBarLineClickListener(this);
            if (!TextUtils.isEmpty(com_report_bar_line_mobileconfig_include_security) && com_report_bar_line_mobileconfig_include_security.equals("1")) {
                chartview.setWaterMark(true, BookInit.getInstance().getCurrentUserName());
            } else {
                chartview.setWaterMark(false, BookInit.getInstance().getCurrentUserName());
            }
            //**************************
            yDataList.clear();
            yDataList.addAll(root.Result.dataSource.yData);
            chartview.setRoot(root);
//            tv_child_title.setText(root.Result.dataSource.title);
            dismissDialog();
            if (addView.getChildCount() != 0) {
                addView.removeAllViews();
            }
            addView.addView(chartview);
        } else if (requestName.equals(BAR_LINE_REQUEST_SOSO)) {
            mSosoResult = JSON.parseObject(requestValue, SosoResult.class);
            mSosoResult.setTitle(labName.get(postion++));
            sosoResults.add(mSosoResult);
            mDamageTypeFragment.refresh(sosoResults);
//            netWorkManager.logFunactionFinsh(this, this, "fouctionSuccess", LogManagerEnum.BAR_LINE_GETDICTS.functionCode, mSosoResult.Message.mStatusMessage, INetWorkManager.State.SUCCESS);
        } else if ("functionStartGetData".equals(requestName)) {
            AnsynHttpRequest.requestByPost(this, reportValue(conditionses), BAR_LINE_REPORT_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_VALUE, LogManagerEnum.BAR_LINE_GETDATA.getFunctionCode());
        } else if ("functionStartetdictsGByFieldId".equals(requestName)) {
            AnsynHttpRequest.requestByPost(this, reportSoso(), BAR_LINE_SOSO_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_SOSO, LogManagerEnum.BAR_LINE_GETDICTS.getFunctionCode());
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if ("functionStartGetData".equals(requestName)) {
            AnsynHttpRequest.requestByPost(this, reportValue(conditionses), BAR_LINE_REPORT_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_VALUE, LogManagerEnum.BAR_LINE_GETDATA.getFunctionCode());
        } else if ("functionStartetdictsGByFieldId".equals(requestName)) {
            AnsynHttpRequest.requestByPost(this, reportSoso(), BAR_LINE_SOSO_VALUE, CHTTP.POST_LOG, this, BAR_LINE_REQUEST_SOSO, LogManagerEnum.BAR_LINE_GETDICTS.getFunctionCode());
        } else if (requestName.equals(BAR_LINE_REQUEST_SOSO)) {
            netWorkManager.logFunactionFinsh(this, this, "fouctionFail", LogManagerEnum.BAR_LINE_GETDICTS.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
        } else if (BAR_LINE_REQUEST_VALUE.equals(requestName)) {
            netWorkManager.logFunactionFinsh(this, this, "fouctionFail", LogManagerEnum.BAR_LINE_GETDATA.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

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
            case R.id.tv_share:
                shareListener();
                break;
            case R.id.tv_select:
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(BarLineReportActivity.this,
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
                break;

            case R.id.tv_buttom:

                complete();
                break;
            case R.id.tv_detail:
                startDetailActivity(yDataList);
                break;
        }
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

    int curItem = 0;
    public String apiUrl = null;

    private void shareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享柱线图");
//        shareLink.setDesc(docTitle);
//        shareLink.setThumbnail(iconId);
        shareLink.setUrl(BAR_LINE_REPORT_VALUE);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                BarLineReportActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            private String jsonString;

            @Override
            public void onClick(DialogInterface dialog, int which) {
                jsonString = JSON.toJSONString(conditionsesToShare);
                if (curItem == 0) {
                    apiUrl = "ZX" + app_id + "|" + user_id + "|" + app_version_id + "|" + jsonString + "|" + "order" + "|" + PreferenceUtils.getEMPUserName(BarLineReportActivity.this);
                    shareLink.setAppUrl(apiUrl);

                    MXAPI.getInstance(BarLineReportActivity.this).shareToChat(BarLineReportActivity.this, shareLink);
                } else {
                    apiUrl = "ZX" + app_id + "|" + user_id + "|" + app_version_id + "|" + jsonString + "|" + "order" + "|" + PreferenceUtils.getEMPUserName(BarLineReportActivity.this);
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享柱线图");
                    MXAPI.getInstance(BarLineReportActivity.this).shareToCircle(BarLineReportActivity.this, shareLink);
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

    private void saveToShareCondition(ArrayList<Conditions> conditionses) {
        if (conditionsesToShare == null)
            conditionsesToShare = new ArrayList<Conditions>();
        conditionsesToShare.clear();
        conditionsesToShare.addAll(conditionses);
    }

    private void startDetailActivity(ArrayList<YData> yDatas) {
        Intent intent = new Intent(this, ReportDetailActivity.class);
        intent.putExtra("datas", JSON.toJSONString(yDatas));
        intent.putExtra("from", "BarLine");
        intent.putExtra("titleName", appName);
        startActivity(intent);
    }


}
