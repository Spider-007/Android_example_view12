package com.htmitech.emportal.ui.contacts;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.base.MyBaseFragmentActivity;
import com.minxing.client.widget.SystemMainTopRightPopMenu;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.ui.contacts.ContactOcuView;

public class ContactOcuActivity extends MyBaseFragmentActivity implements View.OnClickListener {
	private ContactOcuView ocuView = null;
	private ImageButton leftBackButton = null;
	private ImageButton rightAddButton = null;
	private Button rightTextButton = null;

	private SystemMainTopRightPopMenu functionPopMenu;
	@Override
	protected int getLayoutById() {
		// TODO Auto-generated method stub
		return R.layout.activity_contactocu;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		functionPopMenu = new SystemMainTopRightPopMenu(ContactOcuActivity.this);
		functionPopMenu.setForTodo();
		
		findViewById(R.id.btn_ocu_person).setOnClickListener(this);
		findViewById(R.id.imageview_ocu_add).setOnClickListener(this);
		
		((TextView)findViewById(R.id.btn_ocu_title)).setText("订阅");
		
		ocuView = (ContactOcuView)findViewById(R.id.ocu_grid_view);
	}
	
	
	
	@Override
	protected void onResume() {
//		appView.load(false);
		ocuView.reLoad();//重新联网同步数据。
		super.onResume();
	}

	@Override
	protected void onPause() {
		ocuView.endEdit();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		ocuView.disableOcuChangeMonitor();
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_ocu_person:
//			((ClientTabActivity) getParent()).getResideMenu().openMenu(
//					ResideMenu.DIRECTION_LEFT);
			finish();
			break;
		case R.id.imageview_ocu_add:
			MXUIEngine.getInstance().getContactManager().addOcu(ContactOcuActivity.this);
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
			break;
		default:
			break;
		}
	}
	
}
