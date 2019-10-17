package com.htmitech.ztcustom.zt.fragment.weldingbasefragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.WeldingBaseBarAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.WeldingBaseBarRequest;
import com.htmitech.ztcustom.zt.domain.WeldingBaseBarResult;
import com.htmitech.ztcustom.zt.domain.WeldingBaseResultList;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * 待焊轨.
 */
public class WaittingWeldingFragment extends BaseFragment {

    private ListView listViewBar;
    private WeldingBaseBarAdapter adapter;
    private List<WeldingBaseResultList> list;

    public WaittingWeldingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waitting_welding, container, false);
    }


    @Override
    protected void initView() {
        listViewBar = (ListView) getView().findViewById(R.id.lv_waitting_welding_bar);
    }

    @Override
    protected void initData() {
        list = new ArrayList<WeldingBaseResultList>();
        adapter = new WeldingBaseBarAdapter(getActivity(), list, "DHG");
        listViewBar.setAdapter(adapter);
        getData();
    }


    private void getData() {

        showProgressDialog(getActivity());
        WeldingBaseBarRequest request = new WeldingBaseBarRequest();
//        request.userid =  ZTCustomInit.get().getmCache().getmListDetails().AccountId;
        request.userid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        Log.e("YJH", "getData->WaittingWeldingFragment:"+ request.userid);
        AnsynHttpRequest.request(getActivity(), request, ContantValues.WAITTINGWELDINGBAR, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(String successMessage) {
                // TODO Auto-generated method stub

                if (successMessage != null && !successMessage.equals("")) {
                    WeldingBaseBarResult result = new WeldingBaseBarResult();
                    result = FastJsonUtils.getPerson(successMessage, WeldingBaseBarResult.class);
                    if (result.success) {
                        if (list != null) {
                            list.clear();
                        }
                        list.addAll(result.results);
                        float maxValue = 0f;
                        for (int i = 0; i < list.size(); i++) {
                            if (!list.get(i).dhgkucun.equals("") && !list.get(i).dhgkurong.equals("")) {
                                float kucuntemp = Float.parseFloat(list.get(i).dhgkucun);
                                float kurongtemp = Float.parseFloat(list.get(i).dhgkurong);
                                if (kucuntemp > maxValue) {
                                    maxValue = kucuntemp;
                                }
                                if (kurongtemp > maxValue) {
                                    maxValue = kurongtemp;
                                }
                            }
                        }
                        adapter.setData(result.results, maxValue);
                        dimssDialog();
                    } else {
                        dimssDialog();
                        Utils.toast(getActivity(), "服务器异常" + result.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    dimssDialog();
                    Utils.toast(getActivity(), "服务器异常,返回为空", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(getActivity(), "服务器异常" , Toast.LENGTH_SHORT);
            }
        });
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
