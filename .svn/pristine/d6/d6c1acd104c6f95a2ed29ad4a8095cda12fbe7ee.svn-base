package com.htmitech.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.MyCheckBox;
import com.htmitech.domain.Node;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.TreeHelper;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.PeopleHeadEnum;
import com.htmitech.others.LoadUserAvatar;


public class SimpleTreeAdapter extends TreeListViewAdapter
{
	private LoadUserAvatar avatarLoader;

	private CallCheckUserListener mCallCheckUserListener;

	private ArrayList<SYS_User> checkSYSUser ; //选择的人员

	private ArrayList<SYS_Department> checkSYSDepartment ;//选择的部门

	private PeopleHeadEnum mPeopleHeadEnum ;

	private ChooseWay mChooseWay;

	private ChooseWayEnum mChooseWayEnum; //部门选择 还是人员选择 还是自由选择

	private boolean isSoSo = true;

	private Context context;
	public SimpleTreeAdapter(Context context, List<SYS_Department> datas,
			int defaultExpandLevel,CallCheckUserListener mCallCheckUserListener) throws IllegalArgumentException,
			IllegalAccessException
	{

		super(context, datas, defaultExpandLevel);
		this.context = context;
		BaseApplication.getApplication(context).setAllNode(getAllNodes());
		avatarLoader = new LoadUserAvatar(context, Constant.SDCARD_PATH);

		mChooseWay = BookInit.getInstance().getmChooseWay();
		checkSYSUser = BaseApplication.getApplication(context).getCheckALlUser();
		mPeopleHeadEnum = BookInit.getInstance().getPeopleHeadEnum();
		checkSYSDepartment = BaseApplication.getApplication(context).getCheckSYSDepartment();
		mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
		this.mCallCheckUserListener = mCallCheckUserListener;

	}

	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
	{
		
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.ht_fragment_choose_contactlist_adapter, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_label);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.tv_department_name);
			viewHolder.cb_select_child =  (MyCheckBox) convertView
					.findViewById(R.id.cb_select_child);
			viewHolder.default_tv = (TextView) convertView
					.findViewById(R.id.default_tv);
			viewHolder.iv_avatar = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			viewHolder.tv_number = (TextView) convertView
					.findViewById(R.id.tv_number);
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
			//人员展示
			if (node.isPeople) {
				viewHolder.tv_number.setVisibility(View.GONE);
				viewHolder.cb_select_child.setVisibility(View.VISIBLE);
				SYS_User mSYS_User = node.getmSYS_User();
				mSYS_User.node = node;
				viewHolder.cb_select_child.setChecked(mSYS_User.getIsCheck());
				viewHolder.cb_select_child.setTag(mSYS_User.getUserId());
				mSYS_User.setmCheckBox(viewHolder.cb_select_child);
				String phoneSrl = mSYS_User.getPhotosurl();
				java.util.Random random = new java.util.Random();
				int index = position % Constant.colorList.length;
				final String phoneSrls = phoneSrl == null ? "" : phoneSrl;
				if (phoneSrls.equals("")) {
					String name = mSYS_User.getFullName();
					int color = mSYS_User.getColor();
					if (color == 0) {
						mSYS_User.setColor(Constant.colorList[index]);
						color = Constant.colorList[index];
					}
					viewHolder.iv_avatar.setVisibility(View.INVISIBLE);
					GradientDrawable myGrad = (GradientDrawable)viewHolder.default_tv.getBackground();
					myGrad.setColor(color);
					Pattern p = Pattern.compile("[a-zA-Z]");
					Matcher m = p.matcher(name);
					int start = 0, end = 0;
					switch (mPeopleHeadEnum) {
						case DEFAULT:
							viewHolder.default_tv.setVisibility(View.GONE);
							break;
						case SURNAME:
							start = 0;
							end = 1;
							break;
						case THENAME:
							start = name.length() - 2;
							end = name.length();
							break;
					}
					if (m.matches()) {
						viewHolder.default_tv.setText(name.substring(start, end).toUpperCase() + "");
					} else {
						if (name.length() < 2) {
							viewHolder.default_tv.setText(name);
						} else {
							viewHolder.default_tv.setText(name.substring(start, end) + "");
						}
					}

					viewHolder.default_tv.setVisibility(View.VISIBLE);
					mSYS_User.nameJan = viewHolder.default_tv.getText().toString();
					viewHolder.cb_select_child.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, viewHolder.cb_select_child, viewHolder.iv_avatar, true, viewHolder.default_tv, false, node, viewHolder.tv_number));
				} else {
					viewHolder.iv_avatar.setVisibility(View.VISIBLE);
					viewHolder.iv_avatar.setImageResource(R.drawable.default_useravatar);
					showUserAvatar(viewHolder.iv_avatar, phoneSrls);
					viewHolder.default_tv.setVisibility(View.GONE);
					viewHolder.cb_select_child.setOnClickListener(new DepartmentPeopleOnClickListener(mSYS_User, viewHolder.cb_select_child, viewHolder.iv_avatar, true, viewHolder.default_tv, true, node, viewHolder.tv_number));
				}
//				node.getCheckNumber();
			} else {
				//部门展示
				viewHolder.tv_number.setVisibility(View.VISIBLE);
				viewHolder.default_tv.setVisibility(View.GONE);
				viewHolder.cb_select_child.setVisibility(View.GONE);
				viewHolder.iv_avatar.setVisibility(View.VISIBLE);
				viewHolder.tv_number.setText(node.getmSYS_Department().checkNumber + "/" + node.getmSYS_Department().getNumber() + "");
				viewHolder.iv_avatar.setImageResource(R.drawable.mx_icon_department);

				switch (mChooseWayEnum){
					case FREECHOOSE:

					case DEPARTMENTCHOOSE:

						SYS_Department mSYS_Department = node.getmSYS_Department();
						viewHolder.cb_select_child.setChecked(mSYS_Department.getIsCheck());
						mSYS_Department.mCheckBox = viewHolder.cb_select_child;
						viewHolder.cb_select_child.setVisibility(View.VISIBLE);
						viewHolder.tv_number.setVisibility(View.GONE);
						viewHolder.cb_select_child.setOnClickListener(new DepartmentClickListener(mSYS_Department,viewHolder.cb_select_child,viewHolder.iv_avatar,node));
						break;
					case PEOPLECHOOSE:

						break;
				}

			}
			viewHolder.tv_number.setTag(node.getId());
			node.setShowNumber(viewHolder.tv_number);
			if (node.getIcon() == -1) {
				viewHolder.icon.setVisibility(View.INVISIBLE);
			} else {
				if(isSoSo) {
					convertView.setEnabled(true);
					viewHolder.icon.setVisibility(View.VISIBLE);
					viewHolder.icon.setImageResource(node.getIcon());
				}else{
					convertView.setEnabled(false);
					viewHolder.icon.setVisibility(View.INVISIBLE);
				}
			}

			viewHolder.label.setText(node.getName());

		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
		MyCheckBox cb_select_child;
		TextView default_tv;
		ImageView iv_avatar;
		ImageView iv_avatars;
		TextView tv_number;
	}
	private void showUserAvatar(ImageView iamgeView,final String avatar) {
		if(avatar==null||avatar.equals("")) return;
		iamgeView.setTag(avatar);
		Bitmap bitmap = avatarLoader.loadImage(iamgeView,
				avatar, new LoadUserAvatar.ImageDownloadedCallBack() {

					@Override
					public void onImageDownloaded(
							ImageView imageView, Bitmap bitmap) {
						if (imageView.getTag() == avatar) {
							imageView.setImageBitmap(bitmap);
						}
					}

				});

		if (bitmap != null) {
			iamgeView.setImageBitmap(bitmap);
		}
	}
	// 部门选择页面
	class DepartmentClickListener implements View.OnClickListener {
		private SYS_Department mSYS_Department;
		private CheckBox cbPeople;
		private ImageView ivAvatar;
		private Node node;
		public DepartmentClickListener(SYS_Department mSYS_Department,CheckBox cbPeople,ImageView ivAvatar,Node node) {
			this.mSYS_Department = mSYS_Department;
			this.cbPeople = cbPeople;
			this.ivAvatar = ivAvatar;
			this.node = node;
		}

		@Override
		public void onClick(View position) {
		/**
		 * 自由选择的时候是单选
		 */
			switch (mChooseWayEnum){
				case DEPARTMENTCHOOSE: //部门选择
					break;
				case PEOPLECHOOSE:   //人员选择
					break;
				case FREECHOOSE: // 自由选择
					for(SYS_User mSYS_User : checkSYSUser){
						mSYS_User.setIsCheck(false);
						mSYS_User.mCheckBox.setChecked(false);
					}
					checkSYSUser.clear();
					for(SYS_Department mSYS_Department : checkSYSDepartment){
						mSYS_Department.setIsCheck(false);
						mSYS_Department.mCheckBox.setChecked(false);
//							mSYS_Department.setIsCheck(isCheck);
					}
					checkSYSDepartment.clear();
					break;
			}
			switch (mChooseWay){
				case SINGLE_CHOOSE:    //部门单选
//					switch (mChooseWayEnum){
//						case FREECHOOSE:
//							break;
//						case DEPARTMENTCHOOSE:
//							break;
//						case PEOPLECHOOSE:
//							break;
//					}
					for(SYS_Department mSYSDepartment : checkSYSDepartment){
						mSYSDepartment.setIsCheck(false);
						mSYSDepartment.mCheckBox.setChecked(false);
						if(mSYSDepartment.node != null){
							mSYSDepartment.node.setNumber(-1);
						}
					}
					checkSYSDepartment.clear();
					break;
				case MORE_CHOOSE://部门多选 什么都不做
					break;
			}

			mSYS_Department.setIsCheck(cbPeople.isChecked());
			if(cbPeople.isChecked()){
				node.setNumber(1);
				mCallCheckUserListener.checkDepartment(mSYS_Department, checkSYSDepartment, cbPeople.isChecked(),ivAvatar,cbPeople);
			} else {
				node.setNumber(-1);
				checkSYSDepartment.remove(mSYS_Department);
				mCallCheckUserListener.checkDepartment(mSYS_Department, checkSYSDepartment, cbPeople.isChecked(), ivAvatar, cbPeople);
			}
			node.getCheckNumber();
		}
	}


	// CheckBox 选择页面
	class DepartmentPeopleOnClickListener implements View.OnClickListener {
		private SYS_User currentSYS_User;
		private CheckBox cbPeople;
		private ImageView ivAvatar;
		private TextView default_tv;
		private boolean flag;
		private boolean isImage;
		private Node node;
		private TextView showNumber;
		public DepartmentPeopleOnClickListener(SYS_User currentSYS_User,CheckBox cbPeople,ImageView ivAvatar,boolean flag,TextView default_tv,boolean isImage,Node node,TextView showNumber) {
			this.currentSYS_User = currentSYS_User;
			this.cbPeople = cbPeople;
			this.ivAvatar = ivAvatar;
			this.flag = flag;
			this.isImage = isImage;
			this.default_tv = default_tv;
			this.node = node;
			this.showNumber = showNumber;
		}

		@Override
		public void onClick(View position) {

			/**
			 * 自由选择的时候是单选
			 */
			switch (mChooseWayEnum){
				case DEPARTMENTCHOOSE: //部门选择
					break;
				case PEOPLECHOOSE:   //人员选择
					break;
				case FREECHOOSE: // 自由选择
					for(SYS_User mSYS_User : checkSYSUser){
						mSYS_User.setIsCheck(false);
						mSYS_User.mCheckBox.setChecked(false);
					}
					checkSYSUser.clear();
					for(SYS_Department mSYS_Department : checkSYSDepartment){
						mSYS_Department.setIsCheck(false);
						mSYS_Department.mCheckBox.setChecked(false);
//							mSYS_Department.setIsCheck(isCheck);
					}
					checkSYSDepartment.clear();
					break;
			}

			switch (mChooseWay){
				case SINGLE_CHOOSE:    //人员单选
//					for(SYS_User mSYS_User : checkSYSUser){
//						mSYS_User.setIsCheck(false);
//						mSYS_User.mCheckBox.setChecked(false);
//						if(mSYS_User.node != null)
//							mSYS_User.node.setNumber(-1);
//							mSYS_User.node.getCheckNumber();
//					}
					checkSYSUser.clear();
					 break;
				case MORE_CHOOSE://人员多选 什么都不做
					break;
			}
			currentSYS_User.setIsCheck(cbPeople.isChecked());
			if(cbPeople.isChecked()){
				node.setNumber(1);
				mCallCheckUserListener.checkUser(currentSYS_User, checkSYSUser, true,ivAvatar,cbPeople,default_tv, isImage);
			} else {
				node.setNumber(-1);
				checkSYSUser.remove(currentSYS_User);
				mCallCheckUserListener.checkUser(currentSYS_User,checkSYSUser,false,ivAvatar,cbPeople,default_tv,isImage);
			}
			node.getCheckNumber();
			switch (mChooseWayEnum){
				case DEPARTMENTCHOOSE: //部门选择
					break;
				case PEOPLECHOOSE:   //人员选择
					BaseApplication.getApplication(context).setCheck(2, currentSYS_User);
					BaseApplication.getApplication(context).setCheckUserTree(checkSYSUser);
					break;
			}
		}
	}

	public void setData(boolean isFlag){
		isSoSo = isFlag;
		super.setData(isFlag);
	}
}
