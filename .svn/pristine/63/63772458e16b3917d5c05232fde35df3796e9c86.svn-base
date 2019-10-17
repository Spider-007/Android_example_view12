package com.htmitech.emportal.ui.homepage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/***
 * 嵌套listview计算高度
 * 
 * @author aaa
 * 
 */
public class XListView extends ListView {
	/***
	 * 嵌套listview计算高度
	 * @param context
	 * @param attrs
	 */
	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public XListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true; 
		}
		return super.dispatchTouchEvent(ev);
	}
}
