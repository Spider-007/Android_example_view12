package com.htmitech.addressbook;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.htmitech.Task.DownLoaderTask;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.fragment.AddressFragment;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.unit.ActivityUnit;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 入口
 *
 * @author Tony
 *
 */
public class BookActivity extends BaseFragmentActivity implements AddressListener,
		BackHandledInterface {
	public BaseFragment mBaseFragment;
	private AddressFragment mAddressFragment;
	private Handler mHanlder = new Handler();
	private DownLoaderTask mDownLoaderTask;
	private JSONObject jsonObject ;
	private ImageView btn_daiban_person;
	private ImageButton title_right_new_function;
	private String addressFragmentType = "";
	private int isFunction = -1; //是否支持"功能号”入口。 0，不支持；1，支持
	private int isContact = -1;//是否支持常用联系人 0 不支持 1支持
	private String app_id;
	private boolean isHome;
	private String appName;
	private String com_addressbook_mobileconfig_corporg_scope;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_activity_main);
		addressFragmentType = getIntent().getStringExtra("addressFragmentType");
		com_addressbook_mobileconfig_corporg_scope = getIntent().getStringExtra("com_addressbook_mobileconfig_corporg_scope");
		app_id = getIntent().getStringExtra("app_id");
		appName = getIntent().getStringExtra("appName");
		isHome = getIntent().getBooleanExtra("isHome",false);

		BookInit.getInstance().setTxlAppId(app_id);
		String mxpp = getIntent().getStringExtra("com_addressbook_mobileconfig_mxpp") == null ? "-1":getIntent().getStringExtra("com_addressbook_mobileconfig_mxpp");
		String contact = getIntent().getStringExtra("com_addressbook_mobileconfig_contact") == null ? "-1":getIntent().getStringExtra("com_addressbook_mobileconfig_contact");
		Constant.ROOTNODE_STRINGID = getIntent().getStringExtra("com_addressbook_mobileconfig_dataroot_orgid") == null ? "" : getIntent().getStringExtra("com_addressbook_mobileconfig_dataroot_orgid");
		Constant.ADDRESS_HEADER_TYPE = getIntent().getStringExtra("com_addressbook_mobileconfig_headertype") == null ? "2" : getIntent().getStringExtra("com_addressbook_mobileconfig_headertype");
		Constant.ADDRESS_LIST_MESSAGE= getIntent().getStringExtra("com_addressbook_mobileconfig_listinfo") == null ? "我的{title}，电话：{mobile_phone}" : getIntent().getStringExtra("com_addressbook_mobileconfig_listinfo");
		Constant.IS_WATER_BACKGROUND = getIntent().getStringExtra("com_addressbook_mobileconfig_include_security") == null ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_include_security");
		Constant.com_addressbook_mobileconfig_home_phone_secrecy = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_home_phone_secrecy"))? "1" : getIntent().getStringExtra("com_addressbook_mobileconfig_home_phone_secrecy");
		Constant.com_addressbook_mobileconfig_office_phone_secrecy = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_office_phone_secrecy")) ? "1" : getIntent().getStringExtra("com_addressbook_mobileconfig_office_phone_secrecy");
		Constant.com_addressbook_mobileconfig_mobile_phone_secrecy = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_mobile_phone_secrecy") ) ? "1" : getIntent().getStringExtra("com_addressbook_mobileconfig_mobile_phone_secrecy");
		Constant.com_addressbook_mobileconfig_otherorg_show = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_otherorg_show") ) ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_otherorg_show");
		Constant.com_addressbook_mobileconfig_viewtype = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_viewtype") ) ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_viewtype");
		Constant.com_addressbook_mobileconfig_viewtype1_level = TextUtils.isEmpty(getIntent().getStringExtra("com_addressbook_mobileconfig_viewtype1_level") ) ? "0" : getIntent().getStringExtra("com_addressbook_mobileconfig_viewtype1_level");
		isFunction = Integer.parseInt(mxpp);
		isContact = Integer.parseInt(contact);
		Constant.IS_CONTACT = isContact == 1 ? true : false;
		Constant.START_TYPE = addressFragmentType;
		initContent();
	}


	@Override
	public void onClickChild(BaseFragment f) {
		// TODO Auto-generated method stub
		switchContent(f);
	}

	/** 初始化显示内容 **/
	private void initContent() {
		BookInit.getInstance().setBookType(addressFragmentType);
		if(addressFragmentType.equals(Constant.LOING_INIT)){
			mAddressFragment = BookInit.getInstance().getMyMap().get(addressFragmentType);
		}else{
			mAddressFragment  = new AddressFragment();
			BookInit.getInstance().getMyMap().put(addressFragmentType,mAddressFragment);
		}
		if(isFunction == 0 && isContact == 0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isContact", isContact);
			map.put("isFunction",isFunction);
			map.put("appName",appName);
			map.put("isHome",isHome);
			map.put("com_addressbook_mobileconfig_corporg_scope",com_addressbook_mobileconfig_corporg_scope);
			ActivityUnit.switchTo(this, UserDetailsChildActivity.class, map);
			this.finish();
		}
		Bundle bundle=new Bundle();
		bundle.putInt("isContact", isContact);
		bundle.putInt("isFunction", isFunction);
		bundle.putString("appName",appName);
		bundle.putBoolean("isHome", isHome);
		bundle.putString("app_id",app_id);
		bundle.putString("com_addressbook_mobileconfig_corporg_scope",com_addressbook_mobileconfig_corporg_scope);
		mAddressFragment.setArguments(bundle);
		BookInit.getInstance().setmAddressFragment(mAddressFragment);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.content, mAddressFragment);
		mContent = mAddressFragment;
		transaction.commitAllowingStateLoss();
	}

	private BaseFragment mContent;

	public void switchContent(BaseFragment to) {

	}


	@Override
	public void onBackPressed() {
//		if (mBaseFragment == null || !mBaseFragment.onBackPressed()) {
//			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//				BookInit.getInstance().setBookType(Constant.LOING_INIT);
//				this.finish();
//			} else {
//				// FragmentManager fm = getSupportFragmentManager();
//				// FragmentTransaction ft = fm.beginTransaction();
//				// ft.hide(mContent).show(mAddressFragment).commit();
//				// mBaseFragment.setFag(false);
//				getSupportFragmentManager().popBackStack();
//			}
//		}
		BookInit.getInstance().setBookType(Constant.LOING_INIT);
		this.finish();
	}

	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		// TODO Auto-generated method stub
		mBaseFragment = selectedFragment;
	}
}
