package com.htmitech.ztcustom.zt.fragment.qualityobjectiondetailfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.QualityObjectionDetailDqlcAdatper;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.chinarailway.DynamicDetailDataActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailCLQKListRequest;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailDqlcListResult;
import com.htmitech.ztcustom.zt.constant.QualityObjectionDetailDqlcResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class QualityObjectionDetailDQLCFragment extends BaseFragment {


    private ListView listView;
    private boolean isFristShow = true;
    private QualityObjectionDetailCLQKListRequest request;//和处理情况的请求参数一样
    private String id;
    private List<QualityObjectionDetailDqlcListResult> listResults;
    private QualityObjectionDetailDqlcAdatper adapter;

    public QualityObjectionDetailDQLCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quality_objection_detail_dqlc, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView() {
        listView = (ListView) getView().findViewById(R.id.lv_quality_objection_detail_dqlc);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        request = new QualityObjectionDetailCLQKListRequest();
//        request.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "initData->QualityObjectionDetailDQLCFragment: "+request.userid );
        request.id = id;
        listResults = new ArrayList<QualityObjectionDetailDqlcListResult>();
        adapter = new QualityObjectionDetailDqlcAdatper(getActivity(), listResults);
        listView.setAdapter(adapter);
    }

    @Override
    public void lazyLoad() {
        if (isFristShow) {
            showProgressDialog(getActivity());
            getData();
        }
        if (isRefresh) {
            if (isFristShow) {
                return;
            }
            getData();
        }
    }

    public void getData() {

        AnsynHttpRequest.request(getActivity(), request, ContantValues.GETZLYYDQLCLIST, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                if (isFristShow) {
                    isFristShow = false;
                    dimssDialog();
                }
                if (isRefresh) {
                    isRefresh = false;
                    dimssDialog();
                }
                if (successMessage != null && !successMessage.equals("")) {
                    QualityObjectionDetailDqlcResult result = new QualityObjectionDetailDqlcResult();
                    result = FastJsonUtils.getPerson(successMessage, QualityObjectionDetailDqlcResult.class);
                    if (result != null) {
                        if (result.success) {
                            if (listResults != null) {
                                listResults.clear();
                            }
                            listResults.addAll(result.results);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }

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
                ToastUtil.showShort(getActivity(),"暂无数据");
            }
        });
    }
}
