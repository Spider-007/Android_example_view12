package com.htmitech.emportal.ui.applicationcenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htmitech.addressbook.R;
import com.htmitech.base.BaseFragment;
import com.htmitech.listener.AddressListener;

/**
 *
 * @author Tony
 *
 *
 *
 */
public class AppCenterListFragment extends BaseFragment {
	private AddressListener mAddressListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_choose_user_details_gaoji, container, false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);
		try {
			mAddressListener = (AddressListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().getClass().getName()
					+ " must implements interface MyListener");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

}
