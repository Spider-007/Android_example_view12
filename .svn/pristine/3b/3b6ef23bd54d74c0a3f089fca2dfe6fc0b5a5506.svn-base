package com.htmitech.ztcustom.zt.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.TanShangProduceDetialAdapter;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.constant.ParameterList;
import com.htmitech.ztcustom.zt.constant.ProduceProgressParmas;
import com.htmitech.ztcustom.zt.constant.TanShangProgressDetialTable;
import com.htmitech.ztcustom.zt.constant.TanShangpProgressDetailRoot;
import com.htmitech.ztcustom.zt.constant.XiangQingBean;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;
import com.htmitech.ztcustom.zt.util.ZTUnit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TanshangProduceDetailsFragment extends Fragment {

	// 取到的全部参数列表
	private ArrayList<ParameterList> list = new ArrayList<ParameterList>();
	private String reportID = "c49524bd-bfaf-42fa-8a39-49173b4891ef";
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH) + 1;
	private ListView lv_detials;
	private TextView tv_detials;
	int num;
	TextView tv_1;
	TextView tv_2;
	TextView tv_3;
	TextView tv_4;
	private ArrayList<String> list_wz = new ArrayList<String>();
	private ArrayList<String> list_lx = new ArrayList<String>();
	private String Sblx = "GC";
	private String userID;
	private String date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		num = getArguments().getInt("position");
		userID = getArguments().getString("userID");
		Sblx = getArguments().getString("Sblx");
		list_wz.addAll(getArguments().getStringArrayList("list_wz"));
		list_lx.addAll(getArguments().getStringArrayList("list_lx"));
		date = getArguments().getString("date");
		View view = inflater.inflate(
				R.layout.fragment_tanshang_progress_details, null, false);
		lv_detials = (ListView) view
				.findViewById(R.id.lv_tanshangfragment_details);
		lv_detials.setClickable(false);
		// lv_detials.setDivider(new ColorDrawable(Color.argb(255, 0x00, 0x80,
		// 0xfc)));
		// lv_detials.setDividerHeight(6);
		tv_detials = (TextView) view.findViewById(R.id.tv_fragment_tsd);
		tv_1 = (TextView) view.findViewById(R.id.tv_detialsitem_1);
		tv_2 = (TextView) view.findViewById(R.id.tv_detialsitem_2);
		tv_3 = (TextView) view.findViewById(R.id.tv_detialsitem_3);
		tv_4 = (TextView) view.findViewById(R.id.tv_detialsitem_4);
		if (num == 2) {
			tv_2.setText("月实施计划数");
			tv_3.setText("月计划数");
			tv_4.setText("月度计划安排率");
		} else if (num == 1) {
			tv_2.setText("年累完成日报数");
			tv_3.setText("全年计划数");
			tv_4.setText("全年计划完成率");
		} else if (num == 0) {
			tv_2.setText("年累实施计划数");
			tv_3.setText("全年计划数");
			tv_4.setText("全年计划安排率");
		} else if (num == 3) {
			tv_2.setText("月完成数");
			tv_3.setText("月实施计划数");
			tv_4.setText("月度完成率");
		} else if (num == 4) {
			tv_2.setText("年累实施计划数");
			tv_3.setText("年累计划数");
			tv_4.setText("年累安排率");
		} else if (num == 5) {
			tv_2.setText("年累完成日报数");
			tv_3.setText("年累实施计划数");
			tv_4.setText("年累完成率");
		}
		initData();
		return view;
	}

	private void initData() {
		getContentValues(reportID);
	}

	// 获取详细参数
	private void getContentValues(String reportID) {

		AnsynHttpRequest.request(getActivity(), null, ContantValues.GETVALUES
				+ reportID, CHTTP.GET, new ObserverCallBack() {

			@Override
			public void success(String requestValue) {
				// TODO Auto-generated method stub
				list.clear();
				XiangQingBean bean = FastJsonUtils.getPerson(requestValue,
						XiangQingBean.class);
				if (bean.getResult() != null) {
					list.addAll(bean.getResult());
				}
				getAllData(list_lx, list_wz);
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

	private void getAllData(ArrayList<String> list_lx, ArrayList<String> list_wz) {
		ProduceProgressParmas entity = new ProduceProgressParmas();
		entity.setReportGuid(reportID);
		list_wz = ZTUnit.getSumitemValue((ArrayList<String>) list_wz);
		list_lx = ZTUnit.getSumitemValue((ArrayList<String>) list_lx);
		for (ParameterList parameterList : list) {
			if (parameterList.getName().equals("sblx_sub_list")) {
				parameterList.setValues(list_lx);
			} else if (parameterList.getName().equals("line_list")) {
				parameterList.setValues(list_wz);
			} else if (parameterList.getName().equals("userid")) {
				ArrayList<String> userid_list = new ArrayList<String>();
				userid_list.add(userID);
				parameterList.setValues(userid_list);
			} else if (parameterList.getName().equals("month")) {
				ArrayList<String> month_list = new ArrayList<String>();
				month_list.add(date);
				parameterList.setValues(month_list);
			} else if (parameterList.getName().equals("sblx")) {
				ArrayList<String> sblx_list = new ArrayList<String>();
				sblx_list.add(Sblx);
				parameterList.setValues(sblx_list);
			}
		}
		entity.setParameterList(list);
		AnsynHttpRequest.request(getActivity(), entity,
				ContantValues.TANSHANG_PRODUCE_YEAR, CHTTP.POST,
				new ObserverCallBack() {

					@Override
					public void success(String requestValue) {
						// TODO Auto-generated method stub
						Log.e("DETIALS", requestValue);

						TanShangpProgressDetailRoot root = FastJsonUtils
								.getPerson(requestValue,
										TanShangpProgressDetailRoot.class);
						if (root != null && root.getResult() != null
								&& root.getResult().getListDataSet()
										.getTable1() != null
								&& root.getResult().getListDataSet()
										.getTable1().size() != 0) {
							List<TanShangProgressDetialTable> table_list = root
									.getResult().getListDataSet().getTable1();
							lv_detials
									.setAdapter(new TanShangProduceDetialAdapter(
											table_list, getActivity(), num,
											tv_detials));
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
}
