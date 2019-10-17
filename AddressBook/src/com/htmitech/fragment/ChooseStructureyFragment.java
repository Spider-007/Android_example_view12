package com.htmitech.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.htmitech.adapter.SimpleTreeAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.addressbook.UserDetailsActivity;
import com.htmitech.api.BookInit;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.CustomEditText;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.SYS_DepartmentDAO;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.domain.Node;
import com.htmitech.domain.OrgUser;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.listener.OnTreeNodeClickListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWayEnum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 结构
 * @author Tony
 * 
 */
public class ChooseStructureyFragment extends BaseFragment implements CallCheckUserListener, TextWatcher {
	private CallBackChoosePeople mCallBackChoosePeople;
//	private BaseApplication myApp;
	private SYS_Department currentSYS_Department;// 当前的department
	private ProgressBar progress_;
	private ListView child_list;
	private int itemHight;
	private CustomEditText mCustomEditText;
	private CharacterParser characterParser;// 汉字转拼音
	private SimpleTreeAdapter mUserDetailChildAdapter;
	private ChooseWayEnum mChooseWayEnum;
	private SYS_DepartmentDAO mSYS_DepartmentDAO;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_choose_user_details_jiegou, container, false);
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
			mCallBackChoosePeople = (CallBackChoosePeople) activity;
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
		currentSYS_Department.setParentSYS_Department(null);
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		progress_ = (ProgressBar)getView().findViewById(R.id.progress_);
		child_list = (ListView) getView().findViewById(R.id.child_list);
		mCustomEditText = (CustomEditText) getView().findViewById(R.id.child_search_input);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		progress_.setVisibility(View.GONE);
		mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
//		myApp = BaseApplication.getApplication(getActivity());
		characterParser = CharacterParser.getInstance();
		mCustomEditText.addTextChangedListener(this);
//		currentSYS_Department = myApp.getSYS_Department_();
//		if (currentSYS_Department == null) {
			mSYS_DepartmentDAO = new SYS_DepartmentDAO(getActivity(), null);
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(getActivity());
						if(Constant.ROOTNODE_STRINGID.equals("")){
							Constant.ROOTNODE_STRINGID =  mSYS_DepartmentDAO.departmentCode(BookInit.getInstance().getPortalId());
						}
						currentSYS_Department = mSYS_DepartmentDAO.getDepartments(Constant.ROOTNODE_STRINGID, new SYS_Department());
						if(currentSYS_Department != null){
							currentSYS_Department.getSYS_DepartmentList().get(0).setNumber((int) mSYS_UserDAO.getCount());
						}
						ChooseStructureyFragment.this.onComplete();
//								.getSYS_Department(UserDetailsChildFragment.this);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
//		} else {
//				progress_.setVisibility(View.GONE);
////				/**
////				 * 下面是树形结构
////				 */
//			try {
//				mUserDetailChildAdapter = new SimpleTreeAdapter(child_list, getActivity(), currentSYS_Department.getSYS_DepartmentList(), 0,this);
//				mUserDetailChildAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener()
//				{
//
//					@Override
//					public void onClick(Node node, int position)
//					{
//
//					}
//
//				});
//				child_list.setAdapter(mUserDetailChildAdapter);
//				ListAdapter listAdapter = child_list.getAdapter();
//				if (listAdapter == null) {
//					return;
//				}
//				View listItem = listAdapter.getView(0, null, child_list);
//				listItem.measure(0, 0);
//				itemHight =  listItem.getMeasuredHeight();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			};
//		}
	}
	//加载完成
	public void onComplete() {
		// TODO Auto-generated method stub
		if(getActivity() == null)
			return;
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mCallBackChoosePeople.callBackProGone();
				progress_.setVisibility(View.GONE);
				try {
					mUserDetailChildAdapter = new SimpleTreeAdapter(getActivity(), currentSYS_Department.getSYS_DepartmentList(), 0, ChooseStructureyFragment.this);
					mUserDetailChildAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {

						@Override
						public void onClick(Node node, int position) {
							//是否是人员  如果是人员 那么近进入查看人员详细信息
//								if(node.isPeople){
//									Intent intent = new Intent(getActivity(), UserDetailsActivity.class);
//									SYS_User mSYS_User = node.getmSYS_User();
//									intent.putExtra("currentSYS_Department",copyUser(mSYS_User));
//									getActivity().startActivity(intent);
//								}
						}

					});
					child_list.setAdapter(mUserDetailChildAdapter);
					ListAdapter listAdapter = child_list.getAdapter();
					if (listAdapter == null) {
						return;
					}
					View listItem = listAdapter.getView(0, null, child_list);
					listItem.measure(0, 0);
					itemHight = listItem.getMeasuredHeight();
				} catch (Exception e) {

				}

			}
		});
	}
	@Override
	public void checkUser(SYS_User mUser, ArrayList<SYS_User> checkAllUser, boolean isCheck, ImageView ivAvatar, CheckBox mCb, TextView default_tv, boolean isImage) {
		Bitmap obmp = null;
		if(isCheck){
			checkAllUser.add(mUser);
			ivAvatar.setDrawingCacheEnabled(true);
			obmp = Bitmap.createBitmap(ivAvatar.getDrawingCache());
			ivAvatar.setDrawingCacheEnabled(false);
			mUser.setBitmap(obmp);
		}else{
		}
		//回调选择人员
		mCallBackChoosePeople.callBackChoosePeopleMessage(checkAllUser, checkAllUser.size(), obmp, ivAvatar, default_tv, isImage, mUser.getColor(), itemHight, isCheck,mCb);
	}

	@Override
	public void checkDepartment(SYS_Department crrentDepartment, ArrayList<SYS_Department> checkDepartment, boolean isCheck, ImageView currentImage, CheckBox mCb) {
		Bitmap obmp = null;
		if(isCheck){
			checkDepartment.add(crrentDepartment);
			currentImage.setDrawingCacheEnabled(true);
			obmp = Bitmap.createBitmap(currentImage.getDrawingCache());
			currentImage.setDrawingCacheEnabled(false);
		}else{
		}
		//回调选择部门
		mCallBackChoosePeople.callBackChooseDepartment(checkDepartment,checkDepartment.size(), obmp,currentImage,itemHight,isCheck,mCb);
	}
	@SuppressLint("DefaultLocale")
	public class PinyinComparators implements Comparator<SYS_User> {

		@SuppressLint("DefaultLocale")
		@Override
		public int compare(SYS_User o1, SYS_User o2) {
			// TODO Auto-generated method stub
			String py1 = o1.getHeader();
			String py2 = o2.getHeader();
			// 判断是否为空""
			if (isEmpty(py1) && isEmpty(py2))
				return 0;
			if (isEmpty(py1))
				return -1;
			if (isEmpty(py2))
				return 1;
			String str1 = "";
			String str2 = "";
			try {
				str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
				str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
			} catch (Exception e) {
			}
			return str1.compareTo(str2);
		}

		private boolean isEmpty(String str) {
			return "".equals(str.trim());
		}
	}

	// 处理首页面的搜索方法
	private void searchUser(final String filterStr) {
		final List<SYS_User> filterDateList = new ArrayList<SYS_User>();

		if (TextUtils.isEmpty(filterStr)) {
			if(mUserDetailChildAdapter == null){
				return;
			}
			mUserDetailChildAdapter.setData(true);
			mUserDetailChildAdapter.notifyDataSetChanged();
		} else {
			filterDateList.clear();
			new Thread(new Runnable() {
				@Override
				public void run() {
					serachMecath(filterStr);
				}
			}).start();
		}
	}

	public void serachMecath(String filterStr){
		switch (mChooseWayEnum){
			case PEOPLECHOOSE:
				for (Node nodes : mUserDetailChildAdapter.getAllNodes()) {
					nodes.isShow = false;
					if(nodes.isPeople){
						SYS_User sortModel = nodes.getmSYS_User();
						String name = sortModel.getFullName();
						String suoxie = sortModel.getSuoxie();
						String phone = sortModel.getMobile();
						if (name.indexOf(filterStr.toString()) != -1
								|| suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
								|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						} else if (phone.contains(filterStr)) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						}
					}

				}

				break;
			case DEPARTMENTCHOOSE:

				for (Node nodes : mUserDetailChildAdapter.getAllNodes()) {
					nodes.isShow = false;
					if(!nodes.isPeople){
						SYS_Department sortModel = nodes.getmSYS_Department();
						String name = sortModel.getFullName();
						String suoxie = sortModel.getSuoxie();
						if (name.indexOf(filterStr.toString()) != -1
								|| suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
								|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						}
					}

				}

				break;
			case FREECHOOSE:
				for (Node nodes : mUserDetailChildAdapter.getAllNodes()) {
					nodes.isShow = false;
					if(nodes.isPeople){
						SYS_User sortModel = nodes.getmSYS_User();
						String name = sortModel.getFullName();
						String suoxie = sortModel.getSuoxie();
						String phone = sortModel.getMobile();
						if (name.indexOf(filterStr.toString()) != -1
								|| suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
								|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						} else if (phone.contains(filterStr)) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						}
					}else{
						SYS_Department sortModel = nodes.getmSYS_Department();
						String name = sortModel.getFullName();
						String suoxie = sortModel.getSuoxie();
						if (name.indexOf(filterStr.toString()) != -1
								|| suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
								|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
//								filterDateList.add(sortModel);
							nodes.isShow = true;
						}
					}

				}
				break;
		}

		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				try{
					mUserDetailChildAdapter.setData(false);
					mUserDetailChildAdapter.notifyDataSetChanged();
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		searchUser(s.toString());
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
