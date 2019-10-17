package com.htmitech.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallBackUserCount;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.others.LoadUserAvatar;
import com.htmitech.pop.CallPhonePopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 已选择页面
 */

public class UserHasChooseAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<SYS_User> arraySYS_User;
	private ArrayList<SYS_Department> arrDepartment;
	private ChildFragmentListener mChildFragmentListener;
	private static final int TYPE_USER_TAG = 0;
	private static final int TYPE_DEPARTMENT_TAG = 1;
	private CallBackUserCount mCallBackUserCount;
	private ChooseWayEnum mChooseWayEnum;
	private LoadUserAvatar avatarLoader;
	public void setData(ArrayList<SYS_User> arraySYS_User,ArrayList<SYS_Department> arrDepartment){
		if(arraySYS_User == null){
			this.arraySYS_User = new ArrayList<SYS_User>();
		}else{
			this.arraySYS_User = arraySYS_User;
		}
		if(arrDepartment == null){
			this.arrDepartment = new ArrayList<SYS_Department>();
		}else{
			this.arrDepartment = arrDepartment;
		}
		avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);
		mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
		this.notifyDataSetChanged();
	}

	public UserHasChooseAdapter(Context context,ArrayList<SYS_User> arraySYS_User,ArrayList<SYS_Department> arrDepartment,CallBackUserCount mCallBackUserCount) {
		if(arraySYS_User == null){
			this.arraySYS_User = new ArrayList<SYS_User>();
		}else{
			this.arraySYS_User = arraySYS_User;
		}
		if(arrDepartment == null){
			this.arrDepartment = new ArrayList<SYS_Department>();
		}else{
			this.arrDepartment = arrDepartment;
		}

		this.context = context;
		this.mCallBackUserCount = mCallBackUserCount;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position < arrDepartment.size()) {
			return TYPE_DEPARTMENT_TAG;
		} else {
			return TYPE_USER_TAG;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arraySYS_User.size() + arrDepartment.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		if (position < arrDepartment.size()) {
			return arrDepartment.get(position);
		} else {
			return arraySYS_User.get(arrDepartment.size() - position);
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolderChild mViewHolderChild = null;
		ViewHolderChildPeople mViewHolderChildPeople = null;
		int itemType = this.getItemViewType(position);
		if (itemType == TYPE_DEPARTMENT_TAG) {
			View viewDepartment = null;
			SYS_Department crrentDepartment = arrDepartment.get(position);
			if (convertView == null) {
				mViewHolderChild = new ViewHolderChild();
				viewDepartment = inflater.inflate(
						R.layout.ht_fragment_choose_contactlist_adapter, null);
				mViewHolderChild.ivAvatar = (ImageView) viewDepartment
						.findViewById(R.id.iv_avatar);
				mViewHolderChild.tv_department_name = (TextView) viewDepartment
						.findViewById(R.id.tv_department_name);
				mViewHolderChild.fragment_child = (RelativeLayout) viewDepartment
						.findViewById(R.id.fragment_child);
				mViewHolderChild.iv_delete_depart = (ImageView) viewDepartment.findViewById(R.id.iv_delete);
				mViewHolderChild.cbChild = (CheckBox) viewDepartment.findViewById(R.id.cb_select_child);
				viewDepartment.setTag(R.id.tag_department, mViewHolderChild);
				convertView = viewDepartment;
			} else {
				mViewHolderChild = (ViewHolderChild) convertView
						.getTag(R.id.tag_department);
			}
			mViewHolderChild.iv_delete_depart.setVisibility(View.VISIBLE);
			mViewHolderChild.cbChild.setVisibility(View.GONE);
			mViewHolderChild.tv_department_name.setText(crrentDepartment.getFullName());
			mViewHolderChild.iv_delete_depart
					.setOnClickListener(new DepartmentOnClickListener(crrentDepartment, mViewHolderChild.cbChild));
		} else {
			if (itemType == TYPE_USER_TAG) {
				View viewUser = null;
				SYS_User mSYS_User = arraySYS_User.get(position
						- arrDepartment.size());
				if (convertView == null) {
					mViewHolderChildPeople = new ViewHolderChildPeople();
					viewUser = inflater.inflate(R.layout.ht_item_contact_choose_list, null);
					mViewHolderChildPeople.ivAvatar = (ImageView) viewUser
							.findViewById(R.id.iv_avatar);
					mViewHolderChildPeople.tvName = (TextView) viewUser
							.findViewById(R.id.tv_name);
					mViewHolderChildPeople.tv_phone = (TextView) viewUser
							.findViewById(R.id.tv_phone);
					mViewHolderChildPeople.user_relative = (RelativeLayout) viewUser
							.findViewById(R.id.user_relative);
					mViewHolderChildPeople.default_tv = (TextView) viewUser.findViewById(R.id.default_tv);
					mViewHolderChildPeople.cbPeople = (CheckBox) viewUser.findViewById(R.id.cb_people);
					mViewHolderChildPeople.iv_delete_people = (ImageView) viewUser.findViewById(R.id.iv_delete);
					viewUser.setTag(R.id.tag_people, mViewHolderChildPeople);
					convertView = viewUser;
				} else {
					mViewHolderChildPeople = (ViewHolderChildPeople) convertView
							.getTag(R.id.tag_people);
				}
				mViewHolderChildPeople.iv_delete_people.setVisibility(View.VISIBLE);
				mViewHolderChildPeople.cbPeople.setVisibility(View.GONE);
				mViewHolderChildPeople.tvName.setText(mSYS_User.getFullName());
				String phoneSrl = mSYS_User.getPhotosurl();
				final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
				if(phoneSrls.equals("")){
					mViewHolderChildPeople.default_tv.setText(mSYS_User.nameJan);
					mViewHolderChildPeople.default_tv.setBackgroundColor(mSYS_User.getColor());
					mViewHolderChildPeople.default_tv.setVisibility(View.VISIBLE);
					mViewHolderChildPeople.iv_delete_people.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, mViewHolderChildPeople.cbPeople));
				}else{
					if(mSYS_User.getBitmap() == null){
						mViewHolderChildPeople.ivAvatar.setTag(phoneSrls);
						Bitmap bitmap = avatarLoader.loadImage(mViewHolderChildPeople.ivAvatar,
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
							mViewHolderChildPeople.ivAvatar.setImageBitmap(bitmap);
						}
					}else{
						mViewHolderChildPeople.ivAvatar.setImageBitmap(mSYS_User.getBitmap());
					}

					mViewHolderChildPeople.default_tv.setVisibility(View.GONE);
					mViewHolderChildPeople.iv_delete_people.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, mViewHolderChildPeople.cbPeople));
				}
			}
		}

		return convertView;
	}

	class ViewHolderChild {
		ImageView ivAvatar;
		TextView tv_department_name;
		CheckBox cbChild;
		ImageView iv_delete_depart;
		RelativeLayout fragment_child;
	}

	class ViewHolderChildPeople {
		ImageView ivAvatar;
		TextView tvName;
		TextView tv_phone;
		CheckBox cbPeople;
		TextView default_tv;
		ImageView iv_delete_people;
		RelativeLayout user_relative;
	}

	// 删除部门
	class DepartmentOnClickListener implements OnClickListener {
		private SYS_Department currentSYS_Department;
		private CheckBox cbPeople;
		public DepartmentOnClickListener(SYS_Department currentSYS_Department, CheckBox cbPeople) {
			this.currentSYS_Department = currentSYS_Department;
			this.cbPeople = cbPeople;

		}

		@Override
		public void onClick(View position) {
			cbPeople.setChecked(false);
			currentSYS_Department.setIsCheck(false);
			currentSYS_Department.mCheckBox.setChecked(false);
			arrDepartment.remove(currentSYS_Department);
			mCallBackUserCount.callUserCount(arrDepartment.size());
			notifyDataSetChanged();
		}
	}

	// 删除选择人员
	class DepartmentPeopleOnClickListener implements OnClickListener {
		private SYS_User currentSYS_User;
		private CheckBox cbPeople;

		public DepartmentPeopleOnClickListener(SYS_User currentSYS_User,CheckBox cbPeople) {
			this.currentSYS_User = currentSYS_User;
			this.cbPeople = cbPeople;

		}

		@Override
		public void onClick(View position) {
			cbPeople.setChecked(false);
			currentSYS_User.setIsCheck(false);
			if(currentSYS_User.mCheckBox != null){
				currentSYS_User.mCheckBox.setChecked(false);
			}

			arraySYS_User.remove(currentSYS_User);
			mCallBackUserCount.callUserCount(arraySYS_User.size());
			if (currentSYS_User.node != null){
				currentSYS_User.node.setNumber(-1);
				currentSYS_User.node.getCheckNumber();
			}
			switch (mChooseWayEnum){
				case DEPARTMENTCHOOSE: //部门选择
					break;
				case PEOPLECHOOSE:   //人员选择 并且
					BaseApplication.getApplication(context).setCheck(3,currentSYS_User);
					break;
			}
			notifyDataSetChanged();
		}
	}


}
