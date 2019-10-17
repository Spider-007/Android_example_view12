package com.htmitech.htworkflowformpluginnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.ui.ActivityResultConstant;
import com.htmitech.emportal.ui.daiban.data.getdoclist.GetDocListEntity;
import com.htmitech.emportal.ui.helppage.HelpActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.htmitech.emportal.ui.widget.DaiBanTopTabIndicator;
import com.htmitech.emportal.ui.widget.MainViewPager;
import com.htmitech.htworkflowformpluginnew.adapter.MyFragmentPagerAdapter;
import com.htmitech.htworkflowformpluginnew.fragment.GYLWorkFlowFormTodoListFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowFormMineListFragment;
import com.htmitech.htworkflowformpluginnew.fragment.WorkFlowFormTodoListFragment;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.pop.ToRightPopMenum;
import com.htmitech.unit.TextUtil;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.tab.MenuTabItem;

import java.util.ArrayList;

import htmitech.com.componentlibrary.unit.PreferenceUtils;


/**
 * 工作流构件入口
 *
 * @author joe
 * @date 2017-06-30 12:07:16
 */
public class InitWorkFlowFormActivity extends MyBaseFragmentActivity implements
        IBaseCallback, IBottomItemSelectCallBack, View.OnClickListener {

    private static final String TAG = "InitWorkFlowFormActivit";
    private DaiBanTopTabIndicator mDaiBanTopTabIndicator;
    public static MainViewPager mViewpager;
    private FragmentManager mFragmentManager;

    private WorkFlowFormTodoListFragment mWorkFlowFormTodoListFragment;
    private WorkFlowFormTodoListFragment mWorkFlowFormTodoListFragmentYB;
    private WorkFlowFormMineListFragment mWorkFlowFormMineListFragment;

    private GYLWorkFlowFormTodoListFragment mWorkFlowFormTodoListFragmentGYL;
    private GYLWorkFlowFormTodoListFragment mWorkFlowFormTodoListFragmentYBGYL;
    private GYLWorkFlowFormTodoListFragment mWorkFlowFormMineListFragmentGYL;


    private static final int FRAGMENT_DAIBAN = 0;
    private static final int FRAGMENT_YIBAN = 1;
    private static final int FRAGMENT_GUANZHU = 2;

    private ToRightPopMenum functionPopMenu;

    private ImageView functionButton;
    private TextView tv_daiban_title;
    private ImageView btn_daiban_person;
    private ImageView btn_daiban_person_home;
    private boolean flag = false; //是否是首页 还是新开页面
    private int current_item;
    private int isStartValue;//是否支持我发起的功能。 0，不支持；1，支持
    private int isFavValue;//是否支持“我的关注”。 0，不支持；1，支持
    private int isWaterSecurity;//是否支持水印
    public int actionButtonStyle = -1;//浮动按钮与底部按钮的
    public String app_id;//appid 新加参数
    public int com_workflow_mobileconfig_IM_enabled;//作流表单详情和流程历史中，是否支持点击人员跳转聊天的功能 0，不支持；1，支持。
    public int todoFlag = -1; //代办已办标记 0代办 1已办
    public int isMyFav = -1;//是否要查询我关注的
    public int isMyStart = -1;//是否要查询我发起的
    public int isShare = -1;//是否isShare分享
    public int isTextUrl;//是否正文展示HTML
    public String app_version_id;//当前应用的版本

    boolean IS_GYL = false;

    public String modelName = "";
    public int customerShortcuts = -1;
    private ViewPagerAdapter mViewPagerAdapter;
    //新增参数
    private int hasdoneShortcuts; //已办面板中是否支持快捷键自定义
    private int hasdoneShortcutsview; //是否需要在已办中显示“筛选面板”
    private int todoShortcuts; //待办面板中是否支持快捷键自定义
    private int todoShortcutsview; //是否需要在待办中显示“筛选面板”

    private int com_workflow_mobileconfig_tabbutton_style;//工作流界面上面的标签按钮，是显示默认的【待办已办】按钮，还是显示【待阅已阅】按钮。1：显示【待办】、【已办】两个按钮(默认)；2：显示【待阅】、【已阅】两个按钮

    private String com_workflow_mobileconfig_importance_workas_toreadflag;


    private String com_workflow_mobileconfig_other_conditions;

    private String com_workflow_mobileconfig_batchoperate;

    private int index = 0;

    private int com_workflow_mobileconfig_include_options;

    private String com_workflow_mobileconfig_others;

    private String com_workflow_mobileconfig_check_mustinput_except;
    private ArrayList<MyBaseFragment> fragmentList;

    private String appName;

    private String typeCode;//是否是定制开发

    public String com_workflow_mobileconfig_confirmdialog;

    public String com_workflow_mobileconfig_downloadType;
    private String com_workflow_mobileconfig_opinion_styleStr;
    private int com_workflow_mobileconfig_opinion_style;

    @Override
    protected int getLayoutById() {
        return R.layout.activity_daiban;
    }

    @Override
    protected void initView() {
        btn_daiban_person = (ImageView) findViewById(R.id.btn_daiban_person);
        btn_daiban_person_home = (ImageView) findViewById(R.id.btn_daiban_person_home);
        btn_daiban_person.setOnClickListener(this);
        btn_daiban_person_home.setOnClickListener(this);
        Log.e(TAG, "创建的次数 " + index++);
        initData();

    }

    public void initData() {
        fragmentList = new ArrayList<MyBaseFragment>();
        //构件参数
        String current_itemStr = getIntent().getStringExtra("current_item");
        appName = getIntent().getStringExtra("appShortName");
        String com_workflow_mobileconfig_actionbutton_style = getIntent().getStringExtra("com_workflow_mobileconfig_actionbutton_style");//操作按钮配置
        String com_workflow_mobileconfig_customer_shortcuts = getIntent().getStringExtra("com_workflow_mobileconfig_customer_shortcuts");//快捷键自定义
        String com_workflow_mobileconfig_docview_style = getIntent().getStringExtra("com_workflow_mobileconfig_docview_style");//正文显示方式
        String com_workflow_mobileconfig_IM_enabledStr = getIntent().getStringExtra("com_workflow_mobileconfig_IM_enabled");//跳转敏行聊天
        String com_workflow_mobileconfig_include_myfav = getIntent().getStringExtra("com_workflow_mobileconfig_include_myfav");//我的关注
        String com_workflow_mobileconfig_include_mystart = getIntent().getStringExtra("com_workflow_mobileconfig_include_mystart");//我的发起
        String com_workflow_mobileconfig_include_security = getIntent().getStringExtra("com_workflow_mobileconfig_include_security");//水印
        String com_workflow_mobileconfig_include_share = getIntent().getStringExtra("com_workflow_mobileconfig_include_share");//分享
        //插件参数
        String com_workflow_plugin_selector_paramter_IsMyFav = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyFav");//是否要查询我关注的
        String com_workflow_plugin_selector_paramter_IsMyStart = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_IsMyStart");//是否要查询我发起的
        String com_workflow_plugin_selector_paramter_ModelName = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_ModelName");//模块名称
        String com_workflow_plugin_selector_paramter_Title = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_Title");//检索标题
        String com_workflow_plugin_selector_paramter_TodoFlag = getIntent().getStringExtra("com_workflow_plugin_selector_paramter_TodoFlag");//待办已办标记
        String com_workflow_mobileconfig_include_optionsstr = getIntent().getStringExtra("com_workflow_mobileconfig_include_options");//意见审批是否需要显示常用意见
        //新增快捷键
        String com_workflow_mobileconfig_customer_hasdone_shortcuts = getIntent().getStringExtra("com_workflow_mobileconfig_customer_hasdone_shortcuts");//已办面板中是否支持快捷键自定义
        String com_workflow_mobileconfig_customer_hasdone_shortcutsview = getIntent().getStringExtra("com_workflow_mobileconfig_customer_hasdone_shortcutsview");//是否需要在已办中显示“筛选面板”
        String com_workflow_mobileconfig_customer_todo_shortcuts = getIntent().getStringExtra("com_workflow_mobileconfig_customer_todo_shortcuts"); //待办面板中是否支持快捷键自定义
        String com_workflow_mobileconfig_customer_todo_shortcutsview = getIntent().getStringExtra("com_workflow_mobileconfig_customer_todo_shortcutsview");//是否需要在待办中显示“筛选面板”
        String com_workflow_mobileconfig_tabbutton_stylestr = getIntent().getStringExtra("com_workflow_mobileconfig_tabbutton_style");//工作流界面上面的标签按钮，是显示默认的【待办已办】按钮，还是显示【待阅已阅】按钮。1：显示【待办】、【已办】两个按钮(默认)；2：显示【待阅】、【已阅】两个按钮
        com_workflow_mobileconfig_check_mustinput_except = getIntent().getStringExtra("com_workflow_mobileconfig_check_mustinput_except");//按钮是否校验必填项，根据配置项进行控制

        Constant.com_workflow_mobileconfig_check_mustinput_except = TextUtils.isEmpty(com_workflow_mobileconfig_check_mustinput_except) ? "" : com_workflow_mobileconfig_check_mustinput_except;
        String com_workflow_mobileconfig_todoflag = getIntent().getStringExtra("com_workflow_mobileconfig_todoflag");
        String com_workflow_mobileconfig_importance_workas_toreadflagstr = getIntent().getStringExtra("com_workflow_mobileconfig_importance_workas_toreadflag");
        String com_workflow_mobileconfig_flowhistory_display_order = getIntent().getStringExtra("com_workflow_mobileconfig_flowhistory_display_order");
        com_workflow_mobileconfig_others = getIntent().getStringExtra("com_workflow_mobileconfig_others");
        com_workflow_mobileconfig_batchoperate = getIntent().getStringExtra("com_workflow_mobileconfig_batchoperate");//是否展示为批量审批列表  0，普通待办列表；1，批量审批列表。
        com_workflow_mobileconfig_other_conditions = getIntent().getStringExtra("com_workflow_mobileconfig_other_conditions");//
        com_workflow_mobileconfig_confirmdialog = getIntent().getStringExtra("com_workflow_mobileconfig_confirmdialog");//
        com_workflow_mobileconfig_downloadType = getIntent().getStringExtra("com_workflow_mobileconfig_docDownloadType");
        com_workflow_mobileconfig_opinion_styleStr = getIntent().getStringExtra("com_workflow_mobileconfig_opinion_style");
        if(TextUtils.isEmpty(com_workflow_mobileconfig_confirmdialog)){
            com_workflow_mobileconfig_confirmdialog = "0";
        }
        if (TextUtils.isEmpty(com_workflow_mobileconfig_other_conditions)) {

            com_workflow_mobileconfig_other_conditions = "";
        }
        typeCode = getIntent().getStringExtra("appCode");
        if (TextUtils.isEmpty(com_workflow_mobileconfig_importance_workas_toreadflagstr)) {
            com_workflow_mobileconfig_importance_workas_toreadflag = "";
        }
        if (!TextUtils.isEmpty(com_workflow_mobileconfig_todoflag)) {
            com_workflow_mobileconfig_importance_workas_toreadflag = "";
        }

        if(TextUtils.isEmpty(com_workflow_mobileconfig_batchoperate)){
            com_workflow_mobileconfig_batchoperate = "0";
        }

        IS_GYL = com_workflow_mobileconfig_batchoperate.equals("1") ? true:false;

        IS_GYL = todoFlag == 1 ? false : IS_GYL;
        //类型转换
        com_workflow_mobileconfig_IM_enabledStr = com_workflow_mobileconfig_IM_enabledStr == null ? "0" : com_workflow_mobileconfig_IM_enabledStr;
        current_itemStr = current_itemStr == null ? "0" : current_itemStr;
        com_workflow_mobileconfig_flowhistory_display_order = TextUtils.isEmpty(com_workflow_mobileconfig_flowhistory_display_order) ? "0" : com_workflow_mobileconfig_flowhistory_display_order;
        com_workflow_mobileconfig_include_myfav = com_workflow_mobileconfig_include_myfav == null ? "0" : com_workflow_mobileconfig_include_myfav;
        com_workflow_mobileconfig_include_security = com_workflow_mobileconfig_include_security == null ? "0" : com_workflow_mobileconfig_include_security;
        com_workflow_mobileconfig_include_share = com_workflow_mobileconfig_include_share == null ? "0" : com_workflow_mobileconfig_include_share;
        com_workflow_mobileconfig_include_mystart = com_workflow_mobileconfig_include_mystart == null ? "0" : com_workflow_mobileconfig_include_mystart;
        com_workflow_mobileconfig_actionbutton_style = com_workflow_mobileconfig_actionbutton_style == null ? "0" : com_workflow_mobileconfig_actionbutton_style;
        com_workflow_mobileconfig_customer_shortcuts = com_workflow_mobileconfig_customer_shortcuts == null ? "0" : com_workflow_mobileconfig_customer_shortcuts;
        com_workflow_plugin_selector_paramter_TodoFlag = com_workflow_plugin_selector_paramter_TodoFlag == null ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag;
        com_workflow_plugin_selector_paramter_IsMyFav = com_workflow_plugin_selector_paramter_IsMyFav == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav;
        com_workflow_plugin_selector_paramter_IsMyStart = com_workflow_plugin_selector_paramter_IsMyStart == null ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart;
        com_workflow_mobileconfig_customer_hasdone_shortcuts = com_workflow_mobileconfig_customer_hasdone_shortcuts == null ? "1" : com_workflow_mobileconfig_customer_hasdone_shortcuts;
        com_workflow_mobileconfig_customer_hasdone_shortcutsview = com_workflow_mobileconfig_customer_hasdone_shortcutsview == null ? "1" : com_workflow_mobileconfig_customer_hasdone_shortcutsview;
        com_workflow_mobileconfig_customer_todo_shortcuts = com_workflow_mobileconfig_customer_todo_shortcuts == null ? "1" : com_workflow_mobileconfig_customer_todo_shortcuts;
        com_workflow_mobileconfig_customer_todo_shortcutsview = com_workflow_mobileconfig_customer_todo_shortcutsview == null ? "1" : com_workflow_mobileconfig_customer_todo_shortcutsview;
        com_workflow_mobileconfig_include_optionsstr = com_workflow_mobileconfig_include_optionsstr == null ? "0" : com_workflow_mobileconfig_include_optionsstr;
        com_workflow_mobileconfig_tabbutton_stylestr = com_workflow_mobileconfig_tabbutton_stylestr == null ? "1" : com_workflow_mobileconfig_tabbutton_stylestr;
        current_item = Integer.parseInt(current_itemStr.equals("") ? "0" : current_itemStr);
        isStartValue = Integer.parseInt(com_workflow_mobileconfig_include_mystart.equals("") ? "0" : com_workflow_mobileconfig_include_mystart);
        isFavValue = Integer.parseInt(com_workflow_mobileconfig_include_myfav.equals("") ? "0" : com_workflow_mobileconfig_include_myfav);
        isWaterSecurity = Integer.parseInt(com_workflow_mobileconfig_include_security.equals("") ? "0" : com_workflow_mobileconfig_include_security);
        isShare = Integer.parseInt(com_workflow_mobileconfig_include_share.equals("") ? "0" : com_workflow_mobileconfig_include_share);
        actionButtonStyle = Integer.parseInt(com_workflow_mobileconfig_actionbutton_style.equals("") ? "0" : com_workflow_mobileconfig_actionbutton_style);
        customerShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_shortcuts.equals("") ? "0" : com_workflow_mobileconfig_customer_shortcuts);
        com_workflow_mobileconfig_IM_enabled = Integer.parseInt(com_workflow_mobileconfig_IM_enabledStr.equals("") ? "0" : com_workflow_mobileconfig_IM_enabledStr);
        todoFlag = Integer.parseInt(com_workflow_plugin_selector_paramter_TodoFlag.equals("") ? "-1" : com_workflow_plugin_selector_paramter_TodoFlag);
        isMyFav = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyFav.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyFav);
        isMyStart = Integer.parseInt(com_workflow_plugin_selector_paramter_IsMyStart.equals("") ? "-1" : com_workflow_plugin_selector_paramter_IsMyStart);
        hasdoneShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_hasdone_shortcuts.equals("") ? "1" : com_workflow_mobileconfig_customer_hasdone_shortcuts); //已办面板中是否支持快捷键自定义
        hasdoneShortcutsview = Integer.parseInt(com_workflow_mobileconfig_customer_hasdone_shortcutsview.equals("") ? "1" : com_workflow_mobileconfig_customer_hasdone_shortcutsview); //是否需要在已办中显示“筛选面板”
        todoShortcuts = Integer.parseInt(com_workflow_mobileconfig_customer_todo_shortcuts.equals("") ? "1" : com_workflow_mobileconfig_customer_todo_shortcuts); //待办面板中是否支持快捷键自定义
        todoShortcutsview = Integer.parseInt(com_workflow_mobileconfig_customer_todo_shortcutsview.equals("") ? "1" : com_workflow_mobileconfig_customer_todo_shortcutsview); //是否需要在待办中显示“筛选面板”
        com_workflow_mobileconfig_include_options = Integer.parseInt(com_workflow_mobileconfig_include_optionsstr.equals("") ? "0" : com_workflow_mobileconfig_include_optionsstr);
        com_workflow_mobileconfig_tabbutton_style = Integer.parseInt(com_workflow_mobileconfig_tabbutton_stylestr.equals("") ? "1" : com_workflow_mobileconfig_tabbutton_stylestr);
        com_workflow_mobileconfig_opinion_style = Integer.parseInt(TextUtils.isEmpty(com_workflow_mobileconfig_opinion_styleStr) ? "0" : com_workflow_mobileconfig_opinion_styleStr);
        Constant.com_workflow_mobileconfig_flowhistory_display_order = com_workflow_mobileconfig_flowhistory_display_order;
        if (com_workflow_mobileconfig_docview_style != null)
            isTextUrl = Integer.parseInt(com_workflow_mobileconfig_docview_style.equals("") ? "0" : com_workflow_mobileconfig_docview_style);
        flag = getIntent().getBooleanExtra("Type", false);
        app_id = getIntent().getStringExtra("app_id");
        app_version_id = getIntent().getStringExtra("app_version_id");
        modelName = com_workflow_plugin_selector_paramter_ModelName;
        modelName = modelName == null ? "" : modelName;
        BookInit.getInstance().setIsWaterSecurity(isWaterSecurity);
        if (!flag) {
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
        Log.d(TAG, strOAUserID);


//        //启动帮助页面
//        if (PreferenceUtils.isLoginDaiYiBan(this)) {
//            PreferenceUtils.setLoginDaiYiBan(this, false);
//            Intent i = new Intent(this, HelpActivity.class);
//            i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
//            startActivity(i);
//        }
        Constant.addGeneralChannel = !flag ? "notHome" : "home";
        onFragmentManager();

        if ((isStartValue | isFavValue) == 0 && (com_workflow_mobileconfig_tabbutton_style == 3 || com_workflow_mobileconfig_tabbutton_style == 4)) {
            tv_daiban_title.setVisibility(View.VISIBLE);
            tv_daiban_title.setText(appName);
            mDaiBanTopTabIndicator.setVisibility(View.GONE);
            int currentItem = 0;
            if (com_workflow_mobileconfig_tabbutton_style == 3) {
                currentItem = 0;
            } else {
                currentItem = 1;
            }
            mViewpager.setCurrentItem(currentItem);
        } else {
            tv_daiban_title.setVisibility(View.GONE);
            mDaiBanTopTabIndicator.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        this.finish();
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean flag = false;
        try{
            if(ClientTabActivity.mTabHost != null && ClientTabActivity.mTabHost.getMenuTabItem() != null){
                //启动帮助页面
                for (MenuTabItem mMenuTabItem : ClientTabActivity.mTabHost.getMenuTabItem()) {
                    if (mMenuTabItem.getButtomEnum() == ApplicationAllEnum.DB) {
                        flag = true;
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        if (!flag) {
            if (PreferenceUtils.isLoginDaiYiBan(this)) {
                PreferenceUtils.setLoginDaiYiBan(this, false);
                Intent i = new Intent(this, HelpActivity.class);
                i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
                startActivity(i);
            }
        } else {
            //启动帮助页面
            if (ClientTabActivity.mTabHost.getMenuTabItemByIndex(ClientTabActivity.mTabHost.getCurrentTab()).getButtomEnum() == ApplicationAllEnum.DB) {
                if (PreferenceUtils.isLoginDaiYiBan(this)) {
                    PreferenceUtils.setLoginDaiYiBan(this, false);
                    Intent i = new Intent(this, HelpActivity.class);
                    i.putExtra(HelpActivity.CURRENT_HELPPAGE, HelpActivity.DAIYIBAN_HELPPAGE);
                    startActivity(i);
                }
            }
        }

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (3 == com_workflow_mobileconfig_tabbutton_style || 5 == com_workflow_mobileconfig_tabbutton_style) {
                switch (position) {
                    case FRAGMENT_GUANZHU:
                        if (mWorkFlowFormMineListFragment == null)
                            mWorkFlowFormMineListFragment = new WorkFlowFormMineListFragment();
                        Bundle mBundleGZ = new Bundle();
                        mBundleGZ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleGZ.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mWorkFlowFormMineListFragment.setArguments(mBundleGZ);
                        return mWorkFlowFormMineListFragment;
                    case FRAGMENT_DAIBAN:
                    default:
                        if (mWorkFlowFormTodoListFragment == null)
                            mWorkFlowFormTodoListFragment = new WorkFlowFormTodoListFragment();
                        Bundle mBundleDB = new Bundle();
                        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 3) {
                            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "0");
                            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
                        } else {
                            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
                            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "0");
                        }

                        mBundleDB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleDB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mBundleDB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                        mBundleDB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleDB.putInt("com_workflow_mobileconfig_opinion_style", com_workflow_mobileconfig_opinion_style);
                        mWorkFlowFormTodoListFragment.setArguments(mBundleDB);
                        return mWorkFlowFormTodoListFragment;
                }
            } else if (4 == com_workflow_mobileconfig_tabbutton_style || 6 == com_workflow_mobileconfig_tabbutton_style) {
                switch (position) {
                    case FRAGMENT_GUANZHU:
                        if (mWorkFlowFormMineListFragment == null)
                            mWorkFlowFormMineListFragment = new WorkFlowFormMineListFragment();
                        Bundle mBundleGZ = new Bundle();
                        mBundleGZ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleGZ.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mWorkFlowFormMineListFragment.setArguments(mBundleGZ);
                        return mWorkFlowFormMineListFragment;
                    case FRAGMENT_DAIBAN:
                    default:
                        if (mWorkFlowFormTodoListFragmentYB == null)
                            mWorkFlowFormTodoListFragmentYB = new WorkFlowFormTodoListFragment();
                        Bundle mBundleYB = new Bundle();
                        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 4) {
                            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "1");
                            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
                        } else {
                            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
                            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "1");
                        }

                        mBundleYB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleYB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
                        mBundleYB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleYB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mWorkFlowFormTodoListFragmentYB.setArguments(mBundleYB);
                        return mWorkFlowFormTodoListFragmentYB;
                }
            } else {
                switch (position) {
                    case FRAGMENT_YIBAN:
                        if (mWorkFlowFormTodoListFragmentYB == null)
                            mWorkFlowFormTodoListFragmentYB = new WorkFlowFormTodoListFragment();
                        Bundle mBundleYB = new Bundle();
                        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 4) {
                            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "1");
                            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
                        } else {
                            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
                            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "1");
                        }

                        mBundleYB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleYB.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleYB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
                        mBundleYB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleYB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mWorkFlowFormTodoListFragmentYB.setArguments(mBundleYB);
                        return mWorkFlowFormTodoListFragmentYB;
                    case FRAGMENT_GUANZHU:
                        if (mWorkFlowFormMineListFragment == null)
                            mWorkFlowFormMineListFragment = new WorkFlowFormMineListFragment();
                        Bundle mBundleGZ = new Bundle();
                        mBundleGZ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleGZ.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleGZ.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleGZ.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mWorkFlowFormMineListFragment.setArguments(mBundleGZ);
                        return mWorkFlowFormMineListFragment;
                    case FRAGMENT_DAIBAN:
                    default:
                        if (mWorkFlowFormTodoListFragment == null)
                            mWorkFlowFormTodoListFragment = new WorkFlowFormTodoListFragment();
                        Bundle mBundleDB = new Bundle();
                        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 3) {
                            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "0");
                            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
                        } else {
                            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
                            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "0");
                        }

                        mBundleDB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_share", isShare);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
                        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
                        mBundleDB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
                        mBundleDB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
                        mBundleDB.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
                        mBundleDB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
                        mBundleDB.putInt("com_workflow_mobileconfig_opinion_style", com_workflow_mobileconfig_opinion_style);
                        mWorkFlowFormTodoListFragment.setArguments(mBundleDB);
                        return mWorkFlowFormTodoListFragment;
                }
            }

        }

        @Override
        public int getCount() {
            if(3 == com_workflow_mobileconfig_tabbutton_style || 5 == com_workflow_mobileconfig_tabbutton_style || 4 == com_workflow_mobileconfig_tabbutton_style || 6 == com_workflow_mobileconfig_tabbutton_style){
                return 2;
            }
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
        if (arg1 == ActivityResultConstant.SAVEOCUS_RESULT_OK && mViewpager != null) {
//            ViewPagerAdapter mFragmentStatePagerAdapter = (ViewPagerAdapter) mViewpager.getAdapter();
//            WorkFlowFormTodoListFragment mGeneralFormTodoListFragment = (WorkFlowFormTodoListFragment) mFragmentStatePagerAdapter.instantiateItem(mViewpager,0);
//            WorkFlowFormTodoListFragment mGeneralFormTodoListFragmentYB = (WorkFlowFormTodoListFragment)mFragmentStatePagerAdapter.instantiateItem(mViewpager, 1);
            if(mWorkFlowFormTodoListFragmentYB != null){
                mWorkFlowFormTodoListFragmentYB.checkIndex = 0;
                mWorkFlowFormTodoListFragmentYB.initData(app_id, HtmitechApplication.getInstance());
            }
            if(mWorkFlowFormTodoListFragment != null){
                mWorkFlowFormTodoListFragment.checkIndex = 0;
                mWorkFlowFormTodoListFragment.initData(app_id, HtmitechApplication.getInstance());
            }
            if(mWorkFlowFormTodoListFragmentGYL != null){
                mWorkFlowFormTodoListFragmentGYL.checkIndex = 0;
                mWorkFlowFormTodoListFragmentGYL.initData(app_id,HtmitechApplication.getInstance());
            }
            if(mWorkFlowFormTodoListFragmentYBGYL != null){
                mWorkFlowFormTodoListFragmentYBGYL.checkIndex = 0;
                mWorkFlowFormTodoListFragmentYBGYL.initData(app_id,HtmitechApplication.getInstance());
            }

        } else if (arg1 == ActivityResultConstant.DOACTION_RESULT_OK) {
            String kind = arg2.getStringExtra("kind");
            if (mWorkFlowFormTodoListFragment != null)
                mWorkFlowFormTodoListFragment.getmHaveDoneListFragment().pullDownRefresh();
            else if (mWorkFlowFormTodoListFragmentYB != null)
                mWorkFlowFormTodoListFragmentYB.getmHaveDoneListFragment().pullDownRefresh();
        }

    }

    public void onFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
        mViewpager = (MainViewPager) this.findViewById(R.id.daiban_viewpager_daiban);
        addFragment();
//        mViewPagerAdapter = new ViewPagerAdapter();
        mViewpager.setAdapter(new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragmentList));
        mViewpager.setNoScroll(true);
        mViewpager.setOffscreenPageLimit(fragmentList.size());
        mViewpager.setCurrentItem(current_item);
        mDaiBanTopTabIndicator = (DaiBanTopTabIndicator)
                findViewById(R.id.daiban_topTabIndicator_bbslist);
        mDaiBanTopTabIndicator.setCom_workflow_mobileconfig_tabbutton_style(com_workflow_mobileconfig_tabbutton_style, true);
        mDaiBanTopTabIndicator.setIsStartValue(isStartValue | isFavValue);
        mDaiBanTopTabIndicator.setViewPager(mViewpager);
    }


    public void addFragment(){
        if (mWorkFlowFormMineListFragment == null){
            mWorkFlowFormMineListFragment = new WorkFlowFormMineListFragment();
        }
        Bundle mBundleGZ = new Bundle();
        mBundleGZ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleGZ.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
        mBundleGZ.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        mBundleGZ.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        mBundleGZ.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        mBundleGZ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundleGZ.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
        mBundleGZ.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
        mBundleGZ.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
        mBundleGZ.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
        mBundleGZ.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        mBundleGZ.putString("appShortName", appName);
        mBundleGZ.putBoolean("IS_GYL", IS_GYL);
        mWorkFlowFormMineListFragment.setArguments(mBundleGZ);


        if(IS_GYL){
            if (mWorkFlowFormTodoListFragmentYBGYL == null){
                mWorkFlowFormTodoListFragmentYBGYL = new GYLWorkFlowFormTodoListFragment();
            }
        }else{
            if (mWorkFlowFormTodoListFragmentYB == null){
                mWorkFlowFormTodoListFragmentYB = new WorkFlowFormTodoListFragment();
            }
        }

        Bundle mBundleYB = new Bundle();
        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 4) {
            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "1");
            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
        } else {
            mBundleYB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
            mBundleYB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "1");
        }

        mBundleYB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleYB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
        mBundleYB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        mBundleYB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        mBundleYB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        mBundleYB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundleYB.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
        mBundleYB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
        mBundleYB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
        mBundleYB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
        mBundleYB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
        mBundleYB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        mBundleYB.putInt("STATE_FRAGMENT_SHOW", 1);
        mBundleYB.putString("com_workflow_mobileconfig_other_conditions", com_workflow_mobileconfig_other_conditions);
        mBundleYB.putString("typeCode", typeCode);
        mBundleYB.putString("appShortName", appName);
        mBundleYB.putBoolean("IS_GYL", false);
        if(IS_GYL){
            mWorkFlowFormTodoListFragmentYBGYL.setArguments(mBundleYB);
        }else{
            mWorkFlowFormTodoListFragmentYB.setArguments(mBundleYB);
        }




        if(IS_GYL){
            if (mWorkFlowFormTodoListFragmentGYL == null){
                mWorkFlowFormTodoListFragmentGYL = new GYLWorkFlowFormTodoListFragment();
            }
        }else{
            if (mWorkFlowFormTodoListFragment == null){
                mWorkFlowFormTodoListFragment = new WorkFlowFormTodoListFragment();
            }
        }

        Bundle mBundleDB = new Bundle();
        if (com_workflow_mobileconfig_tabbutton_style == 1 || com_workflow_mobileconfig_tabbutton_style == 3) {
            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "0");
            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "");
        } else {
            mBundleDB.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
            mBundleDB.putString("com_workflow_mobileconfig_importance_workas_toreadflag", "0");
        }

        mBundleDB.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleDB.putInt("com_workflow_mobileconfig_customer_shortcuts", customerShortcuts);
        mBundleDB.putInt("com_workflow_mobileconfig_IM_enabled", com_workflow_mobileconfig_IM_enabled);
        mBundleDB.putInt("com_workflow_mobileconfig_include_myfav", isFavValue);
        mBundleDB.putInt("com_workflow_mobileconfig_include_mystart", isStartValue);
        mBundleDB.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundleDB.putInt("com_workflow_mobileconfig_include_share", isShare);
        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcuts", hasdoneShortcuts);
        mBundleDB.putInt("com_workflow_mobileconfig_customer_hasdone_shortcutsview", hasdoneShortcutsview);
        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcuts", todoShortcuts);
        mBundleDB.putInt("com_workflow_mobileconfig_customer_todo_shortcutsview", todoShortcutsview);
        mBundleDB.putInt("com_workflow_mobileconfig_docview_style", isTextUrl);
        mBundleDB.putString("com_workflow_mobileconfig_others", com_workflow_mobileconfig_others);
        mBundleDB.putInt("com_workflow_mobileconfig_include_options", com_workflow_mobileconfig_include_options);
        mBundleDB.putInt("com_workflow_mobileconfig_opinion_style", com_workflow_mobileconfig_opinion_style);
        mBundleDB.putInt("com_workflow_mobileconfig_tabbutton_style", com_workflow_mobileconfig_tabbutton_style);
        mBundleDB.putInt("STATE_FRAGMENT_SHOW", 2);
        mBundleDB.putString("com_workflow_mobileconfig_other_conditions", com_workflow_mobileconfig_other_conditions);
        mBundleDB.putString("typeCode", typeCode);
        mBundleDB.putString("appShortName", appName);
        mBundleDB.putBoolean("IS_GYL", IS_GYL);
        if(IS_GYL){
            mWorkFlowFormTodoListFragmentGYL.setArguments(mBundleDB);
        }else{
            mWorkFlowFormTodoListFragment.setArguments(mBundleDB);
        }


        if(3 == com_workflow_mobileconfig_tabbutton_style || 5 == com_workflow_mobileconfig_tabbutton_style){
            if(IS_GYL){
                fragmentList.add(mWorkFlowFormTodoListFragmentGYL);
                if((isStartValue | isFavValue) == 1) {
                    fragmentList.add(mWorkFlowFormMineListFragment);
                }
            }else{
                fragmentList.add(mWorkFlowFormTodoListFragment);
                if((isStartValue | isFavValue) == 1) {
                    fragmentList.add(mWorkFlowFormMineListFragment);
                }
            }

        }else if(4 == com_workflow_mobileconfig_tabbutton_style || 6 == com_workflow_mobileconfig_tabbutton_style){
            if(IS_GYL){
                fragmentList.add(mWorkFlowFormTodoListFragmentYBGYL);
                if((isStartValue | isFavValue) == 1){
                    fragmentList.add(mWorkFlowFormMineListFragment);
                }
            }else{
                fragmentList.add(mWorkFlowFormTodoListFragmentYB);
                if((isStartValue | isFavValue) == 1){
                    fragmentList.add(mWorkFlowFormMineListFragment);
                }
            }

        }else{
            if(IS_GYL){
                fragmentList.add(mWorkFlowFormTodoListFragmentGYL);
                fragmentList.add(mWorkFlowFormTodoListFragmentYBGYL);
                fragmentList.add(mWorkFlowFormMineListFragment);
            }else{
                fragmentList.add(mWorkFlowFormTodoListFragment);
                fragmentList.add(mWorkFlowFormTodoListFragmentYB);
                fragmentList.add(mWorkFlowFormMineListFragment);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com.htmitech.htworkflowformpluginnew.constant.ActivityResultConstant.otherses.clear();
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean dispatchTouchEvent(MotionEvent ev);
    }

}
