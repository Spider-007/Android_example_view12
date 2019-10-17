package com.htmitech.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.htmitech.addressbook.R;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.listener.AddressListener;
import com.htmitech.others.LoadUserAvatar;

import com.htmitech.app.widget.PhotoView;

/**
 * 头像的放大
 * @author Tony
 * 
 */
public class PhonePhotoFragment extends BaseFragment {
	private AddressListener mAddressListener;
	private PhotoView mPhotoView;
	private ImageView iv_back;
	private String photoUrl;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_photo, container, false);
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
		Bundle args = this.getArguments();
		photoUrl = args.getString("photoUrl");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		getActivity().finish();
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mPhotoView = (PhotoView)getView().findViewById(R.id.iv_photo);
		iv_back = (ImageView)getView().findViewById(R.id.iv_back);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		LoadUserAvatar avatarLoader = new LoadUserAvatar(getActivity(), Constant.SDCARD_PATH);
		String phoneSrl = photoUrl ;
		final String phoneSrls = phoneSrl == null ? "":phoneSrl;
		mPhotoView.setTag(phoneSrls);
		Bitmap bitmap = avatarLoader.loadImage(mPhotoView,
				phoneSrls, new LoadUserAvatar.ImageDownloadedCallBack() {

					@Override
					public void onImageDownloaded(
							ImageView imageView, Bitmap bitmap) {
						if (imageView.getTag() == phoneSrls) {
							imageView.setImageBitmap(bitmap);
						}
					}

				});
		if (bitmap != null) {
			mPhotoView.setImageBitmap(bitmap);
		}
	}

}
