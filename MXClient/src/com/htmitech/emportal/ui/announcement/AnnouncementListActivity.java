package com.htmitech.emportal.ui.announcement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.commonx.pulltorefresh.library.ILoadingLayout;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.announcement.entity.AnnouncementListRequestRoot;
import com.htmitech.emportal.ui.announcement.entity.AnnouncementListResult;
import com.htmitech.emportal.ui.announcement.entity.GetNoticeListByConditionResult;
import com.htmitech.emportal.ui.announcement.entity.GetNoticeListByConditionResultRoot;
import com.htmitech.emportal.ui.announcement.entity.RequestAnnouncementList;
import com.htmitech.emportal.ui.announcement.entity.ResponseAnnouncementList;
import com.htmitech.emportal.ui.announcement.model.GetAnnouncementList;
import com.htmitech.htworkflowformpluginnew.listener.IWorkFlowScrollBack;
import com.htmitech.proxy.interfaces.INetWorkManager;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.minxing.client.util.Utils;

import java.util.ArrayList;

import cn.feng.skin.manager.base.BaseActivity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/*
*    通知公告列表界面
* */
public class AnnouncementListActivity extends BaseActivity implements View.OnClickListener, IBaseCallback, ObserverCallBackType {

    private PullToRefreshListView mListview;
    private ImageView mBack;
    private TextView mTitle;
    private AnnnounceMentListAdapter mMyListAdapter;
    private GetAnnouncementList getAnnouncementList;
    private String app_id;
    private ResponseAnnouncementList mAnnouncementBean;
    public ArrayList<GetNoticeListByConditionResult> mAnnouncementList = new ArrayList<GetNoticeListByConditionResult>();
    public int pageNum = 0;      //开始位置
    public int pageSize = 10;    //结束位置
    private int myfavValue;
    private int shareValue;
    private int showimgValue;
    private int showwaysValue;
    private int signreadValue;
    private INetWorkManager netWorkManager;
    private EmptyLayout emptyLayout;
    private String appName;
    private String app_id1;
    private boolean isHome;
    private ImageView mBackHome;
    public static String GETANNOUNCEMENTLIST = "getNoticeListByCondition";
    public String getNoticeListByConditionUrl = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_ANNOUNCEMENT_LIST_JAVA;
    private AnnouncementListRequestRoot announcementListRequestRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_list);
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        initView();
        GetDataFromServer(pageNum, pageSize, true);


    }

    //UI初始化
    public void initView() {
        //初始化数据
        String myfav = getIntent().getStringExtra("com_notice_mobileconfig_include_myfav");
        String share = getIntent().getStringExtra("com_notice_mobileconfig_include_share");
        String showimg = getIntent().getStringExtra("com_notice_mobileconfig_showimg");
        String showways = getIntent().getStringExtra("com_notice_mobileconfig_showways");
        String signread = getIntent().getStringExtra("com_notice_mobileconfig_signread");
        app_id1 = getIntent().getStringExtra("app_id");
        appName = getIntent().getStringExtra("appName");
        isHome = getIntent().getBooleanExtra("isHome", false); //是否当前在系统主页
        myfavValue = Integer.parseInt(myfav);                //是否支持我关注
        shareValue = Integer.parseInt(share);               //是否支持分享
        showimgValue = Integer.parseInt(showimg);          //是否列表中显示图片
        showwaysValue = Integer.parseInt(showways);       //列表的展示方式 0：传统 1：图文混排
        signreadValue = Integer.parseInt(signread);      //是否记录已读未读
        //创建pulltorefreshlistview
        mListview = (PullToRefreshListView) findViewById(R.id.announcement_listview);
        //返回按钮
        mBack = (ImageView) findViewById(R.id.iv_announcement_back);
        mBackHome = (ImageView) findViewById(R.id.iv_announcement_back_home);
        if (isHome) {
            mBackHome.setVisibility(View.VISIBLE);
            mBack.setVisibility(View.GONE);
        } else {
            mBackHome.setVisibility(View.GONE);
            mBack.setVisibility(View.VISIBLE);
        }
        //标题信息
        mTitle = (TextView) findViewById(R.id.tv_announcement_title);
        //初始化adapter
        mMyListAdapter = new AnnnounceMentListAdapter(AnnouncementListActivity.this, showimgValue, showwaysValue);
        emptyLayout = (EmptyLayout) findViewById(R.id.empty);
        mBack.setOnClickListener(this);
        mBackHome.setOnClickListener(this);
        mTitle.setText(appName == null ? "督办通报" : appName);
        mListview.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = mListview.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("释放开始刷新");// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabels = mListview.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载");// 刷新时
        endLabels.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetNoticeListByConditionResult itemAtPosition = (GetNoticeListByConditionResult) parent.getItemAtPosition(position);
                Intent mIntent = new Intent(AnnouncementListActivity.this, AnnouncementDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data", itemAtPosition);
                mBundle.putInt("myfavValue", myfavValue);
                mBundle.putInt("shareValue", shareValue);
                mBundle.putInt("showimgValue", showimgValue);
                mBundle.putInt("showwaysValue", showwaysValue);
                mBundle.putInt("signreadValue", signreadValue);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub
                // 下拉刷新
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                pullUpLoadMore();
            }
        });

    }

    /*
    * 获取通知公告列表数据
    * @param isFirst 是否是第一次刷新数据（主要用于区别下拉加载）
    * */
    public void GetDataFromServer(int pageNum, int pageSize, boolean isFirst) {
        netWorkManager.logFunactionStart(AnnouncementListActivity.this, this, "notice_getlist", LogManagerEnum.ANNOUNCEMENT_LIST.getFunctionCode());//LogManagerEnum.ANNOUNCEMENT_LIST.functionId
//        RequestAnnouncementList requestAnnouncementList = new RequestAnnouncementList();
//        requestAnnouncementList.app_id = app_id1;
//        requestAnnouncementList.end_time = "";
//        requestAnnouncementList.flag = "0";
//        requestAnnouncementList.modename = "1";
//        requestAnnouncementList.has_read = "";
//        requestAnnouncementList.page_num = pageNum + "";
//        requestAnnouncementList.page_size = pageSize + "";
//        requestAnnouncementList.start_time = "";
//        requestAnnouncementList.title_keyword = "";
//        requestAnnouncementList.days = "";
//        requestAnnouncementList.user_id = PreferenceUtils.getEMPUserID(this);
//        getAnnouncementList = new GetAnnouncementList(this);
//        if (isFirst)       //初次获取数据
//            getAnnouncementList.getDataFromServerByType(GetAnnouncementList.TYPE_GET_ZERO_LIST, requestAnnouncementList);
//        else             //加载更多数据
//            getAnnouncementList.getDataFromServerByType(GetAnnouncementList.TYPE_GET_More_LIST, requestAnnouncementList);
        announcementListRequestRoot = new AnnouncementListRequestRoot();
        announcementListRequestRoot.appId = app_id1;
        announcementListRequestRoot.dbIdentifier = "system";
        announcementListRequestRoot.endTime = "";
        announcementListRequestRoot.flag = "";
        announcementListRequestRoot.modelName = "";
        announcementListRequestRoot.recordEndIndex = (pageNum*pageSize+pageSize)+"";
        announcementListRequestRoot.recordStartIndex = (pageNum*pageSize+1)+"";
        announcementListRequestRoot.startTime = "";
        announcementListRequestRoot.titleKeyWord = "";
        announcementListRequestRoot.userId = PreferenceUtils.getEMPUserID(this);
        AnsynHttpRequest.requestByPostWithToken(this, announcementListRequestRoot, getNoticeListByConditionUrl, CHTTP.POSTWITHTOKEN, this, GETANNOUNCEMENTLIST, com.htmitech.myEnum.LogManagerEnum.ANNOUNCEMENT_LIST.functionCode);

    }

    /*
    * 刷新listview的adapter
    * */
    public void refreshAdapter(ArrayList<GetNoticeListByConditionResult> mAnnouncementList) {
        if (mMyListAdapter != null) {
            mMyListAdapter.setData(mAnnouncementList);
            mListview.setAdapter(mMyListAdapter);
//            mMyListAdapter.notifyDataSetChanged();
        }
//        app_id = getIntent().getStringExtra("app_id");
//        try {
//            ClentAppUnit.getInstance(this).setActivitys(ApplicationAllEnum.TZGG,null);
//        } catch (NotApplicationException e) {
//            e.printStackTrace();
//        }

    }

    /*
    * 下拉刷新
    * */
    public void pullDownRefresh() {
        if(null != mAnnouncementList)
        mAnnouncementList.clear();
        pageNum = 0;
//        pageSize = 10;
        GetDataFromServer(pageNum, pageSize, true);
    }

    /*
    * 上拉加载更多
    * */
    public void pullUpLoadMore() {
        pageNum = pageNum + 1;
//        pageSize = pageSize + 10;
        GetDataFromServer(pageNum, pageSize, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_announcement_back_home:
            case R.id.iv_announcement_back:      //返回
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        mListview.onRefreshComplete();
        if (requestTypeId == GetAnnouncementList.TYPE_GET_ZERO_LIST) {
//            netWorkManager.logFunactionFinsh(AnnouncementListActivity.this, this, "notice_getlist", LogManagerEnum.ANNOUNCEMENT_LIST.getFunctionCode(), ((ResponseAnnouncementList) result).Result.toString(), INetWorkManager.State.SUCCESS);
//            //初次获得列表数据
//            mAnnouncementBean = (ResponseAnnouncementList) result;
//            if (mAnnouncementBean != null && mAnnouncementBean.Result != null) {
//                if (mAnnouncementBean.Result.size() <= 0) {
//                    emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            pageNum = 0;
//                            GetDataFromServer(pageNum, pageSize, true);
//                        }
//                    });
//                    emptyLayout.showEmpty();
//                } else {
//                    emptyLayout.hide();
//                }
//
//            }
//
//            //清空集合里的数据
//            mAnnouncementList.clear();
//            //往空集合里添加新数据
//            if (mAnnouncementBean != null && mAnnouncementBean.Result != null) {
//                mAnnouncementList.addAll(mAnnouncementBean.Result);
//            }

        } else if (requestTypeId == GetAnnouncementList.TYPE_GET_More_LIST) {
            //下拉加载更多
//            mAnnouncementBean = (ResponseAnnouncementList) result;
//            mAnnouncementList.addAll(mAnnouncementBean.Result);
//            if (mAnnouncementBean.Result.size() < 10) {
//                Toast.makeText(AnnouncementListActivity.this, "已是最后一页!", Toast.LENGTH_SHORT).show();
//            }
        }
//        refreshAdapter(mAnnouncementList);
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
        mListview.onRefreshComplete();
        if (requestTypeId == GetAnnouncementList.TYPE_GET_ZERO_LIST) {
            //初次获得列表数据
            netWorkManager.logFunactionFinsh(AnnouncementListActivity.this, this, "notice_getlist", "notice_getlist", result == null ? "" : result.toString(), INetWorkManager.State.FAIL);
        } else if (requestTypeId == GetAnnouncementList.TYPE_GET_More_LIST) {
            //下拉加载更多
        }
        if (!Utils.isNetworkAvailable()) {
            emptyLayout.setNoWifiButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pageNum = 0;
                    GetDataFromServer(pageNum, pageSize, true);
                }
            });
            emptyLayout.showNowifi();
        } else {
            emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnnouncementListActivity.this.finish();
                }
            });
            emptyLayout.showError();
        }
        Toast.makeText(AnnouncementListActivity.this, "获取数据异常", Toast.LENGTH_SHORT).show();
    }

    //---------------------------------------------------日志服务的回调---------------------------------
    @Override
    public void success(String requestValue, int type, String requestName) {
        Log.e("Logdetail", requestValue + "---" + type + "---" + requestName);
        if(GETANNOUNCEMENTLIST.equals(requestName)){
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getNoticeListByConditionUrl, announcementListRequestRoot, this, requestName, com.htmitech.myEnum.LogManagerEnum.ANNOUNCEMENT_LIST.functionCode);
            if(!TextUtils.isEmpty(requestValue)){
                netWorkManager.logFunactionFinsh(AnnouncementListActivity.this, this, "notice_getlist", LogManagerEnum.ANNOUNCEMENT_LIST.getFunctionCode(), "移动端成功", INetWorkManager.State.SUCCESS);
                GetNoticeListByConditionResultRoot getNoticeListByConditionResultRoot = JSONObject.parseObject(requestValue, GetNoticeListByConditionResultRoot.class);
                if(null != getNoticeListByConditionResultRoot && getNoticeListByConditionResultRoot.code == 200){
                    mListview.onRefreshComplete();
                    if(null != getNoticeListByConditionResultRoot){
                        if (getNoticeListByConditionResultRoot.result.size() <= 0) {
                            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pageNum = 0;
                                    GetDataFromServer(pageNum, pageSize, true);
                                }
                            });
                            emptyLayout.showEmpty();
                        } else {
                            emptyLayout.hide();
                        }
//                        mAnnouncementList.clear();
                        mAnnouncementList.addAll(getNoticeListByConditionResultRoot.result);
                        if(pageNum > 0 && mMyListAdapter != null ){
                            mMyListAdapter.notifyDataSetChanged();
                        }else{
                            refreshAdapter(mAnnouncementList);
                        }

                    }

                }
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
    //----------------------------------------------------------------------------------------------
}
