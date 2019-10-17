package com.htmitech.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.htmitech.MyView.EmptyLayout;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

import com.htmitech.myEnum.LogManagerEnum;
import com.htmitech.proxy.interfaces.INetWorkManager;

import com.htmitech.proxy.util.LogManagerProxy;
import com.htmitech.proxy.util.NetWorkManager;
import com.htmitech.video.adapter.VideoClassifiListAdapter;
import com.htmitech.video.bean.VideoClassifiListRequestBean;
import com.htmitech.video.bean.VideoClassifiResultBean;
import com.htmitech.video.bean.VideoClassifiVideoTypeListBean;
import com.htmitech.video.utils.NetworkUtils;
import com.minxing.client.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

public class VideoClassifiActivity extends BaseFragmentActivity implements ObserverCallBackType {

    private EmptyLayout emptyLayout;
    private ListView lvVideoClassifi;
    private String getClassifiUrl = "";
    private static final String HTTPVideoClass = "VideoClassIFI";
    private String app_version_id;
    private String app_id;
    private String app_name;
    private TextView tvTitle;
    private VideoClassifiListRequestBean videoClassifiListRequestBean = null;
    private int pageNum = 1;
    private int pageSize = 100;
    private INetWorkManager netWorkManager;
    private VideoClassifiListAdapter adapter;
    private List<VideoClassifiVideoTypeListBean> adapterList;
    private ImageButton igBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_classifi);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        emptyLayout = (EmptyLayout) findViewById(R.id.layout_search_no);
        lvVideoClassifi = (ListView) findViewById(R.id.lv_video_classifi);
        tvTitle = (TextView) findViewById(R.id.title_name);
        igBack = (ImageButton) findViewById(R.id.title_left_button);
        igBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        adapterList = new ArrayList<VideoClassifiVideoTypeListBean>();
        getClassifiUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.GET_VIDEO_CLASSIFI;
        Intent intent = getIntent();
        app_version_id = intent.getStringExtra("app_version_id");
        app_id = intent.getStringExtra("app_id");
        app_name = intent.getStringExtra("appName");
        tvTitle.setText(app_name);
        videoClassifiListRequestBean = new VideoClassifiListRequestBean();
        videoClassifiListRequestBean.pageNum = pageNum;
        videoClassifiListRequestBean.pageSize = pageSize;
        netWorkManager = LogManagerProxy.getProxyInstance(NetWorkManager.class);
        LogManagerEnum.VIDEO_LIST.appVersionId = app_version_id;
        LogManagerEnum.VIDEO_LIST.app_id = app_id;
        showDialog();
        netWorkManager.logFunactionStart(this, this, LogManagerEnum.VIDEO_LIST);
        adapter = new VideoClassifiListAdapter(this, adapterList);
        lvVideoClassifi.setAdapter(adapter);
    }


    private void initControl() {
        lvVideoClassifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VideoClassifiActivity.this, VideoListActivity.class);
                intent.putExtra("app_version_id", app_version_id);
                intent.putExtra("app_id", app_id);
                intent.putExtra("appName", app_name);
                intent.putExtra("id", adapterList.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (LogManagerEnum.VIDEO_LIST.functionCode.equals(requestName)) {
            AnsynHttpRequest.requestByPostWithToken(this, videoClassifiListRequestBean, getClassifiUrl, CHTTP.POSTWITHTOKEN, this, HTTPVideoClass, LogManagerEnum.VIDEO_LIST.functionCode);
        } else if (HTTPVideoClass.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this,requestValue, type, getClassifiUrl, videoClassifiListRequestBean, this, requestName, LogManagerEnum.VIDEO_LIST.functionCode);
            dismissDialog();
            if (requestValue != null && !requestValue.equals("")) {
                VideoClassifiResultBean bean = FastJsonUtils.getPerson(requestValue, VideoClassifiResultBean.class);
                if (bean.result.pjVideoTypeList.size() == 0) {
                    emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog();
                            netWorkManager.logFunactionStart(VideoClassifiActivity.this, VideoClassifiActivity.this, LogManagerEnum.VIDEO_LIST);
                        }
                    });
                    emptyLayout.showEmpty(R.drawable.img_empty_nodata, "当前没有数据");
                    return;
                }
                if (adapterList != null) {
                    adapterList.clear();
                }
                adapterList.addAll(bean.result.pjVideoTypeList);
                adapter.notifyDataSetChanged();
                netWorkManager.logFunactionFinsh(VideoClassifiActivity.this, VideoClassifiActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.functionCode, bean.message, INetWorkManager.State.SUCCESS);
            } else {
                netWorkManager.logFunactionFinsh(VideoClassifiActivity.this, VideoClassifiActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.functionCode, "返回为空", INetWorkManager.State.FAIL);
                emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        netWorkManager.logFunactionStart(VideoClassifiActivity.this, VideoClassifiActivity.this, LogManagerEnum.VIDEO_LIST);
                    }
                });
                emptyLayout.showEmpty(R.drawable.img_page_none, "服务器连接异常");
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (LogManagerEnum.VIDEO_LIST.functionCode.equals(requestName)) {
            showDialog();
            AnsynHttpRequest.requestByPostWithToken(this, videoClassifiListRequestBean, getClassifiUrl, CHTTP.POSTWITHTOKEN, this, HTTPVideoClass, LogManagerEnum.VIDEO_LIST.functionCode);
        } else if (requestName.equals(HTTPVideoClass)) {
            dismissDialog();
            if (!NetworkUtils.isNetworkConnected(VideoClassifiActivity.this)) {
                emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        netWorkManager.logFunactionStart(VideoClassifiActivity.this, VideoClassifiActivity.this, LogManagerEnum.VIDEO_LIST);
                    }
                });
                emptyLayout.showEmpty(R.drawable.img_no_wifi, "网络连接失败");
                return;
            }
            netWorkManager.logFunactionFinsh(VideoClassifiActivity.this, VideoClassifiActivity.this, "FunctionFinish", LogManagerEnum.VIDEO_LIST.functionCode, exceptionMessage, INetWorkManager.State.FAIL);
            emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();
                    netWorkManager.logFunactionStart(VideoClassifiActivity.this, VideoClassifiActivity.this, LogManagerEnum.VIDEO_LIST);
                }
            });
            emptyLayout.showEmpty(R.drawable.img_page_none, "服务器连接异常");
        }
    }


    @Override
    public void notNetwork() {
        dismissDialog();
        emptyLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                netWorkManager.logFunactionStart(VideoClassifiActivity.this, VideoClassifiActivity.this, LogManagerEnum.VIDEO_LIST);
            }
        });
        emptyLayout.showEmpty(R.drawable.img_no_wifi, "网络连接失败");
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
