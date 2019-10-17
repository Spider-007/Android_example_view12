package activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.schedulemanagerlibrary.R;
import com.htmitech.api.BookInit;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.OAConText;
import com.minxing.pulltorefresh.library.PullToRefreshBase;
import com.minxing.pulltorefresh.library.PullToRefreshListView;

import org.joda.time.LocalDate;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

import Interface.CallBackLayout;
import Interface.INetWorkManager;
import adapter.ScheduleListAdapter;
import calendar.CollapseCalendarView;
import calendar.manager.CalendarManager;
import calendar.manager.Week;
import cn.feng.skin.manager.base.BaseFragmentActivity;
import htmitech.com.componentlibrary.entity.ActionInfo;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import proxy.LogManagerProxy;
import proxy.NetWorkManager;
import schedulebean.ScheduleListRequest;
import schedulebean.ScheduleListResultBean;
import schedulebean.countInfoBean;
import utils.DeviceUtils;
import utils.ScheduleUtils;
import widget.AddFloatingActionButton;
import widget.DetailActivityLayout;
import widget.EmptyLayout;
import widget.FloatingActionButton;
import widget.FunctionPopupWindow;

/*
* 日程管理主页面
* */
public class ScheduleMainActivity extends BaseFragmentActivity implements View.OnClickListener,
        CallBackLayout, View.OnTouchListener, ObserverCallBackType {
    //成员变量
    private CalendarManager mManager;     //日历控件管理类
    private ScheduleListAdapter scheduleListAdapter;
    private DisplayMetrics dm;
    private int screenWidth;             //屏幕宽度
    private DetailActivityLayout mDetailActivityLayout;//功能按钮拖动效果根布局
    private FunctionPopupWindow mFunctionPopupWindow;
    private String actionbuttonStyle;          //功能按钮展示方式（圆圈还是底部条目）
    private String createviewConfig;
    private String detailviewConfig;
    private String includeSecurity;            //是否需要水印
    private String app_id;
    private ArrayList<ActionInfo> mDataList;
    public ArrayList<countInfoBean> mScheduleList = new ArrayList(); //日程红点数据集
    public boolean isHome;   //是否在底部配置
    LinearLayout llCalendarPopup;         //日历控件根布局
    PullToRefreshListView mPullToRefreshListView;  //日程条目列表控件
    EmptyLayout emptyLayoutRoot;          //空页面根布局控件
    CollapseCalendarView calendarView;        //日历控件
    ImageButton titleLeftButton;             //左侧返回按钮
    ImageButton titleLeftButtonHome;        //左侧返回按钮（当前页面为Home页面的时候）
    LinearLayout llScheduleRight;
    Button btScheduleTodat;                  //日历控件跳转到今天操作按钮
    Button btScheduleSearch;                 //日程搜索操作按钮
    TextView scheduleTitleNameBack;           //当左侧是返回按钮时候的title
    TextView scheduleTitleNameHome;            //当左侧是主页标识时候的title
    AddFloatingActionButton function;          //功能操作按钮
    private String dropdownOptionManual;
    private ScheduleListResultBean scheduleListResultBean;
    private ScheduleListRequest scheduleListRequest;
    private String getListUrl;
    private INetWorkManager netWorkManager;
    private String selectDate;
    private String schAdviceStatus = "1";
    private TextView tv_mine;
    private TextView tv_confirm;
    private TextView tv_img_mine;
    private TextView tv_img_confirm;
    private String app_version_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailActivityLayout = (DetailActivityLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_schedule_main, null);
        setContentView(mDetailActivityLayout);
         llCalendarPopup = (LinearLayout)findViewById(R.id.ll_calendar_popup);
         mPullToRefreshListView = (PullToRefreshListView)findViewById(R.id.lv_schedule_list);
         emptyLayoutRoot = (EmptyLayout)findViewById(R.id.empty_layout_root);
         calendarView = (CollapseCalendarView)findViewById(R.id.calendar);
         titleLeftButton = (ImageButton)findViewById(R.id.title_left_button);
         titleLeftButtonHome = (ImageButton)findViewById(R.id.title_left_button_home);
         llScheduleRight = (LinearLayout)findViewById(R.id.ll_schedule_right);
         btScheduleTodat = (Button)findViewById(R.id.bt_schedule_todat);
         btScheduleSearch = (Button)findViewById(R.id.bt_schedule_search);
         scheduleTitleNameBack = (TextView)findViewById(R.id.schedule_title_name_back);
         scheduleTitleNameHome = (TextView)findViewById(R.id.schedule_title_name_home);
         function = (AddFloatingActionButton)findViewById(R.id.function);

        titleLeftButton.setVisibility(View.VISIBLE);
        titleLeftButtonHome.setVisibility(View.VISIBLE);
        llScheduleRight.setVisibility(View.VISIBLE);
        btScheduleTodat.setVisibility(View.VISIBLE);
        btScheduleSearch.setVisibility(View.VISIBLE);
        scheduleTitleNameBack.setVisibility(View.VISIBLE);
//        scheduleTitleNameHome.setVisibility(View.VISIBLE);
        function.setVisibility(View.VISIBLE);

        tv_mine = (TextView) findViewById(R.id.tv_mine);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        tv_img_mine = (TextView) findViewById(R.id.tv_img_mine);
        tv_img_confirm = (TextView) findViewById(R.id.tv_img_confirm);
        selectDate = LocalDate.now().toString();
        dm = getResources().getDisplayMetrics();
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        initConfig();              //获取配置
        mDetailActivityLayout.setValue(this);
        initCalendar();                              //初始化日历控件管理器
        llScheduleRight.setVisibility(View.VISIBLE);  //右面的操作按钮
        scheduleTitleNameBack.setText(mManager.getDateAndWeek());
        scheduleTitleNameHome.setText(mManager.getHeaderText());
        titleLeftButton.setOnClickListener(this);
        titleLeftButtonHome.setOnClickListener(this);
        btScheduleTodat.setOnClickListener(this);
        btScheduleSearch.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        function.setOnTouchListener(this);
        function.setOnClickListener(this);
        function.setSize(FloatingActionButton.SIZE_NORMAL);
//        function.setImageDrawable(getResources().getDrawable(R.drawable.btn_date_operation_add));
        mDataList = new ArrayList<ActionInfo>();
//        ActionInfo actionInfo = new ActionInfo();
//        actionInfo.setActionName("创建");
//        actionInfo.setActionID("share");
//        mDataList.add(actionInfo);
        mFunctionPopupWindow = new FunctionPopupWindow(this,
                new MenuOnClickListener(), mDataList.size());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer(Week.getFristVisibleDat().substring(0, 7) + "-01", Week.getLastVisibleDat().substring(0, 7) + "-31", "getMonthList");
        netWorkManager.logFunactionStart(this, ScheduleMainActivity.this, "getScheduleListData", LogManagerEnum.SCHEDULELIST.getFunctionCode());
//        getDataFromServer(LocalDate.now().toString(),LocalDate.now().toString(),"getList");
//        getDataFromServer(Week.getFristVisibleDat(),Week.getLastVisibleDat(),"getMonthList");
    }

    /*
        * 获取配置
        * */
    private void initConfig() {
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        Intent intent = getIntent();
        //日程管理详情页中的操作按钮样式
        actionbuttonStyle = intent.getStringExtra("com_schedule_mobileconfig_actionbutton_style");
        //日程创建页面配置
        createviewConfig = intent.getStringExtra("com_schedule_mobileconfig_createview_config");
        //日常的详情页面配置
        detailviewConfig = intent.getStringExtra("com_schedule_mobileconfig_detailview_config");
        //日程管理详情页是不是支持水印
        includeSecurity = intent.getStringExtra("com_schedule_mobileconfig_include_security");
        dropdownOptionManual = intent.getStringExtra("com_schedule_mobileconfig_dropdown_option_manual");
        app_version_id = intent.getStringExtra("app_version_id");
        app_id = intent.getStringExtra("app_id");//appId
        isHome = intent.getBooleanExtra("isHome", false);
        if (isHome) {
            titleLeftButton.setVisibility(View.GONE);
            titleLeftButtonHome.setVisibility(View.VISIBLE);
        } else {
            titleLeftButton.setVisibility(View.VISIBLE);
            titleLeftButtonHome.setVisibility(View.GONE);
        }
    }

    /*
    * 初始化日历控件管理器
    * */
    private void initCalendar() {
        mManager = new CalendarManager(LocalDate.now(),
                CalendarManager.State.MONTH, LocalDate.now().withYear(100),
                LocalDate.now().plusYears(100));

        calendarView.init(mManager);
        calendarView.setTitle("");       //去除日程控件的头标题
//        Day firstVisibleDay = calendarView.getFirstVisibleDay();
        calendarView.setDateSelectListener(new CollapseCalendarView.OnDateSelect() {
            //日期选择监听
            @Override
            public void onDateSelected(LocalDate date) {
                selectDate = date.toString();
                getDataFromServer(date.toString(), date.toString(), "getList");
                HandleDateChange(date);
            }
        });
        mManager.setMonthChangeListener(new CalendarManager.OnMonthChangeListener() {
            //月份切换监听
            @Override
            public void monthChange(String month, LocalDate mSelected) {
                scheduleTitleNameBack.setText(month);
                getDataFromServer(Week.getFristVisibleDat().substring(0, 7) + "-01", Week.getLastVisibleDat().substring(0, 7) + "-31", "getMonthList");
                if (null != scheduleListResultBean && null != scheduleListResultBean.result && null != scheduleListResultBean.result.list)
                    scheduleListResultBean.result.list.clear();
                if (null != scheduleListAdapter)
                    scheduleListAdapter.notifyDataSetChanged();
            }

        });
    }

    /*
    * 日历时间切换
    * */
    private void HandleDateChange(LocalDate date) {
        //切换日历日期的时候标题栏时间显示变化
        scheduleTitleNameBack.setText(mManager.getDateAndWeek());
    }

    private void initListViewAdapter() {


        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED); //设置listview即可上拉也可下拉
        scheduleListAdapter = new ScheduleListAdapter(ScheduleMainActivity.this, scheduleListResultBean.result.list, 0, app_id, "", selectDate);
        scheduleListAdapter.setOnAdapterItemClick(new ScheduleListAdapter.OnAdapterItemClick() {
            @Override
            public void onAdapterClick(int position) {
                Intent mIntent = new Intent(ScheduleMainActivity.this, ScheduleDetailActivity.class);
                mIntent.putExtra("com_schedule_mobileconfig_actionbutton_style", actionbuttonStyle);
                mIntent.putExtra("com_schedule_mobileconfig_createview_config", createviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_detailview_config", detailviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
                mIntent.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
                mIntent.putExtra("app_version_id", app_version_id);
                mIntent.putExtra("flag", "1");
                mIntent.putExtra("app_id", app_id);
                mIntent.putExtra("sch_id", scheduleListResultBean.result.list.get(position).schId);
                startActivity(mIntent);
            }
        });
        mPullToRefreshListView.setAdapter(scheduleListAdapter);
    }

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.title_left_button) {//                setAlarmTime(this, SystemClock.elapsedRealtime()+5000);
            finish();

        } else if (i1 == R.id.title_left_button_home) {
            BookInit.getInstance().getmCallbackMX().callUserMeesageMain();

        } else if (i1 == R.id.bt_schedule_todat) {
            calendarView.changeDate(LocalDate.now().toString());
            scheduleTitleNameBack.setText(LocalDate.now().toString());
            selectDate = LocalDate.now().toString();
            getDataFromServer(selectDate, selectDate, "getList");
            scheduleTitleNameBack.setText(mManager.getDateAndWeek());

        } else if (i1 == R.id.bt_schedule_search) {
            Intent intent = new Intent(this, ScheduleSearchActivity.class);
            intent.putExtra("app_id", app_id);
            startActivity(intent);

        } else if (i1 == R.id.function) {
            if (!isTuoZhuai) {
                if (mFunctionPopupWindow == null) {
                    return;
                }
                if (!mFunctionPopupWindow.isShowing()) {
//                        function.setImageDrawable(getResources().getDrawable(R.drawable.btn_date_operation_add));

                    for (int i = 0; i < mDataList.size(); i++) {
                        if (mDataList.get(i).getActionID().equals("Share")) {
                            mDataList.remove(i);
                        }
                    }

                    mFunctionPopupWindow = new FunctionPopupWindow(this,
                            new MenuOnClickListener(), mDataList.size());
                    mFunctionPopupWindow.initArcMenu(mDataList, "1");
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
//                        function.setImageDrawable(getResources().getDrawable(R.drawable.btn_date_operation_add));
                    mFunctionPopupWindow.dismiss();
                }
            }
            Intent mIntents = new Intent(ScheduleMainActivity.this, ScheduleDetailActivity.class);
            mIntents.putExtra("com_schedule_mobileconfig_actionbutton_style", actionbuttonStyle);
            mIntents.putExtra("com_schedule_mobileconfig_createview_config", createviewConfig);
            mIntents.putExtra("com_schedule_mobileconfig_detailview_config", detailviewConfig);
            mIntents.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
            mIntents.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
            mIntents.putExtra("app_version_id", app_version_id);
            mIntents.putExtra("flag", "0");
            mIntents.putExtra("app_id", app_id);
            startActivity(mIntents);
//                function.setVisibility(View.VISIBLE);

        } else if (i1 == R.id.tv_mine) {
            tv_img_confirm.setVisibility(View.INVISIBLE);
            tv_img_mine.setVisibility(View.VISIBLE);
            schAdviceStatus = "1";
            getDataFromServer(selectDate, selectDate, "getList");

        } else if (i1 == R.id.tv_confirm) {
            tv_img_confirm.setVisibility(View.VISIBLE);
            tv_img_mine.setVisibility(View.INVISIBLE);
            schAdviceStatus = "0";           //0是无效，需要确认
            getDataFromServer(selectDate, selectDate, "getList");

        } else {
        }
    }

    @Override
    public void callBackLayout() {
        if (action_move)
            function.layout(left, top, right, bottom);
//        if(null != mFunctionPopupWindow && mFunctionPopupWindow.isShowing()){
//            mFunctionPopupWindow.dismiss();
//        }
    }

    int popupHeight;
    int popupWidth;
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

    public void getDataFromServer(String stratTime, String endTime, String requestName) {
        try {
//            getListUrl = "http://htrf.dscloud.me:8083/data-crab/schschedule/findPage";
            getListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_SCHEDULE_LIST;
            scheduleListRequest = new ScheduleListRequest();
            scheduleListRequest.appId = Long.parseLong(app_id);
            scheduleListRequest.groupCorpId = OAConText.getInstance(this).group_corp_id;
            scheduleListRequest.pageNum = 1;
            scheduleListRequest.startTime = stratTime + " 00:00:00";
            scheduleListRequest.endTime = endTime + " 24:00:00";
            scheduleListRequest.corpId = BookInit.getInstance().getCorp_id();
            scheduleListRequest.schAdviceStatus = schAdviceStatus;
//            scheduleListRequest.corpId = "97226251340611590";
            scheduleListRequest.pageSize = 100;
            scheduleListRequest.userId = OAConText.getInstance(this).UserID;
            AnsynHttpRequest.requestByPostWithToken(this, scheduleListRequest, getListUrl, CHTTP.POSTWITHTOKEN, this, requestName, LogManagerEnum.SCHEDULELIST.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.equals("getScheduleListData")) {
            getDataFromServer(selectDate, selectDate, "getList");
        }
        try {
            if (requestName.equals("getList")) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getListUrl, scheduleListRequest, this, "getList", LogManagerEnum.SCHEDULELIST.getFunctionCode());
                if (!TextUtils.isEmpty(requestValue)) {
                    if (null != scheduleListResultBean && null != scheduleListResultBean.result &&
                            null != scheduleListResultBean.result.list) {
                        scheduleListResultBean.result.list.clear();
                    }
                    scheduleListResultBean = JSONObject.parseObject(requestValue, ScheduleListResultBean.class);
                    if (null != scheduleListResultBean && null != scheduleListResultBean.result &&
                            null != scheduleListResultBean.result.list && scheduleListResultBean.result.list.size() == 0) {
                        //当返回数据为空或小于等于0的时候展示空白页
                        emptyLayoutRoot.setEmptyButtonClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getDataFromServer(selectDate, selectDate, "getList");
                            }
                        });
                        emptyLayoutRoot.showEmpty(R.mipmap.img_empty_nodata, "当前没有日程");
                    } else {
                        emptyLayoutRoot.hide();
                        initListViewAdapter();
                    }
                    netWorkManager.logFunactionFinsh(ScheduleMainActivity.this, ScheduleMainActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULELIST.getFunctionCode(), scheduleListResultBean.message, INetWorkManager.State.SUCCESS);
                }
            } else if (requestName.equals("getMonthList")) {
                requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getListUrl, scheduleListRequest, this, "getMonthList", LogManagerEnum.SCHEDULELIST.getFunctionCode());
                if (!TextUtils.isEmpty(requestValue)) {
                    ScheduleListResultBean scheduleListResultInfo = JSONObject.parseObject(requestValue, ScheduleListResultBean.class);
                    if (null != scheduleListResultInfo && null != scheduleListResultInfo.result &&
                            null != scheduleListResultInfo.result.list) {
                        mScheduleList.clear();
                        for (int i = 0; i < scheduleListResultInfo.result.list.size(); i++) {
                            ArrayList<countInfoBean> scheduleStateList = ScheduleUtils.getScheduleState(scheduleListResultInfo.result.list.get(i).schBeginTime.split(" ")[0]
                                    , scheduleListResultInfo.result.list.get(i).schEndTime.split(" ")[0]);

                            mScheduleList.addAll(scheduleStateList);
                        }
                        for (int i = 0; i < mScheduleList.size(); i++) {
                            for (int j = i + 1; j < mScheduleList.size(); j++) {
                                if (mScheduleList.get(i).Date.equals(mScheduleList.get(j).Date)) {
                                    if (mScheduleList.get(i).State.equals("red") || mScheduleList.get(j).State.equals("red")) {
                                        mScheduleList.get(i).State = "red";
                                        mScheduleList.get(j).State = "red";
                                    }
                                }
                            }
                        }
                    }
                    netWorkManager.logFunactionFinsh(ScheduleMainActivity.this, ScheduleMainActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULELIST.getFunctionCode(), scheduleListResultInfo.message, INetWorkManager.State.SUCCESS);
                    initScheduleState();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * 初始化日历红点标识
    * */
    private void initScheduleState() {
        org.json.JSONObject json = new org.json.JSONObject();
        Iterator it = mScheduleList.iterator();
        while (it.hasNext()) {
            org.json.JSONObject jsonObject2 = new org.json.JSONObject();
            countInfoBean next = (countInfoBean) it.next();
            try {
                if (next.State.equals("red")) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put("red");
                    jsonObject2.put("list", jsonArray);
                } else if (next.State.equals("green")) {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put("green");

                    jsonObject2.put("list", jsonArray);
                }
                json.put(next.Date, jsonObject2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        calendarView.setArrayData(json);
        calendarView.init(mManager);
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("getScheduleListData")) {
            getDataFromServer(LocalDate.now().toString(), LocalDate.now().toString(), "getList");
            getDataFromServer(Week.getFristVisibleDat(), Week.getLastVisibleDat(), "getMonthList");
        }
        netWorkManager.logFunactionFinsh(ScheduleMainActivity.this, ScheduleMainActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULELIST.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
    }

    @Override
    public void notNetwork() {
        emptyLayoutRoot.setNoWifiButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer(LocalDate.now().toString(), LocalDate.now().toString(), "getList");
            }
        });
        emptyLayoutRoot.showNowifi();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

    }
}
