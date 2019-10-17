package com.htmitech.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.adapter.UserDetailChildAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.SYS_DepartmentDAO;
import com.htmitech.dao.SYS_OrgUserDAO;
import com.htmitech.dao.SYS_UserDAO;
import com.htmitech.dao.ViewSYS_OrgUserDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.unit.ImageUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * 通讯录二级fragment
 * 修改成动态数据库查询
 * @author Tony
 */
public class UpdateUserDetailsChildFragment extends BaseFragment implements
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
    private SYS_DepartmentDAO mSYS_DepartmentDAO;
    private SYS_OrgUserDAO mSYS_OrgUserDAO ;
    private ViewSYS_OrgUserDAO mUserDao;
    private LinearLayout ll_no_neirong;
    private TextView tv_no_neirong;
    private TextView daibantopTabIndicator_bbslist;
    private int isContact = -1;
    private int isFunction = -1;
    private ImageView bit_back;
    private boolean isHome;
    private String appName;
    private int indexNumber = 0;
    private String com_addressbook_mobileconfig_corporg_scope;

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
        Bundle args = this.getArguments();
        isContact = args.getInt("isContact",-1);
        isFunction = args.getInt("isFunction",-1);
        appName = args.getString("appName");
        com_addressbook_mobileconfig_corporg_scope = args.getString("com_addressbook_mobileconfig_corporg_scope");
        isHome = args.getBoolean("isHome",false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        FrameLayout root = (FrameLayout)getView().findViewById(R.id.content);
        if(Constant.IS_WATER_BACKGROUND.equals("1")){
            root.setBackground(ImageUtil.drawTextToBitmap(getActivity(), BookInit.getInstance().getCurrentUserName()));
            root.getBackground().setAlpha((int)(0.5 * 255));
        }


        iv_no_book = (ImageView)getView().findViewById(R.id.iv_no_book);
        btn_daiban_person = (ImageView) getView().findViewById(R.id.btn_daiban_person);
        bit_back = (ImageView) getView().findViewById(R.id.bit_back);
        title_right_new_function = (ImageButton) getView().findViewById(R.id.title_right_new_function);
        upSYS_DepartmentList = new ArrayList<SYS_Department>();
        progress_ = (ProgressBar)getView().findViewById(R.id.progress_);
        ll_no_neirong = (LinearLayout) getView().findViewById(R.id.ll_no_neirong);
        tv_no_neirong = (TextView) getView().findViewById(R.id.tv_no_neirong);
        daibantopTabIndicator_bbslist = (TextView) getView().findViewById(R.id.daibantopTabIndicator_bbslist);
        pro_count = (TextView) getView().findViewById(R.id.pro_count);
        pro_all = (TextView) getView().findViewById(R.id.pro_all);
        pro_frame = (FrameLayout) getView().findViewById(R.id.pro_frame);
        child_list = (ListView) getView().findViewById(R.id.child_list);
        if(Constant.IS_WATER_BACKGROUND.equals("1")){
            child_list.setBackground(ImageUtil.drawTextToBitmap(getActivity(), BookInit.getInstance().getCurrentUserName()));
            child_list.getBackground().setAlpha((int)(0.5 * 255));
        }
        daibantopTabIndicator_bbslist.setText(""+appName);
        tv_child_name = (TextView) getView().findViewById(R.id.tv_child_name);
        child_search_input = (EditText) getView().findViewById(
                R.id.child_search_input);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        if(isHome){
            title_right_new_function.setVisibility(View.GONE);
            btn_daiban_person.setVisibility(View.VISIBLE);
            bit_back.setVisibility(View.GONE);
//            btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_community_list_phone);
        }else{
            if(!TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype) && Constant.com_addressbook_mobileconfig_viewtype.equals("1") && !TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype1_level) && Constant.com_addressbook_mobileconfig_viewtype1_level.equals("0")){
                title_right_new_function.setVisibility(View.GONE);
            }else{
                title_right_new_function.setVisibility(View.VISIBLE);
            }
            bit_back.setVisibility(View.VISIBLE);
            btn_daiban_person.setVisibility(View.GONE);
//            btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_back);
        }
        mSYS_OrgUserDAO = new SYS_OrgUserDAO(getActivity());
        mUserDao = new ViewSYS_OrgUserDAO(getActivity());
        myApp = BaseApplication.getApplication(getActivity());
        child_search_input.addTextChangedListener(this);
        btn_daiban_person.setOnClickListener(this);
        title_right_new_function.setOnClickListener(this);
        bit_back.setOnClickListener(this);
        characterParser = CharacterParser.getInstance();
        currentSYS_Department = new SYS_Department();
//        if (currentSYS_Department == null) {
        mSYS_DepartmentDAO = new SYS_DepartmentDAO(getActivity(), myApp);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    if(!TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype) && Constant.com_addressbook_mobileconfig_viewtype.equals("1") && !TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype1_level) && Constant.com_addressbook_mobileconfig_viewtype1_level.equals("0")){
                        if(Constant.ROOTNODE_STRINGID.equals("") && TextUtils.isEmpty(com_addressbook_mobileconfig_corporg_scope)){//Constant.ROOTNODE_STRINGID 这个值是com_addressbook_mobileconfig_dataroot_orgid配置
                            Constant.ROOTNODE_STRINGID =  mSYS_DepartmentDAO.departmentCode(BookInit.getInstance().getPortalId());
                        }
                        ArrayList<SYS_User> arraylistUser = mUserDao.findIdByUser(Constant.GROUP_TREE_CODE, "",false,"");
                        SYS_Department mSYS_Department = new SYS_Department();
                        mSYS_Department.setDepartmentCode(Constant.ROOTNODE_STRINGID);
                        mSYS_Department.setTreeCode(Constant.GROUP_TREE_CODE);
                        mSYS_Department.setFullName(Constant.ROOTNODEORGANISE_STRING);
                        mSYS_Department.setSYS_User(new ArrayList<SYS_User>(arraylistUser));
                        currentSYS_Department = mSYS_Department;
                    }else{
                        if(!TextUtils.isEmpty(Constant.ROOTNODE_STRINGID)){
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateDepartments(Constant.ROOTNODE_STRINGID, new SYS_Department());
                        }else if("1".equals(com_addressbook_mobileconfig_corporg_scope) || "2".equals(com_addressbook_mobileconfig_corporg_scope)){
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateUnitsRoot( PreferenceUtils.getEMPUserID(getActivity()),new SYS_Department());
                        }else if("3".equals(com_addressbook_mobileconfig_corporg_scope) || "4".equals(com_addressbook_mobileconfig_corporg_scope)){
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateDepartmentsRoot( PreferenceUtils.getEMPUserID(getActivity()),new SYS_Department());
                        }else{
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateDepartments(Constant.ROOTNODE_STRINGID, new SYS_Department());
                        }
                        SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(getActivity());
                        if(null != currentSYS_Department){
                            currentSYS_Department.setTreeCode(Constant.GROUP_TREE_CODE);
                        }
                        ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_UserDAO.getSYSUer(Constant.ROOTNODE_STRINGID, currentSYS_Department);
                        if(currentSYS_Department != null)
                            currentSYS_Department.setSYS_User(arraylistUser);

                    }

                    UpdateUserDetailsChildFragment.this.onComplete();
//								.getSYS_Department(UserDetailsChildFragment.this);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
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
        indexNumber--;
        // 当输入框有值的时候
        if (!TextUtils.isEmpty(child_search_input.getText().toString())) {
            child_search_input.setText("");
            return true;
        }
        if (upSYS_DepartmentList.size() > 0) {
            // 判断展示title
            if (upSYS_DepartmentList.size() == 1) {
                tv_child_name.setVisibility(View.GONE);
                title_right_new_function.setVisibility(View.GONE);
                if(isHome){
                    btn_daiban_person.setVisibility(View.VISIBLE);
                    bit_back.setVisibility(View.GONE);
//                    btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_community_list_phone);
                }
            } else {
                btn_daiban_person.setVisibility(View.GONE);
                bit_back.setVisibility(View.VISIBLE);
//                btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_back);
                tv_child_name.setText(""
                        + upSYS_DepartmentList.get(
                        upSYS_DepartmentList.size() - 1).getFullName());
                tv_child_name.setVisibility(View.VISIBLE);
            }
            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
            ll_no_neirong.setVisibility(View.GONE);
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    upSYS_DepartmentList.get(upSYS_DepartmentList.size() - 1),
                    this, false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
            currentSYS_Department = upSYS_DepartmentList
                    .get(upSYS_DepartmentList.size() - 1); // 返回的时候把上一级
            // 也就是当前的Department拿到
//            addUserListCache(currentSYS_Department);
            upSYS_DepartmentList.remove(upSYS_DepartmentList.size() - 1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void notifyDataSetChanged(SYS_Department upSYS_Department,
                                     final SYS_Department mSYS_Department) {
        tv_child_name.setText("" + mSYS_Department.getFullName());
        tv_child_name.setVisibility(View.VISIBLE);
        upSYS_DepartmentList.add(upSYS_Department);
        if(upSYS_DepartmentList.size() > 0 && isContact == 0){
            title_right_new_function.setVisibility(View.VISIBLE);
//            btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_back);
            btn_daiban_person.setVisibility(View.GONE);
            bit_back.setVisibility(View.VISIBLE);
        }
        indexNumber++;
        progress_.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {

                    if(!TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype) && Constant.com_addressbook_mobileconfig_viewtype.equals("1") && !TextUtils.isEmpty(Constant.com_addressbook_mobileconfig_viewtype1_level) && (indexNumber+"").equals(Constant.com_addressbook_mobileconfig_viewtype1_level)){
                        ArrayList<SYS_User> arraylistUser = mUserDao.findIdByUser(mSYS_Department.getTreeCode(), "",false,"");
                        mSYS_Department.setSYS_User(arraylistUser);
                    }else{
//                    SYS_OrgUserDAO mSYS_OrgUserDAO = new SYS_OrgUserDAO(getActivity());
//                    ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_OrgUserDAO.findPartmentIdOrgUser(mSYS_Department.getDepartmentCode(), mSYS_Department);
                        if("2".equals(com_addressbook_mobileconfig_corporg_scope)){
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateUnitsRoot2(mSYS_Department.getDepartmentCode(), mSYS_Department);
                        }else if("4".equals(com_addressbook_mobileconfig_corporg_scope)){

                        }else{
                            currentSYS_Department = mSYS_DepartmentDAO.getUpdateDepartments(mSYS_Department.getDepartmentCode(), mSYS_Department);
                        }
                        SYS_UserDAO mSYS_UserDAO = new SYS_UserDAO(getActivity());
                        ArrayList<SYS_User> arraylistUser = (ArrayList<SYS_User>) mSYS_UserDAO.getSYSUer(mSYS_Department.getDepartmentCode(), mSYS_Department);

                        mSYS_Department.setSYS_User(arraylistUser);

                    }
                    currentSYS_Department = mSYS_Department;
                    UpdateUserDetailsChildFragment.this.onComplete();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 缓存方法
//    public void addUserListCache(SYS_Department currentSYS_Department) {
//        if (currentDepartmentAllUserListCache.get(currentSYS_Department
//                .getDepartmentCode()) == null) {
//            getAllUser(currentSYS_Department);// 获取当前目录下的所有User
//            currentDepartmentAllUserListCache.put(
//                    currentSYS_Department.getDepartmentCode(), userAllList);
//        } else {
//            userAllList = currentDepartmentAllUserListCache
//                    .get(currentSYS_Department.getDepartmentCode());
//        }
//    }

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
            ll_no_neirong.setVisibility(View.GONE);
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    currentSYS_Department, this, false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
        } else {
            addUserListCache(currentSYS_Department,filterStr);

        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_daiban_person) {
            setBack();

        } else if (view.getId() == R.id.title_right_new_function) {
//            BookInit.getInstance().getmCallbackMX().callAddBook();

            if(isHome){
                ll_no_neirong.setVisibility(View.GONE);
                tv_child_name.setVisibility(View.GONE);
                title_right_new_function.setVisibility(View.GONE);
//                btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_community_list_phone);
                btn_daiban_person.setVisibility(View.VISIBLE);
                bit_back.setVisibility(View.GONE);
                if(upSYS_DepartmentList.size() > 0){
                    UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                            upSYS_DepartmentList.get(0),
                            this, false, getActivity());
                    child_list.setAdapter(mUserDetailChildAdapter);
                    currentSYS_Department = upSYS_DepartmentList
                            .get(0);
                    upSYS_DepartmentList.clear();
                }

            }else{
                getActivity().finish();
            }


        }else if(view.getId() == R.id.bit_back){
            setBack();
        }
    }

    public void setBack(){
        indexNumber--;
        if (!TextUtils.isEmpty(child_search_input.getText().toString())) {
            child_search_input.setText("");
        }
        if (upSYS_DepartmentList.size() > 0) {
            // 判断展示title
            if (upSYS_DepartmentList.size() == 1) {
                if(isHome)
                    title_right_new_function.setVisibility(View.GONE);
                tv_child_name.setVisibility(View.GONE);
                if(isHome){
//                        btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_community_list_phone);
                    btn_daiban_person.setVisibility(View.VISIBLE);
                    bit_back.setVisibility(View.GONE);
                }
            } else {
                title_right_new_function.setVisibility(View.VISIBLE);
//                    btn_daiban_person.setBackgroundResource(R.drawable.mx_btn_back);
                btn_daiban_person.setVisibility(View.GONE);
                bit_back.setVisibility(View.VISIBLE);
                tv_child_name.setText(""
                        + upSYS_DepartmentList.get(
                        upSYS_DepartmentList.size() - 1).getFullName());
                tv_child_name.setVisibility(View.VISIBLE);
            }
            ll_no_neirong.setVisibility(View.GONE);
            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                    upSYS_DepartmentList.get(upSYS_DepartmentList.size() - 1),
                    this, false, getActivity());
            child_list.setAdapter(mUserDetailChildAdapter);
            currentSYS_Department = upSYS_DepartmentList
                    .get(upSYS_DepartmentList.size() - 1); // 返回的时候把上一级
            // 也就是当前的Department拿到
//                addUserListCache(currentSYS_Department);
            upSYS_DepartmentList.remove(upSYS_DepartmentList.size() - 1);
        } else {
            if(!isHome){
                getActivity().finish();
            }else{
                if(isHome){
                    BookInit.getInstance().getmCallbackMX().callUserMeesageMain();
                }else{
                    mAddressListener.onClickChild(this);
                }
//                    if(isContact == 0){
//
//                    }else{
//
//                    }
            }


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
                tv_no_neirong.setVisibility(View.VISIBLE);
                progress_.setVisibility(View.GONE);
                if(currentSYS_Department == null || (currentSYS_Department.getSYS_DepartmentList().size() == 0 && currentSYS_Department.getSYS_User().size() == 0)){
                    ll_no_neirong.setVisibility(View.VISIBLE);

                    tv_no_neirong.setText("暂无记录");
                }else{
                    ll_no_neirong.setVisibility(View.GONE);
                }
                UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                        currentSYS_Department, UpdateUserDetailsChildFragment.this, false,
                        getActivity());
                child_list.setAdapter(mUserDetailChildAdapter);
//                addUserListCache(currentSYS_Department);
            }
        }));
    }

    /**
     * 获取当前目录下所有的User集合 方便搜索
     * @param currentSYS_Department
     */
    public synchronized void addUserListCache(final SYS_Department currentSYS_Department,final String likes){
        progress_.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(!TextUtils.isEmpty(Constant.ROOTNODE_STRINGID)){
                        com_addressbook_mobileconfig_corporg_scope = "";
                    }
                    userAllList = (ArrayList<SYS_User>) mUserDao.findIdByUser(currentSYS_Department.getTreeCode(), likes,true,com_addressbook_mobileconfig_corporg_scope);

                    Log.d("addUserListCache","------------->");
                    final SYS_Department mSYS_Department = new SYS_Department();
                    mSYS_Department.setDepartmentCode(currentSYS_Department
                            .getDepartmentCode());
                    mSYS_Department.setFullName(currentSYS_Department.getFullName());
                    mSYS_Department.setParentDepartment(currentSYS_Department
                            .getParentDepartment());
                    mSYS_Department
                            .setSYS_User(new ArrayList<SYS_User>(userAllList));

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progress_.setVisibility(View.GONE);
                            if(userAllList.size() == 0){
                                ll_no_neirong.setVisibility(View.VISIBLE);
                            }else{
                                ll_no_neirong.setVisibility(View.GONE);
                            }
                            UserDetailChildAdapter mUserDetailChildAdapter = new UserDetailChildAdapter(
                                    mSYS_Department, UpdateUserDetailsChildFragment.this, true, getActivity());
                            child_list.setAdapter(mUserDetailChildAdapter);
                        }
                    });
//                    getActivity().runOnUiThread((new Runnable() {
//                        @Override
//                        public void run() {
//                            progress_.setVisibility(View.GONE);
//
//                        }
//                    }));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
