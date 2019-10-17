package com.htmitech.ztcustom.zt.fragment.plannedcashratefragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableFourBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableFourRequest;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableThreeBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableThreeRequest;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableTwoBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateTableTwoRequest;
import com.htmitech.ztcustom.zt.event.PlanRateDataChangeEvent;
import com.htmitech.ztcustom.zt.event.PlannedRateTypeCodeChangeEvent;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnPlannedRateFragmentCallbackListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.Utils;
import mobilereport.com.chatkit.domain.HTMRDataTable;
import mobilereport.com.chatkit.domain.Table;
import mobilereport.com.chatkit.listener.IOnItemClickListener;
import mobilereport.com.chatkit.myView.FromLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPlannedRateFragmentCallbackListener} interface
 * to handle interaction events.
 */
public class PlannedRateTableFragment extends BaseFragment implements View.OnClickListener {

    private OnPlannedRateFragmentCallbackListener mListener;

    private LinearLayout bt_dxl;
    private LinearLayout bt_dy;
    private LinearLayout bt_lj;
    private LinearLayout bt_gc;
    private ImageView cash_rate_img;
    private ImageView same_montch_img;
    private ImageView cumulative_img;
    private ImageView steel_mill_img;
    private LinearLayout ll_bottom;
    private FromLayout formLayout_pz_dx;
    private FromLayout formLayout_dy_fh;
    private FromLayout formLayout_lj_fh;
    private FromLayout formLayout_gc_fh;
    private FromLayout currentFormLayout;
    private HTMRDataTable mHTMRDataTable;
    private TextView textViewTitle;

    private String year;
    private String month;
    private String projectID;
    private String typecode;
    private String gccode;
    private String usedircode;
    private String title;
    private String gcname;

    private Context mContext;

    private PlannedRateTableTwoRequest tableTwoRequest;
    private PlannedRateTableThreeRequest tableThreeRequest;
    private PlannedRateTableFourRequest tableFourRequest;

    private int rawResource;
    private String selectColor = "#037FFD";
    private String noselectColor = "#FFFFFF";


    public PlannedRateTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_planned_rate_table, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    protected void initView() {
        bt_dxl = (LinearLayout) getView().findViewById(R.id.bt_planned_rate_table_dxl);
        bt_dy = (LinearLayout) getView().findViewById(R.id.bt_planned_rate_table_dy);
        bt_lj = (LinearLayout) getView().findViewById(R.id.bt_planned_rate_table_lj);
        bt_gc = (LinearLayout) getView().findViewById(R.id.bt_planned_rate_table_gc);
        cash_rate_img = (ImageView) getView().findViewById(R.id.cash_rate_img);
        same_montch_img = (ImageView) getView().findViewById(R.id.same_montch_img);
        cumulative_img = (ImageView) getView().findViewById(R.id.cumulative_img);
        steel_mill_img = (ImageView) getView().findViewById(R.id.steel_mill_img);
        ll_bottom = (LinearLayout) getView().findViewById(R.id.ll_planned_rate_table_bottom);
        bt_dxl.setOnClickListener(this);
        bt_dy.setOnClickListener(this);
        bt_lj.setOnClickListener(this);
        bt_gc.setOnClickListener(this);
        textViewTitle = (TextView) getView().findViewById(R.id.tv_planned_rate_table_title);
        formLayout_pz_dx = (FromLayout) getView().findViewById(R.id.fromlayout_planned_rate_table_pz_dx);
        formLayout_dy_fh = (FromLayout) getView().findViewById(R.id.fromlayout_planned_rate_table_dy_fh);
        formLayout_lj_fh = (FromLayout) getView().findViewById(R.id.fromlayout_planned_rate_table_lj_fh);
        formLayout_gc_fh = (FromLayout) getView().findViewById(R.id.fromlayout_planned_rate_table_gc_fh);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            year = bundle.getString(PlannedCashRateConfig.YEAR) != null ? bundle.getString(PlannedCashRateConfig.YEAR) : "";
            month = bundle.getString(PlannedCashRateConfig.MONTH) != null ? bundle.getString(PlannedCashRateConfig.MONTH) : "";
            projectID = bundle.getString(PlannedCashRateConfig.PROJECTID) != null ? bundle.getString(PlannedCashRateConfig.PROJECTID) : "";
            typecode = bundle.getString(PlannedCashRateConfig.TYPECODE) != null ? bundle.getString(PlannedCashRateConfig.TYPECODE) : "";
            gccode = bundle.getString(PlannedCashRateConfig.GCCODE) != null ? bundle.getString(PlannedCashRateConfig.GCCODE) : "";
            usedircode = bundle.getString(PlannedCashRateConfig.USEDIRCODE) != null ? bundle.getString(PlannedCashRateConfig.USEDIRCODE) : "";
            title = bundle.getString(PlannedCashRateConfig.TITLE) != null ? bundle.getString(PlannedCashRateConfig.TITLE) : "";
            gcname = bundle.getString(PlannedCashRateConfig.GCNAME) != null ? bundle.getString(PlannedCashRateConfig.GCNAME) : "";
//            currentfragment = bundle.getString(PlannedCashRateConfig.CURRENTFRAGMENT) != null ? bundle.getString(PlannedCashRateConfig.CURRENTFRAGMENT) : "";
            Log.e("heyang", year + month + " " + projectID + " " + typecode + " ");
        }
        resetFormLayoutGone();
        if (this.getTag().contains("TWO")) {
            ll_bottom.setVisibility(View.GONE);
            rawResource = R.raw.pzdx_two;
            formLayout_pz_dx.setVisibility(View.VISIBLE);
            currentFormLayout = formLayout_pz_dx;
            tableTwoRequest = new PlannedRateTableTwoRequest();
            title = PlannedCashRateConfig.PZ_DXL_TITLE;
            getDataTwoTable();
        } else if (this.getTag().contains("THREE")) {
            ll_bottom.setVisibility(View.VISIBLE);
            tableThreeRequest = new PlannedRateTableThreeRequest();
            if (this.getTag().equals(PlannedCashRateConfig.PZ_DX_THREE_FRAGMENT_TAG)) {
                rawResource = R.raw.pzdx_three;
                formLayout_pz_dx.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_pz_dx;
                title = gcname + PlannedCashRateConfig.PZ_DXL_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.DY_PZ_FH_THREE_FRAGMENT_TAG)) {
                rawResource = R.raw.dypz_three;
                formLayout_dy_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_dy_fh;
                title = gcname + PlannedCashRateConfig.DY_FH_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.LJ_PZ_FH_THREE_FRAGMENT_TAG)) {
                rawResource = R.raw.ljpz_three;
                formLayout_lj_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_lj_fh;
                title = gcname + PlannedCashRateConfig.LJ_FH_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.GC_SJ_FH_THREE_FRAGMENT_TAG)) {
                rawResource = R.raw.gcsj_three;
                formLayout_gc_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_gc_fh;
                title = gcname + PlannedCashRateConfig.GC_SJ_FH_TITLE;
            }
            getDataThreeTable();
        } else if (this.getTag().contains("FOUR")) {
            ll_bottom.setVisibility(View.VISIBLE);
            tableFourRequest = new PlannedRateTableFourRequest();
            if (this.getTag().equals(PlannedCashRateConfig.PZ_DX_FOUR_FRAGMENT_TAG)) {
                rawResource = R.raw.pzdx_four;
                formLayout_pz_dx.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_pz_dx;
                title = gcname + PlannedCashRateConfig.PZ_DXL_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.DY_PZ_FH_FOUR_FRAGMENT_TAG)) {
                rawResource = R.raw.dypz_four;
                formLayout_dy_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_dy_fh;
                title = gcname + PlannedCashRateConfig.DY_FH_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.LJ_PZ_FH_FOUR_FRAGMENT_TAG)) {
                rawResource = R.raw.ljpz_four;
                formLayout_lj_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_lj_fh;
                title = gcname + PlannedCashRateConfig.LJ_FH_TITLE;
            } else if (this.getTag().equals(PlannedCashRateConfig.GC_SJ_FH_FOUR_FRAGMENT_TAG)) {
                rawResource = R.raw.gcsj_four;
                formLayout_gc_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_gc_fh;
                title = gcname + PlannedCashRateConfig.GC_SJ_FH_TITLE;
            }
            getDataFourTable();
        }
        resetButtonColor();
        textViewTitle.setText(title);
        initControl();
    }

    private void initControl() {

        currentFormLayout.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onItemClick(final ArrayList<Table> arrayList) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).isCheck) {
                                Log.e("heyang", "currentFormLayout_click" + "get(0).value: " + arrayList.get(0).value);
                                if (getTag().contains("TWO")) {
                                    mListener.onItemClickListener(getTag(), PlannedCashRateConfig.PZ_DX_THREE_FRAGMENT_TAG, typecode, arrayList.get(0).value, null, arrayList.get(1).value);
                                } else if (getTag().contains("THREE")) {
                                    String goToFragment = "";
                                    if (typecode.equals(PlannedCashRateConfig.PZ_DX)) {
                                        goToFragment = PlannedCashRateConfig.PZ_DX_FOUR_FRAGMENT_TAG;
                                    } else if (typecode.equals(PlannedCashRateConfig.DY_PZ_FH)) {
                                        goToFragment = PlannedCashRateConfig.DY_PZ_FH_FOUR_FRAGMENT_TAG;
                                    } else if (typecode.equals(PlannedCashRateConfig.LJ_PZ_FH)) {
                                        goToFragment = PlannedCashRateConfig.LJ_PZ_FH_FOUR_FRAGMENT_TAG;
                                    } else if (typecode.equals(PlannedCashRateConfig.GC_SJ_FH)) {
                                        goToFragment = PlannedCashRateConfig.GC_SJ_FH_FOUR_FRAGMENT_TAG;
                                    }
                                    mListener.onItemClickListener(getTag(), goToFragment, typecode, gccode, arrayList.get(0).value, gcname);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnPlannedRateFragmentCallbackListener) {
            mListener = (OnPlannedRateFragmentCallbackListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPlannedRateFragmentCallbackListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (!getTag().contains("TWO")) {
            String typecodeTemp = typecode;
            resetFormLayoutGone();
            if (v.getId() == R.id.bt_planned_rate_table_dxl) {
                typecode = PlannedCashRateConfig.PZ_DX;
                rawResource = getTag().contains("THREE") ? R.raw.pzdx_three : R.raw.pzdx_four;
                formLayout_pz_dx.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_pz_dx;
                title = gcname + PlannedCashRateConfig.PZ_DXL_TITLE;
            } else if (v.getId() == R.id.bt_planned_rate_table_dy) {
                typecode = PlannedCashRateConfig.DY_PZ_FH;
                rawResource = getTag().contains("THREE") ? R.raw.dypz_three : R.raw.dypz_four;
                formLayout_dy_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_dy_fh;
                title = gcname + PlannedCashRateConfig.DY_FH_TITLE;
            } else if (v.getId() == R.id.bt_planned_rate_table_lj) {
                typecode = PlannedCashRateConfig.LJ_PZ_FH;
                rawResource = getTag().contains("THREE") ? R.raw.ljpz_three : R.raw.ljpz_four;
                formLayout_lj_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_lj_fh;
                title = gcname + PlannedCashRateConfig.LJ_FH_TITLE;
            } else if (v.getId() == R.id.bt_planned_rate_table_gc) {
                typecode = PlannedCashRateConfig.GC_SJ_FH;
                rawResource = getTag().contains("THREE") ? R.raw.gcsj_three : R.raw.gcsj_four;
                formLayout_gc_fh.setVisibility(View.VISIBLE);
                currentFormLayout = formLayout_gc_fh;
                title = gcname + PlannedCashRateConfig.GC_SJ_FH_TITLE;
            }
            if (typecodeTemp.equals(typecode)) {
                return;
            }
            resetButtonColor();
            initControl();
            textViewTitle.setText(title);
            if (getTag().contains("THREE")) {
                getDataThreeTable();
            } else if (getTag().contains("FOUR")) {
                PlannedRateTypeCodeChangeEvent event = new PlannedRateTypeCodeChangeEvent();
                event.typeCode = typecode;
                EventBus.getDefault().post(event);
                getDataFourTable();
            }
        }
    }

    private void getDataTwoTable() {
        mListener.onRequestDataStart();
        tableTwoRequest.projectid = projectID;
        tableTwoRequest.querydate = year + month;
        tableTwoRequest.typecode = typecode;
        tableTwoRequest.userid = OAConText.getInstance(getActivity()).UserID;

        AnsynHttpRequest.request(mContext, tableTwoRequest, CHTTP.PLANED_CASH_RATE_TWO_TABLE, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        final PlannedRateTableTwoBean tableTwoBean = FastJsonUtils.getPerson(successMessage, PlannedRateTableTwoBean.class);
                        Log.d("heyang", tableTwoBean.code + "");
                        if (tableTwoBean.code == 200) {
                            //得到数据后更新视图
                            updateViewByData(JSON.toJSONString(tableTwoBean.result));
                        } else {
                            if (tableTwoBean.code == 400) {
                                Utils.toast(mContext, "暂无数据", Toast.LENGTH_SHORT);
                            } else {
                                Utils.toast(mContext, "请求失败", Toast.LENGTH_SHORT);
                            }
                        }
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        Utils.toast(mContext, "请检查网络连接", Toast.LENGTH_SHORT);
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        mListener.onRequestDataFinish();
                        Utils.toast(mContext, "请求错误" + exceptionMessage, Toast.LENGTH_SHORT);
                    }
                });
    }

    private void getDataThreeTable() {
        mListener.onRequestDataStart();
        tableThreeRequest.gccode = gccode;
        tableThreeRequest.projectid = projectID;
        tableThreeRequest.querydate = year + month;
        tableThreeRequest.typecode = typecode;
        tableThreeRequest.userid = OAConText.getInstance(getActivity()).UserID;
        AnsynHttpRequest.request(mContext, tableThreeRequest, CHTTP.PLANED_CASH_RATE_THREE_TABLE, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        final PlannedRateTableThreeBean tableThreeBean = FastJsonUtils.getPerson(successMessage, PlannedRateTableThreeBean.class);
                        Log.d("heyang", tableThreeBean.code + "");
                        if (tableThreeBean.code == 200) {
                            //得到数据后更新视图
                            updateViewByData(JSON.toJSONString(tableThreeBean.result));
                        } else {
                            if (tableThreeBean.code == 400) {
                                Utils.toast(mContext, "暂无数据", Toast.LENGTH_SHORT);
                            } else {
                                Utils.toast(mContext, "请求失败", Toast.LENGTH_SHORT);
                            }
                            mListener.onRequestDataFinish();
                        }

                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        Utils.toast(mContext, "请检查网络连接", Toast.LENGTH_SHORT);
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        mListener.onRequestDataFinish();
                        Utils.toast(mContext, "请求错误" + exceptionMessage, Toast.LENGTH_SHORT);
                    }
                });
    }

    private void getDataFourTable() {
        mListener.onRequestDataStart();
        tableFourRequest.gccode = gccode;
        tableFourRequest.projectid = projectID;
        tableFourRequest.querydate = year + month;
        tableFourRequest.typecode = typecode;
        tableFourRequest.usedircode = usedircode;
        tableFourRequest.userid = OAConText.getInstance(getActivity()).UserID;

        AnsynHttpRequest.request(mContext, tableFourRequest, CHTTP.PLANED_CASH_RATE_FOUR_TABLE, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        final PlannedRateTableFourBean tableFourBean = FastJsonUtils.getPerson(successMessage, PlannedRateTableFourBean.class);
                        Log.d("heyang", tableFourBean.code + "");
                        if (tableFourBean.code == 200) {
                            //得到数据后更新视图
                            updateViewByData(JSON.toJSONString(tableFourBean.result));
                        } else {
                            if (tableFourBean.code == 400) {
                                Utils.toast(mContext, "暂无数据", Toast.LENGTH_SHORT);
                            } else {
                                Utils.toast(mContext, "请求失败", Toast.LENGTH_SHORT);
                            }
                        }
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        Utils.toast(mContext, "请检查网络连接", Toast.LENGTH_SHORT);
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        mListener.onRequestDataFinish();
                        Utils.toast(mContext, "请求错误" + exceptionMessage, Toast.LENGTH_SHORT);
                    }
                });
    }


    public void updateViewByData(final String requestValue) {
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
                            if (null != currentFormLayout && null != mHTMRDataTable) {
                                currentFormLayout.initFromLayout(mHTMRDataTable);
                            }
                            long currentTimeMillis = System.currentTimeMillis();
                            Log.d("WorkReportSitFragment", "WorkReportSitFragment------------------->" + (currentTimeMillis - upMillis));
                            mListener.onRequestDataFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mListener.onRequestDataFinish();
                        }
                    }
                });
            }
        }).start();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(PlanRateDataChangeEvent planRateDataChangeEvent) {
        if (planRateDataChangeEvent != null) {
            month = !TextUtils.isEmpty(planRateDataChangeEvent.month) ? planRateDataChangeEvent.month : month;
            year = !TextUtils.isEmpty(planRateDataChangeEvent.year) ? planRateDataChangeEvent.year : year;
            projectID = planRateDataChangeEvent.projectID != null ? planRateDataChangeEvent.projectID : projectID;
            Log.e("heyang", "month: " + month + "year: " + year + "projectID: " + projectID);
            if (this.getTag().contains("TWO")) {
                getDataTwoTable();
            } else if (this.getTag().contains("THREE")) {
                getDataThreeTable();
            } else if (this.getTag().contains("FOUR")) {
                getDataFourTable();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTypeCodeChangeEvent(PlannedRateTypeCodeChangeEvent event) {
        if (!TextUtils.isEmpty(event.typeCode)) {
            if (getTag().contains("THREE")) {
                typecode = event.typeCode;
                resetFormLayoutGone();
                if (typecode.equals(PlannedCashRateConfig.PZ_DX)) {
                    rawResource = R.raw.pzdx_three;
                    formLayout_pz_dx.setVisibility(View.VISIBLE);
                    currentFormLayout = formLayout_pz_dx;
                    title = gcname + PlannedCashRateConfig.PZ_DXL_TITLE;
                } else if (typecode.equals(PlannedCashRateConfig.DY_PZ_FH)) {
                    rawResource = R.raw.dypz_three;
                    formLayout_dy_fh.setVisibility(View.VISIBLE);
                    currentFormLayout = formLayout_dy_fh;
                    title = gcname + PlannedCashRateConfig.DY_FH_TITLE;
                } else if (typecode.equals(PlannedCashRateConfig.LJ_PZ_FH)) {
                    rawResource = R.raw.ljpz_three;
                    formLayout_lj_fh.setVisibility(View.VISIBLE);
                    currentFormLayout = formLayout_lj_fh;
                    title = gcname + PlannedCashRateConfig.LJ_FH_TITLE;
                } else if (typecode.equals(PlannedCashRateConfig.GC_SJ_FH)) {
                    rawResource = R.raw.gcsj_three;
                    formLayout_gc_fh.setVisibility(View.VISIBLE);
                    currentFormLayout = formLayout_gc_fh;
                    title = gcname + PlannedCashRateConfig.GC_SJ_FH_TITLE;
                }
                textViewTitle.setText(title);
                resetButtonColor();
                initControl();
                getDataThreeTable();
            }
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
            //bodyJson ->
            JSONObject jsonObject = JSON.parseObject(titleJson);
            data = jsonObjectBody.getJSONArray("datas");
            jsonObject.put("data", data);


            String jsonAll = jsonObject.toJSONString();
            mHTMRDataTable = JSON.parseObject(jsonAll, HTMRDataTable.class);

            for (int i = 0; i < mHTMRDataTable.data.size(); i++) {
                String annualconfigrate = mHTMRDataTable.data.get(i).getString("annualconfigrate") + "%";
                String monthlyconfigrate = mHTMRDataTable.data.get(i).getString("monthlyconfigrate") + "%";
                mHTMRDataTable.data.get(i).put("monthlyconfigrate", monthlyconfigrate);
                mHTMRDataTable.data.get(i).put("annualconfigrate", annualconfigrate);
            }

        } catch (Exception e) {

        }

    }


    private String readRawFile() {
        String content;
        Resources resources = this.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(rawResource);
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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void resetButtonColor() {
        bt_gc.setBackgroundColor(getResources().getColor(R.color.planned_bottom_color));
        bt_lj.setBackgroundColor(getResources().getColor(R.color.planned_bottom_color));
        bt_dy.setBackgroundColor(getResources().getColor(R.color.planned_bottom_color));
        bt_dxl.setBackgroundColor(getResources().getColor(R.color.planned_bottom_color));

        if (typecode.equals(PlannedCashRateConfig.PZ_DX)) {
            cash_rate_img.setColorFilter(Color.parseColor(selectColor));
            same_montch_img.setColorFilter(Color.parseColor(noselectColor));
            cumulative_img.setColorFilter(Color.parseColor(noselectColor));
            steel_mill_img.setColorFilter(Color.parseColor(noselectColor));
        } else if (typecode.equals(PlannedCashRateConfig.DY_PZ_FH)) {
            cash_rate_img.setColorFilter(Color.parseColor(noselectColor));
            same_montch_img.setColorFilter(Color.parseColor(selectColor));
            cumulative_img.setColorFilter(Color.parseColor(noselectColor));
            steel_mill_img.setColorFilter(Color.parseColor(noselectColor));
        } else if (typecode.equals(PlannedCashRateConfig.LJ_PZ_FH)) {
            cash_rate_img.setColorFilter(Color.parseColor(noselectColor));
            same_montch_img.setColorFilter(Color.parseColor(noselectColor));
            cumulative_img.setColorFilter(Color.parseColor(selectColor));
            steel_mill_img.setColorFilter(Color.parseColor(noselectColor));
        } else if (typecode.equals(PlannedCashRateConfig.GC_SJ_FH)) {
            cash_rate_img.setColorFilter(Color.parseColor(noselectColor));
            same_montch_img.setColorFilter(Color.parseColor(noselectColor));
            cumulative_img.setColorFilter(Color.parseColor(noselectColor));
            steel_mill_img.setColorFilter(Color.parseColor(selectColor));
        }
    }

    public void resetFormLayoutGone() {
        formLayout_pz_dx.setVisibility(View.GONE);
        formLayout_dy_fh.setVisibility(View.GONE);
        formLayout_lj_fh.setVisibility(View.GONE);
        formLayout_gc_fh.setVisibility(View.GONE);
    }
}
