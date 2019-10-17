package com.htmitech.htworkflowformpluginnew.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.htmitech.base.BaseFragment;
import htmitech.com.componentlibrary.base.MyBaseFragment;
import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	ArrayList<MyBaseFragment> list;
    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<MyBaseFragment> list) {
        super(fm);  
        this.list = list;  
          
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
//		((ViewPager) arg0).removeView((View) arg2);
    }
      
    @Override  
    public int getCount() {  
        return list.size();  
    }  
      
    @Override  
    public Fragment getItem(int arg0) {  
        return list.get(arg0);  
    }  
      
      
}
