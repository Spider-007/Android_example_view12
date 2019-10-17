package com.htmitech.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.addressbook.PeopleMessageActivity;
import com.htmitech.addressbook.PeopleMessageEditActivity;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.PeopleMessage;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.TD_User;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.photo.SelectPhotoActivity;
import com.htmitech.pop.SelectPicPopupWindow;
import com.htmitech.unit.ActivityUnit;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentPeopleMessageAdapter extends BaseAdapter {
	private PeopleMessageActivity context;
	private TD_User mTD_User;
	private ArrayList<PeopleMessage> peopleMessageColumnName;
	private LayoutInflater inflater;
	private LoadUserAvatar avatarLoader;
	private SelectPicPopupWindow menuWindow;
	private OrgUser orgUser;
	public FragmentPeopleMessageAdapter(PeopleMessageActivity context,ArrayList<PeopleMessage> peopleMessageColumnName,SYS_User mSYS_User,OrgUser orgUser){
		this.context = context;
		this.orgUser = orgUser;
		this.peopleMessageColumnName = peopleMessageColumnName;
		inflater = LayoutInflater.from(context);
		avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
	}

	public void setData(ArrayList<PeopleMessage> peopleMessageColumnName,OrgUser mOrgUser){
		this.orgUser = mOrgUser;
		this.peopleMessageColumnName = peopleMessageColumnName;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return peopleMessageColumnName.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return peopleMessageColumnName.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler mViewHodler = null;
		PeopleMessage mPeopleMessage = peopleMessageColumnName.get(arg0);
		if(arg1 == null){
			mViewHodler = new ViewHodler();
			arg1 = inflater.inflate(R.layout.ht_fragment_message_adapter, null);
			mViewHodler.tv_name = (TextView) arg1.findViewById(R.id.tv_name);
			mViewHodler.tv_name_value = (TextView) arg1.findViewById(R.id.tv_name_value);
			mViewHodler.iv_right = (ImageView) arg1.findViewById(R.id.iv_right);
			mViewHodler.tv_message_head = (ImageView) arg1.findViewById(R.id.tv_message_head);
			mViewHodler.default_tv = (TextView) arg1.findViewById(R.id.default_tv);
			mViewHodler.rl_head_view = (RelativeLayout) arg1.findViewById(R.id.rl_head_view);
			arg1.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) arg1.getTag();
		}
		TD_User mTD_User = mPeopleMessage.getmTD_User();
		boolean enabledEdit = mTD_User.isEnabledEdit();
		mViewHodler.tv_name.setText(mTD_User.getDisLabel()+" ：");
		mViewHodler.tv_name_value.setVisibility(View.VISIBLE);
		if(TextUtils.isEmpty(mPeopleMessage.getValue()) || mPeopleMessage.getValue().equalsIgnoreCase("null")){
			mViewHodler.tv_name_value.setText("未填写");
			mPeopleMessage.setValue("");
			mViewHodler.tv_name_value.setTextColor(context.getResources().getColor(R.color.grayaddress));
		}else{
			mViewHodler.tv_name_value.setText(mPeopleMessage.getValue());
			mViewHodler.tv_name_value.setTextColor(context.getResources().getColor(R.color.black));
		}

		if(enabledEdit){
			mViewHodler.iv_right.setVisibility(View.VISIBLE);
			arg1.setEnabled(true);
		}else{
			arg1.setEnabled(false);
			mViewHodler.iv_right.setVisibility(View.INVISIBLE);
		}
		//头像单独添加
		if("head_type".equals(mPeopleMessage.getName())){
			mViewHodler.rl_head_view.setVisibility(View.VISIBLE);
			mViewHodler.tv_message_head.setEnabled(true);
			mViewHodler.tv_name_value.setVisibility(View.INVISIBLE);
//			mViewHodler.tv_message_head.setOnClickListener(new ItemOnClickListener(mPeopleMessage, false));
			try {
				BookInit.getInstance().getPhotoBitMap(context, mViewHodler.tv_message_head, mViewHodler.default_tv);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			arg1.setOnClickListener(new ItemOnClickListener(mPeopleMessage,true));
		}else{
			mViewHodler.tv_message_head.setVisibility(View.GONE);
			mViewHodler.default_tv.setVisibility(View.GONE);
			mViewHodler.rl_head_view.setVisibility(View.GONE);
			arg1.setOnClickListener(new ItemOnClickListener(mPeopleMessage, false));
		}
		return arg1;
	}
	private class ItemOnClickListener implements View.OnClickListener {
		public PeopleMessage mPeopleMessage;
		private boolean isPhoto;
		public ItemOnClickListener(PeopleMessage mPeopleMessage,boolean isPhoto){
			this.mPeopleMessage = mPeopleMessage;
			this.isPhoto = isPhoto;
		}
		public ItemOnClickListener(){

		}
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.tv_message_head){
//				BookInit.getInstance().setRequestUser(orgUser);
//				BookInit.getInstance().setOrgUser(orgUser);
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("photoUrl",orgUser.getPic_path());
//				ActivityUnit.switchTo(context, PhotoActivity.class, map);
//				menuWindow = new SelectPicPopupWindow(context, this);
//				menuWindow.setMessage();
//				//显示窗口
//				menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

//				Intent intent = new Intent(context, PhotoActivity.class);
//
//				context.startActivity(intent);


			}else if(v.getId() == R.id.bt_send){
//				Intent intent = new Intent(context, SelectPhotoActivity.class);
//				intent.addFlags()
//				context.startActivity(intent);
//				Map<String,Object> map = new HashMap<String,Object>();
//				map.put("photoUrl", mSYS_User.getPhotosurl());
				BookInit.getInstance().setOrgUser(orgUser);

				ActivityUnit.switchTo(context, SelectPhotoActivity.class,null);

				menuWindow.dismiss();
			}else if(v.getId() == R.id.bt_call){
				// 拍照
				//设置图片的保存路径,作为全局变量
//				BookInit.getInstance().setRequestUser(mSYS_User);
				BookInit.getInstance().setOrgUser(orgUser);
				String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/filename.jpg";
				File temp = new File(imageFilePath);
				Uri imageFileUri = Uri.fromFile(temp);//获取文件的Uri
				Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//跳转到相机Activity
				it.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);//告诉相机拍摄完毕输出图片到指定的Uri
				context.startActivityForResult(it, 102);
				menuWindow.dismiss();
			}else if(v.getId() == R.id.bt_save){
				menuWindow.dismiss();
			}else{
				if(isPhoto){
					menuWindow = new SelectPicPopupWindow(context, this);
					menuWindow.setMessage();
					//显示窗口
					menuWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//
				}else{
					Map<String,Object> intent = new HashMap<String,Object>();
//					mSYS_User.setPhotosurl("docment");
					intent.put("title", mPeopleMessage.getmTD_User().getDisLabel());
					intent.put("fieldName",mPeopleMessage.getmTD_User().getFieldName());
					intent.put("value", mPeopleMessage.getValue());
//					intent.put("mSYS_User", mSYS_User);
					intent.put("orgUser", orgUser);
					ActivityUnit.switchTo(context, PeopleMessageEditActivity.class, intent);


//					Intent intent = new Intent(context, PeopleMessageEditActivity.class);
//					intent.putExtra("title",mPeopleMessage.getmTD_User().getDisLabel());
//					intent.putExtra("value",mPeopleMessage.getValue());
//					intent.putExtra("mSYS_User",mSYS_User);
//					context.startActivity(intent);
				}

			}

		}
	}
	public class ViewHodler{
		public TextView tv_name;
		public TextView tv_name_value;
		public ImageView iv_right;
		public ImageView tv_message_head;
		public TextView default_tv;
		public RelativeLayout rl_head_view;
	}

	private void showUserAvatar(ImageView iamgeView,final String avatar) {
		if(BookInit.getInstance().getBitmap() != null){
			iamgeView.setImageBitmap(BookInit.getInstance().getBitmap());
			return;
		}
		if(avatar==null||avatar.equals(""))
			return;
		iamgeView.setTag(avatar);
		avatarLoader.setIsLocalhost(true);
		Bitmap bitmap = avatarLoader.loadImage(iamgeView,
				avatar, new LoadUserAvatar.ImageDownloadedCallBack() {

					@Override
					public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
						if (imageView.getTag() == avatar) {
							if(BookInit.getInstance().getBitmap() != null){
								imageView.setImageBitmap(BookInit.getInstance().getBitmap());
							}else{
								imageView.setImageBitmap(bitmap);
							}
						}
					}

				});

		if (bitmap != null) {
			if(BookInit.getInstance().getBitmap() != null){
				iamgeView.setImageBitmap(BookInit.getInstance().getBitmap());
			}else{
				iamgeView.setImageBitmap(bitmap);
			}

		}
	}
}
