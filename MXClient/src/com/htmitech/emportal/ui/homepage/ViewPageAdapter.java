package com.htmitech.emportal.ui.homepage;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.htmitech.api.BookInit;

public class ViewPageAdapter extends PagerAdapter {

	//�����б�
	private List<View> views = null;
	private Context context;
	public ViewPageAdapter(List<View> views,Context context) {
		this.views = views;
		this.context = context;
	}

	@Override
	public Object instantiateItem(View parent, int position) {

		try{
			((ViewPager) parent).addView(views.get(views.size() != 0 ? position % views.size() : 0), 0);
		}catch (Exception e){

		}


		return views.get(views.size() != 0 ? position % views.size() : 0);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {

//
//		((ViewPager) container).removeView(views.get(position));
	}


	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}
