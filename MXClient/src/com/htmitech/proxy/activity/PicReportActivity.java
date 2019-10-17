package com.htmitech.proxy.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.RippleLayout;
import com.htmitech.api.BookInit;
import com.htmitech.app.widget.DrawableCenterTextView;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.ActionInfo;
import com.htmitech.emportal.ui.detail.CallBackLayout;
import com.htmitech.emportal.ui.detail.DetailActivityLayout;
import com.htmitech.emportal.ui.pop.FunctionPopupWindow;
import com.htmitech.emportal.ui.widget.flatingactionButton.AddFloatingActionButton;
import com.htmitech.emportal.ui.widget.flatingactionButton.FloatingActionButton;
import com.htmitech.listener.BackHandledInterface;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.adapter.PicReportButtomAdapter;
import com.htmitech.proxy.doman.Conditions;
import com.htmitech.proxy.doman.Datas;
import com.htmitech.proxy.doman.JumpParameter;
import com.htmitech.proxy.doman.MobileconfigEvent;
import com.htmitech.proxy.doman.Order;
import com.htmitech.proxy.doman.PicData;
import com.htmitech.proxy.doman.PicTitleMessage;
import com.htmitech.proxy.doman.ReportSoso;
import com.htmitech.proxy.doman.ReportSosoResult;
import com.htmitech.proxy.doman.ResultPic;
import com.htmitech.proxy.doman.SosoResult;
import com.htmitech.proxy.fragment.StatiscalReportFragment;
import com.htmitech.proxy.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.unit.ImageUtil;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.ShareLink;
import com.mx.google.gson.internal.LinkedHashTreeMap;

import java.util.ArrayList;
import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import mobilereport.com.chatkit.domain.DetectionValue;
import mobilereport.com.chatkit.myView.PicChartView;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;

/**
 * ------------------------------------------------------------
 * 饼状图
 * ——————————————————————————————
 */
public class PicReportActivity extends BaseFragmentActivity implements ObserverCallBackType, BackHandledInterface, View.OnTouchListener, CallBackDamageTypeGridListener, CallBackLayout, View.OnClickListener {
    public ImageButton title_left_button;
    public TextView title_name;
    public TextView tv_child_title;
    public String userName;
    public LinearLayout ll_top_title;
    public LinearLayout ll_pic_view_layout;
    public String app_id;
    public String user_id;
    public String app_version_id;
    public PicChartView mPicChartView;
    private final static String PIC_REPORT_VALUE = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.PIC_REPORT_VALUE;
    private final static String PIC_REQUEST_SOSO_URL = ServerUrlConstant.SERVER_BASE_URL() + ServerUrlConstant.PIC_REPORT_SOSO;
    private final static String PIC_REQUEST_VALUE = "BODY";//搜索值
    private final static String PIC_REQUEST_SOSO = "SOSO";//搜索值

    private String com_report_pie_mobileconfig_define;
    private String com_report_pie_mobileconfig_include_share;
    private String com_report_pie_mobileconfig_actionbutton_style;
    private String com_report_pie_mobileconfig_include_security;
    private String com_report_pie_mobileconfig_condition;
    private String com_report_pie_mobileconfig_font_controller;
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
    private JSONArray conditionJSONArray;
    private boolean isShare = false; //是否是分享过来的
    private GridView gradview;
    private PicReportButtomAdapter mPicReportButtomAdapter;
    private UserMessageScrollView scroll_view;
    private TextView tv_time;
    private RippleLayout rl_layout;
    private String field_value = "";
    private String interface_id = "-1";
    private String label_name;
    private String key_word;
    private String input_type;
    private String default_name;
    private ArrayList<Conditions> conditionses;
    private ArrayList<Conditions> conditionsesToShare;
    private String conditions = "-1";
    private TextView iv_no_add;
    String one = "";
    String two = "";
    String three = "";
    String four = "";
    private ArrayList<String> labName;
    private TextView tv_buttom;
    private JSONObject conditionJSONObject;
    private Map<String, String> map;
    private String appName;
    private MobileconfigEvent mobileconfigEvent;
    private ArrayList<Datas> DatasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_report);
        initView();
        initData();
    }

    public void initView() {
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
        title_name = (TextView) findViewById(R.id.title_name);
        tv_child_title = (TextView) findViewById(R.id.tv_child_title);
        rl_layout = (RippleLayout) findViewById(R.id.rl_layout);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_no_add = (TextView) findViewById(R.id.iv_no_add);
        tv_buttom = (TextView) findViewById(R.id.tv_buttom);
        ll_top_title = (LinearLayout) findViewById(R.id.ll_top_title);
        ll_pic_view_layout = (LinearLayout) findViewById(R.id.ll_pic_view_layout);
        mPicChartView = (PicChartView) findViewById(R.id.fanChartView);
        ll_content_soso = (LinearLayout) findViewById(R.id.ll_content_soso);
        ll_bottom_style = (LinearLayout) findViewById(R.id.ll_bottom_style);
        menuMultipleActions = (AddFloatingActionButton) findViewById(R.id.function);
        tvShare = (DrawableCenterTextView) findViewById(R.id.tv_share);
        tv_select = (DrawableCenterTextView) findViewById(R.id.tv_select);
        tv_detail = (DrawableCenterTextView) findViewById(R.id.tv_detail);
        scroll_view = (UserMessageScrollView) findViewById(R.id.scroll_view);
        gradview = (GridView) findViewById(R.id.gradview);
        menuMultipleActions.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        tv_buttom.setOnClickListener(this);
        menuMultipleActions.setOnTouchListener(this);
//        tv_time.setOnClickListener(this);
        menuMultipleActions.setSize(FloatingActionButton.SIZE_NORMAL);
        mDetailActivityLayout = (DetailActivityLayout) findViewById(R.id.dal_layout);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initData() {

        DatasList = new ArrayList<Datas>();
        map = new LinkedHashTreeMap<String, String>();
        labName = new ArrayList<String>();
        mDetailActivityLayout.setValue(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
        Intent intent = getIntent();
        mobileconfigEvent = (MobileconfigEvent) intent.getSerializableExtra("mobileconfigEvent");
        userName = intent.getStringExtra("userName");
        appName = intent.getStringExtra("appName");
        app_id = intent.getStringExtra("app_id");
        app_version_id = intent.getStringExtra("app_version_id");
        com_report_pie_mobileconfig_define = intent.getStringExtra("com_report_pie_mobileconfig_define");
        com_report_pie_mobileconfig_include_share = intent.getStringExtra("com_report_pie_mobileconfig_include_share");
        com_report_pie_mobileconfig_actionbutton_style = intent.getStringExtra("com_report_pie_mobileconfig_actionbutton_style");
        com_report_pie_mobileconfig_include_security = intent.getStringExtra("com_report_pie_mobileconfig_include_security");
        com_report_pie_mobileconfig_condition = intent.getStringExtra("com_report_pie_mobileconfig_condition");
        com_report_pie_mobileconfig_font_controller = intent.getStringExtra("com_report_pie_mobileconfig_font_controller");
        isShare = intent.getBooleanExtra("isShare", false);
        if (!TextUtils.isEmpty(com_report_pie_mobileconfig_actionbutton_style) && com_report_pie_mobileconfig_actionbutton_style.equals("1")) {
            ll_bottom_style.setVisibility(View.GONE);
            menuMultipleActions.setVisibility(View.VISIBLE);
        } else {
            ll_bottom_style.setVisibility(View.VISIBLE);
            menuMultipleActions.setVisibility(View.GONE);
        }
        if (isShare) {
            ll_bottom_style.setVisibility(View.GONE);
            menuMultipleActions.setVisibility(View.GONE);
            user_id = intent.getStringExtra("userID");
        } else {
            user_id = BookInit.getInstance().getCrrentUserId();
        }
        title_name.setText(appName + "");
        PicTitleMessage mPicTitleMessage = JSON.parseObject(com_report_pie_mobileconfig_define, PicTitleMessage.class);
        mPicChartView.setClockWise(mPicTitleMessage.clockWise);
        mPicChartView.setSmillCircleBackColor(mPicTitleMessage.backgroundColor);
        mPicChartView.setFull(!mPicTitleMessage.full);
        mPicChartView.setShowText(true);
        mPicChartView.setBackgroundColor(Color.parseColor(mPicTitleMessage.backgroundColor));
        ll_top_title.setBackgroundColor(Color.parseColor(mPicTitleMessage.backgroundColor));
        scroll_view.setBackgroundColor(Color.parseColor(mPicTitleMessage.backgroundColor));
        ll_pic_view_layout.setBackground(ImageUtil.drawTextToBitmap(this, BookInit.getInstance().getCurrentUserName()));


        if (!TextUtils.isEmpty(com_report_pie_mobileconfig_include_security) && com_report_pie_mobileconfig_include_security.equals("1")) {
            mPicChartView.setWaterMark(false);
            mPicChartView.getBackground().setAlpha((int) (0.5 * 255));
            ll_top_title.getBackground().setAlpha((int) (0.5 * 255));
            scroll_view.getBackground().setAlpha((int) (0.5 * 255));
        } else {
            mPicChartView.setWaterMark(false);
        }
        if (!TextUtils.isEmpty(com_report_pie_mobileconfig_font_controller) && com_report_pie_mobileconfig_font_controller.equals("1")) {
            mPicChartView.setSP(true);
        }
        showDialog();
        conditionJSONArray = JSON.parseArray(com_report_pie_mobileconfig_condition);
        conditionJSONObject = (JSONObject) conditionJSONArray.get(0);
        field_value = conditionJSONObject.getString("default_value");
        if (interface_id == null || interface_id.equals("-1")) {
            interface_id = conditionJSONObject.getString("interface_id");
        }
        label_name = conditionJSONObject.getString("label_name");
        key_word = conditionJSONObject.getString("key");
        field_name = conditionJSONObject.getString("field_name");
        input_type = conditionJSONObject.getString("input_type");
        conditionses = new ArrayList<Conditions>();

        String title = mPicTitleMessage.title.getText();
        String[] titles = title.split("\\|");
        for (String s : titles) {
            map.put(s, s);
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

                Conditions mConditions = new Conditions();
                mConditions.setField_name(field_name);
                mConditions.setField_value(field_value);
                conditionses.add(mConditions);
                if (hidden_flag.equals("1")) {
                    continue;
                }
                labName.add(label_name);
                AnsynHttpRequest.requestByPost(PicReportActivity.this, reportSoso(), PIC_REQUEST_SOSO_URL, CHTTP.POST_LOG, PicReportActivity.this, PIC_REQUEST_SOSO + "", "");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        if (isShare) {
            conditionses = (ArrayList<Conditions>) JSON.parseArray(com_report_pie_mobileconfig_condition, Conditions.class);
        }

        String name = "";
        for (String key : map.keySet()) {
            name += map.get(key);
        }

        tv_time.setText(name);
        saveToShareCondition(conditionses);
        AnsynHttpRequest.requestByPost(this, reportValue(conditionses), PIC_REPORT_VALUE, CHTTP.POST_LOG, this, PIC_REQUEST_VALUE + "", "");
        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicReportActivity.this.finish();
            }
        });
        mDataList = new ArrayList<ActionInfo>();
        ActionInfo mActionInfoShare = new ActionInfo();
        mActionInfoShare.setActionID("Share");
        mActionInfoShare.setActionName("分享");
        if (!TextUtils.isEmpty(com_report_pie_mobileconfig_include_share) && com_report_pie_mobileconfig_include_share.equals("1")) {
            tvShare.setVisibility(View.VISIBLE);
            mDataList.add(mActionInfoShare);

        } else {
            tvShare.setVisibility(View.GONE);
            ll_bottom_style.setVisibility(View.GONE);
            menuMultipleActions.setVisibility(View.GONE);
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


//        rl_layout.setOnRippleCompleteListener(new RippleLayout.OnRippleCompleteListener() {
//            @Override
//            public void onComplete(int id) {
//                if (mPopBirthHelper != null) {
//                    mPopBirthHelper.dismiss();
//                }
//                mPopBirthHelper = new PopChooseTimeHelper(PicReportActivity.this);
//
//
//                mPopBirthHelper.setTimeEnums(TimeEnum.YEAR, TimeEnum.MONTH, TimeEnum.DAY);
//
//                mPopBirthHelper.setOnClickOkListener(new PopChooseTimeHelper.OnClickOkListener() {
//
//                    @Override
//                    public void onClickOk(String birthday) {
//                        // TODO Auto-generated method stub
//
//
//                    }
//                });
//
//                mPopBirthHelper.show(rl_layout);
//            }
//        });
    }

    public ReportSoso reportSoso() {
        ReportSoso mReportSoso = new ReportSoso();
        mReportSoso.setApp_id(app_id);
        mReportSoso.setUser_id(user_id);
//        if(mList!=null&&mList.get(0)!=null)
//            mReportSoso.setInterface_id(mList.get(0).field_name);
//        else
        mReportSoso.setInterface_id(interface_id);
        mReportSoso.setKey_word(key_word);
        return mReportSoso;
    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            menuViewTag = v.getTag().toString();
            if (menuViewTag.equals("Share")) {
                ShareListener();
            } else if (menuViewTag.equals("Select")) {
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(PicReportActivity.this,
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
            } else if (menuViewTag.equals("Detail")) {
                startDetailActivity(DatasList);
            }
        }

    }

    public PicData reportValue(ArrayList<Conditions> conditionses) {

        PicData mPicData = new PicData();
        mPicData.setUser_id(user_id);
        mPicData.setApp_id(app_id);
        mPicData.setApp_version_id(app_version_id);
        mPicData.setConditions(conditionses);
        mOrder = new Order();
        mOrder.setField_name(conditionses.get(0).field_name);
        mOrder.setOrder_type("0");
        mPicData.setOrder(mOrder);
        return mPicData;
    }

    public int postion = 0;

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals(PIC_REQUEST_SOSO)) {
            try {
                mSosoResult = JSON.parseObject(requestValue, SosoResult.class);
                mSosoResult.setTitle(labName.get(postion++));
                sosoResults.add(mSosoResult);
                mDamageTypeFragment.refresh(sosoResults);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ResultPic mResultPic = JSON.parseObject(requestValue, ResultPic.class);
            if (mResultPic.Result == null) {
                return;
            }
            try {
                String textColor = mResultPic.Result.center_title.getTextColor();
                if (TextUtils.isEmpty(textColor)) {
                    textColor = "#000000";
                }
                ArrayList<DetectionValue> mDetectionValueList = new ArrayList<DetectionValue>();
                int postion = 0;
                for (Datas mDatas : mResultPic.Result.pieData.data) {
                    String color = "";
                    if (TextUtils.isEmpty(mDatas.color)) {
                        color = mResultPic.Result.pieData.colors.get(postion++);
                    } else {
                        color = mDatas.color;
                    }
                    mDetectionValueList.add(new DetectionValue(color, Double.parseDouble(mDatas.value), mDatas.name, "0"));
                }
                DatasList.clear();
                DatasList.addAll(mResultPic.Result.pieData.data);
                mPicChartView.initPicChart(mDetectionValueList);

                mPicChartView.centerTitle(mResultPic.Result.getCenter_title().getText(), mResultPic.Result.getCenter_title().getFontSize(), textColor, mResultPic.Result.getCenter_title().isShow());
                ll_top_title.setVisibility(mResultPic.Result.getTitle().isShow() ? View.VISIBLE : View.GONE);

                tv_child_title.setText(mResultPic.Result.getTitle().getText());
                tv_child_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, mResultPic.Result.getTitle().getFontSize());
                tv_child_title.setTextColor(Color.parseColor(mResultPic.Result.getTitle().getTextColor()));
                gradview.setAdapter(new PicReportButtomAdapter(PicReportActivity.this, mDetectionValueList, mPicChartView.getTotal()));

//                tv_time.setText(so_name + danwei);
                dismissDialog();
            } catch (Exception e) {
                iv_no_add.setVisibility(View.VISIBLE);
                mPicChartView.setVisibility(View.GONE);
                scroll_view.setVisibility(View.GONE);
                dismissDialog();
            }

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

    public String menuViewTag;
    public String tempOne = "";
    public String tempThree = "";

    @Override
    public void callBack(ArrayList<ReportSosoResult> list) {
        showDialog();
        conditionses1 = new ArrayList<Conditions>();

        if (list.size() > 0) {

            for (ReportSosoResult mReportSosoResult : list) {
                int index = 0;
                int hidden = 0;
                for (int i = 0; i < conditionJSONArray.size(); i++) {
                    if (hidden <= mReportSosoResult.getPostion()) {
                        conditionJSONObject = (JSONObject) conditionJSONArray.get(i);
                        String hidden_flag = conditionJSONObject.getString("hidden_flag");
                        if (hidden_flag.equals("1")) {
                            index++;
                        } else {
                            hidden++;
                        }
                    }

                }
                JSONObject conditionJSONObject = (JSONObject) conditionJSONArray.get(mReportSosoResult.getPostion() + index);
                default_name = conditionJSONObject.getString("default_name");
                String field_name = conditionJSONObject.getString("field_name");
                Conditions mConditions = new Conditions();
                mConditions.setField_name(field_name);
                mConditions.setField_value(mReportSosoResult.getValue());
                conditionses1.add(mConditions);

                if (!TextUtils.isEmpty(map.get(field_name))) {
                    map.put(field_name, mReportSosoResult.getName());
                }
            }
        }
        String name = "";
        for (String key : map.keySet()) {
            name += map.get(key);
        }

        tv_time.setText(name);

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
        AnsynHttpRequest.requestByPost(this, reportValue(conditionses1), PIC_REPORT_VALUE, CHTTP.POST_LOG, this, PIC_REQUEST_VALUE + "", "");

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

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }

    //分享
    //分享
    public int curItem = 0;
    public String apiUrl = null;
    private ArrayList<Conditions> conditionses1;
    private Order mOrder;


    private void ShareListener() {
        // 弹选择窗
        //设置分享参数
        shareLink = new ShareLink();
        shareLink.setTitle("分享饼图");
//        shareLink.setDesc(docTitle);
//        shareLink.setThumbnail(iconId);
        shareLink.setUrl(PIC_REPORT_VALUE);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                PicReportActivity.this);
        builder.setTitle("请选择分享位置");
        final String[] pos = {"分享给同事", "分享到工作组"};
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            private String jsonString;

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                jsonString = JSONArray.toJSONString(conditionses1);
                jsonString = JSON.toJSONString(conditionsesToShare);
                if (curItem == 0) {
                    apiUrl = "BT" + app_id + "|" + user_id + "|" + app_version_id + "|" + "com_report_pie_mobileconfig_define" + "|" + com_report_pie_mobileconfig_include_security + "|" + jsonString + "|" + PreferenceUtils.getEMPUserName(PicReportActivity.this);
                    shareLink.setAppUrl(apiUrl);

                    MXAPI.getInstance(PicReportActivity.this).shareToChat(PicReportActivity.this, shareLink);
                } else {
                    apiUrl = "BT" + app_id + "|" + user_id + "|" + app_version_id + "|" + "com_report_pie_mobileconfig_define" + "|" + com_report_pie_mobileconfig_include_security + "|" + jsonString + "|" + PreferenceUtils.getEMPUserName(PicReportActivity.this);
                    shareLink.setAppUrl(apiUrl);
                    shareLink.setTitle("分享饼图");
                    MXAPI.getInstance(PicReportActivity.this).shareToCircle(PicReportActivity.this, shareLink);
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
            case R.id.tv_select:
                ll_content_soso.clearAnimation();
                downAnimation = AnimationUtils.loadAnimation(PicReportActivity.this,
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
            case R.id.tv_time:

                break;
            case R.id.tv_detail:
                startDetailActivity(DatasList);
                break;
        }

    }

    private PopChooseTimeHelper mPopBirthHelper;

    private void saveToShareCondition(ArrayList<Conditions> conditionses) {
        if (conditionsesToShare == null)
            conditionsesToShare = new ArrayList<Conditions>();
        conditionsesToShare.clear();
        conditionsesToShare.addAll(conditionses);
    }

    private void startDetailActivity(ArrayList<Datas> datas) {
        Intent intent = new Intent(this, ReportDetailActivity.class);
        intent.putExtra("datas", JSON.toJSONString(datas));
        intent.putExtra("from", "Pie");
        intent.putExtra("titleName", appName);
        startActivity(intent);
    }
}
