package com.htmitech.ztcustom.zt.fragment;

import android.content.Context;
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
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.domain.generalinfomation.NewFindInjuryRequest;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.DataTimeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import htmitech.com.componentlibrary.unit.Utils;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;


/**
 * 新发重伤
 */
public class NewFindInjuryFragment extends BaseFragment implements View.OnClickListener {


    private Button buttonZD;//重伤按钮
    private Button buttonZS;//折断按钮
    private Button buttonDay;
    private Button buttonMonth;
    private Button buttonYear;
    private FromLayout formLayout;
    private HTMRDataTable mHTMRDataTable;
    private HTMRDataTable mHTMRDataTableTemp;//缓存用
    private ImageView imageViewReturn;
    private NewFindInjuryRequest entity;
    private Calendar calendar;
    private String currSelectDate = "day";
    private String userID;
    private TextView textViewTitle;
    private boolean hasGet = false;

    public NewFindInjuryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_find_injury, container, false);
    }

    @Override
    protected void initView() {
        buttonZD = (Button) getView().findViewById(R.id.bt_new_find_injury_zd);
        buttonZD.setOnClickListener(this);
        buttonZS = (Button) getView().findViewById(R.id.bt_new_find_injury_zs);
        buttonZS.setOnClickListener(this);
        buttonDay = (Button) getView().findViewById(R.id.bt_new_find_injury_day);
        buttonDay.setOnClickListener(this);
        buttonMonth = (Button) getView().findViewById(R.id.bt_new_find_injury_month);
        buttonMonth.setOnClickListener(this);
        buttonYear = (Button) getView().findViewById(R.id.bt_new_find_injury_year);
        buttonYear.setOnClickListener(this);
        formLayout = (FromLayout) getView().findViewById(R.id.fromlayout_new_find_injury);
        imageViewReturn = (ImageView) getView().findViewById(R.id.iv_new_find_injury_return_previous);
        imageViewReturn.setOnClickListener(this);
        imageViewReturn.setVisibility(View.GONE);
        textViewTitle = (TextView) getView().findViewById(R.id.tv_new_find_injury_midtitle);
    }

    @Override
    protected void initData() {
//        userID = ZTCustomInit.get().getmCache().currentUserId;
        userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
        entity = new NewFindInjuryRequest();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        entity.datebegin = sdf.format(d);//默认是当日
        entity.dateend = sdf.format(d);
        entity.userid = userID;
        entity.defectlevel = "ZD";
        if (null != ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType && ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType.equals("TZ")) {//铁总用户固定传入000
            entity.parentorgcode = "000";
            entity.parentorgid = "0";
        } else {
            entity.parentorgcode = "";   //其他用户传入登录后拿到的orgcode
//            entity.parentorgid = ZTCustomInit.get().getmCache().getmListDetails().OrgId;
            entity.parentorgid = ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
        }

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
                        //判断当前用户是不是铁总用户
                        if (!ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptType.equals("TZ")) {
                            return;
                        }
                        if (hasGet) {
                            return;
                        }
                        hasGet = true;
                        imageViewReturn.setVisibility(View.VISIBLE);
                        Log.e("STRING", arrayList.get(0).value);
                        if (entity == null) {
                            entity = new NewFindInjuryRequest();
                        }
                        entity.parentorgcode = arrayList.get(0).value;
                        entity.parentorgid = "0";
                        getData();
                    }
                });
            }
        });
    }

    private void getData() {

        showProgressDialog(getActivity());
        AnsynHttpRequest.request(getActivity(), entity, ContantValues.NEWFINDINJURY, CHTTP.POST, new ObserverCallBack() {

            @Override
            public void success(final String requestValue) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            initHTMRDataTable(readRawFile(), requestValue);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->");
                                        long upMillis = System.currentTimeMillis();
                                        if (null != formLayout && mHTMRDataTable != null) {
                                            formLayout.initFromLayout(mHTMRDataTable);
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


    @Override
    public boolean onBackPressed() {
        return false;
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
    public void onClick(View v) {
        if (v.getId() == R.id.bt_new_find_injury_zd) {
            if (entity != null && "ZD".equals(entity.defectlevel)) {
                return;
            }
            textViewTitle.setText("新发折断数量");
            buttonZD.setBackgroundResource(R.drawable.newfindinjury_pressed_left_shape);
            buttonZS.setBackgroundResource(R.drawable.newfindinjury_unpressed_right_shape);
            buttonZD.setTextColor(Color.parseColor("#FFFAFAFA"));
            buttonZS.setTextColor(Color.parseColor("#FF3A9BFC"));
            if (entity == null) {
                entity = new NewFindInjuryRequest();
            }
            entity.defectlevel = "ZD";
            getData();
        } else if (v.getId() == R.id.bt_new_find_injury_zs) {
            if (entity != null && "ZS".equals(entity.defectlevel)) {
                return;
            }
            textViewTitle.setText("新发重伤数量");
            buttonZD.setBackgroundResource(R.drawable.newfindinjury_unpressed_left_shape);
            buttonZS.setBackgroundResource(R.drawable.newfindinjury_pressed_right_shape);
            buttonZD.setTextColor(Color.parseColor("#FF3A9BFC"));
            buttonZS.setTextColor(Color.parseColor("#FFFAFAFA"));
            if (entity == null) {
                entity = new NewFindInjuryRequest();
            }
            entity.defectlevel = "ZS";
            getData();
        } else if (v.getId() == R.id.bt_new_find_injury_day) {
            if ("day".equals(currSelectDate)) {
                return;
            }
            currSelectDate = "day";
            initDayMonthYear();
            buttonDay.setBackgroundResource(R.drawable.newfindinjury_day_button_pressed);
            if (entity == null) {
                entity = new NewFindInjuryRequest();
            }
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            entity.datebegin = sdf.format(d);
            entity.dateend = sdf.format(d);
            getData();
        } else if (v.getId() == R.id.bt_new_find_injury_month) {
            if ("month".equals(currSelectDate)) {
                return;
            }
            currSelectDate = "month";
            initDayMonthYear();
            buttonMonth.setBackgroundResource(R.drawable.newfindinjury_day_button_pressed);
            if (entity == null) {
                entity = new NewFindInjuryRequest();
            }
            entity.datebegin = DataTimeUtil.formatDate(DataTimeUtil.getCurrentMonthFrist());
            entity.dateend = DataTimeUtil.formatDate(DataTimeUtil.getCurrentMonthLast());
            getData();
        } else if (v.getId() == R.id.bt_new_find_injury_year) {
            if ("year".equals(currSelectDate)) {
                return;
            }
            currSelectDate = "year";
            initDayMonthYear();
            buttonYear.setBackgroundResource(R.drawable.newfindinjury_day_button_pressed);
            if (entity == null) {
                entity = new NewFindInjuryRequest();
            }
            entity.datebegin = DataTimeUtil.formatDate(DataTimeUtil.getCurrYearFirst());
            entity.dateend = DataTimeUtil.formatDate(DataTimeUtil.getCurrYearLast());
            getData();
        } else if (v.getId() == R.id.iv_new_find_injury_return_previous) {
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

    public void initDayMonthYear() {
        buttonDay.setBackgroundResource(R.drawable.newfindinjury_day_button_unpressed);
        buttonMonth.setBackgroundResource(R.drawable.newfindinjury_day_button_unpressed);
        buttonYear.setBackgroundResource(R.drawable.newfindinjury_day_button_unpressed);
    }


    /**
     * 初始化定义
     */
    public void initHTMRDataTable(String titleJson, String bodyJson) {
        if (TextUtils.isEmpty(titleJson) || TextUtils.isEmpty(bodyJson)) {
            return;
        }
        JSONArray data;
        JSONObject jsonObjectBody = JSON.parseObject(bodyJson);
        JSONObject jsonObject = JSON.parseObject(titleJson);
        data = jsonObjectBody.getJSONArray("datas");
        jsonObject.put("data", data);
        String jsonAll = jsonObject.toJSONString();
        mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);

    }

    private String readRawFile() {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(R.raw.xfzs);
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