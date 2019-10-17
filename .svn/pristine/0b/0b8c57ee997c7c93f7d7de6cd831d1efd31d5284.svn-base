package com.htmitech.addressbook;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.adapter.UserDetailChildAdapter;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.fragment.GeneralFunctionFragment;
import com.htmitech.fragment.UserDetailsChildFragment;
import com.htmitech.listener.BackHandledInterface;
import com.htmitech.myApplication.BaseApplication;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通用功能 快速查找
 * 
 * @author Tony
 * 
 */
public class GeneralFunctionSearchActivity extends BaseFragmentActivity implements TextWatcher,View.OnClickListener {
	private EditText school_friend_member_search_input;
	private List<SYS_User> userList;
	private ListView general_search_list;
	private TextView general_is_exit;
	private CharacterParser characterParser;// 汉字转拼音
	public ImageView btn_daiban_person;
	private ProgressBar progress_search;
	private SYS_UserDAO mSYS_UserDAO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ht_activity_general_search);
		initContent();
	}

	/** 初始化显示内容 **/
	private void initContent() {
		mSYS_UserDAO = new SYS_UserDAO(GeneralFunctionSearchActivity.this, BaseApplication.getApplication(GeneralFunctionSearchActivity.this));
		btn_daiban_person = (ImageView)findViewById(R.id.btn_daiban_person);
		school_friend_member_search_input = (EditText)findViewById(R.id.school_friend_member_search_input);
		general_is_exit = (TextView)findViewById(R.id.general_is_exit);
		general_search_list = (ListView)findViewById(R.id.general_search_list);
		progress_search = (ProgressBar)findViewById(R.id.progress_search);
		userList = BaseApplication.getApplication(this).getUserList();
		characterParser = CharacterParser.getInstance();
		school_friend_member_search_input.addTextChangedListener(this);
		btn_daiban_person.setOnClickListener(this);
		school_friend_member_search_input.setFocusableInTouchMode(true);

	}

	@Override
	public void onBackPressed() {
		this.finish();
	}



	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		filterUser(s.toString());
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
	private List<SYS_User> filterDateList;
	// 处理首页面的搜索方法
	private void filterUser(final String filterStr) {
		if (TextUtils.isEmpty(filterStr)) {
			general_search_list.setVisibility(View.GONE);
		} else {
			general_search_list.setVisibility(View.VISIBLE);
			progress_search.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (this) {
						try {
							filterDateList = mSYS_UserDAO.findIdByUser(filterStr.toString());

							GeneralFunctionSearchActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									SYS_Department mSYS_Department = new SYS_Department();
									mSYS_Department.setDepartmentCode(Constant.ROOTNODE_STRINGID);
									mSYS_Department.setFullName(Constant.ROOTNODEORGANISE_STRING);
									mSYS_Department.setSYS_User(new ArrayList<SYS_User>(filterDateList));
									UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
											mSYS_Department, null, true, GeneralFunctionSearchActivity.this);
									general_search_list.setAdapter(mUserDetailChildAdapter);
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
	public void onClick(View view) {
		if(view.getId() == R.id.btn_daiban_person){
			finish();
		}
	}
}
