package com.htmitech.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.fragment.GeneralFunctionFragment;
import com.htmitech.listener.BackHandledInterface;

/**
 * 通用功能
 * 
 * @author Tony
 * 
 */
public class UserMessageActivity extends BaseFragmentActivity implements BackHandledInterface{
	public BaseFragment mBaseFragment;
	private GeneralFunctionFragment mGeneralFunctionFragment;
	public int size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_activity_main);
		Intent intent = getIntent();
		size = intent.getIntExtra("size", 0);
		initContent();
	}

	/** 初始化显示内容 **/
	private void initContent() {
		mGeneralFunctionFragment = new GeneralFunctionFragment();
		Bundle mBundle = new Bundle();
		mBundle.putInt("size",size);
		mGeneralFunctionFragment.setArguments(mBundle);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.content,  mGeneralFunctionFragment);
		transaction.commit();

	}

	@Override
	public void onBackPressed() {
		this.finish();
	}


	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		mBaseFragment = selectedFragment;
	}
}
