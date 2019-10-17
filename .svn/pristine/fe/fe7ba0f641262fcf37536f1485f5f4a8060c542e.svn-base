package com.htmitech.ztcustom.zt.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by heyang on 2016-10-12.
 */
public class ContainerPagerAdapter extends PagerAdapter{

    private List<FrameLayout> listFramLayout;

    public ContainerPagerAdapter(List<FrameLayout> listFramLayout){
        this.listFramLayout=listFramLayout;
    }

    //获得size
    @Override
    public int getCount() {
        return listFramLayout.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //销毁Item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(listFramLayout.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    //实例化Item
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(listFramLayout.get(position));
        return listFramLayout.get(position);
    }
}
