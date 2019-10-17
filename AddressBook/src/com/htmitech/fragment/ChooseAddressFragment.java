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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.adapter.ChooseContactAdapter;
import com.htmitech.adapter.ContactAdapter;
import com.htmitech.adapter.UserDetailChildAdapter;
import com.htmitech.addressbook.GeneralFunctionActivity;
import com.htmitech.addressbook.R;
import com.htmitech.addressbook.UserDetailsChildActivity;
import com.htmitech.api.BookInit;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.Sidebar;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.dao.T_UserRelationshipDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.listener.CallBackUserCount;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseTreeHierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 联系人列表页 选择页面
 *
 */
public class ChooseAddressFragment extends BaseFragment implements
		Sidebar.OnTouchingLetterChangedListener, TextWatcher, OnClickListener,CallCheckUserListener {
	private ChooseContactAdapter adapter;
	private List<T_UserRelationship> contactList;
	private ListView listView;
	private boolean hidden;
	private Sidebar sidebar;
	private TextView tv_total;
	private LayoutInflater infalter;
	private TextView mDialog;
	private CharacterParser characterParser;// 汉字转拼音
	private ArrayList<SYS_User> mSYSUser = null;
	private EditText mSearchInput;
	private ListView search_list; // 搜索的ListView
	private FrameLayout fragment_bottom;
	private int sizeAll ;
	private ProgressBar progress;
	private Activity activity = null;
	private ImageView btn_daiban_person;
	private ImageButton title_right_new_function;
	private ImageView bit_back;
	private FrameLayout frame_search;
	private ProgressBar progress_search;
	private RelativeLayout layout_daiban_titlebar;
	private CallBackChoosePeople mCallBackChoosePeople;
	private int itemHight;
	private RelativeLayout rl_soso;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_contactlist, container, false);
	}

	@Override
	public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
		super.onAttach(activity);
		this.activity = activity;
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
	protected void initView() {
		// TODO Auto-generated method stub
		rl_soso = (RelativeLayout) getView().findViewById(R.id.rl_soso);
		btn_daiban_person = (ImageView)getView().findViewById(R.id.btn_daiban_person);
		title_right_new_function = (ImageButton)getView().findViewById(R.id.title_right_new_function);
		progress = (ProgressBar) getActivity().findViewById(R.id.progress_);
		listView = (ListView) getView().findViewById(R.id.list);
		frame_search = (FrameLayout)getView().findViewById(R.id.frame_search);
		progress_search = (ProgressBar)getView().findViewById(R.id.progress_search);
		layout_daiban_titlebar = (RelativeLayout) getView().findViewById(R.id.layout_daiban_titlebar);
		characterParser = CharacterParser.getInstance();
		// 获取设置contactlist
		bit_back =(ImageView) getView().findViewById(R.id.bit_back);
		infalter = LayoutInflater.from(getActivity());
		mSearchInput = (EditText) getActivity().findViewById(
				R.id.school_friend_member_search_input);
		View footerView = infalter.inflate(R.layout.ht_item_contact_list_footer,
				null);
		listView.addFooterView(footerView);
		sidebar = (Sidebar) getActivity().findViewById(R.id.sidebar);
		mDialog = (TextView) getView().findViewById(R.id.floating_header);
		search_list = (ListView) getView().findViewById(R.id.search_list);
		fragment_bottom = (FrameLayout) getView().findViewById(
				R.id.fragment_bottom);
		tv_total = (TextView) footerView.findViewById(R.id.tv_total);

	}



	public SYS_UserDAO mSYS_UserDAO;
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		layout_daiban_titlebar.setVisibility(View.GONE);
		rl_soso.setVisibility(View.GONE);
		mSYS_UserDAO = new SYS_UserDAO(getActivity(), BaseApplication.getApplication(getActivity()));
		contactList = new ArrayList<T_UserRelationship>();
		sidebar.setTextView(mDialog);
		sidebar.setOnTouchingLetterChangedListener(this);
		adapter = new ChooseContactAdapter(getActivity(), R.layout.ht_item_contact_choose_list,
				contactList,this);
		listView.setSelected(true);
		listView.setAdapter(adapter);
		tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
		btn_daiban_person.setOnClickListener(this);
		title_right_new_function.setOnClickListener(this);
		bit_back.setOnClickListener(this);
		mSearchInput.addTextChangedListener(this);
		progress.setVisibility(View.GONE);
		getContactList();
		callBoradcastReceiver();
	}


	// 刷新ui
	public void refresh() {
		try {
			// 可能会在子线程中调到这方
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
//					getContactList();

					Animation animation = new AlphaAnimation(0f, 1f);
					animation.setDuration(500);
					//1f为延时
					LayoutAnimationController controller = new LayoutAnimationController(animation, 0.5f);
					controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
					listView.setLayoutAnimation(controller);
                    adapter.setData(contactList);
                    tv_total.setText(String.valueOf(contactList.size())
                            + "位联系人");
                }
            });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setProGone(){
		if(progress != null)
			progress.setVisibility(View.GONE);
	}
	/**
	 * 获取联系人列表
	 */
	public void getContactList() {
		// contactList.clear();
		// // 获取好友列表
//		progress.setVisibility(View.VISIBLE);
//		isClick(false);
		final T_UserRelationshipDAO mT_UserRelationshipDAO = new T_UserRelationshipDAO(
				this.getActivity());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					contactList = mT_UserRelationshipDAO.getContactList();
					BaseApplication.getApplication(getActivity()).setContext(contactList);
					// 对list进行排序
					Collections.sort(contactList, new PinyinComparator() {
					});
					refresh();

				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}


	@Override
	public void checkUser(SYS_User mUser, ArrayList<SYS_User> checkAllUser, boolean isCheck, ImageView currentImage, CheckBox mCb, TextView default_tv, boolean isImage) {
		Bitmap obmp = null;
		if(isCheck){
			checkAllUser.add(mUser);
			currentImage.setDrawingCacheEnabled(true);
			obmp = Bitmap.createBitmap(currentImage.getDrawingCache());
			currentImage.setDrawingCacheEnabled(false);
			mUser.setBitmap(obmp);
		}else{
		}
		//回调选择人员
		mCallBackChoosePeople.callBackChoosePeopleMessage(checkAllUser, checkAllUser.size(), obmp,currentImage,default_tv,isImage,mUser.getColor(),itemHight,isCheck,mCb);
	}

	@Override
	public void checkDepartment(SYS_Department crrentDepartment, ArrayList<SYS_Department> checkDepartment, boolean isCheck, ImageView currentImage, CheckBox mCb) {

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

	public static ChooseAddressFragment newInstance() {
		
		Bundle args = new Bundle();
		
		ChooseAddressFragment fragment = new ChooseAddressFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@SuppressLint("DefaultLocale")
	public class PinyinComparator implements Comparator<T_UserRelationship> {

		@SuppressLint("DefaultLocale")
		@Override
		public int compare(T_UserRelationship o1, T_UserRelationship o2) {
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								  int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		filterUser(arg0.toString(), mSYSUser);
	}
	private List<SYS_User> filterDateList;
	// 处理首页面的搜索方法
	private void filterUser(final String filterStr, final List<SYS_User> list) {
		filterDateList = new ArrayList<SYS_User>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = list;
			frame_search.setVisibility(View.GONE);
			fragment_bottom.setVisibility(View.VISIBLE);
		} else {
			progress_search.setVisibility(View.VISIBLE);
			frame_search.setVisibility(View.VISIBLE);
			fragment_bottom.setVisibility(View.GONE);
			new Thread(new Runnable() {
				@Override
				public void run() {
					filterDateList.clear();
					synchronized (this) {
						try {
							filterDateList = mSYS_UserDAO.findIdByUser(filterStr.toString());
							Collections.sort(filterDateList, new PinyinComparators() {
							});
                            getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									SYS_Department mSYS_Department = new SYS_Department();
									mSYS_Department.setDepartmentCode(Constant.ROOTNODE_STRINGID);
									mSYS_Department.setFullName(Constant.ROOTNODEORGANISE_STRING);
									mSYS_Department.setSYS_User(new ArrayList<SYS_User>(filterDateList));
									UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
											mSYS_Department, null, true, getActivity());
									search_list.setAdapter(mUserDetailChildAdapter);
									progress_search.setVisibility(View.GONE);
								}
							});

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();


		}
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		// TODO Auto-generated method stub
		int position = 0;
		// 该字母首次出现的位置
		if (adapter != null) {
			position = adapter.getPositionForSection(s.charAt(0));
		}
		if (position != -1) {
			listView.setSelection(position);
		}
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		// 处理返回键返回首页面
		if (search_list.getVisibility() == View.VISIBLE) {
			mSearchInput.setText("");
			return true;
		}
		if(BookInit.getInstance().getBookType().equals(Constant.HOME_INIT)){
			bit_back.setVisibility(View.GONE);
			btn_daiban_person.setVisibility(View.VISIBLE);
		}
		getActivity().finish();
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.re_function){// 功能号
			BookInit.getInstance().getmCallbackMX().callFunction();
		}else if(arg0.getId() == R.id.re_unit_contacts){// 单位通讯录
			Intent mIntent = new Intent(activity,UserDetailsChildActivity.class);
			mIntent.putExtra("size", sizeAll);
			activity.startActivity(mIntent);
//			Intent mIntent = new Intent(activity,UserChooseActivity.class);
//			activity.startActivity(mIntent);
		}else if(arg0.getId() == R.id.re_discussion_groups){//讨论组

		}else if(arg0.getId() == R.id.btn_daiban_person){
			BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
		}else if(arg0.getId() == R.id.title_right_new_function){
			Intent mIntent = new Intent(activity,GeneralFunctionActivity.class);
			mIntent.putExtra("size", sizeAll);
			activity.startActivity(mIntent);
		}else if(arg0.getId() == R.id.bit_back){
			if(BookInit.getInstance().getBookType().equals(Constant.HOME_INIT)){
				bit_back.setVisibility(View.GONE);
				btn_daiban_person.setVisibility(View.VISIBLE);
			}
			BookInit.getInstance().setBookType(Constant.LOING_INIT);
			getActivity().finish();
		}

//		switch (arg0.getId()) {
//		case R.id.re_function:// 功能号
//			break;
//		case R.id.re_unit_contacts:// 单位通讯录
////			mAddressListener.onClickChild(new UserDetailsChildFragment());
//			Intent mIntent = new Intent(getActivity(),UserDetailsChildActivity.class);
//			mIntent.putExtra("size", sizeAll);
//			getActivity().startActivity(mIntent);
//			break;
//		case R.id.re_discussion_groups:// 讨论组
//			break;
	}
	public void isClick(boolean flag){
		mSearchInput.setEnabled(flag);
	}
	public void callBoradcastReceiver(){
		if(getActivity() != null && BookInit.getInstance().getIsBoradCast()) {
			getContactList();
		}
	}
	public List<T_UserRelationship> getContactlist(){
		return contactList;
	}
}
