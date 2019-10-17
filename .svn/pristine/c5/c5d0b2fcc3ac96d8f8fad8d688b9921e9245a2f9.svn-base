package com.htmitech.ztcustom.zt.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.Shangsxqdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.GetDefectDetailRequest;
import com.htmitech.ztcustom.zt.domain.GetDefectDetailSuccess;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;


/**
 * 现存伤损详情——伤损详情
 */
public class ShangsxqFragment extends BaseFragment {
    private ListView lv_ssxq;
    private Shangsxqdapter mShangsxqdapter;
    private String userid;
    private String defect_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zt_fragment_shangsxq, container,
                false);
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setDefect_id(String defect_id) {
        this.defect_id = defect_id;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void initView() {
        lv_ssxq = (ListView) getActivity().findViewById(R.id.lv_ssxq);
    }

    @Override
    protected void initData() {

        GetDefectDetailRequest mGetDefectDetailRequest = new GetDefectDetailRequest();
        mGetDefectDetailRequest.userid = userid;
        mGetDefectDetailRequest.defect_id = defect_id;
        mShangsxqdapter = new Shangsxqdapter(getActivity(),null);
        lv_ssxq.setAdapter(mShangsxqdapter);
        AnsynHttpRequest.request(getActivity(), mGetDefectDetailRequest, CHTTP.GETDEFECTDETAIL,
                CHTTP.POST, new ObserverCallBack() {

                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        GetDefectDetailSuccess mGetDefectDetailSuccess = FastJsonUtils
                                .getPerson(successMessage, GetDefectDetailSuccess.class);
                        mShangsxqdapter.setData(mGetDefectDetailSuccess.defect_detail_list);

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
}
