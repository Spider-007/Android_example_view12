package com.htmitech.addressbook;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.adapter.MyFragmentPagerAdapter;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.domain.SYS_User;
import com.htmitech.fragment.AddressFragment;
import com.htmitech.fragment.UpdateUserDetailsMessageFragment;
import com.htmitech.fragment.UserDetailsFileFragment;
import com.htmitech.fragment.UserDetailsWorkFragment;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.listener.CallbackMX;

/**
 * 个人信息
 * 
 * @author Tony
 * 
 */
public class UserDetailsActivity extends BaseFragmentActivity implements
		AddressListener, BackHandledInterface {
	public BaseFragment mBaseFragment;
	private ViewPager mPager;
	private ArrayList<BaseFragment> fragmentList;
	private ImageView image;
	private TextView tvPeopleMessage, tvWork, tvFile;
	private int currentIndex;
	private TextView userName;
	private  ImageView back;
	private SYS_User currentSYS_Department;
	/**
	 * 屏幕的宽度
	 */
	private int screenWidth;
	private CallbackMX mCallbackMX;
	public String app_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_fragment_user_details);
		currentSYS_Department = BookInit.getInstance().getCurrentUser();
		InitTextView();
		InitViewPager();
		initTabLineWidth();
		initData();
		mCallbackMX = BookInit.getInstance().getmCallbackMX();
	}
	public void initData(){
		app_id = getIntent().getStringExtra("app_id");
		userName.setText(""+currentSYS_Department.getFullName());
	}
	/*
	 * 初始化标签名
	 */
	public void InitTextView() {
		tvPeopleMessage = (TextView) findViewById(R.id.tv_people_message);
		userName = (TextView) findViewById(R.id.tv_user_name);
		tvWork = (TextView) findViewById(R.id.tv_work);
		tvFile = (TextView) findViewById(R.id.tv_file);
		image = (ImageView) findViewById(R.id.cursor);
		back = (ImageView) findViewById(R.id.back);
		tvPeopleMessage.setOnClickListener(new txListener(0));
		back.setOnClickListener(new txListener(0));
		tvWork.setOnClickListener(new txListener(1));
		tvFile.setOnClickListener(new txListener(2));
	}

	public class txListener implements OnClickListener {
		private int index = 0;

		public txListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.back){
				UserDetailsActivity.this.finish();
			}else{
				mPager.setCurrentItem(index);
			}
//			switch(v.getId()){
//			case R.id.back:
//				UserDetailsActivity.this.finish();
//				break;
//			default:
//				mPager.setCurrentItem(index);
//				break;
//			}
			
		}
	}

	/*
	 * 初始化ViewPager
	 */
	public void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.viewpager);
		fragmentList = new ArrayList<BaseFragment>();
		UpdateUserDetailsMessageFragment mUserDetailsMessageFragment = BookInit.getInstance().getmUserDetailsMessageFragment();
		UserDetailsFileFragment mUserDetailsFileFragment = new UserDetailsFileFragment();
		UserDetailsWorkFragment mUserDetailsWorkFragment = new UserDetailsWorkFragment();
		fragmentList.add(mUserDetailsMessageFragment);
//		fragmentList.add(mUserDetailsFileFragment);
//		fragmentList.add(mUserDetailsWorkFragment);

		// 给ViewPager设置适配器
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		tvPeopleMessage.setTextColor(Color.BLUE);
		mPager.setCurrentItem(0);// 设置当前显示标签页为第一页
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			/**
			 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}

			/**
			 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
			 * offsetPixels:当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image
						.getLayoutParams();

				Log.e("offset:", offset + "");
				/**
				 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
				 * 设置mTabLineIv的左边距 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1;
				 * 1->0
				 */

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				} else if (currentIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				}
				image.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					tvPeopleMessage.setTextColor(Color.BLUE);
					break;
				case 1:
					tvWork.setTextColor(Color.BLUE);
					break;
				case 2:
					tvFile.setTextColor(Color.BLUE);
					break;
				}
				currentIndex = position;
			}
		});// 页面变化时的监听器
	}

	/**
	 * 重置颜色
	 */
	private void resetTextView() {
		tvPeopleMessage.setTextColor(Color.BLACK);
		tvWork.setTextColor(Color.BLACK);
		tvFile.setTextColor(Color.BLACK);
	}

	/**
	 * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) image
				.getLayoutParams();
		lp.width = screenWidth / 3;
		image.setLayoutParams(lp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	AddressFragment mAddressFragment;

	/** 初始化显示内容 **/
	private void initContent() {
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		// TODO Auto-generated method stub
		mBaseFragment = selectedFragment;
	}

	@Override
	public void onClickChild(BaseFragment f) {
		// TODO Auto-generated method stub

	}
}
