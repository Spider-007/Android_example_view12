package com.htmitech.emportal.ui.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DetailActivityLayout extends RelativeLayout {
	public DetailActivityLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DetailActivityLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DetailActivityLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	CallBackLayout mCallBackLayout;

	public void setValue(CallBackLayout mCallBackLayout) {
		this.mCallBackLayout = mCallBackLayout;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		// layoutView(mAddFloatingActionButton, this.left, this.top, this.right,
		// this.bottom);
		super.onLayout(true, left, top, right, bottom);
		if (mCallBackLayout != null)
			mCallBackLayout.callBackLayout();

	}

}
