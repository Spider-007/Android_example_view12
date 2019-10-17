package com.htmitech.ztcustom.zt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.interfaces.CallBackScreening;
import com.htmitech.ztcustom.zt.util.DensityUtil;

import java.util.ArrayList;

public class DynamicFragment extends BaseFragment implements OnClickListener,
		OnCheckedChangeListener {
	private CallBackScreening mCallBackScreening;
	private TextView tv_clean, tv_queding;
	private RadioGroup rg_pmfs;
	private TextView tv_qs, tv_zs, tv_zd, tv_qfs;
	private TextView tv_gg, tv_dc, tv_hf;
	private ArrayList<String> sscdList;
	private ArrayList<String> sblxList;
	private EditText et_paiming;
	private LinearLayout layout_sscd, layout_sblx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zt_fragment_dynamic, container, false);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口 */
		super.onAttach(activity);
		try {
			mCallBackScreening = (CallBackScreening) activity;
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
		tv_clean = (TextView) getActivity().findViewById(R.id.tv_clean);
		tv_queding = (TextView) getActivity().findViewById(R.id.tv_quding);
		rg_pmfs = (RadioGroup) getActivity().findViewById(R.id.rg_pmfs);
		layout_sscd = (LinearLayout) getActivity().findViewById(
				R.id.layout_sscd);
		layout_sblx = (LinearLayout) getActivity().findViewById(
				R.id.layout_sblx);
		et_paiming = (EditText) getActivity().findViewById(R.id.et_paiming);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		tv_clean.setOnClickListener(this);
		tv_queding.setOnClickListener(this);
		rg_pmfs.setOnCheckedChangeListener(this);
		sscdList = new ArrayList<String>();
		sblxList = new ArrayList<String>();
		DicttypeResult mDicttypeResult_SSCD = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SSCD");
		DicttypeResult mDicttypeResult_SBLX = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SBLX");
		WindowManager wm = getActivity().getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int num = 0;
		int textHight = 60;
		if(width == 1080){
			textHight = 90;
		}else{
			textHight = DensityUtil.dip2px(getActivity(), 30);
		}
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, textHight);
		layoutParams.setMargins(0, 10, 20, 10);
		layout.setLayoutParams(layoutParams);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		//设置伤损程度   动态获取
		for(Dictitemlist mDictitemlist : mDicttypeResult_SSCD.getDictitemlist()){
			sscdList.add(mDictitemlist.getCode());
			if(num == 0){
				layout = new LinearLayout(getActivity());
				layout.setLayoutParams(layoutParams);
			}
			TextView view = new TextView(getActivity());
			LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
					layoutParams.WRAP_CONTENT, textHight, 1);
			textparams.setMargins(20, 0, 0, 0);
			view.setLayoutParams(textparams);
			view.setTextSize(14);
			view.setGravity(Gravity.CENTER);
			view.setPadding(10, 10, 10, 10);
			view.setText("" + mDictitemlist.name);
			view.setTag(""+mDictitemlist.code);
			view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
			view.setOnClickListener(new OnclickSSCD_SBLX("SSCD"));
//			if("QS".equals(""+mDictitemlist.code)){
			view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_down);
//			}
			layout.addView(view);
			num++;
			if(num == 3){
				num = 0;
				layout_sscd.addView(layout);
			}
		}
		num = 0;
		//设置设备类型  动态获取
		for(Dictitemlist mDictitemlist : mDicttypeResult_SBLX.getDictitemlist()){
			sblxList.add(mDictitemlist.getCode());
			if(num == 0){
				layout = new LinearLayout(getActivity());
				layout.setLayoutParams(layoutParams);
			}
			TextView view = new TextView(getActivity());
			LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
					layoutParams.WRAP_CONTENT, textHight, 1);
			textparams.setMargins(20, 0, 0, 0);
			view.setLayoutParams(textparams);
			view.setTextSize(14);
			view.setGravity(Gravity.CENTER);
			view.setPadding(10, 10, 10, 10);
			view.setText(""+mDictitemlist.name);
			view.setTag(""+mDictitemlist.code);
			view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
			view.setOnClickListener(new OnclickSSCD_SBLX("SBLX"));
			if("GG".equals(""+mDictitemlist.code)){
				view.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_down);
			}
			layout.addView(view);
			num++;
			if(num == 3){
				num = 0;
				layout_sblx.addView(layout);
			}
		}
	}

	public class OnclickSSCD_SBLX implements OnClickListener {
		public String code;

		public OnclickSSCD_SBLX(String code) {
			this.code = code;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String tag = arg0.getTag().toString();
			if (code.equals("SSCD")) {
				if (sscdList.contains(tag)) {
					sscdList.remove(tag);
					arg0.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
				} else {
					sscdList.add(tag);
					arg0.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_down);
				}
			}else if(code.equals("SBLX")){
				if (sblxList.contains(tag)) {
					sblxList.remove(tag);
					arg0.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter);
				} else {
					sblxList.add(tag);
					arg0.setBackgroundResource(R.drawable.zt_fragment_gridview_adapter_down);
				}
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.tv_clean ){
			mCallBackScreening.clean();
		}else if(arg0.getId() ==R.id.tv_quding){
			mCallBackScreening.screeningValue(sscdList, sblxList,null, danwei,
					et_paiming.getText().toString());
		}
	}

	String danwei = "DW";

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId ==R.id.rb_danwei ){
			danwei = "DW";
		}else if(checkedId ==R.id.rb_xl){
			danwei = "XL";
		}
	}
}
