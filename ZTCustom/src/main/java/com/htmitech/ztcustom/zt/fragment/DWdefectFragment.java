package com.htmitech.ztcustom.zt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.GetDefectStatOrgAdapter;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrg;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrgSuccess;
import com.htmitech.ztcustom.zt.interfaces.CallBackDefectStatOrg;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import htmitech.com.componentlibrary.unit.Utils;


public class DWdefectFragment extends BaseFragment {


	private CallBackDefectStatOrg mCallBackDefectStatOrg;

	private ProgressBar progress_;

	public GetDefectStatByOrg mGetDefectStatByOrg;
	private ListView lv_dynamic;

	private GetDefectStatOrgAdapter mGetDefectStatOrgAdapter;
	private String code;
	public GetDefectStatByOrg getmGetDefectStatByOrg() {
		return mGetDefectStatByOrg;
	}

	public void setmGetDefectStatByOrg(GetDefectStatByOrg mGetDefectStatByOrg) {
		this.mGetDefectStatByOrg = mGetDefectStatByOrg;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zt_dw_defect_fragment, container, false);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口 */
		super.onAttach(activity);
		try {
			mCallBackDefectStatOrg = (CallBackDefectStatOrg) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName()
					+ " must implements interface MyListener");
		}
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		progress_ = (ProgressBar) getView().findViewById(R.id.progress_);
		lv_dynamic = (ListView) getView().findViewById(R.id.lv_dynamic);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mGetDefectStatOrgAdapter = new GetDefectStatOrgAdapter(getActivity(),null,mCallBackDefectStatOrg,false);
		lv_dynamic.setAdapter(mGetDefectStatOrgAdapter);
		requestGetDefectStat();
	}
	public void requestGetDefectStat(){
		progress_.setVisibility(View.VISIBLE);
		AnsynHttpRequest.request(getActivity(), mGetDefectStatByOrg, CHTTP.GETDEFECTSTATBYORG,
				CHTTP.POST, new ObserverCallBack() {

					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						progress_.setVisibility(View.GONE);
						GetDefectStatByOrgSuccess mGetDefectStatSuccess = FastJsonUtils
								.getPerson(successMessage, GetDefectStatByOrgSuccess.class);
						mGetDefectStatOrgAdapter.setBeginTime(mGetDefectStatByOrg.begin_stat_date);
						mGetDefectStatOrgAdapter.setEndTime(mGetDefectStatByOrg.end_stat_date);
						mGetDefectStatOrgAdapter.setCode(code);
						mGetDefectStatOrgAdapter.setOrg_id(mGetDefectStatByOrg.org_id);
						mGetDefectStatOrgAdapter.setOrgType(mGetDefectStatByOrg.org_type);
						mGetDefectStatOrgAdapter.setData(mGetDefectStatSuccess.org_value_list);
					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub

						Utils.toast(getActivity(), "暂无数据" , Toast.LENGTH_SHORT);
						progress_.setVisibility(View.GONE);
					}
				});

	}

	public void requestGetDefectStat(GetDefectStatByOrg mGetDefectStatByOrg,String code){
		this.code = code;
		this.mGetDefectStatByOrg = mGetDefectStatByOrg;
		requestGetDefectStat();

	}


}
