package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.CheJianDayReportTableAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.CheJianDayReportParmeters;
import com.htmitech.ztcustom.zt.constant.CheJianDayReportResultBean;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author heyang 车间探伤Activity
 * 
 */
public class CheJianDayReportActivity extends BaseFragmentActivity implements
		OnClickListener {

	// 单位日报解析数据总体list
	private List<CheJianDayReportResultBean> cheJianList = new ArrayList<CheJianDayReportResultBean>();
	private int year, month, day, position;
	private String orgid;
	private ListView lv_CheJianDayReport;
	private TextView tv_name;
	private TextView tv_date;
	private ImageButton ib_back;
	private ImageButton ib_search;
	private ArrayList<String> listname = new ArrayList<String>();
	RelativeLayout mHead;
	private LinearLayout ll_top_search;
	private EditText et_search;
	private TextView tv_search_cancle;
	private TextView tv_search_submit;
	private String searchString;
	private List<String> list_wz;
	private List<String> list_lx;
	private String Sblx;
	private String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_che_jian_day_report);
		//    <--------------------Administrator -> 2019-8-13:20:38: 拿到缓存 使用getcisAccount的userID->请求接口赋值--------------------->
//		userID = ZTCustomInit.get().getmCache().getmListDetails().AccountId;
		userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
		Log.e("YJH", "onCreate->cisAccountId"+userID);
		Intent intent = getIntent();
		orgid = intent.getStringExtra("orgid");
		year = intent.getIntExtra("year", 0);
		month = intent.getIntExtra("month", 0);
		day = intent.getIntExtra("day", 0);
		listname = intent.getStringArrayListExtra("listname");
		position = intent.getIntExtra("position", -1);
		list_wz = intent.getStringArrayListExtra("list_wz");
		list_lx = intent.getStringArrayListExtra("list_lx");
		// Log.e("WZ", list_wz.get(0).toString());

		Sblx = intent.getStringExtra("Sblx");
		Intent intent1 = new Intent();
		intent1.putExtra("position", position);
		setResult(201, intent1);
		initView();
		initData();
	}

	private void initView() {
		lv_CheJianDayReport = (ListView) findViewById(R.id.lv_chejiandayreport_table);
		lv_CheJianDayReport
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		tv_name = (TextView) findViewById(R.id.tv_chejiadayreport_nname);
		tv_date = (TextView) findViewById(R.id.tv_chejiandayreport_title_data);
		ib_back = (ImageButton) findViewById(R.id.ib_chejiandayreport_back);
		ib_search = (ImageButton) findViewById(R.id.ib_chejiandayreport_search);
		ib_back.setOnClickListener(this);
		ib_search.setOnClickListener(this);
		mHead = (RelativeLayout) findViewById(R.id.i_chejianhead);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setBackgroundColor(Color.parseColor("#0080FC"));
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		ll_top_search = (LinearLayout) findViewById(R.id.ll_chejiandayreport_search_top);
		et_search = (EditText) findViewById(R.id.et_chejiandayreport_search);
		tv_search_cancle = (TextView) findViewById(R.id.tv_chejiandayreport_cancle);
		tv_search_cancle.setOnClickListener(this);
		tv_search_submit = (TextView) findViewById(R.id.tv_chejiandayreport_submit);
		tv_search_submit.setOnClickListener(this);
	}

	private void initData() {
		String days = day + "";
		String months = month + "";
		if (day < 10) {
			days = "0" + day;
		}
		if (month < 10) {
			months = "0" + month;
		}
		tv_date.setText(year + "年" + months + "月" + days + "日");
		String name = "";
		for (int i = 0; i < listname.size(); i++) {
			if (i == (listname.size() - 1)) {
				if (listname.get(i) != null) {
					name = name + listname.get(i);
				}
			} else {
				if (listname.get(i) != null) {
					name = name + listname.get(i) + ">";
				}
			}
		}
		tv_name.setText(name);
		// 获取车间的日报
		showProgressDialog(CheJianDayReportActivity.this);
		getCheJianData(orgid, year, month, day, list_wz, list_lx, Sblx);
	}

	/**
	 * 车间探伤日报
	 */
	private void getCheJianData(String cjID, int year, int month, int day,
			List<String> list_wz, List<String> list_lx, String Sblx) {
		CheJianDayReportParmeters entity = new CheJianDayReportParmeters();
		entity.setUserid(userID);
		entity.setWorkday(year + "-" + month + "-" + day);
		entity.setCjid(cjID);
		if (list_wz == null) {
			List<String> list = new ArrayList<String>();
			list.add("");
			entity.setWzlx(list);
		} else {
			entity.setWzlx(list_wz);
		}
		entity.setSblx(Sblx);
		if (list_lx == null) {
			List<String> list = new ArrayList<String>();
			list.add("");
			entity.setSblxmx(list);
		} else {
			entity.setSblxmx(list_lx);
		}
		entity.setSearch("");
		AnsynHttpRequest.request(this, entity, ContantValues.CHEJIANDAYREPORT,
				CHTTP.POST, new ObserverCallBack() {

					@Override
					public void success(String requestValue) {
						// TODO Auto-generated method stub
//						Log.e("RESULT", requestValue);
						List<CheJianDayReportResultBean> cheJianListlinshi = new ArrayList<CheJianDayReportResultBean>();
						cheJianListlinshi = FastJsonUtils.getPersonList(
								requestValue, CheJianDayReportResultBean.class);
						dimssDialog();
						if (cheJianListlinshi != null
								&& cheJianListlinshi.size() != 0) {
							if (cheJianList != null) {
								cheJianList.clear();
							}
							cheJianList.addAll(cheJianListlinshi);
							lv_CheJianDayReport
									.setAdapter(new CheJianDayReportTableAdapter(
											cheJianList,
											CheJianDayReportActivity.this,
											mHead));
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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.ib_chejiandayreport_back ){
			this.finish();
		}else if(arg0.getId() ==R.id.ib_chejiandayreport_search){
			ll_top_search.setVisibility(View.VISIBLE);
		}else if(arg0.getId() ==R.id.tv_chejiandayreport_cancle){
			ll_top_search.setVisibility(View.GONE);
		}else if(arg0.getId() ==R.id.tv_chejiandayreport_submit){
			if (et_search.getText() == null) {
				Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
			} else {
				ll_top_search.setVisibility(View.GONE);
				// 做判斷如果當前的chejianlist中含有搜索的所有條件就留下
				searchString = et_search.getText().toString();
				List<CheJianDayReportResultBean> cheJianListll = new ArrayList<CheJianDayReportResultBean>();
				cheJianListll.addAll(cheJianList);
				String[] a = searchString.split(" ");
				for (int i = 0; i < cheJianList.size(); i++) {
					for (int j = 0; j < a.length; j++) {
						if (!cheJianList.get(i).toString().contains(a[j])) {
							cheJianListll.remove(cheJianList.get(i));
							break;
						}
					}
				}
				lv_CheJianDayReport
						.setAdapter(new CheJianDayReportTableAdapter(
								cheJianListll, CheJianDayReportActivity.this,
								mHead));
			}
		}
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			return false;
		}
	}

}
