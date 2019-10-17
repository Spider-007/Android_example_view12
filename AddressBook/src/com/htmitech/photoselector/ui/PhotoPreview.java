package com.htmitech.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.htmitech.addressbook.R;

import htmitech.com.componentlibrary.entity.PhotoModel;
import com.htmitech.app.widget.PhotoView;


public class PhotoPreview extends LinearLayout implements OnClickListener {

	private ProgressBar pbLoading;
	private PhotoView ivContent;
	private OnClickListener l;

	public PhotoPreview(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_photopreview, this, true);

		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_vpp);
		ivContent = (PhotoView) findViewById(R.id.iv_content_vpp);
		ivContent.setOnClickListener(this);
	}

	public PhotoPreview(Context context, AttributeSet attrs, int defStyle) {
		this(context);
	}

	public PhotoPreview(Context context, AttributeSet attrs) {
		this(context);
	}

	public void loadImage(PhotoModel photoModel) {
		loadImage(photoModel.getOriginalPath());
	}

	private void loadImage(String path) {
//		ImageLoader.getInstance().loadImage(path, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				ivContent.setImageBitmap(loadedImage);
//				pbLoading.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				ivContent.setImageDrawable(getResources().getDrawable(R.drawable.pictures_no));
//				pbLoading.setVisibility(View.GONE);
//			}
//		});


		Glide.with(getContext()).load(path).
				placeholder(com.htmitech.addressbook.R.drawable.pictures_no).
				error(com.htmitech.addressbook.R.drawable.pictures_no).into(ivContent);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.l = l;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_content_vpp && l != null)
			l.onClick(ivContent);
	};

}
