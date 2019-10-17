package com.htmitech.ztcustom.zt.chinarailway;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.adapter.DynamicGridAdapter;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.domain.DamageSummar;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.ParameterList;
import com.htmitech.ztcustom.zt.fragment.DynamicFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDynamicAdapter;
import com.htmitech.ztcustom.zt.interfaces.CallBackScreening;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;
import com.htmitech.ztcustom.zt.util.ZTUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.unit.ToastUtil;
import htmitech.com.componentlibrary.unit.Utils;

public class DynamicActivity extends BaseFragmentActivity implements
		CallBackScreening, OnClickListener, CallBackDynamicAdapter {
	private LinearLayout layout_dynamic_title;
	private ListView lv_dynamic;
	private ArrayList<String> sblx_list; // 设备类型

	private ArrayList<String> sscdList;// 伤损程度

	private ArrayList<ParameterList> array;
	private ArrayList<Dictitemlist> mDicttypeResult;
	private DynamicGridAdapter mDynamicGridAdapter;

	private LinearLayout ll_popupLayout;

	private TextView iv_shaixuan;

	public String orgId = "";

	public ImageView ibn_fn5_back;

	private ArrayList<String> orgIdList;

	//    <--------------------Administrator -> 2019-8-17:11:26:--------------------->
    public String pmfs = "DW";

    public String keys = "sum";
    public String top = "50";
	public FunctionPopupWindow menuWindow;
	private ImageView function;
	private View CurrentView;
	private TextView tv_title_name;

	private HorizontalScrollView my_horizon;
	private boolean isShare;
	private String appUrl;
	private String ranktype = "DW";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CurrentView = LayoutInflater.from(this).inflate(
				R.layout.zt_activity_dynamic, null);
		setContentView(CurrentView);
		isShare = getIntent().getBooleanExtra("flag_share", false);
		appUrl = getIntent().getStringExtra("share");
		initView();
		initData();
	}

	public void initView() {
		layout_dynamic_title = (LinearLayout) findViewById(R.id.layout_dynamic_title);
		lv_dynamic = (ListView) findViewById(R.id.lv_dynamic);
		ll_popupLayout = (LinearLayout) findViewById(R.id.ll_popupLayout);
		iv_shaixuan = (TextView) findViewById(R.id.iv_shaixuan);
		ibn_fn5_back = (ImageView) findViewById(R.id.ibn_fn5_back);
		function = (ImageView) findViewById(R.id.function);
		tv_title_name = (TextView) findViewById(R.id.tv_title_name);
		my_horizon = (HorizontalScrollView) findViewById(R.id.my_horizon);
	}

	String[] appUrlList;

	public void initData() {

		if (isShare) {
			appUrlList = appUrl.split("\\|");
			keys = appUrlList[appUrlList.length - 1];
			layout_dynamic_title.setVisibility(View.GONE);
			function.setVisibility(View.GONE);
			iv_shaixuan.setVisibility(View.GONE);
			my_horizon.setVisibility(View.GONE);
			tv_title_name.setText(appUrlList[2].equals("DW") ? "单位" : "线路");
		}
		menuWindow = new FunctionPopupWindow(this, this);
//		orgId = ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId;
		orgId = ZTCustomInit.get().getmCache().getCisAccountDetail().cisDeptId;
		Log.e("yjh", "DynamicActivity->ORGId" + orgId);
		//cisAccountId 3444
		//orgid -> 192
		ll_popupLayout.getBackground().setAlpha(220);
		sblx_list = new ArrayList<String>();
		sscdList = new ArrayList<String>();
		orgIdList = new ArrayList<String>();
		//伤损程度
		DicttypeResult mDicttypeResult_SSCD = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SSCD");
		Log.e("YJH", "initData->mDicttypeResult_SSCD:"+mDicttypeResult_SSCD.getDictitemlist().toString() );

		//设备类型
		DicttypeResult mDicttypeResult_SBLX = ZTCustomInit.get().getmCache().getmDicttypeResult().get("SBLX");
		Log.e("YJH", "initData->mDicttypeResult_SBLX:"+mDicttypeResult_SBLX.getDictitemlist().toString());
		for (Dictitemlist mDictitemlist : mDicttypeResult_SSCD.getDictitemlist()) {
			sscdList.add(mDictitemlist.getCode());
		}

		for(Dictitemlist mDictitemlist : mDicttypeResult_SBLX.getDictitemlist()){
			sblx_list.add(mDictitemlist.getCode());
		}

		sscdList.add("QS");
		sblx_list.add("GG");
		orgIdList.add(orgId);
		mDynamicGridAdapter = new DynamicGridAdapter(this, null, this, keys, isShare);
		lv_dynamic.setAdapter(mDynamicGridAdapter);

		//    <--------------------Administrator -> 2019-8-17:11:05:mDicttypeResult-->--------------------->
		mDicttypeResult = ZTCustomInit.get().getmCache().getDictitemlist();
		array = ZTCustomInit.get().getmCache().getmDynamicReportParameters().getResult();

		showProgressDialog(this);
		ibn_fn5_back.setOnClickListener(this);
		iv_shaixuan.setOnClickListener(this);
		function.setOnClickListener(this);
//		if (DynamicActivity.this.orgId.equals("TZ")) {
//			tv_title_name.setText(pmfs.equals("DW") ? "铁路局" : "线路");
//		} else if (DynamicActivity.this.orgId.length() == 3) {
//			tv_title_name.setText(pmfs.equals("DW") ? "工务段" : "线路");
//		} else if (DynamicActivity.this.orgId.length() == 5) {
//			tv_title_name.setText(pmfs.equals("DW") ? "车间" : "线路");
//		} else if (DynamicActivity.this.orgId.length() == 7) {
//			tv_title_name.setText(pmfs.equals("DW") ? "工区" : "线路");
//		} else if (DynamicActivity.this.orgId.length() == 9) {
//			tv_title_name.setText(pmfs.equals("DW") ? "班组" : "线路");
//		}
		CurrentView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				if (menuWindow.isShowing()) {
					function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
					menuWindow.dismiss();
				}
				return false;
			}
		});
		AnsynHttpRequest.request(this, updateDate(array, pmfs, keys, top, orgId),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						JSONObject jsonObject = JSON
								.parseObject(successMessage);
						JSONObject result = jsonObject.getJSONObject("Result");
						if (result == null) {
							ToastUtil.showShort(DynamicActivity.this, "Result为空，请重新操作！");
//							ToastInfo.getInstance(DynamicActivity.this).setText("Result为空，请重新操作！");
							dimssDialog();
							return;
						}
						JSONObject listDataSet = result
								.getJSONObject("ListDataSet");
						JSONArray table1 = listDataSet.getJSONArray("Table1");
						if (table1 == null || table1.size() == 0) {
							ToastUtil.showShort(DynamicActivity.this, "数组为空，请重新操作！");
//							ToastInfo.getInstance(DynamicActivity.this).setText("数组为空，请重新操作！");
							dimssDialog();
							return;
						}
						JSONObject mJSONObject = table1.getJSONObject(0);
						TextView text = new TextView(DynamicActivity.this);
						text.setTextColor(DynamicActivity.this.getResources()
								.getColor(R.color.railway_year_month_data));
						text.setText("合计");
						text.setTextSize(15);
						text.setPadding(20, 0, 20, 0);
						text.setOnClickListener(new TextOnClick("sum", text));
						//    <--------------------Administrator -> 2019-8-17:11:22:标题那一栏的显示--------------------->
						layout_dynamic_title.addView(text);
							mDicttypeResult.get(0).code = "";
							for (Dictitemlist d : mDicttypeResult) {
								text = new TextView(DynamicActivity.this);
								text.setTextColor(DynamicActivity.this
										.getResources().getColor(R.color.white));
								text.setTextSize(15);
								text.setPadding(20, 0, 20, 0);
								text.setOnClickListener(new TextOnClick(d.code,
										text));
								text.setText("" + d.name);
								layout_dynamic_title.addView(text);
							}


/*						for (Map.Entry<String, Object> entry : mJSONObject
								.entrySet()) {
							String key = entry.getKey();
							String name = getName(key);
							if (!name.equals("")) {
								text = new TextView(DynamicActivity.this);
								text.setTextColor(DynamicActivity.this
										.getResources().getColor(R.color.white));
								text.setTextSize(15);
								text.setPadding(20, 0, 20, 0);
								text.setOnClickListener(new TextOnClick(key,
										text));
								text.setText("" + name);
								layout_dynamic_title.addView(text);
							}
						}*/

						mDynamicGridAdapter.setData(table1, keys);
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
						Utils.toast(DynamicActivity.this, "请求错误:" + exceptionMessage, Toast.LENGTH_SHORT);
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

	/**
	 * 点击表头
	 *
	 * @author htrf-pc
	 */

	public class TextOnClick implements OnClickListener {
		private String key;
		private TextView text;

		public TextOnClick(String key, TextView text) {
			this.key = key;
			this.text = text;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < layout_dynamic_title.getChildCount(); i++) {
				TextView text = (TextView) layout_dynamic_title.getChildAt(i);
				text.setTextColor(DynamicActivity.this.getResources().getColor(
						R.color.white));
			}
			text.setTextColor(DynamicActivity.this.getResources().getColor(
					R.color.railway_year_month_data));
			keys = key;
			showProgressDialog(DynamicActivity.this);
			AnsynHttpRequest.request(DynamicActivity.this,
					updateDate(array, pmfs, key, top, orgId),
					CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
					new ObserverCallBack() {
						@Override
						public void success(String successMessage) {
							// TODO Auto-generated method stub
							JSONObject jsonObject = JSON
									.parseObject(successMessage);
							JSONObject result = jsonObject
									.getJSONObject("Result");
							if (result == null) {
								ToastUtil.showShort(DynamicActivity.this, "Result为空，请重新操作！");
								return;
							}
							JSONObject listDataSet = result
									.getJSONObject("ListDataSet");
							JSONArray table1 = listDataSet
									.getJSONArray("Table1");
							Log.e("YJH", "success:->table1"+table1.toString());
							mDynamicGridAdapter.setData(table1, keys);
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

		}

	}

	StringBuffer share = new StringBuffer();

	public DamageSummar updateDate(ArrayList<ParameterList> array,
								   String ranktype, String orderby, String top, String orgid) {
		DamageSummar mDamageSummar = new DamageSummar();
		share.setLength(0);
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
					sscdList.clear();
					String[] ss = appUrlList[index].split(",");
					for (int i = 0; i < ss.length; i++) {
						sscdList.add(ss[i]);
					}
					mParameterList.getValues().addAll(
							ZTUnit.getSumitemValue(sscdList));
				} else if (mParameterList.getName().equals("ranktype")) {
					mParameterList.getValues().add(appUrlList[index]);
				} else if (mParameterList.getName().equals("orderby")) {
					orderby = appUrlList[index];
					if (orderby.equals("share")) {
						orderby = "";
					}
					mParameterList.getValues().add(orderby);
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
							ZTUnit.getSumitemValue(sscdList));
					for (int i = 0; i < sscdList.size(); i++) {
						if (sscdList.size() == 1) {
							share.append("" + sscdList.get(i));
						} else if (i < sscdList.size() - 1) {
							share.append(sscdList.get(i) + ",");
						} else {
							share.append("," + sscdList.get(i));
						}
					}
					if (sscdList == null || sscdList.size() == 0) {
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
					share.append("" + "share" + "|");
					if (orderby.equals("sum")) {
						orderby = "";
					}
					mParameterList.getValues().add(orderby);
				} else if (mParameterList.getName().equals("userid")) {
					//ZTCustomInit.get().getmCache().getmListDetails().AccountId
					mParameterList.getValues().add(ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId);

					//ZTCustomInit.get().getmCache().getmListDetails().AccountId
					share.append("" + ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId + "|");
				} else if (mParameterList.getName().equals("orgid")) {
					mParameterList.getValues().add(orgid);
					share.append("" + orgid + "|");
				} else if (mParameterList.getName().equals("top")) {
					mParameterList.getValues().add(top);
					share.append("" + top + "|");
				}
			}
		}
		share.append("" + keys + "|");
		mDamageSummar.setParameterList(array);
		mDamageSummar.setReportGuid(CHTTP.DYNAMIC);
		return mDamageSummar;
	}

	public String getName(String code) {
		try {
			for (Dictitemlist d : mDicttypeResult) {
				if (d.code.equals(code)) {
					return d.name;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("YJH", "DynamicActivity->getName: " + e + "--------" + e.getMessage());
		}
		return "";
	}

	// 点击确定的时候走这方法
	@Override
	public void screeningValue(ArrayList<String> sscdList,
							   ArrayList<String> sblxList, ArrayList<String> czztList, String pmfs, String pmsl) {
		// TODO Auto-generated method stub
		this.pmfs = pmfs;
		this.top = pmsl;
		ll_popupLayout
				.setAnimation(AnimationUtils.makeOutAnimation(this, true));
		ll_popupLayout.setVisibility(View.GONE);
		sblx_list.clear();
		this.sscdList.clear();
		sblx_list.addAll(sblxList);
		this.sscdList.addAll(sscdList);
		showProgressDialog(DynamicActivity.this);
		AnsynHttpRequest.request(DynamicActivity.this,
				updateDate(array, pmfs, keys, pmsl, orgId),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						JSONObject jsonObject = JSON
								.parseObject(successMessage);
						JSONObject result = jsonObject.getJSONObject("Result");
						if (result == null) {
							ToastUtil.showShort(DynamicActivity.this, "Result为空，请重新操作！");
							return;
						}
						JSONObject listDataSet = result
								.getJSONObject("ListDataSet");
						JSONArray table1 = listDataSet.getJSONArray("Table1");
						Log.e("YJH", "success: "+table1.toString());
						mDynamicGridAdapter.setData(table1, keys);
						dimssDialog();
						if (table1.size() == 1) {
							orgIdList.remove(orgIdList.size() - 1);
							return;
						}
						DynamicActivity.this.orgId = orgIdList.get(orgIdList
								.size() - 1);
						JSONObject mJSONObject = table1.getJSONObject(0);
						String ranktypes = mJSONObject.getString("ranktype");
						tv_title_name.setText(ranktypes.equals("DW") ? "单位" : "线路");

//						if (DynamicActivity.this.orgId.equals("TZ")) {
//							tv_title_name.setText(DynamicActivity.this.pmfs.equals("DW") ? "铁路局"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 3) {
//							tv_title_name.setText(DynamicActivity.this.pmfs.equals("DW") ? "工务段"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 5) {
//							tv_title_name.setText(DynamicActivity.this.pmfs.equals("DW") ? "车间"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 7) {
//							tv_title_name.setText(DynamicActivity.this.pmfs.equals("DW") ? "工区"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 9) {
//							tv_title_name.setText(DynamicActivity.this.pmfs.equals("DW") ? "班组"
//									: "线路");
//						}
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

	// 点击取消
	@Override
	public void clean() {
		// TODO Auto-generated method stub
		ll_popupLayout
				.setAnimation(AnimationUtils.makeOutAnimation(this, true));
		ll_popupLayout.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.iv_shaixuan) {
			ll_popupLayout.setAnimation(AnimationUtils.makeInAnimation(this,
					false));
			ll_popupLayout.setVisibility(View.VISIBLE);
			if (menuWindow.isShowing()) {
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
				menuWindow.dismiss();
			}
		} else if (arg0.getId() == R.id.ibn_fn5_back) {
			orgIdList.remove(orgIdList.size() - 1);
			if (orgIdList.size() > 0) {
				String orgId = "";
				orgId = orgIdList.get(orgIdList.size() - 1);
				callOrgId(orgId, "DW");
			} else {
				this.finish();
			}
		} else if (arg0.getId() == R.id.function) {
			if (!menuWindow.isShowing()) {
				menuWindow = new FunctionPopupWindow(this, this);
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
		} else if (arg0.getId() == R.id.iv_fun_detail_data) {
			function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
			menuWindow.dismiss();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sblx_list", sblx_list);
			params.put("sscdList", sscdList);
			params.put("orgId", orgId);
			params.put("pmfs", pmfs);
			params.put("keys", keys);
			params.put("top", top);
			ZTActivityUnit.switchTo(this, DynamicDetailDataActivity.class,
					params);
		} else if (arg0.getId() == R.id.iv_share) {
			String shareString = share.toString();
			shareString = shareString.substring(0, shareString.length() - 1);
			ShareUnit
					.ShareListener(
							this,
							"分享现存伤损动态排名" + tv_title_name.getText().toString(),
							"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
							shareString, "AA");

		}

	}

	@Override
	public void callOrgId(final String orgId, final String ranktype) {
		// TODO Auto-generated method stub
//		if(orgId.length() == 5){  //到达公路段就直接返回 暂时处理措施
//			return ;
//		}
		if (!orgId.equals("") && !orgIdList.contains(orgId)) {
			orgIdList.add("" + orgId);
		}
		showProgressDialog(DynamicActivity.this);
		AnsynHttpRequest.request(DynamicActivity.this,
				updateDate(array, pmfs, keys, top, orgId),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						JSONObject jsonObject = JSON
								.parseObject(successMessage);
						JSONObject result = jsonObject.getJSONObject("Result");
						if (result == null) {
							ToastUtil.showShort(DynamicActivity.this, "Result为空，请重新操作！");
							return;
						}
						JSONObject listDataSet = result
								.getJSONObject("ListDataSet");
						JSONArray table1 = listDataSet.getJSONArray("Table1");
						dimssDialog();
						if (table1.size() == 1 || table1.size() == 0 || ranktype.equals("XL")) {
							orgIdList.remove(orgIdList.size() - 1);
							return;
						}
						JSONObject mJSONObject = table1.getJSONObject(0);
						String ranktypes = mJSONObject.getString("ranktype");
						tv_title_name.setText(ranktypes.equals("DW") ? "单位" : "线路");
						DynamicActivity.this.orgId = orgIdList.get(orgIdList
								.size() - 1);
//						if (DynamicActivity.this.orgId.equals("TZ")) {
//							tv_title_name.setText(pmfs.equals("DW") ? "铁路局"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 3) {
//							tv_title_name.setText(pmfs.equals("DW") ? "工务段"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 5) {
//							tv_title_name.setText(pmfs.equals("DW") ? "车间"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 7) {
//							tv_title_name.setText(pmfs.equals("DW") ? "工区"
//									: "线路");
//						} else if (DynamicActivity.this.orgId.length() == 9) {
//							tv_title_name.setText(pmfs.equals("DW") ? "班组"
//									: "线路");
//						}
						mDynamicGridAdapter.setData(table1, keys);
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
}
