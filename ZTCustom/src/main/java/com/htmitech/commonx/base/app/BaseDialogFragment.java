package com.htmitech.commonx.base.app;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialogFragment extends DialogFragment {

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(getLayoutId(), container, false);
		initViews();
		return mView;
	}

	protected void runOnUiThread(Runnable action) {
		if (getActivity() == null) {
			return;
		}
		getActivity().runOnUiThread(action);
	}

	protected View getRootView() {
		return mView;
	}

	protected View findViewById(int id) {
		return mView.findViewById(id);
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
