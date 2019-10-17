package com.htmitech.ztcustom.zt.chinarailway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.fragment.ZBReportFragment;
import com.htmitech.ztcustom.zt.fragment.ZBWeldFragment;


public class ZBRealTimeBroadActivity extends FragmentActivity implements
		OnCheckedChangeListener, OnPageChangeListener {
	private ViewPager viewpager;
	private FragmentPagerAdapter adapter;
	private RadioGroup rgMenu;
	private ImageButton ibnSearch,ibn_fn4_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zb_activity_realtime_broad);
		rgMenu = (RadioGroup) findViewById(R.id.rg_menu);
		rgMenu.setOnCheckedChangeListener(this);

		viewpager = (ViewPager) findViewById(R.id.vp_pager);
		adapter = new InnerPagerAdapter(getSupportFragmentManager());
		ibn_fn4_back = (ImageButton) findViewById(R.id.ibn_fn5_back);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(this);
		
		ibnSearch = (ImageButton) findViewById(R.id.ibn_report_search);
		ibnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ZBRealTimeBroadActivity.this,
						ZBSearchActivity.class);
				startActivityForResult(intent,0);
			}
		});
		ibn_fn4_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	ZBReportFragment mZBReportFragment;
	ZBWeldFragment mZBWeldFragment;
	private class InnerPagerAdapter extends FragmentPagerAdapter {

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				mZBReportFragment = new ZBReportFragment();
				fragment = mZBReportFragment;
				break;
			case 1:
				mZBWeldFragment = new ZBWeldFragment();
				fragment = mZBWeldFragment;
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case 0:
			if (resultCode == 1) {
				String keyword = data.getStringExtra("keyword");
				if(ponstion == 0){
					mZBReportFragment.setKeyWord(keyword);
					mZBReportFragment.getData("",0);
				}else if(ponstion == 1){
					mZBWeldFragment.setKeyWord(keyword);
					mZBWeldFragment.getData("",0);
				}
			}
			break;
		default:
			break;
		}
		
	}
	public int ponstion = 0;
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId ==R.id.ibn_fn5_back ){
			ponstion = 0;
			viewpager.setCurrentItem(0);
		}else if(checkedId ==R.id.function){
			ponstion = 1;
			viewpager.setCurrentItem(1);
		}
	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			rgMenu.check(R.id.rb_report);
			break;
		case 1:
			rgMenu.check(R.id.rb_weld);
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
}
