package com.htmitech.photoselector.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.addressbook.R;

import java.io.File;

import htmitech.com.componentlibrary.entity.PhotoModel;

import static com.htmitech.edittext.SuperEditText.isChecked;


/**
 * @author Aizaz AZ
 *
 */

public class PhotoItem extends LinearLayout implements OnCheckedChangeListener,
		OnLongClickListener ,View.OnClickListener{

	private ImageView ivPhoto;
	private CheckBox cbPhoto;
	private TextView tvToumingdu;
	private onPhotoItemCheckedListener listener;
	private PhotoModel photo;
	private boolean isCheckAll;
	private onItemClickListener l;
	CompoundButton buttonView;
	private int position;	
	private Context context;
	private PhotoItem(Context context) {
		super(context);
	}

	public PhotoItem(Context context, onPhotoItemCheckedListener listener) {
		this(context);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.layout_photoitem, this,
				true);
		this.listener = listener;

		setOnLongClickListener(this);

		ivPhoto = (ImageView) findViewById(R.id.iv_photo_lpsi);
		cbPhoto = (CheckBox) findViewById(R.id.cb_photo_lpsi);
		tvToumingdu = (TextView) findViewById(R.id.tv_toumingdu);
		tvToumingdu.getBackground().setAlpha(100);

		cbPhoto.setOnCheckedChangeListener(this); // CheckBoxѡ��״̬�ı������
		if(((PhotoSelectorActivity)context).isMultipleChoice){
			cbPhoto.setVisibility(View.GONE);
			ivPhoto.setOnClickListener(this);
		}else {
			cbPhoto.setVisibility(View.VISIBLE);
		}

	}
	@Override
	public void onClick(View view) {
		listener.onCheckedChanged(photo, null, isChecked, cbPhoto, tvToumingdu);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (!isCheckAll) {
			listener.onCheckedChanged(photo, buttonView, isChecked,cbPhoto,tvToumingdu);
		}
//		if (isChecked) {
//			setDrawingable();
//			ivPhoto.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//		} else {
//			ivPhoto.clearColorFilter();
//		}
//		setCheck(isChecked);
//		photo.setChecked(isChecked);
	}
	public void setCheck(boolean isCheck){
		tvToumingdu.setVisibility(isCheck ? View.VISIBLE : View.GONE);
	}

	/** ����·���µ�ͼƬ��Ӧ������ͼ */
	public void setImageDrawable(final PhotoModel photo) {
		this.photo = photo;
		// You may need this setting form some custom ROM(s)
		/*
		 * new Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { ImageLoader.getInstance().displayImage(
		 * "file://" + photo.getOriginalPath(), ivPhoto); } }, new
		 * Random().nextInt(10));
		 */
		File file = new File(photo.getOriginalPath());
		Glide.with(getContext()).load(file).placeholder(R.drawable.pictures_no).into(ivPhoto);
//		ImageLoader.getInstance().displayImage(
//				"file://" + photo.getOriginalPath(), ivPhoto);
	}

	private void setDrawingable() {
		ivPhoto.setDrawingCacheEnabled(true);
		ivPhoto.buildDrawingCache();
	}

	@Override
	public void setSelected(boolean selected) {
		if (photo == null) {
			return;
		}
//		isCheckAll = true;
		cbPhoto.setChecked(selected);
		isCheckAll = false;
	}

	public void setOnClickListener(onItemClickListener l, int position) {
		this.l = l;
		this.position = position;
	}


	// @Override
	// public void
	// onClick(View v) {
	// if (l != null)
	// l.onItemClick(position);
	// }

	/** ͼƬItemѡ���¼������� */
	public static interface onPhotoItemCheckedListener {
		public void onCheckedChanged(PhotoModel photoModel,
									 CompoundButton buttonView, boolean isChecked,CheckBox cb_photo,TextView tmd);
	}

	/** ͼƬ����¼� */
	public interface onItemClickListener {
		public void onItemClick(int position);
	}

	@Override
	public boolean onLongClick(View v) {
		if (l != null)
			l.onItemClick(position);
		return true;
	}

}
