package com.htmitech.proxy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.htmitech.MyView.EmptyLayout;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.OAConText;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.pop.LoginAlertDialog;
import com.htmitech.proxy.adapter.ChilDaccountAdapter;
import com.htmitech.proxy.doman.EmpCisInfo;
import com.htmitech.proxy.doman.EmpCisInfoResult;
import com.htmitech.proxy.doman.EmpCisparameter;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.proxy.util.ZTActivityUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * 子账号激活入口
 *
 * @author joe
 */
public class ChilDaccountActivity extends cn.feng.skin.manager.base.BaseActivity implements
        ObserverCallBackType {
    private static final String TAG = "ChilDaccountActivity";

    private ImageView ibn_fn5_back;
    private EmpCisparameter mEmpCisparameter;
    private String getEmpCisListUrl = "";
    private static final String MHTTPTYPE = "empcislist";
    private ListView lv_childaccount;
    private ChilDaccountAdapter adapter;
    private EmpCisInfoResult mEmpCisInfoResult;
    private List<EmpCisInfo> empCisInfoList;
    private Gson mGson = new Gson();
    private String jsonToString;

    private LoginAlertDialog loginAlertDialog;

    private EmptyLayout mEmptyLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zt_activity_childaccount);
        initView();
        initData();

    }

    public void initView() {
        ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
        lv_childaccount = (ListView) findViewById(R.id.lv_childaccount);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.emptyLayout);
        ibn_fn5_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lv_childaccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EmpCisInfo mEmpCisInfo = empCisInfoList.get(i);
                if (mEmpCisInfo.getStatus_msg().equals("已激活")) {
                    return;
                } else {

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("titleName", mEmpCisInfo.getCis_name());
                    params.put("Cis_id", mEmpCisInfo.getCis_id());
                    ZTActivityUnit.switchTo(ChilDaccountActivity.this, ChilDaccountYZActivity.class, params);
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (empCisInfoList != null) {
            empCisInfoList.clear();
        }
        initData();
    }

    public void initData() {
        getEmpCisListUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.ObjecToveSyscontrollerGetEmpCisList;
        mEmpCisparameter = new EmpCisparameter();
        mEmpCisparameter.setCis_id("");
        mEmpCisparameter.setPortal_id(BookInit.getInstance().getPortalId());
        mEmpCisparameter.setUser_id(OAConText.getInstance(ChilDaccountActivity.this).UserID);
        jsonToString = mGson.toJson(mEmpCisparameter);
        AnsynHttpRequest.requestByPostWithToken(this, jsonToString, getEmpCisListUrl, CHTTP.POSTWITHTOKEN, this, MHTTPTYPE, LogManagerEnum.GGENERAL.getFunctionCode());
    }


    @Override
    public void success(String requestValue, int type, String requestName) {

        requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getEmpCisListUrl, jsonToString, this, requestName, LogManagerEnum.GGENERAL.getFunctionCode());
        if (requestValue != null && !requestValue.equals("")) {
            if (requestName.equals(MHTTPTYPE)) {
                mEmpCisInfoResult = mGson.fromJson(requestValue.toString(), EmpCisInfoResult.class);
                empCisInfoList = mEmpCisInfoResult.getResult();
                if (empCisInfoList != null) {
                    mEmptyLayout.hide();
                    adapter = new ChilDaccountAdapter(empCisInfoList, ChilDaccountActivity.this);
                    lv_childaccount.setAdapter(adapter);
                }
            }
        } else {
            Log.d(TAG, "success: " + requestValue);
            return;
        }

    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        mEmptyLayout.showError();
    }

    @Override
    public void notNetwork() {
        mEmptyLayout.setNoWifiButtonClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnsynHttpRequest.requestByPostWithToken(ChilDaccountActivity.this, jsonToString, getEmpCisListUrl, CHTTP.POSTWITHTOKEN, ChilDaccountActivity.this, MHTTPTYPE, LogManagerEnum.GGENERAL.getFunctionCode());
            }
        });
        mEmptyLayout.showNowifi();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
