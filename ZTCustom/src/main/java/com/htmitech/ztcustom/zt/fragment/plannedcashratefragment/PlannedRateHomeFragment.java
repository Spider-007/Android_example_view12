package com.htmitech.ztcustom.zt.fragment.plannedcashratefragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.utils.OAConText;
import com.htmitech.ztcustom.zt.app.PlannedCashRateConfig;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.constant.PlannedRateHomeBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateHomeRequestBean;
import com.htmitech.ztcustom.zt.constant.PlannedRateHomeResultBean;
import com.htmitech.ztcustom.zt.event.PlanRateDataChangeEvent;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.interfaces.OnPlannedRateFragmentCallbackListener;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPlannedRateFragmentCallbackListener} interface
 * to handle interaction events.
 */
public class PlannedRateHomeFragment extends BaseFragment implements View.OnClickListener {

    private OnPlannedRateFragmentCallbackListener mListener;

    private TextView tv_PZ_DX_one;
    private TextView tv_DY_PZ_FH_two;
    private TextView tv_LJ_PZ_FH_three;
    private TextView tv_GC_SJ_FH_four;

    private LinearLayout ll_pz;
    private LinearLayout ll_dy;
    private LinearLayout ll_lj;
    private LinearLayout ll_gc;

    private String year;
    private String month;
    private String projectID;

    private Context mContext;

    private PlannedRateHomeRequestBean rateHomeRequestBean;
    private PlannedRateHomeResultBean resultBean;

    public PlannedRateHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_planned_rate_home, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    protected void initView() {
        tv_PZ_DX_one = (TextView) getView().findViewById(R.id.tv_PZ_DX_one);
        tv_DY_PZ_FH_two = (TextView) getView().findViewById(R.id.tv_DY_PZ_FH_two);
        tv_LJ_PZ_FH_three = (TextView) getView().findViewById(R.id.tv_LJ_PZ_FH_three);
        tv_GC_SJ_FH_four = (TextView) getView().findViewById(R.id.tv_GC_SJ_FH_four);
        ll_pz = (LinearLayout) getView().findViewById(R.id.ll_fragment_planned_rate_home_pz);
        ll_pz.setOnClickListener(this);
        ll_lj = (LinearLayout) getView().findViewById(R.id.ll_fragment_planned_rate_home_lj);
        ll_lj.setOnClickListener(this);
        ll_dy = (LinearLayout) getView().findViewById(R.id.ll_fragment_planned_rate_home_dy);
        ll_dy.setOnClickListener(this);
        ll_gc = (LinearLayout) getView().findViewById(R.id.ll_fragment_planned_rate_home_gc);
        ll_gc.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            year = bundle.getString(PlannedCashRateConfig.YEAR) != null ? bundle.getString(PlannedCashRateConfig.YEAR) : "";
            month = bundle.getString(PlannedCashRateConfig.MONTH) != null ? bundle.getString(PlannedCashRateConfig.MONTH) : "";
            projectID = bundle.getString(PlannedCashRateConfig.PROJECTID) != null ? bundle.getString(PlannedCashRateConfig.PROJECTID) : "";
            Log.e("heyang", year + month + " " + projectID);
        }
        rateHomeRequestBean = new PlannedRateHomeRequestBean();
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
        if (v.getId() == R.id.ll_fragment_planned_rate_home_pz) {
            mListener.onItemClickListener(this.getTag(), PlannedCashRateConfig.PZ_DX_TWO_FRAGMENT_TAG, PlannedCashRateConfig.PZ_DX, null, null, null);
        } else if (v.getId() == R.id.ll_fragment_planned_rate_home_dy) {
            mListener.onItemClickListener(this.getTag(), PlannedCashRateConfig.DY_PZ_FH_TWO_FRAGMENT_TAG, PlannedCashRateConfig.DY_PZ_FH, null, null, null);
        } else if (v.getId() == R.id.ll_fragment_planned_rate_home_lj) {
            mListener.onItemClickListener(this.getTag(), PlannedCashRateConfig.LJ_PZ_FH_TWO_FRAGMENT_TAG, PlannedCashRateConfig.LJ_PZ_FH, null, null, null);
        } else if (v.getId() == R.id.ll_fragment_planned_rate_home_gc) {
            mListener.onItemClickListener(this.getTag(), PlannedCashRateConfig.GC_SJ_FH_TWO_FRAGMENT_TAG, PlannedCashRateConfig.GC_SJ_FH, null, null, null);
        }
    }

    private void getData() {
        mListener.onRequestDataStart();
        rateHomeRequestBean.projectid = projectID;
        rateHomeRequestBean.querydate = year;
        rateHomeRequestBean.userid = OAConText.getInstance(getActivity()).UserID;
        AnsynHttpRequest.request(mContext, rateHomeRequestBean, CHTTP.PLANNED_CASH_RATE_HOME, CHTTP.POST,
                new ObserverCallBack() {
                    @Override
                    public void success(String successMessage) {
                        // TODO Auto-generated method stub
                        final PlannedRateHomeBean rateHomeBean = FastJsonUtils.getPerson(successMessage, PlannedRateHomeBean.class);
                        Log.d("heyang", rateHomeBean.code + "");
                        if (rateHomeBean.code == 200) {
                            resultBean = rateHomeBean.result;
                            if (resultBean.success) {
                                if (resultBean.datas != null) {
                                    ((Activity) mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            for (int i = 0; i < resultBean.datas.size(); i++) {
                                                if (resultBean.datas.get(i).typecode.contentEquals(PlannedCashRateConfig.PZ_DX)) {
                                                    tv_PZ_DX_one.setText(resultBean.datas.get(i).configratetotal + "%");
                                                } else if (resultBean.datas.get(i).typecode.contentEquals(PlannedCashRateConfig.DY_PZ_FH)) {
                                                    tv_DY_PZ_FH_two.setText(String.format("%.1f", resultBean.datas.get(i).monthlyconfigdeliverytotal) + "\r\n" + "万吨");
                                                } else if (resultBean.datas.get(i).typecode.contentEquals(PlannedCashRateConfig.LJ_PZ_FH)) {
                                                    tv_LJ_PZ_FH_three.setText(String.format("%.1f", resultBean.datas.get(i).annualconfigdeliverytotal) + "\r\n" + "万吨");
                                                } else if (resultBean.datas.get(i).typecode.contentEquals(PlannedCashRateConfig.GC_SJ_FH)) {
                                                    tv_GC_SJ_FH_four.setText(String.format("%.1f", resultBean.datas.get(i).actualdeliverytotal) + "\r\n" + "万吨");
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            if (rateHomeBean.code == 400) {
                                Utils.toast(mContext, "暂无数据", Toast.LENGTH_SHORT);
                            } else {
                                Utils.toast(mContext, "请求失败", Toast.LENGTH_SHORT);
                            }
                            tv_PZ_DX_one.setText("暂无数据");
                            tv_DY_PZ_FH_two.setText("暂无数据");
                            tv_LJ_PZ_FH_three.setText("暂无数据");
                            tv_GC_SJ_FH_four.setText("暂无数据");
                        }
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void notNetwork() {
                        // TODO Auto-generated method stub
                        Utils.toast(mContext, "请检查网络连接", Toast.LENGTH_SHORT);
                        tv_PZ_DX_one.setText("暂无数据");
                        tv_DY_PZ_FH_two.setText("暂无数据");
                        tv_LJ_PZ_FH_three.setText("暂无数据");
                        tv_GC_SJ_FH_four.setText("暂无数据");
                        mListener.onRequestDataFinish();
                    }

                    @Override
                    public void fail(String exceptionMessage) {
                        // TODO Auto-generated method stub
                        mListener.onRequestDataFinish();
                        Utils.toast(mContext, "请求错误" + exceptionMessage, Toast.LENGTH_SHORT);
                        tv_PZ_DX_one.setText("暂无数据");
                        tv_DY_PZ_FH_two.setText("暂无数据");
                        tv_LJ_PZ_FH_three.setText("暂无数据");
                        tv_GC_SJ_FH_four.setText("暂无数据");
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
