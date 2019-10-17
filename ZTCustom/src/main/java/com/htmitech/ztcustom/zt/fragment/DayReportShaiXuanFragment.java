package com.htmitech.ztcustom.zt.fragment;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.base.BaseFragment;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.Dicttype;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.interfaces.DayReportCallBackListener;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DayReportShaiXuanFragment extends BaseFragment implements
		OnClickListener, OnCheckedChangeListener {

	private LinearLayout ll_wz1;
	private LinearLayout ll_wz2;
	private LinearLayout ll_wz3;
	private LinearLayout ll_wz4;
	private LinearLayout ll_lx1;
	private LinearLayout ll_lx2;
	private LinearLayout ll_lx3;
	private LinearLayout ll_lx4;
	private TextView tv_cancle;
	private TextView tv_submit;
	private RadioGroup rg_pmfs;
	private EditText et_jhh;
	private RadioButton rb_gg;
	private RadioButton rb_dc;
	private RadioButton rb_hf;
	private List<String> list_wz;
	private List<String> list_lx;
	private List<String> list_wz_act = new ArrayList<String>();// 从上个界面传过来的
	private List<String> list_lx_act = new ArrayList<String>();// 从上个界面传过来的
	private String SBLX_act;// 从上个界面穿过来的
	private boolean flag_frist_act;
	private String rb_text = "GG";
	private DayReportCallBackListener listener;// 接口回调对象

	List<Dictitemlist> diclist_lx = new ArrayList<Dictitemlist>();
	List<Dictitemlist> dicList_wz = new ArrayList<Dictitemlist>();
	private int width;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Bundle bundle = getArguments();
		SBLX_act = bundle.getString("SBLX");
		list_lx_act = bundle.getStringArrayList("list_lx");
		list_wz_act = bundle.getStringArrayList("list_wz");
		flag_frist_act = bundle.getBoolean("flag_frist");
		return inflater.inflate(R.layout.dayreport_shaixuan_fragment, null,
				false);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.tv_dayreport_quding ){
			String jhh = et_jhh.getText() + "";

			if (ll_wz1.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_wz1.getChildCount(); i++) {
					if ((ll_wz1.getChildAt(i)).isSelected()) {
						list_wz.add(dicList_wz.get(i).getCode());
					}
				}
			}
			if (ll_wz2.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_wz2.getChildCount(); i++) {
					if ((ll_wz2.getChildAt(i)).isSelected()) {
						list_wz.add(dicList_wz.get(i + 3).getCode());
					}
				}
			}
			if (ll_wz3.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_wz3.getChildCount(); i++) {
					if ((ll_wz3.getChildAt(i)).isSelected()) {
						list_wz.add(dicList_wz.get(i + 6).getCode());
					}
				}
			}
			if (ll_wz4.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_wz4.getChildCount(); i++) {
					if ((ll_wz4.getChildAt(i)).isSelected()) {
						list_wz.add(dicList_wz.get(i + 9).getCode());
					}
				}
			}
			if (ll_lx1.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_lx1.getChildCount(); i++) {
					if (ll_lx1.getChildAt(i).isSelected()) {
						list_lx.add(diclist_lx.get(i).getCode());
					}
				}
			}
			if (ll_lx2.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_lx2.getChildCount(); i++) {
					if (ll_lx2.getChildAt(i).isSelected()) {
						list_lx.add(diclist_lx.get(i + 3).getCode());
					}
				}
			}
			if (ll_lx3.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_lx3.getChildCount(); i++) {
					if (ll_lx3.getChildAt(i).isSelected()) {
						list_lx.add(diclist_lx.get(i + 6).getCode());
					}
				}
			}
			if (ll_lx4.VISIBLE == View.VISIBLE) {
				for (int i = 0; i < ll_lx4.getChildCount(); i++) {
					if (ll_lx4.getChildAt(i).isSelected()) {
						list_lx.add(diclist_lx.get(i + 9).getCode());
					}
				}
			}

			listener.getData(list_wz, list_lx, jhh, rb_text);
		}else if(arg0.getId() ==R.id.tv_dayreport_clean){
			listener.cancle();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (DayReportCallBackListener) activity;
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		tv_cancle = (TextView) getActivity().findViewById(
				R.id.tv_dayreport_clean);
		tv_submit = (TextView) getActivity().findViewById(
				R.id.tv_dayreport_quding);
		rg_pmfs = (RadioGroup) getActivity().findViewById(
				R.id.rg_dayreport_shaixuan);
		et_jhh = (EditText) getActivity().findViewById(
				R.id.et_dayreport_shaixuan_jhh);
		rb_gg = (RadioButton) getActivity().findViewById(R.id.rb_dayreport_gg);
		rb_dc = (RadioButton) getActivity().findViewById(R.id.rb_dayreport_dc);
		rb_hf = (RadioButton) getActivity().findViewById(R.id.rb_dayreport_hf);
		ll_wz1 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_wz1);
		ll_wz2 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_wz2);
		ll_wz3 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_wz3);
		ll_wz4 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_wz4);
		ll_lx1 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_lx1);
		ll_lx2 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_lx2);
		ll_lx3 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_lx3);
		ll_lx4 = (LinearLayout) getActivity().findViewById(
				R.id.ll_dayreport_lx4);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
		width = (screenWidth - dip2px(getActivity(), 40) - 120) / 3;
		list_wz = new ArrayList<String>();
		list_lx = new ArrayList<String>();
		tv_cancle.setOnClickListener(this);
		tv_submit.setOnClickListener(this);

		if (dicList_wz != null) {
			dicList_wz.clear();
		}
		getWZShuJuZiDian("XB1");

		if (!flag_frist_act) {
			if (SBLX_act.equals("GG")) {
				rb_gg.setButtonDrawable(R.drawable.zt_check_true);
				rb_dc.setButtonDrawable(R.drawable.zt_check_false);
				rb_hf.setButtonDrawable(R.drawable.zt_check_false);
				rb_text = "GG";
				getSBLXMX("GGZLX");
			} else if (SBLX_act.equals("DC")) {
				rb_gg.setButtonDrawable(R.drawable.zt_check_false);
				rb_dc.setButtonDrawable(R.drawable.zt_check_true);
				rb_hf.setButtonDrawable(R.drawable.zt_check_false);
				rb_text = "DC";
				getSBLXMX("CCGZ");
			} else if (SBLX_act.equals("HF")) {
				rb_gg.setButtonDrawable(R.drawable.zt_check_false);
				rb_dc.setButtonDrawable(R.drawable.zt_check_false);
				rb_hf.setButtonDrawable(R.drawable.zt_check_true);
				rb_text = "HF";
				getSBLXMX("HFZLX");
			}
		} else {
			getSBLXMX("GGZLX");
		}

		rg_pmfs.setOnCheckedChangeListener(this);
		// rg_pmfs.check(R.id.rb_dayreport_gg);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if(arg1 ==R.id.rb_dayreport_gg ){
			rb_gg.setButtonDrawable(R.drawable.zt_check_true);
			rb_dc.setButtonDrawable(R.drawable.zt_check_false);
			rb_hf.setButtonDrawable(R.drawable.zt_check_false);
			rb_text = "GG";
			getSBLXMX("GGZLX");
		}else if(arg1 ==R.id.rb_dayreport_dc){
			rb_gg.setButtonDrawable(R.drawable.zt_check_false);
			rb_dc.setButtonDrawable(R.drawable.zt_check_true);
			rb_hf.setButtonDrawable(R.drawable.zt_check_false);
			rb_text = "DC";
			getSBLXMX("CCGZ");
		}else if(arg1 ==R.id.rb_dayreport_hf){
			rb_gg.setButtonDrawable(R.drawable.zt_check_false);
			rb_dc.setButtonDrawable(R.drawable.zt_check_false);
			rb_hf.setButtonDrawable(R.drawable.zt_check_true);
			rb_text = "HF";
			getSBLXMX("HFZLX");
		}
	}

	private void getSBLXMX(String Sblx) {
		ll_lx1.removeAllViews();
		ll_lx2.removeAllViews();
		ll_lx3.removeAllViews();
		ll_lx4.removeAllViews();
		ll_lx1.setVisibility(View.GONE);
		ll_lx2.setVisibility(View.GONE);
		ll_lx3.setVisibility(View.GONE);
		ll_lx4.setVisibility(View.GONE);
		if (diclist_lx != null) {
			diclist_lx.clear();
		}
		getLXShuJuZiDian(Sblx);

	}

	/**
	 * 得到位置的数据字典
	 * 
	 * @param dicttype
	 */
	private void getWZShuJuZiDian(String dicttype) {
		Dicttype bean = new Dicttype();
		bean.dicttype = dicttype;
		AnsynHttpRequest.request(getActivity(), bean,
				CHTTP.IB04_GETDICTTYPEOBJECT, CHTTP.POST,
				new ObserverCallBack() {

					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						DicttypeResult dicttypeResult = FastJsonUtils
								.getPerson(successMessage, DicttypeResult.class);
						if (dicttypeResult != null
								&& dicttypeResult.getDictitemlist() != null
								&& dicttypeResult.getDictitemlist().size() != 0) {// 判空

							dicList_wz.addAll(dicttypeResult.getDictitemlist());

							// 动态生成按钮
							for (int i = 0; i < dicList_wz.size(); i++) {
								final Button button = new Button(getActivity());
								button.setText(dicList_wz.get(i).getName());
								button.setBackgroundResource(R.drawable.dayreport_shaixuan_button);
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										width, LayoutParams.WRAP_CONTENT);
								params.gravity = Gravity.CENTER;
								button.setLayoutParams(params);
								params.setMargins(20, 0, 20, 0);
								button.setTextSize(12.5f);
								button.setPadding(10, 28, 10, 28);
								button.setSingleLine(true);
								button.setEllipsize(TruncateAt.END);
								button.setGravity(Gravity.CENTER);
								button.setTextColor(Color.WHITE);
								button.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										if (button.isSelected()) {
											button.setSelected(false);
										} else {
											button.setSelected(true);
										}
									}
								});
								if (i <= 2) {
									ll_wz1.setVisibility(View.VISIBLE);
									ll_wz1.setWeightSum(3);
									ll_wz1.addView(button);
								}
								if (i > 2 && i <= 5) {
									ll_wz2.setVisibility(View.VISIBLE);
									ll_wz2.setWeightSum(3);
									ll_wz2.addView(button);
								}
								if (i > 5 && i <= 8) {
									ll_wz3.setVisibility(View.VISIBLE);
									ll_wz3.setWeightSum(3);
									ll_wz3.addView(button);
								}
								if (i > 8 && i <= 11) {
									ll_wz4.setVisibility(View.VISIBLE);
									ll_wz4.setWeightSum(3);
									ll_wz4.addView(button);
								}
								if (!flag_frist_act) {
									if (list_wz_act.contains(dicList_wz.get(i)
											.getCode())) {
										button.setSelected(true);
									}
								}
								if (flag_frist_act) {
									button.setSelected(true);
								}
							}
//							if (ll_wz1.VISIBLE == View.VISIBLE
//									&& ll_wz1.getChildCount() != 0) {
//								if (flag_frist_act) {
//									ll_wz1.getChildAt(0).setSelected(true);
//								}
//							}
						}
					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * 得到类型的数据字典
	 * 
	 * @param dicttype
	 */
	private void getLXShuJuZiDian(String dicttype) {
		Dicttype bean = new Dicttype();
		bean.dicttype = dicttype;
		AnsynHttpRequest.request(getActivity(), bean,
				CHTTP.IB04_GETDICTTYPEOBJECT, CHTTP.POST,
				new ObserverCallBack() {

					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						DicttypeResult dicttypeResult = FastJsonUtils
								.getPerson(successMessage, DicttypeResult.class);
						if (dicttypeResult != null
								&& dicttypeResult.getDictitemlist() != null
								&& dicttypeResult.getDictitemlist().size() != 0) {
							diclist_lx.addAll(dicttypeResult.getDictitemlist());
							// 动态生成按钮
							for (int i = 0; i < diclist_lx.size(); i++) {
								final Button button = new Button(getActivity());
								button.setText(diclist_lx.get(i).getName());
								button.setBackgroundResource(R.drawable.dayreport_shaixuan_button);
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										width, LayoutParams.WRAP_CONTENT);
								button.setLayoutParams(params);
								params.setMargins(20, 0, 20, 0);
								params.gravity = Gravity.CENTER;
								button.setTextSize(12.5f);
								button.setEllipsize(TruncateAt.END);
								button.setSingleLine(true);
								button.setPadding(10, 28, 10, 28);
								button.setGravity(Gravity.CENTER);
								button.setTextColor(Color.WHITE);
								button.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										if (button.isSelected()) {
											button.setSelected(false);
										} else {
											button.setSelected(true);
										}
									}
								});
								// 如果第一次筛选则所有按钮默认选中
								if (flag_frist_act) {
									button.setSelected(true);
								} else if (!flag_frist_act
										&& !SBLX_act.equals("1")) {// 如果不是第一次筛选上个界面传回来的按钮默认选中
									if (list_lx_act.contains(diclist_lx.get(i)
											.getCode())) {
										button.setSelected(true);
									}
								}
								if (SBLX_act.equals("1")) {
									button.setSelected(true);
								}
								if (i <= 2) {
									ll_lx1.setVisibility(View.VISIBLE);
									ll_lx1.setWeightSum(3);
									ll_lx1.addView(button);
								}
								if (i > 2 && i <= 5) {
									ll_lx2.setVisibility(View.VISIBLE);
									ll_lx2.setWeightSum(3);
									ll_lx2.addView(button);
								}
								if (i > 5 && i <= 8) {
									ll_lx3.setVisibility(View.VISIBLE);
									ll_lx3.setWeightSum(3);
									ll_lx3.addView(button);
								}
								if (i > 8 && i <= 11) {
									ll_lx4.setVisibility(View.VISIBLE);
									ll_lx4.setWeightSum(3);
									ll_lx4.addView(button);
								}
							}
							SBLX_act = "1";
						}

					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
