package com.htmitech.ztcustom.zt.fragment.weldingbasefragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.WeldingBaseDetailFromLayoutRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;

import java.io.IOException;
import java.io.InputStream;

import htmitech.com.componentlibrary.unit.Utils;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.myView.FromLayout;

/**
 * 焊轨基地详情——项目
 */
public class WeldingBaseXMFragment extends BaseFragment {

    private TextView textViewTitle;
    private FromLayout fromLayout;
    private String type;
    private String id;
    private String name;
    private HTMRDataTable mHTMRDataTable;
    private String url = "";
    private int rawID;

    public WeldingBaseXMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welding_base_xm, container, false);
    }


    @Override
    protected void initView() {
        textViewTitle = (TextView) getView().findViewById(R.id.tv_welding_base_xm_title);
        fromLayout = (FromLayout) getView().findViewById(R.id.fromlayout_welding_base_xm);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        id = bundle.getString("id");
        name = bundle.getString("hanguijidi");
        textViewTitle.setText(name + "焊轨基地");
        if ("DHG".equals(type)) {
            url = ContantValues.WELDINGBASEDETAILDHGXM;
            rawID = R.raw.hgjddhgxm;
        } else if ("CPG".equals(type)) {
            url = ContantValues.WELDINGBASEDETAILCPGXM;
            rawID = R.raw.hgjdcpgxm;
        }
        getWeldingBaseDetailData();
    }

    public void getWeldingBaseDetailData() {

        showProgressDialog(getActivity());
        WeldingBaseDetailFromLayoutRequest request = new WeldingBaseDetailFromLayoutRequest();
        request.userid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        request.hanguijidiid = id;
        AnsynHttpRequest.request(getActivity(), request, url, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(rawID), requestValue);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (mHTMRDataTable != null) {
                                            fromLayout.initFromLayout(mHTMRDataTable);
                                        }
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                        dimssDialog();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dimssDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            dimssDialog();
                        }
                    }
                }).start();

            }

            @Override
            public void notNetwork() {
                // TODO Auto-generated method stub
                Utils.toast(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
                dimssDialog();
            }

            @Override
            public void fail(String exceptionMessage) {
                // TODO Auto-generated method stub
                dimssDialog();
                Utils.toast(getActivity(), "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
            }

        });
    }


    /**
     * 初始化定义
     */
    public void initHTMRDataTable(String titleJson, String bodyJson) {
        if (TextUtils.isEmpty(titleJson) || TextUtils.isEmpty(bodyJson)) {
            return;
        }
        try {
            JSONArray data;
            JSONObject jsonObjectBody = JSON.parseObject(bodyJson);
            JSONObject jsonObject = JSON.parseObject(titleJson);
            data = jsonObjectBody.getJSONArray("results");
            jsonObject.put("data", data);
            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);
        } catch (Exception e) {

        }

    }

    private String readRawFile(int rawID) {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(rawID);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            content = new String(buffer);
            return content;
        } catch (IOException e) {
            Log.e("write file", e.toString());
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("close file", e.toString());
                }
            }
        }
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
