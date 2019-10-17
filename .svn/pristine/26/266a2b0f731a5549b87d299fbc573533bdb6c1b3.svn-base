package com.htmitech.ztcustom.zt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDataListener;

import net.simonvt.datepicker.DatePicker;
import net.simonvt.datepicker.DatePicker.OnDateChangedListener;

import java.util.Calendar;

/**
 *
 * @author Tony
 *
 */
public class DamageSummaryFragment extends BaseFragment implements
		OnDateChangedListener, OnClickListener {

	public DatePicker mDatePicker;
	private static final String YEAR = "year";
	private static final String MONTH = "month";
	private static final String DAY = "day";
	private Calendar mCalendar;
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	private TextView tvYear, tvMonth, tvData;
	private CallBackDataListener mCallBackDataListener;
	public int startMonth = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.zt_fragment_injuries_type, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);
		try {
			mCallBackDataListener = (CallBackDataListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName()
					+ " must implements interface MyListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mDatePicker = (DatePicker) getView().findViewById(R.id.datePicker);
		tvYear = (TextView) getView().findViewById(R.id.tv_year);
		tvMonth = (TextView) getView().findViewById(R.id.tv_month);
		tvData = (TextView) getView().findViewById(R.id.tv_data);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mCalendar = Calendar.getInstance();
		year = mCalendar.get(Calendar.YEAR);
		monthOfYear = mCalendar.get(Calendar.MONTH);
		dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
		monthOfYear = monthOfYear + startMonth;
		mDatePicker.init(year, monthOfYear, dayOfMonth, this);
		tvYear.setOnClickListener(this);
		tvMonth.setOnClickListener(this);
		tvData.setOnClickListener(this);


		type = 1;
		tvMonth.setTextColor(getResources().getColor(R.color.white));
		tvYear.setTextColor(getResources().getColor(
				R.color.railway_year_month_data));
		tvData.setTextColor(getResources().getColor(
				R.color.railway_year_month_data));

		tvYear.setBackgroundResource(R.drawable.zt_fragment_years);
		tvMonth.setBackgroundResource(R.drawable.zt_fragment_month_down);
		tvData.setBackgroundResource(R.drawable.zt_fragment_day);
		mCallBackDataListener.onClickChildOne(year, monthOfYear + 1, dayOfMonth);
//		mDatePicker.basisMouth();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
							  int dayOfMonth) {
		// TODO Auto-generated method stub
		mDatePicker.init(year, monthOfYear, dayOfMonth, this);
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth  = dayOfMonth;
		switch (type) {
			case 0:
				mCallBackDataListener.onClickChild(year, monthOfYear + 1,
						dayOfMonth, CallBackDataListener.Type.YEAR);
				break;
			case 1:
				mCallBackDataListener.onClickChild(year, monthOfYear + 1,
						dayOfMonth, CallBackDataListener.Type.MONTH);
				break;
			case 2:
				mCallBackDataListener.onClickChild(year, monthOfYear + 1,
						dayOfMonth, CallBackDataListener.Type.DATA);
				break;
		}
	}

	public int type = 2;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.tv_year ){
			type = 0;
			tvYear.setTextColor(getResources().getColor(R.color.white));
			tvMonth.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));
			tvData.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));
			tvYear.setBackgroundResource(R.drawable.zt_fragment_years_down);
			tvMonth.setBackgroundResource(R.drawable.zt_fragment_month);
			tvData.setBackgroundResource(R.drawable.zt_fragment_day);
			mDatePicker.basisYear();
			mCallBackDataListener.onClickChild(year, monthOfYear + 1,
					dayOfMonth, CallBackDataListener.Type.YEAR);
		}else if(arg0.getId() ==R.id.tv_month){
			type = 1;
			tvMonth.setTextColor(getResources().getColor(R.color.white));
			tvYear.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));
			tvData.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));

			tvYear.setBackgroundResource(R.drawable.zt_fragment_years);
			tvMonth.setBackgroundResource(R.drawable.zt_fragment_month_down);
			tvData.setBackgroundResource(R.drawable.zt_fragment_day);
			mCallBackDataListener.onClickChild(year, monthOfYear + 1,
					dayOfMonth, CallBackDataListener.Type.MONTH);
			mDatePicker.basisMouth();
		}else if(arg0.getId() ==R.id.tv_data){
			type = 2;
			tvData.setTextColor(getResources().getColor(R.color.white));
			tvYear.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));
			tvMonth.setTextColor(getResources().getColor(
					R.color.railway_year_month_data));

			tvYear.setBackgroundResource(R.drawable.zt_fragment_years);
			tvMonth.setBackgroundResource(R.drawable.zt_fragment_month);
			tvData.setBackgroundResource(R.drawable.zt_fragment_day_down);
			mDatePicker.basisDay();
			mCallBackDataListener.onClickChild(year, monthOfYear + 1,
					dayOfMonth, CallBackDataListener.Type.DATA);
		}

	}

	public void setInitDate(){
		mDatePicker.init(year, monthOfYear, dayOfMonth, this);
	}
}
