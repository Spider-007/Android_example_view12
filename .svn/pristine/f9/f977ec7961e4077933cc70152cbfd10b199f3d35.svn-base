package com.htmitech.ztcustom.zt.chinarailway;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.DamageDetailDataAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.DamageSummar;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.ParameterList;
import com.htmitech.ztcustom.zt.domain.Table;
import com.htmitech.ztcustom.zt.fragment.DynamicFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackScreening;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.ZTUnit;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.ToastUtil;

/**
 * 现存伤损动态排名
 */
public class DynamicDetailDataActivity extends BaseFragmentActivity implements
		OnClickListener, CallBackScreening {
	private ListView tv_detail_data;
	private LinearLayout layout_addview;
	private RelativeLayout mHead;
	private TextView tv_title_data;
	private String reportGuid = "";
	private ArrayList<Table> tableList;
	private ArrayList<ArrayList<Table>> twoList;
	private ArrayList<String> sumitemList;
	private ImageView back;
	private TextView titleName;
	private ArrayList<String> sblx_list = new ArrayList<String>();
	private ArrayList<String> sscd_List = new ArrayList<String>();
	public String pmfs = "DW";
	public String keys = "sum";
	public String top;
	private ArrayList<ParameterList> array;
	private String orgId;
	private LinearLayout ll_popupLayout;
	private TextView iv_shaixuan;
	private DamageDetailDataAdapter mDamageDetailDataAdapter;
	private ImageView function;
	private boolean isShare;
	private String appUrl;
	private String[] appUrlList;
	private ArrayList<Dictitemlist> list = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zt_fragment_dynamic_data);
		Intent intent = getIntent();
		isShare = intent.getBooleanExtra("flag_share", false);
		appUrl = intent.getStringExtra("share");
		if (isShare) {
			appUrlList = appUrl.split("\\|");
		} else {
			sblx_list = intent.getStringArrayListExtra("sblx_list");
			sscd_List = intent.getStringArrayListExtra("sscdList");
			pmfs = intent.getStringExtra("pmfs");
			keys = intent.getStringExtra("keys");
			top = intent.getStringExtra("top");
			orgId = intent.getStringExtra("orgId");
		}
		twoList = new ArrayList<ArrayList<Table>>();
		sumitemList = new ArrayList<String>();
		initView();
		initData();

	}

	@SuppressLint("WrongViewCast")
	public void initView() {
		tv_detail_data = (ListView) findViewById(R.id.tv_detail_data);
		layout_addview = (LinearLayout) findViewById(R.id.layout_addview);
		mHead = (RelativeLayout) findViewById(R.id.head);
		tv_title_data = (TextView) findViewById(R.id.tv_title_data);
		back = (ImageView) findViewById(R.id.ibn_fn5_back);
		ll_popupLayout = (LinearLayout) findViewById(R.id.ll_popupLayout);
		titleName = (TextView) findViewById(R.id.tv_fn5_title_name);
		iv_shaixuan = (TextView) findViewById(R.id.iv_shaixuan);
		function = (ImageView) findViewById(R.id.function);
	}

	public void initData() {
		if (isShare) {
			function.setVisibility(View.GONE);
			iv_shaixuan.setVisibility(View.GONE);
		}
		list = ZTCustomInit.get().getmCache().getDictitemlist();
		back.setOnClickListener(this);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mHead.setBackgroundColor(getResources().getColor(R.color.shebei));
		iv_shaixuan.setOnClickListener(this);
		function.setOnClickListener(this);
		menuWindow = new FunctionPopupWindow(this, this);
		titleName.setText("现存伤损动态排名");
		ll_popupLayout.getBackground().setAlpha(220);

		tv_detail_data
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());


		array = ZTCustomInit.get().getmCache().getmDynamicReportParameters().getResult();
		showProgressDialog(this);
		AnsynHttpRequest.request(this,
				updateDate(array, pmfs, keys, top, orgId),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						JSONObject mJSONObject = JSON
								.parseObject(successMessage);
						JSONObject Result = mJSONObject.getJSONObject("Result");
						if (Result == null) {
							ToastUtil.showShort(DynamicDetailDataActivity.this,"Result为空，请重新操作！");
							return;
						}
						JSONObject ListDataSet = Result
								.getJSONObject("ListDataSet");
						JSONArray Table1List = ListDataSet
								.getJSONArray("Table1");
						for (int i = 0; i < Table1List.size(); i++) {
							tableList = new ArrayList<Table>();
							JSONObject object = Table1List.getJSONObject(i);
							if (i == 0) {
								layout_addview.addView(addView1("合计"),
										0);
							}
							tableList.add(0,new Table("totalvalue", ((int) Float.parseFloat(object.getString("totalvalue"))+"")));
							for(Dictitemlist mDictitemlist :list){
								if (i == 0){
									layout_addview.addView(addView1(mDictitemlist.name));
								}
								tableList.add(new Table(mDictitemlist.name, (int) Float
										.parseFloat(object.getString(mDictitemlist.code)) + ""));
							}
							tableList.add(new Table("shortname", object.getString("shortname")));
//							for (Map.Entry<String, Object> entry : object
//									.entrySet()) {
//								String key = entry.getKey();
//								
//								String name = ZTUnit.getDictitemlistName(list,
//										key);
//								String value = entry.getValue() == null ? ""
//										: entry.getValue().toString();
//								if (i == 0) {
//									if (!TextUtils.isEmpty(name)) {
//										layout_addview.addView(addView1(name));
//									} else if (key.equals("totalvalue")
//											|| key.equals("value")) {
//										layout_addview.addView(addView1("合计"),
//												0);
//									} else if (!key.equals("order")
//											&& !key.equals("sum")
//											&& !key.equals("orgid")
//											&& !key.equals("sumclass")
//											&& !key.equals("shortname")
//											&& !key.equals("ranktype")) {
//										layout_addview.addView(addView1(key));
//									}
//								}
//								if (!TextUtils.isEmpty(name)) {
//									tableList.add(new Table(name, (int) Float
//											.parseFloat(value) + ""));
//								} else if (key.equals("totalvalue")
//										|| key.equals("value")) {
//									tableList.add(
//											0,
//											new Table(key, (int) Float
//													.parseFloat(value) + ""));
//								} else if (!key.equals("order")
//										&& !key.equals("sum")
//										&& !key.equals("orgid")
//										&& !key.equals("sumclass")
//										&& !key.equals("ranktype")) {
//									tableList.add(new Table(key, value));
//								}
//							}
							twoList.add(tableList);
						}
						mDamageDetailDataAdapter = new DamageDetailDataAdapter(
								DynamicDetailDataActivity.this, twoList, mHead);
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
					}
				});

		DynamicFragment mDynamicFragment = new DynamicFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.content, mDynamicFragment);
		transaction.commit();
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

	StringBuffer share = new StringBuffer();

	public DamageSummar updateDate(ArrayList<ParameterList> array,
								   String ranktype, String orderby, String top, String orgid) {
		DamageSummar mDamageSummar = new DamageSummar();
		if (isShare) {
			int index = 0;
			for (ParameterList mParameterList : array) {
				mParameterList.getValues().clear();
				if (mParameterList.getName().equals("sblx_list")) {

					sblx_list.clear();
					String[] ss = appUrlList[index].split(",");
					for (int i = 0; i < ss.length; i++) {
						sblx_list.add(ss[i]);
					}
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sblx_list));
				} else if (mParameterList.getName().equals("sscd_list")) {
					sscd_List.clear();
					String[] ss = appUrlList[index].split(",");
					for (int i = 0; i < ss.length; i++) {
						sscd_List.add(ss[i]);
					}
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sscd_List));
				} else if (mParameterList.getName().equals("ranktype")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("orderby")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("userid")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("orgid")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("top")) {
					mParameterList.getValues().add(appUrlList[index]);
				}
				index++;
			}
		} else {
			for (ParameterList mParameterList : array) {
				mParameterList.getValues().clear();
				if (mParameterList.getName().equals("sblx_list")) {
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sblx_list));

					for (int i = 0; i < sblx_list.size(); i++) {
						if (sblx_list.size() == 1) {
							share.append("" + sblx_list.get(i));
						} else if (i < sblx_list.size() - 1) {
							share.append(sblx_list.get(i) + ",");
						} else {
							share.append(sblx_list.get(i));
						}
					}
					share.append("|");
				} else if (mParameterList.getName().equals("sscd_list")) {
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sscd_List));
					for (int i = 0; i < sscd_List.size(); i++) {
						if (sblx_list.size() == 1) {
							share.append("" + sscd_List.get(i));
						} else if (i < sscd_List.size() - 1) {
							share.append(sscd_List.get(i) + ",");
						} else {
							share.append(sscd_List.get(i));
						}
					}
					if (sscd_List == null || sscd_List.size() == 0) {
						share.append("share");
					}
					share.append("|");
				} else if (mParameterList.getName().equals("ranktype")) {
					mParameterList.getValues().add(ranktype);
					if (ranktype.equals("")) {
						ranktype = "share";
					}
					share.append("" + ranktype + "|");
				} else if (mParameterList.getName().equals("orderby")) {
					share.append("" + orderby + "|");
					if (orderby.equals("sum")) {
						orderby = "";
					}
					mParameterList.getValues().add(orderby);
				} else if (mParameterList.getName().equals("userid")) {
					//    <--------------------Administrator -> 2019-8-16:16:34:ZTCustomInit.get().getmCache().getmListDetails().AccountId--------------------->
					mParameterList.getValues().add(ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId);

					share.append(""
							+ ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId + "|");
				} else if (mParameterList.getName().equals("orgid")) {
					mParameterList.getValues().add(orgid);
					share.append("" + orgid + "|");
				} else if (mParameterList.getName().equals("top")) {
					mParameterList.getValues().add(top);
					share.append("" + top + "|");
				}
			}
		}
		mDamageSummar.setParameterList(array);
		mDamageSummar.setReportGuid(CHTTP.DYNAMIC);
		return mDamageSummar;
	}

	FunctionPopupWindow menuWindow;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() ==R.id.ibn_fn5_back ){
			this.finish();
		}else if(arg0.getId() ==R.id.iv_shaixuan){
			if (menuWindow.isShowing()) {
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
				menuWindow.dismiss();
			}
			ll_popupLayout.setAnimation(AnimationUtils.makeInAnimation(this,
					false));
			ll_popupLayout.setVisibility(View.VISIBLE);
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
							"分享" + titleName.getText().toString() + "详细数据",
							"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
							shareString, "BB");
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.zt_base_slide_right_out);
	}

	// 点击确定的时候走这方法
	@Override
	public void screeningValue(ArrayList<String> sscdList,
			ArrayList<String> sblxList,ArrayList<String> czztList, String pmfs, String pmsl) {
		// TODO Auto-generated method stub
		twoList.clear();
		this.pmfs = pmfs;
		this.top = pmsl;
		ll_popupLayout.setVisibility(View.GONE);
		ll_popupLayout
				.setAnimation(AnimationUtils.makeOutAnimation(this, true));
		sblx_list.clear();
		sscd_List.clear();
		sblx_list.addAll(sblxList);
		sscd_List.addAll(sscdList);
		showProgressDialog(this);
		AnsynHttpRequest.request(this,
				updateDate(array, pmfs, keys, pmsl, orgId),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(final String successMessage) {
						// TODO Auto-generated method stub
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								JSONObject mJSONObject = JSON
										.parseObject(successMessage);
								JSONObject Result = mJSONObject
										.getJSONObject("Result");
								if (Result == null) {
									ToastUtil.showShort(DynamicDetailDataActivity.this,"Result为空，请重新操作！");
									return;
								}
								JSONObject ListDataSet = Result
										.getJSONObject("ListDataSet");
								JSONArray Table1List = ListDataSet
										.getJSONArray("Table1");
								for (int i = 0; i < Table1List.size(); i++) {
									tableList = new ArrayList<Table>();
									JSONObject object = Table1List
											.getJSONObject(i);
									tableList.add(0,new Table("totalvalue", ((int) Float.parseFloat(object.getString("totalvalue"))+"")));
									for(Dictitemlist mDictitemlist :list){
										tableList.add(new Table(mDictitemlist.name, (int) Float
												.parseFloat(object.getString(mDictitemlist.code)) + ""));
									}
									tableList.add(new Table("shortname", object.getString("shortname")));
//									for (Map.Entry<String, Object> entry : object
//											.entrySet()) {
//										String key = entry.getKey();
//										ArrayList<Dictitemlist> list = null;
//										list = app.getmCache()
//												.getDictitemlist();
//										String name = ZTUnit
//												.getDictitemlistName(list, key);
//										String value = entry.getValue()
//												.toString();
//										if (!TextUtils.isEmpty(name)) {
//											tableList.add(new Table(name,
//													(int) Float
//															.parseFloat(value)
//															+ ""));
//										} else if (key.equals("totalvalue")
//												|| key.equals("value")) {
//											tableList.add(
//													0,
//													new Table(key, (int) Float
//															.parseFloat(value)
//															+ ""));
//										} else if (!key.equals("order")
//												&& !key.equals("sum")
//												&& !key.equals("orgid")
//												&& !key.equals("sumclass")) {
//											tableList
//													.add(new Table(key, value));
//										}
//									}
									twoList.add(tableList);
								}
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								mhandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										mDamageDetailDataAdapter = new DamageDetailDataAdapter(
												DynamicDetailDataActivity.this,
												twoList, mHead);
										tv_detail_data
												.setAdapter(mDamageDetailDataAdapter);
										dimssDialog();
									}
								});
							}
						}).start();

					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub
						dimssDialog();
					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub

					}
				});
	}

	Handler mhandler = new Handler();

	// 点击取消
	@Override
	public void clean() {
		// TODO Auto-generated method stub
		ll_popupLayout
				.setAnimation(AnimationUtils.makeOutAnimation(this, true));
		ll_popupLayout.setVisibility(View.GONE);
	}
}
