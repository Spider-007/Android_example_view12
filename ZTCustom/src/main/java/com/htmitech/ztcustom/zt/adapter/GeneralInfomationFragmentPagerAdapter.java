package com.htmitech.ztcustom.zt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by heyang on 2018-4-12.
 */
public class GeneralInfomationFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private FragmentManager fragmetnmanager;  //创建FragmentManager

    //需要同时获取FragmentManager和数据
    public GeneralInfomationFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmetnmanager = fm;
    }

    //获取Item的数据，也就是Fragment
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //ViewPager的Item数目
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
