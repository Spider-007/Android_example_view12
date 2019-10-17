package com.htmitech.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.adapter.UserDetailChooseChildAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.app.CharacterParser;
import com.htmitech.app.widget.CustomEditText;
import com.htmitech.base.BaseFragment;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.listener.AddressListener;
import com.htmitech.listener.CallBackChoosePeople;
import com.htmitech.listener.CallCheckUserListener;
import com.htmitech.listener.ChildFragmentListener;
import com.htmitech.myEnum.CheckStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 系统选择（第三方选择）
 * @author Tony
 *
 */
public class ChooseSystemFragment extends BaseFragment implements CallCheckUserListener,ChildFragmentListener,TextWatcher {
    private CallBackChoosePeople mCallBackChoosePeople;
    private ListView child_list;
    private ArrayList<SYS_User> userList ;
    private SYS_Department currentSYS_Department;
    private CustomEditText mCustomEditText;
    private LinearLayout ll_no_neirong;
    private CharacterParser characterParser;// 汉字转拼音
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.ht_fragment_choose_system, container, false);
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
        return false;
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        child_list = (ListView) getView().findViewById(R.id.child_list);
        mCustomEditText = (CustomEditText) getView().findViewById(R.id.child_search_input);
        ll_no_neirong = (LinearLayout) getView().findViewById(R.id.ll_no_neirong);

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        Bundle args = this.getArguments();
        characterParser = CharacterParser.getInstance();
        userList = (ArrayList<SYS_User>) args.getSerializable("systemUser");
        currentSYS_Department = new SYS_Department();
        currentSYS_Department.getSYS_DepartmentList().clear();
        currentSYS_Department.getSYS_User().clear();
        currentSYS_Department.setSYS_User(new ArrayList<SYS_User>(userList));
        UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                currentSYS_Department, this,
                false, getActivity(),this);
        mUserDetailChildAdapter.system = true;
        mCustomEditText.addTextChangedListener(this);
        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(500);
        //1f为延时
        LayoutAnimationController controller = new LayoutAnimationController(animation, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        child_list.setLayoutAnimation(controller);

        child_list.setAdapter(mUserDetailChildAdapter);
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
        mCallBackChoosePeople.callBackChoosePeopleMessage(checkAllUser, checkAllUser.size(), obmp,ivAvatar,default_tv,isImage,mUser.getColor(),0,isCheck,mCb);

    }

    @Override
    public void checkDepartment(SYS_Department crrentDepartment, ArrayList<SYS_Department> checkDepartment, boolean isCheck, ImageView ivAvatar, CheckBox mCb) {
        /**
         * 暫時沒有部門
         */
    }

    @Override
    public void notifyDataSetChanged(SYS_Department upSYS_Department, SYS_Department mSYS_Department) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchUser(charSequence.toString(), userList);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    // 处理首页面的搜索方法
    private void searchUser(final String filterStr,final List<SYS_User> list) {

        final List<SYS_User> filterDateList = new ArrayList<SYS_User>();
        final List<SYS_Department> departmentSearch = new ArrayList<SYS_Department>();
        if (TextUtils.isEmpty(filterStr)) {
            ll_no_neirong.setVisibility(View.GONE);
            currentSYS_Department.setSYS_User(new ArrayList<SYS_User>(userList));
            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                    currentSYS_Department, this,
                    false, getActivity(),this);
            child_list.setAdapter(mUserDetailChildAdapter);
        } else {
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

                    currentSYS_Department.setSYS_User(new ArrayList<SYS_User>(filterDateList));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserDetailChooseChildAdapter mUserDetailChildAdapter = new UserDetailChooseChildAdapter(
                                    currentSYS_Department, ChooseSystemFragment.this,
                                    false, getActivity(), ChooseSystemFragment.this);
                            child_list.setAdapter(mUserDetailChildAdapter);
                        }
                    });
                }
            }).start();

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
}
