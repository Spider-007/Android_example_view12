package com.htmitech.ztcustom.zt.fragment;

/**
 * 探伤完成情况汇总下拉菜单
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackCompleteFragmentDataListener;

import net.simonvt.datepicker.DatePicker;

import java.util.Calendar;

public class CompleteSumDrawLayoutFragment extends BaseFragment implements
		OnClickListener, DatePicker.OnDateChangedListener, OnTouchListener,
		OnGestureListener {
	private TextView tv_niandu;
	private TextView tv_jidu;
	private TextView tv_yuedu;
	private LinearLayout ll_annian;
	private LinearLayout ll_anjidu;
	private EditText et_complete_fragment;
	private net.simonvt.datepicker.DatePicker datePicker;
	private Button bt_first;
	private Button bt_second;
	private Button bt_thrid;
	private Button bt_fourth;
	private Calendar c = Calendar.getInstance();
	private int year ;
	//= c.get(Calendar.YEAR);
	private int startmonth = 1;
	private int endmonth ;
	//c.get(Calendar.MONTH) + 1;
	private int day = c.get(Calendar.DAY_OF_MONTH);
	private final int NIANDU = 1;
	private final int YTUEDU = 2;
	private final int JIDU = 3;
	private int WHICH = 0;
	private TextView tv_drawlayout_out;
	private CallBackCompleteFragmentDataListener listener;
	private int whichSelected;
	private GestureDetector gestureDetector;
	private LinearLayout ll_complete_fragment_drawlayout_up;
	private boolean flag = true;
	public int startMonth = 0;// activity决定值得大小

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle = getArguments();
		whichSelected = bundle.getInt("whichSelected");
		return inflater.inflate(R.layout.complete_sum_drawlayout_fragment,
				container, false);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);
		try {
			listener = (CallBackCompleteFragmentDataListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName()
					+ " must implements interface MyListener");
		}
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setEndmonth(int endmonth) {
		this.endmonth = endmonth;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		tv_niandu = (TextView) getActivity().findViewById(
				R.id.tv_complete_niandu);
		tv_niandu.setOnClickListener(this);
		tv_jidu = (TextView) getActivity().findViewById(R.id.tv_complete_jidu);
		tv_jidu.setOnClickListener(this);
		tv_yuedu = (TextView) getActivity()
				.findViewById(R.id.tv_complete_yuedu);
		tv_yuedu.setOnClickListener(this);
		ll_annian = (LinearLayout) getActivity().findViewById(
				R.id.ll_complete_drawlayout_fragment_annian);
		ll_anjidu = (LinearLayout) getActivity().findViewById(
				R.id.ll_complete_drawlayout_fragment_anjidu);
		// ll_anyue = (LinearLayout) getActivity().findViewById(
		// R.id.ll_complete_drawlayout_fragment_anyue);
		datePicker = (DatePicker) getActivity().findViewById(
				R.id.dp_complete_drawlayout_an);
		bt_first = (Button) getActivity().findViewById(
				R.id.bt_complete_drawlayout_first);
		bt_first.setOnClickListener(this);
		bt_second = (Button) getActivity().findViewById(
				R.id.bt_complete_drawlayout_second);
		bt_second.setOnClickListener(this);
		bt_thrid = (Button) getActivity().findViewById(
				R.id.bt_complete_drawlayout_thrid);
		bt_thrid.setOnClickListener(this);
		bt_fourth = (Button) getActivity().findViewById(
				R.id.bt_complete_drawlayout_fourth);
		bt_fourth.setOnClickListener(this);
		tv_drawlayout_out = (TextView) getActivity().findViewById(
				R.id.tv_completesum_drawlayout_fragmentout);
		// tv_drawlayout_out.setOnClickListener(this);
		// tv_drawlayout_out.setOnTouchListener(this);
		et_complete_fragment = (EditText) ll_anjidu
				.findViewById(R.id.et_complete_drawlayout_fragment);
		ll_complete_fragment_drawlayout_up = (LinearLayout) getActivity()
				.findViewById(R.id.ll_complete_drawlayout_fragment_up);
		gestureDetector = new GestureDetector(this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if (startMonth != 0) {
			if (endmonth == 12) {
				year++;
				endmonth = startMonth;
			} else {
				endmonth = endmonth + startMonth;
			}
		}
		datePicker.init(year, endmonth, day, this);
		datePicker.basisYear();
		whichSelectedFirst();
		WHICH = YTUEDU;
		ll_complete_fragment_drawlayout_up.setOnClickListener(this);
		ll_complete_fragment_drawlayout_up.setOnTouchListener(this);
	}

	// 每次进入fragment选择进入那个界面
	private void whichSelectedFirst() {
		if (whichSelected == 0) {
			tv_niandu.performClick();
		} else if (whichSelected == 1) {
			tv_jidu.performClick();
		} else if (whichSelected == 2) {
			// tv_niandu.performClick();
			tv_yuedu.performClick();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.tv_complete_niandu ){
			WHICH = NIANDU;
			whichSelected = 0;
			ll_annian.setVisibility(View.VISIBLE);
			ll_anjidu.setVisibility(View.GONE);
			if (startMonth != 0) {
				if (c.get(Calendar.MONTH) + 1 == 12) {
					year = c.get(Calendar.YEAR) + 1;
					endmonth = startMonth;
				} else {
					endmonth=c.get(Calendar.MONTH)+1+ startMonth;
					year = c.get(Calendar.YEAR);
				}
			}else{
				endmonth = c.get(Calendar.MONTH) + 1;
				year = c.get(Calendar.YEAR);
			}
			datePicker.init(year, endmonth-1, c.get(Calendar.DAY_OF_MONTH), this);
			startmonth = 1;
			datePicker.basisYear();
			tv_niandu.setSelected(true);
			tv_jidu.setSelected(false);
			tv_yuedu.setSelected(false);
		}else if(arg0.getId() ==R.id.tv_complete_jidu){
			whichSelected = 1;
			WHICH = JIDU;
			ll_annian.setVisibility(View.GONE);
			ll_anjidu.setVisibility(View.VISIBLE);
			bt_first.setSelected(true);
			bt_second.setSelected(false);
			bt_thrid.setSelected(false);
			bt_fourth.setSelected(false);
			year = c.get(Calendar.YEAR);
			et_complete_fragment.setText(year + "");
			startmonth = 1;
			endmonth = 3;
			tv_niandu.setSelected(false);
			tv_jidu.setSelected(true);
			tv_yuedu.setSelected(false);
		}else if(arg0.getId() ==R.id.tv_complete_yuedu){
			whichSelected = 2;
			WHICH = YTUEDU;
			ll_annian.setVisibility(View.VISIBLE);
			ll_anjidu.setVisibility(View.GONE);
			if (startMonth != 0) {
				if (c.get(Calendar.MONTH) + 1 == 12) {
					year = c.get(Calendar.YEAR) + 1;
					endmonth = startMonth;
				} else {
					endmonth=c.get(Calendar.MONTH)+1+ startMonth;
					year = c.get(Calendar.YEAR);
				}
			}else{
				endmonth = c.get(Calendar.MONTH) + 1;
				year = c.get(Calendar.YEAR);
			}
			datePicker.init(year,endmonth-1, c.get(Calendar.DAY_OF_MONTH), this);
			startmonth = endmonth;
			datePicker.basisMouth();
			tv_niandu.setSelected(false);
			tv_jidu.setSelected(false);
			tv_yuedu.setSelected(true);
		}else if(arg0.getId() ==R.id.bt_complete_drawlayout_first){
			bt_first.setSelected(true);
			bt_second.setSelected(false);
			bt_thrid.setSelected(false);
			bt_fourth.setSelected(false);
			year = c.get(Calendar.YEAR);
			startmonth = 1;
			endmonth = 3;
		}else if(arg0.getId() ==R.id.bt_complete_drawlayout_second){
			bt_first.setSelected(false);
			bt_second.setSelected(true);
			bt_thrid.setSelected(false);
			bt_fourth.setSelected(false);
			year = c.get(Calendar.YEAR);
			startmonth = 4;
			endmonth = 6;
		}else if(arg0.getId() ==R.id.bt_complete_drawlayout_thrid){
			bt_first.setSelected(false);
			bt_second.setSelected(false);
			bt_thrid.setSelected(true);
			bt_fourth.setSelected(false);
			year = c.get(Calendar.YEAR);
			startmonth = 7;
			endmonth = 9;
		}else if(arg0.getId() ==R.id.bt_complete_drawlayout_fourth){
			bt_first.setSelected(false);
			bt_second.setSelected(false);
			bt_thrid.setSelected(false);
			bt_fourth.setSelected(true);
			year = c.get(Calendar.YEAR);
			startmonth = 10;
			endmonth = 12;
		}
	}

	// 日期控件时间改变方法
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		if (WHICH == NIANDU) {
			if (year < c.get(Calendar.YEAR)) {
				this.year = year;
				this.endmonth = 12;
				this.startmonth = 1;
			} else if (year == c.get(Calendar.YEAR)) {
				this.year = year;
				this.endmonth = c.get(Calendar.MONTH) + 1;
				this.startmonth = 1;
			}
		}
		if (WHICH == YTUEDU) {
			this.year = year;
			this.endmonth = monthOfYear + 1;
			this.startmonth = monthOfYear + 1;
		}
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(arg1);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO 上滑监听 返回数据
		int y = (int) (arg0.getY() - arg1.getY());
		if (y > 50) {
			if (ll_anjidu.getVisibility() == View.VISIBLE) {
				year = Integer.parseInt(et_complete_fragment.getText()
						.toString());
				Log.e("year", year + "");
			}
			listener.getFragmentData(this.year, this.startmonth, this.endmonth,
					this.whichSelected);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
