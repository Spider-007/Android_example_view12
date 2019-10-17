package com.htmitech.ztcustom.zt.fragment.plannedcashratefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.adapter.PlannedRateBarAdapter;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.PlannedRateBarBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateBarData;
import com.htmitech.ztcustom.zt.constant.PlannedRateBarRequest;
import com.htmitech.ztcustom.zt.event.PlanRateDataChangeEvent;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnPlannedRateFragmentCallbackListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPlannedRateFragmentCallbackListener} interface
 * to handle interaction events.
 */
public class PlannedRateBarFragment extends BaseFragment implements View.OnClickListener {

    private OnPlannedRateFragmentCallbackListener mListener;

    private TextView tvTitle;
    private ListView listView;
    private TextView tv_configweight;
    private TextView tv_deliveryweight;
    private TextView tv_contractweight;
    private TextView tv_qjcontractweight;

    private String year;
    private String month;
    private String projectID;
    private String typecode;
    private String currentfragment;
    private String title;

    private Context mContext;

    private PlannedRateBarAdapter adapter;

    public PlannedRateBarRequest plannedRateBarRequest;
    public PlannedRateBarBean plannedRateBarBean;
    public ArrayList<PlannedRateBarData> plannedRateBarDataArrayList;
    public float maxProgress = 0.000f;

    public PlannedRateBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_planned_rate_bar, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    protected void initView() {
        tvTitle = (TextView) getView().findViewById(R.id.tv_planned_rate_bar_title);
        listView = (ListView) getView().findViewById(R.id.listview_planned_rate_bar);
        tv_configweight = (TextView) getView().findViewById(R.id.tv_planned_rate_configweight);
        tv_deliveryweight = (TextView) getView().findViewById(R.id.tv_planned_rate_deliveryweight);
        tv_contractweight = (TextView) getView().findViewById(R.id.tv_planned_rate_contractweight);
        tv_qjcontractweight = (TextView) getView().findViewById(R.id.tv_planned_rate_qjcontractweight);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            year = bundle.getString(PlannedCashRateConfig.YEAR) != null ? bundle.getString(PlannedCashRateConfig.YEAR) : "";
            month = bundle.getString(PlannedCashRateConfig.MONTH) != null ? bundle.getString(PlannedCashRateConfig.MONTH) : "";
            projectID = bundle.getString(PlannedCashRateConfig.PROJECTID) != null ? bundle.getString(PlannedCashRateConfig.PROJECTID) : "";
            typecode = bundle.getString(PlannedCashRateConfig.TYPECODE) != null ? bundle.getString(PlannedCashRateConfig.TYPECODE) : "";
            title = bundle.getString(PlannedCashRateConfig.TITLE) != null ? bundle.getString(PlannedCashRateConfig.TITLE) : "";
            Log.e("heyang", year + month + " " + projectID);
        }
        tvTitle.setText(title);
        //    <--------------------Administrator -> 2019-8-19:19:15:修复bug 显示的数据条的数据和底部的数据显示 错乱-> 修改--------------------->
        if (getTag().equals(PlannedCashRateConfig.DY_PZ_FH_TWO_FRAGMENT_TAG)) {
            tv_configweight.setText("当月配置计划数");
            tv_deliveryweight.setText("当月配置计划发货数");
            tv_contractweight.setText("当月配置计划订货数");
            tv_qjcontractweight.setText("当月配置计划欠交数");
        } else if (getTag().equals(PlannedCashRateConfig.LJ_PZ_FH_TWO_FRAGMENT_TAG)) {
            tv_configweight.setText("累计配置计划数");
            tv_deliveryweight.setText("累计配置计划发货数");
            tv_contractweight.setText("累计配置计划订货数");
            tv_qjcontractweight.setText("累计配置计划欠交数");
        } else if (getTag().equals(PlannedCashRateConfig.GC_SJ_FH_TWO_FRAGMENT_TAG)) {
            //    <--------------------Administrator -> 2019-8-7:1:32: 修改底部文字显示--------------------->
            tv_configweight.setText("当月钢厂实际发货数");
            tv_deliveryweight.setText("钢厂累计发货数");
//            tv_deliveryweight.setText("当月配置计划订货数");
            tv_contractweight.setText("钢厂累计订货数");
            tv_qjcontractweight.setText("钢厂累计欠交数");
        }
        plannedRateBarRequest = new PlannedRateBarRequest();
        plannedRateBarDataArrayList = new ArrayList<>();
        adapter = new PlannedRateBarAdapter(plannedRateBarDataArrayList, mContext, getTag());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gotoFragment = "";
                if (getTag().equals(PlannedCashRateConfig.DY_PZ_FH_TWO_FRAGMENT_TAG)) {
                    gotoFragment = PlannedCashRateConfig.DY_PZ_FH_THREE_FRAGMENT_TAG;
                } else if (getTag().equals(PlannedCashRateConfig.LJ_PZ_FH_TWO_FRAGMENT_TAG)) {
                    gotoFragment = PlannedCashRateConfig.LJ_PZ_FH_THREE_FRAGMENT_TAG;
                } else if (getTag().equals(PlannedCashRateConfig.GC_SJ_FH_TWO_FRAGMENT_TAG)) {
                    gotoFragment = PlannedCashRateConfig.GC_SJ_FH_THREE_FRAGMENT_TAG;
                }
                mListener.onItemClickListener(getTag(), gotoFragment, typecode, plannedRateBarDataArrayList.get(position).gccode,
                        null, plannedRateBarDataArrayList.get(position).gcname);
            }
        });
        getData();
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

    }

    private void getData() {
        mListener.onRequestDataStart();
        plannedRateBarRequest.projectid = projectID;
        plannedRateBarRequest.querydate = year + month;
        plannedRateBarRequest.typecode = typecode;
        plannedRateBarRequest.userid = OAConText.getInstance(getActivity()).UserID;
        AnsynHttpRequest.request(mContext, plannedRateBarRequest, CHTTP.PLANED_CASH_RATE_TWO_BAR, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        final PlannedRateBarBean plannedRateBarBean = FastJsonUtils.getPerson(successMessage, PlannedRateBarBean.class);
                        Log.d("heyang", plannedRateBarBean.code + "");
                        if (plannedRateBarBean.code == 200) {
                            if (plannedRateBarBean.result.datas != null && plannedRateBarDataArrayList != null) {
                                plannedRateBarDataArrayList.clear();
                                plannedRateBarDataArrayList.addAll(plannedRateBarBean.result.datas);
                                maxProgress = 0.000f;
                                for (int i = 0; i < plannedRateBarDataArrayList.size(); i++) {
                                    float temp = 0.000f;
                                    if (plannedRateBarDataArrayList.get(i).configweight != null) {
                                        if (temp < Float.parseFloat(plannedRateBarDataArrayList.get(i).configweight)) {
                                            temp = Float.parseFloat(plannedRateBarDataArrayList.get(i).configweight);
                                        }
                                    }
                                    if (plannedRateBarDataArrayList.get(i).contractweight != null) {
                                        if (temp < Float.parseFloat(plannedRateBarDataArrayList.get(i).contractweight)) {
                                            temp = Float.parseFloat(plannedRateBarDataArrayList.get(i).contractweight);
                                        }
                                    }
                                    if (plannedRateBarDataArrayList.get(i).deliveryweight != null) {
                                        if (temp < Float.parseFloat(plannedRateBarDataArrayList.get(i).deliveryweight)) {
                                            temp = Float.parseFloat(plannedRateBarDataArrayList.get(i).deliveryweight);
                                        }
                                    }
                                    if (plannedRateBarDataArrayList.get(i).monthlydeliveryweight != null) {
                                        if (temp < Float.parseFloat(plannedRateBarDataArrayList.get(i).monthlydeliveryweight)) {
                                            temp = Float.parseFloat(plannedRateBarDataArrayList.get(i).monthlydeliveryweight);
                                        }
                                    }
                                    if (plannedRateBarDataArrayList.get(i).qjcontractweight != null) {
                                        if (temp < Float.parseFloat(plannedRateBarDataArrayList.get(i).qjcontractweight)) {
                                            temp = Float.parseFloat(plannedRateBarDataArrayList.get(i).qjcontractweight);
                                        }
                                    }
                                    if (maxProgress < temp) {
                                        maxProgress = temp;
                                    }
                                }
                                adapter.setMaxProgress(maxProgress);
                                adapter.notifyDataSetChanged();
                            }

                        } else {
                            if (plannedRateBarBean.code == 400) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(PlanRateDataChangeEvent planRateDataChangeEvent) {
        if (planRateDataChangeEvent != null) {
            month = !TextUtils.isEmpty(planRateDataChangeEvent.month) ? planRateDataChangeEvent.month : month;
            year = !TextUtils.isEmpty(planRateDataChangeEvent.year) ? planRateDataChangeEvent.year : year;
            projectID = planRateDataChangeEvent.projectID != null ? planRateDataChangeEvent.projectID : projectID;
            Log.e("heyang", "month: " + month + "year: " + year + "projectID: " + projectID);
            getData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
