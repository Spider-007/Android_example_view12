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

import htmitech.com.componentlibrary.unit.ToastUtil;


public class XLdefectFragment extends BaseFragment {


	private CallBackDefectStatOrg mCallBackDefectStatOrg;

	private ProgressBar progress_;

	public GetDefectStatByOrg mGetDefectStatByOrg;
	private ListView lv_dynamic;

	private GetDefectStatOrgAdapter mGetDefectStatOrgAdapter;

	public GetDefectStatByOrg getmGetDefectStatByOrg() {
		return mGetDefectStatByOrg;
	}

	public void setmGetDefectStatByOrg(GetDefectStatByOrg mGetDefectStatByOrg) {
		this.mGetDefectStatByOrg = copyGetDefectStatByOrg(mGetDefectStatByOrg);
		this.mGetDefectStatByOrg.stat_type = "LINE";
	}
	public GetDefectStatByOrg copyGetDefectStatByOrg(GetDefectStatByOrg mGetDefectStatByOrg){
		GetDefectStatByOrg mGetDefectStatByOrgs = new GetDefectStatByOrg();
		mGetDefectStatByOrgs.userid = mGetDefectStatByOrg.userid;
		mGetDefectStatByOrgs.stat_type = mGetDefectStatByOrg.stat_type;
		mGetDefectStatByOrgs.sblx_list = mGetDefectStatByOrg.sblx_list;
		mGetDefectStatByOrgs.begin_stat_date = mGetDefectStatByOrg.begin_stat_date;
		mGetDefectStatByOrgs.czzt_list = mGetDefectStatByOrg.czzt_list;
		mGetDefectStatByOrgs.defect_type = mGetDefectStatByOrg.defect_type;
		mGetDefectStatByOrgs.org_type = mGetDefectStatByOrg.org_type;
		mGetDefectStatByOrgs.end_stat_date = mGetDefectStatByOrg.end_stat_date;
		mGetDefectStatByOrgs.org_id = mGetDefectStatByOrg.org_id;
		mGetDefectStatByOrgs.sscd_list = mGetDefectStatByOrg.sscd_list;
		return mGetDefectStatByOrgs;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zt_xl_defect_fragment, container, false);
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
						ToastUtil.showShort(getActivity(),"暂无数据");
						progress_.setVisibility(View.GONE);
					}
				});

	}
	public String code;
	public void requestGetDefectStat(GetDefectStatByOrg mGetDefectStatByOrg,String code){
		this.code = code;
		this.mGetDefectStatByOrg = copyGetDefectStatByOrg(mGetDefectStatByOrg);
		this.mGetDefectStatByOrg.stat_type = "LINE";
		requestGetDefectStat();

	}



}
