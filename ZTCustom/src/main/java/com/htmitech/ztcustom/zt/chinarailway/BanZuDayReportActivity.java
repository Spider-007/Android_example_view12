package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.BanZuDayReportTableAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.constant.BanZuDayReportBean;
import com.htmitech.ztcustom.zt.constant.BanZuDayReportRequest;
import com.htmitech.ztcustom.zt.constant.BanZuDayReportRoot;
import com.htmitech.ztcustom.zt.constant.BanZuItemBean;
import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.FastJsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 探伤工作日报动态
 */
public class BanZuDayReportActivity extends BaseFragmentActivity implements
		OnClickListener {

	// 单位日报解析数据总体list
	private List<BanZuDayReportRoot> BanZuList = new ArrayList<BanZuDayReportRoot>();
	private int year, month, day, position;
	private String orgid;
	private ListView lv_CheJianDayReport;
	private TextView tv_name;
	private TextView tv_date;
	private ImageButton ib_back;
	private ImageButton ib_search;
	private ArrayList<String> listname = new ArrayList<String>();
	// RelativeLayout mHead;
	View mHead;
	private LinearLayout ll_top_search;
	private EditText et_search;
	private TextView tv_search_cancle;
	private TextView tv_search_submit;
	private String searchString;
	private List<String> list_wz = new ArrayList<String>();
	private List<String> list_lx;
	private String Sblx;
	private String userID;
	private String search = "";
	private LinearLayout ll_layout_sum;
	ArrayList<BanZuDayReportBean> array = new ArrayList<BanZuDayReportBean>();
	private TextView tv_banzu_name;
	private ImageView iv_i;
	private FunctionPopupWindow functionPopWindow;
	private String apiUrlTemp;

	private boolean isShare = false;// 判断是否是分享的界面
	private String shareParams;// 分享后的参数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ban_zu_day_report);

		Intent intent = getIntent();
		isShare = intent.getBooleanExtra("flag_share", false);
		shareParams = intent.getStringExtra("share");

		userID = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
		if (!isShare) {
			orgid = intent.getStringExtra("orgid");
			year = intent.getIntExtra("year", 0);
			month = intent.getIntExtra("month", 0);
			day = intent.getIntExtra("day", 0);
			listname = intent.getStringArrayListExtra("listname");
			position = intent.getIntExtra("position", -1);
			list_wz.addAll(intent.getStringArrayListExtra("list_wz"));
			list_lx = intent.getStringArrayListExtra("list_lx");
			Sblx = intent.getStringExtra("Sblx");
		}
		Intent intent1 = new Intent();
		intent1.putExtra("position", position);
		setResult(201, intent1);
		initView();
		initData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ban_zu_day_report, menu);
		return true;
	}

	private void initView() {
		lv_CheJianDayReport = (ListView) findViewById(R.id.lv_banzu_table);
		lv_CheJianDayReport
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		tv_name = (TextView) findViewById(R.id.tv_banzu_nname);
		tv_date = (TextView) findViewById(R.id.tv_banzu_title_data);
		ib_back = (ImageButton) findViewById(R.id.ib_banzu_back);
		ib_search = (ImageButton) findViewById(R.id.ib_banzu_search);
		tv_banzu_name = (TextView) findViewById(R.id.tv_banzuitem_banzu);
		ib_back.setOnClickListener(this);
		ib_search.setOnClickListener(this);
		ll_layout_sum = (LinearLayout) findViewById(R.id.banzu_ll_layout_item);
		mHead = LayoutInflater.from(this)
				.inflate(R.layout.banzu_all_item, null);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setBackgroundColor(Color.parseColor("#0080FC"));
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		tv_banzu_name.setBackgroundColor(Color.parseColor("#0080FC"));
		ll_top_search = (LinearLayout) findViewById(R.id.ll_banzu_search_top);
		et_search = (EditText) findViewById(R.id.et_banzu_search);
		tv_search_cancle = (TextView) findViewById(R.id.tv_banzu_cancle);
		tv_search_cancle.setOnClickListener(this);
		tv_search_submit = (TextView) findViewById(R.id.tv_banzu_submit);
		tv_search_submit.setOnClickListener(this);
		ll_layout_sum.addView(mHead);
		iv_i = (ImageView) findViewById(R.id.iv_banzu_i);
		iv_i.setOnClickListener(this);
	}

	private void initData() {
		functionPopWindow = new FunctionPopupWindow(this, this);
		if (!isShare) {
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
		}
		// 获取车间的日报
		showProgressDialog(BanZuDayReportActivity.this);
		getBanZuDayReport(orgid, year, month, day, list_wz, search);
	}

	/**
	 * 班组探伤日报
	 */
	private void getBanZuDayReport(String orgid, int year, int month, int day,
			List<String> list_wz, String search) {
		BanZuDayReportRequest entity = new BanZuDayReportRequest();
		if (!isShare) {
			entity.setUserid(userID);
			entity.setWorkday(year + "-" + month + "-" + day);
			entity.setGq_orgid(orgid);
			if (list_wz == null) {
				List<String> list = new ArrayList<String>();
				list.add("");
				entity.setWzlx(list);
			} else {
				entity.setWzlx(list_wz);
			}
			entity.setSearch("");
		} else if (isShare) {
			list_wz.clear();
			String[] ss = shareParams.split("\\|");
			entity.setUserid(ss[0]);
			entity.setWorkday(ss[1]);
			entity.setGq_orgid(ss[2]);
			if (ss[3].equalsIgnoreCase("share")) {
				list_wz.add("");
				entity.setWzlx(list_wz);
			} else {
				String[] listwz_ss = ss[3].split(",");
				for (int i = 0; i < listwz_ss.length; i++) {
					list_wz.add(listwz_ss[i]);
				}
				entity.setWzlx(list_wz);
			}
			if (ss[4].equalsIgnoreCase("share")) {
				entity.setSearch("");
			} else {
				entity.setSearch(ss[4]);
			}
			tv_name.setText(ss[5]);
			tv_date.setText(ss[6]);
			ib_search.setVisibility(View.GONE);
			iv_i.setVisibility(View.GONE);
		}
		AnsynHttpRequest.request(this, entity, ContantValues.GETBANZUDAYREPORT,
				CHTTP.POST, new ObserverCallBack() {

					@Override
					public void success(String requestValue) {
						// TODO Auto-generated method stub
						Log.e("RESULT", requestValue);
						List<BanZuDayReportRoot> banZuDayReportRootList = new ArrayList<BanZuDayReportRoot>();
						banZuDayReportRootList = FastJsonUtils.getPersonList(
								requestValue, BanZuDayReportRoot.class);
						dimssDialog();
						if (banZuDayReportRootList != null
								&& banZuDayReportRootList.size() != 0) {// 判空
							if (BanZuList != null) {
								BanZuList.clear();
							}
							BanZuList.addAll(banZuDayReportRootList);
							String merge_name = "";
							BanZuDayReportBean bean = null;

							for (int i = 0; i < banZuDayReportRootList.size(); i++) {
								ArrayList<BanZuItemBean> itemList = new ArrayList<BanZuItemBean>();
								BanZuDayReportRoot banZuDayReportroot = banZuDayReportRootList
										.get(i);
								if (merge_name.equals(banZuDayReportroot
										.getMerge_name())) {
									BanZuItemBean banZuItemBean = new BanZuItemBean();
									banZuItemBean
											.setType_name(banZuDayReportroot
													.getType_name());
									banZuItemBean
											.setWork_day(banZuDayReportroot
													.getWork_day());
									banZuItemBean
											.setStart_milage(banZuDayReportroot
													.getStart_milage());
									banZuItemBean
											.setEnd_milage(banZuDayReportroot
													.getEnd_milage());
									banZuItemBean.setGdh(banZuDayReportroot
											.getGdh());
									banZuItemBean.setDch(banZuDayReportroot
											.getDch());
									banZuItemBean.setGg_gg(banZuDayReportroot
											.getGg_gg());
									banZuItemBean.setHf_csgh(banZuDayReportroot
											.getHf_csgh());
									banZuItemBean.setHf_jghf(banZuDayReportroot
											.getHf_jghf());
									banZuItemBean.setHf_lrh(banZuDayReportroot
											.getHf_lrh());
									banZuItemBean.setHf_xsgh(banZuDayReportroot
											.getHf_xsgh());
									banZuItemBean.setDc_gmgc(banZuDayReportroot
											.getDc_gmgc());
									banZuItemBean.setDc_hjgc(banZuDayReportroot
											.getDc_hjgc());
									banZuItemBean.setDc_kdxc(banZuDayReportroot
											.getDc_kdxc());
									banZuItemBean.setWwcyy(banZuDayReportroot
											.getWwcyy());
									banZuItemBean.setGznr(banZuDayReportroot
											.getGznr());
									banZuItemBean
											.setMilage_start_end(banZuDayReportroot
													.getMilage_start_end());
									bean.getBanzuitem().add(banZuItemBean);
								} else {
									if (itemList != null) {
										itemList.clear();
									}
									merge_name = banZuDayReportroot
											.getMerge_name();
									bean = new BanZuDayReportBean();
									bean.setMerge_name(merge_name);
									BanZuItemBean banZuItemBean = new BanZuItemBean();
									banZuItemBean
											.setType_name(banZuDayReportroot
													.getType_name());
									banZuItemBean
											.setWork_day(banZuDayReportroot
													.getWork_day());
									banZuItemBean
											.setStart_milage(banZuDayReportroot
													.getStart_milage());
									banZuItemBean
											.setEnd_milage(banZuDayReportroot
													.getEnd_milage());
									banZuItemBean.setGdh(banZuDayReportroot
											.getGdh());
									banZuItemBean.setDch(banZuDayReportroot
											.getDch());
									banZuItemBean.setGg_gg(banZuDayReportroot
											.getGg_gg());
									banZuItemBean.setHf_csgh(banZuDayReportroot
											.getHf_csgh());
									banZuItemBean.setHf_jghf(banZuDayReportroot
											.getHf_jghf());
									banZuItemBean.setHf_lrh(banZuDayReportroot
											.getHf_lrh());
									banZuItemBean.setHf_xsgh(banZuDayReportroot
											.getHf_xsgh());
									banZuItemBean.setDc_gmgc(banZuDayReportroot
											.getDc_gmgc());
									banZuItemBean.setDc_hjgc(banZuDayReportroot
											.getDc_hjgc());
									banZuItemBean.setDc_kdxc(banZuDayReportroot
											.getDc_kdxc());
									banZuItemBean.setGznr(banZuDayReportroot
											.getGznr());
									banZuItemBean.setWwcyy(banZuDayReportroot
											.getWwcyy());
									banZuItemBean
											.setMilage_start_end(banZuDayReportroot
													.getMilage_start_end());
									itemList.add(banZuItemBean);
									bean.setBanzuitem(itemList);
									array.add(bean);
								}
							}

							// lv_CheJianDayReport
							// .setAdapter(new CheJianDayReportTableAdapter(
							// BanZuList,
							// BanZuDayReportActivity.this, mHead));
							lv_CheJianDayReport
									.setAdapter(new BanZuDayReportTableAdapter(
											array, BanZuDayReportActivity.this,
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
		if(arg0.getId() == R.id.ib_banzu_back){
			finish();
		}else if(arg0.getId() == R.id.ib_banzu_search){
			ll_top_search.setVisibility(View.VISIBLE);
		}else if(arg0.getId() == R.id.tv_banzu_cancle){
			ll_top_search.setVisibility(View.GONE);
		}else if(arg0.getId() == R.id.tv_banzu_submit){
			if (et_search.getText() == null) {
				Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
			} else {
				// ll_top_search.setVisibility(View.GONE);
				// // 做判斷如果當前的chejianlist中含有搜索的所有條件就留下
				// searchString = et_search.getText().toString();
				// List<BanZuDayReportRoot> cheJianListll = new
				// ArrayList<BanZuDayReportRoot>();
				// cheJianListll.addAll(BanZuList);
				// String[] a = searchString.split(" ");
				// for (int i = 0; i < BanZuList.size(); i++) {
				// for (int j = 0; j < a.length; j++) {
				// if (!BanZuList.get(i).toString().contains(a[j])) {
				// cheJianListll.remove(BanZuList.get(i));
				// break;
				// }
				// }
				// }
				// lv_CheJianDayReport
				// .setAdapter(new CheJianDayReportTableAdapter(
				// cheJianListll, BanZuDayReportBean.this,
				// mHead));
				search = et_search.getText() + "";
				getBanZuDayReport(orgid, year, month, day, list_wz, search);
			}
		}else if(arg0.getId() == R.id.iv_banzu_i){
			if (!functionPopWindow.isShowing()) {
				functionPopWindow = new FunctionPopupWindow(this, this);
				functionPopWindow.setLinearVisibility();
				int popupWidth = functionPopWindow.mMenuView.getMeasuredWidth();
				int popupHeight = functionPopWindow.mMenuView
						.getMeasuredHeight();
				iv_i.setBackgroundResource(R.drawable.htmitech_shezhi);
				int[] location = new int[2];
				iv_i.getLocationOnScreen(location);
				// 显示窗口
				functionPopWindow.showAtLocation(iv_i, Gravity.NO_GRAVITY,
						(location[0] + iv_i.getWidth() / 2) - popupWidth / 2,
						location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
				functionPopWindow.update();
			} else {
				iv_i.setBackgroundResource(R.drawable.htmitech_shezhi_1);
				functionPopWindow.dismiss();
			}
		}else if(arg0.getId() == R.id.iv_share){
			shareListener();
		}
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.banzu_horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			return false;
		}
	}

	private void shareListener() {
		String workday = year + "-" + month + "-" + day;
		String date = tv_date.getText().toString();
		String wzlx = "";
		if (search.equals("")) {
			search = "share";
		}
		if (list_wz.size() == 0) {
			apiUrlTemp = userID + "|" + workday + "|" + orgid + "|" + "share"
					+ "|" + search + "|" + tv_name.getText().toString() + "|"
					+ tv_date.getText().toString();
		} else if (list_wz.size() != 0) {
			for (int i = 0; i < list_wz.size(); i++) {
				if (i != list_wz.size() - 1) {
					wzlx += list_wz.get(i) + ",";
				} else {
					wzlx += list_wz.get(i);
				}
			}
			apiUrlTemp = userID + "|" + workday + "|" + orgid + "|" + wzlx
					+ "|" + search + "|" + tv_name.getText().toString() + "|"
					+ tv_date.getText().toString();
		}
		ShareUnit
				.ShareListener(
						this,
						"探伤生产日报",
						"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
						apiUrlTemp, "DD");
	}
}
