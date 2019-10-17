package com.htmitech.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.adapter.UserDetailChildAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.AbCircleProgressBar;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.SYS_DepartmentDAO;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.AbOnProgressListener;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.listener.ObtainCurrentCount;
import com.htmitech.myApplication.BaseApplication;

/**
 * 通讯录二级fragment
 *
 * @author Tony
 */

public class UserDetailsChildFragment extends BaseFragment implements
        ChildFragmentListener, TextWatcher, View.OnClickListener {
    private ListView child_list;
    private AddressListener mAddressListener;
    private BaseApplication myApp;
    private ArrayList<SYS_Department> upSYS_DepartmentList;
    private TextView tv_child_name;
    private EditText child_search_input;
    private CharacterParser characterParser;// 汉字转拼音
    private SYS_Department currentSYS_Department;// 当前的department
    private Map<String, ArrayList<SYS_User>> currentDepartmentAllUserListCache = new HashMap<String, ArrayList<SYS_User>>();// 当前目录下所有User对应的k
    private ArrayList<SYS_User> userAllList; // 当前目录下的所有User集合
    private TextView pro_count, pro_all;
    private FrameLayout pro_frame;
    private Handler mHandler = new Handler();
    private ImageView btn_daiban_person;
    private ImageButton title_right_new_function;
    private ProgressBar progress_;
    private ImageView iv_no_book;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ht_fragment_child_contactlist, container,
                false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        iv_no_book = (ImageView)getView().findViewById(R.id.iv_no_book);
        btn_daiban_person = (ImageView) getView().findViewById(R.id.btn_daiban_person);
        title_right_new_function = (ImageButton) getView().findViewById(R.id.title_right_new_function);
        upSYS_DepartmentList = new ArrayList<SYS_Department>();
        progress_ = (ProgressBar)getView().findViewById(R.id.progress_);
        pro_count = (TextView) getView().findViewById(R.id.pro_count);
        pro_all = (TextView) getView().findViewById(R.id.pro_all);
        pro_frame = (FrameLayout) getView().findViewById(R.id.pro_frame);
        child_list = (ListView) getView().findViewById(R.id.child_list);
        tv_child_name = (TextView) getView().findViewById(R.id.tv_child_name);
        child_search_input = (EditText) getView().findViewById(
                R.id.child_search_input);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        myApp = BaseApplication.getApplication(getActivity());
        child_search_input.addTextChangedListener(this);
        btn_daiban_person.setOnClickListener(this);
        title_right_new_function.setOnClickListener(this);
        characterParser = CharacterParser.getInstance();
        currentSYS_Department = myApp.getSYS_Department_();
        userAllList = new ArrayList<SYS_User>();
        if (currentSYS_Department == null) {
            final SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(getActivity(), myApp);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        if(Constant.ROOTNODE_STRINGID.equals("")){
                            Constant.ROOTNODE_STRINGID =  mSYS_DepartmentDAO.departmentCode(BookInit.getInstance().getPortalId());
                        }
                        currentSYS_Department = mSYS_DepartmentDAO.getDepartments(Constant.ROOTNODE_STRINGID, new SYS_Department());
                        UserDetailsChildFragment.this.onComplete();
//								.getSYS_Department(UserDetailsChildFragment.this);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            progress_.setVisibility(View.GONE);
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    currentSYS_Department, UserDetailsChildFragment.this,
                    false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
            addUserListCache(currentSYS_Department);
        }
    }

    @Override
    public void onAttach(Activity activity) {/* 判断宿主activity是否实现了接口MyListener */
        super.onAttach(activity);
        try {
            mAddressListener = (AddressListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName()
                    + " must implements interface MyListener");
        }
    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        // 当输入框有值的时候
        if (!TextUtils.isEmpty(child_search_input.getText().toString())) {
            child_search_input.setText("");
            return true;
        }
        if (upSYS_DepartmentList.size() > 0) {
            // 判断展示title
            if (upSYS_DepartmentList.size() == 1) {
                tv_child_name.setVisibility(View.GONE);
            } else {
                tv_child_name.setText(""
                        + upSYS_DepartmentList.get(
                        upSYS_DepartmentList.size() - 1).getFullName());
                tv_child_name.setVisibility(View.VISIBLE);
            }
            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    upSYS_DepartmentList.get(upSYS_DepartmentList.size() - 1),
                    this, false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
            userAllList = new ArrayList<SYS_User>();
            currentSYS_Department = upSYS_DepartmentList
                    .get(upSYS_DepartmentList.size() - 1); // 返回的时候把上一级
            // 也就是当前的Department拿到
            addUserListCache(currentSYS_Department);
            upSYS_DepartmentList.remove(upSYS_DepartmentList.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void notifyDataSetChanged(SYS_Department upSYS_Department,
                                     SYS_Department mSYS_Department) {
        currentSYS_Department = mSYS_Department;
        tv_child_name.setText("" + mSYS_Department.getFullName());
        tv_child_name.setVisibility(View.VISIBLE);
        upSYS_DepartmentList.add(upSYS_Department);
        if(currentSYS_Department.getSYS_DepartmentList() == null || currentSYS_Department.getSYS_User() == null ||(currentSYS_Department.getSYS_DepartmentList().size()+currentSYS_Department.getSYS_User().size() == 0)){
            iv_no_book.setVisibility(View.VISIBLE);
            child_list.setVisibility(View.GONE);
        }else{
            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
        }
        UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                mSYS_Department, this, false, getActivity());
        child_list.setAdapter(mUserDetailChildAdapter);
        userAllList = new ArrayList<SYS_User>();
        addUserListCache(currentSYS_Department);

    }

    // 缓存方法
    public void addUserListCache(SYS_Department currentSYS_Department) {
        if (currentDepartmentAllUserListCache.get(currentSYS_Department
                .getDepartmentCode()) == null) {
            getAllUser(currentSYS_Department);// 获取当前目录下的所有User
            currentDepartmentAllUserListCache.put(
                    currentSYS_Department.getDepartmentCode(), userAllList);
        } else {
            userAllList = currentDepartmentAllUserListCache
                    .get(currentSYS_Department.getDepartmentCode());
        }
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
        searchUser(arg0.toString(), userAllList);
    }

    // 处理首页面的搜索方法
    private void searchUser(final String filterStr,final List<SYS_User> list) {
        final List<SYS_User> filterDateList = new ArrayList<SYS_User>();

        if (TextUtils.isEmpty(filterStr)) {
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    currentSYS_Department, this, false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
        } else {
            filterDateList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (SYS_User sortModel : list) {
                        String name = sortModel.getFullName();
                        String suoxie = sortModel.getSuoxie();
                        String phone = sortModel.getMobile();
                        if (name.indexOf(filterStr.toString()) != -1
                                || suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
                                || characterParser.getSelling(name).startsWith(
                                filterStr.toString())) {
                            filterDateList.add(sortModel);
                        } else if (phone.contains(filterStr)) {
                            filterDateList.add(sortModel);
                        }
                    }
                    Collections.sort(filterDateList, new PinyinComparators() {
                    });

                    final SYS_Department mSYS_Department = new SYS_Department();
                    mSYS_Department.setDepartmentCode(currentSYS_Department
                            .getDepartmentCode());
                    mSYS_Department.setFullName(currentSYS_Department.getFullName());
                    mSYS_Department.setParentDepartment(currentSYS_Department
                            .getParentDepartment());
                    mSYS_Department
                            .setSYS_User(new ArrayList<SYS_User>(filterDateList));

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                                    mSYS_Department, UserDetailsChildFragment.this, true, getActivity());
                            child_list.setAdapter(mUserDetailChildAdapter);
                        }
                    });
                }
            }).start();


        }
    }

    // 查询该集目录下的所有User
    public void getAllUser(SYS_Department currentSYS_Department) {
        userAllList.addAll(currentSYS_Department.getSYS_User());
        Iterator in = currentSYS_Department.getSYS_DepartmentList().iterator();
        while (in.hasNext()) {
            SYS_Department supSYS_Department = (SYS_Department) in.next();
            getAllUser(supSYS_Department);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_daiban_person) {
            if (!TextUtils.isEmpty(child_search_input.getText().toString())) {
                child_search_input.setText("");
            }
            if (upSYS_DepartmentList.size() > 0) {
                // 判断展示title
                if (upSYS_DepartmentList.size() == 1) {
                    tv_child_name.setVisibility(View.GONE);
                } else {
                    tv_child_name.setText(""
                            + upSYS_DepartmentList.get(
                            upSYS_DepartmentList.size() - 1).getFullName());
                    tv_child_name.setVisibility(View.VISIBLE);
                }
                iv_no_book.setVisibility(View.GONE);
                child_list.setVisibility(View.VISIBLE);
                UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                        upSYS_DepartmentList.get(upSYS_DepartmentList.size() - 1),
                        this, false, getActivity());
                child_list.setAdapter(mUserDetailChildAdapter);
                userAllList = new ArrayList<SYS_User>();
                currentSYS_Department = upSYS_DepartmentList
                        .get(upSYS_DepartmentList.size() - 1); // 返回的时候把上一级
                // 也就是当前的Department拿到
                addUserListCache(currentSYS_Department);
                upSYS_DepartmentList.remove(upSYS_DepartmentList.size() - 1);
            } else {
                mAddressListener.onClickChild(this);
            }


        } else if (view.getId() == R.id.title_right_new_function) {
            BookInit.getInstance().getmCallbackMX().callAddBook();
        }
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

    //加载完成
    public void onComplete() {
        // TODO Auto-generated method stub
        if(getActivity() == null){
            return;
        }
        getActivity().runOnUiThread((new Runnable() {
            @Override
            public void run() {
                progress_.setVisibility(View.GONE);
                UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                        currentSYS_Department, UserDetailsChildFragment.this, false,
                        getActivity());
                child_list.setAdapter(mUserDetailChildAdapter);
                addUserListCache(currentSYS_Department);
            }
        }));
    }

}
