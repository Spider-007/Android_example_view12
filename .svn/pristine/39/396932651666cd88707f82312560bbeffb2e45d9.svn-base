package com.htmitech.emportal.ui.daiban;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.htmitech.emportal.R;
import htmitech.com.componentlibrary.base.MyBaseFragment;

import java.util.ArrayList;

public class MineListFragment extends MyBaseFragment {


    private com.htmitech.emportal.ui.widget.NewTopTabIndicator tabIndicatorMine;
    private com.htmitech.emportal.ui.widget.MainViewPager viewPagerMine;
    private ImageView mineDivider;
    private MyAdapter mAdapter;
    private String[] titleArray;
    private ArrayList<Fragment> pages;
    private int isFavValue;
    private int isStartValue;
    private int todoFlag;
    public String modelName;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_list_fragment;
    }

    @Override
    protected void initViews() {
        mineDivider = (ImageView) findViewById(R.id.imageview_mine_divider);
        tabIndicatorMine = (com.htmitech.emportal.ui.widget.NewTopTabIndicator) findViewById(R.id.topTabIndicator_mine);
        viewPagerMine = (com.htmitech.emportal.ui.widget.MainViewPager) findViewById(R.id.viewPager_mine);
        viewPagerMine.setOffscreenPageLimit(2);
        viewPagerMine.setNoScroll(true);
        if (pages == null) {
            pages = new ArrayList<Fragment>();
        }

        MineFaQiFragment mMineFaQiFragment = new MineFaQiFragment();
        MinePrevenanceFragment mMinePrevenanceFragment = new MinePrevenanceFragment();

        if (getArguments() != null) {
            isFavValue = getArguments().getInt("isFavValue");    //是否支持我关注的
            isStartValue = getArguments().getInt("isStartValue");//是否支持我发起的
            todoFlag = getArguments().getInt("todoFlag");
            modelName = getArguments().getString("modelName");
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt("todoFlag", todoFlag);
        mBundle.putString("modelName", modelName);
        mMineFaQiFragment.setArguments(mBundle);
        mMinePrevenanceFragment.setArguments(mBundle);
        if (isStartValue == 1 && isFavValue != 1) {
            pages.add(mMineFaQiFragment);
            titleArray = new String[]{"我发起的"};
        } else if (isStartValue != 1 && isFavValue == 1) {
            pages.add(mMinePrevenanceFragment);
            titleArray = new String[]{"我关注的"};
        } else {
            pages.add(mMineFaQiFragment);
            pages.add(mMinePrevenanceFragment);
            titleArray = new String[]{"我发起的", "我关注的"};
        }
        inittitles();
        mAdapter = new MyAdapter(getFragmentManager());
        viewPagerMine.setAdapter(mAdapter);
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
