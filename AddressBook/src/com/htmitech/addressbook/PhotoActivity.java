package com.htmitech.addressbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.photo.ImageLoader;

import com.htmitech.app.widget.PhotoView;

/**
 * 通用功能
 *
 * @author Tony
 *
 */
public class PhotoActivity extends BaseFragmentActivity implements BackHandledInterface{
	public BaseFragment mBaseFragment;
	private PhotoView mPhotoView;
	private ImageView iv_back;
	private String photoUrl;
	private boolean isCurrent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_fragment_photo);
		Intent intent = getIntent();
		photoUrl = intent.getStringExtra("photoUrl");
		isCurrent = intent.getBooleanExtra("isCurrent",false);
		initContent();
	}

	/** 初始化显示内容 **/
	private void initContent() {
		mPhotoView = (PhotoView)findViewById(R.id.iv_photo);
		iv_back = (ImageView)findViewById(R.id.iv_back);
		LoadUserAvatar avatarLoader = new LoadUserAvatar(this, Constant.SDCARD_PATH);
		String phoneSrl = photoUrl ;
		final String phoneSrls = phoneSrl == null ? "":phoneSrl;
		if(!isCurrent){
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
		}else {
			if(BookInit.getInstance().getBitmap() != null) {
				mPhotoView.setImageBitmap(BookInit.getInstance().getBitmap());
			}else{
				ImageLoader.getInstance(10, ImageLoader.Type.LIFO).loadImage(photoUrl, mPhotoView);
			}

		}
		iv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PhotoActivity.this.finish();
//				ActivityUnit.finish(PhotoActivity.this);
			}
		});
	}

	@Override
	public void onBackPressed() {
		PhotoActivity.this.finish();
//		ActivityUnit.finish(PhotoActivity.this);
	}


	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		mBaseFragment = selectedFragment;
	}
}
