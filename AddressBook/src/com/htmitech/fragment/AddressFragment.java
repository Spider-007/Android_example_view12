package com.htmitech.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.htmitech.dao.SYS_DepartmentDAO;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.dao.T_UserRelationshipDAO;
import com.htmitech.dao.ViewSYS_OrgUserDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.domain.T_UserRelationship;
import com.htmitech.listener.CallBackUserCount;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.unit.ActivityUnit;
import com.htmitech.unit.ImageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人列表页
 */
public class AddressFragment extends BaseFragment implements
        Sidebar.OnTouchingLetterChangedListener, TextWatcher, OnClickListener, CallBackUserCount {
    private ContactAdapter adapter;
    private List<T_UserRelationship> contactList;
    private ListView listView;
    private boolean hidden;
    private com.htmitech.app.widget.Sidebar sidebar;
    private TextView tv_total;
    private LayoutInflater infalter;
    private TextView mDialog;
    private CharacterParser characterParser;// 汉字转拼音
    private ArrayList<SYS_User> mSYSUser = null;
    private EditText mSearchInput;
    private ListView search_list; // 搜索的ListView
    private FrameLayout fragment_bottom;
    private int sizeAll;
    private RelativeLayout re_function, re_unit_contacts, re_discussion_groups;
    private ProgressBar progress;
    private Activity activity = null;
    private ImageView btn_daiban_person;
    private ImageButton title_right_new_function;
    private ImageView bit_back;
    private FrameLayout frame_search;
    private ProgressBar progress_search;
    private int isFunction;//是否支持"功能号”入口。 0，不支持；1，支持
    private int isContact;//是否支持常用联系人
    private boolean isHome;
    private TextView daibantopTabIndicator_bbslist;
    private String appName;
    private String com_addressbook_mobileconfig_corporg_scope;

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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        // TODO Auto-generated method stub
        isContact = bundle.getInt("isContact");
        isFunction = bundle.getInt("isFunction");
        appName = bundle.getString("appName");
        com_addressbook_mobileconfig_corporg_scope = bundle.getString("com_addressbook_mobileconfig_corporg_scope");
        isHome = bundle.getBoolean("isHome");
        LinearLayout root = (LinearLayout) getView().findViewById(R.id.ll_child_layout);
        if (Constant.IS_WATER_BACKGROUND.equals("1")) {
            root.setBackground(ImageUtil.drawTextToBitmap(getActivity(), BookInit.getInstance().getCurrentUserName()));
            root.getBackground().setAlpha((int) (0.5 * 255));
        }

        btn_daiban_person = (ImageView) getView().findViewById(R.id.btn_daiban_person);
        title_right_new_function = (ImageButton) getView().findViewById(R.id.title_right_new_function);
        progress = (ProgressBar) getActivity().findViewById(R.id.progress_);
        listView = (ListView) getView().findViewById(R.id.list);
        daibantopTabIndicator_bbslist = (TextView) getView().findViewById(R.id.daibantopTabIndicator_bbslist);
        frame_search = (FrameLayout) getView().findViewById(R.id.frame_search);
        progress_search = (ProgressBar) getView().findViewById(R.id.progress_search);
        characterParser = CharacterParser.getInstance();
        // 获取设置contactlist
        bit_back = (ImageView) getView().findViewById(R.id.bit_back);
        infalter = LayoutInflater.from(getActivity());
        mSearchInput = (EditText) getActivity().findViewById(
                R.id.school_friend_member_search_input);
        View headView = infalter.inflate(R.layout.ht_item_contact_list_header,
                null);
        listView.addHeaderView(headView);
        View footerView = infalter.inflate(R.layout.ht_item_contact_list_footer,
                null);
        listView.addFooterView(footerView, null, false);
        sidebar = (com.htmitech.app.widget.Sidebar) getActivity().findViewById(R.id.sidebar);
        mDialog = (TextView) getView().findViewById(R.id.floating_header);
        search_list = (ListView) getView().findViewById(R.id.search_list);
        fragment_bottom = (FrameLayout) getView().findViewById(
                R.id.fragment_bottom);
        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        re_function = (RelativeLayout) headView.findViewById(R.id.re_function);// 功能号
        if (isFunction == 1) {
            re_function.setVisibility(View.VISIBLE);
        } else {
            re_function.setVisibility(View.GONE);
        }
        daibantopTabIndicator_bbslist.setText("" + appName);
        re_unit_contacts = (RelativeLayout) headView
                .findViewById(R.id.re_unit_contacts);// 单位通讯录
        re_discussion_groups = (RelativeLayout) headView
                .findViewById(R.id.re_discussion_groups);// 讨论组

    }


    public SYS_UserDAO mSYS_UserDAO;
    public ViewSYS_OrgUserDAO mUserDao;

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        mSYS_UserDAO = new SYS_UserDAO(getActivity(), BaseApplication.getApplication(getActivity()));

        mUserDao = new ViewSYS_OrgUserDAO(getActivity());
        contactList = new ArrayList<T_UserRelationship>();
        sidebar.setTextView(mDialog);
        sidebar.setOnTouchingLetterChangedListener(this);
        adapter = new ContactAdapter(getActivity(), R.layout.ht_item_contact_list,
                contactList, this);
        listView.setSelected(true);
        listView.setAdapter(adapter);
        tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
        btn_daiban_person.setOnClickListener(this);
        title_right_new_function.setOnClickListener(this);
        re_function.setOnClickListener(this);
        re_unit_contacts.setOnClickListener(this);
        bit_back.setOnClickListener(this);
        re_discussion_groups.setOnClickListener(this);
        mSearchInput.addTextChangedListener(this);
        progress.setVisibility(View.VISIBLE);
        if (!BookInit.getInstance().getIsBoradCast()) { // 只有等于false的时候进行旋转
            isClick(false);
        }
        if (BookInit.getInstance().getBookType().equals(Constant.HOME_INIT)) {
            bit_back.setVisibility(View.VISIBLE);
            btn_daiban_person.setVisibility(View.GONE);
        }
//		getContactList();
        callBoradcastReceiver();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            getContactList();
//			refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            getContactList();
//			refresh();
        }
    }

    // 刷新ui
    public void refresh() {
        try {
            // 可能会在子线程中调到这方
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
//					getContactList();
                    if (BookInit.getInstance().getIsBoradCast()) {
                        progress.setVisibility(View.GONE);
                        isClick(true);
                    }
                    if (isContact == 0) {
                        return;
                    }
                    adapter.setData(contactList);
                    tv_total.setText(String.valueOf(contactList.size())
                            + "位联系人");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                try {
                    if (BookInit.getInstance().getIndexCurrentUser() == null) {
                        SYS_UserDAO mUserDAO = new SYS_UserDAO(getActivity());
                        SYS_User current = mUserDAO.findUserIdSYS_User(BookInit.getInstance().getCrrentUserId());
                        BookInit.getInstance().setIndexCurrentUser(current);
                    }
                    SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(AddressFragment.this.getActivity());
                    if (Constant.ROOTNODE_STRINGID.equals("")) {
                        Constant.ROOTNODE_STRINGID = mSYS_DepartmentDAO.departmentCode(BookInit.getInstance().getPortalId());
                    }
                    Constant.GROUP_TREE_CODE = mSYS_DepartmentDAO.departmentTreeCode(Constant.ROOTNODE_STRINGID);
                    contactList = mT_UserRelationshipDAO.getContactList();
                    // 对list进行排序
                    Collections.sort(contactList, new PinyinComparator() {
                    });
                    BaseApplication.getApplication(getActivity()).setContext(contactList);
                    refresh();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void callUserCount(int count) {
        tv_total.setText(String.valueOf(count)
                + "位联系人");
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

    public static AddressFragment newInstance() {

        Bundle args = new Bundle();

        AddressFragment fragment = new AddressFragment();
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
                            if(!TextUtils.isEmpty(Constant.ROOTNODE_STRINGID)){
                                com_addressbook_mobileconfig_corporg_scope = "";
                            }
                            filterDateList = mUserDao.findIdByUser(Constant.GROUP_TREE_CODE, filterStr.toString(), true, com_addressbook_mobileconfig_corporg_scope);
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
        if (BookInit.getInstance().getBookType().equals(Constant.HOME_INIT)) {
            bit_back.setVisibility(View.GONE);
            btn_daiban_person.setVisibility(View.VISIBLE);
        }
        getActivity().finish();
        return false;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        if (arg0.getId() == R.id.re_function) {// 功能号
            BookInit.getInstance().getmCallbackMX().callFunction();
        } else if (arg0.getId() == R.id.re_unit_contacts) {// 单位通讯录
//			Intent mIntent = new Intent(activity,UserDetailsChildActivity.class);
//			mIntent.putExtra("size", sizeAll);
//			activity.startActivity(mIntent);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("size", sizeAll);
            map.put("appName", "单位通讯录");
            map.put("com_addressbook_mobileconfig_corporg_scope", com_addressbook_mobileconfig_corporg_scope);
            ActivityUnit.switchTo(activity, UserDetailsChildActivity.class, map);
//			Intent mIntent = new Intent(activity,UserChooseActivity.class);
//			activity.startActivity(mIntent);
        } else if (arg0.getId() == R.id.re_discussion_groups) {//讨论组

        } else if (arg0.getId() == R.id.btn_daiban_person) {
            BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
        } else if (arg0.getId() == R.id.title_right_new_function) {
            Intent mIntent = new Intent(activity, GeneralFunctionActivity.class);
            mIntent.putExtra("size", sizeAll);
            activity.startActivity(mIntent);
        } else if (arg0.getId() == R.id.bit_back) {
            if (BookInit.getInstance().getBookType().equals(Constant.HOME_INIT)) {
                bit_back.setVisibility(View.GONE);
                btn_daiban_person.setVisibility(View.VISIBLE);
            }
            BookInit.getInstance().setBookType(Constant.LOING_INIT);
            getActivity().finish();
        }

    }

    public void isClick(boolean flag) {
        re_unit_contacts.setEnabled(flag);
        mSearchInput.setEnabled(flag);
    }

    public void callBoradcastReceiver() {
        if (getActivity() != null && BookInit.getInstance().getIsBoradCast()) {
            getContactList();
        }
    }

    public void deleteUserLocal(String userId) {
        T_UserRelationshipDAO mT_UserRelationshipDAO = new T_UserRelationshipDAO(getActivity());
        mT_UserRelationshipDAO.deleteUser(userId);
        getContactList();
    }

}
