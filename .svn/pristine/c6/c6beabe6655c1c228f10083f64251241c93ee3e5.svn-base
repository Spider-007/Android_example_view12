package com.htmitech.htcommonformplugin.activity;

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

import com.htmitech.app.Constant;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.ui.widget.DaiBanTopTabIndicator;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.htcommonformplugin.fragment.GeneralFormMineListFragment;
import com.htmitech.htcommonformplugin.fragment.GeneralFormTodoListFragment;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.minxing.client.ClientTabActivity;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/***
 * 通用表单构件入口
 *
 * @author joe
 * @date 2017/04/17
 */
public class InitFormFragmentActivity extends MyBaseFragmentActivity implements
        IBaseCallback, IBottomItemSelectCallBack, View.OnClickListener {

    private DaiBanTopTabIndicator mDaiBanTopTabIndicator;
    public static MainViewPager mViewpager;
    private FragmentManager mFragmentManager;
//	private PopupWindow mPopupWindow = null;

    private GeneralFormTodoListFragment mGeneralFormTodoListFragment;
    private GeneralFormTodoListFragment mGeneralFormTodoListFragmentYB;
    private GeneralFormMineListFragment mGuanZhuListFragment;
    private static final int FRAGMENT_DAIBAN = 0;
    private static final int FRAGMENT_YIBAN = 1;
    private static final int FRAGMENT_GUANZHU = 2;

    private ToRightPopMenum functionPopMenu;

    private ImageView functionButton;
    private TextView tv_daiban_title;
    private boolean flag = false; //是否是首页 还是新开页面
    private int current_item;
    private int isStartValue;//是否支持他人关注的功能。 0，不支持；1，支持
    private int isFavValue;//是否支持“我的关注”。 0，不支持；1，支持
    private int isOtherFavValue;//是否支持他人关注的
    private int isWaterSecurity;//是否支持水印
    public int actionButtonStyle = -1;//浮动按钮与底部按钮的
    public String app_id;//appid 新加参数
    public int com_workflow_mobileconfig_IM_enabled;//作流表单详情和流程历史中，是否支持点击人员跳转聊天的功能 0，不支持；1，支持。
    public int todoFlag = -1; //代办已办标记 0代办 1已办
    public int isMyFav = -1;//是否要查询我关注的
    public int isMyStart = -1;//是否要查询我发起的
    public int isShare = -1;//是否isShare分享
    public String modelName = "";
    public int customerShortcuts = -1;
    private ViewPagerAdapter mViewPagerAdapter;

    //新增参数
    private int  hasdoneShortcuts ; //已办面板中是否支持快捷键自定义
    private int  hasdoneShortcutsview; //是否需要在已办中显示“筛选面板”
    private int  todoShortcuts; //待办面板中是否支持快捷键自定义
    private int  todoShortcutsview; //是否需要在待办中显示“筛选面板”
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
        String com_workflow_mobileconfig_include_myfav = getIntent().getStringExtra("com_commonform_mobileconfig_include_myfav");

        String com_commonform_mobileconfig_include_otherfav = getIntent().getStringExtra("com_commonform_mobileconfig_include_otherfav");
        String com_commonform_mobileconfig_include_security = getIntent().getStringExtra("com_commonform_mobileconfig_include_security");
        String com_commonform_mobileconfig_include_share = getIntent().getStringExtra("com_commonform_mobileconfig_include_share");
        String com_workflow_mobileconfig_include_mystart = getIntent().getStringExtra("com_commonform_mobileconfig_include_mystart");

        String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_commonform_mobileconfig_actionbutton_style");
        String com_workflow_mobileconfig_customer_shortcuts = getIntent().getStringExtra("com_commonform_mobileconfig_customer_shortcuts");
        String com_workflow_mobileconfig_IM_enabledStr = getIntent().getStringExtra("com_commonform_mobileconfig_IM_enabled");
        String com_workflow_plugin_selector_paramter_TodoFlag = getIntent().getStringExtra("com_commonform_plugin_selector_paramter_TodoFlag");
        String com_workflow_plugin_selector_paramter_IsMyFav = getIntent().getStringExtra("com_commonform_plugin_selector_paramter_IsMyFav");
        String com_workflow_plugin_selector_paramter_IsMyStart = getIntent().getStringExtra("com_commonform_plugin_selector_paramter_IsMyStart");
        //新增快捷键
        String com_commonform_mobileconfig_customer_hasdone_shortcuts = getIntent().getStringExtra("com_commonform_mobileconfig_customer_hasdone_shortcuts");//已办面板中是否支持快捷键自定义
        String com_commonform_mobileconfig_customer_hasdone_shortcutsview = getIntent().getStringExtra("com_commonform_mobileconfig_customer_hasdone_shortcutsview");//是否需要在已办中显示“筛选面板”
        String com_commonform_mobileconfig_customer_todo_shortcuts = getIntent().getStringExtra("com_commonform_mobileconfig_customer_todo_shortcuts"); //待办面板中是否支持快捷键自定义
        String com_commonform_mobileconfig_customer_todo_shortcutsview = getIntent().getStringExtra("com_commonform_mobileconfig_customer_todo_shortcutsview");//是否需要在待办中显示“筛选面板”

        modelName = getIntent().getStringExtra("com_commonform_plugin_selector_paramter_ModelName");
        modelName = modelName == null ? "" : modelName;
        com_workflow_mobileconfig_IM_enabledStr = com_workflow_mobileconfig_IM_enabledStr == null ? "0" : com_workflow_mobileconfig_IM_enabledStr;
        current_itemStr = current_itemStr == null ? "0" : current_itemStr;
        com_workflow_mobileconfig_include_myfav = com_workflow_mobileconfig_include_myfav == null ? "0" : com_workflow_mobileconfig_include_myfav;
        com_commonform_mobileconfig_include_otherfav = com_commonform_mobileconfig_include_otherfav == null ? "0" : com_commonform_mobileconfig_include_otherfav;
        com_commonform_mobileconfig_include_security = com_commonform_mobileconfig_include_security == null ? "0" : com_commonform_mobileconfig_include_security;
        com_commonform_mobileconfig_include_share = com_commonform_mobileconfig_include_share == null ? "0" : com_commonform_mobileconfig_include_share;
        com_workflow_mobileconfig_include_mystart = com_workflow_mobileconfig_include_mystart == null ? "0" : com_workflow_mobileconfig_include_mystart;
        com_workflow_mobileconfig_actionbutton_style = com_workflow_mobileconfig_actionbutton_style == null ? "0" : com_workflow_mobileconfig_actionbutton_style;
        com_workflow_mobileconfig_customer_shortcuts = com_workflow_mobileconfig_customer_shortcuts == null ? "0" : com_workflow_mobileconfig_customer_shortcuts;
        com_workflow_plugin_selector_paramter_TodoFlag = com_workflow_plugin_selector_paramter_TodoFlag == null ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag;
        com_workflow_plugin_selector_paramter_IsMyFav = com_workflow_plugin_selector_paramter_IsMyFav == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav;
        com_workflow_plugin_selector_paramter_IsMyStart = com_workflow_plugin_selector_paramter_IsMyStart == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart;
        com_commonform_mobileconfig_customer_hasdone_shortcuts =  com_commonform_mobileconfig_customer_hasdone_shortcuts == null ? "1" : com_commonform_mobileconfig_customer_hasdone_shortcuts;
        com_commonform_mobileconfig_customer_hasdone_shortcutsview = com_commonform_mobileconfig_customer_hasdone_shortcutsview == null ? "1" : com_commonform_mobileconfig_customer_hasdone_shortcutsview;
        com_commonform_mobileconfig_customer_todo_shortcuts = com_commonform_mobileconfig_customer_todo_shortcuts == null ? "1" : com_commonform_mobileconfig_customer_todo_shortcuts;
        com_commonform_mobileconfig_customer_todo_shortcutsview = com_commonform_mobileconfig_customer_todo_shortcutsview == null ? "1" : com_commonform_mobileconfig_customer_todo_shortcutsview;


        current_item = Integer.parseInt(current_itemStr.equals("") ? "0" : current_itemStr);
        isStartValue = Integer.parseInt(com_workflow_mobileconfig_include_mystart.equals("") ? "0" : com_workflow_mobileconfig_include_mystart);
        isFavValue = Integer.parseInt(com_workflow_mobileconfig_include_myfav.equals("") ? "0" : com_workflow_mobileconfig_include_myfav);
        isOtherFavValue = Integer.parseInt(com_commonform_mobileconfig_include_otherfav.equals("") ? "0" : com_commonform_mobileconfig_include_otherfav);
        isWaterSecurity = Integer.parseInt(com_commonform_mobileconfig_include_security.equals("") ? "0" : com_commonform_mobileconfig_include_security);
        isShare = Integer.parseInt(com_commonform_mobileconfig_include_share.equals("") ? "0" : com_commonform_mobileconfig_include_share);
        actionButtonStyle = Integer.parseInt(com_workflow_mobileconfig_actionbutton_style.equals("") ? "0" : com_workflow_mobileconfig_actionbutton_style);
        customerShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_shortcuts.equals("") ? "0" : com_workflow_mobileconfig_customer_shortcuts);
        com_workflow_mobileconfig_IM_enabled = Integer.parseInt(com_workflow_mobileconfig_IM_enabledStr.equals("") ? "0" : com_workflow_mobileconfig_IM_enabledStr);
        todoFlag = Integer.parseInt(com_workflow_plugin_selector_paramter_TodoFlag.equals("") ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag);
        isMyFav = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyFav.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav);
        isMyStart = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyStart.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart);
        hasdoneShortcuts = Integer.parseInt(com_commonform_mobileconfig_customer_hasdone_shortcuts.equals("") ? "1" : com_commonform_mobileconfig_customer_hasdone_shortcuts) ; //已办面板中是否支持快捷键自定义
        hasdoneShortcutsview = Integer.parseInt(com_commonform_mobileconfig_customer_hasdone_shortcutsview.equals("") ? "1" : com_commonform_mobileconfig_customer_hasdone_shortcutsview); //是否需要在已办中显示“筛选面板”
        todoShortcuts = Integer.parseInt(com_commonform_mobileconfig_customer_todo_shortcuts.equals("") ? "1" : com_commonform_mobileconfig_customer_todo_shortcuts); //待办面板中是否支持快捷键自定义
        todoShortcutsview = Integer.parseInt(com_commonform_mobileconfig_customer_todo_shortcutsview.equals("") ? "1" : com_commonform_mobileconfig_customer_todo_shortcutsview); //是否需要在待办中显示“筛选面板”

        flag = getIntent().getBooleanExtra("Type", false);
//        getIntent().putExtra("hideSearch",true);
        app_id = getIntent().getStringExtra("app_id");
        if(!flag){
            btn_daiban_person_home.setVisibility(View.VISIBLE);
            btn_daiban_person.setVisibility(View.GONE);
        }
        functionButton = (ImageView) findViewById(R.id.imageview_daiban_more);
        functionPopMenu = new ToRightPopMenum(
                this);
        functionPopMenu.setView(ApplicationAllEnum.DB.tab_item_id, functionButton);

        tv_daiban_title = (TextView) findViewById(R.id.tv_daiban_title);
        functionButton.setOnClickListener(this);





        String strOAUserID = PreferenceUtils.getOAUserID(this);
        Log.d("InitForm", strOAUserID);


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

        Constant.addGeneralChannel = !flag ? "notHome":"home";
        onFragmentManage();
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
                    if (mGeneralFormTodoListFragmentYB == null)
                        mGeneralFormTodoListFragmentYB = new GeneralFormTodoListFragment();
                    Bundle mBundleYB = new Bundle();
                    mBundleYB.putString("com_commonform_plugin_selector_paramter_todoflag", "1");
                    mBundleYB.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
                    mBundleYB.putInt("com_commonform_mobileconfig_customer_shortcuts", customerShortcuts);
                    mBundleYB.putInt("com_commonform_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                    mBundleYB.putInt("com_commonform_mobileconfig_include_myfav", isFavValue);
                    mBundleYB.putInt("com_commonform_mobileconfig_include_otherfav", isOtherFavValue);
                    mBundleYB.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
                    mBundleYB.putInt("com_commonform_mobileconfig_include_share", isShare);
                    mBundleYB.putInt("com_commonform_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                    mBundleYB.putInt("com_commonform_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                    mBundleYB.putInt("com_commonform_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                    mBundleYB.putInt("com_commonform_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                    mGeneralFormTodoListFragmentYB.setArguments(mBundleYB);
                    return mGeneralFormTodoListFragmentYB;
                case FRAGMENT_GUANZHU:
                    if (mGuanZhuListFragment == null)
                        mGuanZhuListFragment = new GeneralFormMineListFragment();
                    Bundle mBundleGZ = new Bundle();
                    mBundleGZ.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
                    mBundleGZ.putInt("com_commonform_mobileconfig_customer_shortcuts", customerShortcuts);
                    mBundleGZ.putInt("com_commonform_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                    mBundleGZ.putInt("com_commonform_mobileconfig_include_myfav", isFavValue);
                    mBundleGZ.putInt("com_commonform_mobileconfig_include_otherfav", isOtherFavValue);
                    mBundleGZ.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
                    mBundleGZ.putInt("com_commonform_mobileconfig_include_share", isShare);
                    mBundleGZ.putInt("com_commonform_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                    mBundleGZ.putInt("com_commonform_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                    mBundleGZ.putInt("com_commonform_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                    mBundleGZ.putInt("com_commonform_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                    mGuanZhuListFragment.setArguments(mBundleGZ);
                    return mGuanZhuListFragment;
                case FRAGMENT_DAIBAN:
                default:
                    if (mGeneralFormTodoListFragment == null)
                        mGeneralFormTodoListFragment = new GeneralFormTodoListFragment();
                    Bundle mBundleDB = new Bundle();
                    mBundleDB.putString("com_commonform_plugin_selector_paramter_todoflag", "0");
                    mBundleDB.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
                    mBundleDB.putInt("com_commonform_mobileconfig_customer_shortcuts", customerShortcuts);
                    mBundleDB.putInt("com_commonform_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                    mBundleDB.putInt("com_commonform_mobileconfig_include_myfav", isFavValue);
                    mBundleDB.putInt("com_commonform_mobileconfig_include_otherfav", isOtherFavValue);
                    mBundleDB.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
                    mBundleDB.putInt("com_commonform_mobileconfig_include_share", isShare);
                    mBundleDB.putInt("com_commonform_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                    mBundleDB.putInt("com_commonform_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                    mBundleDB.putInt("com_commonform_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                    mBundleDB.putInt("com_commonform_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                    mGeneralFormTodoListFragment.setArguments(mBundleDB);
                    return mGeneralFormTodoListFragment;
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
        if (result != null) {
            GetDocListEntity entity = (GetDocListEntity) result;
            Log.d("InitForm", "取得了" + entity.getResult().length + "条待办信息");
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
                if (flag) {
                    this.finish();
                } else {
                    ((ClientTabActivity) getParent()).callUserMeesageMain();
                }

                break;
            case R.id.imageview_daiban_more:
                if (!functionPopMenu.isShowing()) {
                    functionPopMenu.showAsDropDown(arg0);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
//
        if (arg1 == ActivityResultConstant.SAVEOCUS_RESULT_OK && mViewpager != null) {
//            ViewPagerAdapter mFragmentStatePagerAdapter = (ViewPagerAdapter) mViewpager.getAdapter();
//            GeneralFormTodoListFragment mGeneralFormTodoListFragment = (GeneralFormTodoListFragment) mFragmentStatePagerAdapter.instantiateItem(mViewpager,0);
//            GeneralFormTodoListFragment mGeneralFormTodoListFragmentYB = (GeneralFormTodoListFragment)mFragmentStatePagerAdapter.instantiateItem(mViewpager, 1);
//            if(mGeneralFormTodoListFragment != null && mGeneralFormTodoListFragment != null){
//                mGeneralFormTodoListFragment.initData(app_id, HtmitechApplication.getInstance());
//                mGeneralFormTodoListFragmentYB.initData(app_id, HtmitechApplication.getInstance());
//            }

        } else if (arg1 == ActivityResultConstant.DOACTION_RESULT_OK) {
            String kind = arg2.getStringExtra("kind");
            if (mGeneralFormTodoListFragment != null)
                mGeneralFormTodoListFragment.getmHaveDoneListFragment().pullDownRefresh();
            else if (mGeneralFormTodoListFragmentYB != null)
                mGeneralFormTodoListFragmentYB.getmHaveDoneListFragment().pullDownRefresh();
        }
    }

    public void onFragmentManage(){
        mFragmentManager = getSupportFragmentManager();
        mViewpager = (MainViewPager) this.findViewById(R.id.daiban_viewpager_daiban);
        mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mViewpager.setAdapter(mViewPagerAdapter);
        mViewpager.setNoScroll(true);
        mViewpager.setOffscreenPageLimit(4);
        mViewpager.setCurrentItem(current_item);
        mDaiBanTopTabIndicator = (DaiBanTopTabIndicator)
                findViewById(R.id.daiban_topTabIndicator_bbslist);
        mDaiBanTopTabIndicator.setIsStartValue(isOtherFavValue | isFavValue);
        mDaiBanTopTabIndicator.setViewPager(mViewpager);
    }




}
