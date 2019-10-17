package com.htmitech.ztcustom.zt.chinarailway;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.base.BaseFragmentActivity;
import com.htmitech.ztcustom.zt.dialog.ActionSheetDialog;
import com.htmitech.ztcustom.zt.domain.DamageSummar;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.Dicttype;
import com.htmitech.ztcustom.zt.domain.DicttypeResult;
import com.htmitech.ztcustom.zt.domain.ParameterList;
import com.htmitech.ztcustom.zt.domain.damage.DamageSummarResult;
import com.htmitech.ztcustom.zt.domain.damage.Table1;
import com.htmitech.ztcustom.zt.domain.damage.Users;
import com.htmitech.ztcustom.zt.enums.DicttypeEnum;
import com.htmitech.ztcustom.zt.enums.TransType;
import com.htmitech.ztcustom.zt.fragment.DamageTypeFragment;
import com.htmitech.ztcustom.zt.interfaces.CallBackDamageTypeGridListener;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;
import com.htmitech.ztcustom.zt.util.DamageUtil;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;
import com.htmitech.ztcustom.zt.view.CircleWaveView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import htmitech.com.componentlibrary.unit.Utils;

/**
 * 损伤类型
 * 
 * @author Tony
 * 
 */
public class DamageTypeActivity extends BaseFragmentActivity implements
		CircleWaveView.RoundOnClick, OnClickListener, CallBackDamageTypeGridListener {
	private CircleWaveView mCircleWaveView;
	private Animation upAnimation, downAnimation;
	private LinearLayout ll_Popup;
	private ImageView function;
	private FunctionPopupWindow menuWindow;
	private TextView tvDateTitle;
	private String typeCode;
	private DicttypeResult mDicttypeResult;
	private ArrayList<String> sumitemList = new ArrayList<String>();
	private String dateTime;
	private LinearLayout layout_iv_up, layout_iv_down;
	private DamageSummarResult mDamageSummarResult;
	private String code;
	private String startTime, endTime;
	private ImageView back;
	private View CurrentView;
	private TextView titleName;
	private boolean isShare = false;
	private String appUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CurrentView = LayoutInflater.from(this).inflate(
				R.layout.zt_activity_damages_type, null);
		setContentView(CurrentView);
		mDicttypeResult = (DicttypeResult) getIntent().getSerializableExtra(
				"DicttypeResult");

		isShare = getIntent().getBooleanExtra("flag_share", false);
		appUrl = getIntent().getStringExtra("share");

		code = getIntent().getStringExtra("code");
		dateTime = getIntent().getStringExtra("dateTime");
		typeCode = code;
		startTime = getIntent().getStringExtra("startTime");
		endTime = getIntent().getStringExtra("endTime");
		mCircleWaveView = (CircleWaveView) findViewById(R.id.circle_line);
		ll_Popup = (LinearLayout) findViewById(R.id.ll_popupLayout);
		function = (ImageView) findViewById(R.id.function);
		tvDateTitle = (TextView) findViewById(R.id.tv_title_data);
		layout_iv_up = (LinearLayout) findViewById(R.id.layout_iv_up);
		layout_iv_down = (LinearLayout) findViewById(R.id.layout_iv_down);
		back = (ImageView) findViewById(R.id.ibn_fn5_back);
		titleName = (TextView) findViewById(R.id.tv_fn5_title_name);

		if (isShare) {
			layout_iv_up.setVisibility(View.GONE);
			request(code);
			function.setVisibility(View.GONE);
		} else {
			initDataHeah();
		}

	}

	public void initDataHeah() {
		for (Dictitemlist mDictitemlist : mDicttypeResult.getDictitemlist()) {
			sumitemList.add(mDictitemlist.code);
		}
		showProgressDialog(this);
		ArrayList<ParameterList> array = ZTCustomInit.get().getmCache().getmReportParameters()
				.getResult();
		AnsynHttpRequest.request(this, updateDate(array),
				CHTTP.GETLISTDEFINIENSHASDATABYIDANDPARAMETERS, CHTTP.POST,
				new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						// TODO Auto-generated method stub
						mDamageSummarResult = JSON.parseObject(successMessage,
								DamageSummarResult.class);
						initData();
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
						Utils.toast(DamageTypeActivity.this, "请求错误:"+exceptionMessage, Toast.LENGTH_SHORT);
					}
				});
		mCircleWaveView.setName(DicttypeEnum.getName(code));
		tvDateTitle.setText("" + dateTime);
		titleName.setText("设备伤损情况汇总-" + DicttypeEnum.getName(code));
	}

	StringBuffer share = new StringBuffer();

	public DamageSummar updateDate(ArrayList<ParameterList> array) {
		DamageSummar mDamageSummar = new DamageSummar();
//    <--------------------Administrator -> 2019-8-16:14:53:修改cisDeptId --------------------->
		DamageUtil.damageUtil(share, isShare, array, sumitemList, startTime,
				code,  ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId, endTime,ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId, appUrl);
		//ZTCustomInit.get().getmCache().getmListDetails().AccountId

		// for (ParameterList mParameterList : array) {
		// mParameterList.getValues().clear();
		// if (mParameterList.getName().equals("sumitem_list")) {
		// mParameterList.getValues().addAll(
		// ZTUnit.getSumitemValue(sumitemList));
		// } else if (mParameterList.getName().equals("datebegin")) {
		// mParameterList.getValues().add(startTime);
		// } else if (mParameterList.getName().equals("sumclass")) {
		// mParameterList.getValues().add(code);
		// } else if (mParameterList.getName().equals("orgid")) {
		// mParameterList.getValues().add(app.getmCache().getmListDetails().OrgId);
		// } else if (mParameterList.getName().equals("dateend")) {
		// mParameterList.getValues().add(endTime);
		// } else if (mParameterList.getName().equals("userid")) {
		// mParameterList.getValues().add(app.getmCache().getmListDetails().AccountId);
		// }
		// }

		share.append(dateTime);
		share.append("|" + code + "|");
		mDamageSummar.setParameterList(array);
		mDamageSummar.setReportGuid(CHTTP.REPORTGUID_SSLX);
		return mDamageSummar;
	}

	public void initData() {
		mCircleWaveView.setRoundOnClick(this);
		layout_iv_up.setOnClickListener(this);
		function.setOnClickListener(this);
		layout_iv_down.setOnClickListener(this);
		back.setOnClickListener(this);
		ll_Popup.getBackground().setAlpha(220);
		menuWindow = new FunctionPopupWindow(this, this);
		upAnimation = AnimationUtils.loadAnimation(this,
				R.anim.zt_score_business_query_enter);
		upAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				layout_iv_up.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layout_iv_down.setVisibility(View.VISIBLE);
			}
		});

		downAnimation = AnimationUtils.loadAnimation(this,
				R.anim.zt_score_business_query_exit);

		downAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				layout_iv_down.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layout_iv_up.setVisibility(View.VISIBLE);
				if (checkDictitemlist != null && checkDictitemlist.size() != 0) {
					sumitemList.clear();
					ArrayList<Table1> arr = mDamageSummarResult.Result.ListDataSet.Table1;
					ArrayList<Table1> tempList = new ArrayList<Table1>();
					float all = arr.get(arr.size() - 1).value;
					float num = 0;
					for (Dictitemlist mDictitemlist : checkDictitemlist) {
						for (Table1 mTable1 : arr) {
							if (mDictitemlist.code.equals(mTable1.key)) {
								num += mTable1.value;
								tempList.add(mTable1);
								break;
							}
						}
					}
					Table1 remTable = new Table1();
					remTable.value = all - num;
					remTable.name = "剩余";
					tempList.add(remTable);
					mCircleWaveView.setAllValue(all + "");
					mCircleWaveView.setTable(tempList,
							mDicttypeResult.dictitemlist, false);
				}
			}
		});
		ArrayList<Table1> arr = null;
		if (isShare) {
			String[] appUrlList = appUrl.split("\\|");
			String[] codeList = appUrlList[0].split(",");
			checkDictitemlist.clear();
			for (int i = 0; i < codeList.length; i++) {
				Dictitemlist mDictitemlist = new Dictitemlist();
				mDictitemlist.code = codeList[i];
				checkDictitemlist.add(mDictitemlist);
			}
			sumitemList.clear();
			arr = mDamageSummarResult.Result.ListDataSet.Table1;
			ArrayList<Table1> tempList = new ArrayList<Table1>();
			float all = arr.get(arr.size() - 1).value;
			float num = 0;
			for (Dictitemlist mDictitemlist : checkDictitemlist) {
				for (Table1 mTable1 : arr) {
					if (mDictitemlist.code.equals(mTable1.key)) {
						num += mTable1.value;
						tempList.add(mTable1);
						break;
					}
				}
			}
			if((all - num) > 0 ){
				Table1 remTable = new Table1();
				remTable.value = all - num;
				remTable.name = "剩余";
				tempList.add(remTable);
			}
			mCircleWaveView.setAllValue(all + "");
			mCircleWaveView.setTable(tempList, mDicttypeResult.dictitemlist,
					false);
		} else {
			arr = mDamageSummarResult.Result.ListDataSet.Table1;
			mCircleWaveView.setAllValue(arr.get(arr.size() - 1).value + "");
			DamageTypeFragment mDamageTypeFragment = new DamageTypeFragment();
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("dictitemlist",
					mDicttypeResult.getDictitemlist());
			mDamageTypeFragment.setArguments(mBundle);
			transaction.add(R.id.content, mDamageTypeFragment);
			transaction.commit();

		}

		// 如果数据大于五条就分条显示
		if (!isShare && sumitemList.size() > 5) {
			layout_iv_up.setVisibility(View.GONE);
			ll_Popup.setVisibility(View.VISIBLE); // 显示布局
			ll_Popup.startAnimation(upAnimation);
			// layout_iv_down.setVisibility(View.VISIBLE);
		} else {
			if(!isShare){
				layout_iv_up.setVisibility(View.GONE);
				mCircleWaveView.setTable(arr, mDicttypeResult.dictitemlist, true);
			}
		}
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

		mGestureDetectorUp = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
						// // System.out.println("水平方向移动距离过大");
						// return true;
						// }
						if (Math.abs(velocityY) < 100) {
							// System.out.println("手指移动的太慢了");
							return true;
						}

						// 手势向下 down
						if ((e2.getRawY() - e1.getRawY()) > 200) {
							ll_Popup.setVisibility(View.VISIBLE); // 显示布局
							ll_Popup.startAnimation(upAnimation);
							mCircleWaveView.setNum(0);
							return true;
						}
						// 手势向上 up
						if ((e1.getRawY() - e2.getRawY()) > 200) {
							return true;
						}
						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});

		mGestureDetectorDown = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// if (Math.abs(e1.getRawX() - e2.getRawX()) > 250) {
						// // System.out.println("水平方向移动距离过大");
						// return true;
						// }
						if (Math.abs(velocityY) < 100) {
							// System.out.println("手指移动的太慢了");
							return true;
						}

						// 手势向下 down
						if ((e2.getRawY() - e1.getRawY()) > 200) {
							return true;
						}
						// 手势向上 up
						if ((e1.getRawY() - e2.getRawY()) > 200) {
							mCircleWaveView.setFocusable(true);
							ll_Popup.setVisibility(View.GONE); // 取出布局
							ll_Popup.startAnimation(downAnimation); // 开始退出动画
							return true;
						}
						return super.onFling(e1, e2, velocityX, velocityY);
					}
				});
		layout_iv_down.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				mGestureDetectorDown.onTouchEvent(event);
				return false;
			}
		});

		layout_iv_up.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				mGestureDetectorUp.onTouchEvent(event);
				return false;
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

	GestureDetector mGestureDetectorUp, mGestureDetectorDown;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCircleWaveView.stop();
	}

	public void request(final String code) {
		showProgressDialog(DamageTypeActivity.this);
		Object object;
		String url;
		if (code.equals("SSLX")) {
			object = new Users(
					ZTCustomInit.get().getmCache().getmGetUserAuth().Result.ListNode.get(0).AccountId);
			url = CHTTP.IB05_GETSSLXLIST;
			if(isShare){
				String[] appUrlList = appUrl.split("\\|");
				String[] codeList = appUrlList[0].split(",");
				for (int i = 0; i < codeList.length; i++) {
					Dictitemlist mDictitemlist = new Dictitemlist();
					mDictitemlist.code = codeList[i];
					checkDictitemlist.add(mDictitemlist);
				}
			}
		} else {
			object = new Dicttype(code);
			url = CHTTP.IB04_GETDICTTYPEOBJECT;
		}
		AnsynHttpRequest.request(DamageTypeActivity.this, object, url,
				CHTTP.POST, new ObserverCallBack() {
					@Override
					public void success(String successMessage) {
						if (code.equals("SSLX")) {
							ArrayList<Dictitemlist> dictitemlist = (ArrayList<Dictitemlist>) JSON
									.parseArray(successMessage,
											Dictitemlist.class);
							DicttypeResult mDicttypeResult = new DicttypeResult();
							mDicttypeResult.class_code = code;
							mDicttypeResult.dictitemlist = dictitemlist;
							ZTCustomInit.get().getmCache().getmDicttypeResult()
									.put(code, mDicttypeResult);
							DamageTypeActivity.this.mDicttypeResult = mDicttypeResult;
							if (!isShare) {
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("DicttypeResult", mDicttypeResult);
								params.put("dateTime", dateTime);
								params.put("startTime", startTime);
								params.put("endTime", endTime);
								params.put("code", code);
								ZTActivityUnit.switchTo(
										DamageTypeActivity.this,
										DamageTypeActivity.class, params);
								DamageTypeActivity.this.finish();
							}
						} else {
							DicttypeResult mDicttypeResult = JSON.parseObject(
									successMessage, DicttypeResult.class);
							DamageTypeActivity.this.mDicttypeResult = mDicttypeResult;
							ZTCustomInit.get().getmCache().getmDicttypeResult()
									.put(code, mDicttypeResult);
							if (!isShare) {
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("DicttypeResult", mDicttypeResult);
								params.put("dateTime", dateTime);
								params.put("startTime", startTime);
								params.put("endTime", endTime);
								params.put("code", code);
								ZTActivityUnit.switchTo(
										DamageTypeActivity.this,
										DamageTypeActivity.class, params);
								DamageTypeActivity.this.finish();
							}
						}
						if (isShare) {
							initDataHeah();
						}
						dimssDialog();
					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated
						// method stub
						dimssDialog();
					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated
						// method stub

					}
				});
	}

	@Override
	public void onClick() {
		if (!isShare && !ll_Popup.isShown()) {
			new ActionSheetDialog(DamageTypeActivity.this)
					.builder()
					.setTitle("请选择维度")
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem(DicttypeEnum.getNames())
					.setOnSheetItemClickListener(
							new ActionSheetDialog.OnSheetItemClickListener() {
								@Override
								public void onClick(final String code) {
									if (code == null) {
										Toast.makeText(DamageTypeActivity.this,
												"请选择维度!", Toast.LENGTH_SHORT)
												.show();
										return;
									}

									request(code);
								}
							}).show();
		}
	}

	@Override
	public void onClick(View view) {
		if(view.getId() ==R.id.layout_iv_up ){
			ll_Popup.setVisibility(View.VISIBLE); // 显示布局
			ll_Popup.startAnimation(upAnimation);
			mCircleWaveView.setNum(0);
		}else if(view.getId() ==R.id.layout_iv_down){
			mCircleWaveView.setFocusable(true);
			ll_Popup.setVisibility(View.GONE); // 取出布局
			ll_Popup.startAnimation(downAnimation); // 开始退出动画
		}else if(view.getId() ==R.id.function){
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

		}else if(view.getId() ==R.id.iv_fun_detail_data){
			if (menuWindow.isShowing()) {
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
				menuWindow.dismiss();
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dateTime", dateTime);
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			if (typeCode.equals("SSLX")) {
				params.put("type", TransType.DAMAGE_TYPE_SSLX);
			} else {
				params.put("type", TransType.DAMAGE_QT);
			}
			params.put("code", code);
			ZTActivityUnit.switchTo(this, DamageDetailDataActivity.class,
					params);
		}else if(view.getId() ==R.id.iv_share){
			String shareString = share.toString();
			StringBuffer append = new StringBuffer();
			if (checkDictitemlist.size() > 0) {
				for (int i = 0; i < checkDictitemlist.size(); i++) {
					if (sumitemList.size() == 1) {
						append.append("" + checkDictitemlist.get(i).code);
					} else if (i < checkDictitemlist.size() - 1) {
						append.append(checkDictitemlist.get(i).code + ",");
					} else {
						append.append(checkDictitemlist.get(i).code);
					}
				}
				shareString = append.toString() + shareString;
			}

			shareString = shareString.substring(0, shareString.length() - 1);
			ShareUnit
					.ShareListener(
							this,
							"分享" + titleName.getText().toString(),
							"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
							shareString, "VV");
		}else if(view.getId() ==R.id.ibn_fn5_back){
			this.finish();
		}
	}

	private ArrayList<Dictitemlist> checkDictitemlist = new ArrayList<Dictitemlist>();

	@Override
	public void callBack(ArrayList<Dictitemlist> list) {
		// TODO Auto-generated method stub
		checkDictitemlist = list;

	}

	@Override
	public void otherOnClick(Table1 mTable1) {
		// TODO Auto-generated method stub
		Log.d("otherOnClick", "mTable1 == "+mTable1.name+mTable1.key);
	}

}
