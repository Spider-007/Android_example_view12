package com.htmitech.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.htmitech.addressbook.GeneralFunctionSearchActivity;
import com.htmitech.addressbook.R;
import com.htmitech.addressbook.UserDetailsChildActivity;
import com.htmitech.base.BaseFragment;
import com.htmitech.listener.AddressListener;

/**
 * 点击按钮显示查找联系人和通讯录的fragment
 */
public class GeneralFunctionFragment extends BaseFragment implements View.OnClickListener{
	public LinearLayout function_search;
	public LinearLayout  function_book;
	public ImageView btn_daiban_person;
	public int userSize;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_general, container, false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Bundle args = this.getArguments();
		userSize = args.getInt("size");
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
		function_book = (LinearLayout) getView().findViewById(R.id.function_book);
		function_search = (LinearLayout)getView().findViewById(R.id.function_search);
		btn_daiban_person = (ImageView)getView().findViewById(R.id.btn_daiban_person);
		function_book.setOnClickListener(this);
		function_search.setOnClickListener(this);
		btn_daiban_person.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() ==R.id.function_book ){
			Intent mIntent = new Intent(getActivity(), UserDetailsChildActivity.class);
			mIntent.putExtra("size",userSize);
			getActivity().startActivity(mIntent);
		}else if(view.getId() == R.id.function_search){
			Intent mIntent = new Intent(getActivity(), GeneralFunctionSearchActivity.class);
			getActivity().startActivity(mIntent);
		}else if(view.getId() == R.id.btn_daiban_person){
			getActivity().finish();
		}
	}
}
