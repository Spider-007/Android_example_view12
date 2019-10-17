package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.fragment.TanshangProduceDetailsFragment;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.unit.ShareUnit;

import java.util.ArrayList;

/**
 * 探伤生产进度动态
 */
public class TanShangProduceDetailsActivity extends FragmentActivity implements
		OnClickListener {

	private ViewPager viewpager;
	private FragmentPagerAdapter fragmentPagerAdapter;
	private ImageView iv_back;
	private ArrayList<String> list_wz = new ArrayList<String>();
	private ArrayList<String> list_lx = new ArrayList<String>();
	private String Sblx = "GC";
	private String userID;
	private ImageView iv_point1;
	private ImageView iv_point2;
	private ImageView iv_point3;
	private ImageView iv_point4;
	private ImageView iv_point5;
	private ImageView iv_point6;
	private ImageView ivPoint[] = new ImageView[6];
	private ImageView iv_i;
	private FunctionPopupWindow functionPopWindow;
	// 分享拼接的字符串
	private String apiUrlTemp;
	// 判断是否为分享过来的
	private boolean isShare;
	// 得到分享的内容
	private String shareParmas;
	private String date = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tan_shang_produce_details);
		Intent intent = getIntent();
		isShare = intent.getBooleanExtra("flag_share", false);
		if (!isShare) {
			list_wz = intent.getStringArrayListExtra("list_wz");
			list_lx = intent.getStringArrayListExtra("list_lx");
			Sblx = intent.getStringExtra("SBLX");
			userID = intent.getStringExtra("userID");
			date = intent.getStringExtra("date");
		}else if(isShare){
			shareParmas = intent.getStringExtra("share");
			String[] ss = shareParmas.split("\\|");
			if (ss[0].equals("share")) {
				list_lx.add("");
			} else {
				String[] listlx_ss = ss[0].split(",");
				for (int i = 0; i < listlx_ss.length; i++) {
					list_lx.add(listlx_ss[i]);
				}
			}
			if (ss[1].equals("share")) {
				list_wz.add("");
			} else {
				String[] listwz_ss = ss[1].split(",");
				for (int i = 0; i < listwz_ss.length; i++) {
					list_wz.add(listwz_ss[i]);
				}
			}
			userID=ss[2];
			date=ss[3];
			Sblx=ss[4];
		}
		initView();
		initData();
		initCortol();
		viewpager.setAdapter(fragmentPagerAdapter);
	}

	private void initCortol() {
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < ivPoint.length; i++) {
					ivPoint[i].setBackgroundResource(R.drawable.table_point);
				}
				ivPoint[arg0].setBackgroundResource(R.drawable.table_point_pre);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initView() {
		viewpager = (ViewPager) findViewById(R.id.vp_tanshang_produce_details);
		iv_back = (ImageView) findViewById(R.id.ib_produce_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		iv_point1 = (ImageView) findViewById(R.id.iv_progressdetial_point1);
		iv_point1.setBackgroundResource(R.drawable.table_point_pre);
		iv_point2 = (ImageView) findViewById(R.id.iv_progressdetial_point2);
		iv_point2.setBackgroundResource(R.drawable.table_point);
		iv_point3 = (ImageView) findViewById(R.id.iv_progressdetial_point3);
		iv_point3.setBackgroundResource(R.drawable.table_point);
		iv_point4 = (ImageView) findViewById(R.id.iv_progressdetial_point4);
		iv_point4.setBackgroundResource(R.drawable.table_point);
		iv_point5 = (ImageView) findViewById(R.id.iv_progressdetial_point5);
		iv_point5.setBackgroundResource(R.drawable.table_point);
		iv_point6 = (ImageView) findViewById(R.id.iv_progressdetial_point6);
		iv_point6.setBackgroundResource(R.drawable.table_point);
		ivPoint[0] = iv_point1;
		ivPoint[1] = iv_point2;
		ivPoint[2] = iv_point3;
		ivPoint[3] = iv_point4;
		ivPoint[4] = iv_point5;
		ivPoint[5] = iv_point6;
		iv_i = (ImageView) findViewById(R.id.iv_progress_detial_i);
		iv_i.setOnClickListener(this);
	}

	private void initData() {

		if(isShare){
			iv_i.setVisibility(View.GONE);
		}
		functionPopWindow = new FunctionPopupWindow(this, this);
		fragmentPagerAdapter = new FragmentPagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 6;
			}

			@Override
			public android.support.v4.app.Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				TanshangProduceDetailsFragment fragment = new TanshangProduceDetailsFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("position", arg0);
				bundle.putString("Sblx", Sblx);
				bundle.putString("userID", userID);
				bundle.putStringArrayList("list_wz", list_wz);
				bundle.putStringArrayList("list_lx", list_lx);
				bundle.putString("date", date);
				fragment.setArguments(bundle);
				return fragment;
			}
		};
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if(arg0.getId() ==R.id.iv_progress_detial_i ){
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

		}else if(arg0.getId() ==R.id.iv_share){
			shareListener();
		}
	}

	//分享的参数
	private void shareListener() {

		String listwz = "";
		String listlx = "";

		if (list_wz.size() == 0 && list_lx.size() == 0) {
			apiUrlTemp = "share" + "|" + "share" + "|" + userID + "|" + date
					+ "|" + Sblx;
		}
		if (list_wz.size() != 0 && list_lx.size() == 0) {
			for (int i = 0; i < list_wz.size(); i++) {
				if (i != list_wz.size() - 1) {
					listwz += list_wz.get(i) + ",";
				} else {
					listwz += list_wz.get(i);
				}
			}
			apiUrlTemp = "share" + "|" + listwz + "|" + userID + "|" + date
					+ "|" + Sblx;
		}
		if (list_wz.size() == 0 && list_lx.size() != 0) {
			for (int i = 0; i < list_lx.size(); i++) {
				if (i != list_lx.size() - 1) {
					listlx += list_lx.get(i) + ",";
				} else {
					listlx += list_lx.get(i);
				}
			}
			apiUrlTemp = listlx + "|" + "share" + "|" + userID + "|" + date
					+ "|" + Sblx;
		}
		if (list_wz.size() != 0 && list_lx.size() != 0) {
			for (int i = 0; i < list_wz.size(); i++) {
				if (i != list_wz.size() - 1) {
					listwz += list_wz.get(i) + ",";
				} else {
					listwz += list_wz.get(i);
				}
			}
			for (int i = 0; i < list_lx.size(); i++) {
				if (i != list_lx.size() - 1) {
					listlx += list_lx.get(i) + ",";
				} else {
					listlx += list_lx.get(i);
				}
			}
			apiUrlTemp = listlx + "|" + listwz + "|" + userID + "|" + date
					+ "|" + Sblx;
		}

		ShareUnit
				.ShareListener(
						this,
						"探伤完成情况汇总",
						"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
						apiUrlTemp, "YY");
	}
}
