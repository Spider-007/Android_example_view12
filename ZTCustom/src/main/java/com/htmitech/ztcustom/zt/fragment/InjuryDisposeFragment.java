package com.htmitech.ztcustom.zt.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.chinarailway.InjuryDisposeListActivity;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.generalinfomation.InjuryDisposeRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.Utils;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;

/**
 * 重伤处置
 */
public class InjuryDisposeFragment extends BaseFragment implements View.OnClickListener {

    private Button buttonWCZ;//未处置按钮
    private Button buttonWXH;//未销号按钮
    private ImageView imageViewReturn;
    private FromLayout formLayout;
    private HTMRDataTable mHTMRDataTable;
    private InjuryDisposeRequest entity;
    private String userID;
    private boolean hasGet = false;
    private TextView textViewFormTitle;

    public InjuryDisposeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        buttonWCZ = (Button) getView().findViewById(R.id.bt_injury_dispose_wcz);
        buttonWCZ.setOnClickListener(this);
        buttonWXH = (Button) getView().findViewById(R.id.bt_injury_dispose_wxh);
        buttonWXH.setOnClickListener(this);
        formLayout = (FromLayout) getView().findViewById(R.id.fromlayout_injury_dispose);
        imageViewReturn = (ImageView) getView().findViewById(R.id.iv_injury_dispose_return_previous);
        imageViewReturn.setOnClickListener(this);
        imageViewReturn.setVisibility(View.GONE);
        textViewFormTitle = (TextView) getView().findViewById(R.id.tv_injury_dispose_midtitle);
    }

    @Override
    protected void initData() {
        userID = ZTCustomInit.get().getmCache().currentUserId;
        entity = new InjuryDisposeRequest();
        if (null != ZTCustomInit.get().getmCache() &&
                null != ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId &&
                null !=  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType &&
                ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType.equals("TZ")) {//铁总用户固定传入000
            entity.parentorgcode = "000";
            entity.parentorgid = "0";
        } else {
            entity.parentorgcode = "";   //其他用户传入登录后拿到的orgcode
            entity.parentorgid =  ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
        }
        entity.undealtype = "WCZ";
        entity.userid = userID;
        initControl();
        getData();
    }


    private void initControl() {

        formLayout.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).isCheck) {
                                if (i == 1) {//这种是往组织机构里面跳转
                                    if (! ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType.equals("TZ")) {
                                        return;
                                    }
                                    if (hasGet) {
                                        return;
                                    }
                                    hasGet = true;
                                    imageViewReturn.setVisibility(View.VISIBLE);
                                    if (entity == null) {
                                        entity = new InjuryDisposeRequest();
                                    }
                                    entity.parentorgcode = arrayList.get(0).value;
                                    entity.parentorgid = "0";
                                    getData();
                                } else {//这种是设备类型
                                    if(arrayList.get(i).key.contains("gg")){
                                        startNewActivity(arrayList, "GG");
                                    }else if(arrayList.get(i).key.contains("dc")){
                                        startNewActivity(arrayList, "DC");
                                    }else if(arrayList.get(i).key.contains("hf")){
                                        startNewActivity(arrayList, "HF");
                                    }

                                }
                            }
                        }
                    }
                });

//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        new InjuryDisposeItemClickAlertDialog(getActivity()).builder().setOrgName(arrayList.get(1).value).
//                                setNegativeButton("", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//
//                                    }
//                                }).
//                                setPositiveButton("", new OnDialogInjuryDisposeItemClickListener() {
//                                    @Override
//                                    public void callBack(String chooseString) {
//
//                                        if (chooseString.equals("org")) {
//                                            if (!app.getmCache().getmListDetails().cisDeptType.equals("TZ")) {
//                                                return;
//                                            }
//                                            if (hasGet) {
//                                                return;
//                                            }
//                                            hasGet = true;
//                                            imageViewReturn.setVisibility(View.VISIBLE);
//                                            if (entity == null) {
//                                                entity = new InjuryDisposeRequest();
//                                            }
//                                            entity.parentorgcode = arrayList.get(0).value;
//                                            entity.parentorgid = "0";
//                                            getData();
//                                        } else if (chooseString.equals("gg")) {
//                                            startNewActivity(arrayList, "GG");
//                                        } else if (chooseString.equals("dc")) {
//                                            startNewActivity(arrayList, "DC");
//                                        } else if (chooseString.equals("hf")) {
//                                            startNewActivity(arrayList, "HF");
//                                        }
//
//                                    }
//                                }).show();
//                    }
//                });
            }
        });
    }

    public void startNewActivity(ArrayList<Table> arrayList, String sblx) {
        Intent intent = new Intent(getActivity(), InjuryDisposeListActivity.class);
        intent.putExtra("undealtype", entity.undealtype);
        intent.putExtra("sblx", sblx);
        intent.putExtra("orgcode", arrayList.get(0).value);
        intent.putExtra("title", textViewFormTitle.getText() + "（" + arrayList.get(1).value + "）");
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_injury_dispose, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    private void getData() {

        showProgressDialog(getActivity());
        AnsynHttpRequest.request(getActivity(), entity, ContantValues.INJURYDISPOSE, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initHTMRDataTable(readRawFile(), requestValue);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                    long upMillis = System.currentTimeMillis();
//                                formLayout.setViewHeight(46);
                                    if(null != formLayout && null != mHTMRDataTable)
                                    formLayout.initFromLayout(mHTMRDataTable);
                                    long currentTimeMillis = System.currentTimeMillis();
                                    Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                                    dimssDialog();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    dimssDialog();
                                }
                            }
                        });
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


    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.bt_injury_dispose_wcz ){
            if (entity != null && "WCZ".equals(entity.undealtype)) {
                return;
            }
            textViewFormTitle.setText("24小时内未处置重伤");
            buttonWCZ.setBackgroundResource(R.drawable.newfindinjury_pressed_left_shape);
            buttonWXH.setBackgroundResource(R.drawable.newfindinjury_unpressed_right_shape);
            buttonWCZ.setTextColor(Color.parseColor("#FFFAFAFA"));
            buttonWXH.setTextColor(Color.parseColor("#FF3A9BFC"));
            if (entity == null) {
                entity = new InjuryDisposeRequest();
            }
            entity.undealtype = "WCZ";
            getData();
        }else if(v.getId() ==R.id.bt_injury_dispose_wxh){
            if (entity != null && "WXH".equals(entity.undealtype)) {
                return;
            }
            textViewFormTitle.setText("24小时内未销号重伤");
            buttonWCZ.setBackgroundResource(R.drawable.newfindinjury_unpressed_left_shape);
            buttonWXH.setBackgroundResource(R.drawable.newfindinjury_pressed_right_shape);
            buttonWCZ.setTextColor(Color.parseColor("#FF3A9BFC"));
            buttonWXH.setTextColor(Color.parseColor("#FFFAFAFA"));
            if (entity == null) {
                entity = new InjuryDisposeRequest();
            }
            entity.undealtype = "WXH";
            getData();
        }else if(v.getId() ==R.id.iv_injury_dispose_return_previous){
            imageViewReturn.setVisibility(View.GONE);
            hasGet = false;
            if (ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType.equals("TZ")) {//铁总用户固定传入000
                entity.parentorgcode = "000";
                entity.parentorgid = "0";
            } else {
                entity.parentorgcode = "";
                entity.parentorgid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
            }
            getData();
        }
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
            data = jsonObjectBody.getJSONArray("datas");
            jsonObject.put("data", data);
            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);
        } catch (Exception e) {

        }

    }

    private String readRawFile() {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(R.raw.zscz);
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

}
