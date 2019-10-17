package com.htmitech.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;

import com.htmitech.Task.DownLoaderTask;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.fragment.UpdateUserDetailsChildFragment;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.BackHandledInterface;

/**
 * 单位通讯录
 *
 * @author Tony
 *
 */
public class UserDetailsChildActivity extends BaseFragmentActivity implements
		AddressListener, BackHandledInterface{
	public BaseFragment mBaseFragment;
	private UpdateUserDetailsChildFragment mUserDetailsChildFragment;
	private Handler mHanlder = new Handler();
	private DownLoaderTask mDownLoaderTask;
	private int size ;
	private int isContact = -1;

	private int isFunction;

	private boolean isHome;
	private String appName;
	private String com_addressbook_mobileconfig_corporg_scope;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_activity_main);
		Intent intent = getIntent();
		size = intent.getIntExtra("size", 0);
		isContact = intent.getIntExtra("isContact", -1);
		isHome = intent.getBooleanExtra("isHome", false);
		appName = intent.getStringExtra("appName");
		com_addressbook_mobileconfig_corporg_scope = intent.getStringExtra("com_addressbook_mobileconfig_corporg_scope");
		isFunction = intent.getIntExtra("isFunction", -1);
		if(isHome){
			String mxpp = getIntent().getStringExtra("com_addressbook_mobileconfig_mxpp") == null ? "-1":getIntent().getStringExtra("com_addressbook_mobileconfig_mxpp");
			String contact = getIntent().getStringExtra("com_addressbook_mobileconfig_contact") == null ? "-1":getIntent().getStringExtra("com_addressbook_mobileconfig_contact");
			Constant.ROOTNODE_STRINGID = getIntent().getStringExtra("com_addressbook_mobileconfig_dataroot_orgid") == null ? "" : getIntent().getStringExtra("com_addressbook_mobileconfig_dataroot_orgid");
			Constant.ADDRESS_HEADER_TYPE = getIntent().getStringExtra("com_addressbook_mobileconfig_headertype") == null ? "2" : getIntent().getStringExtra("com_addressbook_mobileconfig_headertype");
			Constant.ADDRESS_LIST_MESSAGE= getIntent().getStringExtra("com_addressbook_mobileconfig_listinfo") == null ? "我的{title}，电话：{mobile_phone}" : getIntent().getStringExtra("com_addressbook_mobileconfig_listinfo");
			Constant.IS_WATER_BACKGROUND = getIntent().getStringExtra("com_addressbook_mobileconfig_include_security") == null ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_include_security");
			Constant.com_addressbook_mobileconfig_otherorg_show = getIntent().getStringExtra("com_addressbook_mobileconfig_otherorg_show") == null ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_otherorg_show");
		}
		initContent();
	}

	@Override
	public void onClickChild(BaseFragment f) {
		// TODO Auto-generated method stub
		this.finish();
	}

	/** 初始化显示内容 **/
	private void initContent() {

		mUserDetailsChildFragment = new UpdateUserDetailsChildFragment();
		Bundle mBundle = new Bundle();
		mBundle.putInt("size", size);
		mBundle.putInt("isContact",isContact);
		mBundle.putInt("isFunction",isFunction);
		mBundle.putString("appName",appName);
		mBundle.putString("com_addressbook_mobileconfig_corporg_scope",com_addressbook_mobileconfig_corporg_scope);
		mBundle.putBoolean("isHome",isHome);
		mUserDetailsChildFragment.setArguments(mBundle);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
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
