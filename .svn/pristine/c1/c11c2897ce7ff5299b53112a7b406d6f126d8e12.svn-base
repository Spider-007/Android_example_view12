package com.htmitech.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.adapter.UserDetailChooseChildAdapter;
import com.htmitech.addressbook.ChooseGeneralActivity;
import com.htmitech.addressbook.R;
import com.htmitech.api.BookInit;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.Constant;
import com.htmitech.base.BaseFragment;
import com.htmitech.dao.SYS_DepartmentDAO;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myApplication.BaseApplication;
import com.htmitech.myEnum.CheckStatus;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.unit.DensityUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 搜索  层级结构
 *
 * @author Tony
 */
public class UserChooseChildFragment extends BaseFragment implements
        ChildFragmentListener, TextWatcher, View.OnClickListener,CallCheckUserListener {
    private ListView child_list;
    private CallBackChoosePeople mCallBackChoosePeople;
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
    private ChooseWayEnum mChooseWayEnum;
    private int itemHight;

    private LinearLayout ll_no_neirong;
    private TextView tv_no_neirong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ht_fragment_choose_contactlist, container,
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
        itemHight = DensityUtil.dip2px(getActivity(),50);
        mChooseWayEnum = BookInit.getInstance().getChooseWayEnum();
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
        ll_no_neirong = (LinearLayout) getView().findViewById(R.id.ll_no_neirong);
        tv_no_neirong = (TextView)getView().findViewById(R.id.tv_no_neirong);
    }
    public void copyDepartment(SYS_Department parentSYS_Department) {
        if(parentSYS_Department == null){
            return;
        }
        SYS_Department mNewSYS_Department = new SYS_Department();
        List<SYS_Department> SYS_DepartmentList = new ArrayList<SYS_Department>();
        SYS_DepartmentList.addAll(parentSYS_Department.getSYS_DepartmentList());
        List<SYS_User> SYS_User = new ArrayList<SYS_User>();
        SYS_User.addAll(parentSYS_Department.getSYS_User());
        String code = new String(parentSYS_Department.getDepartmentCode());
        String ParentDepartment = new String(
                parentSYS_Department.getParentDepartment());
        String ShortName = new String(parentSYS_Department.getShortName());
        String FullName = new String(parentSYS_Department.getFullName());
        mNewSYS_Department.setDepartmentCode(code);
        mNewSYS_Department.setParentDepartment(ParentDepartment);
        mNewSYS_Department.setShortName(ShortName);
        mNewSYS_Department.setFullName(FullName);
        mNewSYS_Department.setSYS_User(SYS_User);
        mNewSYS_Department.setSYS_DepartmentList(SYS_DepartmentList);
        currentSYS_Department = mNewSYS_Department;
    }
    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        progress_.setVisibility(View.GONE);
        myApp = BaseApplication.getApplication(getActivity());
        child_search_input.addTextChangedListener(this);
        btn_daiban_person.setOnClickListener(this);
        title_right_new_function.setOnClickListener(this);
        characterParser = CharacterParser.getInstance();
//        copyDepartment(myApp.getSYS_Department_());
        userAllList = new ArrayList<SYS_User>();
        departmentAll = new ArrayList<SYS_Department>();
//        if (currentSYS_Department == null) {

        switch (mChooseWayEnum){
            case DEPARTMENTCHOOSE:
                child_search_input.setHint("按部门/拼音");
                break;
        }
        child_search_input.setEnabled(false);
            final SYS_DepartmentDAO mSYS_DepartmentDAO = new SYS_DepartmentDAO(getActivity(), null);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        if(Constant.ROOTNODE_STRINGID.equals("")){
                            Constant.ROOTNODE_STRINGID =  mSYS_DepartmentDAO.departmentCode(BookInit.getInstance().getPortalId());
                        }
                        currentSYS_Department = mSYS_DepartmentDAO.getDepartments(Constant.ROOTNODE_STRINGID, new SYS_Department());
                        UserChooseChildFragment.this.onComplete();

//								.getSYS_Department(UserDetailsChildFragment.this);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
//        } else {
//            progress_.setVisibility(View.GONE);
//            /**
//             * 下面是带部门的
//             */
//            /**
//             * 下面是带部门的
//             */
//            updateUserCheck(currentSYS_Department);
//            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
//                    currentSYS_Department, this,
//                    false, getActivity(),UserChooseChildFragment.this);
//            child_list.setAdapter(mUserDetailChildAdapter);
//            addUserListCache(currentSYS_Department);
//            ListAdapter listAdapter = child_list.getAdapter();
//            if (listAdapter == null) {
//                return;
//            }
//            View listItem = listAdapter.getView(0, null, child_list);
//            listItem.measure(0, 0);
//            itemHight =  listItem.getMeasuredHeight();
//            /**
//             * 下面是全部人员的
//             */
////            progress_.setVisibility(View.GONE);
////            addUserListCache(currentSYS_Department);
////            currentSYS_Department.getSYS_DepartmentList().clear();
////            currentSYS_Department.getSYS_User().clear();
////            currentSYS_Department.setSYS_User(new ArrayList<SYS_User>(userAllList));
////            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
////                    currentSYS_Department, this,
////                    false, getActivity(),UserChooseChildFragment.this);
////            child_list.setAdapter(mUserDetailChildAdapter);
//        }
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
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        // 当输入框有值的时候
        if(child_search_input == null){
            return false;
        }
        if (!TextUtils.isEmpty(child_search_input.getText().toString())) {
            child_search_input.setText("");
            return true;
        }
        if (upSYS_DepartmentList.size() > 0) {
            // 判断展示title
            if (upSYS_DepartmentList.size() == 1) {
                tv_child_name.setVisibility(View.GONE);
                ((ChooseGeneralActivity)getActivity()).setGone();
            } else {
                tv_child_name.setText(""
                        + upSYS_DepartmentList.get(
                        upSYS_DepartmentList.size() - 1).getFullName());
                tv_child_name.setVisibility(View.VISIBLE);
            }
            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                    upSYS_DepartmentList.get(upSYS_DepartmentList.size() - 1),
                    this, false, getActivity(),UserChooseChildFragment.this);
            child_list.setAdapter(mUserDetailChildAdapter);
            userAllList = new ArrayList<SYS_User>();
            departmentAll = new ArrayList<SYS_Department>();
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

    public void onAKeyBackPressed(){
        if (upSYS_DepartmentList.size() > 0) {

            tv_child_name.setVisibility(View.GONE);


            iv_no_book.setVisibility(View.GONE);
            child_list.setVisibility(View.VISIBLE);
            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                    upSYS_DepartmentList.get(0),
                    this, false, getActivity(),UserChooseChildFragment.this);
            child_list.setAdapter(mUserDetailChildAdapter);
            userAllList = new ArrayList<SYS_User>();
            departmentAll = new ArrayList<SYS_Department>();
            currentSYS_Department = upSYS_DepartmentList
                    .get(0); // 返回的时候把上一级
            // 也就是当前的Department拿到
            addUserListCache(currentSYS_Department);
            upSYS_DepartmentList.clear();
        }
    }

    @Override
    public void notifyDataSetChanged(SYS_Department upSYS_Department,
                                     SYS_Department mSYS_Department) {

        currentSYS_Department = mSYS_Department;
        /**
         * 部门选择的时候展示人员暂时给屏蔽掉
         */
        switch (mChooseWayEnum){
            case DEPARTMENTCHOOSE:
                currentSYS_Department.getSYS_User().clear();
                break;
        }
        if(currentSYS_Department.getSYS_DepartmentList() == null || currentSYS_Department.getSYS_DepartmentList().size() == 0){
            boolean isflag = false;
            switch (mChooseWayEnum){
                case DEPARTMENTCHOOSE:
                    isflag = true;
                    break;
            }
            if(isflag)
                return;
        }


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


        UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                mSYS_Department, this, false, getActivity(),UserChooseChildFragment.this);
        child_list.setAdapter(mUserDetailChildAdapter);
        userAllList = new ArrayList<SYS_User>();
        departmentAll = new ArrayList<SYS_Department>();
            addUserListCache(currentSYS_Department);
        ((ChooseGeneralActivity)getActivity()).setVisible();
    }

    // 缓存方法
    public void addUserListCache(SYS_Department currentSYS_Department) {
        if(currentSYS_Department == null){
            return;
        }
        getAllDepartment(currentSYS_Department);
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
    boolean isSoso = false;
    // 处理首页面的搜索方法
    private void searchUser(final String filterStr,final List<SYS_User> list) {

        final List<SYS_User> filterDateList = new ArrayList<SYS_User>();
        final List<SYS_Department> departmentSearch = new ArrayList<SYS_Department>();
        if (TextUtils.isEmpty(filterStr)) {
            ll_no_neirong.setVisibility(View.GONE);
            isSoso = false;
            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                    currentSYS_Department, this, false, getActivity(),UserChooseChildFragment.this);
            mUserDetailChildAdapter.setSoso(false);
            child_list.setAdapter(mUserDetailChildAdapter);
        } else {
            isSoso = true;
            filterDateList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final SYS_Department mSYS_Department = new SYS_Department();
                    mSYS_Department.setDepartmentCode(currentSYS_Department
                            .getDepartmentCode());
                    mSYS_Department.setFullName(currentSYS_Department.getFullName());
                    mSYS_Department.setParentDepartment(currentSYS_Department
                            .getParentDepartment());
                    switch (mChooseWayEnum){
                        case PEOPLECHOOSE: //人员选择
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

                            mSYS_Department
                                    .setSYS_User(new ArrayList<SYS_User>(filterDateList));
                            break;
                        case DEPARTMENTCHOOSE: //部门选择
                            for(SYS_Department sortModel : departmentAll){
                                String name = sortModel.getFullName();
                                String suoxie = sortModel.getSuoxie();
                                if (name.indexOf(filterStr.toString()) != -1
                                        || suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
                                        || characterParser.getSelling(name).contains(
                                        filterStr.toString())) {
                                    departmentSearch.add(sortModel);
                                }
                            }
                            mSYS_Department.setSYS_DepartmentList(departmentSearch);
                            break;
                        case FREECHOOSE: //自由选择

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

                            mSYS_Department
                                    .setSYS_User(new ArrayList<SYS_User>(filterDateList));

                            for(SYS_Department sortModel : departmentAll){
                                String name = sortModel.getFullName();
                                String suoxie = sortModel.getSuoxie();
                                if (name.indexOf(filterStr.toString()) != -1
                                        || suoxie.indexOf(filterStr.toString()) != -1 || suoxie.toLowerCase().indexOf(filterStr.toString()) != -1
                                        || characterParser.getSelling(name).startsWith(
                                        filterStr.toString())) {
                                    departmentSearch.add(sortModel);
                                }
                            }
                            mSYS_Department.setSYS_DepartmentList(departmentSearch);

                            break;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if(!isSoso){
                                return;
                            }
                            switch (mChooseWayEnum) {
                                case PEOPLECHOOSE: //人员选择
                                    if(mSYS_Department.getSYS_User() == null || mSYS_Department.getSYS_User().size() == 0){
                                        ll_no_neirong.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_no_neirong.setVisibility(View.GONE);
                                    }


                                    break;
                                case DEPARTMENTCHOOSE: //部门选择
                                    if(mSYS_Department.getSYS_DepartmentList() == null || mSYS_Department.getSYS_DepartmentList().size() == 0){
                                        ll_no_neirong.setVisibility(View.VISIBLE);
                                    }else{
                                        ll_no_neirong.setVisibility(View.GONE);
                                    }

                                    break;
                            }


                            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                                    mSYS_Department, UserChooseChildFragment.this, true, getActivity(),UserChooseChildFragment.this);
                            mUserDetailChildAdapter.setSoso(true);
                            child_list.setAdapter(mUserDetailChildAdapter);
                        }
                    });
                }
            }).start();


        }
    }
    ArrayList<SYS_Department> departmentAll ;
    /**
     * 获取该集目录下所有子部门
     */
    public void getAllDepartment(SYS_Department mSYS_Department){
        departmentAll.addAll(mSYS_Department.getSYS_DepartmentList());
        Iterator in = mSYS_Department.getSYS_DepartmentList().iterator();
        while (in.hasNext()) {
            SYS_Department supSYS_Department = (SYS_Department) in.next();
            getAllDepartment(supSYS_Department);
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
            onBackPressed();

        } else if (view.getId() == R.id.title_right_new_function) {
            BookInit.getInstance().getmCallbackMX().callAddBook();
        }
    }
    @Override
    public void checkUser(SYS_User mUser,ArrayList<SYS_User> checkAllUser,boolean isCheck,ImageView ivAvatar,CheckBox cbPeople,TextView default_tv,boolean isImage) {
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
        mCallBackChoosePeople.callBackChoosePeopleMessage(checkAllUser, checkAllUser.size(), obmp,ivAvatar,default_tv,isImage,mUser.getColor(),itemHight,isCheck,cbPeople);

    }
    @Override
    public void checkDepartment(SYS_Department crrentDepartment,ArrayList<SYS_Department> checkDepartment,boolean isCheck, ImageView ivAvatar, CheckBox mCb) {

        Bitmap obmp = null;
        if(isCheck){
            checkDepartment.add(crrentDepartment);
            ivAvatar.setDrawingCacheEnabled(true);
            obmp = Bitmap.createBitmap(ivAvatar.getDrawingCache());
            ivAvatar.setDrawingCacheEnabled(false);
        }else{
        }
        for(SYS_Department mSYS_Department:checkDepartment){
            getCheckAllUser(mSYS_Department);
        }
        //回调选择部门
        mCallBackChoosePeople.callBackChooseDepartment(checkDepartment,checkDepartment.size(), obmp,ivAvatar,itemHight,isCheck,mCb);


    }
    // 获取已选择的所有user
    public void getCheckAllUser(SYS_Department currentSYS_Department) {
        Iterator in = currentSYS_Department.getSYS_DepartmentList().iterator();
        while (in.hasNext()) {
            SYS_Department supSYS_Department = (SYS_Department) in.next();
            getCheckAllUser(supSYS_Department);
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
            return ;
        }
        getActivity().runOnUiThread((new Runnable() {
            @Override
            public void run() {
                child_search_input.setEnabled(true);
                mCallBackChoosePeople.callBackProGone();
                progress_.setVisibility(View.GONE);
                UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                        currentSYS_Department, UserChooseChildFragment.this, false,
                        getActivity(), UserChooseChildFragment.this);
                mUserDetailChildAdapter.setSoso(false);
                child_list.setAdapter(mUserDetailChildAdapter);
                addUserListCache(currentSYS_Department);
                BaseApplication.getApplication(getActivity()).setUserListAll(userAllList);
                ListAdapter listAdapter = child_list.getAdapter();
                if (listAdapter == null) {
                    return;
                }
                View listItem = listAdapter.getView(0, null, child_list);
                try{
                    if(listItem != null) {
                        listItem.measure(0, 0);
                        itemHight = listItem.getMeasuredHeight();
                    }
                }catch (Exception e){

                }
            }
        }));
    }

}
