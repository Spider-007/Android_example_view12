package com.htmitech.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.video.adapter.VideoListAdapter;
import com.htmitech.video.bean.VideoDetailInfo;
import com.htmitech.video.bean.VideoListBean;
import com.htmitech.video.bean.VideoListPageList;
import com.htmitech.video.bean.VideoListRequestBean;
import com.htmitech.video.utils.NetworkUtils;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.client.util.Utils;

import java.util.ArrayList;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class VideoListActivity extends BaseFragmentActivity implements View.OnClickListener, ObserverCallBackType {

    private PullToRefreshListView pullToRefreshListView;
    private VideoListAdapter adapter = null;
    private ImageButton igBack;
    private String getVideoListUrl = "";
    private static final String HTTPLIST = "getList";
    private VideoListRequestBean videoListRequestBean = null;
    private INetWorkManager netWorkManager;
    private String app_version_id;
    private String app_id;
    private int pageNum = 1;
    private int pageSize = 10;
    private List<VideoListPageList> adapterList;
    private boolean hasNextPage = false;
    private TextView tvTitle;
    private ImageView ivIsPlay;
    private int pages;
    private EmptyLayout mEmptyLayout;
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        initView();
        initData();
        initControl();
    }

    public void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.video_pulllist);
        igBack = (ImageButton) findViewById(R.id.title_left_button);
        tvTitle = (TextView) findViewById(R.id.title_name);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.layout_search_no);
    }

    public void initData() {
        adapterList = new ArrayList<VideoListPageList>();
        Intent intent = getIntent();
        app_version_id = intent.getStringExtra("app_version_id");
        app_id = intent.getStringExtra("app_id");
        String app_name = intent.getStringExtra("appName");
        typeId = Integer.parseInt(intent.getStringExtra("id"));
        tvTitle.setText(app_name);
        videoListRequestBean = new VideoListRequestBean();
        igBack.setOnClickListener(this);
        getVideoListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_VIDEO_LIST;
        videoListRequestBean.pageNum = pageNum;
        videoListRequestBean.pageSize = pageSize;
        videoListRequestBean.videoTypeID = typeId;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        LogManagerEnum.VIDEO_LIST.appVersionId = app_version_id;
        LogManagerEnum.VIDEO_LIST.app_id = app_id;
        showDialog();
        netWorkManager.logFunactionStart(this, this, LogManagerEnum.VIDEO_LIST);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new VideoListAdapter(this, adapterList);
        pullToRefreshListView.setAdapter(adapter);
    }

    public void initControl() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

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

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ivIsPlay = (ImageView) view.findViewById(R.id.iv_video_item_isplay);
                if (ivIsPlay != null)
                    ivIsPlay.setImageResource(R.drawable.icon_video_news_pre);
                VideoDetailInfo videoDetailInfo = new VideoDetailInfo();
                videoDetailInfo.title = adapterList.get(position - 1).title;
                videoDetailInfo.videoPath = adapterList.get(position - 1).filePath;
                VideoDetailActivity.start(VideoListActivity.this, videoDetailInfo);
//                ((ImageView) view.findViewById(R.id.iv_video_item_isplay)).setImageResource(R.drawable.icon_video_news_normal);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ivIsPlay != null)
            ivIsPlay.setImageResource(R.drawable.icon_video_news_normal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_button:
                finish();
                break;
        }
    }

    private void pullDownRefresh() {
        pageNum = 1;
        videoListRequestBean.pageNum = pageNum;
        videoListRequestBean.pageSize = pageSize;
        netWorkManager.logFunactionStart(this, this, LogManagerEnum.VIDEO_LIST);
    }

    private void pullUpLoadMore() {
        pageNum++;
        if (pageNum <= pages) {
            videoListRequestBean.pageNum = pageNum;
            videoListRequestBean.pageSize = pageSize;
            netWorkManager.logFunactionStart(this, this, LogManagerEnum.VIDEO_LIST);
        } else {
            Utils.toast(this, "已经是最后一页了！", Toast.LENGTH_SHORT);
            pullToRefreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    pullToRefreshListView.onRefreshComplete();
                }
            }, 100);
        }
    }


    @Override
    public void success(String requestValue, int type, String requestName) {
        if (LogManagerEnum.VIDEO_LIST.getFunctionCode().equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, videoListRequestBean, getVideoListUrl, CHTTP.POSTWITHTOKEN, this, HTTPLIST, LogManagerEnum.VIDEO_LIST.getFunctionCode());
        } else if (requestName.equals(HTTPLIST)) {
            mEmptyLayout.hide();
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getVideoListUrl, videoListRequestBean, this, requestName, LogManagerEnum.VIDEO_LIST.getFunctionCode());
            pullToRefreshListView.onRefreshComplete();
            if (requestValue != null && !requestValue.equals("")) {
                VideoListBean bean = FastJsonUtils.getPerson(requestValue, VideoListBean.class);
//                hasNextPage = bean.result.page.hasNextPage;
                pages = bean.result.page.pages;
                if (pageNum == 1) {
                    if (adapterList != null) {
                        adapterList.clear();
                    }
                    adapterList.addAll(bean.result.page.list);
                } else {
                    adapterList.addAll(bean.result.page.list);
                }
                adapter.notifyDataSetChanged();
                netWorkManager.logFunactionFinsh(VideoListActivity.this, VideoListActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.getFunctionCode(), bean.message, INetWorkManager.State.SUCCESS);
                if (adapterList.size() == 0) {
                    mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog();
                            pullDownRefresh();
                        }
                    });
                    mEmptyLayout.showEmpty(R.drawable.img_empty_nodata, "当前没有数据");
                }
            } else {
                netWorkManager.logFunactionFinsh(VideoListActivity.this, VideoListActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.getFunctionCode(), "返回为空", INetWorkManager.State.FAIL);
                mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        pullDownRefresh();
                    }
                });
                mEmptyLayout.showEmpty(R.drawable.img_page_none, "服务器连接异常");
            }
            dismissDialog();
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.VIDEO_LIST.functionCode.equals(requestName)) {
            showDialog();
            AnsynHttpRequest.requestByPostWithToken(this, videoListRequestBean, getVideoListUrl, CHTTP.POSTWITHTOKEN, this, HTTPLIST, LogManagerEnum.VIDEO_LIST.getFunctionCode());
        } else if (requestName.equals(HTTPLIST)) {
            pullToRefreshListView.onRefreshComplete();
            dismissDialog();
            if (!NetworkUtils.isNetworkConnected(VideoListActivity.this)) {
                mEmptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        pullDownRefresh();
                    }
                });
                mEmptyLayout.showEmpty(R.drawable.img_no_wifi, "网络连接失败");
                return;
            }
            netWorkManager.logFunactionFinsh(VideoListActivity.this, VideoListActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.getFunctionCode(), exceptionMessage, INetWorkManager.State.FAIL);
            mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                    pullDownRefresh();
                }
            });
            mEmptyLayout.showEmpty(R.drawable.img_page_none, "服务器连接异常");
        }
    }

    @Override
    public void notNetwork() {
        dismissDialog();
        mEmptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                pullDownRefresh();
            }
        });
        mEmptyLayout.showEmpty(R.drawable.img_no_wifi, "无网络，请设置网络！");
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }

}
