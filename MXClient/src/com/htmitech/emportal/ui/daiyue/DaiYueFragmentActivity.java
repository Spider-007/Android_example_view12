package com.htmitech.emportal.ui.daiyue;

import android.widget.ImageView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.htmitech.emportal.ui.daiban.DaiBanFragmentActivity;
import com.htmitech.emportal.ui.main.IBottomItemSelectCallBack;
import com.minxing.client.widget.SystemMainTopRightPopMenu;

public class DaiYueFragmentActivity extends MyBaseFragmentActivity implements IBaseCallback,
		IBottomItemSelectCallBack {

	private SystemMainTopRightPopMenu functionPopMenu;
	@Override
	protected int getLayoutById() {
		// TODO Auto-generated method stub
		return R.layout.activity_daiyue;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		functionPopMenu = new SystemMainTopRightPopMenu(DaiYueFragmentActivity.this);
		functionPopMenu.setForTodo();
	}

	@Override
	public void onSuccess(int statusCode, Object result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentTabClick(int position) {
		// TODO Auto-generated method stub
		
	}

}
