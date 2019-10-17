package com.htmitech.htcommonformplugin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import com.htmitech.emportal.ui.daiban.MineFaQiFragment;
import com.htmitech.emportal.ui.daiban.MinePrevenanceFragment;

import java.util.ArrayList;
/***
 * 通用表单中的关注和分享
 */
public class GeneralFormMineListFragment extends MyBaseFragment {


    private com.htmitech.emportal.ui.widget.NewTopTabIndicator tabIndicatorMine;
    private com.htmitech.emportal.ui.widget.MainViewPager viewPagerMine;
    private ImageView mineDivider;
    private MyAdapter mAdapter;
    private String[] titleArray;
    private ArrayList<Fragment> pages;
    private String com_commonform_mobileconfig_include_myfav;//是否支持“我的关注”。 0，不支持；1，支持
    private String com_commonform_mobileconfig_include_otherfav;//是否支持他人关注的功能。 0，不支持；1，支持
    private LinearLayout ll_layout;
    public String modelName;
    private String todoFlag;
    private int actionButtonStyle; //表单详情中的操作按钮样式
    private int customerShortcuts;  //是否支持快捷键自定义
    private int com_workflow_mobileconfig_IM_enabled;//表单详情和历史操作中，是否支持点击人员跳转聊天的功能。
    private int isFavValue;//是否支持我的关注
    private int isOtherFavValue;//是否支持他人关注
    private int isWaterSecurity;//是不是支持水印
    private int isShare;//是否支持表单分享功能。
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_general_form_mine_list;
    }

    @Override
    protected void initViews() {
        com_commonform_mobileconfig_include_myfav = getActivity().getIntent().getStringExtra("com_commonform_mobileconfig_include_myfav");
        com_commonform_mobileconfig_include_otherfav = getActivity().getIntent().getStringExtra("com_commonform_mobileconfig_include_otherfav");
        Bundle mBunlde = getArguments();
        todoFlag = mBunlde.getString("com_commonform_plugin_selector_paramter_todoflag");
        actionButtonStyle= mBunlde.getInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
        customerShortcuts= mBunlde.getInt("com_commonform_mobileconfig_customer_shortcuts",customerShortcuts);
        com_workflow_mobileconfig_IM_enabled= mBunlde.getInt("com_commonform_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        isFavValue= mBunlde.getInt("com_commonform_mobileconfig_include_myfav",isFavValue);
        isOtherFavValue= mBunlde.getInt("com_commonform_mobileconfig_include_otherfav",isOtherFavValue);
        isWaterSecurity= mBunlde.getInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
        isShare= mBunlde.getInt("com_commonform_mobileconfig_include_share", isShare);
//        getActivity().getIntent().putExtra("com_commonform_plugin_selector_paramter_myfavflag",com_commonform_mobileconfig_include_myfav);
//        getActivity().getIntent().putExtra("com_commonform_plugin_selector_paramter_otherfavflag",com_commonform_mobileconfig_include_otherfav);
        mineDivider = (ImageView) findViewById(R.id.imageview_mine_divider);
        tabIndicatorMine = (com.htmitech.emportal.ui.widget.NewTopTabIndicator) findViewById(R.id.topTabIndicator_mine);
        viewPagerMine = (com.htmitech.emportal.ui.widget.MainViewPager) findViewById(R.id.viewPager_mine);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        getActivity().getIntent().putExtra("isMine",true);
        viewPagerMine.setNoScroll(true);
        if (pages == null) {
            pages = new ArrayList<Fragment>();
        }

        HaveDoneListFragment myGZHaveDoneListFragment = new HaveDoneListFragment();
        myGZHaveDoneListFragment.setIsMine(true);
        Bundle mBundleMy = new Bundle();
        mBundleMy.putString("com_commonform_plugin_selector_paramter_todoflag", "");
        mBundleMy.putString("com_commonform_plugin_selector_paramter_myfavflag", com_commonform_mobileconfig_include_myfav);
        mBundleMy.putString("com_commonform_plugin_selector_paramter_otherfavflag", "");

        mBundleMy.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleMy.putInt("com_commonform_mobileconfig_customer_shortcuts",customerShortcuts);
        mBundleMy.putInt("com_commonform_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        mBundleMy.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
        mBundleMy.putInt("com_commonform_mobileconfig_include_share", isShare);

        myGZHaveDoneListFragment.setArguments(mBundleMy);
        HaveDoneListFragment otherGZHaveDoneListFragment = new HaveDoneListFragment();


        Bundle mBundleOther = new Bundle();
        mBundleOther.putString("com_commonform_plugin_selector_paramter_todoflag", "");
        mBundleOther.putString("com_commonform_plugin_selector_paramter_myfavflag", "");
        mBundleOther.putString("com_commonform_plugin_selector_paramter_otherfavflag", com_commonform_mobileconfig_include_otherfav);

        mBundleOther.putInt("com_commonform_mobileconfig_actionbutton_style", actionButtonStyle);
        mBundleOther.putInt("com_commonform_mobileconfig_customer_shortcuts",customerShortcuts);
        mBundleOther.putInt("com_commonform_mobileconfig_IM_enabled",com_workflow_mobileconfig_IM_enabled);
        mBundleOther.putInt("com_commonform_mobileconfig_include_security", isWaterSecurity);
        mBundleOther.putInt("com_commonform_mobileconfig_include_share", isShare);

        otherGZHaveDoneListFragment.setIsMine(true);
        otherGZHaveDoneListFragment.setArguments(mBundleOther);
        if(com_commonform_mobileconfig_include_myfav != null && com_commonform_mobileconfig_include_myfav.equals("1")){
            titleArray = new String[]{"我的关注"};
            pages.add(myGZHaveDoneListFragment);
        }
        if(com_commonform_mobileconfig_include_otherfav != null && com_commonform_mobileconfig_include_otherfav.equals("1")){
            pages.add(otherGZHaveDoneListFragment);
            titleArray = new String[]{"他人关注"};
        }

        if(com_commonform_mobileconfig_include_otherfav != null && com_commonform_mobileconfig_include_otherfav != null &&  com_commonform_mobileconfig_include_myfav.equals("1") && com_commonform_mobileconfig_include_otherfav.equals("1")){
            titleArray = new String[]{"我的关注", "他人关注"};
        }
        viewPagerMine.setOffscreenPageLimit(pages.size());
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
