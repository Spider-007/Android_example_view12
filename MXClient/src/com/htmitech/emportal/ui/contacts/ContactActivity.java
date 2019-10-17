package com.htmitech.emportal.ui.contacts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.htmitech.emportal.R;
import com.minxing.kit.MXUIEngine;
import com.minxing.kit.ui.contacts.ContactManager;
import com.minxing.kit.ui.contacts.MXContactsActivity;

public class ContactActivity extends MXContactsActivity   {

	
	public static ContactActivity intance = null;
	
	

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initContactsHeaderView(false);
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		intance = this;
	}
	
	


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		intance = null;
		Log.i("onDestroy", "backclick!");
	}


	
	/**
	 * 初始化通讯录header view
	 * 
	 */
	private void initContactsHeaderView(Boolean showHandle) {
		final ContactManager contactManager = MXUIEngine.getInstance().getContactManager();
		RelativeLayout contactsHeader = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.contacts_header_view, null);
		View contactsHandler = contactsHeader.findViewById(R.id.system_handle);

		if (showHandle) {
			contactsHandler.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					MainActivity.this.getResideMenu().openMenu(
//							ResideMenu.DIRECTION_LEFT);
				}
			});
		} else {
			ImageButton bacKBtn = (ImageButton) contactsHeader.findViewById(R.id.title_left_back_button);
			contactsHandler.setVisibility(View.GONE);
			bacKBtn.setVisibility(View.VISIBLE);
			bacKBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("contactsHandler", "backclick!");
					ContactActivity.intance.finish();
//					finish();
				}
			});
		}

		ImageButton newContaceButton = (ImageButton) contactsHeader.findViewById(R.id.title_right_new_function);
		newContaceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				contactManager.addContacts(ContactActivity.this);
			}
		});
		contactManager.setHeaderView(contactsHeader);
		contactManager.setBackListener(new ContactManager.HomeScreenBackListener() {

			@Override
			public boolean onBack(Context context) {
//				moveTaskToBack(true);
				finish();
				return true;
			}
		});
	}

	

	
}
