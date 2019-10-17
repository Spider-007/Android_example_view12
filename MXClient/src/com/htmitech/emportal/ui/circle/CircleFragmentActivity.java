package com.htmitech.emportal.ui.circle;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.MyBaseFragmentActivity;

public class CircleFragmentActivity extends MyBaseFragmentActivity implements
		IBaseCallback {

	@Override
	protected int getLayoutById() {
		return R.layout.activity_circle;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int statusCode, Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result)  {
		// TODO Auto-generated method stub

	}

}
