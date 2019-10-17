package com.htmitech.ztcustom.zt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshBase;
import com.htmitech.commonx.pulltorefresh.library.PullToRefreshListView;
import com.htmitech.ztcustom.zt.adapter.CardListAdapter;
import com.htmitech.ztcustom.zt.adapter.HistoryListAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.GetDefectDetailRequest;
import com.htmitech.ztcustom.zt.domain.GetDefectHistoryListSuccess;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * 现存伤损详情——历史信息
 */
public class HistoricalMessageFragment extends BaseFragment implements View.OnClickListener {
    private PullToRefreshListView mPullToRefreshListView;
    private GetDefectDetailRequest mGetDefectDetailRequest;
    private HistoryListAdapter mHistoryListAdapter ;
    private String userid;
    private String defect_id;
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setDefect_id(String defect_id) {
        this.defect_id = defect_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zt_fragment_historical_message, container,
                false);
    }
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView() {
        mPullToRefreshListView = (PullToRefreshListView) getActivity().findViewById(R.id.lv_card_list);
    }

    @Override
    protected void initData() {
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.setOnClickListener(this);

        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉加载更多");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放开始加载");

        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        mPullToRefreshListView.setAdapter(new CardListAdapter(getActivity(), null));

        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);


        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullDownToRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                pullUpToRefresh();
            }
        });

        mGetDefectDetailRequest = new GetDefectDetailRequest();
        mGetDefectDetailRequest.userid = userid;
        mGetDefectDetailRequest.defect_id = defect_id;
        mHistoryListAdapter = new HistoryListAdapter(getActivity(),null);
        mPullToRefreshListView.setAdapter(mHistoryListAdapter);
        AnsynHttpRequest.request(getActivity(), mGetDefectDetailRequest, CHTTP.GETDEFECTHISTORYLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetDefectHistoryListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectHistoryListSuccess.class);
                        mHistoryListAdapter.setData(mGetDefectStatSuccess.defect_history_list, 0);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }
    /**
     * 上拉
     */
    public void pullDownToRefresh(){
        mGetDefectDetailRequest.defect_id = userid;
        AnsynHttpRequest.request(getActivity(), mGetDefectDetailRequest, CHTTP.GETDEFECTHISTORYLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetDefectHistoryListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectHistoryListSuccess.class);
                        mPullToRefreshListView.onRefreshComplete();
                        mHistoryListAdapter.setData(mGetDefectStatSuccess.defect_history_list, 0);
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }
    /**
     * 下拉
     */
    public void pullUpToRefresh(){
        mGetDefectDetailRequest.defect_id = mHistoryListAdapter.getmHistoryList().get(mHistoryListAdapter.getmHistoryList().size() - 1).defect_id+ "";
        AnsynHttpRequest.request(getActivity(), mGetDefectDetailRequest, CHTTP.GETDEFECTHISTORYLIST,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        mPullToRefreshListView.onRefreshComplete();
                        GetDefectHistoryListSuccess mGetDefectStatSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectHistoryListSuccess.class);
                        if(mGetDefectStatSuccess.defect_history_list.size() == 0){
                            Utils.toast(getActivity(), "已经是最后一页了！", Toast.LENGTH_SHORT);
                        }else {
                            mHistoryListAdapter.setData(mGetDefectStatSuccess.defect_history_list, 1);
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub

                    }
                });
    }
    @Override
    public void onClick(View v) {

    }
}
