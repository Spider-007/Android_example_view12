package com.htmitech.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.htmitech.Task.DownLoaderTask;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.fragment.UserChooseChildFragment;
import com.htmitech.fragment.UserDetailsChildFragment;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.myEnum.ChooseWayEnum;

/**
 * 单位通讯录 选择功能
 * 
 * @author Tony
 * 
 */
public class UserChooseActivity extends BaseFragmentActivity implements
		AddressListener, BackHandledInterface{
	public BaseFragment mBaseFragment;
	private UserChooseChildFragment mUserDetailsChildFragment;
	private Handler mHanlder = new Handler();
	private DownLoaderTask mDownLoaderTask;
	private ChooseWayEnum mChooseWayEnum;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_activity_main);

		mChooseWayEnum = (ChooseWayEnum) getIntent().getSerializableExtra("ChooseWayEnum");
		initContent();
	}

	@Override
	public void onClickChild(BaseFragment f) {
		// TODO Auto-generated method stub
			this.finish();
	}

	/** 初始化显示内容 **/
	private void initContent() {

		mUserDetailsChildFragment = new UserChooseChildFragment();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("ChooseWayEnum", mChooseWayEnum);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		mUserDetailsChildFragment.setArguments(mBundle);
		transaction.add(R.id.content, mUserDetailsChildFragment);
		transaction.commit();

	}

	@Override
	public void onBackPressed() {
		if (mBaseFragment == null || !mBaseFragment.onBackPressed()) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				this.finish();
			} else {
				getSupportFragmentManager().popBackStack();
			}
		}
	}

	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		// TODO Auto-generated method stub
		mBaseFragment = selectedFragment;
	}

}
