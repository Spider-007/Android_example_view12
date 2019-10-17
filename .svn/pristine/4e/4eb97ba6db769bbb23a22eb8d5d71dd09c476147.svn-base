package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.schedulemanagerlibrary.R;
import com.htmitech.api.BookInit;
import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.utils.OAConText;
import com.minxing.pulltorefresh.library.PullToRefreshBase;
import com.minxing.pulltorefresh.library.PullToRefreshListView;

import Interface.INetWorkManager;
import adapter.ScheduleListAdapter;
import cn.feng.skin.manager.base.BaseFragmentActivity;
import dao.AppliationCenterDao;
import entiy.AppInfo;
import entiy.AppVersion;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;
import proxy.LogManagerProxy;
import proxy.NetWorkManager;
import schedulebean.ScheduleListRequest;
import schedulebean.ScheduleListResultBean;
import widget.EmptyLayout;

/*
* 日程条目搜索接口
* */
public class ScheduleSearchActivity extends BaseFragmentActivity implements View.OnClickListener, TextWatcher,
        ObserverCallBackType {


    EditText etSearchContent;

    TextView tvSearchCancel;

    LinearLayout llSearchHeader;

    FrameLayout flSearchRoot;

    PullToRefreshListView lvScheduleSearchList;

    EmptyLayout emptyLayoutRoot;
    private String app_id;
    private ScheduleListAdapter scheduleListAdapter;
    private String actionbuttonStyle;          //功能按钮展示方式（圆圈还是底部条目）
    private String createviewConfig;
    private String detailviewConfig;
    private String includeSecurity;            //是否需要水印
    private String dropdownOptionManual;
    private ScheduleListResultBean scheduleListResultBean;
    private String keyword;
    private INetWorkManager netWorkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_search);
         etSearchContent = (EditText)findViewById(R.id.et_search_content);
        tvSearchCancel = (TextView)findViewById(R.id.tv_search_cancel);
        llSearchHeader = (LinearLayout)findViewById(R.id.ll_search_header);
        flSearchRoot = (FrameLayout)findViewById(R.id.fl_search_root);
        lvScheduleSearchList = (PullToRefreshListView)findViewById(R.id.lv_schedule_search_list);
        emptyLayoutRoot = (EmptyLayout)findViewById(R.id.empty_layout_root);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        initConfig();
        tvSearchCancel.setOnClickListener(this);
        etSearchContent.addTextChangedListener(this);
        etSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getDataFromServer(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    /*
 * 获取配置
 * */
    private void initConfig() {
        Intent intent = getIntent();
        app_id = intent.getStringExtra("app_id");
        AppliationCenterDao appliationCenterDao = new AppliationCenterDao(this);
        AppInfo appInfo = appliationCenterDao.getAppInfo(app_id);
        if (null != appInfo && null != appInfo.getmAppVersionList() && appInfo.getmAppVersionList().size() > 0) {
            AppVersion appVersion = appInfo.getmAppVersionList().get(0);
            if (null != appVersion && null != appVersion.getAppVersionConfigArrayList()) {
                for (int i = 0; i < appVersion.getAppVersionConfigArrayList().size(); i++) {
                    if (appVersion.getAppVersionConfigArrayList().get(i).getConfig_code().equals("com_schedule_mobileconfig_detailview_config")) {
                        detailviewConfig = appVersion.getAppVersionConfigArrayList().get(i).getConfig_value();
                    } else if (appVersion.getAppVersionConfigArrayList().get(i).getConfig_code().equals("com_schedule_mobileconfig_dropdown_option_manual")) {
                        dropdownOptionManual = appVersion.getAppVersionConfigArrayList().get(i).getConfig_value();
                    } else if (appVersion.getAppVersionConfigArrayList().get(i).getConfig_code().equals("com_schedule_mobileconfig_include_security")) {
                        includeSecurity = appVersion.getAppVersionConfigArrayList().get(i).getConfig_value();
                    }
                }
            }
        }

        //日程管理详情页中的操作按钮样式
        actionbuttonStyle = intent.getStringExtra("com_schedule_mobileconfig_actionbutton_style");
        //日程创建页面配置
        createviewConfig = intent.getStringExtra("com_schedule_mobileconfig_createview_config");
        //日常的详情页面配置

        //日程管理详情页是不是支持水印
//        includeSecurity = intent.getStringExtra("");


    }

    private void initListViewAdapter() {
        lvScheduleSearchList.setMode(PullToRefreshBase.Mode.BOTH); //设置listview即可上拉也可下拉
        lvScheduleSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(ScheduleSearchActivity.this, ScheduleDetailActivity.class);
                mIntent.putExtra("com_schedule_mobileconfig_actionbutton_style", actionbuttonStyle);
                mIntent.putExtra("com_schedule_mobileconfig_createview_config", createviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_detailview_config", detailviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
                mIntent.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
                mIntent.putExtra("app_id", app_id);
                mIntent.putExtra("flag", "1");
                mIntent.putExtra("sch_id", scheduleListResultBean.result.list.get(position - 1).schId);
                startActivity(mIntent);
            }
        });
        scheduleListAdapter = new ScheduleListAdapter(ScheduleSearchActivity.this, scheduleListResultBean.result.list, 1, app_id, etSearchContent.getText().toString(), "");
        scheduleListAdapter.setOnAdapterItemClick(new ScheduleListAdapter.OnAdapterItemClick() {
            @Override
            public void onAdapterClick(int position) {
                Intent mIntent = new Intent(ScheduleSearchActivity.this, ScheduleDetailActivity.class);
                mIntent.putExtra("com_schedule_mobileconfig_actionbutton_style", actionbuttonStyle);
                mIntent.putExtra("com_schedule_mobileconfig_createview_config", createviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_detailview_config", detailviewConfig);
                mIntent.putExtra("com_schedule_mobileconfig_include_security", includeSecurity);
                mIntent.putExtra("com_schedule_mobileconfig_dropdown_option_manual", dropdownOptionManual);
                mIntent.putExtra("app_id", app_id);
                mIntent.putExtra("flag", "1");
                startActivity(mIntent);
            }
        });
        lvScheduleSearchList.setAdapter(scheduleListAdapter);
    }

    public void getDataFromServer(String keyword) {
        try {
//            String url = "http://htrf.dscloud.me:8083/data-crab/schschedule/findPage";
            String url = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_SCHEDULE_LIST;
            ScheduleListRequest scheduleListRequest = new ScheduleListRequest();
            scheduleListRequest.appId = Long.parseLong(app_id);
            scheduleListRequest.groupCorpId = OAConText.getInstance(this).group_corp_id;
            scheduleListRequest.pageNum = 1;
//        scheduleListRequest.stratTime = "2017-09-10 12:00:00";   //搜索不传时间参数
//        scheduleListRequest.endTime = "2017-10-10 12:00:00";
//        scheduleListRequest.corpId = BookInit.getInstance().getCorp_id();
            scheduleListRequest.corpId = BookInit.getInstance().getCorp_id();
            scheduleListRequest.pageSize = 100;
            scheduleListRequest.schTitle = keyword == null ? "" : keyword;
//        scheduleListRequest.userId = OAConText.getInstance(this).UserID;
            scheduleListRequest.userId = OAConText.getInstance(this).UserID;
            AnsynHttpRequest.requestByPostWithToken(this, scheduleListRequest, url, CHTTP.POSTWITHTOKEN, this, "getList", LogManagerEnum.GGENERAL.getFunctionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_search_cancel) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(tvSearchCancel.getWindowToken(), 0);
            finish();

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//        keyword = s.toString();
//        getDataFromServer(keyword);
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        try {
            if (requestName.equals("getList")) {
                scheduleListResultBean = JSONObject.parseObject(requestValue, ScheduleListResultBean.class);
                if (null != scheduleListResultBean && null != scheduleListResultBean.result &&
                        null != scheduleListResultBean.result.list && scheduleListResultBean.result.list.size() == 0) {
                    //当返回数据为空或小于等于0的时候展示空白页
                    emptyLayoutRoot.setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getDataFromServer(keyword);
                        }
                    });
                    emptyLayoutRoot.showEmpty(R.mipmap.img_empty_nodata, "抱歉，没有找到相关搜索结果\n" +
                            "换个词试一试吧");
                } else {
                    emptyLayoutRoot.hide();
                    initListViewAdapter();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        netWorkManager.logFunactionFinsh(ScheduleSearchActivity.this, ScheduleSearchActivity.this, "getScheduleDataFinish", LogManagerEnum.SCHEDULELIST.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
    }

    @Override
    public void notNetwork() {
        emptyLayoutRoot.setNoWifiButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer(keyword);
            }
        });
        emptyLayoutRoot.showNowifi();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
