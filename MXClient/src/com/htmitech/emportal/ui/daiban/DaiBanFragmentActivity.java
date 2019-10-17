package com.htmitech.emportal.ui.daiban;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.ui.widget.DaiBanTopTabIndicator;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/***
 * 待办fragment
 *
 * @author tenggang
 */
public class DaiBanFragmentActivity extends MyBaseFragmentActivity implements
        IBaseCallback, IBottomItemSelectCallBack, View.OnClickListener {

    private DaiBanTopTabIndicator mDaiBanTopTabIndicator;
    public static MainViewPager mViewpager;
    private FragmentManager mFragmentManager;
//	private PopupWindow mPopupWindow = null;

    private DaiBanListFragment mDaiBanListFragment;
    private YiBanListFragment mYiBanListFragment;
    private MineListFragment mGuanZhuListFragment;
    private static final int FRAGMENT_DAIBAN = 0;
    private static final int FRAGMENT_YIBAN = 1;
    private static final int FRAGMENT_GUANZHU = 2;

    private ToRightPopMenum functionPopMenu;

    private ImageView functionButton;
    private TextView tv_daiban_title;
    private boolean flag = false; //是否是首页 还是新开页面
    private int current_item;
    private int isStartValue;//是否支持我发起的
    private int isFavValue;//是否支持我关注的
    public int actionButtonStyle = -1;//工作流表单详情中的操作按钮样式
    public String app_id;//appid 新加参数
    public int com_workflow_mobileconfig_IM_enabled;//作流表单详情和流程历史中，是否支持点击人员跳转聊天的功能 0，不支持；1，支持。
    public int todoFlag = -1; //代办已办标记 0代办 1已办
    public int isMyFav = -1;//是否要查询我关注的
    public int isMyStart = -1;//是否要查询我发起的
    public String modelName = "";
    public int customerShortcuts = -1;//是否支持快捷键自定义
    //新增
    public int isWaterSecurity; //是不是支持水印
    public int isShare;  //是否支持表单分享功能。\
    public int isTextUrl;//是否正文展示HTML
    public String  app_version_id;//当前应用的版本




    @Override
    protected int getLayoutById() {
        return R.layout.activity_daiban;
    }

    @Override
    protected void initView() {
        ImageView btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
        ImageView btn_daiban_person_home = (ImageView)findViewById(R.id.btn_daiban_person_home);
        btn_daiban_person.setOnClickListener(this);
        btn_daiban_person_home.setOnClickListener(this);
        String current_itemStr = getIntent().getStringExtra("current_item");
        String com_workflow_mobileconfig_include_myfav = getIntent().getStringExtra("com_workflow_mobileconfig_include_myfav");
        String com_workflow_mobileconfig_include_mystart = getIntent().getStringExtra("com_workflow_mobileconfig_include_mystart");
        String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_workflow_mobileconfig_actionbutton_style");
        String com_workflow_mobileconfig_customer_shortcuts = getIntent().getStringExtra("com_workflow_mobileconfig_customer_shortcuts");
        String com_workflow_mobileconfig_IM_enabledStr = getIntent().getStringExtra("com_workflow_mobileconfig_IM_enabled");
        String com_workflow_plugin_selector_paramter_TodoFlag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_TodoFlag");
        String com_workflow_plugin_selector_paramter_IsMyFav = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyFav");
        String com_workflow_plugin_selector_paramter_IsMyStart = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyStart");
        String com_workflow_mobileconfig_include_security = getIntent().getStringExtra("com_workflow_mobileconfig_include_security");
        String com_workflow_mobileconfig_include_share = getIntent().getStringExtra("com_workflow_mobileconfig_include_share");
        String com_workflow_mobileconfig_docview_style = getIntent().getStringExtra("com_workflow_mobileconfig_docview_style");
        modelName = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_ModelName");
        modelName = modelName == null ? "":modelName;
        com_workflow_mobileconfig_IM_enabledStr = com_workflow_mobileconfig_IM_enabledStr == null ? "0" : com_workflow_mobileconfig_IM_enabledStr;
        current_itemStr = current_itemStr == null ? "0" : current_itemStr;
        com_workflow_mobileconfig_include_myfav = com_workflow_mobileconfig_include_myfav == null ? "0":com_workflow_mobileconfig_include_myfav;
        com_workflow_mobileconfig_include_mystart = com_workflow_mobileconfig_include_mystart == null ? "0":com_workflow_mobileconfig_include_mystart;
        com_workflow_mobileconfig_actionbutton_style = com_workflow_mobileconfig_actionbutton_style == null ? "0" : com_workflow_mobileconfig_actionbutton_style;
        com_workflow_mobileconfig_customer_shortcuts = com_workflow_mobileconfig_customer_shortcuts == null ? "0" : com_workflow_mobileconfig_customer_shortcuts;
        com_workflow_mobileconfig_include_security = com_workflow_mobileconfig_include_security == null ? "0" : com_workflow_mobileconfig_include_security;
        com_workflow_mobileconfig_include_share = com_workflow_mobileconfig_include_share == null ? "0" : com_workflow_mobileconfig_include_share;
        com_workflow_mobileconfig_docview_style = com_workflow_mobileconfig_docview_style == null ? "0" : com_workflow_mobileconfig_docview_style;
        com_workflow_plugin_selector_paramter_TodoFlag = com_workflow_plugin_selector_paramter_TodoFlag == null ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag;
        com_workflow_plugin_selector_paramter_IsMyFav = com_workflow_plugin_selector_paramter_IsMyFav == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav;
        com_workflow_plugin_selector_paramter_IsMyStart = com_workflow_plugin_selector_paramter_IsMyStart == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart;
        current_item = Integer.parseInt(current_itemStr.equals("") ? "0":current_itemStr);
        isStartValue = Integer.parseInt(com_workflow_mobileconfig_include_mystart.equals("") ? "0" : com_workflow_mobileconfig_include_mystart);
        isFavValue = Integer.parseInt(com_workflow_mobileconfig_include_myfav.equals("") ? "0" : com_workflow_mobileconfig_include_myfav);
        actionButtonStyle = Integer.parseInt(com_workflow_mobileconfig_actionbutton_style.equals("") ? "0" : com_workflow_mobileconfig_actionbutton_style);
        customerShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_shortcuts.equals("") ? "0" : com_workflow_mobileconfig_customer_shortcuts);
        com_workflow_mobileconfig_IM_enabled = Integer.parseInt(com_workflow_mobileconfig_IM_enabledStr.equals("") ? "0" : com_workflow_mobileconfig_IM_enabledStr);
        isShare = Integer.parseInt(com_workflow_mobileconfig_include_share.equals("") ? "0" : com_workflow_mobileconfig_include_share);
        isTextUrl = Integer.parseInt(com_workflow_mobileconfig_docview_style.equals("") ? "0" : com_workflow_mobileconfig_docview_style);
        isWaterSecurity = Integer.parseInt(com_workflow_mobileconfig_include_security.equals("") ? "0" : com_workflow_mobileconfig_include_security);

        todoFlag = Integer.parseInt(com_workflow_plugin_selector_paramter_TodoFlag.equals("") ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag);
        isMyFav = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyFav.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav);
        isMyStart = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyStart.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart);
        flag = getIntent().getBooleanExtra("Type", false);
        app_id = getIntent().getStringExtra("app_id");
        app_version_id = getIntent().getStringExtra("app_version_id");
        if(!flag){
            btn_daiban_person_home.setVisibility(View.VISIBLE);
            btn_daiban_person.setVisibility(View.GONE);
        }
        functionButton = (ImageView) findViewById(R.id.imageview_daiban_more);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(ApplicationAllEnum.DB.tab_item_id,functionButton);

        tv_daiban_title = (TextView) findViewById(R.id.tv_daiban_title);
        functionButton.setOnClickListener(this);
        mFragmentManager = getSupportFragmentManager();
        mViewpager = (MainViewPager) this.findViewById(R.id.daiban_viewpager_daiban);
        mViewpager.setAdapter(new ViewPagerAdapter(mFragmentManager));
        mViewpager.setNoScroll(true);
        mViewpager.setOffscreenPageLimit(4);
        mDaiBanTopTabIndicator = (DaiBanTopTabIndicator)
                findViewById(R.id.daiban_topTabIndicator_bbslist);
        mDaiBanTopTabIndicator.setIsStartValue(isStartValue | isFavValue);
        mDaiBanTopTabIndicator.setViewPager(mViewpager);
        //判断是否要进入那个ViewPager的
        if((isStartValue | isFavValue) == 1 && (isMyFav == 1 || isMyStart == 1)){
            current_item = 2;
        }else{
            todoFlag = todoFlag == -1 ? 0 : todoFlag;
            current_item = todoFlag;
        }


        mViewpager.setCurrentItem(current_item);
        String strOAUserID = PreferenceUtils.getOAUserID(this);
        Log.d("DaiBanFragmentActivity", strOAUserID);


//		//启动帮助页面
//		if (PreferenceUtils.isLoginDaiYiBan(this)) {
//			PreferenceUtils.setLoginDaiYiBan(this, false);
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					Intent intent = new Intent(DaiBanFragmentActivity.this, HelpActivity.class);
//					intent.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
//					startActivity(intent);
//				}
//			}, 2000);
//		}
        //启动帮助页面
        if (PreferenceUtils.isLoginDaiYiBan(this)) {
            PreferenceUtils.setLoginDaiYiBan(this, false);
            Intent i = new Intent(this, HelpActivity.class);
            i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
            startActivity(i);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        this.finish();
        return super.onKeyDown(keyCode, event);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_YIBAN:
                    if (mYiBanListFragment == null)
                        mYiBanListFragment = new YiBanListFragment();
                    return mYiBanListFragment;
                case FRAGMENT_GUANZHU:
                    if (mGuanZhuListFragment == null)
                        mGuanZhuListFragment = new MineListFragment();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("isFavValue",isFavValue);
                    mBundle.putInt("isStartValue",isStartValue);
                    mGuanZhuListFragment.setArguments(mBundle);
                    return mGuanZhuListFragment;
                case FRAGMENT_DAIBAN:
                default:
                    if (mDaiBanListFragment == null)
                        mDaiBanListFragment = new DaiBanListFragment();
                    return mDaiBanListFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).getClass().getSimpleName();
        }
    }

    @Override
    public void onSuccess(int statusCode, Object result) {
        // TODO Auto-generated method stub
        //成功时返回的 result 类型 com.htmitech.htmioa.ui.daiban.data.getdoclist.GetDocListEntity

        if (result != null) {
            GetDocListEntity entity = (GetDocListEntity) result;
//			Utils.toast(DaiBanFragmentActivity.this, "取得了" +  entity.getResult().length + "条待办信息", Toast.LENGTH_SHORT);
            Log.d("DaiBanFragmentActivity", "取得了" + entity.getResult().length + "条待办信息");
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
            case R.id.btn_daiban_person_home:
            case R.id.btn_daiban_person:
                if(flag){
                    this.finish();
                }else{
                    ((ClientTabActivity) getParent()).callUserMeesageMain();
                }

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
        if (arg1 == ActivityResultConstant.SAVEOCUS_RESULT_OK && mDaiBanListFragment != null){

            mDaiBanListFragment.initData();

        }else if (arg1 == ActivityResultConstant.DOACTION_RESULT_OK) {
            String kind = arg2.getStringExtra("kind");
            if (mDaiBanListFragment != null)
                mDaiBanListFragment.pullDownRefresh();
            else if (mYiBanListFragment != null)
                mYiBanListFragment.pullDownRefresh(); //未测试功能代码 ，能保证不报错
        }
    }

}
