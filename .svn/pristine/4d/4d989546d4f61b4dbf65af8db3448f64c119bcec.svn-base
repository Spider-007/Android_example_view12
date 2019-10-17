package com.htmitech.emportal.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MainViewPager extends JazzyViewPager {

	public static final int INDEX_DAIBAN = 0;
	public static final int INDEX_DAIYUE = 1;
	public static final int INDEX_COMMUNICATION = 2;
	public static final int INDEX_CIRCLE = 3;
	
	private boolean noScroll = false;

	public MainViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainViewPager(Context context) {
		super(context);
	}

	public void setNoScroll(boolean noScroll) {
		this.noScroll = noScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (noScroll)
			return false;
		else
			return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (noScroll)
			return false;
		else
			return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
	}

}
