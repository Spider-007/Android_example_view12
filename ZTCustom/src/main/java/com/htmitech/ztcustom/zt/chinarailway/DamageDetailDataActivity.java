package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.DamageDetailDataAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.DamageSummar;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.ParameterList;
import com.htmitech.ztcustom.zt.domain.Table;
import com.htmitech.ztcustom.zt.domain.damage.Users;
import com.htmitech.ztcustom.zt.enums.DicttypeEnum;
import com.htmitech.ztcustom.zt.enums.TransType;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.DamageUtil;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.ToastUtil;
import htmitech.com.componentlibrary.unit.Utils;

public class DamageDetailDataActivity extends BaseFragmentActivity implements
		OnClickListener {
	private ListView tv_detail_data;
	private LinearLayout layout_addview;
	private RelativeLayout mHead;
	private String dateTime;
	private TextView tv_title_data;
	private TransType mTransType;
	private String reportGuid = "";
	private String code = "";
	private ArrayList<Table> tableList;
	private ArrayList<ArrayList<Table>> twoList;
	private ArrayList<String> sumitemList;
	private String startTime, endTime;
	private ImageView back;
	private TextView titleName;
	private ImageView function;
	private TextView unit;
	private boolean isShare = false;
	private String appUrl = "";
	private LinearLayout layout_time;
	private String shareTYpe = "";
	private String userId = "";
	private ArrayList<Dictitemlist> dictitemlist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zt_fragment_detail_data);
		Intent intent = getIntent();
		isShare = intent.getBooleanExtra("flag_share", false);
		if (isShare) {
			appUrl = intent.getStringExtra("share");

		}
		mTransType = (TransType) intent.getSerializableExtra("type");
		twoList = new ArrayList<ArrayList<Table>>();
		dateTime = intent.getStringExtra("dateTime");
		code = intent.getStringExtra("code");
		startTime = intent.getStringExtra("startTime");
		endTime = intent.getStringExtra("endTime");
		sumitemList = new ArrayList<String>();
		initView();
		initData();
		unit.setText("单位");
	}

	public void initView() {
		tv_detail_data = (ListView) findViewById(R.id.tv_detail_data);
		layout_addview = (LinearLayout) findViewById(R.id.layout_addview);
		mHead = (RelativeLayout) findViewById(R.id.head);
		tv_title_data = (TextView) findViewById(R.id.tv_title_data);
		back = (ImageView) findViewById(R.id.ibn_fn5_back);
		titleName = (TextView) findViewById(R.id.tv_fn5_title_name);
		function = (ImageView) findViewById(R.id.function);
		unit = (TextView) findViewById(R.id.unit);
		layout_time = (LinearLayout) findViewById(R.id.layout_time);
	}

	public void initData() {
		back.setOnClickListener(this);
		menuWindow = new FunctionPopupWindow(this, this);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		function.setOnClickListener(this);
		tv_title_data.setText("" + dateTime);
		if (code != null && !code.equals("") && !code.equals("null")) {
			titleName.setText("设备伤损情况汇总-" + DicttypeEnum.getName(code));
		} else {
			titleName.setText("设备伤损情况汇总");
		}
		if (isShare) {
			function.setVisibility(View.GONE);
		}
		mHead.setBackgroundColor(getResources().getColor(R.color.shebei));
		tv_detail_data
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		showProgressDialog(this);
		switch (mTransType) {
		case DAMAGE:
			shareTYpe = "UW";
			array = ZTCustomInit.get().getmCache().getmReportParametersCate().getResult();
			reportGuid = CHTTP.REPORTGUIDALL;
			break;
		case DAMAGE_QT:
			shareTYpe = "VW";
			DicttypeResult mDicttypeResult = ZTCustomInit.get().getmCache()
					.getmDicttypeResult().get(code);
			if (mDicttypeResult != null) {
				for (Dictitemlist dict : mDicttypeResult.dictitemlist) {
					sumitemList.add(dict.name);
				}
			}
			reportGuid = CHTTP.REPORTGUID_FENLEI;
			array = ZTCustomInit.get().getmCache().getmReportParametersAll().getResult();
			break;
		case DAMAGE_TYPE_SSLX:
			shareTYpe = "VM";
			sumitemList.addAll(DicttypeEnum.getCodes());
			reportGuid = CHTTP.REPORTGUID_FENLEI;
			array = ZTCustomInit.get().getmCache().getmReportParametersAll().getResult();
			break;
		}
		AnsynHttpRequest.request(this, updateDate(),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						JSONObject mJSONObject = JSON
								.parseObject(successMessage);
						JSONObject Result = mJSONObject.getJSONObject("Result");
						if (Result == null) {
							ToastUtil.showShort(DamageDetailDataActivity.this,"result返回值为空");
							return;
						}
						JSONObject ListDataSet = Result
								.getJSONObject("ListDataSet");
						JSONArray Table1List = ListDataSet
								.getJSONArray("Table1");
						for (int i = 0; i < Table1List.size(); i++) {
							tableList = new ArrayList<Table>();
							JSONObject object = Table1List.getJSONObject(i);
							ArrayList<Dictitemlist> list = null;
							if (i == 0) {
								layout_addview.addView(addView1("合计"), 0);
							}
							if (shareTYpe.equals("UW")) { // 首页进入
								tableList.add(
										0,
										new Table(
												"value",
												((int) Float.parseFloat(object
														.getString("value")) + "")));
							} else {// 非首页进入
								tableList
										.add(0,
												new Table(
														"totalvalue",
														((int) Float
																.parseFloat(object
																		.getString("totalvalue")) + "")));
							}

							if (ZTCustomInit.get().getmCache().getmDicttypeResult().get(code) != null) {
								list = ZTCustomInit.get().getmCache().getmDicttypeResult()
										.get(code).getDictitemlist();
							} else {
								if (isShare) {
									list = dictitemlist;
								}
							}
							if (list != null) {
								for (Dictitemlist mDictitemlist : list) {
									if (i == 0) {
										layout_addview
												.addView(addView1(mDictitemlist.name));
									}
									tableList.add(new Table(
											mDictitemlist.name,
											(int) Float.parseFloat(object
													.getString(mDictitemlist.code))
													+ ""));
								}
							}
							tableList.add(new Table("shortname", object
									.getString("shortname")));

							twoList.add(tableList);
						}
						DamageDetailDataAdapter mDamageDetailDataAdapter = new DamageDetailDataAdapter(
								DamageDetailDataActivity.this, twoList, mHead);
						tv_detail_data.setAdapter(mDamageDetailDataAdapter);
						dimssDialog();
					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub
						dimssDialog();
					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub
						dimssDialog();
						Utils.toast(DamageDetailDataActivity.this, "请求错误"+exceptionMessage, Toast.LENGTH_SHORT);
					}
				});
		mDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (e2.getRawX() - e1.getRawX() > 200) {
							// 右滑
							finish();
						}
						// if (e1.getRawX() - e2.getRawX() > 200 &&
						// M_SIDE_BACK_STATE) {
						// //左滑
						// }
						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	GestureDetector mDetector;

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

	private View addView1(String titleName) {
		// TODO 动态添加布局(xml方式)
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// LayoutInflater
		// inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// LayoutInflater inflater2 = getLayoutInflater();
		LayoutInflater inflater3 = LayoutInflater.from(this);
		View view = inflater3.inflate(
				R.layout.zt_fragment_detail_data_item_text, null);
		TextView tv_detail = (TextView) view.findViewById(R.id.tv_detail);
		tv_detail.setText(titleName);
		view.setLayoutParams(lp);
		return view;

	}

	ArrayList<ParameterList> array;

	public DamageSummar updateDate() {

		DamageSummar mDamageSummar = new DamageSummar();
		if (array == null) {
			return mDamageSummar;
		}
		//    <--------------------Administrator -> 2019-8-16:14:53:添加 ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId--------------------->
		userId = DamageUtil.damageUtil(share, isShare, array, sumitemList,
				startTime, code, ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId,
				endTime,ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId , appUrl);
//		ZTCustomInit.get().getmCache().getmListDetails().AccountId
		Users object = new Users(userId);
		String url = CHTTP.IB05_GETSSLXLIST;
		AnsynHttpRequest.request(this, object, url, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						dictitemlist = (ArrayList<Dictitemlist>) JSON
								.parseArray(successMessage, Dictitemlist.class);

					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated
						// method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated
						// method stub
					}
				});
		share.append(shareTYpe);
		share.append("|" + dateTime);
		share.append("|" + code + "|");
		mDamageSummar.setParameterList(array);
		mDamageSummar.setReportGuid(reportGuid);
		return mDamageSummar;
	}

	StringBuffer share = new StringBuffer();
	FunctionPopupWindow menuWindow;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.ibn_fn5_back ){
			this.finish();
		}else if(arg0.getId() ==R.id.function){
			if (!menuWindow.isShowing()) {
				menuWindow = new FunctionPopupWindow(this, this);
				menuWindow.setLinearVisibility();
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi);
				int popupWidth = menuWindow.mMenuView.getMeasuredWidth();
				int popupHeight = menuWindow.mMenuView.getMeasuredHeight();

				int[] location = new int[2];
				function.getLocationOnScreen(location);
				// 显示窗口
				menuWindow.showAtLocation(function, Gravity.NO_GRAVITY,
						(location[0] + function.getWidth() / 2) - popupWidth
								/ 2, location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
				menuWindow.update();
			} else {
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
				menuWindow.dismiss();
			}
		}else if(arg0.getId() ==R.id.iv_share){
			String shareString = share.toString();
			shareString = shareString.substring(0, shareString.length() - 1);
			ShareUnit
					.ShareListener(
							this,
							"分享" + titleName.getText().toString(),
							"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
							shareString, "WW");
		}

	}

}
