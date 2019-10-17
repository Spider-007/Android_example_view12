package com.htmitech.htworkflowformpluginnew.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.htmitech.emportal.R;
import com.htmitech.htworkflowformpluginnew.myenum.RequestTypeEnum;

import java.util.ArrayList;

import htmitech.com.componentlibrary.base.MyBaseFragment;

/***
 * 工作流表单中的关注 tab
 * @author joe
 * @date 2017-06-30 12:05:00
 */
public class WorkFlowFormMineListFragment extends MyBaseFragment {


    private com.htmitech.emportal.ui.widget.NewTopTabIndicator tabIndicatorMine;
    private com.htmitech.emportal.ui.widget.MainViewPager viewPagerMine;
    private ImageView mineDivider;
    private MyAdapter mAdapter;
    private String[] titleArray;
    private ArrayList<Fragment> pages;
    private String com_workflow_mobileconfig_include_myfav;//是否支持“我的关注”。 0，不支持；1，支持
    private String com_workflow_mobileconfig_include_mystart;//是否支持“我的发起”功能。 0，不支持；1，支持
    private LinearLayout ll_layout;
    public String modelName;
    private String todoFlag;
    private int actionButtonStyle; //表单详情中的操作按钮样式
    private int customerShortcuts;  //是否支持快捷键自定义
    private int com_workflow_mobileconfig_IM_enabled;//表单详情和历史操作中，是否支持点击人员跳转聊天的功能。
    private int isFavValue;//是否支持我的关注
    private int isStartValue;//是否支持我的发起
    private int isWaterSecurity;//是不是支持水印
    private int isShare;//是否支持表单分享功能。
    private int com_workflow_mobileconfig_opinion_style;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_general_form_mine_list;
    }

    @Override
    protected void initViews() {
        com_workflow_mobileconfig_include_myfav = getActivity().getIntent().getStringExtra("com_workflow_mobileconfig_include_myfav");
        com_workflow_mobileconfig_include_mystart = getActivity().getIntent().getStringExtra("com_workflow_mobileconfig_include_mystart");
        Bundle mBunlde = getArguments();
        todoFlag = mBunlde.getString("com_workflow_plugin_selector_paramter_TodoFlag");
        actionButtonStyle= mBunlde.getInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        com_workflow_mobileconfig_opinion_style = mBunlde.getInt("com_workflow_mobileconfig_opinion_style", 0);
        customerShortcuts= mBunlde.getInt("com_workflow_mobileconfig_customer_shortcuts",customerShortcuts);
        com_workflow_mobileconfig_IM_enabled= mBunlde.getInt("com_workflow_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        isFavValue= mBunlde.getInt("com_workflow_mobileconfig_include_myfav",isFavValue);
        isStartValue= mBunlde.getInt("com_workflow_mobileconfig_include_mystart",isStartValue);
        isWaterSecurity= mBunlde.getInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        isShare= mBunlde.getInt("com_workflow_mobileconfig_include_share", isShare);
        mineDivider = (ImageView) findViewById(R.id.imageview_mine_divider);
        tabIndicatorMine = (com.htmitech.emportal.ui.widget.NewTopTabIndicator) findViewById(R.id.topTabIndicator_mine);
        viewPagerMine = (com.htmitech.emportal.ui.widget.MainViewPager) findViewById(R.id.viewPager_mine);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        getActivity().getIntent().putExtra("isMine",true);
        viewPagerMine.setNoScroll(true);
        if (pages == null) {
            pages = new ArrayList<Fragment>();
        }
        //我的发起
        WorkFlowHaveDoneListFragment myFQHaveDoneListFragment = new WorkFlowHaveDoneListFragment();
        myFQHaveDoneListFragment.setRequestType(RequestTypeEnum.MYFQ);
        Bundle mBundleMyGZ = new Bundle();
        mBundleMyGZ.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
        mBundleMyGZ.putString("com_workflow_plugin_selector_paramter_myfavflag", com_workflow_mobileconfig_include_myfav);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_customer_shortcuts",customerShortcuts);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_opinion_style",com_workflow_mobileconfig_opinion_style);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundleMyGZ.putInt("com_workflow_mobileconfig_include_share", isShare);
        myFQHaveDoneListFragment.setArguments(mBundleMyGZ);

        //我的关注
        WorkFlowHaveDoneListFragment myGZHaveDoneListFragment = new WorkFlowHaveDoneListFragment();
        myGZHaveDoneListFragment.setRequestType(RequestTypeEnum.MYGZ);
        Bundle mBundleMyFQ = new Bundle();
        mBundleMyFQ.putString("com_workflow_plugin_selector_paramter_TodoFlag", "");
        mBundleMyFQ.putString("com_workflow_plugin_selector_paramter_IsMyStart", com_workflow_mobileconfig_include_mystart);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_customer_shortcuts",customerShortcuts);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_opinion_style",com_workflow_mobileconfig_opinion_style);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_include_security", isWaterSecurity);
        mBundleMyFQ.putInt("com_workflow_mobileconfig_include_share", isShare);
        myGZHaveDoneListFragment.setArguments(mBundleMyFQ);

        if(com_workflow_mobileconfig_include_mystart != null && com_workflow_mobileconfig_include_mystart.equals("1")){
            pages.add(myFQHaveDoneListFragment);
            titleArray = new String[]{"我的发起"};
        }
        if(com_workflow_mobileconfig_include_myfav != null && com_workflow_mobileconfig_include_myfav.equals("1")){
            titleArray = new String[]{"我的关注"};
            pages.add(myGZHaveDoneListFragment);
        }

        if(com_workflow_mobileconfig_include_mystart != null && com_workflow_mobileconfig_include_myfav != null &&  com_workflow_mobileconfig_include_myfav.equals("1") && com_workflow_mobileconfig_include_mystart.equals("1")){
            titleArray = new String[]{"我的发起", "我的关注"};
        }
        viewPagerMine.setOffscreenPageLimit(pages.size());
        viewPagerMine.setCurrentItem(0);
        if(pages.size() != 0){
            inittitles();
            mAdapter = new MyAdapter(getChildFragmentManager());

            viewPagerMine.setAdapter(mAdapter);
        }


    }

    public void inittitles() {
        tabIndicatorMine.setCommonData2(viewPagerMine, titleArray, R.color.color_title,
                R.color.color_ff888888);
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return pages.get(i);
        }

        @Override
        public int getCount() {
            return pages.size();
        }


    }


}
