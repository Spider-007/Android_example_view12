package com.htmitech.emportal.ui.plugin.oamodel;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.SlidingBackAcitivity;
import com.htmitech.emportal.ui.daiban.MineListFragment;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.ui.widget.DaiBanTopTabIndicator;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.widget.SystemMainTopRightPopMenu;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/***
 * OA待办/已办通用fragment
 * 
 * @author tenggang
 * 
 */
public class OAModelFragmentActivity extends SlidingBackAcitivity implements 
		IBaseCallback, IBottomItemSelectCallBack, View.OnClickListener {

	private DaiBanTopTabIndicator mDaiBanTopTabIndicator;
	private FragmentManager mFragmentManager;
//	private PopupWindow mPopupWindow = null;

	private YiBanListFragment mYiBanListFragment;
	private MineListFragment mGuanZhuListFragment;

	private static final int FRAGMENT_DAIBAN = 0;
	private static final int FRAGMENT_YIBAN = 1;
	private static final int FRAGMENT_GUANZHU = 2;

	private SystemMainTopRightPopMenu functionPopMenu;
	
	private ImageView functionButton;
	
	private String modelName;
	public String app_id;
	private int current_item;
	private int isStartValue;//是否支持我发起的
	private int isFavValue;//是否支持我关注的
	private int todoFlag;
	//新增
	public int isWaterSecurity; //是不是支持水印
	public int isShare;  //是否支持表单分享功能。
	public int actionButtonStyle = -1;//工作流表单详情中的操作按钮样式
	public int customerShortcuts = -1;//是否支持快捷键自定义
	public int com_workflow_mobileconfig_IM_enabled;//作流表单详情和流程历史中，是否支持点击人员跳转聊天的功能 0，不支持；1，支持。
	public String com_workflow_plugin_selector_paramter_Title = "";
	public String appName;
	public TextView tv_title;
	public long tab_item_id;
	public DaiBanListFragment	mDaiBanListFragment;
	public String com_workflow_plugin_selector_paramter_TodoFlag;
	@Override
	protected int getLayoutById() {
		return R.layout.activity_oamodel_daiban;
	}

	@Override
	protected void initView() {
		findViewById(R.id.btn_daiban_back).setOnClickListener(this);
		functionButton = (ImageView)findViewById(R.id.imageview_daiban_more);
//		functionButton.setVisibility(View.GONE);
//		functionPopMenu = new SystemMainTopRightPopMenu(OAModelFragmentActivity.this);
//		functionPopMenu.setForTodo();
//		functionButton.setOnClickListener(this);
		Intent intent = getIntent();
		modelName = intent.getStringExtra("com_workflow_plugin_selector_paramter_ModelName");
		appName = intent.getStringExtra("appName");
		app_id = intent.getStringExtra("app_id");
		current_item = getIntent().getIntExtra("current_item", 0);
		tab_item_id = getIntent().getLongExtra("tab_item_id", 0);
		String com_workflow_mobileconfig_include_myfav = getIntent().getStringExtra("com_workflow_mobileconfig_include_myfav");
		String com_workflow_mobileconfig_include_mystart = getIntent().getStringExtra("com_workflow_mobileconfig_include_mystart");
		String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_workflow_mobileconfig_actionbutton_style");
		String com_workflow_mobileconfig_customer_shortcuts = getIntent().getStringExtra("com_workflow_mobileconfig_customer_shortcuts");
		String com_workflow_mobileconfig_IM_enabledStr = getIntent().getStringExtra("com_workflow_mobileconfig_IM_enabled");
		com_workflow_plugin_selector_paramter_TodoFlag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_TodoFlag");
		String com_workflow_plugin_selector_paramter_IsMyFav = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyFav");
		String com_workflow_plugin_selector_paramter_IsMyStart = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyStart");
		String com_workflow_mobileconfig_include_security = getIntent().getStringExtra("com_workflow_mobileconfig_include_security");
		String com_workflow_mobileconfig_include_share = getIntent().getStringExtra("com_workflow_mobileconfig_include_share");
		com_workflow_plugin_selector_paramter_Title = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_Title");
		modelName = modelName == null ? "":modelName;
		com_workflow_mobileconfig_IM_enabledStr = com_workflow_mobileconfig_IM_enabledStr == null ? "0" : com_workflow_mobileconfig_IM_enabledStr;
		com_workflow_mobileconfig_include_myfav = com_workflow_mobileconfig_include_myfav == null ? "0":com_workflow_mobileconfig_include_myfav;
		com_workflow_mobileconfig_include_mystart = com_workflow_mobileconfig_include_mystart == null ? "0":com_workflow_mobileconfig_include_mystart;
		com_workflow_mobileconfig_actionbutton_style = com_workflow_mobileconfig_actionbutton_style == null ? "0" : com_workflow_mobileconfig_actionbutton_style;
		com_workflow_mobileconfig_customer_shortcuts = com_workflow_mobileconfig_customer_shortcuts == null ? "0" : com_workflow_mobileconfig_customer_shortcuts;
		com_workflow_mobileconfig_include_security = com_workflow_mobileconfig_include_security == null ? "0" : com_workflow_mobileconfig_include_security;
		com_workflow_plugin_selector_paramter_Title = com_workflow_plugin_selector_paramter_Title == null ? "" : com_workflow_plugin_selector_paramter_Title;
		com_workflow_mobileconfig_include_share = com_workflow_mobileconfig_include_share == null ? "0" : com_workflow_mobileconfig_include_share;
		com_workflow_plugin_selector_paramter_TodoFlag = com_workflow_plugin_selector_paramter_TodoFlag == null ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag;
		com_workflow_plugin_selector_paramter_IsMyFav = com_workflow_plugin_selector_paramter_IsMyFav == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav;
		com_workflow_plugin_selector_paramter_IsMyStart = com_workflow_plugin_selector_paramter_IsMyStart == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart;
		isStartValue = Integer.parseInt(com_workflow_mobileconfig_include_mystart.equals("") ? "0" : com_workflow_mobileconfig_include_mystart);
		isFavValue = Integer.parseInt(com_workflow_mobileconfig_include_myfav.equals("") ? "0" : com_workflow_mobileconfig_include_myfav);
		actionButtonStyle = Integer.parseInt(com_workflow_mobileconfig_actionbutton_style.equals("") ? "0" : com_workflow_mobileconfig_actionbutton_style);
		customerShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_shortcuts.equals("") ? "0" : com_workflow_mobileconfig_customer_shortcuts);
		com_workflow_mobileconfig_IM_enabled = Integer.parseInt(com_workflow_mobileconfig_IM_enabledStr.equals("") ? "0" : com_workflow_mobileconfig_IM_enabledStr);
		isShare = Integer.parseInt(com_workflow_mobileconfig_include_share.equals("") ? "0" : com_workflow_mobileconfig_include_share);
		isWaterSecurity = Integer.parseInt(com_workflow_mobileconfig_include_security.equals("") ? "0" : com_workflow_mobileconfig_include_security);

		todoFlag = Integer.parseInt(com_workflow_plugin_selector_paramter_TodoFlag.equals("") ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag);
		String com_commonform_plugin_selector_paramter_todoflag = intent.getStringExtra("com_workflow_plugin_selector_paramter_TodoFlag");
		if(TextUtils.isEmpty(com_commonform_plugin_selector_paramter_todoflag)){
			com_commonform_plugin_selector_paramter_todoflag = "";
		}
		if(com_commonform_plugin_selector_paramter_todoflag!=null)
		todoFlag = Integer.parseInt(com_commonform_plugin_selector_paramter_todoflag.equals("") ? "0" : com_commonform_plugin_selector_paramter_todoflag);
		mFragmentManager = getSupportFragmentManager();
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		mDaiBanTopTabIndicator = (DaiBanTopTabIndicator) this
				.findViewById(R.id.daibantopTabIndicator_bbslist);
		mDaiBanTopTabIndicator.setVisibility(View.GONE);
//		mDaiBanTopTabIndicator.setViewPager(mViewpager);
		String strOAUserID = PreferenceUtils.getOAUserID(this);
		Log.d("DaiBanFragmentActivity", strOAUserID);

		tv_title.setVisibility(View.VISIBLE);
		tv_title.setText(appName);
		mDaiBanListFragment = new DaiBanListFragment();
		mDaiBanListFragment.setModelName(modelName);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.ll_general_from_child, mDaiBanListFragment);
		transaction.show(mDaiBanListFragment);
		ToRightPopMenum functionPopMenu = new ToRightPopMenum(
				this);
		functionPopMenu.setView(tab_item_id, functionButton);

		//提交事务
		transaction.commit();
	}


	@Override
	public void onSuccess(int statusCode, Object result) {
		// TODO Auto-generated method stub
		//成功时返回的 result 类型 com.htmitech.htmioa.ui.daiban.data.getdoclist.GetDocListEntity
		
		if (result != null){
			GetDocListEntity entity = (GetDocListEntity)result;
//			Utils.toast(DaiBanFragmentActivity.this, "取得了" +  entity.getResult().length + "条待办信息", Toast.LENGTH_SHORT);
			Log.d("DaiBanFragmentActivity", "取得了" +  entity.getResult().length + "条待办信息");
		}
	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentTabClick(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_daiban_back:
			finish();
			break;
		case R.id.imageview_daiban_more:
			if (!functionPopMenu.isShowing()) {
				functionPopMenu.showAsDropDown(arg0);
			}
//			
//			showPopWindowOperate(arg0);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
//		Utils.toast(DaiBanFragmentActivity.this, "返回了0:" + arg0 + ",1:" + arg1, Toast.LENGTH_SHORT);
		if(mDaiBanListFragment != null){
			mDaiBanListFragment.refreshOcuData();
		}
	}


}
