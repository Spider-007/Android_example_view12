package com.htmitech.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.adapter.UserHasChooseAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.listener.CallBackUserCount;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.others.LoadUserAvatar;

import java.util.ArrayList;

/**
 * 已选择人员的展示页面
 * @author Tony
 * 
 */
public class ChooseShowPeopleMessageFragment extends BaseFragment implements View.OnClickListener,CallBackUserCount {
	private CallBackChoosePeople mCallBackChoosePeople;
	private ListView lv_choose;
	private UserHasChooseAdapter mUserHasChooseAdapter;
	private ArrayList<SYS_Department> departmentsList;
	private ArrayList<SYS_User> userList;
	private ImageView iv_choose;
	private RelativeLayout ll_layout;
	private TextView cleanAll; // 清空所有选择
	private ChooseWayEnum mChooseWayEnum;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.ht_fragment_choose_pop, container, false);
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
		Bundle args = this.getArguments();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		lv_choose = (ListView) getView().findViewById(R.id.lv_choose);
		iv_choose = (ImageView) getView().findViewById(R.id.iv_choose);
		ll_layout = (RelativeLayout) getView().findViewById(R.id.ll_layout);
		cleanAll = (TextView) getView().findViewById(R.id.tv_clean_all);
		ll_layout.getBackground().setAlpha(100);
	}
	public void setAlpha(int value){
		ll_layout.setBackgroundColor(value);
		ll_layout.getBackground().setAlpha(100);
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		iv_choose.setOnClickListener(this);
		cleanAll.setOnClickListener(this);
		mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();


		userList = BaseApplication.getApplication(getActivity()).getCheckALlUser();
		departmentsList = BaseApplication.getApplication(getActivity()).getCheckSYSDepartment();


		mUserHasChooseAdapter = new UserHasChooseAdapter(getActivity(),userList,departmentsList,this);
		lv_choose.setAdapter(mUserHasChooseAdapter);
	}
	public void setAdapter(ArrayList<SYS_Department> departmentsList ,ArrayList<SYS_User> userList){
		this.userList = userList;
		this.departmentsList = departmentsList;
		mUserHasChooseAdapter.setData(userList,departmentsList);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.iv_choose){
			mCallBackChoosePeople.setShutDownAnimation();
		}else if(v.getId() == R.id.tv_clean_all){
			if(userList != null){
				for(SYS_User mSYS_User : userList){
					mSYS_User.setIsCheck(false);
					mSYS_User.mCheckBox.setChecked(false);
					if (mSYS_User.node != null){
						mSYS_User.node.setNumber(-1);
						mSYS_User.node.getCheckNumber();
					}
					/**
					 * 人员选择的时候 底部宣布删除的时候 也要对应删除常用联系人和组织机构中的人员
					 *
					 */
					switch (mChooseWayEnum){
						case PEOPLECHOOSE:   //人员选择 并且
							BaseApplication.getApplication(getActivity()).setCheck(3, mSYS_User);
							break;
					}
				}
				userList.clear();
			}

			for(SYS_Department mSYS_Department : departmentsList){
				mSYS_Department.setIsCheck(false);
				mSYS_Department.mCheckBox.setChecked(false);
//				mSYS_Department.callUserCount(arrDepartment.size());
			}
			departmentsList.clear();

			callUserCount(0);
		}
	}

	@Override
	public void callUserCount(int count) {
		mCallBackChoosePeople.callBackDeletePeople(count);
	}
}
