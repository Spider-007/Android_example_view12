package com.htmitech.commonx.base.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		initViews();
	}

	/**
	 * 获取布局id，用于setContentView。
	 * 
	 * @return id
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化View。
	 */
	protected abstract void initViews();
}
