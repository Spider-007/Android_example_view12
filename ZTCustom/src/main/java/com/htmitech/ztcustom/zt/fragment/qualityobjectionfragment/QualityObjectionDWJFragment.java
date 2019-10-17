package com.htmitech.ztcustom.zt.fragment.qualityobjectionfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionCommonAdapter;
import com.htmitech.ztcustom.zt.chinarailway.QualityObjectionDetailsActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.GetQualityObjectionListSearchRequest;
import com.htmitech.ztcustom.zt.constant.QualityObjectionListSearchResultList;
import com.htmitech.ztcustom.zt.constant.QualityObjectionListSearchRuslt;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnQualityObjectionRefreshDataCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * 待完结.
 */
public class QualityObjectionDWJFragment extends BaseFragment {


    private PullToRefreshListView listView;
    private QualityObjectionCommonAdapter adapter;
    private GetQualityObjectionListSearchRequest getQualityObjectionListSearchRequest;
    private int pagesize = 10;
    private int page = 1;
    private boolean isFristShow = true;//默认进来第一次时候加载
    private boolean isHasMore = true;
    private List<QualityObjectionListSearchResultList> list;
    private OnQualityObjectionRefreshDataCallBack refreshDataCallBack;

    public QualityObjectionDWJFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQualityObjectionRefreshDataCallBack) {
            refreshDataCallBack = ((OnQualityObjectionRefreshDataCallBack) context);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quality_objection_dwj, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getQualityObjectionListSearchRequest = new GetQualityObjectionListSearchRequest();
//        getQualityObjectionListSearchRequest.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        getQualityObjectionListSearchRequest.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "onCreate->QualityObjectioncdwjFragment:"+getQualityObjectionListSearchRequest.userid);
        getQualityObjectionListSearchRequest.yqbm = "dwj";
        getQualityObjectionListSearchRequest.pagesize = pagesize + "";
        getQualityObjectionListSearchRequest.page = page + "";
    }

    @Override
    protected void initView() {
        listView = (PullToRefreshListView) getView().findViewById(R.id.lv_quality_objection_DWJ);
    }

    @Override
    protected void initData() {
        Log.e("DWJ", "INIT");
        initPullToRefresh();
        list = new ArrayList<QualityObjectionListSearchResultList>();
        adapter = new QualityObjectionCommonAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getQualityObjectionListSearchRequest.page = page + "";
                list.clear();
                // 下拉刷新
                pullDownRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub 上拉加载更多
                page++;
                getQualityObjectionListSearchRequest.page = page + "";
                pullUpLoadMore();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), QualityObjectionDetailsActivity.class);
                intent.putExtra("id", list.get(position - 1).id);
                startActivity(intent);
            }
        });
    }

    private void initPullToRefresh() {
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载");
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉加载更多");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放开始加载");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
    }

    /**
     * 上拉
     */
    private void pullDownRefresh() {

        if (refreshDataCallBack != null) {
            refreshDataCallBack.onRefreshData();
        }
        Log.e("DWJ", "pullDownRefresh被调用");

        AnsynHttpRequest.request(getActivity(), getQualityObjectionListSearchRequest, ContantValues.GETZLYYLBCX, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionListSearchRuslt result = new QualityObjectionListSearchRuslt();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionListSearchRuslt.class);
                    if (result.success) {
                        list.addAll(result.results);
                        if (result.totals.equals(pagesize + "")) {
                            isHasMore = true;
                        } else {
                            isHasMore = false;
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Utils.toast(getActivity(), "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    Utils.toast(getActivity(), "服务器异常,返回为空", Toast.LENGTH_SHORT);
                }
                listView.onRefreshComplete();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
                Utils.toast(getActivity(), "暂无数据", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 下拉
     */
    public void pullUpLoadMore() {

        if (!isHasMore) {
            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.onRefreshComplete();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return;
        }

        AnsynHttpRequest.request(getActivity(), getQualityObjectionListSearchRequest, ContantValues.GETZLYYLBCX, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionListSearchRuslt result = new QualityObjectionListSearchRuslt();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionListSearchRuslt.class);
                    if (result.success) {
                        if (result.totals.equals(pagesize + "")) {
                            isHasMore = true;
                        } else {
                            isHasMore = false;
                        }
                        if (result.results != null && result.results.size() == 0) {
                            isHasMore = false;
                            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
                        } else {
                            list.addAll(result.results);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Utils.toast(getActivity(), "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    Utils.toast(getActivity(), "服务器异常,返回为空", Toast.LENGTH_SHORT);
                }
                listView.onRefreshComplete();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                listView.onRefreshComplete();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                Utils.toast(getActivity(), "暂无数据" , Toast.LENGTH_SHORT);
                listView.onRefreshComplete();
            }
        });
    }

    @Override
    public void lazyLoad() {
        Log.e("DWJ", "被调用");
        if (isFristShow) {
            showProgressDialog(getActivity());
            pullDownRefresh();
        }
        if (isRefresh) {
            if (isFristShow) {
                return;
            }
            page = 1;
            getQualityObjectionListSearchRequest.page = page + "";
            list.clear();
            showProgressDialog(getActivity());
            pullDownRefresh();
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


}
